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

package edu.kit.iks.Cryptographics.Vigenere.Explanation;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.Cryptographics.Vigenere.VigenereModel;
import edu.kit.iks.CryptographicsLib.CharacterFrequencyDiagramView;
import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.VisualizationView;

/*
 * This class represents the view of the first explanatation step. In the first step the disadvantages
 * and possible weak points of the vigenere-cipher.
 */
public class FirstExplanationView extends VisualizationView{
	private static final long serialVersionUID = 6294968461280032987L;
	
	/**
	 * Localization instance
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(FirstExplanationView.class);
	
	static final String vigenereText = "HEREUPON LEGRAND AROSE, WITH A GRAVE AND STATELY AIR, AND BROUGHT ME THE BEETLEFROM A GLASS CASE "
			+ "IN WHICH IT WAS ENCLOSED. IT WAS A BEAUTIFUL SCARABAEUS, AND, AT "
			+ "THAT TIME, UNKNOWN TO NATURALISTS-OF COURSE A GREAT PRIZE IN A SCIENTIFIC POINT "
			+ "OF VIEW. THERE WERE TWO ROUND BLACK SPOTS NEAR ONE EXTREMITY OF THE BACK, AND A "
			+ "LONG ONE NEAR THE OTHER. THE SCALES WERE EXCEEDINGLY HARD AND GLOSSY, WITH ALL THE "
			+ "APPEARANCE OF BURNISHED GOLD. THE WEIGHT OF THE INSECT WAS VERY REMARKABLE, AND, "
			+ "TAKING ALL THINGS INTO CONSIDERATION, I COULD HARDLY BLAME JUPITER FOR HIS OPINION "
			+ "RESPECTING IT.";
	static final String vigenereEncrypted = "VOFOIZCX ZOUBOXR KFYGO, KSHR O QFKJO OXR CHKHOZI OSF, KBN PBCEURH WS DVO POSDZO"
			+ "TBCW O QZKGC QKGO WX KRWMV SH GOC SXQVCCSN. WD KKG K POOEHSTEZ CQKFKPKSEG, KBN, OD"
			+ "HROD HSAO, IXYXCGB DC XODIBOVWCHC-CP QYIBGO O QFOOD DBWJS SB K GMWOBDWPWM DYWXH"
			+ "YT FWOK. DVOFO KOFO HGC BCEBN PVOMY CDYHC BOOB CXS OLDFOASHI CP HRS LOMY, KBN O"
			+ "VCXU YBO BOOB HRS YHRSB. HRS CQKZOG GSBS OLMSORSBQZI VKFN OXR QZYGCM, GWDV KZV HRS"
			+ "KDZSKFKBMS YT LIBBSGRSN UYZN. HRS GSSURH YT DVO WXGOQD KKG FSBM BSWOBYKPVS, KBN,"
			+ "HKYSBQ OVZ DVSBQG SBDC MCXGSROFKHSCX, W MCEZN VKFNZI PVOWS TIZWDSB TYF RWC CZWXWYB"
			+ "BSCDOQDWXU SH.";
	static final String averageText = "HEREUPON LEGRAND AROSE, WITH A GRAVE AND STATELY AIR, AND BROUGHT ME THE BEETLEFROM A GLASS CASE "
			+ "IN WHICH IT WAS ENCLOSED. IT WAS A BEAUTIFUL SCARABAEUS, AND, AT"
			+ "THAT TIME, UNKNOWN TO NATURALISTS-OF COURSE A GREAT PRIZE IN A SCIENTIFIC POINT"
			+ "OF VIEW. THERE WERE TWO ROUND BLACK SPOTS NEAR ONE EXTREMITY OF THE BACK, AND A"
			+ "LONG ONE NEAR THE OTHER. THE SCALES WERE EXCEEDINGLY HARD AND GLOSSY, WITH ALL THE"
			+ "APPEARANCE OF BURNISHED GOLD. THE WEIGHT OF THE INSECT WAS VERY REMARKABLE, AND,"
			+ "TAKING ALL THINGS INTO CONSIDERATION, I COULD HARDLY BLAME JUPITER FOR HIS OPINION"
			+ "RESPECTING IT.";
	static final String encryptedAverageText = "VOFOIZCX ZOUBOXR KFYGO, KSHR O QFKJO OXR CHKHOZI OSF, KBN PBCEURH WS DVO POSDZO"
			+ "TBCW O QZKGC QKGO WX KRWMV SH GOC SXQVCCSN. WD KKG K POOEHSTEZ CQKFKPKSEG, KBN, OD"
			+ "HROD HSAO, IXYXCGB DC XODIBOVWCHC-CP QYIBGO O QFOOD DBWJS SB K GMWOBDWPWM DYWXH"
			+ "YT FWOK. DVOFO KOFO HGC BCEBN PVOMY CDYHC BOOB CXS OLDFOASHI CP HRS LOMY, KBN O"
			+ "VCXU YBO BOOB HRS YHRSB. HRS CQKZOG GSBS OLMSORSBQZI VKFN OXR QZYGCM, GWDV KZV HRS"
			+ "KDZSKFKBMS YT LIBBSGRSN UYZN. HRS GSSURH YT DVO WXGOQD KKG FSBM BSWOBYKPVS, KBN,"
			+ "HKYSBQ OVZ DVSBQG SBDC MCXGSROFKHSCX, W MCEZN VKFNZI PVOWS TIZWDSB TYF RWC CZWXWYB"
			+ "BSCDOQDWXU SH.";
	
	/**
	 * histogramm of encrypted text
	 */
	private CharacterFrequencyDiagramView vigenereHistogramm;
	
