package com.zefu.tiol.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yr.gap.common.plugin.Result;
import com.yr.gap.common.util.CommonUtil;
import com.zefu.tiol.service.CompanyMemberService;

/**
 * 
 * @功能描述 企业领导班子成员
 * @time 2018年10月26日下午3:19:24
 * @author Super
 *
 */
@Controller
@RequestMapping("/companyMember")
public class CompanyMemberController {

	@Resource(name = "companyMemberService")
	private CompanyMemberService companyMemberService;
	
	/**
	 * 
	 * @功能描述 查询所有企业领导班子成员信息
	 * @time 2018年10月26日下午3:19:30
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/queryCompanyMemberList")
	@ResponseBody
	public Object queryCompanyMemberList(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> list = companyMemberService.queryCompanyMemberList(param);	
		return new Result(list);
	}

	/**
	 * 根据会议类型查询关联的领导班子人员
	 * @param request
	 * @return
	 * @by 李缝兴
	 */
	@RequestMapping("/queryCompanyMemberBymeetingType")
	@ResponseBody
	public List<Map<String, Object>> queryCompanyMemberBymeetingType(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> list = companyMemberService.queryCompanyMemberBymeetingType(param);
		return list;
	}
}
