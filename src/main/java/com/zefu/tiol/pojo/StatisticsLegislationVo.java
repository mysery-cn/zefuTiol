package com.zefu.tiol.pojo;

/**
 * 法规统计业务类
 * 
 * @author：陈东茂
 * @date：2018年10月31日
 */
public class StatisticsLegislationVo extends StatisticsLegislation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String rowNum;//序号
	private String companyName;//企业名称
	public String getRowNum() {
		return rowNum;
	}
	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
}
