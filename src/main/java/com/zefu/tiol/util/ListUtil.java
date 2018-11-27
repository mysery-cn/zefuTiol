package com.zefu.tiol.util;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

/**
   * @工程名 : szyd
   * @文件名 : ListUtil.java
   * @工具包名：com.zefu.tiol.util
   * @功能描述: TODO 数组工具类
   * @创建人 ：林鹏
   * @创建时间：2018年11月13日 上午10:28:48
   * @版本信息：V1.0
 */
public class ListUtil {
	
	/**
	   * @功能描述: TODO 判断是否为空
	   * @参数: @param list
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 上午10:30:06
	 */
	public static boolean isBlank(List list){
		if(list.size() == 0 || list == null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	   * @功能描述: TODO 判断是否不为空
	   * @参数: @param list
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 上午10:30:06
	 */
	public static boolean isNotBlank(List list){
		if(list.size() > 0 && list != null){
			return true;
		}else{
			return false;
		}
	}
	
}
