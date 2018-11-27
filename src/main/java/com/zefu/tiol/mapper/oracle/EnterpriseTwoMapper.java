package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnterpriseTwoMapper {

	List<Map<String, Object>> queryMeetingDetail(Map<String, Object> parameter);
	
	List<Map<String, Object>> querySubjectDetail(Map<String, Object> parameter);
	
	public List<Map<String, Object>> queryMeetingTypeSubjectListByPage(Map<String, Object> param);
	
	public int queryTotalCount(Map<String, Object> param);
	
	List<Map<String, Object>> queryCatalogLevel2andH(@Param("param") Map<String, Object> param);
	
	List<Map<String, Object>> querySubjectPageList(Map<String, Object> parameter);
	
	int querySubjectTotalCount(Map<String, Object> parameter);
	
	List<Map<String, Object>> getCurUserCompany(@Param("deptId") String deptId);
	
	List<Map<String, Object>> getCatalogIdsByName(@Param("catalogName") String catalogName);
	
	int reloadSubjectTotalCount(Map<String, Object> parameter);
	
	List<Map<String, Object>> getSubjectByCatalogIds(Map<String, Object> parameter);
}
