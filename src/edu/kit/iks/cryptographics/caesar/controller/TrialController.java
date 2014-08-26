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

import java.util.Random;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.cryptographics.caesar.view.trial.TrialView;
import edu.kit.iks.cryptographics.caesar.view.trial.partial.EnterName;
import edu.kit.iks.cryptographicslib.framework.controller.AbstractSteppableVisualizationController;
import edu.kit.iks.cryptographicslib.framework.model.AbstractVisualizationInfo;
import edu.kit.iks.cryptographicslib.util.Configuration;

/**
 * @author Christian Dreher
 *
 */
public class TrialController extends AbstractSteppableVisualizationController {

    /**
     * Localized strings used in this view.
     * 
     * @author Christian Dreher <uaeef@student.kit.edu>
     */
    private static class Strings {
        
        /**
         * Localization instance.
         */
        private static I18n i18n = Configuration.getInstance().getI18n(TrialController.class);
        
        // Navigation labels and various
        private static String nextButtonLabel = Strings.i18n.tr("Skip experiment");
        private static String backButtonLabel = Strings.i18n.tr("Back to demonstration");
        private static String skipButtonLabel = Strings.i18n.tr("Use random name");
        private static String proceedButtonLabel = Strings.i18n.tr("Proceed");
        
        private static String trialExplanation = Strings.i18n.tr("Now lets try to encrypt your name with "
                + "the key {0}. Just enter your name in the text field below or press the button "
                + "to use a random one.", TrialController.randomKey);
    };
    
    private static int randomKey = (new Random()).nextInt((8 - 5) + 1) + 5;
    
    /**
     * @param visualizationInfo
     */
    public TrialController(AbstractVisualizationInfo visualizationInfo) {
        super(visualizationInfo);
    }

    /* (non-Javadoc)
     * @see edu.kit.iks.cryptographicslib.framework.controller.AbstractSteppableVisualizationController
     * #loadView(edu.kit.iks.cryptographicslib.framework.controller
     * .AbstractSteppableVisualizationController.RunningOrderHelper)
     */
    @Override
    public final void loadView(final RunningOrderHelper roh) {
        VariableHelper vh = new VariableHelper();
        
        vh.add("nextButtonLabel", TrialController.Strings.nextButtonLabel);
        vh.add("backButtonLabel", TrialController.Strings.backButtonLabel);
        vh.add("skipButtonLabel", TrialController.Strings.skipButtonLabel);
        vh.add("proceedButtonLabel", TrialController.Strings.proceedButtonLabel);
        
        this.view = new TrialView(this, vh.toList());
        
        this.defineRunningOrder(roh);
    }

    /* (non-Javadoc)
     * @see edu.kit.iks.cryptographicslib.framework.controller.AbstractController#unloadView()
     */
    @Override
    public final void unloadView() {
        this.view = null;
    }

    /* (non-Javadoc)
     * @see edu.kit.iks.cryptographicslib.framework.controller.AbstractVisualizationController
     * #routeAction(java.lang.String)
     */
    @Override
    public final boolean routeAction(final String callerId) {
        // TODO Auto-generated method stub
        return false;
    }
    
    /* (non-Javadoc)
     * @see edu.kit.iks.cryptographicslib.framework.controller.AbstractVisualizationController#helpAction()
     */
    @Override
    public final String helpAction() {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
     * Helper to define the running order.
     * 
     * @param roh Running order helper
     */
    private void defineRunningOrder(final RunningOrderHelper roh) {
        roh.enqueue(this.prepareEnterName());
    }
    
    private EnterName prepareEnterName() {
        VariableHelper vh = new VariableHelper();
        
        vh.add("explanation", TrialController.Strings.trialExplanation);
        
        return new EnterName(vh.toList());
    }
}
