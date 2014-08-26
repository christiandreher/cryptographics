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

package edu.kit.iks.cryptographicslib.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import edu.kit.iks.cryptographicslib.common.view.partial.ImageView;

/**
 * Utility class to ease file access.
 * 
 * @author Christian Dreher <uaeef@student.kit.edu>
 *
 */
public class Utility {
	
	public static String procedureId = null;
	
	private Utility() {
		
	}
	
	/**
	 * Gets a resource as input stream.
	 * 
	 * @param name Name of the resource
	 * @return InputStream instance containing requested file
	 */
	public static InputStream getResourceAsInputStream(String name) {
		
		if (!Pattern.matches("/.*", name)) {
			name = "/" + name;
		}
		
		InputStream is = Utility.class.getResourceAsStream(name);
		
		return is;
	}
	
	public static ImageView getImageViewFromResourceId(String resourceId) {
		SAXBuilder saxBuilder = new SAXBuilder();
		String path = "";

		// Obtain file object
		InputStream is = Utility.class.getResourceAsStream("/" + Utility.procedureId + "/" + "resources.xml");

		try {
			// Converted file to document object
			Document document = saxBuilder.build(is);

			// Get root node from XML
			Element root = document.getRootElement();
			
			// Get file path from child with given resourceId
			path = root.getChild(resourceId).getAttributeValue("path");
		} catch (JDOMException | IOException | NullPointerException e) {
			Logger.error(e);
		}
		
		return new ImageView(path);
	}
}
