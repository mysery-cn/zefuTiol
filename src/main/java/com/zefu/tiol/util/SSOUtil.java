package com.zefu.tiol.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

public class SSOUtil {

	private final static Logger logger = Logger.getLogger(SSOUtil.class);

	// 如果成功，从Domino服务器返回信息中取用户ID，否则返回false
	public static String getUserIdfromDomino(HttpServletRequest request,
			String url, String cookieName, String key) throws Exception {
		Vector<Object> content = getResponseFromDomino(request, url, cookieName);

		if (content == null) {
			return null;
		}
		
		String string = (String) content.elementAt(0);
		if (string.length() != 0) {
			// 返回格式：userid=zhouxiao
			String[] user = string.split("=");
			if (user.length == 2) {
				// 初始化登录用户信息类（不含登录口令），并返回用户ID
				if (user[0].equalsIgnoreCase(key)) {
					return user[1];
				}
			}
		}
		return null;
	}

	// 读取本地Cookie信息，通过Domino进行用户认证
	private static Vector<Object> getResponseFromDomino(
			HttpServletRequest request, String url, String cookieName)
			throws Exception {

		// 取Domino保存在客户端本地的Cookie
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		// 初始化HTTP请求
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		Vector<Object> content = new Vector<Object>();
		// 把Domino的Cookie信息放到HTTP请求中
		for (int i = 0; i < cookies.length; i++) {
			String dominoCookieName = cookies[i].getName();
			if (cookieName.equalsIgnoreCase(dominoCookieName)) {
				String dominoCookieValue = cookies[i].getValue();
				String sCookie = dominoCookieName + "=" + dominoCookieValue;
				method.setRequestHeader("Cookie", sCookie);
				break;
			}
		}
		try {
			// 将Cookie通过HTTP发送到Domino服务器，请求进行用户验证
			Date begin = new Date();
			logger.debug("通过HTTP发送到Domino服务器，请求进行用户验证!开始");
			client.executeMethod(method);
			InputStream is = method.getResponseBodyAsStream();
			readData(is, content);
			Date end = new Date();
			logger.debug("通过HTTP发送到Domino服务器，请求进行用户验证!完成使用时间为：" + (end.getTime() - begin.getTime()) + "ms");
			method.releaseConnection();
		} catch (Exception e) {
			throw new Exception("Error when communicating with Domino server.");
		}
		return content;
	}

	// 读取HTTP返回信息
	private static void readData(InputStream ins, Vector<Object> content)
			throws IOException {
		String linestr = null;

		BufferedReader br = new BufferedReader(new InputStreamReader(ins));
		while (true) {
			try {
				linestr = br.readLine();
			} catch (Exception e) {
				break;
			}
			if (linestr != null) {
				linestr = linestr.trim();
				if (linestr.length() > 0) {
					content.addElement(linestr);
				}
			} else {
				break;
			}
		}
	}

	// 获取登录Domino服务器的cookie
	public static String getCookieValue(HttpServletRequest request,
			String cookieName) {
		Cookie userCookie = null;

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				userCookie = cookies[i];
				if (cookieName.equals(userCookie.getName())) {
					return userCookie.getValue();
				}
			}
		}
		return null;
	}

}