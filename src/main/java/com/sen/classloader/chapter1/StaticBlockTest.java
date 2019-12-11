package com.sen.classloader.chapter1;

/**
 * @Auther: Sen
 * @Date: 2019/12/11 20:13
 * @Description: 在静态代码块中，可以读取其前面的静态常量并且赋值；对于静态代码块中后面的静态常量，在静态代码块中
 * 只能给后面的静态常量赋值，不能读取。
 */
public class StaticBlockTest {

    private static int x = 0;

    static {
        System.out.println(x);
        x = 10;
        y = 20;
        // System.out.println(y);
    }

    private static int y = 2;
}
