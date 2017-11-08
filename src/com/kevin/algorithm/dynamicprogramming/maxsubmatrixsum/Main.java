package com.kevin.algorithm.dynamicprogramming.maxsubmatrixsum;

/**
 * @Author kevin
 * @Date 2016/12/2 12:06
 * 问题描述：最大子矩阵和。给定一个m行n列的整数矩阵A，试求A的一个子矩阵，使得各元素之和最大。
 * 算法思路：最大子矩阵和为：max(∑∑a[i][j])，其中j1<=j<=j2, i1<=i<=i2, 1<=i1<=i2<=m, 1<=j1<=j2<=n。
 * max∑∑a[i][j]=max{max(∑∑a[i][j])}，
 * 记b[j]=∑a[i][j]，其中i1<=i<=i2，这是一维情形的最大子段和问题，于是：
 * max∑∑a[i][j]=max{max(∑∑a[i][j])}=max{max(∑b[j])}
 * 说明我们可以通过一维情形的最大子段和问题求最大子矩阵和。
 * 例如：在如下的4x4矩阵中，
 *  0 -2 -7  0
 *  9  2 -6  2
 * -4  1 -4  1
 * -1  8  0 -2
 * 假设现在子矩阵是一个3*k的矩阵，如下：
 *  9  2 -6  2
 * -4  1 -4  1
 * -1  8  0 -2
 * 我们要在该子矩阵中找到最大的矩阵。上下相加，得到一维情形，即[4,11,-10,1]，可以看到，最大的子矩阵是3*2的矩阵，最大和为15。
 * 为了能够通过原始矩阵很快得到从第i行到第j行的上下相加的和，可以使用一个辅助矩阵，该辅助矩阵的每一行的值都等于原始矩阵中从第一行累加到该行得到的值。
 */
public class Main {
    public static void main(String[] args) {
        int[][] matrix = new int[][]{
            {0, -2, -7, 0},
            {9, 2, -6, 2},
            {-4, 1, -4, 1},
            {-1, 8, 0, -2}
        };
        subMaxMatrix(matrix);
    }

    public static void subMaxMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] tmp = matrix;
        for (int j = 0; j < n; j++)
            tmp[0][j] = matrix[0][j];
        for (int i = 1; i < m; i++)
            for (int j = 0; j < n; j++)
                tmp[i][j] = tmp[i - 1][j] + matrix[i][j];

        Node node;
        Integer leftX = 0, leftY = 0, rightX = 0, rightY = 0;
        int maxSum = 0;
        int[] result = new int[n];      // 保存从第i行到第j行上下相加的和
        for (int i = 0; i < m; i++) {   // 穷举，初始行i，结束行j
            for (int j = i; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    if (i == 0)
                        result[k] = tmp[j][k];
                    else
                        result[k] = tmp[j][k] - tmp[i - 1][k];
                }
                node = maxSubsequence(result);
                if (node.sum > maxSum) {
                    maxSum = node.sum;
                    leftX = i;
                    leftY = j;
                    rightX = node.start;
                    rightY = node.end;
                }
            }
        }

        System.out.println("(leftX, leftY, rightX, rightY, sum)" +
                "(" + leftX + ", " + leftY + ", " + rightX + ", " + rightY +
                ", " + maxSum + ")");
    }

    public static Node maxSubsequence(int[] array) {
        if (array.length == 0)
            return null;

        Node result = new Node();
        result.sum = 0;
        int thisSum = 0;
        for (int j = 0, i = 0; j < array.length; j++) {
            if (thisSum > 0)
                thisSum += array[j];
            else {
                thisSum = array[j];
                i = j;
            }
            if (thisSum > result.sum) {
                result.sum = thisSum;
                result.start = i;
                result.end = j;
            }
        }
        return result;
    }

    private static class Node {
        int sum;
        int start;
        int end;
    }
}
