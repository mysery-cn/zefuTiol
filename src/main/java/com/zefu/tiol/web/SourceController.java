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
import com.zefu.tiol.service.SourceService;

/**
 * 
 * @功能描述 任务来源信息控制类
 * @time 2018年10月29日上午11:28:25
 * @author Super
 *
 */
@Controller
@RequestMapping("/source")
public class SourceController {
	
	/**
	 * 此备注为ServiceImpl中配置的参数，如不要请用@Autowired
	 */
	@Resource(name = "sourceService")
	private SourceService sourceService;
	
	/**
	 * 
	 * @功能描述 查询任务来源信息
	 * @time 2018年10月30日下午3:19:44
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/queryList")
	@ResponseBody
	public Object queryList(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        List<Map<String, Object>> list = sourceService.queryList(param);
		return new Result(list);
	}
	
	/**
	   * @功能描述: TODO 平台-任务来源查询
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月4日 下午2:38:38
	 */
	@RequestMapping("/querySourceList")
	@ResponseBody
	public Object querySourceList(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        List<Map<String, Object>> list = sourceService.queryList(param);
        page.setTotalCount(list.size());
        page.setData(list);
		return page;
	}
	
	/**
	   * @功能描述: TODO 保存任务来源
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午8:44:17
	 */
	@RequestMapping("/saveSource")
	@ResponseBody
	public Object saveSource(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		sourceService.saveSource(param);
		return new Result();
	}
	
	/**
	   * @功能描述: TODO 修改任务来源
	   * @参数: @param request
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午8:48:29
	 */
	@RequestMapping("/updateSource")
	@ResponseBody
	public Object updateSource(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		sourceService.updateSource(param);
		return new Result();
	}
	
	/**
	   * @功能描述: TODO 删除任务来源
	   * @参数: @param request
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午8:48:29
	 */
	@RequestMapping("/deleteSource")
	@ResponseBody
	public Object deleteSource(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		param.put("ids",(param.get("ids")+"").split(","));
		sourceService.deleteSource(param);
		return new Result();
	}
	
	/**
	   * @功能描述: TODO 查询任务来源详情
	   * @参数: @param request
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午8:48:29
	 */
	@RequestMapping("/querySourceDetail")
	@ResponseBody
	public Object querySourceDetail(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Map<String, Object> detail = sourceService.querySourceDetail(param);
		return new Result(detail);
	}
	
}
