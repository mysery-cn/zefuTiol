package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface AuthCatalogMapper {

	List<Map<String, Object>> queryAuthCatalogList(Map<String, Object> param) throws Exception ;
	
	List<Map<String, Object>> queryAuthCatalogListByPage(Map<String, Object> param);
	
	int queryAuthCatalogListByPageCount(Map<String, Object> param);
	
	void saveAuthCatalog(Map<String, Object> param) throws Exception ;
	
	void deleteAuthCatalog(Map<String, Object> param) throws Exception ;
	
	List<Map<String, Object>> reloadFormUser(Map<String, Object> param);
	
	List<Map<String, Object>> reloadFormRole(Map<String, Object> param);
	
	List<Map<String, Object>> reloadFormOrg(Map<String, Object> param);
	
	List<Map<String, Object>> getPermit(Map<String, Object> param);
}
