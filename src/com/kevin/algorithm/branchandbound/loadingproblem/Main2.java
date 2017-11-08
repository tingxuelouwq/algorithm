package com.kevin.algorithm.branchandbound.loadingproblem;

import com.kevin.algorithm.branchandbound.ArrayQueue;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/31 11:15
 * 装载问题
 * 注：这里给出队列式分支限界法求解的最优解
 * 输入示例：
 4 70 20
 20 10 26 15
 * 输出示例：
 61
 1 0 1 1
 */
public class Main2 {
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
            FIFOLoading loading = new FIFOLoading(n, c1, c2, w);
            loading.maxLoading();
            loading.output();
        }
    }

    private static class FIFOLoading {
        int n;          // 集装箱个数
        int c1;         // 第1艘轮船的载重量
        int c2;         // 第2艘轮船的载重量
        int[] w;        // w[i]表示集装箱i的重量
        ArrayQueue<Node> queue; // 活节点队列
        int cw;         // 当前扩展节点的载重量
        int bestw;      // 最优载重量
        int r;          // 剩余集装箱容量
        Node bestE;     // 当前最优扩展节点
        int[] bestx;    // 当前最优解

        public FIFOLoading(int n, int c1, int c2, int[] w) {
            this.n = n;
            this.c1 = c1;
            this.c2 = c2;
            this.w = w;
            this.queue = new ArrayQueue<>();
            this.cw = 0;
            this.bestw = 0;
            this.r = 0;
            this.bestE = null;
            this.bestx = new int[n + 1];
        }

        /**
         *
         * @param wt 节点所对应的载重量
         * @param i 节点的层数
         * @param parent 节点的父节点
         * @param leftChild 节点是否是其父节点的左儿子
         */
        private void enqueue(int wt, int i, Node parent, boolean leftChild) {
            if (i == n) {
                if (wt == bestw) {
                    bestE = parent;
                    bestx[n] = leftChild ? 1 : 0;
                }
                return;
            }

            Node node = new Node(parent, leftChild, wt);
            queue.offer(node);
        }

        public void maxLoading() {
            // 处理第一层节点
            Node e = null;
            queue.offer(null);      // 同层节点尾部标志
            int i = 1;              // 当前扩展节点所处的层
            for (int j = 2; j <= n; j++) {
                r += w[j];
            }

            while (true) {  // 搜索子集空间树
                // 检查左儿子节点
                int wt = cw + w[i]; // 左儿子节点的重量
                if (wt <= c1) {
                    if (wt > bestw) {
                        bestw = wt;
                    }
                    enqueue(wt, i, e, true);
                }

                // 检查右儿子节点
                if (cw + r > bestw) {   // 可能含最优解
                    enqueue(cw, i, e, false);
                }

                // 取下一个扩展节点
                e = queue.poll();
                if (e == null) {
                    if (queue.isEmpty()) {
                        break;
                    }
                    queue.offer(null);      // 同层节点尾部标志
                    e = queue.poll();       // 取下一个扩展节点
                    i++;
                    r -= w[i];
                }
                cw = e.weight;  // 新扩展节点相应的载重量
            }

            // 构造最优解
            for (int j = n - 1; j > 0; j--) {
                bestx[j] = bestE.leftChild ? 1 : 0;
                bestE = bestE.parent;
            }
        }

        public void output() {
            System.out.println(bestw);
            for (int i = 1; i <= n; i++) {
                System.out.print(bestx[i] + " ");
            }
            System.out.println();
        }

        /**
         * 为了在算法结束后能方便地构造出与最优值相对应的最优解，算法必须存储相应子集树中从活结点到根节点的路径，因此可在每个
         * 节点处设置指向父节点的指针，并设置左、右儿子标志
         */
        private static class Node {
            Node parent;        // 父节点
            boolean leftChild;  // 是否是父节点的左儿子
            int weight;         // 节点所对应的载重量

            public Node(Node parent, boolean leftChild, int weight) {
                this.parent = parent;
                this.leftChild = leftChild;
                this.weight = weight;
            }
        }
    }
}
