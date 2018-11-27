package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @功能描述 任务来源接口类
 * @time 2018年10月28日下午5:01:44
 * @author Super
 *
 */
public interface SourceService {

	/**
	 * 
	 * @功能描述 查询任务来源信息
	 * @time 2018年10月30日下午3:01:42
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> queryList(Map<String, Object> param) throws Exception;
	
	/**
	   * @功能描述: TODO 保存任务来源
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月4日 下午2:41:01
	 */
	void saveSource(Map<String, Object> param) throws Exception;
	
	/**
	   * @功能描述: TODO 修改任务来源
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月4日 下午2:41:01
	 */
	void updateSource(Map<String, Object> param) throws Exception;
	
	/**
	   * @功能描述: TODO 删除任务来源
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月4日 下午2:41:01
	 */
	void deleteSource(Map<String, Object> param) throws Exception;
	
	/**
	   * @功能描述: TODO 查询任务来源详情
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月4日 下午2:41:12
	 */
	Map<String, Object> querySourceDetail(Map<String, Object> param) throws Exception;
	
}
