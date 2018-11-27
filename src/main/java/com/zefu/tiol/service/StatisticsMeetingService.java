package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.yr.gap.dal.plugin.child.Page;

/**
   * @工程名 : szyd
   * @文件名 : StatisticsMeetingService.java
   * @工具包名：com.zefu.tiol.service
   * @功能描述: TODO 会议分类统计Service
   * @创建人 ：林鹏
   * @创建时间：2018年10月25日 上午9:43:22
   * @版本信息：V1.0
 */
public interface StatisticsMeetingService {
	
	/**
	   * @功能描述: TODO 查询会议分类统计结果
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月25日 上午9:51:41
	 */
	Map<String, Object> queryMeetingDetail(Map<String, Object> parameter);
	
	/**
	   * @功能描述: TODO 查询当前年份决策会议情况
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月25日 下午4:59:05
	 */
	Map<String, Object> queryDecisionMeetingDetail(Map<String, Object> parameter);
	
	/**
	   * @功能描述: TODO 查询当前年份决策议题情况
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月25日 下午5:39:54
	 */
	Map<String, Object> queryDecisionSubjectDetail(Map<String, Object> parameter);
	
	/**
	 * 
	 * @功能描述 查询会议类型统计信息
	 * @time 2018年10月30日下午3:11:36
	 * @author Super
	 * @param parameter
	 * @return
	 *
	 */
	Object queryMeetingClassDetail(Map<String, Object> parameter);
	
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
	List<Map<String, Object>> queryMeetingByPage(Map<String, Object> parameter, Page<Map<String, Object>> page);
	
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

}
