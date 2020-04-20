package com.sen.concurrency1.chapter2;

/**
 * @Author: Sen
 * @Date: 2019/12/6 20:00
 * @Description: 税务计数器（仿照Runnable接口的策略模式）
 */
public class TaxCalculator {

    private double salary;

    private double bonus;

    /**
     * 聚合策略接口
     */
    private TaxCalculatorStrategy strategy;

    public TaxCalculator(double salary, double bonus, TaxCalculatorStrategy strategy) {
        this.salary = salary;
        this.bonus = bonus;
        this.strategy = strategy;
    }

    public final double calculateTax() {
        return strategy.calculate(salary, bonus);
    }

    protected double calculateTaxReal() {
        return salary * 0.1 + bonus * 0.15;
    }
}
