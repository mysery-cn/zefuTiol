package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 
 * @功能描述 会议信息及议题信息统一查询接口
 * @time 2018年11月4日上午10:12:37
 * @author Super
 *
 */
@Repository
public interface UnifyMapper {

	
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
	List<Map<String, Object>> queryMeetingByPage(Map<String, Object> param);
	
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
	List<Map<String, Object>> querySubjectByPage(Map<String, Object> param);
	
	/**
	 * 
	 * @功能描述 查询议题信息总记录数
	 * @time 2018年11月4日上午10:17:05
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	int getSubjectTotalCount(Map<String, Object> param);
	
}
