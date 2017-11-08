package com.kevin.algorithm.randomized.numericalprobability.pi;

import com.kevin.algorithm.randomized.Random;

/**
 * @Author kevin
 * @Date 2017/1/15 10:18
 * 用随即投点法计算PI值
 * 问题描述：设有一半径为r的圆及其外切四边形，向该正方形随即地投掷n个点，设落入圆内的点数为k，由于所投入的点在正方形中均匀分布
 * ，因而所投入的点落入园内的概率为πr^2/(4r^2)=π/4。所以当n足够大时，k与n之比就逼近这一概率，即π/4，从而π≈4k/n。由此可得用随
 * 机投点法计算π值的数值概率算法，具体实现时，只要在第一象限计算即可。
 */
public class Main {
    public static void main(String[] args) {
        long n = 10_000_000_000L;
        System.out.println(darts(n));
    }

    public static double darts(long n) {
        Random dart = new Random();
        long k = 0L;
        double x;
        double y;
        for (long i = 1L; i <= n; i++) {
            x = dart.next();
            y = dart.next();
            if ((x * x + y * y) <= 1)
                k++;
        }
        return 4 * k / (double) n;
    }
}
