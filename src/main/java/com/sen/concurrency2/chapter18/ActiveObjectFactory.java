package com.sen.concurrency2.chapter18;


/**
 * @Auther: Sen
 * @Date: 2019/12/11 16:27
 * @Description:
 */
public final class ActiveObjectFactory {

    private ActiveObjectFactory() {

    }

    public static ActiveObject createActiveObject() {
        Servant servant = new Servant();
        ActivationQueue activationQueue = new ActivationQueue();
        SchedulerThread schedulerThread = new SchedulerThread(activationQueue);
        schedulerThread.start();
        return new ActiveObjectProxy(servant, schedulerThread);
    }
}
