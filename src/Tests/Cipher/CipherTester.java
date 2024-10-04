package Tests.Cipher;

import TheIncredibles.clack.Cipher.CaesarCipher;

public class CipherTester {
    public static void main(String[] args) {

        String customAlphabet = "XYZABCDEFGHIJKLMNOPQRSTUVW";
        CaesarCipher cipher = new CaesarCipher(3, customAlphabet);
        String cleartext = "Wklv lv d whvw phvvdjh";
        String decrpt = cipher.encrypt(cleartext);
        System.out.println(decrpt);
        String de = cipher.decrypt(cleartext);
        System.out.println(de);
    }
}