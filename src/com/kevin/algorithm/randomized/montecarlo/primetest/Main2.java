package com.kevin.algorithm.randomized.montecarlo.primetest;

import java.math.BigInteger;

/**
 * @Author kevin
 * @Date 2017/2/2 11:27
 */
public class Main2 {
    public static void main(String[] args) {
        System.out.println(fermatPrimeTest(BigInteger.valueOf(7)));
        System.out.println(fermatPrimeTest(BigInteger.valueOf(11)));
        System.out.println(fermatPrimeTest(BigInteger.valueOf(15)));
        System.out.println(fermatPrimeTest(BigInteger.valueOf(121)));
        System.out.println(fermatPrimeTest(BigInteger.valueOf(561)));
        System.out.println(fermatPrimeTest(new BigInteger("6864797660130609714981900799081393217269435300143305409394463459185543183397656052122559640661454554977296311391480858037121987999716643812574028291115057151")));
    }

    public static boolean fermatPrimeTest(BigInteger n) {
        if (n.toString().equals("1"))
            return false;
        if (n.toString().equals("2"))
            return true;
        BigInteger d = computePower(BigInteger.valueOf(2), n.subtract(BigInteger.valueOf(1)), n);
        if (d.toString().equals("1"))
            return true;
        return false;
    }

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
