package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionMapper {

	List<Map<String, Object>> queryException(Map<String, Object> param) throws Exception ;
	
	int queryCompanyByRegulationType(Map<String, Object> param) throws Exception ;
	
	List<Map<String, Object>> querySubjectExceptionByPage(Map<String, Object> parameter);
	
	int getSubjectExceptionTotalCount(Map<String, Object> parameter);
	
	String queryExceptionType();
	
	List<Map<String, Object>> querySubjectListByPage(Map<String, Object> param) ;
	
	int querySubjectListTotalCount(Map<String, Object> parameter);
}
