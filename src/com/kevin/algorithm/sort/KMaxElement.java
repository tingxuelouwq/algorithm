package com.kevin.algorithm.sort;

import com.kevin.datastructure.heap.MaxHeap;

/**
 * 
 * @author:kevin
 * @date:2016-09-10 19:11:41
 * @desc:找到给定数组中第k小的元素
 *
 * 解法一：假设数据量不大，可以先用快速排序或堆排序，它们的平均时间复杂度为O(NlogN)
 * 解法二：假设数据量不大，使用快排变种，平均时间复杂度为O(N)。
 * 粗略证明：假设每次扔掉一半，则T(N)=n+n/2+n/4+n/8+...+n/2^k=O(n)
 * 解法三：把前k个元素读入数组，构建二叉堆，接着逐个对比剩下的元素，将符合的元素插入k数组，调整k数组。构建二叉堆的时间复杂度为O(k)；
 * 检测元素是否要插入到k数组中，用时O(1)；在必要时，删除k数组中的第k小元素并插入新元素，用时O(logk)，总用时：
 * O(k+(N-k)*logk)=O(Nlogk)
 */
public class KMaxElement {
	public static void main(String[] args) {
		int[] a = {7, 3, 4, 8, 0};
		int k = 2;
		quickSelect(a, k);
		System.out.println(a[k - 1]);

		System.out.println(heapSelect(a, k));
	}

	public static int heapSelect(int[] a, int k) {
		int[] karr = new int[k];
		System.arraycopy(a, 0, karr, 0, k);
		MaxHeap maxHeap = new MaxHeap(karr);

		int kmin;
		for(int i = k; i < a.length; i++) {
			kmin = maxHeap.findMax();
			if(a[i] < kmin) {
				maxHeap.deleteMax();
				maxHeap.insert(a[i]);
			}
		}

		return maxHeap.findMax();
	}

	//使用快排变种
	public static void quickSelect(int[] a, int k) {
		quickSelect(a, 0, a.length - 1, k);
	}

	private static void quickSelect(int[] a, int left, int right, int k) {
		if(left < right) {
			int i = left, j = right, privot = a[i];
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
			
			if(k < i + 1)
				quickSelect(a, left, i - 1, k);
			else if(k > i + 1)
				quickSelect(a, i + 1, right, k);
		}
	}
}
