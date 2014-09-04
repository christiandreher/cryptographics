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

import edu.kit.iks.Cryptographics.Caesar.CryptoView;
import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.KeyboardView;
import edu.kit.iks.CryptographicsLib.NumpadView;

/**
 * @author wasilij
 * 
 */
public class CryptoExperimentView extends CryptoView {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -569214466948994301L;

	/**
	 * localization.
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(
			CryptoExperimentView.class);
	
	/**
	 * 
	 */
	public CryptoExperimentView() {
		super();

		// setup the caption of the next/back Buttons.
		this.getBackButton().setText(
				CryptoExperimentView.i18n.tr("Back to Demonstration"));
		this.getNextButton().setText(
				CryptoExperimentView.i18n.tr("Go to Decryption"));

		// setup the input/output elements for further steps.
		this.setupUserIO();

		// build the user interface.
		this.userCharacterIOContainer.validate();

		// setup the explanations.
		String explanation = this
				.wrapHtml(CryptoExperimentView.i18n
						.tr("In the demonstration you encrypted with the key 3. On further thought it seems logical that this key can vary"
								+ " from 1 to 26.")
						+ "<br>"
						+ CryptoExperimentView.i18n
								.tr("Now you have 2 options:")
						+ "<br>"
						+ "<br>"
						+ CryptoExperimentView.i18n
								.tr("1. You can either put your own key and plaintext into the textfield,")
						+ "<br>"
						+ "<br>"
						+ CryptoExperimentView.i18n
								.tr("2. or you just click \"Generate\" and this awesome program will generate a key and plaintext for you."));
		this.setupExplanations(explanation, GridBagConstraints.PAGE_START, 1,
				0, 6);

		this.validate();
	}

	/**
	 * Called when the user done the needed input in a valid way.
	 * 
	 * @param inputChars
	 * @param key
	 */
	public void setupExperimentCore(char[] inputChars, int key) {
		// User input will be now filled into the boxes. This field is not
		// needed anymore.
		this.removeUserIOContainer();
		this.removeExplanations();

		// setup IO.
		this.setupInOutElements(inputChars, key);

		// setup the alphabet.
		this.setupAlphabet();

		// setup the explanations.
		String explanations = this
				.wrapHtml(CryptoExperimentView.i18n
						.tr("Let's test what you have learned. If help needed touch the help button."));
		this.setupExplanations(explanations,
				GridBagConstraints.LAST_LINE_START, 0, 0, 4);

		// build the new view.
		this.validate();
		this.repaint();
	}

	/**
	 * Creates the keyboard with numerical values and presents it.
	 * 
	 * @param input
	 *            the textfield referred to the numpad.
	 */
	public void createNumpad(JTextField input) {
		this.numpad = new NumpadView(input, NumpadView.NUMBER_MODE);
		GridBagConstraints numpadConst = new GridBagConstraints();
		numpadConst.anchor = GridBagConstraints.LINE_END;
		numpadConst.weightx = 1.0;
		numpadConst.weighty = 0.5;
		numpadConst.gridx = 0;
		numpadConst.gridy = 3;
		numpadConst.gridwidth = 4;
		numpadConst.gridheight = 5;
		numpadConst.insets = new Insets(0, 10, 100, 200);
		this.add(this.numpad, numpadConst);
		this.validate();
	}
	
	/**
	 * Creates the keyboard and shows it in the main container. /**
	 * 
	 * @param input
	 *            the textfield referred to the Keyboard.
	 * @param flag
	 *            the mode the Keyboard should be created in.
	 */
	public void createKeyboard(JTextField input) {
		this.keyboard = new KeyboardView(input, KeyboardView.STRING_MODE);
		GridBagConstraints kbConst = new GridBagConstraints();
		kbConst.anchor = GridBagConstraints.LINE_END;
		kbConst.weightx = 1.0;
		kbConst.weighty = 0.5;
		kbConst.gridx = 0;
		kbConst.gridy = 2;
		kbConst.gridwidth = 11;
		kbConst.gridheight = 3;
		kbConst.insets = new Insets(0, 10, 100, 100);
		this.add(this.keyboard, kbConst);
		this.validate();
		this.repaint();
	}

