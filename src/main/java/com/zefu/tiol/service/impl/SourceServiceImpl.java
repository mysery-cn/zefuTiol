package com.zefu.tiol.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.common.util.CommonUtil;
import com.zefu.tiol.mapper.oracle.SourceMapper;
import com.zefu.tiol.service.SourceService;

/**
 * 
 * @功能描述 任务来源信息实现类
 * @time 2018年10月29日上午11:28:37
 * @author Super
 *
 */
@Service("sourceService")
public class SourceServiceImpl implements SourceService{
	
	@Autowired
	private SourceMapper sourceMapper;


	@Override
	public List<Map<String, Object>> queryList(Map<String, Object> param) throws Exception {
		return sourceMapper.queryList(param);
	}


	@Override
	public void saveSource(Map<String, Object> param) throws Exception {
		param.put("FID", CommonUtil.getUUID());
		sourceMapper.saveSource(param);
	}


	@Override
	public void updateSource(Map<String, Object> param) throws Exception {
		sourceMapper.updateSource(param);
	}


	@Override
	public void deleteSource(Map<String, Object> param) throws Exception {
		sourceMapper.deleteSource(param);
	}


	@Override
	public Map<String, Object> querySourceDetail(Map<String, Object> param) throws Exception {
		return sourceMapper.querySourceDetail(param);
	}

}
