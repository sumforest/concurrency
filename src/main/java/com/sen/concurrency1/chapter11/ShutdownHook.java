package com.sen.concurrency1.chapter11;

/**
 * @Author: Sen
 * @Date: 2019/12/8 02:38
 * @Description: 给应用程序注入钩子程序，用于关闭前经行一些资源释放的相关操作
 */
public class ShutdownHook {

    public static void main(String[] args) {
        int i = 1;

        //mian线程添加线程结束时钩子函数
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Application will be close,do something before shutdown");
            notifyToMaster();
        }));

        while (true) {
            try {
                Thread.sleep(1_000L);
                System.out.println("I am is working");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(i > 20) {
                throw new RuntimeException("some problem happened");
            }
            i++;
        }
    }

    private static void notifyToMaster() {
        System.out.println("send email to master");
        System.out.println("shutdown i/o, shutdown network ,shutdown connection...");
        System.out.println("bye bye !!!");
    }
}
