package com.kevin.algorithm.greedy.scheduler;

import java.util.Scanner;

/**
 * 多处理器调度作业的最短平均完成时间
 * @Author kevin
 * @Date 2016/10/22 11:36
 */
public class MinimumAverageCompletionTime {
    /**
     * 输入：
     3 9
     1 3
     2 5
     3 6
     4 10
     5 11
     6 14
     7 15
     8 18
     9 20
     * 输出：
     将机器1从0到3的时间段分配给作业1
     将机器3从0到5的时间段分配给作业2
     将机器2从0到6的时间段分配给作业3
     将机器1从3到13的时间段分配给作业4
     将机器3从5到16的时间段分配给作业5
     将机器2从6到20的时间段分配给作业6
     将机器1从13到28的时间段分配给作业7
     将机器3从16到34的时间段分配给作业8
     将机器2从20到40的时间段分配给作业9
     */
    public static void main(String[] args) {
        int m;      // 机器个数
        int n;      // 作业个数
        Job[] jobs; // 作业集

        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            m = in.nextInt();
            n = in.nextInt();
            jobs = new Job[n + 1];

            for(int i = 1; i <= n; i++)
                jobs[i] = new Job(in.nextInt(), in.nextInt());

            shortJobPriorityAlgorithm(jobs, n, m);
        }
    }

    /**
     * 短作业优先算法
     * @param jobs 作业集
     * @param n 作业个数
     * @param m 机器个数
     */
    private static void shortJobPriorityAlgorithm(Job[] jobs, int n, int m) {
        if(n <= m) {
            System.out.println("直接为每台机器分配一个作业");
            return;
        }

        quickSort(jobs, 1, n); // 将作业集中的作业按照所需处理时间从小到大排序

        MinHeap<Machine> heap = new MinHeap<>();
        for(int i = 1; i <= m; i++)
            heap.insert(new Machine(i, 0));

        for(int i = 1; i <= n; i++) {
            Machine machine = heap.removeMin();
            System.out.println("将机器" + machine.id +
                    "从" + machine.avail + "到" + (machine.avail + jobs[i].time) +
                    "的时间段分配给作业" + jobs[i].id);
            machine.avail += jobs[i].time;
            heap.insert(machine);
        }
    }

    private static void quickSort(Job[] jobs, int left, int right) {
        if(left < right) {
            int i = left, j = right;
            Job pivot = jobs[i];
            while(i < j) {
                while(i < j && jobs[j].time > pivot.time) j--;
                if(i < j) jobs[i++] = jobs[j];
                while(i < j && jobs[i].time < pivot.time) i++;
                if(i < j) jobs[j--] = jobs[i];
            }
            jobs[i] = pivot;
            quickSort(jobs, left, i - 1);
            quickSort(jobs, i + 1, right);
        }
    }
}
