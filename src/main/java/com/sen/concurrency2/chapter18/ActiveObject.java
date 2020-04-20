package com.sen.concurrency2.chapter18;

/**
 * @Author: Sen
 * @Date: 2019/12/11 02:45
 * @Description:
 */
public interface ActiveObject {
    Result makeString(int count, char fillchar);

    void displayString(String text);
}
