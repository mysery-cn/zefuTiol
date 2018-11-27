package com.zefu.tiol.pojo;

/**
   * @工程名 : szyd
   * @文件名 : SubjectRelevance.java
   * @工具包名：com.zefu.tiol.pojo
   * @功能描述: TODO 议题关联会议议题实体类
   * @创建人 ：林鹏
   * @创建时间：2018年11月13日 下午1:56:29
   * @版本信息：V1.0
 */
public class SubjectRelevance extends BaseEntity {

	private static final long serialVersionUID = 1L;

	// 自定义字段
	private String subjectId; // 议题ID
	private String relevanceId;// 主键
	private String relMeetingId;// 关联会议ID
	private String relMeetingName;//关联会议名称
	private String relSubjectName;//关联议题名称
	private String relSubjectId;// 关联议题ID
	private String updateTime;// 修改时间
	
	//Excle导入字段
	
	
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getRelevanceId() {
		return relevanceId;
	}
	public void setRelevanceId(String relevanceId) {
		this.relevanceId = relevanceId;
	}
	public String getRelMeetingId() {
		return relMeetingId;
	}
	public void setRelMeetingId(String relMeetingId) {
		this.relMeetingId = relMeetingId;
	}
	public String getRelMeetingName() {
		return relMeetingName;
	}
	public void setRelMeetingName(String relMeetingName) {
		this.relMeetingName = relMeetingName;
	}
	public String getRelSubjectName() {
		return relSubjectName;
	}
	public void setRelSubjectName(String relSubjectName) {
		this.relSubjectName = relSubjectName;
	}
	public String getRelSubjectId() {
		return relSubjectId;
	}
	public void setRelSubjectId(String relSubjectId) {
		this.relSubjectId = relSubjectId;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	

	
	
	

}
