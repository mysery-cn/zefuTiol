package com.zefu.tiol.pojo;

/**
 * 列席人员信息表实体
 * 
 * @author linch
 * @date 2018年10月30日
 */
public class Attendance extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// 自定义字段
	private String attendanceId; // 列席人员ID
	private String meetingId;// 会议ID
	private String subjectId;// 议题ID
	private String attendanceName;// 列席人员名称
	private String position;// 职位
	private String counselType;// 顾问类型

	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCounselType() {
		return counselType;
	}

	public void setCounselType(String counselType) {
		this.counselType = counselType;
	}

	public String getAttendanceId() {
		return attendanceId;
	}

	public void setAttendanceId(String attendanceId) {
		this.attendanceId = attendanceId;
	}

	public String getAttendanceName() {
		return attendanceName;
	}

	public void setAttendanceName(String attendanceName) {
		this.attendanceName = attendanceName;
	}
}
