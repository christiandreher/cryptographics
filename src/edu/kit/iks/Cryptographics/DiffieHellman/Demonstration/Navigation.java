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

package edu.kit.iks.Cryptographics.DiffieHellman.Demonstration;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Navigation extends JPanel {

	JButton back, forward;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8585689448627282057L;
	
	public Navigation(String forward) {
		this.forward = new JButton(forward);
		this.setLayout(new BorderLayout());
		this.add(this.forward, BorderLayout.EAST);
	}
	
	public Navigation(String back, String forward) {
		this.forward = new JButton(forward);
		this.back = new JButton(back);
		this.setLayout(new BorderLayout());
		this.add(this.back, BorderLayout.WEST);
		this.add(this.forward, BorderLayout.EAST);
	}
	
	public JButton getBack() {
		return this.back;
	}
	
	public JButton getForward() {
		return this.forward;
	}

}
