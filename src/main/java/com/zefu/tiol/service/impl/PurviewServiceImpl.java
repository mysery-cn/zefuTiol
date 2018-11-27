package com.zefu.tiol.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.PurviewMapper;
import com.zefu.tiol.service.PurviewService;

@Service("purviewService")
public class PurviewServiceImpl implements PurviewService{

	@Autowired
	private PurviewMapper purviewMapper;
	
	@Override
	public List<Map<String, Object>> queryPurviewList(Map<String, Object> param) throws Exception {
		return purviewMapper.queryPurviewList(param);
	}

	@Override
	public List<Map<String, Object>> queryPurviewListByPage(Map<String, Object> param, Page<Map<String, Object>> page)
			throws Exception {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		List<Map<String, Object>> purviewList = purviewMapper.queryPurviewListByPage(param);
		if(purviewList != null && purviewList.size()>0) {
			for(int i=0;i<purviewList.size();i++) {
				List<Map<String, Object>> catalogList = purviewMapper.queryPurviewCatalogListById(purviewList.get(i).get("PURVIEW_ID").toString());
				purviewList.get(i).put("catalogList", catalogList);
				purviewList.get(i).put("catalogCount", catalogList.size());
				
				List<Map<String, Object>> meetingTypeList = purviewMapper.queryPurviewMeetingTypeListById(purviewList.get(i).get("PURVIEW_ID").toString());
				purviewList.get(i).put("meetingTypeList", meetingTypeList);
				purviewList.get(i).put("meetingTypeCount", meetingTypeList.size());
				
				List<Map<String, Object>> companyList = purviewMapper.queryPurviewCompanyListById(purviewList.get(i).get("PURVIEW_ID").toString());
				purviewList.get(i).put("companyList", companyList);
				purviewList.get(i).put("companyCount", companyList.size());
			}
		}
		return purviewList;
	}

	@Override
	public int queryPurviewListByPageCount(Map<String, Object> param) {
		return purviewMapper.queryPurviewListByPageCount(param);
	}

	@Override
	public void savePurview(Map<String, Object> param) throws Exception {
		//新增权限主表---单条记录
		param.put("PURVIEW_ID", UUID.randomUUID().toString().replaceAll("-", ""));
		param.put("PURVIEW_TYPE", param.get("purviewType").toString());
		param.put("PURVIEW_OBJECT", param.get("purviewObject").toString());
		param.put("OBJECT_NAME", param.get("objectName").toString());
		param.put("START_TIME", param.get("startTime").toString());
		param.put("END_TIME", param.get("endTime").toString());
		param.put("STATUS", "1");
		param.put("CREATE_USER", param.get("userId").toString());
		purviewMapper.savePurview(param);
		//新增事项目录次表---多条记录
		String[] ids =  param.get("catalogIds").toString().split(",");
		for(int i=0;i<ids.length;i++) {
			param.put("CATALOG_ID", ids[i]);
			purviewMapper.savePurviewCatalog(param);
		}
		//新增会议类型次表---多条记录
		String[] mids = param.get("meetingTypeIds").toString().split(",");
		for(int i=0;i<mids.length;i++) {
			param.put("TYPE_ID", mids[i]);
			purviewMapper.savePurviewMeetingType(param);
		}
		//新增企业次表---多条记录
		String[] cids = param.get("companyIds").toString().split(",");
		for(int i=0;i<cids.length;i++) {
			param.put("COMPANY_ID", cids[i]);
			purviewMapper.savePurviewCompany(param);
		}
	}

