package com.sen.concurrency2.chapter9;

import java.util.Random;

/**
 * @Author: Sen
 * @Date: 2019/12/10 17:03
 * @Description: 请求处理--服务端
 */
public class RequestServer extends Thread {

    private final RequestQueue queue;

    /**
     * 退出标记，true退出，默认false
     */
    private volatile boolean flag = false;

    private final Random random;

    public RequestServer(RequestQueue queue) {
        this.queue = queue;
        this.random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        while (!flag) {
            Request request = queue.getRequest();
            if (null == request) {
                continue;
            }
            System.out.println("Server --" + Thread.currentThread().getName() + " deal with " + request.getRequestValue());
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void close() {
        this.interrupt();
        this.flag = true;
    }
}
