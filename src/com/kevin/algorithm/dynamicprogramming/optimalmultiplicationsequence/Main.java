package com.kevin.algorithm.dynamicprogramming.optimalmultiplicationsequence;

/**
 * @Author kevin
 * @Date 2016/11/14 22:03
 * 问题描述：求解矩阵最优乘法顺序
 * 算法思路：M(Left,Right)=min{M(Left,i)+M(i+1,Right)+c(Left-1)*c(i)*c(Right)}，其中Left<=i<Right
 */
public class Main {
    /**
     * A:50x10, B:10X40, C:40X30, D:30X5
     */
    public static void main(String[] args) {
        int[] c = {50, 10, 40, 30, 5};
        int n = c.length;
        int[][] lastChange = new int[n][n];
        long[][] memo = new long[n][n];
        System.out.print("矩阵的最少计算次数为: ");
//        System.out.println(optMatrix1(c, lastChange));
//        System.out.println(optMatrix2(c, lastChange, memo));
        System.out.println(optMatrix3(c, lastChange, memo));
        System.out.println("矩阵的最优计算顺序为: ");
        traceback(1, n - 1, lastChange);
        System.out.println();
    }

    /**
     * 解法一：使用递推公式，时间复杂度为O(2^n)
     */

    /**
     * 思路：根据递推公式，采用递归求解。
     * 缺点：会多次求解同一个问题，因为子问题有重叠部分
     * @param c 包含了n个矩阵的列数，其中c[0]包含的是第一个矩阵的行数
     * @param lastChange lastChange[i][j]表示M(i,j)的最优顺序是从哪个矩阵处断开的，有效下标从1开始。比如M(i,j)从矩阵k处断开，则得到的
     *                   是M(i,k)和M(k+1,j)
     * @return 最小乘法次数，用long型，防止超过Integer.MAX_VALUE
     */
    public static long optMatrix1(int[] c, int[][] lastChange) {
        return optMatrix1(1, c.length - 1, c, lastChange);
    }

    /**
     *
     * @param i 从第i个矩阵开始相乘
     * @param j 直到第j个矩阵结束相乘
     * @param c
     * @param lastChange
     * @return
     */
    private static long optMatrix1(int i, int j, int[] c, int[][] lastChange) {
        if(i == j) return 0;

        // 初始时，最小乘法次数为从i处断开时的乘法次数
        long minCount = optMatrix1(i, i, c, lastChange) +
                optMatrix1(i + 1, j, c, lastChange) +
                c[i - 1] * c[i] * c[j];
        // 标记最小乘法次数为从i处断开
        lastChange[i][j] = i;

        for(int k = i + 1; k < j; k++) {
            long thisCount = optMatrix1(i, k, c, lastChange) +
                    optMatrix1(k + 1, j, c, lastChange) +
                    c[i - 1] * c[k] * c[j];
            if(thisCount < minCount) {  // 更新
                minCount = thisCount;
                lastChange[i][j] = k;
            }
        }

        return minCount;
    }

    private static void traceback(int i, int j, int[][] lastChange) {
        if(i == j)  return;
        traceback(i, lastChange[i][j], lastChange);
        traceback(lastChange[i][j] + 1, j, lastChange);
        System.out.print("Multiply A" + i + "," + lastChange[i][j]);
        System.out.println(" and A" + (lastChange[i][j] + 1) + ", " + j);
    }

    /**
     * 解法二：使用一张表保存已解决的子问题的答案，时间复杂度O(n^3)
     */

    /**
     * 思路：使用备忘录将已解决的子问题的答案保存下来，在下次需要解决此子问题时，直接查询备忘录即可。
     * @param c
     * @param lastChange
     * @param memo 备忘录，保存了已解决的子问题的答案，有效下标从1开始。比如memo[i][j]表示M(i,j)
     * @return
     */
    public static long optMatrix2(int[] c, int[][] lastChange, long [][] memo) {
        return optMatrix2(1, c.length - 1, c, lastChange, memo);
    }

    private static long optMatrix2(int i, int j, int[] c, int[][] lastChange, long[][] memo) {
        if(i == j) return 0;
        if(memo[i][j] > 0) return memo[i][j];

        long minCount = optMatrix2(i, i, c, lastChange, memo) +
                optMatrix2(i + 1, j, c, lastChange, memo) +
                c[i - 1] * c[i] * c[j];
        lastChange[i][j] = i;

        for(int k = i + 1; k < j; k++) {
            long thisCount = optMatrix2(i, k, c, lastChange, memo) +
                    optMatrix2(k + 1, j, c, lastChange, memo) +
                    c[i - 1] * c[k] * c[j];
            if(thisCount < minCount) {
                minCount = thisCount;
                lastChange[i][j] = k;
            }
        }
        memo[i][j] = minCount;

        return minCount;
    }

    /**
     * 解法三：使用动态规划求解
     *
     * 迭代过程如下：
     * 当k=1时，迭代计算出：
     * m[1:2]=m[1:1]+m[2:2]+c[0]*c[1]*c[2]
     * m[2:3]=m[2:2]+m[3:3]+c[1]*c[2]*c[3]
     * m[3:4]=m[3:3]+m[4:4]+c[2]*c[3]*c[4]
     * 当k=2时，迭代计算出：
     * m[1:3]=min(m[1:1]+m[2:3]+c[0]*c[1]*c[3],m[1:2]+m[3:3]+c[0]*c[2]*c[3])
     * m[2:4]=min(m[2:2]+m[3:4]+c[1]*c[2]*c[4],m[2:3]+m[4:4]+c[1]*c[3]*c[4])
     * 当k=3时，迭代计算出：
     * m[1:4]=min(m[1:1]+m[2:4]+c[0]*c[1]*c[4],m[1:2]+m[3:4]+c[0]*c[2]*c[4],m[1:3]+m[4:4]+c[0]*c[3]*c[4])
     *
     * 与备忘录方法相比，动态规划会将每个子问题都计算一遍，而备忘录方法则只计算部分子问题，因此备忘录方法更加灵活
     */
    public static long optMatrix3(int[] c, int[][] lastChange, long[][] memo) {
        int n = c.length - 1;

        for(int left = 1; left <= n; left++)    // 初始化对角线
            memo[left][left] = 0;
        for(int k = 1; k < n; k++)  // k is right - left
            for(int left = 1; left <= n - k; left++) {
                // for each position
                int right = left + k;
                memo[left][right] = Long.MAX_VALUE;
                for(int i = left; i < right; i++) {
                    long thisCost = memo[left][i] + memo[i + 1][right] + c[left - 1] * c[i] * c[right];
                    if(thisCost < memo[left][right]) {
                        memo[left][right] = thisCost;
                        lastChange[left][right] = i;
                    }
                }
            }

        return memo[1][n];
    }
}
