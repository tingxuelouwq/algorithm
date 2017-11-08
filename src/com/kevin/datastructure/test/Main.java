package com.kevin.datastructure.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	public static void main(String[] args) {
//		int[] a = {3, -2, 3, -3, 0, -2, -2};
//		int[] a = {1, 1, 1, 2};
//		countSort(a, 2);
//		int[] a = {12, 1, 123};
//		radixSort(a, 3);
		int[] arr = {1, 1, 3, 2};
		bucketSort(arr, 1, 3, 2);
		System.out.println(Arrays.toString(arr));
	}

	public static void bucketSort(int[] a, int min, int max, int step) {
		//桶的个数
		int num = (int)Math.ceil((float)(max - min + 1) / step);
		if(num < 1)
			num  = 1;
		//建桶
		List<List<Integer>> buckets = new ArrayList<>();
		for(int i = 0; i < num; i++)
			buckets.add(new ArrayList<>());
		//初始化桶中的元素
		int index;
		for(int i = 0; i < a.length; i++) {
			index = (a[i] - min) / step;
			buckets.get(index).add(a[i]);
		}
		//对桶中的元素进行排序
		for(List<Integer> bucket : buckets)
			insertSort(bucket);
		//copy back
		index = 0;
		for(List<Integer> bucket : buckets)
			for(Integer item : bucket)
				a[index++] = item;
	}

	private static void insertSort(List<Integer> bucket) {
		int tmp, j;
		for(int i = 1; i < bucket.size(); i++) {
			tmp = bucket.get(i);
			for(j = i; j > 0 && tmp < bucket.get(j - 1); j--)
				bucket.set(j, bucket.get(j - 1));
			bucket.set(j, tmp);
		}
	}

	public static void radixSort(int[] a, int d) {
		final int radix = 10;
		int[] count = new int[radix];
		int[] sortArr = new int[a.length];
		int index;
		for(int i = 1; i <= d; i++) {
			for(int j = 0; j < count.length; j++)
				count[j] = 0;

			for(int j = 0; j < a.length; j++)
				count[getDigit(a[j], i)]++;

			for(int j = 1; j < count.length; j++)
				count[j] += count[j - 1];

			for(int j = a.length - 1; j >= 0; j--) {
				index = getDigit(a[j], i);
				count[index]--;
				sortArr[count[index]] = a[j];
			}

			for(int j = 0; j < a.length; j++)
				a[j] = sortArr[j];
		}
	}

	private static int getDigit(int x, int i) {
		int[] a = {1, 1, 10, 100};
		return (x / a[i]) % 10;
	}

	public static void countSort(int[] a, int range) {
		int[] count = new int[range + 1];
		for(int i = 0; i < a.length; i++)
			count[a[i]]++;
		for(int i = 1; i < count.length; i++)
			count[i] += count[i - 1];

		int[] sortArr = new int[a.length];
		for(int i = a.length - 1; i >= 0; i--) {
			count[a[i]]--;
			sortArr[count[a[i]]] = a[i];
		}

		for(int i = 0; i < a.length; i++)
			sortArr[i] = a[i];
	}

	public static int maxSubSeqProduct(int[] a) {
		if (a == null || a.length == 0) {
			throw new RuntimeException("invalid input!");
		}

		int thisMaxProduct = a[0], thisMinProduct = a[0];
		int preMaxProduct;
		int maxProduct = a[0], minProduct = a[0];
		for(int i = 1; i < a.length; i++) {
			preMaxProduct = thisMaxProduct;
			thisMaxProduct = maxOfThree(thisMaxProduct * a[i], thisMinProduct * a[i], a[i]);
			thisMinProduct = minOfThree(preMaxProduct * a[i], thisMinProduct * a[i], a[i]);
			if(thisMaxProduct > maxProduct)
				maxProduct = thisMaxProduct;
			if(thisMinProduct < minProduct)
				minProduct = thisMinProduct;
		}

		return maxProduct;
	}

	private static int minOfThree(int a, int b, int c) {
		return a < b ? a : b < c ? b : c;
	}

	private static int maxOfThree(int a, int b, int c) {
		return a > b ? a : b > c ? b : c;
	}

}

