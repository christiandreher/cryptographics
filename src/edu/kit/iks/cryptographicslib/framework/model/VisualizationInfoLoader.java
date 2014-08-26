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

package edu.kit.iks.cryptographicslib.framework.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a registry where all {VisualisationInfo}-objects are 
 * accessible through the static {loadAllVisualizationInfo()}-method.
 * Objects of this class cannot be instantiated.
 * 
 * @author Christian Dreher
 */
public final class VisualizationInfoLoader {
    
    /**
     * Private constructor to prevent this class from being instantiated.
     */
    private VisualizationInfoLoader() {
        
    }

    /**
     * Returns a list of all {VisualizationInfo}-objects .
     * 
     * @return A list of all {VisualizationInfo}-objects 
     */
    public static List<AbstractVisualizationInfo> loadAllVisualizationInfos() {
        List<AbstractVisualizationInfo> visualizationInfos = new ArrayList<AbstractVisualizationInfo>();
        
        visualizationInfos.add(new edu.kit.iks.cryptographics.caesar.model.VisualizationInfo());
        visualizationInfos.add(new edu.kit.iks.cryptographics.onetimepad.VisualizationInfo());
        visualizationInfos.add(new edu.kit.iks.cryptographics.aes.VisualizationInfo());
        visualizationInfos.add(new edu.kit.iks.cryptographics.sha2.VisualizationInfo());
        
        return visualizationInfos;
    }
}
