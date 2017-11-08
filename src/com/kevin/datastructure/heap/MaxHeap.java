package com.kevin.datastructure.heap;

/**
 * @Author kevin
 * @Date 2016/9/24 17:00
 */
public class MaxHeap {
    private static final int DEFAULT_CAP = 10;
    private int[] array;
    private int currentSize;

    public MaxHeap() {
        array = new int[DEFAULT_CAP];
        currentSize = 0;
    }

    public MaxHeap(int[] items) {
        array = items;
        currentSize = items.length;

        for(int i = currentSize / 2; i >= 0; i--)
            percDown(array, i, currentSize);
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public void sort() {
        for(int i = currentSize - 1; i > 0; i--) {
            swap(array, 0, i);
            percDown(array, 0, i);
        }
    }

    public void insert(int x) {
        if(currentSize == array.length)
            enlargeArray(currentSize * 2);

        percUp(array, currentSize++, x);
    }

    public int findMax() {
        checkEmpty();
        return array[0];
    }

    public int deleteMax() {
        int item = findMax();
        swap(array, 0, --currentSize);
        percDown(array, 0, currentSize);
        return item;
    }

    public boolean decreaseKey(int x, int deta) {
        int i = indexOf(x);
        if(i == -1)
            return false;
        else {
            array[i] -= deta;
            percDown(array, i, currentSize);
            return true;
        }
    }

    public boolean increaseKey(int x, int deta) {
        int i = indexOf(x);
        if(i == -1)
            return false;
        else {
            array[i] += deta;
            percUp(array, i, array[i]);
            return true;
        }
    }

    public void delete(int x) {
        increaseKey(x, Integer.MAX_VALUE);
        deleteMax();
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

    /**
     * 上滤
     * @param array 数组
     * @param i 待上滤的元素在数组中的下标
     * @param x 待上滤的元素
     */
    private void percUp(int[] array, int i, int x) {
        int parent;
        for(; i > 0; i = parent) {
            parent = (i - 1) / 2;
            if(x > array[parent])
                array[parent] = array[i];
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

    /**
     * 下滤
     * @param array 数组
     * @param i 待下滤的元素下标
     * @param n 数组长度
     */
    private void percDown(int[] array, int i, int n) {
        int tmp, child;
        for(tmp = array[i]; leftChild(i) < n; i = child) {
            child = leftChild(i);
            if(child != n - 1 && array[child] < array[child + 1])
                child++;
            if(tmp < array[child])
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
        MaxHeap heap = new MaxHeap(a);
//		System.out.println(heap.deleteMax());
//		heap.sort();
		heap.decreaseKey(2, 10);
		heap.increaseKey(0, 10);
//        heap.delete(1);
        heap.print();
    }
}
