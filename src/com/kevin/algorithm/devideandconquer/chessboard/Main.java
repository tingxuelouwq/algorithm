package com.kevin.algorithm.devideandconquer.chessboard;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/10/30 10:31
 * 问题描述：在一个2^k*2^k(k>=0)组成的棋盘中，恰有一个方格与其他方格不同，称该方格为特殊方格。显然，特殊方格在棋盘中可能出现的位置
 * 有4^k种。棋盘覆盖问题要求用如图所示的4种不同形状的L型骨牌覆盖给定棋盘上除特殊方格以外的所有方格，且任何2个L型骨牌不得重复覆盖。
 * ┗ ┛ ┎ ┒
 *
 * 算法思路：采用分治法。分治的技巧在于如何划分棋盘，使划分后的子棋盘的大小相同，并且每个棋盘均包含一个特殊方格，从而将原问题分解为
 * 规模较小的棋盘覆盖问题。k>0时，可将2^k*2^k的棋盘划分为4个2^(k-1)*2^(k-1)的子棋盘，这样划分后，由于原棋盘中只有一个特殊方格，所
 * 以，这4个棋盘中只有一个棋盘包含该特殊方格，其余3个子棋盘中没有特殊方格。为了将这3个没有特殊方格的子棋盘转化为特殊棋盘，以便采用
 * 递归方法求解，可以用一个L型骨牌覆盖这3个较小棋盘的汇合处，从而将原问题转化为4个较小规模的棋盘覆盖问题。递归的使用这种划分策略，
 * 直至将棋盘分割为1*1的子棋盘。
 *
 * 算法实现：下面讨论棋盘覆盖问题中数据结构的设计。
 * 1、棋盘：可以用一个二维数组board[size][size]来表示，其中size=2^k。为了在递归处理的过程中使用同一个棋盘，将数组board设为全局
 * 变量
 * 2、子棋盘：整个棋盘用二维数组board[size][size]表示，其中的子棋盘由棋盘左上角的下标tr、tc和子棋盘大小s来表示
 * 3、特殊方格：用board[dr][dc]表示，dr和dc是特殊方格在二维数组board中的下标
 * 4、L型骨牌：一个2^k*2^k的棋盘中有一个特殊方格，所以用到的L型骨牌的个数为(4^k-1)/3，将所有L型骨牌从1开始连续编号，用一个全局变量
 * t表示
 *
 * 时间复杂度分析：设T(k)是算法chessboard覆盖一个2^k*2^k棋盘所需的时间，从算法的划分策略可知：
 * T(K)=1，当k=0时，
 * T(k)=4T(k-1)，当k>0时
 * 因此T(k)=O(4^k)
 */
public class Main {
    static int t;               // 标记L型骨牌的编号

    public static void main(String[] args) {
        int[][] board;  // 棋盘
        int size;   // 棋盘边长
        int dr, dc; // 特殊方格的坐标
        Scanner in = new Scanner(System.in);
        while(in.hasNextInt()) {
            size = in.nextInt();
            dr = in.nextInt();
            dc = in.nextInt();

            board = new int[size][size];
            t = 0;

            chessboard(board, 0, 0, dr, dc, size);
            print(board);
        }
    }

    private static void print(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++)
                System.out.print(board[i][j] + " ");
            System.out.println();
        }
    }

    /**
     * @param board 棋盘
     * @param tr 棋盘左上角的横坐标
     * @param tc 棋盘左上角的纵坐标
     * @param dr 特殊方格的横坐标
     * @param dc 特殊方格的纵坐标
     * @param size 棋盘边长
     */
    private static void chessboard(int[][] board, int tr, int tc, int dr, int dc, int size) {
        if(size == 1)   // 棋盘只有一个方格，则一定是特殊方格，返回
            return;

        int s, t1;  // s表示子棋盘的大小，t1表示本次覆盖所使用的L型骨牌的编号
        t1 = ++t;
        s = size / 2;

        if(dr < tr + s && dc < tc + s)  // 特殊方格在左上角的子棋盘中
            chessboard(board, tr, tc, dr, dc, s);
        else {  // 用t1号L型骨牌覆盖右下角，再递归处理子棋盘
            board[tr + s - 1][tc + s - 1] = t1;
            chessboard(board, tr, tc, tr + s - 1, tc + s - 1, s);
        }

        if(dr < tr + s && dc >= tc + s) // 特殊方格在右上角的子棋盘中
            chessboard(board, tr, tc + s, dr, dc, s);
        else {  // 用t1号L型骨牌覆盖左下角，再递归处理子棋盘
            board[tr + s - 1][tc + s] = t1;
            chessboard(board, tr, tc + s, tr + s - 1, tc + s, s);
        }

        if(dr >= tr + s && dc < tc + s) // 特殊方格在左下角的子棋盘中
            chessboard(board, tr + s, tc, dr, dc, s);
        else {  // 用t1号L型骨牌覆盖右上角，再递归处理子棋盘
            board[tr + s][tc + s - 1] = t1;
            chessboard(board, tr + s, tc, tr + s, tc + s - 1, s);
        }

        if(dr >= tr + s && dc >= tc + s)    // 特殊方格在右下角的子棋盘中
            chessboard(board, tr + s, tc + s, dr, dc, s);
        else {  // 用t1号L型骨牌覆盖左上角，再递归处理子棋盘
            board[tr + s][tc + s] = t1;
            chessboard(board, tr + s, tc + s, tr + s, tc + s, s);
        }
    }
}
