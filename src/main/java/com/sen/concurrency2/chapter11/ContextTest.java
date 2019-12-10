package com.sen.concurrency2.chapter11;

import java.util.stream.IntStream;

/**
 * @Auther: Sen
 * @Date: 2019/12/10 18:54
 * @Description:
 */
public class ContextTest {

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 5).forEach(i->new Thread(new ExecutionTask()).start());
    }
}
