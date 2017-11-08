package com.kevin.algorithm.dynamicprogramming.backpack01;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/11 10:36
 * 0-1背包问题
 * 问题描述：给定n种物品和一背包，物品i的重量是wi，其价值为vi，背包的容量为C。问：应如何选择装入背包的物品，使得背包中物品的
 * 总价值最大？
 *
 * 形式化描述：给定c>0, wi>0, vi>0, 1<=i<=n，要求找一n元向量(x1,x2,...,xn)，xi取值为0或1，使得∑wi·xi<=c，且∑vi·xi最大，即：
 * 要求max∑vi·xi, 1<=i<=n
 * 约束条件为：
 * ∑wi·xi<=c, 1<=i<=n
 * xi属于0或1, 1<=i<=n
 *
 * 算法描述：设(y1,y2,...,yn)是上式的一个最优解，则(y2,...,yn)是下面对应子问题的一个最优解。
 * 要求max∑vi·xi, 2<=i<=n
 * 约束条件为：
 * ∑wi·xi<=c-w1y1, 1<=i<=n
 * xi属于0或1, 1<=i<=n
 * 证明：反证法。若不然，设(z2,z3,...,zn)是上述子问题的一个最优解，而(y2,...,yn)不是最优解，则有：
 * ∑vi·zi>∑vi·yi, 2<=i<=n
 * w1y1+∑wi·zi<=c, 2<=i<=n
 * 因此，v1y1+∑vi·zi>∑vi·yi, 1<=i<=n。说明(y1,z2,...,zn)是0-1背包问题的一个更优解，而(y1,y2,...,yn)不是最优解，矛盾。
 * 设0-1背包问题的子问题：
 * max∑vk·xk, k<=i<=n
 * 约束条件为：
 * ∑wk·xk<=j, k<=i<=n
 * xi属于0或1, k<=i<=n
 * 的最优解为m(i,j)，即m(i,j)表示背包容量为j，可选物品为i,i+1,...,n时，0-1背包问题的最优解。由0-1背包问题的最优子结构性质，
 * 可建立m(i,j)的递归式：
 * m(i,j)=m(i+1,j), 0<=j<wi                     // 表示背包容量不足以装下物品i
 * m(i,j)=max{m(i+1,j), m(i+1,j-wi)+vi}, j>=wi  // 表示背包容量足以装下物品i，此时可以选择装或者不装物品i，取最大值
 * 初始条件为：
 * m(n,j)=0, 0<=j<wn    // 表示背包容量不足以装下物品n
 * m(n,j)=vn, j>=wn     //表示背包容量足以装下物品n
 *
 * 例如：
 * 输入示例：
    4 8
    1 4 2 3
    2 1 4 3
 * n=4, c=8, w[]={1,4,2,3}, v[]={2,1,4,3}
 * i|j  0    1   2    3    4    5    6   7   8
 * 4    0    0   0    3    3    3    3   3   3
 * 3    0    0   4    4    4    7    7   7   7
 * 2    0    0   4    4    4    7    7   7   7
 * 1    0    2   4    6    6    7    9   9   9
 */
public class Main {
    public static void main(String[] args) {
        int n;      // 物品个数
        int c;      // 背包容量
        int[] w;    // 每个物品的重量
        int[] v;    // 每个物品的价值
        int[][] m;  // m[i][j]表示背包容量为j，可选物品为i,i+1,...,n时，0-1背包问题的最优解
        int[] x;    // 物品是否装入背包，0表示不装入，1表示装入
        Scanner sin = new Scanner(System.in);
        while (sin.hasNextInt()) {
            n = sin.nextInt();
            c = sin.nextInt();
            w = new int[n + 1];
            for (int i = 1; i <= n; i++)
                w[i] = sin.nextInt();
            v = new int[n + 1];
            for (int i = 1; i <= n; i++)
                v[i] = sin.nextInt();
            m = new int[n + 1][c + 1];
            x = new int[n + 1];
            backpack01(n, c, w, v, m, x);
        }
    }

    /**
     * 动态规划求解0-1背包问题，时间复杂度O(nc)
     * @param n 物品个数
     * @param c 背包容量
     * @param w 每个物品的重量
     * @param v 每个物品的价值
     * @param m m[i][j]表示背包容量为j，可选物品为i,i+1,...,n时，0-1背包问题的最优解
     * @param x 物品是否装入背包，0表示不装入，1表示装入
     */
    private static void backpack01(int n, int c, int[] w, int[] v, int[][] m, int[] x) {
        int i, j;
        int jMax = Math.min(w[n] - 1, c);
        for (j = 0; j <= jMax; j++) // 背包剩余容量：0<=j<wn
            m[n][j] = 0;
        for (j = w[n]; j <= c; j++) // 背包剩余容量：j>=wn
            m[n][j] = v[n];

        for (i = n - 1; i >= 1; i--) {
            jMax = Math.min(w[i] - 1, c);
            for (j = 0; j <= jMax; j++) // 背包剩余容量：0<=j<wi
                m[i][j] = m[i + 1][j];
            for (j = w[i]; j <= c; j++) // 背包剩余容量：j>=wi
                m[i][j] = Math.max(m[i + 1][j], m[i + 1][j - w[i]] + v[i]);
        }

        // trace back
        jMax = c;
        for (i = 1; i < n; i++) {
            if (m[i][jMax] == m[i + 1][jMax])
                x[i] = 0;
            else {
                x[i] = 1;
                jMax -= w[i];
            }
        }
        x[n] = (m[n][jMax] > 0) ? 1 : 0;

        System.out.println("背包中装下的物品编号为: ");
        for (i = 1; i <= n; i++)
            if (x[i] == 1)
                System.out.print(i + " ");
    }
}
