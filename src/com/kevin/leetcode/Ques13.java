package com.kevin.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名: Ques13<br/>
 * 包名：com.kevin.leetcode<br/>
 * 作者：kevin<br/>
 * 时间：2018/11/29 10:56<br/>
 * 版本：1.0<br/>
 * 描述：
 */
public class Ques13 {

    public static void main(String[] args) {
        String s = "MCMXCIV";
//        System.out.println(romanToInt1(s));
//        System.out.println(romanToInt2(s));
        System.out.println(romanToInt3(s));
    }

    public static int romanToInt1(String s) {
        int len = s.length();
        int[] nums = new int[len];
        for (int i = 0; i < len; i++) {
            switch (s.charAt(i)) {
                case 'I':
                    nums[i] = 1;
                    break;
                case 'V':
                    nums[i] = 5;
                    break;
                case 'X':
                    nums[i] = 10;
                    break;
                case 'L':
                    nums[i] = 50;
                    break;
                case 'C':
                    nums[i] = 100;
                    break;
                case 'D':
                    nums[i] = 500;
                    break;
                case 'M':
                    nums[i] = 1000;
                    break;
            }
        }

        int sum = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] < nums[i + 1]) {
                sum -= nums[i];
            } else {
                sum += nums[i];
            }
        }
        return sum + nums[nums.length - 1];
    }

    public static int romanToInt2(String s) {
        int sum = 0;
        int cur = 0;
        int last = 0;
        int len = s.length();
        for (int i = len - 1; i >= 0; i--) {
            switch (s.charAt(i)) {
                case 'I':
                    cur = 1;
                    break;
                case 'V':
                    cur = 5;
                    break;
                case 'X':
                    cur = 10;
                    break;
                case 'L':
                    cur = 50;
                    break;
                case 'C':
                    cur = 100;
                    break;
                case 'D':
                    cur = 500;
                    break;
                case 'M':
                    cur = 1000;
                    break;
            }
            if (last <= cur) {
                sum += cur;
            } else {
                sum -= cur;
            }
            last = cur;
        }
        return sum;
    }

    public static int romanToInt3(String s) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        int sum = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            if (map.get(s.charAt(i)) < map.get(s.charAt(i + 1))) {
                sum -= map.get(s.charAt(i));
            } else {
                sum += map.get(s.charAt(i));
            }
        }
        sum += map.get(s.charAt(s.length() - 1));
        return sum;
    }
}
