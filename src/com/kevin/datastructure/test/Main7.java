package com.kevin.datastructure.test;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.sqrt;

/**
 * @Author kevin
 * @Date 2016/10/15 20:07
 */
public class Main7 {
    public static void main(String[] args) throws Exception {
        System.out.println(maxCommanFactor(2, 12));
    }

    public static int maxCommanFactor(int a, int b) throws Exception {
        int counter = 0;
        List<List<Integer>> factors = getFactors(a * b);

        for(List<Integer> factor : factors)
            if(gcd(factor.get(0), factor.get(1)) == a)
                counter++;

        return counter;
    }

    private static int gcd(int m, int n) {
        if(m < n)
            return gcd(n, m);

        int r;
        while(n != 0) {
            r = m % n;
            m = n;
            n = r;
        }

        return m;
    }

    private static List<List<Integer>> getFactors(int b) {
        List<List<Integer>> factors = new ArrayList<>();

        for(int i = 1; i <= sqrt(b); i++) {
            if(b % i == 0) {
                List<Integer> factor = new ArrayList<>();
                factor.add(i);
                factor.add(b / i);
                factors.add(factor);
            }
        }

        return factors;
    }
}
