package com.zefu.tiol.pojo;

import java.util.List;

/**
 * 议题实体类
 * 
 * @author linch
 * @date 2018年10月26日
 */
public class Subject extends BaseEntity {

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
	private String subjectId; // 议题ID
	private String meetingId;// 会议ID
	private String subjectCode;// 议题编码
	private String subjectName;// 议题名称
	private String sourceId;// 任务来源ID
	private String specialId;// 专项ID
	private String itemId;// 事项ID
	private String relMeetingId;// 关联会议ID
	private String relMeetingName;//关联会议名称
	private String relSubjectName;//关联议题名称
	private String relSubjectId;// 关联议题ID
	private String passFlag;// 是否通过
	private String approvalFlag;// 是否需报国资委审批
	private String adoptFlag;// 是否听取意见
	private String opinionFileId;// 听取意见情况
	private String subjectFileId;// 议题材料
	private String subjectResult;// 议题决议
	private String verifyFlag;// 是否校验
	private String viaFlag;// 是否经过董事会决策
	private String remark;// 备注
	private String status;// 状态
	private String sid;// 来源ID
	
	//Excle导入字段
	private List<Deliberation> deliberations;//审议结果
	private List<Attendance> attendances;//列席人数组
	private String itemCode;//事项编码
	private String sourceName;//任务来源
    private String specialName;//专项名称
    private String attendanceStr;//列席人字符串
    private String deliberationStr;//审议结果字符串
    private String passFlagName;//是否通过名称
    private String approvalFlagName;//是否需报国资委审批名称
    private List<SubjectRelevance> subjectRelevances;//关联会议议题结果
    private List<SubjectItem> subjectItems;//议题关联事项清单编码结果
	
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

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getcBizname() {
		return cBizname;
	}

	public void setcBizname(String cBizname) {
		this.cBizname = cBizname;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}

	public String getRelMeetingId() {
		return relMeetingId;
	}

	public void setRelMeetingId(String relMeetingId) {
		this.relMeetingId = relMeetingId;
	}

	public String getRelSubjectId() {
		return relSubjectId;
	}

	public void setRelSubjectId(String relSubjectId) {
		this.relSubjectId = relSubjectId;
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

	public String getOpinionFileId() {
		return opinionFileId;
	}

	public void setOpinionFileId(String opinionFileId) {
		this.opinionFileId = opinionFileId;
	}

	public String getSubjectFileId() {
		return subjectFileId;
	}

	public void setSubjectFileId(String subjectFileId) {
		this.subjectFileId = subjectFileId;
	}

	public String getSubjectResult() {
		return subjectResult;
	}

	public void setSubjectResult(String subjectResult) {
		this.subjectResult = subjectResult;
	}

	public String getVerifyFlag() {
		return verifyFlag;
	}

	public void setVerifyFlag(String verifyFlag) {
		this.verifyFlag = verifyFlag;
	}

	public String getViaFlag() {
		return viaFlag;
	}

	public void setViaFlag(String viaFlag) {
		this.viaFlag = viaFlag;
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

	public List<Deliberation> getDeliberations() {
		return deliberations;
	}

	public void setDeliberations(List<Deliberation> deliberations) {
		this.deliberations = deliberations;
	}

	public List<Attendance> getAttendances() {
		return attendances;
	}

	public void setAttendances(List<Attendance> attendances) {
		this.attendances = attendances;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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

	public String getAttendanceStr() {
		return attendanceStr;
	}

	public void setAttendanceStr(String attendanceStr) {
		this.attendanceStr = attendanceStr;
	}

	public String getDeliberationStr() {
		return deliberationStr;
	}

	public void setDeliberationStr(String deliberationStr) {
		this.deliberationStr = deliberationStr;
	}

	public String getPassFlagName() {
		return passFlagName;
	}

	public void setPassFlagName(String passFlagName) {
		this.passFlagName = passFlagName;
	}

	public List<SubjectRelevance> getSubjectRelevances() {
		return subjectRelevances;
	}

	public void setSubjectRelevances(List<SubjectRelevance> subjectRelevances) {
		this.subjectRelevances = subjectRelevances;
	}

	public List<SubjectItem> getSubjectItems() {
		return subjectItems;
	}

	public void setSubjectItems(List<SubjectItem> subjectItems) {
		this.subjectItems = subjectItems;
	}

	public String getApprovalFlagName() {
		return approvalFlagName;
	}

	public void setApprovalFlagName(String approvalFlagName) {
		this.approvalFlagName = approvalFlagName;
	}
	

}
