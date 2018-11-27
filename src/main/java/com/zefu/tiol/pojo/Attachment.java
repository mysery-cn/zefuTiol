package com.zefu.tiol.pojo;

/**
 * 列席人员信息表实体
 * 
 * @author linch
 * @date 2018年10月30日
 */
public class Attachment extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	// 自定义字段
	private String attachmentId; // 附件ID
	private String attachmentName;// 附件名称
	private String fileId;// 文件ID
	private String businessId;// 业务ID
	private String attachmentType;// 附件类型
	private String status;// 状态
	private String createTime;// 创建时间
	private String createUser;// 创建人

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
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

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

}
