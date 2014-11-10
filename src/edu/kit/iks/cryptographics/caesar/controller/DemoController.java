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
import edu.kit.iks.cryptographics.caesar.model.DemoModel;
import edu.kit.iks.cryptographics.caesar.view.demo.DemoView;
import edu.kit.iks.cryptographics.caesar.view.demo.partial.CaesarsIdea;
import edu.kit.iks.cryptographics.caesar.view.demo.partial.Result;
import edu.kit.iks.cryptographics.caesar.view.demo.partial.TryEncryptCaesar;
import edu.kit.iks.cryptographicslib.framework.controller.AbstractSteppableVisualizationController;
import edu.kit.iks.cryptographicslib.framework.model.AbstractVisualizationInfo;
import edu.kit.iks.cryptographicslib.util.Configuration;

/**
 * Demonstration controller.
 * 
 * @author Christian Dreher <uaeef@student.kit.edu>
 */
public class DemoController extends AbstractSteppableVisualizationController {

	/**
	 * Localized strings used in this view.
	 * 
	 * @author Christian Dreher <uaeef@student.kit.edu>
	 */
	private static class Strings {
	    
		/**
		 * Localization instance.
		 */
		private static I18n i18n = Configuration.getInstance().getI18n(DemoController.class);
		
		// Navigation labels and various
		private static String nextButtonLabel = Strings.i18n.tr("Skip demonstration");
		private static String backButtonLabel = Strings.i18n.tr("Back to introduction");
		private static String stepButtonLabel = Strings.i18n.tr("Proceed");
		
		// Caesars Idea
		private static String caesarsIdea = Strings.i18n.tr("Caesar's idea was to simply shift "
				+ "each letter in his name by three, so no undesired reader will know who sent "
				+ "the plan in the future. Press 'Proceed' and see how it works.");
		private static String helpCaesarsIdea = Strings.i18n.tr("Just click 'Proceed' at the "
				+ "bottom to see how Caesars idea exactly works.");
		
		// Example encrypting Caesars name
		private static String caesarsName = Strings.i18n.tr("CAESAR");
		private static String explanation1 = Strings.i18n.tr("Let's start with the first letter, 'C'.");
		private static String explanation2 = Strings.i18n.tr("If you shift 'C' by three to the right, " +
				"you'll get 'F'. It's as simple as that.");
		private static String explanation3 = Strings.i18n.tr("Now it's your turn. Try to encrypt the rest of " +
				"the name on your own. If you need help, Use the upper right button.");
		
		// Conclusion after successfully encrypting Caesars name
		private static String result = Strings.i18n.tr("Well done! That''s it. \"{0}\" encrypted with the key {1} " +
				"is \"{2}\".", 
				Strings.caesarsName,
				DemoController.DEMO_ENCRYPTION_KEY,
				CryptoModel.getInstance().enc(DemoController.DEMO_ENCRYPTION_KEY, Strings.caesarsName));
	};
	
	/**
	 * It is said that the original Caesar cipher used the key 3.
	 */
	private static final Integer DEMO_ENCRYPTION_KEY = 3;
	
	/**
	 * Model for this controller.
	 */
	private DemoModel model;
	
	/**
	 * Instance to manipulate directly on actions.
	 */
	private TryEncryptCaesar tryEncryptCaesar;

	/**
	 * @param visualizationInfo Visualization info
	 */
	public DemoController(final AbstractVisualizationInfo visualizationInfo) {
		super(visualizationInfo);
	}

