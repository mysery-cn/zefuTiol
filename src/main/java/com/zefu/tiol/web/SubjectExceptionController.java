package com.zefu.tiol.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yr.gap.base.util.GapGlobal;
import com.yr.gap.common.core.LoginUser;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.service.MeetingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yr.gap.common.plugin.Result;
import com.yr.gap.common.util.CommonUtil;
import com.zefu.tiol.service.SubjectExceptionService;


@Controller
@RequestMapping("/subjectException")
public class SubjectExceptionController {

	@Resource(name = "subjectExceptionService")
	private SubjectExceptionService subjectExceptionService;

    @Resource(name = "meetingService")
    MeetingService meetingService;

	/**
	 * 修改议题异常
	 * @param request
	 * @return
	 */
	@RequestMapping("/editSubjectException")
	@ResponseBody
	public Object editSubjectException(HttpServletRequest request) {
	    //提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
	    Result ret = new Result();
	    if(parameter.get("exceptionId").equals(null)){
	    	ret.setErrorCode(-1);
	        ret.setErrorInfo("修改失败！");
	    }else{
		    //更新数据
		    this.subjectExceptionService.modifySubjectException(parameter);
	    }
	    return ret;
	}
	
	/**
	 * 删除议题异常
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteSubjectException")
	@ResponseBody
	public Object deleteSubjectException(HttpServletRequest request) {
	    //提供获取接口传参进来的方法
	    Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
	    Result ret = new Result();
	    if(parameter.get("exceptionId").equals(null)){
	    	ret.setErrorCode(-1);
	        ret.setErrorInfo("删除失败！");
	    }else{
		    //更新数据
		    int result = this.subjectExceptionService.removeSubjectException(parameter);
		    if (result == -1){
		      ret.setErrorCode(-1);
		      ret.setErrorInfo("删除失败!");
		    }
	    }
	    return ret;
	}

	/**
	   * @功能描述:  查询议题异常列表
	   * @创建人 ：林鹏
	   * @创建时间：2018/11/19 14:15
	*/
    @RequestMapping("/querySubjectExceptionByPage")
    @ResponseBody
    public Object querySubjectExceptionByPage(HttpServletRequest request) throws Exception {
        //基础数据
        Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
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
        if (catalogList.size() > 0 && meetingTypeList.size() >0 && companyList.size() > 0) {
            param.put("catalogList", catalogList);
            param.put("meetingTypeList", meetingTypeList);
            param.put("companyList", companyList);
        }
        //登陆信息
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(GapGlobal.SESSION.GAP_SSO_SESSION_USERNAME);
        param.put("instID", loginUser.getInstId());

        //查询下属企业列表
        List<String> companyID = meetingService.queryCompanyByInst(param);

        if(companyID.size() > 0 && companyID != null){
            param.put("companyID", companyID);
        }else{
            param.put("companyID", "");
        }
        //分页数据
        Page<Map<String,Object>> page =CommonUtil.getPageFromRequest(request);
        List<Map<String, Object>> list = subjectExceptionService.querySubjectExceptionByPage(param,page);
        //统计总数
        int totalCount = subjectExceptionService.querySubjectExceptionTotalCount(param);
        page.setData(list);
        page.setTotalCount(totalCount);
        return page;
    }




}
