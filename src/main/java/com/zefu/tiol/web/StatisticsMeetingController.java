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
import com.zefu.tiol.service.StatisticsMeetingService;

/**
   * @工程名 : szyd
   * @文件名 : StatisticsMeetingController.java
   * @工具包名：com.zefu.tiol.web
   * @功能描述: TODO 会议分类统计
   * @创建人 ：林鹏
   * @创建时间：2018年10月25日 上午9:42:14
   * @版本信息：V1.0
 */
@Controller
@RequestMapping("/statistics/meeting")
public class StatisticsMeetingController {
	
	@Resource(name = "statisticsMeetingService")
	private StatisticsMeetingService meetingService;
	
	
	/**
	   * @功能描述: TODO 查询会议分类统计结果
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月25日 上午9:50:59
	 */
	@RequestMapping("/queryMeetingDetail")
	@ResponseBody
	public Object queryMeetingDetail(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        //查询数据
        Map<String, Object> detail = meetingService.queryMeetingDetail(parameter);
		return new Result(detail);
	}
	
	
	/**
	   * @功能描述: TODO 查询当前年份决策会议情况
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月25日 下午4:57:50
	 */
	@RequestMapping("/queryDecisionMeetingDetail")
	@ResponseBody
	public Object queryDecisionMeetingDetail(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        //查询数据
        Map<String, Object> detail = meetingService.queryDecisionMeetingDetail(parameter);
		return new Result(detail);
	}
	
	/**
	   * @功能描述: TODO 查询当前年份决策议题情况
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月25日 下午5:39:33
	 */
	@RequestMapping("/queryDecisionSubjectDetail")
	@ResponseBody
	public Object queryDecisionSubjectDetail(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        //查询数据
        Map<String, Object> detail = meetingService.queryDecisionSubjectDetail(parameter);
		return new Result(detail);
	}
	
	/**
	 * 
	 * @功能描述 查询会议类型统计信息
	 * @time 2018年10月30日下午3:20:17
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/queryMeetingClassDetail")
	@ResponseBody
	public Object queryMeetingClassDetail(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        //查询数据
        Object detail = meetingService.queryMeetingClassDetail(parameter);
		return new Result(detail);
	}
	
	/**
	 * 
	 * @功能描述 分页查询会议信息
	 * @time 2018年10月30日下午3:20:49
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/queryMeetingByPage")
	@ResponseBody
	public Object queryMeetingByPage(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        //查询数据
        List<Map<String, Object>> list = meetingService.queryMeetingByPage(parameter, page);
        int totalCount = meetingService.getMeetingTotalCount(parameter);
        page.setTotalCount(totalCount);
        page.setData(list);
		return page;
	}
	
}
