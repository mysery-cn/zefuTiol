package com.zefu.tiol.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.LeaderRegulationMapper;
import com.zefu.tiol.pojo.RegulationVo;
import com.zefu.tiol.service.LeaderRegulationService;

@Service("leaderRegulationService")
public class LeaderRegulationServiceImpl implements LeaderRegulationService{

	@Autowired
	private LeaderRegulationMapper leaderRegulationMapper;
	
	@Override
	public List<RegulationVo> queryCompanyList(Map<String, Object> parameter, Page<Map<String, Object>> page) throws Exception {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		// TODO Auto-generated method stub
		return leaderRegulationMapper.queryCompanyList(parameter);
	}

	@Override
	public int getTotalCount(Map<String, Object> parameter) {
		return leaderRegulationMapper.getTotalCount(parameter);
	}

	@Override
	public List<Map<String, Object>> queryRegulationListByPage(Map<String, Object> parameter,
			Page<Map<String, Object>> page) throws Exception {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		List<Map<String, Object>> list = leaderRegulationMapper.queryRegulationListByPage(parameter);
		return list;
	}

	@Override
	public int getRegulationListTotalCount(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return leaderRegulationMapper.getRegulationListTotalCount(parameter);
	}

	@Override
	public List<Map<String, Object>> queryRegulationByPage(Map<String, Object> parameter,
			Page<Map<String, Object>> page) {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		List<Map<String, Object>> typelist = leaderRegulationMapper.queryList(parameter);
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		parameter.put("typelist", typelist);
		return leaderRegulationMapper.queryRegulationByPage(parameter);
	}

	@Override
	public int getRegulationTotalCount(Map<String, Object> parameter) {
		return leaderRegulationMapper.getRegulationTotalCount(parameter);
	}
	
	@Override
	public List<Map<String, Object>> queryList(Map<String, Object> param) {
		return leaderRegulationMapper.queryList(param);
	}

	@Override
	public List<Map<String, Object>> queryRegulationByType(Map<String, Object> parameter,
			Page<Map<String, Object>> page) {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		List<Map<String, Object>> list = leaderRegulationMapper.queryRegulationByType(parameter);
		return list;
	}

	@Override
	public int queryRegulationByTypeCount(Map<String, Object> parameter) {
		return leaderRegulationMapper.queryRegulationByTypeCount(parameter);
	}

}
