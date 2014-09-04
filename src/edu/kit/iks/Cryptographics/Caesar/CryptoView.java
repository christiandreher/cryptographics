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

package edu.kit.iks.Cryptographics.Caesar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.CryptographicsLib.AlphabetStripView;
import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.KeyboardView;
import edu.kit.iks.CryptographicsLib.NumpadView;
import edu.kit.iks.CryptographicsLib.VisualizationView;

/**
 * This view appears when user finished the introdcution. It is used in the last step of the
 * demonstration phase, in the first and second step of the experiment phase. Because of the small
 * differences between the views of those steps the view is packed in only one Class.
 * 
 * The differences are: <br>
 * 1. When in last step of demonstration phase the the JTextField for the input<br>
 * from the user is disabled. UserOutput is disabled first and is visible only later on. <br>
 * And UserInput contains generated String from the program and no real user input.<br>
 * <br>
 * 2. When in first step of the experiment phase, input is enabled for user input.<br>
 * <br>
 * This view provides elements that allow the user to encrypt his input and decrypt the generated
 * cipher.
 * 
 * @author Wasilij Beskorovajnov.
 * 
 */
public class CryptoView extends VisualizationView {

	/**
	 * Localization instance
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(
			CryptoView.class);

	// Constants:
	/**
	 * 
	 */
	public final static int DEMONSTRATION_MODE = 1;

	/**
	 * 
	 */
	public final static int EXPERIMENT_MODE = 2;

	// General parameters:
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// GUI container for other GUI elements:
	/**
	 * Container for the textfields where user can perform IO.
	 */
	protected JPanel userCharacterIOContainer;

	/**
	 * Container for the next/back buttons.
	 */
	protected JPanel navigationPanel;

	// GUI for feedback from the user:
	/**
	 * Buttons that generates on demand a random plain text and integer caesar key.
	 */
	protected JButton generator;

	/**
	 * Textfield for user literal input.
	 */
	protected JTextField literalInput;

	/**
	 * Textfield for user numerical input.
	 */
	protected JTextField keyInput;

	/**
	 * Keyboard with literals for literal input.
	 */
	protected KeyboardView keyboard;

	/**
	 * Keyboard with numeral values for numerical input.
	 */
	protected NumpadView numpad;

	/**
	 * Button for forwarding the steps of this phase.
	 */
	protected JButton proceed;

	/**
	 * Output from user interaction.
	 */
	protected JTextField[] userOutput;

	// GUI for feedback to the user:

	/**
	 * Contains image that support the feedback to users actions when encrypting/decrypting.
	 */
	protected JPanel feedback;

	/**
	 * Explanation textfield.
	 */
	protected JLabel explanations;

	/**
	 * The alphabet.
	 */
	protected AlphabetStripView alphabet;

	/**
	 * Input from the user for encryption.
	 */
	protected JLabel[] userInput;

	/**
	 * Constructor.
	 */
	protected CryptoView() {

		// setup the layout.
		this.setupViewLayout();

		// setups the next and back buttons.
		this.setupNavigation();

		// build elements.
		this.validate();
	}

	// ----------------------------------------------------------------------------//
	// -------------------------public methods------------------------------------//

	/**
	 * Removes the keyboard from the view.
	 */
	public void removeKeyboard() {
		this.remove(this.keyboard);
		this.keyboard = null;
		this.validate();
		this.repaint();
	}

	/**
	 * removes the alphabet.
	 */
	public void removeAlphabet() {
		this.remove(this.alphabet);
		this.alphabet = null;
		this.validate();
		this.repaint();
	}

	/**
	 * Creates the keyboard and shows it in the main container. /**
	 * 
	 * @param input
	 *            the textfield referred to the Keyboard.
	 * @param flag
	 *            the mode the Keyboard should be created in.
	 */
	public void createKeyboard(JTextField input, final int flag) {
		this.keyboard = new KeyboardView(input, flag);
		GridBagConstraints kbConst = new GridBagConstraints();
		kbConst.anchor = GridBagConstraints.PAGE_END;
		kbConst.weightx = 1.0;
		kbConst.weighty = 0.5;
		kbConst.gridx = 0;
		kbConst.gridy = 0;
		kbConst.gridwidth = 11;
		kbConst.gridheight = 3;
		this.add(this.keyboard, kbConst);
		this.validate();
		this.repaint();
	}
	
