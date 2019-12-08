package com.sen.concurrency1.chapter6;

/**
 * @Auther: Sen
 * @Date: 2019/12/7 15:55
 * @Description: 停止阻塞的线程
 */
public class ThreadService {
    private boolean isEnd = false;

    private Thread crater;

    public void execute(Runnable task) {
        crater = new Thread(() -> {
            Thread t2 = new Thread(task);
            t2.setDaemon(true);
            t2.start();
            try {
                t2.join();
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
            isEnd = true;
        });
        crater.start();

    }

    public void shutdown(long mills) {
        long startEnd = System.currentTimeMillis();
        while (!isEnd) {
            if (System.currentTimeMillis() - startEnd >= mills) {
                crater.interrupt();
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                System.err.println("执行线程的父线程被打断");
            }
        }
    }
}
