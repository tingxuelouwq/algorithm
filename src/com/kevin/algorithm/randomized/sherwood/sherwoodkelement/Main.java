package com.kevin.algorithm.randomized.sherwood.sherwoodkelement;

import com.kevin.algorithm.randomized.Random;

/**
 * @Author kevin
 * @Date 2017/1/16 20:16
 * 舍伍德思想解决第k小问题
 */
public class Main {
    public static void main(String[] args) {
        int[] a = {2, 5, 6, 7, 0, 9};
        int k = 1;
//        selectK(a, k);
        sherwoodSelectK(a, k);
        System.out.println(a[k - 1]);
    }

    /**
     * 舍伍德思想解决第k小问题，非递归实现
     * @param a
     * @param k
     */
    public static void sherwoodSelectK(int[] a, int k) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("a can not be null, and a.length must be greater than 0");
        }
        if (k < 1 || k > a.length) {
            throw new IllegalArgumentException("k must be between 1 and a.length");
        }

        int left = 0;
        int right = a.length - 1;
        Random random = new Random();
        while (true) {
            if (left >= right) {
                return;
            }
            int i = left;
            int j = right + 1;
            int pivot = random.nextInt(right - left) + left;    // 随机选择枢纽元素
            swap(a, left, pivot);
            pivot = a[left];
            while (true) {
                while (a[++i] < pivot);
                while (a[--j] > pivot);
                if (i >= j) {
                    break;
                }
                swap(a, i, j);
            }
            // 最后一次交换
            a[left] = a[j];
            a[j] = pivot;
            if (j - left + 1 == k) {    // 找到目标元素
                return;
            }
            // 对子数组重复划分过程
            if (j - left + 1 < k) { // 目标元素在右侧
                left = j + 1;
                k = k - (j - left + 1);
            } else {
                right = j - 1;
            }
        }
    }

    public static void selectK(int[] a, int k) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("a can not be null, and a.length must be greater than 0");
        }
        if (k < 1 || k > a.length) {
            throw new IllegalArgumentException("k must be between 1 and a.length");
        }
        selectK(a, 0, a.length - 1, k);
    }

    /**
     * 舍伍德思想解决第k小问题，递归实现
     * @param a
     * @param left
     * @param right
     * @param k
     */
    private static void selectK(int[] a, int left, int right, int k) {
        if (left < right) {
            int i = left;
            int j = right;
            Random random = new Random();
            int pivot = random.nextInt(right - left) + left;  // 随机选择枢纽元素
            swap(a, left, pivot);
            pivot = a[left];
            while (i < j) {
                while (i < j && a[j] > pivot) {
                    j--;
                }
                if (i < j) {
                    a[i++] = a[j];
                }
                while (i < j && a[i] < pivot) {
                    i++;
                }
                if (i < j) {
                    a[j--] = a[i];
                }
            }
            a[i] = pivot;
            if (k < i + 1) {
                selectK(a, left, i - 1, k);
            } else if (k > i + 1) {
                selectK(a, i + 1, right, k);
            }
        }
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
