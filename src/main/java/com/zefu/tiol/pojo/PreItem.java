package com.zefu.tiol.pojo;

/**
 * 前置表-事项实体类
 * 
 * @author linch
 * @date 2018年11月06日
 */
public class PreItem extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// 自定义字段
	private String id; // 主键ID
	private String itemId; // 事项清单ID
	private String itemName;// 事项名称
	private String itemCode;// 事项编码
	private String companyId;// 企业ID
	private String legalFlag;// 是否需经法律审核
	private String meetings;// 会议列表，格式为[{"meeting_type_name":"党委会","order_number":1},{"meeting_type_name":"总经理办公会","order_number":2},{"meeting_type_name":"董事会","order_number":3}]
	private String status;// 状态(0-未读取，1-已读取，9-数据异常)
	private String createTime;// 创建时间
	private String updateTime;// 更新时间
	private String remark;// 备注
	private String errorRemark;// 错误说明
	private String operType;// 操作类型(add-新增,del-删除,edit-修改)
	private String fileName;// 压缩包文件名
	private String xmlContent;// xml内容

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

	public String getMeetings() {
		return meetings;
	}

	public void setMeetings(String meetings) {
		this.meetings = meetings;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getLegalFlag() {
		return legalFlag;
	}

	public void setLegalFlag(String legalFlag) {
		this.legalFlag = legalFlag;
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

}
