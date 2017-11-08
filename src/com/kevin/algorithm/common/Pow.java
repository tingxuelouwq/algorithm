package com.kevin.algorithm.common;

/**
 * 
 * @author:wangqi25
 * @date:2016-09-04 16:24:20
 * @desc:幂运算
 * @complexity:O(logN)
 */
public class Pow {
	public static void main(String[] args) {
		int x = 3;
		int n = 3;
		System.out.println("pow(" + x + ", " + n + ") is: " + pow(x, n));
	}
	
	public static int pow(int x, int n) {
		if(n == 0)
			return 1;
		if(n == 1)
			return x;
		
		if(isEven(x))
			return pow(x * x, n / 2);
		else
			return pow(x * x, n / 2) * x;
	}

	private static boolean isEven(int x) {
		if(x % 2 == 0)
			return true;
		else
			return false;
	}
}
