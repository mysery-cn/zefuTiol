package com.zefu.tiol.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.yr.gap.engine.util.StringUtils;
import com.zefu.tiol.constant.BusinessConstant;
import com.zefu.tiol.constant.EBusinessType;
import com.zefu.tiol.mapper.oracle.GenerateXmlMapper;
import com.zefu.tiol.mapper.oracle.IntegrationConfigMapper;
import com.zefu.tiol.mapper.oracle.ItemMapper;
import com.zefu.tiol.service.ItemService;
import com.zefu.tiol.util.DateUtils;
import com.zefu.tiol.util.FileUtils;
import com.zefu.tiol.util.HttpClientUtils;
import com.zefu.tiol.util.TiolComnUtils;
import com.zefu.tiol.vo.TiolItemMeetingVO;
import com.zefu.tiol.vo.TiolItemVO;

/**
 * 
 * @功能描述 会议类型信息实现类
 * @time 2018年10月29日上午11:27:32
 * @author Super
 *
 */
@Service("itemService")
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private GenerateXmlMapper xmlMapper;
	
	@Autowired
	private IntegrationConfigMapper configMapper;

	@Override
	public List<Map<String, Object>> queryList(Map<String, Object> param) {
		return itemMapper.queryList(param);
	}

	@Override
	public List<Map<String, Object>> queryItemType(Map<String, Object> param) {
		return itemMapper.queryItemType(param);
	}

	/**
	 * 查询事项清单列表
	 */
	@Override
	public List<Map<String, Object>> queryItemListByPage(Map<String, Object> param, Page<Map<String, Object>> page) {
		// 查询是否关联下属企业
		List<String> companyID = itemMapper.queryCompanyByInst(param);
		if (companyID.size() > 0 && companyID != null) {
			param.put("companyID", companyID);
		} else {
			param.put("companyID", "");
		}
		// 根据事项ID查询下级事项ID
		String navLevel = param.get("navLevel").toString();
		if (("1").equals(navLevel)) {
			List<String> catalogList = itemMapper.queryChirldCatalogID(param);
			param.put("catalogList", catalogList);
		}
		if (("2").equals(navLevel) && param.get("catalogCode") == null) {
			List<String> catalogList = itemMapper.queryChirldCatalogID(param);
			param.put("catalogList", catalogList);
			param.put("search", "chirld");
		}
		if (("2").equals(navLevel) && param.get("catalogCode") != null) {
			param.put("search", "self");
			param.put("catalogList", "");
		}
		// 查询事项清单数据
		int maxRow = page.getCurrentPage() * page.getPageSize();
		int minRow = (page.getCurrentPage() - 1) * page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		List<Map<String, Object>> itemList = itemMapper.queryItemListByPage(param);
		if (itemList.size() > 0 && itemList != null) {
			// 查询关联事项会议
			List<Map<String, Object>> itemMeetingList = itemMapper.queryItemMeetingListByPage(param);
			if (itemMeetingList.size() > 0 && itemMeetingList != null) {
				// 匹配最终顺序
				for (Map<String, Object> resultMap : itemList) {
					// 获取清单ID
					String itemID = resultMap.get("itemId").toString();
					// 最终结果
					String meetingDetail = "";
					for (Map<String, Object> meetingMap : itemMeetingList) {
						String meeingItem = meetingMap.get("itemId").toString();
						if (meeingItem.equals(itemID)) {
							meetingDetail = meetingDetail + meetingMap.get("meetingName").toString() + ",";
						}
					}
					if (meetingDetail.length() > 1) {
						meetingDetail = meetingDetail.substring(0, meetingDetail.length() - 1);
					}
					resultMap.put("meetingDetail", meetingDetail);
				}
			}
		}
		return itemList;
	}

	/**
	 * 查询事项清单列表总数
	 */
	@Override
	public int queryItemListByPageCount(Map<String, Object> param) {
		// 查询是否关联下属企业
		List<String> companyID = itemMapper.queryCompanyByInst(param);
		if (companyID.size() > 0 && companyID != null) {
			param.put("companyID", companyID);
		} else {
			param.put("companyID", "");
		}
		// 根据事项ID查询下级事项ID
		String navLevel = param.get("navLevel").toString();
		if (("1").equals(navLevel)) {
			List<String> catalogList = itemMapper.queryChirldCatalogID(param);
			param.put("catalogList", catalogList);
		}
		if (("2").equals(navLevel) && param.get("catalogCode") == null) {
			List<String> catalogList = itemMapper.queryChirldCatalogID(param);
			param.put("catalogList", catalogList);
			param.put("search", "chirld");
		}
		if (("2").equals(navLevel) && param.get("catalogCode") != null) {
			param.put("search", "self");
			param.put("catalogList", "");
		}
		return itemMapper.queryItemListByPageCount(param);
	}

	@Override
	public List<Map<String, Object>> queryCatalogItemByPage(Map<String, Object> param, Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		int maxRow = page.getCurrentPage() * page.getPageSize();
		int minRow = (page.getCurrentPage() - 1) * page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		List<Map<String, Object>> result = itemMapper.queryCatalogItemByPage(param);
		if (result.size() > 0 && result != null) {
			// 查询会议流程
			List<Map<String, Object>> meetingResult = itemMapper.queryItemMeetingOrderList(param);
			if (meetingResult.size() > 0 && meetingResult != null) {
				// 匹配最终顺序
				for (Map<String, Object> resultMap : result) {
					// 获取清单ID
					String itemID = resultMap.get("itemId").toString();
					// 最终结果
					String meetingDetail = "";
					for (Map<String, Object> meetingMap : meetingResult) {
						String meeingItem = meetingMap.get("itemId").toString();
						if (meeingItem.equals(itemID)) {
							meetingDetail = meetingDetail + meetingMap.get("meetingName").toString() + ",";
						}
					}
					if (meetingDetail.length() > 1) {
						meetingDetail = meetingDetail.substring(0, meetingDetail.length() - 1);
					}
					resultMap.put("meetingDetail", meetingDetail);
				}
			}
		}
		return result;
	}

	@Override
	public int getCatalogItemCount(Map<String, Object> param) {
		return itemMapper.getCatalogItemCount(param);
	}

	/**
	 * 查询国资委事项清单列表
	 */
	@Override
	public List<Map<String, Object>> queryGzwItemList(Map<String, Object> param, Page<Map<String, Object>> page)
			throws Exception {
		// 查询事项清单数据
		int maxRow = page.getCurrentPage() * page.getPageSize();
		int minRow = (page.getCurrentPage() - 1) * page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		return itemMapper.queryGzwItemList(param);
	}

	/**
	 * 查询国资委事项清单列表总数
	 */
	@Override
	public int queryGzwItemListCount(Map<String, Object> param) throws Exception {
		return itemMapper.queryGzwItemListCount(param);
	}

	/**
	 * 查询国资委事项清单详情
	 */
	@Override
	public Map<String, Object> queryGzwItem(Map<String, Object> param) {
		return itemMapper.queryGzwItem(param);
	}

	/**
	 * 删除国资委事项清单
	 */
	@Override
	public void deleteGzwItem(Map<String, Object> param) {
		itemMapper.deleteGzwItem(param);
	}

	/**
	 * 保存国资委事项清单
	 */
	@Override
	public void saveGzwItem(Map<String, Object> param) {
		param.put("FID", CommonUtil.getUUID());
		itemMapper.saveGzwItem(param);
	}

	/**
	 * 修改国资委事项清单
	 */
	@Override
	public void updateGzwItem(Map<String, Object> param) {
		itemMapper.updateGzwItem(param);
	}

	/**
	 * 修改事项清单
	 */
	@Override
	public void updateItem(Map<String, Object> param) {
		itemMapper.updateItem(param);
	}

	/**
	 * 保存事项明细
	 */
	@Override
	public void saveItem(Map<String, Object> param) {
		param.put("itemId", CommonUtil.getUUID());
		itemMapper.saveItem(param);
		String meetingTypeIds = (String) param.get("meetingTypeIds");
		if(meetingTypeIds != null){
			int orderNumber = 1;
			Map<String,String> tempMeetingTypeIds = new LinkedHashMap<String, String>();
			String[] meetingTypeIdsArray = meetingTypeIds.split(";");
			for (String meetingTypeId : meetingTypeIdsArray) {
				if(!"".equalsIgnoreCase(meetingTypeIds)){
					if(tempMeetingTypeIds.get(meetingTypeId)!=null){
						continue;
					}
					tempMeetingTypeIds.put(meetingTypeId, "1");
					param.put("MEETINGID", CommonUtil.getUUID());
					param.put("itemId", param.get("itemId"));
					param.put("typeId", meetingTypeId);
					param.put("orderNumber", orderNumber);
					itemMapper.saveItemMeeting(param);
					orderNumber++;
				}
			}
		}
	}

	/**
	 * 查询事项清单明细
	 */
	@Override
	public Map<String, Object> queryItem(Map<String, Object> param) {
		return itemMapper.queryItem(param);
	}

	/**
	 * 查询会议类型
	 */
	@Override
	public List<Map<String, Object>> queryMeetingType(Map<String, Object> param) {
		return itemMapper.queryMeetingType(param);
	}

	/**
	 * 更新事项
	 */
	@Override
	public void updateItemSimple(Map<String, Object> param) {
		itemMapper.updateItemSimple(param);
		itemMapper.deleteItemMeeting(param);
		String meetingTypeIds = (String) param.get("meetingTypeIds");
		if (meetingTypeIds != null) {
			int orderNumber = 1;
			if (meetingTypeIds.contains(";")) {
				Map<String, String> tempMeetingTypeIds = new LinkedHashMap<String, String>();
				String[] meetingTypeIdsArray = meetingTypeIds.split(";");
				for (String meetingTypeId : meetingTypeIdsArray) {
					if (!"".equalsIgnoreCase(meetingTypeIds)) {
						if (tempMeetingTypeIds.get(meetingTypeId) != null) {
							continue;
						}
						tempMeetingTypeIds.put(meetingTypeId, "1");
						param.put("MEETINGID", CommonUtil.getUUID());
						param.put("typeId", meetingTypeId);
						param.put("orderNumber", orderNumber);
						itemMapper.saveItemMeeting(param);
						orderNumber++;
					}
				}
			} else {
				if (!"".equalsIgnoreCase(meetingTypeIds)) {
					param.put("MEETINGID", CommonUtil.getUUID());
					param.put("typeId", meetingTypeIds);
					param.put("orderNumber", orderNumber);
					itemMapper.saveItemMeeting(param);
				}
			}
		}
	}

	/**
	 * 查询事项关联会议
	 */
	@Override
	public List<Map<String, Object>> queryItemMeeting(Map<String, Object> param) {
		return itemMapper.queryItemMeeting(param);
	}

	/**
	 * 生成xml
	 */
	@Override
	public String uploadItem(Map<String, Object> param, String companyId, String userName) {
		// String filePath = ServletContext.getRealPath("/");
		// 1.根据事项id，从数据库查询事项列表
		String[] ids = (String[]) param.get("ids");
		List<Map<String, Object>> itemList = itemMapper.queryItemByIds(ids, companyId);
		int success = 0;
		for (Map<String, Object> item : itemList) {
			JSONObject itemJson = new JSONObject();
			itemJson.put("item_id", item.get("ITEM_ID"));
			itemJson.put("item_name", item.get("ITEM_NAME"));
			itemJson.put("item_code", item.get("ITEM_CODE"));

			JSONArray meetingJsonArray = new JSONArray();
			// 查询开会顺序
			List<String> meetingTypeList = xmlMapper.GenerateItemMeetingTypeXml(item.get("ITEM_ID").toString());
			if (meetingTypeList != null && meetingTypeList.size() > 0) {
				for (String meetingType : meetingTypeList) {
					meetingJsonArray.add(meetingType);
				}
			}
			itemJson.put("item_meeting_list", new JSONObject().put("item_meeting", meetingJsonArray));
			String legalFlag = item.containsKey("LEGAL_FLAG") ? item.get("LEGAL_FLAG").toString() : "";
			if (legalFlag.equals("1")) {
				legalFlag = "是";
			} else if (legalFlag.equals("0")) {
				legalFlag = "否";
			}
			itemJson.put("legal_flag", legalFlag);
			itemJson.put("remark", item.get("REMARK"));
			String status = item.get("STATUS").toString();
			String operType = "add";
			if (status.equals("0")) {
				operType = "del";
			} else if (status.equals("2")) {
				operType = "edit";
			}
			itemJson.put("oper_type", operType);
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+XML.toString(new JSONObject().put("tiol",itemJson));
			// 获取路径
			String outPath = this.getClass().getClassLoader().getResource("/").getPath()+"../../uploadFile";//ItemService.class.getResource(File.separator).toString();
			// 文件名
			String xmlFileName=TiolComnUtils.generalFileName(BusinessConstant.UNDERLINE_COODE,
					EBusinessType.ITEM.getCode(),BusinessConstant.VERSION,DateUtils.getFormatTime("yyyyMMdd"));
			// 创建文件夹
			File filePath = new File(outPath);
			filePath.mkdirs();
			String outXmlFilePath = outPath + File.separator + xmlFileName + BusinessConstant.DOT_CODE+BusinessConstant.XML_SUFFIX;
			try {
				// 生成xml文件
				FileUtils.writeContent(outXmlFilePath, xml);
				
				// 压缩文件
				File zipFile = new File(outPath + File.separator + xmlFileName + BusinessConstant.DOT_CODE + BusinessConstant.ZIP_SUFFIX);
				FileOutputStream os = new FileOutputStream(zipFile);
				FileUtils.toZip(outXmlFilePath,os,true);
				Map<String, Object> paramMap = new HashMap<String, Object>();
				Map<String, Object> configMap = configMapper.getInterfaceConfig();
				String uploadUrl = "";
				String uploadUser = "";
				String uploadPassword = "";
				if(null == configMap|| configMap.isEmpty()) {
					return "上传接口未配置";
				}else {
					uploadUrl = String.valueOf(configMap.get("ADDRESS"));
					uploadUser = String.valueOf(configMap.get("USER_NAME"));
					uploadPassword = String.valueOf(configMap.get("PASSWORD"));
					if(StringUtils.isEmpty(uploadUrl)||StringUtils.isEmpty(uploadUser)
							||StringUtils.isEmpty(uploadPassword)) {
						return "上传接口未配置";
					}
				}
						
				paramMap.put("USER", uploadUser);
				paramMap.put("PASSWORD", uploadPassword);
				//业务类型
				paramMap.put("BUSINESS_TYPE", EBusinessType.ITEM.getCode());
				paramMap.put("API_CODE", BusinessConstant.API_CODE);
				paramMap.put("FILE_NAME", zipFile.getName());
				//调用大额资金报送系统通道进行上报
				String result = HttpClientUtils.upload(uploadUrl, zipFile.getPath(), paramMap);
				JSONObject resJson = new JSONObject(result);
				if(resJson.has("CODE")) {
					if(resJson.getString("CODE").equals("1")) {
						success++;
						//更新数据库
						paramMap.clear();
						paramMap.put("ITEM_ID", item.get("ITEM_ID"));
						if(!StringUtils.isEmpty(item.get("UPLOAD_STATUS"))&&"1".equals(item.get("UPLOAD_STATUS"))) {
							paramMap.put("UPLOAD_STATUS", "2");
						}else {
							paramMap.put("UPLOAD_STATUS", "1");
						}
						paramMap.put("UPDATE_TIME", DateUtils.getFormatTime());
						paramMap.put("UPDATE_USER", userName);
						itemMapper.updateItem(paramMap);
					}
				}
				System.out.println(resJson);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "共计 "+ ids.length+" 条，上报成功 "+success+" 条";
	}

	@Override
	public List<Map<String, Object>> queryItemList(Map<String, Object> param) {
		return itemMapper.queryItemList(param);
	}

	
	/**
	 * 获取事项目录代码
	 * <catalogCode,catalogId>
	 * @return
	 */
	Map<String,String> getCatalogCodeMap(){
		List<Map<String,String>> catalogCodeList = this.itemMapper.queryAllCatalogListByCode();
		Map<String,String> catalogCodeMap = new LinkedHashMap<String, String>();
		if(catalogCodeList != null && catalogCodeList.size() > 0){
			for (Map<String, String> map : catalogCodeList) {
				catalogCodeMap.put(map.get("navCode"),map.get("catalogId"));
			}
		}
		return catalogCodeMap;
	}
	
	/**
	 * 获取会议类型map
	 * <typeName,typeId>
	 * @return
	 */
	Map<String,Object> getMeetingTypeMap(){
		List<Map<String,Object>> meetingTypeList  = this.itemMapper.queryMeetingType(null);
		Map<String,Object> meetingTypeMap = new LinkedHashMap<String, Object>();
		if(meetingTypeList != null && meetingTypeList.size() > 0){
			for (Map<String, Object> map : meetingTypeList) {
				if(map.get("typeName") != null){
					meetingTypeMap.put((String)map.get("typeName"),map.get("typeId"));
				}
			}
		}
		return meetingTypeMap;
	}

	/**
	 * Excel导入事项保存
	 */
	@Override
	public String saveTiolItem(List<TiolItemVO> list,Map<String, Object> param) {
		if(null== list) return ""; 
		StringBuffer errorInfo=new StringBuffer();
		Map<String,String> catalogCodeMap = getCatalogCodeMap(); //获取事项目录
		Map<String, Object> meetingTypeMap = this.getMeetingTypeMap(); //获取会议类型
		
		//验证Excel数据
		for(TiolItemVO vo:list) {
			String itemCode=vo.getItemCode();
			String strs[]=itemCode.split("-");
			if(2!=strs.length) {
				 errorInfo.append("第");
				 errorInfo.append((vo.getRow()+1));
				 errorInfo.append("行");
				 errorInfo.append("事项编没有按规范填写: "+itemCode+"\n");
			}
			String itemCodeHead =strs[0];
			itemCodeHead=itemCodeHead.trim();
			if( null == catalogCodeMap.get(itemCodeHead)) {
				 errorInfo.append("第");
				 errorInfo.append((vo.getRow()+1));
				 errorInfo.append("行");
				 errorInfo.append("没有此事项目录: "+itemCodeHead+"\n");
			}
			Map<String, Object> queryItem = new HashMap<String, Object>();
			queryItem.put("itemCode", itemCode);
			int itemCount = this.itemMapper.getCatalogItemCount(queryItem);
			if(itemCount > 0) {
				 errorInfo.append("第");
				 errorInfo.append((vo.getRow()+1));
				 errorInfo.append("行");
				 errorInfo.append("已经存在此事项: "+itemCode+"\n");
			}
			
			
            List<TiolItemMeetingVO> meetingList=vo.getMeetingList();			
            for(TiolItemMeetingVO meetingVO:meetingList) {
            	if(null == meetingTypeMap.get(meetingVO.getName())) {
	   				 errorInfo.append("第");
	   				 errorInfo.append((meetingVO.getRow()+1));
	   				 errorInfo.append("行");
	   				 errorInfo.append("没有此会议类型: "+meetingVO.getName()+"\n");
            	}
            }
		}
		
		if(errorInfo.length()>0 && null!= errorInfo.toString()) {//如果校验不通过，返回错误信息
			return errorInfo.toString();
		}
		
		for(TiolItemVO vo:list) {
			Map<String,Object> inputItem = new LinkedHashMap<String, Object>();
			inputItem.put("CURRENT_USER", param.get("CURRENT_USER"));
			
			String itemCode = vo.getItemCode();
			String strs[]=itemCode.split("-");
			
			inputItem.put("catalogId", catalogCodeMap.get(strs[0]));
			inputItem.put("itemName", vo.getItemName());
			inputItem.put("itemCode", vo.getItemCode());
			String legalFlag;
			if(vo.getLegalFlag() != null && !"".equalsIgnoreCase(vo.getLegalFlag()) && "是".equalsIgnoreCase(vo.getLegalFlag())){
				legalFlag = "1";
			}else{
				legalFlag = "0";
			}
			inputItem.put("legalFlag", legalFlag);
			inputItem.put("remark", vo.getRemark());
			List<TiolItemMeetingVO> meetingList=vo.getMeetingList();	
			if(meetingList != null && meetingList.size() > 0){
				String meetingTypeIds = "";
	            for(TiolItemMeetingVO meetingVO:meetingList) {
	            	meetingTypeIds += meetingTypeMap.get(meetingVO.getName()) + ";";
	            }
	            inputItem.put("meetingTypeIds", meetingTypeIds);
			}
			this.saveItem(inputItem);
		}
		
		return null;
	}
}
