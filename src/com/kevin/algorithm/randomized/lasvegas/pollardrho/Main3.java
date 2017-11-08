package com.kevin.algorithm.randomized.lasvegas.pollardrho;

import com.kevin.algorithm.randomized.Random;

/**
 * @Author kevin
 * @Date 2017/1/20 20:25
 */
public class Main3 {
    public static void main(String[] args) {
        int[] a = {2, 3, 4, 5, 6, 10, 15, 20, 25, 30, 60, 100};
        int k;
        for (int i = 0; i < a.length; i++) {
            k = a[i];
            birthdayTrick(k);
        }
    }

    public static void birthdayTrick(int k) {
        if (k < 2) {
            System.out.println("Please select a k >= 2.");
            return;
        }
        int nTrials = 100_100;  // 实验次数
        int nSucc = 0;          // 实验成功次数
        boolean success;        // 判断本次实验是否成功
        int[] p = new int[k];   // 存储k个随机数
        Random rnd = new Random();  // 随机数生成器
        for (int j = 0; j < nTrials; j++) { // 重复nTrials次实验
            success = false;
            for (int i = 0; i < k; i++) {
                p[i] = rnd.nextInt(365) + 1;
                for (int m = 0; m < i; m++) {
                    if (p[i] == p[m]) {
                        success = true;
                        break;
                    }
                }
                if (success) {
                    break;
                }
            }
            if (success) {
                nSucc++;
            }
        }

        System.out.println("k=" + k + ", prob=" + (double) nSucc / (double) nTrials);
    }
}
