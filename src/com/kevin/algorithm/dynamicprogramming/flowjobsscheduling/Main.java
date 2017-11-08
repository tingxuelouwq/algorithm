package com.kevin.algorithm.dynamicprogramming.flowjobsscheduling;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/12 16:29
 * 流水作业调度
 * 输入：第一行是一个整数，表示流水作业个数；接下来的两行分别为作业在M1和M2上的加工时间
 * 输出：第一行是最短作业加工时间，第二行是最优调度顺序
 * 输入示例：
    5
    2 4 3 6 1
    5 2 3 1 7
 * 输出示例：
    19
    4 0 2 1 3
 */
public class Main {
    public static void main(String[] args) {
        int n;      // 流水作业个数
        int[] a;    // 作业在M1上的加工时间
        int[] b;    // 作业在M2上的加工时间
        int[] c;    // 最优调度
        int i;
        Scanner sin = new Scanner(System.in);
        while (sin.hasNextInt()) {
            n = sin.nextInt();
            a = new int[n];
            for (i = 0; i < n; i++)
                a[i] = sin.nextInt();
            b = new int[n];
            for (i = 0; i < n; i++)
                b[i] = sin.nextInt();
            c = new int[n];

            System.out.println(flowjobs(n, a, b, c));
            for (i = 0; i < n; i++)
                System.out.print(c[i] + " ");
            System.out.println();
        }
    }

    /**
     * 时间复杂度是O(nlogn)，主要是进行排序
     * @param n 流水作业个数
     * @param a 作业在M1上的加工时间
     * @param b 作业在M2上的加工时间
     * @param c 最优调度顺序
     * @return
     */
    private static int flowjobs(int n, int[] a, int[] b, int[] c) {
        int i;
        JobType[] jobs = new JobType[n];
        for (i = 0; i < n; i++) {
            jobs[i] = new JobType();
            jobs[i].index = i;
            jobs[i].key = (a[i] < b[i]) ? a[i] : b[i];  // 根据Johnson法则分别取对应的a[i]或者b[i]作为排序的关键字
            jobs[i].job = (a[i] < b[i]);    // 根据Johnson法则将N1中的作业标记为true
        }

        mergeSort(jobs); // 按照关键字将作业进行升序排序

        int j = 0;
        int k = n - 1;
        for (i = 0; i < n; i++) {
            if (jobs[i].job)    // 对于N1中的作业，按照a[i]进行非递减排序
                c[j++] = jobs[i].index;
            else    // 对于N2中的作业，按照b[i]进行非递增排序
                c[k--] = jobs[i].index;
        }

        j = a[c[0]];
        k = j + b[c[0]];
        for (i = 1; i < n; i++) {   // M1在执行c[i]作业的同时，M2在执行c[i-1]作业，最短执行时间取决于M1和M2谁后执行
            j += a[c[i]];
            k = (j < k) ? (k + b[c[i]]) : (j + b[c[i]]);
        }
        return k;
    }

    private static void mergeSort(JobType[] arr) {
        JobType[] tmpArr = new JobType[arr.length];
        mergeSort(arr, tmpArr, 0, arr.length - 1);
    }

    private static void mergeSort(JobType[] arr, JobType[] tmpArr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, tmpArr, left, mid);
            mergeSort(arr, tmpArr, mid + 1, right);
            merge(arr, tmpArr, left, mid + 1, right);
        }
    }

    private static void merge(JobType[] arr, JobType[] tmpArr, int leftPos, int rightPos, int rightEnd) {
        int leftEnd = rightPos - 1;
        int numOfElements = rightEnd - leftPos + 1;
        int tmpPos = leftPos;

        while (leftPos <= leftEnd && rightPos <= rightEnd) {
            if (arr[leftPos].key < arr[rightPos].key)
                tmpArr[tmpPos++] = arr[leftPos++];
            else
                tmpArr[tmpPos++] = arr[rightPos++];
        }

        while (leftPos <= leftEnd)
            tmpArr[tmpPos++] = arr[leftPos++];
        while (rightPos <= rightEnd)
            tmpArr[tmpPos++] = arr[rightPos++];

        // copy back
        for (int i = 0; i < numOfElements; i++, rightEnd--)
            arr[rightEnd] = tmpArr[rightEnd];
    }

    private static class JobType {
        int index;      // 作业下标
        int key;        // 对作业进行排序的关键字
        boolean job;    // 该作业是否属于N1
    }
}
