package com.kevin.datastructure.graph;

import java.util.Stack;

/**
 * 无向图如果有欧拉回路，当且仅当所有顶点的度数为偶数；无向图如果有欧拉路径，或者所有顶点的度数为偶数，或者仅有两个顶点的度数为奇数。
 * 有向图如果有欧拉回路，当且仅当所有顶点的入度=出度；有向图如果有欧拉路径，或者所有顶点的度数为偶数，或者欧拉路径的起点：
 * 出度=入度+1，终点：入度=出度+1 *
 *
 * @Author kevin
 * @Date 2016/10/12 17:26
 */
public class EulerPath {
    private int vexNum;         //顶点数
    private int[][] edges;       //邻接矩阵
    private Stack<Integer> s;   //用来存储路径的栈

    public EulerPath(int vexNum, int[][] edges) {
        this.vexNum = vexNum;
        this.edges = edges;
        s = new Stack<>();
    }

    public void eulerPath() {
        int num = 0, start = 0; //num标记奇数度顶点的个数，start标记欧拉路径的起点
        int indegree;   //标记顶点的度数

        for(int i = 0; i < vexNum; i++) {
            indegree = 0;
            for(int j = 0; j < vexNum; j++)
                indegree += edges[i][j];
            if ((indegree & 1) == 1) {  //如果该顶点的度数是奇数，则更新num和start
                num++;
                start = i;
            }
        }

        if(num == 0 || num ==2)
            fleury(start);
        else
            System.out.println("There is no euler path!");
    }

    private void fleury(int start) {
        s.push(start);

        boolean flag;   //标记是否可以扩展
        while(!s.isEmpty()) {
            flag = false;

            for (int i = 0; i < vexNum; i++)
                if (edges[s.peek()][i] > 0) {    //若存在一条从s.peek()出发的边，则说明可以扩展
                    flag = true;
                    break;
                }

            if (!flag)
                System.out.print(s.pop() + "->");
            else
                dfs(s.pop());
        }
    }

    private void dfs(int x) {
        s.push(x);

        for(int i = 0; i < vexNum; i++)
            if(edges[x][i] > 0) {
                edges[x][i] = edges[i][x] = 0;  //删除关联边
                dfs(i);
                break;
            }
    }

    public static void main(String[] args) {
        int vexNum = 8;
        int[][] edge = {
                {0, 1, 0, 1, 0, 0, 0, 0},
                {1, 0, 1, 0, 1, 1, 0, 0},
                {0, 1, 0, 1, 0, 0, 0, 0},
                {1, 0, 1, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 1, 1, 1},
                {0, 1, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 1},
                {0, 0, 0, 0, 1, 0, 1, 0}
        };
        EulerPath eulerPath = new EulerPath(vexNum, edge);
        eulerPath.eulerPath();
    }
}
