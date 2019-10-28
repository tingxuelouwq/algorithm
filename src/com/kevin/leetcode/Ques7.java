package com.kevin.leetcode;

/**
 * 类名: Ques7<br/>
 * 包名：com.kevin.leetcode<br/>
 * 作者：kevin<br/>
 * 时间：2018/11/28 11:30<br/>
 * 版本：1.0<br/>
 * 描述：
 */
public class Ques7 {

    public static void main(String[] args) {
        int x = 1534236469;
        System.out.println(reverse(x));
    }

    public static int reverse(int x) {
        long answer = 0;
        while (x != 0) {
            answer = answer * 10 + x % 10;
            x = x / 10;
        }
        return (answer < Integer.MIN_VALUE || answer > Integer.MAX_VALUE) ? 0 : (int) answer;
    }
}
