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
import com.zefu.tiol.service.CatalogService;
import com.zefu.tiol.service.EnterpriseOneService;
import com.zefu.tiol.service.MeetingTypeService;

@Controller
@RequestMapping("/enterpriseOne")
public class EnterpriseOneController {

	@Resource(name = "enterpriseOneService")
	private EnterpriseOneService enterpriseOneService;
	
	@Resource(name = "catalogService")
	private CatalogService catalogService;
	
	@Resource(name = "meetingTypeService")
	private MeetingTypeService meetingTypeService;
	/**
	 * 重要人事任免分布统计图跳转内页
	 * @param request
	 * @return
	 */
	@RequestMapping("/meetingJump")
	public @ResponseBody ModelAndView meetingJump(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> meetingType = meetingTypeService.queryList(param);
		return new ModelAndView("/statistics/enterpriseOne/enterpriseOne_list_quantity.jsp").addObject("meetingType", meetingType);
	}
	
	/**
	 * 2018董事会议题情况统计图跳转内页
	 * @param request
	 * @return
	 */
	@RequestMapping("/decisionJump")
	public @ResponseBody ModelAndView decisionJump(HttpServletRequest request) {
		return new ModelAndView("/statistics/enterpriseOne/enterpriseOne_list_item.jsp");
	}
	
	/**
	 * 涉及出资人重大决策议题统计图跳转内页
	 * @param request
	 * @return
	 */
	@RequestMapping("/investorJump")
	public @ResponseBody ModelAndView investorJump(HttpServletRequest request) {
		return new ModelAndView("/statistics/enterpriseOne/enterpriseOne_list_subject.jsp");
	}
	
//	/**
//	 * 查询当前用户下所对应的企业集合
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping("/getCurUserCompany")
//	@ResponseBody
//	public Object getCurUserCompany(HttpServletRequest request) {
//		//提供获取接口传参进来的方法
//        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
//        //查询数据
//        Map<String, Object> detail = enterpriseOneService.getCurUserCompany(parameter);
//		return new Result(detail);
//	}
	
	/**
	 * 重要人事任免分布统计查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryMeetingDetail")
	@ResponseBody
	public Object queryMeetingDetail(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        //获取当前用户
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(GapGlobal.SESSION.GAP_SSO_SESSION_USERNAME);
        List<Map<String, Object>> companyList = enterpriseOneService.getCurUserCompany(loginUser.getDeptId());
        //查询数据
        Map<String, Object> detail = enterpriseOneService.queryMeetingDetail(parameter,companyList);
		return new Result(detail);
	}
	
	/**
	 * 涉及出资人重大决策议题统计查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryDecisionSubjectDetail")
	@ResponseBody
	public Object queryDecisionSubjectDetail(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
      //获取当前用户
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(GapGlobal.SESSION.GAP_SSO_SESSION_USERNAME);
        List<Map<String, Object>> companyList = enterpriseOneService.getCurUserCompany(loginUser.getDeptId());
        //查询数据
        Map<String, Object> detail = enterpriseOneService.queryDecisionSubjectDetail(parameter,companyList);
		return new Result(detail);
	}
	
	
	/**
	 * 2018董事会议题情况统计查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryInvestorDetail")
	@ResponseBody
	public Object queryInvestorDetail(HttpServletRequest request) {
		 Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        //查询数据
		LoginUser loginUser = (LoginUser) request.getSession().getAttribute(GapGlobal.SESSION.GAP_SSO_SESSION_USERNAME);
        List<Map<String, Object>> companyList = enterpriseOneService.getCurUserCompany(loginUser.getDeptId());
        Map<String, Object> detail = enterpriseOneService.queryInvestorDetail(parameter,companyList);
		return new Result(detail);
	}
	
	/**
	 * 企干一局主页面议题列表查询
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryMeetingTypeSubjectListByPage")
	@ResponseBody
	public Object queryMeetingTypeSubjectListByPage(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
		//权限控制
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
            list = enterpriseOneService.queryMeetingTypeSubjectListByPage(param, page);
            totalCount =     enterpriseOneService.queryTotalCount(param);
        }
		page.setData(list);
		page.setTotalCount(totalCount);
		return page;
	}
	
	/**
	 * 查询级别为1的事项目录：重大决策事项、重要人事任免分布事项、重大项目安排事项、大额资金运作事项
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryCatalogLevel")
	@ResponseBody
	public Object queryCatalogList(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> list = catalogService.queryCatalogLevel(param);	
		return new Result(list);
	}
	
	/**
	 * 查询重要人事任免分布————企业会议列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryMeetingByPage")
	@ResponseBody
	public Object queryMeetingByPage(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(GapGlobal.SESSION.GAP_SSO_SESSION_USERNAME);
        List<Map<String, Object>> companyList = enterpriseOneService.getCurUserCompany(loginUser.getDeptId());
        //查询数据
        List<Map<String, Object>> list = enterpriseOneService.queryMeetingByPage(parameter, page,companyList);
        int totalCount = enterpriseOneService.getMeetingTotalCount(parameter,companyList);
        page.setTotalCount(totalCount);
        page.setData(list);
		return page;
	}
	
	/**
	 * 查询涉及出资人重大决策议题————企业议题列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/querySubjectPageList")
	@ResponseBody
	public Object querySubjectPageList(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
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
            List<Map<String, Object>> list = enterpriseOneService.querySubjectPageList(parameter, page);
            int totalCount = enterpriseOneService.querySubjectTotalCount(parameter);
            page.setTotalCount(totalCount);
            page.setData(list);
        }else{
            page.setTotalCount(0);
            page.setData(null);
        }
		return page;
	}
	
	@RequestMapping("/querySubjectPageListDsh")
	@ResponseBody
	public Object querySubjectPageListDsh(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        //查询数据
        List<Map<String, Object>> list = enterpriseOneService.querySubjectPageListDsh(parameter, page);
        int totalCount = enterpriseOneService.querySubjectDshTotalCount(parameter);
        page.setTotalCount(totalCount);
        page.setData(list);
		return page;
	}

    /**
       * @功能描述:  企干一局-议题列表权限查看数量
       * @创建人 ：林鹏
       * @创建时间：2018/11/22 15:35
    */
    @RequestMapping("/queryOneSubjectListByRoleNumber")
    @ResponseBody
    public Object queryOneSubjectListByRoleNumber(HttpServletRequest request) {
        Map<String,Object> result = new HashMap<String, Object>();
        Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        //所有议题数
        int allCount = enterpriseOneService.querySubjectTotalCount(param);

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
            totalCount = enterpriseOneService.querySubjectTotalCount(param);
        }
        result.put("all", allCount);
        result.put("have", totalCount);
        result.put("not", allCount-totalCount);
        return new Result(result);
    }

    /**
       * @功能描述:  企干一局首页议题列表显示
       * @创建人 ：林鹏
       * @创建时间：2018/11/22 16:12
    */
    @RequestMapping("/queryOneIndexListByRoleNumber")
    @ResponseBody
    public Object queryOneIndexListByRoleNumber(HttpServletRequest request) {
        Map<String,Object> result = new HashMap<String, Object>();
        Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        //所有议题数
        int allCount = enterpriseOneService.queryTotalCount(param);

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
            totalCount = enterpriseOneService.queryTotalCount(param);
        }
        result.put("all", allCount);
        result.put("have", totalCount);
        result.put("not", allCount-totalCount);
        return new Result(result);
    }
}
