package com.sen.concurrency2.chapter18;


/**
 * @Author: Sen
 * @Date: 2019/12/11 16:23
 * @Description:
 */
public class MakeStringRequest extends MethodResult {

    private final int count;
    private final char fillchar;

    public MakeStringRequest(Servant servant, FutureResult futureResult, int count, char fillchar) {
        super(servant, futureResult);
        this.count = count;
        this.fillchar = fillchar;
    }

    @Override
    public void execute() {
        Result result = servant.makeString(count, fillchar);
        futureResult.setResult(result);
    }
}
