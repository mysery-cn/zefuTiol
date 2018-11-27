package com.zefu.tiol.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.yr.gap.bam.service.IBizDataService;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.common.util.StringUtils;
import com.yr.gap.common.vo.AttachmentVo;
import com.zefu.tiol.constant.BusinessConstant;
import com.zefu.tiol.constant.EBusinessType;
import com.zefu.tiol.pojo.Attendee;
import com.zefu.tiol.pojo.Meeting;
import com.zefu.tiol.util.DateUtils;
import com.zefu.tiol.util.FastDfsUtils;
import com.zefu.tiol.util.FileUtils;
import com.zefu.tiol.util.HttpClientUtils;
import com.zefu.tiol.util.MapTrunPojo;
import com.zefu.tiol.util.MeetingDataUtil;
import com.zefu.tiol.util.TiolComnUtils;

import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.IntegrationConfigMapper;
import com.zefu.tiol.mapper.oracle.ItemMapper;
import com.zefu.tiol.mapper.oracle.MeetingMapper;
import com.zefu.tiol.mapper.oracle.RegulationMapper;
import com.zefu.tiol.mapper.oracle.SubjectMapper;
import com.zefu.tiol.service.BizDBService;
import com.zefu.tiol.service.MeetingService;

import javax.annotation.Resource;

/**
 * 会议信息实现类
 * 
 * @author：陈东茂 @date：2018年10月25日
 */
@Service("meetingService")
public class MeetingServiceImpl implements MeetingService {

	private static Logger logger = LoggerFactory.getLogger(MeetingServiceImpl.class);

	@Autowired
	private MeetingMapper meetingmapper;

	@Autowired
	private SubjectMapper subjectMapper;

	@Autowired
	private RegulationMapper regulationMapper;

	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private IntegrationConfigMapper configMapper;

	@Resource(name = "bizDataService")
	private IBizDataService bizDataService;
	
	@Resource(name = "bizDBService")
	private BizDBService bizDBService;

	@Override
	public Map<String, Object> getMeetingDetailById(String meetingId, String companyId) {
		Map<String, Object> detail = meetingmapper.getMeetingDetailById(meetingId, companyId);
		// 查询会议纪要/通知文件名称
		Map<String, Object> paramData = new HashMap<String, Object>();
		if (detail.get("recordFileId") != null) {
			paramData.put("fileIds", detail.get("recordFileId").toString().split(","));
			List<Map<String, Object>> recordFile = regulationMapper.queryAuditFileList(paramData);
			if (recordFile.size() > 0 && recordFile != null) {
				detail.put("recordFile", recordFile);
			} else {
				detail.put("recordFile", "");
			}
		} else {
			detail.put("recordFile", "");
		}

		if (detail.get("noticeFileId") != null) {
			paramData.put("fileIds", detail.get("noticeFileId").toString().split(","));
			List<Map<String, Object>> noticeFile = regulationMapper.queryAuditFileList(paramData);
			if (noticeFile.size() > 0 && noticeFile != null) {
				detail.put("noticeFile", noticeFile);
			} else {
				detail.put("noticeFile", "");
			}

		} else {
			detail.put("noticeFile", "");
		}
		return detail;
	}

	@Override
	public List<Map<String, Object>> queryMeetingByPage(Map<String, Object> param, Page<Map<String, Object>> page) {
		int maxRow = page.getCurrentPage() * page.getPageSize();
		int minRow = (page.getCurrentPage() - 1) * page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		return meetingmapper.queryMeetingByPage(param);
	}

	@Override
	public int getMeetingTotalCount(Map<String, Object> param) {
		return meetingmapper.getMeetingTotalCount(param);
	}

