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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * View of the information page
 * 
 * @author Matthias Plappert
 */
public class InformationView extends JPanel implements MouseListener {
	/**
	 * Possible scroll directions.
	 */
	private enum ScrollDirection {
	    NONE, UP, DOWN 
	};
	
	/**
	 * Resources.
	 */
	private Element resources;
	
	/**
	 * The delay between scroll events when a scroll button is pressed.
	 */
	private static final int scrollDelay = 50; 
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -8593552609491347799L;

	/**
	 * QR code for further information
	 */
	private Image qrCode;
	
	/**
	 * The HTML used to display the additional information in a web view.
	 */
	private String html;
	
	/**
	 * The content of the QR code.
	 */
	private String qrCodeContent;
	
	/**
	 * The QR code view.
	 */
	private ImageView qrCodeView;
	
	/**
	 * Displays the contents of the QR code. 
	 */
	private JLabel qrCodeLabel;
	
	/**
	 * The scrollable container of the web view.
	 */
	private JScrollPane webViewContainer;
	
	/**
	 * The web view.
	 */
	private JEditorPane webView;
	
	/**
	 * Scrolls the web view up.
	 */
	private JButton scrollUpButton;
	
	/**
	 * Scrolls the web view down.
	 */
	private JButton scrollDownButton;
	
	/**
	 * The current scroll direction.
	 */
	private ScrollDirection scrollDirection = ScrollDirection.NONE;
	
	/**
	 * This timer is used to periodically scroll up or down depending on the
	 * current value of scroll direction. This is used when pressing the up or
	 * down button.
	 */
	private Timer scrollTimer;
	
