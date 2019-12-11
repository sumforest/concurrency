package com.sen.classloader.chapter3;

/**
 * @Auther: Sen
 * @Date: 2019/12/12 00:21
 * @Description:
 */
public class EncryptTest {

    private final static byte ENCRYPT_SEED = (byte) 0xff;

    private static String source = "Hello World";
    public static void main(String[] args) {
        byte[] encrypt = new byte[source.length()];
        for (int i = 0; i < encrypt.length; i++) {
            encrypt[i] = (byte) (source.charAt(i) ^ ENCRYPT_SEED);
        }
        System.out.println(new String(encrypt));

        byte[] decrypt = new byte[encrypt.length];
        for (int i = 0; i < decrypt.length; i++) {
            decrypt[i] = (byte) (encrypt[i] ^ ENCRYPT_SEED);
        }
        System.out.println(new String(decrypt));
    }
}
