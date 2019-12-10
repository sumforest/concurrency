package com.sen.concurrency2.chapter15;

/**
 * @Auther: Sen
 * @Date: 2019/12/10 23:22
 * @Description:
 */
public class Message {

    private String value;

    public Message(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
