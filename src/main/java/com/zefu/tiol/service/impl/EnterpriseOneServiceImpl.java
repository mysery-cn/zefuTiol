package com.zefu.tiol.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.EnterpriseOneMapper;
import com.zefu.tiol.mapper.oracle.MeetingTypeMapper;
import com.zefu.tiol.service.EnterpriseOneService;

@Service("enterpriseOneService")
public class EnterpriseOneServiceImpl implements EnterpriseOneService{

	@Autowired
	private EnterpriseOneMapper enterpriseOneMapper;
	@Autowired
	private MeetingTypeMapper meetingTypeMapper;
	
	/**
	 * 重要人事任免分布统计查询
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
		List<Map<String, Object>> itemDetail = enterpriseOneMapper.queryMeetingDetail(parameter);
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
	 * 涉及出资人重大决策议题统计查询
	 * @param parameter
	 * @return
	 */
	@Override
	public Map<String, Object> queryDecisionSubjectDetail(Map<String, Object> parameter,List<Map<String, Object>> companyIdList) {
		Map<String, Object> data = new HashMap<String, Object>();
		//获取数据
		List<String> companyIds = new ArrayList<String>();
		for(Map<String, Object> map_ : companyIdList) {
			companyIds.add(map_.get("companyId").toString());
		}
		parameter.put("companyIds", companyIds);
		List<Map<String, Object>> itemDetail = enterpriseOneMapper.queryDecisionSubjectDetail(parameter);
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
		if(data.get("name") == null){
			data.put("name", "");
		}
		if(data.get("pass") == null){
			data.put("pass", "");
		}
		if(data.get("defer") == null){
			data.put("defer", "");
		}
		if(data.get("veto") == null){
			data.put("veto", "");
		}
		return data;
	}
	
	/**
	 * 2018董事会议题情况统计查询
	 * @param request
	 * @return
	 */
	@Override
	public Map<String, Object> queryInvestorDetail(Map<String, Object> parameter,List<Map<String, Object>> companyIdList) {
		Map<String, Object> data = new HashMap<String, Object>();
		//获取数据
		List<String> companyIds = new ArrayList<String>();
		for(Map<String, Object> map_ : companyIdList) {
			companyIds.add(map_.get("companyId").toString());
		}
		parameter.put("companyIds", companyIds);
		List<Map<String, Object>> investorDetail = enterpriseOneMapper.queryInvestorDetail(parameter);
		if(investorDetail != null && investorDetail.size() > 0){
			//拼接返回格式
			List<String> registerQuantity = new ArrayList<String>();
			List<String> approveQuantity = new ArrayList<String>();
			List<String> subjectQuantity = new ArrayList<String>();
			for (Map<String, Object> map : investorDetail) {
				registerQuantity.add(map.get("registerQuantity").toString());
				approveQuantity.add(map.get("approveQuantity").toString());
				subjectQuantity.add(map.get("subjectQuantity").toString());
			}
			data.put("registerQuantity", registerQuantity);
			data.put("approveQuantity", approveQuantity);
			data.put("subjectQuantity", subjectQuantity);
		}
		if(data.get("registerQuantity") == null){
			data.put("registerQuantity", 0);
		}
		if(data.get("approveQuantity") == null){
			data.put("approveQuantity", 0);
		}
		if(data.get("subjectQuantity") == null){
			data.put("subjectQuantity", 0);
		}
		return data;
	}
	
	/**
	 * 企干一局主页面议题列表查询
	 * @param param
	 * @param page
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryMeetingTypeSubjectListByPage(Map<String, Object> param,Page<Map<String, Object>> page) {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		return enterpriseOneMapper.queryMeetingTypeSubjectListByPage(param);
	}
	
	/**
	 * 根据会议类型查询议题总数
	 */
	@Override
	public int queryTotalCount(Map<String, Object> param) {
		return enterpriseOneMapper.queryTotalCount(param);
	}
	/**
	 * 查询重要人事任免分布————企业会议列表
	 * @param request
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryMeetingByPage(
			Map<String, Object> parameter, Page<Map<String, Object>> page,List<Map<String, Object>> companyIdList) {
		// TODO Auto-generated method stub
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		List<Map<String, Object>> typelist = meetingTypeMapper.queryList(parameter);
		List<String> companyIds = new ArrayList<String>();
		for(Map<String, Object> map_ : companyIdList) {
			companyIds.add(map_.get("companyId").toString());
		}
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		parameter.put("typelist", typelist);
		parameter.put("companyIds", companyIds);
		return enterpriseOneMapper.queryMeetingByPage(parameter);
	}
	/**
	 * 查询重要人事任免分布————企业会议数量统计
	 * @param request
	 * @return
	 */
	@Override
	public int getMeetingTotalCount(Map<String, Object> parameter,List<Map<String, Object>> companyIdList) {
		List<String> companyIds = new ArrayList<String>();
		for(Map<String, Object> map_ : companyIdList) {
			companyIds.add(map_.get("companyId").toString());
		}
		parameter.put("companyIds", companyIds);
		return enterpriseOneMapper.getMeetingTotalCount(parameter);
	}
	/**
	 * 查询涉及出资人重大决策议题————企业议题列表
	 * @param request
	 * @return
	 */
	@Override
	public List<Map<String, Object>> querySubjectPageList(Map<String, Object> parameter,Page<Map<String, Object>> page) {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		parameter.put("itemId", "D");
		List<Map<String, Object>> list = enterpriseOneMapper.querySubjectPageList(parameter);
		return list;
	}
	/**
	 * 查询涉及出资人重大决策议题————企业议题列数量统计
	 * @param parameter
	 * @return
	 */
	@Override
	public int querySubjectTotalCount(Map<String, Object> parameter) {
		return enterpriseOneMapper.querySubjectTotalCount(parameter);
	}
	
	@Override
	public List<Map<String, Object>> getCurUserCompany(String instId) {
		Map<String, Object> data = new HashMap<String, Object>();
		//获取数据
		List<Map<String, Object>> companyList = enterpriseOneMapper.getCurUserCompany(instId);
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
	
	@Override
	public List<Map<String, Object>> querySubjectPageListDsh(Map<String, Object> parameter,Page<Map<String, Object>> page) {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		List<Map<String, Object>> list = enterpriseOneMapper.querySubjectPageListDsh(parameter);
		return list;
	}
	
	@Override
	public int querySubjectDshTotalCount(Map<String, Object> parameter) {
		return enterpriseOneMapper.querySubjectDshTotalCount(parameter);
	}
}
