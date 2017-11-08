package com.kevin.algorithm.devideandconquer.closestpair;

/**
 * @Author kevin
 * @Date 2016/10/28 10:28
 * 按照x坐标排序的点集
 */
public class PointX implements Comparable<PointX> {
    int x, y;   // 点的坐标

    public PointX(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(PointX o) {
        return x < o.x ? -1 : x == o.x ? 0 : 1;
    }

    @Override
    public String toString() {
        return "PointX{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
