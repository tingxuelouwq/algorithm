package com.kevin.algorithm.randomized.lasvegas.pollardrho;

import com.kevin.algorithm.randomized.Random;

/**
 * @Author kevin
 * @Date 2017/1/22 11:25
 */
public class Main4 {
    public static void main(String[] args) {
        int[] a = {2, 3, 4, 5, 6, 10, 15, 20, 25, 30, 60, 100};
        int k;
        for (int i = 0; i < a.length; i++) {
            k = a[i];
            factorization(k);
        }
    }

    public static void factorization(int k) {
        int nTrials = 100_000;  // 实验重复的次数
        int nSuccs = 0;         // 实验成功的次数
        boolean success;        // 本次实验是否成功
        int[] p = new int[k];   // 存储k个随机数
        Random rnd = new Random();  // 随机数产生器
        for (int j = 0; j < nTrials; j++) { // 重复nTrials次实验
            success = false;
            for (int i = 0; i < k; i++) {
                p[i] = rnd.nextInt(1000) + 1;   // 生成范围在[1,1000]的随机数
                for (int m = 0; m < i; m++) {
                    if ((p[i] != p[m]) &&
                            (p[i] - p[m] != 1) &&
                            (p[m] - p[i] != 1) &&
                            (1000 % (p[i] - p[m]) == 0)) {
                        success = true;
                        break;
                    }
                }
                if (success) {
                    break;
                }
            }
            if (success) {
                nSuccs++;
            }
        }

        System.out.println("k=" + k + ", prob=" + (double) nSuccs / (double) nTrials);
    }
}
