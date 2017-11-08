package com.kevin.algorithm.backtracking.permutationtree.coloringproblem;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/23 18:00
 * 图的m着色问题
 * 问题描述：给定无向连通图G和m种不同的颜色，用这些颜色为图G的各个顶点着色，要求每个顶点着一种颜色，并且边的两个关联顶点着
 * 不同颜色。若一个图最少需要m种颜色才能使图中边的两个关联顶点着不同颜色，则称数m为该图的色数。求一个图的色数m的问题称为图
 * 的m可着色优化问题。
 * 输入：第一行是空格隔开的两个整数，分别表示m种不同的颜色，以及顶点个数n；接下来的n行是图的邻接矩阵表示
 * 输出：可供选择的着色方案
 * 输入示例：
3 5
0 1 1 0 0
1 0 1 1 1
1 1 0 1 0
0 1 1 0 1
0 1 0 1 0
 * 输出示例：
1 2 3 1 3
1 3 2 1 2
2 1 3 2 3
2 3 1 2 1
3 1 2 3 2
3 2 1 3 1
 * 算法分析：在至多使用m种颜色的情况下，通过回溯的方法，不断地为每一个顶点着色，在前面n-1个顶点都合法的着色之后，开始对第
 * n个顶点着色，这时候可以枚举可用的m种颜色，通过和第n个顶点相邻的顶点的颜色进行比较，来判断这个颜色是否合法。
 */
public class Main {
    public static void main(String[] args) {
        int m;
        int n;
        int[][] graph;
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            m = in.nextInt();
            n = in.nextInt();
            graph = new int[n + 1][n + 1];
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    graph[i][j] = in.nextInt();
                }
            }
            Coloring coloring = new Coloring(m, n, graph);
            coloring.backtrack(1);
            if (coloring.sum == 0)
                System.out.println("对于给定的m=" + m + ", 本问题无解");
        }
    }

    private static class Coloring {
        int m;          // m种可选的颜色
        int n;          // n个顶点
        int[][] graph;  // 图的邻接矩阵表示
        int[] x;        // 当前解，x[i]表示第i个顶点的颜色为x[i]
        int[] bestx;  // 最优解，bestx[i]表示第i个顶点的颜色为bestx[i]
        int sum;        // 可行的最优解的个数

        public Coloring(int m, int n, int[][] graph) {
            this.m = m;
            this.n = n;
            this.graph = graph;
            this.x = new int[n + 1];
            this.bestx = new int[n + 1];
            this.sum = 0;
        }

        public void backtrack(int t) {
            if (t > n) {
                sum++;
                for (int i = 1; i <= n; i++) {
                    bestx[i] = x[i];
                    System.out.print(bestx[i] + " ");
                }
                System.out.println();
            } else {
                for (int i = 1; i <= m; i++) {
                    x[t] = i;
                    if (ok(t))  // 如果第t个顶点的颜色可以为i，则继续着色下一个顶点
                        backtrack(t + 1);
                    x[t] = 0;
                }
            }
        }

        private boolean ok(int k) {
            for (int j = 1; j < k; j++) {
                if (graph[k][j] == 1 && x[j] == x[k])   // 如果(j,k)是边的关联顶点，并且着色相同，则着色失败
                    return false;
            }
            return true;
        }
    }
}
