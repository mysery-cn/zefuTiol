package com.zefu.tiol.vo;

/**
 * 参会人
 * 
 * @author long
 *
 */
public class TiolAttendeeVO {

	private String name;// 参会人姓名
	private String attendFlag;// 是否参会
	private String reason;// 缺席原因

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAttendFlag() {
		return attendFlag;
	}

	public void setAttendFlag(String attendFlag) {
		this.attendFlag = attendFlag;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
