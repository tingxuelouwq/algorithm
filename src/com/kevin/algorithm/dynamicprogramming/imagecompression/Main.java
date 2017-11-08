package com.kevin.algorithm.dynamicprogramming.imagecompression;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/17 18:56
 * 图像压缩
 * 问题描述：在计算机中常用灰度值序列{p1,p2,...,pn}表示图像，其中pi表示像素点i的灰度值，范围通常是0-255。要求确定像素序列
 * {p1,p2,...,pn}的最佳分段，使得以此分段所需要的存储空间最小，其中每个分段中像素点的个数不超过256。
 * 算法思路：设最优分成m段，l[i]表示第i个像素段有l[i]个像素点，b[i]表示第i个像素段中的每个像素占b[i]位，则第i个像素段所需
 * 的存储空间为：l[i]*b[i]+11，其中11是因为l[i]和b[i]本身也需要记录，由于l[i]<=256，因此需要8位来存储；因为像素点的灰度值
 * 范围在[0,255]，因此需要8位来表示，需要3位来存储，因此需要加上11。
 * 设l[i],b[i],1<=i<=m是灰度值序列{p1,p2,...,pn}的一个最优分段，证明最优子结构就是证明l[i],b[i],2<=i<=m是
 * {p(l[1]+1),...,pn}的一个最优分段。如果l[i],b[i],2<=i<=m不是{p(l[1]+1),...,pn}的一个最优分段，假设l'[i],b'[i],
 * 2<=i<=m是{p(l[1]+1),...,pn}的最优分段，则l[1],l'[2],...,l'[n]；b[1],b'[2],...,b'[n]是灰度值序列{p1,...,pn}的最优分段，
 * 与前述矛盾，因此图像压缩问题存在最优子结构。
 * 设s[i],1<=i<=n是{p1,...,pi}的最优分段所需的存储空间，则
 * s[i]=s[i-1]+1*log(pi)+11
 * =s[i-2]+2*log(max{p(i-1),pi})+11
 * =s[i-3]+3*log(max{p(i-2),p(i-1),pi)})+11
 * ...
 * =s[0]+i*log(max{p1,...,pi})+11
 * 即s[i]=min{s[i-k]+k*bmax(i-k+1,i)}+11，其中1<=k<=min{i,256}
 * 其中，bmax(i,j)=┌log(max{pk}+1)┐，其中i<=k<=j
 * 解释一下上面的式子：
 * s[i]等于前i-k个存储的位数加上后k个存储的位数
 * k*bmax(i-k+1,i)+11表示l[i]*b[i]+11，即k表示l[i]--第i个像素段中像素点的个数，bmax(i-k+1,i)表示从第i-k+1到第i个灰度值中
 * 取最大值加1后取对数再向上取整简化处理。
 * 1<=k<=min{i,256}是因为问题规定每个分段中像素点的个数不超过256
 *
 * 输入：第一行为像素点个数，第二行为每个像素点的灰度值
 * 输出：第一行为最优分段所需的存储空间，第二行为最优划分为几段，第三行为最优分段的具体情况，格式为：(段长度, 所需存储位数)
 *
 * 输入示例：
    6
    10 12 15 255 1 2
 * 输出示例：
    57
    (3,4)
    (1,8)
    (2,2)
 */
public class Main {
    public static void main(String[] args) {
        int n;      // 像素点个数
        int[] p;    // p[i]表示像素点的灰度值
        int[] l;    // l[i]表示第i个分段中像素点的个数
        int[] b;    // b[i]表示第i个分段中每个像素占多少位
        int[] s;    // s[i]表示{p1,...,pi}的最优分段所需的存储空间
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            n = in.nextInt();
            p = new int[n + 1];
            for (int i = 1; i <= n; i++)
                p[i] = in.nextInt();
            l = new int[n + 1];
            b = new int[n + 1];
            s = new int[n + 1];

            compress(n, p, l, b, s);
        }
    }

    /**
     *
     * @param n 像素点个数
     * @param p p[i]表示像素点的灰度值
     * @param l l[i]表示第i个分段中像素点的个数
     * @param b b[i]表示第i个分段中每个像素占多少位
     * @param s s[i]表示{p1,...,pi}的最优分段所需的存储空间
     */
    private static void compress(int n, int[] p, int[] l, int[] b, int[] s) {
        int Lmax = 256;
        int header = 11;
        int bmax;
        int i;
        int j;
        for (i = 1; i <= n; i++) {
            l[i] = 1;
            b[i] = length(p[i]);
            bmax = b[i];
            s[i] = s[i - 1] + bmax;
            for (j = 2; j <= i && j <= Lmax; j++) {
                if (bmax < b[i - j + 1])
                    bmax = b[i - j + 1];
                if (s[i] > s[i - j] + j * bmax) {
                    s[i] = s[i - j] + j * bmax;
                    l[i] = j;
                }
            }
            s[i] += header;
        }

        System.out.println("图像压缩后的最小空间为: " + s[n]);

        // 重新为数组s赋值，用来存储分段位置，从右往左存储
        i = n;
        j = 1;
        s[1] = n;
        while (i > 0) {
            i -= l[i];
            s[++j] = i;
        }
        j -= 1;

        System.out.println("将原灰度值序列分成" + j + "段");
        int m = j;
        int k;
        bmax = 0;
        for (i = 1; i <= m; i++, j--) {
            l[i] = l[s[j]];
            for (k = s[j]; k > s[j] - l[i]; k--)
                if (bmax < b[k])
                    bmax = b[k];
            b[i] = bmax;
            bmax = 0;
            System.out.println("(" + l[i] + ", " + b[i] + ")");
        }
    }

    /**
     *
     * @param i 灰度值
     * @return 该灰度值所占位数
     */
    private static int length(int i) {
        int k = 1;
        i /= 2;
        while (i > 0) {
            k++;
            i /= 2;
        }
        return k;
    }
}
