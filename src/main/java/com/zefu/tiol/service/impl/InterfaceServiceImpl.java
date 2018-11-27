package com.zefu.tiol.service.impl;

import com.yr.gap.bam.service.IBizDataService;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.common.util.StringUtils;
import com.yr.gap.dal.plugin.child.Page;
import com.yr.gap.dal.service.DataAccessService;
import com.zefu.tiol.constant.CollectDataConstant;
import com.zefu.tiol.mapper.oracle.InterfaceMapper;
import com.zefu.tiol.mapper.oracle.ItemMapper;
import com.zefu.tiol.mapper.oracle.MeetingMapper;
import com.zefu.tiol.mapper.oracle.RegulationMapper;
import com.zefu.tiol.pojo.Attendee;
import com.zefu.tiol.pojo.Meeting;
import com.zefu.tiol.service.InterfaceService;
import com.zefu.tiol.service.MeetingService;
import com.zefu.tiol.util.MapTrunPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 服务接口
 */
@Service("interfaceService")
public class InterfaceServiceImpl implements InterfaceService {
	
	@Autowired
	private InterfaceMapper interfaceMapper;

//	@Override
//	public Map<String, Object> getMeetingDetailById(String meetingId,String companyId) {
//		Map<String, Object> detail = meetingmapper.getMeetingDetailById(meetingId,companyId);
//		//查询会议纪要/通知文件名称
//		Map<String, Object> paramData = new HashMap<String, Object>();
//		if(detail.get("recordFileId") != null){
//			paramData.put("fileIds", detail.get("recordFileId").toString().split(","));
//			List<Map<String, Object>> recordFile = regulationMapper.queryAuditFileList(paramData);
//			if(recordFile.size() > 0 && recordFile != null){
//				detail.put("recordFile", recordFile);
//			}else{
//				detail.put("recordFile", "");
//			}
//		}else{
//			detail.put("recordFile", "");
//		}
//
//		if(detail.get("noticeFileId") != null){
//			paramData.put("fileIds", detail.get("noticeFileId").toString().split(","));
//			List<Map<String, Object>> noticeFile = regulationMapper.queryAuditFileList(paramData);
//			if(noticeFile.size() > 0 && noticeFile != null){
//				detail.put("noticeFile", noticeFile);
//			}else{
//				detail.put("noticeFile", "");
//			}
//
//		}else{
//			detail.put("noticeFile", "");
//		}
//
//
//		return detail;
//	}

	@Override
	public void queryInterfaceByPage(Map<String, Object> param, Page<Map<String, Object>> page) {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		List<Map<String, Object>> list = interfaceMapper.queryInterfaceByPage(param);
		Integer count = interfaceMapper.getInterfaceTotalCount(param);

        page.setData(list);
        page.setTotalCount(count);
	}

	@Override
	public int getMeetingTotalCount(Map<String, Object> param) {
		return interfaceMapper.getInterfaceTotalCount(param);
	}

