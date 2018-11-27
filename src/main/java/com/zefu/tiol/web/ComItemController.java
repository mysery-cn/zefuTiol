package com.zefu.tiol.web;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.yr.gap.base.util.GapGlobal;
import com.yr.gap.common.core.LoginUser;
import com.yr.gap.common.plugin.Result;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.service.ComItemService;
import com.zefu.tiol.util.excel.ExcelUtil;
import com.zefu.tiol.vo.AnlizedResult;
import com.zefu.tiol.vo.TemplateType;
import com.zefu.tiol.vo.TiolItemVO;

/**
 * 
 * @功能描述 会议类型信息控制类
 * @time 2018年10月29日上午11:28:02
 * @author Super
 *
 */
@Controller
@RequestMapping("/com_item")
public class ComItemController {
	
	/**
	 * 此备注为ServiceImpl中配置的参数，如不要请用@Autowired
	 */
	@Resource(name = "comItemService")
	private ComItemService comItemService;
	
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
        List<Map<String, Object>> list = comItemService.queryList(param);
		return new Result(list);
	}
	
	/**
	 * 
	 * @功能描述 查询规划发展局事项清单信息
	 * @time 2018年11月1日上午9:16:23
	 * @author Super
	 * @param request
	 * @return
	 *
	 */
	@RequestMapping("/queryItemType")
	@ResponseBody
	public Object queryItemType(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        List<Map<String, Object>> list = comItemService.queryItemType(param);
		return new Result(list);
	}
	
	/**
	   * @功能描述: TODO 根据登陆信息查询企业事项目录
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月2日 上午9:46:28
	 */
	@RequestMapping("/queryItemListByPage")
	@ResponseBody
	public Object queryItemListByPage(HttpServletRequest request) {
		//基础数据
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		//登陆信息
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(GapGlobal.SESSION.GAP_SSO_SESSION_USERNAME);
		//分页数据
		Page<Map<String,Object>> page =CommonUtil.getPageFromRequest(request);
		//查询数据
		param.put("instID", loginUser.getInstId());
		List<Map<String, Object>> list = comItemService.queryItemListByPage(param,page);
		//统计总数
		int totalCount = comItemService.queryItemListByPageCount(param);
		page.setData(list);
		page.setTotalCount(totalCount);
		return page;
	}
	
	@RequestMapping("/queryCatalogItemByPage")
	@ResponseBody
	public Object queryCatalogItemByPage(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		Page<Map<String, Object>> page = CommonUtil.getPageFromRequest(request);
        List<Map<String, Object>> list = comItemService.queryCatalogItemByPage(param, page);
        int totalCount = comItemService.getCatalogItemCount(param);
        page.setTotalCount(totalCount);
        page.setData(list);
		return page;
	}
	
	/**
	   * @throws Exception 
	   * @功能描述: TODO 查询国资委事项清单
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月6日 下午3:36:50
	 */
	@RequestMapping("/queryGzwItemList")
	@ResponseBody
	public Object queryGzwItemList(HttpServletRequest request) throws Exception {
		//基础数据
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		//分页数据
		Page<Map<String,Object>> page =CommonUtil.getPageFromRequest(request);
		//查询数据
		List<Map<String, Object>> list = comItemService.queryGzwItemList(param,page);
		//统计总数
		int totalCount = comItemService.queryGzwItemListCount(param);
		page.setData(list);
		page.setTotalCount(totalCount);
		return page;
	}
	
	/**
	   * @功能描述: TODO 查询国资委事项清单详情
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月6日 下午3:53:36
	 */
	@RequestMapping("/queryGzwItem")
	@ResponseBody
	public Object queryGzwItem(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        Map<String, Object> detail = comItemService.queryGzwItem(param);
		return new Result(detail);
	}
	
	/**
	   * @功能描述: TODO 删除国资委事项清单
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月6日 下午3:57:31
	 */
	@RequestMapping("/deleteGzwItem")
	@ResponseBody
	public Object deleteGzwItem(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		param.put("ids",(param.get("ids")+"").split(","));
        comItemService.deleteGzwItem(param);
		return new Result();
	}
	
	/**
	   * @功能描述: TODO 彻底删除事项清单
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月18日 下午3:57:31
	 */
	@RequestMapping("/removeItem")
	@ResponseBody
	public Object removeItem(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		param.put("ids",(param.get("ids")+"").split(","));
        comItemService.removeItem(param);
		return new Result();
	}
	
	/**
	   * @功能描述: TODO 从回收站还原事项
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：李晓军
	   * @创建时间：2018年11月18日 下午3:57:31
	 */
	@RequestMapping("/restoreItem")
	@ResponseBody
	public Object restoreItem(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		param.put("ids",(param.get("ids")+"").split(","));
		comItemService.restoreItem(param);
		return new Result();
	}
	
	
	/**
	   * @功能描述: TODO 保存国资委事项清单
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月6日 下午3:58:51
	 */
	@RequestMapping("/saveGzwItem")
	@ResponseBody
	public Object saveGzwItem(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        comItemService.saveGzwItem(param);
		return new Result();
	}
	
	/**
	   * @功能描述: TODO 修改国资委事项清单
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月6日 下午3:58:51
	 */
	@RequestMapping("/updateGzwItem")
	@ResponseBody
	public Object updateGzwItem(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        comItemService.updateGzwItem(param);
		return new Result();
	}
	
	/**
	   * @功能描述: 保存事项清单
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 :李晓军
	   * @创建时间: 2018年11月9日  上午10:00
	 */
	@RequestMapping("/saveItem")
	@ResponseBody
	public Object saveItem(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		comItemService.saveItem(param);
		return new Result();
	}
	
	/**
	   * @功能描述: 查询事项清单详情
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：李晓军
	   * @创建时间：2018年11月9日 下午18:44:36
	 */
	@RequestMapping("/queryItem")
	@ResponseBody
	public Object queryItem(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
        Map<String, Object> detail = comItemService.queryItem(param);
        List<Map<String, Object>> list = comItemService.queryItemMeeting(param);
        detail.put("meetingTypeList", list);
		return new Result(detail);
	}
	
	/**
	   * @功能描述: 修改事项清单
	   * @参数: @param request
	   * @参数: @return
	   * @创建人 ：李晓军
	   * @创建时间：2018年11月10日 下午13:21:51
	 */
	@RequestMapping("/updateItem")
	@ResponseBody
	public Object updateItem(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		comItemService.updateItemSimple(param);
		return new Result();
	}
	
	/**
	 * 
	 * @功能描述:查询会议类型
	 * @参数: @param request
     * @参数: @return
     * @创建人 ：李晓军
     * @创建时间：2018年11月10日 下午13:21:51
	 *
	 */
	@RequestMapping("/queryMeetingType")
	@ResponseBody
	public Object queryMeetingType(HttpServletRequest request) {
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		List<Map<String, Object>> list = comItemService.queryMeetingType(param);
		return new Result(list);
	}

	/**
	 * 
	 * @功能描述:判断事项名称和事项编码是否存在
	 * @参数: @param request
     * @参数: @return
     * @创建人 ：李晓军
     * @创建时间：2018年11月10日 下午13:21:51
	 *
	 */
	@RequestMapping("/isExistsItem")
	@ResponseBody
	public Object isExistsItem(HttpServletRequest request){
		JSONObject result=new JSONObject();
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		String itemCode = (String) param.get("itemCode");
		String oitemCode = (String) param.get("oitemCode");
		String itemName = (String) param.get("itemName");
		String oitemName = (String) param.get("oitemName");
		if(itemCode != null && !"".equalsIgnoreCase(itemCode) && oitemCode != null && !"".equalsIgnoreCase(oitemCode)){
			if(itemCode.equals(oitemCode)){
				result.put("isExists", 0);
				return result;
			}
		}
		if(itemName != null && !"".equalsIgnoreCase(itemName) && oitemName != null && !"".equalsIgnoreCase(oitemName)){
			if(itemName.equals(oitemName)){
				result.put("isExists", 0);
				return result;
			}
		}
		int count = this.comItemService.isExistsItem(param);
		if(count > 0){
			result.put("isExists", 1);
		}else{
			result.put("isExists", 0);
		}
		return result;
	}
	
	/**
	 * @功能描述: 根据单位查询事项清单
	 * @参数: @param request
	 * @参数: @return
	 * @创建人 ：李缝兴
	 * @创建时间：2018年11月2日 上午9:46:28
	 */
	@RequestMapping("/queryItemListByCompanyId")
	@ResponseBody
	public Object queryItemListByCompanyId(HttpServletRequest request) {
		//基础数据
		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		//查询数据
		List<Map<String, Object>> list = comItemService.queryItemList(param);

		return list;
	}
	
	/**
	 * @功能描述:上传文件窗口
	 * @参数: @param request
     * @参数: @return
     * @创建人 ：李晓军
     * @创建时间：2018年11月13日 下午13:21:51
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
		return "/com_item/item_fileImport.jsp";
	}

	/**
	 * @功能描述:导入事项
	 * @参数: @param request
     * @参数: @return
     * @创建人 ：李晓军
     * @创建时间：2018年11月10日 下午13:21:51
	 *
	 */
	@RequestMapping(value="/importTiolItem", produces = "text/plain; charset=utf-8")
	@ResponseBody
	public String importTiolItem(HttpServletRequest request,@RequestParam("importFile")MultipartFile importFile) throws Exception {

		Map<String, Object> param = CommonUtil.getParameterFromRequest(request);
		JSONObject result=new JSONObject();
		File localFile=null;
		try{
//			System.out.println("文件名： "+file.getOriginalFilename());
			String fileName=importFile.getOriginalFilename();
			int enc=fileName.lastIndexOf(".");
			if(enc!=-1) {
				String  ext=fileName.substring(enc+1);
			    ext=ext.toLowerCase();
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
				FileUtils.writeByteArrayToFile(localFile, data);
				AnlizedResult anlizedResult=ExcelUtil.anlized(localFile.getAbsolutePath(), TemplateType.toilItem);
				if(anlizedResult.isSuccess() && null != anlizedResult.getData()) {
					Object resultData=anlizedResult.getData();
					if(resultData == null) {
						result.put("result", "数据导入异常");
						return result.toJSONString();
					}
					List<TiolItemVO> list=(List<TiolItemVO>)resultData;
					if(list.size()==0) {
						result.put("result", "没有找到数据，请查看excle第二列是否有数据");
						return result.toJSONString();
					}
					
					String ret=comItemService.saveTiolItem(list,param);
					if(null!=ret && !"".equals(ret)) {
						ret=ret.replaceAll("\n", "<br>");
//						System.out.println("异常信息"+ret);
						result.put("result", ret);
					}else {
						result.put("result", "success");
					}
				}else {
					String errorInfo=anlizedResult.getErrorInfo();
					if(null!=errorInfo ) errorInfo=errorInfo.replaceAll("\n", "<br>");
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


}
