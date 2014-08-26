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

package edu.kit.iks.cryptographics.caesar.view.demo.partial;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JTextPane;

import edu.kit.iks.cryptographics.caesar.model.DemoModel;
import edu.kit.iks.cryptographicslib.common.view.partial.AlphabetStripView;
import edu.kit.iks.cryptographicslib.common.view.partial.EncryptDecryptView;
import edu.kit.iks.cryptographicslib.framework.view.partial.AbstractPartialView;

/**
 * @author Christian Dreher <uaeef@student.kit.edu>
 *
 */
public class TryEncryptCaesar extends AbstractPartialView {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -6007354174035731558L;

	/**
	 * Text area to display the currently needed explanation.
	 */
	private JTextPane explanation = null;
	
	/**
	 * View to encrypt Caesars name letter by letter.
	 */
	private EncryptDecryptView encryptCaesarView;
	
	/**
	 * View to display an alphabet strip with numbers to help the user.
	 */
	private AlphabetStripView alphabetStripView;
	
	/**
	 * @param variables
	 */
	public TryEncryptCaesar(List<SimpleEntry<String, String>> variables) {
		super(variables);
	}
	
	/* (non-Javadoc)
	 * @see edu.kit.iks.cryptographicslib.views.partials.AbstractPartialView#preparePartialView()
	 */
	@Override
	public void preparePartialView() {
		int explanationId = this.addText(this.getVariableValue("explanation1"));
		this.explanation = (JTextPane) this.getElement(explanationId);
		
		String caesarsName = this.getVariableValue("caesarsName");
		String key = this.getVariableValue("key");
		
		this.encryptCaesarView = new EncryptDecryptView(caesarsName, key);
		this.alphabetStripView = new AlphabetStripView();
		
		this.addElement(this.encryptCaesarView);
		this.addElement(this.alphabetStripView);
	}
	
	/**
	 * Restructures the view to highlight the first letter of Caesars name.
	 * 
	 * @param demoModel Current DemoModel instance
	 */
	public void exampleFirstLetter(DemoModel demoModel) {
		this.encryptCaesarView.highlightLabel(demoModel.getCurrentPosition());
		this.alphabetStripView.highlightBlue(demoModel.getCurrentChar());
	}
	
	/**
	 * Restructures the view to solve the encryption of the first letter of Caesars name as
	 * demonstration.
	 * 
	 * @param demoModel Current DemoModel instance
	 */
	public void solveFirstLetter(DemoModel demoModel) {
		this.explanation.setText(this.getVariableValue("explanation2"));
		JTextField firstLetter = this.encryptCaesarView.getInput(0);
		firstLetter.setText(demoModel.getCurrentCharEncrypted());
		
		this.encryptCaesarView.highlightInputInfo(demoModel.getCurrentPosition());
		this.alphabetStripView.highlightBlue(demoModel.getCurrentCharEncrypted());
		
		this.revalidate();
	}
	
	/**
	 * Restructures the view to let the user encrypt the rest of Caesars name.
	 * 
	 * @param demoModel Current DemoModel instance
	 */
	public void encryptFirst(DemoModel demoModel) {
		this.explanation.setText(this.getVariableValue("explanation3"));
		this.encryptCaesarView.highlightInputSuccess(0);
		this.encryptNext(demoModel);
	}
	
	/**
	 * Restructures the view to immediately jump to the next letter if the input was
	 * correct.
	 * 
	 * @param demoModel Current DemoModel instance
	 */
	public void encryptNext(DemoModel demoModel) {
		this.encryptCaesarView.enable(demoModel.getCurrentPosition());
		this.alphabetStripView.unHighlightAll();
		this.alphabetStripView.highlightBlue(demoModel.getCurrentChar());
		this.revalidate();
	}
	
	/**
	 * Sets the user input into the view as feedback after pressing keyboard keys.
	 * 
	 * @param position Position of the letter to be currently encrypted
	 * @param userInput User input
	 */
	public void setInput(int position, String userInput) {
		this.encryptCaesarView.getInput(position).setText(userInput);
	}
	
	/**
	 * Restructures the view to let the user know that his input was correct.
	 */
	public void correctInput(DemoModel demoModel) {
	    this.encryptCaesarView.highlightInputSuccess(demoModel.getCurrentPosition());
	}
	
	/**
	 * Restructures the view to let the user know that his input was incorrect.
	 */
	public void incorrectInput(DemoModel demoModel) {
	    this.encryptCaesarView.highlightInputError(demoModel.getCurrentPosition());
	}
}
