package com.sen.concurrency2.chapter11;

/**
 * @Author: Sen
 * @Date: 2019/12/10 18:32
 * @Description:
 */
public class QueryFromDB {

    public void execute() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String name = "Alex" + Thread.currentThread().getName();
        ContextAction.getContextAction().getContext().setName(name);
    }
}
