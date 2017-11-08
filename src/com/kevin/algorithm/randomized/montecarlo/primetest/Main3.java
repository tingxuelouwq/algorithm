package com.kevin.algorithm.randomized.montecarlo.primetest;

import com.kevin.algorithm.randomized.Random;

import java.math.BigInteger;

/**
 * @Author kevin
 * @Date 2017/2/2 22:58
 */
public class Main3 {
    public static void main(String[] args) {
        System.out.println(millerRabinPrimeTest(BigInteger.valueOf(7), 5));
        System.out.println(millerRabinPrimeTest(BigInteger.valueOf(21), 5));
        System.out.println(millerRabinPrimeTest(BigInteger.valueOf(561), 50));
        System.out.println(millerRabinPrimeTest(new BigInteger("6864797660130609714981900799081393217269435300143305409394463459185543183397656052122559640661454554977296311391480858037121987999716643812574028291115057151"), 50));
    }

    public static boolean millerRabinPrimeTest(BigInteger p, int k) {
        Random rnd = new Random();
        BigInteger a;
        int signal = p.compareTo(BigInteger.valueOf(Integer.MAX_VALUE));
        while (k > 0) {
            if (signal < 0)
                a = BigInteger.valueOf(rnd.nextInt(p.intValue() - 1) + 1);
            else
                a = BigInteger.valueOf(rnd.nextInt(Integer.MAX_VALUE - 1) + 1);
            if (!millerRabinWitness(a, p))
                return false;
            k--;
        }
        return true;
    }

    /**
     * 判断m是否为素数
     * @param a 一个小于m的正整数
     * @param m 待判断的数
     * @return m是否为素数
     */
    public static boolean millerRabinWitness(BigInteger a, BigInteger m) {
        if (m.toString().equals("1"))
            return false;
        if (m.toString().equals("2"))
            return true;

        BigInteger n = m.subtract(BigInteger.valueOf(1));   // n = m - 1
        // 将a^n分解成a^(u·2^t)的形式，因此要求解出u和t
        // 1、计算出小于n的最大t值
        int t = 1;
        BigInteger radix = BigInteger.valueOf(2);
        while (radix.pow(t).compareTo(n) < 0) {
            t++;
        }
        t--;
        // 2、从最大t值开始递减判断其是否合适
        BigInteger u = BigInteger.valueOf(1);
        while (t > 0) {
            u = n.divide(radix.pow(t));
            if (n.mod(radix.pow(t)).toString().equals("0") &&
                    u.mod(radix).toString().equals("1"))
                break;
            t--;
        }

        BigInteger b1;
        BigInteger b2;
        // 计算出a^u(mod p)
        b1 = computePower(a, u, m);
        // 将a^u(mod p)连续t次平方并模p计算出a^(p-1) mod p
        for (int i = 1; i <= t; i++) {
            b2 = b1.pow(2).mod(m);
            if (b2.compareTo(BigInteger.valueOf(1)) == 0 &&
                    b1.compareTo(BigInteger.valueOf(1)) != 0 &&
                    b1.compareTo(n) != 0)   // 如果对于一个小于p的正整数，发现1(模p)的非平凡平方根(即不为1且不为p-1的根)存在，则说明p是合数。
                return false;
            b1 = b2;
        }
        // 如果a^(p-1)与1不模p同余，说明p是合数
        if (!b1.toString().equals("1"))
            return false;

        return true;
    }

    /**
     * 计算a^m(mod n)
     * @param a
     * @param m
     * @param n
     * @return
     */
    public static BigInteger computePower(BigInteger a, BigInteger m, BigInteger n) {
        BigInteger result = new BigInteger("1");
        char[] bins = m.toString(2).toCharArray();
        int len = bins.length;
        for (int i = 0; i < len; i++) {
            result = result.multiply(result).mod(n);
            if (bins[i] == '1') {
                result = result.multiply(a).mod(n);
            }
        }
        return result;
    }
}
