package com.kevin.algorithm.greedy.huffman;

/**
 * 存储字符及其出现的次数的类，实现Comparable接口
 * @Author kevin
 * @Date 2016/10/24 18:34
 */
public class Data implements Comparable<Data> {
    private char c;
    private int frequency;

    public Data() {

    }

    public Data(char c, int frequency) {
        this.c = c;
        this.frequency = frequency;
    }

    @Override
    public int compareTo(Data o) {
        return frequency < o.frequency ? -1 : frequency == o.frequency ? 0 : 1;
    }

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
