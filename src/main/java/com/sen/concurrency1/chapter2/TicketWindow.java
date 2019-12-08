package com.sen.concurrency1.chapter2;

/**
 * @Auther: Sen
 * @Date: 2019/12/6 19:06
 * @Description:
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
