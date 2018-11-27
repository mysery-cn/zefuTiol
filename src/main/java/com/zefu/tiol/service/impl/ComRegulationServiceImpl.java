package com.zefu.tiol.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.common.core.LoginUser;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.zefu.tiol.mapper.oracle.AttachmentMapper;
import com.zefu.tiol.mapper.oracle.ComItemMapper;
import com.zefu.tiol.mapper.oracle.ComRegulationMapper;
import com.zefu.tiol.pojo.RegulationVote;
import com.zefu.tiol.service.ComRegulationService;
import com.zefu.tiol.vo.TiolRegulationVO;
import com.zefu.tiol.vo.TiolVoteModeVO;

/**
 * 
 * @功能描述 企业版-制度信息
 * @time 2018年10月26日下午3:24:39
 * @author Super
 *
 */
@Service("comRegulationService")
public class ComRegulationServiceImpl implements ComRegulationService {
	
	@Autowired
	private ComRegulationMapper comRegulationMapper;
	
	@Autowired
	private ComItemMapper comItemMapper;
	
	@Autowired
	private AttachmentMapper attachmentMapper;

	@Override
	public List<Map<String, Object>> queryRegulationList(Map<String, Object> param) {
		return comRegulationMapper.queryRegulationList(param);
	}

	@Override
	public List<Map<String, Object>> queryListByPage(Map<String, Object> parameter, Page<Map<String, Object>> page) {
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		List<Map<String, Object>> list = comRegulationMapper.queryListByPage(parameter);
		return list;
	}
	
	@Override
	public int getTotalCount(String companyId) {
		return comRegulationMapper.getTotalCount(companyId);
	}
	
	/**
	 * 平台查询制度信息列表
	 */
	@Override
	public List<Map<String, Object>> querRegulationPageList(Map<String, Object> parameter,Page<Map<String, Object>> page) throws Exception {
		//查询是否关联下属企业
		List<String> companyID = comItemMapper.queryCompanyByInst(parameter);
		LoginUser loginUser = (LoginUser) parameter.get("CURRENT_USER");
		companyID.add(loginUser.getCompanyId());
		if(companyID.size() > 0 && companyID != null){
			parameter.put("companyID", companyID);
		}else{
			parameter.put("companyID", "");
		}
		//查询事项清单数据
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		return comRegulationMapper.querRegulationPageList(parameter);
	}
	
	/**
	 * 平台查询制度信息列表总数
	 */
	@Override
	public int querRegulationPageListCount(Map<String, Object> parameter) throws Exception {
		//查询是否关联下属企业
		List<String> companyID = comItemMapper.queryCompanyByInst(parameter);
		if(companyID.size() > 0 && companyID != null){
			parameter.put("companyID", companyID);
		}else{
			parameter.put("companyID", "");
		}
		return comRegulationMapper.querRegulationPageListCount(parameter);
	}
	
	/**
	 * 查询制度详情
	 */
	@Override
	public Map<String, Object> queryRegulationDetail(Map<String, Object> parameter) {
		//制度基础信息
		Map<String, Object> detail = comRegulationMapper.queryRegulationDetail(parameter);
		//获取佐证材料信息
		if(detail.get("auditFileId") != null){
			if(!("").equals(detail.get("auditFileId").toString())){
				parameter.put("fileIds", detail.get("auditFileId").toString().split(","));
				List<Map<String, Object>> auditFileList = comRegulationMapper.queryAuditFileList(parameter);
				if(auditFileList != null && auditFileList.size() > 0){
					detail.put("auditFileList", auditFileList);
				}
			}
			else{
				detail.put("auditFileList", "");
			}
		}else{
			detail.put("auditFileList", "");
		}
		//制度表决方式
		List<Map<String, Object>> voteModeList = comRegulationMapper.queryVoteModeList(parameter);
		detail.put("voteModeList", voteModeList);
		return detail;
	}
	
	/**
	 * 保存制度分类
	 */
	@Override
	public void saveRegulationType(Map<String, Object> param) throws Exception {
		param.put("FID", CommonUtil.getUUID());
		comRegulationMapper.saveRegulationType(param);
	}
	
	/**
	 * 删除制度分类
	 */
	@Override
	public void deleteRegulationType(Map<String, Object> param) {
		comRegulationMapper.deleteRegulationType(param);
	}
	
