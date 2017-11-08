package com.kevin.datastructure.test;

/**
 * @Author kevin
 * @Date 2016/10/15 19:46
 */
public class Main6 {
    public static void main(String[] args) throws Exception {
        System.out.println(sumOfSeries(10));
        System.out.println(sum(100));
    }

    public static int sumOfSeries(int k) throws Exception {
        if(k < 1 || k > 15)
            throw new RuntimeException("k must be in [1, 15]");

        double s = 0;
        double i;

        for(i = 1; ; i++) {
            s += 1 / i;
            if(s >= k)
                break;
        }

        return (int)i;
    }

    public static double sum(double k) {
        double s = 0;

        for(double i = 0; i < k; i++)
            s += i;

        return s;
    }
}
