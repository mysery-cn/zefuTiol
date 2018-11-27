package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.zefu.tiol.pojo.StatisticsRegulation;

/**
 * @功能描述: TODO 制度类型分类统计
 * @创建人 ：林长怀
 * @创建时间：2018年10月25日 上午10:58:01 @版本信息：V1.0
 */
public interface StatisticsRegulationService {
	
	/**
	 * 根据制度类型汇总
	 * 
	 * @param industryId
	 *            行业Id
	 * @return List
	 */
	List<StatisticsRegulation> queryListByType(String industryId);
	
	/**
	 * 根据公司查询列表
	 * @param companyId
	 * @return
	 */
	List<StatisticsRegulation> queryListByCompanyId(String companyId);
	
	/**
	   * @功能描述: TODO 查询当前年份决策议题情况
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月25日 下午8:08:19
	 */
	Map<String, Object> queryRegulationConstructionDetail(Map<String, Object> parameter);
	
	
}
