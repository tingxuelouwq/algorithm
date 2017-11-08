package com.kevin.algorithm.common;

import java.util.Scanner;

import com.kevin.datastructure.stack.MyArrayStack;

/**
 * 
 * @author:wangqi25
 * @date:2016-09-04 19:51:46
 * @desc:计算后缀表达式，支持+、-、*、/、^，输入以=结尾
 * @complexity:
 */
public class PostfixExpression {
	public static void main(String[] args) {
		System.out.println("Please input postfix expression: ");
		System.out.println("The result is: " + evalPostfix());
	}
	
	//输入示例：6 5 2 3 + 8 * + 3 + * 4 / 36 / 3 ^ =
	public static double evalPostfix() {
		MyArrayStack<Double> s = new MyArrayStack<>();
		
		String token = null;
		boolean isNumber = false;;
		double a, b, result = 0.0;
		try(Scanner in = new Scanner(System.in)) {
			while(in.hasNext()) {
				token = in.next();
				if(!token.equals("=")) {
					try {
						isNumber = true;
						result = Double.parseDouble(token);
					} catch (Exception e) {
						isNumber = false;
					}
					
					if(isNumber)
						s.push(result);
					else {
						switch (token) {
						case "+":
							b = s.pop();
							a = s.pop();
							s.push(a + b);
							break;
						case "-":
							b = s.pop();
							a = s.pop();
							s.push(a - b);
							break;
						case "*":
							b = s.pop();
							a = s.pop();
							s.push(a * b);
							break;
						case "/":
							b = s.pop();
							a = s.pop();
							s.push(a / b);
							break;
						case "^":
							b = s.pop();
							a = s.pop();
							s.push(Math.pow(a, b));
							break;
						default:
							System.out.println("invalid input: " + token);
							break;
						}
					}
				} else
					break;
			}
			
			return s.peek();
		}
	}
}
