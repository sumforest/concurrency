package com.sen.classloader.chapter2;

import java.lang.reflect.Method;

/**
 * @Author: Sen
 * @Date: 2019/12/11 22:19
 * @Description:
 */
public class MyClassLoaderTest {

    public static void main(String[] args) throws Exception{
        MyClassLoader myClassLoader = new MyClassLoader("MyClassLoader");
        Class<?> aClass = myClassLoader.loadClass("com.sen.classloader.chapter2.MyObject");

        System.out.println(aClass.getClassLoader());
        System.out.println(aClass.getClassLoader().getParent());
        /*C:\Users\Sen\OneDrive\桌面\MyClassLoader\com\sen\classloader\chapter2*/
        Object obj = aClass.newInstance();
        Method hello = aClass.getMethod("hello", null);
        hello.invoke(obj, null);
    }
}
