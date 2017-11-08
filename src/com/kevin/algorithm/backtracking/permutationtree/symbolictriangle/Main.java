package com.kevin.algorithm.backtracking.permutationtree.symbolictriangle;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/21 19:53
 * 符号三角形问题
 * 问题描述：如下图是由14个“+”和14个“-”组成的符号三角形, 2个同号下面都是“+”，2个异号下面都是“-”。 
- + + - + + +  
 - + - - + +  
  - - + - +  
   + - - -  
    - + +  
     - +  
      - 
 * 在一般情况下，符号三角形的第一行有n个符号, 符号三角形问题要求对于给定的n， 计算有多少个不同的符号三角形，使其所含的“+”
 * 和“-”的个数相同。 
 * 算法思路：可以假设第一行的每个符号分别为+和-，从而推导出剩下的所有行。对于第一行，其解空间是排列树。
 * (1)可以使用x[1][i]表示第一行第i个符号。设+为0，-为1，这样可以使用异或运算符表示符号三角形的关系，++为+即0^0=0，--为+即
 * 1^1=0，+-为-即0^1为1，-+为-即1^0为1
 * (2)因为两种符号个数要求相同，因此当符号总数为奇数时无解；当某种符号超过总数的一半时无解。
 */
public class Main {
    public static void main(String[] args) {
        int n;
        int half;
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            n = in.nextInt();
            half = (1 + n) * n / 2;
            if (half % 2 == 0) {
                Triangle triangle = new Triangle(n, half);
                triangle.backtrack(1);
            }
        }
    }

    private static class Triangle {
        static final char[] ch = {'+', '-'};
        int n;          // n个符号
        int half;       // 符号三角形中符号中+和-的符号个数
        int[][] x;      // 符号三角形，x[1][i]表示第一行第i个符号，+为0，-为1
        int counter;    // 当前符号三角形中1的个数
        int sum;        // 满足条件的符号三角形的个数

        public Triangle(int n, int half) {
            this.n = n;
            this.half = half / 2;
            this.x = new int[n + 1][];
            for (int i = 1, j = n; i <= n; i++, j--) {
                x[i] = new int[j + 1];
            }
            this.counter = 0;
            this.sum = 0;
        }

        public void backtrack(int t) {
            if (t > n) {
                sum++;
                System.out.println("---------" + sum + "--------");
                for (int i = 1; i <= n; i++) {
                    for (int j = 1; j < i; j++) {   // 打印每一行前面的空格
                        System.out.print(" ");
                    }
                    for (int j = 1; j <= n - i + 1; j++) {  // 打印每一行的符号
                        System.out.print(ch[x[i][j]] + " ");
                    }
                    System.out.println();   // 一行打印完后，换行
                }
            } else {
                for (int i = 0; i <= 1; i++) {
                    x[1][t] = i;    // 第1行第i个符号
                    counter += i;   // 统计当前符号三角形中1的个数
                    for (int j = 2; j <= t; j++) {  // 当第1行符号个数>=2个时，可以计算出剩余行的符号
                        x[j][t - j + 1] = x[j - 1][t - j + 1] ^ x[j - 1][t - j + 2];
                        counter += x[j][t - j + 1]; // 更新当前符号三角形中1的个数
                    }
                    if (counter <= half &&
                            (1 + t) * t / 2 - counter <= half) {    // 如果当前符号三角形中1的个数未超过半数，并且
                                                                    // 为0的个数也未超过半数，则继续
                        backtrack(t + 1);
                    }
                    for (int j = 2; j <= t; j++) {  // 回溯
                        counter -= x[j][t - j + 1];
                    }
                    counter -= i;
                }
            }
        }
    }
}
