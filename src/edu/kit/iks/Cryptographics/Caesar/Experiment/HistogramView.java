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

import edu.kit.iks.CryptographicsLib.CharacterFrequencyDiagramView;
import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.KeyboardView;
import edu.kit.iks.CryptographicsLib.NumpadView;
import edu.kit.iks.CryptographicsLib.VisualizationView;

/**
 * This view represents the last view of the experiment phase. The elements contained here allow the
 * user to break a given caesar cipher and have the purpose to show him the disadvantages of the
 * caesar cipher, in particular how easy it is to break it. In addition user gets explanations
 * presented about what histograms are and how they could help him solving his task to break the
 * cipher.
 * 
 * @author Wasilij Beskorovajnov.
 * 
 */
public class HistogramView extends VisualizationView {
	// General parameters:
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * localization.
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(
			HistogramView.class);

	// GUI container for other GUI elements:
	/**
	 * JPanel that contain JLabel explanations and the Button proceed.
	 */
	private JPanel explanationAndForwardingPanel;

	/**
	 * Panel that contains the core elements for the histogram experiment.<br>
	 * 1. The both histograms.<br>
	 * 2. inputfield for the key.<br>
	 * 3. cipher and the decrypted cipher according to given histograms.<br>
	 */
	private JPanel histogramContainer;

	/**
	 * Container for the increment/decrement Buttons.<br>
	 * is used in the brute force step of the experiment.<br>
	 */
	private JPanel keyControl;

	/**
	 * Container for the next/back buttons.
	 */
	private JPanel navigationPanel;

	// GUI for feedback from the user.
	/**
	 * Button for stepping further in the experiment. Not part of the navigation. Unidirectional.
	 */
	private JButton proceed;

	/**
	 * GUI-element for literal feedback from the user.
	 */
	private KeyboardView keyboard;

	/**
	 * GUI-element for numerical Feedback from the user.
	 */
	private NumpadView numpad;

	// GUI for feedback to the user:
	/**
	 * JLabel that contains the feedback on the input from the user. E.g. User typed a key in the
	 * inputfield/clicked a button.
	 */
	private JLabel announcement;

	/**
	 * The name is selfexplanatory.
	 */
	private JLabel explanations;

	// Core elements of the experiment in the histogramphase.:
	/**
	 * This gui-element is a normal diagram, that displays the distribution of letters in a given
	 * plaintext/cipher.
	 */
	private CharacterFrequencyDiagramView plainTextHistogram;
	private CharacterFrequencyDiagramView cipherHistogram;
	private String histogramOriginalText;
	private String histogramCipher;

	/**
	 * JLabel that contains the decrypted cipher.
	 */
	private JLabel plainText;

	/**
	 * JLabel that contains the cipher which the histogram above is presenting.
	 */
	private JLabel cipher;

	/**
	 * Inputfield that allows the user to type a numerical value.
	 */
	private JTextField keyInput;

	/**
	 * The secret key, the user has to find out.
	 */
	private int secretKey;

	// Core elements of the experiment in the bruteforce phase:
	/**
	 * Contains the cipher in the brute force stage.
	 */
	private JLabel bruteForceCipherLabel;

	/**
	 * Buttons for iterating the key.
	 */
	private JButton increment;
	private JButton decrement;

	/**
	 * Key that is being incremented and decremented.
	 */
	private JLabel bruteFoceKey;

	/**
	 * Constructor.
	 */
	public HistogramView() {

		// setup the layout.
		this.setupViewLayout();

		// setup the navigation.
		this.setupNavigation();

		String explanation = "<html><body>"
				+ HistogramView.i18n
						.tr("Cryptologists call it 'breaking a cipher' when someone tries to decrypt text without knowing the key.")
				+ "<br>"
				+ HistogramView.i18n
						.tr("There are always 2 options when it comes to this.")
				+ "<br>"
				+ HistogramView.i18n
						.tr("1. You use literally brute force, meaning you simply try each possible key.")
				+ "<br>"
				+ "<br>"
				+ HistogramView.i18n
						.tr("2. You use elaborate cryptology techniques.");

		this.setupExplanationAndForwarding(explanation, GridBagConstraints.PAGE_START);
		this.proceed.setText(HistogramView.i18n.tr("Lets try brute force"));
	}

