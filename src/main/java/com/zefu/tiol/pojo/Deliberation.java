package com.zefu.tiol.pojo;

/**
 * 审议结果实体
 * 
 * @author linch
 * @date 2018年10月30日
 */
public class Deliberation extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	// 自定义字段
	private String deliberationId; // 审议结果ID
	private String meetingId;// 会议ID
	private String subjectId;// 议题ID
	private String deliberationResult;// 审议结果
	private String deliberationPersonnel;// 审议人

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

	public String getDeliberationId() {
		return deliberationId;
	}

	public void setDeliberationId(String deliberationId) {
		this.deliberationId = deliberationId;
	}

	public String getDeliberationResult() {
		return deliberationResult;
	}

	public void setDeliberationResult(String deliberationResult) {
		this.deliberationResult = deliberationResult;
	}

	public String getDeliberationPersonnel() {
		return deliberationPersonnel;
	}

	public void setDeliberationPersonnel(String deliberationPersonnel) {
		this.deliberationPersonnel = deliberationPersonnel;
	}

}
