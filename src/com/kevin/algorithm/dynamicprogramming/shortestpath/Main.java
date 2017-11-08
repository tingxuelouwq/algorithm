package com.kevin.algorithm.dynamicprogramming.shortestpath;

/**
 * @Author kevin
 * @Date 2016/11/30 11:14
 * 问题描述：所有点对的最短路径
 * 思路：
 * 1、定义D(k,i,j)为从vi到vj只是用v1,v2...,vk作为中间顶点的最短路径的权，显然，D(0,i,j)=w(i,j)，如果(vi,vj)不是图的边，则w(i,j)为INF。
 * 因此D(|v|,i,j)是图中从vi到vj的最短路径的权。考虑D(k,i,j)，对于k>0，vi到vj只使用v1,v2,...,vj作为中间顶点，存在两种情况：
 * (1)使用vk作为中间顶点，即v1...vk...vj，此时D(k,i,j)=D(k,i,k)+D(k,k,j)。由于vi到vj是最短路径，因此vi到vk也是最短路径，意味着vi到vk只
 * 使用v1,v2,...,vk作为中间顶点，因此D(k,i,k)=D(k-1,i,k)；同理，vk到vj也是最短路径，因此D(k,k,j)=D(k-1,k,j)。综上，
 * D(k,i,j)=D(k-1,i,k)+D(k-1,k,j) *
 * (2)不使用vk作为中间顶点，则vi到vj只使用v1,v2,...,vk-1作为中间顶点，因此D(k,i,j)=D(k-1,i,j)
 * 综上，D(k,i,j)=min{D(k-1,i,j), D(k-1,i,k)+D(k-1,k,j)}，可以得到如下的伪代码：
 * D(0,i,j) = w(i,j)
 * for k = 1 to n
 *     for i = 1 to n
 *         for j = 1 to n
 *             D(k,i,j) = min{D(k-1,i,j), D(k-1,i,k)+D(k-1,k,j)}
 * 2、可以看到，整个算法阶段会用到两个矩阵，即D(k)和D(k-1)。如果D(k)=D(k-1,i,j)，则只用一个矩阵即可，因为D(k)中的元素等于更新前的元素；如果
 * D(k)=D(k-1,i,k)+D(k-1,k,j)，则D(k)=D(k,i,k)+D(k,k,j)，即D(k)的更新只依赖于D(k)本身。因此，综上所述，整个算法阶段只需要用到一个矩阵。
 * 可以改进伪代码如下：
 * D(0,i,j) = w(i,j)
 * for k = 1 to n
 *     for i = 1 to n
 *         for j = 1 to n
 *             if (D(k,i,k)+D(k,k,j)) < D(k,i,j))
 *                 D(k,i,j) = D(k,i,k) + D(k,k,j);
 */
public class Main {
    private static final int NOT_A_VERTEX = -1;
    private static final int INF = 9999;

    /**
     * 求所有点对的最短路径，时间复杂度O(n^3)
     * @param a 图的邻接矩阵表示，a[i][i]为0，如果(vi,vj)不是图的边，则a[i][j]=INF
     * @param d d[i][j]表示vi到vj的最短路径的权
     * @param path path[i][j]表示vi到vj的最短路径
     */
    public static void allPairs(int[][] a, int[][] d, int[][] path) {
        int n = a.length;

        // Initialize d and path
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                d[i][j] = a[i][j];
                path[i][j] = NOT_A_VERTEX;
            }

        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    if (d[i][k] + d[k][j] < d[i][j]) {
                        d[i][j] = d[i][k] + d[k][j];
                        path[i][j] = k;
                    }
    }

    public static void printPath(int i, int j, int[][] path) {
        System.out.print("v" + (i + 1) + "->");
        printPath1(i, j, path);
        System.out.println();
    }

    /**
     * 打印最短路径
     * 算法思路：使用分治法，例如v1->v2->v3->v4->v5，除v1外(v1在printPath函数中已经被特殊处理），其余节点可以划分为(vi,vk]和(vk,vj]这
     * 两部分，最终如果path[i][j] == NOT_A_VERTEX，则打印节点vj
     * @param i
     * @param j
     * @param path
     */
    private static void printPath1(int i, int j, int[][] path) {
        if (path[i][j] != NOT_A_VERTEX) {
            printPath1(i, path[i][j], path);
            System.out.print("->");
            printPath1(path[i][j], j, path);
        } else {
            System.out.print("v" + (j + 1));
        }
    }

    public static void main(String[] args) {
        int[][] a = {
                {0, 3, 8, INF, -4},
                {INF, 0, INF, 1, 7},
                {INF, 4, 0, INF, INF},
                {2, INF, -5, 0, INF},
                {INF, INF, INF, 6, 0}
        };
        int n = a.length;
        int[][] d = new int[n][n];
        int[][] path = new int[n][n];
        allPairs(a, d, path);
        printPath(3, 0, path);  // v4->v1
        System.out.println(d[3][0]);
        printPath(2, 0, path);  // v3->v2->v4->v1
        System.out.println(d[2][0]);
        printPath(0, 1, path);  // v1->v5->v4->v3->v2
        System.out.println(d[0][1]);
    }
}
