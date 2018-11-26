package com.kevin.algorithm.dynamicprogramming.longestincreasingsubsequence;

import java.util.Arrays;

/**
 * @Author kevin
 * @Date 2016/12/5 16:52
 * 问题描述：最长递增子序列。给定数a1,a2,...,an，找出使得ai1<ai2<...<aik，且i1<i2<...<ik的最大的k值。例如输入为3,1,4,1,5,9,3,6,5，
 * 那么最大递增子序列的长度为4，该子列为1,4,5,9.
 */
public class Main {
    public static void main(String[] args) {
//        int[] a = {5,6,7,1,2,8};
//        int[] a = {3, 1, 4, 1, 5, 9, 3, 6, 5};
//        int[] a = {2, 1, 5, 3, 6, 4, 8, 9, 7};
        int[] a = {3, 10, 5, 15, 6, 8};
        lis(a);
        lis2(a);
        lis3(a);
        lis4(a);
    }

    /**
     * 解法一：通过最长公共子序列法求最长递增子序列，时间复杂度O(n^2)，但这种做法不能处理有重复元素的序列
     * 算法思路：将问题转换成最长公共子序列问题，例如给定数组{5,6,7,1,2,8}，则可以将原数组进行排序得到{1,2,5,6,7,8}，然后
     * 找出两者的最长公共子序列即可。
     * 求最长公共子序列的算法思路：
     *
     * @param a
     */
    public static void lis(int[] a) {
        int n = a.length;
        int[] b = new int[n];
        System.arraycopy(a, 0, b, 0, n);
        Arrays.sort(b);
        lcs(n, a, b);
    }

