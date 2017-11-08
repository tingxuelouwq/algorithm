package com.kevin.algorithm.common;

/**
 * 
 * @author:wangqi25
 * @date:2016-09-04 10:33:40
 * @desc:判断一个数是否是素数
 * @complexity:O(N^(1/2))
 */
public class IsPrime {
	
	//思路：n是奇数(或者2)，并且n不能被3、5、7、...、n^(1/2)整除
	public static boolean isPrime(int n) {
		if(n == 2 || n == 3)
			return true;
		
		if(n == 1 || n % 2 == 0)
			return false;
		
		for(int i = 3; i * i <= n; i += 2)
			if(n % i == 0)
				return false;
		
		return true;
	}
}
