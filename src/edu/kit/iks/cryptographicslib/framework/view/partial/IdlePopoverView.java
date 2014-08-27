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

package edu.kit.iks.cryptographicslib.framework.view.partial;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.cryptographicslib.common.view.partial.PopoverView;
import edu.kit.iks.cryptographicslib.util.Configuration;

/**
 * The idle popover is presented as soon as the user is detected as idle.
 * It displays a count-down after which the program will reset itself along
 * with an explanation how to continue using the application.
 * 
 * @author Matthias Plappert
 *
 */
public class IdlePopoverView extends PopoverView {
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -4551738579737944993L;
	
	/**
	 * The text label that displays the explanation.
	 */
	private JLabel textLabel;
	
	/**
	 * The text label that displays the count-down.
	 */
	private JLabel countdownLabel;
	
	/**
	 * The button that allows the user to continue using the app. This
	 * might be considered as a duplicate, since the close button does the same
	 * thing, but it's probably easier for the user if he can simply press continue.
	 */
	private JButton continueButton;
	
	/**
	 * The remaining time in milliseconds.
	 */
	private long remainingTime;
	
	/**
	 * The timer used to update the count-down label.
	 */
	private Timer updateTimer;
	
	/**
	 * Localization instance.
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(IdlePopoverView.class);
	
	/**
	 * Creates a new popover with a given remaining time in milliseconds.
	 * The remaining time will be counted down until it reaches zero.
	 * 
	 * @param initialTime Remaining the initial countdown time in milliseconds
	 */
	public IdlePopoverView(long initialTime) {
		super();
		this.remainingTime = initialTime;
		
		// Create a timer to update the view periodically.
		this.updateTimer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (remainingTime > 0) {
					remainingTime -= 1000;
					updateCountdownLabel();
				} else {
					updateTimer.stop();
					updateTimer = null;
				}
			}
		});
		this.updateTimer.start();
		
		// Configure layout.
		this.getContentView().setLayout(new GridBagLayout());
		
		// Text label.
		GridBagConstraints textConstraints = new GridBagConstraints();
		textConstraints.gridx = 0;
		textConstraints.gridy = 0;
		String text = i18n.tr("You haven\'t done anything in a while. To keep this exhibit functional, the system will reset itself soon. If you want to continue using the system, just dismiss this dialog.");
		this.textLabel = new JLabel("<html><div style=\"width:200px;\">" + text + "</div></html>");
		this.getContentView().add(this.textLabel, textConstraints);
		
		// Countdown label.
		GridBagConstraints countdownConstraints = new GridBagConstraints();
		countdownConstraints.gridx = 0;
		countdownConstraints.gridy = 1;
		countdownConstraints.insets = new Insets(20, 0, 20, 0);
		this.countdownLabel = new JLabel();
		this.updateCountdownLabel();
		this.getContentView().add(this.countdownLabel, countdownConstraints);
		
		// Continue button.
		GridBagConstraints continueConstraints = new GridBagConstraints();
		continueConstraints.gridx = 0;
		continueConstraints.gridy = 2;
		this.continueButton = new JButton(i18n.tr("Continue"));
		this.getContentView().add(this.continueButton, continueConstraints);
		
		this.getContentView().validate();
	}
	
	/**
	 * Updates the text of the {countdownLabel}.
	 */
	private void updateCountdownLabel() {
		int seconds = (int)(this.remainingTime / 1000);
		String text = String.format("<html><div style=\"width:200px;text-align:center;\">%d:%02d:%02d</div></html>", seconds/3600, (seconds%3600)/60, (seconds%60));
		this.countdownLabel.setText(text);
	}
	
	/**
	 * Returns the continue button.
	 * 
	 * @return the continue button
	 */
	public JButton getContinueButton() {
		return this.continueButton;
	}
}
