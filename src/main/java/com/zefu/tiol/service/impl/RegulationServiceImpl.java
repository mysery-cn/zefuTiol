package com.zefu.tiol.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.common.vo.AttachmentVo;
import com.yr.gap.dal.plugin.child.Page;
import com.yr.gap.engine.util.StringUtils;
import com.zefu.tiol.constant.BusinessConstant;
import com.zefu.tiol.constant.EBusinessType;
import com.zefu.tiol.mapper.oracle.IntegrationConfigMapper;
import com.zefu.tiol.mapper.oracle.ItemMapper;
import com.zefu.tiol.mapper.oracle.RegulationMapper;
import com.zefu.tiol.pojo.RegulationVote;
import com.zefu.tiol.service.RegulationService;
import com.zefu.tiol.util.DateUtils;
import com.zefu.tiol.util.FastDfsUtils;
import com.zefu.tiol.util.FileUtils;
import com.zefu.tiol.util.HttpClientUtils;
import com.zefu.tiol.util.TiolComnUtils;

/**
 * 
 * @功能描述 制度信息
 * @time 2018年10月26日下午3:24:39
 * @author Super
 *
 */
@Service("regulationService")
public class RegulationServiceImpl implements RegulationService {

	private static Logger logger = LoggerFactory.getLogger(RegulationServiceImpl.class);

	@Autowired
	private RegulationMapper regulationMapper;

	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private IntegrationConfigMapper configMapper;

	@Override
	public List<Map<String, Object>> queryRegulationList(Map<String, Object> param) {
		return regulationMapper.queryRegulationList(param);
	}

	@Override
	public List<Map<String, Object>> queryListByPage(Map<String, Object> parameter, Page<Map<String, Object>> page) {
		int maxRow = page.getCurrentPage() * page.getPageSize();
		int minRow = (page.getCurrentPage() - 1) * page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		List<Map<String, Object>> list = regulationMapper.queryListByPage(parameter);
		return list;
	}

	@Override
	public int getTotalCount(String companyId) {
		return regulationMapper.getTotalCount(companyId);
	}

	/**
	 * 平台查询制度信息列表
	 */
	@Override
	public List<Map<String, Object>> querRegulationPageList(Map<String, Object> parameter,
			Page<Map<String, Object>> page) throws Exception {
		// 查询是否关联下属企业
		List<String> companyID = itemMapper.queryCompanyByInst(parameter);
		if (companyID.size() > 0 && companyID != null) {
			parameter.put("companyID", companyID);
		} else {
			parameter.put("companyID", "");
		}
		// 查询事项清单数据
		int maxRow = page.getCurrentPage() * page.getPageSize();
		;
		int minRow = (page.getCurrentPage() - 1) * page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		return regulationMapper.querRegulationPageList(parameter);
	}

	/**
	 * 平台查询制度信息列表总数
	 */
	@Override
	public int querRegulationPageListCount(Map<String, Object> parameter) throws Exception {
		// 查询是否关联下属企业
		List<String> companyID = itemMapper.queryCompanyByInst(parameter);
		if (companyID.size() > 0 && companyID != null) {
			parameter.put("companyID", companyID);
		} else {
			parameter.put("companyID", "");
		}
		return regulationMapper.querRegulationPageListCount(parameter);
	}

	/**
	 * 查询制度详情
	 */
	@Override
	public Map<String, Object> queryRegulationDetail(Map<String, Object> parameter) {
		// 制度基础信息
		Map<String, Object> detail = regulationMapper.queryRegulationDetail(parameter);
		// 获取佐证材料信息
        List<Map<String, Object>> auditFileList = regulationMapper.queryAuditFileList(parameter);
        if (auditFileList != null && auditFileList.size() > 0) {
            detail.put("auditFileList", auditFileList);
        } else {
            detail.put("auditFileList", "");
        }
		// 制度表决方式
		List<Map<String, Object>> voteModeList = regulationMapper.queryVoteModeList(parameter);
		detail.put("voteModeList", voteModeList);
		return detail;
	}

	/**
	 * 保存制度分类
	 */
	@Override
	public void saveRegulationType(Map<String, Object> param) throws Exception {
		param.put("FID", CommonUtil.getUUID());
		regulationMapper.saveRegulationType(param);
	}

	/**
	 * 删除制度分类
	 */
	@Override
	public void deleteRegulationType(Map<String, Object> param) {
		regulationMapper.deleteRegulationType(param);
	}

	/**
	 * 修改制度分类
	 */
	@Override
	public void updateRegulationType(Map<String, Object> param) {
		regulationMapper.updateRegulationType(param);
	}

	/**
	 * 查询制度分类
	 */
	@Override
	public Map<String, Object> queryRegulationTypeDetail(Map<String, Object> param) {
		return regulationMapper.queryRegulationTypeDetail(param);
	}

