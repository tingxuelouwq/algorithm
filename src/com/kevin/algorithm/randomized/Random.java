package com.kevin.algorithm.randomized;

/**
 * @Author kevin
 * @Date 2017/1/14 12:04
 * 在现实计算机上无法产生真正的随机数，因此在概率算法中使用的随机数都是在一定程度上随即的，即伪随机数。
 * 线性同余法是产生伪随机数的最常用的方法，由线性同余法产生的随机序列a0,a1,...,an满足：
 * a0 = d
 * an = (b · an-1 + c) mod m, n = 1, 2, ...
 * 其中b>=0, c>=0, d<=m。d称为随机数的种子，从直观上看，m应取的充分大，因此可取m为机器大数，另外，应取gcd(b,m)=1，因此可取
 * b为一素数。
 */
public class Random {
    private long seed;  // 当前种子
    private static final long multiplier = 0x5DEECE66DL;
    private static final long addend = 0xBL;
    private static final long mask = (1L << 48) - 1;

    // 构造方法，自动产生种子
    public Random() {
        this.seed = System.currentTimeMillis();
    }

    // 构造方法，缺省值0表示由系统自动产生种子
    public Random(long seed) {
        if (seed == 0)
            this.seed = System.currentTimeMillis();
        else
            this.seed = seed;
    }

    // 产生[0, n-1)之间的随机整数
    public int nextInt(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be positive");
        seed = (seed * multiplier + addend) & mask;
        return (int) ((seed >>> 17) % n);
    }

    // 产生[0, 1)之间的随机实数
    public double next() {
        return nextInt(Integer.MAX_VALUE) / (double) (Integer.MAX_VALUE);
    }

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 1; i <= 10; i++) {
            System.out.println(random.nextInt(100));
            System.out.println(random.next());
        }
    }
}
