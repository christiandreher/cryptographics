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

package edu.kit.iks.Cryptographics.Vigenere.Demonstration;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.CryptographicsLib.AlphabetStripView;
import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.VisualizationView;
import edu.kit.iks.Cryptographics.Vigenere.VigenereModel;

/**
 * in this step, the encryption will be explained
 * @author Aydin Tekin
 */
public class SecondDemonstrationView extends VisualizationView {
	private static final long serialVersionUID = 6294968461280032987L;

	/**
	 * Localization instance
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(SecondDemonstrationView.class);
	
	// PLAINTEXTCHARS WITH THEIR INDEXES
	/**
	 * plain characters
	 */
	private JLabel[] indexCharPlain;
	/**
	 * plain characters position in alphabet
	 */
	private JLabel[] textCharPlain;
	
	/**
	 * Button to skip step
	 */
	private JButton skip;
	
	/**
	 * Button to go back
	 */
	private JButton btnReturn;

	/**
	 * encrypted characters
	 */
	private JLabel[] indexCharEncrypted;
	
	/**
	 * encrypted characters position in alphabet
	 */
	private JLabel[] textCharEncrypted;

	/**
	 * description of the key
	 */
	private JLabel vigenereKeyDesc;
	
	/**
	 * key to encrypt with
	 */
	private String vigenereKey = i18n.tr("OKAY");

	/**
	 * used for calculator
	 */
	private JLabel charFirst;
	private JLabel charSecond;
	private JLabel charFinished;
	
	/**
	 * explanation field
	 */
	private JLabel explanation;
	
	/**
	 * alphabet used for demonstration
	 */
	private AlphabetStripView alphabet;
	
	/**
	 * returns skip button
	 * @return skip button
	 */
	public JButton getSkipButton() {
		return this.skip;
	}
	
	/**
	 * returns back button
	 * @return back button
	 */
	public JButton getReturnButton() {
		return this.btnReturn;
	}
	
	/**
	 * sets the calculator to given parameters
	 * @param a first character
	 * @param b amount of rotation
	 */
	public void setCalculator(int a, int b) {
		int sum = ((a + b) % 26);
		Dimension size;
		this.charFirst.setText(i18n.tr("Plaintext-Char") + ": " + a);
		this.charSecond.setText(i18n.tr("Key-Char") + ": " + b);
		this.charFinished.setText(i18n.tr("Result") + ": " + sum);
		size = this.charFirst.getPreferredSize();
		this.charFirst.setSize(size);
		size = this.charSecond.getPreferredSize();
		this.charSecond.setSize(size);
		size = this.charFinished.getPreferredSize();
		this.charFinished.setSize(size);
		this.validate();
	}
	
	/**
	 * changes given textfield and also applies the numeration in the bottom label
	 * @param i which textfield to be changed
	 * @param character which character to be added
	 */
	public void setTextField(int i, String character) {
		this.textCharEncrypted[i].setText(character);
		if (character.isEmpty()) {
			this.indexCharEncrypted[i].setText("");
		} else { 
			this.indexCharEncrypted[i].setText("" + VigenereModel.characterToInt(character));
		}
		Dimension size = this.indexCharEncrypted[i].getPreferredSize();
		this.indexCharEncrypted[i].setSize(size);
		this.validate();
	}

	/**
	 * this highlights the selected character in the AlphaStripView and sets the character in
	 * the specified textfield
	 * @param num character to be highlighted in the AlphaStripView
	 * @param pos textfield to be changed
	 * @param character character to be set
	 */
	public void highlightAndSetText(int num, int pos, String character) {
		this.alphabet.unHighlightAll();
		this.setTextField(pos, character);
		this.alphabet.highlight(pos);
	}
	
	/**
	 * returns the alphabet
	 * @return alphabet
	 */
	public AlphabetStripView getAlphabet() {
		return alphabet;
	}

	/**
	 * changes visibility of all label containing encrypted chars
	 * @param b visibility of all label containing encrypted chars
	 */
	public void setEncryptedCharsVisible(boolean b) {
		for (int i = 0; i < this.indexCharEncrypted.length; i++) {
			this.indexCharEncrypted[i].setVisible(b);
			this.textCharEncrypted[i].setVisible(b);
		}
	}
	
	/**
	 * sets the visibility of the calculator
	 * @param b visibility of the calculator
	 */
	public void setCalculatorVisible(boolean b) {
		this.charFirst.setVisible(b);
		this.charSecond.setVisible(b);
		this.charFinished.setVisible(b);
	}
	
	/**
	 * sets the visibility of the key
	 * @param b visibility of the key
	 */
	public void setKeyVisible(boolean b) {
		this.vigenereKeyDesc.setVisible(b);
	}
	
	/**
	 * changes the explanation text
	 * @param explanation text to change to
	 */
	public void setExplanation(String explanation) {
		this.explanation.setText(explanation);
	}
	
