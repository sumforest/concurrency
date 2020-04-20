package com.sen.concurrency2.chapter4;

/**
 * @Author: Sen
 * @Date: 2019/12/9 17:45
 * @Description: 线程的对象被观察者
 */
public abstract class ObserverRunnable implements Runnable {

    private final LifeCycleListener listener;

    public ObserverRunnable(final LifeCycleListener listener) {
        this.listener = listener;
    }

    public void notifyChange(RunnableEvent event) {
        listener.onChange(event);
    }

    public enum RunnableStatus{
        RUNNING,ERROR,DONE;
    }

    public static class RunnableEvent{
        private final RunnableStatus status;

        private final Throwable cause;

        private final Thread thread;

        public RunnableEvent(RunnableStatus status, Throwable cause, Thread thread) {
            this.status = status;
            this.cause = cause;
            this.thread = thread;
        }

        public RunnableStatus getStatus() {
            return status;
        }

        public Throwable getCause() {
            return cause;
        }

        public Thread getThread() {
            return thread;
        }
    }
}
