package com.zefu.tiol.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.yr.gap.dal.plugin.child.Page;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yr.gap.common.plugin.Result;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.common.util.StringUtils;
import com.zefu.tiol.service.CatalogService;

/**
 * 
 * @功能描述 事项目录
 * @time 2018年10月26日下午3:18:21
 * @author Super
 *
 */
@Controller
@RequestMapping("/catalog")
public class CatalogController {

	@Resource(name = "catalogService")
	private CatalogService catalogService;
	
	/**
	 * 
	 * @功能描述 查询一级事项目录信息
	 * @time 2018年10月26日下午3:18:49
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/queryCatalogLevel")
	@ResponseBody
	public Object queryCatalogLevel(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> list = catalogService.queryCatalogLevel(param);	
		return new Result(list);
	}
	
	/**
	   * @功能描述: TODO 查询事项列表By树形结构
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年10月27日 上午10:42:58
	 */
	@RequestMapping("/queryCatalogTree")
	@ResponseBody
	public Object queryCatalogTree(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Map<String, Object> data = catalogService.queryCatalogTree(param);	
		return new Result(data);
	}
	
	/**
	 * 
	 * @功能描述 查询大额度资金运作事项二级事项目录信息
	 * @time 2018年10月31日上午10:52:00
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/queryCatalogFLevel")
	@ResponseBody
	public Object queryCatalogFLevel(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> list = catalogService.queryCatalogFLevel(param);	
		return new Result(list);
	}
	
	/**
	   * @功能描述: TODO 平台-事项目录树查询
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月1日 下午2:08:29
	 */
	@RequestMapping("/queryUmsCatalogList")
	@ResponseBody
	public Object queryUmsCatalogList(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		if(StringUtils.isBlank(request.getParameter("pageSize"))){
			if(("parent").equals(param.get("parentCode").toString())){
				List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("navCode", "ROOT");
				data.put("navId", "ROOT");
				data.put("navLevel", "0");
				data.put("navName", "事项目录");
				data.put("childUrl", "{URLNodeData}");
				list.add(data);
				return new Result(list);
			}else{
				List<Map<String, Object>> list = catalogService.queryUmsCatalogList(param);	
				return new Result(list);
			}
		}else{
			Page<Map<String,Object>> page =CommonUtil.getPageFromRequest(request);
			List<Map<String, Object>> list = catalogService.queryUmsCatalogListByPage(param,page);
			int totalCount = catalogService.queryUmsCatalogListByPageCount(param);
			page.setData(list);
			page.setTotalCount(totalCount);
			return page;
		}
		
	}
	
	/**
	   * @功能描述: TODO 保存事项目录
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月1日 下午2:59:44
	 */
	@RequestMapping("/saveCatalog")
	@ResponseBody
	public Object saveCatalog(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		catalogService.saveCatalog(param);	
		return new Result();
	}
	
	/**
	   * @功能描述: TODO 修改事项目录
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月1日 下午2:59:44
	 */
	@RequestMapping("/updateCatalog")
	@ResponseBody
	public Object updateCatalog(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		catalogService.updateCatalog(param);	
		return new Result();
	}
	
	/**
	   * @功能描述: TODO 删除事项目录
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月1日 下午2:59:44
	 */
	@RequestMapping("/deleteCatalog")
	@ResponseBody
	public Object deleteCatalog(HttpServletRequest request) throws Exception {
		Map<String,Object> parameter = CommonUtil.getParameterFromRequest(request);
		parameter.put("ids",(parameter.get("ids")+"").split(","));
		catalogService.deleteCatalog(parameter);	
		return new Result();
	}
	
	/**
	   * @功能描述: TODO 查询事项目录
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月1日 下午2:59:44
	 */
	@RequestMapping("/queryCatalog")
	@ResponseBody
	public Object queryCatalog(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Map<String, Object> detail = catalogService.queryCatalog(param);	
		return new Result(detail);
	}
	
	/**
	   * @功能描述: TODO 判断用户是否有权限编辑
	   * @参数: @param request
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月1日 下午7:31:05
	 */
	@RequestMapping("/queryCatalogRole")
	@ResponseBody
	public Object queryCatalogRole(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		int detail = catalogService.queryCatalogRole(param);	
		return new Result(detail);
	}
	
	/**
	   * @功能描述: TODO 查询所有事项目录To表格
	   * @参数: @param request
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月13日 下午7:48:04
	 */
	@RequestMapping("/queryTableCatalogMessage")
	@ResponseBody
	public Object queryTableCatalogMessage(HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Map<String, Object> detail = catalogService.queryTableCatalogMessage(param);	
		return new Result(detail);
	}
	
}
