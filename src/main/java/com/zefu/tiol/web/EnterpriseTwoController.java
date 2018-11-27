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

import com.yr.gap.base.util.GapGlobal;
import com.yr.gap.common.core.LoginUser;
import com.yr.gap.common.plugin.Result;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.service.EnterpriseTwoService;

@Controller
@RequestMapping("/enterpriseTwo")
public class EnterpriseTwoController {

	@Resource(name = "enterpriseTwoService")
	private EnterpriseTwoService enterpriseTwoService;
	
	@RequestMapping("/subjectJump")
	public @ResponseBody ModelAndView subjectJump(HttpServletRequest request) {
		return new ModelAndView("/statistics/enterpriseTwo/enterpriseTwo_list_item.jsp");
	}
	
	/**
	 * 重要人事任免会议分布统计图
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryMeetingDetail")
	@ResponseBody
	public Object queryMeetingDetail(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        //查询数据
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(GapGlobal.SESSION.GAP_SSO_SESSION_USERNAME);
        List<Map<String, Object>> companyList = enterpriseTwoService.getCurUserCompany(loginUser.getDeptId());
        Map<String, Object> detail = enterpriseTwoService.queryMeetingDetail(parameter,companyList);
		return new Result(detail);
	}
	/**
	 * 2018年董事会议题情况统计图
	 * @param request
	 * @return
	 */
	@RequestMapping("/querySubjectDetail")
	@ResponseBody
	public Object querySubjectDetail(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        //用户权限
        HttpSession session = request.getSession();
        Map<String,Object> permitMap = new HashMap<String, Object>();
        if (session.getAttribute("permitMap") != null && session.getAttribute("permitMap") != "") {
            permitMap = (Map<String, Object>) session.getAttribute("permitMap");
        }
        List<Map<String, Object>> companyList = companyList = (List<Map<String, Object>>) permitMap.get("companyList");
        //查询数据
        Map<String, Object> detail = enterpriseTwoService.querySubjectDetail(parameter,companyList);
		return new Result(detail);
	}
	/**
	 * 企干二局主页面议题列表查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryMeetingTypeSubjectListByPage")
	@ResponseBody
	public Object queryMeetingTypeSubjectListByPage(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
		LoginUser loginUser = (LoginUser) request.getSession().getAttribute(GapGlobal.SESSION.GAP_SSO_SESSION_USERNAME);
        List<Map<String, Object>> companyList = enterpriseTwoService.getCurUserCompany(loginUser.getDeptId());
		//查询数据
		List<Map<String, Object>> list = enterpriseTwoService.queryMeetingTypeSubjectListByPage(param, page,companyList);
		//查询总数
        int totalCount = enterpriseTwoService.queryTotalCount(param,companyList);
		page.setData(list);
		page.setTotalCount(totalCount);
		return page;
	}
	/**
	 * 查询级别为2且编码已H开头的事项目录
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryCatalogLevel2andH")
	@ResponseBody
	public Object queryCatalogLevel2andH(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> list = enterpriseTwoService.queryCatalogLevel2andH(param);	
		return new Result(list);
	}
	/**
	 * 查询二局内页董事会议题列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/querySubjectPageList")
	@ResponseBody
	public Object querySubjectPageList(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        //查询数据
        //用户权限
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
            List<Map<String, Object>> list = enterpriseTwoService.querySubjectPageList(parameter, page,companyList);
            int totalCount = enterpriseTwoService.querySubjectTotalCount(parameter,companyList);
            page.setTotalCount(totalCount);
            page.setData(list);
        }else{
            page.setTotalCount(0);
            page.setData(null);
        }
		return page;
	}
	/**
	 * 重载二局内页董事会议题列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/reloadSubjectPageList")
	@ResponseBody
	public Object reloadSubjectPageList(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(GapGlobal.SESSION.GAP_SSO_SESSION_USERNAME);
        List<Map<String, Object>> companyList = enterpriseTwoService.getCurUserCompany(loginUser.getDeptId());
        //查询数据
        List<Map<String, Object>> list = enterpriseTwoService.reloadSubjectPageList(parameter, page,companyList);
        int totalCount = enterpriseTwoService.reloadSubjectTotalCount(parameter,companyList);
        page.setTotalCount(totalCount);
        page.setData(list);
		return page;
	}

    /**
     * @功能描述:  企干二局议题列表权限数据
     * @创建人 ：林鹏
     * @创建时间：2018/11/22 16:12
     */
    @RequestMapping("/queryTwoIndexListByRoleNumber")
    @ResponseBody
    public Object queryOneIndexListByRoleNumber(HttpServletRequest request) {
        Map<String,Object> result = new HashMap<String, Object>();
        Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        //所有议题数
        int allCount = enterpriseTwoService.querySubjectTotalCount(param,null);

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
            totalCount = enterpriseTwoService.querySubjectTotalCount(param,companyList);
        }
        result.put("all", allCount);
        result.put("have", totalCount);
        result.put("not", allCount-totalCount);
        return new Result(result);
    }
}
