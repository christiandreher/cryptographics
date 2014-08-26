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

package edu.kit.iks.cryptographicslib.common.controller;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.cryptographicslib.common.view.InformationView;
import edu.kit.iks.cryptographicslib.framework.controller.AbstractVisualizationController;
import edu.kit.iks.cryptographicslib.framework.model.AbstractVisualizationInfo;
import edu.kit.iks.cryptographicslib.util.Configuration;
import edu.kit.iks.cryptographicslib.util.Logger;

/**
 * Controller for each procedure displaying further information.
 * 
 * @author Christian Dreher
 */
public class InformationController extends AbstractVisualizationController {
	
	/**
	 * Localization instance.
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(InformationController.class);
	
	private String additionalInformationHtml;
	
	/**
	 * Constructor initializing a new instance of {InformationController}
	 * with given {visualizationInfo}.
	 * 
	 * @param visualizationInfo Object of {VisualizationInfo} containing the data to
	 *		instantiate related controllers from
	 */
	public InformationController(AbstractVisualizationInfo visualizationInfo) {
		super(visualizationInfo);
		
		// Load the HTML.
		String path = visualizationInfo.getAdditionalInformationPath();
		InputStream inputStream = visualizationInfo.getClass().getResourceAsStream(path);
		if (inputStream == null) {
			Logger.debug("InformationController", "InformationController", "could not read " + path);
		}
		this.additionalInformationHtml = InformationController.getStringFromInputStream(inputStream);
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.kit.iks.CryptographicsLib.AbstractController#loadView()
	 */
	@Override
	public void loadView() {
		Image qrCode = this.getVisualizationInfo().getQrCode();
		String qrCodeContent = this.getVisualizationInfo().getQRCodeContent();
		this.view = new InformationView(this.additionalInformationHtml, qrCode, qrCodeContent);
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.kit.iks.CryptographicsLib.AbstractController#unloadView()
	 */
	@Override
	public void unloadView() {
		this.view = null;
	}

	/*
	 * (non-Javadoc)
	 * @see edu.kit.iks.CryptographicsLib.AbstractVisualizationController#getHelp()
	 */
	@Override
	public String helpAction() {
		String help = InformationController.i18n.tr("Here you can learn more about {0}. You can also"
				+ " scan the QR code at the bottom of the page to take additional information home."
				+ " Once you\'re done, just click the exit button.", this.getVisualizationInfo().getName());
		
		return help;
	}
	
	// convert InputStream to String
	private static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public boolean routeAction(String callerId) {
		return false;
	}
}