	/**
	 * Removes the numpad from the view.
	 */
	public void removeNumpad() {
		this.remove(this.numpad);
		this.numpad = null;
		this.validate();
		this.repaint();
	}

	// ----------------------------------------------//
	// -------------------private methods-----------//

	private void setupUserIO() {
		// set the Container for the user interface.
		this.setupUserIOContainer();

		// add the input field.
		this.setupUserLiteralInput();

		// add the key-input field.
		this.setupUserNumericalInput();

		// add the button for generating input if the user is too lazy.
		this.setupIOGenerator();
	}

	private void setupUserIOContainer() {
		this.userCharacterIOContainer = new JPanel(new GridBagLayout());
        this.userCharacterIOContainer.setMinimumSize(new Dimension(500,600));
		GridBagConstraints inConst = new GridBagConstraints();
		inConst.anchor = GridBagConstraints.LINE_START;
		inConst.weightx = 0.5;
		inConst.weighty = 1.0;
		inConst.gridx = 0;
		inConst.gridy = 2;
		inConst.gridheight = 10;
		inConst.gridwidth = 4;
		inConst.fill = GridBagConstraints.VERTICAL;
		inConst.insets = new Insets(0, 100, 100, 0);
		this.add(this.userCharacterIOContainer, inConst);
	}

	private void setupUserLiteralInput() {

		// add the caption of the input field.
		JLabel inputCaption = new JLabel(
				CryptoExperimentView.i18n.tr("Put your name in here!"));
		GridBagConstraints capConst = new GridBagConstraints();
		capConst.gridx = 0;
		capConst.gridy = 0;
		capConst.insets = new Insets(0, 0, 0, 50);
		capConst.fill = GridBagConstraints.BOTH;
		this.userCharacterIOContainer.add(inputCaption, capConst);
		
		this.literalInput = new JTextField();
		this.literalInput.setPreferredSize(new Dimension(250, 50));
		GridBagConstraints inputConst = new GridBagConstraints();
		inputConst.gridx = 0;
		inputConst.gridy = 1;
		inputConst.gridheight = 2;
		inputConst.insets = new Insets(0, 0, 0, 50);
		inputConst.fill = GridBagConstraints.BOTH;
		this.userCharacterIOContainer.add(this.literalInput, inputConst);
		this.literalInput
				.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JLabel textLabel = new JLabel(CryptoExperimentView.i18n.tr("Or"));
		GridBagConstraints textConst = new GridBagConstraints();
		textConst.gridx = 0;
		textConst.gridy = 3;
		textConst.insets = new Insets(50, 200, 0, 0);
		textConst.fill = GridBagConstraints.BOTH;
		this.userCharacterIOContainer.add(textLabel, textConst);
	}

	private void setupUserNumericalInput() {
		this.keyInput = new JTextField();
		this.keyInput.setMinimumSize(new Dimension(50, 50));
		GridBagConstraints keyConst = new GridBagConstraints();
		keyConst.gridx = 2;
		keyConst.gridy = 1;
		keyConst.gridheight = 2;
		keyConst.fill = GridBagConstraints.BOTH;
		this.userCharacterIOContainer.add(this.keyInput, keyConst);
		this.keyInput.setBorder(BorderFactory.createLineBorder(Color.black));

		// add the caption for the input field.
		JLabel keyCaption = new JLabel("The key goes here.");
		GridBagConstraints keyCapConst = new GridBagConstraints();
		keyCapConst.gridx = 2;
		keyCapConst.gridy = 0;
		keyCapConst.fill = GridBagConstraints.BOTH;
		this.userCharacterIOContainer.add(keyCaption, keyCapConst);
	}

	private void setupIOGenerator() {
		this.generator = new JButton(
				CryptoExperimentView.i18n.tr("Generate letters"));
		this.generator.setPreferredSize(new Dimension(300, 50));
		GridBagConstraints genConst = new GridBagConstraints();
		genConst.weightx = 1.0;
		genConst.gridx = 0;
		genConst.gridy = 5;
		genConst.gridwidth = 3;
        genConst.insets = new Insets(50, 0, 0, 0);
		this.userCharacterIOContainer.add(this.generator, genConst);
	}

	private String wrapHtml(String text) {
		return "<html><body><div width=700px>" + text + "</div></body></html>";
	}

}
