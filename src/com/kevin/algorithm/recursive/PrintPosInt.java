package com.kevin.algorithm.recursive;

/**
 * 
 * @author:wangqi25
 * @date:2016-09-02 20:28:39
 * @desc:打印输出正整数
 */
public class PrintPosInt {
	public static void main(String[] args) {
		printPosInt(76543);
		System.out.println();
		
		System.out.println(8 - 8 / 3 * 3);
	}
	
	public static void printPosInt(int n) {
		if(n >= 10)
			printPosInt(n / 10);
		System.out.print(n % 10);
	}
}
