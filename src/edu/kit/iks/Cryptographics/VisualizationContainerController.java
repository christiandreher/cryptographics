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

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.Timer;

import edu.kit.iks.CryptographicsLib.AbstractController;
import edu.kit.iks.CryptographicsLib.AbstractVisualizationController;
import edu.kit.iks.CryptographicsLib.AbstractVisualizationInfo;
import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.Logger;
import edu.kit.iks.CryptographicsLib.MouseClickListener;

/**
 * An instance of this class is a wrapper for visualization controller to
 * outsource common UI elements like navigation buttons
 * 
 * @author Christian Dreher
 */
public class VisualizationContainerController extends AbstractController {

	/**
	 * Index of the current {VisualizationController} in {childControllers}-list
	 */
	private int currentVisualizationControllerIndex;

	/**
	 * List of all visualizationControllers. Behaves like a cache and will be
	 * filled on demand
	 */
	private AbstractVisualizationController[] visualizationControllers;

	/**
	 * {VisualizationInfo}-object holding metadata of the procuedure
	 */
	private AbstractVisualizationInfo visualizationInfo;

	/**
	 * Container to display the actual visualization of a procedure
	 */
	private VisualizationContainerView view;
	
	/**
	 * The timer used to detect an idle user. This is used to figure out when to
	 * present the idle popover, so it's step 1.
	 */
	private Timer idleDetectionTimer;
	
	/**
	 * The timer used to reset the state. This is step two of the idle detection.
	 */
	private Timer resetTimer;
	
	/**
	 * The global event listener used for detecting an idle user.
	 */
	private AWTEventListener idleDetectionListener;
	
	/**
	 * The popover presented to warn the user before going back to the main screen.
	 */
	private IdlePopoverView idlePopoverView;

	/**
	 * List of all child classes
	 */
	@SuppressWarnings("rawtypes")
	List<Class> childClasses;
	
	/**
	 * View of the popover wich shows help for the user.
	 */
	private HelpPopoverView helpPopoverView;

	/**
	 * Constructor initializing a new instance of
	 * {VisualizationContainerController}
	 * 
	 * @param visualizationInfo
	 *            Object of {VisualizationInfo} containing the data to
	 *            instantiate related controllers from
	 */
	public VisualizationContainerController(
			AbstractVisualizationInfo visualizationInfo) {
		this.visualizationInfo = visualizationInfo;
		this.childClasses = this.visualizationInfo.getControllerClasses();
		this.idleDetectionListener = new AWTEventListener() {
			@Override
			public void eventDispatched(AWTEvent e) {
				stopIdleDetectionTimer();
				if (idlePopoverView == null) {
					startIdleDetectionTimer();
				}
			}
		};
		this.startIdleDetectionTimer();

		// Create an array that can hold all visualization controllers.
		// The controller's will be instantiated on demand (lazily).
		this.visualizationControllers = new AbstractVisualizationController[this.childClasses
				.size()];
	}

	/**
	 * Gets the {VisualizationInfo}-object of the current visualization
	 * 
	 * @return {VizualizationInfo}-object of the current visualization
	 */
	public AbstractVisualizationInfo getVisualizationInfo() {
		return this.visualizationInfo;
	}

	/**
	 * Loads the view
	 */
	@Override
	public void loadView() {
		// Observe global mouse events.
		Toolkit.getDefaultToolkit().addAWTEventListener(this.idleDetectionListener, AWTEvent.MOUSE_MOTION_EVENT_MASK);
		
		this.view = new VisualizationContainerView();
		this.view.setName("visualization-container-controller-view");
		// Styling purpose
		this.view.setName("visualizationContainerController");
		this.view.getNameLabel().setText("<html><h1>" + this.getVisualizationInfo().getName() + "</h1></html>");
		
		this.view.getExitButton().addMouseListener(new MouseClickListener() {
			@Override
			public void clicked(MouseEvent e) {
				MainController mainController = (MainController) getParentController();
				getCurrentVisualizationController().unloadView();
				Logger.log("User went back to start screen");
				mainController.presentStartAction();
			}
		});
		this.view.getHelpButton().addMouseListener(new MouseClickListener() {
			@Override
			public void clicked(MouseEvent e) {
				presentHelpPopover();
			}
		});
	}