	@Override
	public void updatePurview(Map<String, Object> param) throws Exception {
		param.put("PURVIEW_ID", param.get("purviewId").toString());
		param.put("PURVIEW_TYPE", param.get("purviewType").toString());
		param.put("PURVIEW_OBJECT", param.get("purviewObject").toString());
		param.put("OBJECT_NAME", param.get("objectName").toString());
		param.put("START_TIME", param.get("startTime").toString());
		param.put("END_TIME", param.get("endTime").toString());
		param.put("UPDATE_USER", param.get("userId").toString());
		purviewMapper.updatePurview(param);
		//根据ID删除次表记录重新添加
		purviewMapper.detePurviewCatalog(param);
        purviewMapper.detePurviewMeetingType(param);
        purviewMapper.detePurviewCompany(param);
		String[] ids =  param.get("catalogIds").toString().split(",");
		for(int i=0;i<ids.length;i++) {
			param.put("CATALOG_ID", ids[i]);
			purviewMapper.savePurviewCatalog(param);
		}

		String[] mids =  param.get("meetingTypeIds").toString().split(",");
		for(int i=0;i<mids.length;i++) {
			param.put("TYPE_ID", mids[i]);
			purviewMapper.savePurviewMeetingType(param);
		}
		
		String[] cids =  param.get("companyIds").toString().split(",");
		for(int i=0;i<cids.length;i++) {
			param.put("COMPANY_ID", cids[i]);
			purviewMapper.savePurviewCompany(param);
		}
	}

	@Override
	public void deletePurview(Map<String, Object> param) throws Exception {
		if(purviewMapper.deletePurview(param)) {
			purviewMapper.deletePurviewCatalog(param);
			purviewMapper.deletePurviewMeetingType(param);
			purviewMapper.deletePurviewCompany(param);
		}
	}

	@Override
	public Map<String, Object> queryPurviewById(Map<String, Object> param)throws Exception {
		Map<String, Object> map = purviewMapper.queryPurviewById(param.get("purviewId").toString());
		List<Map<String, Object>> catalogList = purviewMapper.queryPurviewCatalogListById(param.get("purviewId").toString());
		String catalogIds = "";
		for(int i=0;i<catalogList.size();i++) {
			catalogIds = catalogIds+catalogList.get(i).get("CATALOG_ID")+",";
		}
		List<Map<String, Object>> meetingTypeList = purviewMapper.queryPurviewMeetingTypeListById(param.get("purviewId").toString());
		String meetingTypeIds = "";
		for(int i=0;i<meetingTypeList.size();i++) {
			meetingTypeIds = meetingTypeIds+meetingTypeList.get(i).get("TYPE_ID")+",";
		}
		List<Map<String, Object>> companyList = purviewMapper.queryPurviewCompanyListById(param.get("purviewId").toString());
		String companyIds = "";
		String companyNames = "";
		for(int i=0;i<companyList.size();i++) {
			companyIds = companyIds+companyList.get(i).get("COMPANY_ID")+",";
			companyNames = companyNames+companyList.get(i).get("COMPANY_SHORT_NAME")+",";
		}
		
		map.put("catalogIds", catalogIds.substring(0, catalogIds.length()-1));
		map.put("meetingTypeIds", meetingTypeIds.substring(0, meetingTypeIds.length()-1));
		map.put("companyIds", companyIds.substring(0, companyIds.length()-1));
		map.put("companyNames", companyNames.substring(0, companyNames.length()-1));
		return map;
	}

	@Override
	public Map<String, Object> getPermit(Map<String, Object> param) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> user_purviewIds = purviewMapper.queryPurviewByParam("1",param.get("userId").toString());
		List<String> org_purviewIds = purviewMapper.queryPurviewByParam("0",param.get("orgId").toString());
		List<String> roleIds = (List<String>) param.get("roleIds");
		List<String> role_purviewIds ;
		List<String> new_role_purviewIds = new ArrayList<String>();
		for(int i=0;i<roleIds.size();i++) {
			role_purviewIds = purviewMapper.queryPurviewByParam("1",roleIds.get(i));
			if(role_purviewIds.size()==0) {
				continue;
			}else {
				for(int j=0;j<role_purviewIds.size();j++) {
					new_role_purviewIds.add(role_purviewIds.get(j));
				}
			}
		}
		
