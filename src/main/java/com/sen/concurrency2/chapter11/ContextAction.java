package com.sen.concurrency2.chapter11;

/**
 * @Author: Sen
 * @Date: 2019/12/10 19:18
 * @Description:
 */
public final class ContextAction {

    private final ThreadLocal<Context> threadLocal = new ThreadLocal<Context>(){
        @Override
        protected Context initialValue() {
            return new Context();
        }
    };

    private ContextAction(){

    }

    private static class SingletonHolder{
        private final static ContextAction CONTEXT_ACTION = new ContextAction();
    }

    public static ContextAction getContextAction() {
        return SingletonHolder.CONTEXT_ACTION;
    }

    public Context getContext() {
        return threadLocal.get();
    }
}
