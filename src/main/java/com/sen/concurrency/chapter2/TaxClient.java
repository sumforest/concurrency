package com.sen.concurrency.chapter2;

/**
 * @Auther: Sen
 * @Date: 2019/12/6 20:03
 * @Description: 计算个人所得税（策略模式）
 */
public class TaxClient {

    public static void main(String[] args) {
        // TaxCalculator calculator = new TaxCalculator(10000d, 1430d, new TaxCalculatorStrategyImpl());
        //java8写法定义的TaxCalculatorStrategy接口直接实现
        TaxCalculator calculator = new TaxCalculator(10000d, 1430d,
                (salary, bonus)-> salary * 0.1 + bonus * 0.2);
        double tax = calculator.calculateTax();
        System.out.println(tax);
    }
}
