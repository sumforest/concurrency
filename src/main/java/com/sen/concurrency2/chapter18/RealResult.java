package com.sen.concurrency2.chapter18;

/**
 * @Author: Sen
 * @Date: 2019/12/11 16:23
 * @Description: 真实结果
 */
public class RealResult implements Result {

    private final Object resultValue;

    public RealResult(Object resultValue) {
        this.resultValue = resultValue;
    }

    @Override
    public Object getResultValue() {
        return this.resultValue;
    }
}
