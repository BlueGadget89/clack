package Tests;

import TheIncredibles.clack.Cipher.CaesarCipher;

public class CipherTester
{
    public static void main(String[] args)
    {

        String customAlphabet = "XYZABCDEFGHIJKLMNOPQRSTUVW";
        CaesarCipher cipher = new CaesarCipher(3, customAlphabet);
        String cleartext = "EBIIL TLOIA";
        String decrpt = cipher.decrypt(cleartext);
        System.out.println(cipher.getAlphabet());
        S