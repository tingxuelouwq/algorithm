package com.kevin.algorithm.branchandbound.circuitboardpermutation;

import com.kevin.algorithm.branchandbound.MinHeap;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2017/1/3 20:50
 * 电路板排列问题
 * 输入：第1行是2个正整数m和n。接下来的n行中，每行有m个数，第i行的第j个数为0表示电路板i不在连接块j中，为1表示电路板i在连接块
 * j中
 * 输出：第1行是最小长度，接下来的1行是最佳排列
 * 输入示例：
5 8
0 0 1 0 0
0 1 0 0 0
0 1 1 1 0
1 0 0 0 0
1 0 0 0 0
1 0 0 1 0
0 0 0 0 1
0 0 0 0 1
 * 输出示例：
2
7 8 5 4 6 2 3 1
 *
 * 优先队列式分支限界法求解电路板排列问题
 * 数据结构：电路板排列问题的解空间树是全排列树。每一个节点中，x[i]表示第i个插槽插入电路板i，s表示已确定的电路板排列x[1:s]，
 * cd表示当前电路板排列的密度，now[j]表示在当前电路板排列x[1:s]中，连接块j中的电路板数。优先级由电路板密度cd确定。
 * 算法思路：当s=n-1时，此时已经确定了x[1:n-1]，故当期扩展节点是排列树中的一个叶子节点的父节点，x[]表示相应于该叶子节点的
 * 电路板排列，此时只需要计算出与x[]对应的电路板密度，并在必要时更新当前最优值bestd和最优解bestx即可。当s<n-1时，算法会从
 * x[s+1]到x[n]中选取第s+1个节点，即产生当前扩展节点的所有儿子节点，对于每一个儿子节点，计算出其相应的电路板密度cd，当
 * cd<bestd时，将该儿子节点加入到最小堆中，否则抛弃。
 */
public class Main {
    public static void main(String[] args) {
        int n;
        int m;
        int[][] b;
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            m = in.nextInt();
            n = in.nextInt();
            b = new int[n + 1][m + 1];
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    b[i][j] = in.nextInt();
                }
            }
            Circuit circuit = new Circuit(m, n, b);
            circuit.circuitBoardPermutation();
            circuit.output();
        }
    }

    private static class Circuit {
        int m;          // 连接板数
        int n;          // 电路板数
        int[][] b;      // b[i][j]=1表示电路板i在连接块j中
        int[] bestx;    // 最优排列
        int bestd;      // 最优排列的密度
        MinHeap<Node> heap; // 活结点队列

        public Circuit(int m, int n, int[][] b) {
            this.m = m;
            this.n = n;
            this.b = b;
            this.bestx = new int[n + 1];
            this.bestd = m + 1;
            heap = new MinHeap<>();
        }

        public void circuitBoardPermutation() {
            Node e = new Node(new int[n + 1], 0, 0, new int[m + 1]);
            int[] total = new int[m + 1];   // total[j]表示连接块j所含的电路板数
            for (int i = 1; i <= n; i++) {
                e.x[i] = i; // 初始排列为1,2,...,n
                for (int j = 1; j <= m; j++) {
                    total[j] += b[i][j];
                }
            }

            do {
                if (e.s == n - 1) { // 当前扩展节点是叶子节点的父节点
                    int ld = 0; // 最后一块电路板的密度
                    for (int j = 1; j <= m; j++) {
                        ld += b[e.x[n]][j];
                    }
                    ld = Math.max(ld, e.cd);
                    if (ld < bestd) {   // 找到密度更小的电路板排列
                        bestx = e.x;
                        bestd = ld;
                    }
                }
                for (int i = e.s + 1; i <= n; i++) {    // x[1:s]已确定，要在x[s+1]到x[n]中选取下一个节点，即选取第s+1个节点，其值为x[i]
                    Node node = new Node(new int[n + 1], 0, 0, new int[m + 1]);
                    int ld = 0; // 此时的x[1:s+1]的电路板密度
                    for (int j = 1; j <= m; j++) {
                        node.now[j] = e.now[j] + b[e.x[i]][j];
                        if (node.now[j] > 0 && node.now[j] != total[j]) {
                            ld++;
                        }
                    }
                    node.cd = Math.max(ld, e.cd);
                    if (node.cd < bestd) {  // 可能产生更好的排列
                        node.s = e.s + 1;
                        for (int j = 1; j <= n; j++) {
                            node.x[j] = e.x[j];
                        }
                        node.x[e.s + 1] = e.x[i];   // 第s+1步选取的节点为x[i]，因此将x[s+1]与x[i]交换
                        node.x[i] = e.x[e.s + 1];
                        heap.insert(node);
                    }
                }
                // 取下一个扩展节点
                e = heap.delete();
            } while (e != null && e.cd < bestd);
        }

        public void output() {
            System.out.println(bestd);
            for (int i = 1; i <= n; i++) {
                System.out.print(bestx[i] + " ");
            }
            System.out.println();
        }
    }

    private static class Node implements Comparable<Node> {
        int[] x;    // x[i]表示第i个插槽插入的电路板是x[i]
        int s;      // x[1:s]表示当前节点所对应的部分排列
        int cd;     // x[1:s]的密度
        int[] now;  // now[j]表示x[1:s]中所含连接块j的电路板数

        public Node() {
        }

        public Node(int[] x, int s, int cd, int[] now) {
            this.x = x;
            this.s = s;
            this.cd = cd;
            this.now = now;
        }

        @Override
        public int compareTo(Node o) {
            return cd < o.cd ? -1 : cd == o.cd ? 0 : 1;
        }
    }
}
