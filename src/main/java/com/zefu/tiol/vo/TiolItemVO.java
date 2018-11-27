package com.zefu.tiol.vo;

import java.util.List;

/**
 * 事项清单表
 * 
 * @author long
 *
 */
public class TiolItemVO {
	private String sno; // 序号
	private String itemName;// 事项名称
	private String itemCode;// 事项编码
	private List<TiolItemMeetingVO> meetingList;
	private String legalFlag;// 是否需经活动率审核
	private String remark;// 备注

	private int row;

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
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

	public List<TiolItemMeetingVO> getMeetingList() {
		return meetingList;
	}

	public void setMeetingList(List<TiolItemMeetingVO> meetingList) {
		this.meetingList = meetingList;
	}

	public String getLegalFlag() {
		return legalFlag;
	}

	public void setLegalFlag(String legalFlag) {
		this.legalFlag = legalFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

}
