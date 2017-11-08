package com.kevin.test;

import java.util.Stack;

/**
 * @类名：Main4
 * @包名：com.kevin.test
 * @作者：kevin[wangqi2017@xinhua.org]
 * @时间：2017/8/29 1:06
 * @版本：1.0
 * @描述：
 */
public class Main4 {

    public static void main(String[] args) {
        String str = "(()())";
        System.out.println(solution(str));
    }

    public static int solution(String str) {
        if ("".equals(str)) {
            return 1;
        }

        String[] s = str.split("");
        Stack<String> stack = new Stack<>();
        int result = 1;
        int tmp = 0;

        for (int i = 0; i < s.length; i++) {
            if ("(".equals(s[i])) {
                if (stack.isEmpty() && tmp > 0) {
                    result *= stage(tmp);
                }
                stack.push(s[i]);
                if (tmp > 0) {
                    tmp = 0;
                }
            } else {
                if (!stack.isEmpty()) {
                    stack.pop();
                    tmp++;
                }
            }
        }

        if (tmp == 0) {
            result = 1;
        } else {
            result *= stage(tmp);
        }

        return result;
    }

    public static int stage(int x) {
        int result = 1;
        for (int i = 1; i <= x; i++) {
            result *= i;
        }
        return result;
    }
}