		if(user_purviewIds.size()!=0&&org_purviewIds.size()==0&&new_role_purviewIds.size()==0) {
			param.put("ids", user_purviewIds);
			List<Map<String, Object>> user_catalogList = purviewMapper.queryPurviewCatalogListByIds(param);
			List<Map<String, Object>> user_meetingTypeList = purviewMapper.queryPurviewMeetingTypeListByIds(param);
			List<Map<String, Object>> user_companyList = purviewMapper.queryPurviewCompanyListByIds(param);
			map.put("catalogList", user_catalogList);
			map.put("meetingTypeList", user_meetingTypeList);
			map.put("companyList", user_companyList);
		}
		if(user_purviewIds.size()==0&&org_purviewIds.size()!=0&&new_role_purviewIds.size()==0) {
			param.put("ids", org_purviewIds);
			List<Map<String, Object>> org_catalogList = purviewMapper.queryPurviewCatalogListByIds(param);
			List<Map<String, Object>> org_meetingTypeList = purviewMapper.queryPurviewMeetingTypeListByIds(param);
			List<Map<String, Object>> org_companyList = purviewMapper.queryPurviewCompanyListByIds(param);
			map.put("catalogList", org_catalogList);
			map.put("meetingTypeList", org_meetingTypeList);
			map.put("companyList", org_companyList);
		}
		if(user_purviewIds.size()==0&&org_purviewIds.size()==0&&new_role_purviewIds.size()!=0) {
			param.put("ids", new_role_purviewIds);
			List<Map<String, Object>> role_catalogList = purviewMapper.queryPurviewCatalogListByIds(param);
			List<Map<String, Object>> role_meetingTypeList = purviewMapper.queryPurviewMeetingTypeListByIds(param);
			List<Map<String, Object>> role_companyList = purviewMapper.queryPurviewCompanyListByIds(param);
			map.put("catalogList", role_catalogList);
			map.put("meetingTypeList", role_meetingTypeList);
			map.put("companyList", role_companyList);
		}
		
		if(user_purviewIds.size()!=0&&org_purviewIds.size()!=0&&new_role_purviewIds.size()==0) {
			param.put("ids", user_purviewIds);
			List<Map<String, Object>> user_catalogList = purviewMapper.queryPurviewCatalogListByIds(param);
			List<Map<String, Object>> user_meetingTypeList = purviewMapper.queryPurviewMeetingTypeListByIds(param);
			List<Map<String, Object>> user_companyList = purviewMapper.queryPurviewCompanyListByIds(param);
			param.put("ids", org_purviewIds);
			List<Map<String, Object>> org_catalogList = purviewMapper.queryPurviewCatalogListByIds(param);
			List<Map<String, Object>> org_meetingTypeList = purviewMapper.queryPurviewMeetingTypeListByIds(param);
			List<Map<String, Object>> org_companyList = purviewMapper.queryPurviewCompanyListByIds(param);
			map.put("catalogList", getTheSameSection(user_catalogList,org_catalogList));
			map.put("meetingTypeList", getTheSameSection(user_meetingTypeList,org_meetingTypeList));
			map.put("companyList", getTheSameSection(user_companyList,org_companyList));
		}
		if(user_purviewIds.size()!=0&&org_purviewIds.size()==0&&new_role_purviewIds.size()!=0) {
			param.put("ids", user_purviewIds);
			List<Map<String, Object>> user_catalogList = purviewMapper.queryPurviewCatalogListByIds(param);
			List<Map<String, Object>> user_meetingTypeList = purviewMapper.queryPurviewMeetingTypeListByIds(param);
			List<Map<String, Object>> user_companyList = purviewMapper.queryPurviewCompanyListByIds(param);
			param.put("ids", new_role_purviewIds);
			List<Map<String, Object>> role_catalogList = purviewMapper.queryPurviewCatalogListByIds(param);
			List<Map<String, Object>> role_meetingTypeList = purviewMapper.queryPurviewMeetingTypeListByIds(param);
			List<Map<String, Object>> role_companyList = purviewMapper.queryPurviewCompanyListByIds(param);
			map.put("catalogList", getTheSameSection(user_catalogList,role_catalogList));
			map.put("meetingTypeList", getTheSameSection(user_meetingTypeList,role_meetingTypeList));
			map.put("companyList", getTheSameSection(user_companyList,role_companyList));
		}
		if(user_purviewIds.size()==0&&org_purviewIds.size()!=0&&new_role_purviewIds.size()!=0) {
			param.put("ids", org_purviewIds);
			List<Map<String, Object>> org_catalogList = purviewMapper.queryPurviewCatalogListByIds(param);
			List<Map<String, Object>> org_meetingTypeList = purviewMapper.queryPurviewMeetingTypeListByIds(param);
			List<Map<String, Object>> org_companyList = purviewMapper.queryPurviewCompanyListByIds(param);
			param.put("ids", new_role_purviewIds);
			List<Map<String, Object>> role_catalogList = purviewMapper.queryPurviewCatalogListByIds(param);
			List<Map<String, Object>> role_meetingTypeList = purviewMapper.queryPurviewMeetingTypeListByIds(param);
			List<Map<String, Object>> role_companyList = purviewMapper.queryPurviewCompanyListByIds(param);
			map.put("catalogList", getTheSameSection(org_catalogList,role_catalogList));
			map.put("meetingTypeList", getTheSameSection(org_meetingTypeList,role_meetingTypeList));
			map.put("companyList", getTheSameSection(org_companyList,role_companyList));
		}
		
