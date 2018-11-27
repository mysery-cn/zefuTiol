package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.yr.gap.dal.plugin.child.Page;

/**
 * 
 * @功能描述 事项目录
 * @time 2018年10月26日下午3:20:56
 * @author Super
 *
 */
public interface CatalogService {

	/**
	 * 
	 * @功能描述 查询一级事项目录信息
	 * @time 2018年10月26日下午3:21:01
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> queryCatalogLevel(Map<String, Object> param);
	
	/**
	   * @功能描述: TODO 查询事项列表By树形结构
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月27日 上午10:43:31
	 */
	Map<String, Object> queryCatalogTree(Map<String, Object> param);
	
	/**
	 * 
	 * @功能描述 查询大额度资金运作事项二级事项目录信息
	 * @time 2018年10月31日上午10:50:29
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> queryCatalogFLevel(Map<String, Object> param);
	
	/**
	   * @功能描述: TODO 平台-事项目录树查询
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月1日 下午2:09:05
	 */
	List<Map<String, Object>> queryUmsCatalogList(Map<String, Object> param) throws Exception;
	
	/**
	   * @功能描述: TODO 保存事项目录
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月1日 下午3:01:57
	 */
	void saveCatalog(Map<String, Object> param) throws Exception;
	
	/**
	   * @功能描述: TODO 修改事项目录
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月1日 下午3:01:57
	 */
	void updateCatalog(Map<String, Object> param) throws Exception;
	
	/**
	   * @功能描述: TODO 删除事项目录
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月1日 下午3:01:57
	 */
	void deleteCatalog(Map<String, Object> param) throws Exception;
	
	/**
	   * @功能描述: TODO 查询事项目录
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月1日 下午3:01:57
	 */
	Map<String, Object> queryCatalog(Map<String, Object> param) throws Exception;
	
	/**
	   * @功能描述: TODO 分页查询
	   * @参数: @param param
	   * @参数: @param page
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月1日 下午4:47:13
	 */
	List<Map<String, Object>> queryUmsCatalogListByPage(Map<String, Object> param, Page<Map<String, Object>> page) throws Exception ;
	
	/**
	   * @功能描述: TODO 分页查询总数
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月1日 下午4:55:11
	 */
	int queryUmsCatalogListByPageCount(Map<String, Object> param);
	
	/**
	   * @功能描述: TODO 判断用户是否有权限编辑
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月1日 下午7:31:38
	 */
	int queryCatalogRole(Map<String, Object> param);
	
	/**
	   * @功能描述: TODO 查询所有事项目录To表格
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 下午7:48:50
	 */
	Map<String, Object> queryTableCatalogMessage(Map<String, Object> param);
	
}
