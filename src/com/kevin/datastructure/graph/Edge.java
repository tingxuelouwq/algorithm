package com.kevin.datastructure.graph;

/**
 * @Author kevin
 * @Date 2016/10/5 20:43
 */
public class Edge implements Comparable<Edge> {
    private Vertex begin;    //边的起始顶点
    private Vertex end;      //边的结束顶点
    private int weight;      //边的权值

    private EdgeType edgeType;  //边的类型

    public Edge(Vertex v, Vertex w, int weight) {
        this.begin = v;
        this.end = w;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge o) {  //实现Comparable接口，供Kruskal算法使用
        return weight < o.weight ? -1 : weight == o.weight ? 0 : 1;
    }

    @Override
    public boolean equals(Object obj) { //重写equals方法，供添加边时使用
        if(this == obj)
            return true;

        if(getClass() != obj.getClass())
            return false;

        Edge edge = (Edge)obj;
        return begin.equals(edge.getBegin()) && end.equals(edge.getEnd());
    }

    //getter and setter
    public Vertex getBegin() {
        return begin;
    }

    public void setBegin(Vertex begin) {
        this.begin = begin;
    }

    public Vertex getEnd() {
        return end;
    }

    public void setEnd(Vertex end) {
        this.end = end;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public EdgeType getEdgeType() {
        return edgeType;
    }

    public void setEdgeType(EdgeType edgeType) {
        this.edgeType = edgeType;
    }
}
