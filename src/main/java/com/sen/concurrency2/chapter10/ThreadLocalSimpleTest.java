package com.sen.concurrency2.chapter10;

/**
 * @Author: Sen
 * @Date: 2019/12/10 17:56
 * @Description:
 */
public class ThreadLocalSimpleTest {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return "Tomcat";
        }
    };

    public static void main(String[] args) {
        // threadLocal.set("Tomcat");
        System.out.println(threadLocal.get());
    }
}
