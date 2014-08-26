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

package edu.kit.iks.cryptographics.caesar.model;

import org.xnap.commons.i18n.I18n;

import edu.kit.iks.cryptographicslib.util.Configuration;

/**
 * Model for the visualization of Caesar's cipher.
 * 
 * @author Wasilij Beskorovajnov
 */
public class CryptoModel {

	// makes sure only one instance is being generated.
	private static final CryptoModel model = new CryptoModel();

	// ASCII lower case a code.
	public final int ASCII_LC_A = 'a';

	// ASCII upper case A code.
	public final int ASCII_UC_A = 'A';

	// The reach of the key interval.
	private final int MODULO = 26;

	private static I18n i18n = Configuration.getInstance().getI18n(CryptoModel.class);

	public static CryptoModel getInstance() {
		return CryptoModel.model;
	}

	/**
	 * Constructor.
	 */
	private CryptoModel() {

	}

	/**
	 * Function for encrypting of all sort of String.<br>
	 * CAREFUL: If you want to encrypt html Strings, make sure all tags are closed!!!!<br>
	 * 
	 * @param key
	 *            the key parameter for encryption.
	 * @param text
	 *            the text to encrypt.
	 * @return the encrypted text.
	 */
	public String enc(int key, String text) {
		String cipher = "";
		for (int i = 0; i < text.length(); i++) {
			Character c = text.charAt(i);

			if (Character.isLetter(c)) {

				cipher += String.valueOf(this.shiftChar(key, c));

			} else {
				if (c != '<') {
					cipher += c;
				} else {
					cipher += c;
					while (c != '>') {
						i++;
						c = text.charAt(i);
						cipher += c;
					}
				}
			}

		}
		return cipher;

	}

	/**
	 * Function for decrypting all sort of Strings.
	 * 
	 * @param key
	 *            the key parameter for decryption.
	 * @param cipher
	 *            the cipher to decrypt.
	 * @return decrypted cipher.
	 */
	public String dec(int key, String cipher) {
		return this.enc(-key, cipher);
	}

	/**
	 * Generates a random Integer in the range of 1 and 26.
	 * 
	 * @return the generated key.
	 */
	public int generateKey() {
		return this.generateRandomInt(1, 26);
	}

	/**
	 * Generates a random integer in an interval of a and b.
	 * 
	 * @param a
	 *            the left border of the interval.
	 * @param b
	 *            the right border of the interval.
	 * @return the generated integer.
	 */
	public int generateRandomInt(int a, int b) {
		return (int) (a + ((b - a) * Math.random()));
	}

	/**
	 * Pulls 'randomly' a string from the local pool.
	 * 
	 * @return a random string.
	 */
	public String genRandomBlamings() {
		String[] blamingPool = {
				CryptoModel.i18n.tr("Oh no. What a pity! It went wrong!"),
				CryptoModel.i18n.tr("No my friend. This one doesn't work!"),
				CryptoModel.i18n
						.tr("Sorry, that was wrong. Don't give up now!") };
		int index = this.generateRandomInt(0, blamingPool.length);
		return blamingPool[index];
	}

	/**
	 * Pulls 'randomly' a plainText from the local pool of genRandomPlainSequence() and encrypts it
	 * with a given key.
	 * 
	 * @param key
	 * @return 'random' cipher.
	 */
	public String genRandomCipher(int key) {
		String plain = this.genRandomPlainSequence();
		return this.enc(key, plain);
	}

	/**
	 * Pulls 'randomly' a string from the local pool.
	 * 
	 * @return 'random' string.
	 */
	public String genRandomGrats() {
		String[] gratulationsPool = {
				CryptoModel.i18n.tr("Great work, oh mighty Caesar!"),
				CryptoModel.i18n.tr("Very nice. I like!"),
				CryptoModel.i18n.tr("No one could've done it better!") };
		int index = this.generateRandomInt(0, gratulationsPool.length);
		return gratulationsPool[index];
	}

	/**
	 * Pulls 'randomly' string from the local pool.
	 * 
	 * @return 'random' string.
	 */
	public String genRandomPlainSequence() {
		String[] plainTextPool = {
		        CryptoModel.i18n.tr("ANNA"),
				CryptoModel.i18n.tr("HANNAH"), CryptoModel.i18n.tr("BANANA"),
				CryptoModel.i18n.tr("KOKOS"), CryptoModel.i18n.tr("SECRET"),
				CryptoModel.i18n.tr("EPSILON")
		};

		int index = this.generateRandomInt(0, plainTextPool.length);
		return plainTextPool[index];
	}

	/**
	 * Pulls 'randomly' a bigger string from the local pool.
	 * 
	 * @return the 'random' text.
	 */
	public String genRandomText() {
		String[] textPool = {
		        CryptoModel.i18n.tr("The diagram you see here shows the frequency of each letter"
								+ " in the text you are reading at the moment. It is called a"
								+ " Histogram. If you would count all E's in this explanation"
								+ " you would get the number you see in the diagram on the column"
								+ " above the letter E. Now the program will encrypt this explanation"
								+ " with an unknown key in a most awesome way and we will see the "
								+ " histogram of the cipher. Click Proceed and see the magic!")
		};
		
		return textPool[0];
	}

	/**
	 * Checks if the input is valid.
	 * 
	 * @param input
	 *            the input to check.
	 */
	public boolean isInputValid(String input) {
		return (input.length() < 10 && input.length() > 0);
	}

	/**
	 * Checks if the key is valid.
	 * 
	 * @param key
	 *            the key to check.
	 * @return
	 */
	public boolean isKeyValid(int key) {
		return (key > 0 && key <= this.MODULO);
	}

	private char shiftChar(int key, char charToShift) {
		int offset = charToShift - this.ASCII_UC_A;
		if (Character.isLowerCase(charToShift)) {
			offset = charToShift - this.ASCII_LC_A;
			return (char) ((((offset + this.MODULO) + key) % this.MODULO) + this.ASCII_LC_A);
		}
		return (char) ((((offset + this.MODULO) + key) % this.MODULO) + this.ASCII_UC_A);
	}
}
