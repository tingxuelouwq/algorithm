package com.kevin.algorithm.sort;

public class Main {
	public static void main(String[] args) {
		int[] a = {5, 3, 6, 1, 8, 9};
		heapSort(a);
		print(a);
	}
	
	private static void print(int[] a) {
		for(int i = 0; i < a.length; i++)
			System.out.print(" " + a[i]);
	}
	
	/**
	 * 
	 * @author:kevin
	 * @date:2016-09-10 18:45:43
	 * @desc:
	 * @param a
	 * @complexity:O(N^1.3)
	 */
	public static void shellSort(int[] a) {
		int j, tmp;
		for(int gap = a.length / 2; gap >= 1; gap /= 2)
			for(int i = gap; i < a.length; i++) {
				tmp = a[i];
				for(j = i; j >= gap && tmp < a[j - gap]; j -= gap)
					a[j] = a[j - gap];
				a[j] = tmp;
			}
	}
	
	/**
	 * 
	 * @author:kevin
	 * @date:2016-09-10 18:37:35
	 * @desc:
	 * @param a
	 * @complexity:O(N^2)
	 */
	public static void selectSort(int[] a) {
		int minIndex = 0;
		int tmp;
		for(int i = 0; i < a.length - 1; i++) { 
			minIndex = i;
			for(int j = i + 1; j < a.length; j++)
				if(a[j] < a[minIndex])
					minIndex = j;
			if(minIndex != i) {
				tmp = a[i];
				a[i] = a[minIndex];
				a[minIndex] = tmp;
			}
		}
	}
	
	/**
	 * 
	 * @author:kevin
	 * @date:2016-09-10 18:33:08
	 * @desc:
	 * @param a
	 * @complexity:O(N^2)
	 */
	public static void insertSort(int[] a) {
		int j, tmp;
		for(int i = 1; i < a.length; i++) {
			tmp = a[i];
			for(j = i; j > 0 && tmp < a[j - 1]; j--)
				a[j] = a[j - 1];
			a[j] = tmp;
		}
	}

	public static void bubbleSort(int[] a) {
		for(int i = 0; i < a.length - 1; i++)
			for(int j = 0; j < a.length - 1 - i; j++) {
				if(a[j] > a[j + 1])
					swap(a, j, j + 1);
			}
	}

	private static void swap(int[] a, int i, int j) {
		int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
	
	/**
	 * 
	 * @author:kevin
	 * @date:2016-09-10 19:09:56
	 * @desc:
	 * @param a
	 * @timecomplexity:O(NlogN)
	 * @spacecomplexity:
	 */
	public static void heapSort(int[] a) {
		for(int i = a.length / 2; i >= 0; i--)	//构建最大堆
			percDown(a, i, a.length);
		for(int i = a.length - 1; i > 0; i--) {
			swap(a, 0, i);
			percDown(a, 0, i);
		}
	}

	private static void percDown(int[] a, int i, int n) {
		int tmp, child;
		for(tmp = a[i]; leftChild(i) < n; i = child) {
			child = leftChild(i);
			if(child != n - 1 && a[child] < a[child + 1])
				child++;
			if(tmp < a[child])
				a[i] = a[child];
			else
				break;
		}
		a[i] = tmp;
	}

	private static int leftChild(int i) {
		return 2 * i + 1;
	}

	/**
	 * 
	 * @author:kevin
	 * @date:2016-09-10 18:48:22
	 * @desc:
	 * @param a
	 * @timecomplexity:O(NlogN)
	 * @spacecomplexity:O(logN)
	 */
	public static void quickSort(int[] a) {
		if(a == null)
			throw new RuntimeException("invalid input!");
		
		quickSort(a, 0, a.length - 1);
	}

	private static void quickSort(int[] a, int left, int right) {
		if(left < right) {
			int i = left, j = right;
			int privot = a[i];
			
			while(i < j) {
				while(i < j && a[j] > privot)
					--j;
				if(i < j)
					a[i++] = a[j];
				while(i < j && a[i] < privot)
					++i;
				if(i < j)
					a[j--] = a[i];
			}
			a[i] = privot;
			
			quickSort(a, left, i - 1);
			quickSort(a, i + 1, right);
		}	
	}
	
	/**
	 * 
	 * @author:kevin
	 * @date:2016-09-10 18:48:51
	 * @desc:
	 * @param a
	 * @timecomplexity:O(NlogN)
	 * @spacecomplexity:O(N)
	 */
	public static void mergeSort(int[] a) {
		if(a == null)
			throw new RuntimeException("invalid input!");
		
		int[] tmp = new int[a.length];
		mergeSort(a, tmp, 0, a.length - 1);
	}

	private static void mergeSort(int[] a, int[] tmp, int left, int right) {
		if(left < right) {
			int mid = left + (right - left ) / 2;
			mergeSort(a, tmp, left, mid);
			mergeSort(a, tmp, mid + 1, right);
			merge(a, tmp, left, mid + 1, right);
		}
	}

	private static void merge(int[] a, int[] tmp, int leftPos, int rightPos, int rightEnd) {
		int leftEnd = rightPos - 1;
		int numsOfElements = rightEnd - leftPos + 1;
		int tmpPos = leftPos;
		
		while(leftPos <= leftEnd && rightPos <= rightEnd) {
			if(a[leftPos] <= a[rightPos])
				tmp[tmpPos++] = a[leftPos++];
			else
				tmp[tmpPos++] = a[rightPos++];
		}
		
		while(leftPos <= leftEnd)
			tmp[tmpPos++] = a[leftPos++];
		while(rightPos <= rightEnd)
			tmp[tmpPos++] = a[rightPos++];
		
		for(int i = 0; i < numsOfElements; i++, rightEnd--) {
			a[rightEnd] = tmp[rightEnd];
		}
	}
}
