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

import org.jdom2.Element;
import org.xnap.commons.i18n.I18n;

import edu.kit.iks.Cryptographics.Vigenere.VigenereVisualizationInfo;
import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.ImageView;
import edu.kit.iks.CryptographicsLib.VisualizationView;
/**
 * In this step will be shown how vigenere works.
 * @author Aydin Tekin
 */
public class FirstDemonstrationView extends VisualizationView{
	private static final long serialVersionUID = 6294968461280032987L;
	
	/**
	 * Localization instance
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(FirstDemonstrationView.class);
	
	/**
	 * clock image
	 */
	private ImageView clock;
	
	/**
	 * vigenere image
	 */
	private ImageView vigenere;
	
	/**
	 * text of vigenere
	 */
	private JLabel vigenereText;
	
	/**
	 * skip introduction
	 */
	private JButton skip;
	
	/**
	 * text of modulo-explanation
	 */
	private JLabel moduloText;
	
	/**
	 * link to the resource
	 */
	private Element vigenereXML;
	
	/**
	 * returns skip button
	 * @return skip button
	 */
	public JButton getSkipButton() {
		return this.skip;
	}
	
	/**
	 * creates all needed GUI elements
	 * @param visualizationInfo visualization information of current step
	 */
	private void setupGUI(VigenereVisualizationInfo visualizationInfo) {
		this.vigenereXML = visualizationInfo.getResources();
		this.setLayout(null);
		// create GUI Elements
		this.vigenereText = new JLabel("<html><div width=\"1100\">" + 
				i18n.tr("After Caesar's cipher was broken, Vigenère started to create his own cipher which would fix the weaknesses of Caesar. He also used the same logic by addition and subtraction of characters but this time "
						+ "the key would not be a simple number but a whole string. To understand how Vigenères cipher works, we need to know how 'modulo' works...")
				+ "</div></html>");
		this.moduloText = new JLabel("<html><div width=\"1100\">" + 
				i18n.tr("We use modulo in our daily life without maybe noticing it: If you look at your clock, you have a cyclic sequence of numbers. Like you see in the picture at the bottom, 4 hours after 9 means it's 1. So our "
				+ "clock uses a modulo of 12, because we have only 12 steps in it. If we overflow, we start from the beginnig. The same system will now be used with the alphabet (which has 26 characters) to create Vigenère's cipher...")
				+ "</div></html>");
		this.vigenere= new ImageView(vigenereXML.getChild(
				"vigenereImage").getAttributeValue("path"));
		this.clock = new ImageView(vigenereXML.getChild(
				"moduloImage").getAttributeValue("path"));
		this.skip = new JButton(i18n.tr("Go to Encryption"));
		// set fonts
		// add to the view
		this.add(this.vigenere);
		this.add(this.vigenereText);
		this.add(this.clock);
		this.add(this.moduloText);
		this.add(this.skip);
	}
	
	/**
	 * customizes the created GUI elements
	 */
	private void customizeGUI() {
		Dimension size = this.vigenereText.getPreferredSize();
		this.vigenereText.setBounds(180, 50,
	             size.width, size.height);
		
		size = this.vigenere.getPreferredSize();
		this.vigenere.setBounds(30, 50,
	             size.width, size.height);
		
		size = this.skip.getPreferredSize();
		this.skip.setBounds(1100, 5,
	             size.width, size.height);
		
		size = this.moduloText.getPreferredSize();
		this.moduloText.setBounds(180, 260,
	             size.width, size.height);
		
		size = this.clock.getPreferredSize();
		this.clock.setBounds(500, 500,
	             size.width, size.height);
		
		size = this.getBackButton().getPreferredSize();
		this.getBackButton().setBounds(30, 600,
	             size.width, size.height);
		
		size = this.getNextButton().getPreferredSize();
		this.getNextButton().setBounds(1100, 600,
	             size.width, size.height);
		
		// in the first step set modulo unvisible
		this.moduloText.setVisible(false);
		this.clock.setVisible(false);
		this.validate();
	}
	
	/**
	 * Constructor
	 * @param visualizationInfo visualization information of current step
	 */
	public FirstDemonstrationView(VigenereVisualizationInfo visualizationInfo) {
		super();
		setupGUI(visualizationInfo);
		customizeGUI();
	}
	
	/**
	 * returns the clock image
	 * @return clock image
	 */
	public ImageView getClock() {
		return clock;
	}
	
	/**
	 * returns the modulo label
	 * @return modulo label
	 */
	public JLabel getModuloText() {
		return this.moduloText;
	}
}
