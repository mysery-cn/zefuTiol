package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.yr.gap.dal.plugin.child.Page;

/**
   * @工程名 : szyd
   * @文件名 : StatisticsItemService.java
   * @工具包名：com.zefu.tiol.service
   * @功能描述: TODO	事项清单统计Service
   * @创建人 ：林鹏
   * @创建时间：2018年10月25日 上午8:53:29
   * @版本信息：V1.0
 */
public interface StatisticsItemService {
	
	/**
	   * @功能描述: TODO 查询事项清单统计数据
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月25日 上午9:07:41
	 */
	Map<String, Object> queryItemDetail(Map<String, Object> parameter);
	
	/**
	   * @功能描述: TODO 查询行业事项汇总情况
	   * @参数: @param parameter
	   * @参数: @param page
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月26日 下午2:39:38
	 */
	List<Map<String, Object>> queryIndustryItemList(Map<String, Object> parameter, Page<Map<String, Object>> page);
	
	/**
	   * @功能描述: TODO 查询行业事项总数量
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月26日 下午2:40:00
	 */
	int queryIndustryItemCount(Map<String, Object> parameter);
	
	/**
	   * @功能描述: TODO 根据事项查询事项清单By企业
	   * @参数: @param parameter
	   * @参数: @param page
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月27日 上午11:38:58
	 */
	List<Map<String, Object>> queryItemListByCatalog(Map<String, Object> parameter, Page<Map<String, Object>> page);
	
	/**
	   * @功能描述: TODO 查询事项清单列表总数
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月27日 上午11:39:19
	 */
	int queryItemListByCatalogCount(Map<String, Object> parameter);
	
	/**
	 * 
	 * @功能描述 分页查询各企业事项清单信息
	 * @time 2018年10月31日上午11:28:22
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> queryItemSubjectByPage(Map<String, Object> param, Page<Map<String, Object>> page);
	
	/**
	 * 
	 * @功能描述 获取各企业事项清单总记录
	 * @time 2018年10月31日上午11:29:33
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	int getItemSubjectCount(Map<String, Object> param);
	
	/**
	 * 
	 * @功能描述 大额度资金二级目录议题统计
	 * @time 2018年10月31日下午5:14:09
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	Map<String, Object> queryCatalogSubjectFDetail(Map<String, Object> param);
	
	/**
	 * 
	 * @功能描述  大额度资金一级目录事项清单统计
	 * @time 2018年11月1日下午4:03:04
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	Map<String, Object> queryCatalogItemFDetail(Map<String, Object> param);
	
	/**
	 * 
	 * @功能描述 分页查询各企业事项清单信息
	 * @time 2018年10月31日上午11:28:22
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> queryCatalogItemByPage(Map<String, Object> param, Page<Map<String, Object>> page);
	
	/**
	 * 
	 * @功能描述 获取各企业事项清单总记录
	 * @time 2018年10月31日上午11:29:33
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	int getCatalogItemCount(Map<String, Object> param);

	/**
	   * @功能描述:  查询所有企业事项清单
	   * @创建人 ：林鹏
	   * @创建时间：2018/11/18 15:04
	*/
	List<Map<String, Object>> queryItemList(Map<String, Object> param, Page<Map<String, Object>> page);

	/**
	   * @功能描述:  查询所有企业事项清单总数
	   * @创建人 ：林鹏
	   * @创建时间：2018/11/18 15:04
	*/
	int queryItemListTotalCount(Map<String, Object> param);
}
