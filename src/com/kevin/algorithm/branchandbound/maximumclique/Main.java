package com.kevin.algorithm.branchandbound.maximumclique;

import com.kevin.algorithm.branchandbound.MaxHeap;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2017/1/2 20:28
 * 最大团问题
 * 优先队列式分支限界法求解：解最大团问题的解空间树是一颗子集树。每一个活结点中，都用cliqueSize表示
 * 与该节点相对应的团的顶点数，upperSize表示以该节点为为根的子树中最大顶点数，level表示该节点在解空
 * 间树中所处的层。为了方便回溯，因此用parent表示该节点的父节点，leftChild表示该节点是否是其父节点
 * 的左儿子。
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
3
0 1 1 0 1
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
            clique.maxClique();
            clique.output();
        }
    }

    private static class Clique {
        int n;              // 无向连通图中顶点个数
        int[][] graph;      // 图的邻接矩阵
        MaxHeap<Node> heap; // 活结点优先队列
        int cn;             // 当前团中的顶点数
        int bestn;          // 当前最大团中的顶点数
        int[] bestx;        // 最优解

        public Clique(int n, int[][] graph) {
            this.n = n;
            this.graph = graph;
            this.heap = new MaxHeap<>();
            this.cn = 0;
            this.bestn = 0;
            this.bestx = new int[n + 1];
        }

        public void maxClique() {
            Node e = null;  // 标记当前扩展节点
            int i = 1;      // 标记该节点所处的层
            while (i != n + 1) {
                boolean ok = true;
                Node b = e;
                for (int j = i - 1; j > 0; b = b.parent, j--) {
                    if (b.leftChild && graph[i][j] == 0) {  // 如果该顶点与当前团中的各个顶点不互相关联，则直接舍弃
                        ok = false;
                        break;
                    }
                }
                if (ok) {   // 如果该顶点与当前团中的各个顶点互相关联，则存在两种情况
                    if (cn + 1 > bestn) {   // 第一种情况，将该顶点加入当前团
                        bestn = cn + 1;
                    }
                    addLiveNode(e, true, cn + 1 + n - i, cn + 1, i + 1);
                }
                if (cn + n - i > bestn) {   // 第二种情况，不将该顶点加入当前团，这里用到了限定条件去掉非最优解
                    addLiveNode(e, false, cn + n - i, cn, i + 1);
                }
                e = heap.delete();  // 取下一个扩展节点
                cn = e.cliqueSize;
                i = e.level;
            }

            // 构造最优解
            for (int j = n; j > 0; j--) {
                bestx[j] = e.leftChild ? 1 : 0;
                e = e.parent;
            }
        }

        public void output() {
            System.out.println(bestn);
            for (int i = 1; i <= n; i++) {
                System.out.print(bestx[i] + " ");
            }
            System.out.println();
        }

        private void addLiveNode(Node parent, boolean leftChild, int upperSize, int cliqueSize, int level) {
            Node node = new Node(parent, leftChild, upperSize, cliqueSize, level);
            heap.insert(node);
        }


        private static class Node implements Comparable<Node> {
            Node parent;            // 该节点的父节点
            boolean leftChild;      // 该节点是否是其父节点的左儿子
            int upperSize;          // 当前团最大顶点数的上界
            int cliqueSize;         // 当前团的顶点数
            int level;              // 节点在子集空间树中所处的层

            public Node(Node parent, boolean leftChild, int upperSize, int cliqueSize, int level) {
                this.parent = parent;
                this.leftChild = leftChild;
                this.upperSize = upperSize;
                this.cliqueSize = cliqueSize;
                this.level = level;
            }

            @Override
            public int compareTo(Node o) {
                return upperSize < o.upperSize ? -1 : (upperSize == o.upperSize) ? 0 : 1;
            }
        }
    }
}
