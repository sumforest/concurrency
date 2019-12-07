package com.sen.concurrency.chapter7;

/**
 * @Auther: Sen
 * @Date: 2019/12/6 19:06
 * @Description:
 */
public class TicketWindow extends Thread {

    private final String name;

    private final static int MAX = 500;

    private static int index = 1;

    private static final Object MONITOR = new Object();

    public TicketWindow(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (MONITOR){
                if (index > MAX)
                    break;
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("当前窗口：" + name + "叫号：" + index++);
            }
        }
    }
}
