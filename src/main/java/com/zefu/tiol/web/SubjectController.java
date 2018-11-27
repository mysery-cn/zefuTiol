package com.zefu.tiol.web;

import com.yr.gap.common.core.LoginUser;
import com.yr.gap.common.plugin.Result;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.pojo.Subject;
import com.zefu.tiol.service.MeetingService;
import com.zefu.tiol.service.SubjectService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 议题信息
 * 
 * @author：陈东茂
 * @date：2018年10月26日
 */
@Controller
@RequestMapping("/subject")
public class SubjectController {
	
	/**
	 * 此备注为ServiceImpl中配置的参数，如不要请用@Autowired
	 */
	@Resource(name = "subjectService")
	SubjectService subjectService;
	
	@Resource(name = "meetingService")
	MeetingService meetingService;
	
	/**
	 * 跳转议题详情页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/subjectDetail")
	@ResponseBody
	public ModelAndView subjectDetail(HttpServletRequest request) {
		//提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		return new ModelAndView("/statistics/leader/subject_view.jsp")
                .addObject("meetingId", parameter.get("meetingId"))
				.addObject("subjectId", parameter.get("subjectId"))
                .addObject("companyId", parameter.get("companyId"));
	}
	
	/**
	 * 跳转议题决策页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/subjectProcess")
	@ResponseBody
	public ModelAndView subjectProcess(HttpServletRequest request) {
		//提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		return new ModelAndView("/statistics/common/subject_process_view.jsp").addObject("meetingId", parameter.get("meetingId"))
				.addObject("subjectId", parameter.get("subjectId")).addObject("companyId", parameter.get("companyId"));
	}

	/**
	 * 查询议题简要信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/querySubjectBrief")
	@ResponseBody
	public List<Map<String, Object>> querySubjectBrief(HttpServletRequest request) {
	    //提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
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
			parameter.put("catalogList", catalogList);	
			parameter.put("meetingTypeList", meetingTypeList);
			parameter.put("companyList", companyList);
		    //查询数据
			list = subjectService.listSubjectByMeeting(parameter);
		}
	    return list;
	}

	/**
	 * 查询议题详情信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/querySubjectDetail")
	@ResponseBody
	public Map<String, Object> querySubjectDetail(HttpServletRequest request) {
	    //提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
	    Map<String, Object> list = new HashMap<String, Object>();
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
			list = subjectService.getSubjectDetail(parameter);
		}
	    return list;
	}
	
    /**
     * 查询议题决策流程
     */
	@RequestMapping("/querySubjectMeetingProcess")
	@ResponseBody
	public Object querySubjectMeetingProcess(HttpServletRequest request) {
	    //提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
	    Map<String,Object> result = new HashMap<String,Object>();
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
			result = subjectService.querySubjectMeetingProcess(parameter);
			
		}
	    return result;
	}
	
	/**
	 * 查询党建局议题列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryDangjianSubjectList")
	@ResponseBody
	public Page<Map<String, Object>> queryDangjianSubjectList(HttpServletRequest request) {
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
			list = subjectService.listDangjianSubject(parameter, page);
			totalCount = subjectService.countDangjianSubject(parameter);
		}
	  	page.setTotalCount(totalCount);
	    page.setData(list);
	  	return page;
	}
	
	/**
	 * 获取党建局议题总数及未前置总数
	 * @param request
	 * @return
	 */
	@RequestMapping("/getDangjianSubjectCount")
	@ResponseBody
	public Object getDangjianSubjectCount(HttpServletRequest request) {
		Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		Map<String, Object> map = new HashMap<String, Object>();
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
			map = subjectService.getDangjianSubjectCount(parameter);
		}else{
			map.put("totalNum", 0);
			map.put("exceptionNum", 0);
		}
		return new Result(map);
	}
	
	/**
	 * 
	 * @功能描述 分页查询会议议题信息
	 * @time 2018年11月1日下午6:32:08
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
        	list = subjectService.querySubjectByPage(param, page);
        	totalCount = subjectService.getSubjectTotalCount(param);
	    }
        page.setTotalCount(totalCount);
        page.setData(list);
		return page;
	}
	
	/**
	   * @throws Exception 
	   * @功能描述: TODO 平台查询议题列表
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月4日 上午10:43:36
	 */
	@RequestMapping("/model/querySubjectList")
	@ResponseBody
	public Object querySubjectList(HttpServletRequest request) throws Exception {
		Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        //权限
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
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int totalCount = 0;
        if (catalogList.size() > 0 && meetingTypeList.size() >0 && companyList.size() > 0) {
            parameter.put("catalogList", catalogList);
            parameter.put("meetingTypeList", meetingTypeList);
            parameter.put("companyList", companyList);
            list = subjectService.querySubjectList(parameter, page);
            totalCount = subjectService.querySubjectTotalCount(parameter);
        }
        page.setTotalCount(totalCount);
        page.setData(list);
		return page;
	}

	/**
	 * 保存议题信息
	 * @param request 请求信息
	 * @return
	 * @throws Exception
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月13日
	 */
	@RequestMapping("/saveSubject")
	@ResponseBody
	public Object saveSubject(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Subject subject = subjectService.saveSubject(param);
		return new Result(subject);
	}

	/**
	 * 根据会议查询议题信息
	 * @param request 请求信息
	 * @return
	 * @throws Exception
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月13日
	 */
	@RequestMapping("/getSubjectByMeetingId")
	@ResponseBody
	public List<Map<String,Object>> getSubjectByMeetingId(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String,Object>> subjectList = subjectService.getSubjectByMeetingId(param);
		return subjectList;
	}

	/**
	 * 前往编辑页面
	 * @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月13日
	 */
	@RequestMapping("/toSubjectEdit")
	public ModelAndView toSubjectEdit(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Map<String, Object> subject = subjectService.getSubjectById(param);
		subject.put("companyId", param.get("companyId"));
		return new ModelAndView("/meeting/register/subject_edit.jsp").addObject("subject", subject);
	}

	/**
	 * 上传附件
	 * @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月14日
	 */
	@RequestMapping("/uploadSubjectFile")
	public ModelAndView uploadSubjectFile(@RequestParam("uploadfile") MultipartFile uploadfile, HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		LoginUser user = CommonUtil.getLoginUser(request);
		//上传文件至FS服务
		String fileId = FileUtils.uploadMultipartFileToFs(uploadfile);
		param.put("fileId", fileId);
		param.put("attachmentId", fileId);
		param.put("businessId", param.get("subjectId"));
		param.put("createUser",user.getUserName());
		subjectService.insertAttachment(param);
		return new ModelAndView("/meeting/register/upload_file.jsp").addObject("flag", true).addObject("file", param);
	}

	/**
	 * 查询关联附件列表
	 * @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月14日
	 */
	@RequestMapping("/getSubjectFileById")
	@ResponseBody
	public List<Map<String,Object>> getSubjectFileById(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		return subjectService.getSubjectFileById(param);
	}

	/**
	 * 删除附件关联
	 * @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月14日
	 */
	@RequestMapping("/deleteSubjectFileById")
	@ResponseBody
	public Object deleteSubjectFileById(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		subjectService.deleteSubjectFileByFileId(param);
		return new Result();
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
	@RequestMapping("/getSubjectAllCount")
	@ResponseBody
	public Object getSubjectAllCount(HttpServletRequest request) {
		Map<String,Object> result = new HashMap<String, Object>();
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		//所有议题数
		int allCount = subjectService.getSubjectTotalCount(param);

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
        	totalCount = subjectService.getSubjectTotalCount(param);
		}
		result.put("all", allCount);
		result.put("have", totalCount);
		result.put("not", allCount-totalCount);
		return new Result(result);
	}
}