	// -------------------------------------------------------------------public
	// methods-------------------------------------------------//

	public void setupExplanationAndForwarding(String explanations, int flag) {
		// setup the container.
		this.setupExplanationAndForwardingContainer(flag);

		// setup the explanations.
		this.setExplanations(new JLabel(explanations));
		GridBagConstraints explanationConstraint = new GridBagConstraints();
		explanationConstraint.gridx = 1;
		explanationConstraint.gridy = 0;
		explanationConstraint.gridwidth = 5;
		explanationConstraint.gridheight = 5;
		explanationConstraint.insets = new Insets(10, 0, 10, 0);
		explanationConstraint.fill = GridBagConstraints.HORIZONTAL;
		this.explanationAndForwardingPanel.add(this.explanations,
				explanationConstraint);

		// setup the forwarding.
		this.setupProceed();
		this.explanationAndForwardingPanel.validate();
		this.validate();
	}

	/**
	 * Creates the keyboard with literals and shows it in the main container.
	 * 
	 * @param input
	 */
	public void createKeyboard(JTextField input) {
		this.keyboard = new KeyboardView(input, KeyboardView.CHAR_MODE);
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
	}

	/**
	 * Creates the keyboard with numerical values and presents it.
	 * 
	 * @param input
	 */
	public void createNumpad(JTextField input) {
		this.numpad = new NumpadView(input, NumpadView.NUMBER_MODE);
		GridBagConstraints numpadConst = new GridBagConstraints();
		numpadConst.anchor = GridBagConstraints.FIRST_LINE_START;
		numpadConst.weightx = 1.0;
		numpadConst.weighty = 0.5;
		numpadConst.gridx = 0;
		numpadConst.gridy = 0;
		numpadConst.gridwidth = 11;
		numpadConst.gridheight = 3;
		numpadConst.insets = new Insets(50, 0, 0, 0);
		this.add(this.numpad, numpadConst);
		this.validate();
	}

	/**
	 * Setup method for the first step of the histogram experiment.
	 * 
	 * @param secretKey
	 * @param cipher
	 */
	public void setupBruteForceCore(int secretKey, String cipher) {
		// set the key user has to find out.
		this.secretKey = secretKey;

		// setup the container for the core gui of the experiment.
		this.setupKeyControlContainer();

		// setup the core iteration elements.
		this.setupKeyIterationButtons();

		// setup the labels that show the results of each iteration.
		this.setupCipherPlainPresentationLabels(cipher);

		this.announcement = new JLabel(this.wrapHtml(HistogramView.i18n
				.tr("Brute force means trying to decrypt with each possible key, till you find the right one." +
						"The Key is in the middle. And pressing buttons increments or decrements it. Let's search.")));
		this.announcement.setPreferredSize(new Dimension(600, 100));
		GridBagConstraints annConst = new GridBagConstraints();
		annConst.gridx = 0;
		annConst.gridy = 0;
		annConst.gridwidth = 4;
		this.keyControl.add(this.announcement, annConst);

		// layout the buttons.
		this.repaint();
		this.validate();
	}

	/**
	 * Sets the container for the following histogram elements.
	 */
	public void setupHistogramContainer() {
		this.histogramContainer = new JPanel(new GridBagLayout());
		GridBagConstraints containerConst = new GridBagConstraints();
		containerConst.anchor = GridBagConstraints.PAGE_END;
		containerConst.weightx = 1.0;
		containerConst.weighty = 1.0;
		containerConst.gridx = 0;
		containerConst.gridy = 1;
		containerConst.gridheight = 6;
		containerConst.gridwidth = 3;
//		containerConst.fill = GridBagConstraints.BOTH;
		this.add(this.histogramContainer, containerConst);
	}

