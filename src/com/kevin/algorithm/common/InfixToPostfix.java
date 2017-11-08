package com.kevin.algorithm.common;

import java.util.Scanner;

import com.kevin.datastructure.stack.MyArrayStack;

/**
 * 
 * @author:wangqi25
 * @date:2016-09-04 20:32:47
 * @desc:前缀表达式转后缀表达式，支持+、-、*、/、()，输入以=结尾
 * @complexity:
 */
public class InfixToPostfix {
	public static void main(String[] args) {
		System.out.println("Please input infix expression: ");
		infixToPostfix();
	}
	
	//思路：操作数直接输出，操作符要与栈中的操作符进行比较，即弹出栈中元素，直到碰到优先级更低的操作符，或者碰到(，或者栈为空
	public static void infixToPostfix() {
		MyArrayStack<Character> s = new MyArrayStack<>();
		String expression;
		Character token;
		int i = 0;
		
		try(Scanner sc = new Scanner(System.in)) {
			expression = sc.nextLine();
		}
		
		while((token = expression.charAt(i++)) != '=') {
			if(token >= 'a' && token <= 'z')
				System.out.print(token + " ");
			else {
				switch (token) {
				case '+':
				case '-':
					while(!s.isEmpty() && s.peek() != '(')
						System.out.print(s.pop() + " ");
					s.push(token);
					break;
				case '*':
				case '/':
					while(!s.isEmpty() && s.peek() != '+' && s.peek() != '-' && s.peek() != '(')
						System.out.print(s.pop() + " ");
					s.push(token);
					break;
				case '(':
					s.push(token);
					break;
				case ')':
					while(!s.isEmpty() && s.peek() != '(')
						System.out.print(s.pop() + " ");
					s.pop();
					break;
				default:
					break;
				}
			}
		}
		
		while(!s.isEmpty())
			System.out.print(s.pop() + " ");
		System.out.println();
	}
}