	/**
	 * @功能描述: TODO 查询平台会议列表
	 * @参数: @param parameter
	 * @参数: @param page
	 * @参数: @return
	 * @创建人 ：林鹏
	 * @创建时间：2018年11月3日 下午3:05:30
	 */
	@Override
	public List<Map<String, Object>> queryMeetingList(Map<String, Object> parameter, Page<Map<String, Object>> page)
			throws Exception {
		// 查询数据
		int maxRow = page.getCurrentPage() * page.getPageSize();
		int minRow = (page.getCurrentPage() - 1) * page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		return meetingmapper.queryMeetingList(parameter);
	}

	/**
	 * @功能描述: TODO 查询平台会议列表总数
	 * @参数: @param parameter
	 * @参数: @return
	 * @创建人 ：林鹏
	 * @创建时间：2018年11月3日 下午3:05:36
	 */
	@Override
	public int queryMeetingListTotalCount(Map<String, Object> parameter) throws Exception {
		return meetingmapper.queryMeetingListTotalCount(parameter);
	}

	/**
	 * 查询厅局下属企业
	 */
	@Override
	public List<String> queryCompanyByInst(Map<String, Object> parameter) throws Exception {
		return itemMapper.queryCompanyByInst(parameter);
	}

	@Override
	public Meeting saveMeeting(Map<String, Object> parameter) throws Exception {
		Boolean isUpdate = true;
		// 数据转换
		String fid = parameter.get("meetingId") + "";
		if (fid == null || "".equals(fid) || "null".equals(fid)) {
			fid = CommonUtil.getUUID();
			isUpdate = false;
		}
		// 获取会议信息
		Meeting meeting = MeetingDataUtil.mapToMeeting(parameter, fid);
		// 参会人员解析
		List<Attendee> attendeeList = MeetingDataUtil.getAttendeeList(parameter, fid);

		// 判断是否为更新操作
		if (isUpdate) {
			meetingmapper.deleteAttendeeByMeetingId(meeting.getMeetingId());// 删除原参会人员
			meetingmapper.batchInsertAttendee(attendeeList);// 插入参会人员
			if(meeting.getUploadStatus() != null && "1".equals(meeting.getUploadStatus())){
				//已经上传 更改：上传状态为0，更新状态为：2
				meeting.setUploadStatus("0");//待上传
				meeting.setStatus("2");//修改状态
			}else{
				//未上传直接保存
			}
			meetingmapper.updateMeeting(MapTrunPojo.pojo2Map(meeting));// 更新会议信息
		} else {
			meetingmapper.batchInsertAttendee(attendeeList);// 插入参会人员
			int count = meetingmapper.insertMeeting(meeting);// 插入数据
		}
		return meeting;
	}

	@Override
	public void updateMeeting(Map<String, Object> parameter) {
		meetingmapper.updateMeeting(parameter);
	}

	@Override
	public List<Map<String, Object>> queryByDate(String meetingDate, String meetingName, String companyId) {
		// TODO Auto-generated method stub
		return meetingmapper.queryByDate(meetingDate, meetingName, companyId);
	}

	@Override
	public List<Map<String, String>> getMeetingDetailBySid(String meetingSid) {
		// TODO Auto-generated method stub
		return meetingmapper.getMeetingDetailBySid(meetingSid);
	}

	@Override
	public Map<String, Object> queryMeetingInfo(Map<String, Object> param) {
		return meetingmapper.queryMeetingInfo(param);
	}

