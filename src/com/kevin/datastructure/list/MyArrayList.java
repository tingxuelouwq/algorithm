package com.kevin.datastructure.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<E> implements Iterable<E>{
    private static final int DEFAULT_CAP = 10;
    private E[] theItems;
    private int currentSize;
    private int modCount;

    public MyArrayList() {
        this(DEFAULT_CAP);
    }

    @SuppressWarnings("unchecked")
    public MyArrayList(int i) {
        currentSize = 0;
        modCount = 0;

        if(i <= DEFAULT_CAP)
            theItems = (E[]) new Object[DEFAULT_CAP];
        else
            theItems = (E[]) new Object[i];
    }

    public int size() {
        return currentSize;
    }

    public void clear() {
        modCount++;

        for(int i = 0; i < currentSize; i++)
            theItems[i] = null;

        currentSize = 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(E item) {
        if (item == null) {
            for (int i = 0; i < currentSize; i++) {
                if (theItems[i] == null) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < currentSize; i++) {
                if (theItems[i].equals(item))
                    return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public void ensureCapacity(int cap) {
        if(cap <= DEFAULT_CAP)
            return;

        E[] oldItems = theItems;
        theItems = (E[]) new Object[cap];
        for(int i = 0; i < size(); i++)
            theItems[i] = oldItems[i];
    }

    public E get(int index) {
        rangeCheck(index);

        return theItems[index];
    }

    public E set(int index, E item) {
        rangeCheck(index);

        E oldItem = theItems[index];
        theItems[index] = item;
        return oldItem;
    }

    public void add(E item) {
        add(size(), item);
    }

    public void add(int index, E item) {
        rangeCheckForAdd(index);

        if(currentSize == theItems.length)
            ensureCapacity(currentSize * 2);

        for(int i = currentSize; i > index; i--)
            theItems[i] = theItems[i - 1];
        theItems[index] = item;

        currentSize++;
        modCount++;
    }

    public E remove(int index) {
        rangeCheck(index);

        E removedItem = theItems[index];
        for(int i = index; i < currentSize - 1; i++)
            theItems[i] = theItems[i + 1];
        theItems[--currentSize] = null;
        modCount++;

        return removedItem;
    }

    public boolean remove(Object o) {
        if(o == null) {
            for(int i = 0; i < currentSize; i++)
                if(theItems[i] == null) {
                    remove(i);
                    return true;
                }
        } else {
            for(int i = 0; i < currentSize; i++)
                if(o.equals(theItems[i])) {
                    remove(i);
                    return true;
                }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    private void rangeCheck(int index) {
        if(index >= currentSize || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private void rangeCheckForAdd(int index) {
        if(index > currentSize ||index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + currentSize;
    }

    private class MyIterator implements Iterator<E> {
        private int current;
        private int expectedModCount;
        private boolean okToRemove;

        public MyIterator() {
            current = 0;
            expectedModCount = modCount;
            okToRemove = false;
        }

        @Override
        public boolean hasNext() {
            return current < size();
        }

        @Override
        public E next() {
            if(expectedModCount != modCount)
                throw new ConcurrentModificationException();
            if(!hasNext())
                throw new NoSuchElementException();

            E item = theItems[current];
            current++;
            okToRemove = true;
            return item;
        }

        public void remove() {
            if(expectedModCount != modCount)
                throw new ConcurrentModificationException();
            if(!okToRemove)
                throw new IllegalStateException();

            MyArrayList.this.remove(--current);
            expectedModCount++;
            okToRemove = false;
        }
    }

    public static void main(String[] args) {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("tom");
        list.add("kevin");
        list.add("tom");
        list.add(null);
        for(String item : list)
            System.out.print(item + " ");

        System.out.println();
        System.out.println(list.contains(null));
        System.out.println(list.remove(null));


    }
}