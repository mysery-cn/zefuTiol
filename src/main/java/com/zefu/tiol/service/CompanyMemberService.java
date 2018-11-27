package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @功能描述 企业领导班子成员
 * @time 2018年10月26日下午3:22:28
 * @author Super
 *
 */
public interface CompanyMemberService {
	
	/**
	 * 
	 * @功能描述 查询企业领导班子成员
	 * @time 2018年10月26日下午3:23:34
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	List<Map<String, Object>> queryCompanyMemberList(Map<String, Object> param);
	
	/**
	 * 查询干部系统领导班子成员
	 * @return
	 */
	List<Map<String, Object>> getCompanyMember();
	
	/**
	 * 查询干部系统外部董事
	 * @return
	 */
	List<Map<String, Object>> getCompanyOutDirector();
	
	/**
	 * 新增企业领导班子成员
	 * @param param
	 * @throws Exception
	 */
	void saveCompanyMember(List<Map<String, Object>> tempList) throws Exception ;

	/**
	 * 根据会议类型查询关联的领导班子人员
	 * @param param
	 * @return 李缝兴
	 */
	List<Map<String, Object>> queryCompanyMemberBymeetingType(Map<String, Object> param);
	
	/**
	 * 删除企业领导班子成员
	 * @param param
	 * @throws Exception
	 */
	void deleteCompanyMember(Map<String, Object> param) throws Exception ;

}
