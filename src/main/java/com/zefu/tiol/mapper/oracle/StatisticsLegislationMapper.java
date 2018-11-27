package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zefu.tiol.pojo.StatisticsLegislationVo;

/**
 * 法规局功能模块数据操作类
 * 
 * @author：陈东茂
 * @date：2018年10月30日
 */
@Repository
public interface StatisticsLegislationMapper {
	
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
	List<StatisticsLegislationVo> getLegislationChartData(Map<String, Object> parameter);

	/**
	 * 查询法规统计列表
	 * @param parameter
	 * @return
	 */
	List<Map<String, Object>> listLegislationStatistics(Map<String,Object> parameter);
	
	/**
	 * 查询法规统计总数
	 * @param parameter
	 * @return
	 */
	int countLegislationStatistics(Map<String,Object> parameter);
	
	/**
	 * 查询法规会议议题指标有关数据列表
	 */
    List<Map<String, Object>> listSubjectByOptionType(Map<String,Object> parameter);
	
	/**
	 * 查询法规会议议题指标有关数据总数
	 * @param parameter
	 * @return
	 */
	int countSubjectByOptionType(Map<String,Object> parameter);
	
	/**
	 * 查询法规党委会类型制度列表
	 * @param parameter
	 * @return
	 */
    List<Map<String, Object>> listRegulationByOptionType(Map<String,Object> parameter);
	
	/**
	 * 查询法规党委会类型制度总数
	 * @param parameter
	 * @return
	 */
	int countRegulationByOptionType(Map<String,Object> parameter);
}
