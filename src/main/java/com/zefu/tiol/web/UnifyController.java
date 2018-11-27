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

import com.yr.gap.common.plugin.Result;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.service.UnifyService;

/**
 * 
 * @功能描述 会议信息及议题信息统一查询接口
 * @time 2018年11月4日上午10:42:17
 * @author Super
 *
 */
@Controller
@RequestMapping("/tiol/unify")
public class UnifyController {
	
	/**
	 * 此备注为ServiceImpl中配置的参数，如不要请用@Autowired
	 */
	@Resource(name = "unifyService")
	UnifyService unifyService;
	
	/**
	 * 
	 * @功能描述 分页查询会议信息列表
	 * @time 2018年11月4日上午10:41:38
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/queryMeetingList")
	@ResponseBody
	public Object queryMeetingList(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
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
			list = unifyService.queryMeetingList(param);
		}
		return new Result(list);
	}
	
	/**
	 * 
	 * @功能描述 分页查询会议信息
	 * @time 2018年11月4日上午10:41:29
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/queryMeetingByPage")
	@ResponseBody
	public Object queryMeetingByPage(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        HttpSession session = request.getSession();
		int totalCount = 0;
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
			list = unifyService.queryMeetingByPage(param, page);
			totalCount = unifyService.getyMeetingTotalCount(param);
		}
        page.setTotalCount(totalCount);
        page.setData(list);
		return page;
	}
	
	/**
	 * 
	 * @功能描述 查询议题信息列表
	 * @time 2018年11月4日上午10:41:02
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/querySubjectList")
	@ResponseBody
	public Object querySubjectList(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
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
			list = unifyService.querySubjectList(param);
		}
		return new Result(list);
	}
	
	/**
	 * 
	 * @功能描述 分页查询议题信息
	 * @time 2018年11月4日上午10:40:51
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/querySubjectByPage")
	@ResponseBody
	public Object querySubjectByPage(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        HttpSession session = request.getSession();
        int totalCount = 0;
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
			list = unifyService.querySubjectByPage(param, page);
			totalCount = unifyService.getSubjectTotalCount(param);
		}
        page.setTotalCount(totalCount);
        page.setData(list);
		return page;
	}
	
	/**
	 * 
	 * @功能描述 获取会议有无查看权限数量
	 * @time 2018年11月16日上午10:45:03
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/getMeetingAutoCount")
	@ResponseBody
	public Object getMeetingAutoCount(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<String, Object>();
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		//所有会议数
		int allCount = unifyService.getyMeetingTotalCount(param);
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
        	totalCount = unifyService.getyMeetingTotalCount(param);
		}
		result.put("all", allCount);
		result.put("have", totalCount);
		result.put("not", allCount-totalCount);
		return new Result(result);
	}
	
	/**
	 * 
	 * @功能描述 获取议题有无查看权限数量
	 * @time 2018年11月16日下午12:26:42
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/getSubjectAutoCount")
	@ResponseBody
	public Object getSubjectAutoCount(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<String, Object>();
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		//所有议题数
		int allCount = unifyService.getSubjectTotalCount(param);

		//有查看权限议题数
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
        	totalCount = unifyService.getSubjectTotalCount(param);
		}
		result.put("all", allCount);
		result.put("have", totalCount);
		result.put("not", allCount-totalCount);
		return new Result(result);
	}
	
}
