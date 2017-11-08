package com.kevin.algorithm.randomized.numericalprobability.integral;

import com.kevin.algorithm.randomized.Random;

/**
 * @Author kevin
 * @Date 2017/1/15 11:31
 * 计算定积分
 * 1、用随即投点法计算定积分：设f:[a,b]->[c,d]是连续函数，则由曲线y=f(x)以及x轴和直线x=a,x=b围成的面积由定积分s=∫f(x)dx给出。
 * 根据几何概型可知，P(A)=事件区域A的面积/区域Ω的面积。假设向矩形区域随即均匀的投镖n次，落入阴影为k次，又设M为x=a,x=b,y=c,y=d
 * 所围成的矩形面积，s为定积分面积，则s/M=k/n，得到s=k*M/n
 * 2、用平均值法计算定积分：在积分区间[a,b]上随即均匀的取点，求出由这些点产生的函数值的算术平均值，再乘以积分区间宽度，即可解
 * 出定积分的近似解，即∫f(x)dx=∑f(xi)/n*(b-a), a<=xi<=b
 */
public class Main {
    public static void main(String[] args) {
        long n = 100_000_000L;
        double a = 2.0;
        double b = 3.0;
        double d = f(b);
        System.out.println(darts(n, a, b, d));
        System.out.println(average(n, a, b));
        System.out.println((double) 19 / 3);    // s=∫f(x)dx，其中f(x)=x^2，求解得s=19/3
    }

    /**
     *
     * @param n 取n个点
     * @param a 积分左边界
     * @param b 积分右边界
     * @return
     */
    public static double average(long n, double a, double b) {
        Random dart = new Random();
        double x;
        double sum = 0.0;
        for (long i = 1; i <= n; i++) {
            x = (b - a) * dart.next() + a;  // 产生[a,b]之间的随机数
            sum += f(x);
        }
        return sum / n * (b - a);
    }

    /**
     * 用随即投点法计算定积分
     * @param n 投镖次数
     * @param a 积分左边界
     * @param b 积分右边界
     * @param d 比f(x)的最大值大的一个数
     * @return
     */
    public static double darts(long n, double a, double b, double d) {
        Random dart = new Random();
        long k = 0L;
        double x;
        double y;
        for (long i = 1L; i <= n; i++) {
            x = (b - a) * dart.next() + a;  // 产生[a,b)之间的随机数
            y = d * dart.next();
            if (y <= f(x))
                k++;
        }
        return k * (b - a) * d / n;
    }

    public static double f(double x) {
        return x * x;
    }
}
