package com.kevin.algorithm.recursive;

/**
 * 
 * @author:wangqi25
 * @date:2016-09-02 20:28:59
 * @desc:最大子序列和
 * @complexity:O(NlogN)
 */
public class MaxSubSeqSum {
	public static void main(String[] args) {
		int[] a = {4, -3, 5, -2, -1, 2, 6, -2};
		System.out.println("max sub sequence sum is: " + maxSubSeqSum(a));
	}
	
	public static int maxSubSeqSum(int[] a) {
		checkArray(a);
		return maxSubSeqSum(a, 0, a.length - 1);
	}

	private static int maxSubSeqSum(int[] a, int left, int right) {
		if(left  == right)
			if(a[left] > 0)
				return a[left];
			else
				return 0;
		
		int mid = left + (right - left) / 2;
		int leftMaxSum = maxSubSeqSum(a, left, mid);
		int rightMaxSum = maxSubSeqSum(a, mid + 1, right);
		
		int leftBorderMaxSum = 0, leftBorderSum = 0;
		for(int i = mid; i >= left; i--) {
			leftBorderSum += a[i];
			if(leftBorderSum > leftBorderMaxSum)
				leftBorderMaxSum = leftBorderSum;
		}
		
		int rightBorderMaxSum = 0, rightBorderSum = 0;
		for(int i = mid + 1; i <= right; i++) {
			rightBorderSum += a[i];
			if(rightBorderSum > rightBorderMaxSum)
				rightBorderMaxSum = rightBorderSum;
		}
		
		return maxOfThree(leftMaxSum, rightMaxSum, leftBorderMaxSum + rightBorderMaxSum);
	}

	private static int maxOfThree(int a, int b, int c) {
        return a > b ? a > c ? a : c : b > c ? b : c;
	}

	private static void checkArray(int[] a) {
		if(a == null || a.length == 0)
			throw new RuntimeException("invalid input!");
	}
}
