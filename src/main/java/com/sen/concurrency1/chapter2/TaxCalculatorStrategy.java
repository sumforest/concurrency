package com.sen.concurrency1.chapter2;

/**
 * @Author: Sen
 * @Date: 2019/12/6 20:09
 * @Description: 抽象出来的税务计算策略接口
 * 函数式接口声明jdk8
 */

@FunctionalInterface
public interface TaxCalculatorStrategy {
    /**
     * 计算个人所得税
     * @param salary 工资
     * @param bonus 津贴
     * @return 应缴纳税款
     */
    double calculate(double salary, double bonus);
}
