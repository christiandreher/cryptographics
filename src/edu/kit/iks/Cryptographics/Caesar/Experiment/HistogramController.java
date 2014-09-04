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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.Cryptographics.VisualizationContainerController;
import edu.kit.iks.CryptographicsLib.AbstractVisualizationController;
import edu.kit.iks.CryptographicsLib.AbstractVisualizationInfo;
import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.Logger;
import edu.kit.iks.CryptographicsLib.MouseClickListener;

/**
 * Controller for the last step of the histogram experiment phase.
 * 
 * @author Wasilij Beskorovajnov.
 * 
 */
public class HistogramController extends AbstractVisualizationController {

	/**
	 * localization.
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(
			HistogramController.class);

	/**
	 * The model of this Controller.
	 */
	private CryptoModel model;

	/**
	 * Integer value that shows what step the visualization has proceed.
	 */
	private int step;

	/**
	 * Constructor
	 * 
	 * @param visualizationInfo
	 */
	public HistogramController(AbstractVisualizationInfo visualizationInfo) {
		super(visualizationInfo);
	}

	@Override
	public void loadView() {
		this.view = new HistogramView();
		this.model = CryptoModel.getInstance();
		this.step = 0;

		// generate listener for the button proceed.
		this.generateProceedListener();

		// generate listener for the next/back buttons.
		this.generateNavigationActionListener();

	}

	@Override
	public String getHelp() {
		if (this.getView().getHistogramCipher() != null) {
			return HistogramController.i18n
					.tr("Remember 'E' is in most cases the most frequent letter in a text."
							+ " The column in the histogram above E would be the tallest one."
							+ " So the tallest column in the histogram of a cipher would be the value E was shifted to."
							+ " For example, 'H' is the tallest one. Simply 'H'- 'E' = 8 - 5 = 3. Et voila 3 is the key.");
		}
		return HistogramController.i18n
				.tr("In order to find an unknown key you must try all of them."
						+ " If you are lucky, you dont have to try many. In the worst case you have to try all of them.");
	}

	@Override
	public void unloadView() {
		this.view = null;
		this.model = null;
	}

	// ---------------------------------------------------------------------------//
	// -------------------------------private methods---------------------------//

