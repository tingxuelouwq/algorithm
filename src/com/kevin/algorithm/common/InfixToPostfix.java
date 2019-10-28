package com.kevin.algorithm.common;

import java.util.Scanner;

import com.kevin.datastructure.stack.MyArrayStack;

/**
 * 
 * @author:wangqi25
 * @date:2016-09-04 20:32:47
 * @desc:前缀表达式转后缀表达式，支持+、-、*、/、()，输入以=结尾
 * a+b*c+(d*e+f)*g
 * @complexity:
 */
public class InfixToPostfix {
	public static void main(String[] args) {
		System.out.println("Please input infix expression: ");
//		infixToPostfix();
		infixToPrefix();
	}

    /**
     * 转换逻辑：
     * 从左往右扫描，操作数直接输出，操作符要与栈中元素进行比较，弹出栈中元素优先级
     * 大于等于当前操作符的元素，或者碰到(，或者栈为空。最终结果即为后缀表达式
     * 计算逻辑：
     * 从左往右扫描，操作数入栈，碰到操作符，则出栈两个元素a（第一个栈顶）、b（第二个栈顶），
     * 并计算b（左侧）、a（右侧）与操作符的值
     */
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

    /**
     * 转换逻辑：
     * 从右往左扫描，操作数直接输出，操作符要与栈中元素进行比较，弹出栈中元素优先级
     * 大于当前操作符的元素，或者碰到)，或者栈为空。最终结果的逆序即为前缀表达式
     * 计算逻辑：
     * 从右往左扫描，操作数入栈，碰到操作符，则出栈两个元素a（第一个栈顶）、b（第二个栈顶），
     * 并计算a（左侧）、b（右侧）和操作符的值
     */
    public static void infixToPrefix() {
        MyArrayStack<Character> s = new MyArrayStack<>();
        String expression;
        Character token;
        try(Scanner sc = new Scanner(System.in)) {
            expression = sc.nextLine();
        }
        int i = expression.length() - 1;
        StringBuilder builder = new StringBuilder();
        while(i >= 0) {
            token = expression.charAt(i--);
            if(token >= 'a' && token <= 'z')
                builder.append(token).append(" ");
            else {
                switch (token) {
                    case '+':
                        while(!s.isEmpty() && s.peek() != ')' && s.peek() != '-')
                            builder.append(s.pop()).append(" ");
                        s.push(token);
                        break;
                    case '-':
                        while(!s.isEmpty() && s.peek() != ')' && s.peek() != '+')
                            builder.append(s.pop()).append(" ");
                        s.push(token);
                        break;
                    case '*':
                    case '/':
                        s.push(token);
                        break;
                    case ')':
                        s.push(token);
                        break;
                    case '(':
                        while(!s.isEmpty() && s.peek() != ')')
                            builder.append(s.pop()).append(" ");
                        s.pop();
                        break;
                    default:
                        break;
                }
            }
        }

        while(!s.isEmpty())
            builder.append(s.pop()).append(" ");
        System.out.println(builder.reverse());
    }
}
