package com.sen.concurrency.chapter2;

/**
 * @Auther: Sen
 * @Date: 2019/12/6 20:09
 * @Description: 抽象出来的税务计算策略接口
 */
//函数式接口声明jdk8
@FunctionalInterface
public interface TaxCalculatorStrategy {
    double calculate(double salary, double bonus);
}
