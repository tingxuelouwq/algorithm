package com.kevin.datastructure.tree;

/**
 * @Author kevin
 * @Date 2016/9/20 11:21
 */
public class SplayTree<E extends Comparable<? super E>> {
    private BinaryNode<E> root;
    private BinaryNode<E> header = new BinaryNode<E>(null);

    public boolean isEmpty() {
        return root == null;
    }

    public void makeEmpty() {
        root = null;
    }

    public boolean contains(E x) {
        if(isEmpty())
            return false;

        root = splay(x, root);
        return root.element.compareTo(x) == 0;
    }

    public void remove(E x) {
        if(!contains(x))
            return;

        BinaryNode<E> newRoot = root;
        if(root.left == null) {
            newRoot = root.right;
            root.right = null;
        } else {
            newRoot = root.left;
            newRoot = splay(x, newRoot);
            newRoot.right = root.right;
            root.left = root.right = null;
        }
        root = newRoot;
    }

    public void insert(E x) {
        BinaryNode<E> newNode = new BinaryNode<E>(x);

        if(root == null) {
            root = newNode;
        } else {
            root = splay(x, root);
            int compareResult = x.compareTo(root.element);
            if(compareResult < 0) {
                newNode.left = root.left;
                newNode.right = root;
                root.left = null;
                root = newNode;
            } else if(compareResult > 0) {
                newNode.right = root.right;
                newNode.left = root;
                root.right = null;
                root = newNode;
            } else
                return; // no duplicates
        }
    }

    public E findMin() {
        if(isEmpty())
            throw new RuntimeException("Empty splay tree.");

        BinaryNode<E> p = root;
        while(p.left != null)
            p = p.left;

        root = splay(p.element, root);
        return p.element;
    }

    public E findMax() {
        if(isEmpty())
            throw new RuntimeException("Empty splay tree.");

        BinaryNode<E> p = root;
        while(p.right != null)
            p = p.right;

        root = splay(p.element, root);
        return p.element;
    }

    private BinaryNode<E> splay(E x, BinaryNode<E> t) {
        BinaryNode<E> leftTreeMax, rightTreeMin;

        header.left = header.right = null;
        leftTreeMax = rightTreeMin = header;

        for(;;) {
            if(x.compareTo(t.element) < 0) {
                if(t.left == null)
                    break;
                if(x.compareTo(t.left.element) < 0)
                    t = rotateWithLeftChild(t);
                if(t.left == null)
                    break;
                rightTreeMin.left = t;
                rightTreeMin = t;
                t = t.left;
            } else if(x.compareTo(t.element) > 0) {
                if(t.right == null)
                    break;
                if(x.compareTo(t.right.element) > 0)
                    t = rotateWithRightChild(t);
                if(t.right == null)
                    break;
                leftTreeMax.right = t;
                leftTreeMax = t;
                t = t.right;
            } else
                break;
        }
        leftTreeMax.right = t.left;
        rightTreeMin.left = t.right;
        t.left = header.right;
        t.right = header.left;
        return t;
    }

    private BinaryNode<E> rotateWithRightChild(BinaryNode<E> k2) {
        BinaryNode<E> k1 = k2.right;
        k2.right = k1.left;
        k1.left = k2;
        return k1;
    }

    private BinaryNode<E> rotateWithLeftChild(BinaryNode<E> k2) {
        BinaryNode<E> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        return k1;
    }

    private static class BinaryNode<E> {
        E element;
        BinaryNode<E> left;
        BinaryNode<E> right;

        BinaryNode(E element) {
            this(element, null, null);
        }

        BinaryNode(E element, BinaryNode<E> left, BinaryNode<E> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }

    public void preorder() {
        preorder(root);
    }

    private void preorder(BinaryNode<E> t) {
        if(t != null) {
            System.out.print(t.element + " ");
            preorder(t.left);
            preorder(t.right);
        }
    }

    public static void main(String[] args) {
        SplayTree splayTree = new SplayTree();
        for(int i = 1; i <= 7; i++)
            splayTree.insert(i);

        splayTree.remove(1);
        splayTree.preorder();
    }
}
