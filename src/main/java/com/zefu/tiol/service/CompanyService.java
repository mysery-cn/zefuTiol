package com.zefu.tiol.service;

import java.util.List;
import java.util.Map;

public interface CompanyService {

	/**
	 * 从压减系统同步企业所有信息数据
	 * @return
	 */
	Map<String, Object> synchronizeMsgByReduct();
	
	/**
	 * 查询压减系统与当前系统信息不同的企业数据
	 * @return
	 */
	List<Map<String, Object>> queryDiffrentMsg();
}
