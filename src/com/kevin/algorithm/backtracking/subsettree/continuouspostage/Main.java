package com.kevin.algorithm.backtracking.subsettree.continuouspostage;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/12/29 20:19
 * 连续邮资问题
 * 问题描述：假设国家发行了n种不同面值的邮票，并且规定每张信封上只允许贴m张邮票。连续邮资问题要求对于给定的n和m，给出邮票面值
 * 的最佳设计，使得在1张信封上可以贴出从邮资1开始，增量为1的最大连续邮资区间。例如，当n=2,m=3时，如果面值分别为{1,4}，则在1
 * 到6之间的每一个邮资值都可以得到，当然，还可以得到8,9,12，但由于邮资7得不到，因此此时的最大连续邮资区间为1到6
 * 输入：第一行是n和m
 * 输出：第一行是最大连续邮资区间r，接下来的一行是x[1:n]
 * 输入示例：
5 4
 * 输出示例：
70
1 3 11 15 32
 * 算法分析：
 * 用n元组x[1:n]表示n种不同的邮票面值，并约定它们从小到大排列。因为邮资要从1开始，因此x[1]=1。
 * 设x[1:i]能贴出的最大连续邮资区间为[1:r]，现在要想把第i层的节点往下扩展，有两个问题要解决：一是x[i+1]可以取值的范围是多少；
 * 二是对于一个确定的x[i+1]，如何更新r，从而让它表示x[1:i+1]的最大连续邮资区间。
 * 第一个问题很简单，x[i+1]的取值要和前面i个数各不相同，因此最小应该是x[i]+1，最大应该是r+1，否则r+1没法表示。
 * 对于第二个问题，可以有两种思路：一是计算出所有使用不超过m张x[1:i+1]中的面值能够贴出的邮资，然后从r+1开始逐个检查是否能够被
 * 计算出来；二是从r+1开始，逐个询问它是不是能够使用不超过m张x[1:i+1]中的面值贴出来。这两种思路直接计算的计算量都是巨大的，因
 * 此需要借助动态规划的思想，假设S(i)表示x[1:i]中使用不超过m张邮票的贴法的集合，则这个集合中的元素个数为C(i+1-1,1)+C(i+2-1,2)
 * +...+C(i+m-1,m)个。S(i)中的每个元素都是一种合法的贴法，对应一个邮资。我们可以把S(i)中的元素按照它们的值的相等关系划分为k类
 * ，用T来表示，T有可能为空集。举个例子，假设x[1]=1，m=4，则：
 * T(i)     对应的元素      邮资
 * T(1)     1               1
 * T(2)     1,1             2
 * T(3)     1,1,1           3
 * T(4)     1,1,1,1         4
 * 此时有S(i)=T(1)UT(2)U...UT(k)。
 * 需要注意的是，S(i)中的每个元素的邮资不一定都在连续邮资区间1到r之间，比如继续上面的例子，假设x[1]=1, x[2]=3, m=4，则：
 * T(i)     对应的元素      邮资
 * T(1)     1               1
 * T(2)     1,1             2
 * T(3)     3; 1,1,1        3
 * T(4)     3,1; 1,1,1,1    4
 * T(5)     3,1,1           5
 * T(6)     3,3; 3,1,1,1    6
 * T(7)     3,3,1           7
 * T(8)     3,3,1,1         8
 * T(9)     3,3,3           9
 * T(10)    3,3,3,1         10
 * T(11)    空集            11(不存在)
 * T(12)    3,3,3,3         12
 * 可以看到，此时的连续邮资区间是1到10之间，S(i)中的元素T(12)并不在连续邮资区间之间。
 * 现在考虑x[i+1]加入后，对当前状态S(i)的影响。此时会导致数组x能贴出的邮资范围变大，对S的影响是如果S中的一个元素的邮票不满m张，
 * 那就一直贴x[i+1]，直到该元素中有m张邮票。这个过程中，对于同一邮资可能有多种贴邮票的方式，取使用邮票最小的作为最终元素，比如
 * 上例中，T(4)={{3,1},{1,1,1,1}}，则取{3,1}作为最终元素。
 * 更进一步，对于更新r的过程，我们可以使用y[k]来表示使用不超过m张面值为x[1:i]的邮票贴出邮资k所需的最小邮票数，这样就可以通过
 * y[k]快速推出r的值，比如上述例子中：
 * T(i)     对应的元素      最终的元素   k(邮资)     y[k](贴出邮资k所使用的最小邮票数)
 * T(1)     1               1           1           1
 * T(2)     1,1             1,1         2           2
 * T(3)     3; 1,1,1        3           3           1
 * T(4)     3,1; 1,1,1,1    3,1         4           2
 * T(5)     3,1,1           3,1,1       5           3
 * T(6)     3,3; 3,1,1,1    3,3         6           2
 * T(7)     3,3,1           3,3,1       7           3
 * T(8)     3,3,1,1         3,3,1,1     8           4
 * T(9)     3,3,3           3,3,3       9           3
 * T(10)    3,3,3,1         3,3,3,1     10          4
 * T(11)    空集            空集        11(不存在)   INF
 * T(12)    3,3,3,3         3,3,3,3     12          12
 * 通过y[k]<INF可知，此时的连续邮资区间为1到10
 *
 * 证明可重组合：n个不同元素，可重复的取r个元素，不同的取法有C(n+r-1,r)个。
 * 证明：设a1a2...ar属于C'(n,r)，则1<=a1<=a2<=...<=ai<=...<=ar<=n，与下面的数列对应相加：
 * 0<1<2<...<i-1<...<r-1
 * 即bi=ai+i-1，i=1,2,...,r
 * 则b1b2...br满足1<=b1<b2<...<bi<...<br<=n+r-1，即b1b2...br属于C(n+r-1,r)
 * f:a1a2...ar->b1b2...br
 * 显然，f是双射，因此C'(n,r)=C(n+r-1,r)
 */
