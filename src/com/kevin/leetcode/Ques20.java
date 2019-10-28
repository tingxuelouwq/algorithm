package com.kevin.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 类名: Ques20<br/>
 * 包名：com.kevin.leetcode<br/>
 * 作者：kevin<br/>
 * 时间：2018/11/30 9:41<br/>
 * 版本：1.0<br/>
 * 描述：
 */
public class Ques20 {

    private Map<Character, Character> mappings;

    public Ques20() {
        mappings = new HashMap<>();
        mappings.put(')', '(');
        mappings.put('}', '{');
        mappings.put(']', '[');
    }

    /**
     * Time complexity: O(n) because we simply traverse the given string one character at
     * a time and push and pop operations on a stack take O(1) time.
     * Space complexity: O(n) as we push all opening brackets onto the stack and in the
     * worst case, we will end up pushing all the brackets onto the stack. e.g. ((((((((((.

     */
    public boolean isValid1(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (mappings.containsKey(c)) {
                char top = stack.empty() ? '#' : stack.pop();
                if (top != mappings.get(c)) {
                    return false;
                }
            } else {
                stack.push(c);
            }
        }
        return stack.empty();
    }

    public boolean isValid2(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '(':
                case '{':
                case '[':
                    stack.push(c);
                    break;
                case ')':
                    if (stack.empty() || stack.peek() != '(') {
                        return false;
                    }
                    stack.pop();
                    break;
                case '}':
                    if (stack.empty() || stack.peek() != '{') {
                        return false;
                    }
                    stack.pop();
                    break;
                case ']':
                    if (stack.empty() || stack.peek() != '[') {
                        return false;
                    }
                    stack.pop();
                    break;
            }
        }
        return stack.empty();
    }

    public static void main(String[] args) {
        Ques20 ques6 = new Ques20();
        String s = "{[]}";
        System.out.println(ques6.isValid1(s));
    }
}
