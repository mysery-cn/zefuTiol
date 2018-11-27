package com.zefu.tiol.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.CatalogMapper;
import com.zefu.tiol.service.CatalogService;

/**
 * 
 * @功能描述 事项目录
 * @time 2018年10月26日下午3:22:09
 * @author Super
 *
 */
@Service("catalogService")
public class CatalogServiceImpl implements CatalogService {
	
	@Autowired
	private CatalogMapper catalogMapper;

	@Override
	public List<Map<String, Object>> queryCatalogLevel(Map<String, Object> param) {
		return catalogMapper.queryCatalogLevel(param);
	}
	
	/**
	 * 查询事项列表By树形结构
	 */
	@Override
	public Map<String, Object> queryCatalogTree(Map<String, Object> param) {
		Map<String, Object> dataParam = new HashMap<String, Object>();
		int allNumber = 0;
		//顶级节点
		param.put("level", "1");
		List<Map<String, Object>> catalogTreeTop = catalogMapper.queryCatalogTree(param);
		if(catalogTreeTop.size() > 0 && catalogTreeTop != null){
			for (Map<String, Object> top : catalogTreeTop) {
				int totalCount_o = 0;
				//查询二级节点
				param.put("level", "2");
				param.put("catalogPid", top.get("catalogId"));
				List<Map<String, Object>> catalogTreeSecondary = catalogMapper.queryCatalogTree(param);
				if(catalogTreeSecondary.size() > 0 && catalogTreeSecondary != null){
					for (Map<String, Object> secondary : catalogTreeSecondary) {
						int totalCount = 0;
						param.put("level", "3");
						param.put("catalogPid", secondary.get("catalogId"));
						List<Map<String, Object>> catalogTreeThreeary = catalogMapper.queryCatalogTree(param);
						if(catalogTreeThreeary.size() > 0 && catalogTreeThreeary != null){
							for (Map<String, Object> threeary : catalogTreeThreeary) {
								//查询直属事项清单
								threeary.put("totalCount", catalogMapper.queryCatalogItem(threeary.get("catalogId").toString(),param.get("companyID").toString()));
							}
							secondary.put("catalogTreeThreeary", catalogTreeThreeary);
							//查询下属事项清单
							totalCount = catalogMapper.queryCatalogItemChirld(secondary.get("catalogId").toString(),param.get("companyID").toString());
						}else{
							//查询直属事项清单
							totalCount = catalogMapper.queryCatalogItem(secondary.get("catalogId").toString(),param.get("companyID").toString());
						}
						secondary.put("totalCount", totalCount);
						totalCount_o = totalCount_o + totalCount;
					}
					top.put("totalCount", totalCount_o);
					top.put("catalogTreeSecondary", catalogTreeSecondary);
					allNumber = allNumber + totalCount_o;
				}
			}
		}
		//事项目录树
		dataParam.put("catalogTreeTop", catalogTreeTop);
		//企业名称
		String companyName = catalogMapper.queryCompanyName(param);
		dataParam.put("companyName", companyName);
		//事项清单总数
		dataParam.put("allNumber", allNumber);
		return dataParam;
	}
	
	@Override
	public List<Map<String, Object>> queryCatalogFLevel(Map<String, Object> param) {
		return catalogMapper.queryCatalogFLevel(param);
	}
	
	/**
	 * 平台-事项目录树查询
	 */
	@Override
	public List<Map<String, Object>> queryUmsCatalogList(Map<String, Object> param) throws Exception {
		return catalogMapper.queryUmsCatalogList(param);
	}
	
	/**
	 * 保存事项目录
	 */
	@Override
	public void saveCatalog(Map<String, Object> param) throws Exception {
		param.put("FID", UUID.randomUUID().toString().replaceAll("-", ""));
		catalogMapper.saveCatalog(param);
	}
	
	/**
	 * 修改事项目录
	 */
	@Override
	public void updateCatalog(Map<String, Object> param) throws Exception {
		catalogMapper.updateCatalog(param);
	}
	
	/**
	 * 删除事项目录
	 */
	@Override
	public void deleteCatalog(Map<String, Object> param) throws Exception {
		//验证是否有子级目录
		List<Map<String, Object>> detail =  catalogMapper.queryCatalogListByPid(param);
		if(detail.size() == 0 || detail == null){
			catalogMapper.deleteCatalog(param);
		}
	}
	
	/**
	 * 查询事项目录
	 */
	@Override
	public Map<String, Object> queryCatalog(Map<String, Object> param) throws Exception {
		return catalogMapper.queryCatalog(param);
	}
	
	/**
	 * 分页查询事项目录
	 */
	@Override
	public List<Map<String, Object>> queryUmsCatalogListByPage(Map<String, Object> param,Page<Map<String, Object>> page) throws Exception {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		return catalogMapper.queryUmsCatalogListByPage(param);
	}
	
	/**
	 * 分页查询总数
	 */
	@Override
	public int queryUmsCatalogListByPageCount(Map<String, Object> param) {
		return catalogMapper.queryUmsCatalogListByPageCount(param);
	}
	
	/**
	 * 判断用户是否有权限编辑
	 */
	@Override
	public int queryCatalogRole(Map<String, Object> param) {
		return catalogMapper.queryCatalogRole(param);
	}
	
	/**
	 * 查询所有事项目录To表格
	 */
	@Override
	public Map<String, Object> queryTableCatalogMessage(Map<String, Object> param) {
		Map<String, Object> data = new HashMap<String, Object>();
		//查询级别
		int level = catalogMapper.queryCatalogLevelNumber(param);
		data.put("level", level);
		data.put("cataLog", catalogMapper.queryTableCatalogMessage(param));
		return data;
	}
	
	
}
