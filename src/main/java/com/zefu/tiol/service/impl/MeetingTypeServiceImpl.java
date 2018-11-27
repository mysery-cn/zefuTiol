package com.zefu.tiol.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.common.util.CommonUtil;
import com.zefu.tiol.mapper.oracle.MeetingTypeMapper;
import com.zefu.tiol.service.MeetingTypeService;

/**
 * 
 * @功能描述 会议类型信息实现类
 * @time 2018年10月29日上午11:27:32
 * @author Super
 *
 */
@Service("meetingTypeService")
public class MeetingTypeServiceImpl implements MeetingTypeService{
	
	@Autowired
	private MeetingTypeMapper meetingTypeService;


	@Override
	public List<Map<String, Object>> queryList(Map<String, Object> param) {
		return meetingTypeService.queryList(param);
	}

	@Override
	public void saveMeetingType(Map<String, Object> param) throws Exception {
		param.put("FID", CommonUtil.getUUID());
		meetingTypeService.saveMeetingType(param);
	}

	@Override
	public void updateMeetingType(Map<String, Object> param) throws Exception {
		meetingTypeService.updateMeetingType(param);
	}

	@Override
	public void deleteMeetingType(Map<String, Object> param) throws Exception {
		meetingTypeService.deleteMeetingType(param);
	}

	@Override
	public Map<String, Object> queryMeetingTypeDetail(Map<String, Object> param) throws Exception {
		return meetingTypeService.queryMeetingTypeDetail(param);
	}

	@Override
	public List<Map<String, Object>> queryMeetingTypeListByPage(Map<String, Object> param) {
		return meetingTypeService.queryMeetingTypeListByPage(param);
	}
	

}
