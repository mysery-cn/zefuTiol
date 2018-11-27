package com.zefu.tiol.pojo;
/**
 * 法规统计表实体类
 * 
 * @author：陈东茂
 * @date：2018年10月31日
 */
public class StatisticsLegislation extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String statisticsId; // 统计ID
	private String companyId;// 企业ID
	private String year; // 年度
	private int quantity;// 数量
	private int total; // 总数
	private String statisticsType;// 统计类型
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
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getStatisticsType() {
		return statisticsType;
	}
	public void setStatisticsType(String statisticsType) {
		this.statisticsType = statisticsType;
	}
	public String getStatisticsTime() {
		return statisticsTime;
	}
	public void setStatisticsTime(String statisticsTime) {
		this.statisticsTime = statisticsTime;
	}

	
}
