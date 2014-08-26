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

package edu.kit.iks.cryptographics.caesar.view.trial.partial;

import java.awt.Dimension;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import javax.swing.JTextField;

import edu.kit.iks.cryptographicslib.framework.view.partial.AbstractPartialView;
import edu.kit.iks.cryptographicslib.util.Highlighter;

/**
 * @author Christian Dreher
 *
 */
public class EnterName extends AbstractPartialView {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -4431691560844814000L;

    private JTextField nameInput;
    
    /**
     * @param variables
     */
    public EnterName(List<SimpleEntry<String, String>> variables) {
        super(variables);
    }

    /* (non-Javadoc)
     * @see edu.kit.iks.cryptographicslib.framework.view.partial.AbstractPartialView#preparePartialView()
     */
    @Override
    public void preparePartialView() {
        this.addText(this.getVariableValue("explanation"));
        this.initNameInput();
        this.addElement(this.nameInput);
    }
    
    private void initNameInput() {
        this.nameInput = new JTextField();
        this.nameInput.setPreferredSize(new Dimension(400, 30));
        Highlighter.normal(this.nameInput);
    }
}
