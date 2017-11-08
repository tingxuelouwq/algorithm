package com.kevin.algorithm.recursive;

/**
 * @Author kevin
 * @Date 2016/10/31 10:57
 *
 * 问题描述：求给定的n个元素的全排列
 *
 * 算法思路：设R={r1,r2,...,rn}是要进行全排列的n个元素，Ri=R-{ri}，全排列记为Perm(R)，则R的全排列可归纳如下：
 * 当n=1时，Perm(R)=(r)，其中r是集合R中的唯一元素；
 * 当n>1时，Perm(R)=(r1)Perm(R1)U(r2)Perm(R2)U(r3)Perm(R3)U...U(rn)Perm(Rn)，其中(ri)Perm(Ri)表示在全排列Ri中的每一个排列
 * 前都加上前缀ri得到的排列
 */
public class FullPermutation {
    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        permulate(a);
    }

    public static void permulate(int[] a) {
        permulate(a, 0, a.length - 1);
    }

    private static void permulate(int[] a, int left, int right) {
        if(left == right) {
            for(int i = 0; i < a.length; i++)
                System.out.print(a[i] + " ");
            System.out.println();
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
}
