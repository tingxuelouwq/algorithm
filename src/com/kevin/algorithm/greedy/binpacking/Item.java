package com.kevin.algorithm.greedy.binpacking;

/**
 * @Author kevin
 * @Date 2016/10/25 20:34
 */
public class Item {
    int id;         // 物品的id号
    int weight;     // 物品的权值

    public Item(int id, int weight) {
        this.id = id;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", weight=" + weight +
                '}';
    }
}
