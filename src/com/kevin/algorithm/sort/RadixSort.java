package com.kevin.algorithm.sort;

public class RadixSort {
	public static void main(String[] args) {
		int[] num = {12, 104, 13, 7, 9};
		int d = 3;
//		radixSortLSD(num, d);
		radixSortMSD(num, d);
		print(num);
	}	
	
	private static void print(int[] a) {
		for(int i : a)
			System.out.print(i + " ");
	}
	
	/**
	 * 基数排序(LSD)
	 * 最低位优先法首先根据最低位关键码kd对所有对象进行一次排序；
	 * 再根据次第位关键码kd-1对上一次排序的结果进行再排序；
	 * 依次重复，直到根据关键码k1对最后一次排序的结果进行排序，得到一个有序序列	 * 
	 * 
	 * 10基数排序(LSD)的算法步骤
	 * 1、判断数据在各位的大小，排列数据；
	 * 2、根据1的结果，判断数据在十分位的大小，排列数据。如果数据在这个位置的余数相同，那么数据之间的顺序根据上一轮的排列顺序确定；
	 * 3、依次类推，继续判断数据在百分位、千分位......上面的数据重新排序，直到所有的数据在某一分位上数据都为0。
	 * 
	 * 以12、104、13、7、9为例
	 * 1、按个位数排序是12、13、104、7、9
	 * 2、再根据十位排序104、7、9、12、13
	 * 3、再根据百位排序7、9、12、13、104
	 * 
	 * @param num 待排序数组
	 * @param d 数组中最大元素的位数
	 * @complexity:时间复杂度为O(d(n+radix))，设待排序列为n个记录，d个关键码，关键码的取值范围为radix，则一趟排序
	 * 的时间复杂度为O(n+radix)，要进行d趟排序；空间复杂度为O(n+radix)
	 */
	public static void radixSortLSD(int[] num, int d) {
		final int radix = 10;
		int[] count = new int[radix];
		int[] bucket = new int[num.length];
		
		for(int k = 1; k <= d; k++) {
			for(int i = 0; i < count.length; i++)	//置空
				count[i] = 0;
			
			//对于每一位，其实就是一次计数排序
			for(int i = 0; i < num.length; i++)
				count[getDigit(num[i], k)]++;
			
			for(int i = 1; i < count.length; i++)
				count[i] += count[i - 1];
			
			for(int i = num.length - 1; i >= 0; i--) {	//从后往前扫描，保证稳定性
				int index = getDigit(num[i], k);
				count[index]--;
				bucket[count[index]] = num[i];
			}
			
			//copy back
			for(int i = 0; i < num.length; i++)
				num[i] = bucket[i];
		}
	}
	
	/**
	 * 基数排序(MSD)
	 * 最高优先位法通常是一个递归的过程：
	 * 先根据最高位关键码k1进行一次排序，得到若干对象组，对象组中的每个对象都有相同的关键码k1
	 * 再分别对每个对象组中的对象根据次高关键码k2进行排序，得到若干对象组，对象中的每个对象都有相同的关键码k1和k2
	 * 依次重复，直到关键码kd完成排序为止
	 * 最后，把所有对象组中的对象依次连接起来，得到排序序列
	 */
	public static void radixSortMSD(int[] num, int d) {
		radixSortMSD(num, 0, num.length - 1, d);
	}
	
	private static void radixSortMSD(int[] num, int begin, int end, int d) {
		final int radix = 10;
		int[] count = new int[radix];
		int[] bucket = new int[end - begin + 1];
		
		for(int i = begin; i <= end; i++)
			count[getDigit(num[i], d)]++;
		
		for(int i = 1; i < count.length; i++)
			count[i] += count[i - 1];

		for(int i = end; i >= begin; i--) {
			int index = getDigit(num[i], d);
			count[index]--;
			bucket[count[index]] = num[i];
		}
		
		//copy back
		for(int i = begin, j = 0; i <= end; i++, j++)
			num[i] = bucket[j];
		
		//对各桶中的数据进行再排序
		for(int i = 1; i < count.length; i++) {
			int p1 = begin + count[i - 1];
			int p2 = begin + count[i] - 1;
			if(p1 < p2 && d > 1)
				radixSortMSD(num, p1, p2, d - 1);
		}
	}

	/**
	 * 
	 * @author:kevin
	 * @date:2016-09-10 15:14:35
	 * @desc:
	 * @param x 数字
	 * @param d 获取x在第d位上的数
	 * @return
	 */
	private static int getDigit(int x, int d) {
		int[] a = {1, 1, 10, 100};	//最多支持三位数的基数排序
		return (x / a[d]) % 10;
	}
}
