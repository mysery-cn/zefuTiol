package com.zefu.tiol.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 采集数据时所需常量预定义类
 * @author linch
 * 
 */
public class CollectDataConstant {

	/**
	 * 业务编码常量
	 * */
	final static public String BUSINESS_CODE_ITEM = "0004";//事项
	final static public String BUSINESS_CODE_REGULATION = "0005";//制度
	final static public String BUSINESS_CODE_MEETING = "0006";//会议
	final static public Map<String,String> BUSINESS_CODE_MAP = new HashMap<String,String>();
	static {
		BUSINESS_CODE_MAP.put(BUSINESS_CODE_ITEM, "事项");
		BUSINESS_CODE_MAP.put(BUSINESS_CODE_REGULATION, "制度");
		BUSINESS_CODE_MAP.put(BUSINESS_CODE_MEETING, "会议");
	}
	
	final static public String REGULATION_VOTE_ITEM = "默认";//表决方式默认事项值
	
	/**
	 * 文件名中日期格式要求
	 * */
	final static public String DATE_PATTERN = "yyyyMMdd";
	
	/**
	 * 采集数据时，需要填写的平台字段的常量量预定义
	 * */
	/*通用*/
	final static public String CREATER_ID = "Tiol"; //创建者ID
	final static public String CREATER_NAME = "Tiol"; //创建者
	final static public String CREATE_DEPT_ID = "GZW"; //创建部门ID
	final static public String CREATE_DEPT_NAME = "国资委"; //创建部门
	
	/*事项*/
	final static public String ITEM_MODULE_ID = "matter"; //模块ID
	final static public String ITEM_MODULE_NAME = "事项管理"; //模块名称
	final static public String ITEM_BIZ_ID = "tiol_item"; //业务ID
	final static public String ITEM_BIZ_NAME = "事项清单"; //业务名称
	final static public String ITEM_MEETING_BIZ_ID = "tiol_item_meeting"; //事项会议业务ID
	
	/*制度*/
	final static public String REGULATION_MODULE_ID = "regulation"; //模块ID
	final static public String REGULATION_MODULE_NAME = "制度管理"; //模块名称
	final static public String REGULATION_BIZ_ID = "tiol_regulation"; //业务ID
	final static public String REGULATION_BIZ_NAME = "制度库"; //业务名称
	
	/*会议*/
	final static public String MEETING_MODULE_ID = "meeting"; //模块ID
	final static public String MEETING_MODULE_NAME = "会议管理"; //模块名称
	final static public String MEETING_BIZ_ID = "tiol_meeting"; //业务ID
	final static public String MEETING_BIZ_NAME = "会议库"; //业务名称
	
	/*议题*/
	final static public String SUBJECT_MODULE_ID = "subject"; //模块ID
	final static public String SUBJECT_MODULE_NAME = "议题管理"; //模块名称
	final static public String SUBJECT_BIZ_ID = "tiol_subject"; //业务ID
	final static public String SUBJECT_BIZ_NAME = "议题库"; //业务名称
	
	/*附件*/
	final static public String ATTACHMENT_MODULE_ID = "attachment"; //模块ID
	final static public String ATTACHMENT_MODULE_NAME = "附件材料"; //模块名称
	final static public String ATTACHMENT_BIZ_ID = "tiol_attachment"; //业务ID
	final static public String ATTACHMENT_BIZ_NAME = "附件信息表"; //业务名称
	
	/**
	 * xml标签预定义
	 * */
	/*事项*/
	final static public String XML_ROOT_NAME = "tiol"; //事项唯一标识
	final static public String XML_ITEM_ID = "item_id"; //事项唯一标识
	final static public String XML_ITEM_NAME = "item_name"; //事项名称
	final static public String XML_ITEM_CODE = "item_code"; //事项编码
	final static public String XML_ITEM_MEETING_LIST = "item_meeting_list"; //决策会议及顺序数组
	final static public String XML_ITEM_MEETING = "item_meeting"; //
	final static public String XML_MEETING_TYPE_NAME = "meeting_type_name"; //会议类型名称
	final static public String XML_ORDER_NUMBER = "order_number"; //会议排序号
	final static public String XML_LEGAL_FLAG = "legal_flag"; //是否需经法律审核
	final static public String XML_REMARK = "remark"; //备注
	
}
