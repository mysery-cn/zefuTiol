package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.pojo.RegulationVote;

/**
 * 
 * @功能描述 制度信息
 * @time 2018年10月26日下午3:22:38
 * @author Super
 *
 */
public interface RegulationService {
	
	/**
	 * 
	 * @功能描述 查询制度信息
	 * @time 2018年10月26日下午3:23:47
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> queryRegulationList(Map<String, Object> param);
	
	List<Map<String, Object>> queryListByPage(Map<String, Object> parameter, Page<Map<String, Object>> page)  throws Exception ;

	int getTotalCount(String companyId);
	
	/**
	   * @功能描述: TODO 平台查询制度信息列表
	   * @参数: @param parameter
	   * @参数: @param page
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午3:28:57
	 */
	List<Map<String, Object>> querRegulationPageList(Map<String, Object> parameter, Page<Map<String, Object>> page) throws Exception;
	
	/**
	   * @功能描述: TODO 平台查询制度信息列表总数
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午3:29:15
	 */
	int querRegulationPageListCount(Map<String, Object> parameter) throws Exception;
	
	/**
	   * @功能描述: TODO 查询制度详情
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午6:41:42
	 */
	Map<String, Object> queryRegulationDetail(Map<String, Object> parameter);
	
	/**
	   * @功能描述: TODO 保存制度分类
	   * @参数: @param param
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午8:44:44
	 */
	void saveRegulationType(Map<String, Object> param)  throws Exception;
	
	/**
	 * 
	   * @功能描述: TODO 删除制度分类
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午8:45:30
	 */
	public void deleteRegulationType(Map<String, Object> param);
	
	/**
	 * 
	   * @功能描述: TODO 修改制度分类
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午8:45:30
	 */
	public void updateRegulationType(Map<String, Object> param);
	
	/**
	 * 
	   * @功能描述: TODO 查询制度分类
	   * @参数: @param param
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午8:45:30
	 */
	public Map<String, Object> queryRegulationTypeDetail(Map<String, Object> param);
	
	/**
	   * @功能描述: TODO 查询制度分类列表
	   * @参数: @param param
	   * @参数: @param page
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月7日 上午11:18:52
	 */
	List<Map<String, Object>> queryRegulationTypeListByPage(Map<String, Object> param, Page<Map<String, Object>> page) throws Exception;
	
	/**
	   * @功能描述: TODO 查询制度分类总数
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月7日 上午11:19:16
	 */
	int queryRegulationTypeTotalCount(Map<String, Object> param) throws Exception;

	/**
	   * @功能描述: TODO 更新制度
	 */
	void updateRegulation(Map<String, Object> pojo2Map);

	/**
	 * @功能描述: TODO 新增制度
	 * @param param
	 * @return
	 */
	int insertRegulation(Map<String, Object> param);

	/**
	 * 批量插入制度表决
	 * @param list
	 * @return
	 */
	public int batchInsertRegulationVote(List<RegulationVote> list);
	
	/**
	   * @功能描述: TODO 查询制度分类信息
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月15日 下午7:38:08
	 */
	List<Map<String, Object>> queryRegulationTypeList(Map<String, Object> parameter);

	String uploadRegulation(Map<String, Object> param, String companyId, String url, String userName);

}