public class Main {
    private static final int INF = Integer.MAX_VALUE;
    private static final int MAX_POSTAGE = 1024;    // 最大邮资

    public static void main(String[] args) {
        int n;
        int m;
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            n = in.nextInt();
            m = in.nextInt();
            Stamp stamp = new Stamp(n, m);
            stamp.backtrack(2);
            stamp.output();
        }
    }

    private static class Stamp {
        int n;          // n中不同面值的邮票
        int m;          // 每张信封上最多可贴m张邮票
        int[] x;        // 当前解
        int[] bestx;    // 最优解
        int r;          // 当前解中，连续邮资区间的最大值
        int max;        // 最优解中，连续邮资区间的最大值
        int[] y;        // 使用不超过m张邮票贴出邮资k所需的最小邮票数为y[k]

        public Stamp(int n, int m) {
            this.n = n;
            this.m = m;
            this.x = new int[n + 1];
            x[1] = 1;   // 邮资从1开始
            this.bestx = new int[n + 1];
            this.r = m;     // 初始时，当前解的最大连续邮资区间为m
            this.max = m;   // 初始时，最优解的最大连续邮资区间为m
            this.y = new int[MAX_POSTAGE + 1];
            for (int i = 1; i < y.length - 1; i++) {
                y[i] = INF;
            }
            for (int i = 0; i <= r; i++) {  // 处理x[1]=1的情况，并且初始化y[0]=0
                y[i] = i;
            }
            for (int i = r + 1; i <= y.length - 1; i++) {
                y[i] = INF;
            }
        }

        /**
         *
         * @param i 表示x[1:i-1]这i张邮票的面值已经确定，并且连续邮资区间为1到r，使用不超过
         *          m张邮票贴出邮资k所使用的最小邮票数y[k]也已经确定。现在增加x[i]。
         */
        public void backtrack(int i) {
            if (i > n) {
                if (r > max) {
                    max = r;
                    System.arraycopy(x, 1, bestx, 1, n);
                }
                return;
            }

            int[] backupy = new int[MAX_POSTAGE + 1];
            System.arraycopy(y, 0, backupy, 0, y.length);
            int backupr = r;
            // 增加x[i]，更新y[k]值
            for (int next = x[i - 1] + 1; next <= r + 1; next++) {  // x[i]的取值为[x[i-1]+1,r+1]
                x[i] = next;
                for (int postage = 0; postage <= x[i - 1] * m; postage++) { // 对于每一个给定的x[i]，更新y[k]值。这里，x[1:i-1]的邮资取值范围为[0,x[i-1]*m]，其中x[i-1]*m表示m张邮票的面值都取最大值x[i-1]
                    if (y[postage] < m) {   // 如果邮资为k所使用的最小邮票数y[k]小于m，则一直增加x[i]
                        for (int num = 1; num <= m - y[postage]; num++) {       // 可以增加[1,m-y[postage]]个x[i]
                            if (y[postage] + num < y[postage + num * next]) {   // 增加num个x[i]后，此时的邮资为postage+num*next，如果可以更新此时的y[k]值，则更新
                                y[postage + num * next] = y[postage] + num;
                            }
                        }
                    }
                }
                // 根据y[k]值更新r值
                while (y[r + 1] < INF) {
                    r++;
                }
                backtrack(i + 1);
                // restore
                System.arraycopy(backupy, 0, y, 0, backupy.length);
                r = backupr;
            }
        }

        public void output() {
            System.out.println(max);
            for (int i = 1; i <= n; i++) {
                System.out.print(bestx[i] + " ");
            }
            System.out.println();
        }
    }
}
