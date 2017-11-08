package com.kevin.algorithm.backtracking.permutationtree.cyclepermutation;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/20 16:39
 * 圆排列问题
 * 问题描述：给定n个大小不等的圆c1,c2,...,cn，现要将这n个圆排进一个矩形中，且要求各圆与矩形的底边相切。圆排列问题要求从这n个
 * 圆的所有排列中找出有最小长度的圆排列。例如，当n=3，所给的3个圆的半径分别为1,1,2时，这3个圆的最小长度的圆排列如图，其最小
 * 长度为2+4*sqrt(2)
 * 输入：第一行为圆的个数n，第二行为各个圆的半径
 * 输出：第一行为最短长度，第二行为圆排列，以空格隔开
 * 输入示例：
 * 3
 * 1 1 2
 * 输出示例：
 * 7.65685
 * 1 1 2
 * 算法分析：圆排列问题的解空间是一个排列树。
 */
public class Main {
    public static void main(String[] args) {
        int n;
        double[] r;
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            n = in.nextInt();
            r = new double[n + 1];
            for (int i = 1; i <= n; i++) {
                r[i] = in.nextInt();
            }
            Cycle cycle = new Cycle(n, r);
            cycle.backtrace(1);
            cycle.output();
        }
    }

    private static class Cycle {
        int n;          // 圆的个数
        double[] r;     // 各个圆的半径
        double[] x;     // 当前圆排列中，各个圆的圆心横坐标
        double min;     // 当前最优圆排列的长度
        double[] bestR; // 当前最优圆排列中各个圆的半径

        Cycle(int n, double[] r) {
            this.n = n;
            this.r = r;
            this.x = new double[n + 1];
            this.min = Double.MAX_VALUE;
            this.bestR = new double[n + 1];
        }

        void backtrace(int t) {
            if (t > n) {
                if (x[n] + r[n] + r[1] < min) {
                    min = x[n] + r[n] + r[1];
                    System.arraycopy(r, 1, bestR, 1, n);
                }
            } else {
                for (int i = t; i <= n; i++) {
                    swap(r, t, i);
                    /**
                     * x^2 = sqrt((r1+r2)^2-(r1-r2)^2)，推导出x = 2*sqrt(r1*r2
                     * x[1]的横坐标为0
                     */
                    double coordinate = t == 1 ? x[1] : x[t - 1] + 2.0 * Math.sqrt(r[t] * r[t - 1]);
                    if (coordinate + r[t] + r[1] < min) {
                        x[t] = coordinate;
                        backtrace(t + 1);
                    }
                    swap(r, t, i);
                }
            }
        }

        void swap(double[] r, int i, int j) {
            double tmp = r[i];
            r[i] = r[j];
            r[j] = tmp;
        }

        void output() {
            System.out.println(min);
            for (int i = 1; i <= n; i++) {
                System.out.print(bestR[i] + " ");
            }
            System.out.println();
        }
    }
}
