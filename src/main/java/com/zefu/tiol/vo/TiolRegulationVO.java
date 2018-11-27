package com.zefu.tiol.vo;

import java.util.List;

/**
 * 制度信息表
 * 
 * @author long
 *
 */
public class TiolRegulationVO {
	private String sno;
	private String name; // 制度名称
	private String type;// 制度类型名称
	private String approvalDate;// 审批时间
	private String effectiveDate;// 生效时间
	private String auditFlag;// 是否经过合法审查
	private String rate;// 人数占比
	private String remark;// 备注
	private List<TiolVoteModeVO> voteModeList;
	private int startRow;
	private int endRow;
	private boolean isMerged;// 是否为合并单元格，判断序号这一列
	private int row;

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<TiolVoteModeVO> getVoteModeList() {
		return voteModeList;
	}

	public void setVoteModeList(List<TiolVoteModeVO> voteModeList) {
		this.voteModeList = voteModeList;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public boolean isMerged() {
		return isMerged;
	}

	public void setMerged(boolean isMerged) {
		this.isMerged = isMerged;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
}
