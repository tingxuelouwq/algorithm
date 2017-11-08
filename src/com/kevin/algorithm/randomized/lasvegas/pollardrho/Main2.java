package com.kevin.algorithm.randomized.lasvegas.pollardrho;

import com.kevin.algorithm.randomized.Random;

/**
 * @Author kevin
 * @Date 2017/1/20 20:09
 */
public class Main2 {
    public static void main(String[] args) {
        int[] a = {2, 3, 4, 5, 6, 10, 15, 20, 25, 30, 100};
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

        int nTrials = 100_000;  // 重复实验的次数
        int nSucc = 0;          // 实验成功的次数
        int[] p = new int[k];   // 存储随机产生的k个数
        boolean success;        // 判断一次实验是否成功
        Random rnd = new Random();  // 随机数发生器
        for (int j = 0; j < nTrials; j++) { // 重复nTrials实验
            success = false;
            for (int i = 0; i < k; i++) {
                p[i] = rnd.nextInt(1000) + 1;   // 随机产生[1,1000]的随机数
                for (int m = 0; m < i; m++) {
                    if (p[i] - p[m] == 42 || p[m] - p[i] == 42) {   // 如果两数相差42，则表示本次实验成功
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
