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

package edu.kit.iks.Cryptographics.Vigenere;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xnap.commons.i18n.I18n;

import edu.kit.iks.Cryptographics.Vigenere.Demonstration.*;
import edu.kit.iks.Cryptographics.Vigenere.Experiment.FirstExperimentController;
import edu.kit.iks.Cryptographics.Vigenere.Explanation.FirstExplanationController;
import edu.kit.iks.Cryptographics.Vigenere.Explanation.SecondExplanationController;
import edu.kit.iks.CryptographicsLib.AbstractVisualizationInfo;
import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.InformationController;
import edu.kit.iks.CryptographicsLib.Logger;
import edu.kit.iks.CryptographicsLib.VisualizationDifficulty;

/**
 * @author Aydin Tekin
 * This class provides all needed information for the vigenere-section
 */
public class VigenereVisualizationInfo extends AbstractVisualizationInfo {
	
	/**
	 * Localization instance
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(VigenereVisualizationInfo.class);
	
	/**
	 * link to the resources
	 */
	private Element vigenereResources;
	
	/**
	 * loads the resources which are bundled with the jar file
	 */
	private void loadResources() {
		SAXBuilder saxBuilder = new SAXBuilder();

		InputStream is = this.getClass().getResourceAsStream("/vigenere/VigenereResources.xml");

		try {
			// converted file to document object
			Document document = saxBuilder.build(is);

			// get root node from xml
			this.vigenereResources = document.getRootElement().getChild("vigenere");
		} catch (JDOMException | IOException e) {
			Logger.error(e);
		}
	}
	
	/**
	 * Constructor
	 */
	public VigenereVisualizationInfo() {
		loadResources();
	}
	
	/**
	 * returns the ID
	 * @return ID
	 */
	public String getId() {
		return "vigenere";
	}
	
	/**
	 * returns the name
	 * @return name
	 */
	public String getName() {
		return i18n.tr("Vigenère");
	}
	
	/**
	 * returns the description of vigenere.
	 * @return description of vigenere.
	 */
	public String getDescription() {
		return i18n.tr("Get to know how Vigenère fixed the weaknesses of Caesar to make a 'secure' cipher for many hundred years!");
	}
	
	/**
	 * returns the QRCode-source
	 * @return QRCode-source
	 */
	public String getQRCodeContent() {
		return i18n.tr("http://en.wikipedia.org/wiki/Vigen%C3%A8re_cipher");
	}
	
	/**
	 * returns the offset in the timeline
	 * @return offset in the timeline
	 */
	public float getTimelineOffset() {
		return 0.5f;
	}

	/**
	 * returns the difficulty
	 * @return difficulty
	 */
	public VisualizationDifficulty getDifficulty() {
		return VisualizationDifficulty.MEDIUM;
	}
	
	
	/**
	 * gets the year of the cryptographic algorithm
	 * @return year of the cryptographic algorithm
	 */
	public int getYear() {
		return 1600; //inaccurate
	}
	
	/**
	 * returns all the needed classes for this algorithm
	 * @return all the needed classes for this algorithm
	 */
	@SuppressWarnings("rawtypes")
	public List<Class> getControllerClasses() {
		List<Class> controllerClasses = new ArrayList<Class>();
		controllerClasses.add(FirstDemonstrationController.class);
		controllerClasses.add(SecondDemonstrationController.class);
		controllerClasses.add(ThirdDemonstrationController.class);
		controllerClasses.add(FirstExperimentController.class);
		controllerClasses.add(FirstExplanationController.class);
		controllerClasses.add(SecondExplanationController.class);
		controllerClasses.add(InformationController.class);
		return controllerClasses;
	}
	
	/**
	 * returns the link to the resources
	 * @return link to the resources
	 */
	public Element getResources() {
		return vigenereResources;
	}
}
