package com.kevin.datastructure.graph;

import java.util.*;

/**
 * @Author kevin
 * @Date 2016/10/6 10:54
 */
public class CriticalPath {
    private Graph graph;    //图
    private int[] ve;       //存储事件最早发生时间
    private int[] vl;       //存储事件最晚发生时间
    Stack<Vertex> stack;    //存储拓扑排序得到的序列，从而进行拓扑逆序排序

    public CriticalPath(Graph graph) {
        this.graph = graph;
        this.ve = new int[graph.getVexNum()];
        this.vl = new int[graph.getVexNum()];
        stack = new Stack<>();

        for(int i = 0; i < graph.getVexNum(); i++)
            vl[i] = Integer.MAX_VALUE;
    }

    /**
     * 拓扑排序，得到ve
     */
    public void topsort() {
        Queue<Vertex> q = new LinkedList<>();
        int counter = 0;

        graph.setIndegrees();
        for(Vertex v : graph.getVertexList())
            if(v.getIndegree() == 0)
                q.offer(v);

        while (!q.isEmpty()) {
            Vertex v = q.poll();
            counter++;
            stack.push(v);
            int i = graph.indexOf(v);

            for (Edge e : v.getAdjList()) {
                Vertex w = e.getEnd();
                int j = graph.indexOf(w);
                int weight = e.getWeight();
                w.setIndegree(w.getIndegree() - 1);
                if(w.getIndegree() == 0)
                    q.offer(w);
                if(ve[i] + weight > ve[j])  //ve[j] = max(ve[i]+w(i,j))，其中vi是vj的前驱节点
                    ve[j] = ve[i] + weight;
            }
        }

        if(counter != graph.getVexNum())
            throw new RuntimeException("Found Circle!");
    }

    /**
     * 拓扑逆序，得到vl
     */
    public void reverseTopsort() {
        topsort();  //拓扑排序得到ve

        Vertex t = stack.pop();
        int index = graph.indexOf(t);
        vl[index] = ve[index];  //初始化宿点的最晚开始时间

        while (!stack.isEmpty()) {
            Vertex v = stack.pop();
            int i = graph.indexOf(v);
            for (Edge e : v.getAdjList()) {
                Vertex w = e.getEnd();
                int j = graph.indexOf(w);
                int weight = e.getWeight();
                if(vl[j] - weight < vl[i])
                    vl[i] = vl[j] - weight;
            }
        }
    }

    public List<Pair> getCriticalPath() {
        reverseTopsort();   //拓扑逆序得到vl

        List<Pair> result = new ArrayList<>();
        for (Vertex v : graph.getVertexList()) {
            int i = graph.indexOf(v);
            for (Edge e : v.getAdjList()) {
                Vertex w = e.getEnd();
                int weight = e.getWeight();
                int j = graph.indexOf(w);
                int ee = ve[i]; //活动的最早开始时间
                int el = vl[j] - weight;    //活动的最晚开始时间
                if(ee == el)    //关键路径
                    result.add(new Pair(v, w, weight));
            }
        }

        return result;
    }

    protected static class Pair {
        Vertex first;
        Vertex second;
        int weight;

        public Pair(Vertex first, Vertex second, int weight) {
            this.first = first;
            this.second = second;
            this.weight = weight;
        }
    }
}
