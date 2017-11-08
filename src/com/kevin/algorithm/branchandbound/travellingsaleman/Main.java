package com.kevin.algorithm.branchandbound.travellingsaleman;

import com.kevin.algorithm.branchandbound.MinHeap;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2017/1/3 10:43
 * 旅行售货员问题
 * 输入：第一行为城市的个数n，接下来的n行，每一行表示从一个城市到其他城市的路程(自身为-1)
 * 输出：第一行为总路程，第二行为经过的各个城市
 * 输入示例：
4
-1 30 6 4
30 -1 5 10
6 5 -1 20
4 10 20 -1
 * 输出示例：
25
1 3 2 4 1
 *
 * 算法分析：优先队列式分支限界法求解
 * 1、数据结构：对于每个节点，我们使用x[i]表示第i步到达第x[i]节点，s表示该已经确定了x[1:s]，cc表示从根节点到该节点的费用，
 * rcost表示从该节点到目标节点(即从该节点到根节点)中的各个节点的最小出边费用和，lcost表示当前解所需的最小费用(即费用下界)。
 * 优先队列中各节点的优先级通过lcost来决定。之所以在每个节点中都是用数组x[]保存当前解，是因为这里可能出现多分支，所以不能仅
 * 仅用parent和leftChild来保存当前解，而需要使用数组来保存。
 * 2、算法过程：初始时，算法计算图中每个节点的最小出边费用并用minOut[]记录，如果所给的有向图中某个顶点没有出边，则该图不可能有
 * 回路，算法结束，否则当算法确定第s层节点时，该节点的rcost=∑minOut[i], s<=i<=n，因此lcost=cc+rcost，此时如果lcost<bestc(
 * bestc记录当前解的最优值)，则将该节点入堆，否则将其抛弃。当s=n-1时，此时当前扩展节点是叶子节点的父节点，如果该叶子节点相对应
 * 的一条可行回路的费用(即该节点的cc值加上该节点到叶子节点的费用再加上叶子节点到目标节点的费用之和)小于当前最小费用，则将该节点
 * 入堆，否则抛弃。当s<n-1时，由于当前扩展节点所对应的路径是x[1:s]，因此其可行扩展节点x[i]要从x[s+1:n]中选取，并且(x[s],x[i])
 * 必须是给定的有向图中的一条边，对于每一个可行扩展节点，需要计算出其费用及下界lcost，只有当lcost<bestc时，才将该可行扩展节点
 * 加入到队中，否则抛弃。
 *
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
            City city = new City(n, graph);
            city.travel();
            city.output();
        }
    }

    private static class City {
        static final int NOT_A_EDGE = -1;
        int n;          // n座城市
        int[][] graph;  // 城市之间的邻接矩阵
        int bestc;   // 当前最优值
        int[] bestx;    // 当前最优解，bestX[i]bestX[i]
        MinHeap<Node> heap; // 活结点优先队列

        public City(int n, int[][] graph) {
            this.n = n;
            this.graph = graph;
            this.bestc = Integer.MAX_VALUE;
            this.bestx = new int[n + 1];
            this.heap = new MinHeap<>();
        }

        public void travel() {
            int[] minOut = new int[n + 1];  // 各顶点的最小出边费用
            int minSum = 0; // 最小出边费用和
            int min;
            for (int i = 1; i <= n; i++) {
                min = Integer.MAX_VALUE;
                for (int j = 1; j <= n; j++) {
                    if (graph[i][j] != NOT_A_EDGE && graph[i][j] < min) {
                        min = graph[i][j];
                    }
                }
                if (min == Integer.MAX_VALUE) {
                    System.out.println("没有回路");
                    return;
                }
                minOut[i] = min;
                minSum += min;
            }

            Node e = new Node();
            e.x = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                e.x[i] = i;
            }
            e.s = 1;
            e.cc = 0;
            e.rcost = minSum;
            e.lcost = minSum;

            while (e.s < n) {
                if (e.s == n - 1) { // 当前扩展节点是叶子节点的父节点，再加上2条边即构成回路，判断该回路是否优于最优解
                    if (graph[e.x[n - 1]][n] != NOT_A_EDGE &&
                            graph[e.x[n]][1] != NOT_A_EDGE &&
                            e.cc + graph[e.x[n - 1]][n] + graph[e.x[n]][1] < bestc) {
                        bestc = e.cc + graph[e.x[n - 1]][n] + graph[e.x[n]][1];
                        e.s++;
                        e.cc = bestc;
                        e.lcost = bestc;
                        heap.insert(e);
                    }
                } else {
                    for (int i = e.s + 1; i <= n; i++) {    // 第s+1步选取的顶点为x[i]，x[i]需要从x[s+1:n]中选取
                        if (graph[e.x[e.s]][e.x[i]] != NOT_A_EDGE) { // 如果(x[s],x[i])是一条边，则说明节点x[i]是一个可行节点
                            int cc = e.cc + graph[e.x[e.s]][e.x[i]];    // x[i]的费用为x[s]的费用加上(x[s],x[i])的费用
                            int rcost = e.rcost - minOut[e.x[e.s]];     // x[i]的剩余费用为x[s]的剩余费用减去x[s]的出边费用
                            int lcost = cc + rcost;
                            if (lcost < bestc) {    // 该子树中可能含有最优解，将其入堆
                                Node node = new Node();
                                node.x = new int[n + 1];
                                for (int j = 1; j <= n; j++) {
                                    node.x[j] = e.x[j];
                                }
                                node.x[e.s + 1] = e.x[i];   // 第s+1步选取的顶点为x[i]，因此将x[s+1]与x[i]交换
                                node.x[i] = e.x[e.s + 1];
                                node.s = e.s + 1;
                                node.cc = cc;
                                node.rcost = rcost;
                                node.lcost = lcost;
                                heap.insert(node);
                            }
                        }
                    }
                }
                e = heap.delete();
            }

            // 构造最优解
            for (int i = 1; i <= n; i++) {
                bestx[i] = e.x[i];
            }
        }

        public void output() {
            System.out.println(bestc);
            for (int i = 1; i <= n; i++) {
                System.out.print(bestx[i] + " ");
            }
            System.out.println();
        }

        private static class Node implements Comparable<Node> {
            int[] x;    // 当前解，x[i]表示第i步到达节点x[i]
            int s;      // 根节点到当前节点的路径为x[1:s]
            int cc;     // 从根节点到该节点的费用
            int rcost;  // 从该节点到目标节点中，各节点的最小出边费用和
            int lcost;  // 当前解所需的费用下界，其值为cc+rcost

            public Node() {
            }

            public Node(int[] x, int s, int cc, int rcost, int lcost) {
                this.x = x;
                this.s = s;
                this.cc = cc;
                this.rcost = rcost;
                this.lcost = lcost;
            }

            @Override
            public int compareTo(Node o) {
                return lcost < o.lcost ? -1 : (lcost == o.lcost) ? 0 : 1;
            }
        }
    }
}
