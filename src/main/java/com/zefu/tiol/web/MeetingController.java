package com.zefu.tiol.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.zefu.tiol.pojo.Meeting;
import com.zefu.tiol.util.FileUtils;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yr.gap.base.util.GapGlobal;
import com.yr.gap.common.core.LoginUser;
import com.yr.gap.common.plugin.Result;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.service.MeetingService;

/**
 * 会议信息控制类
 * 
 * @author：陈东茂
 * @date：2018年10月26日
 */
@Controller
@RequestMapping("/meeting")
public class MeetingController {
	
	/**
	 * 此备注为ServiceImpl中配置的参数，如不要请用@Autowired
	 */
	@Resource(name = "meetingService")
	MeetingService meetingService;
	
	/**
	 * 会议信息页面跳转
	 * @param meetingId
	 * @param request
	 * @return
	 */
	@RequestMapping("/meetingDetail")
	public @ResponseBody ModelAndView staticRegulationList(String meetingId,String companyId, HttpServletRequest request) {
		Map<String,Object> meeting = meetingService.getMeetingDetailById(meetingId,companyId);
		return new ModelAndView("/statistics/leader/meeting_view.jsp").addObject("meeting", meeting);
	}
	
	/**
	 * 
	 * @功能描述 分页查询会议信息
	 * @time 2018年10月30日下午3:18:09
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/queryMeetingByPage")
	@ResponseBody
	public Object queryMeetingByPage(HttpServletRequest request) {
		Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
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
			parameter.put("catalogList", catalogList);	
			parameter.put("meetingTypeList", meetingTypeList);
			parameter.put("companyList", companyList);
			list = meetingService.queryMeetingByPage(parameter, page);
        	totalCount = meetingService.getMeetingTotalCount(parameter);
		}
        page.setTotalCount(totalCount);
        page.setData(list);
		return page;
	}
	
	/**
	   * @throws Exception 
	   * @功能描述: TODO 平台-会议列表
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月3日 下午3:03:05
	 */
	@RequestMapping("/model/queryMeetingList")
	@ResponseBody
	public Object queryMeetingList(HttpServletRequest request) throws Exception {
		Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        //登陆信息
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(GapGlobal.SESSION.GAP_SSO_SESSION_USERNAME);
        parameter.put("instID", loginUser.getInstId());
        //查询下属企业列表
  		List<String> companyID = meetingService.queryCompanyByInst(parameter);
  		if(companyID.size() > 0 && companyID != null){
  			parameter.put("companyID", companyID);
  		}else{
  			parameter.put("companyID", "");
  		}
        //查询数据
        List<Map<String, Object>> list = meetingService.queryMeetingList(parameter, page);
        int totalCount = meetingService.queryMeetingListTotalCount(parameter);
        page.setTotalCount(totalCount);
        page.setData(list);
		return page;
	}
	
	/**
	   * @功能描述: TODO 查询会议信息详情
	   * @参数: @param request
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月3日 下午4:35:43
	 */
	@RequestMapping("/queryMeetingMessage")
	@ResponseBody
	public Object queryMeetingMessage(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Map<String,Object> meeting = meetingService.getMeetingDetailById(param.get("meetingId").toString(),param.get("companyId").toString());
		return new Result(meeting);
	}


	/**
	 * @功能描述: 保存会议信息
	 * @参数: @param request
	 * @参数: @return
	 * @参数: @throws Exception
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月9日
	 */
	@RequestMapping("/saveMeeting")
	public ModelAndView saveMeeting(@RequestParam("noticeFile") MultipartFile noticeFile,@RequestParam("summaryFile") MultipartFile summaryFile,HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		//上传文件至FS服务
		String noticeFileId = FileUtils.uploadMultipartFileToFs(noticeFile);
		String summaryFileId = FileUtils.uploadMultipartFileToFs(summaryFile);

		param.put("noticeFileId",noticeFileId);
		param.put("summaryFileId",summaryFileId);
		Meeting meeting = meetingService.saveMeeting(param);
		param.put("meetingId", meeting.getMeetingId());

		Map<String,Object> meetingMap = meetingService.queryMeetingInfo(param);

		String goPage = "/meeting/register/"+param.get("page")+".jsp";
		return new ModelAndView(goPage).addObject("meeting", meetingMap).addObject("flag",true);
	}

