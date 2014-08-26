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
package edu.kit.iks.cryptographicslib.framework.view.partial;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import edu.kit.iks.cryptographicslib.common.view.partial.ImageView;
import edu.kit.iks.cryptographicslib.framework.view.AbstractView;
import edu.kit.iks.cryptographicslib.util.Utility;

/**
 * @author Christian Dreher <uaeef@student.kit.edu>
 *
 */
public abstract class AbstractPartialView extends AbstractView {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -1106719043723627842L;

	private ArrayList<JComponent> elements = new ArrayList<>();
	
	private GridBagConstraints gbc = new GridBagConstraints();
	
	/**
	 * @param variables
	 */
	public AbstractPartialView(List<SimpleEntry<String, String>> variables) {
		super(variables);
		
		this.setLayout(new GridBagLayout());
		this.initGridBagConstraints();
	}
	
	/**
	 * Method to initialize the partial view. Necessary, since maybe not all partial views
	 * are needed, so relying on this method to initialize a partial view when it is needed
	 * results in better resource utilization. 
	 */
	public abstract void preparePartialView();
	
	/**
	 * Adds an element to the content area.
	 * 
	 * @param element Element to add
	 * @return ID to identify the object for modifying or removing it
	 */
	public int addElement(JComponent element) {
		this.elements.add(this.gbc.gridy, element);
		this.add(element, this.gbc);
		
		return this.gbc.gridy++;
	}
	
	/**
	 * Adds a text to the content area.
	 * 
	 * @param text Text to add to content
	 * @return ID to identify the object for modifying or removing it
	 */
	public int addText(String text) {
		JTextPane jtext = new JTextPane();
		jtext.setText(text);
		jtext.setEditable(false);
		jtext.setPreferredSize(new Dimension(800, 80));
		
		StyledDocument doc = jtext.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		return this.addElement(jtext);
	}
	
	/**
	 * Adds a partial view to the content.
	 * 
	 * @param partialView Partial view to add
	 * @return ID to identify the object for modifying or removing it
	 */
	public int addPartialView(JComponent partialView) {
		return this.addElement(partialView);
	}
	
	/**
	 * Adds an image as ImageView to the content by its resource ID.
	 * 
	 * @param resourceId Resource ID defined in resources.xml
	 * @returnID to identify the object for modifying or removing it
	 */
	public int addImageFromResource(String resourceId) {
		ImageView imageFromResource = Utility.getImageViewFromResourceId(resourceId);
		
		return this.addPartialView(imageFromResource);
	}
	
	public JComponent getElement(int index) {
		return this.elements.get(index);
	}
	
	private void initGridBagConstraints() {
		this.gbc.gridx = 0;
		this.gbc.gridy = 0;
		this.gbc.gridwidth = 1;
		this.gbc.anchor = GridBagConstraints.CENTER;
		this.gbc.fill = GridBagConstraints.NONE;
	}
}
