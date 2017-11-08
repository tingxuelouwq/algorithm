package com.kevin.algorithm.greedy.binpacking;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author kevin
 * @Date 2016/10/25 20:24
 */
public class Bin {
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    int capacity; // 箱子的最大容量
    int used;     // 箱子已使用容量
    int avail;    // 箱子的剩余容量
    List<Item> items; // 箱子中存储的物品列表

    public Bin() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public Bin(int initialCapacity) {
        if(initialCapacity <= 0 || Float.isNaN(initialCapacity))
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);

        capacity = initialCapacity;
        items = new ArrayList<>();
    }

    /**
     * 将物品装箱
     * @param item
     */
    public void binPackage(Item item) {
        items.add(item);
        used += item.weight;
        avail = capacity - used;
    }

    @Override
    public String toString() {
        return "Bin{" +
                "capacity=" + capacity +
                ", used=" + used +
                ", avail=" + avail +
                ", items=" + items +
                '}';
    }
}
