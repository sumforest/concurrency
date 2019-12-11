package com.sen.classloader.chapter2;

/**
 * @Auther: Sen
 * @Date: 2019/12/11 22:18
 * @Description:
 */
public class MyObject {

    static {
        System.out.println("MyObject loaded by MyClassLoader");
    }

    public void hello() {
        System.out.println("Hello World!");
    }
}
