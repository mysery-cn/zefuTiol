package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * @功能描述 事项目录
 * @time 2018年10月26日下午3:08:10
 * @author Super
 *
 */
@Repository
public interface CatalogMapper {

	/**
	 * 
	 * @功能描述 查询一级事项目录信息
	 * @time 2018年10月26日下午3:08:47
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> queryCatalogLevel(@Param("param") Map<String, Object> param);
	
	/**
	   * @功能描述: TODO 查询事项目录数据
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月27日 上午11:01:44
	 */
	List<Map<String, Object>> queryCatalogTree(Map<String, Object> param);
	
	/**
	   * @功能描述: TODO 查询企业名称
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月27日 下午2:43:53
	 */
	String queryCompanyName(Map<String, Object> param);
	
	/**
	 * 
	 * @功能描述 查询大额度资金运作事项二级事项目录信息
	 * @time 2018年10月31日上午10:49:28
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
	List<Map<String, Object>> queryUmsCatalogList(Map<String, Object> param) throws Exception ;
	
	/**
	   * @功能描述: TODO 保存事项目录
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月1日 下午3:01:57
	 */
	void saveCatalog(Map<String, Object> param) throws Exception ;
	
	/**
	   * @功能描述: TODO 修改事项目录
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月1日 下午3:01:57
	 */
	void updateCatalog(Map<String, Object> param) throws Exception ;
	
	/**
	   * @功能描述: TODO 删除事项目录
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月1日 下午3:01:57
	 */
	void deleteCatalog(Map<String, Object> param) throws Exception ;
	
	/**
	   * @功能描述: TODO 查询事项目录
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月1日 下午3:01:57
	 */
	Map<String, Object> queryCatalog(Map<String, Object> param) throws Exception ;
	
	/**
	   * @功能描述: TODO 查询是否有子级
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月1日 下午3:50:39
	 */
	List<Map<String, Object>> queryCatalogListByPid(Map<String, Object> param);
	
	/**
	   * @功能描述: TODO 分页查询事项目录
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月1日 下午4:49:39
	 */
	List<Map<String, Object>> queryUmsCatalogListByPage(Map<String, Object> param);
	
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
	   * @功能描述: TODO 查询事项目录直属事项清单数量
	   * @参数: @param catalogId
	   * @参数: @param companyID
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月12日 上午10:47:04
	 */
	int queryCatalogItem(@Param(value="catalogId")String catalogId, @Param(value="companyID")String companyID);
	
	/**
	   * @功能描述: TODO 查询事项目录下属事项清单数量
	   * @参数: @param catalogId
	   * @参数: @param companyID
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月12日 上午10:53:54
	 */
	int queryCatalogItemChirld(@Param(value="catalogId")String catalogId, @Param(value="companyID")String companyID);
	
	/**
	   * @功能描述: TODO 查询所有事项目录To表格
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 下午7:48:50
	 */
	List<Map<String, String>> queryTableCatalogMessage(Map<String, Object> param);
	
	/**
	   * @功能描述: TODO 查询事项最大级别
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 下午8:49:51
	 */
	int queryCatalogLevelNumber(Map<String, Object> param);
	
	
	
	
}
