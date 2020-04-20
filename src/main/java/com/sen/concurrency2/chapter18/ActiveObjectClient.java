package com.sen.concurrency2.chapter18;

/**
 * @Author: Sen
 * @Date: 2019/12/11 17:44
 * @Description:
 */
public class ActiveObjectClient {

    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        new MakeStringRequestClient("Apple", activeObject).start();
        new MakeStringRequestClient("Huawei", activeObject).start();
        new MakeStringRequestClient("Sumsuang", activeObject).start();

        new DisplayStringRequestClient("Tomcat", activeObject).start();
        new DisplayStringRequestClient("Appache", activeObject).start();
    }
}