	/**
	 * 刷新会议表单信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/reloadMeeting")
	public ModelAndView reloadMeeting(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Map<String,Object> meetingMap = meetingService.queryMeetingInfo(param);
		String isTodo = param.get("isTodo") == null ? "" : param.get("isTodo").toString();
		meetingMap.put("isTodo", isTodo);//是否是待办进入的标识
		String goPage = "/meeting/register/"+param.get("page")+".jsp";
		return new ModelAndView(goPage).addObject("meeting", meetingMap);
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
	@RequestMapping("/getMeetingAllCount")
	@ResponseBody
	public Object getMeetingAllCount(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<String, Object>();
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		//所有会议数
		int allCount = meetingService.getMeetingTotalCount(param);
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
        	totalCount = meetingService.getMeetingTotalCount(param);
		}
		result.put("all", allCount);
		result.put("have", totalCount);
		result.put("not", allCount-totalCount);
		return new Result(result);
	}
	
	/**
	   * @功能描述: 上报制度
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 林长怀
	   * @创建时间：2018年11月14日 下午15:09:22
	 */
	@RequestMapping("/uploadMeeting")
	@ResponseBody
	public Object uploadMeeting(HttpServletRequest request) {
		String url = "http://"+request.getServerName() + ":"+request.getServerPort();
		if(!StringUtils.isEmpty(request.getContextPath())&&!"/".equals(request.getContextPath())) {
			url += "/"+request.getContextPath();
		}
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		param.put("ids",(param.get("ids")+"").split(","));
		LoginUser currentUser = (LoginUser) param.get("CURRENT_USER");
		String companyId = currentUser.getCompanyId();
		String userName = currentUser.getUserName();
		String errorMsg = meetingService.uploadMeeting(param,companyId,url,userName);
		if(StringUtils.isEmpty(errorMsg)) {
			return new Result(1,"");
		}else {
			return new Result(0,errorMsg);
		}
	}


	/**
	 * @throws Exception
	 * @功能描述: TODO 查询会议参会人员
	 * @参数: @param request
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月16日 下午5:07:12
	 */
	@RequestMapping("/queryMeetingAttendee")
	@ResponseBody
	public Map<String,Object> queryMeetingAttendee(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Map<String,Object> attendeeList = meetingService.queryMeetingAttendee(param);
		return attendeeList;
	}

	/**
	 * @功能描述: 单位填报查询会议信息
	 * @参数: @param request
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月18日 下午5:07:12
	 */
	@RequestMapping("/queryCompanyMeetingList")
	@ResponseBody
	public Object queryCompanyMeetingList(HttpServletRequest request){
		//查询条件
		Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		//分页参数
		Page page = CommonUtil.getPageFromRequest(request);
		//登陆信息
		LoginUser loginUser = CommonUtil.getLoginUser(request);
		parameter.put("companyId",loginUser.getCompanyId());

		List<Map<String, Object>> list = meetingService.queryCompanyMeetingList(parameter,page);
		int count = meetingService.countCompanyMeetingList(parameter);
		page.setTotalCount(count);
		page.setData(list);
		return page;
	}

	/**
	 * @功能描述: 删除会议
	 * @参数: @param request
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月18日 下午5:07:12
	 */
	@RequestMapping("/deleteMeeting")
	@ResponseBody
	public Object deleteMeeting(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		param.put("ids",(param.get("ids")+"").split(","));
		int count = meetingService.deleteMeeting(param);
		return new Result(count);
	}

	/**
	 * @功能描述: 恢复删除会议
	 * @参数: @param request
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月18日 下午5:07:12
	 */
	@RequestMapping("/recoverMeeting")
	@ResponseBody
	public Object recoverMeeting(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		param.put("ids",(param.get("ids")+"").split(","));
		int count = meetingService.recoverMeeting(param);
		return new Result(count);
	}
}