	@Override
	public String uploadMeeting(Map<String, Object> param, String companyId, String urlStr, String userName) {
		urlStr += "/gapServlet?action=fsManageServlet&fUser=zfos&requesttype=download&fv=1&fid=";
		String[] ids = (String[]) param.get("ids");
		// 获取路径
		String outPath = this.getClass().getClassLoader().getResource("/").getPath() + "../../uploadFile";
		// 文件名
		String xmlFileName = TiolComnUtils.generalFileName(BusinessConstant.UNDERLINE_COODE,
				EBusinessType.MEETING.getCode(), BusinessConstant.VERSION, DateUtils.getFormatTime("yyyyMMdd"));
		// 创建文件夹

		File filePath = new File(outPath + File.separator + xmlFileName);
		if(filePath.exists()) FileUtils.delAllFile(filePath.getPath());
		filePath.mkdirs();


		List<Map<String, Object>> meetingList = meetingmapper.queryMeetingByIds(ids, companyId);
		int success = 0;
		for (Map<String, Object> meeting : meetingList) {
			JSONObject meetingJson = new JSONObject();
			meetingJson.put("meeting_id", meeting.get("MEETING_ID"));
			meetingJson.put("meeting_name", meeting.get("MEETING_NAME"));
			meetingJson.put("meeting_time", meeting.get("MEETING_TIME"));
			meetingJson.put("moderator", meeting.get("MODERATOR"));
			meetingJson.put("meeting_type_name", meeting.get("TYPE_NAME"));

			// 读取会议纪要文件
			String summaryFileId = String.valueOf(meeting.get("SUMMARY_FILE_ID"));
			if (StringUtils.isBlank(summaryFileId)) {
				logger.warn("会议[" + meeting.get("MEETING_NAME") + "]未找到会议纪要文件");
				continue;
			}
			String summaryFileName = String.valueOf(meeting.get("SUMMARY_FILE_NAME"));
			String noticeFileId = String.valueOf(meeting.get("NOTICE_FILE_ID"));
			if (StringUtils.isBlank(noticeFileId)) {
				logger.warn("会议[" + meeting.get("MEETING_NAME") + "]未找到会议通知文件");
				continue;
			}
			String noticeFileName = String.valueOf(meeting.get("NOTICE_FILE_NAME"));
			
			String status = meeting.get("STATUS").toString();
			String operType = "add";
			if (status.equals("0")) {
				operType = "del";
			} else if (status.equals("2")) {
				operType = "edit";
			}
			meetingJson.put("oper_type", operType);

			// 获取参会人员列表
			JSONArray attendeeJsonArray = new JSONArray();
			List<Map<String, Object>> attendeeList = meetingmapper
					.queryAttendeeList(String.valueOf(meeting.get("MEETING_ID")));
			for (Map<String, Object> attendee : attendeeList) {
				JSONObject attendeejson = new JSONObject();
				attendeejson.put("attendee_name", attendee.get("ATTENDEE_NAME"));
				if (StringUtils.isNotBlank(String.valueOf(attendee.get("REASON")))) {
					attendeejson.put("reason", attendee.get("REASON"));
				}
				attendeeJsonArray.add(attendeejson);
			}
			meetingJson.put("attendee_list", new JSONObject().put("attendee", attendeeJsonArray));

			// 获取议题列表
			Map<String, Object> conParam = new HashMap<String, Object>();
			conParam.put("meetingId", meeting.get("MEETING_ID"));
			List<Map<String, Object>> subjectList = subjectMapper.getSubjectByMeetingId(conParam);
			JSONArray subjectJsonArray = new JSONArray();
			for (Map<String, Object> subject : subjectList) {
				JSONObject subjectJson = new JSONObject();
				subjectJson.put("subject_id", subject.get("subjectId"));
				subjectJson.put("subject_name", subject.get("subjectName"));
				subjectJson.put("source_name", subject.get("sourceName"));
				subjectJson.put("special_name", subject.get("specialName"));
				subjectJson.put("subject_result", subject.get("subjectResult"));
				subjectJson.put("remark", subject.get("remark"));

				// passFlag判断
				String passFlag = String.valueOf(subject.get("passFlag"));
				if ("0".equals(passFlag)) {
					passFlag = "否决";
				} else if ("1".equals(passFlag)) {
					passFlag = "通过";
				} else if ("2".equals(passFlag)) {
					passFlag = "缓议";
				}
				subjectJson.put("pass_flag", passFlag);

				// approvalFlag判断
				String approvalFlag = String.valueOf(subject.get("approvalFlag"));
				if ("0".equals(approvalFlag)) {
					approvalFlag = "否";
				} else if ("1".equals(approvalFlag)) {
					approvalFlag = "审批";
				} else if ("2".equals(approvalFlag)) {
					approvalFlag = "备案";
				}
				subjectJson.put("approval_flag", approvalFlag);

				// adoptFlag判断
				String adoptFlag = String.valueOf(subject.get("adoptFlag"));
				if ("0".equals(adoptFlag)) {
					adoptFlag = "否";
				} else if ("1".equals(adoptFlag)) {
					adoptFlag = "是";
				}
				subjectJson.put("adopt_flag", adoptFlag);

				// 事项
				conParam.clear();
				conParam.put("subjectId", subject.get("subjectId"));
				List<Map<String, Object>> itemList = subjectMapper.getItemRelevanceList(conParam);
				JSONArray itemJsonArray = new JSONArray();
				for (Map<String, Object> item : itemList) {
					itemJsonArray.add(String.valueOf(item.get("itemCode")));
				}
				subjectJson.put("item_list", new JSONObject().put("item_code", itemJsonArray));

				// 列席人员列表
				JSONArray attendanceJsonArray = new JSONArray();
				List<Map<String, Object>> attendanceList = subjectMapper.getAttendanceList(conParam);
				if (attendanceList != null && !attendanceList.isEmpty()) {
					for (Map<String, Object> attendance : attendanceList) {
						attendanceJsonArray
								.add(new JSONObject().put("attendance_name", attendance.get("ATTENDANCE_NAME"))
										.put("position", attendance.get("POSITION")));
					}
				}
				subjectJson.put("attendance_list", new JSONObject().put("attendance", attendanceJsonArray));
				// 审议结果列表
				JSONArray deliberationJsonArray = new JSONArray();
				List<Map<String, Object>> deliberationList = subjectMapper.getDeliberationList(conParam);
				if (deliberationList != null && !deliberationList.isEmpty()) {
					for (Map<String, Object> deliberation : deliberationList) {
						deliberationJsonArray.add(new JSONObject()
								.put("deliberation_personnel", deliberation.get("DELIBERATION_PERSONNEL"))
								.put("deliberation_result", deliberation.get("DELIBERATION_RESULT")));
					}
				}
				subjectJson.put("deliberation_list", new JSONObject().put("deliberation", deliberationJsonArray));
				// 关联议题列表
				JSONArray relSubjectJsonArray = new JSONArray();
				List<Map<String, Object>> relSubjectList = subjectMapper.getRelSubjectList(conParam);
				if (relSubjectList != null && !relSubjectList.isEmpty()) {
					for (Map<String, Object> relSubject : relSubjectList) {
						relSubjectJsonArray
								.add(new JSONObject().put("rel_meeting_name", relSubject.get("REL_MEETING_NAME"))
										.put("rel_subject_name", relSubject.get("REL_SUBJECT_NAME")));
					}
				}
				subjectJson.put("relation_list", new JSONObject().put("relation", relSubjectJsonArray));

				subjectJsonArray.add(subjectJson);

				//创建听取意见文件夹
				File subjectOpionFilePath =new File(filePath.getPath()+"/"+subject.get("subjectId")+"/听取意见");
				subjectOpionFilePath.mkdirs();
				
				//创建议题材料文件夹
				File subjectMaterialFilePath =new File(filePath.getPath()+"/"+subject.get("subjectId")+"/议题材料");
				subjectMaterialFilePath.mkdir();
				
				//下载意见和材料
				conParam.clear();
				conParam.put("BUSINESS_ID", subject.get("subjectId"));
				try {
					List<Map<String,Object>> attachmentList = bizDBService.get("TIOL_ATTACHMENT", conParam);
					for(Map<String,Object> attachment : attachmentList) {
						String attachmentType = String.valueOf(attachment.get("ATTACHMENT_TYPE"));
						String fId = String.valueOf(attachment.get("FILE_ID"));
						String fileName = String.valueOf(attachment.get("ATTACHMENT_NAME"));
						AttachmentVo attachmentVo = new AttachmentVo();
						attachmentVo.setFileId(fId);
						attachmentVo.setFileName(fileName);
						InputStream is = FastDfsUtils.downloadFile(urlStr + fId, attachmentVo);
						FileOutputStream fileOutputStream = null;
						if("议题材料".equals(attachmentType)) {//如果是议题材料放入议题材料文件夹
							fileOutputStream = new FileOutputStream(
									new File(subjectMaterialFilePath.getPath() + File.separator + fileName));
						}else {
							fileOutputStream = new FileOutputStream(
									new File(subjectOpionFilePath.getPath() + File.separator + fileName));
						}
						int count = 0;
						byte[] buff = new byte[1024 * 8];
						while ((count = is.read(buff)) != -1) {
							fileOutputStream.write(buff, 0, count);
						}
						is.close();
						fileOutputStream.flush();
						fileOutputStream.close();
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
				

			}
			meetingJson.put("subject_list", new JSONObject().put("subject", subjectJsonArray));

			// 生成xml
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ XML.toString(new JSONObject().put("tiol", meetingJson));
			// ItemService.class.getResource(File.separator).toString();

			// 创建“正式文件”文件夹
			File summaryFilePath = new File(filePath.getPath() + File.separator + "会议纪要");
			summaryFilePath.mkdir();
			// 创建“佐证材料”文件夹
			File noticeFilePath = new File(filePath.getPath() + File.separator + "会议通知");
			noticeFilePath.mkdir();

			String outXmlFilePath = filePath.getPath() + File.separator + xmlFileName + BusinessConstant.DOT_CODE
					+ BusinessConstant.XML_SUFFIX;
			
			try {
				// 生成xml文件
				FileUtils.writeContent(outXmlFilePath, xml);
				
				// 获取会议纪要
				AttachmentVo attachmentVo = new AttachmentVo();
				attachmentVo.setFileId(summaryFileId);
				attachmentVo.setFileName(summaryFileName);
				InputStream is = FastDfsUtils.downloadFile(urlStr + summaryFileId, attachmentVo);
				// 为file生成对应的文件输出流
				FileOutputStream fileOutputStream = new FileOutputStream(
						new File(summaryFilePath.getPath() + File.separator + summaryFileName));
				int count = 0;
				byte[] buff = new byte[1024 * 8];
				while ((count = is.read(buff)) != -1) {
					fileOutputStream.write(buff, 0, count);
				}
				is.close();
				fileOutputStream.flush();
				fileOutputStream.close();
				
				//会议通知文件下载
				attachmentVo = new AttachmentVo();
				attachmentVo.setFileId(noticeFileId);
				attachmentVo.setFileName(noticeFileName);
				is = FastDfsUtils.downloadFile(urlStr + noticeFileId, attachmentVo);
				// 为file生成对应的文件输出流
				fileOutputStream = new FileOutputStream(
						new File(noticeFilePath.getPath() + File.separator + noticeFileName));
				count = 0;
				buff = new byte[1024 * 8];
				while ((count = is.read(buff)) != -1) {
					fileOutputStream.write(buff, 0, count);
				}
				is.close();
				fileOutputStream.flush();
				fileOutputStream.close();

				// 压缩文件夹
				File zipFile = new File(outPath + File.separator + xmlFileName + BusinessConstant.DOT_CODE
						+ BusinessConstant.ZIP_SUFFIX);
				FileOutputStream fos = new FileOutputStream(zipFile);
				FileUtils.toZipWithoutRoot(filePath.getPath(), fos, true);

				// 调用大额资金报送系统通道进行上报
				Map<String, Object> paramMap = new HashMap<String, Object>();
				Map<String, Object> configMap = configMapper.getInterfaceConfig();
				String uploadUrl = "";
				String uploadUser = "";
				String uploadPassword = "";
				if(null == configMap|| configMap.isEmpty()) {
					return "上传接口未配置";
				}else {
					uploadUrl = String.valueOf(configMap.get("ADDRESS"));
					uploadUser = String.valueOf(configMap.get("USER_NAME"));
					uploadPassword = String.valueOf(configMap.get("PASSWORD"));
					if(StringUtils.isBlank(uploadUrl)||StringUtils.isBlank(uploadUser)
							||StringUtils.isBlank(uploadPassword)) {
						return "上传接口未配置";
					}
				}
						
				paramMap.put("USER", uploadUser);
				paramMap.put("PASSWORD", uploadPassword);
				// 业务类型
				paramMap.put("BUSINESS_TYPE", EBusinessType.MEETING.getCode());
				paramMap.put("API_CODE", BusinessConstant.API_CODE);
				paramMap.put("FILE_NAME", zipFile.getName());

				String result = HttpClientUtils.upload(uploadUrl, zipFile.getPath(), paramMap);
				JSONObject resJson = new JSONObject(result);
				if (resJson.has("CODE")) {
					if (resJson.getString("CODE").equals("1")) {
						success++;
						// 更新数据库
						paramMap.clear();
						paramMap.put("MEETING_ID", meeting.get("MEETING_ID"));
						if (!StringUtils.isBlank(String.valueOf(meeting.get("UPLOAD_STATUS")))
								&& "1".equals(meeting.get("UPLOAD_STATUS"))) {
							paramMap.put("UPLOAD_STATUS", "2");
						} else {
							paramMap.put("UPLOAD_STATUS", "1");
						}
						paramMap.put("UPDATE_TIME", DateUtils.getFormatTime());
						paramMap.put("UPDATE_USER", userName);
						meetingmapper.updateMeeting(paramMap);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "共计 " + ids.length + " 条，上报成功 " + success + " 条";
	}

	@Override
	public Map<String, Object> queryMeetingAttendee(Map<String, Object> param) {
		return meetingmapper.queryMeetingAttendee(param);
	}

	@Override
	public List<Map<String, Object>> queryCompanyMeetingList(Map<String, Object> param, Page page) {
		// 查询数据
		int maxRow = page.getCurrentPage() * page.getPageSize();
		int minRow = (page.getCurrentPage() - 1) * page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		return meetingmapper.queryCompanyMeetingList(param);
	}

	@Override
	public int countCompanyMeetingList(Map<String, Object> param) {
		return meetingmapper.countCompanyMeetingList(param);
	}

	@Override
	public int deleteMeeting(Map<String, Object> param) {
		String[] ids = (String[]) param.get("ids");
		int count = 0;
		//获取会议状态信息
		List<Map<String,Object>> meetingList = meetingmapper.queryMeetingListByIds(ids);
		for (int i = 0; i < meetingList.size(); i++) {
			Map<String,Object> meeting = meetingList.get(i);
			if("1".equals(meeting.get("status")) && "0".equals(meeting.get("uploadStatus").toString())){
				//未上传且为新增状态-直接物理删除
				count = count + meetingmapper.deleteMeetingById(meeting.get("meetingId")+"");
			}else{
				//（已上传或（未上传且为修改状态））-更新上传状态为(upload_status)0，状态（status）为0
				meeting.put("uploadStatus","0");
				meeting.put("status","0");
				count = count + meetingmapper.updateMeetingStatus(meeting);
			}
			//删除会议指派内容
			meetingmapper.deleteSubjectTodoByMeetingId(meeting.get("meetingId")+"");
		}
		return count;
	}

	@Override
	public int recoverMeeting(Map<String, Object> param) {
		String[] ids = (String[]) param.get("ids");
		int count = 0;
		for (int i = 0; i < ids.length; i++) {
			Map<String,Object> meeting = new HashMap<String,Object>();
			meeting.put("uploadStatus","0");
			meeting.put("status","2");
			meeting.put("meetingId",ids[i]);
			count = meetingmapper.updateMeetingStatus(meeting);
			count++;
		}
		return count;
	}
}
