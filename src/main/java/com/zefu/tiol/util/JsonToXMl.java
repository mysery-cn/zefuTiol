package com.zefu.tiol.util;

import org.json.JSONObject;
import org.json.XML;

import com.alibaba.fastjson.JSONArray;

public class JsonToXMl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//事项
		/*
		JSONObject item = new JSONObject();
		item.put("item_id", "65832456558134956791543829657810");
		item.put("item_name", "公司总部组织机构调整");
		item.put("item_code", "D12-001");
		JSONArray meetingJsonArray = new JSONArray();
		meetingJsonArray.add("党委会");
		meetingJsonArray.add("总经理办公会");
		meetingJsonArray.add("董事会");
		item.put("item_meeting_list", new JSONObject().put("item_meeting", meetingJsonArray));
		item.put("legal_flag", "否");
		item.put("remark", "");
		item.put("oper_type", "add");
		System.out.println(XML.toString(item));
		*/
		//制度
		JSONObject regulationJson = new JSONObject();
		regulationJson.put("regulation_id", "65832456558134956791543829657810");
		regulationJson.put("regulation_name", "公司总部组织机构调整");
		regulationJson.put("regulation_type_name", "公司总部组织机构调整T");
		regulationJson.put("approval_date", "2018-11-16");
		regulationJson.put("effective_date", "2018-11-17");
		regulationJson.put("audit_flag", "是");
		regulationJson.put("rate", "1/2");
		JSONArray meetingJsonArray = new JSONArray();
		meetingJsonArray.add(new JSONObject().put("item_code","默认").put("vote_mode","赞成票超过到会成员人数的1/2"));
		meetingJsonArray.add(new JSONObject().put("item_code","H06-001").put("vote_mode","赞成票超过到会成员人数的3/4"));
		meetingJsonArray.add(new JSONObject().put("item_code","P08-005").put("vote_mode","赞成票超过应到会成员人数的2/3"));
		regulationJson.put("vote_mode_list", new JSONObject().put("vote_mode", meetingJsonArray));
		System.out.println(XML.toString(regulationJson));
	}

}
