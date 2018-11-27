package com.zefu.tiol.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.ReformMapper;
import com.zefu.tiol.service.ReformService;

/**
   * @工程名 : szyd
   * @文件名 : ReformServiceImpl.java
   * @工具包名：com.zefu.tiol.service.impl
   * @功能描述: TODO
   * @创建人 ：林鹏 改革局功能模块实现层
   * @创建时间：2018年10月30日 下午2:31:01
   * @版本信息：V1.0
 */
@Service("reformService")
public class ReformServiceImpl implements ReformService {
	
	@Autowired
	private ReformMapper reformMapper;
	
	/**
	 * 查询会议议题数据
	 */
	@Override
	public List<Map<String, Object>> querySubjectPageList(Map<String, Object> parameter,Page<Map<String, Object>> page) {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		List<Map<String, Object>> list = reformMapper.querySubjectPageList(parameter);
		return list;
	}
	
	/**
	 * 查询会议议题总数
	 */
	@Override
	public int querySubjectTotalCount(Map<String, Object> parameter) {
		return reformMapper.querySubjectTotalCount(parameter);
	}


	@Override
	public int getMeetingSubjectTotalCount(Map<String, Object> param) {
		return reformMapper.getMeetingSubjectTotalCount(param);
	}
}
