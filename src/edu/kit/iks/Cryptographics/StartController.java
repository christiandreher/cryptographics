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
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import edu.kit.iks.CryptographicsLib.AbstractController;
import edu.kit.iks.CryptographicsLib.AbstractVisualizationInfo;
import edu.kit.iks.CryptographicsLib.ImageView;
import edu.kit.iks.CryptographicsLib.Logger;
import edu.kit.iks.CryptographicsLib.MouseClickListener;
import edu.kit.iks.CryptographicsLib.VisualizationButton;
import edu.kit.iks.CryptographicsLib.VisualizationInfoLoader;

/**
 * An instance of this class is the start controller showing teasers
 * 
 * @author Christian Dreher
 */
public class StartController extends AbstractController {

	/**
	 * An instance of the popover view
	 */
	private TimelinePopoverView popoverView;

	/**
	 * An instance of the timeline view
	 */
	private TimelineView timelineView;

	/**
	 * An instance of the welcome view
	 */
	private WelcomeView welcomeView;

	/**
	 * A list of all VisualizationInfo-objects
	 */
	private List<AbstractVisualizationInfo> visualizationInfos;

	/**
	 * Root element from the xml for the resources.
	 */
	private Element startResources;

	/**
	 * Constructor initializing a new instance of {StartController}
	 */
	public StartController() {
		this.loadAllVisualizationInfos();
	}

	/**
	 * Loads the view
	 */
	@Override
	public void loadView() {
		this.view = new JPanel(new GridBagLayout());
		this.view.setName("start-controller-view");

		SAXBuilder saxBuilder = new SAXBuilder();

		// obtain file object
		InputStream is = this.getClass().getResourceAsStream(
				"/start/startResources.xml");

		try {
			// converted file to document object
			Document document = saxBuilder.build(is);

			// get root node from xml
			this.startResources = document.getRootElement();
		} catch (JDOMException | IOException e) {
			Logger.error(e);
		}

		// Add welcome view and its layout
		GridBagConstraints welcomeViewLayout = new GridBagConstraints();
		welcomeViewLayout.fill = GridBagConstraints.HORIZONTAL;
		welcomeViewLayout.gridy = 0;
		welcomeViewLayout.weighty = 0.95f;
		this.welcomeView = new WelcomeView();
		this.view.add(this.welcomeView, welcomeViewLayout);
		
		String path = startResources.getChild("welcomeImage").getAttributeValue("path");

		GridBagConstraints imgConstraint = new GridBagConstraints();
		imgConstraint.gridx = 0;
		imgConstraint.gridy = 1;
		imgConstraint.insets = new Insets(0, 0, 50, 0);
		ImageView imageToSet = new ImageView(path);
		this.view.add(imageToSet, imgConstraint);

		// Add timeline view and its layout
		GridBagConstraints timelineViewLayout = new GridBagConstraints();
		timelineViewLayout.fill = GridBagConstraints.HORIZONTAL;
		timelineViewLayout.weightx = 1.0f;
		timelineViewLayout.weighty = 0.05f;
		timelineViewLayout.gridy = 2;
		this.timelineView = new TimelineView(visualizationInfos);
		this.view.add(this.timelineView, timelineViewLayout);

		// Add event handlers
		for (VisualizationButton button : this.timelineView.getButtons()) {
			button.addMouseListener(new MouseClickListener() {
				@Override
				public void clicked(MouseEvent event) {
					VisualizationButton button = (VisualizationButton) event
							.getSource();
					presentPopoverAction(button.getVisualizationInfo(), button);
				}
			});
		}

		this.view.validate();
	}

	/**
	 * Unloads the view by setting all object references to null
	 */
	@Override
	public void unloadView() {
		this.view.removeAll();
		this.view.revalidate();

		if (this.popoverView != null) {
			this.dismissPopoverAction();
		}
		this.popoverView = null;
		this.welcomeView = null;
		this.timelineView = null;
		this.view = null;
	}

	/**
	 * Loads the view for the popover which gives the user all needed informations about the cipher
	 * he clicked.
	 * 
	 * @param vsInfo
	 *            Information about the cipher.
	 */
	private void loadPopoverView(AbstractVisualizationInfo vsInfo) {
		if (this.popoverView != null) {
			this.dismissPopoverAction();
		}

		this.popoverView = new TimelinePopoverView(vsInfo);
	}

	/**
	 * Shows a popover with information from given {visualizationInfo}
	 * 
	 * @param visualizationInfo
	 *            Object of {VisualizationInfo} containing the metadata to display
	 */
	public void presentPopoverAction(
			AbstractVisualizationInfo visualizationInfo, JComponent sender) {
		loadPopoverView(visualizationInfo);
		this.popoverView.present(sender);
		this.popoverView.getStartButton().addMouseListener(
				new MouseClickListener() {
					@Override
					public void clicked(MouseEvent event) {
						VisualizationButton startButton = (VisualizationButton) event
								.getSource();
						startVisualizationAction(startButton
								.getVisualizationInfo());
					}
				});
		this.popoverView.getCloseButton().addMouseListener(
				new MouseClickListener() {
					@Override
					public void clicked(MouseEvent event) {
						dismissPopoverAction();
					}
				});
	}

	/**
	 * Dismisses the popover
	 */
	public void dismissPopoverAction() {
		this.popoverView.dismiss();
		this.popoverView = null;
	}

	/**
	 * Starts the visualization of a procedure from given {visualizationInfo}
	 * 
	 * @param visualizationInfo
	 *            Object of {VisualizationInfo} containing the data to instantiate related
	 *            controllers from
	 */
	public void startVisualizationAction(
			AbstractVisualizationInfo visualizationInfo) {
		this.dismissPopoverAction();
		MainController mainController = (MainController) this
				.getParentController();
		Logger.log("User chose " + visualizationInfo.getId());
		mainController.presentVisualizationAction(visualizationInfo);
	}

	/**
	 * Helper method to initialize {_visualizationInfos}
	 */
	private void loadAllVisualizationInfos() {
		this.visualizationInfos = VisualizationInfoLoader
				.loadAllVisualizationInfos();
	}
}
