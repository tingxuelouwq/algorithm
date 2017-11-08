package com.kevin.algorithm.randomized.montecarlo.primetest;

/**
 * @Author kevin
 * @Date 2017/2/1 10:58
 */
public class Main1 {
    public static void main(String[] args) {
        long[] a = {2, 11, 15, 2147483647};
        long start;
        long end;
        boolean isPrime;
        System.out.println("************素数检测************");
        for (int i = 0; i < a.length; i++) {
            start = System.currentTimeMillis();
            isPrime = primeTest(a[i]);
            end = System.currentTimeMillis();
            System.out.println(a[i] + ": " + isPrime);
            System.out.println("用时: " + (end - start) + "ms");
        }
        System.out.println("********************************");
    }

    public static boolean primeTest(long n) {
        if (n == 1)
            return false;
        if (n == 2)
            return true;
        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
