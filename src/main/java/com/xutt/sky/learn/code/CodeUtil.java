package com.xutt.sky.learn.code;

import java.util.Arrays;

public class CodeUtil {
	public static void main(String[] args){
		byte i = -42;
		//int i = 0xd6;
		System.out.println(Integer.toHexString(i));
		System.out.println(Long.toHexString(i));
	}
	
	/**
	 * 二进制转十进制
	 * 
	 * @param binaryString
	 * @return
	 */
	public static String binaryToDecimal(String binaryString) {
		int i = Integer.parseInt(binaryString, 2);
		return String.valueOf(i);
	}
	
	/**
	 * 二进制转十六进制
	 * 
	 * @param binaryString
	 * @return
	 */
	public static String binaryToHexadecimal(String binaryString) {
		int i = Integer.parseInt(binaryString, 2);
		return String.valueOf(i);
	}
	
	/**
	 * 十进制
	 * @param bytes
	 */
	public static void printDecimal(byte[] bytes){
		System.out.println(Arrays.toString(bytes));
	}
	
	/**
	 * 二进制
	 * @param bytes
	 */
	public static void printBinary(byte[] bytes){
		for(int i=0;i<bytes.length;i++){
			System.out.print(Integer.toBinaryString(bytes[i])+" ");
		}
	}
	
	/**
	 * 十六进制
	 * @param bytes
	 */
	public static void printHexadecimal(byte[] bytes){
		for(int i=0;i<bytes.length;i++){
			String str = Integer.toHexString(bytes[i]);
			str = str.substring(6, 8);
			System.out.print(str +" ");
		}
	}
}
