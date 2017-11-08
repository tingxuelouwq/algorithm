package com.kevin.algorithm.dynamicprogramming.polygongame;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/10 16:06
 * 多边形游戏
 * 问题描述：给定一个有n个顶点的多边形，每个顶点是一个整数值，每条边是运算符'+'或'*'，游戏步骤如下：
 * (1)将某条边断开，形成一条数值和符号组成的链
 * (2)接下来的n-1步按以下方式操作：
 * I.选择一条边及边连接的两个顶点v1, v2
 * II.用一个新的顶点代替上述选定的边和顶点，其值为v1和v2经中间的运算符运算后得到的数
 * 输入：第一行是一个整数n，表示顶点个数；第二行是n个整数，表示每个顶点的整数值；第三行是n个字符，表示每条边的运算符
 * 输出：最高得分
 * 输入示例：
    4
    5 -7 4 2
    * + + *
 * 输出示例：
 *
 * 算法描述：用数组v[i]存储操作数，用数组op[i]存储操作符，用数组p[i][j]表示从顶点i开始，链长为j(链的顶点个数)的顺时针链，即
 * p[i][j]是由v[i],op[i+1],v[i+1],op[i+2],...,v[i+j-1],op[i+j-1]组成的链。如果链p[i][j]的最后一次合并运算在op[i+s]处发生
 * ，1<=s<=j-1，则可以在op[i+s]处将该链分割成两个子链p[i][s]，p[i+s][j-s]。因为多边形是封闭的，因此当i+s>n时，顶点i+s实际
 * 编号应为(i+s)mod n。
 * 例如：n=7，选定i=4，得到p[i][j]，即p[4][7]为：v[4],op[5],v[5],op[6],v6,op[7],v[7],op[8%7],v[8%7],op[9%7],v[9%7],
 * op[10%7],v[10%7]。若链p[4][7]最后一次运算在op[4+s],s=2处进行，则可以得到两个子链v[4],op[5],v[5]，即链p[i][s]=p[4][2]，
 * 以及v6,op[7],v[7],op[8%7],v[8%7],op[9%7],v[9%7],op[10%7],v[10%7]，即链p[i+s][j-s]=p[6][5]。
 * 设m[i,j,0]是链p[i][j]合并的最小值，m[i,j,1]时链p[i][j]合并的最大值。若最优合并在op[i+s]处分为两个子链p[i][s]，p[i+s][j-s]
 * 的最大值和最小值均已计算出，即：
 * a=m[i,s,0], b=m[i,s,1], c=m[i+s,j-s,0], d=m[i+s,j-s,1]
 * 则当op[i+s]='+'时，m[i,j,0]=a+c, m[i,j,1]=b+d
 * 当op[i+s]='*'时，m[i,j,0]=max{ac,ad,bc,bd}, m[i,j,1]=max{ac,ad,bc,bd}
 * 初始情况为：m[i,1,0]=v[i], m[i,1,1]=v[i], 1<=i<=n
 */
public class Main {
    public static void main(String[] args) {
        int n;          // 顶点数
        int[] v;        // 顶点数组
        char[] op;      // 运算符数组
        int[][][] m;    // m[i][j][0]是链p[i][j]合并的最小值，m[i][j][1]是链p[i][j]合并的最大值
        int i;
        String[] line;
        Scanner sin = new Scanner(System.in);
        while (sin.hasNext()) {
            n = sin.nextInt();
            v = new int[n + 1];
            m = new int[n + 1][n + 1][2];
            for (i = 1; i <= n; i++) {
                v[i] = sin.nextInt();
                m[i][1][0] = v[i];
                m[i][1][1] = v[i];
            }
            op = new char[n + 1];
            sin.nextLine();
            line = sin.nextLine().trim().split(" ");
            for (i = 1; i <= n; i++)
                op[i] = line[i - 1].charAt(0);
            polygon(n, v, op, m);
        }
    }

    /**
     *
     * @param n 顶点个数
     * @param v 顶点数组
     * @param op 运算符数组
     * @param m m[i][j][0]是链p[i][j]合并的最小值，m[i][j][1]是链p[i][j]合并的最大值
     */
    private static void polygon(int n, int[] v, char[] op, int[][][] m) {
        MinMax result = new MinMax();

        for (int i = 1; i <= n; i++) {  // 起始顶点，即p[i][j]中的i，另外，还表示首次迭代会删除第i条边
            for (int j = 2; j <= n; j++) {  // 链长，即p[i][j]中的j
                m[i][j][0] = Integer.MAX_VALUE;
                m[i][j][1] = Integer.MIN_VALUE;
                for (int s = 1; s < j; s++) {   // 断开位置，即op[i+s]中的s，其中1<=s<=j-1
                    minMax(n, i, s, j, op, m, result);
                    if (m[i][j][0] > result.min)
                        m[i][j][0]  = result.min;
                    if (m[i][j][1] < result.max)
                        m[i][j][1] = result.max;
                }
            }
        }

        // 寻找从哪个位置断开能够得到最优解
        int tmp = m[1][n][1];
        int p = 1;
        for (int i = 1; i <= n; i++) {
            System.out.println("删除第" + i + "条边时最大值为: " + m[i][n][1]);
            if (tmp < m[i][n][1]) {
                tmp = m[i][n][1];
                p = i;
            }
        }
        System.out.println("最优解为[删除第" + p + "条边时最大值为: " + tmp + "]");
    }

    private static void minMax(int n, int i, int s, int j, char[] op, int[][][] m, MinMax result) {
        int a = m[i][s][0];
        int b = m[i][s][1];
        int r = (i + s - 1) % n + 1;    // (i+s) mod n
        int c = m[r][j - s][0];
        int d = m[r][j - s][1];

        if (op[r] == '+') {
            result.min = a + c;
            result.max = b + d;
        } else {
            int[] options = new int[4];
            options[0] = a * c;
            options[1] = a * d;
            options[2] = b * c;
            options[3] = b * d;
            result.min = options[0];
            result.max = options[0];
            for (int k = 1; k < 4; k++) {
                if (result.min > options[k])
                    result.min = options[k];
                if (result.max < options[k])
                    result.max = options[k];
            }
        }
    }

    private static class MinMax {
        int min;
        int max;
    }
}
