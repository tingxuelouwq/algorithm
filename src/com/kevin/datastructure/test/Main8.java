package com.kevin.datastructure.test;

/**
 * @Author kevin
 * @Date 2016/10/22 19:07
 */
public class Main8 {
    public static void main(String[] args) {
        int[] a = {7, 8, 9, 1, 2, 3};
        int x = 9;
        System.out.println(indexOf(a, x));
    }

    public static int indexOf(int[] a, int x) {
        return indexOf(a, 0, a.length - 1, x);
    }

    private static int indexOf(int[] a, int left, int right, int x) {
        int mid = (left + right) / 2;
        while(left <= right) {
            if(a[mid] == x)
                return mid;

            if(a[mid] > a[left])
                if(a[left] <= x && a[mid] > x)
                    return indexOf(a, left, mid - 1, x);
                else
                    return indexOf(a, mid + 1, right, x);
            else
                if(a[right] >= x && a[mid] < x)
                    return indexOf(a, mid + 1, right, x);
                else
                    return indexOf(a, left, mid - 1, x);
        }
        return -1;
    }
}
