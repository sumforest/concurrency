package com.sen.concurrency2.chapter9;

/**
 * @Auther: Sen
 * @Date: 2019/12/10 17:15
 * @Description: 多线程设计模式--确保挂起设计模式
 */
public class SuspensionClient {

    public static void main(String[] args) throws InterruptedException {
        RequestQueue requestQueue = new RequestQueue();
        new RequestClient(requestQueue, new Request("Tomcat")).start();
        RequestServer server = new RequestServer(requestQueue);
        server.start();
        Thread.sleep(25000);
        server.close();
    }
}
