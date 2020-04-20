package com.sen.classloader.chapter4;

/**
 * @Author: Sen
 * @Date: 2019/12/12 01:46
 * @Description: 打破类加载的父亲委托机制
 */
public class SimpleClassLoaderTest {

    public static void main(String[] args) throws Exception {
        SimpleClassLoader classLoader = new SimpleClassLoader("BreakRule");
        Class<?> aClass = classLoader.loadClass("com.sen.classloader.chapter4.SimpleObject");
        System.out.println(aClass.getClassLoader());
    }
}
