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

package edu.kit.iks.cryptographicslib.framework.view;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Timer;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.cryptographicslib.util.Configuration;
import edu.kit.iks.cryptographicslib.util.Logger;
import edu.kit.iks.cryptographicslib.util.IO;

/**
 * An instance of this class represents the view of the welcome screen.
 * 
 * @author Christian Dreher
 */
public class WelcomeView extends JPanel {
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -8871817760284013217L;

	/**
	 * Localization instance.
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(WelcomeView.class);
	
	/**
	 * Generates the teaser code.
	 * 
	 * @author Christian Dreher <uaeef@student.kit.edu>
	 */
	private class TeaserCode {
		
		/**
		 * Text to be displayed.
		 */
		private String text = "";
		
		/**
		 * Length of the text.
		 */
		private int textLength;
		
		/**
		 * Constructor initializing a new random
		 * text with given text length.
		 * 
		 * @param textLength Length of the text
		 */
		public TeaserCode(int textLength) {
			this.textLength = textLength;
			
			for (int i = 0; i < this.textLength; i++) {
				text += this.newRandomChar();
			}
		}
		
		/**
		 * Gets the text.
		 * 
		 * @return The text
		 */
		public String getText() {
			return this.text;
		}
		
		/**
		 * Tilts one random character in the text.
		 * 
		 * @return Text with one tilted character
		 */
		public String tilt() {
			char newChar = this.newRandomChar();
			char[] textAsCharArray = this.text.toCharArray();
			int charToTilt = this.randomInt(0, this.textLength - 1);
			
			textAsCharArray[charToTilt] = newChar;
			this.text = String.valueOf(textAsCharArray);
			
			return this.text;
		}
		
		private char newRandomChar() {
			return (char) this.randomInt(33, 126);
		}
		
		private int randomInt(int min, int max) {
			return (min + (int) (Math.random() * ((max - min) + 1)));
		}
	}

	/**
	 * The teaser code to display.
	 */
	private TeaserCode teaserCode = new TeaserCode(53);
	
	/**
	 * JLabel of the teaser code. Needs to be explicitly set, since
	 * it should be tilted by a timer.
	 */
	private JLabel teaserCodeLabel = new JLabel(teaserCode.getText());
	
	/**
	 * Timer to tilt the teaser code in given interval.
	 */
	private Timer timer;
	
	/**
	 * Interval in which the timer should tilt letters.
	 */
	private int timerInterval = 10;
	
	/**
	 * String to be displayed as header.
	 */
	private String cryptographics = i18n.tr("Cryptographics");

	/**
	 * Constructor initializing the welcome view.
	 */
	public WelcomeView() {
		super(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		JLabel cryptographicsLabel = new JLabel(this.cryptographics);
		cryptographicsLabel.setFont(this.prepareFont(55));
		this.teaserCodeLabel.setFont(new Font("Courier New", Font.PLAIN, 12));
		this.add(cryptographicsLabel, gbc);
		
		gbc.gridy = 1;
		
		this.add(this.teaserCodeLabel, gbc);
		
		this.initTimer();
	}
	
	/**
	 * Stops the timer which randomly modifies the teaser code at the
	 * welcome view.
	 */
	public void stopTeaserCodeTimer() {
		this.timer.stop();
		Logger.debug("WelcomeView", "stopTeaserCodeTimer", "Timer stopped");
	}
	
	/**
	 * Prepares the font for the Cryptographics logo.
	 * 
	 * @param size Fontsize
	 * @return Initialized Font object
	 */
	private Font prepareFont(int size) {
		
		Font font = null;
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT,
					IO.getResourceAsInputStream("fonts/capture_smallz/Capsmall.ttf"));
			
			font = font.deriveFont(Font.PLAIN, size);
		} catch (FontFormatException | IOException e) {
			Logger.error(e);
		}
		
		return font;
	}
	
	/**
	 * Initialized the timer.
	 */
	private void initTimer() {
		Logger.debug("WelcomeView", "initTimer", "Random teaser code timer started");
		this.timer = new Timer(this.timerInterval, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String newTeaserCode = WelcomeView.this.teaserCode.tilt();
				WelcomeView.this.teaserCodeLabel.setText(newTeaserCode);
			}
		});
		
		this.timer.start();
	}
}
