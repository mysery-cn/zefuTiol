package com.zefu.tiol.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zefu.tiol.mapper.oracle.StatisticsRegulationMapper;
import com.zefu.tiol.pojo.StatisticsRegulation;
import com.zefu.tiol.service.StatisticsRegulationService;

/**
 * @功能描述: TODO 重大决策统计
 * @创建人 ：林长怀
 * @创建时间：2018年10月25日 上午8:34:01 @版本信息：V1.0
 */
@Service("statisticsRegulationService")
public class StatisticsRegulationServiceImpl implements StatisticsRegulationService {

	@Autowired
	private StatisticsRegulationMapper statisticsRegulationMapper;

	@Override
	public List<StatisticsRegulation> queryListByType(String industryId) {
		// TODO Auto-generated method stub
		return statisticsRegulationMapper.queryListByType(industryId);
	}
	
	@Override
	public List<StatisticsRegulation> queryListByCompanyId(String companyId) {
		// TODO Auto-generated method stub
		return statisticsRegulationMapper.queryListByCompanyId(companyId);
	}
	
	/**
	 * 查询当前年份决策议题情况
	 */
	@Override
	public Map<String, Object> queryRegulationConstructionDetail(Map<String, Object> parameter) {
		Map<String, Object> data = new HashMap<String, Object>();
		//获取数据
		List<Map<String, Object>> itemDetail = statisticsRegulationMapper.queryRegulationConstructionDetail(parameter);
		if(itemDetail != null && itemDetail.size() > 0){
			//拼接返回格式
			List<String> name = new ArrayList<String>();
			List<String> Y = new ArrayList<String>();
			List<String> F = new ArrayList<String>();
			for (Map<String, Object> map : itemDetail) {
				name.add(map.get("name").toString());
				Y.add(map.get("Y").toString());
				F.add(map.get("F").toString());
			}
			data.put("name", name);
			data.put("Y", Y);
			data.put("F", F);
		}
		return data;
	}
	
	
	
}
