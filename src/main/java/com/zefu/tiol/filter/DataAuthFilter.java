package com.zefu.tiol.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.yr.gap.common.core.LoginUser;
import com.yr.gap.common.util.CommonUtil;
import com.zefu.tiol.service.PurviewService;

@Component
public class DataAuthFilter extends OncePerRequestFilter{
	
	@Autowired
	private PurviewService purviewService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//获取到请求URL的当前用户
		LoginUser loginUser = CommonUtil.getLoginUser(request);
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		if(loginUser == null) {
			 filterChain.doFilter(request, response);
			 return;
		}
		HttpSession session = request.getSession();
		Map<String, Object> dataAuth = (Map<String, Object>) session.getAttribute("permitMap");
		if(dataAuth==null) {
			param.put("userId", loginUser.getUserId());
			param.put("roleIds", loginUser.getRoleList());
			param.put("orgId", loginUser.getOrgId());
			try {
				session.setAttribute("permitMap", purviewService.getPermit(param));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		filterChain.doFilter(request, response);
		return;
	}

}
