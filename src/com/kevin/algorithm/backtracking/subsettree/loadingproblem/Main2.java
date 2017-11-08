package com.kevin.algorithm.backtracking.subsettree.loadingproblem;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/21 10:27
 *
 * 本程序给出的代码考虑了两种剪枝函数：
 * (1)通过约束函数去除不可行解
 * (2)通过限定函数去除不含最优解的子树
 */
public class Main2 {
    public static void main(String[] args) {
        int n;
        int c1;
        int c2;
        int[] w;
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            c1 = in.nextInt();
            c2 = in.nextInt();
            n = in.nextInt();
            w = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                w[i] = in.nextInt();
            }
            Container container = new Container(c1, c2, n, w);
            container.backtrack(1);
            container.output();
        }
    }

    private static class Container {
        int c1;     // 集装箱1载重量
        int c2;     // 集装箱2载重量
        int n;      // 物品数量
        int[] w;    // 各个物品的重量
        int[] x;    // x[i]=1表示该物品装入集装箱，为0表示不装入
        int cw;     // 当前总重量

        int[] bestx;    // 最优解，bestX[i]=1表示该物品装入集装箱，为0表示不装入
        int bestw;      // 当前总重量的最优值
        int r;          // 集装箱剩余容量

        public Container(int c1, int c2, int n, int[] w) {
            this.c1 = c1;
            this.c2 = c2;
            this.n = n;
            this.w = w;
            this.x = new int[n + 1];
            this.cw = 0;
            this.bestx = new int[n + 1];
            this.bestw = 0;
            for (int i = 1; i <= n; i ++) {
                this.r += w[i];
            }
        }

        public void backtrack(int t) {
            if (t > n) {
                if (cw > bestw) {
                    bestw = cw;
                    System.arraycopy(x, 1, bestx, 1, n);
                }
            } else {
                r -= w[t];
                // 约束条件，w[t]装入集装箱
                if (cw + w[t] <= c1) {
                    x[t] = 1;
                    cw += w[t];
                    backtrack(t + 1);
                    x[t] = 0;
                    cw -= w[t];
                }
                // 约束条件，w[t]不装入集装箱，此时分为两种情况，一种是cw+r<=bestw，此时就算得到一个可行解，
                // 该可行解也不是最优解，可以去除；另一种是cw+r>bestw，此时得到的可行解可以更新最优解
                if (cw + r > bestw) {   // 限定条件
                    x[t] = 0;
                    backtrack(t + 1);
                }
                r += w[t];
            }
        }

        public void output() {
            int tmp = 0;
            System.out.println(bestw);
            for (int i = 1; i <= n; i++) {
                tmp += (1 - bestx[i]) * w[i];
                System.out.print(bestx[i] + " ");
            }
            System.out.println();
            if (tmp > c2) {
                System.out.println("此问题无解");
            }
        }
    }
}