package com.kevin.algorithm.devideandconquer.closestpair;

/**
 * @Author kevin
 * @Date 2016/10/28 10:35
 */
public class Pair {
    PointX a, b;    // 最接近的点对
    double dis;     // 最接近的点对距离

    public Pair(PointX a, PointX b, double dis) {
        this.a = a;
        this.b = b;
        this.dis = dis;
    }

    public Pair() {}

    @Override
    public String toString() {
        return "Pair{" +
                "a=" + a +
                ", b=" + b +
                ", dis=" + dis +
                '}';
    }
}
