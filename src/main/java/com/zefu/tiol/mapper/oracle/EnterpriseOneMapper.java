package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnterpriseOneMapper {

	List<Map<String, Object>> queryMeetingDetail(Map<String, Object> parameter);
	
	List<Map<String, Object>> queryDecisionSubjectDetail(Map<String, Object> parameter);
	
	List<Map<String, Object>> queryInvestorDetail(Map<String, Object> parameter);
	
	public List<Map<String, Object>> queryMeetingTypeSubjectListByPage(Map<String, Object> param);
	
	public int queryTotalCount(Map<String, Object> param);
	
	List<Map<String, Object>> queryMeetingByPage(Map<String, Object> parameter);
	
	int getMeetingTotalCount(Map<String, Object> parameter);
	
	List<Map<String, Object>> querySubjectPageList(Map<String, Object> parameter);
	
	int querySubjectTotalCount(Map<String, Object> parameter);
	
	List<Map<String, Object>> getCurUserCompany(@Param("instId") String instId);
	
	List<Map<String, Object>> querySubjectPageListDsh(Map<String, Object> parameter);
	
	int querySubjectDshTotalCount(Map<String, Object> parameter);
}
