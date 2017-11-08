package com.kevin.datastructure.graph;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author kevin
 * @Date 2016/10/5 20:33
 */
public class Vertex implements Comparable<Vertex> {
    private static final int INFINITY = Integer.MAX_VALUE;
    private String data;            //顶点信息
    private List<Edge> adjList;    //顶点的邻接表

    //求最短路径需要用到的一些字段
    private Color color;     //顶点是否被遍历过
    private int dist;        //路径长度
    private int indegree;    //顶点的入度
    private int outdegree;   //顶点的出度
    private Vertex path;     //前驱节点

    //深度优先遍历时需要用到的一些字段
    private int prenum;     //先序遍历编号
    private int low;
    private int postnum;    //后序遍历编号

    //寻找图中所有环时需要用到的字段
    private boolean isBackVex;  //是否是背向边的关联顶点

    public Vertex(String data) {
        this(data, new LinkedList<>());
    }

    public Vertex(String data, List<Edge> adjList) {
        this.data = data;
        this.adjList = adjList;
        this.color = Color.white;
        this.dist = INFINITY;
    }

    @Override
    public int compareTo(Vertex o) {    //实现Comparable接口，供Prime算法和Dijkstra算法使用
        return dist < o.dist ? -1 : dist == o.dist ? 0 : 1;
    }

    @Override
    public boolean equals(Object obj) { //重写equals方法，供添加顶点时使用
        if(this == obj)
            return true;

        if(getClass() != obj.getClass())
            return false;

        Vertex v = (Vertex)obj;
        return data.equals(v.getData());
    }

    //getter and setter
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Edge> getAdjList() {
        return adjList;
    }

    public void setAdjList(List<Edge> adjList) {
        this.adjList = adjList;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public int getIndegree() {
        return indegree;
    }

    public void setIndegree(int indegree) {
        this.indegree = indegree;
    }

    public int getOutdegree() {
        return outdegree;
    }

    public void setOutdegree(int outdegree) {
        this.outdegree = outdegree;
    }

    public Vertex getPath() {
        return path;
    }

    public void setPath(Vertex path) {
        this.path = path;
    }

    public int getPrenum() {
        return prenum;
    }

    public void setPrenum(int prenum) {
        this.prenum = prenum;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public int getPostnum() {
        return postnum;
    }

    public void setPostnum(int postnum) {
        this.postnum = postnum;
    }

    public boolean isBackVex() {
        return isBackVex;
    }

    public void setBackVex(boolean backVex) {
        isBackVex = backVex;
    }
}
