package com.zefu.tiol.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.common.util.StringUtils;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.StatisticsLegislationMapper;
import com.zefu.tiol.pojo.StatisticsLegislationVo;
import com.zefu.tiol.service.StatisticsLegislationService;

/**
 * 法规局功能模块实现类
 * 
 * @author：陈东茂
 * @date：2018年10月30日
 */
@Service("statisticsLegislationService")
public class StatisticsLegislationServiceImpl implements StatisticsLegislationService{
	
	@Autowired
	private StatisticsLegislationMapper legislationMapper;
	
	@Override
	public List<Map<String, Object>> listStatisticsYear() {
		return legislationMapper.listStatisticsYear();
	}

	@Override
	public List<Map<String, Object>> listLegislationStatistics(Map<String, Object> parameter, Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		int maxRow = page.getCurrentPage()*page.getPageSize();
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		List<Map<String, Object>> list = legislationMapper.listLegislationStatistics(parameter);
		return list;
	}

	@Override
	public int countLegislationStatistics(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return legislationMapper.countLegislationStatistics(parameter);
	}

	@Override
	public Map<String, Object> getLegislationChartData(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		List<StatisticsLegislationVo> legislationList = legislationMapper.getLegislationChartData(parameter);
		if(legislationList != null && legislationList.size() > 0){
			//拼接返回格式
			List<Integer> quantity = new ArrayList<Integer>();
			List<String> type = new ArrayList<String>();
			List<Integer> subtraction = new ArrayList<Integer>();
			List<Integer> total = new ArrayList<Integer>();
			for (StatisticsLegislationVo legislation : legislationList) {
				if (legislation != null) {
					type.add(legislation.getStatisticsType());
					quantity.add(legislation.getQuantity());
					subtraction.add(legislation.getTotal()-legislation.getQuantity());
					total.add(legislation.getTotal());
				}
			}
			data.put("type", type);
			data.put("quantity", quantity);
			data.put("subtraction", subtraction);
			data.put("total", total);
		}
		
		return data;
	}

	@Override
	public List<Map<String, Object>> listLegislationOption(Map<String, Object> parameter,
			Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int maxRow = page.getCurrentPage()*page.getPageSize();
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		String optionType = (String) parameter.get("optionType");
		String meetingTypes = (String) parameter.get("meetingTypes");
		if(!StringUtils.isBlank(meetingTypes) ){
			String[] types = meetingTypes.split(",");
			parameter.put("typeNames", types);
		}
		if (StringUtils.isBlank(optionType)) {
			return result;
		}else if("1".equals(optionType)||"2".equals(optionType)||"4".equals(optionType)||"5".equals(optionType)||"6".equals(optionType)){//党组党委会议题查询
			result = legislationMapper.listSubjectByOptionType(parameter);
		}else if("3".equals(optionType)){//党组党委会文件查询
			result = legislationMapper.listRegulationByOptionType(parameter);
		}
		return result;
	}

	@Override
	public int countLegislationOption(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		int count = 0;
		String optionType = (String) parameter.get("optionType");
		String meetingTypes = (String) parameter.get("meetingTypes");
		if(!StringUtils.isBlank(meetingTypes) ){
			String[] types = meetingTypes.split(",");
			parameter.put("typeNames", types);
		}
		if (StringUtils.isBlank(optionType)) {
			return count;
		}else if("1".equals(optionType)||"2".equals(optionType)||"4".equals(optionType)||"5".equals(optionType)||"6".equals(optionType)){//党组党委会议题查询
			count = legislationMapper.countSubjectByOptionType(parameter);
		}else if("3".equals(optionType)){//党组党委会文件查询
			count = legislationMapper.countRegulationByOptionType(parameter);
		}
		return count;
	}

}
