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
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.pojo.StatisticsCompany;
import com.zefu.tiol.service.StatisticsCompanyService;

/**
 * @功能描述: TODO 企业综合统计
 * @创建人 ：林鹏
 * @创建时间：2018年10月24日 上午9:23:01 @版本信息：V1.0
 */
@Controller
@RequestMapping("/statistics/company")
public class StatisticsCompanyController {

	/**
	 * 此备注为ServiceImpl中配置的参数，如不要请用@Autowired
	 */
	@Resource(name = "statisticsCompanyService")
	StatisticsCompanyService statisticsCompanyService;

	@RequestMapping("/queryList")
	@ResponseBody
	public List<StatisticsCompany> queryList(String industryId, HttpServletRequest request) {
		List<StatisticsCompany> list = statisticsCompanyService.queryCompanyList(industryId);
		return list;
	}
	
	/**
	 * 企业综合统计查询
	 * 
	 * @param industryId
	 *            行业Id
	 * @return Result
	 * @throws Exception 
	 */
	@RequestMapping("/querPageList")
	@ResponseBody
	public Page<Map<String, Object>> querPageList(String industryId,HttpServletRequest request) throws Exception {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		//获取分页信息
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        List<StatisticsCompany> list = statisticsCompanyService.queryListByPage(parameter,page);
        parameter.put("industryId", industryId);
        int totalCount = statisticsCompanyService.getTotalCount(parameter);
        page.setTotalCount(totalCount);
        page.setData(list);
        //封装的返回方法
        return page;
	}
	
	/**
	   * @功能描述: TODO 查询企业信息
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月29日 下午4:32:32
	 */
	@RequestMapping("/queryCompanyDetail")
	@ResponseBody
	public Object queryCompanyDetail(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        //查询数据
        Map<String, Object> detail = statisticsCompanyService.queryCompanyDetail(parameter);
		return new Result(detail);
	}
}
