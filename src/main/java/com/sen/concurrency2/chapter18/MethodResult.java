package com.sen.concurrency2.chapter18;


/**
 * @Author: Sen
 * @Date: 2019/12/11 02:46
 * @Description: 方法结果
 */
public abstract class MethodResult {

    protected final Servant servant;

    protected final FutureResult futureResult;

    public MethodResult(Servant servant, FutureResult futureResult) {
        this.servant = servant;
        this.futureResult = futureResult;
    }

    public abstract void execute();
}
