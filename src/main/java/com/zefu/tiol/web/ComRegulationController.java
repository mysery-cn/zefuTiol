package com.zefu.tiol.web;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.yr.gap.bam.service.IBizDataService;
import com.yr.gap.base.util.GapGlobal;
import com.yr.gap.common.core.LoginUser;
import com.yr.gap.common.plugin.Result;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.pojo.RegulationVote;
import com.zefu.tiol.service.AttachmentService;
import com.zefu.tiol.service.ComRegulationService;
import com.zefu.tiol.util.FileUtils;
import com.zefu.tiol.util.excel.ExcelUtil;
import com.zefu.tiol.vo.AnlizedResult;
import com.zefu.tiol.vo.TemplateType;
import com.zefu.tiol.vo.TiolRegulationVO;

/**
 * 
 * @功能描述 企业版-制度信息
 * @time 2018年10月26日下午3:19:56
 * @author Super
 *
 */
@Controller
@RequestMapping("/com_regulation")
public class ComRegulationController {

	@Resource(name = "comRegulationService")
	private ComRegulationService comRegulationService;
	
	@Autowired
	private AttachmentService attachmentService;
	
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
		List<Map<String, Object>> list = comRegulationService.queryRegulationList(param);
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
        List<Map<String, Object>> list = comRegulationService.queryListByPage(parameter,page);
        int totalCount = comRegulationService.getTotalCount(companyId);
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
        List<Map<String, Object>> list = comRegulationService.querRegulationPageList(parameter,page);
        //总数
        int totalCount = comRegulationService.querRegulationPageListCount(parameter);
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
		List<Map<String, Object>> list = bizDataService.get("tiol_regulation_type", new HashMap<String, Object>());
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
		Map<String, Object> detail = comRegulationService.queryRegulationDetail(parameter);
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
		comRegulationService.saveRegulationType(param);
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
		comRegulationService.updateRegulationType(param);
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
		comRegulationService.deleteRegulationType(param);
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
		Map<String, Object> detail = comRegulationService.queryRegulationTypeDetail(param);
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
		List<Map<String, Object>> list = comRegulationService.queryRegulationTypeListByPage(param,page);
		int totalCount = comRegulationService.queryRegulationTypeTotalCount(param);
		page.setData(list);
		page.setTotalCount(totalCount);
		return page;
	}

	@RequestMapping("/addRegulation")
	public String toRegualtionRegisterJsp(HttpServletRequest request){
		String regulationId = UUID.randomUUID().toString().replace("-", "");
		request.setAttribute("regulationId", regulationId);
		request.setAttribute("status", "1");
		return "/com_regulation/regulation_update.jsp";
	}
	
	/**
	 * 
	 * @功能描述 获取投票表决方式
	 * @time 2018年11月16日上午9:16:23
	 * @param request
	 * @return
	 * @author 李晓军
	 *
	 */
	@RequestMapping("/queryVoteModelList")
	@ResponseBody
	public Object queryVoteModelList(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        List<Map<String, Object>> list = this.comRegulationService.queryVoteModelList(param);
		return new Result(list);
	}
	
	/**
	   * @功能描述: TODO 删除制度
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：李晓军
	   * @创建时间：2018年11月16日 下午3:57:31
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		param.put("ids",(param.get("ids")+"").split(","));
		comRegulationService.delete(param);
		return new Result();
	}
	
	/**
	   * @功能描述: TODO 还原删除制度
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：李晓军
	   * @创建时间：2018年11月16日 下午3:57:31
	 */
	@RequestMapping("/restore")
	@ResponseBody
	public Object restore(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		param.put("ids",(param.get("ids")+"").split(","));
		comRegulationService.restore(param);
		return new Result();
	}
	
	/**
	   * @功能描述: TODO 彻底删除制度
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：李晓军
	   * @创建时间：2018年11月16日 下午3:57:31
	 */
	@RequestMapping("/remove")
	@ResponseBody
	public Object remove(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		param.put("ids",(param.get("ids")+"").split(","));
		comRegulationService.remove(param);
		return new Result();
	}

    /**
     * 保存制度
     *
     * @param request
     * @param regulationVoteStr
     * @return
     * @throws Exception
     * @author 刘效、李晓军
     */
	@RequestMapping("/saveRegulation")
	@ResponseBody
	public JSONObject saveRegulation(HttpServletRequest request, String regulationVoteStr, String isUpdate) throws Exception{
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		JSONObject result = new JSONObject();
		
		boolean saveVoteSuccessFlag = false;
		String regulationId = (String) param.get("regulationId");
		String companyId = CommonUtil.getLoginUser(request).getCompanyId();
		//param.put("regulationId", regulationId);
		param.put("companyId", companyId);

		if (StringUtils.isNotEmpty(regulationVoteStr)) {
			String[] regulationVoteList = regulationVoteStr.split(",");
			if (regulationVoteList.length > 0) {
				RegulationVote regulationVote;
				String[] item_mode = new String[2];
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
				comRegulationService.deleteRegulationVote(regulationId);
			    saveVoteSuccessFlag = comRegulationService.batchInsertRegulationVote(list) == regulationVoteList.length;
			}
		}

		int saveRegCnt = 0;

		if(StringUtils.isNotEmpty(isUpdate) && "1".equals(isUpdate)){
			saveRegCnt = comRegulationService.updateRegulation2(param);
		}else {
			saveRegCnt = comRegulationService.insertRegulation(param);
		}

		if (saveRegCnt == 1) {
			result.put("result", "success");
		} else {
			result.put("result", "fail");
		}

		return result;
	}
	
	/**
	 * @功能描述:上传文件窗口
	 * @参数: @param request
     * @参数: @return
     * @创建人 ：李晓军
     * @创建时间：2018年11月14日 下午13:21:51
	 */
	@RequestMapping("/toFileImportPage")
	public String toFileImportPage(HttpServletRequest request)throws Exception {
		//补传任务id
		//如果是做补传，需要从补传页面跳过来，带过来补传任务id，并把该id带到jsp页面的隐藏域
		//如果不是做补传，不需要此参数
		String fileType = request.getParameter("fileType");
		String url = request.getParameter("url");
		request.setAttribute("url", url);
		request.setAttribute("fileType", fileType);
		return "/com_regulation/regulation_fileImport.jsp";
	}
	
	/**
	 * @功能描述:上传文件窗口
	 * @参数: @param request
     * @参数: @return
     * @创建人 ：李晓军
     * @创建时间：2018年11月14日 下午13:21:51
	 */
	@RequestMapping("/attachmentImport")
	public String attachmentImport(HttpServletRequest request)throws Exception {
		//补传任务id
		//如果是做补传，需要从补传页面跳过来，带过来补传任务id，并把该id带到jsp页面的隐藏域
		//如果不是做补传，不需要此参数
		String fileType = request.getParameter("fileType");
		String url = request.getParameter("url");
		String oneFile = request.getParameter("oneFile");
		request.setAttribute("url", url);
		request.setAttribute("fileType", fileType);
		request.setAttribute("oneFile", oneFile);
		return "/com_regulation/regulation_attachmentImport.jsp";
	}
	
	/**
	   * @功能描述: 制度Excel导入
	   * @参数: @param request
	   * @参数: @return
	   * @参数: @throws Exception
	   * @创建人 ：李晓军
	   * @创建时间：2018年11月2日 下午8:48:29
	 */
	@RequestMapping(value = "/importRegulation", produces = "text/plain; charset=utf-8") 
	@ResponseBody
	public String importTiolRegulation(Model model,@RequestParam("importFile")MultipartFile importFile,HttpServletRequest request) throws Exception {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		JSONObject result=new JSONObject();
		File localFile=null;
		try{
			//System.out.println("文件名： "+importFile.getOriginalFilename());
			String fileName=importFile.getOriginalFilename();
			int enc=fileName.lastIndexOf(".");
			if(enc!=-1) {
				String  ext=fileName.substring(enc+1);
			    ext=ext.toLowerCase();
			    System.out.println(ext);
				if(!"xls".equals(ext)  && !"xlsx".equals(ext)) {
					result.put("result", "请打开excel格式文件");
					return result.toJSONString();
				}
			}else {
				result.put("result", "请打开excel格式文件");
				return result.toJSONString();
			}
			if(null!=importFile) {
				byte[] data=importFile.getBytes();
				localFile=new  File(fileName);
				org.apache.commons.io.FileUtils.writeByteArrayToFile(localFile, data);
				AnlizedResult anlizedResult=ExcelUtil.anlized(localFile.getAbsolutePath(), TemplateType.regulation);
				if(anlizedResult.isSuccess() && null != anlizedResult.getData()) {
					Object resultData=anlizedResult.getData();
					if(resultData == null) {
						result.put("result", "数据导入异常");
						return result.toJSONString();
					}
					List<TiolRegulationVO> list=(List<TiolRegulationVO>)resultData;
					if(list.size()==0) {
						result.put("result", "没有找到数据，请查看excle第二列是否有数据");
						return result.toJSONString();
					}
					
					String ret=this.comRegulationService.saveImportRegulation(list,param);
					if(null!=ret && !"".equals(ret)) {
						ret=ret.replaceAll("\n", "<br>");
//						System.out.println("异常信息"+ret);
						result.put("result", ret);
					}else {
						result.put("result", "success");
					}
				}else {
					String errorInfo=anlizedResult.getErrorInfo();
					if(null!=errorInfo )   errorInfo=errorInfo.replaceAll("\n", "<br>");
					result.put("result",errorInfo );
				}
//				System.out.println(localFile.getAbsolutePath());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "数据导入异常:");
			return result.toJSONString();
		}finally {
			if(null!=localFile) {
				localFile.delete();//删除上传的excel文件
			}
		}
		return result.toJSONString();
	}

	/**
	 * @功能描述: TODO 查询制度
	 * @参数: @param regulationID
	 * @参数: @return
	 * @参数: @throws Exception
	 * @创建人 ：刘效
	 * @创建时间：2018年11月17日 下午21:17:29
	 */
	@RequestMapping("/queryRegulationById")
	@ResponseBody
	public Map<String, Object> queryRegulationById(@RequestParam String regulationId){
		Map<String, Object> map = new HashMap<String, Object>();
		if(regulationId != null && !"".equalsIgnoreCase(regulationId)){
			map = comRegulationService.queryRegulationById(regulationId);
			map.put("itemVoteList", comRegulationService.queryItemVotesByRegulationId(regulationId));
		}
		return map;
	}
	
	/**
	 * 上传附件
	 * 
	 * @return
	 * @创建人 ：李晓军
	 * @创建时间：2018年11月14日
	 */
	@RequestMapping(value = "/uploadFile", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String uploadFile(@RequestParam(value="importFile",required=false) MultipartFile importFile, HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		try {
			Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
			LoginUser user = CommonUtil.getLoginUser(request);
			// 上传文件至FS服务
			String fileId = FileUtils.uploadMultipartFileToFs(importFile);
			param.put("fileId", fileId);
			param.put("attachmentId", fileId);
			param.put("attachmentName", importFile.getOriginalFilename());
			param.put("attachmentType", "制度佐证材料");
			param.put("businessId", param.get("regulationId"));
			param.put("createUser", user.getUserName());
			attachmentService.insertAttachment(param);
			result.put("result", "success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", e);
		}
		return result.toJSONString();
	}
	
	/**
	 * 上传单个附件
	 * 
	 * @return
	 * @创建人 ：李晓军
	 * @创建时间：2018年11月14日
	 */
	@RequestMapping(value = "/uploadOneFile", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String uploadOneFile(@RequestParam("importFile") MultipartFile importFile, HttpServletRequest request) throws Exception {
		JSONObject result = new JSONObject();
		try {
			Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
			LoginUser user = CommonUtil.getLoginUser(request);
			// 上传文件至FS服务
			String fileId = FileUtils.uploadMultipartFileToFs(importFile);
			param.put("fileId", fileId);
			param.put("attachmentId", fileId);
			param.put("attachmentName", importFile.getOriginalFilename());
			param.put("attachmentType", "制度正式文件");
			param.put("businessId", param.get("regulationId"));
			param.put("createUser", user.getUserName());
			attachmentService.insertAttachment(param);
			result.put("result", "success");
			result.put("fileId", fileId);
			result.put("fileName", importFile.getOriginalFilename());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", e.toString());
		}
		return result.toJSONString();
	}

	/**
	 * 查询关联附件列表
	 * @return
	 * @创建人 ：李晓军
	 * @创建时间：2018年11月14日
	 */
	@RequestMapping("/getFileById")
	@ResponseBody
	public List<Map<String,Object>> getFileById(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		String regulationId = param.get("regulationId")+"";
		if(regulationId == null || "".equalsIgnoreCase(regulationId)){
			return null;
		}
		return attachmentService.getFileById(regulationId);
	}

	/**
	 * 删除附件关联
	 * @return
	 * @创建人 ：李晓军
	 * @创建时间：2018年11月14日
	 */
	@RequestMapping("/deleteFileById")
	@ResponseBody
	public Object deleteFileById(HttpServletRequest request){
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		param.put("ids",(param.get("ids")+"").split(","));
		attachmentService.deleteFileByFileId(param);
		return new Result();
	}
}
