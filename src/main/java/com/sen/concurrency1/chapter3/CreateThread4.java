package com.sen.concurrency1.chapter3;

/**
 * @Auther: Sen
 * @Date: 2019/12/6 23:43
 * @Description: 测试虚拟机栈的大小和线程构造参数的stackSize的关系
 */
public class CreateThread4 {
    static int countThread = 0;

    public static void main(String[] args) {

        try {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                ++countThread;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        add(1);
                    }

                    private void add(int i) {
                        add(i + 1);
                    }
                });
                thread.start();
            }

        } catch (Error e) {
            e.printStackTrace();
            System.out.println(countThread);
        }
    }
}
