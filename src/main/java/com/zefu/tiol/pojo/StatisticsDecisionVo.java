package com.zefu.tiol.pojo;

/**
 * 重大决策统计业务类
 * 
 * @author：陈东茂
 * @date：2018年10月29日
 */
public class StatisticsDecisionVo extends StatisticsDecision{

	private static final long serialVersionUID = 1L;
	
	private int companyQuantity;//公司数
	private String industryName;//行业名称
	private String companyName;//公司名称

	public int getCompanyQuantity() {
		return companyQuantity;
	}

	public void setCompanyQuantity(int companyQuantity) {
		this.companyQuantity = companyQuantity;
	}

	public String getIndustryName() {
		return industryName;
	}

	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	

}
