package com.kevin.algorithm.backtracking.subsettree.nqueen;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/22 21:27
 *
 * n皇后问题的迭代算法
 */
public class Main2 {
    public static void main(String[] args) {
        int n;
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            n = in.nextInt();
            Queen queen = new Queen(n);
            queen.backtrack();
        }
    }

    private static class Queen {
        int n;      // 棋盘大小nxn，皇后个数为n
        int[] x;    // x[i]表示第i个皇后放在第i行的第x[i]列上
        int sum;    // 可行解的个数

        public Queen(int n) {
            this.n = n;
            this.x = new int[n + 1];
            this.sum = 0;
        }

        public void backtrack() {
            int t = 1;
            x[t] = 0;
            while (t > 0) {
                x[t] += 1;
                while (x[t] <= n && !place(t)) {
                    x[t] += 1;
                }
                if (x[t] <= n) {
                    if (t == n) {
                        sum++;
                        for (int i = 1; i <= n; i++) {
                            System.out.print(x[i] + " ");
                        }
                        System.out.println();
                    } else {
                        t++;
                        x[t] = 0;
                    }
                } else {
                    t--;
                }
            }
        }

        private boolean place(int k) {
            for (int j = 1; j < k; j++) {   // 两个皇后的坐标分别为(j, x[j]), (k, x[k])
                if (x[j] == x[k] || Math.abs(k - j) == Math.abs(x[k] - x[j]))   // 判断两个皇后是否在同一列或者同一斜线上
                    return false;
            }
            return true;
        }
    }
}