    private static void lcs(int n, int[] a, int[] b) {
        int[][] c = new int[n + 1][n + 1];
        int i, j;
        for (i = 0; i <= n; i++)
            c[i][0] = 0;
        for (j = 0; j <= n; j++)
            c[0][j] = 0;

        for (i = 1; i <= n; i++) {
            for (j = 1; j <= n; j++) {
                if (a[i - 1] == b[j - 1])
                    c[i][j] = c[i - 1][j - 1] + 1;
                else
                    c[i][j] = Math.max(c[i - 1][j], c[i][j - 1]);
            }
        }

        // track back
        StringBuilder s = new StringBuilder();
        i = n;
        j = n;
        while (i > 0 && j > 0) {
            if (c[i][j] == c[i - 1][j - 1] + 1 &&
                    a[i - 1] == b[j - 1]) {
                s.insert(0, a[i - 1]);
                i--;
                j--;
            } else if (c[i - 1][j] > c[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        System.out.println("The longest increasing subsequence is: " + s.toString());
    }

    /**
     * 解法二：动态规划，时间复杂度O(n^2)
     * 算法思路：设以aj结尾(以aj作为最大元素)的数组序列的最长递增子序列的长度为L(j)，则L(j)=max{L(i)}+1, i<j且a[j]>a[i]。
     * 也就是说，我们需要遍历在j之前的所有位置i(0<=i<j)，找出满足a[i]<a[j]的所有L(i)，求出max{L(i)}+1即为L(j)。最后，遍历
     * 所有的L(j)，0<=j<n，找出最大值即为最长递增子序列的长度。
     * 例如，给定序列为3 1 4 1 5 9 3 6 5，则
     * l(0)=1   k=0 // k用来标识此时最长自增子序列的末尾元素的位置
     * l(1)=1
     * l(2)=2   k=2
     * l(3)=1
     * l(4)=3   k=4
     * l(5)=4   k=5
     * l(6)=2
     * l(7)=4
     * l(8)=3
     * * @param a
     */
    public static void lis2(int[] a) {
        int n = a.length;
        int[] l = new int[n];
        int[] pre = new int[n]; // 记录以a[i]结尾的最长递增子序列的前驱节点
        int i, j, k, max;       // max表示最长递增子序列的长度，初始为1；k表示最长递增子序列的末尾元素的位置，初始为0

        for (i = 0; i < n; i++) {
            l[i] = 1;
            pre[i] = i;
        }
        for (i = 1, max = 1, k = 0; i < n; i++) {
            for (j = 0; j < i; j++) {
                if (a[i] > a[j] && l[i] < l[j] + 1) {
                    l[i] = l[j] + 1;
                    pre[i] = j;     // 更新以a[i]结尾的最长递增子序列的前驱节点
                    if (max < l[i]) {
                        max = l[i]; // 更新最长递增子序列的长度
                        k = i;      // 更新最长递增子序列的末尾元素
                    }
                }
            }
        }

        int[] result = new int[max];
        i = max - 1;
        while (pre[k] != k) {
            result[i--] = a[k];
            k = pre[k];
        }
        result[i] = a[k];

        System.out.println("(length, subsequence)" + "(" + max + ", " + Arrays.toString(result) + ")");
    }

    /**
     * 解法三：时间复杂度为O(nlogn)的算法
     * 算法思路：假设给定序列为a[1...9]=2 1 5 3 6 4 8 9 7，我们定义一个序列b，b[i]表示所有长度为i的递增子序列中末尾元素
     * 最小的递增子序列的末尾元素，此外，我们还使用一个变量len来记录目前序列b的长度。接下来扫描序列a。
	 * 首先，把a[1]放到b中，即b[1]=2，这意味着长度为1的递增子序列中末尾元素最小的递增子序列的末尾元素是2。此时len=1
	 * 然后，把a[2]放到b中，由于a[2]=1<b[1]，因此令b[1]=1，这意味着长度为1的递增子序列中末尾元素最小的递增子序列的末尾元素是1。此时len=1
     * 接着，把a[3]放到b中，由于a[3]=5>b[1]，因此令b[2]=5，这意味着长度为2的递增子序列中末尾元素最小的递增子序列的末尾元素是5。此时len=2
     * 接着，把a[4]放到b中，由于a[4]=3<b[2]，因此令b[2]=3，这意味着长度为2的递增子序列中末尾元素最小的递增子序列的末尾元素是3。此时len=2
     * 接着，把a[5]放到b中，由于a[5]=6>b[3]，因此令b[3]=6，这意味着长度为3的递增子序列中末尾元素最小的递增子序列的末尾元素是6。此时len=3
     * 接着，把a[6]放到b中，由于a[6]=4<b[3]，因此令b[3]=4，此时len=3
     * 接着，把a[7]放到b中，b[4]=8，len=4
     * 接着，把a[8]放到b中，b[5]=9，len=5
     * 最后，把a[9]放到b中，由于a[9]=7，在b[3]和b[4]之间，因此令b[4]=7，此时len=5
     * 最终，b={1,3,4,7,9}，len=5
     * 观察序列b中的元素，可以看到，序列b中的元素是有序的，而且是进行元素替换(找到序列b中大于待插入元素的第一个元素，然后进行替换)，
     * 这意味着我们可以使用二分查找，将每一个元素的插入时间优化到O(logn)，于是算法的时间复杂度降低到O(nlogn)
     * @param a
     */
    public static void lis3(int[] a) {
        int n = a.length;
        int[] b = new int[n];   // 序列b
        b[0] = a[0];

        int i, len, pos;     // len用于记录序列b的长度，pos用于记录待插入元素在序列b中位置
        for (i = 1, len = 1; i < n; i++) {
            if (a[i] > b[len - 1]) {  // 如果待插入元素大于b中的最大元素，则直接插入到末尾
                b[len++] = a[i];
            } else {
                pos = binarySearch(b, len, a[i]);   // 否则，二分查找要位置，并进行替换
                b[pos] = a[i];
            }
        }

        System.out.println("The length of the longest increasing subsequence is: " + len);
    }

    private static int binarySearch(int[] b, int len, int x) {
        int left = 0, right = len - 1;
        int mid;
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (b[mid] > x)
                right = mid - 1;
            else if (b[mid] < x)
                left = mid + 1;
            else
                return mid;
        }
        return left;
    }

    /**
     * 对解法三的改进：输出最长递增子序列
     * 例如，给定序列为2 1 5 3 6 4 8 9 7，则序列b的更新情况如下：
     * 2
     * 1
     * 1,5          5->1    // 5的前驱是1
     * 1,3          3->1
     * 1,3,6        6->3
     * 1,3,4        4->3
     * 1,3,4,8      8->4
     * 1,3,4,8,9    9->8
     * 1,3,4,7,9    7->4
     * 这里为了输出最长递增子序列，稍微改变一下序列b的含义，b[i]表示长度为i的所有递增子序列中，末尾元素最小的递增子序列的末尾
     * 元素在原始序列中的下标；相应地，记录前驱的序列pre的含义也做相应改变，pre[i]表示原始数组中下标为i的前驱的下标
     * @param a
     */
    public static void lis4(int[] a) {
        int n = a.length;
        int[] pre = new int[n]; // 记录最长递增子序列的前驱，pre[i]表示原始数组中下标为i的前驱的下标
        int[] b = new int[n];
        b[0] = 0;

        int i, len, pos;    // len表示序列b的长度，pos代表待插入元素在序列b中的插入下标
        for (i = 0; i < n; i++)
            pre[i] = i;
        for (i = 1, len = 1; i < n; i++) {
            pos = binarySearch2(a, b, len, a[i]);
            if (pos == 0) { // 如果pos==0，说明长度为1的递增子序列的末尾元素可以被更新
                b[pos] = i;
            } else if (pos == len) {        // 如果pos==len，说明序列b可以增长1
                pre[i] = b[pos - 1];      // 更新前驱，例如5->1
                b[pos] = i;
                len++;
            } else {    // 否则，说明序列b中的某个元素会被更新
                pre[i] = b[pos - 1];
                b[pos] = i;
            }
        }

        i = len - 1;
        int[] result = new int[len];
        int k = b[len - 1];
        while (pre[k] != k) {
            result[i--] = a[k];
            k = pre[k];
        }
        result[i] = a[k];

        System.out.println("(length, subsequence)" + "(" + len + ", " + Arrays.toString(result) + ")");
    }

    private static int binarySearch2(int[] a, int[] b, int len, int x) {
        int left = 0, right = len - 1;
        int mid;
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (a[b[mid]] < x)
                left = mid + 1;
            else if (a[b[mid]] > x)
                right = mid - 1;
            else
                return mid;
        }
        return left;
    }


}
