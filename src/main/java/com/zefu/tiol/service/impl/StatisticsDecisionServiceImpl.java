package com.zefu.tiol.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.constant.StatisticsConstant;
import com.zefu.tiol.mapper.oracle.StatisticsDecisionMapper;
import com.zefu.tiol.pojo.StatisticsDecisionVo;
import com.zefu.tiol.service.StatisticsDecisionService;

/**
 * @功能描述: TODO 重大决策统计
 * @创建人 ：林长怀
 * @创建时间：2018年10月25日 上午8:34:01 @版本信息：V1.0
 */
@Service("statisticsDecisionService")
public class StatisticsDecisionServiceImpl implements StatisticsDecisionService {

	@Autowired
	private StatisticsDecisionMapper statisticsDecisionMapper;

	@Override
	public List<StatisticsDecisionVo> queryListBySource(String industryId) {
		// TODO Auto-generated method stub
		return statisticsDecisionMapper.queryListByType(industryId,StatisticsConstant.TYPE_SOURCE);
	}

	@Override
	public List<StatisticsDecisionVo> queryListByItem(String industryId) {
		// TODO Auto-generated method stub
		return statisticsDecisionMapper.queryListByType(industryId,StatisticsConstant.TYPE_ITEM);
	}
	
	@Override
	public List<Map<String, Object>> listCompanyDecision(Map<String, Object> parameter,
			Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		int maxRow = page.getCurrentPage()*page.getPageSize();
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		List<StatisticsDecisionVo> statisticsType= statisticsDecisionMapper.queryListByType(null,StatisticsConstant.TYPE_SOURCE);
		List<StatisticsDecisionVo> statisticsType2= statisticsDecisionMapper.queryListByType(null,StatisticsConstant.TYPE_ITEM);
		statisticsType.addAll(statisticsType2);
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		parameter.put("statisticsType", statisticsType);
		List<Map<String, Object>> list = statisticsDecisionMapper.listCompanyDecision(parameter);
		return list;
	}

	@Override
	public int countCompanyDecision(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return statisticsDecisionMapper.countCompanyDecision(parameter);
	}
	
	@Override
	public List<Map<String, Object>> listDecisionMeeting(Map<String, Object> parameter,
			Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		int maxRow = page.getCurrentPage()*page.getPageSize();
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		List<StatisticsDecisionVo> sourceType= statisticsDecisionMapper.queryListByType(null,StatisticsConstant.TYPE_SOURCE);
		List<StatisticsDecisionVo> itemType= statisticsDecisionMapper.queryListByType(null,StatisticsConstant.TYPE_ITEM);
		parameter.put("sourceType", sourceType);
		parameter.put("itemType", itemType);
		List<Map<String, Object>> result = statisticsDecisionMapper.listDecisionMeeting(parameter);		
		return result;
	}

	@Override
	public int countDecisionMeeting(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		List<StatisticsDecisionVo> sourceType= statisticsDecisionMapper.queryListByType(null,StatisticsConstant.TYPE_SOURCE);
		List<StatisticsDecisionVo> itemType= statisticsDecisionMapper.queryListByType(null,StatisticsConstant.TYPE_ITEM);
		parameter.put("sourceType", sourceType);
		parameter.put("itemType", itemType);
		return statisticsDecisionMapper.countDecisionMeeting(parameter);
	}

	@Override
	public List<Map<String, Object>> listDecisionSubject(Map<String, Object> parameter,Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		int maxRow = page.getCurrentPage()*page.getPageSize();
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		List<Map<String, Object>> result = statisticsDecisionMapper.listDecisionSubject(parameter);		
		return result;
	}

	@Override
	public int countDecisionSubject(Map<String, Object> parameter) {
		// TODO Auto-generated method stub		
		return statisticsDecisionMapper.countDecisionSubject(parameter);
	}
	
	@Override
	public List<Map<String, Object>> listDecisionCompany(Map<String, Object> parameter,
			Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		int maxRow = page.getCurrentPage()*page.getPageSize();
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		List<Map<String, Object>> result = statisticsDecisionMapper.listDecisionCompany(parameter);		
		return result;
	}

	@Override
	public int countDecisionCompany(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return statisticsDecisionMapper.countDecisionCompany(parameter);
	}
}
