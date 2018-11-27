package com.zefu.tiol.web;

import com.yr.gap.bam.service.IBizDataService;
import com.yr.gap.base.util.GapGlobal;
import com.yr.gap.common.core.LoginUser;
import com.yr.gap.common.plugin.Result;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.pojo.RegulationVote;
import com.zefu.tiol.service.RegulationService;
import com.zefu.tiol.util.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 
 * @功能描述 制度信息
 * @time 2018年10月26日下午3:19:56
 * @author Super
 *
 */
@Controller
@RequestMapping("/regulation")
public class RegulationController {

	@Resource(name = "regulationService")
	private RegulationService regulationService;
	
	@Resource(name = "bizDataService")
	private IBizDataService bizDataService;
	
	
	/**
	 * 
	 * @功能描述 查询制度信息
	 * @time 2018年10月26日下午3:20:01
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/queryRegulationList")
	@ResponseBody
	public Object queryRegulationList(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> list = regulationService.queryRegulationList(param);
		return new Result(list);
	}
	
	@RequestMapping("/regulationDetail")
	public @ResponseBody ModelAndView regulationDetail(String companyId, HttpServletRequest request) {
		return new ModelAndView("/statistics/leader/leader_regulation_company.jsp").addObject("companyId", companyId);
	}
	
	@RequestMapping("/querPageList")
	@ResponseBody
	public Page<Map<String, Object>> querPageList(String companyId,HttpServletRequest request) throws Exception {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		//获取分页信息
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        List<Map<String, Object>> list = regulationService.queryListByPage(parameter,page);
        int totalCount = regulationService.getTotalCount(companyId);
        page.setTotalCount(totalCount);
        page.setData(list);
        //封装的返回方法
        return page;
	}
	
	/**
	   * @功能描述: TODO 平台查询制度信息列表
	   * @参数: @param request
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午3:28:00
	 */
	@RequestMapping("/querRegulationPageList")
	@ResponseBody
	public Page<Map<String, Object>> querPageList(HttpServletRequest request) throws Exception {
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		//获取分页信息
        Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        //登陆信息
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(GapGlobal.SESSION.GAP_SSO_SESSION_USERNAME);
        //查询数据
        parameter.put("instID", loginUser.getInstId());
        List<Map<String, Object>> list = regulationService.querRegulationPageList(parameter,page);
        //总数
        int totalCount = regulationService.querRegulationPageListCount(parameter);
        page.setTotalCount(totalCount);
        page.setData(list);
        //封装的返回方法
        return page;
	}
	
	/**
	   * @throws Exception 
	   * @功能描述: TODO 查询制度分类
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午5:07:12
	 */
	@RequestMapping("/queryRegulationTypeList")
	@ResponseBody
	public Object queryRegulationTypeList(HttpServletRequest request) throws Exception{
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> list = regulationService.queryRegulationTypeList(parameter);
		return new Result(list);
	}
	
	/**
	   * @功能描述: TODO 查询制度详情
	   * @参数: @param request
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午6:40:52
	 */
	@RequestMapping("/queryRegulationDetail")
	@ResponseBody
	public Object queryRegulationDetail(HttpServletRequest request) throws Exception{
		//提供获取接口传参进来的方法
        Map<String, Object> parameter = CommonUtil.getParameterFromRequest(request);
        //查询数据
		Map<String, Object> detail = regulationService.queryRegulationDetail(parameter);
		return new Result(detail);
	}
	
	/**
	   * @功能描述: TODO 保存制度分类
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午8:44:17
	 */
	@RequestMapping("/saveRegulationType")
	@ResponseBody
	public Object saveRegulationType(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		regulationService.saveRegulationType(param);
		return new Result();
	}
	
	/**
	   * @功能描述: TODO 修改制度分类
	   * @参数: @param request
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午8:48:29
	 */
	@RequestMapping("/updateRegulationType")
	@ResponseBody
	public Object updateRegulationType(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		regulationService.updateRegulationType(param);
		return new Result();
	}
	
