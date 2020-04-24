package com.sen.concurrency2.chapter18;

/**
 * @Author: Sen
 * @Date: 2019/12/11 16:26
 * @Description:
 */
class ActiveObjectProxy implements ActiveObject {

    private final Servant servant;

    private final SchedulerThread schedulerThread;

    public ActiveObjectProxy(Servant servant, SchedulerThread schedulerThread) {
        this.servant = servant;
        this.schedulerThread = schedulerThread;
    }


    @Override
    public Result makeString(int count, char fillchar) {
        FutureResult futureResult = new FutureResult();
        schedulerThread.invoke(new MakeStringRequest(servant,futureResult,count,fillchar));
        return futureResult;
    }

    @Override
    public void displayString(String text) {
        schedulerThread.invoke(new DisplayStringRequest(servant, text));
    }
}
