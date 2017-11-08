package com.kevin.algorithm.branchandbound.backpack01;

import com.kevin.algorithm.branchandbound.MaxHeap;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2017/1/2 9:52
 * 0-1背包问题
 * 问题描述：给定n种物品和一背包，物品i的重量是wi，其价值为vi，背包的容量为C，问应如何选择装入背包的物品，使得装入背包中物品
 * 的总价值最大
 * 形式化描述：给定c>0, wi>0, vi>0, 1≤i≤n，要求找一n元向量(x1,x2,…,xn,)，xi∈{0,1}，使得∑wi·xi≤c，且∑vi·xi最大
 * 输入：第一行是两个整数，分别代表物品个数n和背包容量c；第二行是各个物品的重量；第三行是各个物品的价值
 * 输出：第一行是最优总价值，第二行是各个物品的装入情况
 * 输入示例：
3 16
10 8 5
5 4 1
 * 输出示例：
6
1 0 1
 *
 * 算法分析：使用优先队列式分支限界法。活节点队列节点x的优先级定义为从根节点到该节点的物品价值再加上剩余价值。
 */
public class Main {
    public static void main(String[] args) {
        int n;
        int c;
        int[] w;
        int[] v;
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            n = in.nextInt();
            c = in.nextInt();
            w = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                w[i] = in.nextInt();
            }
            v = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                v[i] = in.nextInt();
            }
            Backpack pack = new Backpack(n, c, w, v);
            pack.backpack();
            pack.output();
        }
    }

    private static class Backpack {
        int n;          // 物品个数
        int c;          // 背包容量
        int[] w;        // 各个物品的重量
        int[] v;        // 各个物品的价值
        int cw;         // 当前重量
        int cv;         // 当前价值
        int[] r;        // 剩余价值数组
        int bestv;      // 最优价值
        int[] bestx;    // 最优解
        MaxHeap<Node> heap; // 活结点队列

        public Backpack(int n, int c, int[] w, int[] v) {
            this.n = n;
            this.c = c;
            this.w = w;
            this.v = v;
            this.cw = 0;
            this.cv = 0;
            this.r = new int[n + 1];
            this.bestx = new int[n + 1];
            this.heap = new MaxHeap<>();
        }

        public void backpack() {
            Node e = null;  // 当前扩展节点
            int i = 1;      // 当前扩展节点所处的层
            for (int j = n - 1; j > 0; j--) {
                r[j] = r[j + 1] + v[j + 1];
            }
            int wt;
            int ve;
            while (i <= n) {
                wt = cw + w[i];
                ve = cv + v[i];
                if (wt <= c) {  // 处理左分支
                    if (ve > bestv) {
                        bestv = ve;
                    }
                    addLiveNode(e, true, i + 1, ve + r[i], wt, ve);
                }
                if (cv + r[i] >= bestv) {    // 处理右分支，使用限定条件剪枝(这里注意要加上等号，因为当cv+r[i]==bestv时右
                                             // 分支是一个可行解，如果下一次循环中发现左分支不是最优解，则可以转过来看右分
                                             // 支是否是最优解)
                    addLiveNode(e, false, i + 1, cv + r[i], cw, cv);
                }
                e = heap.delete();
                i = e.level;
                cw = e.weight;
                cv = e.value;
            }

            // 构造最优解
            for (int j = n; j > 0; j--) {
                bestx[j] = e.leftChild ? 1 : 0;
                e = e.parent;
            }
        }

        public void output() {
            System.out.println(bestv);
            for (int i = 1; i <= n; i++) {
                System.out.print(bestx[i] + " ");
            }
            System.out.println();
        }

        private void addLiveNode(Node parent, boolean leftChild, int level, int upvalue, int weight, int value) {
            Node node = new Node(parent, leftChild, level, upvalue, weight, value);
            heap.insert(node);
        }

        private static class Node implements Comparable<Node> {
            Node parent;        // 该节点的父节点
            boolean leftChild;  // 该节点是否是其父节点的左儿子
            int level;          // 该节点的下一层
            int upvalue;        // 该节点的优先级，其值为从根节点到该节点的价值再加上剩余价值之和
            int weight;         // 该节点的重量
            int value;          // 该节点的价值

            public Node(Node parent, boolean leftChild, int level, int upvalue, int weight, int value) {
                this.parent = parent;
                this.leftChild = leftChild;
                this.level = level;
                this.upvalue = upvalue;
                this.weight = weight;
                this.value = value;
            }

            @Override
            public int compareTo(Node o) {
                return upvalue < o.upvalue ? -1 : (upvalue == o.upvalue) ? 0 : 1;
            }
        }
    }
}
