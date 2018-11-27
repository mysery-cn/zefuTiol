package com.zefu.tiol.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zefu.tiol.mapper.oracle.BizDBMapper;
import com.zefu.tiol.service.BizDBService;

/**
 * 
 * @功能描述 任务来源信息实现类
 * @time 2018年10月29日上午11:28:37
 * @author Super
 *
 */
@Service("bizDBService")
public class BizDBServiceImpl implements BizDBService {

	@Autowired
	private BizDBMapper bizDBMapper;

	@Override
	public List<Map<String, Object>> get(String tableName, Map<String, Object> parameter) {
		Map<String, Object> valuesMap = new HashMap<String, Object>();
		for (String key : parameter.keySet()) {
			if (parameter.get(key) != null) {
				valuesMap.put(key, key + "='" + parameter.get(key) + "'");
			}
		}
		return bizDBMapper.queryList(tableName, valuesMap);
	}

	@Override
	public void save(String tableName, Map<String, Object> map) {
		Map<String, Object> valuesMap = new HashMap<String, Object>();
		for (String key : map.keySet()) {
			if (map.get(key) != null) {
				valuesMap.put(key, map.get(key));
			}
		}
		bizDBMapper.save(tableName, valuesMap);
	}

	@Override
	public void batchInsert(List<Map<String, Object>> insertList) {
		for (Map<String, Object> map : insertList) {
			String tableName = (String) map.get("tableName");
			List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("Data");
			for (Map<String, Object> valuesMap : list) {
				save(tableName, valuesMap);
			}
		}
	}

	@Override
	public void batchInsert(String tableName, List<Map<String, Object>> insertList) {
		for (Map<String, Object> valuesMap : insertList) {
			save(tableName, valuesMap);
		}
	}
}
