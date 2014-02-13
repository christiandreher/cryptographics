package edu.kit.iks.Cryptographics.Caesar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.kit.iks.Cryptographics.Caesar.Experiment.CryptoModel;

/**
 * JUnit testcase class for testing the functionality of the model in the visualization of Caesar.
 * 
 * @author Wasilij Beskorovajnov.
 * 
 */
public class CryptoModelTest {
	// Model to test.
	private CryptoModel modelTT;

	private final String MESSAGE_HEADER = "[JUnit] Testing the functionality of ";

	@Before
	public void setUp() throws Exception {
		this.modelTT = CryptoModel.getInstance();
	}

	@Test
	public void testEnc() {
		String alphabet =     "!!!ABcdEFghIJKlmNOPqrSTUvwXYZ!!!";
		String expected = "!!!FGhiJKlmNOPqrSTUvwXYZabCDE!!!";
		String actual = this.modelTT.enc(5, alphabet);
		assertEquals(MESSAGE_HEADER + "enc(String)\n", expected, actual);
	}

	@Test
	public void testDec() {
		String cipher = "!!!KLmnOPqrSTUvwXYZabCDEfgHIJ!!!";
		String expected = "!!!ABcdEFghIJKlmNOPqrSTUvwXYZ!!!";
		String actual = this.modelTT.dec(10, cipher);
		assertEquals(MESSAGE_HEADER + "dec(String)\n", expected, actual);
	}

	@Test
	public void testIsKeyValid() {
		int[] validKeys = { 1, 13, 26 };
		int[] invalidKeys = { -25, 0, 27 };

		for (int validKey : validKeys) {
			assertTrue(MESSAGE_HEADER + "isKeyValid()\n",
					this.modelTT.isKeyValid(validKey));
		}

		for (int invalidKey : invalidKeys) {
			assertFalse(MESSAGE_HEADER + "isKeyValid()\n",
					this.modelTT.isKeyValid(invalidKey));
		}
	}

	@Test
	public void testIsInputValid() {
		String[] validInputArr = { "A", "ABCD", "ABCDFHGRT" };
		String[] invalidInputArr = { "", "ABCDHFGRTZ", "ABDGFHRTEZSHD" };
		for (String validInput : validInputArr) {
			assertTrue(MESSAGE_HEADER + "isInputValid()\n",
					this.modelTT.isInputValid(validInput));
		}
		for (String invalidInput : invalidInputArr) {
			assertFalse(MESSAGE_HEADER + "isInputValid()\n",
					this.modelTT.isInputValid(invalidInput));
		}
	}

	@Test
	public void testGenRandomGrats() {
		for (int i = 0; i < 50; i++) {
		assertNotNull(this.modelTT.genRandomGrats());
		}
	}

	@Test
	public void testGenRandomBlamings() {
		for (int i = 0; i < 50; i++) {
		assertNotNull(this.modelTT.genRandomBlamings());
		}
	}

	@Test
	public void testGenRandomPlainSequence() {
		for (int i = 0; i < 50; i++) {
		assertNotNull(this.modelTT.genRandomPlainSequence());
		}
	}

	@Test
	public void testGenRandomCipher() {
		for (int i = 0; i < 50; i++) {
		assertNotNull(this.modelTT.genRandomCipher(i));
		}
	}

	@Test
	public void testGenRandomText() {
		assertNotNull(this.modelTT.genRandomText());
		
	}

	@Test
	public void testGenerateKey() {
		int key = 0;
		for (int i = 0; i < 99; i++) {
			key = this.modelTT.generateKey();
			if (key > 0 && key < 27) {
				assertTrue(true);
			} else {
				assertTrue(false);
			}
		}
	}

}
