package com.kevin.algorithm.recursive;

/**
 * @Author kevin
 * @Date 2016/10/31 11:44
 *
 * 问题描述：古代有一个梵塔，塔内有三个座A、B、C，A座上有64个盘子，盘子大小不等，大的在下，小的在上。有一个和尚想把这64个盘子从A座
 * 移到C座，但每次只允许移动一个盘子，并且在移动过程中，3个座上的盘子始终保持大盘在下，小盘在上。在移动过程中可以利用个B座，要求打印
 * 移动的步骤。
 *
 * 算法思路：
 * 如果只有一个盘子，则直接将盘子从A移动C；
 * 如果有2个盘子，可以先将盘子1上的盘子2移动到B；将盘子1移动到c；将盘子2移动到c。这说明了：可以借助B将2个盘子从A移动到C，当然，也
 * 可以借助C将2个盘子从A移动到B；
 * 如果有3个盘子，那么根据2个盘子的结论，可以借助C将盘子1上的两个盘子从A移动到B；将盘子1从A移动到C，A变成空座；借助A座，将B上的两个
 * 盘子移动到C。这说明：可以借助一个空座，将3个盘子从一个座移动到另一个;
 * 如果有4个盘子，那么首先借助空座C，将盘子1上的三个盘子从A移动到B；将盘子1移动到C，A变成空座；借助空座A，将B座上的三个盘子移动到C。
 * 依次类推。
 */
public class Hanoi {
    static int step = 0;

    public static void main(String[] args) {
        hanoi(3, 'A', 'B', 'C');
        System.out.println("总共需要" + step + "步");
    }

    public static void hanoi(int n, char a, char b, char c) {
        if(n > 0) {
            hanoi(n - 1, a, c, b);  // 将A上的n-1个盘子借助C移到B
            System.out.println("将" + a + "中顶部的盘子从" + a + "移到" + c);
            ++step;
            hanoi(n - 1, b, a, c);  // 将B上的n-1个盘子借助A移到C
        }
    }
}
