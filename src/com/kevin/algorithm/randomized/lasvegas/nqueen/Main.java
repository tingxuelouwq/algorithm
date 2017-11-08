package com.kevin.algorithm.randomized.lasvegas.nqueen;

import com.kevin.algorithm.randomized.Random;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2017/1/19 22:11
 * 拉斯维加斯算法解决n后问题
 * 问题描述：在n×n格的棋盘上放置彼此不受攻击的n个皇后。按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线上的棋子。
 * n后问题等价于在n×n格的棋盘上放置n个皇后，任何2个皇后不放在同一行或同一列或同一斜线上。
 * 算法思路：对于n后问题的任何一个解而言，每一个皇后在棋盘上的位置无任何规律，不具有系统性，而更象是随机放置的。在棋盘上相继的
 * 各行中随机地放置皇后，并注意使新放置的皇后与已放置的皇后互不攻击，直至n个皇后均已相容地放置好，或已没有下一个皇后的可放置位
 * 置时为止。
 */
public class Main {
    public static void main(String[] args) {
        int n;
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            n = in.nextInt();
            Queen queen = new Queen(n);
            while (!queen.queenLV());   // 反复调用随机放置n个皇后的拉斯维加斯算法，直至放置成功
            queen.output();
        }
    }

    private static class Queen {
        int n;      // 皇后个数
        int[] x;    // 解向量，x[i]表示第i个皇后放在第i行第x[i]列
        int[] y;    // 解向量，表示第i个皇后所有的候选位置，比如i=1,y={1,2,3},则表示第1个皇后可以放置在第1行第1、2、3列
        Random rnd; // 随机数产生器

        public Queen(int n) {
            this.n = n;
            this.x = new int[n + 1];
            this.y = new int[n + 1];
        }

        /**
         * 测试将皇后k置于第k行第x[k]列的合法性
         * @param k
         * @return
         */
        private boolean place(int k) {
            for (int j = 1; j < k; j++) {   // 两个皇后的坐标分别为(k,x[k]), (j,x[j])
                if (x[j] == x[k] || Math.abs(k - j) == Math.abs(x[k] - x[j])) { // 如果在同一列或者同一斜线上，则返回false
                    return false;
                }
            }
            return true;
        }

        /**
         * 在棋盘上随机放置n个皇后的拉斯维加斯算法
         * @return
         */
        public boolean queenLV() {
            rnd = new Random();
            int k = 1;      // 待放置的皇后编号
            int count = 1;  // 是否放置成功，count>0表示成功
            while (k <= n && count > 0) {
                count = 0;
                for (int i = 1; i <= n; i++) {  // 尝试将第k个皇后放置在第k行第i列(第x[k]列)
                    x[k] = i;
                    if (place(k)) { // 如果放置成功，则说明第k个皇后可以放在第k行第i列，因此增加一个候选位置，即y[count++]=i;
                        y[count++] = i;
                    }
                }
                if (count > 0) {
                    x[k++] = y[rnd.nextInt(count)]; // 对于第k个皇后的所有候选位置，随机选择一个放置第k个皇后
                }
            }
            return count > 0;   // 如果最后一个皇后也放置成功，则说明n个皇后均放置成功
        }

        public void output() {
            for (int i = 1; i <= n; i++) {
                System.out.print(x[i] + " ");
            }
            System.out.println();
        }
    }
}