	/**
	 * creates and adds all GUI elements
	 */
	private void setupGUI() {
		this.setLayout(null);
		this.add(this.explanation = new JLabel("<html><div width=\"1200\">"
				+ i18n.tr("Now we want to encrypt 'ANNE'. First of all we add "
				+ "the position of every character, as you can see in the bottom, "
				+ "in the alphabet under each character.")
				+ "</div></html>"));
		this.vigenereKeyDesc = new JLabel(i18n.tr("Vigenere Key") + ": " + this.vigenereKey);
		
		this.add(this.skip = new JButton(i18n.tr("Go to Decryption")));
		this.add(this.btnReturn = new JButton(i18n.tr("Return to Introduction")));
		this.skip.setVisible(true);
		this.alphabet = new AlphabetStripView();

		this.textCharPlain = new JLabel[4];
		this.textCharPlain[0] = new JLabel("A");
		this.textCharPlain[1] = new JLabel("N");
		this.textCharPlain[2] = new JLabel("N");
		this.textCharPlain[3] = new JLabel("E");
		for (int i = 0; i < this.textCharPlain.length; i++) 
			this.add(this.textCharPlain[i]);

		this.indexCharPlain = new JLabel[4];
		this.indexCharPlain[0] = new JLabel(" 1");
		this.indexCharPlain[1] = new JLabel("14");
		this.indexCharPlain[2] = new JLabel("14");
		this.indexCharPlain[3] = new JLabel(" 5");
		for (int i = 0; i < this.indexCharPlain.length; i++)
			this.add(this.indexCharPlain[i]);

		this.textCharEncrypted = new JLabel[4];
		this.textCharEncrypted[0] = new JLabel();
		this.textCharEncrypted[1] = new JLabel();
		this.textCharEncrypted[2] = new JLabel();
		this.textCharEncrypted[3] = new JLabel();
		for (int i = 0; i < this.textCharEncrypted.length; i++)
			this.add(this.textCharEncrypted[i]);

		this.indexCharEncrypted = new JLabel[4];
		this.indexCharEncrypted[0] = new JLabel("  ");
		this.indexCharEncrypted[1] = new JLabel("  ");
		this.indexCharEncrypted[2] = new JLabel("  ");
		this.indexCharEncrypted[3] = new JLabel("  ");
		for (int i = 0; i < this.indexCharEncrypted.length; i++)
			this.add(this.indexCharEncrypted[i]);

		this.add(this.charFirst = new JLabel());
		this.add(this.charSecond = new JLabel());
		this.add(this.charFinished = new JLabel());
		this.add(this.vigenereKeyDesc);
		this.add(this.alphabet);
	}
	
	/**
	 * customizes the created GUI elements
	 */
	private void customizeGUI() {
		setCalculatorVisible(false);
		setEncryptedCharsVisible(false);
		setKeyVisible(false);
		
		Dimension size = this.vigenereKeyDesc.getPreferredSize();
		this.vigenereKeyDesc.setBounds(90, 250, size.width, size.height);
		
		size = this.textCharPlain[0].getPreferredSize();
		this.textCharPlain[0].setBounds(500, 150,
	             size.width, size.height);
		
		size = this.textCharPlain[1].getPreferredSize();
		this.textCharPlain[1].setBounds(550, 150,
	             size.width, size.height);
		
		size = this.textCharPlain[2].getPreferredSize();
		this.textCharPlain[2].setBounds(600, 150,
	             size.width, size.height);

		size = this.textCharPlain[3].getPreferredSize();
		this.textCharPlain[3].setBounds(650, 150,
	             size.width, size.height);
		
		size = this.indexCharPlain[0].getPreferredSize();
		this.indexCharPlain[0].setBounds(500, 210,
	             size.width, size.height);
		
		size = this.indexCharPlain[1].getPreferredSize();
		this.indexCharPlain[1].setBounds(550, 210,
	             size.width, size.height);
		
		size = this.indexCharPlain[2].getPreferredSize();
		this.indexCharPlain[2].setBounds(600, 210,
	             size.width, size.height);

		size = this.indexCharPlain[3].getPreferredSize();
		this.indexCharPlain[3].setBounds(650, 210,
	             size.width, size.height);
		
		size.width = 24;
		size.height = 44;
		this.textCharEncrypted[0].setBounds(500, 300,
	             size.width, size.height);
		
		this.textCharEncrypted[1].setBounds(550, 300,
	             size.width, size.height);
		
		this.textCharEncrypted[2].setBounds(600, 300,
	             size.width, size.height);

		this.textCharEncrypted[3].setBounds(650, 300,
	             size.width, size.height);
		
		size = this.indexCharEncrypted[0].getPreferredSize();
		this.indexCharEncrypted[0].setBounds(500, 360,
	             size.width, size.height);
		
		size = this.indexCharEncrypted[1].getPreferredSize();
		this.indexCharEncrypted[1].setBounds(550, 360,
	             size.width, size.height);
		
		size = this.indexCharEncrypted[2].getPreferredSize();
		this.indexCharEncrypted[2].setBounds(600, 360,
	             size.width, size.height);

		size = this.indexCharEncrypted[3].getPreferredSize();
		this.indexCharEncrypted[3].setBounds(650, 360,
	             size.width, size.height);
		
		size = this.alphabet.getPreferredSize();
		this.alphabet.setBounds(150, 570,
	             size.width, size.height);
		
		size = this.charFirst.getPreferredSize();
		this.charFirst.setBounds(900, 210,
	             size.width, size.height);
		
		size = this.charSecond.getPreferredSize();
		this.charSecond.setBounds(900, 260,
	             size.width, size.height);
		
		size = this.charFinished.getPreferredSize();
		this.charFinished.setBounds(900, 310,
	             size.width, size.height);
		
		size = this.explanation.getPreferredSize();
		this.explanation.setBounds(10, 50,
	             size.width, size.height);
		
		size = this.getBackButton().getPreferredSize();
		this.getBackButton().setBounds(30, 600,
	             size.width, size.height);
		
		
		size = this.getNextButton().getPreferredSize();
		this.getNextButton().setBounds(1100, 600,
	             size.width, size.height);
		
		size = this.getSkipButton().getPreferredSize();
		this.getSkipButton().setBounds(1100, 5,
	             size.width, size.height);
		
		size = this.getReturnButton().getPreferredSize();
		this.getReturnButton().setBounds(30, 5,
	             size.width, size.height);
	}
	
	
	/**
	 * constructor of the view
	 */
	public SecondDemonstrationView() {
		setupGUI();
		customizeGUI();
	}
}