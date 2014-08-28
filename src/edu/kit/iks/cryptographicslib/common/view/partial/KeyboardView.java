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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xnap.commons.i18n.I18n;

import edu.kit.iks.cryptographicslib.framework.controller.AbstractVisualizationController;
import edu.kit.iks.cryptographicslib.util.Configuration;
import edu.kit.iks.cryptographicslib.util.Logger;

/**
 * A displayable keyboard for character input per mouse click or touchscreen. Contains only
 * the basic 26 capital characters, Backspace and Enter.
 * 
 * @author Matthias Jaenicke
 * @author Christian Dreher
 */
public class KeyboardView extends JPanel implements ActionListener {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -1797509729654756462L;
	
	/**
	 * Resources of the keyboard (Icons for enter and backspace).
	 */
	private Element resources;
	
	/**
	 * When character mode is used, the buttons pressed will replace
	 * the text in the given input field.
	 */
	public static final int CHAR_MODE = 0;
	
	/**
	 * When string mode is used, the buttons pressed will append
	 * the text in the given input field.
	 */
	public static final int STRING_MODE = 1;
	
	/**
	 * The input mode set by the constructor.
	 */
	private int inputMode;

	/**
	 * Name of the action which should be called when a button was pressed.
	 */
	private String actionName;
	
	/**
	 * Instance of the controller to delegate action calls to.
	 */
	private AbstractVisualizationController avc;
	
	/**
	 * The text field passed through the constructor.
	 */
	private String input = "";

	/**
	 * The matrix of all buttons that this view paints.
	 */
	private JButton[][] keys;

	/**
	 * Localization instance.
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(KeyboardView.class);
	
	/**
	 * Constructor initializing a new instance of keyboard view
	 * by passed event handler and mode. The mode can either be the
	 * KeyboardView.CHAR_MODE, in which the text in the textField will
	 * always be replaced by the button pressed, or KeyboardView.STRING_MODE,
	 * in which the button pressed will only be appended to the text in the
	 * textField.
	 * 
	 * @param avc AbstractVisualizationController instance
	 * @param mode The input mode (Either CHAR_MODE or STRING_MODE)
	 */
	public KeyboardView(AbstractVisualizationController avc, int mode) {
		super();
		
		this.initResources();

		this.avc = avc;
		this.inputMode = mode; 
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.initKeyboardButtons();
		
		Dimension keySize = new Dimension(45, 45);
		Insets keyMargin = new Insets(0, 5, 0, 5);
		Font keyFont = new Font("Arial", Font.BOLD, 14);
		
		for (int i = 0; i < this.keys.length; i++) {
			JPanel row = new JPanel(new FlowLayout());
			
			for (int j = 0; j < this.keys[i].length; j++) {
				if (this.keys[i][j] != null) {
					this.keys[i][j].setMargin(keyMargin);
					this.keys[i][j].setFont(keyFont);
					this.keys[i][j].setPreferredSize(keySize);
					this.keys[i][j].addActionListener(this);
					
					row.add(this.keys[i][j]);
				}
			}
			
			this.add(row);
		}

		//this.setSize(firstRow.getWidth(), firstRow.getHeight() * 3);
		this.setVisible(true);
	}

	public void setAction(String actionName) {
		this.actionName = actionName;
	}
	
