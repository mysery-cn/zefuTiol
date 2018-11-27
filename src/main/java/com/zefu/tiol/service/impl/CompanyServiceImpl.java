package com.zefu.tiol.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.common.util.StringUtils;
import com.zefu.tiol.mapper.oracle.CompanyMapper;
import com.zefu.tiol.mapper.reduct.FCeInfoMapper;
import com.zefu.tiol.service.CompanyService;

@Service("tiolCompanyService")
public class CompanyServiceImpl implements CompanyService{
	
	@Autowired
	private FCeInfoMapper fCeInfoMapper;
	
	@Autowired
	private CompanyMapper companyMapper;

	@Override
	public Map<String, Object> synchronizeMsgByReduct() {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		int addNum = 0;
		int updateNum = 0;
		List<Map<String, Object>> list = fCeInfoMapper.listAllMsg();
		for (Map<String, Object> map : list) {
			if (map.get("ENT_NAME") == null || map.get("UNISCID") == null) {
				continue;
			}
			String reductCompanyId = map.get("UNISCID").toString().trim();
			String reductCompanyName = map.get("ENT_NAME").toString().trim();
			String reductCompanyShortName = map.get("ENT_NAME_SHORT").toString().trim();
			String reductCompanyOrder = map.get("XH").toString().trim();
			param.put("companyId", reductCompanyId);
			Map<String, Object> company = companyMapper.getCompanyDataById(param);
			if (company == null) {
				param.put("companyName", reductCompanyName);
				param.put("companyShortName", map.get("ENT_NAME_SHORT"));
				param.put("instId", "ROOT");
				param.put("instName", "国务院国有资产监督管理委员会");
				param.put("displayOrder", reductCompanyOrder);
				param.put("instCompanyType", "2");
				companyMapper.insertCompanyData(param);
				addNum += 1;
			}else if(!company.get("COMPANY_ID").toString().equals(reductCompanyId) ||
					!company.get("COMPANY_SHORT_NAME").toString().equals(reductCompanyShortName)||
					!company.get("DISPLAY_ORDER").toString().equals(reductCompanyOrder)){
				param.put("companyName", map.get("ENT_NAME"));
				param.put("companyShortName", map.get("ENT_NAME_SHORT"));
				param.put("displayOrder", reductCompanyOrder);
				companyMapper.updateCompanyMsg(param);
				updateNum += 1;
			}
		}
		result.put("addNum", addNum);
		result.put("updateNum", updateNum);
		return result;
	}
	
	/**
	 * 使用单位扩展ID存放社会信用代码
	 * @param i
	 * @return
	 */
	public Map<String, Object> synchronizeMsgByReduct(int i) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		int addNum = 0;
		int updateNum = 0;
		List<Map<String, Object>> list = fCeInfoMapper.listAllMsg();
		for (Map<String, Object> map : list) {
			if (map.get("ENT_NAME") == null || map.get("UNISCID") == null) {
				continue;
			}
			String reductCompanyId = map.get("UNISCID").toString().trim();
			String reductCompanyName = map.get("ENT_NAME").toString().trim();
			String reductCompanyShortName = map.get("ENT_NAME_SHORT").toString().trim();
			String reductCompanyOrder = map.get("XH").toString().trim();
			param.put("companyName", reductCompanyName);
			Map<String, Object> company = companyMapper.getCompanyDataById(param);
			if (company == null) {
				param.put("companyId", UUID.randomUUID().toString().replaceAll("-", ""));
				param.put("extendCompanyId", reductCompanyId);
				param.put("companyShortName", reductCompanyShortName);
				param.put("instId", "ROOT");
				param.put("instName", "国务院国有资产监督管理委员会");
				param.put("displayOrder", reductCompanyOrder);
				param.put("instCompanyType", "2");
				companyMapper.insertCompanyData(param);
				addNum += 1;
			}else if(!company.get("EXTEND_COMPANY_ID").toString().equals(reductCompanyId) ||
					!company.get("COMPANY_SHORT_NAME").toString().equals(reductCompanyShortName)||
					!company.get("DISPLAY_ORDER").toString().equals(reductCompanyOrder)){
				param.put("companyId", company.get("COMPANY_ID"));
				param.put("extendCompanyId", reductCompanyId);
				param.put("companyShortName", reductCompanyShortName);
				param.put("displayOrder", reductCompanyOrder);
				companyMapper.updateCompanyMsg(param);
				updateNum += 1;
			}
		}
		result.put("addNum", addNum);
		result.put("updateNum", updateNum);
		return result;
	}

	@Override
	public List<Map<String, Object>> queryDiffrentMsg() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> param = new HashMap<String, Object>();
		List<Map<String, Object>> list = fCeInfoMapper.listAllMsg();
		for (Map<String, Object> map : list) {
			if (map.get("ENT_NAME") == null || map.get("UNISCID") == null) {
				continue;
			}
			String reductCompanyId = map.get("UNISCID").toString().trim();
			String reductCompanyName = map.get("ENT_NAME").toString().trim();
			String reductCompanyShortName = map.get("ENT_NAME_SHORT").toString().trim();
			String reductCompanyOrder = map.get("XH").toString().trim();
			param.put("companyId", reductCompanyId);
			Map<String, Object> company = companyMapper.getCompanyDataById(param);
			if (company == null) {
				Map<String, Object> addMap = new HashMap<String, Object>();
				Map<String, Object> newList = new HashMap<String, Object>();
				newList.put("companyName", reductCompanyName);
				newList.put("companyId", reductCompanyId);
				newList.put("companyShortName", reductCompanyShortName);
				newList.put("displayOrder", reductCompanyOrder);
				addMap.put("reduct", newList);
				addMap.put("system", null);
				result.add(addMap);
			}else if(!company.get("COMPANY_ID").toString().equals(reductCompanyId) ||
					!company.get("COMPANY_SHORT_NAME").toString().equals(reductCompanyShortName)||
					!company.get("DISPLAY_ORDER").toString().equals(reductCompanyOrder)){
				Map<String, Object> addMap = new HashMap<String, Object>();
				Map<String, Object> newList = new HashMap<String, Object>();
				Map<String, Object> oldList = new HashMap<String, Object>();
				newList.put("companyId", reductCompanyId);
				newList.put("companyName", reductCompanyName);
				newList.put("companyShortName", reductCompanyShortName);
				newList.put("displayOrder", reductCompanyOrder);
				oldList.put("companyId", company.get("COMPANY_ID"));
				oldList.put("companyName", company.get("COMPANY_NAME"));
				oldList.put("companyShortName", company.get("COMPANY_SHORT_NAME"));
				oldList.put("displayOrder", company.get("DISPLAY_ORDER"));
				addMap.put("reduct", newList);
				addMap.put("system", oldList);
				result.add(addMap);
			}
		}
		return result;
	}
}
