package com.zefu.tiol.vo;

/**
 * 事项会议表
 * 
 * @author long
 *
 */
public class TiolItemMeetingVO {
	private String name;// 会议类型
	private int order;// 序号
	private int row;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
}
