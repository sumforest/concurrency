package com.sen.classloader.chapter4;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.regex.Pattern;

/**
 * @Auther: Sen
 * @Date: 2019/12/11 22:09
 * @Description:
 */
public class SimpleClassLoader extends ClassLoader {

    private final String DIR = "C:\\Users\\Sen\\OneDrive\\桌面\\MyClassLoader\\break\\";

    private String dir = DIR;

    private final String classLoaderName;

    public SimpleClassLoader(String classLoaderName) {
        this.classLoaderName = classLoaderName;
    }

    public SimpleClassLoader(String classLoaderName, ClassLoader parent) {
        super(parent);
        this.classLoaderName = classLoaderName;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String binaryFileName = name.replace(".", "/");
        byte buf[] = loadClassDir(binaryFileName, dir);
        if (buf == null || buf.length == 0)
            throw new ClassNotFoundException(classLoaderName + " class not found " + name);
        return defineClass(name, buf, 0, buf.length);
    }

    @Override
    public Class<?> loadClass(String name,boolean resolve) throws ClassNotFoundException {
        Class<?> aClass = findLoadedClass(name);
        if (aClass == null) {
            if (name.startsWith("java.")) {
                ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
                aClass = systemClassLoader.loadClass(name);
                if (aClass != null) {
                    if (resolve)
                        resolveClass(aClass);
                    return aClass;
                }
            }
            aClass = findClass(name);
        }
        if (resolve)
            resolveClass(aClass);
        return aClass;
    }

    private byte[] loadClassDir(String binaryFileName, String dir) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             FileInputStream fis = new FileInputStream(dir + binaryFileName +".class")
        ) {
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = fis.read(bytes)) != -1) {
                baos.write(bytes,0,len);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
