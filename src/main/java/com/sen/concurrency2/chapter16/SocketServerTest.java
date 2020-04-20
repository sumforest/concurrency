package com.sen.concurrency2.chapter16;

import java.io.IOException;

/**
 * @Author: Sen
 * @Date: 2019/12/11 00:59
 * @Description: 线程二阶段关闭
 */
public class SocketServerTest {

    public static void main(String[] args) throws InterruptedException, IOException {
        AppServer appServer = new AppServer();
        appServer.start();
        Thread.sleep(10_000);
        appServer.shutdown();
    }
}