	/**
	 * Creates a histogram of the cipher argument.
	 * 
	 * @param cipher
	 */
	public void setupCipherHistogram(String cipher) {
		JLabel cipherHistogramHint = new JLabel(
				this.wrapHtml(HistogramView.i18n
						.tr("&darr;Histogram of the cipher.&darr;")));
		GridBagConstraints hintConst = new GridBagConstraints();
		hintConst.gridx = 2;
		hintConst.gridy = 2;
		hintConst.insets = new Insets(0, 50, 5, 50);
		hintConst.fill = GridBagConstraints.HORIZONTAL;
		this.histogramContainer.add(cipherHistogramHint, hintConst);

		this.cipherHistogram = new CharacterFrequencyDiagramView(cipher, 600,
				100);
		GridBagConstraints cipherHistConst = new GridBagConstraints();
		cipherHistConst.gridx = 2;
		cipherHistConst.gridy = 3;
		cipherHistConst.insets = new Insets(5, 50, 5, 50);
		cipherHistConst.fill = GridBagConstraints.BOTH;
		this.histogramContainer.add(this.cipherHistogram, cipherHistConst);

		this.cipher = new JLabel(cipher);
		GridBagConstraints cipherConst = new GridBagConstraints();
		cipherConst.gridx = 2;
		cipherConst.gridy = 4;
		cipherConst.insets = new Insets(5, 50, 5, 50);
		cipherConst.fill = GridBagConstraints.BOTH;
		this.histogramContainer.add(this.cipher, cipherConst);

		this.validate();
		this.repaint();
	}

	/**
	 * Creates a histogram of the plain text argument.
	 * 
	 * @param text
	 */
	public void setupPlainHistogram(String text) {

		JLabel plainHistogramHint = new JLabel(this.wrapHtml(HistogramView.i18n
				.tr("&darr;Histogram of the original text.&darr;")));
		GridBagConstraints hintConst = new GridBagConstraints();
		hintConst.gridx = 0;
		hintConst.gridy = 2;
		hintConst.insets = new Insets(0, 50, 5, 50);
		hintConst.fill = GridBagConstraints.HORIZONTAL;
		this.histogramContainer.add(plainHistogramHint, hintConst);

		this.plainTextHistogram = new CharacterFrequencyDiagramView(text, 600,
				100);
		GridBagConstraints plainTextHistConst = new GridBagConstraints();
		plainTextHistConst.gridx = 0;
		plainTextHistConst.gridy = 3;
		plainTextHistConst.insets = new Insets(5, 50, 5, 50);
		plainTextHistConst.fill = GridBagConstraints.BOTH;
		this.histogramContainer
				.add(this.plainTextHistogram, plainTextHistConst);

		this.plainText = new JLabel();
		GridBagConstraints plainConst = new GridBagConstraints();
		plainConst.gridx = 0;
		plainConst.gridy = 4;
		plainConst.insets = new Insets(5, 50, 5, 50);
		plainConst.fill = GridBagConstraints.BOTH;
		this.histogramContainer.add(this.plainText, plainConst);

		this.validate();
		this.repaint();
	}

	/**
	 * Sets the inputfield where the user can type his key.
	 */
	public void setupKeyInput() {
		JLabel keyCaption = new JLabel(this.wrapHtml(HistogramView.i18n
				.tr("Type your key in this&darr; box and click 'Enter'.")));
		GridBagConstraints capConst = new GridBagConstraints();
		capConst.gridx = 0;
		capConst.gridy = 0;
		capConst.gridwidth = 3;
		capConst.insets = new Insets(0, 475, 0, 100);
		capConst.fill = GridBagConstraints.HORIZONTAL;
		this.histogramContainer.add(keyCaption, capConst);

		this.keyInput = new JTextField();
		this.keyInput.setMinimumSize(new Dimension(50, 50));
		this.keyInput.setBorder(BorderFactory.createLineBorder(Color.black));
		GridBagConstraints keyConst = new GridBagConstraints();
		keyConst.gridx = 0;
		keyConst.gridy = 1;
		keyConst.gridwidth = 3;
		keyConst.gridheight = 2;
		keyConst.insets = new Insets(5, 50, 5, 50);
		this.histogramContainer.add(this.keyInput, keyConst);
	}

	/**
	 * Function that destroys the histogram of a cipher.
	 */
	public void unloadCipherHistogram() {
		this.histogramContainer.remove(this.cipherHistogram);
		this.cipherHistogram = null;
		this.histogramContainer.remove(this.cipher);
		this.cipher = null;
		this.revalidate();
	}

	/**
	 * Function that destroys the ExplanationAndForwardingPanel container.
	 */
	public void unloadExplanationAndForwardingPanel() {
		this.explanationAndForwardingPanel.remove(this.proceed);
		this.explanationAndForwardingPanel.remove(this.explanations);
		this.proceed = null;
		this.explanations = null;
		this.remove(this.explanationAndForwardingPanel);
		this.explanationAndForwardingPanel = null;
		this.validate();
		this.repaint();
	}

