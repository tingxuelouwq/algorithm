package com.kevin.datastructure.test;

import java.math.BigDecimal;

/**
 * @Author kevin
 * @Date 2016/10/29 18:50
 */
public class Main9 {
    public static void main(String[] args) {
        double d = -27.2345;
        System.out.println(Math.ceil(d));   // -28.0
        System.out.println(Math.floor(d));  // -27.0
        System.out.println(Math.abs(d));    // 27.2345
        System.out.println(Math.round(d));  // -27.0

        System.out.println(Math.round(9.5));
        System.out.println(Math.round(-9.5));
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Math.round(123456789987654321.12f));
    }

    public static long compute(BigDecimal a, double b, long c) {
        return a.multiply(BigDecimal.valueOf(b)).multiply(BigDecimal.valueOf(c)).longValue();
    }
}
