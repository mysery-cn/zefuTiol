package com.zefu.tiol.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.CatalogMapper;
import com.zefu.tiol.mapper.oracle.StatisticsItemMapper;
import com.zefu.tiol.service.StatisticsItemService;
import com.zefu.tiol.util.ListUtil;

/**
   * @工程名 : szyd
   * @文件名 : StatisticsItemServiceImpl.java
   * @工具包名：com.zefu.tiol.service.impl
   * @功能描述: TODO	事项清单统计逻辑层
   * @创建人 ：林鹏
   * @创建时间：2018年10月25日 上午8:54:10
   * @版本信息：V1.0
 */
@Service("statisticsItemService")
public class StatisticsItemServiceImpl implements StatisticsItemService {
	
	@Autowired
	private StatisticsItemMapper itemMapper;
	
	@Autowired
	private CatalogMapper catalogMapper;
	
	/**
	 * 查询事项清单统计数据
	 */
	@Override
	public Map<String, Object> queryItemDetail(Map<String, Object> parameter) {
		Map<String, Object> data = new HashMap<String, Object>();
		//获取数据
		List<Map<String, Object>> itemDetail = itemMapper.queryItemDetail(parameter);
		if(itemDetail != null && itemDetail.size() > 0){
			//拼接返回格式
			List<String> name = new ArrayList<String>();
			List<String> value = new ArrayList<String>();
			for (Map<String, Object> map : itemDetail) {
				name.add(map.get("name").toString());
				value.add(map.get("value").toString());
			}
			data.put("name", name);
			data.put("value", value);
		}
		return data;
	}
	
	/**
	 * 查询行业事项汇总情况
	 */
	@Override
	public List<Map<String, Object>> queryIndustryItemList(Map<String, Object> parameter,Page<Map<String, Object>> page) {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		List<Map<String, Object>> list = itemMapper.queryIndustryItemList(parameter);
		return list;
	}
	
	/**
	 * 查询行业事项总数量
	 */
	@Override
	public int queryIndustryItemCount(Map<String, Object> parameter) {
		return itemMapper.queryIndustryItemCount(parameter);
	}
	
