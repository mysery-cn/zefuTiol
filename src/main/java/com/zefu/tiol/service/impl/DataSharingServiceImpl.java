package com.zefu.tiol.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zefu.tiol.mapper.mysql.DataSharingMapper;
import com.zefu.tiol.mapper.oracle.StatisticsDecisionMapper;
import com.zefu.tiol.mapper.oracle.StatisticsItemMapper;
import com.zefu.tiol.mapper.oracle.StatisticsMeetingMapper;
import com.zefu.tiol.mapper.oracle.StatisticsRegulationMapper;
import com.zefu.tiol.mapper.oracle.StatisticsSubjectMapper;
import com.zefu.tiol.pojo.StatisticsDecisionVo;
import com.zefu.tiol.pojo.StatisticsRegulationVo;
import com.zefu.tiol.service.DataSharingService;

@Service("dataSharingService")
public class DataSharingServiceImpl implements DataSharingService{

	@Autowired
	DataSharingMapper mapper;
	
	@Autowired
	private StatisticsDecisionMapper decisionMapper;
	
	@Autowired
	private StatisticsRegulationMapper regulationMapper;
	
	@Autowired
	private StatisticsItemMapper itemMapper;
	
	@Autowired
	private StatisticsMeetingMapper meetingMapper; 
	
	@Autowired
	private StatisticsSubjectMapper subjectMapper; 

	@Override
	public boolean insertForStatisticsDecision() {
		// TODO Auto-generated method stub
		boolean flag = true;
		try{
			String tableName = "r_db_303127408";
			List<StatisticsDecisionVo> list = decisionMapper.listStatisticsDecision();
			mapper.deleteTableData(tableName);
			if(list != null && list.size() != 0){
				mapper.insertForStatisticsDecision(tableName,list);
			}
		}catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	@Override
	public boolean insertForStatisticsRegulation() {
		// TODO Auto-generated method stub
		boolean flag = true;
		try{
			String tableName = "r_db_303127409";
			List<StatisticsRegulationVo> list = regulationMapper.listStatisticsRegulation();
			mapper.deleteTableData(tableName);
			if(list != null && list.size() != 0){
				mapper.insertForStatisticsRegulation(tableName,list);
			}
		}catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean insertForStatisticsItem() {
		// TODO Auto-generated method stub
		boolean flag = true;
		try{
			String tableName = "r_db_303127410";
			List<Map<String,Object>> list = itemMapper.listStatisticsItem();
			mapper.deleteTableData(tableName);
			if(list != null && list.size() != 0){
				mapper.insertForStatisticsItem(tableName,list);
			}
		}catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean insertForStatisticsMeeting() {
		// TODO Auto-generated method stub
		boolean flag = true;
		try{
			String tableName = "r_db_303127411";
			List<Map<String,Object>> list = meetingMapper.listStatisticsMeeting();
			mapper.deleteTableData(tableName);
			if(list != null && list.size() != 0){
				mapper.insertForStatisticsMeeting(tableName,list);
			}
		}catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean insertForStatisticsSubject() {
		// TODO Auto-generated method stub
		boolean flag = true;
	    try{
		    String tableName = "r_db_303127412";
		    List<Map<String,Object>> list = subjectMapper.listStatisticsSubject();
		    mapper.deleteTableData(tableName);
		    if(list != null && list.size() != 0){
			    mapper.insertForStatisticsSubject(tableName,list);
		    }
	    }catch (Exception e) {
		    e.printStackTrace();
		    flag = false;
	    }
	    return flag;
	}

	@Override
	public boolean insertForStatisticsCpyRegulation() {
		// TODO Auto-generated method stub
		boolean flag = true;
		try{
			String tableName = "r_db_303127414";
			List<Map<String,Object>> list = regulationMapper.listStatisticsCpyRegulation();
			mapper.deleteTableData(tableName);
			if(list != null && list.size() != 0){
				mapper.insertForStatisticsCpyRegulation(tableName,list);
			}
		}catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean insertForStatisticsException() {
		// TODO Auto-generated method stub
		boolean flag = true;
		try{
			String tableName = "r_db_303127413";
			List<Map<String,Object>> list = subjectMapper.listStatisticsException();
			mapper.deleteTableData(tableName);
			if(list != null && list.size() != 0){
				mapper.insertForStatisticsException(tableName,list);
			}
		}catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}


}
