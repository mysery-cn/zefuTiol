package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.pojo.StatisticsCompany;

/**
   * @工程名 : szyd
   * @文件名 : DecisionService.java
   * @工具包名：com.zefu.tiol.decision.service
   * @功能描述: TODO 重大决策Service
   * @创建人 ：林鹏
   * @创建时间：2018年10月24日 上午9:33:28
   * @版本信息：V1.0
 */
public interface StatisticsCompanyService {
	
	/**
	   * TODO 分页查询
	   * @param industryId 行业ID
	   * @param page 分页信息
	   * @return List
	 */
	List<StatisticsCompany> queryListByPage(Map<String, Object> parameter, Page<Map<String, Object>> page)  throws Exception ;

	List<StatisticsCompany> queryCompanyList(String industryId);

	/**
	   * TODO 查询总数
	   * @param industryId 行业ID
	   * @return int
	 */
	int getTotalCount(Map<String, Object> parameter);
	
	/**
	   * @功能描述: TODO 查询企业信息
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月29日 下午4:33:06
	 */
	Map<String, Object> queryCompanyDetail(Map<String, Object> parameter);

}
