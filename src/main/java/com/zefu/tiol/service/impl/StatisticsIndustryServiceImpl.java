package com.zefu.tiol.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zefu.tiol.mapper.oracle.StatisticsIndustryMapper;
import com.zefu.tiol.pojo.StatisticsIndustry;
import com.zefu.tiol.service.StatisticsIndustryService;

@Service("statisticsIndustryService")
public class StatisticsIndustryServiceImpl implements StatisticsIndustryService{

	@Autowired
	private StatisticsIndustryMapper statisticsIndustryMapper;
	
	@Override
	public List<StatisticsIndustry> queryIndustryList() throws Exception {
		// TODO Auto-generated method stub
		return statisticsIndustryMapper.queryIndustryList();
	}

	
}
