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
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.service.AuthMeetingTypeService;

@Controller
@RequestMapping("/authMeetingType")
public class AuthMeetingTypeController {

	@Resource(name = "authMeetingTypeService")
	private AuthMeetingTypeService authMeetingTypeService;
	
	@RequestMapping("/queryUmsMeetingTypeList")
	@ResponseBody
	public Object queryUmsMeetingTypeList(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> list = authMeetingTypeService.queryAuthMeetingTypeTree(param);	
		return new Result(list);
	}
	
	@RequestMapping("/queryAuthMeetingTypeList")
	@ResponseBody
	public Object queryAuthMeetingTypeList(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Page<Map<String,Object>> page =CommonUtil.getPageFromRequest(request);
		List<Map<String, Object>> list = authMeetingTypeService.queryAuthMeetingTypeListByPage(param,page);
		int totalCount = authMeetingTypeService.queryAuthMeetingTypeListByPageCount(param);
		page.setData(list);
		page.setTotalCount(totalCount);
		return page;
	}
	
	@RequestMapping("/saveAuthMeetingType")
	@ResponseBody
	public Object saveAuthMeetingType(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		authMeetingTypeService.saveAuthMeetingType(param);	
		return new Result();
	}
	
	@RequestMapping("/reloadForm")
	@ResponseBody
	public Object reloadForm(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> list = authMeetingTypeService.reloadForm(param);	
		return new Result(list);
	}
}
