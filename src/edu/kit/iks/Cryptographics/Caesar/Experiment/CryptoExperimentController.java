/**
 * Copyright (c) 2014 Matthias Jaenicke <matthias.jaenicke@student.kit.edu>,
 * Matthias Plappert <undkc@student.kit.edu>,
 * Julien Duman <uncyc@student.kit.edu>, 
 * Christian Dreher <uaeef@student.kit.edu>,
 * Wasilij Beskorovajnov <uajkm@student.kit.edu> and 
 * Aydin Tekin <aydin.tekin@student.kit.edu>
 * 
 * Released under the MIT license (refer to LICENSE.md)
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package edu.kit.iks.Cryptographics.Caesar.Experiment;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xnap.commons.i18n.I18n;

import edu.kit.iks.Cryptographics.VisualizationContainerController;
import edu.kit.iks.CryptographicsLib.AbstractVisualizationController;
import edu.kit.iks.CryptographicsLib.AbstractVisualizationInfo;
import edu.kit.iks.CryptographicsLib.AlphabetStripView;
import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.ImageView;
import edu.kit.iks.CryptographicsLib.KeyboardView;
import edu.kit.iks.CryptographicsLib.Logger;
import edu.kit.iks.CryptographicsLib.MouseClickListener;

/**
 * Controller for the first and second step of the experiment phase. When user has to put input and
 * encrypt it and in the second step to decrypt a given cipher. Controls the needed elements from
 * {@link CryptoExperimentView}
 * 
 * @author Wasilij Beskorovajnov.
 * 
 */
public class CryptoExperimentController extends AbstractVisualizationController {

	/**
	 * localization.
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(
			CryptoExperimentController.class);

	/**
	 * Indicates what phase of the experiment currently is.
	 */
	private boolean decryptionPhase = false;

	/**
	 * When 0 user has finished the experiment successfully.
	 */
	private int editableFields;

	/**
	 * Model of this controller.
	 */
	private CryptoModel model;

	private Element cryptoResources;

	/**
	 * Constructor.
	 * 
	 * @param visualizationInfo
	 */
	public CryptoExperimentController(
			AbstractVisualizationInfo visualizationInfo) {
		super(visualizationInfo);

		SAXBuilder saxBuilder = new SAXBuilder();

		// obtain file object
		InputStream is = this.getClass().getResourceAsStream(
				"/caesar/CaesarResources.xml");

		try {
			// converted file to document object
			Document document = saxBuilder.build(is);

			// get root node from xml
			this.cryptoResources = document.getRootElement();
		} catch (JDOMException | IOException e) {
			Logger.error(e);
		}

	}

	@Override
	public String getHelp() {
		if (this.decryptionPhase) {
			return CryptoExperimentController.i18n
					.tr("Remember: If you want to decrypt for example D with the key of 3."
							+ " You need to substract 3 from the position of D in the alphabet."
							+ " D - key = 4 - 3 = 1 = A. And if you get a negative Value add 26."
							+ " For example: A - 8 = 1 - 8 = -7. Now add 26 + (-7) = 19 = S.");
		}
		return CryptoExperimentController.i18n
				.tr("Remember: If you want to encrypt for example A with the key of 3."
						+ " You need to add 3 to the position of A in the alphabet."
						+ " A + key = 1 + 3 = 4 = D. And if you get a value that is bigger then 26"
						+ " then substract 26 from it. For example: S + 8 = 19 + 8 = 27."
						+ " Now substract 26: 27 - 26 = 1 = A.");

	}

	@Override
	public void loadView() {
		this.view = new CryptoExperimentView();
		this.model = CryptoModel.getInstance();
		// set the initial value.
		this.editableFields = 2;

		// Create all needed ActionListener.
		this.generateGeneratorButtonActionListener();

		// generate listener for the textfield of the key.
		this.generateKeyInputFocusListener();
		this.generateKeyInputActionListener();

		// generate listener for the literal input textfield.
		this.generateLiteralInputFocusListener();
		this.generateLiteralInputActionListener();

		// Listener for Back/next buttons.
		this.generateNavigationListener();

	}

