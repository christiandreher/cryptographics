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
package edu.kit.iks.cryptographics.caesar.view.intro.partial;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import edu.kit.iks.cryptographicslib.framework.view.partial.AbstractPartialView;

/**
 * @author Christian Dreher <uaeef@student.kit.edu>
 *
 */
public class CaesarsConquerPlan extends AbstractPartialView {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -2519713164017668442L;

	/**
	 * @param variables
	 */
	public CaesarsConquerPlan(List<SimpleEntry<String, String>> variables) {
		super(variables);
	}

	/* (non-Javadoc)
	 * @see edu.kit.iks.cryptographicslib.views.partials.AbstractPartialView#preparePartialView()
	 */
	@Override
	public void preparePartialView() {
		this.addImageFromResource("CaesarIdea");
		
		this.addText(this.getVariableValue("caesarsConquerPlan"));
	}
}
