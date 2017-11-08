package com.kevin.algorithm.randomized.numericalprobability.cointoss;

import com.kevin.algorithm.randomized.Random;

/**
 * @Author kevin
 * @Date 2017/1/14 21:27
 * 伪随机数模拟抛硬币
 * 问题描述：抛10次硬币构成一个事件，重复该事件50000次，用数组head记录这50000次事件得到的正面的次数，输出模拟抛硬币事件得到
 * 的正面次数的频率图。
 */
public class Main {
    public static void main(String[] args) {
        int numberOfCoins = 10;
        long numberOfTosses = 500_000_000L;
        long[] head = new long[numberOfCoins + 1];   // head[i]表示第i次事件得到的正面次数
        for (long i = 0; i < numberOfTosses; i++) {  // 重复事件50000次
            head[tossCoins(numberOfCoins)]++;
        }
        int position;
        for (int i = 0; i <= numberOfCoins; i++) {  // 输出频率图
            position = (int) ((float) head[i] / numberOfTosses * 50);
            System.out.printf("%2d ", i);
            for (int j = 0; j < position - 1; j++) {
                System.out.print(" ");
            }
            System.out.println("*");
        }
    }

    /**
     * 一次事件：抛numberOfCoins次硬币，记录正面的次数
     * @param numberOfCoins
     * @return
     */
    public static int tossCoins(int numberOfCoins) {
        int count = 0;
        Random random = new Random();
        for (int i = 0; i < numberOfCoins; i++) {
            count += random.nextInt(2);
        }
        return count;
    }
}
