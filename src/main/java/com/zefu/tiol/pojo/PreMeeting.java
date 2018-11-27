package com.zefu.tiol.pojo;

/**
 * 前置表-会议实体类
 * 
 * @author linch
 * @date 2018年10月26日
 */
public class PreMeeting extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// 自定义字段
	private String id; // 主键ID
	private String meetingId; // 会议ID
	private String meetingName;// 会议名称
	private String typeName;// 会议类型名称
	private String meetingTime;// 会议时间
	private String moderator;// 主持人
	private String companyId;// 企业ID
	private String summaryFileId;// 会议纪要
	private String noticeFileId;// 会议通知
	private String status;// 状态(0-未读取，1-已读取，9-数据异常)
	private String createTime;// 创建时间
	private String updateTime;// 更新时间
	private String operType;// 操作类型(add-新增,del-删除,edit-修改)
	private String remark;// 备注
	private String fileName;// 压缩包文件名
	private String xmlContent;// xml内容
	private String attendees;// 参会人员列表，格式为格式为[{"attendee_name":"李XX","attend_flag":"否","reason":"因公出国"},{"attendee_name":"张XX"}]
	private String errorRemark;// 错误说明
	
	
	public String getErrorRemark() {
		return errorRemark;
	}

	public void setErrorRemark(String errorRemark) {
		this.errorRemark = errorRemark;
	}

	public String getSummaryFileId() {
		return summaryFileId;
	}

	public void setSummaryFileId(String summaryFileId) {
		this.summaryFileId = summaryFileId;
	}

	public String getAttendees() {
		return attendees;
	}

	public void setAttendees(String attendees) {
		this.attendees = attendees;
	}

	public String getXmlContent() {
		return xmlContent;
	}

	public void setXmlContent(String xmlContent) {
		this.xmlContent = xmlContent;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
