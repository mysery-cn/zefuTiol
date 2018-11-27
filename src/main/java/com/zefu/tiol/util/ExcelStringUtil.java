package com.zefu.tiol.util;

import java.io.UnsupportedEncodingException;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelStringUtil {
	
	
	public static String checkStr(String str) {
		char c;
		char temp;
		Stack<Character> stack = new Stack<Character>();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if (c == '(' || c == '[' || c == '{') {
				stack.add(c);
			} else if (c == ')' || c == ']' || c == '}') {
				if (stack.isEmpty()) {
					sb.append("col:");
					sb.append(i);
					sb.append("  false,");
				}
				temp = stack.peek();
				switch (c) {
				case ')':
					if (temp == '(') {
						stack.pop();
						break;
					} else {
						sb.append(" col:");
						sb.append(i);
						sb.append("  false,");
					}
				case ']':
					if (temp == '[') {
						stack.pop();
						break;
					} else {
						sb.append(" col:");
						sb.append(i);
						sb.append("  false,");
					}
				case '}':
					if (temp == '{') {
						stack.pop();
						break;
					} else {
						sb.append("col:");
						sb.append(i);
						sb.append("  false,");
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 验证字符串内容是否包含下列非法字符<br>
	 * `~!#%^&*=+\\|{};:'\",<>/?○●★☆☉♀♂※¤╬の〆
	 * 
	 * @param content
	 *            字符串内容
	 * @return false代表不包含非法字符，true代表包含非法字符。
	 */
	public static boolean validateLegalString(String content) {
		// String illegal = "`~!#%^&*=+\\|{};:'\",<>/?○●★☆☉♀♂※¤╬の〆";
		String illegal = "$~！@#￥%……&*——《》`~!@#%^&*=+-\\|{};:'\"<>/?○●★☆☉♀♂※¤╬の〆";
		for (int i = 0; i < content.length(); i++) {
			for (int j = 0; j < illegal.length(); j++) {
				if (content.charAt(i) == illegal.charAt(j)) {
					// isLegalChar = content.charAt(i);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 校验一个字符是否是汉字
	 * 
	 * @param c
	 *            被校验的字符
	 * @return true代表是汉字
	 */
	public static boolean isChineseChar(char c) {
		try {
			return String.valueOf(c).getBytes("UTF-8").length > 1;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 验证是否是汉字或者0-9、a-z、A-Z
	 * 
	 * @param c
	 *            被验证的char
	 * @return true代表符合条件
	 */
	public static boolean isRightChar(char c) {
		return isChinese(c) || isWord(c);
	}

	/**
	 * 校验某个字符是否是a-z、A-Z、_、0-9
	 * 
	 * @param c
	 *            被校验的字符
	 * @return true代表符合条件
	 */
	public static boolean isWord(char c) {
		String regEx = "[\\w]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher("" + c);
		return m.matches();
	}

	/**
	 * 判定输入的是否是汉字
	 * 
	 * @param c
	 *            被校验的字符
	 * @return true代表是汉字
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 校验String是否全是中文
	 * 
	 * @param name
	 *            被校验的字符串
	 * @return true代表全是汉字
	 */
	public static boolean checkNameChese(String name) {
		boolean res = true;
		char[] cTemp = name.toCharArray();
		for (int i = 0; i < name.length(); i++) {
			if (!isChinese(cTemp[i])) {
				res = false;
				break;
			}
		}
		return res;
	}

	/**
	 * 校验String是否为中文或英文或数字
	 * 
	 * @param name
	 *            被校验的字符串
	 * @return true代表全是汉字
	 */
	public static boolean checkChsOrEh(String name) {
		boolean res = true;
		char[] cTemp = name.toCharArray();
		for (int i = 0; i < name.length(); i++) {
			if (!isRightChar(cTemp[i]) && cTemp[i] != ',' && cTemp[i] != '(' && cTemp[i] != ')') {
				// System.out.println("异常字符"+cTemp[i]);
				res = false;
				break;
			}
		}
		return res;
	}

	public static boolean checkChsOrEhChar(String name) {
		boolean res = true;
		char[] cTemp = name.toCharArray();
		for (int i = 0; i < name.length(); i++) {
			if (!isRightChar(cTemp[i])) {
				System.out.println("异常字符" + cTemp[i]);
				res = false;
				break;
			}
		}
		return res;
	}

	//字符串标准化
    public static String formatStr(String src){
    	String dest=src;
    	dest=dest.replaceAll("\n", ",").replaceAll("、", ",").replaceAll("（", "(").replaceAll("）", ")").replaceAll("，", ",");
    	dest=dest.replaceAll(" ", "");//去除空格
    	return dest;
    }

}
