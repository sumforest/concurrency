package com.sen.concurrency2.chapter18;

/**
 * @Auther: Sen
 * @Date: 2019/12/11 16:26
 * @Description:
 */
class ActiveObjectProxy implements ActiveObject {

    private final Servant servant;

    private final SchedulerThread schedularThread;

    public ActiveObjectProxy(Servant servant, SchedulerThread schedularThread) {
        this.servant = servant;
        this.schedularThread = schedularThread;
    }


    @Override
    public Result makeString(int count, char fillchar) {
        FutureResult futureResult = new FutureResult();
        schedularThread.invoke(new MakeStringRequest(servant,futureResult,count,fillchar));
        return futureResult;
    }

    @Override
    public void displayString(String text) {
        schedularThread.invoke(new DisplayStringRequest(servant, text));
    }
}
