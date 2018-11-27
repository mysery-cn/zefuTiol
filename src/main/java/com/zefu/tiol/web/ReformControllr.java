package com.zefu.tiol.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yr.gap.common.plugin.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.service.ReformService;

/**
   * @工程名 : szyd
   * @文件名 : ReformControllr.java
   * @工具包名：com.zefu.tiol.web
   * @功能描述: TODO 改革局功能模块
   * @创建人 ：林鹏
   * @创建时间：2018年10月30日 下午2:29:09
   * @版本信息：V1.0
 */

@Controller
@RequestMapping("/model/reform")
public class ReformControllr {
	
	@Resource(name = "reformService")
	private ReformService reformService;
	
	
	/**
	   * @功能描述: TODO 获取议题列表By事项类型
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月30日 下午2:35:46
	 */
	@RequestMapping("/querySubjectPageList")
	@ResponseBody
	public Object querySubjectPageList(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
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
        int totalCount = 0;
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if (catalogList.size() > 0 && meetingTypeList.size() >0 && companyList.size() > 0) {
			parameter.put("catalogList", catalogList);
			parameter.put("meetingTypeList", meetingTypeList);
			parameter.put("companyList", companyList);
            list = reformService.querySubjectPageList(parameter, page);
            totalCount = reformService.querySubjectTotalCount(parameter);
		}
        page.setTotalCount(totalCount);
        page.setData(list);
		return page;
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
	@RequestMapping("/getMeetingSubjectAutoCount")
	@ResponseBody
	public Object getMeetingSubjectAutoCount(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<String, Object>();
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		//所有议题数
		int allCount = reformService.getMeetingSubjectTotalCount(param);

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
			totalCount = reformService.querySubjectTotalCount(param);
		}
		result.put("all", allCount);
		result.put("have", totalCount);
		result.put("not", allCount-totalCount);
		return new Result(result);
	}
}
