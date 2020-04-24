package com.sen.concurrency2.chapter17;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @Author: Sen
 * @Date: 2019/12/11 01:32
 * @Description: 传送带
 */
public class Channel {

    private final Request[] requestQueue;

    /**
     * 取货工人池
     */
    private final WorkerThread[] workerPool;

    private final int MAX_REQUEST_QUEUE_SIZE = 100;

    private int head = 0;

    private int tail = 0;

    private volatile int count = 0;

    public Channel(int workerCount) {
        this.requestQueue = new Request[MAX_REQUEST_QUEUE_SIZE];
        this.workerPool = new WorkerThread[workerCount];
        this.initWorkerPool();
    }

    private void initWorkerPool() {
        IntStream.range(0, workerPool.length)
                .forEach(i->workerPool[i] = new WorkerThread("Worker" + i,this));
    }

    public void startWork() {
        Arrays.asList(workerPool).forEach(WorkerThread::start);
    }

    public synchronized void put(Request request) {
        while (count > requestQueue.length) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                break;
            }
        }
        this.requestQueue[tail] = request;
        // 使tail的范围在 [0,requestQueue.length]
        this.tail = (tail + 1) % requestQueue.length;
        this.count++;
        this.notifyAll();
    }

    public synchronized Request take() {
        while (count <= 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Request request = requestQueue[head];
        this.head = (head + 1) % requestQueue.length;
        this.count--;
        this.notifyAll();
        return request;
    }
}
