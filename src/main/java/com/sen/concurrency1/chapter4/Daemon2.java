package com.sen.concurrency1.chapter4;

/**
 * @Author: Sen
 * @Date: 2019/12/7 00:51
 * @Description: 父线程设置为守护线程后所启动的线程默认继承父线程的守护线程属性，守护线程检测是一层一层进行的当父线程退出时
 * 检测子线程若为守护线程退出，在检测子线程的子线程看是否为守护线程，是则停止，否则继续运行
 *
 */
public class Daemon2 {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " start");

                Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (true) {
                                System.out.println("This thread is health check...");
                                Thread.sleep(1_000);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
                // thread1.setDaemon(true);
                // thread1.setDaemon(false);
                thread1.start();

                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " end");
            }
        });
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(5000);
        System.out.println(Thread.currentThread().getName() + " end");
    }
}
