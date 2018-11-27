package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.yr.gap.dal.plugin.child.Page;

public interface EnterpriseTwoService {
	/**
	 * 重要人事任免会议分布统计图
	 * @param parameter
	 * @return
	 */
	Map<String, Object> queryMeetingDetail(Map<String, Object> parameter,List<Map<String, Object>> companyIdList);
	/**
	 * 2018年董事会议题情况统计图
	 * @param parameter
	 * @return
	 */
	Map<String, Object> querySubjectDetail(Map<String, Object> parameter,List<Map<String, Object>> companyIdList);
	/**
	 * 企干二局主页面议题列表查询
	 * @param param
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> queryMeetingTypeSubjectListByPage(Map<String, Object> param,Page<Map<String, Object>> page,List<Map<String, Object>> companyIdList);
	/**
	 *  企干二局主页面议题列表数量统计
	 * @param param
	 * @return
	 */
	int queryTotalCount(Map<String, Object> param,List<Map<String, Object>> companyIdList);
	/**
	 * 查询级别为2且编码已H开头的事项目录
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> queryCatalogLevel2andH(Map<String, Object> param);
	/**
	 *  查询二局内页董事会议题列表
	 * @param parameter
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> querySubjectPageList(Map<String, Object> parameter, Page<Map<String, Object>> page,List<Map<String, Object>> companyIdList);
	/**
	 *  二局内页董事会议题列表数量统计
	 * @param parameter
	 * @return
	 */
	int querySubjectTotalCount(Map<String, Object> parameter,List<Map<String, Object>> companyIdList);
	/**
	 * 重载二局内页董事会议题列表
	 * @param parameter
	 * @param page
	 * @return
	 */
	List<Map<String, Object>> reloadSubjectPageList(Map<String, Object> parameter, Page<Map<String, Object>> page,List<Map<String, Object>> companyIdList);
	/**
	 * 重载二局内页董事会议题数量统计
	 * @param parameter
	 * @return
	 */
	int reloadSubjectTotalCount(Map<String, Object> parameter,List<Map<String, Object>> companyIdList);
	
	/**
	 * 查询当前用户下所对应的企业集合
	 * @param parameter
	 * @return
	 */
	List<Map<String, Object>> getCurUserCompany(String deptId);
}
