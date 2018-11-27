package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.pojo.Meeting;

import javax.servlet.http.HttpServletRequest;

/**
 * 会议信息接口类
 * @author：陈东茂
 * @date：2018年10月25日
 */
public interface MeetingService {

	Map<String, Object> getMeetingDetailById(String meetingId,String companyId);
	
	/**
	 * 
	 * @功能描述 分页查询会议信息
	 * @time 2018年10月30日下午3:07:10
	 * @author Super
	 * @param param
	 * @param page
	 * @return
	 *
	 */
	List<Map<String, Object>> queryMeetingByPage(Map<String, Object> param, Page<Map<String, Object>> page);
	
	/**
	 * 
	 * @功能描述 获取会议信息总记录数
	 * @time 2018年10月30日下午3:06:34
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	int getMeetingTotalCount(Map<String, Object> param);
	
	/**
	   * @功能描述: TODO 查询平台会议列表
	   * @参数: @param parameter
	   * @参数: @param page
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月3日 下午3:04:35
	 */
	List<Map<String, Object>> queryMeetingList(Map<String, Object> parameter, Page<Map<String, Object>> page) throws Exception ;
	
	/**
	   * @功能描述: TODO 查询平台会议列表总数
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月3日 下午3:05:02
	 */
	int queryMeetingListTotalCount(Map<String, Object> parameter) throws Exception ;
	
	/**
	   * @功能描述: TODO 查询厅局下属企业
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月3日 下午3:10:54
	 */
	List<String> queryCompanyByInst(Map<String, Object> parameter) throws Exception;

	/**
	 * @功能描述: 保存会议信息
	 * @参数: @param parameter
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月9日
	 */
	public Meeting saveMeeting(Map<String, Object> parameter) throws Exception;

	/**
	   * @功能描述: TODO 修改会议
	 */
	void updateMeeting(Map<String, Object> pojo2Map);

	/**
	   * @功能描述: 根据会议日期、会议名称、企业ID查找会议
	 */
	List<Map<String, Object>> queryByDate(String meetingDate, String meetingName, String companyId);

	/**
	   * @功能描述: 根据会议SID查找会议
	 */
	List<Map<String, String>> getMeetingDetailBySid(String meetingSid);

	/**
	 * @功能描述: 查询会议信息
	 * @参数: @param param
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月13日
	 */
	public Map<String,Object> queryMeetingInfo(Map<String,Object> param);


	
	String uploadMeeting(Map<String, Object> param, String companyId, String url, String userName);

	/**
	 * @功能描述: 查询会议参会人员
	 * @参数: @param param
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月13日
	 */
	public Map<String,Object> queryMeetingAttendee(Map<String,Object> param);

	/**
	 * @功能描述: 单位填报查询会议信息
	 * @参数: @param param
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月13日
	 */
	public List<Map<String, Object>> queryCompanyMeetingList(Map<String,Object> param, Page page);

	/**
	 * @功能描述: 单位填报统计会议记录
	 * @参数: @param param
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月13日
	 */
	public int countCompanyMeetingList(Map<String,Object> param);

	/**
	 * @功能描述: 删除会议
	 * @参数: @param param
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月13日
	 */
	public int deleteMeeting(Map<String,Object> param);

	/**
	 * @功能描述: 恢复删除会议
	 * @参数: @param param
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月18日 下午5:07:12
	 */
	public int recoverMeeting(Map<String,Object> param);

}
