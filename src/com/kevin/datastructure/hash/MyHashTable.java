package com.kevin.datastructure.hash;

/**
 * @Author kevin
 * @Date 2016/10/16 17:06
 */
public class MyHashTable<K, V> {
    private static final int DEFAULT_CAP = 10;

    private HashEntry[] table;
    private int currentSize;

    public MyHashTable() {
        this(DEFAULT_CAP);
    }

    public MyHashTable(int capacity) {
        if(capacity <= DEFAULT_CAP)
            capacity = DEFAULT_CAP;

        table = new HashEntry[capacity];
        currentSize = 0;
    }

    public V put(K key, V value) {
        if(currentSize == table.length)
            rehash();

        int index = hash(key);
        if(table[index] == null) {
            table[index] = new HashEntry(key, value);
            currentSize++;
        } else {
            HashEntry<K, V> p1 = table[index];
            HashEntry<K, V> p2 = table[index];

            for (; p1 != null && !p1.key.equals(key); ) {
                p2 = p1;
                p1 = p1.next;
            }

            if (p1 == null)
                p2.next = new HashEntry<K, V>(key, value);
            else
                p1.value = value;

            currentSize++;
        }
        return null;
    }

    public V get(K key) {
        int index = hash(key);
        HashEntry<K, V> p = table[index];

        for(; p != null && !p.key.equals(key);)
            p = p.next;

        return p == null ? null : p.value;
    }

    public int size() {
        return currentSize;
    }

    private void rehash() {
        HashEntry[] oldTable = table;
        table = new HashEntry[currentSize * 2];

        for(int i = 0; i < oldTable.length; i++) {
            HashEntry<K, V> entry = oldTable[i];

            while(entry != null) {
                put(entry.key, entry.value);
                entry = entry.next;
            }
        }
    }

    private int hash(K key) {
        int hashVal = key.hashCode();
        hashVal = hashVal % table.length;

        if(hashVal < 0)
            hashVal += table.length;

        return hashVal;
    }

    private static class HashEntry<K, V> {
        K key;
        V value;
        HashEntry<K, V> next;

        HashEntry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    public static void main(String[] args) {
        MyHashTable<Integer, String> hashtable = new MyHashTable<Integer, String>();

        hashtable.put(1, "aaa1");
        hashtable.put(2, "aaa2");
        hashtable.put(3, "aaa3");
        hashtable.put(4, "aaa4");
        hashtable.put(5, "aaa5");
        hashtable.put(6, "aaa6");
        hashtable.put(7, "aaa7");
        hashtable.put(8, "aaa8");
        hashtable.put(9, "aaa8");
        hashtable.put(10, "aaa10");
        hashtable.put(11, "aaa11");

        System.out.println(hashtable.get(1));
        System.out.println(hashtable.get(10));
        System.out.println(hashtable.size());
    }
}
