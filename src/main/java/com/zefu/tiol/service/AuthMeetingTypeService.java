package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.yr.gap.dal.plugin.child.Page;

public interface AuthMeetingTypeService {

	List<Map<String, Object>> queryAuthMeetingTypeTree(Map<String, Object> param);
	
	List<Map<String, Object>> queryAuthMeetingTypeListByPage(Map<String, Object> param, Page<Map<String, Object>> page) throws Exception ;
	
	int queryAuthMeetingTypeListByPageCount(Map<String, Object> param);
	
	void saveAuthMeetingType(Map<String, Object> param) throws Exception;
	
	void deleteAuthMeetingType(Map<String, Object> param) throws Exception;
	
	List<Map<String, Object>> reloadForm(Map<String, Object> param);
	
	List<Map<String, Object>> getPermit(Map<String, Object> param);
}
