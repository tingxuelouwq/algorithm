package com.kevin.datastructure.graph;

import com.kevin.datastructure.disjoint.DisjointSet;

import java.util.*;

/**
 * @Author kevin
 * @Date 2016/10/5 20:47
 */
public class Graph {
    private static int preCounter = 1;      //先序遍历编号需要用到的字段
    private static int postCounter = 1;     //后序遍历编号需要用到的字段
    private static int circleUG = 0;        //判断无向图是否有圈需要用到的字段
    private static int circleDG = 0;        //判断有向图是否有圈需要用到的字段

    private List<Vertex> vertexList;        //顶点列表
    private List<Edge> edgeList;            //边列表

    public Graph() {
        vertexList = new ArrayList<>();
        edgeList = new ArrayList<>();
    }

    /**
     * getter and setter
     */
    public List<Vertex> getVertexList() {
        return vertexList;
    }

    public void setVertexList(List<Vertex> vertexList) {
        this.vertexList = vertexList;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    /**
     * 添加顶点，不处理重复顶点
     */
    public boolean addVertex(Vertex v) {
        if(!vertexList.contains(v))  //reject duplicate vertex
            vertexList.add(v);

        return true;
    }

    /**
     * 添加边，如果是重复边，则更新权值
     */
    public boolean addEdge(Vertex v, Vertex w) {
       return addEdge(v, w, 1);
    }

    public boolean addEdge(Vertex v, Vertex w, int weight) {
        Edge edge = new Edge(v, w, weight);
        List<Edge> adjList = v.getAdjList();

        int index = edgeList.indexOf(edge);

        if(index == -1) {   //非重复边
            adjList.add(edge);
            edgeList.add(edge);
        } else {    //重复边，更新权值
            edgeList.get(index).setWeight(weight);
        }

        return true;
    }

    /**
     * 获取各顶点的入度
     */
    public void setIndegrees() {
        Vertex w;
        for(Vertex v : vertexList)
            for(Edge e : v.getAdjList()) {
                w = e.getEnd();
                w.setIndegree(w.getIndegree() + 1);
            }
    }

    /**
     * 获取各顶点的出度
     */
    public void getOutdegrees() {
        for(Vertex v : vertexList)
            v.setOutdegree(v.getAdjList().size());
    }

    /**
     * 拓扑排序，时间复杂度O(|E|+|V|)
     * (1)将入度为0的顶点入队
     * (2)从队列中出一个顶点，删除该顶点及其关联的边，重复(1)
     */
    public List<Vertex> topsort() {
        List<Vertex> result = new ArrayList<>();
        Queue<Vertex> q = new LinkedList<>();
        int counter = 0;

        setIndegrees();
        for(Vertex v : vertexList)
            if(v.getIndegree() == 0)
                q.offer(v);

        while (!q.isEmpty()) {
            Vertex v = q.poll();
            result.add(v);
            counter++;
            for (Edge e : v.getAdjList()) {
                Vertex w = e.getEnd();
                w.setIndegree(w.getIndegree() - 1);
                if(w.getIndegree() == 0)
                    q.offer(w);
            }
        }

        if(counter != vertexList.size())
            throw new RuntimeException("Found Circle!");

        return result;
    }

    /**
     * 打印路径
     */
    public void printPath(Vertex t) {
        if(t.getPath() != null) {
            printPath(t.getPath());
            System.out.print("->");
        }
        System.out.print(t.getData());
    }

    /**
     * 无权图最短路径算法：广度优先遍历，时间复杂度O(|E|+|V|)
     */
    public void shortestPathAlgorithmUnweighted(Vertex s) {
        Queue<Vertex> q = new LinkedList<>();
        s.setDist(0);
        s.setColor(Color.gray); //将起始顶点标记为已访问并入队
        q.offer(s);

        while (!q.isEmpty()) {
            Vertex v = q.poll();    //从队列中取出一个顶点
            for (Edge e : v.getAdjList()) {    //遍历该顶点的所有邻接顶点
                Vertex w = e.getEnd();
                if(w.getColor() == Color.white) {   //如果邻接顶点没有被访问过，则将其标记为已访问并入队，同时记录前驱节点
                    w.setDist(v.getDist() + 1);
                    w.setColor(Color.gray);
                    w.setPath(v);
                    q.offer(w);
                }
            }
        }
    }

    /**
     * 有权图的最短路径算法：Dijkstra算法。
     * 时间复杂度分析：
     * 对于稀疏图，由于|E|=O|V|，如果使用优先队列，则时间复杂度为：O(|E|log|V|+|V|log|V|)=O(|V|log|V|)
     * 对于稠密图，由于|E|=O|V|^2，时间复杂度为：O(|E|+|V|^2)=O(|V|^2)
     */
    public void shortestPathAlgorithmDijkstra(Vertex s) {
        s.setDist(0);
        PriorityQueue<Vertex> q = new PriorityQueue<>(vertexList);

        while(!q.isEmpty()) {
            Vertex v = q.poll();
            v.setColor(Color.gray);
            for(Edge e : v.getAdjList()) {
                Vertex w = e.getEnd();
                if(w.getColor() == Color.white)
                    if(v.getDist() + e.getWeight() < w.getDist()) {
                        w.setDist(v.getDist() + e.getWeight());
                        w.setPath(v);
                        q.remove(w);
                        q.offer(w);
                    }
            }
        }
    }

    /**
     * 有权负边图的最短路径算法：时间复杂度O(|E||V|)
     */
    public void shortestPathAlgorithmWeightedNegative(Vertex s) {
        Queue<Vertex> q = new LinkedList<>();

        s.setDist(0);
        q.offer(s);
        s.setColor(Color.gray); //用来标记顶点是否在队列中

        while(!q.isEmpty()) {
            Vertex v = q.poll();
            v.setColor(Color.white);

            for (Edge e : v.getAdjList()) {
                Vertex w = e.getEnd();
                if (v.getDist() + e.getWeight() < w.getDist()) {
                    w.setDist(v.getDist() + e.getWeight());
                    w.setPath(v);
                    if(w.getColor() == Color.white) {   //如果顶点w不在队列中，则将其入队
                        q.offer(w);
                        w.setColor(Color.gray);
                    }
                }
            }
        }
    }

    /**
     * 获取顶点在顶点列表中的位置
     */
    public int indexOf(Vertex v) {
        for(int i = 0; i < vertexList.size(); i++)
            if(vertexList.get(i).equals(v))
                return i;
        return -1;
    }

    /**
     * 获取图中顶点个数
     */
    public int getVexNum() {
        return vertexList.size();
    }

    /**
     * 最小生成树：Prime算法，时间复杂度O(|E|log|V|)
     */
    public void minimumSpanningTreePrime(Vertex s) {
        s.setDist(0);
        PriorityQueue<Vertex> q = new PriorityQueue<>(vertexList);

        while (!q.isEmpty()) {
            Vertex v = q.poll();
            v.setColor(Color.gray);

            for (Edge e : v.getAdjList()) {
                Vertex w = e.getEnd();

                if(w.getColor() == Color.white)
                    if (w.getDist() > e.getWeight()) {
                        w.setDist(e.getWeight());
                        w.setPath(v);
                        q.remove(w);
                        q.offer(w);
                    }
            }
        }
    }

    /**
     * 最小生成树：Kruskal算法，时间复杂度O(|E|log|E|)
     */
    public List<Edge> minimunSpanningTreeKruskal() {
        List<Edge> result = new ArrayList<>();
        int edgeAccepts = 0;
        DisjointSet ds = new DisjointSet(getVexNum());
        PriorityQueue<Edge> q = new PriorityQueue<>(getOneSideEdgesFromUG());

        Edge e;
        int beginIndex, endIndex;
        int root1, root2;

        while (edgeAccepts < getVexNum() - 1) { //最小生成树的边数
            e = q.poll();
            beginIndex = indexOf(e.getBegin());
            endIndex = indexOf(e.getEnd());
            root1 = ds.find(beginIndex);
            root2 = ds.find(endIndex);

            if (root1 != root2) {
                edgeAccepts++;
                result.add(e);
                ds.union(root1, root2);
            }
        }

        return result;
    }

    /**
     * 获取无向图的所有边(单边)
     */
    public List<Edge> getOneSideEdgesFromUG() {
        List<Edge> edges = new ArrayList<>();

        int beginIndex, endIndex;

        for(Vertex v : vertexList)
            for (Edge e : v.getAdjList()) {
                beginIndex = indexOf(e.getBegin());
                endIndex = indexOf(e.getEnd());

                if(beginIndex < endIndex)
                    edges.add(e);
            }

        return edges;
    }

    /**
     * 无向图的深度优先遍历：时间复杂度O(|E|+|V|)
     */
    public void dfsUG() {
        for(Vertex v : vertexList)
            if(v.getColor() == Color.white)
                dfsUG(v);
    }

    private void dfsUG(Vertex v) {
        v.setColor(Color.gray);

        for (Edge e : v.getAdjList()) {
            Vertex w = e.getEnd();

            if (w.getColor() == Color.white) {
                w.setPath(v);
                dfsUG(w);
            }
        }
    }

    /**
     * 在无向图中寻找割点：时间复杂度O(|E|+|V|)
     * (1)计算num(v)，先序遍历编号
     * (2)计算low(v)，low(v)是从顶点v出发，最多只用一条背向边，沿树边能够走到的最小num(w)对应的顶点，即：
     * i.num(v)                          //rule 1
     * ii.所有背向边中最小的num(w)         //rule 2
     * iii.所有边中最小的low(w)            //rule 3
     * 中的最小者。
     * 其中第一个条件是不选取边；第二个条件是选取一条背向边；第三个条件是选择树边以及可能还有一条背向边，即递归的计算v的所有儿子的
     * low(w)值，取最小的low(w)即为low(v)
     * (3)在dfs树中，
     * 根：有至少2个儿子，则为割点                               //case 1
     * 非根：当且仅当有某个儿子w，使得low(w)>=num(v)，则为割点    //case 2
     */
    public void findCutPoint(Vertex v) {
        //假设图是连通图
        int children = 0;   //记录儿子数，用于判断根节点是否是割点
        v.setColor(Color.gray);
        v.setPrenum(preCounter++);    //rule 1
        v.setLow(v.getPrenum());

        for (Edge e : v.getAdjList()) {
            Vertex w = e.getEnd();

            if (w.getColor() == Color.white) {  // tree edge
                children++;
                w.setPath(v);
                findCutPoint(w);
                if(v.getPath() == null && children > 1) //case 1
                    System.out.println(v.getData() + " is a cut point");
                if(v.getPath() != null && w.getLow() >= v.getPrenum()) //case 2
                    System.out.println(v.getData() + " is a cut point");
                v.setLow(Math.min(v.getLow(), w.getLow())); //rule 3
            } else if (v.getPath() != w) {  // back edge
                v.setLow(Math.min(v.getLow(), w.getPrenum())); //rule 2
            }
        }
    }

    /**
     * 求无向图的欧拉路径：使用fleury算法，时间复杂度为：O(|E|+|V|)
     */
    public List<Vertex> eulerPath(Vertex s) {
        setIndegrees(); //初始化入度

        int num = 0;        //奇数度顶点的个数

        for(Vertex v : vertexList)
            if((v.getIndegree() & 1) == 1) {
                ++num;  //更新奇数度顶点的个数
                s = v;  //更新欧拉路径的起点
            }

        if(num == 0 || num == 2)
            return fleury(s);
        else
            throw new RuntimeException("There is no euler path!");
    }

    private List<Vertex> fleury(Vertex s) {
        List<Vertex> result = new ArrayList<>();    //存放欧拉路径
        Stack<Vertex> stack = new Stack<>();        //存放已找到的路径
        List<Edge> adj = null;  //存放顶点的邻接表

        stack.push(s);

        while (!stack.isEmpty()) {
            adj = stack.peek().getAdjList();

            if(adj.size() != 0)    //如果邻接表存在，说明该顶点可以扩展，则dfs该顶点
                dfs(stack.pop(), stack);
            else    //如果邻接表不存在，说明该顶点不能扩展，则将其放入到result中
                result.add(stack.pop());
        }

        return result;
    }

    private void dfs(Vertex v, Stack<Vertex> stack) {
        stack.push(v);

        List<Edge> vEdges = v.getAdjList();
        if (vEdges.size() > 0) {   //删除关联边
            Vertex w = vEdges.get(0).getEnd();
            vEdges.remove(vEdges.get(0));

            List<Edge> wEdges = w.getAdjList();
            for(Edge edge : wEdges)
                if(edge.getEnd().equals(v)) {
                    wEdges.remove(edge);
                    break;
                }

            dfs(w, stack);
        }
    }

    /**
     * 有向图的深度优先遍历：dfs算法，时间复杂度：O(|E|+|V|)
     */
    public void dfsDG() {
        for(Vertex v : vertexList)
            if(v.getColor() == Color.white)
                dfsDG(v);
    }

    private void dfsDG(Vertex v) {
        v.setColor(Color.gray);

        for (Edge e : v.getAdjList()) {
            Vertex w = e.getEnd();

            if (w.getColor() == Color.white) {
                w.setPath(v);
                dfsDG(w);
            }
        }

        v.setColor(Color.black);
    }

    /**
     * 获取有向图的强连通分支：Kosaraju算法
     * (1)对原图G进行一次DFS，得到DFS的后序遍历编号
     * (2)从编号最高的顶点开始，对逆图Gr进行一次DFS，得到强连通分支
     * 时间复杂度：稀疏图O(|E|+|V|)，稠密图O(|V|^2)
     */
    public List<List<Vertex>> strongConnectedComponent() {
        List<List<Vertex>> results = new ArrayList<>();
        Stack<Vertex> stack = new Stack<>();

        for(Vertex v : vertexList)  //对原图进行一次DFS，得到后序遍历编号
            if(v.getColor() == Color.white)
                dfsPostorder(v, stack);

        Graph rGraph = getReverseGraph();   //得到转置图Gr

        while (!stack.isEmpty()) {
            Vertex v = rGraph.vertexAt(indexOf(stack.pop()));
            if (v.getColor() == Color.white) {
                List<Vertex> result = new ArrayList<>();
                rGraph.dfsReverseGraph(v, result);
                results.add(result);
            }
        }

        return results;
    }

    /**
     * 对有向图进行深度优先遍历，得到后序遍历编号
     */
    private void dfsPostorder(Vertex v, Stack<Vertex> stack) {
        v.setColor(Color.gray);

        for (Edge e : v.getAdjList()) {
            Vertex w = e.getEnd();
            if(w.getColor() == Color.white)
                dfsPostorder(w, stack);
        }

        v.setColor(Color.black);
        stack.push(v);
    }

    /**
     * 得到转置图(即将边的方向反转)
     */
    private Graph getReverseGraph() {
        Graph rGraph = new Graph();

        for(Vertex v : vertexList)
            rGraph.addVertex(new Vertex(v.getData()));

        for(Vertex v : vertexList)
            for (Edge e : v.getAdjList()) {
                Vertex w = e.getEnd();
                rGraph.addEdge(rGraph.vertexAt(indexOf(w)),
                        rGraph.vertexAt(indexOf(v)),
                        e.getWeight());
            }

        return rGraph;
    }

    private Vertex vertexAt(int i) {
        return vertexList.get(i);
    }

    /**
     * 对转置图进行深度优先遍历，得到强连通分支
     */
    private void dfsReverseGraph(Vertex v, List<Vertex> result) {
        result.add(v);
        v.setColor(Color.gray);

        for (Edge e : v.getAdjList()) {
            Vertex w = e.getEnd();
            if(w.getColor() == Color.white)
                dfsReverseGraph(w, result);
        }

        v.setColor(Color.black);
    }

    /**
     * 标记有向图中每条边的类型(树边、后向边、前向边、交叉边)，时间复杂度：同DFS
     * 在DFS过程中，对于一条边(u,v)
     * (1)如果v.color == white，即v未被遍历，则uv是树边                                  //case 1
     * (2)如果v.color == gray，即v被遍历，但其后代未被遍历完，则uv是后向边                 //case 2
     * (3)如果v.color == black，即v被遍历，且其后代被遍历完，则uv或者是前向边，或者是交叉边    //case 3
     *
     * 判定交叉边：如果边(u,v)是交叉边，则u的先序遍历编号和后序遍历标号均大于v的，证明：
     * 因为u.prenum > v.prenum，因此v不是u的后代；
     * 因为u.postnum > v.postnum，因此u不是v的后代；
     * 因此u和v属于不同树，是交叉边
     */
    public void markEdgeType() {
        for(Vertex v : vertexList)
            if(v.getColor() == Color.white)
                markEdgeType1(v);

        Vertex u, v;
        for (Edge e : edgeList) { //处理交叉边和前向边
            if (e.getEdgeType() == null) {
                u = e.getBegin();
                v = e.getEnd();

                if(u.getPrenum() > v.getPrenum() && u.getPostnum() > v.getPostnum())
                    e.setEdgeType(EdgeType.crossEdge);
                else
                    e.setEdgeType(EdgeType.forwardEdge);
            }
        }
    }

    private void markEdgeType1(Vertex v) {
        v.setColor(Color.gray);
        v.setPrenum(preCounter++);  //记录顶点的先序遍历编号

        for (Edge e : v.getAdjList()) {
            Vertex w = e.getEnd();

            if(w.getColor() == Color.white) {   //case 1 -- tree edge
                e.setEdgeType(EdgeType.treeEdge);
                w.setPath(v);
                markEdgeType1(w);
            } else if(w.getColor() == Color.gray) //case 2 -- back edge
                e.setEdgeType(EdgeType.backEdge);
            //case 3 -- forward edge or cross edge
        }

        v.setColor(Color.black);
        v.setPostnum(postCounter++);    //记录顶点的后序遍历编号
    }

    /**
     * 使用拓扑排序的变形判断无向图是否有圈，时间复杂度：O(|E|+|V|)
     */
    public boolean hasCircleUGTopsortDistortion() {
        if(edgeList.size() / 2 >= vertexList.size())    //如果|E|>=|V|，一定存在圈。证明：如果没有圈，
                                                        // 则该图必然是k棵树(k>=1)，则|E|=|V|-k
            return true;

        Queue<Vertex> q = new LinkedList<>();
        int inc = 0;

        setIndegrees(); //初始化各顶点的入度

        for(Vertex v : vertexList)  //将入度<=1的顶点入队
            if(v.getIndegree() <= 1)
                q.offer(v);

        while (!q.isEmpty()) {
            Vertex v = q.poll();
            inc++;

            for(Edge e : v.getAdjList()) {  //遍历邻接顶点
                Vertex w = e.getEnd();
                w.setIndegree(w.getIndegree() - 1); //将邻接顶点的入度减1

                if(w.getIndegree() == 1)    //如果邻接顶点的入度==1，则将邻接顶点入队
                    q.offer(w);
            }
        }

        return inc != getVexNum();
    }

    /**
     * 使用深度优先遍历判断无向图是否有圈，时间复杂度：同DFS
     */
    public boolean hasCircleUGDfs() {
        for(Vertex v : vertexList)
            if(v.getColor() == Color.white)
                hasCircleUGDfs(v);

        return circleUG != 0;
    }

    private void hasCircleUGDfs(Vertex v) {
        v.setColor(Color.gray);

        for (Edge e : v.getAdjList()) {
            Vertex w = e.getEnd();

            if(w.getColor() == Color.white) {   //树边
                w.setPath(v);
                hasCircleUGDfs(w);
            } else if(v.getPath() != w) //后向边，说明有圈(无向图的DFS树只包含树边和后向边)
                circleUG++;
        }
    }

    /**
     * 使用拓扑排序判断有向图是否有圈：时间复杂度：同拓扑排序
     */
    public boolean hasCircleDGTopsort() {
        setIndegrees();

        Queue<Vertex> q = new LinkedList<>();
        int num = 0;

        for(Vertex v : vertexList)
            if(v.getIndegree() == 0)
                q.offer(v);

        while (!q.isEmpty()) {
            Vertex v = q.poll();
            num++;

            for(Edge e : v.getAdjList()) {
                Vertex w = e.getEnd();
                w.setIndegree(w.getIndegree() - 1);

                if(w.getIndegree() == 0)
                    q.offer(w);
            }
        }

        return num != getVexNum();
    }

    /**
     * 使用深度优先遍历判断有向图是否有圈：如果有背向边，则说明有圈，时间复杂度：同DFS
     */
    public boolean hasCircleDGDfs() {
        for(Vertex v : vertexList)
            if(v.getColor() == Color.white)
                hasCircleDGDfs(v);

        return circleDG != 0;
    }

    private void hasCircleDGDfs(Vertex v) {
        v.setColor(Color.gray);

        for(Edge e : v.getAdjList()) {
            Vertex w = e.getEnd();

            if(w.getColor() == Color.white) {   //tree edge
                w.setPath(v);
                hasCircleDGDfs(w);
            } else if(w.getColor() == Color.gray)   //back edge
                circleDG++;
        }

        v.setColor(Color.black);
    }

    /**
     * 使用查找强连通分支判断有向图是否有圈，时间复杂度：同Korasaju算法
     */
    public boolean hasCircleDGStrongConnectedComponent() {
        List<List<Vertex>> results = strongConnectedComponent();

        int count = 0;
        for(List<Vertex> result : results)
            if(result.size() >= 2)
                count++;

        return count != 0;
    }

    /**
     * 寻找有向图中的所有圈
     */
    public List<List<Vertex>> findAllCircles() {
        List<List<Vertex>> allCircles = new ArrayList<>();
        LinkedList<Vertex> trace = new LinkedList<>();

        for(Vertex v : vertexList)
            if(v.getColor() == Color.white)
                findAllCircles(v, trace, allCircles);

        return allCircles;
    }

    /**
     * 寻找有向图中的所有圈0
     */
    private void findAllCircles(Vertex v, LinkedList<Vertex> trace, List<List<Vertex>> allCircles) {
        v.setColor(Color.gray);
        trace.addLast(v);

        for(Edge e : v.getAdjList()) {
            Vertex w = e.getEnd();

            if(w.getColor() == Color.white) {   //tree edge
                w.setPath(v);
                findAllCircles(w, trace, allCircles);
            } else if(w.getColor() == Color.gray) { //back edge
                v.setBackVex(true); //标记v,w是背向边所关联的顶点
                w.setBackVex(true);

                List<Vertex> oneCircle = new ArrayList<>();
                int index = trace.indexOf(w);

                for(int i = index; i < trace.size(); i++)
                    oneCircle.add(trace.get(i));

                allCircles.add(oneCircle);
            } else if(w.getColor() == Color.black)  //forward edge or cross edge
                if(w.isBackVex()) { //如果w是背向边关联的顶点，则需要从w开始重新寻找圈
                    w.setColor(Color.white);
                    findAllCircles(w, trace, allCircles);
                }
        }

        v.setColor(Color.black);
        trace.removeLast();
    }

    /**
     * 使用DFS进行拓扑排序，时间复杂度：同DFS
     */
    public Stack<Vertex> dfsTopsort(Vertex v) {
        Stack<Vertex> stack = new Stack<>();
        dfsTopsort(v, stack);
        return stack;
    }

    private void dfsTopsort(Vertex v, Stack<Vertex> stack) {
        v.setColor(Color.gray);

        for(Edge e : v.getAdjList()) {
            Vertex w = e.getEnd();

            if(w.getColor() == Color.white) {   //树边
                w.setPath(v);
                dfsTopsort(w, stack);
            } else if(w.getColor() == Color.gray) //背向边
                throw new RuntimeException("Found circle!");
        }

        v.setColor(Color.black);
        stack.push(v);
    }
}
