package com.kevin.datastructure.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<E> implements Iterable<E> {
	private static class Node<E> {
		private E data;
		private Node<E> prev;
		private Node<E> next;
		
		public Node(E data, Node<E> prev, Node<E> next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}
	
	private Node<E> beginMarker;
	private Node<E> endMarker;
	private int currentSize;
	private int modCount;
	
	public MyLinkedList() {
		beginMarker = new Node<>(null, null, null);
		endMarker = new Node<>(null, beginMarker, null);
		beginMarker.next = endMarker;
		
		currentSize = 0;
		modCount = 0;
	}
	
	public int size() {
		return currentSize;
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}

    public void clear() {
        for (Node<E> p = beginMarker.next; p != endMarker; p = p.next) {
            p.data = null;
            p.prev = null;
            p.next = null;
        }

        beginMarker.next = endMarker;
        endMarker.prev = beginMarker;
        currentSize = 0;
        modCount++;
    }
	
	public E getFirst() {
		Node<E> first = beginMarker.next;
		if(first == endMarker)
			throw new NoSuchElementException();
		
		return first.data;
	}
	
	public E getLast() {
		Node<E> last = endMarker.prev;
		if(last == beginMarker)
			throw new NoSuchElementException();
		
		return last.data;
	}
	
	public void addFirst(E e) {
		linkBefore(beginMarker.next, e);
	}
	
	public void addLast(E e) {
		linkBefore(endMarker, e);
	}
	
	public E removeFirst() {
		Node<E> first = beginMarker.next;
		if(first == endMarker)
			throw new NoSuchElementException();
		
		return unlink(first);
	}
	
	public E removeLast() {
		Node<E> last = endMarker.prev;
		if(last == beginMarker)
			throw new NoSuchElementException();
		
		return unlink(last);
	}

	public E get(int index) {
        checkElementIndex(index);
		return getNode(index).data;
	}
	
	public E set(int index, E e) {
        checkElementIndex(index);
		
		Node<E> item = getNode(index);
		E oldValue = item.data;
		item.data = e;
		return oldValue;
	}
	
	public void add(E e) {
		add(size(), e);
	}
	
	public void add(int index, E e) {
		checkPositionIndex(index);
		linkBefore(getNode(index), e);
	}
	
	public E remove(int index) {
        checkElementIndex(index);
		return unlink(getNode(index));
	}
	
	public boolean remove(Object o) {
		if(o == null) {
			for(Node<E> p = beginMarker.next; p != endMarker; p = p.next) {
				if(p.data == null) {
					unlink(p);
					return true;
				}
			}
		} else {
			for(Node<E> p = beginMarker.next; p != endMarker; p = p.next) {
				if(o.equals(p.data)) {
					unlink(p);
					return true;
				}
			}
		}
		return false;
	}
	
	private E unlink(Node<E> node) {
		E removedValue = node.data;
		
		node.prev.next = node.next;
		node.next.prev = node.prev;		
		currentSize--;
		modCount++;
		
		node.prev = null;
		node.next = null;
		node.data = null;
		
		return removedValue;
	}

	private void linkBefore(Node<E> node, E e) {
		Node<E> item = new Node<E>(e, node.prev, node);
		node.prev.next = item;
		node.prev = item;
		
		currentSize++;
		modCount++;
	}

	private Node<E> getNode(int index) {
		if(index < currentSize / 2) {
			Node<E> p = beginMarker.next;
			for(int i = 0; i < index; i++)
				p = p.next;
			return p;
		} else {
			Node<E> p = endMarker;
			for(int i = currentSize; i > index; i--)
				p = p.prev;
			return p;
		}
	}

	private void checkPositionIndex(int index) {
		if(index > currentSize || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	private void checkElementIndex(int index) {
		if(index >= currentSize || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	private String outOfBoundsMsg(int index) {
		return "Index: " + index + ", Size: " + currentSize;
	}

	@Override
	public Iterator<E> iterator() {
		return new MyIterator();
	}

	private class MyIterator implements Iterator<E> {
		private Node<E> current;
		private int expectedModCount;
		private boolean okToRemove;
		
		public MyIterator() {
			current = beginMarker.next;
			expectedModCount = modCount;
			okToRemove = false;
		}
		
		@Override
		public boolean hasNext() {
			return current != endMarker;
		}

		@Override
		public E next() {
			if(expectedModCount != modCount)
				throw new ConcurrentModificationException();
			if(!hasNext())
				throw new NoSuchElementException();
			
			Node<E> item = current;
			current = current.next;
			okToRemove = true;
			
			return item.data;
		}
		
		public void remove() {
			if(expectedModCount != modCount)
				throw new ConcurrentModificationException();
			if(!okToRemove)
				throw new IllegalStateException();
			
			MyLinkedList.this.unlink(current.prev);
			expectedModCount++;
			okToRemove = false;
		}
	}
	
	public static void main(String[] args) {
		 MyLinkedList<String> list = new MyLinkedList<>();
		 list.add("tom");
		 list.add("kevin");
		 list.add("tom");
		 list.add(null);
		 
		 list.removeFirst();
		 list.removeLast();
		 
		 for(String item : list)
			 System.out.print(item + " ");
		 
		 System.out.println();
		 System.out.println(list.remove(null));
	}
}