	/**
	 * 查询制度分类列表
	 */
	@Override
	public List<Map<String, Object>> queryRegulationTypeListByPage(Map<String, Object> param,
			Page<Map<String, Object>> page) throws Exception {
		int maxRow = page.getCurrentPage() * page.getPageSize();
		int minRow = (page.getCurrentPage() - 1) * page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		return regulationMapper.queryRegulationTypeListByPage(param);
	}

	/**
	 * 查询制度分类总数
	 */
	@Override
	public int queryRegulationTypeTotalCount(Map<String, Object> param) throws Exception {
		return regulationMapper.queryRegulationTypeTotalCount(param);
	}

	@Override
	public void updateRegulation(Map<String, Object> param) {
		// TODO Auto-generated method stub
		regulationMapper.updateRegulation(param);
	}

	/**
	 * 新增制度
	 * 
	 * @param param
	 * @return
	 */
	@Override
	public int insertRegulation(Map<String, Object> param) {
		return regulationMapper.insertRegulation(param);
	}

	/**
	 * 批量插入制度表决
	 *
	 * @param list
	 * @return
	 */
	@Override
	public int batchInsertRegulationVote(List<RegulationVote> list) {
		return regulationMapper.batchInsertRegulationVote(list);
	}

	/**
	 * 查询制度分类
	 */
	@Override
	public List<Map<String, Object>> queryRegulationTypeList(Map<String, Object> parameter) {
		return regulationMapper.queryRegulationTypeList(parameter);
	}

