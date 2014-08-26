/**
 * Copyright (c) 2014 Christian Dreher <uaeef@student.kit.edu>
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
package edu.kit.iks.cryptographicslib.framework.controller;

import java.awt.event.ActionEvent;
import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;

import javax.swing.JComponent;

import edu.kit.iks.cryptographics.main.controller.VisualizationContainerController;
import edu.kit.iks.cryptographicslib.framework.model.AbstractVisualizationInfo;
import edu.kit.iks.cryptographicslib.framework.view.AbstractVisualizationView;
import edu.kit.iks.cryptographicslib.framework.view.partial.AbstractPartialView;

/**
 * @author Christian Dreher <uaeef@student.kit.edu>
 *
 */
public abstract class AbstractSteppableVisualizationController extends AbstractVisualizationController {

	/**
	 * Helper class to easily define a running order of partial views.
	 * 
	 * @author Christian Dreher <uaeef@student.kit.edu>
	 */
	protected class RunningOrderHelper {
		
		LinkedList<SimpleEntry<Integer, AbstractPartialView>> runningOrder = new LinkedList<>();
		
		public RunningOrderHelper() {

		}

		public void enqueue(AbstractPartialView view) {
			this.enqueue(0, view);
		}
		
		public void enqueue(int index, AbstractPartialView view) {
			this.runningOrder.add(new SimpleEntry<>(new Integer(index), view));
		}
		
		public SimpleEntry<Integer, AbstractPartialView> getNext() {
			// poll(), because it returns null when the queue is empty.
			// remove() or similar throw a NoSuchElementException
			return this.runningOrder.poll();
		}
		
		public int size() {
			return this.runningOrder.size();
		}
	}
	
	/**
	 * Helper to keep track of the running order of the partial views.
	 */
	private RunningOrderHelper runningOrderHelper;
	
	/**
	 * Current step.
	 */
	private int currentStep = 0;
	
	/**
	 * @param visualizationInfo
	 */
	public AbstractSteppableVisualizationController(AbstractVisualizationInfo visualizationInfo) {
		super(visualizationInfo);
	}
	
	/**
	 * Overridden actionPerformed to also react on step button action events.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String callerId = ((JComponent) e.getSource()).getName();
		
		if (!this.routeAction(callerId)) {
			if (!this.routeNextBackAction(callerId)) {
				if (callerId.equals("step")) {
					this.defaultStepAction();
				}
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.kit.iks.cryptographicslib.controller.AbstractController#loadView()
	 */
	@Override
	public final void loadView() {
		this.runningOrderHelper = new RunningOrderHelper();
		this.loadView(this.runningOrderHelper);
		
		if (this.runningOrderHelper.size() == 0) {
			throw new RuntimeException("RunningOrderHelper was not populated with views to display. "
					+ "You need to call roh.enqueue(YourView); to define a running order for your "
					+ "views which should be steppable. If you don\'t need steppable views, use "
					+ "AbstractVisualizationController instead.");
		}
		
		this.indexAction();
	}
	
	/**
	 * Will be called as soon as the view needs to get loaded.
	 * Since this is a steppable visualization controller, a
	 * running order for the steps are needed. This should be done
	 * by using the passed RunningOrderHelper object
	 * 
	 * @param roh RunningOrderHelper
	 */
	public abstract void loadView(RunningOrderHelper roh);
	
	/**
	 * Gets the current step.
	 * 
	 * @return Integer representation of current step
	 */
	protected int getCurrentStep() {
		return this.currentStep;
	}
	
	/**
	 * This action will automatically be called after loadView() was executed.
	 * It will load the first partial view in the running order. Therefore, if this
	 * method needs to be overridden for special purposes, it is recommended to
	 * call super.indexAction() in the end to load the first partial view, as 
	 * long as this behavior is desired.
	 */
	protected void indexAction() {
		this.defaultStepAction();
	}
	
	/**
	 * Presents the next step by loading the partial view first
	 * enqueued in the running order.
	 */
	protected final void defaultStepAction() {
		SimpleEntry<Integer, AbstractPartialView> step = this.runningOrderHelper.getNext();
		
		if (step == null) {
			((VisualizationContainerController) this.parentController)
				.presentNextVisualizationController();
		} else {
			this.currentStep = step.getKey();
			((AbstractVisualizationView) this.view).setContent(step.getValue());
		}
	}	
}
