package com.kevin.algorithm.greedy.activityarrange;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/10/26 18:30
 *
 * 问题描述：
 * 设有n个活动的集合E={1,2,…,n}，其中每个活动都要求使用同一资源，如演讲会场等，而在同一时间内只有一个活动能使用这一资源。每个活动i
 * 都有一个要求使用该资源的起始时间si和一个结束时间fi,且si <fi。如果选择了活动i，则它在半开时间区间[si, fi)内占用资源。若区间[si,
 * fi)与区间[sj, fj)不相交,则称活动i与活动j是相容的。也就是说，当si≥fj或sj≥fi时，活动i与活动j相容。活动安排问题就是要在所给的活动
 * 集合中选出最大的相容活动子集合。 
 *
 * 求解思路：
 * 用i代表第i个活动，s[i]代表第i个活动开始时间，f[i]代表第i个活动的结束时间。将活动结束时间从小到大排序，挑选出结束时间尽量早的，并
 * 且满足后一个活动的起始时间晚于前一个活动的结束时间的活动，全部找出这些活动就是最大的相容活动子集合。
 *
 * 输入示例：
11
1 4
3 5
0 6
5 7
3 8
5 9
6 10
8 11
8 12
2 13
12 14
 * 输出示例：
1 4
5 7
8 11
12 14
 */
public class Main {
    public static void main(String[] args) {
        int n;
        int[] s, f;
        boolean[] selected;
        Scanner in = new Scanner(System.in);
        while(in.hasNextInt()) {
            n = in.nextInt();
            s = new int[n + 1];
            f = new int[n + 1];
            selected = new boolean[n + 1];
            for(int i = 1; i <= n; i++) {
                s[i] = in.nextInt();
                f[i] = in.nextInt();
            }

            greedySelect(s, f, n, selected);
            for(int i = 1; i <= n; i++)
                if(selected[i])
                    System.out.println(s[i] + " " + f[i]);
        }
    }

    private static void greedySelect(int[] s, int[] f, int n, boolean[] selected) {
        selected[1] = true;
        int j = 1;  // 记录最近一次加入到集合中的活动

        for(int i = 2; i <= n; i++) {
            if(s[i] >= f[j]) {
                selected[i] = true;
                j = i;
            }
        }
    }
}
