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

package edu.kit.iks.CryptographicsLib;

import javax.swing.JButton;

/**
 * Objects of this class are a decorator for JButton, 
 * holding data about the procedure they represent on the
 * timeline
 * 
 * @author Christian Dreher
 */
public class VisualizationButton extends JButton {
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -1971747371012823882L;
	
	/**
	 * {VisualizationInfo}-object, holding metadata of the 
	 * procedure the button represents
	 */
	private AbstractVisualizationInfo visualizationInfo;
	
	/**
	 * Constructor initializing a new instance of {VisualizationButton} 
	 * with given {vInfo}
	 * 
	 * @param vInfo {VisualizationInfo}-object holding metadata for
	 * 		the button to represent
	 */
	public VisualizationButton(AbstractVisualizationInfo vInfo) {
		// For the synth look and feel it's important to pass a
		// name to identify an object for custom styling
		this.setName("visualizationButton");
		
		visualizationInfo = vInfo;
		this.setText(visualizationInfo.getName());
	}
	
	/**
	 * Gets the {VisualizationInfo}-object holding the metadata 
	 * 
	 * @return {VisualizationInfo}-object
	 */
	public AbstractVisualizationInfo getVisualizationInfo() {
		return visualizationInfo;
	}
}
