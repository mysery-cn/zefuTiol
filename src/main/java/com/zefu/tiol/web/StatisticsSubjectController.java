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
import com.zefu.tiol.service.StatisticsSubjectService;

/**
 * 
 * @功能描述 决策议题统计
 * @time 2018年10月24日下午3:12:19
 * @author Super
 *
 */
@Controller
@RequestMapping("/statistics/subject")
public class StatisticsSubjectController {

	@Resource(name = "statisticsSubjectService")
	private StatisticsSubjectService statisticsSubjectService;
	
	/**
	 * 
	 * @功能描述 查询决策议题统计信息
	 * @time 2018年10月24日下午3:12:25
	 * @author Super
	 * @param request
	 * @return
	 * @throws Exception
	 *
	 */
	@RequestMapping("/querySubjectList")
	@ResponseBody
	public Object querySubjectList(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> list = statisticsSubjectService.querySubjectList(param);
		return new Result(list);
	}
	
	/**
	 * 
	 * @功能描述 分页查询决策议题统计信息
	 * @time 2018年10月24日下午3:12:31
	 * @author Super
	 * @param request
	 * @return
	 * @throws Exception
	 *
	 */
	@RequestMapping("/queryMeetingSubjectListByPage")
	@ResponseBody
	public Object querySubjectListByPage(HttpServletRequest request) throws Exception{
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
			list = statisticsSubjectService.queryMeetingSubjectListByPage(param, page);
			totalCount = statisticsSubjectService.getMeetingSubjectTotalCount(param);
	    }
		page.setData(list);
		page.setTotalCount(totalCount);
		return page;
	}
	
	/**
	   * @功能描述: TODO 根据会议类型查询议题
	   * @参数: @param request
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月29日 下午2:06:01
	 */
	@RequestMapping("/queryMeetingTypeSubjectListByPage")
	@ResponseBody
	public Object queryMeetingTypeSubjectListByPage(HttpServletRequest request) throws Exception{
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
			list = statisticsSubjectService.queryMeetingTypeSubjectListByPage(parameter, page);
			totalCount = statisticsSubjectService.queryTotalCount(parameter);
		}
        page.setTotalCount(totalCount);
        page.setData(list);
		return page;
	}
	
	/**
	 * 
	 * @功能描述 查询本年度事项清单议题情况统计
	 * @time 2018年10月31日下午10:58:18
	 * @author Super
	 * @param request
	 * @return
	 * @throws Exception
	 *
	 */
	@RequestMapping("/queryItemSubjectDetail")
	@ResponseBody
	public Object queryItemSubjectDetail(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Map<String, Object> detail = statisticsSubjectService.queryItemSubjectDetail(param);
		return new Result(detail);
	}

	/**
	   * @功能描述:  查询决策议题数据权限查看数量
	   * @创建人 ：林鹏
	   * @创建时间：2018/11/17 19:45
	*/
	@RequestMapping("/querySubjectRoleNumber")
	@ResponseBody
	public Object querySubjectRoleNumber(HttpServletRequest request) throws Exception{
		Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		Map<String, Object> detail = new HashMap<>();
		//查询所有总数
		int total = statisticsSubjectService.querySubjectRoleNumber(parameter);
		detail.put("total",total);
		//根据权限查询符合的数量
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
			totalCount = statisticsSubjectService.queryTotalCount(parameter);
		}
		detail.put("number",totalCount);
		return new Result(detail);
	}

	/**
	 * 查询决策议题折线图数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getStatisticsSubjectChartData")
	@ResponseBody
	public Object getStatisticsSubjectChartData(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        Map<String, Object> detail = new HashMap<String,Object>();
		detail = statisticsSubjectService.getStatisticsSubjectChartData(param);
		return new Result(detail);
	}
	
	/**
	 * 查询决策议题统计饼图数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSubjectChartData")
	@ResponseBody
	public Object getSubjectChartData(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        Map<String, Object> detail = new HashMap<String,Object>();
		detail = statisticsSubjectService.getSubjectChartData(param);
		return new Result(detail);
	}
	
	/**
	 * 查询决策议题统计列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/querySubjectClassList")
	@ResponseBody
	public Object querySubjectClassList(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
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
        	param.put("catalogList", catalogList);	
        	param.put("meetingTypeList", meetingTypeList);
        	param.put("companyList", companyList);
			list = statisticsSubjectService.listSubjectClass(param,page);
			totalCount = statisticsSubjectService.countSubjectClass(param);
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
		int allCount = statisticsSubjectService.getMeetingSubjectTotalCount(param);

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
        	totalCount = statisticsSubjectService.getMeetingSubjectTotalCount(param);
		}
		result.put("all", allCount);
		result.put("have", totalCount);
		result.put("not", allCount-totalCount);
		return new Result(result);
	}
	
}
