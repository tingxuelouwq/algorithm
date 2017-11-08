package com.kevin.algorithm.backtracking.permutationtree.turnpikereconstruction;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @Author kevin
 * @Date 2016/12/19 11:55
 * 收费公路重建
 * 问题描述：在x轴上给定n个点，那么n个点中每两点之间的距离对为n(n-1)/2个。现给出所有的距离对，求出各个点在x轴上的位置。
 */
public class Main {
    public static void main(String[] args) {
        int n = 6;
        LinkedList<Integer> d = new LinkedList<>();
        d.addAll(Arrays.asList(1, 2, 2, 2, 3, 3, 3, 4, 5, 5, 5, 6, 7, 8, 10));
        int[] x = new int[n + 1];
        turnpike(x, d, n);

        for (int i = 1; i <= n; i++)
            System.out.print(x[i] + " ");
    }

    /**
     *
     * @param x 各个点在x轴上的坐标(第一个点的坐标为0)
     * @param d 所有点对的距离对，从小到大排序
     * @param n x轴上的n个点
     */
    public static boolean turnpike(int[] x, LinkedList<Integer> d, int n) {
        x[1] = 0;
        x[n] = d.removeLast();
        x[n - 1] = d.removeLast();
        if (d.contains(x[n] - x[n - 1])) {
            d.remove(x[n] - x[n - 1]);
            return place(x, d, n, 2, n - 2);
        } else
            return false;
    }

    /**
     *
     * @param x 各个点在x轴上的坐标(第一个点的坐标为0)
     * @param d 所有点对的距离对，从小到大排序
     * @param n x轴上的n个点
     * @param left 尝试放置的点的左边界
     * @param right 尝试放置的点的右边界
     * @return
     */
    private static boolean place(int[] x, LinkedList<Integer> d, int n, int left, int right) {
        if(d.isEmpty())
            return true;
        boolean found = false;
        int dmax = d.getLast();
        int i, j;

        x[right] = dmax;     // 假设点放在x[right]处
        for (i = 1; i < left; i++) {    // 判断x[right]左侧点到x[right]的距离是否都存在
            if (!(d.remove(Integer.valueOf(x[right] - x[i])))) {    // 如果不在，则撤销已进行的操作
                for (j = i - 1; j > 0; j--) {
                    d.add(x[right] - x[j]);
                }
                Collections.sort(d);
                break;
            }
        }

        if (i == left) {    // 如果x[right]左侧点到x[right]的距离都存在
            for (i = right + 1; i <= n; i++) {  // 判断x[right]右侧点到x[right]的距离是否都存在
                if (!(d.remove(Integer.valueOf(x[i] - x[right])))) {    // 如果不在，则撤销已进行的操作
                    for (j = 1; j < left; j++) {
                        d.add(x[right] - x[j]);
                    }
                    for (j = i - 1; j > right; j--) {
                        d.add(x[j] - x[right]);
                    }
                    Collections.sort(d);
                    break;
                }
            }

            if (i == n + 1) {   // 如果两侧都符合，则进行下一轮判断
                found = place(x, d, n, left, right - 1);
                if (!found) {
                    for (j = 1; j < left; j++) {
                        d.add(dmax - x[j]);
                    }
                    for (j = right + 1; j <= n; j++) {
                        d.add(x[j] - dmax);
                    }
                    Collections.sort(d);
                } else {
                    return true;
                }
            }
        }

        x[left] = x[n] - dmax;  // 假设点放在x[left]处
        for (i = 1; i < left; i++) {    // 判断x[left]左侧点到x[left]的距离是否都存在
            if (!(d.remove(Integer.valueOf(x[left] - x[i])))) { // 如果不在，则撤销已进行的操作
                for (j = i - 1; j > 0; j--) {
                    d.add(x[left] - x[j]);
                }
                Collections.sort(d);
                break;
            }
        }

        if (i == left) {    // 如果x[left]左侧点到x[left]的距离都存在
            for (i = right + 1; i <= n; i++) {  // 判断x[left]右侧点到x[left]的距离是否都存在
                if (!(d.remove(Integer.valueOf(x[i] - x[left])))) {    // 如果不在，则撤销已进行的操作
                    for (j = 1; j < left; j++) {
                        d.add(x[left] - x[j]);
                    }
                    for (j = i - 1; j > right; j--) {
                        d.add(x[j] - x[left]);
                    }
                    Collections.sort(d);
                    break;
                }
            }

            if (i == n + 1) {   // 如果两侧都符合，则进行下一轮判断
                found = place(x, d, n, left + 1, right);
                if (!found) {
                    for (j = 1; j < left; j++) {
                        d.add(dmax - x[j]);
                    }
                    for (j = right + 1; j <= n; j++) {
                        d.add(x[j] - dmax);
                    }
                    Collections.sort(d);
                } else {
                    return true;
                }
            }
        }

        return found;
    }
}
