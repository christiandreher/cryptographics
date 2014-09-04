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

package edu.kit.iks.Cryptographics.Vigenere.Demonstration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.Cryptographics.VisualizationContainerController;
import edu.kit.iks.CryptographicsLib.AbstractVisualizationController;
import edu.kit.iks.CryptographicsLib.AbstractVisualizationInfo;
import edu.kit.iks.CryptographicsLib.Configuration;
import edu.kit.iks.CryptographicsLib.Logger;

/**
 * Controller of the second step
 * @author Aydin Tekin
 */
public class SecondDemonstrationController extends AbstractVisualizationController {
	
	/**
	 * current state of progress
	 */
	private int state;
	
	/**
	 * Localization instance
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(SecondDemonstrationController.class);
	
	/**
	 * Constructor
	 * @param visualizationInfo visualization information of current step
	 */
	public SecondDemonstrationController(AbstractVisualizationInfo visualizationInfo) {
		super(visualizationInfo);
	}
	
	/**
	 * changes the GUI according to step
	 */
	private void stepBack() {
		state--;
		Logger.debug("SecondDemonstrationController", "stepBack()", "State: " + state);
		switch (state) {
		case -1:
			VisualizationContainerController containerController = (VisualizationContainerController)getParentController();
			containerController.presentPreviousVisualizationController();
			break;
		case 0:
			getView().setExplanation("<html><div width=\"1200\">"
					+ i18n.tr("Now we want to encrypt 'ANNA'. First of all we add the "
					+ "position of evey character, as you can see in the bottom, in the "
					+ "alphabet under each character.")
					+ "</div></html>");
			getView().setKeyVisible(false);
			getView().setEncryptedCharsVisible(false);
			getView().setCalculatorVisible(false);
			break;
		case 1:
			getView().setExplanation("<html><div width=\"1200\">"
					+ i18n.tr("This is the key for the encryption. Now we go to the "
					+ "next step and encrypt the first character.")
					+ "</div></html>");
			getView().setKeyVisible(true);
			getView().setEncryptedCharsVisible(true);
			getView().setCalculatorVisible(false);
			break;
		case 2:
			getView().setExplanation("<html><div width=\"1200\">"
					+ i18n.tr("The position of 'A' in the alphabet is 1. The "
					+ "position of 'O' is 14. Now we calculate 1 + 14 "
					+ "and get 15.")
					+ "</div></html>");
			getView().setKeyVisible(true);
			getView().setEncryptedCharsVisible(true);
			getView().setCalculatorVisible(true);
			getView().getAlphabet().unHighlightAll();
			getView().setCalculator(1,15);
			getView().setTextField(0, "");
			getView().getAlphabet().highlight(0);
			getView().getAlphabet().highlight(13);
			break;
		case 3:
			getView().setExplanation("<html><div width=\"1200\">"
					+ i18n.tr("The number 16 represents 'P' in the alphabet. "
					+ "So the first encrypted character is 'P'. Lets go "
					+ "to the next one.")
					+ "</div></html>");
			getView().setCalculator(1,15);
			getView().highlightAndSetText(15, 0, "P");
			getView().setTextField(1, "");
			break;
		case 4:
			getView().setExplanation("<html><div width=\"1200\">"
					+ i18n.tr("Now get to the next character. It's an 'N', which "
					+ "represents the number 14. The second character "
					+ "of the key is 'K', which is the 11. 14+11 = 25 -> "
					+ "'Y'!")
					+ "</div></html>");
			getView().setCalculator(14,11);
			getView().highlightAndSetText(24, 1, "Y");
			getView().setTextField(2, "");
			break;
		case 5:
			getView().setExplanation("<html><div width=\"1200\">"
					+ i18n.tr("Now we continue...")
					+ "</div></html>");
			getView().setCalculator(14,1);
			getView().highlightAndSetText(14, 2, "O");
			getView().setTextField(3, "");
			break;
		}
	}
	
