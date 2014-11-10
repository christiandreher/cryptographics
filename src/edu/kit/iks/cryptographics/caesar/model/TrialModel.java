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

package edu.kit.iks.cryptographics.caesar.model;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.cryptographicslib.util.Configuration;
import edu.kit.iks.cryptographicslib.util.Random;

/**
 * @author Christian Dreher
 *
 */
public class TrialModel {
    
    /**
     * Localized strings used in this view.
     * 
     * @author Christian Dreher <uaeef@student.kit.edu>
     */
    private static class Strings {
        
        /**
         * Localization instance.
         */
        private static I18n i18n = Configuration.getInstance().getI18n(TrialModel.class);
        
        // Navigation labels and various
        private static String[] names = {
            Strings.i18n.tr("THOMAS"),
            Strings.i18n.tr("PETER"),
            Strings.i18n.tr("MANUEL"),
            Strings.i18n.tr("CLARA"),
            Strings.i18n.tr("STEFAN"),
            Strings.i18n.tr("KRISTINA"),
            Strings.i18n.tr("ANNIKA"),
            Strings.i18n.tr("SONJA"),
        };
    };
    
    private int currentPosition = 0;
    
    /**
     * Name the user entered, or a random one, if he didn't set one
     */
    private String name = "";
    
    /**
     * Keys below 5 are not very challenging, but 8 is enough
     */
    public static int key = Random.integer(5, 8);
    
    public void useRandomName() {
        int max = TrialModel.Strings.names.length - 1;
        this.name = TrialModel.Strings.names[Random.integer(0, max)];
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean next() {
        this.currentPosition++;
        
        if (this.currentPosition >= this.name.length()) {
            return false;
        }
        
        return true;
    }
    
    public String getCurrentChar() {
        char[] nameCharArray = this.name.toCharArray();
        
        return nameCharArray[this.currentPosition] + "";
    }
    
    public String getCurrentCharEncrypted() {
        String currentChar = this.getCurrentChar();
        
        return CryptoModel.getInstance().enc(TrialModel.key, currentChar);
    }
    
    public int getCurrentPosition() {
        return this.currentPosition;
    }
}
