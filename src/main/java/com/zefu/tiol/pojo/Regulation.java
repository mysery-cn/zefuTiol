package com.zefu.tiol.pojo;

/**
 * 制度实体类
 * 
 * @author linch
 * @date 2018年10月26日
 */
public class Regulation extends BaseEntity {

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
	private String regulationId; // 制度ID
	private String regulationName;// 制度名称
	private String typeId;// 制度类型ID
	private String companyId;// 企业ID
	private String approvalDate;// 审批日期
	private String effectiveDate;// 生效日期
	private String auditFlag;// 是否经过合法审查
	private String auditFileId;// 审查佐证材料
	private String rate;// 人数占比
	private String fileId;// 正式文件
	private String status;// 状态
	private String updateTime;// 更新时间
	private String updateUser;// 更新人
	private String remark;// 备注
	private String sid;//来源ID

	public String getcBizname() {
		return cBizname;
	}

	public void setcBizname(String cBizname) {
		this.cBizname = cBizname;
	}

	public String getRegulationId() {
		return regulationId;
	}

	public void setRegulationId(String regulationId) {
		this.regulationId = regulationId;
	}

	public String getRegulationName() {
		return regulationName;
	}

	public void setRegulationName(String regulationName) {
		this.regulationName = regulationName;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public String getAuditFileId() {
		return auditFileId;
	}

	public void setAuditFileId(String auditFileId) {
		this.auditFileId = auditFileId;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
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

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}
	
	
}
