package com.sen.concurrency2.chapter11;

import java.util.stream.IntStream;

/**
 * @Author: Sen
 * @Date: 2019/12/10 18:54
 * @Description: 使用 {@link ThreadLocal} 解决多线程模式下构建一个对象的信息从数据库和网络上查询数据速度不一致问题
 */
public class ContextTest {

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 5).forEach(i->new Thread(new ExecutionTask()).start());
    }
}
