package com.zefu.tiol.web.dispatch;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yr.gap.common.plugin.Result;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.ums.service.ICompanyService;
import com.zefu.tiol.service.CompanyMemberService;
import com.zefu.tiol.service.CompanyService;

/**
 * 国资委数据共享中心系统对接控制类
 * 
 * @author：杨洋
 * @date：2018年11月21日
 */
@Controller
@RequestMapping("/dispatchCompanyMember")
public class DispatchCompanyMemberController {
	
	@Resource(name = "companyMemberService")
	private CompanyMemberService companyMemberService;
	
	@Resource(name = "tiolCompanyService")
	private CompanyService companyService;
	
	@Resource(name = "companyService")
	private ICompanyService iCompanyService;

	@RequestMapping("/saveCompanyMember")
	@ResponseBody
	public Object saveCompanyMember(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		//获取干部系统内部成员数据
		List<Map<String, Object>> tempList = companyMemberService.getCompanyMember();
		//获取干部系统外部董事数据
		List<Map<String, Object>> outDirectorlist = companyMemberService.getCompanyOutDirector();
		if(tempList.size()>0) {
			companyMemberService.deleteCompanyMember(param);
			param.put("tempList", tempList);
			param.put("outDirectorlist", outDirectorlist);
			companyMemberService.saveCompanyMember(tempList);
			companyMemberService.saveCompanyMember(outDirectorlist);
		}
		return new Result("sucess");
	}
	
	/**
	 * 同步压减系统企业信息数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/synchronizeMsgByReduct")
	@ResponseBody
	public Object synchronizeMsgByReduct(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		//获取干部系统数据
		Map<String, Object> result = companyService.synchronizeMsgByReduct();
		//iCompanyService.
		return new Result(result);
	}
	
	/**
	 * 查询压减系统企业信息与系统不同的数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryCompanyMsgByReduct")
	@ResponseBody
	public Object queryCompanyMsgByReduct(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		//获取干部系统数据
		List<Map<String, Object>> result = companyService.queryDiffrentMsg();
		//iCompanyService.
		return new Result(result);
	}
}
