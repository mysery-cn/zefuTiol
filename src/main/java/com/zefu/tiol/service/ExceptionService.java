package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.yr.gap.dal.plugin.child.Page;

public interface ExceptionService {

	Map<String, Object> queryException(Map<String, Object> param) throws Exception;
	
	List<Map<String, Object>> querySubjectExceptionByPage(Map<String, Object> parameter, Page<Map<String, Object>> page);
	
	int getSubjectExceptionTotalCount(Map<String, Object> parameter);
	
	List<Map<String, Object>> queryExceptionType(Map<String, Object> param);
	
	List<Map<String, Object>> querySubjectListByPage(Map<String, Object> parameter, Page<Map<String, Object>> page);
	
	int querySubjectListTotalCount(Map<String, Object> parameter);
}
