package com.kevin.algorithm.recursive;

/**
 * @Author kevin
 * @Date 2016/10/31 11:27
 *
 * 问题描述：
 * 将正整数n表示成一系列正整数之和，n=n1+n2+n3+...+nk(其中，n1>=n2>=n3>=...>=nk, k>=1)。正整数n的这种表示称为正整数n的划分，
 * 不同划分的个数称为正整数n的划分个数，例如正整数6有11种不同的划分：
 * 6
 * 5+1
 * 4+2,4+1+1
 * 3+3,3+2+1,3+1+1+1+1
 * 2+2+2,2+2+1+1,2+1+1+1+1
 * 1+1+1+1+1+1
 * 记q(n,m)为正整数n的所有划分中，最大加数不大于m的划分个数。可以建立如下递推关系：
 * q(n,m)=
 * q(n,n)，当n<m时
 * 1+q(n,n-1)，当n=m时
 * q(n,m-1)+q(n-m,m)，当n>m>1时
 * 1，当n=1，m=1时
 * 比较难理解的是当n>m>1时，q(n,m)=q(n,n-1)+q(n-m,m)。以q(6,4)为例，q(6,3)是后3排的内容，即：
 * 3+3,3+2+1,3+1+1+1+1
 * 2+2+2,2+2+1+1,2+1+1+1+1
 * 1+1+1+1+1+1
 * 而q(2,4)=q(2,2)，是第3排的内容，即：
 * 2    -- 4+2
 * 1+1  -- 4+1+1
*/
public class IntegerPartition {
    public static void main(String[] args) {
        System.out.println(partition(6));
    }

    public static int partition(int n) {
        if(n < 1)
            return 0;
        else
            return partition(n, n);
    }

    private static int partition(int n, int m) {
        if(n == 1 || m == 1)
            return 1;
        else if(n < m)
            return partition(n, n);
        else if(n == m)
            return 1+ partition(n, n - 1);
        else
            return partition(n, m - 1) + partition(n - m, m);
    }
}