	/**
	 * 根据事项查询事项清单By企业
	 */
	@Override
	public List<Map<String, Object>> queryItemListByCatalog(Map<String, Object> parameter,Page<Map<String, Object>> page) {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		//迭代1.2级事项ID
		if(parameter.get("catalogId") != null){
			List<String> catalogChirldId = itemMapper.queryCatalogChirldId(parameter);
			if(ListUtil.isNotBlank(catalogChirldId)){
				parameter.put("catalogList", catalogChirldId);
			}
		}
		//结果
		List<Map<String, Object>> result = itemMapper.queryItemListByCatalog(parameter);
		if(result.size() > 0 && result != null){
			//查询会议流程
			List<Map<String, Object>> meetingResult = itemMapper.queryItemMeetingOrderList(parameter);
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
							if(meetingMap.get("meetingName") != null){
								meetingDetail = meetingDetail + meetingMap.get("meetingName").toString() + ",";
							}
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
	
	/**
	 * 查询事项清单列表总数
	 */
	@Override
	public int queryItemListByCatalogCount(Map<String, Object> parameter) {
		//迭代1.2级事项ID
		if(parameter.get("catalogId") != null){
			List<String> catalogChirldId = itemMapper.queryCatalogChirldId(parameter);
			if(ListUtil.isNotBlank(catalogChirldId)){
				parameter.put("catalogList", catalogChirldId);
			}
		}
		return itemMapper.queryItemListByCatalogCount(parameter);
	}

	@Override
	public List<Map<String, Object>> queryItemSubjectByPage(Map<String, Object> param, Page<Map<String, Object>> page) {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		List<Map<String, Object>> typelist = catalogMapper.queryCatalogFLevel(param);
		param.put("typelist", typelist);
		String sql = "";
		for (int i = 0; i < typelist.size(); i++) {
			if (i == 0) {
				sql = "convert(int,TYPECODE"+i+")";
			} else {
				sql = sql +"+convert(int,TYPECODE"+i+")"; 
			}
		}
		param.put("sql", sql);
		return itemMapper.queryItemSubjectByPage(param);
	}

	@Override
	public int getItemSubjectCount(Map<String, Object> param) {
		return itemMapper.getItemSubjectCount(param);
	}

	@Override
	public Map<String, Object> queryCatalogSubjectFDetail(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		//获取数据
		List<Map<String, Object>> itemDetail = itemMapper.queryCatalogSubjectFDetail(param);
		if(itemDetail != null && itemDetail.size() > 0){
			//拼接返回格式
			List<String> idArray = new ArrayList<String>();
			List<String> nameArray = new ArrayList<String>();
			List<String> quantityArray = new ArrayList<String>();
			for (Map<String, Object> map : itemDetail) {
				if (map.get("CATALOG_ID") != null) {
					idArray.add(map.get("CATALOG_ID").toString());
				} else {
					idArray.add("");
				}
				if (map.get("CATALOG_NAME") != null) {
					nameArray.add(map.get("CATALOG_NAME").toString());
				} else {
					nameArray.add("");
				}
				if (map.get("QUANTITY") != null) {
					quantityArray.add(map.get("QUANTITY").toString());
				} else {
					quantityArray.add("0");
				}
			}
			data.put("idArray", idArray);
			data.put("nameArray", nameArray);
			data.put("quantityArray", quantityArray);
		}
		return data;
	}

	@Override
	public Map<String, Object> queryCatalogItemFDetail(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		//获取数据
		List<Map<String, Object>> itemDetail = itemMapper.queryCatalogItemFDetail(param);
		//拼接返回格式
		List<String> nameArray = new ArrayList<String>();
		List<String> quantityArray = new ArrayList<String>();
		if(itemDetail != null && itemDetail.size() > 0){
			for (Map<String, Object> map : itemDetail) {
				nameArray.add(map.get("nameArray").toString());
				quantityArray.add(map.get("quantityArray").toString());
			}
		} else {
			nameArray.add(new SimpleDateFormat("yyyy年").format(new Date()));
			quantityArray.add("0");
		}
		data.put("nameArray", nameArray);
		data.put("quantityArray", quantityArray);
		return data;
	}

	@Override
	public List<Map<String, Object>> queryCatalogItemByPage(
			Map<String, Object> param, Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		param.put("minRow", minRow * 4);
		param.put("maxRow", maxRow * 4);
		List<Map<String, Object>> list = itemMapper.queryCatalogItemByPage(param);
		List<Map<String, Object>> deatil = new ArrayList<Map<String, Object>>();
		int i=0;
		int sums = 0;
		Map<String, Object> maps = new HashMap<String, Object>();
		for (Map<String, Object> map : list) {
			if (i==0) {
				maps.put("F01", map.get("CN".toString()));
				maps.put("ID1", map.get("CATALOG_ID"));
				sums = sums + Integer.parseInt(map.get("CN").toString());
			} else if (i==1) {
				maps.put("F02", map.get("CN".toString()));
				maps.put("ID2", map.get("CATALOG_ID"));
				sums = sums + Integer.parseInt(map.get("CN").toString());
			} else if (i==2) {
				maps.put("F03", map.get("CN".toString()));
				maps.put("ID3", map.get("CATALOG_ID"));
				sums = sums + Integer.parseInt(map.get("CN").toString());
			} else if (i==3) {
				maps.put("F04", map.get("CN".toString()));
				maps.put("ID4", map.get("CATALOG_ID"));
				sums = sums + Integer.parseInt(map.get("CN").toString());
			}
			i++;
			if (i==4) {
				minRow++;
				maps.put("RN", minRow);
				maps.put("COMPANY_ID", map.get("COMPANY_ID".toString()));
				maps.put("COMPANY_SHORT_NAME", map.get("COMPANY_SHORT_NAME".toString()));
				deatil.add(maps);
				maps.put("SUMS", sums);
				maps = new HashMap<String, Object>();
				i=0;
				sums = 0;
			}
		}
		return deatil;
	}

	@Override
	public int getCatalogItemCount(Map<String, Object> param) {
		return itemMapper.getCatalogItemCount(param) / 4;
	}

	/**
	   * @功能描述:  查询所有企业事项清单
	   * @创建人 ：林鹏
	   * @创建时间：2018/11/18 15:05
	*/
	@Override
	public List<Map<String, Object>> queryItemList(Map<String, Object> parameter, Page<Map<String, Object>> page) {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);

		//迭代1.2级事项ID
		if(parameter.get("catalogId") != null){
			List<String> catalogChirldId = itemMapper.queryCatalogChirldId(parameter);
			if(ListUtil.isNotBlank(catalogChirldId)){
				parameter.put("catalogList", catalogChirldId);
			}
		}
		//结果
		List<Map<String, Object>> result = itemMapper.queryItemList(parameter);
		if(result.size() > 0 && result != null){
			//查询会议流程
			List<Map<String, Object>> meetingResult = itemMapper.queryItemMeetingOrderList(parameter);
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
							if(meetingMap.get("meetingName") != null){
								meetingDetail = meetingDetail + meetingMap.get("meetingName").toString() + ",";
							}
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

	/**
	   * @功能描述:  查询所有企业事项清单总数
	   * @创建人 ：林鹏
	   * @创建时间：2018/11/18 15:05
	*/
	@Override
	public int queryItemListTotalCount(Map<String, Object> param) {
		return itemMapper.queryItemListTotalCount(param);
	}
}
