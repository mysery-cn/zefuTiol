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
import com.zefu.tiol.service.StatisticsDirectorateService;

/**
 * 
 * @功能描述 董事会决策议题统计
 * @time 2018年10月24日下午3:09:20
 * @author Super
 *
 */
@Controller
@RequestMapping("/statistics/directorate")
public class StatisticsDirectorateController {

	@Resource(name = "statisticsDirectorateService")
	private StatisticsDirectorateService statisticsDirectorateService;
	
	/**
	 * 
	 * @功能描述 查询是否经过董事会决策统计信息
	 * @time 2018年10月24日下午3:09:29
	 * @author Super
	 * @param request
	 * @return
	 * @throws Exception
	 * 
	 */
	@RequestMapping("/queryStatisticsDirectorateVia")
	@ResponseBody
	public Object queryStatisticsDirectorateVia(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Map<String, Object> detail = statisticsDirectorateService.queryStatisticsDirectorateVia(param);
		return new Result(detail);
	}
	
}
