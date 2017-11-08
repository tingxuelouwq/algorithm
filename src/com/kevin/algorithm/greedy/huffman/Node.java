package com.kevin.algorithm.greedy.huffman;

/**
 * 存储树节点的类，实现了Comparable接口
 * @Author kevin
 * @Date 2016/10/24 18:34
 */
public class Node implements Comparable<Node> {
    private Data data;
    private Node left;
    private Node right;

    public Node() {

    }

    public Node(Data data) {
        this(data, null, null);
    }

    public Node(Data data, Node left, Node right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(Node o) {
        return data.compareTo(o.data);
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
