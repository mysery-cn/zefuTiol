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
import com.zefu.tiol.service.StatisticsItemService;

/**
   * @工程名 : szyd
   * @文件名 : StatisticsItemController.java
   * @工具包名：com.zefu.tiol.web
   * @功能描述: TODO	事项清单统计
   * @创建人 ：林鹏
   * @创建时间：2018年10月25日 上午8:52:48
   * @版本信息：V1.0
 */
@Controller
@RequestMapping("/statistics/item")
public class StatisticsItemController {
	
	@Resource(name = "statisticsItemService")
	private StatisticsItemService itemService;
	
	
	/**
	   * @功能描述: TODO 查询事项清单统计数据
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月25日 上午9:05:38
	 */
	@RequestMapping("/queryItemDetail")
	@ResponseBody
	public Object queryItemDetail(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        //查询数据
        Map<String, Object> detail = itemService.queryItemDetail(parameter);
		return new Result(detail);
	}
	
	/**
	   * @功能描述: TODO 行业事项汇总信息
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月26日 下午2:38:44
	 */
	@RequestMapping("/queryIndustryItemList")
	@ResponseBody
	public Page<Map<String, Object>> queryIndustryItemList(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		//获取分页信息
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        //查询数据
        List<Map<String, Object>> detail = itemService.queryIndustryItemList(parameter,page);
        //查询总数
        int totalCount = itemService.queryIndustryItemCount(parameter);
        page.setTotalCount(totalCount);
        page.setData(detail);
		return page;
	}
	
	
	/**
	   * @功能描述: TODO 根据事项查询事项清单By企业
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月27日 上午11:37:52
	 */
	@RequestMapping("/queryItemListByCatalog")
	@ResponseBody
	public Page<Map<String, Object>> queryItemListByCatalog(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		//获取分页信息
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        //查询数据
        List<Map<String, Object>> detail = itemService.queryItemListByCatalog(parameter,page);
        //查询总数
        int totalCount = itemService.queryItemListByCatalogCount(parameter);
        page.setTotalCount(totalCount);
        page.setData(detail);
		return page;
	}
	
	/**
	 * 
	 * @功能描述 分页查询各企业事项清单信息
	 * @time 2018年10月31日上午11:50:00
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/queryItemSubjectByPage")
	@ResponseBody
	public Object queryItemSubjectByPage(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        //查询数据
        List<Map<String, Object>> list = itemService.queryItemSubjectByPage(param, page);
        int totalCount = itemService.getItemSubjectCount(param);
        page.setTotalCount(totalCount);
        page.setData(list);
		return page;
	}
	
	/**
	 * 
	 * @功能描述 大额度资金二级目录议题统计
	 * @time 2018年11月1日下午4:05:07
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/queryCatalogSubjectFDetail")
	@ResponseBody
	public Object queryCatalogSubjectFDetail(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        //查询数据
        Map<String, Object> detail = itemService.queryCatalogSubjectFDetail(param);
		return new Result(detail);
	}
	
	/**
	 * 
	 * @功能描述 大额度资金一级目录事项清单统计
	 * @time 2018年11月1日下午4:05:25
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/queryCatalogItemFDetail")
	@ResponseBody
	public Object queryCatalogItemFDetail(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        //查询数据
        Map<String, Object> detail = itemService.queryCatalogItemFDetail(param);
		return new Result(detail);
	}
	
	/**
	 * 
	 * @功能描述 分页查询各企业事项清单信息
	 * @time 2018年10月31日上午11:28:22
	 * @author Super
	 * @param param
	 * @return
	 *
	 */
	@RequestMapping("/queryCatalogItemByPage")
	@ResponseBody
	public Object queryCatalogItemByPage(HttpServletRequest request) {
		//提供获取接口传参进来的方法
        Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        //查询数据
        List<Map<String, Object>> list = itemService.queryCatalogItemByPage(param, page);
        int totalCount = itemService.getCatalogItemCount(param);
        page.setTotalCount(totalCount);
        page.setData(list);
		return page;
	}

	/**
	   * @功能描述:  查询所有企业事项清单
	   * @创建人 ：林鹏
	   * @创建时间：2018/11/18 15:02
	*/
	@RequestMapping("/queryItemList")
	@ResponseBody
	public Page<Map<String, Object>> queryItemList(HttpServletRequest request) {
		//提供获取接口传参进来的方法
		Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		//获取分页信息
		Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
		//查询数据
		List<Map<String, Object>> detail = itemService.queryItemList(parameter,page);
		//查询总数
		int totalCount = itemService.queryItemListTotalCount(parameter);
		page.setTotalCount(totalCount);
		page.setData(detail);
		return page;
	}




}
