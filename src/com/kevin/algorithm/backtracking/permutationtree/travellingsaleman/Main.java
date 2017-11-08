package com.kevin.algorithm.backtracking.permutationtree.travellingsaleman;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/20 12:24
 * 旅行售货员问题
 * 问题描述：某售货员要到若干城市去推销商品，已知各城市之间的路程(或旅费)。他要选定一条从驻地出发，经过每个城市一次，最后回到
 * 驻地的路线，使总的路程(或旅费)最小。
 * 输入：第一行为城市的个数n，接下来的n行，每一行表示从一个城市到其他城市的路程(自身为-1)
 * 输出：第一行为总路程，第二行为经过的各个城市
 * 输入示例：
 4
 -1 30 6 4
 30 -1 5 10
 6 5 -1 20
 4 10 20 -1
 * 输出示例：
25
1 3 2 4 1
 * 算法分析：旅行售货员问题的解空间如图，是一颗排列树。
 */
public class Main {
    private static final int NOT_A_VETEXT = -1;

    public static void main(String[] args) {
        int n;
        int[][] graph;
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            n = in.nextInt();
            graph = new int[n + 1][n + 1];
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    graph[i][j] = in.nextInt();
                }
            }
            City city = new City(n, graph);
            city.backtrack(2);
            city.output();
        }
    }

    private static class City {
        int n;              // 城市个数
        int[][] graph;      // graph[i][j]表示城市i与城市j之间的路程
        int[] x;            // x[i]表示售货员第i步到达的城市
        boolean[] visit;    //visit[i]表示第i个城市是否已经达到过
        int curWeight;      // 当前总路程

        int bestWeight;     // 当前最优总路程
        int[] bestX;        // 当前最优解

        City(int n, int[][] graph) {
            this.n = n;
            this.graph = graph;
            this.x = new int[n + 1];
            this.visit = new boolean[n + 1];
            this.curWeight = 0;
            this.bestWeight = Integer.MAX_VALUE;
            this.bestX = new int[n + 1];

            x[1] = 1;
            visit[1] = true;
        }

        void backtrack(int t) {
            if (t > n) {
                if (curWeight + graph[x[n]][1] < bestWeight) {
                    bestWeight = curWeight + graph[x[n]][1];
                    System.arraycopy(x, 1, bestX, 1, n);
                }
            } else {
                for (int i = 1; i <= n; i++) {
                    if ((graph[x[t - 1]][i] != NOT_A_VETEXT) && !visit[i] &&    // 约束条件
                            (curWeight + graph[x[t - 1]][i] < bestWeight)) {    // 限定条件
                        x[t] = i;
                        visit[i] = true;
                        curWeight += graph[x[t - 1]][i];
                        backtrack(t + 1);
                        x[t] = 0;
                        visit[i] = false;
                        curWeight -= graph[x[t - 1]][i];
                    }
                }
            }
        }

        void output() {
            System.out.println(bestWeight);
            for (int i = 1; i <= n; i++) {
                System.out.print(bestX[i] + " ");
            }
            System.out.println(bestX[1]);
        }
    }
}
