package com.kevin.algorithm.backtracking.subsettree.nqueen;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/22 20:51
 * n皇后问题
 * 问题描述：在nxn格的棋盘上放置彼此不受攻击的n个皇后。按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线上的棋
 * 子。n后问题等价于在n×n格的棋盘上放置n个皇后，任何2个皇后不放在同一行或同一列或同一斜线上。
 * 输入：第一行是棋盘的行数
 * 输出：第一行是可行方案的个数，第二行是由空格隔开的n个整数，表示第i个皇后放在第i行的第j列上
 * 输入示例：
4
 * 输出示例：
2
2 4 1 3
3 1 4 2
 * 算法分析：用n元数组x[1:n]表示n皇后问题的解，x[i]表示第i个皇后放在第i行的第x[i]列上，因此其解空间为子集树。对于棋盘上的两个
 * 皇后，假设其坐标分别为(i,j)，(k,l)，i!=k，显然：
 * (1)这两个皇后在不同行上；
 * (2)如果这两个皇后在不同列上，则j!=l
 * (3)如果这两个皇后在同一斜线上，则其横坐标之间的差值与其纵坐标之间的差值相等，即|k-i|=|l-j|
 */
public class Main {
    public static void main(String[] args) {
        int n;
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            n = in.nextInt();
            Queen queen = new Queen(n);
            queen.backtrack(1);
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

        public void backtrack(int t) {
            if (t > n) {
                sum++;
                for (int i = 1; i <= n; i++) {
                    System.out.print(x[i] + " ");
                }
                System.out.println();
            } else {
                for (int i = 1; i <= n ;i++) {
                    x[t] = i;
                    if (place(t)) { // 判断将第t个皇后放在第t行的x[t]列是否合适
                        backtrack(t + 1);
                    }
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