	@Override
	public String uploadRegulation(Map<String, Object> param, String companyId, String urlStr, String userName) {
		String[] ids = (String[]) param.get("ids");
		List<Map<String, Object>> regulationList = regulationMapper.queryRegulationByIds(ids, companyId);
		int success = 0;
		for (Map<String, Object> regulation : regulationList) {
			JSONObject regulationJson = new JSONObject();
			regulationJson.put("regulation_id", regulation.get("REGULATION_ID"));
			regulationJson.put("regulation_name", regulation.get("REGULATION_NAME"));
			regulationJson.put("regulation_type_name", regulation.get("TYPE_NAME"));
			regulationJson.put("approval_date", regulation.get("APPROVAL_DATE"));
			regulationJson.put("effective_date", regulation.get("EFFECTIVE_DATE"));
			String auditFlag = String.valueOf(regulation.get("AUDIT_FLAG"));
			if ("1".equals(auditFlag)) {
				auditFlag = "是";
			} else if ("0".equals(auditFlag)) {
				auditFlag = "否";
			}
			regulationJson.put("audit_flag", auditFlag);
			regulationJson.put("rate", regulation.get("RATE"));
			regulationJson.put("remark", regulation.get("REMARK"));

			// 读取制度文件
			String fileId = String.valueOf(regulation.get("FILE_ID"));
			if (StringUtils.isEmpty(fileId)) {
				logger.warn("制度[" + regulation.get("REGULATION_NAME") + "]未找到制度文件");
				continue;
			}
			String status = regulation.get("STATUS").toString();
			String operType = "add";
			if (status.equals("0")) {
				operType = "del";
			} else if (status.equals("2")) {
				operType = "edit";
			}
			regulationJson.put("oper_type", operType);

			// 获取表决方式列表
			Map<String, Object> parameter = new HashMap<String, Object>();
			parameter.put("regulationID", regulation.get("REGULATION_ID"));
			JSONArray meetingJsonArray = new JSONArray();
			List<Map<String, Object>> voteModeList = regulationMapper.queryVoteModeList(parameter);
			for (Map<String, Object> voteMode : voteModeList) {
				meetingJsonArray.add(new JSONObject()
						.put("item_code",
								StringUtils.isEmpty(voteMode.get("itemCode")) ? "默认" : voteMode.get("itemCode"))
						.put("vote_mode", voteMode.get("modeName")));
			}
			regulationJson.put("vote_mode_list", new JSONObject().put("vote_mode", meetingJsonArray));
			// 生成xml
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ XML.toString(new JSONObject().put("tiol", regulationJson));
			// 获取路径
			String outPath = this.getClass().getClassLoader().getResource("/").getPath() + "../../uploadFile";// ItemService.class.getResource(File.separator).toString();
			// 文件名
			String xmlFileName = TiolComnUtils.generalFileName(BusinessConstant.UNDERLINE_COODE,
					EBusinessType.REGULATION.getCode(), BusinessConstant.VERSION, DateUtils.getFormatTime("yyyyMMdd"));
			// 创建文件夹

			File filePath = new File(outPath + File.separator + xmlFileName);
			filePath.mkdirs();
			// 创建“正式文件”文件夹
			File normalFilePath = new File(filePath.getPath() + File.separator + "正式文件");
			normalFilePath.mkdir();
			// 创建“佐证材料”文件夹
			File materialFilePath = new File(filePath.getPath() + File.separator + "佐证材料");
			materialFilePath.mkdir();

			String outXmlFilePath = filePath.getPath() + File.separator + xmlFileName + BusinessConstant.DOT_CODE
					+ BusinessConstant.XML_SUFFIX;
			URL url = null;
			try {
				// 生成xml文件
				FileUtils.writeContent(outXmlFilePath, xml);
				// 获取制度文件
				String fileName = String.valueOf(regulation.get("FILE_NAME"));
				AttachmentVo attachment = new AttachmentVo();
				attachment.setFileId(fileId);
				attachment.setFileName(fileName);
				urlStr += "/gapServlet?action=fsManageServlet&fUser=zfos&requesttype=download&fv=1&fid="+ fileId;
				InputStream is = FastDfsUtils.downloadFile(urlStr, attachment);
				// 为file生成对应的文件输出流
				FileOutputStream fos = new FileOutputStream(
						new File(normalFilePath.getPath() + File.separator + fileName));
				int count = 0;
				byte[] buff = new byte[1024 * 8];
				while ((count = is.read(buff)) != -1) {
					fos.write(buff, 0, count);
				}
				is.close();
				fos.flush();
				fos.close();

				// 获取佐证材料
				List<Map<String, Object>> mFileList = regulationMapper
						.getMaterialFile(regulation.get("REGULATION_ID").toString());
				for (Map<String, Object> mFileMap : mFileList) {
					fileName = String.valueOf(mFileMap.get("ATTACHMENT_NAME"));
					fileId = String.valueOf(mFileMap.get("FILE_ID"));
					attachment = new AttachmentVo();
					attachment.setFileId(fileId);
					attachment.setFileName(fileName);
					urlStr += "/gapServlet?action=fsManageServlet&fUser=zfos&requesttype=download&fv=1&fid="+ fileId;
					is = FastDfsUtils.downloadFile(urlStr, attachment);
					// 为file生成对应的文件输出流
					fos = new FileOutputStream(
							new File(normalFilePath.getPath() + File.separator + fileName));
					count = 0;
					buff = new byte[1024 * 8];
					while ((count = is.read(buff)) != -1) {
						fos.write(buff, 0, count);
					}
					is.close();
					fos.flush();
					fos.close();
				}
				// 压缩文件夹
				File zipFile = new File(outPath + File.separator + xmlFileName + BusinessConstant.DOT_CODE
						+ BusinessConstant.ZIP_SUFFIX);
				fos = new FileOutputStream(zipFile);
				FileUtils.toZipWithoutRoot(filePath.getPath(), fos, true);

				// 调用大额资金报送系统通道进行上报
				Map<String, Object> paramMap = new HashMap<String, Object>();
				Map<String, Object> configMap = configMapper.getInterfaceConfig();
				String uploadUrl = "";
				String uploadUser = "";
				String uploadPassword = "";
				if(null == configMap|| configMap.isEmpty()) {
					return "上传接口未配置";
				}else {
					uploadUrl = String.valueOf(configMap.get("ADDRESS"));
					uploadUser = String.valueOf(configMap.get("USER_NAME"));
					uploadPassword = String.valueOf(configMap.get("PASSWORD"));
					if(StringUtils.isEmpty(uploadUrl)||StringUtils.isEmpty(uploadUser)
							||StringUtils.isEmpty(uploadPassword)) {
						return "上传接口未配置";
					}
				}
						
				paramMap.put("USER", uploadUser);
				paramMap.put("PASSWORD", uploadPassword);
				// 业务类型
				paramMap.put("BUSINESS_TYPE", EBusinessType.REGULATION.getCode());
				paramMap.put("API_CODE", BusinessConstant.API_CODE);
				paramMap.put("FILE_NAME", zipFile.getName());

				String result = HttpClientUtils.upload(uploadUrl, zipFile.getPath(), paramMap);
				logger.info("【制度上传】上传返回:"+result);
				JSONObject resJson = new JSONObject(result);
				if (resJson.has("CODE")) {
					if (resJson.getString("CODE").equals("1")) {
						success++;
						// 更新数据库
						paramMap.clear();
						paramMap.put("REGULATION_ID", regulation.get("REGULATION_ID"));
						if (!StringUtils.isEmpty(regulation.get("UPLOAD_STATUS"))
								&& "1".equals(regulation.get("UPLOAD_STATUS"))) {
							paramMap.put("UPLOAD_STATUS", "2");
						} else {
							paramMap.put("UPLOAD_STATUS", "1");
						}
						paramMap.put("UPDATE_TIME", DateUtils.getFormatTime());
						paramMap.put("UPDATE_USER", userName);
						regulationMapper.updateRegulation(paramMap);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "共计 " + ids.length + " 条，上报成功 " + success + " 条";
	}

}