	/**
	 * 修改制度分类
	 */
	@Override
	public void updateRegulationType(Map<String, Object> param) {
		comRegulationMapper.updateRegulationType(param);
	}
	
	/**
	 * 查询制度分类
	 */
	@Override
	public Map<String, Object> queryRegulationTypeDetail(Map<String, Object> param) {
		return comRegulationMapper.queryRegulationTypeDetail(param);
	}
	
	/**
	 * 查询制度分类列表
	 */
	@Override
	public List<Map<String, Object>> queryRegulationTypeListByPage(Map<String, Object> param,Page<Map<String, Object>> page) throws Exception{
		int maxRow = page.getCurrentPage()*page.getPageSize();;
		int minRow = (page.getCurrentPage()-1)*page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		return comRegulationMapper.queryRegulationTypeListByPage(param);
	}
	
	/**
	 * 查询制度分类总数
	 */
	@Override
	public int queryRegulationTypeTotalCount(Map<String, Object> param) throws Exception {
		return comRegulationMapper.queryRegulationTypeTotalCount(param);
	}

	@Override
	public void updateRegulation(Map<String, Object> param) {
		// TODO Auto-generated method stub
		comRegulationMapper.updateRegulation(param);
	}

	/**
	 * 新增制度
	 * @param param
	 * @return
	 */
	@Override
	public int insertRegulation(Map<String, Object> param) {
		return comRegulationMapper.insertRegulation(param);
	}

	/**
	 * 批量插入制度表决
	 *
	 * @param list
	 * @return
	 */
	@Override
	public int batchInsertRegulationVote(List<RegulationVote> list) {
		return comRegulationMapper.batchInsertRegulationVote(list);
	}

	/**
	 * 获取制度类型Map
	 * @return
	 */
	Map<String,Object> getRegulationTypeMap(){
		Map<String,Object> returnMap = new LinkedHashMap<String, Object>();
		List<Map<String,Object>> list = this.comRegulationMapper.queryRegulationTypeList();
		if(list != null && list.size() > 0){
			for (Map<String, Object> map : list) {
				if(map.get("typeName") != null){
					returnMap.put((String)map.get("typeName"),map.get("typeId"));
				}
			}
		}
		return returnMap;
	}

	/**
	 * 获取企业下事项全部数据
	 * @return
	 */
	Map<String,Object> getItemMap(Map<String,Object> param){
		Map<String,Object> returnMap = new LinkedHashMap<String, Object>();
		List<Map<String,Object>> list = this.comRegulationMapper.queryItemList(param);
		if(list != null && list.size() > 0){
			for (Map<String, Object> map : list) {
				if(map.get("itemCode") != null){
					returnMap.put((String)map.get("itemCode"),map.get("itemId"));
				}
			}
		}
		return returnMap;
	}
	
	/**
	 * 获取表决方式
	 * @return
	 */
	Map<String,Object> getVoteModeMap(){
		Map<String,Object> returnMap = new LinkedHashMap<String, Object>();
		List<Map<String,Object>> list = this.comRegulationMapper.queryVoteModeListSimple();
		if(list != null && list.size() > 0){
			for (Map<String, Object> map : list) {
				if(map.get("modeName") != null){
					returnMap.put((String)map.get("modeName"),map.get("modeId"));
				}
			}
		}
		return returnMap;
	}
	