		if(user_purviewIds.size()!=0&&org_purviewIds.size()!=0&&new_role_purviewIds.size()!=0) {
			param.put("ids", user_purviewIds);
			List<Map<String, Object>> user_catalogList = purviewMapper.queryPurviewCatalogListByIds(param);
			List<Map<String, Object>> user_meetingTypeList = purviewMapper.queryPurviewMeetingTypeListByIds(param);
			List<Map<String, Object>> user_companyList = purviewMapper.queryPurviewCompanyListByIds(param);
			param.put("ids", org_purviewIds);
			List<Map<String, Object>> org_catalogList = purviewMapper.queryPurviewCatalogListByIds(param);
			List<Map<String, Object>> org_meetingTypeList = purviewMapper.queryPurviewMeetingTypeListByIds(param);
			List<Map<String, Object>> org_companyList = purviewMapper.queryPurviewCompanyListByIds(param);
			param.put("ids", new_role_purviewIds);
			List<Map<String, Object>> role_catalogList = purviewMapper.queryPurviewCatalogListByIds(param);
			List<Map<String, Object>> role_meetingTypeList = purviewMapper.queryPurviewMeetingTypeListByIds(param);
			List<Map<String, Object>> role_companyList = purviewMapper.queryPurviewCompanyListByIds(param);
			
			List<Map<String, Object>> newCatalogList = getTheSameSection(user_catalogList,org_catalogList);
			List<Map<String, Object>> newMeetingTypeList = getTheSameSection(user_meetingTypeList,org_meetingTypeList);
			List<Map<String, Object>> newCompanyList = getTheSameSection(user_companyList,org_companyList);
			
			map.put("catalogList", getTheSameSection(newCatalogList,role_catalogList));
			map.put("meetingTypeList", getTheSameSection(newMeetingTypeList,role_meetingTypeList));
			map.put("companyList", getTheSameSection(newCompanyList,role_companyList));
		}
		if(user_purviewIds.size()==0&&org_purviewIds.size()==0&&new_role_purviewIds.size()==0) {
			List<Map<String, Object>> nullList = new ArrayList<Map<String, Object>>();
			map.put("catalogList", nullList);
			map.put("meetingTypeList", nullList);
			map.put("companyList", nullList);
		}
		return map;
	}

	public List getTheSameSection(List<Map<String, Object>> list1,List<Map<String, Object>> list2) {
		List resultList = new ArrayList();
		for (Map<String, Object> item:list2) {//遍历list1
			if (list1.contains(item)) {//如果存在这个数
			resultList.add(item);//放进一个list里面，这个list就是交集
			}
		}
		return resultList;
	}

	@Override
	public List<Map<String, Object>> listPurviewPerson(Map<String, Object> param) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Object catalogId = param.get("catalogId");
		if(catalogId!=null){
			String[] catalogIds = catalogId.toString().split(",");
			param.put("catalogIds", catalogIds);
		}
		List<Map<String, Object>> list = purviewMapper.listPurviewPerson(param);
		for (Map<String, Object> map : list) {
			String purviewObject = (String) map.get("purviewObject");
			String objectName = (String) map.get("objectName");
			String[] ids = purviewObject.split(",");
			String[] names = objectName.split(",");
			for (int i = 0; i<ids.length;i++) {
				Map<String, Object> object = new HashMap<String, Object>();
				object.put("id", ids[i]);
				object.put("name", names[i]);
				result.add(object);
			}
		}
		return result;
	}	
}
