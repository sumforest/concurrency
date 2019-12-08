package com.sen.concurrency1.chapter3;

/**
 * @Auther: Sen
 * @Date: 2019/12/6 23:07
 * @Description: 测试jvm创建的main线程的栈帧的大小
 */
public class CreateThread2 {
    private static int count = 0;

    //由jvm启动时创建的main线程,并给定了栈帧的大小
    public static void main(String[] args) {
        try {
            add(1);
        } catch (Error e) {
            e.printStackTrace();
            System.out.println(count);
        }
    }

    private static void add(int i){
        ++count;
        add(i + 1);
    }
}
