package com.kevin.algorithm.backtracking.permutationtree.batchjobscheduling;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/21 16:33
 * 批作业调度问题
 * 问题描述：给定n个作业的集合j={j1,j2,...,jn}，每一个作业必须先由机器1处理，然后由机器2处理。作业j[i]需要机器j的处理时间为 
 * t[j][i] ，其中i=1,2,...,n, j=1,2。要求对于给定的n个作业，制定最佳作业调度方案，使其在机器2上完成处理的时间和最小。例如：
 * tji          机器1     机器2
 * 作业1         2          1
 * 作业2         3          1
 * 作业3         2          3
 * 这3个作业的6种可能的调度方案是1,2,3；1,3,2；2,1,3；2,3,1；3,1,2；3,2,1，它们所相应的完成时间和分别是19，18，20，21，19，
 * 19。易见，最佳调度方案是1,3,2，其完成时间和为18。
 * 以1,2,3为例:
 * 作业1在机器1上完成的时间为2,在机器2上完成的时间为3
 * 作业2在机器1上完成的时间为5,在机器2上完成的时间为6
 * 作业3在机器1上完成的时间为7,在机器2上完成的时间为10
 * 3+6+10=19，所以是19
 * 输入：第一行是n个作业，接下来的n行，每一行由空格分隔的两个整数组成，第一个整数表示机器1处理该作业所需时间，第二个整数表示
 * 机器2处理该作业所需时间
 * 输出：第一行是总处理时间，第二行是调度方案
 * 输入示例：
 3
 2 1
 3 1
 2 3
 * 输出示例：
 18
 1 3 2
 */
public class Main {
    public static void main(String[] args) {
        int n;      // 作业个数
        int[][] c;  // c[i][j]表示机器j处理作业i所需时间
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            n = in.nextInt();
            c = new int[n + 1][n + 1];
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= 2; j++) {
                    c[i][j] = in.nextInt();
                }
            }
            Schedule schedule = new Schedule(n, c);
            schedule.backtrack(1);
            schedule.output();
        }
    }

    private static class Schedule {
        int n;      // 作业个数
        int[][] c;  // c[i][j]表示机器j处理作业i所需时间
        int[] x;    // 当前调度方案
        int cost1;          // 当前调度方案，机器1加工总时间
        int[] cost2;        // 当前调度方案，机器2加工总时间
        int thisCost;       // 当前调度方案，总加工时间
        int[] bestx;        // 最优调度方案
        int bestCost;       // 最优调度方案所需时间

        public Schedule(int n, int[][] c) {
            this.n = n;
            this.c = c;
            this.x = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                x[i] = i;
            }
            this.cost1 = 0;
            this.cost2 = new int[n + 1];
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
                    cost1 += c[x[t]][1];
                    cost2[t] = (cost2[t - 1] > cost1) ? (cost2[t - 1] + c[x[t]][2]) : (cost1 + c[x[t]][2]);
                    thisCost += cost2[t];
                    if (thisCost < bestCost) {
                        backtrack(t + 1);
                    }
                    cost1 -= c[x[t]][1];
                    thisCost -= cost2[t];
                    swap(x, t, i);
                }
            }
        }

        public void output() {
            System.out.println(bestCost);
            for (int i = 1; i <= n; i++) {
                System.out.print(bestx[i] + " ");
            }
        }

        private void swap(int[] a, int i, int j) {
            int tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }
}
