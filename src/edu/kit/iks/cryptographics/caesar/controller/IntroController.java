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

package edu.kit.iks.cryptographics.caesar.controller;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.cryptographics.caesar.view.intro.IntroView;
import edu.kit.iks.cryptographics.caesar.view.intro.partial.CaesarsConquerPlan;
import edu.kit.iks.cryptographics.caesar.view.intro.partial.CaesarsPlanCrossed;
import edu.kit.iks.cryptographics.caesar.view.intro.partial.CourierCarryingPlan;
import edu.kit.iks.cryptographics.caesar.view.intro.partial.CourierCaughtByKryptolix;
import edu.kit.iks.cryptographics.caesar.view.intro.partial.KryptolixReadingPlan;
import edu.kit.iks.cryptographicslib.framework.controller.AbstractSteppableVisualizationController;
import edu.kit.iks.cryptographicslib.framework.model.AbstractVisualizationInfo;
import edu.kit.iks.cryptographicslib.util.Configuration;

/**
 * Introduction controller.
 * 
 * @author Christian Dreher <uaeef@student.kit.edu>
 */
public class IntroController extends AbstractSteppableVisualizationController {

	/**
	 * Strings to be displayed.
	 * 
	 * @author Christian Dreher <uaeef@student.kit.edu>
	 */
	private static class Strings {
	    
	    /**
	     * Localization instance.
	     */
	    private static I18n i18n = Configuration.getInstance().getI18n(IntroController.class);
	    
		// Navigation labels
		public static String nextButtonLabel = Strings.i18n.tr("Skip introduction");
		public static String stepButtonLabel = Strings.i18n.tr("Tell me more");
		
		// Help
		public static String help = Strings.i18n.tr("If you want to hear the story "
				+ "of how Caesar came up with encrypting, click \'Tell me more\', otherwise "
				+ "click \'Skip Introduction\'");

		// Story texts
		public static String caesarsConquerPlan = Strings.i18n.tr("One fine day, at about 70 B.C., Caesar was "
				+ "puzzling on an extremely brilliant plan to finally conquer Gallia and was about to "
				+ "send it to his generals.");
		public static String courierCarryingPlan = Strings.i18n.tr("Unfortunately his courier took the "
				+ "way through the forest, where Kryptolix chased some wild boars.");
		public static String courierCaughtByKryptolix = Strings.i18n.tr("When Kryptolix noticed the "
				+ "unsuspecting and whistling roman courier, he punched him back to Rome as the crow flies. "
				+ "But the courier had lost his invaluable scroll!");
		public static String kryptolixReadingPlan = Strings.i18n.tr("When reading the scroll the courier "
				+ "lost, Kryptolix identified it as Caesar\'s plan to conquer Gallia. Because of this, "
				+ "Kryptolix and his friends were able to defeat Caesar once more!");
		public static String caesarsPlanCrossed = Strings.i18n.tr("Caesar was raging! While he was "
				+ "torturing some minions, suddenly an hellacious and foolproof idea crossed his mind. "
				+ "In his next message he would encrypt his name! Hue Hue Hue. Help him!");
	}

	/**
	 * @param visualizationInfo Visualization info
	 */
	public IntroController(final AbstractVisualizationInfo visualizationInfo) {
		super(visualizationInfo);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
    public final boolean routeAction(final String callerId) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see edu.kit.iks.cryptographicslib.controller.AbstractSteppableVisualizationController
	 * #loadView(edu.kit.iks.cryptographicslib.controller.AbstractSteppableVisualizationController.RunningOrderHelper)
	 */
	@Override
    public final void loadView(final RunningOrderHelper roh) {
		// Set variables for view
		VariableHelper vh = new VariableHelper();
		
		vh.add("nextButtonLabel", IntroController.Strings.nextButtonLabel);
		vh.add("stepButtonLabel", IntroController.Strings.stepButtonLabel);
		vh.add("helpText", IntroController.Strings.help);
		
		// Create view
		this.view = new IntroView(this, vh.toList());
		
		// Define running order
		this.defineRunningOrder(roh);
	}

	/* (non-Javadoc)
	 * @see edu.kit.iks.CryptographicsLib.AbstractController#unloadView()
	 */
	@Override
    public final void unloadView() {
		this.view = null;
	}

	/* (non-Javadoc)
	 * @see edu.kit.iks.CryptographicsLib.AbstractVisualizationController#getHelp()
	 */
	@Override
    public final String helpAction() {
		return this.view().getHelp();
	}
	
	/**
	 * Defines the running order of the partial views.
	 * 
	 * @param roh Running order helper
	 */
	private void defineRunningOrder(final RunningOrderHelper roh) {
		roh.enqueue(this.prepareCaesarsConquerPlan());
		roh.enqueue(this.prepareCourierCarryingPlan());
		roh.enqueue(this.prepareCourierCaughtByKryptolix());
		roh.enqueue(this.prepareKryptolixReadingPlan());
		roh.enqueue(this.prepareCaesarsPlanCrossed());
	}
	
	/**
	 * Partial view initialization: Prepares view (Caesars conquer plan).
	 * 
	 * @return Prepared view
	 */
	private CaesarsConquerPlan prepareCaesarsConquerPlan() {
		VariableHelper vh = new VariableHelper();
		vh.add("caesarsConquerPlan", IntroController.Strings.caesarsConquerPlan);
		
		return new CaesarsConquerPlan(vh.toList());
	}
	
	/**
	 * Partial view initialization: Prepares view (Courier carrying plan).
	 * 
	 * @return Prepared view
	 */
	private CourierCarryingPlan prepareCourierCarryingPlan() {
		VariableHelper vh = new VariableHelper();
		vh.add("courierCarryingPlan", IntroController.Strings.courierCarryingPlan);
		
		return new CourierCarryingPlan(vh.toList());
	}
	
	/**
	 * Partial view initialization: Prepares view (Courier caught by Kryptolix).
	 * 
	 * @return Prepared view
	 */
	private CourierCaughtByKryptolix prepareCourierCaughtByKryptolix() {
		VariableHelper vh = new VariableHelper();
		vh.add("courierCaughtByKryptolix", IntroController.Strings.courierCaughtByKryptolix);
		
		return new CourierCaughtByKryptolix(vh.toList());
	}
	
	/**
	 * Partial view initialization: Prepares view (Kryptolix reading plan).
	 * 
	 * @return Prepared view
	 */
	private KryptolixReadingPlan prepareKryptolixReadingPlan() {
		VariableHelper vh = new VariableHelper();
		vh.add("kryptolixReadingPlan", IntroController.Strings.kryptolixReadingPlan);
		
		return new KryptolixReadingPlan(vh.toList());
	}
	
	/**
	 * Partial view initialization: Prepares view (Caesars plan crossed).
	 * 
	 * @return Prepared view
	 */
	private CaesarsPlanCrossed prepareCaesarsPlanCrossed() {
		VariableHelper vh = new VariableHelper();
		vh.add("caesarsPlanCrossed", IntroController.Strings.caesarsPlanCrossed);
		
		return new CaesarsPlanCrossed(vh.toList());
	}

	/**
	 * Casts the view.
	 * 
	 * @return Casted view
	 */
	private IntroView view() {
		return (IntroView) this.view;
	}
}