	private void generateKeyInputActionListener() {
		// Not really much input checking necessary because the keyboard has only numerical values.
		this.getView().getKeyInput().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (HistogramController.this.getView().getKeyInput().getText()
						.length() > 0) {
					int key = 0;
					try {
						key = Integer.parseInt(HistogramController.this
								.getView().getKeyInput().getText());
					} catch (NumberFormatException e1) {
						Logger.error(e1);
					}
					if (HistogramController.this.getModel().isKeyValid(key)) {
						String decryptedCipher = HistogramController.this
								.getModel().dec(
										key,
										HistogramController.this.getView()
												.getHistogramCipher());
						HistogramController.this
								.getView()
								.getPlainText()
								.setText(
										HistogramController.this.wrapHtml(
												decryptedCipher, 400));

						if (key == HistogramController.this.getView()
								.getSecretKey()) {
							HistogramController.this
									.getView()
									.getKeyInput()
									.setBorder(
											BorderFactory
													.createLineBorder(Color.green));
							String explanations = HistogramController.this.wrapHtml(
									HistogramController.i18n
											.tr("You found the right key! See how easy it is with histograms?"
													+ " If you want to try one more press proceed. Otherwise you can go directly to further information."
													+ " There you can learn more about caesar's cipher."),
									600);
							HistogramController.this.getView()
									.getExplanations().setText(explanations);
							HistogramController.this.getView().getProceed()
									.setVisible(true);
							HistogramController.this.getView().requestFocus();
							HistogramController.this.getView().remove(
									HistogramController.this.getView()
											.getNumpad());
							HistogramController.this.getView().setNumpad(null);
							HistogramController.this.getView().validate();
							HistogramController.this.getView().repaint();

						} else {
							HistogramController.this.getView().getKeyInput()
									.setText("");
							String explanations = HistogramController.this.wrapHtml(
									HistogramController.this.getModel()
											.genRandomBlamings()
											+ " "
											+ HistogramController.i18n
													.tr("The key was wrong."),
									600);
							HistogramController.this.getView()
									.getExplanations().setText(explanations);
							HistogramController.this
									.getView()
									.getKeyInput()
									.setBorder(
											BorderFactory
													.createLineBorder(Color.red));
						}

					} else {
						String explanations = HistogramController.this.wrapHtml(
								HistogramController.this.getModel()
										.genRandomBlamings()
										+ " "
										+ HistogramController.i18n
												.tr("This key is invalid. Please type a number between 1 and 26. If you dont understand why,"
														+ " it is recommended to go to early stages."),
								600);
						HistogramController.this.getView().getExplanations()
								.setText(explanations);
						HistogramController.this
								.getView()
								.getKeyInput()
								.setBorder(
										BorderFactory
												.createLineBorder(Color.red));
					}
				} else {
					HistogramController.this.getView().getKeyInput()
							.setText("");
					String explanations = HistogramController.this.wrapHtml(
							HistogramController.this.getModel()
									.genRandomBlamings()
									+ " "
									+ HistogramController.i18n
											.tr("The key field was empty!"),
							600);
					HistogramController.this.getView().getExplanations()
							.setText(explanations);
					HistogramController.this
							.getView()
							.getKeyInput()
							.setBorder(
									BorderFactory.createLineBorder(Color.red));
				}
			}
		});
	}

	private void generateKeyInputFocusListener() {
		this.getView().getKeyInput().addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				if (HistogramController.this.getView().getNumpad() == null) {
					HistogramController.this.getView().createNumpad(
							HistogramController.this.getView().getKeyInput());
				}

				if (HistogramController.this.getView().getKeyInput()
						.isEditable()) {
					HistogramController.this
							.getView()
							.getKeyInput()
							.setBorder(
									BorderFactory.createLineBorder(Color.blue,
											5));
				}
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// Nothing to do here.
			}

		});
	}

	private void generateNavigationActionListener() {
		this.getView().getBackButton().addActionListener(new ActionListener() {
			/*
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt .event.ActionEvent)
			 */
			@Override
			public void actionPerformed(ActionEvent event) {
				VisualizationContainerController containerController = (VisualizationContainerController) HistogramController.this
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
				VisualizationContainerController containerController = (VisualizationContainerController) HistogramController.this
						.getParentController();
				containerController.presentNextVisualizationController();
			}
		});
	}

	private void generateDecrementMouseListener() {
		this.getView().getDecrement()
				.addMouseListener(new MouseClickListener() {
					@Override
					public void clicked(MouseEvent e) {
						int bruteForceKey = 0;
						try {
							bruteForceKey = Integer
									.parseInt(HistogramController.this
											.getView().getBruteForceKey()
											.getText()) - 1;
						} catch (NumberFormatException e1) {
							e1.printStackTrace();
						}
						if (HistogramController.this.getModel().isKeyValid(
								bruteForceKey)) {
							HistogramController.this.getView()
									.getBruteForceKey()
									.setText("" + bruteForceKey);
							HistogramController.this
									.getView()
									.getPlainText()
									.setText(
											(HistogramController.this
													.getModel()
													.dec(bruteForceKey,
															HistogramController.this
																	.getView()
																	.getCipherBruteForce()
																	.getText())));

							HistogramController.this.isKeyFound(bruteForceKey);
						} else {
							// Key wrong. Nothing to do.
						}
					}
				});
	}

	private void isKeyFound(int bruteForceKey) {

		// compare the keys.
		if (bruteForceKey == HistogramController.this.getView().getSecretKey()) {
			HistogramController.this.getView().getKeyControl()
					.setBorder(BorderFactory.createLineBorder(Color.green, 5));
			HistogramController.this
					.getView()
					.getAnnouncement()
					.setText(
							HistogramController.this.wrapHtml(
									HistogramController.this.getModel()
											.genRandomGrats()
											+ " "
											+ HistogramController.i18n
													.tr("You found the secret key and are now able to read the secret message. The Key is")
											+ " "
											+ bruteForceKey
											+ ". "
											+ HistogramController.i18n
													.tr("Now only the histogram technique left."),
									500));

			HistogramController.this
					.getView()
					.getExplanations()
					.setText(
							HistogramController.i18n.tr("The Key was")
									+ " "
									+ bruteForceKey
									+ ". "
									+ HistogramController.i18n
											.tr("Congratulations! Yeay! Now lets try to break it with histograms."));

		} else {
			HistogramController.this
					.getView()
					.getAnnouncement()
					.setText(
							HistogramController.this.getModel()
									.genRandomBlamings());
			HistogramController.this.getView().getKeyControl()
					.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
		}

	}

	private void generateIncrementMouseListener() {
		this.getView().getIncrement()
				.addMouseListener(new MouseClickListener() {
					@Override
					public void clicked(MouseEvent e) {
						int bruteForceKey = 0;
						try {
							bruteForceKey = Integer
									.parseInt(HistogramController.this
											.getView().getBruteForceKey()
											.getText()) + 1;
						} catch (NumberFormatException e1) {
							e1.printStackTrace();
						}
						if (HistogramController.this.getModel().isKeyValid(
								bruteForceKey)) {

							HistogramController.this.getView()
									.getBruteForceKey()
									.setText("" + bruteForceKey);
							HistogramController.this
									.getView()
									.getPlainText()
									.setText(
											(HistogramController.this
													.getModel()
													.dec(bruteForceKey,
															HistogramController.this
																	.getView()
																	.getCipherBruteForce()
																	.getText())));

							HistogramController.this.isKeyFound(bruteForceKey);
						} else {
							// Key wrong. Nothing to do.
						}
					}
				});
	}

	private void generateProceedListener() {
		this.getView().getProceed().addMouseListener(new MouseClickListener() {
			@Override
			public void clicked(MouseEvent e) {
				if (HistogramController.this.getStep() == 0) {
					HistogramController.this.setStep(1);
					HistogramController.this.getView()
							.unloadExplanationAndForwardingPanel();
					// Keys smaller than 5 are too simple and bigger then 20 could be annoying.
					int secret = HistogramController.this.getModel()
							.generateRandomInt(5, 20);

					HistogramController.this.getView().setupBruteForceCore(
							secret,
							HistogramController.this.getModel()
									.genRandomCipher(secret));
					HistogramController.this.generateIncrementMouseListener();
					HistogramController.this.generateDecrementMouseListener();
					HistogramController.this
							.getView()
							.getKeyControl()
							.setBorder(
									BorderFactory.createLineBorder(Color.blue,
											2));
					HistogramController.this
							.getView()
							.setupExplanationAndForwarding(
									HistogramController.this.wrapHtml(
											HistogramController.i18n
													.tr("Or: If you want to try a more elaborate way breaking Caesar-cipher "
															+ "you can try histogram technique by pressing the button below."),
											900), GridBagConstraints.PAGE_END);
					HistogramController.this.generateProceedListener();
					HistogramController.this
							.getView()
							.getProceed()
							.setText(
									HistogramController.i18n
											.tr("Try histogram technique"));

				} else if (HistogramController.this.getStep() == 1) {
					// unload the old explanations.
					HistogramController.this.getView()
							.unloadExplanationAndForwardingPanel();
					HistogramController.this.getView().remove(
							HistogramController.this.getView().getKeyControl());
					HistogramController.this.getView().setKeyControl(null);

					String explanation = HistogramController.this.getModel()
							.genRandomText();

					HistogramController.this.getView()
							.setHistogramOriginalText(
									HistogramController.this.wrapHtml(
											explanation, 400));

					HistogramController.this.getView()
							.setupExplanationAndForwarding(
									HistogramController.this.wrapHtml(
											explanation, 700),
									GridBagConstraints.PAGE_START);

					// Build the new experiment.
					HistogramController.this.setStep(2);
					HistogramController.this.generateProceedListener();
					HistogramController.this.getView()
							.setupHistogramContainer();
					HistogramController.this.getView().setupPlainHistogram(
							explanation);

					HistogramController.this.getView().revalidate();
					HistogramController.this.getView().repaint();
				} else if (HistogramController.this.getStep() == 2) {
					HistogramController.this.getView().getProceed()
							.setVisible(false);
					HistogramController.this.setStep(3);
					String textToEncrypt = HistogramController.this.getView()
							.getHistogramOriginalText();
					String htmlCipher = HistogramController.this.getModel()
							.enc(3, textToEncrypt);

					HistogramController.this
							.getView()
							.getExplanations()
							.setText(
									HistogramController.this.wrapHtml(
											HistogramController.i18n
													.tr("When you look at the histogram of the cipher you can see that 'H' is now the most frequent one."
															+ " It is obvious that the letter 'E' was shifted to 'H'. When we substract 'H' - 'E' we get the key."
															+ " Put the key 3 in the box below and let the programm decrypt the cipher with it."),
											700));

					HistogramController.this.getView().setupCipherHistogram(
							htmlCipher);
					HistogramController.this.getView().setHistogramCipher(
							htmlCipher);
					HistogramController.this.getView().setSecretKey(3);
					HistogramController.this.getView().setupKeyInput();
					HistogramController.this.generateKeyInputFocusListener();
					HistogramController.this.generateKeyInputActionListener();
					HistogramController.this.getView().validate();
					HistogramController.this.getView().repaint();

				} else {
					// Build the new experiment.
					HistogramController.this.setStep(HistogramController.this
							.getStep() + 1);
					HistogramController.this.getView().unloadCipherHistogram();
					HistogramController.this.getView().unloadKeyInput();
					HistogramController.this.getView().getPlainText()
							.setText("");
					HistogramController.this.getView().requestFocus();
					HistogramController.this
							.getView()
							.getExplanations()
							.setText(
									HistogramController.i18n
											.tr("Ok lets try another one!"));

					String plainText = HistogramController.this.getModel()
							.genRandomText();
					int key = HistogramController.this.getModel().generateKey();
					String cipher = HistogramController.this.getModel().enc(
							key, plainText);

					HistogramController.this.getView().setupCipherHistogram(
							HistogramController.this.wrapHtml(cipher, 400));
					HistogramController.this.getView().setupKeyInput();
					HistogramController.this.generateKeyInputFocusListener();
					HistogramController.this.generateKeyInputActionListener();
					HistogramController.this.getView().setHistogramCipher(
							cipher);
					HistogramController.this.getView().setSecretKey(key);
					HistogramController.this.getView().getProceed()
							.setVisible(false);
					if (HistogramController.this.getView().getNumpad() != null) {
						HistogramController.this.getView().remove(
								HistogramController.this.getView().getNumpad());
						HistogramController.this.getView().setNumpad(null);
					}
					HistogramController.this.getView().validate();
					HistogramController.this.getView().repaint();
				}

			}
		});
	}

	private String wrapHtml(String text, int width) {
		return "<html><body><div width=" + width + "px>" + text
				+ "</div></body></html>";
	}

	// ---------------------------------------------------------------------------------//
	// -----------------------------------Getter/Setter---------------------------------//
	/**
	 * @return the model
	 */
	public CryptoModel getModel() {
		return this.model;
	}

	/**
	 * @return the step
	 */
	public int getStep() {
		return this.step;
	}

	/**
	 * Gets the view
	 * 
	 * @return The view of this controller
	 */
	@Override
	public HistogramView getView() {
		return (HistogramView) this.view;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(CryptoModel model) {
		this.model = model;
	}

	/**
	 * @param step
	 *            the step to set
	 */
	public void setStep(int step) {
		this.step = step;
	}
}
