package com.sen.concurrency2.chapter18;

/**
 * @Author: Sen
 * @Date: 2019/12/11 02:45
 * @Description:
 */
public interface ActiveObject {

    /**
     * 产生字符串
     * @param count 数量
     * @param fillchar 填充字符
     * @return {@link Result}
     */
    Result makeString(int count, char fillchar);

    /**
     * 显示字符串
     * @param text
     */
    void displayString(String text);
}
