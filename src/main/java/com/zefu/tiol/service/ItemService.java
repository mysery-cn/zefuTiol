package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.vo.TiolItemVO;

/**
 * 
 * @功能描述 会议类型接口类
 * @time 2018年10月28日下午5:01:44
 * @author Super
 *
 */
public interface ItemService {

	/**
	 * 
	 * @功能描述 查询会议类型信息
	 * @time 2018年10月30日下午3:06:34
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> queryList(Map<String, Object> param);
	
	/**
	 * 
	 * @功能描述  查询规划发展局事项清单信息
	 * @time 2018年11月1日上午9:13:24
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> queryItemType(Map<String, Object> param);
	
	/**
	   * @功能描述: TODO 查询事项清单列表
	   * @参数: @param param
	   * @参数: @param page
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 上午9:49:52
	 */
	List<Map<String, Object>> queryItemListByPage(Map<String, Object> param, Page<Map<String, Object>> page);
	
	/**
	   * @功能描述: TODO 查询事项清单列表总数
	   * @参数: @param param
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 上午9:50:10
	 */
	int queryItemListByPageCount(Map<String, Object> param);
	
	/**
	 * 
	 * @功能描述 分页查询事项清单信息
	 * @time 2018年11月2日下午8:59:34
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> queryCatalogItemByPage(Map<String, Object> param, Page<Map<String, Object>> page);
	
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
	   * @功能描述: TODO 查询国资委事项清单列表
	   * @参数: @param param
	   * @参数: @param page
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月6日 下午3:38:23
	 */
	List<Map<String, Object>> queryGzwItemList(Map<String, Object> param, Page<Map<String, Object>> page) throws Exception;
	
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
	 * 查询事项明细
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
	 * 查询事项关联会议
	 * @param param
	 * @return
	 * @author 李晓军
	 */
	List<Map<String, Object>> queryItemMeeting(Map<String, Object> param);



	/**
	   * @param companyId 
	 * @功能描述: 上报事项清单
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 林长怀
	   * @创建时间：2018年11月14日 下午15:19:13
	 */
	String uploadItem(Map<String, Object> param, String companyId, String userName);


	/**
	 * 根据param条件查询事项
	 * @param param
	 * @author 李缝兴
	 */
	List<Map<String, Object>> queryItemList(Map<String, Object> param);

	/**
	 * Excel导入事项保存
	 * @param list
	 * @return
	 * @author 李晓军
	 */
	String saveTiolItem(List<TiolItemVO> list,Map<String, Object> param);

	
}
