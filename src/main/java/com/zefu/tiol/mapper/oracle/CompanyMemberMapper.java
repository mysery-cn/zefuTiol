package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * @功能描述 企业领导班子成员
 * @time 2018年10月26日下午3:09:20
 * @author Super
 *
 */
@Repository
public interface CompanyMemberMapper {

	/**
	 * 
	 * @功能描述 查询所有企业领导班子员信息
	 * @time 2018年10月26日下午3:10:02
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	public List<Map<String, Object>> queryCompanyMemberList(@Param("param") Map<String, Object> param);
	
	/**
	 * 查询所有企业
	 * @return
	 */
	public List<Map<String, Object>> queryAllCompany();
	/**
	 * 保存成员类型
	 * @param param
	 * @throws Exception
	 */
	void saveCompanyMember(Map<String, Object> param) throws Exception ;

	/**
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> queryCompanyMemberListByGroup(Map<String, Object> param);
	
	/**
	 * 删除所有企业领导班子
	 * @param param
	 * @throws Exception
	 */
	void deleteCompanyMember(Map<String, Object> param) throws Exception ;
	
}