	/**
	 * Excel导入制度
	 * 
	 * @throws Exception
	 */
	@Override
	public String saveImportRegulation(List<TiolRegulationVO> list, Map<String, Object> param) throws Exception {
		if (null == list)
			return "";
		StringBuffer errorInfo = new StringBuffer();
		// 制度类型Map
		Map<String, Object> regualtionTypeMap = this.getRegulationTypeMap();
		// 企业所有事项
		Map<String, Object> itemMap = this.getItemMap(param);
		// 表决方式
		Map<String, Object> voteModeMap = this.getVoteModeMap();
		// check先进行检验
		for (TiolRegulationVO vo : list) {
			String name = vo.getName();
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("regulationName", vo.getName());
			queryMap.put("CURRENT_USER", param.get("CURRENT_USER"));
			if (this.comRegulationMapper.isExistsRegulation(queryMap) > 0) {
				errorInfo.append("第");
				errorInfo.append((vo.getRow() + 1));
				errorInfo.append("行");
				errorInfo.append("已经存在此制度:" + name + "\n");
			}

			String typeName = vo.getType();
			if (null == regualtionTypeMap.get(typeName)) {
				errorInfo.append("第");
				errorInfo.append((vo.getRow() + 1));
				errorInfo.append("行");
				errorInfo.append("没有制度类型:" + typeName + "\n");
			}

			List<TiolVoteModeVO> voteModeList = vo.getVoteModeList();
			for (TiolVoteModeVO voteModeVO : voteModeList) {
				String itemCode = voteModeVO.getItemCode();
				String modeName = voteModeVO.getMode();
				if (null != itemCode && !"".equals(itemCode) && !"默认".equals(itemCode) && !"-".equals(itemCode)) {
					if (null == itemMap.get(itemCode)) {
						errorInfo.append("第");
						errorInfo.append((voteModeVO.getRow() + 1));
						errorInfo.append("行");
						errorInfo.append("没有事项清单:" + itemCode + "\n");
					}
				}
				if (null != modeName && !"-".equals(modeName)) {
					if (null == voteModeMap.get(modeName)) {
						errorInfo.append("第");
						errorInfo.append((voteModeVO.getRow() + 1));
						errorInfo.append("行");
						errorInfo.append("没有投票方式:" + voteModeVO.getMode());
					}
				}
			}
		}
		if (errorInfo.length() > 0 && null != errorInfo.toString()) {
			return errorInfo.toString();
		}

		for (TiolRegulationVO vo : list) {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("regulationName", vo.getName());
			queryMap.put("CURRENT_USER", param.get("CURRENT_USER"));

			if (this.comRegulationMapper.isExistsRegulation(queryMap) > 0) {
				throw new Exception("已经存在此制度:" + vo.getName());
			}

			Map<String, Object> insertParam = new HashMap<String, Object>();

			String regulationId = UUID.randomUUID().toString().replace("-", "");

			insertParam.put("regulationId", regulationId);
			insertParam.put("regulationName", vo.getName());
			insertParam.put("typeCode", regualtionTypeMap.get(vo.getType()));
			insertParam.put("regulationName", vo.getName());
			insertParam.put("CURRENT_USER", param.get("CURRENT_USER"));
			if (vo.getApprovalDate() != null && !"".equalsIgnoreCase(vo.getApprovalDate()) && !"-".equalsIgnoreCase(vo.getApprovalDate())) {
				insertParam.put("approvalDate", vo.getApprovalDate().replace("/", "-"));
			}
			if (vo.getEffectiveDate() != null && !"".equalsIgnoreCase(vo.getEffectiveDate()) && !"-".equalsIgnoreCase(vo.getEffectiveDate())) {
				
				insertParam.put("effectiveDate", vo.getEffectiveDate().replace("/", "-"));
			}
			if (vo.getAuditFlag() != null && "是".equalsIgnoreCase(vo.getAuditFlag())) {
				insertParam.put("auditFlag", 1);
			} else {
				insertParam.put("auditFlag", 0);
			}
			if(vo.getRate() != null && !"".equalsIgnoreCase(vo.getRate())){
				if(!"-".equalsIgnoreCase(vo.getRate())){
					insertParam.put("rate", vo.getRate());
				}
			}
			insertParam.put("remark", vo.getRemark());

			List<RegulationVote> vlist = new ArrayList<RegulationVote>();
			List<TiolVoteModeVO> voteModeList = vo.getVoteModeList();
			
			for (TiolVoteModeVO voteModeVO : voteModeList) {
				String itemCode = voteModeVO.getItemCode();
				String mode = voteModeVO.getMode();
				
				if(itemCode != null && !"".equalsIgnoreCase(itemCode)){
					if("-".equalsIgnoreCase(itemCode)){
						continue;
					}
				}
				if(mode != null && !"".equalsIgnoreCase(mode)){
					if("-".equalsIgnoreCase(mode)){
						continue;
					}
				}
				RegulationVote regulationVote = new RegulationVote();
				regulationVote.setVoteId(UUID.randomUUID().toString().replace("-", ""));
				regulationVote.setRegulationId(regulationId);
				if(itemMap.get(voteModeVO.getItemCode()) != null){
					regulationVote.setItemId((String) itemMap.get(voteModeVO.getItemCode()));
				}
				if(voteModeMap.get(voteModeVO.getMode()) != null){
					regulationVote.setModeId((String) voteModeMap.get(voteModeVO.getMode()));
				}
				vlist.add(regulationVote);
			}
			this.comRegulationMapper.insertImportRegulation(insertParam);
			if(vlist.size() > 0){
				this.batchInsertRegulationVote(vlist);
			}
			
		}
		return "";
	}

