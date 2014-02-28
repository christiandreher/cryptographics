package edu.kit.iks.CryptographicsLib;

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
import javax.swing.JTextField;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xnap.commons.i18n.I18n;

/**
 * A displayable keyboard for character input per mouse click or touchscreen. Contains only
 * the basic 26 capital characters, Backspace and Enter.
 * 
 * @author Matthias Jaenicke
 * @author Christian Dreher
 */
public class KeyboardView extends JPanel implements ActionListener {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -1797509729654756462L;
	
	/**
	 * Resources of the keyboard
	 * (Icons for enter and backspace)
	 */
	private Element resources;
	
	/**
	 * When character mode is used, the buttons pressed will replace
	 * the text in the given input field
	 */
	public static final int CHAR_MODE = 0;
	
	/**
	 * When string mode is used, the buttons pressed will append
	 * the text in the given input field
	 */
	public static final int STRING_MODE = 1;
	
	/**
	 * The input mode set by the constructor
	 */
	private int inputMode;

	/**
	 * The text field passed through the constructor
	 */
	private JTextField textField;

	/**
	 * The matrix of all buttons that this view paints
	 */
	private JButton[][] keys;

	private static I18n i18n = Configuration.getInstance().getI18n(KeyboardView.class);
	
	/**
	 * Constructor initializing a new instance of keyboard view
	 * by passed text field (the textField which should be manipulated
	 * by this keyboard) and the mode. The mode can either be the
	 * KeyboardView.CHAR_MODE, in which the text in the textField will
	 * always be replaced by the button pressed, or KeyboardView.STRING_MODE,
	 * in which the button pressed will only be appended to the text in the
	 * textField
	 * 
	 * @param textField Text field to be manipulated by this keyboard
	 * @param mode The input mode (Either CHAR_MODE or STRING_MODE)
	 */
	public KeyboardView(JTextField textField, int mode) {
		super();
		
		this.initResources();

		this.textField = textField;
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

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton trigger = (JButton) e.getSource();
		String buttonLabel = trigger.getText();
		String buttonName = trigger.getName();
		
		if (buttonName.equals("button-enter")) {
			Logger.d("KeyboardView", "actionPerformed", "Enter pressed");
			this.textField.getActionListeners()[0].actionPerformed(e);
		} else if (buttonName.equals("button-backspace")) {
			Logger.d("KeyboardView", "actionPerformed", "Backspace pressed");
			String currentText = this.textField.getText();
			
			if (currentText.length() > 1) {
				String trimmedText = currentText.substring(0, currentText.length() - 1);
				
				this.textField.setText(trimmedText);
			} else {
				this.textField.setText("");
			}
		} else if (buttonName.equals("button-key")) {
			Logger.d("KeyboardView", "actionPerformed", "Key pressed");
			
			if (this.inputMode == KeyboardView.STRING_MODE) {
				String currentText = this.textField.getText();
				String newText = currentText + buttonLabel;
				
				this.textField.setText(newText);
			} else if (this.inputMode == KeyboardView.CHAR_MODE) {
				this.textField.setText(buttonLabel);
				
			}
		}
		
	}
	
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
			Logger.e(e);
		}
	}
	
	private void initKeyboardButtons() {
		JButton[][] keysInit = {
			// First row
			{kf("Q"), kf("W"), kf("E"),	kf("R"), kf("T"), kf(i18n.tr("keyboard-y")), kf("U"), 
				kf("I"), kf("O"), kf("P"), kf("Back", "bs")},
			
			// Second row
			{kf("A"), kf("S"), kf("D"), kf("F"), kf("G"), kf("H"), kf("J"), 
				kf("K"), kf("L"), kf("Enter", "e")},
				
			// Third row
			{kf(i18n.tr("keyboard-z")), kf("X"), kf("C"), kf("V"), kf("B"), kf("N"), kf("M")}
		};
		
		this.keys = keysInit;
	}
	
	/**
	 * Creates a new JButton instance with given String as label.
	 * kf stands for "Key factory" and is a factory method to
	 * instantiate new buttons more convenient
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
	 * @param name (bs|e|?), according to the button which should be created
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
	 * Loads an image icon from given path
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
        	Logger.e(e);
        }
    	
    	return image;
	}
}
