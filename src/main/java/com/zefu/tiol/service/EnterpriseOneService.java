package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.yr.gap.dal.plugin.child.Page;

public interface EnterpriseOneService {

	/**
	 * 重要人事任免分布统计查询
	 * @param parameter
	 * @return
	 */
	Map<String, Object> queryMeetingDetail(Map<String, Object> parameter,List<Map<String, Object>> companyIdList);
	/**
	 * 涉及出资人重大决策议题统计查询
	 * @param parameter
	 * @return
	 */
	Map<String, Object> queryDecisionSubjectDetail(Map<String, Object> parameter,List<Map<String, Object>> companyIdList);
	/**
	 * 2018董事会议题情况统计查询
	 * @param request
	 * @return
	 */
	Map<String, Object> queryInvestorDetail(Map<String, Object> parameter,List<Map<String, Object>> companyIdList);
	/**
	 * 企干一局主页面议题列表查询
	 * @param param
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> queryMeetingTypeSubjectListByPage(Map<String, Object> param,Page<Map<String, Object>> page);
	/**
	 * 企干一局主页面议题列表数量统计
	 * @param param
	 * @return
	 */
	int queryTotalCount(Map<String, Object> param);
	/**
	 * 查询重要人事任免分布————企业会议列表
	 * @param request
	 * @return
	 */
	List<Map<String, Object>> queryMeetingByPage(Map<String, Object> parameter, Page<Map<String, Object>> page,List<Map<String, Object>> companyIdList);
	/**
	 * 查询重要人事任免分布————企业会议数量统计
	 * @param request
	 * @return
	 */
	int getMeetingTotalCount(Map<String, Object> parameter,List<Map<String, Object>> companyIdList);
	/**
	 * 查询涉及出资人重大决策议题————企业议题列表
	 * @param request
	 * @return
	 */
	List<Map<String, Object>> querySubjectPageList(Map<String, Object> parameter, Page<Map<String, Object>> page);
	/**
	 * 查询涉及出资人重大决策议题————企业议题列数量统计
	 * @param parameter
	 * @return
	 */
	int querySubjectTotalCount(Map<String, Object> parameter);
	
	/**
	 * 查询当前用户下所对应的企业集合
	 * @param parameter
	 * @return
	 */
	List<Map<String, Object>> getCurUserCompany(String instId);
	
	List<Map<String, Object>> querySubjectPageListDsh(Map<String, Object> parameter, Page<Map<String, Object>> page);
	
	int querySubjectDshTotalCount(Map<String, Object> parameter);
}
