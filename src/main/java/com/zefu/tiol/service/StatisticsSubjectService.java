package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yr.gap.dal.plugin.child.Page;

/**
 * 
 * @功能描述 决策议题统计
 * @time 2018年10月24日下午3:13:00
 * @author Super
 *
 */
public interface StatisticsSubjectService {

	/**
	 * 
	 * @功能描述 列表查询
	 * @time 2018年10月24日下午3:13:05
	 * @author Super
	 * @param param
	 * @return
	 * @throws Exception
	 *
	 */
	List<Map<String, Object>> querySubjectList(Map<String, Object> param) throws Exception;
	
	/**
	 * 
	 * @功能描述 分页查询
	 * @time 2018年10月24日下午3:13:12
	 * @author Super
	 * @param param
	 * @param page
	 * @return
	 * @throws Exception
	 *
	 */
	List<Map<String, Object>> queryMeetingSubjectListByPage(Map<String, Object> param, Page<Map<String, Object>> page) throws Exception;
	
	/**
	 * 
	 * @功能描述 获取总记录数
	 * @time 2018年10月30日下午3:56:13
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	int getMeetingSubjectTotalCount(@Param("param") Map<String, Object> param);
	
	/**
	   * @功能描述: TODO 根据会议类型查询议题
	   * @参数: @param param
	   * @参数: @param page
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月29日 下午2:06:46
	 */
	List<Map<String, Object>> queryMeetingTypeSubjectListByPage(Map<String, Object> param,Page<Map<String, Object>> page);
	
	/**
	   * @功能描述: TODO 根据会议类型查询议题总数
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月29日 下午2:54:49
	 */
	int queryTotalCount(Map<String, Object> param);

	/**
	   * @功能描述:  查询所有数量
	   * @创建人 ：林鹏
	   * @创建时间：2018/11/17 19:49
	*/
	int querySubjectRoleNumber(Map<String, Object> param);

	/**
	 * 
	 * @功能描述 查询本年度事项清单议题情况统计
	 * @time 2018年10月31日下午10:57:06
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	Map<String, Object> queryItemSubjectDetail(Map<String, Object> param);
	
	/**
	 * 获取决策议题折线图表数据
	 * @param param
	 * @return
	 */
	Map<String, Object> getStatisticsSubjectChartData(Map<String, Object> param);
	
	/**
	 * 获取决策议题饼图表数据
	 * @param param
	 * @return
	 */
	Map<String, Object> getSubjectChartData(Map<String, Object> param);

	/**
	 * 查询议题事项分类列表
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> listSubjectClass(Map<String, Object> param, Page<Map<String, Object>> page);

	/**查询议题事项分类总数
	 * 
	 * @param param
	 * @return
	 */
	int countSubjectClass(Map<String, Object> param);
}
