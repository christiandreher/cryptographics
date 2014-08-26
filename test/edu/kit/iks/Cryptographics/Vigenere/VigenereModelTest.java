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

package edu.kit.iks.Cryptographics.Vigenere;

import org.junit.Test;

import edu.kit.iks.cryptographics.Vigenere.VigenereModel;
import junit.framework.TestCase;

public class VigenereModelTest extends TestCase {
	@Test
	public void testEnc() {
		char test = VigenereModel.enc(1, 'B');
		assertEquals('C',test);
	}
	
	@Test
	public void testDec() {
		char test = VigenereModel.dec('B', 'A');
		assertEquals('Y',test);
	}
	
	@Test
	public void testCharacterToInt() {
		for (int i = 0; i < 26; i++) {
			char c = (char)('A' + i);
			String str = "" + c;
			int result = VigenereModel.characterToInt(str);
			assertEquals(i + 1, result);
		}
	}

	@Test
	public void testInvalidCharacterToInt() {
		int result = VigenereModel.characterToInt("AY");
		assertEquals(0, result);
		
		result = VigenereModel.characterToInt("$");
		assertEquals(0, result);
		
		result = VigenereModel.characterToInt("}");
		assertEquals(0, result);
	}
	
	@Test
	public void testGetCharPositionated() {
		String temp = VigenereModel.getCharPositionated(0, 2, "HAALLLLOO");
		assertEquals("HALLO", temp);
	}

}
