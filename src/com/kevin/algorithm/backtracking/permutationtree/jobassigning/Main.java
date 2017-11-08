package com.kevin.algorithm.backtracking.permutationtree.jobassigning;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/21 16:15
 * 问题描述：有n份作业分配给n个人去完成，每人完成一份作业。假定第i个人完成第j份作业需要花费cij时间，cij>0，1<=i,j<=n，试设计一个回溯算法，将n份
 * 作业分配给n个人完成，使得总时间最短。
 * 输入：第一行是一个整数，代表有n个人，接下来的每一行代表第i个人完成第j个作业所需的时间，其中1<=i,j<=n
 * 输出：第一行是总时间，接下来的n行，每一行的格式为(i,j)，表示第i个作业分配给第j个工人
 * 输入示例：
5
50 87 62 56 92
43 22 98 57 36
1  5  97 96 43
58 62 27 73 27
60 71 38 71 95
 * 输出示例：
144
(1,3)
(2,2)
(3,5)
(4,1)
(5,4)
 * 算法分析：该问题的解空间树是排列树。
 */
public class Main {
    public static void main(String[] args) {
        int n;      // n个工人
        int[][] c;  // c[i][j]表示第i个工人完成第j个作业所需的时间
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            n = in.nextInt();
            c = new int[n + 1][n + 1];
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    c[i][j] = in.nextInt();
                }
            }
            Assign assign = new Assign(n, c);
            assign.backtrack(1);
            assign.output();
        }
    }

    private static class Assign {
        int n;      // 工人数量
        int[][] c;  // c[i][j]表示第i个工人完成第j个作业所需的时间
        int[] x;    // x[i]表示第i个作业交给第x[i]个工人完成
        int thisCost;   // 当前花费总时间

        int[] bestx;    // 最优解，bestx[i]表示第i个作业交给第bestx[i]个工人完成
        int bestCost;   // 最优值，当前花费总时间

        public Assign(int n, int[][] c) {
            this.n = n;
            this.c = c;
            this.x = new int[n + 1];
            for (int i = 1; i <= n ;i++) {
                x[i] = i;
            }
            this.thisCost = 0;
            this.bestx = new int[n + 1];
            this.bestCost = Integer.MAX_VALUE;
        }

        public void backtrack(int t) {
            if (t > n) {
                if (thisCost < bestCost) {
                    bestCost = thisCost;
                    System.arraycopy(x, 1, bestx, 1, n);
                }
            } else {
                for (int i = t; i <= n; i++) {
                    swap(x, t, i);
                    if (thisCost + c[x[t]][t] < bestCost) {
                        thisCost += c[x[t]][t];
                        backtrack(t + 1);
                        thisCost -= c[x[t]][t];
                    }
                    swap(x, t, i);
                }
            }
        }

        public void output() {
            System.out.println(bestCost);
            for (int i = 1; i <= n; i++) {
                System.out.println("(" + i + "," + bestx[i] + ")");
            }
        }

        private void swap(int[] a, int i, int j) {
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }
}