	/**
	 * 删除制度
	 */
	@Override
	public void delete(Map<String, Object> param) {
		List<Map<String,Object>> deleteList = this.comRegulationMapper.queryRegulationByIds(param);
		List<Object> realDelIdsList = new ArrayList<Object>();
		List<Object> recycleIdsList = new ArrayList<Object>();
		//判断事项未上传为物理删除(uploadStatus=0、status=1or2)、已上传为逻辑删除(uploadStatus=1、status=1or2)
		if (deleteList != null && deleteList.size() > 0) {
			for (Map<String, Object> map : deleteList) {
				String uploadStatus = (String) map.get("UPLOAD_STATUS");
				String status = (String) map.get("STATUS");
				if(uploadStatus != null && "0".equalsIgnoreCase(uploadStatus)){
					if(status != null && !"0".equalsIgnoreCase(status)){
						realDelIdsList.add(map.get("REGULATION_ID"));
					}
				}else if(uploadStatus != null && "1".equalsIgnoreCase(uploadStatus)){
					recycleIdsList.add(map.get("REGULATION_ID"));
				}
			}
		}
		
		//执行物理删除
		if(realDelIdsList.size() > 0){
			Map<String, Object> realDelParam = new HashMap<String, Object>();
			realDelParam.put("ids", realDelIdsList);
			comRegulationMapper.deleteRegulation(realDelParam);
			comRegulationMapper.deleteRegulationVote(realDelParam);
		}
		//申请删除，修改状态
		if(recycleIdsList.size() > 0){
			Map<String, Object> recycleParam = new HashMap<String, Object>();
			recycleParam.put("ids", recycleIdsList);
			recycleParam.put("status", "0");
			recycleParam.put("uploadStatus", "0");
			comRegulationMapper.updateRegulationStatusByids(recycleParam);
		}

	}

	/**
	 * 删除制度
	 */
	@Override
	public void remove(Map<String, Object> param) {
		this.comRegulationMapper.deleteRegulation(param);
		this.comRegulationMapper.deleteRegulationVote(param);
	}
	
	/**
	 * 获取投票表决方式
	 */
	@Override
	public List<Map<String, Object>> queryVoteModelList(Map<String, Object> param) {
		return comRegulationMapper.queryVoteModeListSimple();
	}

	/**
	 * 根据ID查询制度
	 *
	 * @param regulationID
	 * @return
	 * @author 刘效
	 */
	@Override
	public Map<String, Object> queryRegulationById(String regulationID) {
		Map<String, Object> returnMap = comRegulationMapper.queryRegulationById(regulationID);
		if(returnMap != null && returnMap.get("FILE_ID") != null){
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("fileId", returnMap.get("FILE_ID"));
			List<Map<String, Object>> attachmentList= this.attachmentMapper.getFileByFileId(queryMap);
			if(attachmentList != null && attachmentList.size() > 0){
				for (Map<String, Object> map : attachmentList) {
					returnMap.put("fileName", map.get("attachmentName"));
					returnMap.put("fileId", map.get("fileId"));
					break;
				}
			}
		}
		return returnMap;
	}

	/**
	 * 根据制度id查询事项表决方式
	 *
	 * @param regulationId
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryItemVotesByRegulationId(String regulationId) {
		return comRegulationMapper.queryItemVotesByRegulationId(regulationId);
	}

	/**
	 * 还原删除数据
	 */
	@Override
	public void restore(Map<String, Object> param) {
		param.put("status", "1");
		param.put("uploadStatus", "0");
		this.comRegulationMapper.updateRegulationStatusByids(param);
	}

	/**
	 * 根据制度id删除事项表决方式
	 *
	 * @param regulationId
	 * @return
	 * @author 刘效
	 */
	@Override
	public int deleteRegulationVote(String regulationId) {
		return comRegulationMapper.deleteRegulationVote2(regulationId);
	}

	/**
	 * 更新制度
	 *
	 * @param param
	 * @return
	 * @author 刘效
	 */
	@Override
	public int updateRegulation2(Map<String, Object> param) {
		return comRegulationMapper.updateRegulation2(param);
	}
}
