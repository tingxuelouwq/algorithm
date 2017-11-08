package com.kevin.algorithm.common;

/**
 * 
 * @author:wangqi25
 * @date:2016-09-04 16:11:21
 * @desc:二分查找
 * @complexity:O(logN)
 */
public class BinarySearch {
	public static void main(String[] args) {
		int[] a = {1, 3, 4, 5, 6, 0};
		int k = -1;
		System.out.println("the index of " + k + " is: " + binarySearch(a, k));
	}
	
	public static int binarySearch(int[] a, int k) {
		checkArray(a);
		
		int left = 0, right = a.length - 1;
		while(left <= right) {
			int mid = left + (right - left) / 2;
			if(a[mid] < k)
				left = mid + 1;
			else if(a[mid] > k)
				right =  mid - 1;
			else
				return mid;
		}
		
		return -1;
	}

	private static void checkArray(int[] a) {
		if(a == null || a.length == 0)
			throw new RuntimeException("invalid input!");
	}
}
