package com.kevin.datastructure.heap;

public class MinHeap {
	private static final int DEFAULT_CAP = 10;
	private int[] array;
	private int currentSize;
	
	public MinHeap() {
		array = new int[DEFAULT_CAP];
		currentSize = 0;
	}

	/**
	 * 通过这种方式建堆的时间复杂度是O(N)，其实就是所有节点的高度和。
	 * 证明：高为h的理想二叉树的所有节点的高度和为(2^(h+1)-1)-(h+1)
	 * 该树由高度h上的1个节点，高度h-1上的2个节点，高度h-2上的2^2个节点...组成，因此所有节点的高度和为：
	 * S=2^i(h-i)求和，其中i取值从0到h，得证
	 * @param items
	 */
	public MinHeap(int[] items) {
		array = items;
		currentSize = items.length;

		for (int i = currentSize / 2; i >= 0; i--)
			percDown(array, i, currentSize);
	}

	public boolean isEmpty() {
		return currentSize == 0;
	}

	public void sort() {
		for(int i = currentSize - 1; i >= 0; i--) {
			swap(array, 0, i);
			percDown(array, 0, i);
		}
	}

	public void insert(int x) {
		if(currentSize == array.length)
			enlargeArray(currentSize * 2);

		percUp(array, currentSize++, x);
	}

	public int findMin() {
		checkEmpty();
		return array[0];
	}

	public int deleteMin() {
		int item = findMin();
		array[0] = array[--currentSize];
		percDown(array, 0, currentSize);
		return item;
	}

	public boolean decreaseKey(int x, int deta) {
		int i = indexOf(x);
		if(i == -1)
			return false;
		else {
			array[i] -= deta;
			percUp(array, i, array[i]);
			return true;
		}
	}

	public boolean increaseKey(int x, int deta) {
		int i = indexOf(x);
		if(i == -1)
			return false;
		else {
			array[i] += deta;
			percDown(array, i, currentSize);
			return true;
		}
	}

	public void delete(int x) {
		decreaseKey(x, Integer.MAX_VALUE);
		deleteMin();
	}

	private int indexOf(int x) {
		for(int i = 0; i < currentSize; i++)
			if(x == array[i])
				return i;
		return -1;
	}

	public void print() {
		for(int i = 0; i < currentSize; i++)
			System.out.print(array[i] + " ");
	}

	private void checkEmpty() {
		if(isEmpty())
			throw new RuntimeException("empty heap!");
	}

	private void checkIndex(int index) {
		if(index < 0 || index >= currentSize)
			throw new ArrayIndexOutOfBoundsException();
	}

	private void swap(int[] array, int i, int i1) {
		int tmp = array[i];
		array[i] = array[i1];
		array[i1] = tmp;
	}

	private void percUp(int[] arr, int i, int x) {
		int parent;
		for(; i > 0; i = parent) {
			parent = (i - 1) / 2;
			if(x < array[parent])
				array[i] = array[parent];
			else
				break;
		}
		array[i] = x;
	}

	private void enlargeArray(int i) {
		int[] oldArr = array;
		array = new int[i];
		System.arraycopy(oldArr, 0, array, 0, oldArr.length);
	}

	private void percDown(int[] arr, int i, int n) {
		int tmp, child;
		for(tmp = array[i]; leftChild(i) < n; i = child) {
			child = leftChild(i);
			if(child != n - 1 && array[child] > array[child + 1])
				child++;
			if(tmp > array[child])
				array[i] = array[child];
			else 
				break;
		}
		array[i] = tmp;
	}

	private int leftChild(int i) {
		return 2 * i + 1;
	}

	public static void main(String[] args) {
		int[] a = {12, 10, 4, 9, 20, 29};
		MinHeap heap = new MinHeap(a);
//		System.out.println(heap.deleteMin());
//		heap.sort();
//		heap.decreaseKey(2, 10);
//		heap.increaseKey(0, 10);
		heap.delete(1);
		heap.print();
	}
}
