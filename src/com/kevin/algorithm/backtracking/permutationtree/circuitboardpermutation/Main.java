package com.kevin.algorithm.backtracking.permutationtree.circuitboardpermutation;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/28 16:48
 * 电路板排列问题
 * 问题描述：将n块电路板以最佳排列方式插入带有n个插槽的机箱中。n块电路板的不同排列方式对应于不同的电路板插入方案。设B={1,2,...,n}
 * 是n块电路板的集合，L={N1,N2,...,Nm}是连接这n块电路板中若干电路板的m个连接块，其中Ni是B的一个子集，且Ni中的电路板用同一条
 * 导线连接在一起。设x表示n块电路板的一个排列，即在机箱的第i个插槽中插入的电路板编号为x[i]。x所确定的电路板排列密度定义为：
 * 跨越相邻电路板插槽的最大连线数。在设计机箱时，插槽一侧的布线间隙由电路板排列密度确定，因此，电路板排列问题要求对于给定的
 * 电路板连接条件(即连接块)，确定电路板的最佳排列，使其具有最小密度。
 * 输入：第1行是2个正整数n和m。接下来的n行中，每行有m个数，第i行的第j个数为0表示电路板i不在连接块j中，为1表示电路板i在连接块
 * j中
 * 输出：第1行是最小长度，接下来的1行是最佳排列
 * 输入示例：
 8 5
 0 0 1 0 0
 0 1 0 0 0
 0 1 1 1 0
 1 0 0 0 0
 1 0 0 0 0
 1 0 0 1 0
 0 0 0 0 1
 0 0 0 0 1
 * 输出示例：
 2
 1 2 3 4 5 6 7 8
 * 算法分析：电路板排列问题的解空间是排列树。设total[j]是连接块j中的电路板数，对于电路板的部分排列x[1:i]，设now[j]是x[1:i]
 * 中连接块j中的电路板数，则连接块j的连接线跨越相邻插槽i和i+1，当且仅当now[j]>0 && now[j] != total[j]
 */
public class Main {
    public static void main(String[] args) {
        int n;
        int m;
        int[][] b;
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            n = in.nextInt();
            m = in.nextInt();
            b = new int[n + 1][m + 1];
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    b[i][j] = in.nextInt();
                }
            }
            CircuitBoard board = new CircuitBoard(n, m, b);
            board.backtrack(1);
            board.output();
        }
    }

    private static class CircuitBoard {
        int n;          // 电路板数
        int m;          // 连接板数
        int[][] b;      // b[i][j]=1表示电路板i在连接块j中
        int[] total;    // total[j]表示连接块j中的电路板数
        int[] now;      // now[j]表示当前排列中，连接块j中的电路板数
        int[] x;        // 当前排列
        int[] bestx;    // 最优排列
        int cd;         // 当前排列的密度
        int bestd;      // 最优排列的密度

        public CircuitBoard(int n, int m, int[][] b) {
            this.n = n;
            this.m = m;
            this.b = b;
            this.total = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    total[j] += b[i][j];
                }
            }
            this.now = new int[n + 1];
            this.x = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                x[i] = i;
            }
            this.bestx = new int[n + 1];
            this.cd = 0;
            this.bestd = Integer.MAX_VALUE;
        }

        public void backtrack(int t) {
            if (t > n) {
                bestd = cd;
                System.arraycopy(x, 1, bestx, 1, n);
            } else {
                for (int i = t; i <= n; i++) {
                    int ld = 0;
                    int tmpd = cd;
                    swap(x, t, i);
                    for (int j = 1; j <= m; j++) {
                        now[j] += b[x[t]][j];
                        if (now[j] > 0 && now[j] != total[j]) {
                            ld++;
                        }
                    }
                    if (cd < ld) {
                        cd = ld;
                    }
                    if (cd < bestd) {
                        backtrack(t + 1);
                    }
                    for (int j = 1; j <= m; j++) {
                        now[j] -= b[x[t]][j];
                    }
                    cd = tmpd;
                    swap(x, t, i);
                }
            }
        }

        public void output() {
            System.out.println(bestd);
            for (int i = 1; i <= n; i++) {
                System.out.print(bestx[i] + " ");
            }
            System.out.println();
        }

        private void swap(int[] a, int i, int j) {
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }


}
