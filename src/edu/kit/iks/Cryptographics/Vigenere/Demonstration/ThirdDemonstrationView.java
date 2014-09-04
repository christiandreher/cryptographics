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

import edu.kit.iks.Cryptographics.Vigenere.VigenereModel;
import edu.kit.iks.CryptographicsLib.AlphabetStripView;
import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.VisualizationView;

/**
 * in this step the decryption will be explained.
 * @author Aydin Tekin
 */
public class ThirdDemonstrationView extends VisualizationView{
	private static final long serialVersionUID = 6294968461280032987L;
	
	/**
	 * Localization instance
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(ThirdDemonstrationView.class);
	
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
	private JLabel[] textCharDecrypted;
	
	/**
	 * description of the key
	 */
	private JLabel vigenereKeyDesc;
	
	/**
	 * key to encrypt with
	 */
	private String vigenereKey = i18n.tr("KISS");
	
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
	 * sets the calculator to given parameters
	 * @param a first character
	 * @param b amount of rotation
	 */
	public void setCalculator(int a, int b) {
		int temp = (a - b);
		if (temp < 0)
			temp = 26 + temp;
		Dimension size;
		this.charFirst.setText(i18n.tr("Plaintext-Char") + ": " + a);
		this.charSecond.setText(i18n.tr("Key-Char") + ": " + b);
		this.charFinished.setText(i18n.tr("Result") + ": " + temp);
		size = this.charFirst.getPreferredSize();
		this.charFirst.setSize(size);
		size = this.charSecond.getPreferredSize();
		this.charSecond.setSize(size);
		size = this.charFinished.getPreferredSize();
		this.charFinished.setSize(size);
		this.validate();
	}
	
	/**
	 * returns the alphabet
	 * @return alphabet
	 */
	public AlphabetStripView getAlphabet() {
		return alphabet;
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
	 * sets the visibility of the calculator
	 * @param b visibility of the calculator
	 */
	public void setCalculatorVisible(boolean b) {
		this.charFirst.setVisible(b);
		this.charSecond.setVisible(b);
		this.charFinished.setVisible(b);
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
	 * creates and adds all GUI elements
	 */
	private void setupGUI() {
		this.setLayout(null);
		this.add(this.explanation = new JLabel("<html><div width=\"1200\">"
				+ i18n.tr("Now we want to decrypt 'ENQM'. Instead of adding up, we "
				+ "use substraction to decrypt it! So let's go...")
				+ "</div></html>"));
		this.vigenereKeyDesc = new JLabel(i18n.tr("Vigenere Key") +
				": " + this.vigenereKey);
		this.alphabet = new AlphabetStripView();
		this.add(this.skip = new JButton(i18n.tr("Go To Experiment")));
		this.add(this.btnReturn = new JButton(i18n.tr("Return to Encryption")));
		this.skip.setVisible(true);
		this.textCharPlain = new JLabel[4];
		this.textCharPlain[0] = new JLabel("E");
		this.textCharPlain[1] = new JLabel("N");
		this.textCharPlain[2] = new JLabel("Q");
		this.textCharPlain[3] = new JLabel("M");
		for (int i = 0; i < this.textCharPlain.length; i++)
			this.add(this.textCharPlain[i]);

		this.indexCharPlain = new JLabel[4];
		this.indexCharPlain[0] = new JLabel(" 5");
		this.indexCharPlain[1] = new JLabel("14");
		this.indexCharPlain[2] = new JLabel("17");
		this.indexCharPlain[3] = new JLabel("13");
		for (int i = 0; i < this.indexCharPlain.length; i++)
			this.add(this.indexCharPlain[i]);

		this.textCharDecrypted = new JLabel[4];
		this.textCharDecrypted[0] = new JLabel();
		this.textCharDecrypted[1] = new JLabel();
		this.textCharDecrypted[2] = new JLabel();
		this.textCharDecrypted[3] = new JLabel();
		for (int i = 0; i < this.textCharDecrypted.length; i++)
			this.add(this.textCharDecrypted[i]);

		this.indexCharDecrypted = new JLabel[4];
		this.indexCharDecrypted[0] = new JLabel("  ");
		this.indexCharDecrypted[1] = new JLabel("  ");
		this.indexCharDecrypted[2] = new JLabel("  ");
		this.indexCharDecrypted[3] = new JLabel("  ");
		for (int i = 0; i < this.indexCharDecrypted.length; i++)
			this.add(this.indexCharDecrypted[i]);
		
       
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
		Dimension size = this.vigenereKeyDesc.getPreferredSize();
		this.vigenereKeyDesc.setBounds(90, 300, size.width, size.height);
		
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
		this.textCharDecrypted[0].setBounds(500, 300,
	             size.width, size.height);
		
		this.textCharDecrypted[1].setBounds(550, 300,
	             size.width, size.height);
		
		this.textCharDecrypted[2].setBounds(600, 300,
	             size.width, size.height);

		this.textCharDecrypted[3].setBounds(650, 300,
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
	public ThirdDemonstrationView() {
		setupGUI();
		customizeGUI();
	}
}