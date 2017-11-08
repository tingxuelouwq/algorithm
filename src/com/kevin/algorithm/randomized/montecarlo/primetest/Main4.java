package com.kevin.algorithm.randomized.montecarlo.primetest;

import com.kevin.algorithm.randomized.Random;

/**
 * @Author kevin
 * @Date 2017/2/3 14:04
 * 素数检测问题
 * 数学原理：
 * 费马定理：如果p是一个素数，则对于任何小于p的正整数a，都有a^(p-1)=1(mod p)
 * 二次探测原理：如果p是一个素数，且对于任何小于p的正整数x，都有x^2=1(mod p)，则x=1或p-1
 * Carmichael数：满足费马定理的整数未必全是素数，有些合数也满足费马定理，这些合数称为Carmichael数，比如561,1105,1729...
 * 模幂的快速算法：对于大整数p来说，a^(p-1)(mod p)不是一个容易计算的数字，直接计算的效果并不比因子分解好，所以需要寻找一种更有效的取模幂
 * 算法。通常来说，重复平方法是一个不错的选择，下面通过例子介绍一下这个方法。
 * 假设现在要求a的10次方，一种方法当然是将10个a连乘，不过还有这样一种计算方法：
 * 10的二进制表示是1010，因此：
 * a^10=a^(1010)
 * 现初始化结果d=a^0=1，我们希望通过乘上某些数，使得d变换到2^10，变换序列如下：
 * a^0
 * a^0 x a^0 x a    = a^1
 * a^1 x a^1        = a^10
 * a^10 x a^10 x a  = a^101
 * a^101 x a^101    = a^1010
 * 可以看到这样一个规律：对中间结果d自身进行平方，等于在二进制指数的尾部生出一个0；对中间结果d自身进行平方再乘以底数，等于在二进制指数尾部
 * 生出一个1。靠这样不断让指数生长，就可以构造出幂。如果在每次运算时取模，就可以得到模幂了
 */
public class Main4 {
    public static void main(String[] args) {
        System.out.println(primeMC(7, 5));
        System.out.println(primeMC(21, 5));
        System.out.println(primeMC(31, 5));
        System.out.println(primeMC(561, 5));
    }

    public static boolean primeMC(int n, int k) {
        Random rnd = new Random();
        int a;
        for (int i = 1; i <= k; i++) {
            a = rnd.nextInt(n - 1) + 1;
            if (!isPrime(a, n))
                return false;
        }
        return true;
    }

    /**
     * 判断n是否为素数
     * @param a 一个小于m的正整数
     * @param n 待判断的数
     * @return
     */
    public static boolean isPrime(int a, int n) {
        if (n == 1)
            return false;
        if (n == 2)
            return true;

        int p = n - 1;
        // a^p=a^(u·2^t)
        int t = 1;
        while (Math.pow(2, t) <= n) {
            t++;
        }
        int u = 1;
        while (t > 0) {
            u = (int) (p / Math.pow(2, t));
            if (p % Math.pow(2, t) == 0 &&
                    u % 2 == 1)
                break;
            t--;
        }

        // 计算a^p并进行二次探测
        int b1;
        int b2;
        b1 = computePower(a, u, n);
        for (int i = 1; i <= t; i++) {
            b2 = (int) (Math.pow(b1, 2) % n);
            if (b2 == 1 && b1 != 1 && b1 != n - 1)
                return false;
            b1 = b2;
        }

        // 判断a^(n-1)与1是否对n同余
        if (b1 != 1)
            return false;

        return true;
    }

    /**
     * 计算a^p(mod n)
     * @param a
     * @param p
     * @param n
     * @return
     */
    public static int computePower(int a, int p, int n) {
        int result = 1;
        char[] bins = Long.toBinaryString(p).toCharArray();
        int len = bins.length;
        for (int i = 0; i < len; i++) {
            result = (result * result) % n;
            if (bins[i] == '1')
                result = result * a % n;
        }
        return result;
    }
}
