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

package edu.kit.iks.Cryptographics;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.CryptographicsLib.Configuration;

/**
 * An instance of this class represents the view of the welcome screen 
 * 
 * @author Christian Dreher
 */
public class WelcomeView extends JPanel {
	
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -8871817760284013217L;

	/**
	 * Localization instance
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(WelcomeView.class);
	
	/**
	 * String to be displayed as header
	 */
	private String welcome = i18n.tr("Welcome");
	
	/**
	 * String to be displayed as a description of how
	 * to use the timeline
	 */
	private String description = i18n.tr("This is Cryptographics, a small tool with the aim"
			+ " to show how basic and advanced cryptographic procedures work in principle."
			+ " Below you can see a timeline with some procedures. Tap on the dots representing"
			+ " their date of invention to view a small description and start the visualization.");
	
	public WelcomeView() {
		super(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		this.add(new JLabel(this.wrapHtml("<h2>" + this.welcome + "</h2>")), gbc);
		
		gbc.gridy = 1;
		
		this.add(new JLabel(this.wrapHtml("<div width=\"1000\">"
				+ this.description
				+ "</div>")), gbc);
	}
	
	private String wrapHtml(String text) {
		return "<html><body>" + text + "</body></html>";
	}
}
