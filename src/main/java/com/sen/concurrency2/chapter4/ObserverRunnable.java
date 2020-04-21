package com.sen.concurrency2.chapter4;

/**
 * @Author: Sen
 * @Date: 2019/12/9 17:45
 * @Description: 抽象线程的对象--被观察者
 */
public abstract class ObserverRunnable implements Runnable {

    private final LifeCycleListener listener;

    public ObserverRunnable(final LifeCycleListener listener) {
        this.listener = listener;
    }

    /**
     * 通知观察者状态发生改变
     * @param event 事件
     */
    public void notifyChange(RunnableEvent event) {
        listener.onChange(event);
    }

    public enum RunnableStatus{

        /**
         * 运行状态
         */
        RUNNING,

        /**
         * 线程出现异常
         */
        ERROR,

        /**
         * 执行线程完成
         */
        DONE
    }

    /**
     * 线程事件
     */
    public static class RunnableEvent{

        /**
         * 线程状态
         */
        private final RunnableStatus status;

        /**
         * 线程异常
          */
        private final Throwable cause;

        /**
         * 触发当前事件的线程
         */
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
