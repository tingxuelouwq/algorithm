package com.kevin.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名: Ques1<br/>
 * 包名：com.kevin.leetcode<br/>
 * 作者：kevin[wangqi2017@xinhua.org]<br/>
 * 时间：2018/11/26 14:51<br/>
 * 版本：1.0<br/>
 * 描述：
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 *
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 *
 * Example:
 *
 * Given nums = [2, 7, 11, 15], target = 9,
 *
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 */
public class Ques1 {

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
//        int[] result = twoSum1(nums, target);
//        int[] result = twoSum2(nums, target);
        int[] result = twoSum3(nums, target);
        System.out.println(result[0] + "," + result[1]);
    }

    /**
     * 时间复杂度O(n^2)
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum1(int[] nums, int target) {
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * 时间复杂度O(n)
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            int pair = target - nums[i];
            if (map.containsKey(pair)) {
                return new int[]{map.get(pair), i};
            } else {
                map.put(nums[i], i);
            }
        }
        return new int[2];
    }

    /**
     * 时间复杂度O(nlogn)
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum3(int[] nums, int target) {
        int[] copyNums = Arrays.copyOf(nums, nums.length);
        Arrays.sort(copyNums);
        int left = 0, right = nums.length - 1;
        int[] result = new int[2];
        while (left < right) {
            int sum = copyNums[left] + copyNums[right];
            if (sum == target) {
                result[0] = left;
                result[1] = right;
                break;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (copyNums[result[0]] == nums[i]) {
                result[0] = i;
                break;
            }
        }

        for (int i = nums.length - 1; i >= 0; i--) {
            if (copyNums[result[1]] == nums[i]) {
                result[1] = i;
                break;
            }
        }

        return result;
    }
}
