package com.sen.concurrency2.chapter6;

import java.util.Random;

/**
 * @Author: Sen
 * @Date: 2019/12/9 20:21
 * @Description: 写者--写线程
 */
public class WriterWorker extends Thread {

    private Random random = new Random(System.currentTimeMillis());

    private SharedData data;

    /**
     * 写入的内容
     */
    private String filter;

    /**
     * 共享资源当前下标
     */
    private int index = 0;

    public WriterWorker(SharedData data, String filter) {
        this.data = data;
        this.filter = filter;
    }

    @Override
    public void run() {
        try {
            while (true) {
                data.writeData(nextChar());
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取下一个字符
     * @return next
     */
    private char nextChar() {
        // 读取完成后复位index
        if (index > filter.length() - 1) {
            index = 0;
        }
        char next = filter.charAt(index);
        index++;
        return next;
    }
}