	/**
	 * Constructor initializing a new instance of {InformationView}
	 * with given {HTML} and {qrCode}
	 * 
	 * @param html The HTML used to display the additional informations
	 * @param qrCode QR code for further information
	 * @param url The content of the QR code
	 */
	public InformationView(String html, Image qrCode, String qrCodeContent) {
		this.qrCode = qrCode;
		this.html = html;
		this.qrCodeContent = qrCodeContent;
		
		this.initResources();
		
		// Initialize view.
		this.setLayout(new BorderLayout());
		this.loadWebViewComponents();
		this.loadQRCodeViewComponents();
		this.validate();
		
		// The JScrollPane returns invalid scroll information, resulting in an invalid
		// button state. Adding this slight delay fixes the problem.
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				updateButtonStates();
			}
		});
	}
	
	/**
	 * Helper to init the resources 
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
			this.resources = document.getRootElement().getChild("InformationView");
		} catch (JDOMException | IOException e) {
			Logger.error(e);
		}
	}
	
	/**
	 * Loads the web view and related components.
	 */
	private void loadWebViewComponents() {
		JPanel layoutContainer = new JPanel(new BorderLayout());
		layoutContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		
		this.loadWebView();
        layoutContainer.add(this.webViewContainer, BorderLayout.CENTER);
        
        this.loadScrollButtons();
        JPanel buttonLayoutContainer = new JPanel(new BorderLayout());
        buttonLayoutContainer.add(this.scrollUpButton, BorderLayout.NORTH);
        buttonLayoutContainer.add(this.scrollDownButton, BorderLayout.SOUTH);
        buttonLayoutContainer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        layoutContainer.add(buttonLayoutContainer, BorderLayout.EAST);
        
        // Valdiate and add.
        layoutContainer.validate();
        this.add(layoutContainer, BorderLayout.CENTER);
    }
	
	private void loadWebView() {
		// Editor pane used as a web view.
		this.webView = new JEditorPane();
		this.webView.setEditable(false);
		this.webView.setContentType("text/html");
		this.webView.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		try {
			this.webView.setText(this.getHtml());
		} catch(Exception e) {
			Logger.error(e);
		}
		this.webView.setCaretPosition(0); // scrolls to the top
		
		// Scroll pane that contains the editor pane.
		this.webViewContainer = new JScrollPane(this.webView);
		this.webViewContainer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.webViewContainer.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		this.webViewContainer.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		this.webViewContainer.validate();
	}
	
	/**
	 * Loads the scroll up and scroll down buttons for scrolling the web view.
	 * This is necessary because the scroll bar is very clumsy to control on
	 * a touch screen device.
	 */
	private void loadScrollButtons() {
		// Scroll up button.
		this.scrollUpButton = new JButton();
		this.scrollUpButton.setIcon(this.loadIcon("ArrowUp"));
		this.scrollUpButton.addMouseListener(this);
		
		// Scroll down button.
		this.scrollDownButton = new JButton();
		this.scrollDownButton.setIcon(this.loadIcon("ArrowDown"));
		this.scrollDownButton.addMouseListener(this);
	}
	
	/**
	 * Loads an icon with the given name.
	 * @param name The name
	 * @return The icon
	 */
	private Icon loadIcon(String name) {
		String path = this.resources.getChild(name).getAttributeValue("path");
		ImageIcon icon = null;
    	try {                
    		InputStream is = this.getClass().getResourceAsStream(path);
    		icon = new ImageIcon(ImageIO.read(is));
        } catch (IOException e) {
        	Logger.error(e);
        }
    	
    	return icon;
	}
	
	/**
	 * Loads the QR code view and related components.
	 */
	private void loadQRCodeViewComponents() {
		// We use a container to horizontally center the QR code.
		JPanel container = new JPanel(new GridBagLayout());
		
		GridBagConstraints imageConstraints = new GridBagConstraints();
		imageConstraints.insets = new Insets(10, 10, 10, 10);
		imageConstraints.anchor = GridBagConstraints.ABOVE_BASELINE;
		this.qrCodeView = new ImageView(this.qrCode);
		container.add(this.qrCodeView, imageConstraints);
		
		GridBagConstraints labelConstraints = new GridBagConstraints();
		labelConstraints.anchor = GridBagConstraints.ABOVE_BASELINE;
		try {
			this.qrCodeLabel = new JLabel(URLDecoder.decode(this.qrCodeContent, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			Logger.error(e);
		}
		container.add(this.qrCodeLabel, labelConstraints);
		container.setName("visualizationContainerFooter");
		
		this.add(container, BorderLayout.SOUTH);
	}
	
	/**
	 * Returns the HTML contents of the additional informations.
	 * 
	 * @return the HTML contents
	 */
	public String getHtml() {
		return this.html;
	}
	
	/**
	 * Gets the QR code for further information
	 * 
	 * @return QR code for further information
	 */
	public Image getQrCode() {
		return this.qrCode;
	}
	
	/**
	 * The QR code content.
	 * 
	 * @return QR code content
	 */
	public String getQrCodeContent() {
		return this.qrCodeContent;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.scrollDirection = this.getScrollDirectionFromMouseEvent(e);
		this.scroll(this.scrollDirection);
		if (this.scrollTimer != null) {
			this.stopScrollTimer();
		}
		this.startScrollTimer();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (this.scrollTimer != null) {
			this.stopScrollTimer();
		}
		this.scrollDirection = ScrollDirection.NONE;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Unused
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Unused
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Unused
	}
	
	/**
	 * Converts the given mouse event into the correct scroll direction.
	 * 
	 * @param e The MouseEvent that causes the scroll.
	 * @return The scroll direction for that event or {ScrollDirection.NONE} if
	 * the event cannot be properly mapped.
	 */
	private ScrollDirection getScrollDirectionFromMouseEvent(MouseEvent e) {
		Component source = (Component)e.getSource();
		if (this.scrollUpButton.equals(source)) {
			return ScrollDirection.UP;
		} else if (this.scrollDownButton.equals(source)) {
			return ScrollDirection.DOWN;
		} else {
			return ScrollDirection.NONE;
		}
	}
	
	/**
	 * Starts the scroll timer. We use this timer to scroll multiple times
	 */
	private void startScrollTimer() {
		this.scrollTimer = new Timer(InformationView.scrollDelay, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				scroll(scrollDirection);
			}
		});
		this.scrollTimer.start();
	}
	
	/**
	 * Stops the scroll timer.
	 */
	private void stopScrollTimer() {
		this.scrollTimer.stop();
		this.scrollTimer = null;
	}
	
	/**
	 * Scrolls the web view in the given direction.
	 * @param direction the scroll direction
	 */
	private void scroll(ScrollDirection direction) {
		JScrollBar vertical = this.webViewContainer.getVerticalScrollBar();
		switch (direction) {
			case UP:
				vertical.setValue(Math.max(vertical.getMinimum(), vertical.getValue() - vertical.getBlockIncrement()));
				break;
				
			case DOWN:
				vertical.setValue(Math.min(vertical.getMaximum(), vertical.getValue() + vertical.getBlockIncrement()));
				break;
				
			default:
				// Do nothing.
				break;
		}
		this.updateButtonStates();
	}
	
	/**
	 * Updates the scroll button states.
	 */
	private void updateButtonStates() {
		JScrollBar vertical = this.webViewContainer.getVerticalScrollBar();
		
		if (vertical.getValue() <= vertical.getMinimum()) {
			this.scrollUpButton.setEnabled(false);
		} else {
			this.scrollUpButton.setEnabled(true);
		}
		
		if (vertical.getValue() >= vertical.getMaximum() - vertical.getModel().getExtent() - 1) {
			this.scrollDownButton.setEnabled(false);
		} else {
			this.scrollDownButton.setEnabled(true);
		}
	}
}
