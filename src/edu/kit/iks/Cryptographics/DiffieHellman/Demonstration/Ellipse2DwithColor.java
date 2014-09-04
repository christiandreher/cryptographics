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

package edu.kit.iks.Cryptographics.DiffieHellman.Demonstration;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

/**
 * wrapper for Ellipse2d so that it
 * holds also a color
 * @author kai
 *
 */

public class Ellipse2DwithColor extends Ellipse2D.Double {
	
	private static final long serialVersionUID = 8676966439493063852L;
	
	/** the color the ellipse2d has */
	private Color color;
	
	/**
	 * Use default color
	 * @param x see ellipse2d
	 * @param y see ellipse2d
	 * @param w see ellipse2d
	 * @param h see ellipse2d
	 */
	public Ellipse2DwithColor(double x, double y, double w, double h) {
		super(x, y, w, h);
		//default color
		this.color = Color.BLACK;
	}
	
	/**
	 * The same as ellipse2d, just use also a color
	 * @param x see ellipse2d
	 * @param y see ellipse2d
	 * @param w see ellipse2d
	 * @param h see ellipse2d
	 * @param color the color to use
	 */
	public Ellipse2DwithColor(double x, double y, double w, double h, Color color) {
		super(x, y, w, h);
		this.color = color;
	}
	
	/**
	 * return the color
	 * @return the color
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * set the color of the ellipse
	 * @param color2 the color to set to
	 */
	public void setColor(Color color2) {
		this.color = color2;
	}

}
