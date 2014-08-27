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

import javax.swing.JLabel;

import edu.kit.iks.cryptographicslib.common.view.partial.PopoverView;

/**
 * Objects of this class represent the popover when
 * the user clicks on the help button.
 * 
 * @author Christian Dreher
 */
public class HelpPopoverView extends PopoverView {
	
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -8140547473696504022L;

	/**
	 * Text to be displayed.
	 */
	private String helpText;
	
	/**
	 * Label to actually display the text on the UI.
	 */
	private JLabel helpTextLabel;
	
	/**
	 * Constructor of the help popover.
	 * 
	 * @param helpText the help text
	 */
	public HelpPopoverView(String helpText) {
		super();
		
		this.helpText = helpText;
		this.helpTextLabel = new JLabel("<html><div style=\"width:200px;\">" + helpText + "</div></html>");
		this.getContentView().add(this.helpTextLabel);
	}
	
	/**
	 * Returns the help text.
	 * 
	 * @return the help text
	 */
	public String getHelpText() {
		return helpText;
	}
	
	/**
	 * Returns the help text label.
	 * 
	 * @return the help text label
	 */
	public JLabel getHelpTextLabel() {
		return helpTextLabel;
	}
}
