package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * @功能描述 事项清单
 * @time 2018年10月30日下午2:59:25
 * @author Super
 *
 */
@Repository
public interface ItemMapper {

	/**
	 * 
	 * @功能描述 查询事项清单信息
	 * @time 2018年10月30日下午3:00:10
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> queryList(Map<String, Object> param);

	/**
	 * 
	 * @功能描述 查询规划发展局事项清单信息
	 * @time 2018年11月1日上午9:11:51
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> queryItemType(Map<String, Object> param);

	/**
	 * @功能描述: TODO 根据组织ID查询相关类别单位
	 * @参数: @param param
	 * @参数: @return
	 * @创建人 ：林鹏
	 * @创建时间：2018年11月2日 上午9:52:04
	 */
	List<String> queryCompanyByInst(Map<String, Object> param);

	/**
	 * @功能描述: TODO 查询事项清单列表
	 * @参数: @param param
	 * @参数: @return
	 * @创建人 ：林鹏
	 * @创建时间：2018年11月2日 上午9:55:49
	 */
	List<Map<String, Object>> queryItemListByPage(Map<String, Object> param);

	/**
	 * @功能描述: TODO 查询事项清单列表总数
	 * @参数: @param param
	 * @参数: @return
	 * @创建人 ：林鹏
	 * @创建时间：2018年11月2日 上午10:06:37
	 */
	int queryItemListByPageCount(Map<String, Object> param);

	/**
	 * @功能描述: TODO 查询事项关联会议顺序
	 * @参数: @param param
	 * @参数: @return
	 * @创建人 ：林鹏
	 * @创建时间：2018年11月2日 上午10:51:05
	 */
	List<Map<String, Object>> queryItemMeetingListByPage(Map<String, Object> param);

	/**
	 * @功能描述: TODO 获取子级事项目录ID
	 * @参数: @param param
	 * @参数: @return
	 * @创建人 ：林鹏
	 * @创建时间：2018年11月2日 下午2:27:00
	 */
	List<String> queryChirldCatalogID(Map<String, Object> param);

	/**
	 * 
	 * @功能描述 分页查询事项清单信息
	 * @time 2018年11月2日下午8:59:34
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> queryCatalogItemByPage(Map<String, Object> param);

	/**
	 * 
	 * @功能描述 查询事项清单信息总记录数
	 * @time 2018年11月2日下午9:00:03
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	int getCatalogItemCount(Map<String, Object> param);

	/**
	 * 
	 * @功能描述 查询事项清单会议类型
	 * @time 2018年11月2日下午10:09:16
	 * @author Super
	 * @param parameter
	 * @return
	 *
	 */
	List<Map<String, Object>> queryItemMeetingOrderList(Map<String, Object> param);

	/**
	 * @功能描述: TODO 查询国资委事项清单列表
	 * @参数: @param param
	 * @参数: @param page
	 * @参数: @return
	 * @参数: @throws Exception
	 * @创建人 ：林鹏
	 * @创建时间：2018年11月6日 下午3:38:23
	 */
	List<Map<String, Object>> queryGzwItemList(Map<String, Object> param) throws Exception;

	/**
	 * @功能描述: TODO 查询国资委事项清单列表总数
	 * @参数: @param param
	 * @参数: @return
	 * @参数: @throws Exception
	 * @创建人 ：林鹏
	 * @创建时间：2018年11月6日 下午3:38:35
	 */
	int queryGzwItemListCount(Map<String, Object> param) throws Exception;

	/**
	 * @功能描述: TODO 查询国资委事项清单详情
	 * @参数: @param param
	 * @参数: @return
	 * @创建人 ：林鹏
	 * @创建时间：2018年11月6日 下午3:56:56
	 */
	Map<String, Object> queryGzwItem(Map<String, Object> param);

	/**
	 * @功能描述: TODO 删除国资委事项清单
	 * @参数: @param param
	 * @创建人 ：林鹏
	 * @创建时间：2018年11月6日 下午3:58:10
	 */
	void deleteGzwItem(Map<String, Object> param);

	/**
	 * @功能描述: TODO 保存国资委事项清单
	 * @参数: @param param
	 * @创建人 ：林鹏
	 * @创建时间：2018年11月6日 下午3:59:08
	 */
	void saveGzwItem(Map<String, Object> param);

	/**
	 * @功能描述: TODO 修改国资委事项清单
	 * @参数: @param param
	 * @创建人 ：林鹏
	 * @创建时间：2018年11月6日 下午3:59:39
	 */
	void updateGzwItem(Map<String, Object> param);

	/**
	 * @功能描述: TODO 修改事项清单
	 * @参数: @param param
	 * @创建时间：2018年11月9日 下午9:39:27
	 */
	void updateItem(Map<String, Object> param);
	
	/**
	 * 保存事项清单
	 * @param param
	 * @author 李晓军
	 */
	void saveItem(Map<String, Object> param);

	/**
	 * 查询事项清单明细
	 * @param param
	 * @return
	 * @author 李晓军
	 */
	Map<String, Object> queryItem(Map<String, Object> param);

	/**
	 * 查询会议类型
	 * @param param
	 * @return
	 * @author 李晓军
	 */
	List<Map<String, Object>> queryMeetingType(Map<String, Object> param);

	/**
	 * 更新事项明细
	 * @param param
	 * @author 李晓军
	 */
	void updateItemSimple(Map<String, Object> param);

	/**
	 * 删除事项关联会议
	 * @param param
	 * @author 李晓军
	 */
	void deleteItemMeeting(Map<String, Object> param);

	/**
	 * 保存事项关联会议
	 * @param param
	 * @author 李晓军
	 */
	void saveItemMeeting(Map<String, Object> param);

	/**
	 * 查询事项关联会议
	 * @param param
	 * @author 李晓军
	 * @return
	 */
	List<Map<String, Object>> queryItemMeeting(Map<String, Object> param);

	/**
	   * @功能描述: 查询事项列表
	   * @param param
	   * @param: companyId
	   * @return
	   * @创建人 林长怀
	   * @创建时间：2018年11月14日 下午15:21:13
	 */
	List<Map<String, Object>> queryItemByIds(@Param("items")String[] items,@Param("companyId")String companyId);

	/**
	 * 根据param条件查询事项
	 * @param param
	 * @author 李缝兴
	 */
	List<Map<String, Object>> queryItemList(Map<String, Object> param);
	
	/**
	 * 查询事项目录代码
	 * @param param
	 * @return
	 * @author 李晓军
	 */
	List<Map<String, String>> queryAllCatalogListByCode();
}
