package com.zefu.tiol.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.ComItemMapper;
import com.zefu.tiol.service.ComItemService;
import com.zefu.tiol.vo.TiolItemMeetingVO;
import com.zefu.tiol.vo.TiolItemVO;

/**
 * 
 * @功能描述 企业版-事项管理 业务实现
 * @time 2018年10月29日上午11:27:32
 * @author Super
 *
 */
@Service("comItemService")
public class ComItemServiceImpl implements ComItemService{
	
	@Autowired
	private ComItemMapper comItemMapper;
	
	@Override
	public List<Map<String, Object>> queryList(Map<String, Object> param) {
		return comItemMapper.queryList(param);
	}

	@Override
	public List<Map<String, Object>> queryItemType(Map<String, Object> param) {
		return comItemMapper.queryItemType(param);
	}

	/**
	 * 查询事项清单列表
	 */
	@Override
	public List<Map<String, Object>> queryItemListByPage(Map<String, Object> param, Page<Map<String, Object>> page) {
		//查询是否关联下属企业
		List<String> companyID = comItemMapper.queryCompanyByInst(param);
		if(companyID.size() > 0 && companyID != null){
			param.put("companyID", companyID);
		}else{
			param.put("companyID", "");
		}
		//根据事项ID查询下级事项ID
		String navLevel= param.get("navLevel").toString();
		if(("1").equals(navLevel)){
			List<String> catalogList = comItemMapper.queryChirldCatalogID(param);
			param.put("catalogList", catalogList);
		}
		if(("2").equals(navLevel) && param.get("catalogCode") == null){
			List<String> catalogList = comItemMapper.queryChirldCatalogID(param);
			param.put("catalogList", catalogList);
			param.put("search", "chirld");
		}
		if(("2").equals(navLevel) && param.get("catalogCode") != null){
			param.put("search", "self");
			param.put("catalogList", "");
		}
		//查询事项清单数据
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		List<Map<String, Object>> itemList = comItemMapper.queryItemListByPage(param);
		if(itemList.size() > 0 && itemList != null){
			//查询关联事项会议
			List<Map<String, Object>> itemMeetingList = comItemMapper.queryItemMeetingListByPage(param);
			if(itemMeetingList.size() > 0 && itemMeetingList != null){
				//匹配最终顺序
				for (Map<String, Object> resultMap : itemList) {
					//获取清单ID
					String itemID = resultMap.get("itemId").toString();
					//最终结果
					String meetingDetail = "";
					for (Map<String, Object> meetingMap : itemMeetingList) {
						String meeingItem = meetingMap.get("itemId").toString();
						if(meeingItem.equals(itemID)){
							meetingDetail = meetingDetail + meetingMap.get("meetingName").toString() + ",";
						}
					}
					if(meetingDetail.length() > 1){
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
		//查询是否关联下属企业
		List<String> companyID = comItemMapper.queryCompanyByInst(param);
		if(companyID.size() > 0 && companyID != null){
			param.put("companyID", companyID);
		}else{
			param.put("companyID", "");
		}
		//根据事项ID查询下级事项ID
		String navLevel= param.get("navLevel").toString();
		if(("1").equals(navLevel)){
			List<String> catalogList = comItemMapper.queryChirldCatalogID(param);
			param.put("catalogList", catalogList);
		}
		if(("2").equals(navLevel) && param.get("catalogCode") == null){
			List<String> catalogList = comItemMapper.queryChirldCatalogID(param);
			param.put("catalogList", catalogList);
			param.put("search", "chirld");
		}
		if(("2").equals(navLevel) && param.get("catalogCode") != null){
			param.put("search", "self");
			param.put("catalogList", "");
		}
		return comItemMapper.queryItemListByPageCount(param);
	}

	@Override
	public List<Map<String, Object>> queryCatalogItemByPage(
			Map<String, Object> param, Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		List<Map<String, Object>> result = comItemMapper.queryCatalogItemByPage(param);
		if(result.size() > 0 && result != null){
			//查询会议流程
			List<Map<String, Object>> meetingResult = comItemMapper.queryItemMeetingOrderList(param);
			if(meetingResult.size() > 0 && meetingResult != null){
				//匹配最终顺序
				for (Map<String, Object> resultMap : result) {
					//获取清单ID
					String itemID = resultMap.get("itemId").toString();
					//最终结果
					String meetingDetail = "";
					for (Map<String, Object> meetingMap : meetingResult) {
						String meeingItem = meetingMap.get("itemId").toString();
						if(meeingItem.equals(itemID)){
							meetingDetail = meetingDetail + meetingMap.get("meetingName").toString() + ",";
						}
					}
					if(meetingDetail.length() > 1){
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
		return comItemMapper.getCatalogItemCount(param);
	}
	
	/**
	 * 查询国资委事项清单列表
	 */
	@Override
	public List<Map<String, Object>> queryGzwItemList(Map<String, Object> param, Page<Map<String, Object>> page)
			throws Exception {
		//查询事项清单数据
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		return comItemMapper.queryGzwItemList(param);
	}
	
	/**
	 * 查询国资委事项清单列表总数
	 */
	@Override
	public int queryGzwItemListCount(Map<String, Object> param) throws Exception {
		return comItemMapper.queryGzwItemListCount(param);
	}
	
	/**
	 * 查询国资委事项清单详情
	 */
	@Override
	public Map<String, Object> queryGzwItem(Map<String, Object> param) {
		return comItemMapper.queryGzwItem(param);
	}
	
	/**
	 * 删除事项清单
	 */
	@Override
	public void deleteGzwItem(Map<String, Object> param) {
		List<Map<String,Object>> deleteList = this.comItemMapper.queryItemByIds(param);
		List<Object> realDelIdsList = new ArrayList<Object>();
		List<Object> recycleIdsList = new ArrayList<Object>();
		//判断事项未上传为物理删除(uploadStatus=0、status=1or2)、已上传为逻辑删除(uploadStatus=1、status=1or2)
		if (deleteList != null && deleteList.size() > 0) {
			for (Map<String, Object> map : deleteList) {
				String uploadStatus = (String) map.get("uploadStatus");
				String status = (String) map.get("status");
				if(uploadStatus != null && "0".equalsIgnoreCase(uploadStatus)){
					if(status != null && !"0".equalsIgnoreCase(status)){
						realDelIdsList.add(map.get("itemId"));
					}
				}else if(uploadStatus != null && "1".equalsIgnoreCase(uploadStatus)){
					recycleIdsList.add(map.get("itemId"));
				}
			}
		}
		
		//执行物理删除
		if(realDelIdsList.size() > 0){
			Map<String, Object> realDelParam = new HashMap<String, Object>();
			realDelParam.put("ids", realDelIdsList);
			comItemMapper.deleteItemByids(realDelParam);
			comItemMapper.deleteItemMeetingByids(realDelParam);
		}
		//申请删除，修改状态
		if(recycleIdsList.size() > 0){
			Map<String, Object> recycleParam = new HashMap<String, Object>();
			recycleParam.put("ids", recycleIdsList);
			recycleParam.put("status", "0");
			recycleParam.put("uploadStatus", "0");
			comItemMapper.updateItemStatusByids(recycleParam);
		}
		
	}
	
	
	
	/**
	 * 保存国资委事项清单
	 */
	@Override
	public void saveGzwItem(Map<String, Object> param) {
		param.put("FID", CommonUtil.getUUID());
		comItemMapper.saveGzwItem(param);
	}
	
	/**
	 * 修改国资委事项清单
	 */
	@Override
	public void updateGzwItem(Map<String, Object> param) {
		comItemMapper.updateGzwItem(param);
	}
	
	/**
	 * 修改事项清单
	 */
	@Override
	public void updateItem(Map<String, Object> param) {
		comItemMapper.updateItem(param);
	}
	
	/**
	 * 保存事项明细
	 */
	@Override
	public void saveItem(Map<String, Object> param) {
		param.put("itemId", CommonUtil.getUUID());
		comItemMapper.saveItem(param);
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
					comItemMapper.saveItemMeeting(param);
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
		return comItemMapper.queryItem(param);
	}

	/**
	 * 查询会议类型
	 */
	@Override
	public List<Map<String, Object>> queryMeetingType(Map<String, Object> param) {
		return comItemMapper.queryMeetingType(param);
	}

	/**
	 * 更新事项
	 */
	@Override
	public void updateItemSimple(Map<String, Object> param) {
		comItemMapper.updateItemSimple(param);
		comItemMapper.deleteItemMeeting(param);
		String meetingTypeIds = (String) param.get("meetingTypeIds");
		if(meetingTypeIds != null){
			int orderNumber = 1;
			if(meetingTypeIds.contains(";")){
				Map<String,String> tempMeetingTypeIds = new LinkedHashMap<String, String>();
				String[] meetingTypeIdsArray = meetingTypeIds.split(";");
				for (String meetingTypeId : meetingTypeIdsArray) {
					if(!"".equalsIgnoreCase(meetingTypeIds)){
						if(tempMeetingTypeIds.get(meetingTypeId)!=null){
							continue;
						}
						tempMeetingTypeIds.put(meetingTypeId, "1");
						param.put("MEETINGID", CommonUtil.getUUID());
						param.put("typeId", meetingTypeId);
						param.put("orderNumber", orderNumber);
						comItemMapper.saveItemMeeting(param);
						orderNumber++;
					}
				}
			}else{
				if(!"".equalsIgnoreCase(meetingTypeIds)){
					param.put("MEETINGID", CommonUtil.getUUID());
					param.put("typeId", meetingTypeIds);
					param.put("orderNumber", orderNumber);
					comItemMapper.saveItemMeeting(param);
				}
			}
		}
	}

	/**
	 * 查询事项关联会议
	 */
	@Override
	public List<Map<String, Object>> queryItemMeeting(Map<String, Object> param) {
		return comItemMapper.queryItemMeeting(param);
	}

	@Override
	public List<Map<String, Object>> queryItemList(Map<String, Object> param) {
		return comItemMapper.queryItemList(param);
	}

	
	/**
	 * 获取事项目录代码
	 * <catalogCode,catalogId>
	 * @return
	 */
	Map<String,String> getCatalogCodeMap(){
		List<Map<String,String>> catalogCodeList = this.comItemMapper.queryAllCatalogListByCode();
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
		List<Map<String,Object>> meetingTypeList  = this.comItemMapper.queryMeetingType(null);
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
			queryItem.put("CURRENT_USER", param.get("CURRENT_USER"));
			int itemCount = this.comItemMapper.getCatalogItemCount(queryItem);
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

	/**
	 * 企业判断事项是否存在
	 */
	@Override
	public int isExistsItem(Map<String, Object> param) {
		return this.comItemMapper.isExistsItem(param);
	}

	@Override
	public void restoreItem(Map<String, Object> param) {
		param.put("status", "1");
		param.put("uploadStatus", "0");
		this.comItemMapper.updateItemStatusByids(param);
	}

	@Override
	public void removeItem(Map<String, Object> param) {
		comItemMapper.deleteItemByids(param);
		comItemMapper.deleteItemMeetingByids(param);
	}
}
