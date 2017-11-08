package com.kevin.datastructure.stack;

import java.util.EmptyStackException;

/**
 * 
 * @author:kevin
 * @date:2016-09-04 16:43:50
 * @desc:用单向链表实现的栈
 * @complexity:
 */
public class MyLinkedStack<E> {
	private static class Node<E> {
		public E data;
		public Node<E> next;
		
		public Node(E data, Node<E> next) {
			this.data = data;
			this.next = next;
		}
	}
	
	private Node<E> head;
	private int size;
	
	public MyLinkedStack() {
		head = null;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public void clear() {
		for(Node<E> p =  head; p != null;) {
			Node<E> x = p.next;
			p.data = null;
			p.next = null;
			p = x;
		}
	}
	
	public E top() {
		if(isEmpty())
			throw new EmptyStackException();
		
		return head.data;
	}
	
	public E peek() {
		if(isEmpty())
			return null;
		
		return head.data;
	}
	
	public void push(E e) {
		if(isEmpty())
			head = new Node<>(e, null);
		else
			head = new Node<>(e, head);
		
		++size;
	}
	
	public E pop() {
		E top = top();
		
		Node<E> peek = head;
		head = head.next;
		peek.data = null;
		peek.next = null;
		--size;
		
		return top;
	}
	
	public static void main(String[] args) {
		MyLinkedStack<Integer> stack = new MyLinkedStack<>();
		stack.push(1);
		stack.push(2);
		System.out.println(stack.top());
	}
}
