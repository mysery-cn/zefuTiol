package com.zefu.tiol.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @功能描述 通用数据库查询方法
 * @time 2018年10月31日下午8:29:13
 * @author linch
 *
 */
public interface BizDBService {

	/**
	 * 
	 * @功能描述 查询指定表信息
	 * @param tableName 表名
	 * @param param 查询条件
	 * @return
	 *
	 */
	List<Map<String, Object>> get(String tableName, Map<String, Object> parameter);

	/**
	 * 
	 * @功能描述 单条记录保存
	 * @param tableName 表名
	 * @param map 需要保存的字段与参数
	 * @return
	 *
	 */
	void save(String tableName, Map<String, Object> map);

	/**
	 * 
	 * @功能描述 保存到不同表
	 * @param insertList 需要保存的字段与参数,每个Map需包含tableName和data
	 * @return
	 *
	 */
	void batchInsert(List<Map<String, Object>> insertList);
	
	/**
	 * 
	 * @功能描述 批量保存
	 * @param insertList 需要保存的字段与参数,每个Map需包含需要插入的字段和值
	 * @return
	 *
	 */
	void batchInsert(String tableName, List<Map<String, Object>> insertList);

	
	
}
