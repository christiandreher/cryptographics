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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 * @author Matthias Jaenicke.
 * 
 * A horizontal strip of all letters from A to Z.
 * Beneath it is matched by a corresponding strip of the numbers from 1 to 26.
 */
public class AlphabetStripView extends JPanel {
	
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 2443142838200011020L;
	
	private JLabel[] chars;
	private JLabel[] nums;
	
	private static final Border normalBorder = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.lightGray, Color.gray);
//	private static final Border normalBorder = BorderFactory.createLineBorder(Color.black);
	private static final Border highlightBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.lightGray.darker(), Color.gray.darker());
	
	/**
	 * Generates the alphabet strip with default dimension
	 * which is (26 * 30) x (2 * 25)
	 */
	public AlphabetStripView() {
		super();
		generateStrip(30, 25);
	}
	
	/**
	 * Generates the alphabet strip with given parameters
	 * (26 * width) x (2 * height)
	 * @param width Width of single cell
	 * @param height Height of single cell
	 */
	public AlphabetStripView(int width, int height) {
		super();
		generateStrip(width, height);
	}

	private void generateStrip(int width, int height) {

		// A layout with 2 rows and 26 columns. The desired form is simply 
		// achieved by first putting the alphabet and then the numbers in. 
		GridLayout layout = new GridLayout(2, 26);
		this.setLayout(layout);
		
		// Puts the 26 characters of the alphabet in the first row
		int asciiA = 65;
		chars = new JLabel[26];
		for (int i = 0; i < 26; i++) {
			chars[i] = new JLabel(String.valueOf((char)(i+asciiA)));
//			chars[i].setBorder(BorderFactory.createLineBorder(Color.black));
			chars[i].setBorder(normalBorder);
			chars[i].setVerticalAlignment(JLabel.CENTER);
			chars[i].setHorizontalAlignment(JLabel.CENTER);
			this.add(chars[i]);
		}
		
		// Puts the numbers from 1 to 26 in the second row
		nums = new JLabel[26];
		for (int i = 0; i < 26; i++) {
			nums[i] = new JLabel("" + (i+1));
//			nums[i].setBorder(BorderFactory.createLineBorder(Color.black));
			nums[i].setBorder(normalBorder);
			nums[i].setVerticalAlignment(JLabel.CENTER);
			nums[i].setHorizontalAlignment(JLabel.CENTER);
			this.add(nums[i]);
		}
		
		Dimension d = new Dimension(width * 26, height * 2);
		this.setPreferredSize(d);
	}
	
	/**
	 * Highlights the char and its number with the given key
	 * @param key number between 0 and 25
	 */
	public void highlight(int key) {
		
		assert(key >= 0 && key < 26);
		chars[key].setBorder(highlightBorder);
//		chars[key].setBackground(Color.yellow);
		chars[key].setForeground(Color.red);
		nums[key].setBorder(highlightBorder);
//		nums[key].setBackground(Color.yellow);
		nums[key].setForeground(Color.red);
		
		this.repaint();
	}
	
	/**
	 * Reverts highlighting of the char and its number with the given key
	 * @param key number between 0 and 25
	 */
	public void unHighlight(int key) {

		assert(key >= 0 && key < 26);
		chars[key].setBorder(normalBorder);
//		chars[key].setBackground(Color.yellow);
		chars[key].setForeground(Color.black);
		nums[key].setBorder(normalBorder);
//		nums[key].setBackground(Color.yellow);
		nums[key].setForeground(Color.black);
		
		this.repaint();
	}
	
	public void unHighlightAll() {
		for (int i = 0; i < chars.length; i++) {
			unHighlight(i);
		}
	}
}
