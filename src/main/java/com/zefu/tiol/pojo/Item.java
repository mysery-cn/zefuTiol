package com.zefu.tiol.pojo;

/**
 * 事项实体类
 * 
 * @author linch
 * @date 2018年10月26日
 */
public class Item extends BaseEntity {

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
	private String itemId; // 事项清单ID
	private String catalogId;// 事项目录ID
	private String itemName;// 事项名称
	private String itemCode;// 事项编码
	private String companyId;// 企业ID
	private String legalFlag;// 是否需经法律审核
	private String status;// 状态
	private String createTime;//创建时间
	private String updateTime;// 更新时间
	private String updateUser;// 更新人
	private String remark;// 备注
	private String sid;//来源ID

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
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
