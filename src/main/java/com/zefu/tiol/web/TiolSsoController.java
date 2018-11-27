package com.zefu.tiol.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yr.gap.bam.service.IBizSystemConfigService;
import com.yr.gap.common.core.LoginUser;
import com.yr.gap.common.util.ClientInfoUtil;
import com.yr.gap.common.util.CookieUtil;
import com.yr.gap.constants.GapConstants;
import com.yr.gap.redis.util.CacheToolUtil;
import com.yr.gap.ums.base.STUtil;
import com.yr.gap.ums.bean.UserValidateInfo;
import com.yr.gap.ums.service.IConfigService;
import com.yr.gap.ums.service.IUserValidationService;
import com.yr.gap.ums.util.InitUmsConfigUtil;
import com.zefu.tiol.util.SSOUtil;

/**
 * 
 * @功能描述 单点登录
 * @time 2018年11月6日下午7:01:05
 * @author Super
 *
 */
@RequestMapping({ "/tiol" })
@Controller
public class TiolSsoController {
	
	private final static Logger logger = Logger.getLogger(TiolSsoController.class);
	
	@Resource(name="bizSystemConfig")
	IBizSystemConfigService bizSystemConfigService;
	
	@Resource(name="userValidationService")
	private IUserValidationService userValidationService;
	
	@Resource(name="configService")
	private IConfigService configService;
	
	/**
	 * 
	 * @功能描述 单点登录入口
	 * @time 2018年11月6日下午7:00:45
	 * @author Super
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Object login(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//获取单点登录配置信息
		List<Map<String,Object>> info = new ArrayList<Map<String,Object>>();
		//获取校验接口地址
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("key","cfg_tiol_sso_url");
		data.put("rangeId","ROOT");
		data.put("appId","global");
		info = (List<Map<String,Object>>) bizSystemConfigService.getConfigValue(data);
		String url = null;
		if (info.size() > 0 && info.get(0).get("CONFIG_VALUE") != null) {
			url = info.get(0).get("CONFIG_VALUE").toString();
		}
		
		//获取cookie名称
		data = new HashMap<String,Object>();
		data.put("key","cfg_tiol_sso_cookie_name");
		data.put("rangeId","ROOT");
		data.put("appId","global");
		info = (List<Map<String,Object>>) bizSystemConfigService.getConfigValue(data);
		String cookieName = null;
		if (info.size() > 0 && info.get(0).get("CONFIG_VALUE") != null) {
			cookieName = info.get(0).get("CONFIG_VALUE").toString();
		}
		
		//获取cookie登录用户信息
		data = new HashMap<String,Object>();
		data.put("key","cfg_tiol_sso_cookie_user");
		data.put("rangeId","ROOT");
		data.put("appId","global");
		info = (List<Map<String,Object>>) bizSystemConfigService.getConfigValue(data);
		String userKey = null;
		if (info.size() > 0 && info.get(0).get("CONFIG_VALUE") != null) {
			userKey = info.get(0).get("CONFIG_VALUE").toString();
		}
		
		data = new HashMap<String,Object>();
		data.put("key","cfg_tiol_sso_cookie_loginUrl");
		data.put("rangeId","ROOT");
		data.put("appId","global");
		info = (List<Map<String,Object>>) bizSystemConfigService.getConfigValue(data);
		String toLoginAgain = null;
		if (info.size() > 0 && info.get(0).get("CONFIG_VALUE") != null) {
			toLoginAgain = info.get(0).get("CONFIG_VALUE").toString();
		}
		
		logger.info("url:"+url+",cookieName:"+cookieName+",userKey:"+userKey+",toLoginAgain:"+toLoginAgain);
		
		//客户端cookie信息
		Cookie[] cookies = request.getCookies();
		if(cookies == null){
			logger.info("cookies为空，校验失败重定向跳转到登录页");
			if (toLoginAgain != null && !"".equals(toLoginAgain)) {
				//校验失败重定向跳转到登录页
				response.sendRedirect(toLoginAgain);
			}
			return "cookies为空，校验失败";
		}
		
		//调用校验接口获取用户信息
		String userId = null;
		if (url != null && !"".equals(url)) {
			userId = SSOUtil.getUserIdfromDomino(request, url, cookieName, userKey);
		} else {
			logger.info("校验接口为空，校验失败重定向跳转到登录页");
			if (toLoginAgain != null && !"".equals(toLoginAgain)) {
				//校验失败重定向跳转到登录页
				response.sendRedirect(toLoginAgain);
			}
			return "校验接口为空，校验失败";
		}
		
		//通过接口返回的用户ID获取用户信息
		LoginUser userInfo = null;
		if (userId != null && !"".equals(userId)) {
			userInfo = getUserInfo(request, response, userId);
		} else {
			logger.info("userId为空，校验失败重定向跳转到登录页");
			if (toLoginAgain != null && !"".equals(toLoginAgain)) {
				//校验失败重定向跳转到登录页
				response.sendRedirect(toLoginAgain);
			}
			return "userId为空，校验失败";
		}
		
		if (userInfo != null) {
			logger.debug("写入session及cookie信息");
			//写入session及cookie
			setSessionValue(request, response, userInfo);
			logger.info("校验成功重定向跳转到首页");
			//校验成功重定向跳转到首页
			response.sendRedirect("/");
		} else {
			logger.info("userInfo为空，校验失败重定向跳转到登录页");
			if (toLoginAgain != null && !"".equals(toLoginAgain)) {
				//校验失败重定向跳转到登录页
				response.sendRedirect(toLoginAgain);
			}
			return "userInfo为空，校验失败";
		}
		return null;
	}
	
	/**
	 * 
	 * @功能描述 获取用户信息
	 * @time 2018年11月6日下午6:59:59
	 * @author Super
	 * @param request
	 * @param response
	 * @param userId
	 * @return
	 * @throws Exception
	 *
	 */
	public LoginUser getUserInfo(HttpServletRequest request,
			HttpServletResponse response, String userId) throws Exception {
		UserValidateInfo validateInfo = this.userValidationService.webUserValidation(null, userId, request, false);
		LoginUser userInfo = null;
		if (validateInfo.isState()) {
			userInfo = validateInfo.getUserInfo();
		}
		return userInfo;
	}
	
	/**
	 * 
	 * @功能描述 写入session及cookie信息
	 * @time 2018年11月6日下午7:00:17
	 * @author Super
	 * @param request
	 * @param response
	 * @param userInfo
	 * @throws Exception
	 *
	 */
	public void setSessionValue(HttpServletRequest request, HttpServletResponse response, LoginUser userInfo)
		    throws Exception {
		String timeout = this.configService.getNocacheConfigValue("SESSION_TIME", "120");
		Date date = new Date();
		String clientIp = ClientInfoUtil.getClientIpAddr(request);
		String ltapToken = STUtil.creatLtapToken(userInfo.getCompanyId(), userInfo.getUserId(), clientIp, date, timeout, (byte[])InitUmsConfigUtil.getUmsConfigByKey(GapConstants.UmsConfigKey.SM2PrivateKey));

		CacheToolUtil.setObject("D2_USERMGR_SSO_LOGINUSER_" + ltapToken, userInfo);

		request.getSession().setAttribute(ltapToken, Long.valueOf((date.getTime() + Long.parseLong(timeout) * 60000L) / 1000L));

		request.getSession().setAttribute("GAP-SESSION-USERNAME", userInfo);

		CookieUtil.setCookie(response, "GAP-SESSION", ltapToken);
	}
	
}