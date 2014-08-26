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

import org.junit.Before;
import org.junit.Test;

import edu.kit.iks.cryptographics.DiffieHellman.Model;
import junit.framework.TestCase;

public class ModelTest extends TestCase  {
	private Model model;
	
	@Before
	public void setUp() throws Exception {
		this.model = new Model();
	}
	
	@Test
	public void testMixAlicePrivateAndPublic() {
		Color publicColor = Color.RED;
		Color privateColor = Color.BLUE;
		
		this.model.setPublicColor(publicColor);
		assertEquals(this.model.getPublicColor(), publicColor);
		this.model.setAlicePrivateColor(privateColor);
		assertEquals(this.model.getAlicePrivateColor(), privateColor);
		
		this.model.mixAlicePrivateAndPublic();
		Color expected = new Color(127, 0, 127);
		Color actual = this.model.getAliceMixedColor();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testMixBobPrivateAndPublic() {
		Color publicColor = Color.YELLOW;
		Color privateColor = Color.PINK;
		
		this.model.setPublicColor(publicColor);
		assertEquals(this.model.getPublicColor(), publicColor);
		this.model.setBobPrivateColor(privateColor);
		assertEquals(this.model.getBobPrivateColor(), privateColor);
		
		this.model.mixBobPrivateAndPublic();
		Color expected = new Color(255, 215, 87);
		Color actual = this.model.getBobMixedColor();
		assertEquals(expected, actual);
	}
}
