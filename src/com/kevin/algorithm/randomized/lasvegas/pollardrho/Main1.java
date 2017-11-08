package com.kevin.algorithm.randomized.lasvegas.pollardrho;

/**
 * @Author kevin
 * @Date 2017/1/20 19:56
 */
public class Main1 {
    public static void main(String[] args) {
        int n = 35;
        System.out.println(factorization(n));
    }

    public static int factorization(int n) {
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return i;
            }
        }
        return 1;
    }
}