	// ------------------------------------------------------------------//
	// --------------------private methods-------------------------------//
	/**
	 * Function for generating ActionListener for the needed input fields, after the user typed a
	 * string to encrypt.
	 * 
	 * @param inputChars
	 */
	private void generateIOListener(char[] inputChars) {
		// Generate Listener for the userOutput JTextfield
		for (int i = 0; i < inputChars.length; i++) {

			// Needed for delegating to the inner type ActionListener, when the actionEvent from the
			// Button "ENTER" on the KEayboard is fired.
			final JTextField userOutput = this.getView().getUserOutput()[i];

			this.generateUserOutputFocusListener(i);
			this.generateUserOutputActionListener(i, userOutput);
		}
	}

	private void generateUserOutputActionListener(int i,
			final JTextField userOutput) {
		this.getView().getUserOutput()[i]
				.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (userOutput.isEditable()) {
							int key = 0;
							try {
								key = Integer
										.parseInt(CryptoExperimentController.this
												.getView().getKeyInput()
												.getText());
							} catch (NumberFormatException e1) {
								Logger.error(e1);
							}
							// If the phase is decrypting use dec, else the phase is
							// encrypting, therefore use enc.
							String encryptedOrDecryptedcipher = "";
							if (CryptoExperimentController.this.decryptionPhase) {
								encryptedOrDecryptedcipher = CryptoExperimentController.this
										.getModel().dec(key,
												userOutput.getName());
							} else {
								encryptedOrDecryptedcipher = CryptoExperimentController.this
										.getModel().enc(key,
												userOutput.getName());
							}

							if ((encryptedOrDecryptedcipher).equals(userOutput
									.getText())) {
								CryptoExperimentController.this
										.setFeedbackImage("CaesarPositive");
								// user encrypted/decrypted correctly.
								if ((CryptoExperimentController.this
										.getEditableFields() - 1) != 0) {

									// still some editabe fields left.
									CryptoExperimentController.this
											.notifyUserValidAction(userOutput);
								} else {
									// User encrypted all characters valid.
									CryptoExperimentController.this
											.notifyUserFinishedExperiment(userOutput);

								}
							} else {
								userOutput.setText("");
								CryptoExperimentController.this
										.setFeedbackImage("CaesarNegative");
								// User encrypted invalid! Need another try.
								CryptoExperimentController.this
										.notifyUserInvalidAction(userOutput);
							}

						}
					}

				});
	}

	private void setFeedbackImage(String resourceID) {
		this.getView().getFeedback().removeAll();
		String path = "";
		try {
			path = CryptoExperimentController.this.getCryptoResources()
					.getChild("CryptoResources").getChild(resourceID)
					.getAttributeValue("path");
		} catch (NullPointerException nullException) {
			// Element not found.
			System.out.println("[NullPointerException] Ressource not found.");
			nullException.printStackTrace();
		}

		GridBagConstraints imgConstraint = new GridBagConstraints();
		imgConstraint.gridx = this.getView().getUserInput().length + 1;
		imgConstraint.gridy = 0;
		ImageView imageToSet = new ImageView(path);
		this.getView().getFeedback().add(imageToSet, imgConstraint);

		this.getView().validate();
		this.getView().repaint();
	}

	private void notifyUserInvalidAction(JTextField userOutput) {
		this.getView().getExplanations()
				.setText(this.getModel().genRandomBlamings());
		userOutput.requestFocus();
	}

	private void notifyUserFinishedExperiment(JTextField userOutput) {
		userOutput.setBorder(BorderFactory.createLineBorder(Color.green));
		this.getView().requestFocus();
		userOutput.setEditable(false);
		this.getView()
				.getAlphabet()
				.unHighlight(
						userOutput.getName().charAt(0)
								- this.getModel().ASCII_UC_A);
		String notifyString = "";
		if (!this.decryptionPhase) {
			notifyString = this
					.wrapHtml(CryptoExperimentController.i18n
							.tr("All done right!")
							+ " "
							+ this.getModel().genRandomGrats()
							+ " "
							+ CryptoExperimentController.i18n
									.tr("The next step is to decrypt a given message! Let's move on to decrypting."));
		} else {
			notifyString = this
					.wrapHtml(CryptoExperimentController.i18n
							.tr("All done right!")
							+ " "
							+ this.getModel().genRandomGrats()
							+ " "
							+ CryptoExperimentController.i18n
									.tr("In the next stage of the visualization you will learn how to decrypt without a key."));
		}

		this.getView().getExplanations().setText(notifyString);
		this.getView().removeKeyboard();
		this.getView().removeAlphabet();

		this.getView().getNavigationPanel()
				.remove(this.getView().getNextButton());
		GridBagConstraints nextConst = new GridBagConstraints();
		nextConst.anchor = GridBagConstraints.CENTER;
		nextConst.weightx = 1.0;
		nextConst.weighty = 0.1;
		nextConst.gridx = 1;
		nextConst.gridy = 1;
		nextConst.gridwidth = 26;
		nextConst.gridheight = 2;
		this.getView().add(this.getView().getNextButton(), nextConst);

		this.getView().requestFocus();
	}

	private void notifyUserValidAction(JTextField userOutput) {
		userOutput.setBorder(BorderFactory.createLineBorder(Color.green));
		userOutput.setEditable(false);
		this.setEditableFields(this.getEditableFields() - 1);
		this.getView()
				.getExplanations()
				.setText(
						this.getModel().genRandomGrats()
								+ " "
								+ CryptoExperimentController.i18n.trn(
										"You have {0} left!",
										"You have {0} left!",
										this.getEditableFields(),
										this.getEditableFields()));
		this.getView().getUserOutput()[CryptoExperimentController.this
				.getView().getUserOutput().length - this.getEditableFields()]
				.requestFocus();
	}

	private void generateUserOutputFocusListener(int i) {
		this.getView().getUserOutput()[i].addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {

				JTextField output = (JTextField) e.getSource();

				CryptoExperimentController.this.resetUnfocusedTextfield();

				if (CryptoExperimentController.this.getView().getKeyboard() != null) {
					CryptoExperimentController.this.getView().removeKeyboard();
				}
				if (output.isEditable()) {
					// highlights the character in the alphabet.
					CryptoExperimentController.this.highlightUserFocus(output);
				}

			}

			@Override
			public void focusLost(FocusEvent e) {
				// Nothing to do here.

			}
		});
	}

	private void highlightUserFocus(JTextField output) {
		int charToEncryptAscii = output.getName().charAt(0);
		AlphabetStripView viewAlphabet = CryptoExperimentController.this
				.getView().getAlphabet();
		viewAlphabet.highlight(charToEncryptAscii
				- CryptoExperimentController.this.getModel().ASCII_UC_A);

		output.setBorder(BorderFactory.createLineBorder(Color.blue, 5));
		CryptoExperimentController.this.getView().createKeyboard(output,
				KeyboardView.CHAR_MODE);
	}

	private void resetUnfocusedTextfield() {
		for (JTextField outputIterator : this.getView().getUserOutput()) {

			if (outputIterator.getText() != null
					&& this.getView().getAlphabet() != null) {
				int charToEncryptAscii = outputIterator.getName().charAt(0);
				AlphabetStripView viewAlphabet = this.getView().getAlphabet();
				viewAlphabet.unHighlight(charToEncryptAscii
						- this.getModel().ASCII_UC_A);
				if (outputIterator.isEditable()) {
					outputIterator.setBorder(BorderFactory
							.createLineBorder(Color.darkGray));
				}
			}
		}
	}

	private void generateNavigationListener() {
		this.getView().getBackButton().addActionListener(new ActionListener() {
			/*
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt .event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent event) {
				// When switching back from demonstration to
				// experiment in the encryption phase, the variable will remain set to true when not
				// reset.
				if (CryptoExperimentController.this.decryptionPhase) {
					CryptoExperimentController.this.decryptionPhase = false;
				}
				// load next state.
				VisualizationContainerController containerController = (VisualizationContainerController) CryptoExperimentController.this
						.getParentController();
				containerController.presentPreviousVisualizationController();
			}
		});
		this.getView().getNextButton().addActionListener(new ActionListener() {
			/*
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt .event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent event) {
				if (!CryptoExperimentController.this.decryptionPhase) {

					CryptoExperimentController.this.getView().remove(
							CryptoExperimentController.this.getView()
									.getNextButton());

					// set up the aligment of the button Next;
					CryptoExperimentController.this.getView().getNextButton()
							.setPreferredSize(new Dimension(300, 50));
					CryptoExperimentController.this
							.getView()
							.getNavigationPanel()
							.add(CryptoExperimentController.this.getView()
									.getNextButton(), BorderLayout.EAST);

					// Keys bigger then 10 could be too annoying. 1 as key is also too simple.
					int key = CryptoExperimentController.this.getModel()
							.generateRandomInt(2, 10);

					// start Decryption!
					if (CryptoExperimentController.this.getView().getKeyboard() != null) {
						CryptoExperimentController.this.getView()
								.removeKeyboard();
					}
					CryptoExperimentController.this.decryptionPhase = true;
					char[] cipher = CryptoExperimentController.this
							.getModel()
							.enc(key,
									CryptoExperimentController.this.getModel()
											.genRandomPlainSequence())
							.toString().toCharArray();

					CryptoExperimentController.this.prepareExperimentPhase(key,
							cipher);

					// set the explanations.
					CryptoExperimentController.this
							.getView()
							.getExplanations()
							.setText(
									CryptoExperimentController.this.wrapHtml(CryptoExperimentController.i18n
											.tr("You have already learned much. But you've probably already asked yourself: How do I decrypt?")
											+ "<br>"
											+ CryptoExperimentController.i18n
													.tr("It's simple: subtract the key from a given letter. And if you get a negative value add 26 to it.")));
					CryptoExperimentController.this
							.getView()
							.getNextButton()
							.setText(
									CryptoExperimentController.i18n
											.tr("Go to Histograms"));

				} else {
					// load the previous state.
					VisualizationContainerController containerController = (VisualizationContainerController) CryptoExperimentController.this
							.getParentController();
					containerController.presentNextVisualizationController();
				}
			}

		});
	}

	private void generateLiteralInputActionListener() {
		this.getView().getLiteralInput()
				.addActionListener(new ActionListener() {

					/*
					 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
					 * ActionEvent)
					 */
					@Override
					public void actionPerformed(ActionEvent e) {
						if (CryptoExperimentController.this.getView()
								.getLiteralInput().isEditable()) {

							// input possible.
							String explanationContent = CryptoExperimentController.this
									.getView().getExplanations().getText();
							String input = CryptoExperimentController.this
									.getView().getLiteralInput().getText();

							if (CryptoExperimentController.this.getModel()
									.isInputValid(input)) {
								// input is valid.
								if ((CryptoExperimentController.this
										.getEditableFields() - 1) != 0) {
									// for proceeding key input is needed.
									CryptoExperimentController.this
											.notifyUserValidLiteralInput(explanationContent);

								} else {
									// it was the last needed input.
									int key = 0;
									try {
										key = Integer
												.parseInt(CryptoExperimentController.this
														.getView()
														.getKeyInput()
														.getText());
									} catch (NumberFormatException e1) {
										e1.printStackTrace();
									}

									// refactor the input into an character array.
									char[] inputChars = new char[input.length()];
									input.getChars(0, input.length(),
											inputChars, 0);
									CryptoExperimentController.this.getView()
											.getKeyInput().setBorder(null);
									CryptoExperimentController.this.getView()
											.removeKeyboard();
									CryptoExperimentController.this
											.prepareExperimentPhase(key,
													inputChars);

								}
							} else {
								CryptoExperimentController.this
										.getView()
										.getExplanations()
										.setText(
												CryptoExperimentController.i18n
														.tr("Your input is invalid. Please try another one!"));
							}

						}
					}
				});
	}

	private void notifyUserValidLiteralInput(String explanationContent) {
		this.getView()
				.getExplanations()
				.setText(
						explanationContent
								+ "<br>"
								+ CryptoExperimentController.i18n
										.tr("This input is ok. Now only the key is left."));
		this.getView().getLiteralInput()
				.setBorder(BorderFactory.createLineBorder(Color.green));
		this.getView().getLiteralInput().setEditable(false);
		this.setEditableFields((this.getEditableFields() - 1));
		this.getView().getKeyInput().requestFocus();
	}

	private void generateGeneratorButtonActionListener() {
		this.getView().getGenerator()
				.addMouseListener(new MouseClickListener() {
					@Override
					public void clicked(MouseEvent e) {
						if (CryptoExperimentController.this.getView()
								.getNumpad() != null) {
							CryptoExperimentController.this.getView()
									.removeNumpad();
						}

						// make some preparations.
						char[] generatedCharacters = CryptoExperimentController.this
								.getModel().genRandomPlainSequence()
								.toCharArray();
						// Keys bigger then 10 can be annoying and smaller then 2 senseless.
						int key = CryptoExperimentController.this.getModel()
								.generateRandomInt(2, 10);
						CryptoExperimentController.this.prepareExperimentPhase(
								key, generatedCharacters);
					}
				});
	}

	private void generateLiteralInputFocusListener() {
		this.getView().getLiteralInput().addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {

				if (CryptoExperimentController.this.getView().getKeyInput()
						.getBorder() != null
						&& CryptoExperimentController.this.getView()
								.getKeyInput().isEditable()) {
					CryptoExperimentController.this.getView().getKeyInput()
							.setBorder(BorderFactory.createLineBorder(Color.black));

				}

				if (CryptoExperimentController.this.getView().getKeyboard() != null) {
					CryptoExperimentController.this.getView().removeKeyboard();
				} else if (CryptoExperimentController.this.getView()
						.getNumpad() != null) {
					CryptoExperimentController.this.getView().removeNumpad();
				}

				if (CryptoExperimentController.this.getView().getLiteralInput()
						.isEditable()) {
					CryptoExperimentController.this
							.getView()
							.getLiteralInput()
							.setBorder(
									BorderFactory.createLineBorder(Color.blue,
											5));
					CryptoExperimentController.this.getView().createKeyboard(
							CryptoExperimentController.this.getView()
									.getLiteralInput());
					CryptoExperimentController.this.getView().repaint();
					CryptoExperimentController.this.getView().getKeyboard().repaint();
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// Nothing to do here.
			}
		});
	}

	private void generateKeyInputActionListener() {
		this.getView().getKeyInput().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (CryptoExperimentController.this.getView().getKeyInput()
						.isEditable()) {
					// input still possible.
					String explanationContent = CryptoExperimentController.this
							.getView().getExplanations().getText();
					if (CryptoExperimentController.this.getView().getKeyInput()
							.getText().length() > 0) {
						int key = 0;
						try {
							key = Integer
									.parseInt(CryptoExperimentController.this
											.getView().getKeyInput().getText());
						} catch (NumberFormatException e1) {
							Logger.error(e1);
						}

						if (CryptoExperimentController.this.getModel()
								.isKeyValid(key)) {
							// User typed a valid key.
							if ((CryptoExperimentController.this
									.getEditableFields() - 1) != 0) {
								// Only literal input needed. Notify the user.
								CryptoExperimentController.this
										.notifyOfValidKey(explanationContent);
							} else {
								CryptoExperimentController.this.getView()
										.removeNumpad();
								// The key was the last needed input. Start Experiment with the
								// input values.
								String input = CryptoExperimentController.this
										.getView().getLiteralInput().getText();
								char[] inputChars = new char[input.length()];
								input.getChars(0, input.length(), inputChars, 0);
								CryptoExperimentController.this
										.prepareExperimentPhase(key, inputChars);
							}
						} else {
							CryptoExperimentController.this
									.getView()
									.getExplanations()
									.setText(
											explanationContent
													+ "<br>"
													+ CryptoExperimentController.this
															.wrapHtml(CryptoExperimentController.i18n
																	.tr("This key is not valid. Please put a number between 1 and 26."
																			+ " For demonstration purposes the keys between -1 and -26 are not necessary"
																			+ " therefore not possible, but could be used in general as keys too.")));
							CryptoExperimentController.this
									.getView()
									.getKeyInput()
									.setBorder(
											BorderFactory
													.createLineBorder(Color.red));
						}
					} else {
						CryptoExperimentController.this
								.getView()
								.getExplanations()
								.setText(
										explanationContent
												+ "<br>"
												+ CryptoExperimentController.i18n
														.tr("The key field is empty!"));
						CryptoExperimentController.this
								.getView()
								.getKeyInput()
								.setBorder(
										BorderFactory
												.createLineBorder(Color.red));
					}
				}
			}
		});
	}

	private void prepareExperimentPhase(int key, char[] input) {

		this.setEditableFields(input.length);

		// load the view!
		this.getView().setupExperimentCore(input, key);

		// Generate Listener for the userOutput JTextfield
		this.generateIOListener(input);

		this.getView().getUserOutput()[0].requestFocus();
	}

	private void notifyOfValidKey(String explanationContent) {
		this.getView()
				.getExplanations()
				.setText(
						explanationContent
								+ " "
								+ CryptoExperimentController.i18n
										.tr("This key is ok. Now put your name into the bigger box to the left."));
		this.getView().getKeyInput()
				.setBorder(BorderFactory.createLineBorder(Color.green));
		this.getView().getKeyInput().setEditable(false);
		this.setEditableFields((this.getEditableFields() - 1));
		this.getView().getLiteralInput().requestFocus();
	}

	private void generateKeyInputFocusListener() {
		this.getView().getKeyInput().addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {

				if (CryptoExperimentController.this.getView().getLiteralInput()
						.getBorder() != null
						&& CryptoExperimentController.this.getView()
								.getLiteralInput().isEditable()) {
					CryptoExperimentController.this.getView().getLiteralInput()
							.setBorder(BorderFactory.createLineBorder(Color.black));

				}

				if (CryptoExperimentController.this.getView().getKeyboard() != null) {
					CryptoExperimentController.this.getView().removeKeyboard();
				} else if (CryptoExperimentController.this.getView()
						.getNumpad() != null) {
					CryptoExperimentController.this.getView().removeNumpad();
				}

				if (CryptoExperimentController.this.getView().getKeyInput()
						.isEditable()) {
					CryptoExperimentController.this
							.getView()
							.getKeyInput()
							.setBorder(
									BorderFactory.createLineBorder(Color.blue,
											5));
					CryptoExperimentController.this.getView().createNumpad(
							CryptoExperimentController.this.getView()
									.getKeyInput());
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				// Nothing to do here.
			}
		});
	}

	/**
	 * @return the editableFields
	 */
	public int getEditableFields() {
		return this.editableFields;
	}

	/**
	 * @return the model
	 */
	public CryptoModel getModel() {
		return this.model;
	}

	/**
	 * Gets the view
	 * 
	 * @return The view of this controller
	 */
	@Override
	public CryptoExperimentView getView() {
		return (CryptoExperimentView) this.view;
	}

	/**
	 * @return the decryptionPhase
	 */
	public boolean isDecryptionPhase() {
		return this.decryptionPhase;
	}

	/**
	 * @param editableFields
	 *            the editableFields to set
	 */
	public void setEditableFields(int editableFields) {
		this.editableFields = editableFields;
	}

	/**
	 * 
	 */
	public void unloadInOut() {
		this.getView().remove(this.getView().userCharacterIOContainer());
		this.getView().setInOutPanel(null);

	}

	@Override
	public void unloadView() {

	}

	private String wrapHtml(String text) {
		return "<html><body><div width=900px>" + text + "</div></body></html>";
	}

	/**
	 * @return the cryptoResources
	 */
	public Element getCryptoResources() {
		return cryptoResources;
	}

	/**
	 * @param cryptoResources
	 *            the cryptoResources to set
	 */
	public void setCryptoResources(Element cryptoResources) {
		this.cryptoResources = cryptoResources;
	}
}
