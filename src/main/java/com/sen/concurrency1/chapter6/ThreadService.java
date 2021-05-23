package com.sen.concurrency1.chapter6;

/**
 * @Author: Sen
 * @Date: 2019/12/7 15:55
 * @Description: 停止阻塞的线程
 */
public class ThreadService {

    private boolean isEnd = false;

    private Thread crater;

    public void execute(Runnable task) {
        crater = new Thread(() -> {
            Thread t2 = new Thread(task);
            //设置用于执行任务的为守护线程，当只有执行任务的线程时程序退出
            t2.setDaemon(true);
            t2.start();
            try {
                //父线程等待当前执行任务线程-->让crater阻塞
                t2.join();
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
            // 执行完成或者抛出异常，标记执行完成
            isEnd = true;
        });
        crater.start();
    }

    public void shutdown(long mills) {
        long startEnd = System.currentTimeMillis();
        //轮循标记
        while (!isEnd) {
            //超过期待结束时间中断工作线程
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
