package com.sen.concurrency2.chapter17;

/**
 * @Auther: Sen
 * @Date: 2019/12/11 02:24
 * @Description:
 */
public class WorkerThreadClient {

    public static void main(String[] args) {
        Channel channel = new Channel(5);
        channel.startWork();
        new TransportWorker(channel, "Tomcat").start();
        new TransportWorker(channel, "Dubbo").start();
        new TransportWorker(channel, "Spring").start();
    }
}
