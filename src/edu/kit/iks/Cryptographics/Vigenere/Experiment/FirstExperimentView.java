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

package edu.kit.iks.Cryptographics.Vigenere.Experiment;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.Cryptographics.Vigenere.VigenereModel;
import edu.kit.iks.CryptographicsLib.AlphabetStripView;
import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.KeyboardView;
import edu.kit.iks.CryptographicsLib.VisualizationView;

/**
 * In this step, the user will decrypt a given message
 * @author Aydin Tekin
 */
public class FirstExperimentView extends VisualizationView{
	private static final long serialVersionUID = 6294968461280032987L;
	
	/**
	 * Localization instance
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(FirstExperimentView.class);
	
	/**
	 * plain characters
	 */
	private JLabel[] indexCharPlain;
	
	/**
	 * plain characters position in alphabet
	 */
	private JLabel[] textCharPlain;
	/**
	 * decrypted characters
	 */
	private JLabel[] indexCharDecrypted;
	
	/**
	 * decrypted characters position in alphabet
	 */
	private JTextField[] textCharDecrypted;
	
	/**
	 * description of the key
	 */
	private JLabel vigenereKeyDesc;
	
	/**
	 * key to encrypt with
	 */
	private String vigenereKey = "ABCDE";
	
	/**
	 * explanation field
	 */
	private JLabel explanation;
	
	/**
	 * alphabet used for demonstration
	 */
	private AlphabetStripView alphabet;
	
	/**
	 * error message
	 */
	private JLabel errorMessage;
	
	/**
	 * keyboard used for inputs
	 */
	private KeyboardView keyboard;
	
	/**
	 * Button to skip step
	 */
	private JButton skip;
	
	/**
	 * Button to go back
	 */
	private JButton btnReturn;
	
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
	 * creates the keyboard
	 * @param input listener for the keyboard
	 * @param flag which type of keyboard we want
	 */
	public void createKeyboard(JTextField input, final int flag) {
		this.keyboard = new KeyboardView(input, flag);
		this.add(this.keyboard);
		Dimension size = this.keyboard.getPreferredSize();
		this.keyboard.setBounds(400, 400, size.width, size.height);
		this.validate();
	}
	
	/**
	 * hightlights the given textfield
	 * @param i
	 */
	public void highlightTextBorder(int i) {
		this.textCharDecrypted[i].setEnabled(true);
		this.textCharDecrypted[i].setBorder(BorderFactory.createLineBorder(Color.blue, 5));
	}
	
	/**
	 * unhighlights the given textfield
	 * @param i
	 */
	public void unHighlightTextBorder(int i) {
		this.textCharDecrypted[i].setEnabled(false);
		this.textCharDecrypted[i].setBorder(null);
	}
	
	/**
	 * @return the keyboard
	 */
	public KeyboardView getKeyboard() {
		return this.keyboard;
	}
	
	/**
	 * @param keyboard
	 *            the keyboard to set
	 */
	public void setKeyboard(KeyboardView keyboard) {
		this.keyboard = keyboard;
	}
	
	/**
	 * returns the alphabet
	 * @return alphabet
	 */
	public AlphabetStripView getAlphabet() {
		return this.alphabet;
	}
	
	/**
	 * returns the key
	 * @return key
	 */
	public String getKey() {
		return this.vigenereKey;
	}
	
	/**
	 * changes given textfield and also applies the numeration in the bottom label
	 * @param i which textfield to be changed
	 * @param character which character to be added
	 */
	public void setTextField(int i, String character) {
		this.textCharDecrypted[i].setText(character);
		if (character.isEmpty()) {
			this.indexCharDecrypted[i].setText("");
		} else { 
			this.indexCharDecrypted[i].setText("" + VigenereModel.characterToInt(character));
		}
		Dimension size = this.indexCharDecrypted[i].getPreferredSize();
		this.indexCharDecrypted[i].setSize(size);
		this.validate();
	}
	
	/**
	 * returns the decrypted textfield of the current index
	 * @param i index of the textfield
	 * @return textfield according index
	 */
	public JTextField getTextFieldDecrypted(int i) {
		return this.textCharDecrypted[i];
	}
	
	/**
	 * returns the plain textfield of the current index
	 * @param i index of the textfield
	 * @return textfield according index
	 */
	public JLabel getTextFieldPlain(int i) {
		return this.textCharPlain[i];
	}
	
	/**
	 * return all textfields
	 * @return all textfields
	 */
	public JTextField[] getTextFields() {
		return this.textCharDecrypted;
	}
	
	/**
	 * changes the explanation text
	 * @param explanation text to change to
	 */
	public void setExplanation(String explanation) {
		this.explanation.setText(explanation);
		Dimension size = this.explanation.getPreferredSize();
		this.explanation.setSize(size.width, size.height);
		this.validate();
	}
	
	/**
	 * shows error message and highlights the texfield
	 * @param i highlights the texfield
	 */
	public void showError(int i) {
		this.textCharDecrypted[i].setBorder(BorderFactory.createLineBorder(Color.red, 5));
		this.textCharDecrypted[i].setText("");
		this.errorMessage.setVisible(true);
	}
	
