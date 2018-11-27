package com.zefu.tiol.mapper.reduct;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 压减系统公司信息数据操作类
 * 
 * @author：陈东茂
 * @date：2018年11月20日
 */
@Repository
public interface FCeInfoMapper {

	/**
	 * 查询压减系统所有数据
	 * @return
	 */
	List<Map<String,Object>> listAllMsg();
}
