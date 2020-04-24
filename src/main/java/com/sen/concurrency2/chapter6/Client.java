package com.sen.concurrency2.chapter6;

/**
 * @Author: Sen
 * @Date: 2019/12/9 20:42
 * @Description: 手动时实现一个读写锁
 */
public class Client {

    public static void main(String[] args) {
        SharedData data = new SharedData(10);
        new ReaderWorker(data).start();
        new ReaderWorker(data).start();
        new ReaderWorker(data).start();
        new ReaderWorker(data).start();
        new ReaderWorker(data).start();

        new WriterWorker(data, "acbfrthh").start();
        new WriterWorker(data, "ACBFRTHH").start();
    }
}
