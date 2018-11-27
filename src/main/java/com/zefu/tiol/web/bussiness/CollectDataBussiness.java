package com.zefu.tiol.web.bussiness;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.engine.util.StringUtils;
import com.zefu.tiol.constant.CollectDataConstant;
import com.zefu.tiol.pojo.Attachment;
import com.zefu.tiol.pojo.Attendance;
import com.zefu.tiol.pojo.Attendee;
import com.zefu.tiol.pojo.Deliberation;
import com.zefu.tiol.pojo.Item;
import com.zefu.tiol.pojo.ItemMeeting;
import com.zefu.tiol.pojo.Meeting;
import com.zefu.tiol.pojo.PreItem;
import com.zefu.tiol.pojo.PreMeeting;
import com.zefu.tiol.pojo.PreRegulation;
import com.zefu.tiol.pojo.PreSubject;
import com.zefu.tiol.pojo.Regulation;
import com.zefu.tiol.pojo.RegulationVote;
import com.zefu.tiol.pojo.Subject;
import com.zefu.tiol.service.BizDBService;
import com.zefu.tiol.service.ItemService;
import com.zefu.tiol.service.MeetingService;
import com.zefu.tiol.service.PreDataService;
import com.zefu.tiol.service.RegulationService;
import com.zefu.tiol.service.SubjectService;
import com.zefu.tiol.util.DateUtils;
import com.zefu.tiol.util.FileUtils;
import com.zefu.tiol.util.MapTrunPojo;
import com.zefu.tiol.util.SambaUtil;
import com.zefu.tiol.util.ValidateCorporationID;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CollectDataBussiness {
	private static Logger logger = LoggerFactory.getLogger(CollectDataBussiness.class);

	private static Set<String> fileDownSet = new HashSet<String>();// 记录已下载的日期文件夹

	private PreDataService preDataService;
	private BizDBService bizDBService;
	private ItemService itemService;
	private RegulationService regulationService;
	private MeetingService meetingService;
	private SubjectService subjectService;

	/**
	 * 缓存常用数据，提高数据采集效率
	 */
	public static Map<String, String> meetingTypeMap = new HashMap<String, String>();// <会议类型,会议类型ID>
	public static Map<String, String> catalogMap = new HashMap<String, String>();// <事项目录编码,事项目录ID>
	public static Map<String, String> regulaTypeMap = new HashMap<String, String>();// <制度类型名称,制度类型ID>
	public static Map<String, String> voteModeMap = new HashMap<String, String>();// <表决方式名称,表决方式ID>
	public static Map<String, String> sourceMap = new HashMap<String, String>();// <任务来源名称,任务来源ID>
	public static Map<String, String> specialMap = new HashMap<String, String>();// <专项名称,专项ID>
	public static Map<String, String> passFlagMap = new HashMap<String, String>();// <是否通过描述,对应值(0/1/2)>
	public static Map<String, String> approvalFlagMap = new HashMap<String, String>();// <是否上报描述,对应值(0/1/2)>

//	public static Map<String, String> meetingMap = new HashMap<String, String>();// <会议sid,会议ID>

	private String filePath; // 共享文件夹路径
	private String host; // 共享文件夹路径
	private String username; // 共享文件夹路径
	private String password; // 共享文件夹路径
	private String localPath; // 共享文件夹路径

	// 业务类初始化
	public CollectDataBussiness(BizDBService bizDBService, PreDataService preDataService, ItemService itemService,
			RegulationService regulationService, MeetingService meetingService, SubjectService subjectService) {
		// 持久层初始化
		// this.bizDataService = bizDataService;
		this.bizDBService = bizDBService;
		this.preDataService = preDataService;
		this.itemService = itemService;
		this.regulationService = regulationService;
		this.meetingService = meetingService;
		this.subjectService = subjectService;

		// 配置信息初始化
		if (StringUtils.isEmpty(filePath)) {
			getProperties();
		}

		// 缓存信息初始化
		if (meetingTypeMap.isEmpty()) {
			initMap();
		}
	}

	// 配置信息初始化
	private void getProperties() {
		// TODO Auto-generated method stub
		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getResourceAsStream("/platform.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		filePath = StringUtils.isEmpty(properties.getProperty("collect_data_path")) ? ""
				: properties.getProperty("collect_data_path");
		localPath = StringUtils.isEmpty(properties.getProperty("collect_data_localPath")) ? ""
				: properties.getProperty("collect_data_localPath");
		host = StringUtils.isEmpty(properties.getProperty("collect_data_host")) ? ""
				: properties.getProperty("collect_data_host");
		username = StringUtils.isEmpty(properties.getProperty("collect_data_username")) ? ""
				: properties.getProperty("collect_data_username");
		password = StringUtils.isEmpty(properties.getProperty("collect_data_password")) ? ""
				: properties.getProperty("collect_data_password");

	}

	public boolean initMap() {
		try {
			// 1.从数据库获取所有会议类型
			List<Map<String, Object>> list = bizDBService.get("BIZ_TIOL_MEETING_TYPE", new HashMap<String, Object>());
			// 2.判断是否有数据
			if (null == list || list.isEmpty()) {
				logger.error("会议类型未定义");
				return false;
			}
			// 3.缓存所有会议类型
			for (Map<String, Object> map : list) {
				String aliasStr = String.valueOf(map.get("ALIAS"));
				if(!StringUtils.isEmpty(aliasStr)) {
					String[] aliases = aliasStr.split(",");
					for(String alias:aliases) {
						meetingTypeMap.put(alias, map.get("TYPE_ID").toString());
					}
				}
				meetingTypeMap.put(map.get("TYPE_NAME").toString(), map.get("TYPE_ID").toString());
			}

			// 4.从数据库获取所有事项目录
			list = bizDBService.get("biz_tiol_catalog", new HashMap<String, Object>());

			// 5.判断是否有数据
			if (null == list || list.isEmpty()) {
				logger.error("【数据采集】获取事项目录失败");
				return false;
			}
			// 6.缓存所有事项目录
			for (Map<String, Object> map : list) {
				if (null == map.get("CATALOG_CODE") || map.get("CATALOG_ID") == null) {
					continue;
				}
				catalogMap.put(map.get("CATALOG_CODE").toString(), map.get("CATALOG_ID").toString());
			}

			// 7.从数据库获取所有制度类型
			list = bizDBService.get("biz_tiol_regulation_type", new HashMap<String, Object>());
			// 8.判断是否有数据
			if (null == list || list.isEmpty()) {
				logger.error("【数据采集】获取制度类型失败");
				return false;
			}
			// 9.缓存所有制度类型
			for (Map<String, Object> map : list) {
				regulaTypeMap.put(map.get("TYPE_NAME").toString(), map.get("TYPE_ID").toString());
			}

			// 10.从数据库获取所有任务来源
			list = bizDBService.get("biz_tiol_source", new HashMap<String, Object>());
			// 11.判断是否有数据
			if (null == list || list.isEmpty()) {
				logger.error("【数据采集】获取任务来源失败");
				return false;
			}
			// 12.缓存所有任务来源
			for (Map<String, Object> map : list) {
				sourceMap.put(map.get("SOURCE_NAME").toString(), map.get("SOURCE_ID").toString());
			}

			// 13.从数据库获取所有专项名称信息
			list = bizDBService.get("biz_tiol_special", new HashMap<String, Object>());
			// 14.判断是否有数据
			if (null == list) {
				logger.error("【数据采集】获取专项名称信息失败");
				return false;
			}
			// 15.缓存所有任务来源
			for (Map<String, Object> map : list) {
				specialMap.put(map.get("SPECIAL_NAME").toString(), map.get("SPECIAL_ID").toString());
			}

			// 16.缓存所有表决方式
			list = bizDBService.get("tiol_vote_mode", new HashMap<String, Object>());
			if (null == list || list.isEmpty()) {
				logger.error("【数据采集】获取表决方式信息失败");
				return false;
			}
			for (Map<String, Object> map : list) {
				voteModeMap.put(map.get("MODE_NAME").toString(), map.get("MODE_ID").toString());
			}

			// 17.缓存议题是否通过字典
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("CONFIG_KEY", "cfg_veto_name");
			list = bizDBService.get("T_BUSINESS_CONFIG", param);
			for (Map<String, Object> map : list) {
				String[] flagNames = map.get("CONFIG_VALUE").toString().split(",");
				for (String flagName : flagNames) {
					passFlagMap.put(flagName.toString(), "0");
				}
			}
			param.put("CONFIG_KEY", "cfg_defer_name");
			list = bizDBService.get("T_BUSINESS_CONFIG", param);
			for (Map<String, Object> map : list) {
				String[] flagNames = map.get("CONFIG_VALUE").toString().split(",");
				for (String flagName : flagNames) {
					passFlagMap.put(flagName.toString(), "2");
				}
			}
			param.put("CONFIG_KEY", "cfg_pass_name");
			list = bizDBService.get("T_BUSINESS_CONFIG", param);
			for (Map<String, Object> map : list) {
				String[] flagNames = map.get("CONFIG_VALUE").toString().split(",");
				for (String flagName : flagNames) {
					passFlagMap.put(flagName.toString(), "1");
				}
			}
			// 18.缓存是否需报国资委审批字典
			param.put("CONFIG_KEY", "cfg_report_sasac");
			list = bizDBService.get("T_BUSINESS_CONFIG", param);
			for (Map<String, Object> map : list) {
				String[] flagNameValues = map.get("CONFIG_VALUE").toString().split(",");
				for (String flagNameValue : flagNameValues) {
					String[] nameValue = flagNameValue.split(":");
					approvalFlagMap.put(nameValue[0], nameValue[1]);
				}
			}

		} catch (Exception e) {
			logger.error("【数据采集】数据初始化失败  ", e);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 比对共享文件夹与本地文件夹，下载未下载的文件
	 * 
	 * @param filePath
	 *            共享文件夹路径
	 * @param host
	 *            共享文件夹IP地址
	 * @param username
	 *            共享文件夹登录用户名
	 * @param password
	 *            共享文件夹登录密码
	 * @param localPath
	 *            本地存放文件夹路径
	 */
	public List<File> downFile() throws SmbException, MalformedURLException, UnknownHostException {
		// if (fileDownSet.isEmpty()) {
		fileDownSet.clear();
		File localDir = new File(localPath);
		if (!localDir.exists()) {
			localDir.mkdirs();
		}
		File[] localFiles = localDir.listFiles();
		for (File file : localFiles) {
			fileDownSet.add(file.getName());
		}
		// }

		List<File> fileList = new ArrayList<File>();
		SmbFile[] smbFiles = SambaUtil.getSmbFiles(host, username, password, filePath);
		System.out.println("【数据采集】文件夹个数 "+smbFiles.length);
		for (SmbFile sf : smbFiles) {
			System.out.println("【数据采集】文件 "+sf.getName());
			if (!fileDownSet.contains(sf.getName().replace("/", ""))) {// 如果未下载，则开始下载
				if(sf.getName().equals(DateUtils.getFormatTime("yyyy-MM-dd"))) {//如果是当前日期文件夹，则不下载
					continue;
				}
				SambaUtil.downloadFilesFromSamba(sf, localPath);
				fileList.add(new File(localPath + "\\" + sf.getName()));
				fileDownSet.add(sf.getName());
			}
		}
		return fileList;
	}

	public static boolean validFileName(String fileName) {

		String[] infos = fileName.split("_");

		// 1.校验文件名分段数 是否大于4段
		if (4 > infos.length) {
			logger.error("文件【" + fileName + "】不符合命名规则");
			return false;
		}

		// 2.校验第一段是否为统一社会信用代码
		if (!ValidateCorporationID.validate(infos[0])) {
			logger.error("文件【" + fileName + "】中的第一段不是有效的统一社会信用代码");
			return false;
		}

		// 3.校验第二段是否为预定义编码
		if (!CollectDataConstant.BUSINESS_CODE_MAP.containsKey(infos[1])) {
			logger.error("文件【" + fileName + "】中的第二段不是有效业务编码");
			return false;
		}

		// // 4.校验第四段日期是否正确
		// if (!DateUtils.isDateMatch(infos[3], CollectDataConstant.DATE_PATTERN)) {
		// logger.error("文件【" + fileName + "】中的第四段，日期格式不合法");
		// return false;
		// }
		return true;
	}

	/**
	 * 从解压文件夹中事项清单信息，存入前置库
	 * 
	 * @param unZipFile
	 *            解压后的文件夹
	 * @param preDataService
	 * @return
	 */
	public static boolean collectPreItemData(File unZipFile, PreDataService preDataService) {
		File[] files = unZipFile.listFiles();
		// 1.判断解压文件夹下是否有文件
		if (0 == files.length) {
			logger.error("文件夹【数据采集-事项】【" + unZipFile.getPath() + "】下，未找到任何文件。");
			return false;
		}
		// 2.遍历文件夹下所有xml文件
		for (File xmlFile : files) {
			if (!xmlFile.getName().toLowerCase().endsWith(".xml")) {
				logger.info("【数据采集-事项】【" + xmlFile.getPath() + "】，不是xml文件");
				return false;
			}
			String companyCode = unZipFile.getName().split("_")[0];

			// 3.解析xml文件
			SAXReader reader = new SAXReader();
			Document document;
			try {
				document = reader.read(xmlFile);
			} catch (DocumentException e) {
				logger.error("【数据采集-事项】【" + xmlFile.getPath() + "】，无法解析。");
				e.printStackTrace();
				return false;
			}
			Element node = document.getRootElement();

			// 4.根据xml生成前置表事项实体
			PreItem pItem = getPreItemFromElement(node);
			pItem.setCompanyId(companyCode);
			pItem.setFileName(xmlFile.getPath());
			pItem.setXmlContent(document.asXML());

			// 5.存入事项前置表
			preDataService.savePreItem(pItem);
		}
		return true;
	}

	public static boolean collectPreRegulationData(File unZipFile, PreDataService preDataService,
			BizDBService bizDBService) {
		File[] files = unZipFile.listFiles();
		if (0 == files.length) {
			logger.error("【数据采集-制度】文件夹【" + unZipFile.getPath() + "】下，未找到任何文件。");
			return false;
		}
		String regulationId = "";// 保存佐证材料时使用的制度ID
		// 2.遍历文件夹下所有xml文件
		for (File xmlFile : files) {
			// 3.1 如果不是xml文件，则跳过
			if (!xmlFile.getName().toLowerCase().endsWith(".xml")) {
				continue;
			}

			String companyCode = unZipFile.getName().split("_")[0];// 根据解压文件名，获取企业代码(即企业ID)

			// 4.解析xml文件
			SAXReader reader = new SAXReader();
			Document document;
			try {
				document = reader.read(xmlFile);
			} catch (DocumentException e) {
				logger.error("【数据采集-制度】【" + xmlFile.getPath() + "】，无法解析。");
				e.printStackTrace();
				return false;
			}

			Element node = document.getRootElement();

			// 5.根据xml生成制度前置表对象
			PreRegulation regulation = getPreRegulationFromElement(node);
			regulation.setCompanyId(companyCode);
			regulation.setFileName(xmlFile.getPath());
			regulation.setXmlContent(document.asXML());

			// 6.保存正式文件
			File formalPath = new File(unZipFile.getPath() + "\\正式文件");
			if (!formalPath.exists()) {
				 if(!"del".equals(regulation.getOperType())){
					logger.error("【数据采集-制度】【" + companyCode + "】的制度正式文件文件夹不存在");
					return false;
				 }
			} else {
				File[] formalFiles = formalPath.listFiles();
				if (1 != formalFiles.length && !"del".equals(regulation.getOperType())) {
					logger.error("【数据采集-制度】【" + companyCode + "】的制度的正式文件不存在，或存在多个正式文件请检查");
					return false;
				} else {
					String fileId = FileUtils.upload(formalFiles[0]);// 保存制度正式文件
					regulation.setFileId(fileId);
				}
			}

			// 7.存入前置制度表
			preDataService.savePreRegulation(regulation);

			// 记录制度Id，用于后续材料附件关联
			regulationId = regulation.getId();
		}
		// 8.保存佐证材料
		List<Map<String, Object>> attachmentMapList = new ArrayList<Map<String, Object>>();
		File materialPath = new File(unZipFile.getPath() + "\\佐证材料");
		if (materialPath.exists()) {
			File[] materialFiles = materialPath.listFiles();
			for (File materialFile : materialFiles) {
				String fileId = FileUtils.upload(materialFile);// 保存佐证材料文件

				// 生成附件信息表实体
				Attachment attachment = new Attachment();
				attachment.setAttachmentId(CommonUtil.getUUID());
				attachment.setAttachmentName(materialFile.getName());
				attachment.setFileId(fileId);
				attachment.setBusinessId(regulationId);
				attachment.setAttachmentType("制度佐证材料");
				attachment.setStatus("1");
				attachment.setCreateTime(DateUtils.getFormatTime());
				attachment.setCreateUser(CollectDataConstant.CREATER_ID);

				Map<String, Object> attachmentMap = MapTrunPojo.pojo2Map(attachment);
				attachmentMapList.add(attachmentMap);
			}
		}
		if (!attachmentMapList.isEmpty()) {
			bizDBService.batchInsert(CollectDataConstant.ATTACHMENT_BIZ_ID, attachmentMapList);
		}
		return true;
	}

	public static boolean collectPreMeetingData(File unZipFile, PreDataService preDataService,
			BizDBService bizDBService) {
		// 1.判断文件夹内是否有文件
		File[] files = unZipFile.listFiles();
		if (0 == files.length) {
			logger.error("【数据采集-会议】文件夹【" + unZipFile.getPath() + "】下，未找到任何文件。");
			return false;
		}

		String companyCode = unZipFile.getName().split("_")[0];// 根据解压文件名，获取企业代码(即企业ID)

		// 2.保存会议纪要
		String summaryFileId = "";// 会议纪要文件ID
		File summaryFileDirectory = new File(unZipFile.getPath() + "\\会议纪要");
		if (summaryFileDirectory.exists()) {
			File[] summaryFiles = summaryFileDirectory.listFiles();
			if (summaryFiles.length > 0) {
				summaryFileId = FileUtils.upload(summaryFiles[0]);
			}
		}

		// 3.保存会议通知
		String noticeFileId = "";// 会议通知文件ID
		File noticeFileDirectory = new File(unZipFile + "\\会议通知");
		if (noticeFileDirectory.exists()) {
			File[] noticeFiles = noticeFileDirectory.listFiles();
			if (noticeFiles.length > 0) {
				noticeFileId = FileUtils.upload(noticeFiles[0]);
			}
		}

		// 4.遍历文件夹读取xml文件
		File xmlFile = null;
		for (File f : files) {
			if ((!f.getName().toLowerCase().endsWith(".xml"))) {
				continue;
			}
			xmlFile = f;
		}

		// 5.解析xml文件
		SAXReader reader = new SAXReader();
		Document document;
		try {
			document = reader.read(xmlFile);
		} catch (DocumentException e) {
			logger.error("【数据采集-会议】【" + xmlFile.getPath() + "】，无法解析。");
			e.printStackTrace();
			return false;
		}

		Element node = document.getRootElement();
		PreMeeting preMeeting = null;
		List<PreSubject> preSubjects = null;
		List<Map<String, Object>> attachmentMapList = new ArrayList<Map<String, Object>>();
		// 6.生成会议前置表实体类
		try {
			preMeeting = getPreMeetingFromElement(node);
			preMeeting.setCompanyId(companyCode);
			preMeeting.setFileName(xmlFile.getPath());
			preMeeting.setXmlContent(document.asXML());
			preMeeting.setNoticeFileId(noticeFileId);
			preMeeting.setSummaryFileId(summaryFileId);

			// 7.保存会议到前置表
			preDataService.savePreMeeting(preMeeting);

			// 8.解析xml获取议题列表
			preSubjects = getPreSubjectFromElement(node, preMeeting);

			// 9.保存议题材料和听取意见
			for (PreSubject pSubject : preSubjects) {
				// 9.1 根据议题ID读取文件夹
				File subjectDirectory = new File(unZipFile.getPath() + "\\" + pSubject.getSubjectId());
				if (subjectDirectory.exists()) {
					File opionDirectory = new File(subjectDirectory.getPath() + "\\听取意见");
					if (opionDirectory.exists()) {
						File[] opionFiles = opionDirectory.listFiles();
						for (File opionFile : opionFiles) {
							// 上传文件
							String fileId = FileUtils.upload(opionFile);// 保存听取意见文件
							// 生成附件信息表实体
							Attachment attachment = new Attachment();
							attachment.setAttachmentId(CommonUtil.getUUID());
							attachment.setAttachmentName(opionFile.getName());
							attachment.setFileId(fileId);
							attachment.setBusinessId(pSubject.getId());
							if (opionFile.getName().contains("法律意见书")) {
								attachment.setAttachmentType("法律意见书");
							} else {
								attachment.setAttachmentType("听取意见");
							}
							attachment.setStatus("1");
							attachment.setCreateTime(DateUtils.getFormatTime());
							attachment.setCreateUser(CollectDataConstant.CREATER_ID);

							Map<String, Object> attachmentMap = MapTrunPojo.pojo2Map(attachment);
							attachmentMapList.add(attachmentMap);
						}
					}
					File materialDir = new File(subjectDirectory.getPath() + "\\议题材料");
					if (materialDir.exists()) {
						File[] materialFiles = materialDir.listFiles();
						for (File materialFile : materialFiles) {
							// 上传文件
							String fileId = FileUtils.upload(materialFile);// 保存议题材料文件
							// 生成附件信息表实体
							Attachment attachment = new Attachment();
							attachment.setAttachmentId(CommonUtil.getUUID());
							attachment.setAttachmentName(materialFile.getName());
							attachment.setFileId(fileId);
							attachment.setBusinessId(pSubject.getId());
							attachment.setAttachmentType("议题材料");
							attachment.setStatus("1");
							attachment.setCreateTime(DateUtils.getFormatTime());
							attachment.setCreateUser(CollectDataConstant.CREATER_ID);

							Map<String, Object> attachmentMap = MapTrunPojo.pojo2Map(attachment);
							attachmentMapList.add(attachmentMap);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("【数据采集-会议】【" + xmlFile.getPath() + "】，读取内容发现有误。", e);
			return false;
		}
		// 10.保存议题列表
		preDataService.batchInsertPreSubject(preSubjects);

		// 11.保存附件列表
		if (!attachmentMapList.isEmpty()) {
			bizDBService.batchInsert(CollectDataConstant.ATTACHMENT_BIZ_ID, attachmentMapList);
		}

		return true;
	}

	private static List<PreSubject> getPreSubjectFromElement(Element node, PreMeeting preMeeting) {
		List<PreSubject> list = new ArrayList<PreSubject>();
		List<Element> eleList = node.element("subject_list").elements("subject");
		for (Element ele : eleList) {// 遍历会议
			String subjectId = ele.elementText("subject_id");// 议题ID
			String subjectName = ele.elementText("subject_name");// 议题名称
			String sourceName = ele.elementText("source_name");// 任务来源
			String specialName = ele.elementText("special_name");// 专项名称
			String itemCode = "";
			if(ele.element("item_list")!=null) {
				List<Element> itemsList = ele.element("item_list").elements("item_code");
				for (Element item : itemsList) {
					itemCode += ","+item.getStringValue();
				}
				if(itemCode.length()>1) {
					itemCode = itemCode.substring(1);
				}
			}
			String passFlag = ele.elementText("pass_flag");// 是否通过
			String approvalFlag = ele.elementText("approval_flag");// 是否需报国资委审批 1审批，2备案，0否
			String adoptFlag = ele.elementText("adopt_flag");// 是否听取意见
			String subjectResult = ele.elementText("subject_result");// 议题决议
			String remark = ele.elementText("remark");// 备注
			String operType = preMeeting.getOperType();// 操作类型
			String meetingName = preMeeting.getMeetingName();// 会议名称
			String companyId = preMeeting.getCompanyId();// 企业ID

			// 获取列席人员
			JSONArray attendances = new JSONArray();
			List<Element> attendancesList = ele.element("attendance_list").elements("attendance");
			for (Element attendance : attendancesList) {
				String attendanceName = attendance.elementText("attendance_name");// 列席人员名称
				String position = attendance.elementText("position");// 列席人职位

				JSONObject json = new JSONObject();
				json.put("attendance_name", attendanceName);
				json.put("position", position);
				attendances.add(json);
			}

			// 获取审议结果列表
			JSONArray deliberations = new JSONArray();
			List<Element> deliberationsList = ele.element("deliberation_list").elements("deliberation");
			for (Element deliberation : deliberationsList) {
				String deliberationPersonnel = deliberation.elementText("deliberation_personnel");// 审议人
				String deliberationResult = deliberation.elementText("deliberation_result");// 审议结果

				JSONObject json = new JSONObject();
				json.put("deliberation_personnel", deliberationPersonnel);
				json.put("deliberation_result", deliberationResult);
				deliberations.add(json);
			}

			PreSubject ps = new PreSubject();
			ps.setAdoptFlag(adoptFlag);
			ps.setApprovalFlag(approvalFlag);
			ps.setAttendances(attendances.toString());
			ps.setCreateTime(DateUtils.getFormatTime());
			ps.setDeliberations(deliberations.toString());
			// ps.setErrorRemark(errorRemark);
			ps.setFileName(preMeeting.getFileName());
			ps.setId(CommonUtil.getUUID());
			ps.setItemCode(itemCode);
			ps.setMeetingId(preMeeting.getId());
			ps.setOperType(operType);
			ps.setPassFlag(passFlag);

			ps.setRemark(remark);
			ps.setSourceName(sourceName);
			ps.setSpecialName(specialName);
			ps.setSubjectId(subjectId);
			ps.setSubjectName(subjectName);
			ps.setSubjectResult(subjectResult);
			ps.setStatus("0");
			ps.setMeetingName(meetingName);
			ps.setCompanyId(companyId);
			if (!StringUtils.isEmpty(ps.getRemark()) && ps.getRemark().length() > 84) {
				ps.setRemark(ps.getRemark().substring(0, 83) + "...");
			}

			// 读取关联会议和关联议题
			// String relMeetingName = ele.elementText("rel_meeting_name");// 关联会议名称
			// String relSubjectName = ele.elementText("rel_subject_name");// 关联议题名称
			JSONArray relationArray = new JSONArray();
			if (ele.element("relation_list") != null) {// 如果有多个关联会议
				List<Element> relationList = ele.element("relation_list").elements("relation");
				for (Element relation : relationList) {
					String relMeetingName = relation.element("rel_meeting_name").getStringValue();
					String relSubjectName = relation.element("rel_subject_name").getStringValue();
					JSONObject json = new JSONObject();
					json.put("rel_meeting_name", relMeetingName);
					json.put("rel_subject_name", relSubjectName);
					relationArray.add(json);
				}
			} else if (ele.element("rel_meeting_name") != null && ele.element("rel_subject_name") != null) {
				String relMeetingName = ele.element("rel_meeting_name").getStringValue();
				String relSubjectName = ele.element("rel_subject_name").getStringValue();

				JSONObject json = new JSONObject();
				json.put("rel_meeting_name", relMeetingName);
				json.put("rel_subject_name", relSubjectName);
				relationArray.add(json);
			}
			ps.setRelSubjectName(relationArray.toString());
			list.add(ps);
		}
		return list;
	}

	private static PreMeeting getPreMeetingFromElement(Element node) {
		// 1.从xml读取相关参数
		String meetingId = node.elementText("meeting_id");
		String meetingName = node.elementText("meeting_name");
		String meetingTypeName = node.elementText("meeting_type_name");
		String meetingTime = node.elementText("meeting_time");
		String moderator = node.elementText("moderator");
		String operType = node.elementText("oper_type");

		// 2.读取参会人员列表
		List<Element> eleList = node.element("attendee_list").elements("attendee");
		JSONArray attendees = new JSONArray();
		for (Element ele : eleList) {// 遍历会议
			String attendeeName = ele.elementText("attendee_name");// 获取参会人姓名
			String attendFlag = ele.elementText("attend_flag");// 是否参会
			String reason = ele.elementText("reason");// 是否参会

			JSONObject json = new JSONObject();
			json.put("attendee_name", attendeeName);
			json.put("attend_flag", attendFlag);
			json.put("reason", reason);
			attendees.add(json);
		}

		PreMeeting preMeeting = new PreMeeting();
		preMeeting.setAttendees(attendees.toString());
		preMeeting.setCreateTime(DateUtils.getFormatTime());
		preMeeting.setId(CommonUtil.getUUID());
		preMeeting.setMeetingId(meetingId);
		preMeeting.setMeetingName(meetingName);
		preMeeting.setMeetingTime(meetingTime);
		preMeeting.setModerator(moderator);
		preMeeting.setOperType(operType);
		preMeeting.setStatus("0");
		preMeeting.setTypeName(meetingTypeName);
		if (!StringUtils.isEmpty(preMeeting.getRemark()) && preMeeting.getRemark().length() > 84) {
			preMeeting.setRemark(preMeeting.getRemark().substring(0, 83) + "...");
		}

		return preMeeting;
	}

	// 根据xml生成前置表事项实体
	private static PreItem getPreItemFromElement(Element node) {
		// 1.从xml读取相关参数
		String itemId = node.elementText("item_id");
		String itemName = node.elementText("item_name");
		String itemCode = node.elementText("item_code");
		String legalFlag = node.elementText("legal_flag");
		String remark = node.elementText("remark");
		String operType = node.elementText("oper_type");

		// 1.2 读取会议列表
		List<Element> eleList = node.element("item_meeting_list").elements("item_meeting");
		JSONArray meetingArray = new JSONArray();
		for (Element ele : eleList) {// 遍历会议
			String meetingTypeName = ele.getStringValue();
			JSONObject json = new JSONObject();
			json.put("meeting_type_name", meetingTypeName);
			meetingArray.add(json);
		}

		PreItem pItem = new PreItem();
		// pItem.setCompanyId(companyId);外部填写
		pItem.setCreateTime(DateUtils.getFormatTime());
		// pItem.setErrorRemark(errorRemark);新增不填
		// pItem.setFileName(fileName);外部填写
		pItem.setId(CommonUtil.getUUID());
		pItem.setItemCode(itemCode);
		pItem.setItemId(itemId);
		pItem.setItemName(itemName);
		pItem.setLegalFlag(legalFlag);
		pItem.setMeetings(meetingArray.toString());
		pItem.setOperType(operType);
		pItem.setRemark(remark);
		pItem.setStatus("0");
		if (!StringUtils.isEmpty(pItem.getRemark()) && pItem.getRemark().length() > 84) {
			pItem.setRemark(pItem.getRemark().substring(0, 83) + "...");
		}
		// pItem.setUpdateTime(updateTime); 新增不填
		// pItem.setXml(xml);外部填写
		return pItem;
	}

	// 根据xml生成前置表事项实体
	private static PreRegulation getPreRegulationFromElement(Element node) {
		// 1.从xml读取相关参数
		String regulationId = node.elementText("regulation_id");
		String regulationName = node.elementText("regulation_name");
		String regulationTypeName = node.elementText("regulation_type_name");
		String approvalDate = node.elementText("approval_date");
		String effectiveDate = node.elementText("effective_date");
		String auditFlag = node.elementText("audit_flag");
		String rate = node.elementText("rate");
		String remark = node.elementText("remark");
		String operType = node.elementText("oper_type");

		// 1.2 读取表决方式列表
		List<Element> eleList = node.element("vote_mode_list").elements("vote_mode");
		JSONArray voteArray = new JSONArray();
		for (Element ele : eleList) {// 遍历会议
			String itemCode = ele.elementText("item_code");// 事项编码
			String voteMode = ele.elementText("vote_mode");// 表决方式
			String voteRemark = ele.elementText("remark");// 表决方式
			JSONObject json = new JSONObject();
			json.put("item_code", itemCode);
			json.put("vote_mode", voteMode);
			json.put("remark", voteRemark);
			voteArray.add(json);
		}

		PreRegulation preRegulation = new PreRegulation();
		preRegulation.setApprovalDate(approvalDate);
		preRegulation.setAuditFlag(auditFlag);
		// preRegulation.setCompanyId(companyId);
		preRegulation.setCreateTime(DateUtils.getFormatTime());
		preRegulation.setEffectiveDate(effectiveDate);
		// preRegulation.setErrorRemark(errorRemark);
		// preRegulation.setFileName(fileName);
		preRegulation.setId(CommonUtil.getUUID());
		preRegulation.setOperType(operType);
		preRegulation.setRate(rate);
		preRegulation.setRegulationId(regulationId);
		preRegulation.setRegulationName(regulationName);
		preRegulation.setRemark(remark);
		preRegulation.setStatus("0");
		preRegulation.setTypeName(regulationTypeName);
		// preRegulation.setUpdateTime(updateTime);
		preRegulation.setVoteModes(voteArray.toString());
		// preRegulation.setXml(xml);
		if (!StringUtils.isEmpty(preRegulation.getRemark()) && preRegulation.getRemark().length() > 84) {
			preRegulation.setRemark(preRegulation.getRemark().substring(0, 83) + "...");
		}
		return preRegulation;
	}

	public void dealPreItem(List<PreItem> preItemList) {
		// TODO Auto-generated method stub
		for (PreItem preItem : preItemList) {
			if (StringUtils.isEmpty(preItem.getOperType())) {
				preItem.setErrorRemark("操作类型oper_type为空");
				preItem.setStatus("2");
				preItem.setUpdateTime(DateUtils.getFormatTime());
				preDataService.updatePreItem(preItem);
				continue;
			}
			// 根据事项编码，获取目录ID
			String itemCode = preItem.getItemCode();
			// 判断事项清单编码格式是否标准
			if (itemCode.indexOf("-") == -1) {
				// 编码格式异常
				preItem.setErrorRemark("编码格式异常");
				preItem.setUpdateTime(DateUtils.getFormatTime());
				preItem.setStatus("2");
				preDataService.updatePreItem(preItem);
				continue;
			}
			String catalogCode = itemCode.split("-")[0];
			String catalogId = "";
			catalogId = catalogMap.get(catalogCode);
			// 判断目录编码是否存在
			if (StringUtils.isEmpty(catalogId)) {
				// 如果目录找不到，则记录此异常
				preItem.setErrorRemark("无法通过事项编码找到事项目录");
				preItem.setUpdateTime(DateUtils.getFormatTime());
				preItem.setStatus("2");
				preDataService.updatePreItem(preItem);
				continue;
			}

			Item item = new Item();
			item.setFid(CommonUtil.getUUID());
			item.setcModuleid(CollectDataConstant.ITEM_MODULE_ID);
			item.setcModulename(CollectDataConstant.ITEM_MODULE_NAME);
			item.setcBizid(CollectDataConstant.ITEM_BIZ_ID);
			item.setcBizname(CollectDataConstant.ITEM_BIZ_NAME);
			item.setcCreaterid(CollectDataConstant.CREATER_ID);
			item.setcCreatername(CollectDataConstant.CREATER_NAME);
			item.setcCreatedeptid(CollectDataConstant.CREATE_DEPT_ID);
			item.setcCreatedeptname(CollectDataConstant.CREATE_DEPT_NAME);
			item.setcCompanyid(preItem.getCompanyId());

			item.setItemId(CommonUtil.getUUID());
			item.setCatalogId(catalogId);
			item.setItemName(preItem.getItemName());
			item.setItemCode(itemCode);
			item.setCompanyId(preItem.getCompanyId());
			item.setLegalFlag(("是").equals(preItem.getLegalFlag()) ? "1" : "0");
			item.setcCreatedate(DateUtils.getFormatTime());
			item.setStatus("1");
			item.setRemark(preItem.getRemark());
			item.setSid(preItem.getItemId());
			item.setCreateTime(DateUtils.getFormatTime());
			// 解析事项会议
			List<Map<String, Object>> itemMeetingMapList = new ArrayList<Map<String, Object>>();
			try {
				JSONArray meetingArray = JSONArray.fromObject(preItem.getMeetings());
				boolean breakFlag = false;
				for (int i = 0, len = meetingArray.size(); i < len; i++) {
					JSONObject meetingJson = meetingArray.getJSONObject(i);
					// 获取原始值
					String meetingTypeName = meetingJson.getString("meeting_type_name");
					String orderNumber = String.valueOf(i+1);

					// 获取会议类型ID
					String meetingTypeId = meetingTypeMap.get(meetingTypeName.trim());
					if (StringUtils.isEmpty(meetingTypeId)) {
						preItem.setErrorRemark("会议类型[" + meetingTypeName + "]未定义");
						preItem.setStatus("2");
						preItem.setUpdateTime(DateUtils.getFormatTime());
						preDataService.updatePreItem(preItem);
						breakFlag = true;
						break;
					}

					ItemMeeting itemMeeting = new ItemMeeting();
					itemMeeting.setItemMeetingId(CommonUtil.getUUID());
					itemMeeting.setItemId(item.getItemId());
					itemMeeting.setTypeId(meetingTypeId);
					itemMeeting.setAlias(meetingTypeName);
					if (!StringUtils.isEmpty(orderNumber)) {
						itemMeeting.setOrderNumber(Integer.valueOf(orderNumber));
					}
					itemMeetingMapList.add(MapTrunPojo.pojo2Map(itemMeeting));
				}
				if (breakFlag) {
					continue;
				}
			} catch (Exception e) {
				logger.error("【数据采集-事项】解析会议列表失败", e);
				preItem.setErrorRemark("解析会议列表异常");
				preItem.setStatus("9");
				preItem.setUpdateTime(DateUtils.getFormatTime());
				preDataService.updatePreItem(preItem);
				continue;
			}

			try {
				// 保存事项会议列表
				if (!itemMeetingMapList.isEmpty()) {
					bizDBService.batchInsert("tiol_item_meeting", itemMeetingMapList);
				}

				// 插入数据库业务表
				if (preItem.getOperType().equals("add")) {
					// 新增数据时，数据是否存在判断
					int exist = preDataService.checkItemIsExist(itemCode, catalogId, preItem.getCompanyId());
					if (exist > 0) {
						// 事项清单不为空，则记录异常
						preItem.setErrorRemark("事项编码" + itemCode + "已存在");
						preItem.setUpdateTime(DateUtils.getFormatTime());
						preItem.setStatus("2");
						preDataService.updatePreItem(preItem);
						continue;
					}
					bizDBService.save("biz_" + CollectDataConstant.ITEM_BIZ_ID, MapTrunPojo.pojo2Map(item));
				} else if (preItem.getOperType().equals("del")) {
					Item delItem = new Item();
					delItem.setUpdateTime(DateUtils.getFormatTime());
					delItem.setSid(item.getSid());
					delItem.setItemCode(itemCode);
					delItem.setCatalogId(catalogId);
					delItem.setCompanyId(item.getCompanyId());
					delItem.setStatus("0");
					itemService.updateItem(MapTrunPojo.pojo2Map(delItem));
				} else if (preItem.getOperType().equals("edit")) {
					// 修改操作-旧记录设为0,新增一条新纪录
					Item delItem = new Item();
					delItem.setUpdateTime(DateUtils.getFormatTime());
					delItem.setSid(item.getSid());
					delItem.setItemCode(itemCode);
					delItem.setCatalogId(catalogId);
					delItem.setCompanyId(item.getCompanyId());
					delItem.setStatus("0");
					itemService.updateItem(MapTrunPojo.pojo2Map(delItem));
					// 新增事项清单记录
					bizDBService.save("biz_" + CollectDataConstant.ITEM_BIZ_ID, MapTrunPojo.pojo2Map(item));
				}
			} catch (Exception e) {
				logger.error("【数据采集-事项】保存事项至业务表时异常", e);
				preItem.setErrorRemark("保存至数据库异常");
				preItem.setStatus("9");
				preItem.setUpdateTime(DateUtils.getFormatTime());
				preDataService.updatePreItem(preItem);
				continue;
			}

			// 更新前置表
			preItem.setStatus("1");
			preItem.setUpdateTime(DateUtils.getFormatTime());
			preDataService.updatePreItem(preItem);
		}
	}

	public void dealPreRegulation(List<PreRegulation> preRegulationList) {
		for (PreRegulation preRegulation : preRegulationList) {

			String typeId = regulaTypeMap.get(preRegulation.getTypeName());
			if (StringUtils.isEmpty(typeId)) {
				// 如果类型找不到，则记录此异常
				preRegulation.setErrorRemark("无法通过制度类型名称找到制度");
				preRegulation.setUpdateTime(DateUtils.getFormatTime());
				preRegulation.setStatus("2");
				preDataService.updatePreRegulation(preRegulation);
				continue;
			}

			if (StringUtils.isEmpty(preRegulation.getOperType())) {
				preRegulation.setErrorRemark("操作类型oper_type为空");
				preRegulation.setStatus("2");
				preRegulation.setUpdateTime(DateUtils.getFormatTime());
				preDataService.updatePreRegulation(preRegulation);
				continue;
			}
			// 组装保存实体
			Regulation regulation = new Regulation();
			regulation.setFid(preRegulation.getId());
			regulation.setcModuleid(CollectDataConstant.REGULATION_MODULE_ID);
			regulation.setcModulename(CollectDataConstant.REGULATION_MODULE_NAME);
			regulation.setcBizid(CollectDataConstant.REGULATION_BIZ_ID);
			regulation.setcBizname(CollectDataConstant.REGULATION_BIZ_NAME);
			regulation.setcCreaterid(CollectDataConstant.CREATER_ID);
			regulation.setcCreatername(CollectDataConstant.CREATER_NAME);
			regulation.setcCreatedeptid(CollectDataConstant.CREATE_DEPT_ID);
			regulation.setcCreatedeptname(CollectDataConstant.CREATE_DEPT_NAME);
			regulation.setcCompanyid(preRegulation.getCompanyId());

			regulation.setRegulationId(preRegulation.getId());
			regulation.setRegulationName(preRegulation.getRegulationName());
			regulation.setTypeId(typeId);
			regulation.setCompanyId(preRegulation.getCompanyId());
			regulation.setApprovalDate(preRegulation.getApprovalDate());
			regulation.setEffectiveDate(preRegulation.getEffectiveDate());
			regulation.setStatus("1");
			// 验证是否经过合法审查
			if (("是").equals(preRegulation.getAuditFlag())) {
				regulation.setAuditFlag("1");
			} else if (("否").equals(preRegulation.getAuditFlag())) {
				regulation.setAuditFlag("0");
			} else {
				// 如果类型找不到，则记录此异常
				preRegulation.setErrorRemark("无法解析合法审查状态数据");
				preRegulation.setUpdateTime(DateUtils.getFormatTime());
				preRegulation.setStatus("2");
				preDataService.updatePreRegulation(preRegulation);
				continue;
			}
			regulation.setRate(preRegulation.getRate());// 必填
			regulation.setRemark(preRegulation.getRemark());
			// 验证文件是否上传成功
			if (StringUtils.isEmpty(preRegulation.getFileId())) {
				if(!"del".equals(preRegulation.getOperType())) {
					// 文件不存在,或者上传失败
					preRegulation.setErrorRemark("文件不存在,或者上传失败");
					preRegulation.setUpdateTime(DateUtils.getFormatTime());
					preRegulation.setStatus("9");
					preDataService.updatePreRegulation(preRegulation);
					continue;
				}
			}
			regulation.setFileId(preRegulation.getFileId());// 必填
			regulation.setSid(preRegulation.getRegulationId());
			// 记录表决方式查询结果
			int vote = 0;
			// 解析表决方式
			List<Map<String, Object>> voteMapList = new ArrayList<Map<String, Object>>();
			try {
				String voteStr = preRegulation.getVoteModes();
				JSONArray voteArray = JSONArray.fromObject(voteStr);
				boolean breakFlag = false;
				for (int i = 0, len = voteArray.size(); i < len; i++) {
					JSONObject voteJson = voteArray.getJSONObject(i);
					// 获取原始值
					String itemCode = voteJson.containsKey("item_code") ? voteJson.getString("item_code") : "";
					String voteMode = voteJson.containsKey("vote_mode") ? voteJson.getString("vote_mode") : "";
					String remark = voteJson.containsKey("remark") ? voteJson.getString("remark") : "";

					// 获取事项ID
					String itemId = "";
					if (!StringUtils.isEmpty(itemCode) && !CollectDataConstant.REGULATION_VOTE_ITEM.equals(itemCode)) {
						Map<String, Object> parameter = new HashMap<String, Object>();
						// 组装查询条件，根据事项编码、企业代码查询
						parameter.put("ITEM_CODE", itemCode);
						parameter.put("COMPANY_ID", preRegulation.getCompanyId());
						parameter.put("STATUS", "1");
						List<Map<String, Object>> itemList = null;
						try {
							itemList = bizDBService.get("biz_" + CollectDataConstant.ITEM_BIZ_ID, parameter);
						} catch (Exception e) {
							logger.error("【数据采集-制度】获取事项信息时出错", e);
							preRegulation.setErrorRemark("根据事项编码[" + itemCode + "]查询时，系统异常");
							preRegulation.setStatus("9");
							preRegulation.setUpdateTime(DateUtils.getFormatTime());
							preDataService.updatePreRegulation(preRegulation);
							breakFlag = true;
							break;
						}

						if (null == itemList || itemList.isEmpty()) {
							preRegulation.setErrorRemark("表决方式的对应的事项编码[" + itemCode + "]未定义");
							preRegulation.setStatus("2");
							preRegulation.setUpdateTime(DateUtils.getFormatTime());
							preDataService.updatePreRegulation(preRegulation);
							breakFlag = true;
							break;
						}
						itemId = itemList.get(0).get("ITEM_ID").toString();
					}

					// 获取表决方式ID
					String voteId = voteModeMap.get(voteMode);
					if (StringUtils.isEmpty(voteId)) {
						vote = 1;
						preRegulation.setErrorRemark("表决方式[" + voteMode + "]未定义");
						preRegulation.setStatus("2");
						preRegulation.setUpdateTime(DateUtils.getFormatTime());
						preDataService.updatePreRegulation(preRegulation);
						breakFlag = true;
						break;
					}
					RegulationVote regulationVote = new RegulationVote();
					regulationVote.setVoteId(CommonUtil.getUUID());
					regulationVote.setModeId(voteId);
					regulationVote.setItemId(itemId);
					regulationVote.setRegulationId(regulation.getRegulationId());
					regulationVote.setRemark(remark);
					voteMapList.add(MapTrunPojo.pojo2Map(regulationVote));
				}
				if (breakFlag) {
					continue;
				}
			} catch (Exception e) {
				logger.error("【数据采集-制度】解析表决方式失败", e);
				preRegulation.setErrorRemark("表决方式转换失败");
				preRegulation.setStatus("9");
				preRegulation.setUpdateTime(DateUtils.getFormatTime());
				preDataService.updatePreRegulation(preRegulation);
				continue;
			}

			// 如果存在表决方式不存在跳出
			if (vote == 1) {
				preRegulation.setErrorRemark("表决方式未定义,跳出保存制度信息");
				preRegulation.setStatus("2");
				preRegulation.setUpdateTime(DateUtils.getFormatTime());
				preDataService.updatePreRegulation(preRegulation);
				continue;
			}

			try {
				// 保存voteList
				if (!voteMapList.isEmpty()) {
					bizDBService.batchInsert("tiol_regulation_vote", voteMapList);
				}
				// 插入数据库业务表
				if (preRegulation.getOperType().equals("add")) {
					// 查询制度是否已存在
					int isExist = preDataService.checkRegulationExist(preRegulation.getRegulationId(), typeId,
							preRegulation.getCompanyId(), preRegulation.getRegulationName());
					if (isExist > 0) {
						preRegulation.setErrorRemark("制度信息已存在");
						preRegulation.setStatus("2");
						preRegulation.setUpdateTime(DateUtils.getFormatTime());
						preDataService.updatePreRegulation(preRegulation);
						continue;
					} else {
						bizDBService.save("biz_" + CollectDataConstant.REGULATION_BIZ_ID,
								MapTrunPojo.pojo2Map(regulation));
					}
				} else if (preRegulation.getOperType().equals("del")) {
					Regulation delRegulation = new Regulation();
					delRegulation.setUpdateTime(DateUtils.getFormatTime());
					delRegulation.setSid(preRegulation.getRegulationId());
					delRegulation.setStatus("0");
					regulationService.updateRegulation(MapTrunPojo.pojo2Map(delRegulation));
				} else if (preRegulation.getOperType().equals("edit")) {
					// 删除原本记录
					Regulation delRegulation = new Regulation();
					delRegulation.setUpdateTime(DateUtils.getFormatTime());
					delRegulation.setSid(preRegulation.getRegulationId());
					delRegulation.setStatus("0");
					regulationService.updateRegulation(MapTrunPojo.pojo2Map(delRegulation));
					// 保存新纪录
					bizDBService.save("biz_" + CollectDataConstant.REGULATION_BIZ_ID, MapTrunPojo.pojo2Map(regulation));
				}

			} catch (Exception e) {
				logger.error("【数据采集-制度】保存制度至业务表时异常", e);
				preRegulation.setErrorRemark("保存至数据库异常");
				preRegulation.setStatus("9");
				preRegulation.setUpdateTime(DateUtils.getFormatTime());
				preDataService.updatePreRegulation(preRegulation);
				continue;
			}
			preRegulation.setStatus("1");
			preRegulation.setUpdateTime(DateUtils.getFormatTime());
			preDataService.updatePreRegulation(preRegulation);
		}
	}

	public void dealMeeting(List<PreMeeting> preMeetingList) {
		for (PreMeeting preMeeting : preMeetingList) {
			String typeId = meetingTypeMap.get(preMeeting.getTypeName().trim());
			if (StringUtils.isEmpty(typeId)) {
				preMeeting.setErrorRemark("会议类型[" + preMeeting.getTypeName() + "]未定义");
				preMeeting.setStatus("2");
				preMeeting.setUpdateTime(DateUtils.getFormatTime());
				preDataService.updatePreMeeting(preMeeting);
				continue;
			}

			Meeting meeting = new Meeting();
			meeting.setFid(preMeeting.getId());
			meeting.setcModuleid(CollectDataConstant.MEETING_MODULE_ID);
			meeting.setcModulename(CollectDataConstant.MEETING_MODULE_NAME);
			meeting.setcBizid(CollectDataConstant.MEETING_BIZ_ID);
			meeting.setcBizname(CollectDataConstant.MEETING_BIZ_NAME);
			meeting.setcCreaterid(CollectDataConstant.CREATER_ID);
			meeting.setcCreatername(CollectDataConstant.CREATER_NAME);
			meeting.setcCreatedeptid(CollectDataConstant.CREATE_DEPT_ID);
			meeting.setcCreatedeptname(CollectDataConstant.CREATE_DEPT_NAME);
			meeting.setcCompanyid(preMeeting.getCompanyId());

			meeting.setMeetingId(preMeeting.getId());
			meeting.setMeetingName(preMeeting.getMeetingName());
			meeting.setTypeId(typeId);
			meeting.setMeetingTime(preMeeting.getMeetingTime());
			meeting.setModerator(preMeeting.getModerator());
			meeting.setCompanyId(preMeeting.getCompanyId());
			meeting.setRemark(preMeeting.getRemark());
			meeting.setcCreatedate(DateUtils.getFormatTime());
			meeting.setStatus("1");
			meeting.setSid(preMeeting.getMeetingId());
			meeting.setcCreatedate(DateUtils.getFormatTime());
			meeting.setSummaryFileId(preMeeting.getSummaryFileId());
			meeting.setNoticeFileId(preMeeting.getNoticeFileId());
			meeting.setAlias(preMeeting.getTypeName());
			
			// 如果不是董事会或其他，则必须有会议通知文件
			if(StringUtils.isEmpty(preMeeting.getNoticeFileId())) {
				if(!preMeeting.getTypeName().trim().contains("董事")&&!preMeeting.getTypeName().trim().contains("其他")) {
					preMeeting.setErrorRemark("会议通知文件未找到");
					preMeeting.setStatus("2");
					preMeeting.setUpdateTime(DateUtils.getFormatTime());
					preDataService.updatePreMeeting(preMeeting);
					continue;
				}
			}
			
			if (StringUtils.isEmpty(preMeeting.getOperType())) {
				preMeeting.setErrorRemark("操作类型oper_type为空");
				preMeeting.setStatus("2");
				preMeeting.setUpdateTime(DateUtils.getFormatTime());
				preDataService.updatePreMeeting(preMeeting);
				continue;
			}

			// 根据企业ID、开会日期、会议名称，判断会议是否已存在
			if (preMeeting.getOperType().equals("add")) {
				// 获取会议日期
				Date meetingTime = DateUtils.getDateByDateStr(preMeeting.getMeetingTime(), null);
				String meetingDate = DateUtils.getFormatTime(meetingTime, "yyyy-MM-dd");
				List<Map<String, Object>> meetingList = meetingService.queryByDate(meetingDate,
						meeting.getMeetingName(), meeting.getCompanyId());
				if (!meetingList.isEmpty()) {
					logger.warn("【数据采集-会议】会议已存在");
					preMeeting.setErrorRemark("会议已存在");
					preMeeting.setStatus("2");
					preMeeting.setUpdateTime(DateUtils.getFormatTime());
					preDataService.updatePreMeeting(preMeeting);
					continue;
				}
			}

			// 解析参会人员
			List<Map<String, Object>> attendeeList = new ArrayList<Map<String, Object>>();
			try {
				String attendees = preMeeting.getAttendees();
				JSONArray attendeeArray = JSONArray.fromObject(attendees);
				for (int i = 0, len = attendeeArray.size(); i < len; i++) {
					JSONObject attendeeJson = attendeeArray.getJSONObject(i);
					// 获取原始值
					String attendeeName = attendeeJson.containsKey("attendee_name")
							? attendeeJson.getString("attendee_name")
							: "";
					String attendFlag = attendeeJson.containsKey("attend_flag") ? attendeeJson.getString("attend_flag")
							: "";
					String reason = attendeeJson.containsKey("reason") ? attendeeJson.getString("reason") : "";

					Attendee attendee = new Attendee();
					attendee.setAttendeeId(CommonUtil.getUUID());
					attendee.setAttendeeName(attendeeName);
					attendee.setMeetingId(meeting.getMeetingId());
					// 判断是否出席
					if ("否".equals(attendFlag)) {
						attendFlag = "0";
					} else if ("是".equals(attendFlag)) {
						attendFlag = "1";
					} else {
						if (StringUtils.isEmpty(reason)) {// 如果原因为空
							attendFlag = "1";
						} else {
							attendFlag = "0";
						}
					}
					attendee.setAttendFlag(attendFlag);
					attendee.setReason(reason);
					attendeeList.add(MapTrunPojo.pojo2Map(attendee));
				}
			} catch (Exception e) {
				logger.error("【数据采集-会议】解析参会人员失败", e);
				preMeeting.setErrorRemark("表决方式转换失败");
				preMeeting.setStatus("9");
				preMeeting.setUpdateTime(DateUtils.getFormatTime());
				preDataService.updatePreMeeting(preMeeting);
				continue;
			}
			try {
				// 保存参会人员
				if (!attendeeList.isEmpty()) {
					bizDBService.batchInsert("tiol_attendee", attendeeList);
				}
				// 插入数据库业务表
				if (preMeeting.getOperType().equals("add")) {
					bizDBService.save("biz_" + CollectDataConstant.MEETING_BIZ_ID, MapTrunPojo.pojo2Map(meeting));
//					meetingMap.put(meeting.getSid(), meeting.getMeetingId());
				} else {
					// 同步删除此会议所有议题
					subjectService.updateSubjectByMeetingSid(meeting.getSid());

					Meeting delMeeting = new Meeting();
					delMeeting.setSid(meeting.getSid());
					delMeeting.setUpdateTime(DateUtils.getFormatTime());
					delMeeting.setStatus("0");
					meetingService.updateMeeting(MapTrunPojo.pojo2Map(delMeeting));
					if (preMeeting.getOperType().equals("edit")) {
						bizDBService.save("biz_" + CollectDataConstant.MEETING_BIZ_ID, MapTrunPojo.pojo2Map(meeting));
//						meetingMap.put(meeting.getSid(), meeting.getMeetingId());
					}

				}

			} catch (Exception e) {
				logger.error("【数据采集-会议】保存会议至业务表时异常", e);
				preMeeting.setErrorRemark("保存至数据库异常");
				preMeeting.setStatus("9");
				preMeeting.setUpdateTime(DateUtils.getFormatTime());
				preDataService.updatePreMeeting(preMeeting);
				continue;
			}
			preMeeting.setStatus("1");
			preMeeting.setUpdateTime(DateUtils.getFormatTime());
			preDataService.updatePreMeeting(preMeeting);
		}
	}

	public void dealSubject(List<PreSubject> preSubjectList) {

		for (PreSubject preSubject : preSubjectList) {// 遍历查询到的未处理议题
			List<Map<String, Object>> relationMapList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> itemMapList = new ArrayList<Map<String, Object>>();
			Subject subject = new Subject();
			String subjectId = preSubject.getId();
			subject.setSubjectId(subjectId);
			subject.setFid(subjectId);
			// 解析关联事项
			if (!StringUtils.isEmpty(preSubject.getItemCode())) {
				String[] itemCodes = preSubject.getItemCode().replaceAll("，", ",").split(",");
				for (String itemCode : itemCodes) {
					if("默认".equals(itemCode)) {
						continue;
					}
					// 根据企业ID、事项编码查询事项ID
					Map<String, Object> parameter = new HashMap<String, Object>();
					parameter.put("COMPANY_ID", preSubject.getCompanyId());
					parameter.put("ITEM_CODE", itemCode);
					parameter.put("STATUS", "1");
					List<Map<String, Object>> itemList = null;
					try {
						itemList = bizDBService.get("biz_" + CollectDataConstant.ITEM_BIZ_ID, parameter);
					} catch (Exception e) {
						logger.error("【数据采集-议题】查询事项ID时异常", e);
						preSubject.setErrorRemark("查询事项ID时异常");
						preSubject.setStatus("9");
						preSubject.setUpdateTime(DateUtils.getFormatTime());
						preDataService.updatePreSubject(preSubject);
						continue;
					}
					if (null == itemList || itemList.isEmpty()) {
						preSubject.setErrorRemark("事项编码[" + preSubject.getItemCode() + "]未找到");
						preSubject.setStatus("2");
						preSubject.setUpdateTime(DateUtils.getFormatTime());
						preDataService.updatePreSubject(preSubject);
						continue;
					}
					String itemId = itemList.get(0).get("ITEM_ID").toString();// 事项ID

					Map<String, Object> itemMap = new HashMap<String, Object>();
					itemMap.put("RELEVANCE_ID", CommonUtil.getUUID());
					itemMap.put("SUBJECT_ID", subjectId);
					itemMap.put("ITEM_ID", itemId);
					itemMap.put("UPDATE_TIME", DateUtils.getFormatTime());
					itemMapList.add(itemMap);
				}
			}
			// 解析关联议题和关联会议
			if (!StringUtils.isEmpty(preSubject.getRelSubjectName())) {
				String relMeetingId = "";// 关联会议ID
				String relMeetingName = "";// 关联会议名称
				String relSubjectId = "";// 关联议题ID
				String relSubjectName = "";// 关联议题名称
				// 如果关联议题是列表
				if (preSubject.getRelSubjectName().startsWith("[")) {
					try {
						JSONArray relationArray = JSONArray.fromObject(preSubject.getRelSubjectName());
						boolean hasError = false;
						for (int i = 0, len = relationArray.size(); i < len; i++) {// 遍历关联议题
							JSONObject relationJson = relationArray.getJSONObject(i);
							relMeetingName = relationJson.containsKey("rel_meeting_name")
									? relationJson.getString("rel_meeting_name")
									: "";
							relSubjectName = relationJson.containsKey("rel_subject_name")
									? relationJson.getString("rel_subject_name")
									: "";

							// 根据企业ID、关联会议名称、关联议题名称，查找关联的议题对象
							Map<String, Object> parameter = new HashMap<String, Object>();
							// a.根据企业ID、关联会议名称查询关联会议ID
							parameter.put("COMPANY_ID", preSubject.getCompanyId());
							parameter.put("MEETING_NAME", relMeetingName);
							parameter.put("STATUS", "1");
							List<Map<String, Object>> meetingList = null;
							try {
								meetingList = bizDBService.get("biz_" + CollectDataConstant.MEETING_BIZ_ID, parameter);
							} catch (Exception e) {
								logger.error("【数据采集-议题】查询关联会议时异常", e);
								preSubject.setErrorRemark("查询关联会议时异常");
								preSubject.setStatus("9");
								preSubject.setUpdateTime(DateUtils.getFormatTime());
								preDataService.updatePreSubject(preSubject);
								hasError = true;
								break;
							}
							if (null == meetingList || meetingList.isEmpty()) {
								preSubject.setErrorRemark("关联会议[" + relMeetingName + "]未找到");
								preSubject.setStatus("2");
								preSubject.setUpdateTime(DateUtils.getFormatTime());
								preDataService.updatePreSubject(preSubject);
								hasError = true;
								break;
							}
							relMeetingId = meetingList.get(0).get("MEETING_ID").toString();

							// b.根据关联会议ID、关联议题名称 查询关联议题ID、和议题编码
							parameter.clear();
							parameter.put("MEETING_ID", relMeetingId);
							parameter.put("SUBJECT_NAME", relSubjectName);
							parameter.put("STATUS", "1");
							List<Map<String, Object>> subList = null;
							try {
								subList = bizDBService.get("biz_" + CollectDataConstant.SUBJECT_BIZ_ID, parameter);
							} catch (Exception e) {
								logger.error("【数据采集-议题】查询关联议题时异常", e);
								preSubject.setErrorRemark("查询关联议题时异常");
								preSubject.setStatus("9");
								preSubject.setUpdateTime(DateUtils.getFormatTime());
								preDataService.updatePreSubject(preSubject);
								hasError = true;
								break;
							}
							if (null == subList || subList.isEmpty()) {
								preSubject.setErrorRemark("关联议题[" + relSubjectName + "]未找到");
								preSubject.setStatus("2");
								preSubject.setUpdateTime(DateUtils.getFormatTime());
								preDataService.updatePreSubject(preSubject);
								hasError = true;
								break;
							}

							relSubjectId = subList.get(0).get("SUBJECT_ID").toString();

							Map<String, Object> relationMap = new HashMap<String, Object>();
							relationMap.put("RELEVANCE_ID", CommonUtil.getUUID());
							relationMap.put("SUBJECT_ID", subjectId);
							relationMap.put("REL_MEETING_ID", relMeetingId);
							relationMap.put("REL_SUBJECT_ID", relSubjectId);
							relationMap.put("REL_MEETING_NAME", relMeetingName);
							relationMap.put("REL_SUBJECT_NAME", relSubjectName);
							relationMap.put("UPDATE_TIME", DateUtils.getFormatTime());
							relationMapList.add(relationMap);
						}
						if(hasError) {
							continue;
						}
					} catch (Exception e) {
						logger.error("【数据采集-议题】关联议题无法解析", e);
						preSubject.setErrorRemark("关联议题无法解析");
						preSubject.setStatus("9");
						preSubject.setUpdateTime(DateUtils.getFormatTime());
						preDataService.updatePreSubject(preSubject);
						continue;
					}
				}
			}

			String sourceId = "";// 任务来源ID
			String specialId = "";// 专项ID
			if (!StringUtils.isEmpty(preSubject.getSourceName()) && !"-".equals(preSubject.getSourceName().trim())) {
				sourceId = sourceMap.get(preSubject.getSourceName());
				if (StringUtils.isEmpty(sourceId)) {
					preSubject.setErrorRemark("任务来源[" + preSubject.getSourceName() + "]未找到");
					preSubject.setStatus("2");
					preSubject.setUpdateTime(DateUtils.getFormatTime());
					preDataService.updatePreSubject(preSubject);
					continue;
				}
			}

			if (!StringUtils.isEmpty(preSubject.getSpecialName()) && !"-".equals(preSubject.getSpecialName().trim())) {
				specialId = specialMap.get(preSubject.getSpecialName());// 专项ID
				if (StringUtils.isEmpty(specialId)) {
					preSubject.setErrorRemark("专项任务[" + preSubject.getSpecialName() + "]未找到");
					preSubject.setStatus("2");
					preSubject.setUpdateTime(DateUtils.getFormatTime());
					preDataService.updatePreSubject(preSubject);
					continue;
				}
			}

			// 构建议题信息实体
			subject.setSid(preSubject.getSubjectId());
			
			//判断会议是否入库
			String meetingId= preSubject.getMeetingId();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("meetingId", meetingId);
			Map<String, Object> meeting = meetingService.queryMeetingInfo(param);
			if(null == meeting || meeting.isEmpty()) {
				logger.warn("【数据采集-议题】此议题所属会议未找到");
				preSubject.setErrorRemark("此议题所属会议未入库");
				preSubject.setStatus("2");
				preSubject.setUpdateTime(DateUtils.getFormatTime());
				preDataService.updatePreSubject(preSubject);
				continue;
			}
			subject.setMeetingId(preSubject.getMeetingId());
			// 字典值判断
			if (!passFlagMap.containsKey(preSubject.getPassFlag())) {
				logger.warn("【数据采集-议题】是否通过[" + preSubject.getPassFlag() + "]未定义");
				preSubject.setErrorRemark("是否通过[" + preSubject.getPassFlag() + "]未定义");
				preSubject.setStatus("2");
				preSubject.setUpdateTime(DateUtils.getFormatTime());
				preDataService.updatePreSubject(preSubject);
				continue;
			}
			if (!approvalFlagMap.containsKey(preSubject.getApprovalFlag())) {
				logger.warn("【数据采集-议题】是否需报国资委审批[" + preSubject.getApprovalFlag() + "]未定义");
				preSubject.setErrorRemark("是否需报国资委审批[" + preSubject.getApprovalFlag() + "]未定义");
				preSubject.setStatus("2");
				preSubject.setUpdateTime(DateUtils.getFormatTime());
				preDataService.updatePreSubject(preSubject);
				continue;
			}
			if (!passFlagMap.containsKey(preSubject.getAdoptFlag())) {
				logger.warn("【数据采集-议题】是否听取意见[" + preSubject.getAdoptFlag() + "]未定义");
				preSubject.setErrorRemark("是否听取意见[" + preSubject.getAdoptFlag() + "]未定义");
				preSubject.setStatus("2");
				preSubject.setUpdateTime(DateUtils.getFormatTime());
				preDataService.updatePreSubject(preSubject);
				continue;
			}

			
			subject.setSubjectName(preSubject.getSubjectName());
			subject.setSourceId(sourceId);
			subject.setSpecialId(specialId);
			subject.setPassFlag(passFlagMap.get(preSubject.getPassFlag()));
			subject.setApprovalFlag(approvalFlagMap.get(preSubject.getApprovalFlag()));
			subject.setAdoptFlag(passFlagMap.get(preSubject.getAdoptFlag()));
			subject.setSubjectResult(preSubject.getSubjectResult());
			subject.setVerifyFlag("0");
			subject.setRemark(preSubject.getRemark());
			subject.setStatus("1");

			// 解析列席人员
			List<Map<String, Object>> attendanceList = new ArrayList<Map<String, Object>>();
			try {
				String attendances = preSubject.getAttendances();
				JSONArray attendanceArray = JSONArray.fromObject(attendances);
				for (int i = 0, len = attendanceArray.size(); i < len; i++) {
					JSONObject attendanceJson = attendanceArray.getJSONObject(i);
					// 获取原始值
					String attendanceName = attendanceJson.containsKey("attendance_name")
							? attendanceJson.getString("attendance_name")
							: "";
					String position = attendanceJson.containsKey("position") ? attendanceJson.getString("position")
							: "";

					Attendance attendance = new Attendance();
					attendance.setAttendanceId(CommonUtil.getUUID());
					attendance.setAttendanceName(attendanceName);
					attendance.setPosition(position);
					if (position.contains("法律顾问")) {
						attendance.setCounselType("法律顾问");
					}
					attendance.setMeetingId(preSubject.getMeetingId());
					attendance.setSubjectId(preSubject.getId());
					attendanceList.add(MapTrunPojo.pojo2Map(attendance));
				}
			} catch (Exception e) {
				logger.error("【数据采集-议题】解析列席人员失败", e);
				preSubject.setErrorRemark("解析列席人员失败");
				preSubject.setStatus("9");
				preSubject.setUpdateTime(DateUtils.getFormatTime());
				preDataService.updatePreSubject(preSubject);
				continue;
			}

			// 解析审议结果
			List<Map<String, Object>> deliberationList = new ArrayList<Map<String, Object>>();
			try {
				String deliberations = preSubject.getDeliberations();
				JSONArray deliberationArray = JSONArray.fromObject(deliberations);
				for (int i = 0, len = deliberationArray.size(); i < len; i++) {
					JSONObject deliberationJson = deliberationArray.getJSONObject(i);
					// 获取原始值
					String deliberationPersonnel = deliberationJson.getString("deliberation_personnel");
					String deliberationResult = deliberationJson.getString("deliberation_result");

					Deliberation deliberation = new Deliberation();
					deliberation.setDeliberationId(CommonUtil.getUUID());
					deliberation.setDeliberationPersonnel(deliberationPersonnel);
					deliberation.setDeliberationResult(deliberationResult);
					deliberation.setMeetingId(preSubject.getMeetingId());
					deliberation.setSubjectId(preSubject.getId());

					deliberationList.add(MapTrunPojo.pojo2Map(deliberation));
				}
			} catch (Exception e) {
				logger.error("【数据采集-会议】解析列些人员失败", e);
				preSubject.setErrorRemark("解析列些人员失败");
				preSubject.setStatus("9");
				preSubject.setUpdateTime(DateUtils.getFormatTime());
				preDataService.updatePreSubject(preSubject);
				continue;
			}

			try {
				// 保存列席人员
				if (!attendanceList.isEmpty()) {
					bizDBService.batchInsert("tiol_attendance", attendanceList);
				}
				//保存议题关联事项
				if (!itemMapList.isEmpty()) {
					bizDBService.batchInsert("tiol_subject_item", itemMapList);
				}
				
				//保存关联议题和会议
				if (!relationMapList.isEmpty()) {
					bizDBService.batchInsert("tiol_subject_relevance", relationMapList);
				}

				// 保存审议
				if (!deliberationList.isEmpty()) {
					bizDBService.batchInsert("tiol_deliberation", deliberationList);
				}
				if (StringUtils.isEmpty(preSubject.getOperType())) {
					preSubject.setErrorRemark("操作类型oper_type为空");
					preSubject.setStatus("2");
					preSubject.setUpdateTime(DateUtils.getFormatTime());
					preDataService.updatePreSubject(preSubject);
					continue;
				}
				// 插入数据库业务表
				if (preSubject.getOperType().equals("add")) {
					bizDBService.save("biz_" + CollectDataConstant.SUBJECT_BIZ_ID, MapTrunPojo.pojo2Map(subject));
				} else {
					Subject delSubject = new Subject();
					delSubject.setSid(subject.getSid());
					delSubject.setStatus("0");
					meetingService.updateMeeting(MapTrunPojo.pojo2Map(delSubject));
					if (preSubject.getOperType().equals("edit")) {
						bizDBService.save("biz_" + CollectDataConstant.SUBJECT_BIZ_ID, MapTrunPojo.pojo2Map(subject));
					}
				}
			} catch (Exception e) {
				logger.error("【数据采集-议题】保存议题至业务表时异常", e);
				preSubject.setErrorRemark("保存至数据库异常");
				preSubject.setStatus("9");
				preSubject.setUpdateTime(DateUtils.getFormatTime());
				preDataService.updatePreSubject(preSubject);
				continue;
			}
			preSubject.setStatus("1");
			preSubject.setUpdateTime(DateUtils.getFormatTime());
			preDataService.updatePreSubject(preSubject);
		}
	}
}
