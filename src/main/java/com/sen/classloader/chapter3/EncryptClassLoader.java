package com.sen.classloader.chapter3;

import java.io.*;

/**
 * @Auther: Sen
 * @Date: 2019/12/12 00:46
 * @Description:
 */
public class EncryptClassLoader extends ClassLoader {

    private final static byte ENCRYPT_SEED = (byte) 0xff;

    private final static String DEFAULT_DIR = "C:\\Users\\Sen\\OneDrive\\桌面\\MyClassLoader\\classloader2\\";

    private String dir = DEFAULT_DIR;

    public EncryptClassLoader() {

    }

    public EncryptClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classpath = name.replace(".", "/");
        File file = new File(dir, classpath + ".class");
        if (!file.exists()) {
            throw new ClassNotFoundException("no such class" + name);
        }
        byte[] classbytes = loadClassToByte(dir + classpath + ".class");
        if (classbytes == null || classbytes.length == 0) {
            throw new ClassNotFoundException("something wrong happen in I/O");
        }
        return this.defineClass(name, classbytes, 0, classbytes.length);
    }

    private byte[] loadClassToByte(String classpath) {
        try (FileInputStream fis = new FileInputStream(classpath);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int data;
            while ((data = fis.read()) != -1)
                baos.write(data ^ ENCRYPT_SEED);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
