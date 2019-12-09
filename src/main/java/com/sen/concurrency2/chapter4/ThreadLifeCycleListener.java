package com.sen.concurrency2.chapter4;

import sun.misc.Lock;

import java.util.List;

/**
 * @Auther: Sen
 * @Date: 2019/12/9 17:53
 * @Description:
 */
public class ThreadLifeCycleListener implements LifeCycleListener {

    private final Object LOCK = new Object();

    public void checkLifeCycleStatus(List<String> ids) {
        ids.forEach(id->new Thread(new ObserverRunnable(this){
            @Override
            public void run() {
                notifyChange(new RunnableEvent(RunnableStatus.RUNNING, null, Thread.currentThread()));
                System.out.println("query for the id:" + id);
                try {
                    Thread.sleep(1000);
                    int a = 10 / 0;
                    notifyChange(new RunnableEvent(RunnableStatus.DONE, null, Thread.currentThread()));
                } catch (Exception e) {
                    notifyChange(new RunnableEvent(RunnableStatus.ERROR, e, Thread.currentThread()));
                }

            }
        },id).start());
    }

    @Override
    public void onChange(ObserverRunnable.RunnableEvent event) {
        synchronized (LOCK) {
            System.out.println(event.getThread().getName() + "-->current status is " + event.getStatus().toString());
            if (event.getCause() != null) {
                System.out.println("something wrong is happening");
                event.getCause().printStackTrace();
            }
        }
    }
}
