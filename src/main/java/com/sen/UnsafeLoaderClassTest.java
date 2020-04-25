package com.sen;

/**
 * @Author: Sen
 * @Date: 2019/12/15 17:22
 * @Description: 用于 {@link sun.misc.Unsafe} 实现类加载
 */
public class UnsafeLoaderClassTest {

    private String name;

    public UnsafeLoaderClassTest() {
        this.name = "UnsafeLoader";
    }

    public String getName() {
        return name;
    }
}
