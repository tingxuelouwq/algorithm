package com.kevin.datastructure.test;

/**
 * @Author kevin
 * @Date 2016/10/8 21:14
 */
public class Main3 {
    public static void main(String[] args) {
        new C();
    }
}

abstract class A {
    A() {
        System.out.println("A");
    }

    static {
        System.out.println("abstract");
    }

    public static void console() {}

    public int a = 10;
    protected int b = 10;
    private int c = 10;

    public void print() {}
    protected void print(int a) {}
    private void print(int a, int b) {}

    public abstract void out();
    protected abstract void out(int a);
//    private abstract void out(int a, int b);
}

interface B {
    public static final int a = 10;
}

class C extends A {
    C() {
        System.out.println("C");
    }

    @Override
    public void out() {

    }

    @Override
    protected void out(int a) {

    }
}
