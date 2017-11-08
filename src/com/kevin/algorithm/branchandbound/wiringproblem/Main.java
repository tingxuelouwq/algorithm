package com.kevin.algorithm.branchandbound.wiringproblem;

import com.kevin.algorithm.branchandbound.ArrayQueue;

import java.util.Scanner;

/**
 * @Author kevin
 * @Date 2017/1/1 18:42
 * 布线问题
 * 问题描述：印刷电路板将布线区域划分成nxm个方格，电路布线问题要求确定连接方格a的中点到方格b的中点的最短布线方案。在布线时，
 * 电路只能沿直线或直角布线，为了避免线路相交，已经布了线的方格做了封锁标记，其他线路不允许穿过被封锁的方格。一个布线的例子
 * 如图所示，图中包含障碍，起始点为a，目标点为b。
 *
 * 输入：第一行是2个整数，分别表示方格阵列的行数n和列数m；第二行是起始位置的行和列；第三行是结束位置的行和列；接下来的n行，
 * 每行m个整数，表示方格阵列，其中0表示该方格允许布线，1表示该方格被封锁
 * 输出：如果能够找到最短布线方案，则第一行是最短布线路径长度，第二行是最短布线路径；如果不能够找到最短布线方案，则输出-1
 * 输入示例：
7 7
3 2
4 6
0 0 1 0 0 0 0
0 0 1 1 0 0 0
0 0 0 0 1 0 0
0 0 0 1 1 0 0
1 0 0 0 1 0 0
1 1 1 0 0 0 0
1 1 1 0 0 0 0
 * 输出示例：
9
3 2
4 2
5 2
5 3
5 4
6 4
6 5
6 6
5 6
4 6
 *
 * 算法分析：用队列式分支限界法来求解布线问题。布线问题的解空间是一个图，解此问题的队列式分支限界法从起始位置a开始将它作为
 * 第一个扩展节点，与该扩展节点相邻并且可达的方格成为可行节点被加入到活节点队列中，并且将这些方格标记为1，表示从起始方格a
 * 到这些方格的距离为1。接着，算法从活结点队列中取出队首节点作为下一个扩展节点，并将与当前扩展节点相邻且未标记过的可达方格
 * 标记为2，并存入活结点队列。这个过程一直继续到算法搜索到目标方格或活节点队列为空时为止。
 *
 * 算法实现：
 * 1、首先定义一个表示电路板上方格位置的类Position，它的私有成员row和col分别表示方格所在的行和列。在电路板的任何一个方格处，
 * 布线可沿右、下、左、上4个方向进行，相应的Position类对象数组offset如下：
 * offset[i].row    offset[i].col
 *      0               1
 *      1               0
 *      0               -1
 *      -1              0
 * 2、用二维数组grid表示电路板。初始时，grid[i][j]=0，表示该方格允许布线，grid[i][j]=1表示该方格被封锁，不允许布线。为了处理
 * 方格边界的情况，我们在所给方格阵列四周增设标记为1的附加方格。算法开始时，测试初始方格与目标方格是否相同，如果相同则不必计算
 * ，直接返回最短距离0，否则算法设置方格阵列的围墙，初始化位移矩阵offset。
 * 3、由于数字0和1用于表示方格的开放或封锁状态，所以在表示距离时不用这两个数字，因而将距离的值都加2。算法将起始位置的距离标记
 * 为2，然后标记所有距离为3的方格并存入活结点队列，接着依次标记所有距离为4、5、...、的方格，直至到达目标方格或活结点队列为空
 *
 * 算法时间复杂度分析：由于每个方格成为活结点进入活结点队列最多1次，因此活结点队列中最多只处理O(mn)个活结点。扩展每个节点需
 * O(1)时间，因此算法共耗时O(mn)。构造相应的最短布线路径需要O(L)时间，其中L是最短布线路径的长度。
 */
