package com.kevin.algorithm.randomized.montecarlo.introduce2montecarlo;

import com.kevin.algorithm.randomized.Random;

/**
 * @Author kevin
 * @Date 2017/1/31 21:39
 * 主元素判定问题：在一个长度为n的数组中，如果有超过n/2个元素具有相同的值，则具有该值的元素就叫做该数组的主元素。现在要求给出一种算法，
 * 在O(n)时间内判定给定数组是否存在主元素。
 */
public class Main2 {
    public static void main(String[] args) {
        int[] a = {4, 5, 8, 1, 8, 4, 9, 2, 2, 2, 2, 2, 5, 7, 8, 2, 2, 2, 2, 2, 1, 0, 9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 4, 7, 8, 2, 2, 2, 2, 2, 0, 1, 2, 2, 2, 2, 2};
        int count;
        System.out.println("************主元素探测测试*************");
        System.out.println("阈值2，测试100次");
        count = 0;
        for (int i = 1; i <= 100; i++) {
            if (hasPrincipalElement(a, 2)) {
                count++;
                System.out.print("true" + " ");
            } else {
                System.out.print("false" + " ");
            }
            if (i % 20 == 0) {
                System.out.println();
            }
        }
        System.out.println("正确率: " + count + "%");

        System.out.println("阈值3，测试100次");
        count = 0;
        for (int i = 1; i <= 100; i++) {
            if (hasPrincipalElement(a, 3)) {
                count++;
                System.out.print("true" + " ");
            } else {
                System.out.print("false" + " ");
            }
            if (i % 20 == 0) {
                System.out.println();
            }
        }
        System.out.println("正确率: " + count + "%");

        System.out.println("阈值10，测试100次");
        count = 0;
        for (int i = 1; i <= 100; i++) {
            if (hasPrincipalElement(a, 10)) {
                count++;
                System.out.print("true" + " ");
            } else {
                System.out.print("false" + " ");
            }
            if (i % 20 == 0) {
                System.out.println();
            }
        }
        System.out.println("正确率: " + count + "%");
        System.out.println("*************************************");
    }

    /**
     * 使用Monte-Carlo思想求解主元素判定问题，主元素判定问题是一个一致偏真问题
     * @param a 元素数组
     * @param n 阈值
     * @return 是否存在主元素
     */
    public static boolean hasPrincipalElement(int[] a, int n) {
        Random rnd = new Random();
        boolean result = false;
        int index;
        int count;
        for (int i = 0; i < n; i++) {
            index = rnd.nextInt(a.length);
            count = 0;
            for (int j = 0; j < a.length; j++) {
                if (a[j] == a[index]) {
                    count++;
                }
            }
            if (count > a.length / 2) {
                result = true;
                break;
            }
        }
        return result;
    }
}
