package com.zefu.tiol.mapper.mysql;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface DataSynchroMapper {
	
	List<Map<String, Object>> getCompanyMember();
}
