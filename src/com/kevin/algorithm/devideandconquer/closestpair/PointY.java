package com.kevin.algorithm.devideandconquer.closestpair;

/**
 * @Author kevin
 * @Date 2016/10/28 10:28
 * 按照y坐标排序的点集
 */
public class PointY implements Comparable<PointY> {
    int posInX; // 该点在PointX中对应的下标
    int x, y;   // 点的坐标

    public PointY(int posInX, int x, int y) {
        this.posInX = posInX;
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(PointY o) {
        return y < o.y ? -1 : y == o.y ? 0 : 1;
    }
}
