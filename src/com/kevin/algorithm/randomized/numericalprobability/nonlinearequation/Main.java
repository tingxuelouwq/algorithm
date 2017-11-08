package com.kevin.algorithm.randomized.numericalprobability.nonlinearequation;

import com.kevin.algorithm.randomized.Random;

/**
 * @Author kevin
 * @Date 2017/1/15 20:58
 * 求解非线性方程组
 * 问题描述：假设要求解下面的非线性方程组：
 * f1(x1,x2,...,xn)=0
 * f2(x1,x2,...,xn)=0
 * ...
 * fn(x1,x2,...,xn)=0
 * 要求确定上述方程组在指定求根范围内的一组解x1*,x2*,...,xn*。
 * 算法思路：为了求解所给的非线性方程组，我们构造一函数Φ(x)=∑fi(x)^2，其中x=(x1,x2,...,xn)。易知，函数Φ(x)的零点即是所求非
 * 线性方程组的一组解。在指定的求根区域D内，选定一个随机点x0作为随即搜索的出发点。在算法的搜索过程中，假设第j步随即搜索得到的
 * 随即搜索点为xj，则在第j+1步，首先计算出下一步的随机搜索方向r，然后通过搜索步长a计算出第j+1步的随机搜索增量△xj，从而得到
 * 第j+1步的随机搜索点xj+1=xj+△xj。当Φ(xj+1)<ε时，取xj+1为所求非线性方程组的一组近似解，否则进行下一步新的随即搜索过程。
 */
public class Main {
    public static void main(String[] args) {
        int n = 2;
        int steps = 1_000_000_000;
        int m = 100_000;
        double a0 = 0.0001;
        double k = 1.1;
        double epsilon = 0.01;
        double[] x0 = {0.0, 0.0, 0.0};
        double[] dx0 = {0.0, 0.01, 0.01};
        double[] x = new double[n + 1];
        if (nonLinear(x0, dx0, x, a0, k, epsilon, n, steps, m)) {
            for (int i = 1; i <= n; i++) {
                System.out.println(x[i]);
            }
        } else {
            System.out.println("没有找到解，或许您可以调整参数");
        }
    }

    /**
     * @param x0      根初始值
     * @param dx0     增量初始值
     * @param x       根
     * @param a0      步长初始值
     * @param k       步长调整因子
     * @param epsilon 精度
     * @param n       方程组个数
     * @param steps   计算次数
     * @param m       计算失败次数
     * @return
     */
    public static boolean nonLinear(double[] x0, double[] dx0, double[] x, double a0, double k,
                                    double epsilon, int n, int steps, int m) {
        Random random = new Random();
        boolean success;    // 搜索成功标志
        double[] dx = new double[n + 1];    // 增量
        double[] r = new double[n + 1];     // 搜索方向，取值为[-1,1]
        int mm = 0; // 当前计算失败次数
        int j = 0;  // 当前计算次数
        double a = a0;  // 步长
        for (int i = 1; i <= n; i++) {
            x[i] = x0[i];
            dx[i] = dx0[i];
        }
        double fx = f(x, n);    // 计算目标函数值
        double min = fx;        // 当前最优值
        while (j < steps) {
            j++;
            // 计算随机搜索的步长
            if (fx < min) { // 搜索成功
                min = fx;
                a *= k;
                success = true;
            } else {    // 搜索失败
                mm++;
                if (mm > m) {
                    a /= k;
                }
                success = false;
            }
            // 计算随机搜索的方向和增量
            for (int i = 1; i <= n; i++) {
                r[i] = 2.0 * random.next() - 1;
            }
            if (success) {
                for (int i = 1; i <= n; i++) {
                    dx[i] = a * r[i];
                }
            } else {
                for (int i = 1; i <= n; i++) {
                    dx[i] = a * r[i] - dx[i];
                }
            }
            // 计算随机搜索点
            for (int i = 1; i <= n; i++) {
                x[i] += dx[i];
            }
            // 计算目标函数值
            fx = f(x, n);
            if (fx <= epsilon) {
                return true;
            }
        }
        return false;
    }

    /**
     * 非线性方程组为：
     * x1 - x2 = 1
     * x1 + x2 = 3
     * 构造函数Φ(x)=∑fi(x)^2
     *
     * @param x
     * @param n
     * @return
     */
    public static double f(double[] x, int n) {
       return (x[1] - x[2] - 1) * (x[1] - x[2] - 1) +
                (x[1] + x[2] - 3) * (x[1] + x[2] - 3);
    }
}
