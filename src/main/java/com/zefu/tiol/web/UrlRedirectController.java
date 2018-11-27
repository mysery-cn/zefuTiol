package com.zefu.tiol.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yr.gap.common.util.CommonUtil;
import com.zefu.tiol.pojo.StatisticsDecisionVo;
import com.zefu.tiol.service.CatalogService;
import com.zefu.tiol.service.LeaderRegulationService;
import com.zefu.tiol.service.MeetingService;
import com.zefu.tiol.service.MeetingTypeService;
import com.zefu.tiol.service.StatisticsDecisionService;

/**
   * @工程名 : szyd
   * @文件名 : UrlRedirectController.java
   * @工具包名：com.zefu.tiol.web
   * @功能描述: TODO 工程路径跳转统一入口
   * @创建人 ：林鹏
   * @创建时间：2018年10月30日 上午9:18:03
   * @版本信息：V1.0
 */

@Controller
@RequestMapping("/tiol/url")
public class UrlRedirectController {
	
	@Resource(name = "meetingService")
	private MeetingService meetingService;
	
	@Resource(name = "statisticsDecisionService")
	private StatisticsDecisionService statisticsDecisionService;
	
	@Resource(name = "meetingTypeService")
	private MeetingTypeService meetingTypeService;
	
	@Resource(name = "leaderRegulationService")
	LeaderRegulationService leaderRegulationService;
	
	@Resource(name = "catalogService")
	CatalogService catalogService;
	
	
	/**
	   * @功能描述: TODO 首页跳转
	   * @参数: @param companyId
	   * @参数: @param request
	   * @参数: @param response
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月31日 上午9:44:28
	 */
	@RequestMapping("/goLeaderList")
	public @ResponseBody ModelAndView goLeaderList(String companyId, HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/statistics/leader/leader_list.jsp");
	}
	
	
	/**
	   * @功能描述: TODO 跳转事项任务清单页面
	   * @参数: @param meetingId
	   * @参数: @param companyId
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月30日 上午9:52:55
	 */
	@RequestMapping("/goLeaderItem")
	public @ResponseBody ModelAndView goLeaderItem(String instFlag, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		return new ModelAndView("/statistics/leader/leader_item.jsp").addObject("instFlag", param.get("instFlag"));
	}
	
	/**
	   * @功能描述: TODO 跳转改革局视图统计3
	   * @参数: @param instFlag
	   * @参数: @param request
	   * @参数: @param response
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月30日 下午7:28:42
	 */
	@RequestMapping("/goReformSubject")
	public @ResponseBody ModelAndView goReformSubject(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		return new ModelAndView("/statistics/reform/reform_subject.jsp")
				.addObject("companyID",  param.get("companyID"))
				.addObject("companyName",  param.get("companyName"))
				.addObject("typeID",  param.get("typeID"));
	}
	
	/**
	   * @功能描述: TODO 跳转不合规页面
	   * @参数: @param instFlag
	   * @参数: @param request
	   * @参数: @param response
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月30日 下午7:31:02
	 */
	@RequestMapping("/goTourSubjectNon")
	public @ResponseBody ModelAndView goTourSubjectNon(String companyId,String search, HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/statistics/tour/tour_subject_non.jsp")
				.addObject("companyId", companyId)
				.addObject("search", search);
	}
	
	/**
	   * @功能描述: TODO 跳转事项清单
	   * @参数: @param companyId
	   * @参数: @param request
	   * @参数: @param response
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月30日 下午7:32:50
	 */
	@RequestMapping("/goTourItem")
	public @ResponseBody ModelAndView goTourItem(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/statistics/tour/tour_item.jsp");
	}
	
	/**
	   * @功能描述: TODO 跳转制度分页页面
	   * @参数: @param industryId
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月30日 下午7:35:50
	 */
	@RequestMapping("/goTourRegulation")
	public @ResponseBody ModelAndView goTourRegulation(String industryId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> regulationType = leaderRegulationService.queryList(param);
		return new ModelAndView("/statistics/tour/tour_regulation.jsp")
				.addObject("regulationType", regulationType)
				.addObject("industryId", industryId);
	}
	
	/**
	   * @功能描述: TODO 跳转制度详情
	   * @参数: @param companyId
	   * @参数: @param request
	   * @参数: @param response
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月30日 下午7:38:57
	 */
	@RequestMapping("/goRegulationDetail")
	public @ResponseBody ModelAndView goRegulationDetail(String companyId, HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/statistics/tour/tour_regulation_company.jsp")
				.addObject("companyId", companyId);
	}
	
