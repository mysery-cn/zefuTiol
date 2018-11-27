package com.zefu.tiol.pojo;

/**
 * 企业统计表实体类
 * @author Chris-Lin
 * @date 2018年10月24日
 * */
public class StatisticsCompany extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String statisticsId; //统计ID
	private String companyId;//企业ID
	private String industryId;//行业
	private int subjectQuantity;//议题数
	private int meetingQuantity;//会议数
	private int itemQuantity;//事项数
	private int regulationQuantity;//制度数
	private int nonstandardQuantity;//程序不规范数量
	private String statisticsTime;//统计时间
	private String companyName;//企业简称
	private int rn;//序号
	
	
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
	public int getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public int getRegulationQuantity() {
		return regulationQuantity;
	}
	public void setRegulationQuantity(int regulationQuantity) {
		this.regulationQuantity = regulationQuantity;
	}
	public int getNonstandardQuantity() {
		return nonstandardQuantity;
	}
	public void setNonstandardQuantity(int nonstandardQuantity) {
		this.nonstandardQuantity = nonstandardQuantity;
	}
	public String getStatisticsTime() {
		return statisticsTime;
	}
	public void setStatisticsTime(String statisticsTime) {
		this.statisticsTime = statisticsTime;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getRn() {
		return rn;
	}

	public void setRn(int rn) {
		this.rn = rn;
	}
	
}
