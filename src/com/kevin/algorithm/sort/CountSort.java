package com.kevin.algorithm.sort;

public class CountSort {
	public static void main(String[] args) {
		int[] a = {1, 1, 3, 2};
		countSort(a, 3);
		print(a);
	}
	
	private static void print(int[] a) {
		for(int i : a)
			System.out.print(i + " ");
	}

	/**
	 * 
	 * @author:kevin
	 * @date:2016-09-07 20:47:13
	 * @desc:计数排序
	 * 1、建一个长度为k+1的数组C
	 * 2、遍历待排序的数组，计算每一个元素出现的次数，比如元素i出现了3次，则C[i]=3
	 * 3、从0开始遍历C，累加数组C，即C[i+1]=C[i]+C[i-1]
	 * 4、建一个长度为k的临时数组T，从头开始遍历待排序数组，把元素都安排到T里面
	 * 对应关系：原数组中的元素值对应的是该元素在数组C中的下标。累加数组C后，数组C中的元素值-1对应的是该元素在数组T中的下标。
	 * @param a 待排序数组
	 * @param range 待排序数组中的最大元素
	 * @complexity:假设n个记录中每个关键字都介于0到k-1之间，则计数排序的时间复杂度为O(n+k)，空间代价为O(n+k)。
	 * 当k=O(n)时，时间复杂度O(N)，空间复杂度为O(N)
	 */
	public static void countSort(int[] a, int range) {
		int count[] = new int[range + 1];
		for(int i = 0; i < a.length; i++)
			count[a[i]]++;
		for(int i = 1; i < count.length; i++)
			count[i] += count[i - 1];
		int[] sortArr = new int[a.length];
		for(int i = a.length - 1; i >= 0; i--) {	//从后往前扫描，保证稳定性
			count[a[i]]--;
			sortArr[count[a[i]]] = a[i];
		}
		
		for(int i = 0; i < a.length; i++)	//copy back
			a[i] = sortArr[i];
	}
}
