package com.zefu.tiol.mapper.mysql;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zefu.tiol.pojo.StatisticsDecisionVo;
import com.zefu.tiol.pojo.StatisticsRegulationVo;

@Repository
public interface DataSharingMapper {
	
	/**
	 * 删除表所有数据
	 * @param tableName
	 */
	void deleteTableData(@Param("tableName")String tableName);
	
	/**
	 * 插入重大决策统计数据
	 * @param list
	 */
	void insertForStatisticsDecision(@Param("tableName")String tableName,@Param("list")List<StatisticsDecisionVo> list);
	
	/**
	 * 插入制度分类统计数据
	 * @param tableName
	 * @param list
	 */
	void insertForStatisticsRegulation(@Param("tableName")String tableName,@Param("list")List<StatisticsRegulationVo> list);
	
	/**
	 * 插入事项清单汇总统计数据
	 * @param list
	 */
	void insertForStatisticsItem(@Param("tableName")String tableName,@Param("list")List<Map<String,Object>> list);
	
	/**
	 * 插入会议分类汇总统计数据
	 * @param list
	 */
	void insertForStatisticsMeeting(@Param("tableName")String tableName,@Param("list")List<Map<String,Object>> list);
	
	/**
	 * 插入会议分类汇总统计数据
	 * @param list
	 */
	void insertForStatisticsSubject(@Param("tableName")String tableName,@Param("list")List<Map<String,Object>> list);
	
	/**
	 * 插入会议分类汇总统计数据
	 * @param list
	 */
	void insertForStatisticsCpyRegulation(@Param("tableName")String tableName,@Param("list")List<Map<String,Object>> list);
	
	/**
	 * 插入会议分类汇总统计数据
	 * @param list
	 */
	void insertForStatisticsException(@Param("tableName")String tableName,@Param("list")List<Map<String,Object>> list);
}
