package com.sen.concurrency1.chapter11;

import java.util.Arrays;
import java.util.Optional;

/**
 * @Auther: Sen
 * @Date: 2019/12/8 14:58
 * @Description: 跟踪线程的堆栈信息
 */
public class Test2 {

    public void test() {
        Arrays.asList(Thread.currentThread().getStackTrace()).stream().filter(e->!e.isNativeMethod())
                .forEach(
                        e-> Optional.of(e.getClassName()+":"+e.getMethodName()+":"+e.getLineNumber())
                .ifPresent(System.out::println));
    }
}
