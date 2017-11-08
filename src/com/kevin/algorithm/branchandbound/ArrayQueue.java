package com.kevin.algorithm.branchandbound;

/**
 * @Author kevin
 * @Date 2016/12/30 21:08
 */
public class ArrayQueue<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private E[] array;
    private int size;
    private int front;
    private int rear;

    public ArrayQueue() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayQueue(int i) {
        if (i <= DEFAULT_CAPACITY) {
            i = DEFAULT_CAPACITY;
        }
        array = (E[]) new Object[i];
        size = 0;
        front = rear = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == array.length;
    }

    public void offer(E item) {
        if (isFull()) {
            throw new RuntimeException("Queue is full!");
        }
        rear = (front + size) % array.length;
        array[rear] = item;
        size++;
    }

    public E poll() {
        if (isEmpty()) {
            return null;
        }
        E item = array[front];
        array[front] = null;
        front = (front + 1) % array.length;
        size--;
        return item;
    }

    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue();
        arrayQueue.offer(2);
        arrayQueue.offer(6);
        System.out.println(arrayQueue.poll());
    }
}
