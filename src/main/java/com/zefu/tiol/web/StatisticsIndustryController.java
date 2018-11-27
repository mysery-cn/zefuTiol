package com.zefu.tiol.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yr.gap.common.plugin.Result;
import com.zefu.tiol.pojo.StatisticsIndustry;
import com.zefu.tiol.service.StatisticsIndustryService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @功能描述: TODO 行业信息获取
 * @创建人 ：杨洋
 * @创建时间：2018年10月25日 上午16:23 @版本信息：V1.0
 */
@Controller
@RequestMapping("/statistics/industry")
public class StatisticsIndustryController {

	@Resource(name = "statisticsIndustryService")
	StatisticsIndustryService statisticsIndustryService;
	
	@RequestMapping("/queryList")
	@ResponseBody
	public Object queryList(HttpServletRequest request) throws Exception {
		List<StatisticsIndustry> statisticsIndustryList = statisticsIndustryService.queryIndustryList();

		// 4.定义返回内容
		JSONObject resultJson = new JSONObject();
		JSONArray idArray = new JSONArray();
		JSONArray nameArray = new JSONArray();

		// 5.遍历查询结果，返回三个数组，方便展示
		for (StatisticsIndustry statistics : statisticsIndustryList) {
			idArray.add(statistics.getInstId());
			nameArray.add(statistics.getInstName());
		}
		resultJson.put("idArray", idArray);
		resultJson.put("nameArray", nameArray);
		return new Result(resultJson);
	}
}
