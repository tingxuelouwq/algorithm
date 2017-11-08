package com.kevin.leetcode;

/**
 * @Author kevin
 * @Date 2016/11/28 19:30
 */
public class Main {
    public static void main(String[] args) {
        int[] c = {50, 10, 40, 30, 5};
        int n = c.length;
        int[][] lastChange = new int[n][n];
        long[][] m = new long[n][n];
        System.out.println(optMatrix(c, lastChange, m));
        trace(1, n - 1, lastChange);
    }

    public static long optMatrix(int[] c, int[][] lastChange, long[][] m) {
        int n = c.length - 1;

        for(int left = 1; left <= n; left++)
            m[left][left] = 0;
        for(int k = 1; k < n; k++)
            for(int left = 1; left <= n - k; left++) {
                int right = left + k;
                m[left][right] = Long.MAX_VALUE;
                for(int i = left; i < right; i++) {
                    long thisCost = m[left][i] + m[i + 1][right] + c[left - 1] * c[i] * c[right];
                    if (thisCost < m[left][right]) {
                        m[left][right] = thisCost;
                        lastChange[left][right] = i;
                    }
                }
            }

        return m[1][n];
    }

    public static void trace(int left, int right, int[][] lastChange) {
        if(left == right)   return;
        trace(left, lastChange[left][right], lastChange);
        trace(lastChange[left][right] + 1, right, lastChange);
        System.out.print("Multiply A[" + left + ":" + lastChange[left][right] + "]");
        System.out.println(" and A[" + (lastChange[left][right] + 1) + ":" + right + "]");
    }
}
