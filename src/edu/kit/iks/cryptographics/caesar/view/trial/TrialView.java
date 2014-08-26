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

package edu.kit.iks.cryptographics.caesar.view.trial;

import java.awt.event.ActionListener;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import edu.kit.iks.cryptographicslib.framework.view.AbstractVisualizationView;

/**
 * @author Christian Dreher
 *
 */
public class TrialView extends AbstractVisualizationView {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1744518643503441631L;

    /**
     * @param al
     * @param variables
     */
    public TrialView(ActionListener al, List<SimpleEntry<String, String>> variables) {
        super(al, variables);
        
        this.setNextButton(this.getVariableValue("nextButtonLabel"));
        this.setBackButton(this.getVariableValue("backButtonLabel"));
        this.setStepButton(this.getVariableValue("skipButtonLabel"));
    }
}
