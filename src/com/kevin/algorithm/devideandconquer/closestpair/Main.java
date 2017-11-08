package com.kevin.algorithm.devideandconquer.closestpair;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/10/28 10:28
 * 问题描述：给定平面上的n个点，找其中的一对点，使得在n个点的所有点对中，该点对的距离最小。
 */
public class Main {
    public static void main(String[] args) {
        int n;
        PointX[] X;
        Scanner in = new Scanner(System.in);
        while(in.hasNextInt()) {
            n = in.nextInt();
            X = new PointX[n];
            for(int i = 0; i < n; i++)
                X[i] = new PointX(in.nextInt(), in.nextInt());
            System.out.println(closestPair(X));
        }
    }

    private static Pair closestPair(PointX[] X) {
        if(X.length < 2)
            return null;

        /**
         * 预处理：保留两个表，一个是按照x坐标排序的点的表，另一个是按照y排序的点的表
         */
        mergeSort(X);   // 按照x坐标将点集排序

        PointY[] Y = new PointY[X.length];
        for(int i = 0; i < X.length; i++)
            Y[i] = new PointY(i, X[i].x, X[i].y);
        mergeSort(Y);   // 按照y坐标将点集排序

        PointY[] Z = new PointY[X.length];
        return closestPair(X, Y, Z, 0, X.length - 1);

    }

    /**
     *
     * @param X 在[left,right]区间内按照x坐标排好序的点集
     * @param Y 在[left,right]区间内按照y坐标排好序的点集
     * @param Z tmp数组
     * @param left
     * @param right
     * @return
     */
    private static Pair closestPair(PointX[] X, PointY[] Y, PointY[] Z, int left, int right) {
        if(right - left == 1) { // 只有两个点的情形
            Pair result = new Pair();
            result.a = X[left];
            result.b = X[right];
            result.dis = distance(X[left], X[right]);
            return result;
        }

        if(right - left == 2) { // 只有三个点的情形
            double d1 = distance(X[left], X[left + 1]);
            double d2 = distance(X[left + 1], X[right]);
            double d3 = distance(X[left], X[right]);
            if(d1 <= d2 && d1 <= d3)
                return new Pair(X[left], X[left + 1], d1);
            else if(d2 <= d3)
                return new Pair(X[left + 1], X[right], d2);
            else
                return new Pair(X[left], X[right], d3);
        }

        int mid = (left + right) / 2;

        // 将分割线左边的点放到Z的左边，将分割线右边的点放到Z的右边，并且Z中两边的点均已经按照y坐标排好序
        int head = left, tail = mid + 1;
        for(int i = left; i <= right; i++) {
            if(Y[i].posInX <= mid)
                Z[head++] = Y[i];
            else
                Z[tail++] = Y[i];
        }

        Pair res1 = closestPair(X, Z, Y, left, mid);
        Pair res2 = closestPair(X, Z, Y, mid + 1, right);

        // 由于在递归过程中，将Y当做tmp数组，因此在递归调用返回时需要重构Y
        merge(Z, Y, left, mid + 1, right);

        // 计算分割线左右两边中最接近的点对
        Pair deta = res1.dis <= res2.dis ? res1 : res2;
        // 将带状区域中的点放到Z中
        int len = 0;
        for(int i = left; i <= right; i++) {
            if(Math.abs(X[mid].x - Y[i].x) < deta.dis)
                Z[len++] = Y[i];
        }
        // 搜索带状区域
        for(int i = 0; i < len; i++) {
            for(int j = i + 1; j < len &&
                    (Z[j].y - Z[i].y < deta.dis) ; j++) {
                double dis = distance(X[Z[j].posInX], X[Z[i].posInX]);
                if(dis < deta.dis) {
                    deta.dis = dis;
                    deta.a = X[Z[j].posInX];
                    deta.b = X[Z[j].posInX];
                }
            }
        }
        return deta;
    }

    private static double distance(PointX p1, PointX p2) {
        int dx = p1.x - p2.x;
        int dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * 归并排序
     * @param arr
     * @param <E>
     */
    private static <E extends Comparable<? super E>> void mergeSort(E[] arr) {
        E[] tmpArr = (E[]) (new Comparable[arr.length]);
        mergeSort(arr, tmpArr, 0, arr.length - 1);
    }

    private static <E extends Comparable<? super E>> void mergeSort(E[] arr, E[] tmpArr, int left, int right) {
        if(left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, tmpArr, left, mid);
            mergeSort(arr, tmpArr, mid + 1, right);
            merge(arr, tmpArr, left, mid + 1, right);
            copyBack(arr, tmpArr, left, right);
        }
    }

    private static <E extends Comparable<? super E>> void merge(E[] arr, E[] tmpArr,
                                                                int leftPos, int rightPos, int rightEnd) {
        int leftEnd = rightPos - 1;
        int tmpPos = leftPos;

        while(leftPos <= leftEnd && rightPos <= rightEnd) {
            if(arr[leftPos].compareTo(arr[rightPos]) <= 0)
                tmpArr[tmpPos++] = arr[leftPos++];
            else
                tmpArr[tmpPos++] = arr[rightPos++];
        }

        while(leftPos <= leftEnd)
            tmpArr[tmpPos++] = arr[leftPos++];
        while(rightPos <= rightEnd)
            tmpArr[tmpPos++] = arr[rightPos++];
    }

    private static <E extends Comparable<? super E>> void copyBack(E[] arr, E[] tmpArr, int left, int right) {
        for(int i = left; i <= right; i++)
            arr[i] = tmpArr[i];
    }
}