	public void setupAlphabet() {
		this.alphabet = new AlphabetStripView();
		GridBagConstraints alphConst = new GridBagConstraints();
		alphConst.anchor = GridBagConstraints.CENTER;
		alphConst.gridx = 1;
		alphConst.gridy = 1;
		alphConst.gridwidth = 26;
		alphConst.gridheight = 2;
		alphConst.fill = GridBagConstraints.HORIZONTAL;
		this.add(this.alphabet, alphConst);
	}

	// --------------------------------------------------------------------------------------//
	// --------------------------------protected methods-------------------------------------//

	/**
	 * Creates all needed elements for the encryption/decryption phase.
	 * 
	 * @param inputChars
	 * @param key
	 */
	protected void setupInOutElements(char[] inputChars, int key) {

		this.userInput = new JLabel[inputChars.length];
		this.userOutput = new JTextField[inputChars.length];

		// setup the container for the IO fields.
		this.setupUserCharacterIOContainer(inputChars.length * 4);

		for (int i = 0; i <= inputChars.length; i++) {
			// called only in the last iteration, when all Elements are already created.
			if (i == inputChars.length) {
				// sets the key into a Label with gridX = i(to the right from the IO-textfields.)
				this.setupKeyDisplay(i, key);

				this.feedback = new JPanel();
				this.feedback.setPreferredSize(new Dimension(130, 130));
				GridBagConstraints feedbackConst = new GridBagConstraints();
				feedbackConst.insets = new Insets(25, 25, 50, 25);
				feedbackConst.gridx = i + 1;
				feedbackConst.gridy = 0;
				feedbackConst.gridheight = 2;
				feedbackConst.gridwidth = 2;
				feedbackConst.fill = GridBagConstraints.BOTH;
				this.userCharacterIOContainer.add(this.feedback, feedbackConst);

				break;
			}
			// setup the labels that present the input to decrypt/encrypt.
			this.setupUserInput(i, inputChars[i]);

			// setup the textfields for the IO.
			this.setupUserOutput(i, inputChars[i]);
		}
		this.userCharacterIOContainer.validate();
	}
	protected void removeUserIOContainer() {
		this.remove(this.userCharacterIOContainer);
		this.userCharacterIOContainer = null;
	}

	protected void removeExplanations() {
		this.remove(this.explanations);
		this.explanations = null;
	}

	protected void setupExplanations(String explanations, final int flag,
			int yGrid, int xGrid, int widthGrid) {
		// setup the explanations.
		this.explanations = new JLabel(explanations);
		this.explanations.setPreferredSize(new Dimension(1000, 200));
		GridBagConstraints expConst = new GridBagConstraints();
		expConst.anchor = flag;
		expConst.gridx = xGrid;
		expConst.gridy = yGrid;
		expConst.gridwidth = widthGrid;
		this.add(this.explanations, expConst);

	}

	// -------------------------------------------------------------------------------------//
	// ----------------------private methods------------------------------------------------//

	private void setupViewLayout() {
		// set the layout to GridBagLayout.
		GridBagLayout introLayout = new GridBagLayout();
		this.setLayout(introLayout);
		GridBagConstraints viewConst = new GridBagConstraints();
		viewConst.weightx = 1.0;
		viewConst.weighty = 1.0;
		viewConst.gridwidth = 3;
		viewConst.gridheight = 4;
		introLayout.setConstraints(this, viewConst);
	}

	private void setupUserCharacterIOContainer(int widthGrid) {
		this.userCharacterIOContainer = new JPanel(new GridBagLayout());
//		this.userCharacterIOContainer.setPreferredSize(new Dimension(600, 200));
		GridBagConstraints panelConst = new GridBagConstraints();
		panelConst.anchor = GridBagConstraints.PAGE_START;
		panelConst.weightx = 0.5;
		panelConst.weighty = 0.5;
		panelConst.gridx = 0;
		panelConst.gridy = 1;
		panelConst.gridwidth = widthGrid;
		panelConst.gridheight = 2;
		panelConst.insets = new Insets(0, 0, 200, 0);
		this.add(this.userCharacterIOContainer, panelConst);
	}

