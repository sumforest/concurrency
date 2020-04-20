package com.sen.concurrency1.chapter2;

/**
 * @Author: Sen
 * @Date: 2019/12/6 20:11
 * @Description:
 */
public class TaxCalculatorStrategyImpl implements TaxCalculatorStrategy {

    private final static double SALARY_TAX = 0.1;

    private final static double BONUS_TAX = 0.15;

    @Override
    public double calculate(double salary, double bonus) {
        return salary * SALARY_TAX + bonus * BONUS_TAX;
    }

}
