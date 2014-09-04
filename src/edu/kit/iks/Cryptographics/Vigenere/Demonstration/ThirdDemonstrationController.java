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

public class ThirdDemonstrationController extends AbstractVisualizationController {

	/**
	 * Localization instance
	 */
	private static I18n i18n = Configuration.getInstance().getI18n(ThirdDemonstrationController.class);
	
	/**
	 * current state of progress
	 */
	private int state;
	
	/**
	 * Constructor
	 * @param visualizationInfo visualization information of current step
	 */
	public ThirdDemonstrationController(AbstractVisualizationInfo visualizationInfo) {
		super(visualizationInfo);
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
	 * changes the GUI according to step
	 */
	private void stepBack() {
		state--;
		Logger.debug("SecondDemonstrationController", "backButton.addActionListener()", "State: " + state);
		switch (state) {
			case -1:
				VisualizationContainerController containerController = (VisualizationContainerController)getParentController();
				containerController.presentPreviousVisualizationController();
				break;
			case 0:
				getView().setExplanation("<html><div width=\"1200\">"
						+ i18n.tr("Now we want to decrypt 'ENQM'. Instead of adding "
						+ "up, we use substraction to decrypt it! So let's go...")
						+ "</div></html>");
				getView().setCalculatorVisible(false);
				break;
			case 1:
				getView().setExplanation("<html><div width=\"1200\">"
						+ i18n.tr("'E' = 5 and 'K' = 11. 3 - 11 = -6. What now? "
						+ "Well it's easy, just reverse the modulo...")
						+ "</div></html>");
				getView().setCalculatorVisible(true);
				getView().setCalculator(5,11);
				getView().getAlphabet().unHighlightAll();
				getView().getAlphabet().highlight(4);
				getView().getAlphabet().highlight(10);
				getView().setTextField(0, "");
				break;
			case 2:
				getView().setExplanation("<html><div width=\"1200\">"
						+ i18n.tr("Negative numbers mean you start from the 'Z' and "
						+ "go 7 steps backwards. So we get 'T' = 20!")
						+ "</div></html>");
				getView().setCalculator(4,11);
				getView().highlightAndSetText(19, 0, "T");
				getView().setTextField(1, "");
				break;
			case 3:
				getView().setExplanation("<html><div width=\"1200\">"
						+ i18n.tr("'N' = 14 and 'I' = 9. 14 - 9 = 5 -> 'E'")
						+ "</div></html>");
				getView().setCalculator(14,9);
				getView().highlightAndSetText(4, 1, "E");
				getView().setTextField(2, "");
				break;
			case 4:
				getView().setExplanation("<html><div width=\"1200\">"
						+ i18n.tr("'Q' = 17 and 'S' = 19. 17 - 19 = -2 -> Go 2 Steps "
						+ "backwards from 'Z' -> 'X'")
						+ "</div></html>");
				getView().setCalculator(17,19);
				getView().highlightAndSetText(23, 2, "X");
				getView().setTextField(3, "");
				break;
		}
	}
	
	/**
	 * changes the GUI according to step
	 */
	private void stepForward() {
		state++;
		switch (state) {
		case 1:
			getView().setExplanation("<html><div width=\"1200\">"
					+ i18n.tr("'E' = 5 and 'K' = 11. 3 - 11 = -6. What now? Well "
					+ "it's easy, just reverse the modulo...")
					+ "</div></html>");
			getView().setCalculatorVisible(true);
			getView().setCalculator(5,11);
			getView().getAlphabet().unHighlightAll();
			getView().getAlphabet().highlight(4);
			getView().getAlphabet().highlight(10);
			break;
		case 2:
			getView().setExplanation("<html><div width=\"1200\">"
					+ i18n.tr("Negative numbers mean you start from the 'Z' and "
					+ "go 7 steps backwards. So we get 'T' = 20!")
					+ "</div></html>");
			getView().setCalculator(5,11);
			getView().highlightAndSetText(19, 0, "T");
			break;
		case 3:
			getView().setExplanation("<html><div width=\"1200\">"
					+ i18n.tr("'N' = 14 and 'I' = 9. 14 - 9 = 5 -> 'E'")
					+ "</div></html>");
			getView().setCalculator(14,9);
			getView().highlightAndSetText(4, 1, "E");
			break;
		case 4:
			getView().setExplanation("<html><div width=\"1200\">"
					+ i18n.tr("'Q' = 17 and 'S' = 19. 17 - 19 = -2 -> Go 2 Steps "
					+ "backwards from 'Z' -> 'X'")
					+ "</div></html>");
			getView().setCalculator(17,19);
			getView().highlightAndSetText(23, 2, "X");
			break;
		case 5:
			getView().setExplanation("<html><div width=\"1200\">"
					+ i18n.tr("'M' = 13 and 'S' = 19. 13 - 19 = -6 -> Go 6 Steps "
					+ "backwards from 'Z' -> 'T'")
					+ "</div></html>");
			getView().setCalculator(13,19);
			getView().highlightAndSetText(19, 3, "T");
			break;	
		case 6:
			VisualizationContainerController containerController = (VisualizationContainerController)getParentController();
			containerController.presentNextVisualizationController();
		}
	}

	/**
	 * loads the view and registers action listeners
	 */
	@Override
	public void loadView() {
		this.state = 0;
		this.view = new ThirdDemonstrationView();
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
	 * returns the view
	 * @return view of current step
	 */
	@Override
	public ThirdDemonstrationView getView() {
		return (ThirdDemonstrationView)this.view;
	}
}
