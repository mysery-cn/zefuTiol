package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthMeetingTypeMapper {

	List<Map<String, Object>> queryAuthMeetingTypeTree(Map<String, Object> param);
	
	List<Map<String, Object>> queryAuthMeetingTypeListByPage(Map<String, Object> param);
	
	int queryAuthMeetingTypeListByPageCount(Map<String, Object> param);
	
	void saveAuthMeetingType(Map<String, Object> param) throws Exception ;
	
	void deleteAuthMeetingType(Map<String, Object> param) throws Exception ;
	
	List<Map<String, Object>> reloadFormUser(Map<String, Object> param);
	
	List<Map<String, Object>> reloadFormRole(Map<String, Object> param);
	
	List<Map<String, Object>> reloadFormOrg(Map<String, Object> param);
	
	List<Map<String, Object>> getPermit(Map<String, Object> param);

	Map<String, Object> getMeetingTypeById(@Param("typeId") String typeId);
}
