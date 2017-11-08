package com.kevin.algorithm.randomized.lasvegas.nqueen;

import com.kevin.algorithm.randomized.Random;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2017/1/20 9:52
 * n后问题
 * 消极：拉斯维加斯算法过于消极，一旦失败，从头再来
 * 乐观：回溯法过于乐观，一旦放置某个皇后失败，就进行系统回退一步的策略，而这一步往往不一定有效
 * 折中：先用拉斯维加斯算法随机地放置前若干个节点，例如k个，然后使用回溯法放置后若干个节点，但不考虑已经放置的前k个节点。这里需要注意的是，
 * 如果前面的随机选择位置不好，可能使得后面回溯不成功；另外，随机放置的皇后越多，则后续回溯阶段的平均时间就越少，失败的概率也越大。
 */
public class Main2 {
    public static void main(String[] args) {
        int n;
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            n = in.nextInt();
            Queen queen = new Queen(n);
            queen.nQueen(3);
        }
    }

    private static class Queen {
        int n;      // 皇后个数
        int[] x;    // 解向量，x[i]表示第i个皇后放置在第i行第x[i]列
        int[] y;    // 解向量，表示第i个皇后所有的候选位置，比如i=1,y={1,2,3},则表示第1个皇后可以放置在第1行第1、2、3列
        Random rnd; // 随机数发生器

        public Queen(int n) {
            this.n = n;
            this.x = new int[n + 1];
            this.y = new int[n + 1];
        }

        public void nQueen(int stop) {
            while (!queenLV(stop));
            if (backtrack(stop + 1)) {
                for (int i = 1; i <= n; i++) {
                    System.out.print(x[i] + " ");
                }
                System.out.println();
            } else {
                System.out.println("本次运行无解，请尝试再次运行");
            }
        }

        /**
         * 回溯
         * @param t t = stopVegas + 1
         */
        private boolean backtrack(int t) {
            if (t > n) {
                return true;
            } else {
                for (int i = 1; i <= n; i++) {
                    x[t] = i;
                    if (place(t) && backtrack(t + 1)) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * 随机放置stopVegas个皇后的拉斯维加斯算法
         * @param stopVegas
         * @return
         */
        private boolean queenLV(int stopVegas) {
            rnd = new Random();
            int k = 1;
            int count = 1;
            while (k <= stopVegas && count > 0) {
                count = 0;
                for (int i = 1; i <= n; i++) {  // 尝试将皇后k放在第k行第i列，如果可行，则第k行第i列是皇后k的一个候选位置
                    x[k] = i;
                    if (place(k)) {
                        y[count++] = i;
                    }
                }
                if (count > 0) {
                    x[k++] = y[rnd.nextInt(count)]; // 从候选位置中随机选择一个放置皇后k
                }
            }
            return count > 0;
        }

        /**
         * 检测将皇后k放在第k行第x[k]列是否合适
         * @param k
         * @return
         */
        private boolean place(int k) {
            for (int j = 1; j < k; j++) {   // 两个皇后的坐标分别为(j,x[j]), (k,x[k])
                if (x[j] == x[k] || Math.abs(j - k) == Math.abs(x[j] - x[k])) {
                    return false;
                }
            }
            return true;
        }
    }
}
