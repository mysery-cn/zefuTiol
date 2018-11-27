package com.zefu.tiol.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.EnterpriseTwoMapper;
import com.zefu.tiol.service.EnterpriseTwoService;

@Service("enterpriseTwoService")
public class EnterpriseTwoServiceImpl implements EnterpriseTwoService{

	@Autowired
	private EnterpriseTwoMapper enterpriseTwoMapper;
	
	/**
	 * 重要人事任免会议分布统计图
	 * @param parameter
	 * @return
	 */
	@Override
	public Map<String, Object> queryMeetingDetail(Map<String, Object> parameter,List<Map<String, Object>> companyIdList) {
		Map<String, Object> data = new HashMap<String, Object>();
		//获取数据
		int flag = 0;
		List<String> companyIds = new ArrayList<String>();
		for(Map<String, Object> map_ : companyIdList) {
			companyIds.add(map_.get("companyId").toString());
		}
		parameter.put("companyIds", companyIds);
		List<Map<String, Object>> itemDetail = enterpriseTwoMapper.queryMeetingDetail(parameter);
		if(itemDetail != null && itemDetail.size() > 0){
			//拼接返回格式
			List<String> name = new ArrayList<String>();
			List<String> value = new ArrayList<String>();
			for (Map<String, Object> map : itemDetail) {
				name.add(map.get("name").toString());
				value.add(map.get("value").toString());
				if(map.get("value").toString().equals("0")) {
					flag++;
				}
			}
			data.put("name", name);
			data.put("value", value);
		}
		if(flag==itemDetail.size()) {
			data.put("name", "");
			data.put("value", "");
		}
		return data;
	}
	
	/**
	 * 2018年董事会议题情况统计图
	 * @param parameter
	 * @return
	 */
	@Override
	public Map<String, Object> querySubjectDetail(Map<String, Object> parameter,List<Map<String, Object>> companyIdList) {
		Map<String, Object> data = new HashMap<String, Object>();
		//获取数据
		int flag = 0;
		parameter.put("companyList", companyIdList);
		List<Map<String, Object>> itemDetail = enterpriseTwoMapper.querySubjectDetail(parameter);
		if(itemDetail != null && itemDetail.size() > 0){
			//拼接返回格式
			List<String> name = new ArrayList<String>();
			List<String> value = new ArrayList<String>();
			for (Map<String, Object> map : itemDetail) {
				name.add(map.get("name").toString());
				value.add(map.get("value").toString());
				if(map.get("value").toString().equals("0")) {
					flag++;
				}
			}
			data.put("name", name);
			data.put("value", value);
		}
		if(flag==itemDetail.size()) {
			data.put("name", "");
			data.put("value", "");
		}
		return data;
	}
	
	/**
	 * 企干二局主页面议题列表查询
	 * @param param
	 * @param page
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryMeetingTypeSubjectListByPage(Map<String, Object> param,Page<Map<String, Object>> page,List<Map<String, Object>> companyIdList) {
		List<String> companyIds = new ArrayList<String>();
		for(Map<String, Object> map_ : companyIdList) {
			companyIds.add(map_.get("companyId").toString());
		}
		param.put("companyIds", companyIds);
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		return enterpriseTwoMapper.queryMeetingTypeSubjectListByPage(param);
	}
	
	/**
	 *  企干二局主页面议题列表数量统计
	 * @param param
	 * @return
	 */
	@Override
	public int queryTotalCount(Map<String, Object> param,List<Map<String, Object>> companyIdList) {
		List<String> companyIds = new ArrayList<String>();
		for(Map<String, Object> map_ : companyIdList) {
			companyIds.add(map_.get("companyId").toString());
		}
		param.put("companyIds", companyIds);
		return enterpriseTwoMapper.queryTotalCount(param);
	}
	
	/**
	 * 查询级别为2且编码已H开头的事项目录
	 * @param param
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryCatalogLevel2andH(Map<String, Object> param) {
		return enterpriseTwoMapper.queryCatalogLevel2andH(param);
	}

	/**
	 *  查询二局内页董事会议题列表
	 * @param parameter
	 * @param page
	 * @return
	 */
	@Override
	public List<Map<String, Object>> querySubjectPageList(Map<String, Object> parameter,Page<Map<String, Object>> page,List<Map<String, Object>> companyIdList) {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		List<Map<String, Object>> list = enterpriseTwoMapper.querySubjectPageList(parameter);
		return list;
	}
	
	/**
	 *  二局内页董事会议题列表数量统计
	 * @param parameter
	 * @return
	 */
	@Override
	public int querySubjectTotalCount(Map<String, Object> parameter,List<Map<String, Object>> companyIdList) {
		return enterpriseTwoMapper.querySubjectTotalCount(parameter);
	}
	
	/**
	 * 重载二局内页董事会议题列表
	 * @param parameter
	 * @param page
	 * @return
	 */
	@Override
	public List<Map<String, Object>> reloadSubjectPageList(Map<String, Object> parameter,Page<Map<String, Object>> page,List<Map<String, Object>> companyIdList) {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		List<String> companyIds = new ArrayList<String>();
		for(Map<String, Object> map_ : companyIdList) {
			companyIds.add(map_.get("companyId").toString());
		}
		parameter.put("companyIds", companyIds);
		List<Map<String, Object>> catalogIdList = enterpriseTwoMapper.getCatalogIdsByName(parameter.get("catalogName").toString());
		List<String> catalogIds = new ArrayList<String>();
		for(Map<String, Object> map_ : catalogIdList) {
			catalogIds.add(map_.get("catalogId").toString());
		}
		parameter.put("catalogIds", catalogIds);
		List<Map<String, Object>> list = enterpriseTwoMapper.getSubjectByCatalogIds(parameter);
		return list;
	}
	
	/**
	 * 重载二局内页董事会议题数量统计
	 * @param parameter
	 * @return
	 */
	@Override
	public int reloadSubjectTotalCount(Map<String, Object> parameter,List<Map<String, Object>> companyIdList) {
		List<String> companyIds = new ArrayList<String>();
		for(Map<String, Object> map_ : companyIdList) {
			companyIds.add(map_.get("companyId").toString());
		}
		parameter.put("companyIds", companyIds);
		List<Map<String, Object>> catalogIdList = enterpriseTwoMapper.getCatalogIdsByName(parameter.get("catalogName").toString());
		List<String> catalogIds = new ArrayList<String>();
		for(Map<String, Object> map_ : catalogIdList) {
			catalogIds.add(map_.get("catalogId").toString());
		}
		parameter.put("catalogIds", catalogIds);
		return enterpriseTwoMapper.reloadSubjectTotalCount(parameter);
	}
	
	@Override
	public List<Map<String, Object>> getCurUserCompany(String deptId) {
		Map<String, Object> data = new HashMap<String, Object>();
		//获取数据
		List<Map<String, Object>> companyList = enterpriseTwoMapper.getCurUserCompany(deptId);
		if(companyList != null && companyList.size() > 0){
			//拼接返回格式
			List<String> companyId = new ArrayList<String>();
			for (Map<String, Object> map : companyList) {
				companyId.add(map.get("companyId").toString());
			}
			data.put("companyId", companyId);
		}
		return companyList;
	}
}
