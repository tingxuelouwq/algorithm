package com.kevin.algorithm.branchandbound;

/**
 * @Author kevin
 * @Date 2017/1/1 16:02
 */
public class MaxHeap<E extends Comparable<? super E>> {
    private static final int DEFAULT_CAPACITY = 10;
    private E[] array;
    private int size;

    public MaxHeap() {
        this(DEFAULT_CAPACITY);
    }

    public MaxHeap(int i) {
        if (i <= DEFAULT_CAPACITY) {
            i = DEFAULT_CAPACITY;
        }
        array = (E[]) (new Comparable[i]);
        size = 0;
    }

    public MaxHeap(E[] items) {
        array = items;
        size = items.length;
        for (int i = array.length / 2; i >= 0; i--) {
            percDown(array, i, array.length);
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(E item) {
        if (size == array.length) {
            ensureCapacity(array.length * 2);
        }
        array[size] = item;
        percUp(array, size++, item);
    }

    public E delete() {
        if (!isEmpty()) {
            E item = array[0];
            size--;
            swap(array, 0, size);
            percDown(array, 0, size);
            return item;
        }
        return null;
    }

    private void swap(E[] a, int i, int j) {
        E tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    /**
     *
     * @param a 要进行上滤操作的数组
     * @param i 对a[i]进行上滤操作
     * @param item 即a[i]
     */
    private void percUp(E[] a, int i, E item) {
        int parent;
        for (; i > 0; i = parent) {
            parent = (i - 1) / 2;
            if (a[parent].compareTo(item) < 0) {
                a[i] = a[parent];
            } else {
                break;
            }
        }
        a[i] = item;
    }

    private void ensureCapacity(int newCap) {
        if (newCap <= DEFAULT_CAPACITY) {
            newCap = DEFAULT_CAPACITY;
        }
        E[] oldArray = array;
        array = (E[]) (new Comparable[newCap]);
        for (int i = 0; i < oldArray.length; i++) {
            array[i] = oldArray[i];
        }
    }

    /**
     *
     * @param a 要进行下滤操作的数组
     * @param i 对a[i]进行下滤操作
     * @param n 数组a的长度
     */
    private void percDown(E[] a, int i, int n) {
        int child;
        E tmp;
        for (tmp = a[i]; leftChild(i) < n; i = child) {
            child = leftChild(i);
            if (child != n - 1 && a[child].compareTo(a[child + 1]) < 0) {
                child++;
            }
            if (a[child].compareTo(tmp) > 0) {
                a[i] = a[child];
            } else {
                break;
            }
        }
        a[i] = tmp;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    public static void main(String[] args) {
        Integer[] a = {5, 3, 8, 2, 10};
        MaxHeap<Integer> maxHeap = new MaxHeap<>(a);
        System.out.println(maxHeap.delete());
    }
}
