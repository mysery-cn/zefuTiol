package com.zefu.tiol.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yr.gap.base.util.GapGlobal;
import com.yr.gap.common.core.LoginUser;
import com.yr.gap.common.plugin.Result;
import com.zefu.tiol.service.GenerateXmlService;

/**
   * @工程名 : szyd
   * @文件名 : GenerateXmlController.java
   * @工具包名：com.zefu.tiol.web
   * @功能描述: TODO 生成xml文件
   * @创建人 ：林鹏
   * @创建时间：2018年11月5日 下午3:40:21
   * @版本信息：V1.0
 */
@Controller
@RequestMapping("/Generate/Xml")
public class GenerateXmlController {
	
	
	@Resource(name = "generateXmlService")
	private GenerateXmlService xmlService;
	
	/**
	   * @功能描述: TODO 生成下发企业资料文件
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月5日 下午3:46:23
	 */
	@RequestMapping("/GenerateXmlToCompany")
	@ResponseBody
	public Object GenerateXmlToCompany(HttpServletRequest request) {
		Result result = new Result();
		try {
			xmlService.GenerateXmlToCompany();
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorCode(-1);
			result.setErrorInfo("文件生成失败");
		}
		return result;
	}
	
	/**
	   * @功能描述: TODO 解析企业XML文件
	   * @参数: @param fileName 文件名称
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月16日 下午9:24:56
	 */
	@RequestMapping("/parsingXmlToCompany")
	@ResponseBody
	public Object parsingXmlToCompany(String fileName,HttpServletRequest request) {
		//登陆信息
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(GapGlobal.SESSION.GAP_SSO_SESSION_USERNAME);
		Result result = new Result();
		try {
			xmlService.parsingXmlToCompany(loginUser,fileName);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorCode(-1);
			result.setErrorInfo("文件解析失败");
		}
		return result;
	}
	
	
	
	
}