	/**
	 * changes the GUI according to step
	 */
	private void stepForward() {
		state++;
		Logger.debug("SecondDemonstrationController", "stepForward()", "State: " + state);
		switch (state) {
		case 1:
			getView().setExplanation("<html><div width=\"1200\">"
					+ i18n.tr("This is the key for the encryption. Now we go "
					+ "to the next step and encrypt the first character.")
					+ "</div></html>");
			getView().setKeyVisible(true);
			getView().setEncryptedCharsVisible(true);
			getView().setCalculatorVisible(false);
			break;
		case 2:
			getView().setExplanation("<html><div width=\"1200\">"
					+ i18n.tr("The position of 'A' in the alphabet is 1. The "
					+ "position of 'O' is 15. Now we calculate 1 + 15 "
					+ "and get 16.")
					+ "</div></html>");
			getView().setKeyVisible(true);
			getView().setEncryptedCharsVisible(true);
			getView().setCalculatorVisible(true);
			getView().setCalculator(1,15);
			getView().getAlphabet().unHighlightAll();
			getView().getAlphabet().highlight(0);
			getView().getAlphabet().highlight(14);
			break;
		case 3:
			getView().setExplanation("<html><div width=\"1200\">"
					+ i18n.tr("The number 16 represents 'P' in the alphabet. So "
					+ "the first encrypted character is 'P'. Lets go to "
					+ "the next one.")
					+ "</div></html>");
			getView().highlightAndSetText(15, 0, "P");
			break;
		case 4:
			getView().setExplanation("<html><div width=\"1200\">"
					+ i18n.tr("Now get to the next character. It's an 'N', which "
					+ "represents the number 14. The second character of "
					+ "the key is 'K', which is the 11. 14+11 = 25 -> 'Y'!")
					+ "</div></html>");
			getView().setCalculator(14,11);
			getView().highlightAndSetText(24, 1, "Y");
			break;
		case 5:
			getView().setExplanation("<html><div width=\"1200\">"
					+ i18n.tr("Now we continue...")
					+ "</div></html>");
			getView().setCalculator(14,1);
			getView().highlightAndSetText(14, 2, "O");
			break;
		case 6:
			getView().setExplanation("<html><div width=\"1200\">"
					+ i18n.tr("E = 5 and Y = 25. 25 + 5 = 30, but our alphabet doesnt "
					+ "have 30 characters. So we now use the modulo: 30 modulo "
					+ "26 = 4.")
					+ "</div></html>");
			getView().setCalculator(5,25);
			getView().highlightAndSetText(3, 3, "D");
			break;
		case 7:
			VisualizationContainerController containerController = (VisualizationContainerController)getParentController();
			containerController.presentNextVisualizationController();
			break;
		}
	}
	
	/**
	 * loads the view and registers action listeners
	 */
	@Override
	public void loadView() {
		this.state = 0;
		this.view = new SecondDemonstrationView();
		this.getView().getBackButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				stepBack();
			}
		});
		this.getView().getNextButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				stepForward();
			}
		});
		this.getView().getSkipButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				VisualizationContainerController containerController = (VisualizationContainerController)getParentController();
				containerController.presentNextVisualizationController();
			}
		});
		this.getView().getReturnButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				VisualizationContainerController containerController = (VisualizationContainerController)getParentController();
				containerController.presentPreviousVisualizationController();
			}
		});
	}

	/**
	 * unloads the view
	 */
	@Override
	public void unloadView() {
		this.view = null;
	}
	
	/**
	 * returns the help string
	 * @return help string
	 */
	@Override
	public String getHelp() {
		return i18n.tr("Follow the explanations!");
	}
	
	/**
	 * returns the view
	 * @return view of current step
	 */
	@Override
	public SecondDemonstrationView getView() {
		return (SecondDemonstrationView) this.view;
	}
}
