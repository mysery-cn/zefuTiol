package com.zefu.tiol.service;

import com.yr.gap.common.core.LoginUser;

/**
   * @工程名 : szyd
   * @文件名 : GenerateXmlService.java
   * @工具包名：com.zefu.tiol.service
   * @功能描述: TODO 生成XML文件Service
   * @创建人 ：林鹏
   * @创建时间：2018年11月5日 下午3:41:26
   * @版本信息：V1.0
 */
public interface GenerateXmlService {
	
	/**
	   * @return 
	   * @功能描述: TODO 生成企业下发材料XML
	   * @参数: 
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月5日 下午3:48:20
	 */
	void GenerateXmlToCompany() throws Exception ;
	
	/**
	   * @param loginUser 
	   * @param fileName 
	   * @功能描述: TODO 解析企业材料
	   * @参数: 
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月16日 下午2:01:56
	 */
	void parsingXmlToCompany(LoginUser loginUser, String fileName) throws Exception;

	
	
	
	
	
	
}
