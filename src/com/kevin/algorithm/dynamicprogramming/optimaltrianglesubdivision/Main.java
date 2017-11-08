package com.kevin.algorithm.dynamicprogramming.optimaltrianglesubdivision;

/**
 * @Author kevin
 * @Date 2016/12/9 17:04
 * 凸多边形最优三角剖分
 * 问题相关定义：
 * 凸多边形的三角剖分：将凸多边形分割成互不相交的三角形的弦的集合T
 * 凸多边形的最优三角剖分：给定凸多边形P，以及由多边形的边和弦组成的三角形上的权函数w，要求该凸多边形的三角剖分，使得该
 * 三角剖分中的各个三角形上的权值之和最小
 * 算法描述：
 * 若凸(n+1)边形P={v0,v1,...,vn}的最优三角剖分T包含三角形v0vkvn, 1<=k<n，则T的权为三个部分权之和：三角形v0vkvn的权，子
 * 多边形{v0,v1,...,vk}和子多边形{vk,vk+1,...,vn}的权之和。可以断言，由T所确定的这两个子多边形的三角剖分也是最优的。
 * 我们定义一个t[i][j], 1<=i<j<=n，为凸多边形[vi-1,vi,...,vj}的最优三角剖分所对应的权函数值，即其最优值，则凸(n+1)边形P
 * 的最优值为t[1][n]。
 * t[i][j]的值可以利用最优子结构性质递归地计算，当j-i>=1时，凸子多边形至少有3个顶点，最优剖分包括三角形vi-1vkvj的权，子
 * 多边形{vi-1,vi,...,vk}的权和子多边形{vk,vk+1,...,vj}的权之和，其中i<=k<j。因此可得递推关系式：
 * t[i][j]=0, i=j时
 * t[i][j]=min{t[i][k]+t[k+1][j]+w(vk-1vkvj)},其中i<=k<j, i<j时
 * 例如：凸多边形的权值为：
 * {v0,v1,v2,v3,v4,v5}={
 * {0,2,2,3,1,4},
 * {2,0,1,5,2,3},
 * {2,1,0,2,1,4},
 * {3,5,2,0,6,2},
 * {1,2,1,6,0,1},
 * {4,3,4,2,1,0}
 * }
 * 迭代过程如下：
 * r=2时，迭代计算出：
 * t[1][2]=t[1][1]+t[2][2]+w(0,1,2)=5
 * t[2][3]=t[2][2]+t[3][3]+w(1,2,3)=8
 * t[3][4]=t[3][3]+t[4][4]+w(2,3,4)=9
 * t[4][5]=t[4][4]+t[5][5]+w(3,4,5)=9
 * r=3时，迭代计算出：
 * t[1][3]=min{t[1][1]+t[2][3]+w(0,1,3), t[1][2]+t[3][3]+w(0,2,3)}=12
 * t[2][4]=min{t[2][2]+t[3][4]+w(1,2,4), t[2][3]+t[3][4]+w(1,3,4)}=13
 * t[3][5]=min{t[3][3]+t[4][5]+w(2,3,5), t[3][4]+t[4][5]+w(2,4,5)}=15
 * r=4时，迭代计算出：
 * t[1][4]=min{t[1][1]+t[2][4]+w(0,1,4), t[1][2]+t[3][4]+w(0,2,4), t[1][3]+t[4][4]+w(0,3,4)}=18
 * t[2][5]=min{t[2][2]+t[3][5]+w(1,2,5), t[2][3]+t[4][5]+w(1,3,5), t[2][4]+t[5][5]+w(1,4,5)}=25
 * r=5时，迭代计算出：
 * t[1][5]=min{t[1][1]+t[2][5]+w(0,1,4), t[1][2]+t[3][5]+w(0,2,5), t[1][3]+t[4][5]+w(0,3,5), t[1][4]+t[5][5]+w(0,4,5)}=24
 */
public class Main {
    public static void main(String[] args) {
        int n = 6;  // 6个顶点
        int[][] weights = {
                {0, 2, 2, 3, 1, 4},
                {2, 0, 1, 5, 2, 3},
                {2, 1, 0, 2, 1, 4},
                {3, 5, 2, 0, 6, 2},
                {1, 2, 1, 6, 0, 1},
                {4, 3, 4, 2, 1, 0}
        };
        int[][] lastChange = new int[n ][n];
        int[][] memo = new int[n][n];
        System.out.println(optimalTriangleSubdivisioin(n, weights, lastChange, memo));
        traceback(1, n - 1, lastChange);

    }

    private static void traceback(int i, int j, int[][] lastChange) {
        if (i == j)
            return;
        traceback(i, lastChange[i][j], lastChange);
        traceback(lastChange[i][j] + 1, j, lastChange);
        System.out.println("三角剖分顶点: (v" + (i - 1) + ", v" + lastChange[i][j] + ", v" + j + ")");
    }

    public static int optimalTriangleSubdivisioin(int n, int[][] weights, int[][] lastChage, int[][] memo) {
        int r, i, j, k;
        for (i = 1; i < n; i++)
            memo[i][i] = 0;
        for (r = 2; r < n; r++) {   // r为当前计算的链长(链中有r个顶点，即子问题的规模)
            for (i = 1; i < n - r + 1; i++) {
                j =  i + r - 1;
                memo[i][j] = Integer.MAX_VALUE;
                for (k = i; k < j; k++) {
                    int u = memo[i][k] + memo[k + 1][j] + weight(weights, i - 1, k, j);
                    if (u < memo[i][j]) {
                        memo[i][j] = u;
                        lastChage[i][j] = k;
                    }
                }
            }
        }
        return memo[1][n - 1];
    }

    private static int weight(int[][] weights, int i, int j, int k) {
        return weights[i][j] + weights[i][k] + weights[j][k];
    }
}
