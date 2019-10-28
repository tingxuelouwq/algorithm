package com.kevin.leetcode;

/**
 * 类名: Ques14<br/>
 * 包名：com.kevin.leetcode<br/>
 * 作者：kevin<br/>
 * 时间：2018/11/29 14:12<br/>
 * 版本：1.0<br/>
 * 描述：
 */
public class Ques14 {

    public static void main(String[] args) {
        String[] strs = {"flow",""};
//        System.out.println(longestCommonPrefix1(strs));
//        System.out.println(longestCommonPrefix2(strs));
//        System.out.println(longestCommonPrefix3(strs));
        System.out.println(longestCommonPrefix4(strs));
    }

    /**
     * Time complexity: O(S), where S is the sum of all characters in all strings. In the
     * worst case all n strings are the same. The algorithm compares the string S1 with
     * the other strings [S2...Sn]. There are S character comparisons, where S is the
     * sum of all characters in the input array.
     *
     * Space complexity: O(1). We only used constant extra space.
     */
    public static String longestCommonPrefix1(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }
        return prefix;
    }

    /**
     * Time complexity: O(S), where S is the sum of all characters in all strings.
     * In the worst case there will be n equal strings with length m and the algorithm
     * performs S = m*n character comparisons. Even though the worst case is still the
     * same as Approach 1, in the best case there are at most n*minLen comparisons where
     * minLen is the length of the shortest string in the array.
     *
     * Space complexity: O(1). We only used constant extra space.
     */
    public static String longestCommonPrefix2(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i == strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }
        return strs[0];
    }

    /**
     * Time complexity: O(S), where S is the number of all characters in the array,
     * S = m*n Time complexity is 2*T(n/2)+O(m). Therefore time complexity is O(S).
     * In the best case this algorithm performs O(minLen⋅n) comparisons, where minLenminLen
     * is the shortest string of the array
     *
     * Space complexity: O(m⋅log(n)). There is a memory overhead since we store recursive
     * calls in the execution stack. There are log(n) recursive calls, each store need m
     * space to store the result, so space complexity is O(m⋅log(n))
     */
    public static String longestCommonPrefix3(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        return longestCommonPrefix(strs, 0, strs.length - 1);
    }

    private static String longestCommonPrefix(String[] strs, int left, int right) {
        if (left == right) {
            return strs[left];
        } else {
            int mid = (left + right) / 2;
            String lcpLeft = longestCommonPrefix(strs, left, mid);
            String lcpRight = longestCommonPrefix(strs, mid + 1, right);
            return commonPrefix(lcpLeft, lcpRight);
        }
    }

    private static String commonPrefix(String left, String right) {
        int min = Math.min(left.length(), right.length());
        for (int i = 0; i < min; i++) {
            if (left.charAt(i) != right.charAt(i)) {
                return left.substring(0, i);
            }
        }
        return left.substring(0, min);
    }

    /**
     * Time complexity: O(S⋅log(n)), where SS is the sum of all characters in all strings.
     * The algorithm makes log(n) iterations, for each of them there are S=m∗n comparisons,
     * which gives in total O(S⋅log(n)) time complexity.
     *
     * Space complexity: O(1). We only used constant extra space.
     */
    public static String longestCommonPrefix4(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        int minLen = Integer.MAX_VALUE;
        for (String str : strs) {
            minLen = Math.min(minLen, str.length());
        }
        int low = 1;
        int high = minLen;
        while (low <= high) {
            int middle = (low + high) / 2;
            if (isCommonPrefix(strs, middle)) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return strs[0].substring(0, (low + high) / 2);
    }

    private static boolean isCommonPrefix(String[] strs, int len) {
        String str1 = strs[0].substring(0, len);
        for (int i = 1; i < strs.length; i++) {
            if (!strs[i].startsWith(str1)) {
                return false;
            }
        }
        return true;
    }
}
