package com.kevin.algorithm.common;

/**
 * 
 * @author:wangqi25
 * @date:2016-09-04 16:19:06
 * @desc:使用欧几里得算法求最大公约数
 * @complexity:O(logN)
 */
public class Gcd {
	public static void main(String[] args) {
		int m = 25;
		int n = 15;
		System.out.println("The gcd(" + m + ", " + n + ") is: " + gcd(m, n));
	}
	
	public static int gcd(int m, int n) {
		while(n != 0) {
			int r = m % n;
			m = n;
			n = r;
		}
		return m;
	}
}
