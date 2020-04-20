package com.sen.concurrency2.chapter6;

/**
 * @Author: Sen
 * @Date: 2019/12/9 20:42
 * @Description:
 */
public class Client {

    public static void main(String[] args) {
        SharedData data = new SharedData(10);
        new ReaderWorker(data).start();
        new ReaderWorker(data).start();
        new ReaderWorker(data).start();
        new ReaderWorker(data).start();
        new ReaderWorker(data).start();

        new WorkerWriter(data, "acbfrthh").start();
            new WorkerWriter(data, "ACBFRTHH").start();
    }
}
