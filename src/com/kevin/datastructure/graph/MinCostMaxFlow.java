package com.kevin.datastructure.graph;

/**
 * @Author kevin
 * @Date 2016/10/9 21:01
 */
public class MinCostMaxFlow {
    private static final int INF = Integer.MAX_VALUE;

    private int vexNum;     //顶点个数
    private int[][] cap;    //残量图
    private int[][] cost;   //费用图
    private int minCost;    //最小费用
    private int maxFlow;    //最大流

    /**
     * 求基于费用的可增流路时需要用到的变量
     */
    private boolean[] visited;
    private int[] path;
    private int[] dis;
    private int[] queue;
    private int head;
    private int tail;

    public MinCostMaxFlow(int vexNum, int[][] cap, int[][] cost) {
        this.vexNum = vexNum;
        this.cap = cap;
        this.cost = cost;
        minCost = maxFlow = 0;

        visited = new boolean[vexNum];
        path = new int[vexNum];
        dis = new int[vexNum];
        queue = new int[vexNum];
        head = 0;
        tail = 1;
    }

    /**
     * 判断源宿点之间是否有基于费用的可增流路：使用spfa算法(可以处理负值边)
     */
    private boolean spfa(int s, int t) {
        for(int i = 0; i < vexNum; i++) {   //初始化
            visited[i] = false;
            path[i] = -1;
            dis[i] = INF;
            queue[i] = -1;
        }
        head = 0;
        tail = 1;

        dis[head] = 0;          //将源点入队，并标记为在队中
        queue[head] = s;
        visited[s] = true;

        while (head != tail) {
            int v = queue[head];    //出队一个元素
            visited[v] = false;     //将其标记为不在队中

            queue[head++] = -1;
            if(head == vexNum)
                head = 0;

            for(int i = 0; i < vexNum; i++) //遍历邻接顶点
                if(cap[v][i] > 0 && dis[v] + cost[v][i] < dis[i]) {  //如果有更小的费用，则更新费用，并记录前驱
                    dis[i] = dis[v] + cost[v][i];
                    path[i] = v;
                    if(!visited[i]) {   //如果邻接顶点不在队列中，则将其入队，并标记为在队列中
                        queue[tail++] = i;
                        visited[i] = true;
                        if(tail == vexNum)
                            tail = 0;
                    }
                }
        }

        return dis[t] != INF;
    }

    /**
     * 得到增流值
     */
    private int getDeta(int s, int t) {
        int deta = INF;
        int source, des;

        for(des = t; des != s; des = source) {
            source = path[des];
            if(deta > cap[source][des])
                deta = cap[source][des];
        }

        return deta;
    }

    /**
     * 计算最小费用最大流
     */
    public void minCostMaxFlow(int s, int t) {
        int deta;
        int source, des;

        while (spfa(s, t)) {
            deta = getDeta(s, t);   //得到增流值

            for(des = t; des != s; des = source) {  //更新残量图
                source = path[des];
                cap[source][des] -= deta;
                cap[des][source] += deta;
                minCost += deta * cost[source][des];    //更新最小费用
            }
            maxFlow += deta;    //更新最大流
        }

        System.out.println("maxFlow: " + maxFlow);
        System.out.println("minCost: " + minCost);
    }

    public static void main(String[] args) {
        int vexNum = 5;
		int cap[][] = {{0, 15, 16, 0, 0},
				{0, 0, 0, 13, 14},
				{0, 11, 0, 17, 0},
				{0, 0, 0, 0, 8},
				{0, 0, 0, 0, 0}};
		int cost[][] = {{0, 4, 1, 0, 0},
				{-4, 0, -2, 6, 1},
				{-1, 2, 0, 3, 0},
				{0, -6, -3, 0, 2},
				{0, -1, 0, -2, 0}};
        /*int cap[][] = {{0, 10, 8, 0, 0},
                {0, 0, 0, 2, 7},
                {0, 5, 0, 10, 0},
                {0, 0, 0, 0, 4},
                {0, 0, 0, 0, 0}};
        int cost[][] = {{0, 4, 1, 0, 0},
                {-4, 0, -2, 6, 1},
                {-1, 2, 0, 3, 0},
                {0, -6, -3, 0, 2},
                {0, -1, 0, -2, 0}};*/

        MinCostMaxFlow minCostMaxFlow = new MinCostMaxFlow(vexNum, cap, cost);
        minCostMaxFlow.minCostMaxFlow(0, 4);
    }
}
