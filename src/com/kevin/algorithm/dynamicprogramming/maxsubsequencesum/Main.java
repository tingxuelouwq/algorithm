package com.kevin.algorithm.dynamicprogramming.maxsubsequencesum;

/**
 * @Author kevin
 * @Date 2016/12/2 10:17
 * 问题描述：最大子段和问题。对于给定序列a1,a2,...,an，寻找它的某个连续子段，使得其和最大。例如{-2,11,-4,13,-5,-2}的最大子段为{11,-4,13}，
 * 其和为20.
 */
public class Main {
    public static void main(String[] args) {
        int[] a = {-2,11,-4,13,-5,-2};
        solution1(a);
        solution2(a);
        solution3(a);
        solution4(a);
        solution5(a);
    }

    /**
     * 解法一：穷举法，时间复杂度O(n^3)
     * 思路：
     * 以a[0]开始：{a[0]},{a[0],a[1]},...,{a[0],a[1],...,a[n]}共n个
     * 以a[1]开始，{a[1]},{a[1],a[2]},...,{a[0],a[1],...,a[n]}共n-1个
     * ...
     * 以a[n]开始，{a[n]}共1个
     * @param a
     */
    public static void solution1(int[] a) {
        int n = a.length;
        int start = 0, end = 0, sum = 0;
        int i, j, k, thisSum;
        for (i = 0; i < n; i++)
            for (j = i; j < n; j++) {
                thisSum = 0;
                for (k = i; k <= j; k++)
                    thisSum += a[k];
                if (thisSum > sum) {
                    sum = thisSum;
                    start = i;
                    end = k;
                }
            }

        System.out.println("(start, end, sum) = " + "(" + start + ", " + end + ", " + sum + ")");
    }

    /**
     * 解法二：穷举法改进，时间复杂度O(n^2)
     * 思路：去掉最后一个for循环
     * @param a
     */
    public static void solution2(int[] a) {
        int n = a.length;
        int start = 0, end = 0, sum = 0;
        int i, j, thisSum;
        for (i = 0; i < n; i++) {
            thisSum = 0;
            for (j = i; j < n; j++) {
                thisSum += a[j];
                if (thisSum > sum) {
                    sum = thisSum;
                    start = i;
                    end = j + 1;    // end = j + 1是为了不包含end
                }
            }
        }

        System.out.println("(start, end, sum) = " + "(" + start + ", " + end + ", " + sum + ")");
    }

    /**
     * 解法三：分治法，时间复杂度为O(nlogn)
     * 思路：将序列a[1:n]分成长度相等的两段a[1:n/2]和a[n/2+1,n]，分别求出这两段的最大子段和，则a[1:n]的最大子段和有三种情形：
     * (1)与a[1:n/2]的最大子段和相同；
     * (2)与a[n/2+1:n]的最大子段和相同；
     * (3)为∑ak，且1<=i<=n/2, n/2+1<=j<=n
     * 可用递归求得情形(1)，(2)。对于情形(3)，可以在a[1:n/2]中计算出s1=max∑ak，其中1<=i<=n/2，在a[n/2+1:n]中计算出s2=max∑ak，其中
     * n/2+1<=j<=n，则s1+s2即为情形(3)的结果
     * @param a
     */
    public static void solution3(int[] a) {
        System.out.println(a.length > 0 ? inSolution3(a, 0, a.length - 1) : 0);
    }

    private static int inSolution3(int[] a, int left, int right) {
        if (left == right)
            return a[left] > 0 ? a[left] : 0;

        int mid = (left + right) / 2;
        int leftMaxSum = inSolution3(a, left, mid);
        int rightMaxSum = inSolution3(a, mid + 1, right);

        int i;
        int leftPosMaxSum = 0, thisLeftPosMaxSum = 0;
        for (i = mid; i >= left; i--) {
            thisLeftPosMaxSum += a[i];
            if (thisLeftPosMaxSum > leftPosMaxSum)
                leftPosMaxSum = thisLeftPosMaxSum;
        }

        int rightPosMaxSum = 0, thisRightPosMaxSum = 0;
        for (i = mid + 1; i <= right; i++) {
            thisRightPosMaxSum += a[i];
            if (thisRightPosMaxSum > rightPosMaxSum)
                rightPosMaxSum = thisRightPosMaxSum;
        }

        return maxOfThree(leftMaxSum, rightMaxSum, leftPosMaxSum + rightPosMaxSum);
    }

    private static int maxOfThree(int a, int b, int c) {
        return a > b ? a > c ? a : c : b > c ? b : c;
    }

    /**
     * 解法四：动态规划，时间复杂度O(n)
     * @param a
     */
    public static void solution4(int[] a) {
        int start = 0, end = 0, maxSum = 0;
        int i, j, thisSum = 0;
        for (i = 0, j = 0; j < a.length; j++) {
            thisSum += a[j];
            if (thisSum > maxSum) {
                maxSum = thisSum;
                start = i;
                end = j + 1;    // end = j + 1是为了不包含end
            } else if (thisSum < 0) {
                i = j + 1;
                thisSum = 0;
            }
        }

        System.out.println("(start, end, sum) = " + "(" + start + ", " + end + ", " + maxSum + ")");
    }

    /**
     * 解法五：动态规划，时间复杂度O(n)
     * 解法五同解法四，不过这里会详细说明动态规划的过程。
     * 最大子段和为：max∑ak=max(max∑ak)，其中i<=k<=j，1<=i<=j，1<=j<=n。令b[j]=max∑ak，其中i<=k<=j，1<=i<=j，1<=j<=n，则最大子段和
     * 为：max(b[j])，其中1<=j<=n。由b[j]的定义可知，当b[j-1]>0时，b[j]=b[j-1]+a[j]，否则，b[j]=a[j]，由此可得b[j]的动态规划递推式如下：
     * b[j]=max(b[j-1]+a[j],a[j])，1<=j<=n
     * @param a
     */
    public static void solution5(int[] a) {
        int start = 0, end = 0, maxSum = 0;
        int i, j, thisSum = 0;
        for (i = 0, j = 0; j < a.length; j++) {
            if (thisSum > 0)
                thisSum += a[j];
            else {
                thisSum = a[j];
                i = j;
            }
            if (thisSum > maxSum) {
                maxSum = thisSum;
                start = i;
                end = j;
            }
        }

        System.out.println("(start, end(inclusive), sum) = " + "(" + start + ", " + end + ", " + maxSum + ")");
    }
}
