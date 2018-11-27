package com.zefu.tiol.pojo;

/**
 * 重大决策统计实体类
 * 
 * @author Chris-Lin
 * @date 2018年10月25日
 */
public class StatisticsRegulation extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String statisticsId; // 统计ID
	private String companyId;// 企业ID
	private String industryId;// 行业ID
	private String typeId;// 制度类型ID
	private String typeName;// 制度类型名称
	private int quantity;// 制度数
	private int companyQuantity;// 企业数（非表字段）
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

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getCompanyQuantity() {
		return companyQuantity;
	}

	public void setCompanyQuantity(int companyQuantity) {
		this.companyQuantity = companyQuantity;
	}

	public String getStatisticsTime() {
		return statisticsTime;
	}

	public void setStatisticsTime(String statisticsTime) {
		this.statisticsTime = statisticsTime;
	}

}
