package com.zefu.tiol.util;

import org.springframework.util.StringUtils;

import com.zefu.tiol.constant.BusinessConstant;

public class TiolComnUtils {

	/**
	 * 
	 * @title: generalFileName
	 * @description: 生成文件名
	 * @param suffix
	 *            后缀名（不带点）
	 * @param delimiter
	 *            分隔符
	 * @param params
	 *            拼接文件名的参数
	 * @return
	 * @author: lxc
	 * @date: 2018年11月4日
	 */
	public static String generalFileName(String delimiter, String... params) {
		if (params == null || params.length == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer("");
		for (String param : params) {
			if (!StringUtils.isEmpty(delimiter)) {
				sb.append(delimiter).append(param);
			} else {
				sb.append(param);
			}
		}
		if (!StringUtils.isEmpty(delimiter)) {
			sb.delete(0, 1);
		}
		//sb.append(BusinessConstant.DOT_CODE).append(suffix);
		return sb.toString();
	}

	/**
	 * 
	 * @title: replaceSuffix
	 * @description: 替换文件后缀
	 * @param fileName
	 *            文件名
	 * @param newSuffix
	 *            新后缀
	 * @return
	 * @author: lxc
	 * @date: 2018年11月5日
	 */
	public static String replaceSuffix(String fileName, String newSuffix) {
		return StringUtils.split(fileName, "\\.")[0] + newSuffix;
	}
}
