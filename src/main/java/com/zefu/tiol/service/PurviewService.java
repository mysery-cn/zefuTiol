package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.yr.gap.dal.plugin.child.Page;

public interface PurviewService {
	
	/**
	 * 权限功能列表查询
	 * @param param
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> queryPurviewList(Map<String, Object> param) throws Exception;
	/**
	 * 权限功能列表查询-分页
	 * @param param
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> queryPurviewListByPage(Map<String, Object> param, Page<Map<String, Object>> page) throws Exception ;
	/**
	 * 根据主键id查询权限主表
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> queryPurviewById(Map<String, Object> param) throws Exception ;
	/**
	 * 列表记录数统计
	 * @param param
	 * @return
	 */
	int queryPurviewListByPageCount(Map<String, Object> param);
	/**
	 * 提交保存权限
	 * @param param
	 * @throws Exception
	 */
	void savePurview(Map<String, Object> param) throws Exception;
	/**
	 * 更新权限
	 * @param param
	 * @throws Exception
	 */
	void updatePurview(Map<String, Object> param) throws Exception;
	/**
	 * 删除权限
	 * @param param
	 * @throws Exception
	 */
	void deletePurview(Map<String, Object> param) throws Exception;
	/**
	 * 查询当前登录用户的所有权限（事项目录、会议类型、企业）
	 * @param param
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getPermit(Map<String, Object> param) throws Exception;
	
	/**
	 * 查询会议权限人员、角色或部门
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> listPurviewPerson(Map<String, Object> param);
}
