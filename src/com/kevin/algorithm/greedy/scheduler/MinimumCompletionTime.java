package com.kevin.algorithm.greedy.scheduler;

import java.util.Scanner;

/**
 * 问题：
 * 设有n个独立的作业[1,2,...,n}，由m台相同的机器进行加工处理，作业i所需的时间为ti。约定：任何作业可以在任何一台机器上加工处理，
 * 但未完工前不允许中断处理；任何作业不能拆分成更小的作业。要求给出一种调度方案，使所给的n个作业在尽可能短的时间内由m台机器加工
 * 处理完成。
 *
 * 分析：
 * 多机的最短完成时间问题是一个NPC问题，到目前为止还没有完全有效的解法。对于这类问题，用贪心选择策略有时可以设计出一个比较好的
 * 近似解法。
 *
 * 思路：
 * 采用长作业优先的贪心策略。
 * 当n<=m时，只要将机器i的[0,ti]时间段分配给作业i即可；
 * 当n>m时，将n个作业所需的处理时间由大到小排序，然后依次将作业分配给最空闲的机器进行处理。
 *
 * @Author kevin
 * @Date 2016/10/21 20:18
 */
public class MinimumCompletionTime {
    /**
     * 输入：
     3 7
     1 2
     2 14
     3 4
     4 16
     5 6
     6 5
     7 3
     *输出：
     将机器1从0到16的时间段分配给作业4
     将机器3从0到14的时间段分配给作业2
     将机器2从0到6的时间段分配给作业5
     将机器2从6到11的时间段分配给作业6
     将机器2从11到15的时间段分配给作业3
     将机器3从14到17的时间段分配给作业7
     将机器2从15到17的时间段分配给作业1
     *
     */
    public static void main(String[] args) {
        int m;          // 机器个数
        int n;          // 作业个数
        Job[] jobs;     // 作业集

        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            m = in.nextInt();
            n = in.nextInt();
            jobs = new Job[n + 1];

            for(int i = 1; i <= n; i++)
                jobs[i] = new Job(in.nextInt(), in.nextInt());

            greedy(jobs, n, m);
        }
    }

    /**
     * Take the long job priority greedy strategy to get the minimum completion time.
     * @param jobs job set
     * @param n the number of jobs
     * @param m the number of machines
     */
    private static void greedy(Job[] jobs, int n, int m) {
        if(n <= m) {
            System.out.println("直接为每个作业分配一台机器");
            return;
        }

        selectSort(jobs, n);   // descending sort the jobs according to the corresponding time

        MinHeap<Machine> heap = new MinHeap<>();    // init machines
        for(int i = 1; i <= m; i++) {
            Machine machine = new Machine(i, 0);
            heap.insert(machine);
        }

        for(int i = 1; i <= n; i++) {
            Machine machine = heap.removeMin();
            System.out.println("将机器" + machine.id +
                    "从" + machine.avail + "到" + (machine.avail + jobs[i].time) +
                    "的时间段分配给作业" + jobs[i].id);
            machine.avail += jobs[i].time;
            heap.insert(machine);
        }
    }

    /**
     * Descending sort the jobs according to the corresponding time
     * @param jobs job set
     * @param n the number of jobs
     */
    private static void selectSort(Job[] jobs, int n) {
        Job tmp;
        int max;

        for(int i = 1; i < n; i++) {
            max = i;
            for(int j = i + 1; j <= n; j++) {
                if(jobs[max].time < jobs[j].time)
                    max = j;
            }

            if(max != i) {
                tmp = jobs[i];
                jobs[i] = jobs[max];
                jobs[max] = tmp;
            }
        }
    }
}
