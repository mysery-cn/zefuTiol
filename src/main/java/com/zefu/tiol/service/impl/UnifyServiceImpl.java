package com.zefu.tiol.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.UnifyMapper;
import com.zefu.tiol.service.UnifyService;

/**
 * 
 * @功能描述 会议信息及议题信息统一查询实现类
 * @time 2018年11月4日下午12:53:47
 * @author Super
 *
 */
@Service("unifyService")
public class UnifyServiceImpl implements UnifyService{
	
	@Autowired
	private UnifyMapper unifyMapper;

	@Override
	public List<Map<String, Object>> queryMeetingList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return unifyMapper.queryMeetingList(param);
	}

	@Override
	public List<Map<String, Object>> queryMeetingByPage(
			Map<String, Object> param, Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		int maxRow = page.getCurrentPage()*page.getPageSize();
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		List<Map<String, Object>> result = unifyMapper.queryMeetingByPage(param);
		return result;
	}

	@Override
	public int getyMeetingTotalCount(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return unifyMapper.getyMeetingTotalCount(param);
	}

	@Override
	public List<Map<String, Object>> querySubjectList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return unifyMapper.queryMeetingList(param);
	}

	@Override
	public List<Map<String, Object>> querySubjectByPage(
			Map<String, Object> param, Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		int maxRow = page.getCurrentPage()*page.getPageSize();
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		List<Map<String, Object>> result = unifyMapper.querySubjectByPage(param);
		return result;
	}

	@Override
	public int getSubjectTotalCount(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return unifyMapper.getSubjectTotalCount(param);
	}

}
