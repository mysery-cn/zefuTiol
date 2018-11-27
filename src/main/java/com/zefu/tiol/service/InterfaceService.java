package com.zefu.tiol.service;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.pojo.Meeting;

import java.util.List;
import java.util.Map;

/**
 * 服务接口
 */
public interface InterfaceService {

//	Map<String, Object> getMeetingDetailById(String meetingId, String companyId);

	/**
	 *
	 * @功能描述 分页查询服务接口
	 * @param param
	 * @return
	 *
	 */
    void queryInterfaceByPage(Map<String, Object> param, Page<Map<String, Object>> page);

	/**
	 *
	 * @功能描述 获取服务接口总记录数
	 * @param param
	 * @return
	 *
	 */
	int getMeetingTotalCount(Map<String, Object> param);

	/**
	 * 新增服务接口
	 * @param parameter
	 */
    void saveInterface(Map<String, Object> parameter);

//	/**
//	   * @功能描述: TODO 查询平台会议列表
//	   * @参数: @param parameter
//	   * @参数: @param page
//	   * @参数: @return
//	   * @创建人 ：林鹏
//	   * @创建时间：2018年11月3日 下午3:04:35
//	 */
//	List<Map<String, Object>> queryMeetingList(Map<String, Object> parameter, Page<Map<String, Object>> page) throws Exception ;
//
//	/**
//	   * @功能描述: TODO 查询平台会议列表总数
//	   * @参数: @param parameter
//	   * @参数: @return
//	   * @创建人 ：林鹏
//	   * @创建时间：2018年11月3日 下午3:05:02
//	 */
//	int queryMeetingListTotalCount(Map<String, Object> parameter) throws Exception ;
//
//	/**
//	   * @功能描述: TODO 查询厅局下属企业
//	   * @参数: @param parameter
//	   * @参数: @return
//	   * @创建人 ：林鹏
//	   * @创建时间：2018年11月3日 下午3:10:54
//	 */
//	List<String> queryCompanyByInst(Map<String, Object> parameter) throws Exception;
//
//	/**
//	 * @功能描述: 保存会议信息
//	 * @参数: @param parameter
//	 * @参数: @return
//	 * @创建人 ：李缝兴
//	 * @创建时间：2018年11月9日
//	 */
//	public Meeting saveMeeting(Map<String, Object> parameter) throws Exception;
//
//	/**
//	   * @功能描述: TODO 修改会议
//	 */
//	void updateMeeting(Map<String, Object> pojo2Map);
//
//	/**
//	   * @功能描述: 根据会议日期、会议名称、企业ID查找会议
//	 */
//	List<Map<String, Object>> queryByDate(String meetingDate, String meetingName, String companyId);
//
//	/**
//	   * @功能描述: 根据会议SID查找会议
//	 */
//	List<Map<String, String>> getMeetingDetailBySid(String meetingSid);

}
