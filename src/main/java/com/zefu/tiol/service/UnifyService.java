package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.yr.gap.dal.plugin.child.Page;

/**
 * 
 * @功能描述 会议信息及议题信息统一查询接口
 * @time 2018年11月4日上午10:19:27
 * @author Super
 *
 */
public interface UnifyService {
	
	/**
	 * 
	 * @功能描述 查询会议信息列表
	 * @time 2018年11月4日上午10:13:46
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> queryMeetingList(Map<String, Object> param);
	
	/**
	 * 
	 * @功能描述 分页查询会议信息
	 * @time 2018年11月4日上午10:16:46
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> queryMeetingByPage(Map<String, Object> param, Page<Map<String, Object>> page);
	
	/**
	 * 
	 * @功能描述 查询会议信息总记录数
	 * @time 2018年11月4日上午10:17:05
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	int getyMeetingTotalCount(Map<String, Object> param);
	
	/**
	 * 
	 * @功能描述 查询议题信息列表
	 * @time 2018年11月4日上午10:14:29
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> querySubjectList(Map<String, Object> param);
	
	/**
	 * 
	 * @功能描述 分页查询议题信息
	 * @time 2018年11月4日上午10:17:49
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> querySubjectByPage(Map<String, Object> param, Page<Map<String, Object>> page);
	
	/**
	 * 
	 * @功能描述 查询议题信息总记录数
	 * @time 2018年11月4日上午10:18:02
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	int getSubjectTotalCount(Map<String, Object> param);
	
}