	/**
	 * histogramm of average text
	 */
	private CharacterFrequencyDiagramView averageHistogramm;
	
	/**
	 * explanation
	 */
	private JLabel explanation;
	
	/**
	 * key length
	 */
	private JLabel keyLength;
	
	/**
	 * explanation
	 */
	private JLabel secondExplanation;
	
	/**
	 * explanation
	 */
	private JLabel thirdExplanation;
	
	/**
	 * button to find key
	 */
	private JButton findKeyLength;
	
	/**
	 * Button to go back
	 */
	private JButton btnReturn;
	
	/**
	 * Button to skip step
	 */
	private JButton skip;
	
	/**
	 * returns back button
	 * @return back button
	 */
	public JButton getReturnButton() {
		return this.btnReturn;
	}
	
	/**
	 * returns skip button
	 * @return skip button
	 */
	public JButton getSkipButton() {
		return this.skip;
	}
	
	/**
	 * sets the keylength label
	 * @param s text to change label to
	 */
	public void setKeyLength(String s) {
		this.keyLength.setText(s);
	}
	
	/**
	 * returns the button to get keylength
	 * @return button to get keylength
	 */
	public JButton getKeyLengthButton() {
		return this.findKeyLength;
	}
	
	/**
	 * changes the explanation text
	 * @param explanation text to change to
	 */
	public void setExplanation(String s){
		this.explanation.setText(s);
		Dimension size = this.explanation.getPreferredSize();
		this.explanation.setBounds(10, 50,
	             size.width, size.height);
		this.validate();
	}
	
	/**
	 * changes the visibility according to first step
	 * @param b visibility according to first step
	 */
	public void visibleFirstState(boolean b) {
		this.keyLength.setVisible(b);
		this.findKeyLength.setVisible(b);
	}
	
	/**
	 * changes the visibility according to second step
	 * @param b visibility according to second step
	 */
	public void visibleSecondState(boolean b) {
		this.vigenereHistogramm.setVisible(b);
		this.secondExplanation.setVisible(b);
		this.averageHistogramm.setVisible(b);
		this.thirdExplanation.setVisible(b);
	}
	
