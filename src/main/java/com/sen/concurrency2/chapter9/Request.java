package com.sen.concurrency2.chapter9;

/**
 * @Author: Sen
 * @Date: 2019/12/10 16:56
 * @Description:
 */
public class Request {

    private final String requestValue;

    public Request(String requestValue) {
        this.requestValue = requestValue;
    }

    public String getRequestValue() {
        return requestValue;
    }
}
