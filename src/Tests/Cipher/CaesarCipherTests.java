package Tests.Cipher;

import TheIncredibles.clack.Cipher.CaesarCipher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CaesarCipherTests {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    public void restoreSystemOutput() {
        System.setOut(originalOut);
    }

    private void printInputOutput(String input, String output) {
        System.out.println("Input: " + input);
        System.out.println("Output: " + output);
    }

    @Test
    public void testEncryptWithDefaultAlphabet() {
        CaesarCipher cipher = new CaesarCipher(3);
        String cleartext = "Hello World";
        String expectedCiphertext = "Khoor Zruog";
        String actualCiphertext = cipher.encrypt(cleartext);
        assertEquals(expectedCiphertext, actualCiphertext);
    }

    @Test
    public void testDecryptWithDefaultAlphabet() {
        CaesarCipher cipher = new CaesarCipher(3);
        String ciphertext = "KHOOR ZRUOG";
        String expectedCleartext = "HELLO WORLD";
        String actualCleartext = cipher.decrypt(ciphertext);
        assertEquals(expectedCleartext, actualCleartext);
    }

    @Test
    public void testEncryptWithCustomAlphabet() {
        String customAlphabet = "XYZABCDEFGHIJKLMNOPQRSTUVW";
        CaesarCipher cipher = new CaesarCipher(3, customAlphabet);
        String cleartext = "This is a test message.";
        String expectedCiphertext = "Wklv lv d whvw phvvdjh.";
        String actualCiphertext = cipher.encrypt(cleartext);
        assertEquals(expectedCiphertext, actualCiphertext);
    }

    @Test
    public void testDecryptWithCustomAlphabet() {
        String customAlphabet = "XYZABCDEFGHIJKLMNOPQRSTUVW";
        CaesarCipher cipher = new CaesarCipher(3, customAlphabet);
        String ciphertext = "Wklv lv d whvw phvvdjh.";
        String expectedCleartext = "This is a test message.";
        String actualCleartext = cipher.decrypt(ciphertext);
        assertEquals(expectedCleartext, actualCleartext);
    }

    @Test
    public void testNonAlphabetCharacters() {
        CaesarCipher cipher = new CaesarCipher(3);
        String cleartext = "HELLO, WORLD!";
        String expectedCiphertext = "KHOOR, ZRUOG!";
        String actualCiphertext = cipher.encrypt(cleartext);
        assertEquals(expectedCiphertext, actualCiphertext);
    }

    @Test
    public void testEmptyString() {
        CaesarCipher cipher = new CaesarCipher(3);
        String cleartext = "";
        String expectedCiphertext = "";
        String actualCiphertext = cipher.encrypt(cleartext);
        assertEquals(expectedCiphertext, actualCiphertext);
        assertEquals(cleartext, cipher.decrypt(expectedCiphertext));
    }
}

