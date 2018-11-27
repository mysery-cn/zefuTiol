package com.zefu.tiol.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yr.gap.common.core.LoginUser;
import com.yr.gap.common.plugin.Result;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.common.util.StringUtils;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.service.PurviewService;

/**
 * 权限控制类
 */
@Controller
@RequestMapping("/purview")
public class PurviewController {

	@Resource(name = "purviewService")
	private PurviewService purviewService;
	
	@RequestMapping("/queryPurviewList")
	@ResponseBody
	public Object queryPurviewList(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		if(StringUtils.isBlank(request.getParameter("pageSize"))){
			List<Map<String, Object>> list = purviewService.queryPurviewList(param);
			return new Result(list);
		}else{
			Page<Map<String,Object>> page =CommonUtil.getPageFromRequest(request);
			List<Map<String, Object>> list = purviewService.queryPurviewListByPage(param,page);
			int totalCount = purviewService.queryPurviewListByPageCount(param);
			page.setData(list);
			page.setTotalCount(totalCount);
			return page;
		}
	}
	
	@RequestMapping("/queryPurviewDetail")
	@ResponseBody
	public Object queryPurviewDetail(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Map<String, Object> detail = purviewService.queryPurviewById(param);
		return new Result(detail);
	}
	
	@RequestMapping("/savePurview")
	@ResponseBody
	public Object savePurview(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		LoginUser loginUser = CommonUtil.getLoginUser(request);
		param.put("userId", loginUser.getUserId());
		purviewService.savePurview(param);	
		return new Result();
	}
	
	@RequestMapping("/updatePurview")
	@ResponseBody
	public Object updatePurview(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		LoginUser loginUser = CommonUtil.getLoginUser(request);
		param.put("userId", loginUser.getUserId());
		purviewService.updatePurview(param);	
		return new Result();
	}
	
	@RequestMapping("/deletePurview")
	@ResponseBody
	public Object deletePurview(HttpServletRequest request) throws Exception {
		Map<String,Object> parameter = CommonUtil.getParameterFromRequest(request);
		parameter.put("ids",(parameter.get("ids")+"").split(","));
		purviewService.deletePurview(parameter);	
		return new Result();
	}
	
	/**
	 * 根据会议查询权限人员
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryPurviewListByMeeting")
	@ResponseBody
	public Object queryPurviewListByMeeting(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> list = purviewService.listPurviewPerson(param);
		return new Result(list);
	}
}
