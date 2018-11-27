package com.zefu.tiol.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yr.gap.common.plugin.Result;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.common.util.StringUtils;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.service.AuthCatalogService;

@Controller
@RequestMapping("/authCatalog")
public class AuthCatalogController {

	@Resource(name = "authCatalogService")
	private AuthCatalogService authCatalogService;
	
	@RequestMapping("/queryAuthCatalogList")
	@ResponseBody
	public Object queryAuthCatalogList(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		if(StringUtils.isBlank(request.getParameter("pageSize"))){
			List<Map<String, Object>> list = authCatalogService.queryAuthCatalogList(param);
			return new Result(list);
		}else{
			Page<Map<String,Object>> page =CommonUtil.getPageFromRequest(request);
			List<Map<String, Object>> list = authCatalogService.queryAuthCatalogListByPage(param,page);
			int totalCount = authCatalogService.queryAuthCatalogListByPageCount(param);
			page.setData(list);
			page.setTotalCount(totalCount);
			return page;
		}
		
	}
	
	@RequestMapping("/saveAuthCatalog")
	@ResponseBody
	public Object saveAuthCatalog(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		authCatalogService.saveAuthCatalog(param);	
		return new Result();
	}
	
	@RequestMapping("/reloadForm")
	@ResponseBody
	public Object reloadForm(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> list = authCatalogService.reloadForm(param);	
		return new Result(list);
	}
}
