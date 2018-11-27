package com.zefu.tiol.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zefu.tiol.mapper.cadre.CadreMsgMapper;
import com.zefu.tiol.mapper.oracle.AuthMeetingTypeMapper;
import com.zefu.tiol.mapper.oracle.CompanyMemberMapper;
import com.zefu.tiol.mapper.oracle.CompanyMemberTypeMapper;
import com.zefu.tiol.service.CompanyMemberService;

/**
 * 
 * @功能描述 企业领导班子成员
 * @time 2018年10月26日下午3:24:24
 * @author Super
 *
 */
@Service("companyMemberService")
public class CompanyMemberServiceImpl implements CompanyMemberService {
	
	@Autowired
	private CompanyMemberMapper companyMemberMapper;
	
	@Autowired
	private CadreMsgMapper cadreMsgMapper;

	@Autowired
	private AuthMeetingTypeMapper authMeetingTypeMapper;

	@Autowired
	private CompanyMemberTypeMapper typeMapper;
	
	@Override
	public List<Map<String, Object>> queryCompanyMemberList(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		return companyMemberMapper.queryCompanyMemberList(param);
	}

	@Override
	public List<Map<String, Object>> getCompanyMember() {
		// TODO Auto-generated method stub
		return cadreMsgMapper.getCompanyMember();
	}
	
	@Override
	public List<Map<String, Object>> getCompanyOutDirector() {
		// TODO Auto-generated method stub
		return cadreMsgMapper.getCompanyOutDirector();
	}

	@Override
	public void saveCompanyMember(List<Map<String, Object>> tempList) throws Exception {
		//处理数据中心数据
		List<Map<String, Object>> companyList = companyMemberMapper.queryAllCompany();
		List<Map<String, Object>> typeList = typeMapper.queryAllMemberType();
		for(int i=0;i<tempList.size();i++) {
			Map<String, Object> param = new HashMap<String, Object>();
			String companyId ="";
			String companyName = (String) tempList.get(i).get("单位全称");
			String position = (String) tempList.get(i).get("职务");
			String startDate = (String) tempList.get(i).get("任职时间");
			String endDate = (String) tempList.get(i).get("免职时间");
			String name = (String)tempList.get(i).get("姓名");
			String memberId = (String)tempList.get(i).get("人员编码");
			//转换统一社会信用代码
			for(int j=0;j<companyList.size();j++) {
				if(companyList.get(j).get("COMPANY_NAME").equals(companyName)) {
					companyId = (String) companyList.get(j).get("COMPANY_ID");
				}
			}
			OUT:
			for (Map<String, Object> type : typeList) {
				String positions = type.get("GROUP_POSITION").toString();
				String[] pos=positions.split(",");
				for (String p : pos) {
					if (position.equals(p)) {
						param.put("GROUP_TYPE", type.get("TYPE_NAME").toString());
						break OUT;
					}
				}
			}
			//数据组装
			param.put("FID", UUID.randomUUID().toString().replaceAll("-", ""));
			param.put("MEMBER_ID", memberId);
			param.put("NAME", name);
			param.put("POSITION", position);
			param.put("REMARK", companyName);
			param.put("COMPANY_ID", companyId);
			param.put("REMARK", companyName);
			param.put("START_DATE", startDate);
			if (endDate==null || endDate.equals("null")) {
				param.put("STATUS", "1");
			}else{
				param.put("END_DATE", endDate);
				param.put("STATUS", "0");
			}
			param.put("CREATE_TIME", new Date());
			param.put("CREATE_USER", "Tiol");
			param.put("ORDER_NUMBER", i+1);
			companyMemberMapper.saveCompanyMember(param);
		}
	}

	@Override
	public List<Map<String, Object>> queryCompanyMemberBymeetingType(Map<String, Object> param) {
		if(param.get("typeId") == null || "".equals(param.get("typeId").toString())) return null;
		String meetingTypeId = param.get("typeId").toString();
		Map<String,Object> meetType = authMeetingTypeMapper.getMeetingTypeById(meetingTypeId);
		if(meetType.get("GROUP_TYPE") == null || "".equals(meetType.get("GROUP_TYPE").toString())) return null;
		param.put("groupType",meetType.get("GROUP_TYPE"));
		List<Map<String, Object>> list = companyMemberMapper.queryCompanyMemberListByGroup(param);
		return list;
	}

	@Override
	public void deleteCompanyMember(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		companyMemberMapper.deleteCompanyMember(param);
	}
}
