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

package edu.kit.iks.cryptographicslib.common.view.partial;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import edu.kit.iks.cryptographicslib.util.Highlighter;

/**
 * @author Matthias Jaenicke.
 * 
 * A horizontal strip of all letters from A to Z.
 * Beneath it is matched by a corresponding strip of the numbers from 1 to 26.
 */
public class AlphabetStripView extends JPanel {
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 2443142838200011020L;
	
	private JPanel wrapper;
	
	private JLabel[] chars;
	private JLabel[] nums;
	
	/**
	 * Generates the alphabet strip with default dimension
	 * which is (26 * 30) x (2 * 25).
	 */
	public AlphabetStripView() {
		super(new GridBagLayout());
		generateStrip(40, 35);
	}

	private void generateStrip(int width, int height) {
		this.wrapper = new JPanel();
		
		// A layout with 2 rows and 26 columns. The desired form is simply 
		// achieved by first putting the alphabet and then the numbers in. 
		GridLayout layout = new GridLayout(2, 26);
		
		// 1 pixel margin
		layout.setHgap(1);
		layout.setVgap(1);
		
		this.wrapper.setLayout(layout);
		
		// Puts the 26 characters of the alphabet in the first row
		int asciiA = 65;
		chars = new JLabel[26];
		for (int i = 0; i < 26; i++) {
			chars[i] = new JLabel(String.valueOf((char) (i + asciiA)));
			chars[i].setVerticalAlignment(JLabel.CENTER);
			chars[i].setHorizontalAlignment(JLabel.CENTER);
			Highlighter.normal(chars[i]);
			
			this.wrapper.add(chars[i]);
		}
		
		// Puts the numbers from 1 to 26 in the second row
		nums = new JLabel[26];
		for (int i = 0; i < 26; i++) {
			nums[i] = new JLabel("" + (i+1));
			nums[i].setVerticalAlignment(JLabel.CENTER);
			nums[i].setHorizontalAlignment(JLabel.CENTER);
			Highlighter.normal(nums[i]);
			
			this.wrapper.add(nums[i]);
		}
		
		Dimension d = new Dimension(width * 26, height * 2);
		this.wrapper.setPreferredSize(d);
		this.add(this.wrapper);
	}
	
	public void highlightBlue(String letter) {
		for (int i = 0; i < this.chars.length; i++) {
			if (this.chars[i].getText().equals(letter)) {
				this.highlightBlueByIndex(i);
				return;
			}
		}
	}
	
	public void highlightBlueByIndex(int index) {
	    Highlighter.info(this.chars[index]);
	    Highlighter.info(this.nums[index]);
		
		this.repaint();
	}

	/**
	 * Reverts highlighting of the char and its number with the given key.
	 * 
	 * @param key number between 0 and 25
	 */
	public void unHighlight(int key) {
		assert (key >= 0 && key < 26);
		
		Highlighter.normal(chars[key]);
		Highlighter.normal(nums[key]);
		
		this.repaint();
	}
	
	public void unHighlightAll() {
		for (int i = 0; i < chars.length; i++) {
			unHighlight(i);
		}
	}
}
