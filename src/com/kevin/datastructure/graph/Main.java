package com.kevin.datastructure.graph;

import java.util.List;
import java.util.Stack;

/**
 * @Author kevin
 * @Date 2016/10/5 21:40
 */
public class Main {
    public static void main(String[] args) {
//        testTopSort();

//        testShortestPathAlgorithmUnweighted();

//        testShortestPathAlgorithmDijkstra();

//        testShortestPathAlgorithmWeightedNegative();

//        testCriticalPathAlgorithm();

//        testMinimunSpanningTreePrime();

//        testMinimunSpanningTreeKruskal();

//        testDfsUG();

//        testCutPointUG();

//        testEulerPath();

//        testDfsDG();

//        testStrongConnectedComponent();

//        testMarkEdgeType();

//        testHasCircleUGTopsortDistortion();

//        testHasCircleUGDfs();

//        testHasCircleDGTopsort();

//        testHasCircleDGDfs();

//        testHasCircleDGStrongConnectedComponent();

//        testDfsTopsort();

        testFindAllCircles();
    }

    /**
     * 测试拓扑排序
     */
    public static void testTopSort() {
        Graph graph = initForTopsortAndHasCircle();
        List<Vertex> result = graph.topsort();
        int i;
        for(i = 0; i < result.size(); i++) {
            if(i == result.size() - 1)
                System.out.print(result.get(i).getData());
            else
                System.out.print(result.get(i).getData() + "->");
        }
    }

    /**
     * 测试无权图的最短路径算法
     */
    public static void testShortestPathAlgorithmUnweighted() {
        Graph graph = initForShortestPathAlgorithmUnweighted();
        List<Vertex> vertexList = graph.getVertexList();
        Vertex s = null;
        Vertex t = null;
        for(Vertex v : vertexList) {
            if(v.getData().equals("v1"))
                s = v;
            if(v.getData().equals("v7"))
                t = v;
        }
        graph.shortestPathAlgorithmUnweighted(s);
        graph.printPath(t);
    }

    /**
     * 测试有权图的最短路径算法
     */
    public static void testShortestPathAlgorithmDijkstra() {
        Graph graph = initForShortestPathAlgorithmDijkstra();
        List<Vertex> vertexList = graph.getVertexList();
        Vertex s = null;
        Vertex t = null;
        for(Vertex v : vertexList) {
            if(v.getData().equals("v1"))
                s = v;
            if(v.getData().equals("v6"))
                t = v;
        }
        graph.shortestPathAlgorithmDijkstra(s);
        graph.printPath(t);
    }

    /**
     * 测试有权负边图的最短路径算法
     */
    public static void testShortestPathAlgorithmWeightedNegative() {
        Graph graph = initForShortestPathAlgorithmWeightedNegative();
        List<Vertex> vertexList = graph.getVertexList();
        Vertex s = null;
        Vertex t = null;
        for(Vertex v : vertexList) {
            if(v.getData().equals("v1"))
                s = v;
            if(v.getData().equals("v6"))
                t = v;
        }
        graph.shortestPathAlgorithmWeightedNegative(s);
        graph.printPath(t);
    }

    /**
     * 测试关键路径算法
     */
    public static void testCriticalPathAlgorithm() {
        Graph graph = initForCriticalPath();
        CriticalPath criticalPath = new CriticalPath(graph);
        List<CriticalPath.Pair> result = criticalPath.getCriticalPath();
        for(CriticalPath.Pair pair : result) {
            Vertex v = pair.first;
            Vertex w = pair.second;
            int weight = pair.weight;
            System.out.println(v.getData() + "->" + w.getData() + "(" + weight + ")");
        }
    }

    /**
     * 测试最小生成树：Prime算法
     */
    public static void testMinimunSpanningTreePrime() {
        Graph graph = initForMinimumSpanningTree();
        Vertex s = null;
        for(Vertex v : graph.getVertexList())
            if(v.getData().equals("v1"))
                s = v;

        graph.minimumSpanningTreePrime(s);
        for(Vertex v : graph.getVertexList())
            if(v.getPath() != null)
                System.out.println(v.getData() + "->" + v.getPath().getData() + "(" + v.getDist() + ")");
    }