	/**
	 * Destroys the KeyInput.
	 */
	public void unloadKeyInput() {
		this.histogramContainer.remove(this.keyInput);
		this.keyInput = null;
		this.validate();
	}

	// --------------------------------------------------------------private
	// methods--------------------------------------------------//

	private void setupViewLayout() {
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		GridBagConstraints viewConst = new GridBagConstraints();
		viewConst.weightx = 1.0;
		viewConst.weighty = 1.0;
		viewConst.fill = GridBagConstraints.BOTH;
		layout.setConstraints(this, viewConst);
	}

	private void setupNavigationPanel() {
		// set up a container for the navigation Buttons (Next and Back).
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

	private void setupNavigation() {
		this.setupNavigationPanel();

		// TODO: dont instantiate the buttons in the upper class.
		// First need to remove the buttons because they are added in the upper
		// class.
		this.remove(this.getBackButton());
		this.remove(this.getNextButton());

		// set up the alignment of the button back;
		this.setBackButton(new JButton(HistogramView.i18n
				.tr("Back to Experiment")));
		this.getBackButton().setPreferredSize(new Dimension(300, 50));
		this.getBackButton().setMaximumSize(new Dimension(300, 50));
		this.navigationPanel.add(this.getBackButton(), BorderLayout.WEST);

		// set up the aligment of the button Next;
		this.setNextButton(new JButton(HistogramView.i18n
				.tr("Skip the Histograms")));
		this.getNextButton().setPreferredSize(new Dimension(300, 50));
		this.getNextButton().setMinimumSize(new Dimension(300, 50));
		this.navigationPanel.add(this.getNextButton(), BorderLayout.EAST);

	}

	private void setupExplanationAndForwardingContainer(int flag) {
		this.explanationAndForwardingPanel = new JPanel(new GridBagLayout());
		// set the alignment of the Container for the explanations and the button Proceed.
		GridBagConstraints explanationPanelConstraint = new GridBagConstraints();
		explanationPanelConstraint.anchor = flag;
		explanationPanelConstraint.weightx = 1.0;
		explanationPanelConstraint.weighty = 0.1;
		explanationPanelConstraint.gridx = 0;
		explanationPanelConstraint.gridy = 0;
		explanationPanelConstraint.gridwidth = 6;
		explanationPanelConstraint.gridheight = 6;
		explanationPanelConstraint.insets = new Insets(50, 0, 50, 0);
		explanationPanelConstraint.fill = GridBagConstraints.HORIZONTAL;
		this.add(this.explanationAndForwardingPanel, explanationPanelConstraint);
	}

	private void setupProceed() {
		// setup the proceed button.
		this.proceed = new JButton(HistogramView.i18n.tr("Proceed"));
		this.proceed.setVisible(true);
		GridBagConstraints proceedConst = new GridBagConstraints();
		this.proceed.setPreferredSize(new Dimension(250, 50));
		this.proceed.setMinimumSize(new Dimension(100, 50));
		proceedConst.gridx = 3;
		proceedConst.gridy = 6;
		proceedConst.gridwidth = 6;
		proceedConst.insets = new Insets(0, 0, 10, 0);
		this.explanationAndForwardingPanel.add(this.proceed, proceedConst);

	}

	private void setupCipherPlainPresentationLabels(String cipher) {

		this.bruteForceCipherLabel = new JLabel(cipher);
		this.bruteForceCipherLabel.setPreferredSize(new Dimension(150, 50));
		GridBagConstraints cipherConst = new GridBagConstraints();
		cipherConst.gridx = 0;
		cipherConst.gridy = 1;
		cipherConst.insets = new Insets(5, 5, 5, 5);
		this.keyControl.add(this.bruteForceCipherLabel, cipherConst);

		this.plainText = new JLabel("");
		this.plainText.setPreferredSize(new Dimension(150, 50));
		GridBagConstraints plainConst = new GridBagConstraints();
		plainConst.gridx = 0;
		plainConst.gridy = 2;
		plainConst.insets = new Insets(5, 5, 5, 5);
		this.keyControl.add(this.plainText, plainConst);
	}

	private void setupKeyIterationButtons() {
		// key
		this.bruteFoceKey = new JLabel("" + 1);
		GridBagConstraints keyLabelConst = new GridBagConstraints();
		keyLabelConst.gridx = 3;
		keyLabelConst.gridy = 1;
		keyLabelConst.insets = new Insets(5, 5, 50, 5);
		this.keyControl.add(this.bruteFoceKey, keyLabelConst);

		// increment.
		this.increment = new JButton("+1");
		this.increment.setPreferredSize(new Dimension(100, 50));
		GridBagConstraints incConst = new GridBagConstraints();
		incConst.gridx = 4;
		incConst.gridy = 1;
		incConst.insets = new Insets(5, 5, 50, 5);
		this.keyControl.add(this.increment, incConst);

		// decrement.
		this.decrement = new JButton("-1");
		this.decrement.setPreferredSize(new Dimension(100, 50));
		GridBagConstraints decConst = new GridBagConstraints();
		decConst.gridx = 4;
		decConst.gridy = 2;
		decConst.insets = new Insets(5, 5, 50, 5);
		this.keyControl.add(this.decrement, decConst);
	}

	private void setupKeyControlContainer() {
		// setup the Panel for buttons for incrementing/decrementing the key.
		this.keyControl = new JPanel(new GridBagLayout());
		// this.keyControl.setBorder(BorderFactory.createLineBorder(Color.green));
		GridBagConstraints keyConst = new GridBagConstraints();
		keyConst.anchor = GridBagConstraints.PAGE_START;
		keyConst.weightx = 0.5;
		keyConst.weighty = 0.5;
		keyConst.gridx = 1;
		keyConst.gridy = 1;
		keyConst.gridwidth = 3;
		keyConst.gridheight = 4;
		keyConst.insets = new Insets(50, 0, 0, 0);
		// keyConst.fill = GridBagConstraints.HORIZONTAL;
		this.add(this.keyControl, keyConst);
	}

	// --------------------------------------------------------------Getter/Setter----------------------------------------------------//

	/**
	 * @return the numpad
	 */
	public NumpadView getNumpad() {
		return this.numpad;
	}

	/**
	 * @param numpad
	 *            the numpad to set
	 */
	public void setNumpad(NumpadView numpad) {
		this.numpad = numpad;
	}

	/**
	 * @return the announcement
	 */
	public JLabel getAnnouncement() {
		return this.announcement;
	}

	/**
	 * @return the cipher
	 */
	public JLabel getCipherBruteForce() {
		return this.bruteForceCipherLabel;
	}

	/**
	 * @return the cipherHistogram
	 */
	public CharacterFrequencyDiagramView getCipherHistogram() {
		return this.cipherHistogram;
	}

	/**
	 * @return the cipherText
	 */
	public JLabel getCipherText() {
		return this.cipher;
	}

	/**
	 * @return the decrement
	 */
	public JButton getDecrement() {
		return this.decrement;
	}

	/**
	 * @return the explanationPanel
	 */
	public JPanel getExplanationPanel() {
		return this.explanationAndForwardingPanel;
	}

	/**
	 * @return the explanations
	 */
	public JLabel getExplanations() {
		return this.explanations;
	}

	/**
	 * @return the histogramCipher
	 */
	public String getHistogramCipher() {
		return this.histogramCipher;
	}

	/**
	 * @return the histogramContainer
	 */
	public JPanel getHistogramContainer() {
		return this.histogramContainer;
	}

	/**
	 * @return the increment
	 */
	public JButton getIncrement() {
		return this.increment;
	}

	/**
	 * @return the key
	 */
	public JLabel getBruteForceKey() {
		return this.bruteFoceKey;
	}

	/**
	 * @return the keyboard
	 */
	public KeyboardView getKeyboard() {
		return this.keyboard;
	}

	/**
	 * @return the keyControl
	 */
	public JPanel getKeyControl() {
		return this.keyControl;
	}

	/**
	 * @return the keyInput
	 */
	public JTextField getKeyInput() {
		return this.keyInput;
	}

	/**
	 * @return the navigationPanel
	 */
	public JPanel getNavigationPanel() {
		return this.navigationPanel;
	}

	/**
	 * @return the plain
	 */
	public JLabel getPlainText() {
		return this.plainText;
	}

	/**
	 * @return the plainTextHistogram
	 */
	public CharacterFrequencyDiagramView getPlainTextHistogram() {
		return this.plainTextHistogram;
	}

	/**
	 * @return the proceed
	 */
	public JButton getProceed() {
		return this.proceed;
	}

	/**
	 * @return the secretKey
	 */
	public int getSecretKey() {
		return this.secretKey;
	}

	/**
	 * @param announcement
	 *            the announcement to set
	 */
	public void setAnnouncement(JLabel announcement) {
		this.announcement = announcement;
	}

	/**
	 * @param cipher
	 *            the cipher to set
	 */
	public void setCipher(JLabel cipher) {
		this.bruteForceCipherLabel = cipher;
	}

	/**
	 * @param cipherHistogram
	 *            the cipherHistogram to set
	 */
	public void setCipherHistogram(CharacterFrequencyDiagramView cipherHistogram) {
		this.cipherHistogram = cipherHistogram;
	}

	/**
	 * @param cipherText
	 *            the cipherText to set
	 */
	public void setCipherText(JLabel cipherText) {
		this.cipher = cipherText;
	}

	/**
	 * @param label
	 * @param c
	 */
	public void setContent(JLabel label, char c) {

	}

	/**
	 * @param decrement
	 *            the decrement to set
	 */
	public void setDecrement(JButton decrement) {
		this.decrement = decrement;
	}

	/**
	 * @param explanationPanel
	 *            the explanationPanel to set
	 */
	public void setExplanationPanel(JPanel explanationPanel) {
		this.explanationAndForwardingPanel = explanationPanel;
	}

	/**
	 * @param explanations
	 *            the explanations to set
	 */
	public void setExplanations(JLabel explanations) {
		this.explanations = explanations;
	}

	/**
	 * @param histogramCipher
	 *            the histogramCipher to set
	 */
	public void setHistogramCipher(String histogramCipher) {
		this.histogramCipher = histogramCipher;
	}

	/**
	 * @param histogramContainer
	 *            the histogramContainer to set
	 */
	public void setHistogramContainer(JPanel histogramContainer) {
		this.histogramContainer = histogramContainer;
	}

	/**
	 * @param increment
	 *            the increment to set
	 */
	public void setIncrement(JButton increment) {
		this.increment = increment;
	}

	/**
	 * @param keyboard
	 *            the keyboard to set
	 */
	public void setKeyboard(KeyboardView keyboard) {
		this.keyboard = keyboard;
	}

	/**
	 * @param keyControl
	 *            the keyControl to set
	 */
	public void setKeyControl(JPanel keyControl) {
		this.keyControl = keyControl;
	}

	/**
	 * @param keyInput
	 *            the keyInput to set
	 */
	public void setKeyInput(JTextField keyInput) {
		this.keyInput = keyInput;
	}

	/**
	 * @param navigationPanel
	 *            the navigationPanel to set
	 */
	public void setNavigationPanel(JPanel navigationPanel) {
		this.navigationPanel = navigationPanel;
	}

	/**
	 * @param plain
	 *            the plain to set
	 */
	public void setPlainText(JLabel plaintext) {
		this.plainText = plaintext;
	}

	/**
	 * @param plainTextHistogram
	 *            the plainTextHistogram to set
	 */
	public void setPlainTextHistogram(
			CharacterFrequencyDiagramView plainTextHistogram) {
		this.plainTextHistogram = plainTextHistogram;
	}

	/**
	 * @param proceed
	 *            the proceed to set
	 */
	public void setProceed(JButton proceed) {
		this.proceed = proceed;
	}

	/**
	 * @param secretKey
	 *            the secretKey to set
	 */
	public void setSecretKey(int secretKey) {
		this.secretKey = secretKey;
	}

	private String wrapHtml(String text) {
		return "<html><body>" + text + "</body></html>";
	}

	/**
	 * @return the histogramOriginalText
	 */
	public String getHistogramOriginalText() {
		return histogramOriginalText;
	}

	/**
	 * @param histogramOriginalText
	 *            the histogramOriginalText to set
	 */
	public void setHistogramOriginalText(String histogramOriginalText) {
		this.histogramOriginalText = histogramOriginalText;
	}
}
