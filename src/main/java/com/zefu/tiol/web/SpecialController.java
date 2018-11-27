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
import com.zefu.tiol.service.SpecialService;

/**
   * @工程名 : szyd
   * @文件名 : SpecialController.java
   * @工具包名：com.zefu.tiol.web
   * @功能描述: TODO 专项任务类
   * @创建人 ：林鹏
   * @创建时间：2018年11月4日 下午3:18:50
   * @版本信息：V1.0
 */

@Controller
@RequestMapping("/special")
public class SpecialController {
	
	@Resource(name = "specialService")
	private SpecialService specialService;
	
	/**
	   * @功能描述: TODO 平台-任务来源查询
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月4日 下午2:38:38
	 */
	@RequestMapping("/querySpecialList")
	@ResponseBody
	public Object querySpecialList(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
		List<Map<String, Object>> list = specialService.querySpecialList(param,page);
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
	@RequestMapping("/saveSpecial")
	@ResponseBody
	public Object saveSpecial(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		specialService.saveSpecial(param);
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
	@RequestMapping("/updateSpecial")
	@ResponseBody
	public Object updateSpecial(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		specialService.updateSpecial(param);
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
	@RequestMapping("/deleteSpecial")
	@ResponseBody
	public Object deleteSpecial(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		param.put("ids",(param.get("ids")+"").split(","));
		specialService.deleteSpecial(param);
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
	@RequestMapping("/querySpecialDetail")
	@ResponseBody
	public Object querySpecialDetail(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Map<String, Object> detail = specialService.querySpecialDetail(param);
		return new Result(detail);
	}
	
	
	
	
	
}
