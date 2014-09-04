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

package edu.kit.iks.Cryptographics.DiffieHellman;

import java.awt.Color;

/**
 * The Model will keep some of our colors
 * need in the Diffie-Hellman Key-Exchange Analogy.
 * 
 * @author kai
 *
 */

public class Model {
	
	/**
	 * Our public color
	 */
	private Color publicColor;
	
	/**
	 * Alices private color
	 */
	private Color alicePrivateColor;
	
	/**
	 * Alices mixed color
	 */
	private Color aliceMixedColor;
	
	/**
	 * Bobs private color
	 */
	private Color bobPrivateColor;
	
	/**
	 * Bobs mixed color
	 */
	private Color bobMixedColor;

	/**
	 * Constructor is empty, because
	 * values should be initialized with setter
	 */
	public Model() {
	}
	
	/**
	 * return the public color
	 * @return the public color
	 */
	public Color getPublicColor() {
		return publicColor;
	}

	/**
	 * set the public color
	 * @param publicColor which color to set to
	 */
	public void setPublicColor(Color publicColor) {
		this.publicColor = publicColor;
	}

	/**
	 * return alices private color
	 * @return alices private color
	 */
	public Color getAlicePrivateColor() {
		return alicePrivateColor;
	}

	/**
	 * set alices private color
	 * @param alicePrivateColor
	 */
	public void setAlicePrivateColor(Color alicePrivateColor) {
		this.alicePrivateColor = alicePrivateColor;
	}
	
	/**
	 * return alices mixed color
	 * @return alices mixed color, if it was set/mixed
	 */
	public Color getAliceMixedColor() {
		return aliceMixedColor;
	}

	/**
	 * return bobs private color
	 * @return bobs private color
	 */
	public Color getBobPrivateColor() {
		return bobPrivateColor;
	}

	/**
	 * set bobs private color
	 * @param bobPrivateColor the value to set bobs private color
	 */
	public void setBobPrivateColor(Color bobPrivateColor) {
		this.bobPrivateColor = bobPrivateColor;
	}

	/**
	 * return bobs mixed color if it was computed
	 * @return bobs mixed color
	 */
	public Color getBobMixedColor() {
		return bobMixedColor;
	}
	
	/**
	 * compute mixture of alices private and public color
	 * and save the color
	 */
	public void mixAlicePrivateAndPublic() {
		this.aliceMixedColor = computeMixedColor(this.alicePrivateColor, this.publicColor);
	}
	
	/**
	 * compute mixture of bobs private and public color
	 * and save the color
	 */
	public void mixBobPrivateAndPublic() {
		this.bobMixedColor = computeMixedColor(this.bobPrivateColor, publicColor);
	}
	
	/**
	 * compute mixture from two colors
	 * @param color the first color
	 * @param color2 the second color
	 * @return the mixture
	 */
	private Color computeMixedColor(Color color, Color color2) {
		int r1 = color.getRed();
		int r2 = color2.getRed();
		int g1 = color.getGreen();
		int g2 = color2.getGreen();
		int b1 = color.getBlue();
		int b2 = color2.getBlue();
		return new Color((r1+r2)/2, (g1+g2)/2, (b1+b2)/2);
	}
}
