package com.kevin.algorithm.randomized.sherwood.shuffle;

import com.kevin.algorithm.randomized.Random;

import java.util.Arrays;

/**
 * @Author kevin
 * @Date 2017/1/16 21:51
 * 舍伍德思想对输入洗牌
 */
public class Main {
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6};
        System.out.println(Arrays.toString(a));
        shuffle(a);
        System.out.println(Arrays.toString(a));

    }

    public static void shuffle(int[] a) {
        Random random = new Random();
        int i;
        int j;
        int n = a.length;
        for (i = 0; i < n; i++) {
            j = random.nextInt(n - i) + i;
            swap(a, i, j);
        }
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
