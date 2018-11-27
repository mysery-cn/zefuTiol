package com.zefu.tiol.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yr.gap.common.plugin.Result;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.service.StatisticsLegislationService;

/**
 * 法规局功能模块
 * 
 * @author：陈东茂
 * @date：2018年10月30日
 */
@Controller
@RequestMapping("/statistics/legislation")
public class StatisticsLegislationController {

	@Resource(name = "statisticsLegislationService")
	StatisticsLegislationService legislationService;
	
	/**
	 * 获取法规统计图数据
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryLegislationChart")
	@ResponseBody
	public Object queryLegislationChart(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        Map<String,Object> detail = new HashMap<String,Object>();
		detail = legislationService.getLegislationChartData(parameter);
		return new Result(detail);
	}
	
	@RequestMapping("/listStatisticsYear")
	@ResponseBody
	public Object listStatisticsYear(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        List<Map<String, Object>> list = legislationService.listStatisticsYear();
		return new Result(list);
	}
	
	/**
	 * 分页查询法规统计列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/listLegislationStatistics")
	@ResponseBody
	public Page<Map<String, Object>> listLegislationStatistics(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		//获取分页信息
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        List<Map<String,Object>> detail = new ArrayList<Map<String,Object>>();
        int totalCount = 0;
        HttpSession session = request.getSession();
        Map<String,Object> permitMap = new HashMap<String, Object>();
		if (session.getAttribute("permitMap") != null && session.getAttribute("permitMap") != "") {
			permitMap = (Map<String, Object>) session.getAttribute("permitMap");
		}
		List<Map<String, Object>> catalogList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> meetingTypeList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> companyList = new ArrayList<Map<String, Object>>();
		if (permitMap != null && permitMap.size() > 0) {
			if (permitMap.get("catalogList") != null && permitMap.get("catalogList") != "") {
				catalogList = (List<Map<String, Object>>) permitMap.get("catalogList");
			}
			if (permitMap.get("meetingTypeList") != null && permitMap.get("meetingTypeList") != "") {
				meetingTypeList = (List<Map<String, Object>>) permitMap.get("meetingTypeList");
			}
			if (permitMap.get("companyList") != null && permitMap.get("companyList") != "") {
				companyList = (List<Map<String, Object>>) permitMap.get("companyList");
			}
		}
		if (catalogList.size() > 0 && meetingTypeList.size() >0 && companyList.size() > 0) {
			parameter.put("catalogList", catalogList);	
			parameter.put("meetingTypeList", meetingTypeList);
			parameter.put("companyList", companyList);
			//查询数据
	        detail = legislationService.listLegislationStatistics(parameter,page);
	        //查询总数
	        totalCount = legislationService.countLegislationStatistics(parameter);
		}
        
        page.setTotalCount(totalCount);
        page.setData(detail);
		return page;
	}
	
	/**
	 * 跳转法规指标列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/legislationOption")
	@ResponseBody
	public ModelAndView legislationOption(HttpServletRequest request) {
		//提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		return new ModelAndView("/statistics/legislation/legislation_option_list.jsp").addObject("optionType", parameter.get("optionType"))
				.addObject("companyId", parameter.get("companyId")).addObject("companyName", parameter.get("companyName"));
	}
	
	/**
	 * 查询法规指标有关数据列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/listLegislationOption")
	@ResponseBody
	public Page<Map<String, Object>> listLegislationOption(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		//获取分页信息
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        List<Map<String,Object>> detail = new ArrayList<Map<String,Object>>();
        int totalCount = 0;
        HttpSession session = request.getSession();
        Map<String,Object> permitMap = new HashMap<String, Object>();
		if (session.getAttribute("permitMap") != null && session.getAttribute("permitMap") != "") {
			permitMap = (Map<String, Object>) session.getAttribute("permitMap");
		}
		List<Map<String, Object>> catalogList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> meetingTypeList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> companyList = new ArrayList<Map<String, Object>>();
		if (permitMap != null && permitMap.size() > 0) {
			if (permitMap.get("catalogList") != null && permitMap.get("catalogList") != "") {
				catalogList = (List<Map<String, Object>>) permitMap.get("catalogList");
			}
			if (permitMap.get("meetingTypeList") != null && permitMap.get("meetingTypeList") != "") {
				meetingTypeList = (List<Map<String, Object>>) permitMap.get("meetingTypeList");
			}
			if (permitMap.get("companyList") != null && permitMap.get("companyList") != "") {
				companyList = (List<Map<String, Object>>) permitMap.get("companyList");
			}
		}
		if (catalogList.size() > 0 && meetingTypeList.size() >0 && companyList.size() > 0) {
			parameter.put("catalogList", catalogList);	
			parameter.put("meetingTypeList", meetingTypeList);
			parameter.put("companyList", companyList);
			//查询数据
	        detail = legislationService.listLegislationOption(parameter,page);
	        //查询总数
	        totalCount = legislationService.countLegislationOption(parameter);
		}
        
        page.setTotalCount(totalCount);
        page.setData(detail);
		return page;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getLegislationOptionCount")
	@ResponseBody
	public Object getLegislationOptionCount(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<String, Object>();
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		//所有会议数
		int allCount = legislationService.countLegislationOption(param);
		//有查看权限会议数
		int totalCount = 0;
		 HttpSession session = request.getSession();
		Map<String,Object> permitMap = new HashMap<String, Object>();
		if (session.getAttribute("permitMap") != null && session.getAttribute("permitMap") != "") {
			permitMap = (Map<String, Object>) session.getAttribute("permitMap");
		}
		List<Map<String, Object>> catalogList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> meetingTypeList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> companyList = new ArrayList<Map<String, Object>>();
		if (permitMap != null && permitMap.size() > 0) {
			if (permitMap.get("catalogList") != null && permitMap.get("catalogList") != "") {
				catalogList = (List<Map<String, Object>>) permitMap.get("catalogList");
			}
			if (permitMap.get("meetingTypeList") != null && permitMap.get("meetingTypeList") != "") {
				meetingTypeList = (List<Map<String, Object>>) permitMap.get("meetingTypeList");
			}
			if (permitMap.get("companyList") != null && permitMap.get("companyList") != "") {
				companyList = (List<Map<String, Object>>) permitMap.get("companyList");
			}
		}
		if (catalogList.size() > 0 && meetingTypeList.size() >0 && companyList.size() > 0) {
			param.put("catalogList", catalogList);	
			param.put("meetingTypeList", meetingTypeList);
			param.put("companyList", companyList);
        	totalCount = legislationService.countLegislationOption(param);
		}
		result.put("all", allCount);
		result.put("have", totalCount);
		result.put("not", allCount-totalCount);
		return new Result(result);
	}
}
