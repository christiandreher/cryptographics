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

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xnap.commons.i18n.I18n;
import org.xnap.commons.i18n.I18nFactory;

/**
 * This class allows to configure certain parameters of the application
 * at a central place.
 * 
 * @author Matthias Plappert
 */
public class Configuration {
	
	/**
	 * Shared instance for singleton
	 */
	static private Configuration sharedInstance = null;
	
	/**
	 * The timeout after which a user is considered idle in milliseconds.
	 */
	private int idleTimeout = 5 * 60 * 1000;
	
	/**
	 * The timeout after which the program resets itself after a user is
	 * idle in milliseconds.
	 */
	private int resetTimeout = 60 * 1000;
	
	/**
	 * Indicates if the debug mode is enabled.
	 */
	private boolean debugMode= false;
	
	/**
	 * Indicates if the fullscreen mode is enabled.
	 */
	private boolean fullscreenMode= true;
	
	/**
	 * Indicates if the look and feel is enabled.
	 */
	private boolean lookAndFeel= true;
	
	/**
	 * Indicates if the mouse cursor is enabled.
	 */
	private boolean mouseCursor = false;
	
	/**
	 * The language code.
	 */
	private String languageCode = "de";
	
	/**
	 * Marked as private to enforce singleton pattern.
	 */
	private Configuration(String path) {
		try {
			// Load document.
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(new File(path));

			// Get root element.
			Element element = document.getRootElement();
			for (Element child : element.getChildren()) {
				parseConfigElement(child);
			}
		} catch (JDOMException | IOException e) {
			// Could not read configuration. Use default values and print stack
			// trace.
			e.printStackTrace();
		}
	}
	
	/**
	 * Parses the values of the configuration XML.
	 * @param child the current child element
	 */
	private void parseConfigElement(Element child) {
		String value = child.getValue();
		String name = child.getName();
		
		switch (name) {
			case "idleTimeout":
				this.idleTimeout = Integer.parseInt(value);
				break;
				
			case "resetTimeout":
				this.resetTimeout = Integer.parseInt(value);
				break;
				
			case "debugMode":
				this.debugMode = Boolean.parseBoolean(value);
				break;
				
			case "fullscreenMode":
				this.fullscreenMode = Boolean.parseBoolean(value);
				break;
				
			case "lookAndFeel":
				this.lookAndFeel = Boolean.parseBoolean(value);
				break;
				
			case "mouseCursor":
				this.mouseCursor = Boolean.parseBoolean(value);
				break;
				
			case "languageCode":
				this.languageCode = value;
				break;
				
			default:
				// Do not use the logger here since this happens very early
				// and the logger might not be configured yet.
				System.out.println("Unknown configuration element " + child.getName() + ". Skipping.");
				break;
		}
	}
	
	/**
	 * Returns a shared instance (singleton pattern)
	 * @return the shared instance
	 */
	static public Configuration getInstance() {
		if (sharedInstance == null) {
			sharedInstance = new Configuration("config.xml");
		}
		return sharedInstance;
	}
	
	/**
	 * Returns the time in milliseconds after that a user is considered idle.
	 * @return the timeout in milliseconds
	 */
	public int getIdleTimeout() {
		return this.idleTimeout;
	}
	
	/**
	 * Returns the time in milliseconds for the program reset after a user is
	 * detected as idle.
	 * @return the timeout in milliseconds
	 */
	public int getResetTimeout() {
		return this.resetTimeout;
	}
	
	/**
	 * Returns true if the debug mode should be used.
	 * @return true if the debug mode is enabled 
	 */
	public boolean isDebugModeEnabled() {
		return this.debugMode;
	}
	
	/**
	 * Returns true if the mouse cursor should be visible. This setting
	 * will always return false if debug mode is disabled.
	 * 
	 * @return true if the mouse cursor should be visible
	 */
	public boolean isMouseCursorEnabled() {
		if (this.isDebugModeEnabled()) {
			return this.mouseCursor;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns true if the look and feel should be enabled. This setting
	 * will always return true if debug mode is disabled.
	 * 
	 * @return true if the look and feel should be enabled
	 */
	public boolean isLookAndFeelEnabled() {
		if (this.isDebugModeEnabled()) {
			return this.lookAndFeel;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns true if the fullscreen mode should be enabled. This setting
	 * will always return true if debug mode is disabled.
	 * @return
	 */
	public boolean isFullscreenModeEnabled() {
		if (this.isDebugModeEnabled()) {
			return this.fullscreenMode;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the language code used for localization.
	 * @return the language code
	 */
	public String getLanguageCode() {
		return this.languageCode;
	}
	
	/**
	 * Returns the I18n instance for the package the given
	 * class is in
	 * 
	 * @param className fully qualified class name (ClassName.class)
	 * @return I18n instance to use the tr functions
	 */
	@SuppressWarnings("rawtypes")
	public I18n getI18n(Class className) {
		Locale loc = new Locale(this.getLanguageCode());
		I18n i18n = I18nFactory.getI18n(className, loc);
		
		return i18n;
	}
}
