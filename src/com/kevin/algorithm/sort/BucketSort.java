package com.kevin.algorithm.sort;

import java.util.ArrayList;
import java.util.List;

public class BucketSort {
	public static void main(String[] args) {
		int[] arr = {1, 1, 3, 2};
		asc(arr, 2);
		print(arr);
	}
	
	private static void print(int[] a) {
		for(int i : a)
			System.out.print(i + " ");
	}
	
	public static void asc(int[] arr, int step) {
		int min = arr[0], max = arr[0];
		for(int i = 1; i < arr.length; i++) {
			if(arr[i] < min)
				min = arr[i];
			if(arr[i] > max)
				max = arr[i];
		}
		bucketSort(arr, min, max, step);
	}
	
	/**
	 * 
	 * @author:kevin
	 * @date:2016-09-10 10:33:47
	 * @desc:桶排序
	 * 例如要对大小为[1...1000]范围内的n个整数A[1...n]排序，可以把桶设为大小为10的范围。具体而言，设集合B[1]存储[1...10]的
	 * 整数，集合B[2]存储[11...20]的整数，...，总共100个桶。然后对A[1...n]扫描一遍，把每个A[i]放入对应的桶B[i]中。再对这100
	 * 个桶中每个桶里的数字进行排序，此时可使用冒泡、选择、快排等排序算法。最后依次输出每个桶里的数字，这样就得到一个排好序
	 * 的序列了。
	 * @param arr 待排序的数组
	 * @param min 数组中的最小数字
	 * @param max 数组中的最大数字
	 * @param step 步长，即桶的大小
	 * @complexity:时间复杂度O(M+N)，其中M是桶的的个数，取决于数组中的最大元素以及步长；空间复杂度O(M+N)
	 */
	public static void bucketSort(int[] arr, int min, int max, int step) {
		List<List<Integer>> list = new ArrayList<>();
		
		//计算桶个数
		int num = (int)Math.ceil((float)(max - min + 1) / step);
		if(num < 1)
			num = 1;
		
		//建桶
		for(int i = 0; i < num; i++) {
			List<Integer> bucket = new ArrayList<>();
			list.add(bucket);
		}
		
		//将数据放到对应的桶中
		for(int i = 0; i < arr.length; i++) {
			int index = (arr[i] - min) / step;
			list.get(index).add(arr[i]);
		}
		
		//对每个桶内的数据进行排序
		for(List<Integer> bucket : list)
			insertSort(bucket);
		
		//copy back
		int index = 0;
		for(List<Integer> bucket : list)
			for(Integer item : bucket)
				arr[index++] = item;
	}

	private static void insertSort(List<Integer> bucket) {
		int j, tmp;
		for(int i = 1; i < bucket.size(); i++) {
			tmp = bucket.get(i);
			for(j = i; j > 0 && tmp < bucket.get(j - 1); j--)
				bucket.set(j, bucket.get(j - 1));
			bucket.set(j, tmp);
		}
	}
}
