package com.kevin.algorithm.backtracking.subsettree.maximumclique;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/23 16:32
 * 最大团问题
 * 问题描述：
 * 给定无向图G={V,E}，以下给出几个概念。
 * 完全子图：G1={V1,E1}，其中V1是V的子集，且对于任意的u属于V1，v属于V1，有(u,v)属于E，则称G1是G的完全子图
 * G的团：G的完全子图G1是G的团，当且仅当G1不包含在G的更大的完全子图中
 * G的最大团：顶点数最多的团
 * 空子图：G2={V2,E2}，其中V2是V的子集，且对于任意的u属于V2，v属于V2，有(u,v)不属于E，则称G2是G的空子图
 * G的独立集：G的空子图G2是G的独立集，当且仅当G2不包含在G的更大的空子图中
 * G的最大独立集：顶点数最多的独立集
 * 补图：G'={V,E'}，其中E'是G对应的完全图的边集减去E
 * 如果G1是G的完全子图，则G1是G'的空子图，反之亦然。因此，G的团与G'的独立集之间存在一一对应的关系。特殊地，G1是G的最大团，
 * 当且仅当G1是G'的最大独立集。
 * 输入：第一行是顶点个数n，接下来的n行是无向图的邻接矩阵
 * 输出：最优解，x[i]=1表示该顶点是最大团中的一个顶点，为0表示该顶点不是最大团中的一个顶点
 * 输入示例：
5
0 1 0 1 1
1 0 1 0 1
0 1 0 0 1
1 0 0 0 1
1 1 1 1 0
 * 输出示例：
1 1 0 0 1
1 0 0 1 1
0 1 1 0 1
 * 算法分析：无向图G的最大团和最大独立集问题都可以用回溯法在O(n2^n)的时间内解决。图G的最大团和最大独立集问题都可以看做是
 * 图G的顶点集V的子集选取问题，因此可以用子集树来表示问题的解空间。首先设最大团为一个空团，往其中加入一个顶点，然后依次考
 * 虑团中的每个顶点，查看该顶点加入团之后是否仍然构成一个团，如果可以，则考虑将该顶点加入团或者舍弃两种情况，如果不行，则
 * 直接舍弃，然后递归判断下一顶点。对于无连接或者直接舍弃两种情况，在递归前，可采用剪枝策略来避免无效搜索。为了判断当前顶
 * 点加入团之后是否仍是一个团，只需要考虑该顶点和团中顶点是否都有连接。程序中采用了一个比较简单的剪枝策略，即如果剩余未考
 * 虑的顶点数加上团中顶点数不大于当前解的顶点数，可停止继续深度搜索，否则继续深度递归，当搜索到一个叶结点时，即可停止搜索，
 * 此时更新最优解和最优值。
 */
public class Main {
    public static void main(String[] args) {
        int n;
        int[][] graph;
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            n = in.nextInt();
            graph = new int[n + 1][n + 1];
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    graph[i][j] = in.nextInt();
                }
            }
            Clique clique = new Clique(n, graph);
            clique.backtrack(1);
        }
    }

    private static class Clique {
        int n;          // 顶点个数
        int[][] graph;  // 邻接矩阵表示的无向图
        int[] x;        // 当前解，x[i]=1表示该顶点是最大团中的一个顶点
        int[] bestx;    // 最优解，bestx[i]=1表示该顶点是最大团中的一个顶点
        int cn;         // 当前解中的顶点个数
        int bestn;      // 最优解中的顶点个数

        public Clique(int n, int[][] graph) {
            this.n = n;
            this.graph = graph;
            this.x = new int[n + 1];
            this.bestx = new int[n + 1];
            this.cn = 0;
            this.bestn = 0;
        }

        public void backtrack(int t) {
            if (t > n) {
                bestn = cn;
                for (int i = 1; i <= n; i++) {
                    bestx[i] = x[i];
                    System.out.print(bestx[i] + " ");
                }
                System.out.println();
            } else {
                boolean flag = true;
                for (int i = 1; i < t; i++) {
                    if (x[i] == 1 && graph[t][i] == 0) {    // 如果该顶点与当前团中的各个顶点不互相关联，则直接舍弃
                        flag = false;
                        break;
                    }
                }
                if (flag) { // 如果该顶点与当前团中的各个顶点互相关联，则存在两种情况
                    x[t] = 1;   // 第一种情况，将该顶点加入当前团
                    cn++;
                    backtrack(t + 1);
                    x[t] = 0;
                    cn--;
                }
                if (cn + n - t >= bestn) {   // 第二种情况，不将该顶点加入当前团，这里用到了限定条件去掉非最优解
                    x[t] = 0;
                    backtrack(t + 1);
                }
            }
        }
    }
}
