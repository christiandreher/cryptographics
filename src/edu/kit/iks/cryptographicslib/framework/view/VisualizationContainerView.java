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

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.cryptographicslib.util.Configuration;

/**
 * An instance of this class represents the view of container controller
 * beside the actual visualization.
 * 
 * @author Christian Dreher
 */
public class VisualizationContainerView extends JPanel {
	
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 2802070691102616667L;

	/**
	 * Name of the current visualization.
	 */
	private JLabel nameLabel;
	
	/**
	 * Button to exit the current visualization.
	 */
	private JButton exitButton;
	
	/**
	 * Button to access interactive help for the current step inside the
	 * visualization.
	 */
	private JButton helpButton;
	
	/**
	 * The content view that contains the actual visualization.
	 */
	private JPanel contentView;
	
	/**
	 * Localization instance.
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(VisualizationContainerView.class);
	
	/**
	 * Constructor initializing a new instance of {VisualizationContainerView}.
	 */
	public VisualizationContainerView() {
		super(new BorderLayout());
		
		// Create the container for buttons and title.
		JPanel container = new JPanel(new GridBagLayout());
		container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.add(container, BorderLayout.NORTH);
		
		// Create the exit button.
		GridBagConstraints exitConstraints = new GridBagConstraints();
		exitConstraints.weightx = 1.0;
		exitConstraints.weighty = 0.1;
		exitConstraints.gridx = 0;
		exitConstraints.gridy = 0;
		exitConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		this.exitButton = new JButton(i18n.tr("Exit"));
		container.add(this.exitButton, exitConstraints);
		
		// Create the name label.
		GridBagConstraints nameConstraints = new GridBagConstraints();
		nameConstraints.weightx = 1.0;
		nameConstraints.weighty = 0.1;
		nameConstraints.gridx = 1;
		nameConstraints.gridy = 0;
		this.nameLabel = new JLabel();
		container.add(this.nameLabel, nameConstraints);
		
		// Create the help button.
		GridBagConstraints helpConstraints = new GridBagConstraints();
		helpConstraints.weightx = 1.0;
		helpConstraints.weighty = 0.1;
		helpConstraints.gridx = 2;
		helpConstraints.gridy = 0;
		helpConstraints.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		this.helpButton = new JButton(i18n.tr("Help"));
		container.add(this.helpButton, helpConstraints);
		
		// Create content view.
		this.contentView = new JPanel(new BorderLayout());
	    this.contentView.setName("visualizationContainerContent");
	    this.contentView.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    this.add(this.contentView, BorderLayout.CENTER);
	    
	    this.validate();
	}

	/**
	 * @return the nameLabel
	 */
	public JLabel getNameLabel() {
		return nameLabel;
	}

	/**
	 * @return the exitButton
	 */
	public JButton getExitButton() {
		return exitButton;
	}

	/**
	 * @return the helpButton
	 */
	public JButton getHelpButton() {
		return helpButton;
	}
	
	/**
	 * @return the contentView
	 */
	public JPanel getContentView() {
		return contentView;
	}
}
