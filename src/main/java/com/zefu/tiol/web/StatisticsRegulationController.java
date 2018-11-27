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
import com.zefu.tiol.pojo.StatisticsRegulation;
import com.zefu.tiol.service.StatisticsRegulationService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @功能描述: TODO 制度分类统计
 * @创建人 ：林长怀
 * @创建时间：2018年10月25日 上午8:53:01 @版本信息：V1.0
 */
@Controller
@RequestMapping("/statistics/regulation")
public class StatisticsRegulationController {

	@Resource(name = "statisticsRegulationService")
	StatisticsRegulationService statisticsRegulationService;

	/**
	 * 根据制度类型汇总
	 * 
	 * @param industryId
	 *            行业Id
	 * @return Result
	 */
	@RequestMapping("/queryRegulationList")
	@ResponseBody
	public Object staticRegulationList(String industryId, HttpServletRequest request) {
		// 1.根据制度类型汇总
		List<StatisticsRegulation> RegulationlistBySource = statisticsRegulationService.queryListByType(industryId);
		JSONObject resultJson = new JSONObject();
		JSONArray idArray = new JSONArray();
		JSONArray nameArray = new JSONArray();
		JSONArray quantityArray = new JSONArray();
		if(RegulationlistBySource != null && RegulationlistBySource.size() > 0){
			for(StatisticsRegulation statistics : RegulationlistBySource) {
				idArray.add(statistics.getTypeId());
				nameArray.add(statistics.getTypeName());
				quantityArray.add(statistics.getQuantity());
			}
			resultJson.put("idArray", idArray);
			resultJson.put("nameArray", nameArray);
			resultJson.put("quantityArray", quantityArray);
		}else {
			resultJson.put("idArray", "");
			resultJson.put("nameArray", "");
			resultJson.put("quantityArray", "");
		}
		return new Result(resultJson);
	}
	
	/**
	   * @功能描述: TODO 查询规章制度建设情况
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月25日 下午8:06:59
	 */
	@RequestMapping("/queryRegulationConstructionDetail")
	@ResponseBody
	public Object queryRegulationConstructionDetail(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        //查询数据
        Map<String, Object> detail = statisticsRegulationService.queryRegulationConstructionDetail(parameter);
		return new Result(detail);
	}
	
	

}
