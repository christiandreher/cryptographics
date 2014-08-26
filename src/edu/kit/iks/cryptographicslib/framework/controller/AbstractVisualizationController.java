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

package edu.kit.iks.cryptographicslib.framework.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import edu.kit.iks.cryptographics.main.controller.VisualizationContainerController;
import edu.kit.iks.cryptographicslib.framework.model.AbstractVisualizationInfo;

/**
 * Abstract visualization controller specialized for
 * the needs of a procedure to visualize its contents.
 * 
 * @author Christian Dreher
 */
public abstract class AbstractVisualizationController extends AbstractController
	implements ActionListener {

	/**
	 * Helper class to easier form key value pairs for view variables.
	 * 
	 * @author Christian Dreher <uaeef@student.kit.edu>
	 */
	protected class VariableHelper {
		private List<SimpleEntry<String, String>> variables = new ArrayList<>();
		
		public VariableHelper() {
			
		}
		
		public void add(String key, String value) {
			this.variables.add(new SimpleEntry<>(key, value));
		}
		
		public List<SimpleEntry<String, String>> toList() {
			return this.variables;
		}
	}
	
	/**
	 * {VisualizationInfo}-object holding all metadata of the procedure.
	 */
	private AbstractVisualizationInfo visualizationInfo;
	
	/**
	 * Constructor initializing a new instance of AbstractVisualizationController 
	 * with given {visualizationInfo}.
	 * 
	 * @param visualizationInfo {VisualizationInfo}-object holding 
	 * 		metadata of the procedure
	 */
	public AbstractVisualizationController(AbstractVisualizationInfo visualizationInfo) {
		this.visualizationInfo = visualizationInfo;
	}
	
	/**
	 * Gets the {VisualizationInfo}-object of the visualization.
	 * 
	 * @return{VisualizationInfo}-object holding metadata of the procedure
	 */
	public AbstractVisualizationInfo getVisualizationInfo() {
		return this.visualizationInfo;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String callerId = ((JComponent) e.getSource()).getName();
		
		if (!this.routeAction(callerId)) {
			this.routeNextBackAction(callerId);
		}
	}
	
	public boolean routeNextBackAction(String callerId) {
		switch (callerId) {
			case "next":
				this.defaultNextAction();
				break;
			case "back":
				this.defaultBackAction();
				break;
			default:
				return false;
		}
		
		((VisualizationContainerController) this.getParentController()).dismissHelpPopover();
		
		return true;
	}
	
	public abstract boolean routeAction(String callerId);
	
	/**
	 * Gets the currently relevant help text.
	 * 
	 * @return help text
	 */
	abstract public String helpAction();
	
	protected final void defaultNextAction() {
		((VisualizationContainerController) this.getParentController()).presentNextVisualizationController();
	}
	
	protected final void defaultBackAction() {
		((VisualizationContainerController) this.getParentController()).presentPreviousVisualizationController();
	}
}
