package com.zefu.tiol.pojo;

/**
 * 重大决策统计实体类
 * 
 * @author Chris-Lin
 * @date 2018年10月25日
 */
public class StatisticsDecision extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String statisticsId; // 统计ID
	private String companyId;// 企业ID
	private String industryId;// 行业
	private String objectId;// 统计对象ID
	private String statisticsName;// 任务来源或目录名称
	private String statisticsType;// 统计类型(1任务来源，2事项目录)
	private int subjectQuantity;// 议题数
	private int meetingQuantity;// 会议数
	private String statisticsTime;// 统计时间

	public String getStatisticsId() {
		return statisticsId;
	}

	public void setStatisticsId(String statisticsId) {
		this.statisticsId = statisticsId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getStatisticsName() {
		return statisticsName;
	}

	public void setStatisticsName(String statisticsName) {
		this.statisticsName = statisticsName;
	}

	public String getStatisticsType() {
		return statisticsType;
	}

	public void setStatisticsType(String statisticsType) {
		this.statisticsType = statisticsType;
	}

	public int getSubjectQuantity() {
		return subjectQuantity;
	}

	public void setSubjectQuantity(int subjectQuantity) {
		this.subjectQuantity = subjectQuantity;
	}

	public int getMeetingQuantity() {
		return meetingQuantity;
	}

	public void setMeetingQuantity(int meetingQuantity) {
		this.meetingQuantity = meetingQuantity;
	}

	public String getStatisticsTime() {
		return statisticsTime;
	}

	public void setStatisticsTime(String statisticsTime) {
		this.statisticsTime = statisticsTime;
	}

}