	/* (non-Javadoc)
	 * @see edu.kit.iks.cryptographicslib.controller.AbstractVisualizationController#routeAction(java.lang.String)
	 */
	@Override
    public final boolean routeAction(final String callerId) {
		switch (callerId) {
			case "exampleFirstLetter":
				this.exampleFirstLetter();
				break;
			case "solveFirstLetter":
				this.solveFirstLetter();
				break;
			case "tryNextLetter":
				this.tryNextLetter();
				break;
			case "tryNextLetterParameterized":
				String userInput = this.view().getUserInput();
				this.tryNextLetter(userInput);
				break;
			default:
				return false;
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see edu.kit.iks.cryptographicslib.controller.AbstractSteppableVisualizationController
	 * #loadView(edu.kit.iks.cryptographicslib.controller.AbstractSteppableVisualizationController.RunningOrderHelper)
	 */
	@Override
    public final void loadView(final RunningOrderHelper roh) {
		VariableHelper vh = new VariableHelper();
		
		vh.add("nextButtonLabel", DemoController.Strings.nextButtonLabel);
		vh.add("backButtonLabel", DemoController.Strings.backButtonLabel);
		vh.add("stepButtonLabel", DemoController.Strings.stepButtonLabel);
		
		this.view = new DemoView(this, vh.toList());
		this.model = new DemoModel(DemoController.Strings.caesarsName.toCharArray(),
		        DemoController.DEMO_ENCRYPTION_KEY);
		
		this.defineRunningOrder(roh);
	}

	/* (non-Javadoc)
	 * @see edu.kit.iks.CryptographicsLib.controller.AbstractController#unloadView()
	 */
	@Override
    public final void unloadView() {
		this.view = null;
		this.tryEncryptCaesar = null;
	}

	/* (non-Javadoc)
	 * @see edu.kit.iks.CryptographicsLib.controller.AbstractVisualizationController#getHelp()
	 */
	@Override
    public final String helpAction() {
		return this.view().getHelp();
	}
	
	/**
	 * Default Action.
	 * 
	 * Overridden, because the partial view will be reused for the
	 * next step, therefore the default behavior is undesired
	 * 
	 * @see edu.kit.iks.cryptographicslib.framework.controller.AbstractSteppableVisualizationController#indexAction()
	 */
	@Override
    protected final void indexAction() {
		this.view().setStepButtonAction("exampleFirstLetter");
		super.indexAction();
	}
	
	/**
	 * Action to demonstrate the encryption of the first letter of Caesars name.
	 */
	protected final void exampleFirstLetter() {
		this.view().setStepButtonAction("solveFirstLetter");
		this.defaultStepAction();
		
		this.tryEncryptCaesar.exampleFirstLetter(this.model);
	}
	
	/**
	 * Action to solve the encryption of the first letter of Caesars name.
	 */
	protected final void solveFirstLetter() {
		this.view().setStepButtonAction("tryNextLetter");
		
		this.tryEncryptCaesar.solveFirstLetter(this.model);
		
		this.model.next();
	}
	
	/**
	 * Action to let build the view to let the user try to encrypt the rest of Caesars name.
	 */
	protected final void tryNextLetter() {
		this.view().useCharKeyboard();
		this.view().setKeyboardAction("tryNextLetterParameterized");
		this.view().hideStepButton();
		this.view().showKeyboard();
		
		this.tryEncryptCaesar.encryptFirst(this.model);
	}
	
	/**
	 * Action to let the user try to encrypt the rest of Caesars name.
	 * 
	 * @param userInput Key the user pressed
	 */
	protected final void tryNextLetter(final String userInput) {
		this.tryEncryptCaesar.setInput(this.model.getCurrentPosition(), userInput);

		String expected = this.model.getCurrentCharEncrypted();
		
		if (userInput.equals(expected)) {
			this.tryEncryptCaesar.correctInput(this.model);
			if (this.model.next()) {
				this.tryEncryptCaesar.encryptNext(this.model);
			} else {
			    this.view().hideKeyboard();
				this.view().showStepButton();
				this.view().setDefaultStepButtonAction();
				this.defaultStepAction();
			}
		} else {
			this.tryEncryptCaesar.incorrectInput(this.model);
		}
	}

	/**
	 * Partial view initialization: Prepares view which displays Caesars idea.
	 * 
	 * @return Prepared view
	 */
	private CaesarsIdea prepareCaesarsIdea() {
		VariableHelper vh = new VariableHelper();
		
		vh.add("caesarsIdea", DemoController.Strings.caesarsIdea);
		vh.add("helpText", DemoController.Strings.helpCaesarsIdea);
		
		return new CaesarsIdea(vh.toList());
	}

	/**
	 * Partial view initialization: Prepares view which displays the demonstration.
	 * 
	 * @return Prepared view
	 */
	private TryEncryptCaesar prepareTryEncryptCeasar() {
		VariableHelper vh = new VariableHelper();
		
		vh.add("explanation1", DemoController.Strings.explanation1);
		vh.add("explanation2", DemoController.Strings.explanation2);
		vh.add("explanation3", DemoController.Strings.explanation3);
		vh.add("caesarsName", DemoController.Strings.caesarsName);
		vh.add("key", DemoController.DEMO_ENCRYPTION_KEY.toString());
		
		this.tryEncryptCaesar = new TryEncryptCaesar(vh.toList());
		
		return this.tryEncryptCaesar;
	}
	
	/**
	 * Partial view initialization: Prepares a view to congratulate the user for successfully encrypting
	 * Caesars name.
	 * 
	 * @return Prepared view
	 */
	private Result prepareResult() {
		VariableHelper vh = new VariableHelper();
		
		vh.add("result", DemoController.Strings.result);
		
		return new Result(vh.toList());
	}
	
	/**
	 * Helper to define the running order.
	 * 
	 * @param roh Running order helper
	 */
	private void defineRunningOrder(final RunningOrderHelper roh) {
		roh.enqueue(this.prepareCaesarsIdea());
		roh.enqueue(this.prepareTryEncryptCeasar());
		roh.enqueue(this.prepareResult());
	}
	
	/**
	 * Helper to cast the view.
	 * 
	 * @return casted view
	 */
	private DemoView view() {
		return (DemoView) this.view;
	}
}