	/**
	 * hide error message
	 */
	public void hideError() {
		this.errorMessage.setVisible(false);
	}
	
	
	/**
	 * creates and adds all GUI elements
	 */
	private void setupGUI() {
		this.setLayout(null);
		this.add(this.explanation = new JLabel("<html><div width=\"1200\">"
				+ i18n.tr("Now it's your turn! Decrypt this string...")
				+ "</div></html>"));
		this.add(this.errorMessage = new JLabel(i18n.tr("Wrong Answer! Try again!")));
		
		this.vigenereKeyDesc = new JLabel(i18n.tr("Vigenere Key") + ": " + this.vigenereKey);
		this.alphabet = new AlphabetStripView();
		this.add(this.skip = new JButton(i18n.tr("Go to Explanation")));
		this.add(this.btnReturn = new JButton(i18n.tr("Return to Decryption")));
		this.skip.setVisible(true);
		this.textCharPlain = new JLabel[5];
		this.textCharPlain[0] = new JLabel("T");
		this.textCharPlain[1] = new JLabel("W");
		this.textCharPlain[2] = new JLabel("S");
		this.textCharPlain[3] = new JLabel("I");
		this.textCharPlain[4] = new JLabel("W");
		for (int i = 0; i < this.textCharPlain.length; i++)
			this.add(this.textCharPlain[i]);

		this.indexCharPlain = new JLabel[5];
		this.indexCharPlain[0] = new JLabel("20");
		this.indexCharPlain[1] = new JLabel("23");
		this.indexCharPlain[2] = new JLabel("19");
		this.indexCharPlain[3] = new JLabel(" 9");
		this.indexCharPlain[4] = new JLabel("23");
		for (int i = 0; i < this.indexCharPlain.length; i++)
			this.add(this.indexCharPlain[i]);

		this.textCharDecrypted = new JTextField[5];
		this.textCharDecrypted[0] = new JTextField();
		this.textCharDecrypted[1] = new JTextField();
		this.textCharDecrypted[2] = new JTextField();
		this.textCharDecrypted[3] = new JTextField();
		this.textCharDecrypted[4] = new JTextField();
		for (int i = 0; i < this.textCharDecrypted.length; i++) {
			this.add(this.textCharDecrypted[i]);
			this.textCharDecrypted[i].setEnabled(false);
		}
		
		this.indexCharDecrypted = new JLabel[5];
		this.indexCharDecrypted[0] = new JLabel("  ");
		this.indexCharDecrypted[1] = new JLabel("  ");
		this.indexCharDecrypted[2] = new JLabel("  ");
		this.indexCharDecrypted[3] = new JLabel("  ");
		this.indexCharDecrypted[4] = new JLabel("  ");
		for (int i = 0; i < this.indexCharDecrypted.length; i++)
			this.add(this.indexCharDecrypted[i]);
		
		this.add(this.vigenereKeyDesc);
		this.add(this.alphabet);
	}
	
	/**
	 * customizes the created GUI elements
	 */
	private void customizeGUI() {
		Dimension size = this.vigenereKeyDesc.getPreferredSize();
		this.vigenereKeyDesc.setBounds(90, 300, size.width, size.height);
		
		size = this.errorMessage.getPreferredSize();
		this.errorMessage.setBounds(500, 400, size.width, size.height);
		
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
		
		size = this.textCharPlain[4].getPreferredSize();
		this.textCharPlain[4].setBounds(700, 150,
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
		
		size = this.indexCharPlain[4].getPreferredSize();
		this.indexCharPlain[4].setBounds(700, 210,
	             size.width, size.height);
		
		size.width = 50;
		size.height = 44;
		this.textCharDecrypted[0].setBounds(500, 300,
	             size.width, size.height);
		
		this.textCharDecrypted[1].setBounds(550, 300,
	             size.width, size.height);
		
		this.textCharDecrypted[2].setBounds(600, 300,
	             size.width, size.height);

		this.textCharDecrypted[3].setBounds(650, 300,
	             size.width, size.height);
		
		this.textCharDecrypted[4].setBounds(700, 300,
	             size.width, size.height);
		
		size = this.indexCharDecrypted[0].getPreferredSize();
		this.indexCharDecrypted[0].setBounds(500, 360,
	             size.width, size.height);
		
		size = this.indexCharDecrypted[1].getPreferredSize();
		this.indexCharDecrypted[1].setBounds(550, 360,
	             size.width, size.height);
		
		size = this.indexCharDecrypted[2].getPreferredSize();
		this.indexCharDecrypted[2].setBounds(600, 360,
	             size.width, size.height);

		size = this.indexCharDecrypted[3].getPreferredSize();
		this.indexCharDecrypted[3].setBounds(650, 360,
	             size.width, size.height);
		
		size = this.indexCharDecrypted[4].getPreferredSize();
		this.indexCharDecrypted[4].setBounds(700, 360,
	             size.width, size.height);
		
		size = this.alphabet.getPreferredSize();
		this.alphabet.setBounds(150, 570,
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
		
		getAlphabet().highlight(0);
		getAlphabet().highlight(19);
		highlightTextBorder(0);
		hideError();
	}
	
	/**
	 * Constructor
	 */
	public FirstExperimentView() {
		setupGUI();
		customizeGUI();
	}
}