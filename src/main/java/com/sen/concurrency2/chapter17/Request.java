package com.sen.concurrency2.chapter17;

/**
 * @Auther: Sen
 * @Date: 2019/12/11 01:33
 * @Description:
 */
public class Request {

    private final String desc;

    public Request(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "Request{" +
                "desc='" + desc + '\'' +
                '}';
    }
}
