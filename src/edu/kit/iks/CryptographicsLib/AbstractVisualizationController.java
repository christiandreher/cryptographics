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

/**
 * Abstract visualisization controller specialized for
 * the needs of a procedure to visualize its contents
 * 
 * @author Christian Dreher
 */
public abstract class AbstractVisualizationController extends AbstractController {
	
	/**
	 * {VisualizationInfo}-object holding all metadata of the procedure
	 */
	private AbstractVisualizationInfo visualizationInfo;
	
	/**
	 * Constructor initializing a new instance of AbstractVisualizationController 
	 * with given {visualizationInfo}
	 * 
	 * @param visualizationInfo {VisualizationInfo}-object holding 
	 * 		metadata of the procedure
	 */
	public AbstractVisualizationController(AbstractVisualizationInfo visualizationInfo) {
		this.visualizationInfo = visualizationInfo;
	}
	
	/**
	 * Gets the {VisualizationInfo}-object of the visualization
	 * 
	 * @return{VisualizationInfo}-object holding metadata of the procedure
	 */
	public AbstractVisualizationInfo getVisualizationInfo() {
		return this.visualizationInfo;
	}
	
	/**
	 * Gets the currently relevant help text
	 * 
	 * @return help text
	 */
	abstract public String getHelp();
}
