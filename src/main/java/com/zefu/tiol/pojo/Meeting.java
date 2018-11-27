package com.zefu.tiol.pojo;

import java.util.List;

/**
 * 会议实体类
 * 
 * @author linch
 * @date 2018年10月26日
 */
public class Meeting extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// 平台字段(字段名，包括大小写不可修改)
	private String fid; // 唯一主键
	private String cModuleid; // 模块ID
	private String cModulename; // 模块名称
	private String cBizid; // 业务ID
	private String cBizname; // 业务名称
	private String cCreaterid; // 创建者ID
	private String cCreatername; // 创建者
	private String cCreatedate; // 创建时间
	private String cCreatedeptid; // 创建部门ID
	private String cCreatedeptname; // 创建部门
	private String cCompanyid; // 单位id

	// 自定义字段
	private String meetingId; // 会议ID
	private String meetingName;// 会议名称
	private String typeId;// 会议类型ID
	private String meetingTime;// 会议时间
	private String moderator;// 主持人
	private String companyId;// 企业ID
	private String summaryFileId;// 会议纪要
	private String recordFileId;// 会议记录
	private String noticeFileId;// 会议通知
	private String status;// 状态
	private String updateTime;// 更新时间
	private String updateUser;// 更新人
	private String remark;// 备注
	private String sid;// 来源ID
	private String alias;//会议分类名称
	private String registerType;//录入类型 0批量导入,1集中填报,2协同填报
	private String uploadStatus;//上传状态 0待上传,1 已上传
	
	//Excel导入字段
	private List<Attendee> attendees;//出席人
	private List<Subject> subjects;//会议议题
	
	

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getcBizname() {
		return cBizname;
	}

	public void setcBizname(String cBizname) {
		this.cBizname = cBizname;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getMeetingName() {
		return meetingName;
	}

	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getMeetingTime() {
		return meetingTime;
	}

	public void setMeetingTime(String meetingTime) {
		this.meetingTime = meetingTime;
	}

	public String getModerator() {
		return moderator;
	}

	public void setModerator(String moderator) {
		this.moderator = moderator;
	}

	public String getSummaryFileId() {
		return summaryFileId;
	}

	public void setSummaryFileId(String summaryFileId) {
		this.summaryFileId = summaryFileId;
	}

	public String getRecordFileId() {
		return recordFileId;
	}

	public void setRecordFileId(String recordFileId) {
		this.recordFileId = recordFileId;
	}

	public String getNoticeFileId() {
		return noticeFileId;
	}

	public void setNoticeFileId(String noticeFileId) {
		this.noticeFileId = noticeFileId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getcModuleid() {
		return cModuleid;
	}

	public void setcModuleid(String cModuleid) {
		this.cModuleid = cModuleid;
	}

	public String getcModulename() {
		return cModulename;
	}

	public void setcModulename(String cModulename) {
		this.cModulename = cModulename;
	}

	public String getcBizid() {
		return cBizid;
	}

	public void setcBizid(String cBizid) {
		this.cBizid = cBizid;
	}

	public String getcCreaterid() {
		return cCreaterid;
	}

	public void setcCreaterid(String cCreaterid) {
		this.cCreaterid = cCreaterid;
	}

	public String getcCreatername() {
		return cCreatername;
	}

	public void setcCreatername(String cCreatername) {
		this.cCreatername = cCreatername;
	}

	public String getcCreatedate() {
		return cCreatedate;
	}

	public void setcCreatedate(String cCreatedate) {
		this.cCreatedate = cCreatedate;
	}

	public String getcCreatedeptid() {
		return cCreatedeptid;
	}

	public void setcCreatedeptid(String cCreatedeptid) {
		this.cCreatedeptid = cCreatedeptid;
	}

	public String getcCreatedeptname() {
		return cCreatedeptname;
	}

	public void setcCreatedeptname(String cCreatedeptname) {
		this.cCreatedeptname = cCreatedeptname;
	}

	public String getcCompanyid() {
		return cCompanyid;
	}

	public void setcCompanyid(String cCompanyid) {
		this.cCompanyid = cCompanyid;
	}

	public List<Attendee> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<Attendee> attendees) {
		this.attendees = attendees;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public String getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
}
