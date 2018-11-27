package com.zefu.tiol.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zefu.tiol.mapper.oracle.PreDataMapper;
import com.zefu.tiol.pojo.PreItem;
import com.zefu.tiol.pojo.PreMeeting;
import com.zefu.tiol.pojo.PreRegulation;
import com.zefu.tiol.pojo.PreSubject;
import com.zefu.tiol.service.PreDataService;

/**
 * 四类业务数据前置表综合操作接口实现
 * @author linch
 * @date	2018-11-08 
 */
@Service("preDataService")
public class PreDataServiceImpl implements PreDataService{

	@Autowired
	private PreDataMapper preDataMapper;
	
	@Override
	public void savePreItem(PreItem preItem) {
		// TODO Auto-generated method stub
		preDataMapper.savePreItem(preItem);
	}

	@Override
	public void savePreRegulation(PreRegulation preRegulation) {
		// TODO Auto-generated method stub
		preDataMapper.savePreRegulation(preRegulation);
	}

	@Override
	public void savePreMeeting(PreMeeting preMeeting) {
		// TODO Auto-generated method stub
		preDataMapper.savePreMeeting(preMeeting);
	}

	@Override
	public void batchInsertPreSubject(List<PreSubject> preSubjectList) {
		// TODO Auto-generated method stub
		preDataMapper.batchInsertPreSubject(preSubjectList);
	}

	@Override
	public List<PreItem> getUndealItem() {
		// TODO Auto-generated method stub
		return preDataMapper.getUndealItem();
	}

	@Override
	public List<PreRegulation> getUndealRegulation() {
		// TODO Auto-generated method stub
		return preDataMapper.getUndealRegulation();
	}

	@Override
	public List<PreMeeting> getUndealMeeting() {
		// TODO Auto-generated method stub
		return preDataMapper.getUndealMeeting();
	}

	@Override
	public List<PreSubject> getUndealSubject() {
		// TODO Auto-generated method stub
		return preDataMapper.getUndealSubject();
	}

	@Override
	public void updatePreItem(PreItem preItem) {
		// TODO Auto-generated method stub
		preDataMapper.updatePreItem(preItem);
	}

	@Override
	public void updatePreRegulation(PreRegulation preRegulation) {
		// TODO Auto-generated method stub
		preDataMapper.updatePreRegulation(preRegulation);
	}

	@Override
	public void updatePreMeeting(PreMeeting preMeeting) {
		// TODO Auto-generated method stub
		preDataMapper.updatePreMeeting(preMeeting);
	}

	@Override
	public void updatePreSubject(PreSubject preSubject) {
		// TODO Auto-generated method stub
		preDataMapper.updatePreSubject(preSubject);
	}
	
	@Override
	public int checkItemIsExist(String itemCode, String catalogId, String companyId) {
		return preDataMapper.checkItemIsExist(itemCode, catalogId, companyId);
	}
	
	@Override
	public int checkRegulationExist(String regulationId, String typeId, String companyId, String regulationName) {
		return preDataMapper.checkRegulationExist(regulationId, typeId, companyId, regulationName);
	}
	
	

}