public class Main {
    public static void main(String[] args) {
        int n;
        int m;
        Position start;
        Position end;
        int[][] grid;
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {
            n = in.nextInt();
            m = in.nextInt();
            start = new Position(in.nextInt(), in.nextInt());
            end = new Position(in.nextInt(), in.nextInt());
            grid = new int[n + 2][m + 2];
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    grid[i][j] = in.nextInt();
                }
            }
            WireRouter router = new WireRouter(n, m, start, end, grid);
            router.printPath();
        }
    }

    private static class Position {
        int row;    // 方格所在的行
        int col;    // 方格所在的列

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private static class WireRouter {
        int n;          // 方格阵列大小
        int m;
        Position start; // 起点
        Position end;   // 终点
        int[][] grid;   // 方格阵列
        ArrayQueue<Position> q; // 活结点队列
        int pathLen;        // 最短布线路径长度
        Position[] path;    // 最短布线路径

        public WireRouter(int n, int m, Position start, Position end, int[][] grid) {
            this.n = n;
            this.m = m;
            this.start = start;
            this.end = end;
            this.grid = grid;
            this.q = new ArrayQueue<>();
            this.pathLen = 0;
            this.path = null;
        }

        public boolean findPath() {
            // 首先判断起始位置和结束位置是否相同if
            if (start.row == end.row && start.col == end.col) {
                pathLen = 0;
                return true;
            }

            // 初始化位移矩阵offset
            Position[] offset = new Position[4];
            offset[0] = new Position(0, 1);
            offset[1] = new Position(1, 0);
            offset[2] = new Position(0, -1);
            offset[3] = new Position(-1, 0);

            // 设置方格阵列围墙
            for (int i = 0; i <= m + 1; i++) {  // 顶部和底部
                grid[0][i] = grid[n + 1][i] = 1;
            }
            for (int i = 0; i <= n + 1; i++) {  // 左侧和右侧
                grid[i][0] = grid[i][m + 1] = 1;
            }

            grid[start.row][start.col] = 2; // 起始位置的距离为2
            int numOfNbrs = 4;               // 相邻方格数为4
            Position nbr = new Position(0, 0);
            Position here = new Position(start.row, start.col);
            q.offer(here);
            boolean finished = false;
            // 标记可达方格位置
            while (!q.isEmpty()) {
                here = q.poll();
                for (int i = 0; i < numOfNbrs; i++) {
                    nbr.row = here.row + offset[i].row;
                    nbr.col = here.col + offset[i].col;
                    if (grid[nbr.row][nbr.col] == 0) {  // 该方格未被标记
                        grid[nbr.row][nbr.col] = grid[here.row][here.col] + 1;
                        if (nbr.row == end.row && nbr.col == end.col) { // 到达目标位置
                            finished = true;
                            break;
                        }
                        q.offer(new Position(nbr.row, nbr.col));
                    }
                }
                if (finished) { // 到达目标位置
                    break;
                }
            }

            if (!finished) {
                return false;
            }

            // 构造最短布线路径
            pathLen = grid[end.row][end.col] - 2;
            path = new Position[pathLen];
            // 从目标位置向起始位置回溯
            here = end;
            for (int i = pathLen - 1; i >= 0; i--) {
                path[i] = here;
                for (int j = 0; j < numOfNbrs; j++) {
                    nbr.row = here.row + offset[j].row;
                    nbr.col = here.col + offset[j].col;
                    if (grid[nbr.row][nbr.col] == i + 2) {
                        break;
                    }
                }
                here = new Position(nbr.row, nbr.col);
            }
            return true;
        }

        public void printPath() {
            if (findPath()) {
                System.out.println(pathLen);
                System.out.println(start.row + " " + start.col);
                for (int i = 0; i < pathLen; i++) {
                    System.out.println(path[i].row + " " + path[i].col);
                }
            } else {
                System.out.println(-1);
            }
        }
    }
}
