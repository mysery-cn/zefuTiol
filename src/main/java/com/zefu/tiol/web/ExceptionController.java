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
import com.zefu.tiol.service.ExceptionService;
import com.zefu.tiol.service.LeaderRegulationService;
import com.zefu.tiol.service.SubjectService;

@Controller
@RequestMapping("/exception")
public class ExceptionController {

	@Resource(name = "exceptionService")
	private ExceptionService exceptionService;
	
	@Resource(name = "leaderRegulationService")
	LeaderRegulationService leaderRegulationService;
	
	@Resource(name = "subjectService")
	private SubjectService subjectService;
	
	/**
	 * 委领导图表点击跳转（默认跳转至制度异常列表）
	 * @param request
	 * @return
	 */
	@RequestMapping("/exceptionJump")
	public @ResponseBody ModelAndView exceptionJump(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> regulationType = leaderRegulationService.queryList(param);
		return new ModelAndView("/statistics/exception/exception_regulation_list.jsp").addObject("regulationType", regulationType);
	}
	
	/**
	 * 跳转至企业异常议题列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/exceptionSubjectDetail")
	public @ResponseBody ModelAndView exceptionSubjectDetail(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		return new ModelAndView("/statistics/exception/exception_list_detail.jsp").addObject("companyId", param.get("companyId")).addObject("exceptionType", param.get("exceptionType"));
	}
	
	/**
	 * 获取制度类型
	 * @param request
	 * @return
	 */
	@RequestMapping("/getRegulationType")
	@ResponseBody
	public Object getRegulationType(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> regulationType = leaderRegulationService.queryList(param);
		return new Result(regulationType);
	}
	
	/**
	 * 获取异常统计图数据（5个议题异常气泡+1个制度异常气泡）
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryException")
	@ResponseBody
	public Object queryException(HttpServletRequest request) throws Exception {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        //查询数据
        Map<String, Object> detail = exceptionService.queryException(parameter);
		return new Result(detail);
	}
	/**
	 * 查询议题异常数量列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/querySubjectExceptionByPage")
	@ResponseBody
	public Object querySubjectExceptionByPage(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int totalCount =0;
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
	        list = exceptionService.querySubjectExceptionByPage(parameter, page);
	        totalCount = exceptionService.getSubjectExceptionTotalCount(parameter);
		}
        
        page.setTotalCount(totalCount);
        page.setData(list);
		return page;
	}
	
	/**
	 * 查询字典表获取异常类型
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryExceptionType")
	@ResponseBody
	public Object queryExceptionType(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> list = exceptionService.queryExceptionType(param);	
		return new Result(list);
	}
	
	/**
	 * 查询企业议题详情列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/querySubjectListByPage")
	@ResponseBody
	public Object querySubjectListByPage(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int totalCount =0;
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
	        list = exceptionService.querySubjectListByPage(parameter, page);
	        totalCount = exceptionService.querySubjectListTotalCount(parameter);
		}
        
        page.setTotalCount(totalCount);
        page.setData(list);
		return page;
	}
	
	@RequestMapping("/checkException")
	@ResponseBody
	public String collectData(HttpServletRequest request) {
		subjectService.checkSubject();
		return "数据校验完成";
	}
	
}
