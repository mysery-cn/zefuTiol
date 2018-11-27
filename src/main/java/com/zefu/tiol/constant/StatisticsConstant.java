package com.zefu.tiol.constant;

public class StatisticsConstant {
	/**
	 * 重大决策统计表-统计类型常量
	 * */
	static public String TYPE_SOURCE = "1";//根据任务来源统计
	static public String TYPE_ITEM = "2";//根据事项目录统计
	
	/**
	 * 法规统计表--统计类型常量
	 */
	static public String LEGISLATION_COUNCIL_COUNSEL ="1";//根据党委会法律顾问出席统计
	static public String LEGISLATION_DIRECTOR_COUNSEL ="2";//根据董事会法律顾问出席统计
	static public String LEGISLATION_COUNCIL_FILE ="3";//根据党委会文件合法合规统计
	static public String LEGISLATION_REPORT_OPTION ="4";//根据上报国资委事项法律意见书出具统计
	static public String LEGISLATION_ECONOMIC_REGULATION ="5";//根据重大经济活动及规章制度合法合规统计
	static public String LEGISLATION_OVERSEAS_PROJECT ="6";//根据境外重大项目法律顾问参与统计

	static public String COUNSEL_TYPE_ECONOMIC ="经济"; //顾问类型名称-经济师
	static public String COUNSEL_TYPE_LEGAL ="法律"; //顾问类型名称-法律顾问
}