	/**
	   * @功能描述: TODO 跳转企业事项详情页面
	   * @参数: @param companyId
	   * @参数: @param request
	   * @参数: @param response
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月31日 上午9:21:20
	 */
	@RequestMapping("/goLeaderItemDetail")
	public @ResponseBody ModelAndView goLeaderItemDetail(String companyId,String catalogType, HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("/statistics/leader/leader_item_detail.jsp")
				.addObject("catalogType", catalogType)
				.addObject("companyId", companyId);
	}

	/**
	   * @功能描述: TODO
	   * @参数: @param type
	   * @参数: @param companyId
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月31日 下午4:44:08
	 */
	@RequestMapping("/goCompanyDetail")
	public @ResponseBody ModelAndView goCompanyDetail(String companyId, HttpServletRequest request) {
		return new ModelAndView("/statistics/common/companyDetail.jsp")
				.addObject("companyId", companyId);
	}
	
	/**
	   * @功能描述: TODO 跳转巡视局重大措施页面
	   * @参数: @param type
	   * @参数: @param companyId
	   * @参数: @param request
	   * @参数: @param response
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月31日 下午8:34:13
	 */
	@RequestMapping("/goDecisionDetail")
	public @ResponseBody ModelAndView goDecisionDetail(HttpServletRequest request, HttpServletResponse response) {
		// 1.汇总上级重要决定习总书记的批示和指示
		List<StatisticsDecisionVo> decision = statisticsDecisionService.queryListBySource(null);
		// 2.根据事项目录汇总
		List<StatisticsDecisionVo> decisionlist = statisticsDecisionService.queryListByItem(null);
		// 3.合并结果
		decision.addAll(decisionlist);
		return new ModelAndView("/statistics/tour/tour_decision_list.jsp")
				.addObject("decision", decision);
	}
	
	/**
	   * @功能描述: TODO 跳转巡视局重大措施页面企业详情
	   * @参数: @param type
	   * @参数: @param request
	   * @参数: @param response
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月31日 下午9:04:10
	 */
	@RequestMapping("/goDecisionDetailByCompany")
	public @ResponseBody ModelAndView goDecisionDetailByCompany(HttpServletRequest request, HttpServletResponse response) {
		//提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		return new ModelAndView("/statistics/tour/tour_decision_subject.jsp")
				.addObject("statisticsType", parameter.get("statisticsType"))
				.addObject("objectId", parameter.get("objectId"))
				.addObject("companyId", parameter.get("companyId"));
	}
	
	/**
	 * 查看委领导重大措施议题页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goLeaderDecisionSubject")
	@ResponseBody
	public ModelAndView goLeaderDecisionSubject(HttpServletRequest request) {
		//提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		return new ModelAndView("/statistics/leader/decision_subject_list.jsp").addObject("statisticsType", parameter.get("statisticsType"))
				.addObject("objectId", parameter.get("objectId")).addObject("companyId", parameter.get("companyId"))
				.addObject("industryId", parameter.get("industryId")).addObject("companyName", parameter.get("companyName"));
	}
	
	/**
	 * 查看委领导重大措施会议页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goLeaderDecisionMeeting")
	@ResponseBody
	public ModelAndView goLeaderDecisionMeeting(HttpServletRequest request) {
		//提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		return new ModelAndView("/statistics/leader/decision_meeting_list.jsp")
                .addObject("statisticsType", parameter.get("statisticsType"))
				.addObject("objectId", parameter.get("objectId"))
                .addObject("companyId", parameter.get("companyId"))
				.addObject("industryId", parameter.get("industryId"))
                .addObject("companyName", parameter.get("companyName"));
	}
	
	/**
	 * 查看委领导重大措施会议页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goLeaderDecisionCompany")
	@ResponseBody
	public ModelAndView goLeaderDecisionCompany(HttpServletRequest request) {
		//提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		return new ModelAndView("/statistics/leader/decision_company_list.jsp").addObject("statisticsType", parameter.get("statisticsType"))
				.addObject("objectId", parameter.get("objectId")).addObject("industryId", parameter.get("industryId"));
	}
	
	/**
	 * 跳转委领导重大决策详情页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goLeaderDecisionDetail")
	public @ResponseBody ModelAndView goLeaderDecisionDetail(HttpServletRequest request) {
		//提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
	    String industryId = (String) parameter.get("industryId");
		// 1.汇总上级重要决定习总书记的批示和指示
		List<StatisticsDecisionVo> decision = statisticsDecisionService.queryListBySource(industryId);
		// 2.根据事项目录汇总
		List<StatisticsDecisionVo> decisionlist = statisticsDecisionService.queryListByItem(industryId);
		// 3.合并结果
		decision.addAll(decisionlist);
		return new ModelAndView("/statistics/leader/leader_decision_list.jsp").addObject("decision", decision).addObject("industryId",industryId);
	}
	
	/**
	 * 
	 * @功能描述 委领导会议分类跳转
	 * @time 2018年11月9日上午9:13:26
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/goMeetingByPage")
	public @ResponseBody ModelAndView goMeetingByPage(HttpServletRequest request) {
	    Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> meetingType = meetingTypeService.queryList(param);
		return new ModelAndView("/statistics/leader/meeting_classify_list.jsp").addObject("meetingType", meetingType);
	}
	
	/**
	 * 委领导议题分类跳转
	 * @param request
	 * @return
	 */
	@RequestMapping("/goLeaderSubjectClass")
	public @ResponseBody ModelAndView goLeaderSubjectClass(HttpServletRequest request) {
	    Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> catalog = catalogService.queryCatalogLevel(param);
		return new ModelAndView("/statistics/leader/subject_classify_list.jsp").addObject("catalog", catalog);
	}
	
