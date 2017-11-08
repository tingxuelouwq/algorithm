package com.kevin.algorithm.common;

import java.util.Scanner;

import com.kevin.datastructure.stack.MyArrayStack;

/**
 * 
 * @author:wangqi25
 * @date:2016-09-04 17:01:16
 * @desc:检测平衡符号，支持[](){}的检测
 * @complexity:
 */
public class BalanceSymbol {
	public static void main(String[] args) {
		isBanlanceSymbols();
	}
	
	public static void isBanlanceSymbols() {
		MyArrayStack<String> s = new MyArrayStack<>();
		
		String symbol;
		//输入一系列符号，以空格分隔，以0结尾
		System.out.println("Please input symbols, ends with 0: ");
		try(Scanner in = new Scanner(System.in)) {
			while(in.hasNext()) {
				symbol = in.next();
				if(!symbol.equals("0")) {
					switch (symbol) {
					case "(":
					case "[":
					case "{":
						s.push(symbol);
						break;
					case ")":
						internalProcess(s, "(");
						break;
					case "]":
						internalProcess(s, "[");
						break;
					case "}":
						internalProcess(s, "{");
						break;
					default:
						System.out.println("invalid input: " + symbol);
						break;
					}
				} else
					break;
			}
		}
	}

	private static void internalProcess(MyArrayStack<String> s, String symbol) {
		String top = s.peek();
		if(top == null || !top.equals(symbol))
			System.out.println("lack of " + symbol);
		else
			s.pop();
	}
}
