package com.sen.classloader.chapter3;

import java.io.*;

/**
 * @Auther: Sen
 * @Date: 2019/12/12 00:30
 * @Description:
 */
public final class EncryptUtil {

    private final static byte ENCRYPT_SEED = (byte) 0xff;

    private final static String DEFAULT_DIR = "C:\\Users\\Sen\\OneDrive\\桌面\\MyClassLoader\\classloader2\\";

    private EncryptUtil() {

    }

    public static void encrypt(String source, String target) {
        File file = new File(DEFAULT_DIR, source);
        if (!file.exists()) {
            System.err.println("找不到" + source);
        }
        try (FileInputStream fis = new FileInputStream(file);
             FileOutputStream fos = new FileOutputStream(DEFAULT_DIR + target)) {
            int data;
            while ((data = fis.read()) != -1) {
                fos.write(data ^ ENCRYPT_SEED);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        encrypt("MyObject.class", "MyObject1.class");
    }

}
