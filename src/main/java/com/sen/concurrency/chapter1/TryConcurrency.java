package com.sen.concurrency.chapter1;

/**
 * @Auther: Sen
 * @Date: 2019/12/6 17:44
 * @Description: 创建并启动线程jconsole监控
 */
public class TryConcurrency {

    public static void main(String[] args) throws InterruptedException {
        new Thread("read"){
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        readFileFromDb(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        new Thread("write"){
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        writeFileToDb(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        Thread.sleep(1000 * 20);
    }

    public static void readFileFromDb(int i) throws InterruptedException {
        System.out.println("reading..." + i);
        Thread.sleep(1000);
        System.out.println("read completed"+ i);
    }

    public static void writeFileToDb(int i) throws InterruptedException {
        System.out.println("writing"+ i);
        Thread.sleep(1000);
        System.out.println("wirte completed"+ i);
    }
}
