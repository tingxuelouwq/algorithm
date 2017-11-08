package com.kevin.datastructure.stack;

import java.util.EmptyStackException;

/**
 * 
 * @author:wangqi25
 * @date:2016-09-04 16:29:09
 * @desc:用数组实现的栈
 * @complexity:
 */
public class MyArrayStack<E> {
	private static final int DEFAULT_CAP = 10;
	private E[] array;
	private int size;
	private int topStack;	//栈顶指针
	
	@SuppressWarnings("unchecked")
	public MyArrayStack() {
		array = (E[]) new Object[DEFAULT_CAP];
		size = 0;
		topStack = -1;
	}
	
	@SuppressWarnings("unchecked")
	public MyArrayStack(int i) {
		size = 0;
		topStack = -1;
		
		if(i <= DEFAULT_CAP)
			array = (E[]) new Object[DEFAULT_CAP];
		else
			array = (E[]) new Object[i];
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public void clear() {
		for(int i = 0; i < size; i++)
			array[i] = null;
		
		size = 0;
		topStack = -1;
	}
	
	@SuppressWarnings("unchecked")
	public void ensureCapacity(int cap) {
		if(cap <= DEFAULT_CAP)
			return;
		
		E[] oldArray = array;
		array = (E[]) new Object[cap];
		for(int i = 0; i < size; i++)
			array[i] = oldArray[i];
	}
	
	public E top() {
		if(isEmpty())
			throw new EmptyStackException();
		
		return array[topStack];
	}
	
	public E peek() {
		if(isEmpty())
			return null;
		
		return array[topStack];
	}
	
	public void push(E e) {
		if(size == array.length)
			ensureCapacity(array.length >> 1);
		
		array[++topStack] = e;
		++size;
	}
	
	public E pop() {
		E top = top();
		array[topStack--] = null;
		--size;
		return top;
	}
	
	public static void main(String[] args) {
		MyArrayStack<Integer> stack = new MyArrayStack<>();
		stack.push(1);
		stack.push(2);
		
		Integer top = stack.pop();
		System.out.println(top);
	}
}
