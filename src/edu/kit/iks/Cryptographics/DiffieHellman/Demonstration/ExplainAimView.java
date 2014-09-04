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

package edu.kit.iks.Cryptographics.DiffieHellman.Demonstration;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.VisualizationView;

/**
 * Explain the aim of DH-KE
 * 
 * @author kai
 *
 */
public class ExplainAimView extends VisualizationView {
	
	/**
	 * Localization instance
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(ExplainAimView.class);
	
	/* holds the explanation */
	private JLabel aimExplain;

	/* demonstrate the problem of just sending the color */
	private ColorChannel cc;

	/* no help need here */
	private String help = i18n.tr("No help");
	
	private Navigation n;
	
	/* the explanation string */
	private String explain = i18n.tr("Our aim is to exchange a secret on a public channel, " +
			"without Eve getting the secret too. Therefore we can't simply send the secret, as shown " +
			"in the figure above as Eve would get the secret too. Thus we need " +
			"to exchange the secret in a different way");
	
	private static final long serialVersionUID = 5986978581223106407L;

	/**
	 * Layout and build the subcomponents
	 */
	public ExplainAimView() {
		super();
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.weightx = 0.1;
		gbc.weighty = 0.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		n = new Navigation(i18n.tr("Skip the Introduction"));
		this.add(n, gbc);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 1;
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		layout.setConstraints(this.getNextButton(), gbc);
		
		this.aimExplain = new JLabel();
		this.aimExplain.setText("<html><div style=\"width:400px\">" + explain + "</div></html>");
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		this.add(aimExplain, gbc);
		
		this.cc = new ColorChannel(new Dimension(800, 200), 50);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.weightx = 0.1;
		gbc.weighty = 0.1;
		gbc.fill = GridBagConstraints.BOTH;
		this.add(this.cc, gbc);
		this.cc.setColorNextToSend(Color.BLUE);
		this.cc.setKeepColor(false);
		this.cc.setRepeat(true);
		this.cc.sendToBob(null, false, "");
		
	}

	/**
	 * get the color channel
	 * @return the color channel
	 */
	public ColorChannel getColorChannel() {
		return this.cc;
	}

	/**
	 * explain string
	 * @return the explain string
	 */
	public String getHelp() {
		return help ;
	}
	
	public JButton getSkipButton() {
		return n.getForward();
	}
	


}
