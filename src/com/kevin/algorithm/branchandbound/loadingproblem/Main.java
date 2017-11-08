package com.kevin.algorithm.branchandbound.loadingproblem;

import com.kevin.algorithm.branchandbound.ArrayQueue;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/30 21:25
 * 装载问题
 * 注：这里的程序只给出了最优值，并没有给出最优解。最优解的代码见Main2.java
 * 输入示例：
4 70 20
20 10 26 12
 * 输出示例：
61
 * 算法分析：容易证明，如果一个给定装载问题有解，则采用下面的策略可得到最优装载方案。
 * (1)首先将第一艘轮船尽可能装满；
 * (2)将剩余的集装箱装上第二艘轮船。
 * 队列式分支限界法求解：
 * 1、在算法的循环体中，首先将当前扩展节点出队，检测当前扩展节点的左儿子节点是否为可扩展节点，如果是则将其加入到活队列中，然后
 * 将其右儿子节点加入到活结点队列中(右儿子节点一定是可扩展节点，暂时没有考虑剪枝)
 * 2、活结点队列中的队首元素被取出来作为当前扩展节点，由于队列中每一层节点之后都有一个尾部标记-1，故在取队首元素时，活结点队列
 * 一定不空。当取出的元素是-1时，再判断当前队列是否为空。如果队列非空，则将尾部标记-1加入活结点队列，算法开始处理下一层的活结点
 * 3、节点的左子树表示将此集装箱装上船，右子树表示不将此集装箱装上船。设bestw是当前最优解；cw是当前扩展结点所相应的重量；r是剩
 * 余集装箱的重量。则当cw+r<bestw时，可将其右子树剪去。另外，为了确保右子树成功剪枝，应该在算法每一次进入左子树的时候更新bestw
 * 的值
 */
public class Main {
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
            System.out.println(loading.maxLoading());
        }
    }

    private static class FIFOLoading {
        int n;          // 集装箱个数
        int c1;         // 第1艘轮船的载重量
        int c2;         // 第2艘轮船的载重量
        int[] w;        // w[i]表示集装箱i的重量
        ArrayQueue<Integer> queue;  // 活结点队列
        int cw;         // 当前扩展节点的载重量
        int bestw;      // 最优载重量
        int r;          // 剩余集装箱容量

        public FIFOLoading(int n, int c1, int c2, int[] w) {
            this.n = n;
            this.c1 = c1;
            this.c2 = c2;
            this.w = w;
            queue = new ArrayQueue<>();
            cw = 0;
            bestw = 0;
        }

        public int maxLoading() {
            // 处理第一层节点
            queue.offer(-1);    // 同层节点尾部标志
            int i = 1;          // 当前扩展节点所处的层
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
                    if (i < n) {
                        queue.offer(wt);
                    }
                }

                // 检查右儿子节点
                if (cw + r > bestw && i < n) {   // 可能含最优解
                    queue.offer(cw);
                }

                // 取下一个扩展节点
                cw = queue.poll();
                if (cw == -1) {
                    if (queue.isEmpty()) {
                        return bestw;
                    }
                    queue.offer(-1);        // 同层节点尾部标志
                    cw = queue.poll();      // 取下一个扩展节点
                    i++;
                    r -= w[i];
                }
            }
        }
    }
}
