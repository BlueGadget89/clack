package TheIncredibles.clack.Cipher;

import java.util.Locale;

/**
 * CaesarCipher class provides methods to encrypt and decrypt messages using the Caesar cipher technique.
 * The class supports an optional custom alphabet and handles case sensitivity by converting all input to uppercase.
 */
public class CaesarCipher {
    private String alphabet;
    private int key;
    public static String DEFAULT_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Constructor to initialize the CaesarCipher with a key and an optional alphabet.
     *
     * @param key The number of positions to shift the characters.
     * @param alphabet The alphabet to use for the cipher. Defaults to "ABCDEFGHIJKLMNOPQRSTUVWXYZ" if null.
     */
    public CaesarCipher(int key, String alphabet) {
        this.key = key;
        this.alphabet = (alphabet != null) ? alphabet : DEFAULT_ALPHABET;
    }


    /**
     * Constructor to initialize the CaesarCipher with a key and an optional alphabet.
     *
     * @param key The number of positions to shift the characters.
     */
    public CaesarCipher(int key) {
        this.key = key;
        this.alphabet = DEFAULT_ALPHABET;
    }

    /**
     * Method that is used for encrypting messages using Caeser
     *
     * Cipher text can include special characters like "?", "$", "!" etc, the
     * special characters just won't be encrypted, they will be returned in
     * in the encrypted message as is.
     *
     * The method is case-sensitive, so it will carry over the specific case of
     * the letters in the string.
     *
     * EX -> 'Dog!' (with standard alphabet and a key of '3') would return 'Grj!'.
     *
     * @param cleartext The text to be encrypted.
     * @return The encrypted ciphertext.
     */
    public String encrypt(String cleartext) {
        String alphabetLower = this.alphabet.toLowerCase(Locale.ENGLISH);
        StringBuilder ciphertext = new StringBuilder();

        // Just creating an upper case version of the text to make it
        // easier to check to see if the chars in the text are in the
        // alphabet
        String cleartextUpper = cleartext.toUpperCase();

        for (int i = 0; i < cleartext.length(); i++) {
            int index = this.alphabet.indexOf(cleartextUpper.charAt(i));    // Use uppercase for index lookup
            if (index != -1) {
                int newIndex = (index + key) % this.alphabet.length();
                if (Character.isUpperCase(cleartext.charAt(i))) {           // Use uppercase alphabet
                    ciphertext.append(this.alphabet.charAt(newIndex));
                } else {                                                    // Use lowercase alphabet
                    ciphertext.append(alphabetLower.charAt(newIndex));
                }
            } else {
                ciphertext.append(cleartext.charAt(i));
            }
        }
        return ciphertext.toString();
    }

    /**
     * Decrypts the given ciphertext using the Caesar cipher.
     *
     * Cipher text can include special characters like "?", "$", "!", " " etc, the
     * special characters just won't be encrypted, they will be returned in
     * in the encrypted message as is.
     *
     * The method is case-sensitive, so it will carry over the specific case of
     * the letters in the string.
     *
     * EX -> 'Grj!' (with standard alphabet and a key of '3') would return 'Dog!'.
     *
     * @param ciphertext The text to be decrypted.
     * @return The decrypted cleartext.
     */
    public String decrypt(String ciphertext) {
        StringBuilder cleartext = new StringBuilder();
        String alphabetLower = this.alphabet.toLowerCase(Locale.ENGLISH);

        // Just creating an upper case version of the text to make it
        // easier to check to see if the chars in the text are in the
        // alphabet
        String ciphertextUpper = ciphertext.toUpperCase();

        for (int i = 0; i < ciphertext.length(); i++) {
            int index = this.alphabet.indexOf(ciphertextUpper.charAt(i));   // Use uppercase for index lookup
            if (index != -1) {
                int newIndex = (index - key + this.alphabet.length()) % this.alphabet.length();
                if (Character.isUpperCase(ciphertext.charAt(i))) {
                    cleartext.append(this.alphabet.charAt(newIndex));       // Use uppercase alphabet
                } else {
                    cleartext.append(alphabetLower.charAt(newIndex));       // Use lowercase alphabet
                }
            } else {
                cleartext.append(ciphertext.charAt(i));                     // Append non-alphabet characters as is
            }
        }
        return cleartext.toString();
    }


    /**
     * getter for the objects alphabet
     *
     * @return The alphabet that is being used
     */
    public String getAlphabet() {return this.alphabet;}
}