    /**
     * 测试最小生成树：Kruskal算法
     */
    public static void testMinimunSpanningTreeKruskal() {
        Graph graph = initForMinimumSpanningTree();
        List<Edge> result = graph.minimunSpanningTreeKruskal();

        for(Edge e : result)
            System.out.println(e.getBegin().getData() + "->" + e.getEnd().getData() + "(" + e.getWeight() + ")");
    }

    /**
     * 测试无向图的深度优先遍历：dfs算法
     */
    public static void testDfsUG() {
        Graph graph = initForDfsUGandCutPoint();
        graph.dfsUG();

        for(Vertex v : graph.getVertexList())
            if(v.getPath() != null)
                System.out.println(v.getPath().getData() + "->" + v.getData());
    }

    /**
     * 测试在无向图中寻找割点
     */
    public static void testCutPointUG() {
        Graph graph = initForDfsUGandCutPoint();
        Vertex s = null;
        for(Vertex v : graph.getVertexList())
            if(v.getData().equals("A"))
                s = v;
        graph.findCutPoint(s);
    }

    /**
     * 测试在无向图中寻找欧拉路径
     */
    public static void testEulerPath() {
        Graph graph = initForEulerPathUG();
        Vertex s = null;
        for(Vertex v : graph.getVertexList())
            if(v.getData().equals("v1")) {
                s = v;
                break;
            }

        List<Vertex> result = graph.eulerPath(s);

        for(int i = 0; i < result.size(); i++)
            if(i < result.size() -1)
                System.out.print(result.get(i).getData() + "->");
            else
                System.out.print(result.get(i).getData());
    }

    /**
     * 测试有向图的深度优先遍历
     */
    public static void testDfsDG() {
        Graph graph = initForDfsDGAndStrongConnectedComponentAndMarkEdgeTypeAndHasCircleDG();
        graph.dfsDG();

        for(Vertex v : graph.getVertexList())
            if (v.getPath() != null)
                System.out.println(v.getPath().getData() + "->" + v.getData());
    }

    /**
     * 测试有向图的强连通分支
     */
    public static void testStrongConnectedComponent() {
        Graph graph = initForDfsDGAndStrongConnectedComponentAndMarkEdgeTypeAndHasCircleDG();
        List<List<Vertex>> results = graph.strongConnectedComponent();

        for(int i = 0; i < results.size(); i++) {
            System.out.print("{");

            for(int j = 0; j < results.get(i).size(); j++) {
                if(j < results.get(i).size() - 1)
                    System.out.print(results.get(i).get(j).getData() + ",");
                else
                    System.out.print(results.get(i).get(j).getData());
            }

            if(i < results.size() - 1)
                System.out.print("}, ");
            else
                System.out.print("}");
        }
    }

    /**
     * 测试标记有向图中边的类型
     */
    public static void testMarkEdgeType() {
        Graph graph = initForDfsDGAndStrongConnectedComponentAndMarkEdgeTypeAndHasCircleDG();
        graph.markEdgeType();

        for(Edge e : graph.getEdgeList())
            System.out.println("(" +
                    e.getBegin().getData() +
                    "," +
                    e.getEnd().getData() +
                    "): " +
                    e.getEdgeType()
            );
    }

    /**
     * 测试判断无向图是否有环：利用拓扑排序的变形
     */
    public static void testHasCircleUGTopsortDistortion() {
        Graph graph = initForHasCircleUG();
        System.out.println(graph.hasCircleUGTopsortDistortion());
    }

    /**
     * 测试判断无向图是否有环：利用DFS
     */
    public static void testHasCircleUGDfs() {
        Graph graph = initForHasCircleUG();
        System.out.println(graph.hasCircleUGDfs());
    }

