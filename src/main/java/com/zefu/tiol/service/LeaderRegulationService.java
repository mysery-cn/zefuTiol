package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.pojo.RegulationVo;

public interface LeaderRegulationService {

	List<RegulationVo> queryCompanyList(Map<String, Object> parameter, Page<Map<String, Object>> page)  throws Exception ;
	
	int getTotalCount(Map<String, Object> parameter);
	
	List<Map<String, Object>> queryRegulationListByPage(Map<String, Object> parameter, Page<Map<String, Object>> page)  throws Exception ;

	int getRegulationListTotalCount(Map<String, Object> parameter);
	
	List<Map<String, Object>> queryRegulationByPage(Map<String, Object> parameter, Page<Map<String, Object>> page);
	
	int getRegulationTotalCount(Map<String, Object> parameter);
	
	List<Map<String, Object>> queryList(Map<String, Object> param);
	
	List<Map<String, Object>> queryRegulationByType(Map<String, Object> parameter, Page<Map<String, Object>> page);
	
	int queryRegulationByTypeCount(Map<String, Object> parameter);
}
