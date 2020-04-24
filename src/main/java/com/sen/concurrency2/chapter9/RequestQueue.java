package com.sen.concurrency2.chapter9;

import java.util.LinkedList;

/**
 * @Author: Sen
 * @Date: 2019/12/10 16:57
 * @Description: 请求队列
 */
public class RequestQueue {

    private final LinkedList<Request> queue;

    public RequestQueue() {
        this.queue = new LinkedList<>();
    }

    /**
     * 获取请求
     * @return
     */
    public Request getRequest() {
        synchronized (queue) {
            while (queue.size() <= 0) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    return  null;
                }
            }
            return queue.removeFirst();
        }
    }

    /**
     * 添加请求
     * @param request
     */
    public void putRequest(Request request) {
        synchronized (queue) {
            queue.addLast(request);
            queue.notifyAll();
        }
    }
}