	/**
	 * Method to be called on action performed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton trigger = (JButton) e.getSource();
		String buttonLabel = trigger.getText();
		String buttonName = trigger.getName();
		
		if (buttonName.equals("button-enter")) {
			Logger.debug("KeyboardView", "actionPerformed", "Enter pressed");
		} else if (buttonName.equals("button-backspace")) {
			Logger.debug("KeyboardView", "actionPerformed", "Backspace pressed");
			String currentText = this.input;
			
			if (currentText.length() > 1) {
				String trimmedText = currentText.substring(0, currentText.length() - 1);
				
				this.input = trimmedText;
			} else {
				this.input = "";
			}
		} else if (buttonName.equals("button-key")) {
			Logger.debug("KeyboardView", "actionPerformed", "Key pressed");
			
			if (this.inputMode == KeyboardView.STRING_MODE) {
				
				String currentText = this.input;
				String newText = currentText + buttonLabel;
				this.input = newText;
				
			} else if (this.inputMode == KeyboardView.CHAR_MODE) {

				this.input = buttonLabel;
			}
		}
		
		this.avc.routeAction(this.actionName);
	}
	
	public String getInput() {
		return this.input;
	}
	
	public void clearInput() {
		this.input = "";
	}
	
	/**
	 * Helper to init the resources.
	 */
	private void initResources() {
		SAXBuilder saxBuilder = new SAXBuilder();

		// obtain file object
		InputStream is = this.getClass().getResourceAsStream(
				"/icons/IconResources.xml");

		try {
			// converted file to document object
			Document document = saxBuilder.build(is);

			// get root node from xml
			this.resources = document.getRootElement().getChild("Keyboard");
		} catch (JDOMException | IOException e) {
			Logger.error(e);
		}
	}
	
	/**
	 * Helper to init the buttons.
	 */
	private void initKeyboardButtons() {
		JButton[][] keysInit = {
			// First row
			{kf("Q"), kf("W"), kf("E"),	kf("R"), kf("T"), kf(i18n.tr("keyboard-y")), kf("U"), 
				kf("I"), kf("O"), kf("P"), kf("Back", "bs")},
			
			// Second row
			{kf("A"), kf("S"), kf("D"), kf("F"), kf("G"), kf("H"), kf("J"), 
				kf("K"), kf("L") /*, kf("Enter", "e")*/},
				
			// Third row
			{kf(i18n.tr("keyboard-z")), kf("X"), kf("C"), kf("V"), kf("B"), kf("N"), kf("M")}
		};
		
		this.keys = keysInit;
	}
	
	/**
	 * Creates a new JButton instance with given String as label.
	 * kf stands for "Key factory" and is a factory method to
	 * instantiate new buttons more convenient.
	 * 
	 * @param label The label of the button
	 * 
	 * @return new Instance of JButton
	 */
	private JButton kf(String label) {
		return kf(label, "key");
	}
	
	/**
	 * Creates a new JButton instance as special purpose button.
	 * If given name is "bs" (BackSpace), a backspace button
	 * will be created. If given name is "e" (Enter), an enter
	 * button will be created. All other will result in a normal
	 * button representing a character
	 * 
	 * @param label The label of the button
	 * @param name (bs|e|key), according to the button which should be created
	 * 
	 * @return new Instance of JButton
	 */
	private JButton kf(String label, String name) {
		JButton button = null;
		
		if (name.equals("bs") && this.inputMode == KeyboardView.STRING_MODE) {
			ImageIcon backspace = this.loadIcon(this.resources
					.getChild("Backspace")
					.getAttributeValue("path"));
			
			button = new JButton(backspace);
			button.setName("button-backspace");
		} else if (name.equals("e") && this.inputMode == KeyboardView.STRING_MODE) {
			ImageIcon enter = this.loadIcon(this.resources
					.getChild("Enter")
					.getAttributeValue("path"));
			
			button = new JButton(enter);
			button.setName("button-enter");
		} else if (name.equals("key")) {
			button = new JButton(label);
			button.setName("button-key");
		}
		
		
		return button;
	}
	
	/**
	 * Loads an image icon from given path.
	 * 
	 * @param path path to image
	 * @return ImageIcon instance
	 */
	private ImageIcon loadIcon(String path) {
		ImageIcon image = null;
		
    	try {                
    		InputStream is = this.getClass().getResourceAsStream(path);
            image = new ImageIcon(ImageIO.read(is));
        } catch (IOException e) {
        	Logger.error(e);
        }
    	
    	return image;
	}
}
