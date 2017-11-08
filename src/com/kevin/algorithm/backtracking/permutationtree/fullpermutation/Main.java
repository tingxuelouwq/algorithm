package com.kevin.algorithm.backtracking.permutationtree.fullpermutation;

/**
 * @Author kevin
 * @Date 2016/12/2 17:12
 * 问题描述：打印一组数字的全排列
 * 算法思路：回溯法
 */
public class Main {
    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        permulate(a, 0, 2);
    }

    private static void permulate(int[] a, int left, int right) {
        if (left == right) {
            print(a);
        } else {
            for (int i = left; i <= right; i++) {
                swap(a, left, i);
                permulate(a, left + 1, right);
                swap(a, left, i);
            }
        }
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static void print(int[] a) {
        int i;
        for (i = 0; i < a.length - 1; i++)
            System.out.print(a[i] + " ");
        System.out.println(a[i]);
    }
}
