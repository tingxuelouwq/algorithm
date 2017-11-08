package com.kevin.algorithm.backtracking.subsettree.loadingproblem;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/20 20:12
 * 装载问题
 * 问题描述：有一批共n个集装箱要装上2艘载重量分别为c1和c2的轮船，其中集装箱i的重量为wi，要求确定是否有一个合理的装载方案，可将
 * 这些集装箱装上这2艘轮船，如果有，找出一种装载方案。例如当n=3,c1=c2=50,且w=[10,40,40]时，则可以将集装箱1和2装到第一艘轮船上
 * ，而将集装箱3装到第二艘轮船上；如果w=[20,40,40]，则无法将这3个集装箱都装上轮船。
 * 算法分析：容易证明，如果一个给定装载问题有解，则采用下面的策略可得到最优装载方案。
 * (1)首先将第一艘轮船尽可能装满；
 * (2)将剩余的集装箱装上第二艘轮船。
 * 将第一艘轮船尽可能装满等价于选取全体集装箱的一个子集，使该子集中集装箱重量之和最接近C1。由此可知，装载问题等价于特殊的0-1
 * 背包问题，其解空间是一颗子集树。
 * 输入：第一行为空格隔开的三个整数，分别代表集装箱c1的载重量，集装箱c2的载重量，以及物品个数；第二行为空格隔开的n个整数，代表
 * n个物品的重量
 * 输出：第一行为集装箱1的实际载重量，第二行为空格隔开的n个整数，代表哪些物品放入集装箱1，第三行表示该问题是否无解
 * 输入示例：
50 50 3
10 40 40
 * 输出示例：
50
1 0 1
 * 输入示例：
50 50 3
20 40 40
 * 输出示例：
40
0 0 1
此问题无解
 * 本程序给出的代码只使用了约束函数去除不可行解
 */
public class Main {
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
        int c1;     // 集装箱1容量
        int c2;     // 集装箱2容量
        int n;      // 物品数量
        int[] w;    // 各个物品的重量
        int[] x;    // x[i]=1表示第i个物品放入集装箱，为0表示不放入
        int curWeight;  // 当前重量

        int[] bestX;    // 最优解，bestX[i]=1表示第i个物品放入集装箱，为0表示不放入
        int bestWeight; // 最优值

        Container(int c1, int c2, int n, int[] w) {
            this.c1 = c1;
            this.c2 = c2;
            this.n = n;
            this.w = w;
            this.x = new int[n + 1];
            this.curWeight = 0;
            this.bestX = new int[n + 1];
            this.bestWeight = 0;
        }

        void backtrack(int t) {
            if (t > n) {
                if (curWeight > bestWeight) {
                    bestWeight = curWeight;
                    System.arraycopy(x, 1, bestX, 1, n);
                }
            } else {
                    for (int i = 0; i <= 1; i++) {
                        x[t] = i;
                        if (i == 0) {
                            backtrack(t + 1);
                        } else {
                            if (curWeight + w[t] <= c1) { // 约束条件
                                x[t] = 1;
                                curWeight += w[t];
                                backtrack(t + 1);
                                x[t] = 0;
                                curWeight -= w[t];
                            }
                        }
                    }
                }
        }

        void output() {
            int tmp = 0;
            System.out.println(bestWeight);
            for (int i = 1; i <= n; i++) {
                tmp = tmp + (1 - bestX[i]) * w[i];
                System.out.print(bestX[i] + " ");
            }
            System.out.println();
            if (tmp > c2) {
                System.out.println("此问题无解");
            }
        }
    }
}
