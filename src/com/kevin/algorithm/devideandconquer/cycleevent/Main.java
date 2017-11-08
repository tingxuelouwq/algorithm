package com.kevin.algorithm.devideandconquer.cycleevent;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/10/30 21:02
 *
 * 问题描述：
 * 设有n=2^k个运动员要进行网球循环赛，现要设计一个满足以下要求的比赛日程表：
 * (1)每个选手必须与其他n-1个选手各赛一次；
 * (2)每个选手一天只能赛一次；
 * (3)循环赛在n-1天内结束。
 * 请按此要求将比赛日程表设计成n*(n-1)的表，表中的第i行，第j列处填入第i个选手第j天所遇到的对手，其中1<=i<=n，1<=j<=(n-1)
 *
 * 算法思路：
 * 按分治策略，将所有的选手分为两半，则n个选手的比赛日程表可以通过n/2个选手的比赛日程表来决定。递归地用这种一分为二的策略对选手进行
 * 划分，直到只剩下两个选手，此时只需要让这两个选手进行比赛即可。
 * 1 2 3 4 5 6 7 8
 * 2 1 4 3 6 5 8 7
 * 3 4 1 2 7 8 5 6
 * 4 3 2 1 8 7 6 5
 * 5 6 7 8 1 2 3 4
 * 6 5 8 7 2 1 4 3
 * 7 8 5 6 3 4 1 2
 * 8 7 6 5 4 3 2 1
 * 上图中，左上角与左下角分别为选手1-4和选手5-8前三天的比赛日程，将左上角的所有数字按其相对位置抄到右下角，将左下角的所有数字按其
 * 相对位置抄到右上角，这样我们就分别安排好了选手1-4和选手5-8在后四天的比赛日程。
 *
 * 算法步骤：
 * 1、初始化：
 * 用一个for循环输出日程表的第一行，即for(int i = 1; i <= n; i++) a[1][i] = i;
 * 2、划分整个问题为几个部分(k个部分)：
 * 用一个for循环将问题分成几部分，以k=3，n=8为例，将问题分为三部分，第一部分为：根据已填充好的第一行，填充第二行；第二部分为：根据
 * 已填充好的第一、二行，填充第三、四行；第三部分为：根据已填充好的前四行，填充后四行
 * 3、划分每一部分为几个单元(n /= 2个单元)：
 * 用一个for循环对步骤3中提到的每一部分进行划分，即每一部分的填充步骤是：对于第一部分，将其分为四个小单元；对于第二部分，将其分为
 * 两个小单元；对于第三部分，将其分为一个小单元
 * 4、填充每个单元：
 * 对每一个小单元进行填充，填充的原则是：按照对角线填充。
 */
public class Main {
    public static void main(String[] args) {
        int k;
        int n;
        int[][] schedule;
        Scanner in = new Scanner(System.in);
        while(in.hasNextInt()) {
            k = in.nextInt();
            n = 1;
            for(int i = 1; i <= k; i++)
                n *= 2;
            schedule = new int[n + 1][n + 1];

            fillSchedule(schedule, k, n);
            printSchedule(schedule, n);
            
        }
    }

    private static void printSchedule(int[][] a, int n) {
        for(int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void fillSchedule(int[][] a, int k, int n) {
        for(int i = 1; i <= n; i++) // 初始化：填充第一行
            a[1][i] = i;

        int m = 1;
        for(int s = 1; s<= k; s++) {    // 划分整个问题为k个部分
            n /= 2;
            for(int t = 1; t <= n; t++) {   // 划分每个部分为n /= 2个单元
                // 按照对角线填充每个单元
                for(int i = m + 1; i <= 2 * m ; i++) {  // 控制行
                    for(int j = m + 1; j <= 2 * m; j++) {   // 控制列
                        a[i][j + (t - 1) * m * 2] = a[i - m][j + (t - 1) * m * 2 - m];
                        a[i][j + (t - 1) * m * 2 - m] = a[i - m][j + (t - 1) * m * 2];
                    }
                }
            }
            m *= 2;
        }
    }
}
