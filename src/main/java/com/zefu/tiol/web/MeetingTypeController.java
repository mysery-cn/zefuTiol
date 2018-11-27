package com.zefu.tiol.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yr.gap.bam.service.IBizDataService;
import com.yr.gap.common.plugin.Result;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.service.MeetingTypeService;

/**
 * 
 * @功能描述 会议类型信息控制类
 * @time 2018年10月29日上午11:28:02
 * @author Super
 *
 */
@Controller
@RequestMapping("/meetingType")
public class MeetingTypeController {
	
	/**
	 * 此备注为ServiceImpl中配置的参数，如不要请用@Autowired
	 */
	@Resource(name = "meetingTypeService")
	private MeetingTypeService meetingTypeService;
	
	@Resource(name = "bizDataService")
	private IBizDataService bizDataService;
	
	
	/**
	 * 
	 * @功能描述 查询会议类型信息
	 * @time 2018年10月30日下午3:18:31
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/queryList")
	@ResponseBody
	public Object queryList(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        List<Map<String, Object>> list = meetingTypeService.queryList(param);
		return new Result(list);
	}
	
	
	/**
	   * @功能描述: TODO 保存会议分类
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午8:44:17
	 */
	@RequestMapping("/saveMeetingType")
	@ResponseBody
	public Object saveMeetingType(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		meetingTypeService.saveMeetingType(param);
		return new Result();
	}
	
	/**
	   * @功能描述: TODO 修改会议分类
	   * @参数: @param request
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午8:48:29
	 */
	@RequestMapping("/updateMeetingType")
	@ResponseBody
	public Object updateMeetingType(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		meetingTypeService.updateMeetingType(param);
		return new Result();
	}
	
	/**
	   * @功能描述: TODO 删除会议分类
	   * @参数: @param request
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午8:48:29
	 */
	@RequestMapping("/deleteMeetingType")
	@ResponseBody
	public Object deleteMeetingType(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		param.put("ids",(param.get("ids")+"").split(","));
		meetingTypeService.deleteMeetingType(param);
		return new Result();
	}
	
	/**
	   * @功能描述: TODO 查询会议分类详情
	   * @参数: @param request
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午8:48:29
	 */
	@RequestMapping("/queryMeetingTypeDetail")
	@ResponseBody
	public Object queryRegulationTypeDetail(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Map<String, Object> detail = meetingTypeService.queryMeetingTypeDetail(param);
		return new Result(detail);
	}
	
	/**
	   * @throws Exception 
	   * @功能描述: TODO 查询会议分类
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午5:07:12
	 */
	@RequestMapping("/queryMeetingTypeListByPage")
	@ResponseBody
	public Object queryMeetingTypeListByPage(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Page<Map<String,Object>> page =CommonUtil.getPageFromRequest(request);
		List<Map<String, Object>> list = meetingTypeService.queryMeetingTypeListByPage(param);
		page.setData(list);
		page.setTotalCount(list.size());
		return page;
	}

}
