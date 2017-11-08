package com.kevin.datastructure.disjoint;

/**
 * @Author kevin
 * @Date 2016/9/24 21:46
 */
public class DisjointSet {
    private int[] pre;  //记录各节点的前驱节点，如果没有前驱节点，即记录该节点的高度的负值-1

    public DisjointSet(int numElements) {
        pre = new int[numElements];
        for(int i = 0; i < numElements; i++)
            pre[i] = -1;
    }

    /**
     * 路径压缩：从x到根的路径上的每一个节点，都使其父节点成为该树的根
     * @param x
     * @return
     */
    public int find(int x) {
        if(pre[x] < 0)
            return x;
        else
            return pre[x] = find(pre[x]);
    }

    /**
     * 路径压缩算法的非递归实现
     * @param x
     * @return
     */
    public int find2(int x) {
        int r = x;
        while(pre[r] >= 0)  //找到x的根节点
            r = pre[r];

        //路径压缩
        int i = x, j;
        while(pre[i] != r) {
            j = pre[i];
            pre[i] = r;
            i = j;
        }

        return r;
    }

    /**
     * 将高度小的根作为高度大的根的子树
     * @param x
     * @param y
     */
    public void union(int x, int y) {
        int fx = find(x);
        int fy = find(y);
        if(pre[fy] < pre[fx])
            pre[fx] = fy;
        else {
            if(pre[fx] == pre[fy])
                pre[fx]--;
            pre[fy] = fx;
        }
    }

    public static void main(String[] args) {
        int numElements = 8;
        DisjointSet disjointSet = new DisjointSet(numElements);
        disjointSet.union(6, 7);
        disjointSet.union(4, 5);
        disjointSet.union(4, 6);
        disjointSet.union(3, 4);
        System.out.println(disjointSet.find2(7));
    }
}
