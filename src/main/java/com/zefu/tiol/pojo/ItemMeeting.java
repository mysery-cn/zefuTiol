package com.zefu.tiol.pojo;

import java.util.Date;

/**
 * 事项实体类
 * 
 * @author linch
 * @date 2018年10月26日
 */
public class ItemMeeting extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// 平台字段(字段名，包括大小写不可修改)
	private String fid; // 唯一主键
	private String cModuleid; // 模块ID
	private String cModulename; // 模块名称
	private String cBizid; // 业务ID
	private String cBizname; // 业务名称
	private String cCreaterid; // 创建者ID
	private String cCreatername; // 创建者
	private Date cCreatedate; // 创建时间
	private String cCreatedeptid; // 创建部门ID
	private String cCreatedeptname; // 创建部门
	private String cCompanyid; // 单位id

	// 自定义字段
	private String itemId; // 事项清单ID
	private String itemMeetingId;// 事项会议ID
	private String typeId;// 会议类型ID
	private String alias;// 会议类型别名
	private int orderNumber;// 排序号

	
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
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

	public String getItemMeetingId() {
		return itemMeetingId;
	}

	public void setItemMeetingId(String itemMeetingId) {
		this.itemMeetingId = itemMeetingId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
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

	public Date getcCreatedate() {
		return cCreatedate;
	}

	public void setcCreatedate(Date cCreatedate) {
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

}
