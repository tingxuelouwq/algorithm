package com.kevin.algorithm.randomized.sherwood.skiplist;

import com.kevin.algorithm.randomized.Random;

import java.util.Arrays;

/**
 * @Author kevin
 * @Date 2017/1/19 16:56
 * 由于跳跃表采用了随机化技术，它的每一种运算在最坏情况下的期望时间均为O(logn)，另外，跳跃表所占用的空间为O(n)
 */
public class SkipList<K extends Comparable<? super K>, V> {
    private float prob;     // 事先确定的概率，用于分配节点级别
    private int maxLevel;   // 跳跃表节点级别上界
    private int levels;     // 当前最大节点级别
    private int size;       // 当前元素个数
    private K tailKey; // 尾节点元素键值
    private SkipNode<K, V> head;  // 头结点
    private SkipNode<K, V> tail;  // 尾节点
    private SkipNode[] last;// 指针数组，存储搜索元素时与虚线相交的指针
    private Random r;       // 随机数产生器

    /**
     *
     * @param upperKey 最大节点的键
     * @param capacity 跳跃表容量
     * @param p 第i层的元素同时在第i+1层的概率为p
     */
    public SkipList(K upperKey, int capacity, float p) {
        prob = p;
        // 初始化跳跃表级别上界maxLevel = log1/p(n) = log(n) / log(1/p)
        maxLevel = (int) Math.round(Math.log(capacity) / Math.log(1 / prob)) - 1;
        tailKey = upperKey;
        // 创建头结点、尾节点和指针数组
        head = new SkipNode(null, null, maxLevel + 1);
        tail = new SkipNode(tailKey, null, 0);
        last = new SkipNode[maxLevel + 1];
        // 将跳跃表初始化为空表
        for (int i = 0; i <= maxLevel; i++) {
            head.next[i] = tail;
        }
        r = new Random();   // 初始化随机数产生器
    }

    public int size() {
        return size;
    }

    /**
     * 在跳跃表中搜索键为key的元素并返回，如果未找到则返回null
     * 算法search从最高级指针链开始搜索，一直到0级指针连。在每一级搜索中都尽可能地接近要搜索的元素。当算法从for循环
     * 退出时，指针正好处在待寻找元素的左边，因此算法末尾处，将当前指针与指针所指的下一个元素进行比较，即可确定要找
     * 的元素是否在跳跃表中。
     * @param key
     * @return
     */
    public SkipNode search(K key) {
        SkipNode<K, V> e = null;
        SkipNode<K, V> p = head;
        for (int i = levels; i >= 0; i--) {
            while (p.next[i].key.compareTo(key) < 0) {  // 在第i级链中搜索
                p = p.next[i];
            }
            last[i] = p;    // 存储于虚线相交的指针
        }
        e = p.next[0];
        return e.key.equals(key) ? e : null;
    }

    /**
     * 在跳跃表中插入一个元素
     * 在插入一个新节点时，算法put随机地为其分配一个节点级别。当要插入的元素键值越界时，将引发IllegalArgumentException
     * 异常；当插入元素成功时，返回与该元素相关联的旧值，即如果该元素已经存在则返回旧值，如果该元素不存在则返回null
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value) {
        if (tailKey.compareTo(key) <= 0) {   // 插入元素的键值越界，抛出异常
            throw new IllegalArgumentException("key is too large");
        }

        SkipNode<K, V> p = search(key);
        if (p != null) {    // 如果元素已经存在，则替换旧值并将其返回
            V oldValue = p.value;
            p.value = value;
            return oldValue;
        }

        // 如果元素不存在，确定新节点级别
        int level = level();
        if (level > levels) {
            for (int i = levels + 1; i <= level; i++) {
                last[i] = head; // 更新与虚线相交的指针
            }
            levels = level;     // 更新跳跃表最大节点级别
        }

        SkipNode<K, V> newNode = new SkipNode(key, value, level + 1);
        for (int i = 0; i <= level; i++) {
            newNode.next[i] = last[i].next[i];
            last[i].next[i] = newNode;
        }
        size++;
        return null;
    }

    /**
     * 从跳跃表中删除一个元素
     * 在remove算法执行过程中，如果没有找到键为key的元素则返回null，否则返回相应的元素的值
     * @param key
     * @return
     */
    public V remove(K key) {
        if (tailKey.compareTo(key) <= 0) {
            return null;
        }

        SkipNode<K, V> p = search(key);
        if (p == null) {    // 未找到该元素，返回null
            return null;
        }

        // 从跳跃表中删除节点
        for (int i = 0; i <= levels && last[i].next[i] == p; i--) {
            last[i].next[i] = p.next[i];
        }


        // 更新跳跃表当前最大节点级别
        while (levels > 0 && head.next[levels] == tail) {
            levels--;
        }

        size--;
        return p.value;
    }

    /**
     * 产生不超过maxLevel的随机级别
     * @return
     */
    private int level() {
        int level = 0;
        while (r.next() < prob) {
            level++;
        }
        return level < maxLevel ? level : maxLevel;
    }

    public void print() {
        SkipNode<K, V> p = head.next[0];
        for (; p != tail; p = p.next[0]) {
            System.out.println(p);
        }
    }

    static class SkipNode<K extends Comparable<? super K>, V> {
        K key;      // 存储集合中元素的键
        V value;    // 存储集合中元素的值
        SkipNode[] next;    // 指针数组，next[i]表示该节点的第i级指针

        public SkipNode(K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.next = new SkipNode[size];
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SkipNode<?, ?> skipNode = (SkipNode<?, ?>) o;

            if (key != null ? !key.equals(skipNode.key) : skipNode.key != null) return false;
            return value != null ? value.equals(skipNode.value) : skipNode.value == null;
        }

        @Override
        public int hashCode() {
            int result = key != null ? key.hashCode() : 0;
            result = 31 * result + (value != null ? value.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "SkipNode{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

    public static void main(String[] args) {
        SkipList<Integer, Integer> skipList = new SkipList<>(100, 20, 0.5f);
        int[] a = {5, 3, 2, 11, 7, 13, 19, 17, 23};
        for (int i = 0; i < a.length; i++) {
            skipList.put(a[i], 1);
        }
        skipList.print();
    }
}