	/**
	 * 
	 * @功能描述 委领导会议分类企业会议列表跳转
	 * @time 2018年11月9日上午9:38:33
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/goMeetingQuantity")
	public @ResponseBody ModelAndView goMeetingQuantity(String year,HttpServletRequest request) {
	    Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> meetingType = meetingTypeService.queryList(param);
		return new ModelAndView("/statistics/leader/meeting_quantity_list.jsp")
				.addObject("year", year)
				.addObject("meetingType", meetingType);
	}
	
	/**
	   * @功能描述: TODO 委领导跳转议题列表
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月9日 下午10:44:42
	 */
	@RequestMapping("/goLeaderCompanySubject")
	@ResponseBody
	public ModelAndView goLeaderCompanySubject(HttpServletRequest request) {
		//提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		return new ModelAndView("/statistics/leader/leader_subject_list.jsp")
				.addObject("search", "")
				.addObject("companyId", parameter.get("companyId"));
	}
	
	/**
	 * 
	 * @功能描述 跳转会议列表
	 * @time 2018年11月14日上午11:09:07
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/goMeetingList")
	@ResponseBody
	public ModelAndView goMeetingList(HttpServletRequest request) {
		//提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		return new ModelAndView("/statistics/leader/meeting_list.jsp")
				.addObject("typeId", parameter.get("typeId"))
				.addObject("companyId", parameter.get("companyId"))
				.addObject("catalogId", parameter.get("catalogId"));
	}
	
	/**
	 * 
	 * @功能描述 跳转议题列表
	 * @time 2018年11月14日上午11:15:16
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/goSubjectList")
	@ResponseBody
	public ModelAndView goSubjectList(HttpServletRequest request) {
		//提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		return new ModelAndView("/statistics/leader/subject_list.jsp")
				.addObject("levelCode", parameter.get("levelCode"))
				.addObject("companyId", parameter.get("companyId"))
				.addObject("catalogId", parameter.get("catalogId"));
	}
	
	/**
	 * 
	 * @功能描述 跳转事项清单议题列表
	 * @time 2018年11月14日上午11:36:16
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/goItemSubjectList")
	@ResponseBody
	public ModelAndView goItemSubjectList(HttpServletRequest request) {
		//提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		return new ModelAndView("/financial/item_subject_list.jsp")
				.addObject("levelCode", parameter.get("levelCode"))
				.addObject("companyId", parameter.get("companyId"))
				.addObject("catalogCode", parameter.get("catalogCode"));
	}
	
	/**
	 * 
	 * @功能描述 跳转事项清单列表
	 * @time 2018年11月14日上午11:36:35
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/goItemList")
	@ResponseBody
	public ModelAndView goItemList(HttpServletRequest request) {
		//提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		return new ModelAndView("/financial/item_list.jsp")
				.addObject("levelCode", parameter.get("levelCode"))
				.addObject("companyId", parameter.get("companyId"))
				.addObject("catalogId", parameter.get("catalogId"));
	}

	/**
	   * @功能描述:  事项清单汇总视图独立跳转
	   * @创建人 ：林鹏
	   * @创建时间：2018/11/18 15:27
	*/
	@RequestMapping("/goItemListByAlone")
	@ResponseBody
	public ModelAndView goItemListByAlone(HttpServletRequest request) {
		//提供获取接口传参进来的方法
		Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		return new ModelAndView("/statistics/leader/leader_item_detail_all.jsp")
				.addObject("catalogType", parameter.get("catalogType"));
	}
}