    /**
     * 测试判断有向图是否有环：利用拓扑排序
     */
    public static void testHasCircleDGTopsort() {
        Graph graph = initForDfsDGAndStrongConnectedComponentAndMarkEdgeTypeAndHasCircleDG();
//        Graph graph = initForTopsortAndHasCircle();
        System.out.println(graph.hasCircleDGTopsort());
    }

    /**
     * 测试判断有向图是否有环：利用DFS
     */
    public static void testHasCircleDGDfs() {
//        Graph graph = initForDfsDGAndStrongConnectedComponentAndMarkEdgeTypeAndHasCircleDG();
        Graph graph = initForTopsortAndHasCircle();
        System.out.println(graph.hasCircleDGDfs());
    }

    /**
     * 测试判断有向图是否有环：利用强连通分支：Korasaju算法
     */
    public static void testHasCircleDGStrongConnectedComponent() {
        Graph graph = initForDfsDGAndStrongConnectedComponentAndMarkEdgeTypeAndHasCircleDG();
//        Graph graph = initForTopsortAndHasCircle();
        System.out.println(graph.hasCircleDGStrongConnectedComponent());
    }

    public static void testFindAllCircles() {
        Graph graph = initForDfsDGAndStrongConnectedComponentAndMarkEdgeTypeAndHasCircleDG();
        List<List<Vertex>> allCircles = graph.findAllCircles();

        for(List<Vertex> oneCircle : allCircles) {
            for (Vertex v : oneCircle)
                System.out.print(v.getData() + " ");
            System.out.println();
        }
    }


    /**
     * 测试使用DFS进行拓扑排序
     */
    public static void testDfsTopsort() {
        Graph graph = initForTopsortAndHasCircle();
        Vertex s = null;
        for(Vertex v : graph.getVertexList())
            if(v.getData().equals("v1")) {
                s = v;
                break;
            }
        Stack<Vertex> stack = graph.dfsTopsort(s);

        while(!stack.isEmpty())
            System.out.println(stack.pop().getData());
    }

    public static Graph initForTopsortAndHasCircle() {
        Graph graph = new Graph();

        Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");
        Vertex v5 = new Vertex("v5");
        Vertex v6 = new Vertex("v6");
        Vertex v7 = new Vertex("v7");

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);
        graph.addVertex(v6);
        graph.addVertex(v7);

        graph.addEdge(v1, v2);
        graph.addEdge(v1, v3);
        graph.addEdge(v1, v4);
        graph.addEdge(v2, v4);
        graph.addEdge(v2, v5);
        graph.addEdge(v3, v6);
        graph.addEdge(v4, v3);
        graph.addEdge(v4, v6);
        graph.addEdge(v4, v7);
        graph.addEdge(v5, v4);
        graph.addEdge(v5, v7);
        graph.addEdge(v7, v6);

