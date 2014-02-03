package edu.kit.iks.CryptographicsLib;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

/**
 * Abstract Controller
 * 
 * @author Christian Dreher
 */
abstract public class AbstractController {

	/**
	 * The view of the controller
	 */
	protected JComponent view = null;

	/**
	 * The parent controller
	 */
	protected AbstractController parentController = null;

	/**
	 * List of all child controllers. A view controller can have many child view
	 * controllers. The view of a child view controller should always be
	 * visible, so this is not a list of possible child controllers but rather a
	 * list of all currently used child controllers!
	 */
	protected List<AbstractController> childControllers = new ArrayList<AbstractController>();

	/**
	 * Adds a child controller
	 * 
	 * @param childController
	 *            Child controller to be added
	 */
	public void addChildController(AbstractController childController) {
		childController.parentController = this;
		childControllers.add(childController);
	}

	/**
	 * Removes a child controller
	 * 
	 * @param childController
	 *            Controller to be removed
	 */
	public void removeChildController(AbstractController childController) {

	}

	/**
	 * Loads the view of this controller
	 */
	abstract public void loadView();

	/**
	 * Unloads the view of this controller
	 */
	abstract public void unloadView();

	/**
	 * Gets the view
	 * 
	 * @return The view of this controller
	 */
	public JComponent getView() {
		return this.view;
	}

	/**
	 * Gets the parent controller
	 * 
	 * @return The parent controller of this controller
	 */
	public AbstractController getParentController() {
		return this.parentController;
	}

	/**
	 * Gets the list of all child controllers
	 * 
	 * @return List of all child controllers
	 */
	public List<AbstractController> getChildControllers() {
		// TODO: return immutable copy
		return this.childControllers;
	}

	/**
	 * Checks whether the view is loaded or not
	 * 
	 * @return {true}, if the view is loaded, {false} if not
	 */
	public boolean viewIsLoaded() {
		return (this.view != null);
	}
	
}
