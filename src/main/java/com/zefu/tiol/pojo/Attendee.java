package com.zefu.tiol.pojo;

/**
 * 参会人信息表实体
 * 
 * @author linch
 * @date 2018年10月26日
 */
public class Attendee extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// 自定义字段
	private String attendeeId; // 参会人ID
	private String meetingId;// 会议ID
	private String attendeeName;// 参会人姓名
	private String attendFlag;// 是否参会
	private String reason;// 缺席原因

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getAttendeeId() {
		return attendeeId;
	}

	public void setAttendeeId(String attendeeId) {
		this.attendeeId = attendeeId;
	}

	public String getAttendeeName() {
		return attendeeName;
	}

	public void setAttendeeName(String attendeeName) {
		this.attendeeName = attendeeName;
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
