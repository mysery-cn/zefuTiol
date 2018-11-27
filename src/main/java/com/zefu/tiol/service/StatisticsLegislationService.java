package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.yr.gap.dal.plugin.child.Page;

/**
 * 法规局功能模块接口类
 * 
 * @author：陈东茂
 * @date：2018年10月30日
 */
public interface StatisticsLegislationService {
	
	/**
	 * 查询统计表年份列表
	 * @return
	 */
	List<Map<String, Object>> listStatisticsYear();
	
	/**
	 * 查询法规统计图数据
	 * @param parameter
	 * @return
	 */
	Map<String, Object> getLegislationChartData(Map<String, Object> parameter);
	
	/**
	 * 查询法规统计列表
	 * @param parameter
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> listLegislationStatistics(Map<String, Object> parameter,Page<Map<String, Object>> page);
	
	/**
	 * 查询法规统计总数
	 * @param parameter
	 * @return
	 */
	int countLegislationStatistics(Map<String,Object> parameter);
	
	/**
	 * 查询法规指标有关数据列表
	 * @param parameter
	 * @param page
	 * @return
	 */
    List<Map<String, Object>> listLegislationOption(Map<String, Object> parameter,Page<Map<String, Object>> page);
	
	/**
	 * 查询法规指标有关数据总数
	 * @param parameter
	 * @return
	 */
	int countLegislationOption(Map<String,Object> parameter);

}
