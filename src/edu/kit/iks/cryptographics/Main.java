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

package edu.kit.iks.cryptographics;

import javax.swing.SwingUtilities;

import edu.kit.iks.cryptographics.main.controller.MainController;
import edu.kit.iks.cryptographicslib.Configuration;
import edu.kit.iks.cryptographicslib.Logger;

/**
 * Main Class containing the main()-method
 * 
 * @author Christian Dreher
 */
public class Main {
	/**
	 * Main method (hook for Java to start from)
	 * 
	 * @param args  Arguments
	 */
	public static void main(String[] args) {
		// Configure logger.
		if (Configuration.getInstance().isDebugModeEnabled()) {
			Logger.setDebugMode();
		}
		
		Logger.log("Cryptographics launched");
		Logger.debug("Main", "main", "Debugger running");
		
		// Run app on the AWT event queue.
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainController mainController = new MainController();
				mainController.loadView();
				mainController.presentStartAction();
			}
		});
	}
}