	/**
	 * Unloads the view
	 */
	@Override
	public void unloadView() {
		// Remove observer.
		Toolkit.getDefaultToolkit().removeAWTEventListener(this.idleDetectionListener);
		
		// Stop timers.
		this.stopIdleDetectionTimer();
		this.stopResetTimer();
		
		if (this.helpPopoverView != null) {
			this.dismissHelpPopover();
		}
		
		if (this.idlePopoverView != null) {
			this.dismissIdlePopover();
		}
		
		this.view.removeAll();
		this.view.revalidate();
		
		this.helpPopoverView = null;
		this.idlePopoverView = null;
		this.view = null;
	}

	/**
	 * Gets the view
	 */
	@Override
	public VisualizationContainerView getView() {
		return this.view;
	}
	
	/**
	 * Creates and presents a new idle popover. The idle popover is presented
	 * after the program things that the user is idle. It displays a count-down
	 * after which it resets itself.  
	 */
	public void presentIdlePopover() {
		if (this.helpPopoverView != null) {
			this.dismissHelpPopover();
		}
		if (this.idlePopoverView != null) {
			this.dismissIdlePopover();
		}
		
		// Create popover.
		this.idlePopoverView = new IdlePopoverView(Configuration.getInstance().getResetTimeout());
		this.idlePopoverView.present(this.getView().getExitButton());
		
		// Create mouse listeners for buttons.
		MouseClickListener listener = new MouseClickListener() {
			@Override
			public void clicked(MouseEvent e) {
				dismissIdlePopover();
				startIdleDetectionTimer();
			}
		};
		this.idlePopoverView.getCloseButton().addMouseListener(listener);
		this.idlePopoverView.getContinueButton().addMouseListener(listener);
		
		this.startResetTimer();
	}
	
	/**
	 * Dismisses the active idle popover.
	 */
	public void dismissIdlePopover() {
		this.idlePopoverView.dismiss();
		this.idlePopoverView = null;
		this.stopResetTimer();
	}
	
	/**
	 * Stops the reset timer.
	 */
	private void stopResetTimer() {
		if (this.resetTimer != null) {
			this.resetTimer.stop();
			this.resetTimer = null;
		}
	}
	
