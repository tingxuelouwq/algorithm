package com.kevin.algorithm.dynamicprogramming.longestcommonsubsequence;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author kevin
 * @Date 2016/11/30 16:18
 * 问题描述：最长公共子序列。若给定序列X={x1,x2,...,xm}，则另一序列Z={z1,z2,...,zn}是X的子序列是指存在一个严格递增的下标序列{i1,i2,...,ik}，
 * 使得对于所有j=1,2,...,k，有xij=zj。例如序列Z={B,C,D,B}是序列X={A,B,C,B,D,A,B}的子序列。
 * 算法思路：设序列X={x1,x2,...,xm}和Y={y1,y2,...,yn}的最长公共子序列为Z={z1,z2,...,zk}，则有：
 * (1)若xm=yn，则zk=xm=yn，且z(k-1)是X(m-1)和Y(n-1)的最长公共子序列；
 * (2)若xm!=yn，且zk!=xm，则Z是X(m-1)和Y的最长公共子序列；
 * (3)若xm!=yn，且zk!=yn，则Z是X和Y(n-1)的最长公共子序列。
 * 其中X(m-1)={x1,x2,...,x(m-1)}，Y(n-1)={y1,y2,...,y(n-1)}
 * 定义c[i][j]为序列Xi和Yj的最长公共子序列的长度，其中Xi={x1,x2,...,xi}，Yj={y1,y2,...,yj}，则：
 * 当i=0或者j=0时，c[i][j]=0；
 * 当i,j>0时，如果xi=yj，则c[i][j]=c[i-1][j-1]+1；
 * 当i,j>0时，如果xi!=yj，则c[i][j]=max(c[i-1][j],c[i][j-1])。
 * 例如Xi={A,B,C,B,D,A,B}，Yj={B,D,C,A,B,A}，则根据上述递推关系可以得到c[i][j]如下：
 *      0    1   2   3   4   5   6
 *      yj   B   D   C   A   B   A
 * 0 xi 0    0   0   0   0   0   0
 * 1 A  0    0   0   0   1   1   1
 * 2 B  0    1   1   1   1   2   2
 * 3 C  0    1   1   2   2   2   2
 * 4 B  0    1   1   2   2   3   3
 * 5 D  0    1   2   2   2   3   3
 * 6 A  0    1   2   2   3   3   4
 * 7 B  0    1   2   2   3   4   4
 */
public class Main {
    public static void main(String[] args) {
        char[] x = {'A', 'B', 'C', 'B', 'D', 'A', 'B'};
        char[] y = {'B', 'D', 'C', 'A', 'B', 'A'};
        System.out.println(lcs(x, y));
        List<String> list = lcsAll(x, y);
        for (String line : list)
            System.out.println(line);
    }

    /**
     * 动态规划求最长公共子序列，时间复杂度O(mn)
     * @param x
     * @param y
     * @return
     */
    public static String lcs(char[] x, char[] y) {
        int m = x.length;
        int n = y.length;
        int[][] c = new int[m+1][n+1];

        int i, j;
        for (i = 0; i <= m; i++)
            c[i][0] = 0;
        for (j = 0; j <= n; j++)
            c[0][j] = 0;

        for (i = 1; i <= m; i++)
            for(j = 1; j <= n; j++) {
                if (x[i - 1] == y[j - 1])
                    c[i][j] = c[i - 1][j - 1] + 1;
                else
                    c[i][j] = Math.max(c[i - 1][j], c[i][j - 1]);
            }

        // trace back
        StringBuilder common = new StringBuilder();
        i = m;
        j = n;
        while (i > 0 && j > 0) {
            if(c[i][j] == c[i - 1][j - 1] + 1 &&
                    x[i - 1] == y[j - 1]) {   // there was a match
                common.insert(0, x[i - 1]);
                i--;
                j--;
            } else if (c[i - 1][j] >= c[i][j - 1])
                i--;
            else
                j--;
        }

        return common.toString();
    }

    /**
     * 求所有的最长公共子序列
     * 算法思路：使用回溯算法，通过pos指定回溯时需要保留的元素个数
     * @param x
     * @param y
     * @return
     */
    public static List<String> lcsAll(char[] x, char[] y) {
        int m = x.length;
        int n = y.length;
        int[][] c = new int[m + 1][n + 1];
        int[][] b = new int[m + 1][n + 1];

        int i, j;
        for (i = 0; i <= m; i++)
            c[i][0] = 0;
        for (j = 0; j <= n; j++)
            c[0][j] = 0;

        for (i = 1; i <= m; i++)
            for (j = 1; j <= n; j++) {
                if (x[i - 1] == y[j - 1])
                    c[i][j] = c[i - 1][j - 1] + 1;
                else
                    c[i][j] = Math.max(c[i - 1][j], c[i][j - 1]);
            }

        // trace back
        List<String> list = new LinkedList<>();
        StringBuilder line = new StringBuilder();
        traceBack(m, n, x, y, c, list, line, 0);
        return list;
    }

    /**
     * 回溯所有公共最长子序列
     * @param i 数组x的长度
     * @param j 数组y的长度
     * @param x
     * @param y
     * @param c
     * @param list 存储所有公共最长子序列
     * @param line 一个公共最长子序列
     * @param len 回溯时line中需要保留的元素个数
     */
    private static void traceBack(int i, int j, char[] x, char[] y, int[][] c, List<String> list, StringBuilder line, int len) {
        if (i == 0 || j == 0) {
            list.add(line.reverse().toString());
            line.reverse();
            return;
        }

        if (c[i][j] == c[i - 1][j - 1] + 1 &&
                x[i - 1] == y[j - 1]) {
            line.append(x[i - 1]);
            traceBack(i - 1, j - 1, x, y, c, list, line, len + 1);
        } else if (c[i - 1][j] > c[i][j - 1]) {
            traceBack(i - 1, j, x, y, c, list, line, len);
        } else if (c[i - 1][j] < c[i][j - 1]) {
            traceBack(i, j - 1, x, y, c, list, line, len);
        } else {
            traceBack(i - 1, j, x, y, c, list, line, len);
            for (int k = line.length() - 1; k >= len; k--)
                line.deleteCharAt(k);
            traceBack(i, j - 1, x, y, c, list, line, len);
        }
    }
}
