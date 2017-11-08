package com.kevin.datastructure.graph;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2016/10/9 19:40
 */
public class MaxFlow {
    private int vexNum;     //顶点个数
    private int source;     //源点
    private int des;        //宿点
    private int maxflow;    //从源点到宿点的最大流


    private int[][] G;      //原图
    private int[][] Gr;     //残量图
    private int[][] Gf;     //流图

    /**
     * 求可增流路时需要用到的变量
     */
    boolean[] visited;
    int[] path;
    int[] queue;
    int first;
    int last;

    /**
     * 初始化图
     * 输入：
     * 第一行为顶点个数vexNum
     * 第二行为源点和宿点
     * 从第三行开始为各边的容量，总共vexNum行，vexNum列
     */
    private void init() {
        try (Scanner in = new Scanner(System.in)) {
            vexNum = in.nextInt();
            source = in.nextInt();
            des = in.nextInt();

            G = new int[vexNum][vexNum];
            Gr = new int[vexNum][vexNum];
            Gf = new int[vexNum][vexNum];

            int item;
            for(int i = 0; i < vexNum; i++)
                for(int j = 0; j <vexNum; j++) {
                    item = in.nextInt();
                    G[i][j] = item;
                    Gr[i][j] = item;
                }

            visited = new boolean[vexNum];
            path = new int[vexNum];
            queue = new int[vexNum];
            first = last = 0;
        }
    }

    /**
     * 寻找可增流路：使用bfs算法
     */
    private boolean bfs(int s, int t) {
        for(int i = 0; i < vexNum; i++) {   //初始化队列，前驱，以及顶点是否访问
            path[i] = -1;
            queue[i] = -1;
            visited[i] = false;
        }

        first = last = 0;

        visited[s] = true;  //将源点标记为已访问，并入队
        queue[first] = s;

        while (first <= last) {
            int v = queue[first];   //出队一个元素
            queue[first++] = -1;

            for(int i = 0; i < vexNum; i++)
                if(Gr[v][i] != 0)       //遍历邻接顶点
                    if(!visited[i]) {    //如果邻接顶点未被访问，则将其标记为已访问并入队，记录前驱
                        visited[i] = true;
                        path[i] = v;
                        queue[++last] = i;
                    }
        }

        return visited[t];
    }

    /**
     * 得到可增流量
     */
    private int getDeta(int s, int t) {
        int deta = Integer.MAX_VALUE;
        int source, des;

        for(des = t; des != s; des = source) {
            source = path[des];

            if(deta > Gr[source][des])
                deta = Gr[source][des];
        }

        return deta;
    }

    /**
     * 计算最大流
     * 1、找到一条可增流路
     * (1)增流值最大的可增流路(D算法)，则算法时间复杂度为：|E|log(Capmax)|E|log|V|
     * (2)边数最少的可增流路(bfs算法)，则算法时间复杂度为：|E||V||E|
     * 2、更新残量图Gr
     * fij -= deta
     * fji += deta
     * 3、更新流图Gf
     * fij += deta
     */
    private void maxflow(int s, int t) {
        int deta;
        int source, des;

        while (bfs(s, t)) {
            //得到可赠流量
            deta = getDeta(s, t);

            //更新残量图Gr，更新流图Gf
            for(des = t; des != s; des = source) {
                source = path[des];
                Gr[source][des] -= deta;
                Gr[des][source] += deta;
                Gf[source][des] += deta;
            }

            //maxflow += deta;  //另一种计算最大流的方式，即将所有可增流量相加即得到最大流
        }

        for(int i = 0; i < vexNum; i++) //计算最大流
            if(Gf[s][i] != 0)
                maxflow += Gf[s][i];
    }

    /**
     6
     0 5
     0 3 2 0 0 0
     0 0 1 3 4 0
     0 0 0 0 2 0
     0 0 0 0 0 2
     0 0 0 0 0 3
     0 0 0 0 0 0
     */
    public int maxflow() {
        init();
        maxflow(source, des);
        return this.maxflow;
    }

    public static void main(String[] args) {
        MaxFlow maxFlow = new MaxFlow();
        System.out.println(maxFlow.maxflow());
    }
}
