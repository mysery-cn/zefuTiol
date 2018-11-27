package com.zefu.tiol.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.MeetingTypeMapper;
import com.zefu.tiol.mapper.oracle.StatisticsMeetingMapper;
import com.zefu.tiol.service.StatisticsMeetingService;

/**
   * @工程名 : szyd
   * @文件名 : StatisticsMeetingServiceImpl.java
   * @工具包名：com.zefu.tiol.service.impl
   * @功能描述: TODO 会议分类统计逻辑层
   * @创建人 ：林鹏
   * @创建时间：2018年10月25日 上午9:48:17
   * @版本信息：V1.0
 */
@Service("statisticsMeetingService")
public class StatisticsMeetingServiceImpl implements StatisticsMeetingService {
	
	@Autowired
	private StatisticsMeetingMapper meetingMapper;
	
	@Autowired
	private MeetingTypeMapper meetingTypeMapper;
	
	/**
	 * 查询会议分类统计结果
	 */
	@Override
	public Map<String, Object> queryMeetingDetail(Map<String, Object> parameter) {
		Map<String, Object> data = new HashMap<String, Object>();
		//获取数据
		List<Map<String, Object>> itemDetail = meetingMapper.queryMeetingDetail(parameter);
		if(itemDetail != null && itemDetail.size() > 0){
			//拼接返回格式
			List<String> name = new ArrayList<String>();
			List<String> value = new ArrayList<String>();
			for (Map<String, Object> map : itemDetail) {
				name.add(map.get("name").toString());
				value.add(map.get("value").toString());
			}
			data.put("name", name);
			data.put("value", value);
		}
		if(data.get("name") == null){
			data.put("name", "");
		}
		if(data.get("value") == null){
			data.put("value", "");
		}
		return data;
	}
	
	/**
	 * 查询当前年份决策会议情况
	 */
	@Override
	public Map<String, Object> queryDecisionMeetingDetail(Map<String, Object> parameter) {
		Map<String, Object> data = new HashMap<String, Object>();
		//获取数据
		List<Map<String, Object>> itemDetail = meetingMapper.queryDecisionMeetingDetail(parameter);
		if(itemDetail != null && itemDetail.size() > 0){
			//拼接返回格式
			List<String> name = new ArrayList<String>();
			List<String> value = new ArrayList<String>();
			for (Map<String, Object> map : itemDetail) {
				name.add(map.get("name").toString());
				value.add(map.get("value").toString());
			}
			data.put("name", name);
			data.put("value", value);
		}
		return data;
	}
	
	/**
	 * 查询当前年份决策议题情况
	 */
	@Override
	public Map<String, Object> queryDecisionSubjectDetail(Map<String, Object> parameter) {
		Map<String, Object> data = new HashMap<String, Object>();
		//获取数据
		List<Map<String, Object>> itemDetail = meetingMapper.queryDecisionSubjectDetail(parameter);
		if(itemDetail != null && itemDetail.size() > 0){
			//拼接返回格式
			List<String> name = new ArrayList<String>();
			List<String> pass = new ArrayList<String>();
			List<String> defer = new ArrayList<String>();
			List<String> veto = new ArrayList<String>();
			for (Map<String, Object> map : itemDetail) {
				name.add(map.get("name").toString());
				pass.add(map.get("pass").toString());
				defer.add(map.get("defer").toString());
				veto.add(map.get("veto").toString());
			}
			data.put("name", name);
			data.put("pass", pass);
			data.put("defer", defer);
			data.put("veto", veto);
		}
		return data;
	}

	@Override
	public Map<String, Object> queryMeetingClassDetail(
			Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		List<Map<String, Object>> typelist = meetingTypeMapper.queryList(parameter);
		parameter.put("typelist", typelist);
		//获取数据
		List<LinkedHashMap<String, Object>> detail = meetingMapper.queryMeetingClassDetail(parameter);
		List<String> name = new ArrayList<String>();
		List<String> type = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();
		for (Map<String, Object> map : typelist) {
			if (map.get("TYPE_NAME") != null) {
				type.add(map.get("TYPE_NAME").toString());
			}
		}
		//获取会议数量
		if (detail.size() > 0) {
			for (Map<String, Object> map : detail) {
				name.add(map.get("name").toString());
				List<String> value = new ArrayList<String>();
				Iterator iter = map.entrySet().iterator();
				while (iter.hasNext()) {
					Entry entry = (Entry) iter.next();
				    String key = entry.getKey().toString();
				    if (key.length() > 5 && key.substring(0, 5).equals("value")) {
				    	String val = entry.getValue().toString();
				    	value.add(val);
					}
				}
				values.add(value);
			}
		} else {
			name.add(new SimpleDateFormat("yyyy年MM月").format(new Date()));
			List<String> list = new ArrayList<String>();
			for (String str : type) {
				list.add("0");
			}
			values.add(list);
		}
		//行转列
		List<List> lists=new ArrayList<List>();
		if (values.size() > 0) {
			int index=0;
			index=((List) values.get(0)).size();
			List list=null;
			for (int i = 0; i < index; i++) {
				list=new ArrayList<List>();
				for (Object item : values) {
					list.add(((List) item).get(i));
				}
				lists.add(list);
			}
		}
		
		data.put("type", type);
		data.put("name", name);
		data.put("value", lists);
		return data;
	}

	@Override
	public List<Map<String, Object>> queryMeetingByPage(
			Map<String, Object> parameter, Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		List<Map<String, Object>> typelist = meetingTypeMapper.queryList(parameter);
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		parameter.put("typelist", typelist);
		String sql = "";
		for (int i = 0; i < typelist.size(); i++) {
			if (i == 0) {
				sql = "convert(int,TYPECODE"+i+")";
			} else {
				sql = sql +"+convert(int,TYPECODE"+i+")"; 
			}
		}
		parameter.put("sql", sql);
		return meetingMapper.queryMeetingByPage(parameter);
	}

	@Override
	public int getMeetingTotalCount(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return meetingMapper.getMeetingTotalCount(parameter);
	}
	
	
	
}
