package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * @功能描述 决策议题统计
 * @time 2018年10月26日下午3:14:04
 * @author Super
 *
 */
@Repository
public interface StatisticsSubjectMapper {

	/**
	 * 
	 * @功能描述 查询决策议题统计
	 * @time 2018年10月26日下午3:14:21
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	public List<Map<String, Object>> queryStatSubjectList(@Param("param") Map<String, Object> param);
	
	/**
	 * 
	 * @功能描述 分页查询会议议题
	 * @time 2018年10月26日下午3:17:33
	 * @author Super
	 * @param param
	 * @param page
	 * @return
	 *
	 */
	public List<Map<String, Object>> queryMeetingSubjectListByPage(@Param("param") Map<String, Object> param);
	
	/**
	 * 
	 * @功能描述 获取会议议题总记录数
	 * @time 2018年10月30日下午3:58:49
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	public int getMeetingSubjectTotalCount(@Param("param") Map<String, Object> param);
	
	/**
	   * @功能描述: TODO 根据会议类型查询议题
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月29日 下午2:08:57
	 */
	public List<Map<String, Object>> queryMeetingTypeSubjectListByPage(Map<String, Object> param);
	
	/**
	   * @功能描述: TODO 根据会议类型查询议题总数
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月29日 下午2:55:31
	 */
	public int queryTotalCount(Map<String, Object> param);
	
	/**
	 * 
	 * @功能描述 查询本年度事项清单议题情况统计
	 * @time 2018年10月31日下午10:55:16
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	public List<Map<String, Object>> queryItemSubjectDetail(Map<String, Object> param);
	
	/**
	 * 查询统计表所有数据
	 * @return
	 */
	public List<Map<String, Object>> listStatisticsSubject();
	
	/**
	 * 查询决策议题异常统计表
	 * @return
	 */
	public List<Map<String, Object>> listStatisticsException();

	/**
	 * @功能描述:  查询所有数量
	 * @创建人 ：林鹏
	 * @创建时间：2018/11/17 19:49
	 */
	int querySubjectRoleNumber(Map<String, Object> param);


	/**
	 * 查询决策议题折线图表数据
	 * @return
	 */
	public Map<String, Object> getStatisticsSubjectChartData(Map<String, Object> param);
	
	/**
	 * 查询决策议题饼图数据
	 * @return
	 */
	public Integer getSubjectChartData(Map<String, Object> param);

	/**
	 * 查询议题事项分类列表
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> listSubjectClass(Map<String, Object> param);

	/**
	 * 查询议题分类总数
	 * @param param
	 * @return
	 */
	public int countSubjectClass(Map<String, Object> param);
}
