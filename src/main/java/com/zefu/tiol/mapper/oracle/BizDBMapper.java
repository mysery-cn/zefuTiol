package com.zefu.tiol.mapper.oracle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * @功能描述 任务来源
 * @time 2018年10月30日下午3:01:25
 * @author Super
 *
 */
@Repository
public interface BizDBMapper {

	List<Map<String, Object>> queryList(@Param("tableName") String tableName,
			@Param("paramMap") Map<String, Object> paramMap);

	void save(@Param("tableName")String tableName,@Param("valuesMap")Map<String, Object> valuesMap);

	void update(@Param("tableName")String tableName, @Param("valuesMap")Map<String, Object> valuesMap, @Param("idName")String idName, @Param("id")String id);
	
}
