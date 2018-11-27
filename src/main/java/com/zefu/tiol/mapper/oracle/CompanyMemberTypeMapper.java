package com.zefu.tiol.mapper.oracle;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
/**
 * 领导班子成员类型
 * 
 * @author：陈东茂
 * @date：2018年11月24日
 */
@Repository
public interface CompanyMemberTypeMapper {
	
	/**
	 * 查询所有企业领导班子类型
	 * @return
	 */
	public List<Map<String, Object>> queryAllMemberType();
}
