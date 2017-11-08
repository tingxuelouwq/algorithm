package com.kevin.algorithm.dynamicprogramming.optimalbinarysearchtree;

/**
 * @Author kevin
 * @Date 2016/11/28 20:22
 * 问题描述：给定一组单词w1,w2,...,wn和它们出现的概率p1,p2,...,pn，找出一颗由这些单词组成的二叉查找树，使得存取这些单词的期望最小
 * 思路：
 * 在一颗二叉查找树中，访问深度d处的一个元素所需要的比较次数为d+1（根节点的深度为0），因此如果wi被放在深度di上，则要将∑pi(1+di)最小化，其中1<=i<=n
 * 假设我们想要把排序后的单词w(left),w(left+1),...,w(right-1),w(right)放到一颗二叉查找树中。设最优二叉查找树以w(i)为根，则左子树必须包含
 * w(left),...,w(i-1)，右子树必须包含w(i+1),...,w(right)，设最优二叉查找树的开销为c(left,right)，则：
 * c(left，right) = min{p(i) + c(left,i-1) + c(i+1,right) + ∑p(j) + ∑p(k)}，其中left<=i<=right，left<=j<=i-1，i+1<=k<=right
 * 其中c(left,i-1)表示左子树相对于它的根的代价，c(i+1,right)表示右子树相对于它的根的代价，由于这两颗子树的每个节点从w(i)开始都比从它们的根开始
 * 深一层，因此需要加上后面两项
 */
public class Main {
    public static void main(String[] args) {
        double[] p = {0, 0.22, 0.18, 0.20, 0.05, 0.25, 0.02, 0.08};
        int n = p.length;
        int[][] r = new int[n][n];
        double[][] c = new double[n + 1][n];
        System.out.println("该最优二叉查找树的期望代价为: " + optimalBST(p, r, c));
        System.out.println("该最优二叉查找树的中序遍历为: ");
        traceBack(1, n - 1, r);
    }

    /**
     * 动态规划求解最优二叉查找树，时间复杂度O(n^3)
     * @param p 各个数的概率
     * @param r r[i][j]表示子区域[i,j]的最优二叉查找树的根
     * @param c c[i][j]表示子区域[i,j]的最优二叉查找树的期望
     * @return
     */
    public static double optimalBST(double[] p, int[][] r, double[][] c) {
        int n = p.length - 1;

        for (int left = 1; left <= n; left++) {
            c[left][left] = p[left];
            r[left][left] = left;
        }
        for (int k = 1; k < n; k++)  // k is right - left
            for (int left = 1; left <= n - k; left++) {
                // for each position
                int right = left + k;
                c[left][right] = Double.MAX_VALUE;
                double thisP = 0.0;
                for (int i = left; i <= right; i++)
                    thisP += p[i];
                for (int i = left; i <= right; i++) {
                    double thisCost = c[left][i - 1] + c[i + 1][right] + thisP;
                    if (thisCost < c[left][right]) {
                        c[left][right] = thisCost;
                        r[left][right] = i;
                    }
                }
            }

        return c[1][n];
    }

    /**
     * 中序遍历最优二叉查找树
     * @param left
     * @param right
     * @param r
     */
    public static void traceBack(int left, int right, int[][] r) {
        if(left <= right) {
            System.out.println(r[left][right]);
            traceBack(left, r[left][right] - 1, r);
            traceBack(r[left][right] + 1, right, r);
        }
    }
}
