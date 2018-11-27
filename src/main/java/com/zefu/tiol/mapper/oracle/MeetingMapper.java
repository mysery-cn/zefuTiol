package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import com.zefu.tiol.pojo.Attendee;
import com.zefu.tiol.pojo.Meeting;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingMapper {
	
	public Map<String, Object> getMeetingDetailById(@Param("meetingId")String meetingId,@Param("companyId")String companyId);

	/**
	 * 
	 * @功能描述 分页查询会议信息
	 * @time 2018年10月30日下午3:06:04
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> queryMeetingByPage(Map<String, Object> param);
	
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
	List<Map<String, Object>> queryMeetingList(Map<String, Object> parameter);
	
	/**
	   * @功能描述: TODO 查询平台会议列表总数
	   * @参数: @param parameter
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月3日 下午3:05:02
	 */
	int queryMeetingListTotalCount(Map<String, Object> parameter);

	/**
	 * @功能描述: 批量插入参会人员
	 * @参数: @param attendeeList 所有参会人员
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月10日 下午
	 */
	int batchInsertAttendee(List<Attendee> attendeeList);
	
	void updateMeeting(Map<String, Object> param);

	public List<Map<String, Object>> queryByDate(@Param("meetingDate")String meetingDate, @Param("meetingName")String meetingName, @Param("companyId")String companyId);

	public List<Map<String, String>> getMeetingDetailBySid(@Param("sid")String sid);

	/**
	 * @功能描述: 插入会议信息
	 * @参数: @param meeting 所有参会人员
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月13日 下午
	 */
	public int insertMeeting(Meeting meeting);

	/**
	 * @功能描述: 查询会议信息
	 * @参数: @param param 查询数组
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月13日 下午
	 */
	public Map<String,Object> queryMeetingInfo(Map<String,Object> param);
	
	/**
	   * @功能描述: TODO 保存会议记录
	   * @参数: @param meeting
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 上午9:52:21
	 */
	void saveMeeting(Meeting meeting);


	/**
	 * @功能描述: 根据会议删除参会人员
	 * @参数: @param meetingId 会议ID
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月13日 上午9:52:21
	 */
	int deleteAttendeeByMeetingId(@Param("meetingId")String meetingId);

	public List<Map<String, Object>> queryMeetingByIds(@Param("ids")String[] ids, @Param("companyId")String companyId);

	public List<Map<String, Object>> queryAttendeeList(@Param("meetingId")String meetingId);


	/**
	 * @功能描述: 根据会议查询参会人员
	 * @参数: @param
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月15日 上午9:52:21
	 */
	Map<String,Object> queryMeetingAttendee(Map<String,Object> param);


	List<Map<String,Object>> queryCompanyMeetingList(Map<String,Object> param);

	int countCompanyMeetingList(Map<String,Object> param);

	/**
	 * @功能描述: 根据主键查询所有会议
	 * @参数: @param
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月15日 上午9:52:21
	 */
	List<Map<String,Object>> queryMeetingListByIds(@Param("ids")String[] ids);

	/**
	 * @功能描述: 物理删除会议
	 * @参数: @param
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月15日 上午9:52:21
	 */
	int deleteMeetingById(@Param("meetingId")String meetingId);

	/**
	 * @功能描述: 更新会议状态
	 * @参数: @param
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月15日 上午9:52:21
	 */
	int updateMeetingStatus(Map<String,Object> param);

	/**
	 * @功能描述: 删除任务同时删除待办
	 * @参数: @param
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月15日 上午9:52:21
	 */
	int deleteSubjectTodoByMeetingId(@Param("meetingId") String meetingId);
}
