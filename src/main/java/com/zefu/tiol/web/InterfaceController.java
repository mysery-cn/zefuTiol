package com.zefu.tiol.web;

import com.yr.gap.base.util.GapGlobal;
import com.yr.gap.common.core.LoginUser;
import com.yr.gap.common.plugin.Result;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.pojo.Meeting;
import com.zefu.tiol.service.InterfaceService;
import com.zefu.tiol.service.MeetingService;
import com.zefu.tiol.util.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 服务接口
 */
@Controller
@RequestMapping("/serviceInterface")
public class InterfaceController {
	
	/**
	 * 此备注为ServiceImpl中配置的参数，如不要请用@Autowired
	 */
	@Resource(name = "interfaceService")
	InterfaceService interfaceService;
	
//	/**
//	 * 会议信息页面跳转
//	 * @param meetingId
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/meetingDetail")
//	public @ResponseBody ModelAndView staticRegulationList(String meetingId,String companyId, HttpServletRequest request) {
//		Map<String,Object> meeting = meetingService.getMeetingDetailById(meetingId,companyId);
//		return new ModelAndView("/statistics/leader/meeting_view.jsp").addObject("meeting", meeting);
//	}

	/**
	 * 查询服务接口分页
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryInterfaceByPage")
	@ResponseBody
	public Object queryInterfaceByPage(HttpServletRequest request) {
		Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        interfaceService.queryInterfaceByPage(parameter, page);
		return page;
	}

	@RequestMapping("/saveInterface")
	@ResponseBody
	public Object saveInterface(HttpServletRequest request) throws Exception {
		Map<String,Object> parameter = CommonUtil.getParameterFromRequest(request);
		parameter.put("UI_ID", UUID.randomUUID().toString().replaceAll("-", ""));
		interfaceService.saveInterface(parameter);
		return new Result(parameter);
	}
	
//	/**
//	   * @throws Exception
//	   * @功能描述: TODO 平台-会议列表
//	   * @参数: @param request
//	   * @参数: @return
//	   * @创建人 ：林鹏
//	   * @创建时间：2018年11月3日 下午3:03:05
//	 */
//	@RequestMapping("/model/queryMeetingList")
//	@ResponseBody
//	public Object queryMeetingList(HttpServletRequest request) throws Exception {
//		Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
//        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
//        //登陆信息
//        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(GapGlobal.SESSION.GAP_SSO_SESSION_USERNAME);
//        parameter.put("instID", loginUser.getInstId());
//        //查询下属企业列表
//  		List<String> companyID = meetingService.queryCompanyByInst(parameter);
//  		if(companyID.size() > 0 && companyID != null){
//  			parameter.put("companyID", companyID);
//  		}else{
//  			parameter.put("companyID", "");
//  		}
//        //查询数据
//        List<Map<String, Object>> list = meetingService.queryMeetingList(parameter, page);
//        int totalCount = meetingService.queryMeetingListTotalCount(parameter);
//        page.setTotalCount(totalCount);
//        page.setData(list);
//		return page;
//	}
//
//	/**
//	   * @功能描述: TODO 查询会议信息详情
//	   * @参数: @param request
//	   * @参数: @return
//	   * @参数: @throws Exception
//	   * @创建人 ：林鹏
//	   * @创建时间：2018年11月3日 下午4:35:43
//	 */
//	@RequestMapping("/queryMeetingMessage")
//	@ResponseBody
//	public Object queryMeetingMessage(HttpServletRequest request) throws Exception{
//		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
//		Map<String,Object> meeting = meetingService.getMeetingDetailById(param.get("meetingId").toString(),param.get("companyId").toString());
//		return new Result(meeting);
//	}
//
//
//	/**
//	 * @功能描述: 保存会议信息
//	 * @参数: @param request
//	 * @参数: @return
//	 * @参数: @throws Exception
//	 * @创建人 ：李缝兴
//	 * @创建时间：2018年11月9日
//	 */
//	@RequestMapping("/saveMeeting")
//	public ModelAndView saveMeeting(@RequestParam("noticeFile") MultipartFile noticeFile,@RequestParam("summaryFile") MultipartFile summaryFile,HttpServletRequest request) throws Exception{
//		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
//		//上传文件至FS服务
//		String noticeFileId = FileUtils.uploadMultipartFileToFs(noticeFile);
//		String summaryFileId = FileUtils.uploadMultipartFileToFs(summaryFile);;
//
//		param.put("noticeFileId",noticeFileId);
//		param.put("summaryFileId",summaryFileId);
//		Meeting meeting = meetingService.saveMeeting(param);
//
//		Map<String,Object> meetingMap = meetingService.getMeetingDetailById(meeting.getMeetingId(),meeting.getCompanyId());
//		return new ModelAndView("/meeting/register/meeting_register.jsp").addObject("meeting", meetingMap);
//	}

}
