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

package edu.kit.iks.cryptographicslib.framework.view;

import java.awt.event.ActionListener;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.cryptographicslib.util.Configuration;

/**
 * @author Christian Dreher <uaeef@student.kit.edu>
 *
 */
public abstract class AbstractView extends JPanel {
	
	/**
	 * Serial version UID.
	 */
	protected static final long serialVersionUID = -8541837612624248511L;

	/**
	 * Localization instance.
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(AbstractView.class);
	
	/**
	 * Default help text.
	 */
	private String noHelp = AbstractView.i18n.tr("No help can be offered at this moment.");
	
	/**
	 * Data structure to hold key value pairs.
	 * 
	 * @author Christian Dreher <uaeef@student.kit.edu>
	 */
	private class Variable {
		public String key;
		public String value;
	}
	
	/**
	 * List of all view variables.
	 */
	protected ArrayList<Variable> variables = new ArrayList<>();
	
	/**
	 * Action listener to delegate actions to.
	 */
	protected ActionListener globalActionListener;
	
	/**
	 * Constructor initializing the view with variables.
	 * 
	 * @param variables All variables
	 */
	public AbstractView(List<SimpleEntry<String, String>> variables) {
		// Default help text
		this.setVariable("helpText", this.noHelp);
		
		if (variables != null) {
			for (SimpleEntry<String, String> variable : variables) {
				this.setVariable(variable.getKey(), variable.getValue());
			}
		}
	}

	/**
	 * Gets the variable helpText. By default, noHelp will be returned.
	 * 
	 * @return Current help text
	 */
	public String getHelp() {
		return this.getVariableValue("helpText");
	}
	
	/**
	 * Sets the global ActionListener.
	 * 
	 * @param al ActionListener instance
	 */
	public void setGlobalActionListener(ActionListener al) {
		this.globalActionListener = al;
	}

	/**
	 * Sets a variable with given key and value to the view.
	 *  
	 * @param key Key of the variable
	 * @param value Value of the variable
	 */
	public void setVariable(String key, String value) {
		Variable variable = this.getVariable(key);
		
		if (variable == null) {
			variable = new Variable();
			variable.key = key;
			
			this.variables.add(variable);
		}
		
		variable.value = value;
	}

	/**
	 * Gets a variable by key.
	 * 
	 * @param key Key of the variable
	 * 
	 * @return Variable, or null if variable was not found
	 */
	private Variable getVariable(String key) {
		for (Variable var : this.variables) {
			if (var.key.equals(key)) {
				return var;
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the value of a variable by its key.
	 * 
	 * @param key Key of the variable
	 * 
	 * @return Value of the variable
	 */
	protected String getVariableValue(String key) {
		Variable variable = this.getVariable(key);
		
		if (variable == null) {
			throw new RuntimeException("Variable with name \"" + key + "\" does not exist.");
		}
		
		return variable.value;
	}
}
