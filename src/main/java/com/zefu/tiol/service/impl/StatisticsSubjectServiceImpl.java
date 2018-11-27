package com.zefu.tiol.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.common.util.DateUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.CatalogMapper;
import com.zefu.tiol.mapper.oracle.StatisticsSubjectMapper;
import com.zefu.tiol.service.StatisticsSubjectService;

/**
 *
 * @功能描述 决策议题统计
 * @time 2018年10月24日下午3:13:34
 * @author Super
 *
 */
@Service("statisticsSubjectService")
public class StatisticsSubjectServiceImpl implements StatisticsSubjectService {

//	@Autowired
//	private DataAccessService dataAccessService;

	@Autowired
	private StatisticsSubjectMapper statisticsSubjectMapper;
	
	@Autowired
	private CatalogMapper catalogMapper;

	@Override
	public List<Map<String, Object>> querySubjectList(Map<String, Object> param) throws Exception {
		return statisticsSubjectMapper.queryStatSubjectList(param);
	}

	@Override
	public List<Map<String, Object>> queryMeetingSubjectListByPage(
			Map<String, Object> param, Page<Map<String, Object>> page) throws Exception {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		return statisticsSubjectMapper.queryMeetingSubjectListByPage(param);
	}

	@Override
	public int getMeetingSubjectTotalCount(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return statisticsSubjectMapper.getMeetingSubjectTotalCount(param);
	}

	/**
	 * 根据会议类型查询议题
	 */
	@Override
	public List<Map<String, Object>> queryMeetingTypeSubjectListByPage(Map<String, Object> param,Page<Map<String, Object>> page) {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		return statisticsSubjectMapper.queryMeetingTypeSubjectListByPage(param);
	}

	/**
	 * 根据会议类型查询议题总数
	 */
	@Override
	public int queryTotalCount(Map<String, Object> param) {
		return statisticsSubjectMapper.queryTotalCount(param);
	}

	@Override
	public Map<String, Object> queryItemSubjectDetail(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		//获取数据
		List<Map<String, Object>> itemDetail = statisticsSubjectMapper.queryItemSubjectDetail(param);
		if(itemDetail != null && itemDetail.size() > 0){
			//拼接返回格式
			List<String> name = new ArrayList<String>();
			List<String> pass = new ArrayList<String>();
			List<String> defer = new ArrayList<String>();
			List<String> veto = new ArrayList<String>();
			String year = "";
			for (Map<String, Object> map : itemDetail) {
				if (map.get("name") != null) {
					name.add(map.get("name").toString());
				} else {
					name.add("");
				}
				if (map.get("pass") != null) {
					pass.add(map.get("pass").toString());
				} else {
					pass.add("0");
				}
				if (map.get("defer") != null) {
					defer.add(map.get("defer").toString());
				} else {
					defer.add("0");
				}
				if (map.get("veto") != null) {
					veto.add(map.get("veto").toString());
				} else {
					veto.add("0");
				}
				if (map.get("year") != null) {
					year = map.get("year").toString();
				} else {
					year = "";
				}
			}
			data.put("name", name);
			data.put("pass", pass);
			data.put("defer", defer);
			data.put("veto", veto);
			data.put("year", year);
		}
		return data;
	}
	
	@Override
	public Map<String, Object> getStatisticsSubjectChartData(
			Map<String, Object> param) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String,Object>> series = new ArrayList<Map<String,Object>>();
		List<String> xAxisData=new ArrayList<String>();
		List<String> legendData=new ArrayList<String>();
		int quarter = DateUtil.getCurrentQuarter();
		int year = DateUtil.getCurrentYear();
		List<Map<String,Object>> quarterList = new ArrayList<Map<String,Object>>();
		// 补全去年的季度
		for (int i = 5 - quarter; i >=1; i--) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("year", year-1);
			map.put("quarter", (4 - i + 1));
			quarterList.add(map);
			xAxisData.add((year-1)+"Q"+(4 - i + 1));
		}
		// 先找到今年已过的所有季度
		for (int i = 1; i <= quarter; i++) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("year", year);
			map.put("quarter", i);
			quarterList.add(map);
			xAxisData.add(year+"Q"+i);
		}
		param.put("quarterList", quarterList);
		List<Map<String,Object>> catalogList = catalogMapper.queryCatalogLevel(param);
		if(catalogList != null && catalogList.size() > 0){
			for (Map<String, Object> map : catalogList) {
				param.put("catalogId", map.get("CATALOG_ID"));
				//获取数据
				Map<String, Object> dataList = statisticsSubjectMapper.getStatisticsSubjectChartData(param);
				Map<String,Object> serie = new HashMap<String, Object>();
				Map<String,Object> label = new HashMap<String, Object>();
				legendData.add((String) map.get("CATALOG_NAME"));
				label.put("show", true);
				serie.put("label", label);
				serie.put("name", map.get("CATALOG_NAME"));
				serie.put("type", "line");
				List<String> data = new ArrayList<String>();
				for (Integer i = 0; i < quarterList.size(); i++) {
					String object = dataList.get(i.toString()).toString();
					data.add(object);
				}
				serie.put("data", data);
				series.add(serie);
			}
		}
		result.put("series", series);
		result.put("legendData", legendData);
		result.put("xAxisData", xAxisData);
		return result;
	}
	
	@Override
	public Map<String, Object> getSubjectChartData(Map<String, Object> parameter) {
		Map<String, Object> data = new HashMap<String, Object>();
		//获取数据
		List<Map<String,Object>> catalogList = catalogMapper.queryCatalogLevel(parameter);
		parameter.put("catalogList", catalogList);
		if(catalogList != null && catalogList.size() > 0){
			//拼接返回格式
			List<String> name = new ArrayList<String>();
			List<String> value = new ArrayList<String>();
			for (Map<String, Object> map : catalogList) {
				parameter.put("catalogId", map.get("CATALOG_ID"));
				Integer sum = statisticsSubjectMapper.getSubjectChartData(parameter);
				if(sum >0){
					name.add(map.get("CATALOG_NAME").toString());
					value.add(sum.toString());
				}
			}
			data.put("name", name);
			data.put("value", value);
		}
		if(data.get("name") == null){
			data.put("name", "");
		}
		if(data.get("value") == null){
			data.put("value", "");
		}
		return data;
	}

    /**
     * @param param
     * @功能描述: 查询所有数量
     * @创建人 ：林鹏
     * @创建时间：2018/11/17 19:49
     */
    @Override
    public int querySubjectRoleNumber(Map<String, Object> param) {
        return statisticsSubjectMapper.querySubjectRoleNumber(param);
    }
	@Override
	public List<Map<String, Object>> listSubjectClass(Map<String, Object> param,
			Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		int maxRow = page.getCurrentPage()*page.getPageSize();
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		List<Map<String,Object>> catalogList = catalogMapper.queryCatalogLevel(param);
		param.put("catalogList", catalogList);
		return statisticsSubjectMapper.listSubjectClass(param);
	}

	@Override
	public int countSubjectClass(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return statisticsSubjectMapper.countSubjectClass(param);
	}
}
