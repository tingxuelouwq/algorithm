package com.kevin.datastructure.test;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/10/14 20:51
 */
public class Main4 {
    public static void main(String[] args) {
        int n, r;
        int[] a;

        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            n = in.nextInt();
            r = in.nextInt();
            a = new int[n];

            for(int i = 0; i < n; i++)
                a[i] = in.nextInt();

            System.out.println(finalValue(a, r));
        }
    }

    /**
     * 获取收敛值
     * @param a 原数组
     * @param r 步长(1<=r<=n)
     * @return
     */
    public static int finalValue(int[] a, int r) {
        if(a == null || a.length == 0)
            throw new RuntimeException("invalid array!");
        if(r < 1 || r > a.length)
            throw new RuntimeException("invalid step!");

        int[] tmp = new int[a.length];  //tmp数组用于计算每一个b[i]
        int[] b = new int[a.length];    //b数组用于存放映射后的所有元素
        mapping(a, b, tmp, r);
        return a[0];
    }

    /**
     * 映射过程
     * @param a 原数组
     * @param b 映射后的数组
     * @param tmp 用于计算每一个映射值的数组
     * @param r 步长
     */
    private static void mapping(int[] a, int[] b, int[] tmp, int r) {
        int n = a.length;

        for(int step = r; step <= n; step++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < step; j++)  //填充tmp
                    tmp[j] = a[(i + j) % n];

                Arrays.sort(tmp, 0, step); //排序tmp

                b[i] = tmp[(int) Math.ceil((float) (step - 1) / 2)];
            }

            //copy back
            for(int i = 0; i < n; i++)
                a[i] = b[i];
        }
    }
}
