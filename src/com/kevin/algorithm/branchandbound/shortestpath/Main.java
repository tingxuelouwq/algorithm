package com.kevin.algorithm.branchandbound.shortestpath;

import com.kevin.algorithm.branchandbound.MinHeap;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/30 19:34
 * 单源最短路径问题
 * 问题描述：在给定的有向图G中，每一边都有一个非负边权，要求图G的从源顶点s到目标顶点t之间的最短路径。
 * 算法分析：使用优先队列分支限界法。初始时，队列中只有源顶点s，遍历s的所有邻接顶点，如果dist[s]+c(s,v)<dist[v]，则并更新顶点
 * v的路径值，然后选取路径值最小的顶点作为当前扩展节点，继续搜索。
 * 输入：第一行是顶点个数n，源点s和宿点t，接下来的n行是邻接矩阵
 * 输出：第一行是从源点s到宿点t的最短路径值，第二行是最短路径
 * 输入示例：
 11 0 10
 0 2 3 4 0 0 0 0 0 0 0
 0 0 3 0 7 2 0 0 0 0 0
 0 0 0 0 0 9 2 0 0 0 0
 0 0 0 0 0 0 2 0 0 0 0
 0 0 0 0 0 0 0 2 3 0 0
 0 0 0 0 0 0 1 0 3 0 0
 0 0 0 0 0 0 0 0 5 1 0
 0 0 0 0 0 0 0 0 0 0 1
 0 0 0 0 0 0 0 0 0 0 2
 0 0 0 0 0 0 0 0 0 0 2
 0 0 0 0 0 0 0 0 0 0 0
 * 输出示例：
 8
 10 9 6 2 0
 */
public class Main {
    public static void main(String[] args) {
        int n;
        int s;
        int t;
        int[][] graph;
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            n = in.nextInt();
            s = in.nextInt();
            t = in.nextInt();
            graph = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    graph[i][j] = in.nextInt();
                }
            }
            Path path = new Path(n, graph);
            path.shortPath(s, t);
        }
    }

    private static class Path {
        static final int INF = Integer.MAX_VALUE;
        int n;          // 有向图中顶点个数
        int[][] graph;  // 有向图的邻接矩阵表示
        int[] dist;     // 距离
        int[] pre;      // 前驱

        public Path(int n, int[][] graph) {
            this.n = n;
            this.graph = graph;
        }

        public void shortPath(int s, int t) {
            dist = new int[n];
            for (int i = 0; i < n; i++) {
                dist[i] = INF;
            }
            dist[0] = 0;
            pre = new int[n];
            pre[0] = -1;

            MinHeap<Integer> minHeap = new MinHeap<>();
            minHeap.insert(s);
            while (!minHeap.isEmpty()) {
                int i = minHeap.delete();
                for (int j = 0; j < n; j++) {
                    if (graph[i][j] > 0 && (dist[i] + graph[i][j] < dist[j])) {
                        dist[j] = dist[i] + graph[i][j];
                        pre[j] = i;
                        minHeap.insert(j);
                    }
                }
            }

            System.out.println(dist[t]);
            while (pre[t] != -1) {
                System.out.print(t + " ");
                t = pre[t];
            }
            System.out.println(t);
        }
    }
}
