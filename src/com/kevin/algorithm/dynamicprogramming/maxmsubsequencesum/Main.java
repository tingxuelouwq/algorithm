package com.kevin.algorithm.dynamicprogramming.maxmsubsequencesum;

/**
 * @Author kevin
 * @Date 2016/12/4 21:26
 * 问题描述：最大m子段和问题。给定由n个整数组成的序列a1,a2,...,an，以及一个正整数m，要求确定此序列的m个不相交子段的总和达到最大。
 * 算法思路：设b(i,j)表示数组a的前j项中i个不相交子段和的最大值，且第i个子段包含a[j]，其中1<=i<=m, i<=j<=n，则所求的最优值显然是：
 * max{b(m,j)}，其中m<=j<=n。对于b(i,j)，考虑最后一项a[j]，有两种情况：
 * (1)若a[j]单独构成一个子段，即第i个子段仅包含a[j]，则b(i,j)=max{b(i,t)}+a[j]，其中i<=t<j
 * (2)若a[j]不单独构成一个子段，即第i个子段除了包含a[j]外，还至少包含a[j-1]，则b(i,j)=b(i,j-1)+a[j]
 * 因此，b(i,j)=max{b(i,j-1)+a[j], max{b(i,t)}+a[j]}，其中1<=i<=m, i<=j<=n, i<=t<j。初始时，b(0,j)=0，(1<=j<=n)，
 * b(i,0)=0，(1<=i<=m)
 * 例如：给定序列为{2,3,-7,6,4,-5}，m=4，则递推过程如下：
 *      j   1   2    3    4    5    6
 * i    0   0   0    0    0    0    0
 * 1    0   2   5   -2    *    *    *
 * 2    0   -   5   -2   11    *    *
 * 3    0   -   -   -2   11   15    *
 * 4    0   -   -    -    4   15   10
 * 其中*表示不会用到这些元素，-表示无法计算出的元素，可以看到1<=i<=m，i<=j<=n-m+i
 */
public class Main {
    public static void main(String[] args) {
//        int[] a = {0,2 ,3,-7,6,4,-5};
        int[] a = {-2, -3, 5, 8, -5, 6, 2};
        int m = 4;
        System.out.println(maxSum(a, m));
        System.out.println(maxSum2(a, m));
    }

    /**
     * 算法的时间复杂度为O(mn^2)，空间复杂度为O(mn)
     * @param a 数组下标从1开始
     * @param m
     * @return
     */
    public static int maxSum(int[] a, int m) {
        int n = a.length - 1;
        int[][] b = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++)
            b[i][0] = 0;
        for (int j = 0; j <= n; j++)
            b[0][j] = 0;
        for (int i = 1; i <= m; i++) {
            b[i][i] = b[i - 1][i - 1] + a[i];   /* 当i == j时，每一项为一子段，此时b(i,j)=max{b(i-1,t)}+a[j]，
                                                 * 其中i-1<=t<j，由于i==j，因此t=i-1，所以b(i,j)=b(i-1,i-1]+a[j]，即
                                                 * b(i,i)=b(i-1,i-1)+a[i]*/
            for (int j = i + 1; j <= n - m + i; j++) {
                b[i][j] = b[i][j - 1] + a[j];   // 表示a[j]不单独构成一个子段
                for (int k = i - 1; k < j; k++) {
                    if (b[i][j] < b[i - 1][k] + a[j])
                        b[i][j] = b[i - 1][k] + a[j];   // 表示a[j]单独构成一个子段
                }
            }
        }

        int maxSum = 0;
        for (int j = m; j <= n; j++)
            if (maxSum < b[m][j])
                maxSum = b[m][j];
        return maxSum;
    }

    /**
     * 算法改进思路：由递推公式b(i,j)=max{b(i,j-1)+a[j], max{b(i-1,t)}+a[j]}, (i-1<=t<j)可以看出，b(i,j)在更新时，需要用到的是
     * b(i,j-1)的值，以及max{b(i-1.t)}，(1<=t<j)的值，即只关注以下两点：
     * (1)第i行第j-1列处的元素，即第i行前j-1项的最大子段和
     * (2)第i-1行，从第i-1列到j-1列中所有元素的最大值，即第i-1行前j-1项中的最大子段和，(i<=j)
     * 因此可以考虑用一个数组表示b(i,j-1)，用另一个数组表示max{b(i-1,t)}, (i-1<=t<=j-1)。这里使用数组b和c，其中b[j-1]表示b(i,j-1)，
     * c[j-1]表示max{b(i-1,t)}, (i-1<=t<=j-1)。接下来考虑如何更新数组b和c。
     * 更新数组b很简单，即b[j] = (b[j - 1] > c[j - 1]) ? (b[j - 1] + a[j]) : (c[j - 1] + a[j]);
     * 更新数组c。数组c中元素可以分成两部分，前一部分代表第i行前j-1项的最大子段和，后一部分代表第i-1行前j-1项的最大子段和。更新b[j]时，
     * 会用到c[j-1]，即第i-1行前j-1项的最大子段和，b[j]更新完毕后，应该将c[j-1]更新为第i行前j-1项的最大子段和，即每次更新b[j]，会用到
     * 数组c后一部分的第一个元素，更新完成后，便将该元素更新为数组c前一部分的最后一个元素。
     * for (int i = 1; i <= m; i++) {
     *     b[i] = b[i - 1] + a[i];  // i == j时
     *     thisSum = b[i];
     *     for (int j = i + 1; j <= n - m + i; j++) {
     *         b[j] = (b[j - 1] > c[j - 1]) ? (b[j - 1] + a[j]) : (c[j - 1] + a[j]);    // 更新b[j]
     *         c[j - 1] = thisSum;  // 更新c[j-1]
     *         if (thisSum < b[j])  // 使用thisSum记录b[i],...,b[j]的最大值
     *             thisSum = b[j];
     *     }
     *     c[n - m + i] = thisSum;  // 更新c[j-1]
     * }
     * 时间复杂度O(m(n-m))，空间复杂度O(n)
     * @param a
     * @param m
     * @return
     */
    public static int maxSum2(int[] a, int m) {
        int n = a.length - 1;
        int[] b = new int[n + 1];
        int[] c = new int[n + 1];
        int thisSum;
        for (int i = 1; i <= m; i++) {
            b[i] = b[i - 1] + a[i];   /* 当i == j时，每一项为一子段，此时b(i,j)=max{b(i-1,t)}+a[j]，
                                       * 其中i-1<=t<j，由于i==j，因此t=i-1，所以b(i,j)=b(i-1,i-1]+a[j]，即
                                       * b(i,i)=b(i-1,i-1)+a[i]，即b[i]=b[i-1]+a[i]*/
            thisSum = b[i];           // 标记b(i,j)的最大值，用于更新c[j-1]
            for (int j = i + 1; j <= n - m + i; j++) {
                b[j] = (b[j - 1] > c[j - 1]) ? (b[j - 1] + a[j]) : (c[j - 1] + a[j]);
                c[j - 1] = thisSum;   // 更新c[j-1]，这里的thisSum代表的是第i行前j-1项的最大子段和
                if (thisSum < b[j])   // 使用thisSum记录b[i],...,b[j]的最大值，即第i行前j项的最大子段和
                    thisSum = b[j];
            }
            c[n - m + i] = thisSum;   // 更新c[j-1]，对最后一个元素进行更新
        }

        int maxSum = 0;
        for (int j = m; j <= n; j++)
            if (maxSum < b[j])
                maxSum = b[j];
        return maxSum;
    }
}