	/**
	   * @功能描述: TODO 删除制度分类
	   * @参数: @param request
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午8:48:29
	 */
	@RequestMapping("/deleteRegulationType")
	@ResponseBody
	public Object deleteRegulationType(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		param.put("ids",(param.get("ids")+"").split(","));
		regulationService.deleteRegulationType(param);
		return new Result();
	}
	
	/**
	   * @功能描述: TODO 查询制度分类详情
	   * @参数: @param request
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午8:48:29
	 */
	@RequestMapping("/queryRegulationTypeDetail")
	@ResponseBody
	public Object queryRegulationTypeDetail(HttpServletRequest request) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Map<String, Object> detail = regulationService.queryRegulationTypeDetail(param);
		return new Result(detail);
	}
	
	/**
	   * @throws Exception 
	   * @功能描述: TODO 查询制度分类
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 下午5:07:12
	 */
	@RequestMapping("/queryRegulationTypeListByPage")
	@ResponseBody
	public Object queryRegulationTypeListByPage(HttpServletRequest request) throws Exception{
		Page<Map<String,Object>> page =CommonUtil.getPageFromRequest(request);
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> list = regulationService.queryRegulationTypeListByPage(param,page);
		int totalCount = regulationService.queryRegulationTypeTotalCount(param);
		page.setData(list);
		page.setTotalCount(totalCount);
		return page;
	}


    /**
     * 保存制度
     *
     * @param request
     * @param regulationVoteStr
     * @param auditFile
     * @param file
     * @return
     * @throws Exception
     */
	@RequestMapping("/saveRegulation")
	public ModelAndView saveRegulation(HttpServletRequest request, String regulationVoteStr, @RequestParam(value = "auditFile",required = false) MultipartFile auditFile, @RequestParam(value = "file",required = false) MultipartFile file) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);

		String auditFileId = "";
		String fileId = "";
		String regulationId = UUID.randomUUID().toString().replace("-", "");
		String companyId = CommonUtil.getLoginUser(request).getCompanyId();

		if(auditFile != null && !auditFile.isEmpty()){
			auditFileId = FileUtils.uploadMultipartFileToFs(auditFile);
		}
		if(file != null && !file.isEmpty()) {
			fileId = FileUtils.uploadMultipartFileToFs(file);
		}

		param.put("regulationId", regulationId);
		param.put("auditFile", auditFileId);
		param.put("file", fileId);
		param.put("companyId", companyId);

		if (StringUtils.isNotEmpty(regulationVoteStr)) {
			String[] regulationVoteList = regulationVoteStr.split(",");
			if (regulationVoteList.length > 0) {
				RegulationVote regulationVote;
				String[] item_mode;
				ArrayList<RegulationVote> list = new ArrayList<RegulationVote>();
				for (String str : regulationVoteList) {
					item_mode = str.split("/");
					regulationVote = new RegulationVote();
					regulationVote.setVoteId(UUID.randomUUID().toString().replace("-", ""));
					regulationVote.setRegulationId(regulationId);
					regulationVote.setItemId(item_mode[0]);
					regulationVote.setModeId(item_mode[1]);
					list.add(regulationVote);
				}
				regulationService.batchInsertRegulationVote(list);
			}
		}

		regulationService.insertRegulation(param);

		return new ModelAndView("/regulation/regulation_register.jsp");
	}
	
	/**
	   * @功能描述: 上报制度
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 林长怀
	   * @创建时间：2018年11月14日 下午15:09:22
	 */
	@RequestMapping("/uploadRegulation")
	@ResponseBody
	public Object uploadRegulation(HttpServletRequest request) {
		String url = "http://"+request.getServerName() + ":"+request.getServerPort();
		if(!StringUtils.isEmpty(request.getContextPath())&&!"/".equals(request.getContextPath())) {
			url += "/"+request.getContextPath();
		}
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		param.put("ids",(param.get("ids")+"").split(","));
		LoginUser currentUser = (LoginUser) param.get("CURRENT_USER");
		String companyId = currentUser.getCompanyId();
		String userName = currentUser.getUserName();
		String errorMsg = regulationService.uploadRegulation(param,companyId,url,userName);
		if(StringUtils.isEmpty(errorMsg)) {
			return new Result(1,""); 
		}else {
			return new Result(0,errorMsg);
		}
	}
}
