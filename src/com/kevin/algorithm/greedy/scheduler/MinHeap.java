package com.kevin.algorithm.greedy.scheduler;

/**
 * @Author kevin
 * @Date 2016/10/21 21:03
 */
public class MinHeap<E extends Comparable<? super E>> {
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    private static final int MAXIMUM_CAPACITY = 1 << 30;

    private E[] arr;
    private int size;

    public MinHeap(int initialCapacity) {
        if(initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        if(initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;

        arr = (E[]) (new Comparable[initialCapacity]);
        size = 0;
    }

    public MinHeap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public MinHeap(E[] items) {
        arr = items;
        size = items.length;

        buildHeap();
    }

    /**
     * Build the min heap.
     */
    private void buildHeap() {
        for(int i = arr.length / 2; i >= 0; i--)
            percDown(arr, i, arr.length);
    }

    /**
     * Percolate down the element at the specified position in the array of specified length.
     * @param a the specified array
     * @param i the specified position of the element to be percolated down
     * @param n the length of the array
     */
    private void percDown(E[] a, int i, int n) {
        E tmp;
        int child;
        for(tmp = a[i]; leftChild(i) < n; i = child) {
            child = leftChild(i);
            if(child != n - 1 && a[child].compareTo(a[child + 1]) > 0)
                child++;
            if(tmp.compareTo(a[child]) > 0)
                a[i] = a[child];
            else
                break;
        }
        a[i] = tmp;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    public void insert(E x) {
        if(size == arr.length)
            resize(arr.length * 2);
        percUp(arr, size++, x);
    }

    /**
     * Percolate up the specified element at the specified position in the array
     * @param a the specified array
     * @param i the specified position of the element to be percolated up
     * @param x the element to be percolated up
     */
    private void percUp(E[] a, int i, E x) {
        int parent;
        for(; i > 0; i = parent) {
            parent = (i - 1) / 2;
            if(a[parent].compareTo(x) > 0)
                a[i] = a[parent];
            else
                break;
        }
        a[i] = x;
    }

    private void resize(int newCapacity) {
        if(newCapacity == MAXIMUM_CAPACITY)
            throw new RuntimeException("Reached maximum capcity: " + MAXIMUM_CAPACITY);

        E[] oldArr = arr;
        arr = (E[]) (new Comparable[newCapacity]);
        for(int i = 0; i < oldArr.length; i++)
            arr[i] = oldArr[i];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E findMin() {
        if(isEmpty())
            throw new RuntimeException("Empty heap!");
        return arr[0];
    }

    public E removeMin() {
        E item = findMin();
        arr[0] = arr[--size];
        percDown(arr, 0, size);
        return item;
    }
}
