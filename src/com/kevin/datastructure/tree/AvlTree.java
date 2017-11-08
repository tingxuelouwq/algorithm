package com.kevin.datastructure.tree;

import com.kevin.datastructure.stack.MyArrayStack;

public class AvlTree {
	private static class AvlNode {
		int element;
		AvlNode left;
		AvlNode right;
		int height = 0;
		
		public AvlNode(int element, AvlNode left, AvlNode right) {
			this.element = element;
			this.left = left;
			this.right = right;
		}
	}
	
	private AvlNode root;
	
	public int height(AvlNode t) {
		return t == null ? -1 : t.height;
	}
	
	public void insert(int x) {
		root = insert(x, root);
	}

	private AvlNode insert(int x, AvlNode t) {
		if(t == null)
			return new AvlNode(x, null, null);
		
		int compareResult = x < t.element ? -1 : x == t.element ? 0 : 1;
		if(compareResult < 0)
			t.left = insert(x, t.left);
		else if(compareResult > 0)
			t.right = insert(x, t.right);
		else
			;	//duplicate element, do nothing
		
		return balance(t);
	}
	
	public void remove(int x) {
		root = remove(x, root);
	}

	private AvlNode remove(int x, AvlNode t) {
		if(t == null)
			return null;
		
		int compareResult = x < t.element ? -1 : x == t.element ? 0 : 1;
		if(compareResult < 0)
			t.left = remove(x, t.left);
		else if(compareResult > 0)
			t.right = remove(x, t.right);
		else if(t.left != null && t.right != null) {
			t.element = findMax(t.right).element;
			t.right = remove(t.element, t.right);
		} else
			t = t.left != null ? t.left : t.right;
		
		return balance(t);
	}
	
	public AvlNode findMax(AvlNode t) {
		if(t != null) 
			while(t.right != null)
				t = t.right;
		
		return t;
	}
	
	public AvlNode findMin(AvlNode t) {
		if(t == null)
			return null;
		else if(t.left == null)
			return t;
		else
			return findMin(t.left);
	}
	
	public boolean contains(int x) {
		return contains(x, root);
	}

	private boolean contains(int x, AvlNode t) {
		if(t == null)
			return false;
		
		int compareResult = x < t.element ? -1 : x == t.element ? 0 : 1;
		if(compareResult < 0)
			return contains(x, t.left);
		else if(compareResult > 0)
			return contains(x, t.right);
		else
			return true;
	}
	
	public boolean isEmpty() {
		return root == null;
	}
	
	public void makeEmpty() {
		root = null;
	}
	
	public void preorder() {
		preorder(root);
	}
	
	private void preorder(AvlNode t) {
		if(t != null) {
			System.out.print(t.element + " ");
			preorder(t.left);
			preorder(t.right);
		}
	}
	
	public void preorder2() {
		preorder2(root);
	}
	
	private void preorder2(AvlNode p) {
		MyArrayStack<AvlNode> s = new MyArrayStack<>();
		
		while(!s.isEmpty() || p != null) {
			while(p != null) {
				System.out.print(p.element + " ");
				s.push(p);
				p = p.left;
			}
			p = s.pop();
			p = p.right;
		}
	}

	public void inorder() {
		inorder(root);
	}

	private void inorder(AvlNode t) {
		if(t != null) {
			inorder(t.left);
			System.out.print(t.element + " ");
			inorder(t.right);
		}
	}
	
	public void inorder2() {
		inorder2(root);
	}
	
	private void inorder2(AvlNode p) {
		MyArrayStack<AvlNode> s = new MyArrayStack<>();
		while(!s.isEmpty() || p != null) {
			while(p != null) {
				s.push(p);
				p = p.left;
			}
			p = s.pop();
			System.out.print(p.element + " ");
			p = p.right;
		}
	}

	public void postorder() {
		postorder(root);
	}

	private void postorder(AvlNode t) {
		if(t != null) {
			postorder(t.left);
			postorder(t.right);
			System.out.print(t.element + " ");
		}
	}
	
	public void postorder2() {
		postorder2(root);
	}

	private void postorder2(AvlNode p) {
		MyArrayStack<AvlNode> s = new MyArrayStack<>();
		AvlNode pre = null;
		while(!s.isEmpty() || p != null) {
			while(p != null) {
				s.push(p);
				p = p.left;
			}
			p = s.peek();
			if(p.right == null || p.right == pre) {
				p = s.pop();
				System.out.print(p.element + " ");
				pre = p;
				p = null;
			} else
				p = p.right;
		}
	}

	private static final int ALLOWED_IMBALANCE = 1;
	private AvlNode balance(AvlNode t) {
		if(t == null)
			return t;
		
		if(height(t.left) - height(t.right) > ALLOWED_IMBALANCE) {
			if(height(t.left.left) >= height(t.left.right))
				t = rotateWithLeftChild(t);
			else
				t = doubleRotateWithLeft(t);
		} else if(height(t.right) - height(t.left) > ALLOWED_IMBALANCE) {
			if(height(t.right.right) >= height(t.right.left))
				t = rotateWithRightChild(t);
			else
				t = doubleRotateWithRight(t);
		}
		
		t.height = Math.max(height(t.left), height(t.right)) + 1;
		return t;
	}
	
	private AvlNode rotateWithLeftChild(AvlNode k2) {
		AvlNode k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max(height(k1.left), k2.height) + 1;
		return k1;
	}
	
	private AvlNode rotateWithRightChild(AvlNode k2) {
		AvlNode k1 = k2.right;
		k2.right = k1.left;
		k1.left = k2;
		k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max(k2.height, height(k1.right)) + 1;
		return k1;
	}
	
	private AvlNode doubleRotateWithLeft(AvlNode k3) {
		k3.left = rotateWithRightChild(k3.left);
		return rotateWithLeftChild(k3);
	}
	
	private AvlNode doubleRotateWithRight(AvlNode k3) {
		k3.right = rotateWithLeftChild(k3.right);
		return rotateWithRightChild(k3);
	}

	public static void main(String[] args) {
		AvlTree avl = new AvlTree();
		int[] a = {4, 5, 7, 1, 2};
		for(int i = 0; i < a.length; i++)
			avl.insert(a[i]);
		
		avl.preorder();
		System.out.println();
		avl.inorder();
		System.out.println();
		avl.postorder();
		System.out.println();
		avl.preorder2();
		System.out.println();
		avl.inorder2();
		System.out.println();
		avl.postorder2();
	}
}
