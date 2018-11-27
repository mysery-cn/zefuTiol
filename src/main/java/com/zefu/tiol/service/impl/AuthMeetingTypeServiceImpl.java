package com.zefu.tiol.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.AuthMeetingTypeMapper;
import com.zefu.tiol.service.AuthMeetingTypeService;

@Service("authMeetingTypeService")
public class AuthMeetingTypeServiceImpl implements AuthMeetingTypeService{

	@Autowired
	private AuthMeetingTypeMapper authMeetingTypeMapper;

	@Override
	public List<Map<String, Object>> queryAuthMeetingTypeTree(Map<String, Object> param) {
		return authMeetingTypeMapper.queryAuthMeetingTypeTree(param);
	}

	@Override
	public List<Map<String, Object>> queryAuthMeetingTypeListByPage(Map<String, Object> param,
			Page<Map<String, Object>> page) throws Exception {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		return authMeetingTypeMapper.queryAuthMeetingTypeListByPage(param);
	}

	@Override
	public int queryAuthMeetingTypeListByPageCount(Map<String, Object> param) {
		return authMeetingTypeMapper.queryAuthMeetingTypeListByPageCount(param);
	}

	@Override
	public void saveAuthMeetingType(Map<String, Object> param) throws Exception {
		authMeetingTypeMapper.deleteAuthMeetingType(param);
		String[] userIds = param.get("userIds").toString().split(",");
		String[] userNames = param.get("userNames").toString().split(",");
		
		String[] roleIds = param.get("roleIds").toString().split(",");
		String[] roleNames = param.get("roleNames").toString().split(",");
		
		String[] groupIds = param.get("groupIds").toString().split(",");
		String[] groupNames = param.get("groupNames").toString().split(",");
		if(userIds[0].equals("") != true) {
			for(int i=0;i<userIds.length;i++) {
				param.put("AUTH_ID", UUID.randomUUID().toString().replaceAll("-", ""));
				param.put("TYPE_ID", param.get("cid").toString());
				param.put("RELATION_TYPE", "1");
				param.put("RELATION_ID", userIds[i].toString());
				authMeetingTypeMapper.saveAuthMeetingType(param);
			}
		}
		if(roleIds[0].equals("") != true) {
			for(int i=0;i<roleIds.length;i++) {
				param.put("AUTH_ID", UUID.randomUUID().toString().replaceAll("-", ""));
				param.put("TYPE_ID", param.get("cid").toString());
				param.put("RELATION_TYPE", "2");
				param.put("RELATION_ID", roleIds[i].toString());
				authMeetingTypeMapper.saveAuthMeetingType(param);
			}
		}
		if(groupIds[0].equals("") != true) {
			for(int i=0;i<groupIds.length;i++) {
				param.put("AUTH_ID", UUID.randomUUID().toString().replaceAll("-", ""));
				param.put("TYPE_ID", param.get("cid").toString());
				param.put("RELATION_TYPE", "3");
				param.put("RELATION_ID", groupIds[i].toString());
				authMeetingTypeMapper.saveAuthMeetingType(param);
			}
		}
	}

	@Override
	public void deleteAuthMeetingType(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Map<String, Object>> reloadForm(Map<String, Object> param) {
		List<Map<String, Object>> userList = authMeetingTypeMapper.reloadFormUser(param);
		List<Map<String, Object>> roleList = authMeetingTypeMapper.reloadFormRole(param);
		List<Map<String, Object>> orgList = authMeetingTypeMapper.reloadFormOrg(param);
		Map<String, Object> map = new HashMap<String, Object>();
		String userIds = "";String userNames = "";
		if(userList.size()>0) {
			for(int i=0;i<userList.size();i++) {
				userIds = userIds+userList.get(i).get("userId")+",";
				userNames = userNames+userList.get(i).get("userName")+",";
			}
			userIds = userIds.substring(0, userIds.length()-1);
			userNames = userNames.substring(0, userNames.length()-1);
		}
		String roleIds = "";String roleNames = "";
		if(roleList.size()>0) {
			for(int j=0;j<roleList.size();j++) {
				roleIds = roleIds+roleList.get(j).get("roleId")+",";
				roleNames = roleNames+roleList.get(j).get("roleName")+",";
			}
			roleIds = roleIds.substring(0, roleIds.length()-1);
			roleNames = roleNames.substring(0, roleNames.length()-1);
		}
		String orgIds = "";String orgNames = "";
		if(orgList.size()>0) {
			for(int n=0;n<orgList.size();n++) {
				orgIds = orgIds+orgList.get(n).get("orgId")+",";
				orgNames = orgNames+orgList.get(n).get("orgName")+",";
			}
			orgIds = orgIds.substring(0, orgIds.length()-1);
			orgNames = orgNames.substring(0, orgNames.length()-1);
		}
		map.put("userIds", userIds);
		map.put("userNames", userNames);
		map.put("roleIds", roleIds);
		map.put("roleNames", roleNames);
		map.put("orgIds", orgIds);
		map.put("orgNames", orgNames);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(map);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getPermit(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return authMeetingTypeMapper.getPermit(param);
	}
}
