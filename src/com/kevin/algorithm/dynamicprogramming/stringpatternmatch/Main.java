package com.kevin.algorithm.dynamicprogramming.stringpatternmatch;

/**
 * @Author kevin
 * @Date 2016/12/6 20:30
 * KMP算法
 * 假设主串为s1,s2,...,sn，模式串为p1,p2,...,pm，当si与pj失配时，si应与模式串中的哪个字符再比较？
 * 假设此时si应与模式串中的pk(k<j)再比较，则模式串中的前k-1个字符构成的子串应满足：
 * p1...p(k-1) = s(i-(k-1))...s(i-1)
 * 其中1<k<j，且不可能存在k'>k满足上式。
 * 另外，已经得到的“部分匹配”的结果是：
 * p(j-(k-1))...pj = s(i-(k-1))...s(i-1)
 * 因此，p1...p(k-1) = p(j-(k-1))...pj
 * 这意味着，如果模式串中存在满足上式的两个子串，则在匹配过程中，当主串中si与模式串中pj失配时，si应与模式串中pk再比较。
 * 令next[j]=k，next[j]表明当模式串中pj与主串中si失配时，在模式串中需要重新与主串中si进行再比较的字符是pk，由此得出模式串
 * 中的next函数如下：
 * next[j]=0, j=1时
 * next[j]=Max{k|1<k<j且p1...p(k-1)=p(j-(k-1))...p(j-1)}
 * next[j]=1, 其他
 * 例如：
 * j        1   2   3   4   5   6   7   8
 * 模式串   a   b   a   a   b   c   a   c
 * next[j]  0   1   1   2   2   3   1   2
 * 在求得模式串的next函数之后，匹配可如下进行：假设主串中si与模式串中pj进行比较，令i的初始值为pos，j的初始值为1。若si=pj，则
 * i、j分别增1，否则，i不变，j退回next[j]的位置再比较，若相等，则i、j分别增1，否则，j再退回下一个next[j]的位置再比较，依次类
 * 推，直至下列两种可能：一种是j退到某个next值时，字符比较相等，则i、j分别增1，继续进行匹配；另一种是j退到next值为0，即模式串
 * 中的第一个字符失配，则此时需将模式串继续向右滑动一个位置，即从主串的下一个字符s(i+1)起和模式串重新开始匹配。由此得出匹配算
 * 法如下：
 * int index_KMP(char[] s, char[] t, int pos) { // s[0]和t[0]保存数组长度
 *     i = pos, j = 1;
 *     while (i <= s[0] && j <= t[0]) {
 *         if (j == 0 || s[i] == t[j]) {
 *             ++i; ++j;
 *         } else {
 *             j = next[j];
 *         }
 *     }
 *     if (j > t[0])
 *         return i - t[0];
 *     else
 *         return -1;
 * }
 *
 * 计算next函数值
 * next函数的定义如下：
 * next[j]=0, j=1时
 * next[j]=Max{k|1<k<j且p1...p(k-1)=p(j-(k-1))...p(j-1)}
 * next[j]=1, 其他
 * 由定义可知：next[1]=0;
 * 设next[j]=k，这表明在模式串中存在下列关系：
 * p1...p(k-1)=p(j-(k-1))...p(j-1)
 * 其中1<k<j，且不可能存在k'>k满足上式。此时，next[j+1]=?可能有两种情况：
 * (1)pj=pk，则表明在模式串中：
 * p1...pk=p(j-(k-1))...pj
 * 并且不可能存在k'>k满足上式。因此next[j+1]=k+1，即next[j+1]=next[j]+1
 * (2)pj!=pk，则表明在模式串中：
 * p1...pk!=p(j-(k-1))...pj
 * 但此时，有p1=p(j-(k-1)),...,p(k-1)=p(j-1)，但pk!=pj，因此可将这种情况视为一个模式匹配问题，模式串既是主串，又是模式串。
 * 因此，当pj!=pk时，应将主串(即模式串)中pj与模式串中p(next[k])再比较。若next[k]=k'，且pj=pk'，则表明在模式串中：
 * p1...pk'=p(j-(k'-1))...pj, (1<k'<k<j)
 * 因此，next[j+1]=k'+1，即next[j+1]=next[k]+1
 * 同理，若pj!=pk'，则应将主串(即模式串)中pj与模式传中p(next[k'])再比较，...，依次类推，直至pj和模式串中某个字符匹配成功，
 * 或者不存在任何k'(k'<j)满足上式，则next[j+1]=1。
 * 由此得出next函数值的计算公式如下：
 * 已知next[j]=k，当p[j]=p[k]时，next[j+1]=k+1；当p[j]!=p[k]时，比较p[j]与p(next[k])；当next[k]=0时(即模式串中第一个字符
 * 失配)，next[j+1]=1
 * 例如：
 * j        1   2   3   4   5   6   7   8
 * 模式串   a   b   a   a   b   c   a   c
 * next[j]  0   1   1   2   2   3   1   2
 * 求next函数值的函数如下：
 * void get_next(char[] t, int[] next) {
 *     next[1] = 0;
 *     j = 1;
 *     k = 0;
 *     while (j < t[0]) {
 *         if (k == 0 || t[j] == t[k]) {
 *             ++j;
 *             ++k;
 *             next[j] = k;
 *         } else {
 *             k = next[k];
 *         }
 *     }
 * }
 *
 * 改进next函数
 * 前面定义的next函数可以继续改进。假设主串中si与模式串中pj失配，若按前面的定义，则si与pk(k = next[j])再比较。此时，如果
 * pj=pk，则其实si可以跳过和pk进行再比较，而直接与p(next[k])进行比较。换句话说，此时next[j]=next[k]。
 * 例如：
 * j            1   2   3   4   5
 * 模式串       a   a   a   a   b
 * next[j]      0   1   2   3   4
 * nextval[j]   0   0   0   0   4
 * 假设主串为aaabaaaab，模式串为aaaab，当i=4，j=4时，s[i]!=p[j]，由next[j]的指示还需要进行3次比较：
 * i=4, j=3;    i=4, j=2;   i=4, j=1;
 * 实际上，因为模式串中第1-3个字符与第4个字符相同，因此不需要再和主串中第4个字符相比较，而可以直接比较i=5, j=1。
 * 求next函数改进值的函数如下：
 * void get_nextval(char[] t, int[] nextval) {
 *     nextval[1] = 0;
 *     j = 1;
 *     k = 0;
 *     while (j < t[0]) {
 *         if (k == 0 || t[j] == t[k]) {
 *             ++j;
 *             ++k;
 *             if (t[j] != t[k])
 *                 nextval[j] = k;
 *             else
 *                 nextval[j] = nextval[k];
 *         } else {
 *             k = next[k];
 *         }
 *     }
 * }
 */
