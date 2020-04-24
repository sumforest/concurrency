package com.sen.concurrency2.chapter10;

/**
 * @Author: Sen
 * @Date: 2019/12/10 17:56
 * @Description: 线程本地变量 {@link ThreadLocal}
 */
public class ThreadLocalSimpleTest {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return "Tomcat";
        }
    };

    public static void main(String[] args) {
        threadLocal.set("Tomcat123");
        System.out.println(threadLocal.get());
    }
}
