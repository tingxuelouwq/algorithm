package com.kevin.algorithm.dynamicprogramming.longestcommonsubstring;

/**
 * @Author kevin
 * @Date 2016/11/30 21:58
 * 问题描述：最长公共子串。给定字符串s，定义字符串s的子串s[i:j]为s中从i到j这一段，定义字符串s的后缀suffix(s,i)为s中从i开始到串末尾的这一段，即
 * suffix(s,i)=s[i:len(s)]。字符串z如果既是字符串x的后缀，也是字符串y的后缀，则z是x和y的一个公共后缀，如果z是x和y的所有公共后缀中最长的那一个，
 * 则称z为x和y的最长公共后缀。字符串z如果既是字符串x的子串，也是字符串y的子串，则z是x和y的一个公共子串，如果z是x和y的所有公共子串中最长的那一个，
 * 则称z为x和y的最长公共子串。
 * 算法思路：要求长度分别为p和q的字符串s和t的最长公共子串，可以先求出任意前缀子串对s[1:i]和t[1:j]的最长公共后缀，用LCSuffix(s[1:i],t[1:j])
 * 表示，则s和t的最长公共子串LCS(s,t)为：LCS(s,t)=max{LCSuffix(s[1:i],t[1:j])}，其中1<=i<=p，1<=j<=q。对于字符串s和t的任意前缀子串对，
 * 有如下的递推公式：
 * 当s[i]=t[j]时，LCSuffix(s[1:i],t[1:j])=LCSuffix(s[1:i-1],t[1:j-1])+s[i]；
 * 当s[i]!=t[j]时，LCSuffix(s[1:i],t[1:j])=""。
 * 设c[i][j]表示LCSuffix(s[1:i],t[1:j])的大小，则以上递推公式变为：
 * 当s[i]=t[j]时，c[i][j]=c[i-1][j-1]+1；
 * 当s[i]!=t[j]时，c[i][j]=0
 * 例如，字符串s="abab"，字符串t="baba"，则递推过程如下：
 *      0    1   2   3   4
 *      s    a   b   a   b
 * 0 t  ""   ""  ""  ""  ""
 * 1 b  ""   ""  b   ""  b
 * 2 a  ""   a   ""  ba  ""
 * 3 b  ""   ""  ab  ""  bab
 * 4 a  ""   a   ""  aba ""
 */
public class Main {
    public static void main(String[] args) {
        String str1 = "abab";
        String str2 = "baba";
        lcs(str1, str2);
        lcs2(str1, str2);
    }

    /**
     * 动态规划求解最长公共子串，时间复杂度O(mn)
     * @param str1
     * @param str2
     */
    public static void lcs(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        if (m == 0 || n == 0) return;

        int[][] c = new int[m + 1][n + 1];
        int i, j;
        for (i = 0; i <= m; i++)
            c[i][0] = 0;
        for (j = 0; j <= n; j++)
            c[0][j] = 0;

        int end1 = 0;
        int end2 = 0;
        int len = 0;
        for (i = 1; i <= m; i++) {
            for (j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1))
                    c[i][j] = c[i - 1][j - 1] + 1;
                else
                    c[i][j] = 0;
                if (c[i][j] > len) {
                    len = c[i][j];
                    end1 = i;
                    end2 = j;
                }
            }
        }

        // 输出最长公共子串
        String common = str1.substring(end1 - len, end1);
        System.out.println("(first, second, length, common) = " + "(" +
                (end1 - len) + ", " + (end2 - len) + ", " + len + ", " + common.toString() + ")");
    }

    /**
     * 解法二：将字符串s1和字符串s2分别写到两个直尺上，然后将s1固定，将s2的尾部与s1的头部对齐，接着逐渐移动s2，比较重叠部分的字符串中公共子串的
     * 长度，直到s2移动到s1的尾部。例如s1="abab"，s2="baba"，则过程如下：
     * s1     abab    abab    abab    abab    abab    abab    abab
     * s2  baba     baba     baba     baba     baba     baba     baba
     *      "a"     ""      "aba"   ""      "bab"    ""       "b"
     */
    public static void lcs2(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        if (len1 == 0 || len2 == 0) return;

        int len = len1 + len2;
        int first = 0, second = 0, max = 0;     // 最大公共子串分别在s1和s2中的起始下标，以及子串长度
        int start1, start2, curmax;             /* 本次移动，start1表示重叠部分中s1的起始下标，start2表示重叠部分中s2的起始下标，
                                                 * curmax表示重叠部分的最大公共子串的长度 */
        int i, j;
        for (i = 1; i < len; i++) {
            start1 = 0;
            start2 = 0;
            curmax = 0;
            if ( i <= len2)
                start2 = len2 - i;
            else
                start1 = i - len2;
            for (j = 0; (start2 + j) < len2 && (start1 + j) < len1; j++)    // 对重叠部分进行处理
                if (str2.charAt(start2 + j) == str1.charAt(start1 + j))
                    curmax++;
            if (curmax > max) {
                max = curmax;
                first = start1;
                second = start2;
            }
        }

        // 输出最大公共子串
        String common = str1.substring(first, first + max);
        System.out.println("(first, second, length, common) = " + "(" +
                first + ", " + second + ", " + max + ", " + common + ")");
    }
}