public class Main {
    public static void main(String[] args) {
        char[] source = {9, 'a', 'a', 'a', 'b', 'a', 'a', 'a', 'a', 'b'};
        char[] target = {5, 'a', 'a', 'a', 'a', 'b'};
        System.out.println(indexKMP(source, target, 1));

        char[] source2 = {17, 'a', 'c', 'a', 'b', 'a', 'a', 'b', 'a', 'a', 'b', 'c', 'a', 'c', 'a', 'a', 'b', 'c'};
        char[] target2 = {8, 'a', 'b', 'a', 'a', 'b', 'c', 'a', 'c'};
        System.out.println(indexKMP(source2, target2, 1));

        char[] source3 = "aaabaaaab".toCharArray();
        char[] target3 = "aaaab".toCharArray();
        System.out.println(indexKMP2(source3, source3.length, target3, target3.length, 0));
    }

    /**
     * 常规做法，时间复杂度O(mn)
     * @param source 主串
     * @param sourceLength 主串长度
     * @param target 模式穿
     * @param targetLength 模式串长度
     * @param fromIndex 从主串中搜索的起始位置
     * @return 模式串在主串中的起始位置
     */
    public static int indexOf(char[] source, int sourceLength, char[] target, int targetLength, int fromIndex) {
        int i = fromIndex;
        int j = 0;
        while (j < targetLength && i < sourceLength) {
            if (target[j] == source[i]) {
                j++;
                i++;
            } else {
                j = 0;
                i = i - j + 1;
            }
        }
        return j == targetLength ? i - targetLength : -1;
    }

    /**
     *
     * @param source 主串，数组下标从1开始，source[0]存储主串长度
     * @param target 模式串，数组下标从1开始，target[0]存储模式串长度
     * @param fromIndex
     * @return
     */
    public static int indexKMP(char[] source, char[] target, int fromIndex) {
        int[] next = new int[target[0] + 1];
        getNext(target, next);

        int i = fromIndex;
        int j = 1;
        while (i <= source[0] && j <= target[0]) {
            if (j == 0 || source[i] == target[j]) {
                ++i;
                ++j;
            } else {
                j = next[j];
            }
        }
        if (j > target[0])
            return i - target[0];
        else
            return -1;
    }

    private static void getNext(char[] target, int[] next) {
        next[1] = 0;
        int j = 1;
        int k = 0;
        while (j < target[0]) {
            if (k == 0 || target[j] == target[k]) {
                ++j;
                ++k;
                if (target[j] != target[k])
                    next[j] = k;
                else
                    next[j] = next[k];
            } else {
                k = next[k];
            }
        }
    }

    /**
     *
     * @param source 主串，下标从0开始
     * @param sourceLength 主串长度
     * @param target 模式串，下标从0开始
     * @param targetLength 模式串长度
     * @param fromIndex
     * @return
     */
    public static int indexKMP2(char[] source, int sourceLength, char[] target, int targetLength, int fromIndex) {
        int[] next = new int[targetLength + 1];
        getNext2(target, targetLength, next);

        int i = fromIndex;
        int j = 0;
        while (i < sourceLength && j < targetLength) {
            if (j == -1 || source[i] == target[j]) {
                ++i;
                ++j;
            } else {
                j = next[j] - 1;
            }
        }
        if (j == targetLength)
            return i - targetLength;
        else
            return -1;
    }

    private static void getNext2(char[] target, int targetLength, int[] next) {
        next[1] = 0;
        int i = 1;
        int j = 0;
        while (i < targetLength) {
            if (j == 0 || target[i - 1] == target[j - 1]) {
                ++i;
                ++j;
                if (target[i - 1] != target[j - 1])
                    next[i] = j;
                else
                    next[i] = next[j];
            } else {
                j = next[j];
            }
        }
    }

}
