package com.kevin.algorithm.randomized.lasvegas.pollardrho;

import com.kevin.algorithm.randomized.Random;

/**
 * @Author kevin
 * @Date 2017/1/23 17:57
 */
public class Main5 {
    public static void main(String[] args) {
        int[] a = {4, 6, 9, 10, 14, 16, 18, 21, 24, 30, 50, 81, 100};
        for (int i = 0; i < a.length; i++) {
            System.out.println("n=" + a[i] + ", p=" + pollardRho(a[i]));
        }
    }

    /**
     * 已知n是一个合数，求n的一个因子
     * @param n
     * @return
     */
    public static int pollardRho(int n) {
        Random rnd = new Random();
        int a = rnd.nextInt(n); // 随机生成[0,n-1]之间的数作为x1
        int b = rnd.nextInt(n);
        int p;
        while (true) {
            a = f(n, a, rnd);
            b = f(n, f(n, b, rnd), rnd);
            if (b != a) {   // 如果b!=a，说明不存在f环
                p = gcd(Math.abs(b - a), n);
                if (p > 1) {
                    return p;
                }
            } else {    // 如果b==a，说明存在f环，需要重新选择一个随机种子
                rnd = new Random();
                a = rnd.nextInt(n);
                b = rnd.nextInt(n);
            }
        }
    }

    public static int gcd(int m, int n) {
        int r;
        while (n != 0) {
            r = m % n;
            m = n;
            n = r;
        }
        return m;
    }

    public static int f(int n, int x, Random rnd) {
        return ((int) Math.pow(x, 2) + rnd.nextInt(n)) % n; // f(x) = (x^2 + a) mod n，其中a随机生成
    }
}
