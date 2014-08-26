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

package edu.kit.iks.cryptographics.main.view.partial;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.cryptographicslib.common.view.partial.PopoverView;
import edu.kit.iks.cryptographicslib.framework.model.AbstractVisualizationInfo;
import edu.kit.iks.cryptographicslib.util.Configuration;

/**
 * Objects of this class represent the popover when
 * the user clicks on a button in the timeline.
 * 
 * @author Christian Dreher
 */
public class TimelinePopoverView extends PopoverView {
	
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 1914359780570867605L;

	/**
     * {VisualizationInfo}-object holding metadata of the current visualization.
     */
	private AbstractVisualizationInfo visualizationInfo;
	
	/**
     * Label to display the name of the current visualization.
     */
	private JLabel nameLabel;

	/**
     * Label to display the current difficulty.
     */
	private JLabel difficultyLabel;

	/**
     * Label to display a description.
     */
	private JLabel descriptionLabel;

	/**
     * Button to start the currently displayed visualization.
     */
	private VisualizationButtonView startButton;
	
	/**
	 * Localization instance.
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(TimelinePopoverView.class);
	
	/**
	 * Constructor of the timeline popover.
	 * 
	 * @param visualizationInfo Informations about a cipher.
	 */
	public TimelinePopoverView(AbstractVisualizationInfo visualizationInfo) {
		super();
		
		this.visualizationInfo = visualizationInfo;
		
		// Configure content view.
		this.getContentView().setLayout(new GridBagLayout());
		
		// Create name label.
		GridBagConstraints nameConstraints = new GridBagConstraints();
		nameConstraints.gridx = 0;
		nameConstraints.gridy = 0;
		nameConstraints.gridwidth = 2;
		nameConstraints.insets = new Insets(0, 0, 0, 0);
		this.nameLabel = new JLabel("<html><h2>" + visualizationInfo.getName() + "</h2></html>");
		this.getContentView().add(this.nameLabel, nameConstraints);
		
		// Create difficulty label.
		GridBagConstraints difficultyConstraints = new GridBagConstraints();
		difficultyConstraints.gridx = 2;
		difficultyConstraints.gridy = 0;
		difficultyConstraints.anchor = GridBagConstraints.LINE_END;
		difficultyConstraints.insets = new Insets(0, 0, 0, 0);
		this.difficultyLabel = new JLabel(visualizationInfo.getHumanReadableDifficulty());
		this.difficultyLabel.setForeground(visualizationInfo.getDifficultyColor());
		this.getContentView().add(this.difficultyLabel, difficultyConstraints);
		
		// Create description label.
		GridBagConstraints descriptionConstraints = new GridBagConstraints();
		descriptionConstraints.gridx = 0;
		descriptionConstraints.gridy = 1;
		descriptionConstraints.gridwidth = 3;
		descriptionConstraints.insets = new Insets(20, 0, 20, 0);
		this.descriptionLabel = new JLabel("<html><div style=\"width:220px;\">" + visualizationInfo.getDescription() + "</div></html>");
		this.getContentView().add(this.descriptionLabel, descriptionConstraints);
		
		// Create start button
		GridBagConstraints startConstraints = new GridBagConstraints();
		startConstraints.gridx = 0;
		startConstraints.gridy = 2;
		startConstraints.gridwidth = 3;
		startConstraints.anchor = GridBagConstraints.ABOVE_BASELINE;
		this.startButton = new VisualizationButtonView(visualizationInfo);
		this.startButton.setText(i18n.tr("Start"));
		this.getContentView().add(this.startButton, startConstraints);
		
		this.getContentView().validate();
	}

	/**
	 * Returns the StartButton.
	 * 
	 * @return the startButton button to return.
	 */
	public VisualizationButtonView getStartButton() {
		return startButton;
	}
	
	/**
	 * Returns the visualization info.
	 * 
	 * @return the visualization info.
	 */
	public AbstractVisualizationInfo getVisualizationInfo() {
		return visualizationInfo;
	}
}
