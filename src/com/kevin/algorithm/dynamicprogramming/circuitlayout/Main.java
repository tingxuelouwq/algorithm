package com.kevin.algorithm.dynamicprogramming.circuitlayout;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/18 16:47
 * 电路布线问题
 * 问题描述：在一块电路板的上下两端分别有n个接线柱。根据电路设计，要求用导线(i,π(i))将上端接线柱i与下端接线柱π(i)相连，其中
 * π(i), 1<=i<=n是{1,2,...,n}的一个排列，导线(i,π(i))称为电路板上的第i条连线。在制作电路板时，要求将这n条连线分布到若干绝缘
 * 层上，在同一层上的连线不相交。要求确定哪些连线被安排在第一层上，使得该层上有尽可能多的连线。换句话说，就是确定导线集
 * Nets={i,π(i), 1<=i<=n}的最大不相交子集。
 * 输入：第一行为整数n，接下来的n行由一个空格隔开的两个整数组成，表示(i,π(i))
 * 输出：第一行为最多的连线数m，接下来的m行输出这m条连线(i,π(i))
 * 输入示例：
10
1 8
2 7
3 4
4 2
5 5
6 1
7 9
8 3
9 10
10 6
 * 输出示例：

 * 算法分析：设a[i][j]表示上端接线柱i与下端接线柱j前的最大不相交子集的大小，则：
 * (1)若i与j相连，则a[i][j]=a[i-1][j-1]+1
 * (2)若i与j不相连，则a[i][j]=max(a[i-1][j], a[i][j-1])
    0	1	2	3	4	5	6	7	8	9	10
0	0	0	0	0	0	0	0	0	0	0	0
1	0	0	0	0	0	0	0	0	1	1	1
2	0	0	0	0	0	0	0	1	1	1	1
3	0	0	0	0	1	1	1	1	1	1	1
4	0	0	1	1	1	1	1	1	1	1	1
5	0	0	1	1	1	2	2	2	2	2	2
6	0	1	1	1	1	2	2	2	2	2	2
7	0	1	1	1	1	2	2	2	2	3	3
8	0	1	1	2	2	2	2	2	2	3	3
9	0	1	1	2	2	2	2	2	2	3	4
10	0	1	1	2	2	2	3	3	3	3	4
 */
public class Main {
    public static void main(String[] args) {
        int n;          // n个接线柱
        int[] b;        // b[i]表示上端接线柱i与下端接线柱b[i]相连
        int[][] a;      // a[i][j]表示上端接线柱i与下端接线柱j前的最大不相交子集的大小
        Scanner sin = new Scanner(System.in);
        while (sin.hasNextInt()) {
            n = sin.nextInt();
            b = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                sin.nextInt();
                b[i] = sin.nextInt();
            }
            a = new int[n + 1][n + 1];
            circuitLayout(n, b, a);
        }
    }

    /**
     *
     * @param n n个接线柱
     * @param b b[i]表示上端接线柱i与下端接线柱b[i]相连
     * @param a a[i][j]表示上端接线柱i与下端接线柱j前的最大不相交子集的大小
     */
    private static void circuitLayout(int n, int[] b, int[][] a) {
        int i, j;
        for (i = 1; i <= n; i++) {
            for (j = 1; j <= n; j++) {
                if (b[i] == j)
                    a[i][j] = a[i - 1][j - 1] + 1;
                else
                    a[i][j] = Math.max(a[i - 1][j], a[i][j - 1]);
            }
        }

        System.out.println(a[n][n]);

        // trace back
        i = n;
        j = n;
        while (i > 0 && j > 0) {
            if ((a[i][j] == a[i - 1][j - 1] + 1) &&
                    b[i] == j) {
                System.out.println("(" + i + ", " + b[i] + ")");
                i--;
                j--;
            } else if (a[i - 1][j] >= a[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }
    }
}
