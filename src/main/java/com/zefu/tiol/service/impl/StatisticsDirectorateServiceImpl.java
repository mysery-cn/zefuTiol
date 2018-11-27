package com.zefu.tiol.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zefu.tiol.mapper.oracle.StatisticsDirectorateMapper;
import com.zefu.tiol.service.StatisticsDirectorateService;

/**
 * 
 * @功能描述 董事会决策议题统计
 * @time 2018年10月24日下午3:08:52
 * @author Super
 *
 */
@Service("statisticsDirectorateService")
public class StatisticsDirectorateServiceImpl implements StatisticsDirectorateService {
	
	
	@Autowired
	private StatisticsDirectorateMapper statisticsDirectorateMapper;

	@Override
	public Map<String, Object> queryStatisticsDirectorateVia(
			Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		//获取数据
		List<Map<String, Object>> itemDetail = statisticsDirectorateMapper.queryStatisticsDirectorateVia(param);
		if(itemDetail != null && itemDetail.size() > 0){
			//拼接返回格式
			List<String> name = new ArrayList<String>();
			List<String> value = new ArrayList<String>();
			for (Map<String, Object> map : itemDetail) {
				if (map.get("unvia") != null) {
					String unvia = map.get("unvia").toString();
					if (Integer.parseInt(unvia) != 0) {
						name.add("未经过董事会决策");
						value.add(unvia);
					}
				}
				if (map.get("via") != null) {
					String via = map.get("via").toString();
					if (Integer.parseInt(via) != 0) {
						name.add("经过董事会决策");
						value.add(via);
					}
				}
			}
			data.put("name", name);
			data.put("value", value);
		}
		if(data.get("name") == null){
			data.put("name", "");
		}
		if(data.get("value") == null){
			data.put("value", "");
		}
		return data;
	}

}
