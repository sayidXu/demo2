package com.xutt.sky.portal.util;

import java.util.Random;

public class TablePkCodeUtil {
	/**
	 * 生成10位的随机数包含数字和大小写字母
	 * 
	 * @param parCode
	 * @return
	 */
	public static String getCodePk(Object parCode) {
		String str = "";
		if (parCode != null && !parCode.equals("")) {
			str = parCode.toString();
		}
		Random random = new Random();
		str += (char) (65 + random.nextInt(26));// 取得大写字母
		for (int i = 0; i < 8; i++) {
			str += String.valueOf(random.nextInt(10));
		}
		return str;
	}

	/**
	 * 生成30位的随机数，包含数字和大小写字母
	 * 
	 * @param parCode
	 * @return
	 */
	public static String getDataCodePk(Object parCode) {
		String str = "";
		if (parCode != null && !parCode.equals("")) {
			str = parCode.toString();
		}
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			str += (char) (65 + random.nextInt(26));// 取得大写字母
			str += String.valueOf(random.nextInt(10));
			str += (char) (97 + random.nextInt(26));// 取得小写字母
		}
		return changePosition(str.toCharArray());
	}

	/**
	 * 随机生成长度为6位的大写字母、数字、小写字母
	 * 
	 * @return
	 */
	public static String getUserPasswordLength6() {
		String str = "";
		Random random = new Random();
		for (int i = 0; i < 2; i++) {
			str += (char) (65 + random.nextInt(26));// 取得大写字母
			str += String.valueOf(random.nextInt(10));
			str += (char) (97 + random.nextInt(26));// 取得小写字母
		}
		return changePosition(str.toCharArray());
	}

	/**
	 * 随机生成6位的大小写字母
	 * 
	 * @return
	 */
	public static String getUserKeyLength6() {
		String str = "";
		Random random = new Random();
		for (int i = 0; i < 3; i++) {
			str += (char) (65 + random.nextInt(26));// 取得大写字母
			str += (char) (97 + random.nextInt(26));// 取得小写字母
		}
		return changePosition(str.toCharArray());
	}

	// 重排序
	public static String changePosition(char[] str) {
		Random random = new Random();
		char[] sss = null;
		for (int index = str.length - 1; index >= 0; index--) {
			// 从0到index处之间随机取一个值，跟index处的元素交换
			sss = exchange(str, random.nextInt(index + 1), index);
		}
		String resultStr = "";
		for (int i = 0; i < sss.length; i++) {
			resultStr += sss[i];
		}
		return resultStr;
	}

	// 交换位置
	private static char[] exchange(char[] str, int p1, int p2) {
		char temp = str[p1];
		str[p1] = str[p2];
		str[p2] = temp; // 更好位置
		return str;
	}

	public static void main(String arg[]) {
		String str = TablePkCodeUtil.getUserPasswordLength6();
		System.out.println(str + "    length=" + str.length());
		String strs = "J9aL6u";
		String s = TablePkCodeUtil.changePosition(strs.toCharArray());
		System.out.println(s);

	}
}
