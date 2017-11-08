package com.kevin.datastructure.graph;

/**
 * @Author kevin
 * @Date 2016/10/13 16:15
 */
public enum EdgeType {
    treeEdge("树边"), backEdge("后向边"), forwardEdge("前向边"), crossEdge("交叉边");

    private String desc;

    private EdgeType(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}
