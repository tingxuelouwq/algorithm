package com.kevin.datastructure.tree;

import com.kevin.datastructure.stack.MyArrayStack;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BinarySearchTree {
	private static class BinaryNode {
		int element;
		BinaryNode left;
		BinaryNode right;
		
		public BinaryNode(int element, BinaryNode left, BinaryNode right) {
			this.element = element;
			this.left = left;
			this.right = right;
		}
	}
	
	private BinaryNode root;

	/**
	 * 前序遍历创建二叉树，以'-1'表示空节点
	 * 6 4 2 3 -1 -1 -1 -1 5 1 -1 -1 7 -1 -1
	 */
	public void createBinaryTreeByPreorder() {
		try(Scanner in = new Scanner(System.in)) {
			root = createBinaryTreeByPreorder(in);
		}
	}

	private BinaryNode createBinaryTreeByPreorder(Scanner in) {
		int value = in.nextInt();
		if(value == -1)
			return null;
		BinaryNode node = new BinaryNode(value, null, null);
		node.left = createBinaryTreeByPreorder(in);
		node.right = createBinaryTreeByPreorder(in);
		return node;
	}

	/**
	 * 中序遍历不能创建二叉树，因为情况不唯一，比如#B#A#D#C#，至少可以找到两种二叉树：
	 * (1)
	 *         A
	 *     B       C
	 *  #    #  D   #
	 *  	  #  #
	 * (2)
	 *           C
	 *         A    #
	 *      B    D
	 *    #   # #  #
	 */

	/**
	 * 后序遍历创建二叉树
	 */

	public void insert(int x) {
		root = insert(x, root);
	}

	private BinaryNode insert(int x, BinaryNode t) {
		if(t == null)
			return new BinaryNode(x, null, null);
		
		int compareResult = x < t.element ? -1 : x == t.element ? 0 : 1;
		if(compareResult < 0)
			t.left = insert(x, t.left);
		else if(compareResult > 0)
			t.right = insert(x, t.right);
		else
			;	//duplicate element, do nothing
		
		return t;
	}
	
	public void remove(int x) {
		root = remove(x, root);
	}

	private BinaryNode remove(int x, BinaryNode t) {
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
		
		return t;
	}

	public BinaryNode findMax(BinaryNode t) {
		if(t != null) 
			while(t.right != null)
				t = t.right;
		
		return t;
	}
	
	public BinaryNode findMin(BinaryNode t) {
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

	private boolean contains(int x, BinaryNode t) {
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
	
	private void preorder(BinaryNode t) {
		if(t != null) {
			System.out.print(t.element + " ");
			preorder(t.left);
			preorder(t.right);
		}
	}
	
	public void preorder2() {
		preorder2(root);
	}
	
	private void preorder2(BinaryNode p) {
		MyArrayStack<BinaryNode> s = new MyArrayStack<>();
		
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

	private void inorder(BinaryNode t) {
		if(t != null) {
			inorder(t.left);
			System.out.print(t.element + " ");
			inorder(t.right);
		}
	}
	
	public void inorder2() {
		inorder2(root);
	}
	
	private void inorder2(BinaryNode p) {
		MyArrayStack<BinaryNode> s = new MyArrayStack<>();
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

	private void postorder(BinaryNode t) {
		if(t != null) {
			postorder(t.left);
			postorder(t.right);
			System.out.print(t.element + " ");
		}
	}
	
	public void postorder2() {
		postorder2(root);
	}

	private void postorder2(BinaryNode p) {
		MyArrayStack<BinaryNode> s = new MyArrayStack<>();
		BinaryNode pre = null;
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

	public void levelorder() {
		if(root == null)
			return;

		Queue<BinaryNode> q = new LinkedList<>();
		q.offer(root);

		while(!q.isEmpty()) {
			BinaryNode node = q.poll();
			System.out.print(node.element + " ");
			if(node.left != null)
				q.offer(node.left);
			if(node.right != null)
				q.offer(node.right);
		}
	}

	public void levelorder2() {
		if(root == null)
			return;
		Link head = new Link(root, null);
		Link first = head;
		Link second = head;

		while(first != null) {
			if(first.node.left != null) {
				second.next = new Link(first.node.left, null);
				second = second.next;
			}
			if(first.node.right != null) {
				second.next = new Link(first.node.right, null);
				second = second.next;
			}
			System.out.print(first.node.element + " ");
			first = first.next;
		}
	}

	private static class Link {
		BinaryNode node;
		Link next;

		Link(BinaryNode node, Link next) {
			this.node = node;
			this.next = next;
		}
	}

	public int height(BinaryNode t) {
		if(t == null)
			return -1;
		int leftHeight = height(t.left);
		int rightHeight = height(t.right);
		return (leftHeight > rightHeight ? leftHeight : rightHeight) + 1;
	}

	/**
	 * 判断一颗二叉树是否是平衡二叉树
	 * @return
	 */
	public boolean isBalanceTree() {
		return isBalanceTree(root);
	}

	private boolean isBalanceTree(BinaryNode t) {
		if(t == null)
			return true;
		int leftHeight = height(t.left);
		int rightHeight = height(t.right);
		int distance = leftHeight > rightHeight ? leftHeight - rightHeight : rightHeight - leftHeight;

		if(distance > 1)
			return false;
		else
			return isBalanceTree(t.left) && isBalanceTree(t.right);
	}

	public static void main(String[] args) {
		BinarySearchTree bst = new BinarySearchTree();
//		int[] a = {4, 5, 7, 1, 2};
//		for(int i = 0; i < a.length; i++)
//			bst.insert(a[i]);
//
//		bst.preorder();
//		System.out.println();
//		bst.inorder();
//		System.out.println();
//		bst.postorder();
//		System.out.println();
//		bst.preorder2();
//		System.out.println();
//		bst.inorder2();
//		System.out.println();
//		bst.postorder2();
		bst.createBinaryTreeByPreorder();
		bst.preorder();
		System.out.println();
		bst.levelorder2();
	}
}