	private void setupKeyDisplay(int xGrid, int key) {
		JLabel keyLabel = new JLabel(CryptoView.i18n.tr("Key"));
		GridBagConstraints labelConst = new GridBagConstraints();
		keyLabel.setPreferredSize(new Dimension(100, 25));
		labelConst.insets = new Insets(5, 25, 25, 25);
		labelConst.gridx = xGrid;
		labelConst.gridy = 0;
		labelConst.ipadx = 20;
		labelConst.ipady = 20;
		this.userCharacterIOContainer.add(keyLabel, labelConst);

		this.keyInput = new JTextField();
		GridBagConstraints keyConst = new GridBagConstraints();
		this.keyInput.setText("" + key);
		this.keyInput.setPreferredSize(new Dimension(25, 25));
		this.keyInput.setEditable(false);
		keyConst.insets = new Insets(5, 25, 25, 25);
		keyConst.gridx = xGrid;
		keyConst.gridy = 1;
		keyConst.ipadx = 20;
		keyConst.ipady = 20;
		this.userCharacterIOContainer.add(this.keyInput, keyConst);
	}

	private void setupUserInput(int i, char content) {
		// fields where the input will be encrypted
		this.userInput[i] = new JLabel();
		this.userInput[i].setText("" + content);
		this.userInput[i].setBorder(null);
		this.userInput[i].setPreferredSize(new Dimension(25, 25));
		GridBagConstraints inputConst = new GridBagConstraints();
		inputConst.insets = new Insets(5, 25, 25, 25);
		inputConst.gridx = i;
		inputConst.gridy = 0;
		inputConst.ipadx = 20;
		inputConst.ipady = 20;
		this.userCharacterIOContainer.add(this.userInput[i], inputConst);

	}

	private void setupUserOutput(int i, char correspondingInputContent) {
		// fields where the encrypted input is put in.
		this.userOutput[i] = new JTextField();
		this.userOutput[i].setName("" + correspondingInputContent);
		this.userOutput[i].setPreferredSize(new Dimension(25, 25));
		this.userOutput[i].setOpaque(true);
		this.userOutput[i].setBorder(BorderFactory
				.createLineBorder(Color.darkGray));

		GridBagConstraints outConst = new GridBagConstraints();
		outConst.gridx = i;
		outConst.gridy = 1;
		outConst.ipadx = 20;
		outConst.ipady = 20;
		outConst.insets = new Insets(5, 25, 25, 25);
		this.userCharacterIOContainer.add(this.userOutput[i], outConst);

	}

	private void setupNavigation() {

		// set up a container for the navigation Buttons (Next and Back).
		this.setupNavigationContainer();

		// TODO: dont instantiate the buttons in the upper class.
		// First need to remove the buttons because they are added in the upper
		// class.
		this.remove(this.getBackButton());
		this.remove(this.getNextButton());

		this.setBackButton(new JButton());
		this.getBackButton().setPreferredSize(new Dimension(350, 50));
		this.navigationPanel.add(this.getBackButton(), BorderLayout.WEST);

		// set up the aligment of the button Next;
		this.setNextButton(new JButton());
		this.getNextButton().setPreferredSize(new Dimension(300, 50));
		this.navigationPanel.add(this.getNextButton(), BorderLayout.EAST);

	}

	private void setupNavigationContainer() {
		this.navigationPanel = new JPanel(new BorderLayout());
		GridBagConstraints navConst = new GridBagConstraints();
		navConst.anchor = GridBagConstraints.NORTH;
		navConst.weightx = 1.0;
		navConst.weighty = 0.1;
		navConst.gridx = 0;
		navConst.gridy = 0;
		navConst.gridwidth = 13;
		navConst.gridheight = 1;
		navConst.fill = GridBagConstraints.HORIZONTAL;
		this.add(this.navigationPanel, navConst);
	}

