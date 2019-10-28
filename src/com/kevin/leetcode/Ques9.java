package com.kevin.leetcode;

/**
 * 类名: Ques9<br/>
 * 包名：com.kevin.leetcode<br/>
 * 作者：kevin<br/>
 * 时间：2018/11/28 15:54<br/>
 * 版本：1.0<br/>
 * 描述：
 */
public class Ques9 {

    public static void main(String[] args) {
        int x = 121;
//        System.out.println(isPalindrome1(x));
        System.out.println(isPalindrome2(x));
    }

    public static boolean isPalindrome1(int x) {
        if (x < 0) {
            return false;
        }
        String str = x + "";
        int i = 0, j = str.length() - 1;
        while (i < j) {
            char left = str.charAt(i);
            char right = str.charAt(j);
            if (left != right) {
                return false;
            } else {
                i++;
                j--;
            }
        }
        return true;
    }

    public static boolean isPalindrome2(int x) {
        if (x < 0 || (x != 0 && x % 10 == 0)) {
            return false;
        }
        int revertedNumber = 0;
        while (x > revertedNumber) {
            revertedNumber = revertedNumber * 10 + x % 10;
            x /= 10;
        }
        return x == revertedNumber || x == revertedNumber / 10;
    }
}
