package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * @功能描述 董事会决策统计
 * @time 2018年10月26日下午3:12:33
 * @author Super
 *
 */
@Repository
public interface StatisticsDirectorateMapper {
	
	/**
	 * 
	 * @功能描述 查询是否经过董事会决策统计信息
	 * @time 2018年10月26日下午3:13:39
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	public List<Map<String, Object>> queryStatisticsDirectorateVia(@Param("param") Map<String, Object> param);
	
}