        return graph;
    }

    public static Graph initForShortestPathAlgorithmUnweighted() {
        Graph graph = new Graph();

        Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");
        Vertex v5 = new Vertex("v5");
        Vertex v6 = new Vertex("v6");
        Vertex v7 = new Vertex("v7");

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);
        graph.addVertex(v6);
        graph.addVertex(v7);

        graph.addEdge(v1, v2);
        graph.addEdge(v1, v4);
        graph.addEdge(v2, v4);
        graph.addEdge(v2, v5);
        graph.addEdge(v3, v1);
        graph.addEdge(v3, v6);
        graph.addEdge(v4, v3);
        graph.addEdge(v4, v5);
        graph.addEdge(v4, v6);
        graph.addEdge(v4, v7);
        graph.addEdge(v5, v7);
        graph.addEdge(v7, v6);

        return graph;
    }

    public static Graph initForShortestPathAlgorithmDijkstra() {
        Graph graph = new Graph();

        Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");
        Vertex v5 = new Vertex("v5");
        Vertex v6 = new Vertex("v6");
        Vertex v7 = new Vertex("v7");

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);
        graph.addVertex(v6);
        graph.addVertex(v7);

        graph.addEdge(v1, v2, 2);
        graph.addEdge(v1, v4, 1);
        graph.addEdge(v2, v4, 3);
        graph.addEdge(v2, v5, 10);
        graph.addEdge(v3, v1, 4);
        graph.addEdge(v3, v6, 5);
        graph.addEdge(v4, v3, 2);
        graph.addEdge(v4, v5, 2);
        graph.addEdge(v4, v6, 8);
        graph.addEdge(v4, v7, 4);
        graph.addEdge(v5, v7, 6);
        graph.addEdge(v7, v6, 1);

        return graph;
    }

    public static Graph initForShortestPathAlgorithmWeightedNegative() {
        Graph graph = new Graph();

        Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");
        Vertex v5 = new Vertex("v5");
        Vertex v6 = new Vertex("v6");
        Vertex v7 = new Vertex("v7");

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);
        graph.addVertex(v6);
        graph.addVertex(v7);

        graph.addEdge(v1, v2, 2);
        graph.addEdge(v1, v4, 1);
        graph.addEdge(v2, v4, 3);
        graph.addEdge(v2, v5, -1);
        graph.addEdge(v3, v1, 4);
        graph.addEdge(v3, v6, -1);
        graph.addEdge(v4, v3, 2);
        graph.addEdge(v4, v5, 2);
        graph.addEdge(v4, v6, 8);
        graph.addEdge(v4, v7, 4);
        graph.addEdge(v5, v7, 6);
        graph.addEdge(v7, v6, 1);

        return graph;
    }

    public static Graph initForCriticalPath() {
        Graph graph = new Graph();

        Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");
        Vertex v5 = new Vertex("v5");
        Vertex v6 = new Vertex("v6");
        Vertex v7 = new Vertex("v7");

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);
        graph.addVertex(v6);
        graph.addVertex(v7);

        graph.addEdge(v1, v2, 3);
        graph.addEdge(v1, v3, 2);
        graph.addEdge(v1, v4, 6);
        graph.addEdge(v2, v4, 2);
        graph.addEdge(v2, v5, 4);
        graph.addEdge(v3, v4, 1);
        graph.addEdge(v3, v6, 3);
        graph.addEdge(v4, v5, 1);
        graph.addEdge(v5, v7, 3);
        graph.addEdge(v6, v7, 4);

        return graph;
    }

    public static Graph initForMinimumSpanningTree() {
        Graph graph = new Graph();

        Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");
        Vertex v5 = new Vertex("v5");
        Vertex v6 = new Vertex("v6");
        Vertex v7 = new Vertex("v7");

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);
        graph.addVertex(v6);
        graph.addVertex(v7);

        graph.addEdge(v1, v2, 2);
        graph.addEdge(v1, v3, 4);
        graph.addEdge(v1, v4, 1);
        graph.addEdge(v2, v1, 2);
        graph.addEdge(v2, v4, 3);
        graph.addEdge(v2, v5, 10);
        graph.addEdge(v3, v1, 4);
        graph.addEdge(v3, v4, 2);
        graph.addEdge(v3, v6, 5);
        graph.addEdge(v4, v1, 1);
        graph.addEdge(v4, v2, 3);
        graph.addEdge(v4, v3, 2);
        graph.addEdge(v4, v5, 7);
        graph.addEdge(v4, v6, 8);
        graph.addEdge(v4, v7, 4);
        graph.addEdge(v5, v2, 10);
        graph.addEdge(v5, v4, 7);
        graph.addEdge(v5, v7, 6);
        graph.addEdge(v6, v3, 5);
        graph.addEdge(v6, v4, 8);
        graph.addEdge(v6, v7, 1);
        graph.addEdge(v7, v4, 4);
        graph.addEdge(v7, v5, 6);
        graph.addEdge(v7, v6, 1);

        return graph;
    }

    public static Graph initForDfsUGandCutPoint() {
        Graph graph = new Graph();

        Vertex A = new Vertex("A");
        Vertex B = new Vertex("B");
        Vertex C = new Vertex("C");
        Vertex D = new Vertex("D");
        Vertex E = new Vertex("E");
        Vertex F = new Vertex("F");
        Vertex G = new Vertex("G");

        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addVertex(D);
        graph.addVertex(E);
        graph.addVertex(F);
        graph.addVertex(G);

        graph.addEdge(A, B);
        graph.addEdge(A, D);
        graph.addEdge(B, A);
        graph.addEdge(B, C);
        graph.addEdge(C, B);
        graph.addEdge(C, D);
        graph.addEdge(C, G);
        graph.addEdge(D, A);
        graph.addEdge(D, C);
        graph.addEdge(D, E);
        graph.addEdge(D, F);
        graph.addEdge(E, D);
        graph.addEdge(E, F);
        graph.addEdge(F, D);
        graph.addEdge(F, E);

        return graph;
    }

    public static Graph initForEulerPathUG() {
        Graph graph = new Graph();

        Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");
        Vertex v5 = new Vertex("v5");
        Vertex v6 = new Vertex("v6");
        Vertex v7 = new Vertex("v7");
        Vertex v8 = new Vertex("v8");

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);
        graph.addVertex(v5);
        graph.addVertex(v6);
        graph.addVertex(v7);
        graph.addVertex(v8);

        graph.addEdge(v1, v2);
        graph.addEdge(v1, v4);
        graph.addEdge(v2, v1);
        graph.addEdge(v2, v3);
        graph.addEdge(v2, v5);
        graph.addEdge(v2, v6);
        graph.addEdge(v3, v2);
        graph.addEdge(v3, v4);
        graph.addEdge(v4, v1);
        graph.addEdge(v4, v3);
        graph.addEdge(v5, v2);
        graph.addEdge(v5, v6);
        graph.addEdge(v5, v7);
        graph.addEdge(v5, v8);
        graph.addEdge(v6, v2);
        graph.addEdge(v6, v5);
        graph.addEdge(v7, v5);
        graph.addEdge(v7, v8);
        graph.addEdge(v8, v5);
        graph.addEdge(v8, v7);

        return graph;
    }

    public static Graph initForDfsDGAndStrongConnectedComponentAndMarkEdgeTypeAndHasCircleDG() {
        Graph graph = new Graph();

        Vertex A = new Vertex("A");
        Vertex B = new Vertex("B");
        Vertex C = new Vertex("C");
        Vertex D = new Vertex("D");
        Vertex E = new Vertex("E");
        Vertex F = new Vertex("F");
        Vertex G = new Vertex("G");
        Vertex H = new Vertex("H");
        Vertex I = new Vertex("I");
        Vertex J = new Vertex("J");

        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addVertex(D);
        graph.addVertex(E);
        graph.addVertex(F);
        graph.addVertex(G);
        graph.addVertex(H);
        graph.addVertex(I);
        graph.addVertex(J);

        graph.addEdge(A, B);
        graph.addEdge(A, D);
        graph.addEdge(B, C);
        graph.addEdge(B, F);
        graph.addEdge(C, A);
        graph.addEdge(C, D);
        graph.addEdge(C, E);
        graph.addEdge(D, E);
        graph.addEdge(F, C);
        graph.addEdge(G, F);
        graph.addEdge(G, H);
        graph.addEdge(H, F);
        graph.addEdge(H, J);
        graph.addEdge(I, H);
        graph.addEdge(J, I);

        return graph;
    }

    public static Graph initForHasCircleUG() {
        Graph graph = new Graph();

        Vertex v1 = new Vertex("v1");
        Vertex v2 = new Vertex("v2");
        Vertex v3 = new Vertex("v3");
        Vertex v4 = new Vertex("v4");

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);
        graph.addVertex(v4);

        graph.addEdge(v1, v2);
        graph.addEdge(v2, v1);
        graph.addEdge(v2, v3);
        graph.addEdge(v3, v2);
        graph.addEdge(v1, v4);
        graph.addEdge(v4, v1);
        graph.addEdge(v4, v3);
        graph.addEdge(v3, v4);

        return graph;
    }
}
