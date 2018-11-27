package com.zefu.tiol.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.ExceptionMapper;
import com.zefu.tiol.service.ExceptionService;

@Service("exceptionService")
public class ExceptionServiceImpl implements ExceptionService{

	@Autowired
	private ExceptionMapper exceptionMapper;
	
	@Override
	public Map<String, Object> queryException(Map<String, Object> param) throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		//获取数据
		List<Map<String, Object>> itemDetail = exceptionMapper.queryException(param);
		List<String> name = new ArrayList<String>();
		List<String> value = new ArrayList<String>();
		List<String> type = new ArrayList<String>();
		if(itemDetail != null && itemDetail.size() > 0){
			//拼接返回格式
			for (Map<String, Object> map : itemDetail) {
				if(map.get("name").toString().equals("1")) {
					name.add("未经合法合规审查");
				}else if(map.get("name").toString().equals("2")){
					name.add("决策会议顺序不当");
				}else if(map.get("name").toString().equals("3")){
					name.add("党委会未前置");
				}else if(map.get("name").toString().equals("4")){
					name.add("会议召开条件不合规");
				}else if(map.get("name").toString().equals("5")){
					name.add("表决结果不一致");
				}
				value.add(map.get("companyNum").toString()+"家，"+map.get("value").toString()+"个");
				
				type.add(map.get("name").toString());
			}
		}
		
		//定义的异常类型，数量固定
		String[] strs = {"1","2","3","4","5"};
		if (type.size() < strs.length) {
			for (int i = 0; i < strs.length; i++) {
				boolean flag = true;
				for (String string : type) {
					if (strs[i].equals(string)) {
						flag = false;
						break;
					}
				}
				if (flag) {
					if(strs[i].equals("1")) {
						name.add("未经合法合规审查");
						value.add("0家0个");
					}else if(strs[i].equals("2")){
						name.add("决策会议顺序不当");
						value.add("0家0个");
					}else if(strs[i].equals("3")){
						name.add("党委会未前置");
						value.add("0家0个");
					}else if(strs[i].equals("4")){
						name.add("会议召开条件不合规");
						value.add("0家0个");
					}else if(strs[i].equals("5")){
						name.add("表决结果不一致");
						value.add("0家0个");
					}
				}
			}
		}
		
		name.add("制度上传不完整");
		value.add(exceptionMapper.queryCompanyByRegulationType(param)+"家");
		data.put("name", name);
		data.put("value", value);
		return data;
	}

	@Override
	public List<Map<String, Object>> querySubjectExceptionByPage(Map<String, Object> parameter,
			Page<Map<String, Object>> page) {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		return exceptionMapper.querySubjectExceptionByPage(parameter);
	}

	@Override
	public int getSubjectExceptionTotalCount(Map<String, Object> parameter) {
		return exceptionMapper.getSubjectExceptionTotalCount(parameter);
	}

	@Override
	public List<Map<String, Object>> queryExceptionType(Map<String, Object> param) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String[] strArr = exceptionMapper.queryExceptionType().split(",");
		for(int i=0;i<strArr.length;i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("exceptionType", i+1);
			map.put("exceptionCause", strArr[i]);
			list.add(map);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> querySubjectListByPage(Map<String, Object> parameter,
			Page<Map<String, Object>> page) {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		return exceptionMapper.querySubjectListByPage(parameter);
	}

	@Override
	public int querySubjectListTotalCount(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return exceptionMapper.querySubjectListTotalCount(parameter);
	}
}
