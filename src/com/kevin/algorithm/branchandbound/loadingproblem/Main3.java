package com.kevin.algorithm.branchandbound.loadingproblem;

import com.kevin.algorithm.branchandbound.MaxHeap;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/31 16:11
 * 装载问题
 * 优先队列式分支限界法：我们可以使用最大堆来存储活结点，活结点x在优先队列中的优先级定义为从根节点到节点x的路径所对应的载重量
 * 再加上剩余集装箱的重量之和，优先队列中优先级最高的活结点成为下一个扩展节点，一旦有一个叶子节点成为当前扩展节点，则可以断言
 * 该叶子节点所对应的解即为最优解，此时可终止算法。
 * 输入示例：
4 70 20
20 10 26 15
 * 输出示例：
61
1 0 1 1
 */
public class Main3 {
    public static void main(String[] args) {
        int n;
        int c1;
        int c2;
        int[] w;
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            n = in.nextInt();
            c1 = in.nextInt();
            c2 = in.nextInt();
            w = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                w[i] = in.nextInt();
            }
            PriorityLoading loading = new PriorityLoading(n, c1, c2, w);
            loading.maxLoading();
            loading.output();
        }
    }

    private static class PriorityLoading {
        int n;              // 集装箱个数
        int c1;             // 第1艘轮船的载重量
        int c2;             // 第2艘轮船的载重量
        int[] w;            // 每个集装箱的重量
        MaxHeap<Node> heap; // 活节点优先队列
        int cw;             // 当前扩展节点的载重量
        int bestw;          // 最优载重量
        int[] r;            // 剩余容量数组
        int[] bestx;        // 最优解

        public PriorityLoading(int n, int c1, int c2, int[] w) {
            this.n = n;
            this.c1 = c1;
            this.c2 = c2;
            this.w = w;
            this.heap = new MaxHeap<>();
            this.cw = 0;
            this.bestw = 0;
            this.r = new int[n + 1];
            for (int i = n - 1; i > 0; i--) {
                r[i] = r[i + 1] + w[i + 1];
            }
            this.bestx = new int[n + 1];
        }

        public void maxLoading() {
            // 处理第一层节点
            Node e = null;  // 当前扩展节点
            int i = 1;      // 当前扩展节点所处的层
            int wt;
            while (i <= n) {
                wt = cw + w[i];
                if (wt <= c1) { // 处理左分支
                    if (wt > bestw) {
                        bestw = wt;
                    }
                    addLiveNode(e, true, i + 1, wt + r[i]);
                }
                if ( cw + r[i] >= bestw) {   // 处理右分支，这里用了限定条件剪去非最优解的分支
                    addLiveNode(e, false, i + 1, cw + r[i]);
                }
                e = heap.delete();
                i = e.level;
                cw = e.upweight - r[i - 1];
            }

            // 构造最优解
            for (int j = n; j > 0; j--) {
                bestx[j] = e.leftChild ? 1 : 0;
                e = e.parent;
            }
        }

        public void output() {
            System.out.println(bestw);
            for (int i = 1; i <= n; i++) {
                System.out.print(bestx[i] + " ");
            }
            System.out.println();
        }

        private void addLiveNode(Node parent, boolean leftChild, int level, int upweight) {
            Node node = new Node(parent, leftChild, level, upweight);
            heap.insert(node);
        }

        private static class Node implements Comparable<Node> {
            Node parent;        // 该节点的父节点
            boolean leftChild;  // 该节点是否是其父节点的左儿子
            int level;          // 该节点的下一层
            int upweight;       // 该节点的优先级，其值为从根节点到该节点的载重量再加上集装箱剩余容量

            public Node(Node parent, boolean leftChild, int level, int upweight) {
                this.parent = parent;
                this.leftChild = leftChild;
                this.level = level;
                this.upweight = upweight;
            }

            @Override
            public int compareTo(Node o) {
                return upweight < o.upweight ? -1 : (upweight == o.upweight) ? 0 : 1;
            }
        }
    }
}
