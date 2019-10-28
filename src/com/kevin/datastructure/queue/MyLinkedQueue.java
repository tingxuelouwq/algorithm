package com.kevin.datastructure.queue;

/**
 * 
 * @author:wangqi25
 * @date:2016-09-04 21:16:48
 * @desc:使用单向链表实现队列
 * @complexity:
 */
public class MyLinkedQueue<E> {
	private static class Node<E> {
		public E data;
		public Node<E> next;
		public Node(E data, Node<E> next) {
			this.data = data;
			this.next = next;
		}
	}
	
	private Node<E> first;
	private Node<E> last;
	private int size;
	
	public MyLinkedQueue() {
		size = 0;
		first = last = null;
	}
	
	public boolean isEmpty() {
		return first == null && last == null;
	}
	
	public int size() {
		return size;
	}
	
	public void clear() {
		for(Node<E> p = first; p != null;) {
			Node<E> x = p.next;
			p.data = null;
			p.next = null;
			p = x;
		}
		size = 0;
	}
	
	public void offer(E e) {
		Node<E> item = new Node<>(e, null);
		
		if(isEmpty())
			first = last = item;
		else
			last.next = item;
			
		++size;
	}
	
	public E poll() {
		if(isEmpty())
			return null;
		
		Node<E> f = first;
		Node<E> next = f.next;
		E data = f.data;
		f.data = null;
		f.next = null;
		first = next;
		if(first == null)
			last = first;
		
		size--;		
		return data;
	}
	
	public static void main(String[] args) {
		MyLinkedQueue<Integer> queue = new MyLinkedQueue<>();
		queue.offer(1);
		System.out.println(queue.poll());
	}
}