	@Override
	public void saveInterface(Map<String, Object> parameter) {
		parameter.put("INTERFACE_ID", UUID.randomUUID().toString().replaceAll("-", ""));
		interfaceMapper.saveInterface(parameter);
	}

//	/**
//	   * @功能描述: TODO 查询平台会议列表
//	   * @参数: @param parameter
//	   * @参数: @param page
//	   * @参数: @return
//	   * @创建人 ：林鹏
//	   * @创建时间：2018年11月3日 下午3:05:30
//	 */
//	@Override
//	public List<Map<String, Object>> queryMeetingList(Map<String, Object> parameter, Page<Map<String, Object>> page)  throws Exception{
//		//查询数据
//		int maxRow = page.getCurrentPage()*page.getPageSize();;
//		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
//		parameter.put("minRow", minRow);
//		parameter.put("maxRow", maxRow);
//		return meetingmapper.queryMeetingList(parameter);
//	}
//
//	/**
//	   * @功能描述: TODO 查询平台会议列表总数
//	   * @参数: @param parameter
//	   * @参数: @return
//	   * @创建人 ：林鹏
//	   * @创建时间：2018年11月3日 下午3:05:36
//	 */
//	@Override
//	public int queryMeetingListTotalCount(Map<String, Object> parameter) throws Exception{
//		return meetingmapper.queryMeetingListTotalCount(parameter);
//	}
//
//	/**
//	 * 查询厅局下属企业
//	 */
//	@Override
//	public List<String> queryCompanyByInst(Map<String, Object> parameter) throws Exception{
//		return itemMapper.queryCompanyByInst(parameter);
//	}
//
//	@Override
//	public Meeting saveMeeting(Map<String, Object> parameter) throws Exception {
//		Meeting meeting = new Meeting();
//		//数据转换
//		String fid = parameter.get("meetingId")+"";
//		if(fid == null || "".equals(fid) || "null".equals(fid)) fid = CommonUtil.getUUID();
//		meeting.setFid(fid);
//		meeting.setcModuleid(CollectDataConstant.MEETING_MODULE_ID);
//		meeting.setcModulename(CollectDataConstant.MEETING_MODULE_NAME);
//		meeting.setcBizid(CollectDataConstant.MEETING_BIZ_ID);
//		meeting.setcBizname(CollectDataConstant.MEETING_BIZ_NAME);
//		meeting.setcCreaterid(CollectDataConstant.CREATER_ID);
//		meeting.setcCreatername(CollectDataConstant.CREATER_NAME);
//		meeting.setcCreatedeptid(CollectDataConstant.CREATE_DEPT_ID);
//		meeting.setcCreatedeptname(CollectDataConstant.CREATE_DEPT_NAME);
//		meeting.setcCompanyid(parameter.get("companyId")+"");
//		meeting.setStatus("1");
//
//		meeting.setMeetingId(fid);
//		meeting.setMeetingName(parameter.get("meetingName")+"");
//		meeting.setTypeId(parameter.get("meetingType")+"");
//		meeting.setMeetingTime(parameter.get("meetingTime")+"");
//		meeting.setCompanyId(parameter.get("companyId")+"");
//		meeting.setModerator(parameter.get("moderator")+"");
//		meeting.setSummaryFileId(parameter.get("summaryFileId")+"");
//		meeting.setNoticeFileId(parameter.get("noticeFileId")+"");
//
//		//参会人员解析
//		List<Attendee> attendeeList = new ArrayList<Attendee>();
//		if(null != parameter.get("attendeeMember") && !"".equals(parameter.get("attendeeMember")+"")){
//			String[] users = parameter.get("attendeeMember").toString().split("、");
//			for (int i = 0; i < users.length; i++) {
//				String userName = users[i];
//				if(StringUtils.isBlank(userName)) continue;
//				userName = userName.replaceAll("）",")").replaceAll("（","(");
//				String reason = null;
//				//存在括号，该人员未参会
//				if(userName.indexOf(")") > -1) {
//					reason = userName.split("[(]")[1];
//					userName = userName.split("[(]")[0];
//					reason = reason.substring(0 ,reason.length()-1);
//				}
//				Attendee attendee = new Attendee();
//				attendee.setAttendeeId(CommonUtil.getUUID());
//				attendee.setMeetingId(fid);
//				attendee.setAttendeeName(userName);
//				attendee.setAttendFlag("1");
//				if(reason != null) {
//					attendee.setAttendFlag("0");
//					attendee.setReason(reason);
//				}
//				attendeeList.add(attendee);
//			}
//		}
//
//		meetingmapper.batchInsertAttendee(attendeeList);
//		int count = bizDataService.save("biz_" + CollectDataConstant.MEETING_BIZ_ID, MapTrunPojo.pojo2Map(meeting));
//		return meeting;
//	}
//
//
//	@Override
//	public void updateMeeting(Map<String, Object> parameter) {
//		meetingmapper.updateMeeting(parameter);
//	}
//
//	@Override
//	public List<Map<String, Object>> queryByDate(String meetingDate, String meetingName, String companyId) {
//		// TODO Auto-generated method stub
//		return meetingmapper.queryByDate(meetingDate,meetingName,companyId);
//	}
//
//	@Override
//	public List<Map<String, String>> getMeetingDetailBySid(String meetingSid) {
//		// TODO Auto-generated method stub
//		return meetingmapper.getMeetingDetailBySid(meetingSid);
//	}

}
