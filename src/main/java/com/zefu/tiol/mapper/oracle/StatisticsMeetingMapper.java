package com.zefu.tiol.mapper.oracle;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
   * @工程名 : szyd
   * @文件名 : StatisticsMeetingMapper.java
   * @工具包名：com.zefu.tiol.mapper.oracle
   * @功能描述: TODO 会议分类统计Dao
   * @创建人 ：林鹏
   * @创建时间：2018年10月25日 上午9:49:13
   * @版本信息：V1.0
 */
@Repository
public interface StatisticsMeetingMapper {
	
	/**
	   * @功能描述: TODO 查询会议分类统计结果
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月25日 上午9:52:31
	 */
	List<Map<String, Object>> queryMeetingDetail(Map<String, Object> parameter);
	
	/**
	   * @功能描述: TODO 查询当前年份决策会议情况
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月25日 下午4:59:45
	 */
	List<Map<String, Object>> queryDecisionMeetingDetail(Map<String, Object> parameter);
	
	/**
	   * @功能描述: TODO 查询当前年份决策议题情况
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月25日 下午7:53:09
	 */
	List<Map<String, Object>> queryDecisionSubjectDetail(Map<String, Object> parameter);
	
	/**
	 * 
	 * @功能描述 查询会议类型统计信息
	 * @time 2018年10月30日下午3:11:36
	 * @author Super
	 * @param parameter
	 * @return
	 *
	 */
	List<LinkedHashMap<String, Object>> queryMeetingClassDetail(Map<String, Object> parameter);
	
	/**
	 * 
	 * @功能描述 分页查询会议信息
	 * @time 2018年10月30日下午3:12:06
	 * @author Super
	 * @param parameter
	 * @param page
	 * @return
	 *
	 */
	List<Map<String, Object>> queryMeetingByPage(Map<String, Object> parameter);
	
	/**
	 * 
	 * @功能描述 获取会议信息总记录数
	 * @time 2018年10月30日下午3:13:10
	 * @author Super
	 * @param parameter
	 * @return
	 *
	 */
	int getMeetingTotalCount(Map<String, Object> parameter);
	
	/**
	 * 查询会议分类统计所有数据
	 * @return
	 */
	List<Map<String, Object>> listStatisticsMeeting();
	
}
