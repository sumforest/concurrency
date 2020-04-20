package com.sen.concurrency1.chapter2;

/**
 * @Author: Sen
 * @Date: 2019/12/6 19:06
 * @Description: 模拟票据窗口，继承{@link Thread}重写实现run()创建线程
 */
public class TicketWindow extends Thread {

    private final String name;

    private final static int MAX = 50;

    private static int index = 1;

    public TicketWindow(String name){
        this.name = name;
    }

    @Override
    public void run() {
        while(index <= MAX){
            System.out.println("当前窗口：" + name + "叫号：" + index++);
        }
    }
}
