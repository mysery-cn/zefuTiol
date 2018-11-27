package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.yr.gap.dal.plugin.child.Page;

/**
 * 
 * @功能描述 董事会决策议题统计
 * @time 2018年10月24日下午3:07:51
 * @author Super
 *
 */
public interface StatisticsDirectorateService {

	/**
	 * 
	 * @功能描述 查询是否经过董事会决策统计信息
	 * @time 2018年10月24日下午3:08:01
	 * @author Super
	 * @param param
	 * @return
	 * @throws Exception
	 * 
	 */
	Map<String, Object> queryStatisticsDirectorateVia(Map<String, Object> param) throws Exception;
	
}
