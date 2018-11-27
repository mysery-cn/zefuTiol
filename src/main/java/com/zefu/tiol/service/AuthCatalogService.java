package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.yr.gap.dal.plugin.child.Page;

public interface AuthCatalogService {

	List<Map<String, Object>> queryAuthCatalogList(Map<String, Object> param) throws Exception;
	
	List<Map<String, Object>> queryAuthCatalogListByPage(Map<String, Object> param, Page<Map<String, Object>> page) throws Exception ;
	
	int queryAuthCatalogListByPageCount(Map<String, Object> param);
	
	void saveAuthCatalog(Map<String, Object> param) throws Exception;
	
	void deleteAuthCatalog(Map<String, Object> param) throws Exception;
	
	List<Map<String, Object>> reloadForm(Map<String, Object> param);
	
	List<Map<String, Object>> getPermit(Map<String, Object> param);
}
