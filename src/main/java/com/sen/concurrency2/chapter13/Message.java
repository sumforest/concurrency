package com.sen.concurrency2.chapter13;

/**
 * @Author: Sen
 * @Date: 2019/12/10 22:10
 * @Description:
 */
public class Message {
    private String data;

    public Message(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