	/**
	 * creates and adds all GUI elements
	 */
	private void setupGUI() {
		this.setLayout(null);
		this.add(this.skip = new JButton(i18n.tr("Go to Cracking")));
		this.add(this.btnReturn = new JButton(i18n.tr("Return to Experiment")));
		this.add(this.explanation = new JLabel("<html><div width=\"1200\">"
			+ i18n.tr("Vigenère fixed a few weaknesses of Caesar, but still has flaws. If "
			+ "the key is shorter then the text to encrypt, "
       		+ "Vigenère simply concatinates it with itself until the key is long enough. "
       		+ "This again makes it weak against key-length-guessing. "
       		+ "As soon as we guess the key-length, we can use a histogram "
       		+ "(explained in Caesar) to make a static analysis. So lets crack this "
       		+ "chiffre by guessing the key-length (we will use the Kasiski test for it)")
       		+ ":<br><br>"
       		+ vigenereEncrypted
       		+ "</div></html>"));
		this.add(this.secondExplanation = new JLabel("<html><div width=\"1200\">"
				+ i18n.tr("This is the average distribution of the characters in English texts:")
				+ "</div></html>"));
		this.add(this.findKeyLength = new JButton(i18n.tr("Find Key-Length")));
		this.add(this.keyLength = new JLabel("<html><div width=\"1200\">"
				+ i18n.tr("Length of key: ?")
				+ "</div></html>"));
		this.add(this.thirdExplanation = new JLabel("<html><div width=\"1200\">"
				+ i18n.tr("When we look closer at the histogram we see some differences in the "
				+ "distribution of the characters; 'S' is has the most common occurance. "
				+ "In an average normal English text 'E' is the most common one. So maybe "
				+ "our 'S' could've been an 'E' before the encryption. We calculate 19 (position of 'S') "
				+ "- 5 (position of 'E') and we get 14, which means "
				+ "the first character of the key is 'N'.")
				+ "</div></html>"));
		this.vigenereHistogramm = new CharacterFrequencyDiagramView(VigenereModel.getCharPositionated(0, 2, encryptedAverageText), 600,
				100);
		this.add(this.vigenereHistogramm);
		this.vigenereHistogramm.setVisible(false);
		
		this.averageHistogramm = new CharacterFrequencyDiagramView(averageText, 600,
				100);
		this.add(this.averageHistogramm);
		this.averageHistogramm.setVisible(false);
	}
	
	
	/**
	 * customizes the created GUI elements
	 */
	private void customizeGUI() {
		Dimension size = this.explanation.getPreferredSize();
		this.explanation.setBounds(10, 50,
	             size.width, size.height);
		size = this.findKeyLength.getPreferredSize();
		this.findKeyLength.setBounds(600, 390,
	             size.width, size.height);
		
		size = this.keyLength.getPreferredSize();
		this.keyLength.setBounds(10, 390,
	             size.width, size.height);
		
		size = this.secondExplanation.getPreferredSize();
		this.secondExplanation.setBounds(10, 290,
	             size.width, size.height);
		this.secondExplanation.setVisible(false);
		
		size = this.thirdExplanation.getPreferredSize();
		this.thirdExplanation.setBounds(10, 470,
	             size.width, size.height);
		this.thirdExplanation.setVisible(false);
		
		size = this.getBackButton().getPreferredSize();
		this.getBackButton().setBounds(30, 600,
	             size.width, size.height);
		
		size = this.getNextButton().getPreferredSize();
		this.getNextButton().setBounds(1100, 600,
	             size.width, size.height);
		this.getNextButton().setVisible(false);
		
		size = this.getSkipButton().getPreferredSize();
		this.getSkipButton().setBounds(1100, 5,
	             size.width, size.height);
		
		size = this.getReturnButton().getPreferredSize();
		this.getReturnButton().setBounds(30, 5,
	             size.width, size.height);
		
		this.vigenereHistogramm.setBounds(10, 190,
	             600, 100);
		this.averageHistogramm.setBounds(10, 330,
	             600, 100);
	}
	
	/**
	 * Constructor
	 */
	public FirstExplanationView() {
		setupGUI();
		customizeGUI();
	}
}