package Tests;

import TheIncredibles.clack.Cipher.CaesarCipher;

public class CipherTester
{
    public static void main(String[] args)
    {

        CaesarCipher cipher = new CaesarCipher(26);
        String cleartext = "HELLO WORLD";
        String encrpt = cipher.encrypt(cleartext);
        System.out.println(encrpt);
        System.out.println(encrpt);
        System.out.println(cipher.decrypt(cleartext));

    }
}
