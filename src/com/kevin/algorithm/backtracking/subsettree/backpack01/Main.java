package com.kevin.algorithm.backtracking.subsettree.backpack01;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/19 20:16
 * 0-1背包问题
 * 问题描述：给定n种物品和一背包，物品的重量是wi，其价值为pi，背包的容量为C。问应如何选择装入背包的物品，使得装入背包中的物品
 * 的总价值最大。
 * 输入：第一行为空格隔开的两个数，分别代表物品数量n和背包容量c，第二行为各个物品的重量wi，第三行为各个物品的价值pi
 * 输出：第一行为最优总价值，第二行为物品的放入情况，以空格隔开，1表示放入，0表示不放入
 * 输入示例：
3 16
10 8 5
5 4 1
 * 输出示例：
6
1 0 1
 * 算法分析：从n个物品中选择部分商品装入背包，可知该问题的解空间是子集树，比如物品数目n=3时，其解空间如图，边为1代表选择该物
 * 品，边为0代表不选择该物品。
 */
public class Main {
    public static void main(String[] args) {
        int n;
        int c;
        int[] w;
        int[] v;
        Scanner sin = new Scanner(System.in);
        while (sin.hasNextInt()) {
            n = sin.nextInt();
            c = sin.nextInt();
            w = new int[n];
            v = new int[n];
            for (int i = 0; i < n; i++) {
                w[i] = sin.nextInt();
            }
            for (int i = 0; i < n; i++) {
                v[i] = sin.nextInt();
            }
            Backpack backpack = new Backpack(n, c, w, v);
            backpack.trackback(0);
            backpack.output();
        }
    }

    private static class Backpack {
        int n;  // 物品数量
        int c;  // 背包容量
        int[] w;    // 各个物品的重量
        int[] v;    // 各个物品的价值
        int[] x;    // x[i]=1代表物品i放入背包，0代表不放入

        int curWeight;  // 当前放入背包的物品总重量
        int curValue;   // 当前放入背包的物品总价值

        int[] bestX;    // 当前最优解，bestX[i]=1代表物品i放入背包，0代表不放入
        int bestValue;  // 当前最优值

        public Backpack(int n, int c, int[] w, int[] v) {
            this.n = n;
            this.c = c;
            this.w = w;
            this.v = v;
            this.x = new int[n];
            this.curWeight = 0;
            this.curValue = 0;
            this.bestX = new int[n];
            this.bestValue = 0;
        }

        // t = 0 to n-1
        void trackback(int t) {
            if (t > n - 1) {
                if (curValue > bestValue) {
                    bestValue = curValue;
                    for (int i = 0; i < n; i++) {
                        bestX[i] = x[i];
                    }
                }
            } else {
                for (int i = 0; i <= 1; i++) {
                    x[t] = i;
                    if (i == 0) {   // 进入右子树
                        trackback(t + 1);
                    } else {    // 进入左子树
                        if (curWeight + w[t] <= c) {    // 约束条件
                            curWeight += w[t];
                            curValue += v[t];
                            trackback(t + 1);
                            curWeight -= w[t];
                            curValue -= v[t];
                        }
                    }
                }
            }
        }

        void output() {
            System.out.println(bestValue);
            for (int i = 0; i < n; i++) {
                System.out.print(bestX[i] + " ");
            }
            System.out.println();
        }
    }
}
