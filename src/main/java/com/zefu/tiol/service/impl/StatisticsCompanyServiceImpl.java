package com.zefu.tiol.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.StatisticsCompanyMapper;
import com.zefu.tiol.pojo.StatisticsCompany;
import com.zefu.tiol.service.StatisticsCompanyService;

/**
 * @工程名 : szyd
 * @文件名 : DecisionServiceImpl.java
 * @工具包名：com.zefu.tiol.decision.service.impl
 * @功能描述: TODO 重大决策统计Service
 * @创建人 ：林鹏
 * @创建时间：2018年10月24日 上午9:33:08 @版本信息：V1.0
 */
@Service("statisticsCompanyService")
public class StatisticsCompanyServiceImpl implements StatisticsCompanyService {

	@Autowired
	private StatisticsCompanyMapper statisticsCompanyMapper;

	@Override
	public List<StatisticsCompany> queryListByPage(Map<String, Object> parameter, Page<Map<String, Object>> page) {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		List<StatisticsCompany> list = statisticsCompanyMapper.queryListByPage(parameter);
		return list;
	}

	@Override
	public List<StatisticsCompany> queryCompanyList(String industryId) {
		return statisticsCompanyMapper.queryCompanyList(industryId);
	}

	@Override
	public int getTotalCount(Map<String, Object> parameter) {
		return statisticsCompanyMapper.getTotalCount(parameter);
	}
	
	/**
	 * 查询企业信息
	 */
	@Override
	public Map<String, Object> queryCompanyDetail(Map<String, Object> parameter) {
		return statisticsCompanyMapper.queryCompanyDetail(parameter);
	}
	
	
	
}
