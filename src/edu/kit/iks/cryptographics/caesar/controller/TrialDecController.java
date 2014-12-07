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

import edu.kit.iks.cryptographics.caesar.model.CryptoModel;
import edu.kit.iks.cryptographics.caesar.model.TrialModel;
import edu.kit.iks.cryptographics.caesar.view.trialdec.TrialDecView;
import edu.kit.iks.cryptographics.caesar.view.trialdec.partial.DecryptIntro;
import edu.kit.iks.cryptographicslib.framework.controller.AbstractSteppableVisualizationController;
import edu.kit.iks.cryptographicslib.framework.model.AbstractVisualizationInfo;
import edu.kit.iks.cryptographicslib.util.Configuration;

/**
 * @author Christian Dreher
 *
 */
public class TrialDecController extends AbstractSteppableVisualizationController {

    /**
     * Localized strings used in this view.
     * 
     * @author Christian Dreher <uaeef@student.kit.edu>
     */
    private static class Strings {
        
        /**
         * Localization instance.
         */
        private static I18n i18n = Configuration.getInstance().getI18n(TrialDecController.class);
        
        // Navigation labels and various
        private static String nextButtonLabel = Strings.i18n.tr("Skip decrypt experiment");
        private static String backButtonLabel = Strings.i18n.tr("Back to encrypt experiment");
        private static String stepButtonLabel = Strings.i18n.tr("Proceed");
        
        private static String decryptIntro = Strings.i18n.tr("Now, decrypting a text is very similar to en"
                + "crypting. The only difference is, that when you encrypt a letter, you go to the right on "
                + "the alphabet strip. When decrypting, you just go to the left then. Let\'s try it.");
    };
    
    private TrialModel model = new TrialModel();
    
    /**
     * @param visualizationInfo
     */
    public TrialDecController(AbstractVisualizationInfo visualizationInfo) {
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
        
        vh.add("nextButtonLabel", TrialDecController.Strings.nextButtonLabel);
        vh.add("backButtonLabel", TrialDecController.Strings.backButtonLabel);
        vh.add("stepButtonLabel", TrialDecController.Strings.stepButtonLabel);
        
        this.view = new TrialDecView(this, vh.toList());
        
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
        String input;
        
        switch (callerId) {
            case "asdf":
                break;
            default:
                return false;
        }
    
        return true;
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
        roh.enqueue(this.prepareDecryptIntro());
    }

    private DecryptIntro prepareDecryptIntro() {
        VariableHelper vh = new VariableHelper();
        
        vh.add("decryptIntro", Strings.decryptIntro);
        
        return new DecryptIntro(vh.toList());
    }
    
    private TrialDecView view() {
        return (TrialDecView) this.view;
    }
}