	/**
	 * Starts the reset timer. After the timer fires, the program
	 * will present the start controller.
	 */
	private void startResetTimer() {
		if (this.resetTimer == null) {
			int delay = Configuration.getInstance().getResetTimeout();
			this.resetTimer = new Timer(delay, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					stopResetTimer();
					
					MainController mainController = (MainController) getParentController();
					mainController.presentStartAction();
					Logger.log("Reset due to user inactivity");
				}
				
			});
			this.resetTimer.start();
		}
	}
	
	/**
	 * Stops an idle detection timer.
	 */
	private void stopIdleDetectionTimer() {
		if (this.idleDetectionTimer != null) {
			this.idleDetectionTimer.stop();
			this.idleDetectionTimer = null;
		}
	}
	
	/**
	 * Starts the idle detection timer. The idle detection fires if a user
	 * doesn't perform any input for a given period of time.
	 */
	private void startIdleDetectionTimer() {
		if (this.idleDetectionTimer == null) {
			// Create a new timer if the idle popover is currently not already active.
			int delay = Configuration.getInstance().getIdleTimeout();
			this.idleDetectionTimer = new Timer(delay, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					stopIdleDetectionTimer();
					presentIdlePopover();
				}
			});
			this.idleDetectionTimer.start();
		}
	}

	/**
	 * Shows a popover displaying given help.
	 * 
	 */
	public void presentHelpPopover() {
		if (this.helpPopoverView != null) {
			this.dismissHelpPopover();
		}
		if (this.idlePopoverView != null) {
			this.dismissIdlePopover();
		}
		
		String helpText = this.getCurrentVisualizationController().getHelp();
		if (helpText == null) {
			// Do not present help if no help is available.
			return;
		}
		
		this.helpPopoverView = new HelpPopoverView(helpText);
		this.helpPopoverView.getCloseButton().addMouseListener(new MouseClickListener() {
			@Override
			public void clicked(MouseEvent e) {
				dismissHelpPopover();
			}
		});
		this.helpPopoverView.present(this.getView().getHelpButton());
	}
	
	/**
	 * Function for unloading the helpView.
	 */
	public void dismissHelpPopover() {
		this.helpPopoverView.dismiss();
		this.helpPopoverView = null;
	}

	/**
	 * Sets the current visualizationController with given index
	 * 
	 * @param index
	 *            Index of the VisualizationController inside
	 *            {visualizationControllers}-array
	 */
	public void setCurrentVisualizationControllerIndex(int index) {
		AbstractVisualizationController oldController = this
				.getCurrentVisualizationController();
		if (oldController != null) {
			this.removeChildController(oldController);

			// Remove view
			this.getView().getContentView().remove(oldController.getView());
			oldController.unloadView();
		}

		// Load new controller if necessary.
		AbstractVisualizationController newController = this.visualizationControllers[index];
		if (newController == null) {
			newController = this.loadVisualizationController(index);
		}

		// Set new controller
		this.currentVisualizationControllerIndex = index;
		this.addChildController(newController);

		// Load new view
		newController.loadView();
		this.getView().getContentView().add(newController.getView(), BorderLayout.CENTER);
		this.getView().revalidate();
	}

	/**
	 * Gets the current {VisualizationController}
	 * 
	 * @return Current {VisualizationController}
	 */
	public AbstractVisualizationController getCurrentVisualizationController() {
		return this.visualizationControllers[this.currentVisualizationControllerIndex];
	}

	/**
	 * Checks, whether this controller has a next {VisualizationController} or
	 * not
	 * 
	 * @return {true}, if this controller has a next {VisualizationController},
	 *         {false} if not
	 */
	public boolean hasNextVisualizationController() {
		return (this.currentVisualizationControllerIndex < childClasses.size() - 1);
	}

	/**
	 * Presents the next {VisualizationController}
	 */
	public void presentNextVisualizationController() {
		this.setCurrentVisualizationControllerIndex(this.currentVisualizationControllerIndex + 1);
	}

	/**
	 * Checks, whether this controller has a previous {VisualizationController}
	 * or not
	 * 
	 * @return {true}, if this controller has a previous
	 *         {VisualizationController}, {false} if not
	 */
	public boolean hasPreviousVisualizationController() {
		return (this.currentVisualizationControllerIndex > 1);
	}

	/**
	 * Presents the previous {VisualizationController}
	 */
	public void presentPreviousVisualizationController() {
		this.setCurrentVisualizationControllerIndex(this.currentVisualizationControllerIndex - 1);
	}

	/**
	 * Loads the {VisualizationController} with given {index}
	 * 
	 * @param index
	 *            Index of the {VisualizationController} inside
	 *            {visualizationControllers}-array
	 * @return The just loaded {VisualizationController}
	 */
	private AbstractVisualizationController loadVisualizationController(
			int index) {
		Constructor<AbstractVisualizationController> controllerConstructor = null;
		@SuppressWarnings("unchecked")
		Class<AbstractVisualizationController> controllerClass = childClasses
				.get(index);
		AbstractVisualizationController controller = null;

		try {
			// visualizationinfo is now passed to the contructor.
			controllerConstructor = controllerClass
					.getConstructor(AbstractVisualizationInfo.class);
			controller = controllerConstructor
					.newInstance(this.visualizationInfo);
		} catch (InstantiationException | IllegalAccessException
				| NoSuchMethodException | SecurityException
				| IllegalArgumentException | InvocationTargetException e) {
			Logger.error(e);
		}

		this.visualizationControllers[index] = controller;
		return controller;
	}

	/**
	 * Presents the start controller
	 */
	public void presentStartController() {
		if (this.helpPopoverView != null) {
			this.dismissHelpPopover();
		}
		
		MainController mainController = (MainController) this
				.getParentController();
		mainController.presentStartAction();
	}
	
	/**
	 * @return the helpView
	 */
	public HelpPopoverView getHelpPopoverView() {
		return helpPopoverView;
	}
}
