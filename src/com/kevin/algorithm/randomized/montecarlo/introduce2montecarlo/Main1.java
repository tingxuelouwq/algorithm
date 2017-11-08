package com.kevin.algorithm.randomized.montecarlo.introduce2montecarlo;

import com.kevin.algorithm.randomized.Random;

/**
 * @Author kevin
 * @Date 2017/1/31 16:17
 * 四种常用的插值积分方式见1.jpg
 *
 */
public class Main1 {
    /**
     * 被积函数为：f(x)=sin(x)/x
     * @param x
     * @return
     */
    public static double f(double x) {
        return Math.sin(x) / x;
    }

    /**
     * 梯形法则：最简单的差值积分法
     * @param x1 积分下限
     * @param x2 积分上限
     * @return 积分值
     */
    public static double trapezoidalIntegrate(double x1, double x2) {
        return (f(x1) + f(x2)) / 2 * (x2 - x1);
    }

    /**
     * 复杂梯形法则：首先将积分区间分段，然后对每段计算梯形插值再加起来，从而提高精度
     * @param x1 积分上限
     * @param x2 积分下限
     * @param n 分段数量
     * @return 积分值
     */
    public static double complexTrapezoidalIntegrate(double x1, double x2, int n) {
        double result = 0.0;    // 积分值
        double gap = (x2  - x1) / n;    // 每段的区间长度
        double xa;  // 每段的积分下限
        double xb;  // 每段的积分上限
        for (int i = 0; i < n; i++) {
            xa = x1 + i * gap;
            xb = xa + gap;
            result += (xb - xa) / 2 * (f(xa) + f(xb));
        }
        return result;
    }

    /**
     * Sinpson法则
     * @param x1
     * @param x2
     * @return
     */
    public static double sinpsonIntegrate(double x1, double x2) {
        double x = (x1 + x2) / 2;
        return (x2 - x1) / 6 * (f(x1) + 4 * f(x) + f(x2));
    }

    /**
     * 复杂Sinpson法则
     * @param x1
     * @param x2
     * @param n
     * @return
     */
    public static double compleSinpsonIntegrate(double x1, double x2, int n) {
        double result = 0.0;
        double gap = (x2 - x1) / n; // 每段的区间长度
        double xa;
        double xb;
        double xc;
        for (int i = 0; i < n / 2; i++) {
            xa = x1 + 2 * i * gap;  // 每段的积分下限
            xb = xa + gap;  // 每段的积分中点
            xc = xb + gap;  // 每段的积分上限
            result += (x2 - x1) / (3 * n) * (f(xa) + 4 * f(xb) + f(xc));
        }
        return result;
    }

    /**
     * Monte-Carlo积分法：设f:[a,b]->[c,d]是连续函数，则由曲线y=f(x)以及x轴和直线x=a,x=b围成的面积由定积分s=∫f(x)dx给出。
     * 根据几何概型可知，P(A)=事件区域A的面积/区域Ω的面积。假设向矩形区域随即均匀的投镖n次，落入阴影为k次，又设M为x=a,x=b,y=c,y=d
     * 所围成的矩形面积，s为定积分面积，则s/M=k/n，得到s=k*M/n
     * @param x1 积分下限
     * @param x2 积分上限
     * @param d1 比f(x)的最大值大的一个数
     * @param d2 比f(x)的最小值小的一个数
     * @param n 实验重复次数
     * @return
     */
    public static double monteCarloIntegrate(double x1, double x2, double d1, double d2, int n) {
        Random rnd = new Random();
        int positivePointCount = 0; // y>=0区间内落入函数线内的点的数目
        int negativePointCount = 0; // y<0区间内落入函数县内的点的数目
        double xCoordinate;   // 随机产生的x坐标
        double yCoordinate;   // 随机产生的y坐标
        for (int i = 0; i < n; i++) {   // 统计y>=0区间点分布
            xCoordinate = rnd.next() * (x2 - x1) + x1;
            yCoordinate = d1 * rnd.next();
            if (f(xCoordinate) >= yCoordinate) {
                positivePointCount++;
            }
        }
        for (int i = 0; i < n; i++) {   // 统计y<0区间点分布
            xCoordinate = rnd.next() * (x2 - x1) + x1;
            yCoordinate = d2 * rnd.next();
            if (f(xCoordinate) <= yCoordinate) {
                negativePointCount++;
            }
        }

        // 由s=k*M/n得到y>=0区间的面积为positiveCount*d1*(x2-x1)/n，y<0区间的面积为negativeCount*d2*(x2-x1)/n，
        // 因此积分值为：positiveCount*d1*(x2-x1)/n - negativeCount*d2*(x2-x1)/n
        return (positivePointCount * d1 + negativePointCount * d2) * (x2 - x1) / n;
    }

    /**
     * 对sin(x)/x在[1,2]区间上进行定积分。其中，我们分别对复化梯形和复化Sinpson法则做分段为10，10000和1000000的积分测试，
     * 另外，对Monte-Carlo法的投点数也分为10，10000和10000000
     * @param args
     */
    public static void main(String[] args) {
        long start;
        long end;
        double x1 = 1;
        double x2 = 2;
        double d1 = 1;
        double d2 = -1;
        double result;

        System.out.println("*****************积分测试****************");
        start = System.currentTimeMillis();
        result = trapezoidalIntegrate(x1, x2);
        end = System.currentTimeMillis();
        System.out.println("基本梯形法则: " + result);
        System.out.println("用时: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        result = complexTrapezoidalIntegrate(x1, x2, 10);
        end = System.currentTimeMillis();
        System.out.println("复化梯形法则(10次): " + result);
        System.out.println("用时: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        result = complexTrapezoidalIntegrate(x1, x2, 10000);
        end = System.currentTimeMillis();
        System.out.println("复化梯形法则(1万次): " + result);
        System.out.println("用时: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        result = complexTrapezoidalIntegrate(x1, x2, 10000000);
        end = System.currentTimeMillis();
        System.out.println("复化梯形法则(1000万次): " + result);
        System.out.println("用时: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        result = sinpsonIntegrate(x1, x2);
        end = System.currentTimeMillis();
        System.out.println("基本Sinpson法则: " + result);
        System.out.println("用时: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        result = compleSinpsonIntegrate(x1, x2, 10);
        end = System.currentTimeMillis();
        System.out.println("复化Sinpson法则(10次): " + result);
        System.out.println("用时: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        result = compleSinpsonIntegrate(x1, x2, 10000);
        end = System.currentTimeMillis();
        System.out.println("复化Sinpson法则(1万次): " + result);
        System.out.println("用时: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        result = compleSinpsonIntegrate(x1, x2, 10000000);
        end = System.currentTimeMillis();
        System.out.println("复化Sinpson法则(1000万次): " + result);
        System.out.println("用时: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        result = monteCarloIntegrate(x1, x2, d1, d2, 10);
        end = System.currentTimeMillis();
        System.out.println("Monte-Carlo法(10次): " + result);
        System.out.println("用时: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        result = monteCarloIntegrate(x1, x2, d1, d2, 10000);
        end = System.currentTimeMillis();
        System.out.println("Monte-Carlo法(1万次): " + result);
        System.out.println("用时: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        result = monteCarloIntegrate(x1, x2, d1, d2, 10000000);
        end = System.currentTimeMillis();
        System.out.println("Monte-Carlo法(1000万次): " + result);
        System.out.println("用时: " + (end - start) + "ms");
        System.out.println("****************************************");
    }
}
