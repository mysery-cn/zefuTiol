package com.zefu.tiol.pojo;

/**
 * 前置表-制度实体类
 * 
 * @author linch
 * @date 2018年11月06日
 */
public class PreRegulation extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// 自定义字段
	private String id; // 主键ID
	private String regulationId; // 制度ID
	private String regulationName;// 制度名称
	private String typeName;// 制度类型名称
	private String companyId;// 企业ID
	private String approvalDate;// 审批日期
	private String effectiveDate;// 生效日期
	private String auditFlag;// 是否经过合法审查
	private String rate;// 人数占比
	private String voteModes;// 表决方式列表，格式为[{"item_code":"默认","vote_mode":"赞成票超过到会成员人数的1/2","remark":""},{"item_code":"H06-001","vote_mode":"赞成票超过到会成员人数的3/4","remark":""},{"item_code":"P08-005","vote_mode":"赞成票超过应到会成员人数的2/3","remark":""}]
	private String status;// 状态(0-未读取，1-已读取，9-数据异常)
	private String createTime;// 创建时间
	private String updateTime;// 更新时间
	private String remark;// 备注
	private String errorRemark;// 错误说明
	private String operType;// 操作类型(add-新增,del-删除,edit-修改)
	private String fileName;// 压缩包文件名
	private String xmlContent;// xml内容
	private String fileId;// 制度正式文件id

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getXmlContent() {
		return xmlContent;
	}

	public void setXmlContent(String xmlContent) {
		this.xmlContent = xmlContent;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getVoteModes() {
		return voteModes;
	}

	public void setVoteModes(String voteModes) {
		this.voteModes = voteModes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

}
