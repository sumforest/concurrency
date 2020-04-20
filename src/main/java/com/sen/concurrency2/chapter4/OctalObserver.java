package com.sen.concurrency2.chapter4;

/**
 * @Author: Sen
 * @Date: 2019/12/9 03:10
 * @Description:
 */
public class OctalObserver extends Observer {

    public OctalObserver(Subject subject) {
        super(subject);
    }

    @Override
    protected void update() {
        System.out.println("八进制：" + Integer.toOctalString(subject.getStatus()));
    }
}
