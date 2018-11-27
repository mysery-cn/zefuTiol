package com.zefu.tiol.pojo;

/**
 * 议题实体类
 * 
 * @author linch
 * @date 2018年10月26日
 */
public class PreSubject extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// 自定义字段
	private String id; // 主键id
	private String subjectId; // 议题ID
	private String subjectName;// 议题名称
	private String sourceName;// 任务来源名称
	private String specialName;// 专项名称
	private String itemCode;// 事项编码
	private String relMeetingName;// 关联会议ID
	private String relSubjectName;// 关联议题ID
	private String passFlag;// 是否通过
	private String approvalFlag;// 是否需报国资委审批
	private String adoptFlag;// 是否听取意见
	private String subjectResult;// 议题决议
	private String remark;// 备注
	private String createTime;// 创建时间
	private String updateTime;// 更新时间
	private String errorRemark;// 错误说明
	private String operType;// 操作类型(add-新增,del-删除,edit-修改)
	private String fileName;// 压缩包文件名
	private String attendances;// 列席人员列表，[{"attendance_name":"王XX","position":"总法律顾问"},{"attendance_name":"赵XX","position":"总法律顾问"}]
	private String deliberations;// 审议结果列表，[{"deliberation_personnel":"张XX","deliberation_result":"同意"},{"deliberation_personnel":"李XX","deliberation_result":"原则同意"}]
	private String meetingId;// 会议ID
	private String meetingName;// 会议名称
	private String companyId;// 企业ID
	private String status;// 状态(0-未读取，1-已读取，9-数据异常)
	
	public String getMeetingName() {
		return meetingName;
	}

	public void setMeetingName(String meetingName) {
		this.meetingName = meetingName;
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

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAttendances() {
		return attendances;
	}

	public void setAttendances(String attendances) {
		this.attendances = attendances;
	}

	public String getDeliberations() {
		return deliberations;
	}

	public void setDeliberations(String deliberations) {
		this.deliberations = deliberations;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getRelMeetingName() {
		return relMeetingName;
	}

	public void setRelMeetingName(String relMeetingName) {
		this.relMeetingName = relMeetingName;
	}

	public String getRelSubjectName() {
		return relSubjectName;
	}

	public void setRelSubjectName(String relSubjectName) {
		this.relSubjectName = relSubjectName;
	}

	public String getPassFlag() {
		return passFlag;
	}

	public void setPassFlag(String passFlag) {
		this.passFlag = passFlag;
	}

	public String getApprovalFlag() {
		return approvalFlag;
	}

	public void setApprovalFlag(String approvalFlag) {
		this.approvalFlag = approvalFlag;
	}

	public String getAdoptFlag() {
		return adoptFlag;
	}

	public void setAdoptFlag(String adoptFlag) {
		this.adoptFlag = adoptFlag;
	}

	public String getSubjectResult() {
		return subjectResult;
	}

	public void setSubjectResult(String subjectResult) {
		this.subjectResult = subjectResult;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getErrorRemark() {
		return errorRemark;
	}

	public void setErrorRemark(String errorRemark) {
		this.errorRemark = errorRemark;
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

}