	// ----------------------------------------------------------Getter/Setter--------------------------------------------------//
	/**
	 * @param userInput
	 *            the userInput to set
	 */
	public void setUserInput(JLabel[] userInput) {
		this.userInput = userInput;
	}

	/**
	 * @param userOutput
	 *            the userOutput to set
	 */
	public void setUserOutput(JTextField[] userOutput) {
		this.userOutput = userOutput;
	}

	/**
	 * @return the alphabet
	 */
	public AlphabetStripView getAlphabet() {
		return this.alphabet;
	}

	/**
	 * @return the explanations
	 */
	public JLabel getExplanations() {
		return this.explanations;
	}

	/**
	 * @return the generator
	 */
	public JButton getGenerator() {
		return this.generator;
	}

	/**
	 * @return the inOutPanel
	 */
	public JPanel userCharacterIOContainer() {
		return this.userCharacterIOContainer;
	}

	/**
	 * @return input
	 */
	public JTextField getLiteralInput() {
		return this.literalInput;
	}

	/**
	 * @return the key
	 */
	public JTextField getKeyInput() {
		return this.keyInput;
	}

	/**
	 * @return the keyboard
	 */
	public KeyboardView getKeyboard() {
		return this.keyboard;
	}

	/**
	 * @return the navigationPanel
	 */
	public JPanel getNavigationPanel() {
		return this.navigationPanel;
	}

	/**
	 * @return the numpad
	 */
	public NumpadView getNumpad() {
		return this.numpad;
	}

	/**
	 * @return the proceed
	 */
	public JButton getProceed() {
		return this.proceed;
	}

	/**
	 * @return the userInput
	 */
	public JLabel[] getUserInput() {
		return this.userInput;
	}

	/**
	 * @return the userOutput
	 */
	public JTextField[] getUserOutput() {
		return this.userOutput;
	}

	/**
	 * @param alphabet
	 *            the alphabet to set
	 */
	public void setAlphabet(AlphabetStripView alphabet) {
		this.alphabet = alphabet;
	}

	/**
	 * @param explanations
	 *            the explanations to set
	 */
	public void setExplanations(JLabel explanations) {
		this.explanations = explanations;
	}

	/**
	 * @param generator
	 *            the generator to set
	 */
	public void setGenerator(JButton generator) {
		this.generator = generator;
	}

	/**
	 * @param inOutPanel
	 *            the inOutPanel to set
	 */
	public void setInOutPanel(JPanel inOutPanel) {
		this.userCharacterIOContainer = inOutPanel;
	}

	/**
	 * @param input
	 *            the input to set
	 */
	public void setInput(JTextField input) {
		this.literalInput = input;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(JTextField key) {
		this.keyInput = key;
	}

	/**
	 * @param keyboard
	 *            the keyboard to set
	 */
	public void setKeyboard(KeyboardView keyboard) {
		this.keyboard = keyboard;
	}

	/**
	 * @param navigationPanel
	 *            the navigationPanel to set
	 */
	public void setNavigationPanel(JPanel navigationPanel) {
		this.navigationPanel = navigationPanel;
	}

	/**
	 * @param numpad
	 *            the numpad to set
	 */
	public void setNumpad(NumpadView numpad) {
		this.numpad = numpad;
	}

	/**
	 * @param proceed
	 *            the proceed to set
	 */
	public void setProceed(JButton proceed) {
		this.proceed = proceed;
	}

	/**
	 * @return the userCharacterIOContainer
	 */
	public JPanel getUserCharacterIOContainer() {
		return this.userCharacterIOContainer;
	}

	/**
	 * @param userCharacterIOContainer
	 *            the userCharacterIOContainer to set
	 */
	public void setUserCharacterIOContainer(JPanel userCharacterIOContainer) {
		this.userCharacterIOContainer = userCharacterIOContainer;
	}

	/**
	 * @return the feedback
	 */
	public JPanel getFeedback() {
		return feedback;
	}

	/**
	 * @param feedback
	 *            the feedback to set
	 */
	public void setFeedback(JPanel feedback) {
		this.feedback = feedback;
	}

}
