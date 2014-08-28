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

package edu.kit.iks.cryptographicslib.common.view.partial;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.kit.iks.cryptographicslib.util.Highlighter;

/**
 * @author Christian Dreher
 *
 */
public class InputView extends JPanel {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -4953854712887306326L;

    private JTextField input;
    
    public InputView() {
        super(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.ipadx = 20;
        gbc.ipady = 20;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 25, 25, 25);
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        this.input = new JTextField();
        this.input.setHorizontalAlignment(JTextField.CENTER);
        this.input.setPreferredSize(new Dimension(300, 25));
        Highlighter.normal(this.input);
        
        this.add(this.input, gbc);
        this.revalidate();
    }
    
    public String getValue() {
        return this.input.getText();
    }
    
    public void setValue(String value) {
        this.input.setText(value);
    }
    
    public void focus() {
        this.input.requestFocus();
    }
}
