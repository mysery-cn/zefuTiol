package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * 企业信息数据操作类
 * 
 * @author：陈东茂
 * @date：2018年11月20日
 */
@Repository
public interface CompanyMapper {

	/**
	 * 更新公司信息
	 * @param list
	 */
	int updateCompanyMsg(Map<String,Object> param);
	
	/**
	 * 查询所有信息
	 * @param company
	 * @return
	 */
	List<Map<String,Object>> listAllMsg();
	
	/**
	 * 根据公司主键查询公司信息
	 * @param param
	 * @return
	 */
	Map<String,Object> getCompanyDataById(Map<String,Object> param);
	
	/**
	 * 新增企业数据
	 * @param param
	 * @return
	 */
	int insertCompanyData(Map<String,Object> param);
	
}
