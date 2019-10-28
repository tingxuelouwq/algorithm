package com.kevin.datastructure.queue;

/**
 * 
 * @author:wangqi25
 * @date:2016-09-04 21:07:55
 * @desc:用数组实现循环队列
 * @complexity:
 */
public class MyArrayQueue<E> {
	private static final int DEFAULT_CAP = 10;
	private E[] array;
	private int size;
	private int first;
	private int last;
	
	@SuppressWarnings("unchecked")
	public MyArrayQueue() {
		array = (E[]) new Object[DEFAULT_CAP];
		size = 0;
		first = last = 0;
	}
	
	@SuppressWarnings("unchecked")
	public MyArrayQueue(int i) {
		if(i <= DEFAULT_CAP)
			array = (E[]) new Object[DEFAULT_CAP];
		else
			array = (E[]) new Object[i];
		
		size = 0;
		first = last = 0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public boolean isFull() {
		return size() == array.length;
	}
	
	public void clear() {
        for (int i = first; i != last;) {
            array[i] = null;
            i = (i + 1) % array.length;
        }
        array[last] = null;
        first = last = 0;
        size = 0;
	}
	
	public boolean offer(E e) {
		if(isFull())
			return false;
		
		last = (first + size) % array.length;
		array[last] = e;
		++size;
		return true;
	}
	
	public E poll() {
		if(isEmpty())
			return null;
		
		E item = array[first];
		array[first] = null;
		first = (first + 1) % array.length;
		--size;
		
		return item;
	}
	
	public static void main(String[] args) {
		MyArrayQueue<Integer> queue = new MyArrayQueue<>();
		queue.offer(1);
		System.out.println(queue.poll());
	}
}
