package com.zefu.tiol.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.service.LeaderRegulationService;
import com.zefu.tiol.service.StatisticsRegulationService;

@Controller
@RequestMapping("/leader/regulation")
public class LeaderRegulationController {

	@Resource(name = "leaderRegulationService")
	LeaderRegulationService leaderRegulationService;
	
	@Resource(name = "statisticsRegulationService")
	StatisticsRegulationService statisticsRegulationService;
	
	/**
	 * 展示企业制度列表
	 * @param industryId
	 * @param request
	 * @return
	 */
	@RequestMapping("/regulationDetail")
	public @ResponseBody ModelAndView regulationDetail(String industryId, HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> regulationType = leaderRegulationService.queryList(param);
		return new ModelAndView("/statistics/leader/leader_regulation.jsp").addObject("regulationType", regulationType);
	}
	
	/**
	 * 跳转至企业制度详情列表页
	 * @param request
	 * @return
	 */
	@RequestMapping("/leaderRegulationDetail")
	public @ResponseBody ModelAndView leaderRegulationDetail(HttpServletRequest request) {
		Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		return new ModelAndView("/statistics/leader/leader_regulation_company.jsp").addObject("companyId",parameter.get("companyId"));
	}
	
	/**
	 * 跳转至制度详情列表页
	 * @param request
	 * @return
	 */
	@RequestMapping("/leaderRegulationComDetail")
	public @ResponseBody ModelAndView leaderRegulationComDetail(HttpServletRequest request) {
		Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		return new ModelAndView("/statistics/leader/leader_regulation_detail.jsp").addObject("regulationId",  parameter.get("regulationId"));
	}
	
	/**
	 * 跳转至企业制度类型列表页
	 * @param request
	 * @return
	 */
	@RequestMapping("/goRegulationTypeList")
	public @ResponseBody ModelAndView goRegulationTypeList(HttpServletRequest request) {
		Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		return new ModelAndView("/statistics/leader/leader_regulation_company_list.jsp").addObject("typeCode",  parameter.get("typeCode"));
	}
	
	
	@RequestMapping("/queryRegulationByPage")
	@ResponseBody
	public Object queryRegulationByPage(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        //查询数据
        List<Map<String, Object>> list = leaderRegulationService.queryRegulationByPage(parameter, page);
        int totalCount = leaderRegulationService.getRegulationTotalCount(parameter);
        page.setTotalCount(totalCount);
        page.setData(list);
		return page;
	}
	
	@RequestMapping("/queryRegulationList")
	@ResponseBody
	public Page<Map<String, Object>> queryRegulationList(HttpServletRequest request) throws Exception {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		//获取分页信息
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        List<Map<String, Object>> list = leaderRegulationService.queryRegulationListByPage(parameter, page);
        int totalCount = leaderRegulationService.getRegulationListTotalCount(parameter);
        page.setTotalCount(totalCount);
        page.setData(list);
        //封装的返回方法
        return page;
	}
	
	/**
	 * 企业制度类型列表展示
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryRegulationByType")
	@ResponseBody
	public Page<Map<String, Object>> queryRegulationByType(HttpServletRequest request) throws Exception {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		//获取分页信息
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        List<Map<String, Object>> list = leaderRegulationService.queryRegulationByType(parameter, page);
        int totalCount = leaderRegulationService.queryRegulationByTypeCount(parameter);
        page.setTotalCount(totalCount);
        page.setData(list);
        //封装的返回方法
        return page;
	}
}
