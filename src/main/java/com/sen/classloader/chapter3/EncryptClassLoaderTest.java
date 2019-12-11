package com.sen.classloader.chapter3;

import java.lang.reflect.Method;

/**
 * @Auther: Sen
 * @Date: 2019/12/12 01:09
 * @Description:
 */
public class EncryptClassLoaderTest {

    public static void main(String[] args) throws Exception {
        /*MyClassLoader myClassLoader = new MyClassLoader("testEncryptClassLoader");
        myClassLoader.setDir("C:\\Users\\Sen\\OneDrive\\桌面\\MyClassLoader\\classloader2\\");
        myClassLoader.loadClass("com.sen.classloader.chapter2.MyObject");*/

        EncryptClassLoader encryptClassLoader = new EncryptClassLoader();
        Class<?> clazz = encryptClassLoader.loadClass("com.sen.classloader.chapter2.MyObject");
        System.out.println(clazz.getClassLoader());
        Object obj = clazz.newInstance();
        Method method = clazz.getMethod("hello", null);
        method.invoke(obj, null);
    }
}
