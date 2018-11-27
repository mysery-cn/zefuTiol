package com.zefu.tiol.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yr.gap.engine.util.StringUtils;

/**
 * 
 * @Description 日期工具类
 * @author linch
 *
 */
public class DateUtils {

	static String defaultPattern = "yyyy-MM-dd HH:mm:ss";
	static String[] defaultPatterns = new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH",
			"yyyy-MM-dd", "yyyy-M-d HH:mm:ss", "yyyy-M-d HH:mm", "yyyy-M-d HH", "yyyy-M-dd", "yyyy/MM/dd HH:mm:ss",
			"yyyy/MM/dd HH:mm", "yyyy/MM/dd HH", "yyyy/MM/dd", "yyyy/M/d HH:mm:ss", "yyyy/M/d HH:mm", "yyyy/M/d HH",
			"yyyy/M/d", "yyyyMMdd HH:mm:ss", "yyyyMMdd HH:mm", "yyyyMMdd HH", "yyyyMMdd", "yyyyMd HH:mm:ss",
			"yyyyMd HH:mm", "yyyyMd HH", "yyyyMd" };

	/**
	 * 获取当前时间字符串
	 * 
	 * @param formatString
	 *            日期字符串
	 * @param pattern
	 *            要求的日期格式
	 */
	public static String getFormatTime(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	/**
	 * 获取当前时间字符串
	 * 
	 * @param formatString
	 *            日期字符串
	 * @param pattern
	 *            要求的日期格式
	 */
	public static String getFormatTime(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date());
	}

	/**
	 * 获取当前时间字符串
	 * 
	 * @param formatString
	 *            日期字符串
	 * @param pattern
	 *            要求的日期格式
	 */
	public static String getFormatTime() {
		SimpleDateFormat format = new SimpleDateFormat(defaultPattern);
		return format.format(new Date());
	}

	/**
	 * 校验日期是否完全按照要求填写
	 * 
	 * @param formatString
	 *            日期字符串
	 * @param pattern
	 *            要求的日期格式
	 */
	public static boolean isDateMatch(String dateStr, String pattern) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(StringUtils.isEmpty(pattern) ? defaultPattern : pattern);
			Date date = format.parse(dateStr);
			// 判断传入的日期字符串，与 再转换之后的输出的日期字符串是否匹配，不匹配则表示传入的日期字符串未按要求填写
			if (!dateStr.equals(format.format(date))) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 匹配多种日期格式，解析日期
	 * 
	 * @param formatString
	 *            日期字符串
	 * @return Date
	 * 
	 */
	public static Date getDateByDateStr(String dateStr, String[] patterns) {
		Date date = null;
		if (null == patterns || patterns.length == 0) {
			patterns = defaultPatterns;
		}
		for (String pattern : patterns) {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			try {
				date = format.parse(dateStr);
				if (format.format(date).equals(dateStr)) {
					return date;
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat(defaultPattern);
		System.out.println(format.format(getDateByDateStr("2018/10/2", null)));
	}
}
