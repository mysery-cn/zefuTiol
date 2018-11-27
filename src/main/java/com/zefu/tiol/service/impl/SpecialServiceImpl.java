package com.zefu.tiol.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.SpecialMapper;
import com.zefu.tiol.service.SpecialService;

@Service("specialService")
public class SpecialServiceImpl implements SpecialService {

	@Autowired
	private SpecialMapper specialMapper;
	
	
	@Override
	public List<Map<String, Object>> querySpecialList(Map<String, Object> param, Page<Map<String, Object>> page)
			throws Exception {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		return specialMapper.querySpecialList(param);
	}

	@Override
	public void saveSpecial(Map<String, Object> param) throws Exception {
		param.put("FID", CommonUtil.getUUID());
		specialMapper.saveSpecial(param);
	}

	@Override
	public void updateSpecial(Map<String, Object> param) throws Exception {
		specialMapper.updateSpecial(param);
	}

	@Override
	public void deleteSpecial(Map<String, Object> param) throws Exception {
		specialMapper.deleteSpecial(param);
	}

	@Override
	public Map<String, Object> querySpecialDetail(Map<String, Object> param) throws Exception {
		return specialMapper.querySpecialDetail(param);
	}

}
