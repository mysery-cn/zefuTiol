package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zefu.tiol.pojo.RegulationVo;

@Repository
public interface LeaderRegulationMapper {

	List<RegulationVo> queryCompanyList(Map<String, Object> parameter);
	
	public int getTotalCount(Map<String, Object> parameter);
	
	public List<Map<String, Object>> queryRegulationListByPage(Map<String, Object> parameter);

	public int getRegulationListTotalCount(Map<String, Object> parameter);
	
	List<Map<String, Object>> queryList(Map<String, Object> param);
	
	List<Map<String, Object>> queryRegulationByPage(Map<String, Object> parameter);
	
	int getRegulationTotalCount(Map<String, Object> parameter);
	
	List<Map<String, Object>> queryRegulationByType(Map<String, Object> parameter);
	
	int queryRegulationByTypeCount(Map<String, Object> parameter);
}
