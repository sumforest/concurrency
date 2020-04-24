package com.sen.concurrency2.chapter9;

import java.util.Random;

/**
 * @Author: Sen
 * @Date: 2019/12/10 17:12
 * @Description: 请求客户端--提交请求
 */
public class RequestClient extends Thread{

    private final RequestQueue queue;

    private final Request request;

    private final Random random;

    public RequestClient(RequestQueue queue, Request request) {
        this.queue = queue;
        this.request = request;
        random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            queue.putRequest(request);
            System.out.println("Client -- " + Thread.currentThread().getName() + " put " + request.getRequestValue());
            try {
                Thread.sleep(random.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
