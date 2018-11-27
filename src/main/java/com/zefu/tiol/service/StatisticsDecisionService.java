package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.pojo.StatisticsDecisionVo;

/**
 * @功能描述: TODO 重大决策统计
 * @创建人 ：林长怀
 * @创建时间：2018年10月25日 上午9:13:01 @版本信息：V1.0
 */
public interface StatisticsDecisionService {
	
	/**
	 * 根据任务来源，汇总“上级重要决定-习总书记的批示和指示”的会议数和议题数
	 * @param industryId 行业Id
	 * @return List
	 */
	List<StatisticsDecisionVo> queryListBySource(String industryId);

	/**
	 * 根据事项目录，汇总会议数和议题数
	 * @param industryId 行业Id
	 * @return List
	 */
	List<StatisticsDecisionVo> queryListByItem(String industryId);
	
	/**
	 * 查询公司重大措施统计列表
	 * @param parameter
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> listCompanyDecision(Map<String, Object> parameter, Page<Map<String, Object>> page);
	
	/**
	 * 公司重大措施统计列表总数
	 * @param parameter
	 * @return
	 */
	int countCompanyDecision(Map<String, Object> parameter);
	
	/**
	 * 查询重大措施会议分类列表
	 * @param parameter
	 * @param page
	 * @return
	 */
    List<Map<String, Object>> listDecisionMeeting(Map<String, Object> parameter, Page<Map<String, Object>> page);
	
	/**
	 * 公司重大措施会议分类列表总数
	 * @param parameter
	 * @return
	 */
	int countDecisionMeeting(Map<String, Object> parameter);
	
	/**
	 * 查询决策类型议题列表
	 * @param parameter
	 * @return
	 */
	List<Map<String, Object>> listDecisionSubject(Map<String, Object> parameter,Page<Map<String, Object>> page);
	
	/**
	 * 查询决策类型议题总数
	 * @param parameter
	 * @return
	 */
	int countDecisionSubject(Map<String, Object> parameter);
	
	/**
	 * 查询重大措施公司列表
	 * @param parameter
	 * @param page
	 * @return
	 */
    List<Map<String, Object>> listDecisionCompany(Map<String, Object> parameter, Page<Map<String, Object>> page);
	
	/**
	 * 公司重大措施公司列表总数
	 * @param parameter
	 * @return
	 */
	int countDecisionCompany(Map<String, Object> parameter);
	
}
