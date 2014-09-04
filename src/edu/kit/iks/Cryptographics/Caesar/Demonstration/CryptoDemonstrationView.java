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

package edu.kit.iks.Cryptographics.Caesar.Demonstration;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdom2.Element;
import org.xnap.commons.i18n.I18n;

import edu.kit.iks.Cryptographics.Caesar.CryptoView;
import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.KeyboardView;

/**
 * Performs animations for demonstrating Caesar's idea.
 * 
 * @author Wasilij Beskorovajnov.
 * 
 */
public class CryptoDemonstrationView extends CryptoView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static I18n i18n = Configuration.getInstance().getI18n(
			CryptoDemonstrationView.class);

	/**
	 * XML-root element for cipherdemoView elements.
	 */
	private Element cipherDemoResource;

	private JPanel forwardingViewContainer;

	/**
	 * Button needed for proceeding the stepwise animations.
	 */
	private JButton proceed;

	/**
	 * Constructor.
	 */
	public CryptoDemonstrationView() {
		super();

		// setup the caption of the next/back Buttons.
		this.getBackButton().setText(
				CryptoDemonstrationView.i18n.tr("Back to Introduction"));
		this.getNextButton().setText(
				CryptoDemonstrationView.i18n.tr("Skip the demonstration"));

		// setup the fields for the demonstratoin of the encryption.
		String caesar = CryptoDemonstrationView.i18n.tr("CAESAR");
		char[] chars = caesar.toCharArray();

		// Setup the io textfields.
		this.setupInOutElements(chars, 3);

		// Make the Textfields uneditable for the first steps.
		for (JTextField output : this.userOutput) {
			output.setEditable(false);
		}
		
		this.forwardingViewContainer = new JPanel(new GridBagLayout());
		this.forwardingViewContainer.setPreferredSize(new Dimension(800,200));
		GridBagConstraints kbConst = new GridBagConstraints();
		kbConst.anchor = GridBagConstraints.PAGE_END;
		kbConst.weightx = 1.0;
		kbConst.weighty = 1.0;
		kbConst.gridx = 0;
		kbConst.gridy = 0;
		kbConst.gridwidth = 11;
		kbConst.gridheight = 3;
		this.add(this.forwardingViewContainer, kbConst);

		// setup the forwarding button.
		this.proceed = new JButton(CryptoDemonstrationView.i18n.tr("Proceed"));
		this.proceed.setPreferredSize(new Dimension(200, 50));
		this.forwardingViewContainer.add(this.proceed);

		// setup the explanation label.
		String explanations = this.wrapHtml(CryptoDemonstrationView.i18n
						.tr("Imagine now you are the mighty Caesar! Of course your first idea is to substitute each"
								+ " letter from your name with another one from the alphabet. But which one?"
								+ " Press proceed to move on."));
		this.setupExplanations(explanations,
				GridBagConstraints.LAST_LINE_START, 0, 0, 4);

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
	public void createKeyboard(JTextField input, final int flag) {
		this.keyboard = new KeyboardView(input, flag);
		this.forwardingViewContainer.add(this.keyboard);
		this.validate();
		this.repaint();
	}
	
	/**
	 * Removes the keyboard from the view.
	 */
	public void removeKeyboard() {
		this.forwardingViewContainer.remove(this.keyboard);
		this.keyboard = null;
		this.validate();
		this.repaint();
	}
	
	private String wrapHtml(String text) {
		return "<html><body><div width=600px>" + text + "</div></body></html>";
	}
	
	//-----------------------------------------------------//
	//-------------------Getter/Setter---------------------//

	/**
	 * @return the cipherDemoResource
	 */
	public Element getCipherDemoResource() {
		return this.cipherDemoResource;
	}

	/**
	 * @param cipherDemoResource
	 *            the cipherDemoResource to set
	 */
	public void setCipherDemoResource(Element cipherDemoResource) {
		this.cipherDemoResource = cipherDemoResource;
	}

	/**
	 * @return the proceed
	 */
	@Override
	public JButton getProceed() {
		return this.proceed;
	}

	/**
	 * @param proceed
	 *            the proceed to set
	 */
	@Override
	public void setProceed(JButton proceed) {
		this.proceed = proceed;
	}

	/**
	 * @return the forwardingViewContainer
	 */
	public JPanel getForwardingViewContainer() {
		return forwardingViewContainer;
	}

	/**
	 * @param forwardingViewContainer the forwardingViewContainer to set
	 */
	public void setForwardingViewContainer(JPanel forwardingViewContainer) {
		this.forwardingViewContainer = forwardingViewContainer;
	}
	
	

}