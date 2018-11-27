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
import com.zefu.tiol.pojo.StatisticsDecision;
import com.zefu.tiol.pojo.StatisticsDecisionVo;
import com.zefu.tiol.service.StatisticsDecisionService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @功能描述: TODO 重大决策统计
 * @创建人 ：林长怀
 * @创建时间：2018年10月25日 上午8:53:01 @版本信息：V1.0
 */
@Controller
@RequestMapping("/statistics/decision")
public class StatisticsDecisionController {

	@Resource(name = "statisticsDecisionService")
	StatisticsDecisionService statisticsDecisionService;

	/**
	 * 贯彻执行党和国家决策部署的重大措施汇总
	 * 
	 * @param industryId
	 *            行业Id
	 * @return Result
	 */
	@RequestMapping("/queryList")
	@ResponseBody
	public Object queryList(String industryId, HttpServletRequest request) {
		// 1.汇总上级重要决定习总书记的批示和指示
		List<StatisticsDecisionVo> decisionlistBySource = statisticsDecisionService.queryListBySource(industryId);
		// 2.根据事项目录汇总
		List<StatisticsDecisionVo> decisionlist = statisticsDecisionService.queryListByItem(industryId);
		// 3.合并结果
		decisionlistBySource.addAll(decisionlist);

		// 4.定义返回内容
		JSONObject resultJson = new JSONObject();
		JSONArray idArray = new JSONArray();
		JSONArray typeArray = new JSONArray();
		JSONArray nameArray = new JSONArray();
		JSONArray meetingArray = new JSONArray();
		JSONArray subjectArray = new JSONArray();

		// 5.遍历查询结果，返回三个数组，方便展示
		for (StatisticsDecision statistics : decisionlistBySource) {
			idArray.add(statistics.getObjectId());
			nameArray.add(statistics.getStatisticsName());
			typeArray.add(statistics.getStatisticsType());
			meetingArray.add(statistics.getMeetingQuantity());
			subjectArray.add(statistics.getSubjectQuantity());
		}
		resultJson.put("idArray", idArray);
		resultJson.put("typeArray", typeArray);
		resultJson.put("nameArray", nameArray);
		resultJson.put("meetingArray", meetingArray);
		resultJson.put("subjectArray", subjectArray);
		return new Result(resultJson);
	}
	
	
	/**
	 * 查询企业决策议题列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryCompanyDecisionList")
	@ResponseBody
	public Page<Map<String, Object>> queryCompanyDecisionList(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		//获取分页信息
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        new ArrayList<Map<String,Object>>();
        int totalCount = 0;
        List<Map<String,Object>> detail = statisticsDecisionService.listCompanyDecision(parameter,page);
        totalCount = statisticsDecisionService.countCompanyDecision(parameter);
		page.setTotalCount(totalCount);
        page.setData(detail);
		return page;
	}
	
	/**
	 * 查询企业决策会议列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryDecisionMeetingList")
	@ResponseBody
	public Page<Map<String, Object>> queryDecisionMeetingList(HttpServletRequest request) {
	    //提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		//获取分页信息
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
			list = statisticsDecisionService.listDecisionMeeting(parameter,page);
			totalCount = statisticsDecisionService.countDecisionMeeting(parameter);
		}
	  	page.setTotalCount(totalCount);
	    page.setData(list);
	  	return page;
	}
	
	/**
	 * 统计会议可查看数量
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDecisionMeetingCount")
	@ResponseBody
	public Object getDecisionMeetingCount(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<String, Object>();
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		//所有会议数
		int allCount = statisticsDecisionService.countDecisionMeeting(param);
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
        	totalCount = statisticsDecisionService.countDecisionMeeting(param);
		}
		result.put("all", allCount);
		result.put("have", totalCount);
		result.put("not", allCount-totalCount);
		return new Result(result);
	}
	
	/**
	 * 查询措施议题列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryDecisionSubjectList")
	@ResponseBody
	public Page<Map<String, Object>> queryDecisionSubjectList(HttpServletRequest request) {
	    //提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		//获取分页信息
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
			list = statisticsDecisionService.listDecisionSubject(parameter,page);
			totalCount = statisticsDecisionService.countDecisionSubject(parameter);
		}
	  	page.setTotalCount(totalCount);
	    page.setData(list);
	  	return page;
	}
	/**
	 * 统计会议可查看数量
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDecisionSubjectCount")
	@ResponseBody
	public Object getDecisionSubjectCount(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<String, Object>();
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		//所有会议数
		int allCount = statisticsDecisionService.countDecisionSubject(param);
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
        	totalCount = statisticsDecisionService.countDecisionSubject(param);
		}
		result.put("all", allCount);
		result.put("have", totalCount);
		result.put("not", allCount-totalCount);
		return new Result(result);
	}
	
	/**
	 * 查询重大决策企业统计列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryDecisionCompanyList")
	@ResponseBody
	public Page<Map<String, Object>> queryDecisionCompanyList(HttpServletRequest request) {
	    //提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		//获取分页信息
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        List<Map<String, Object>> detail = new ArrayList<Map<String, Object>>();
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
			detail = statisticsDecisionService.listDecisionCompany(parameter,page);
			totalCount = statisticsDecisionService.countDecisionCompany(parameter);
		}
	  	page.setTotalCount(totalCount);
	    page.setData(detail);
	  	return page;
	}
}
