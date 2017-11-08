package com.kevin.test;

/**
 * @Author kevin
 * @Date 2017/1/14 11:59
 */
public class Main2 {
    public static void main(String[] args) {
        final long mask = (1L << 48) - 1;
        System.out.println(Long.toHexString(mask));
    }
}
