package com.zefu.tiol.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yr.gap.common.core.LoginUser;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.common.util.StringUtils;
import com.zefu.tiol.mapper.oracle.GenerateXmlMapper;
import com.zefu.tiol.service.GenerateXmlService;
import com.zefu.tiol.util.FileUtils;
import com.zefu.tiol.util.ListUtil;
import com.zefu.tiol.web.bussiness.XmlParsingBussiness;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
   * @工程名 : szyd
   * @文件名 : GenerateXmlServiceImpl.java
   * @工具包名：com.zefu.tiol.service.impl
   * @功能描述: TODO 生成XML实现类
   * @创建人 ：林鹏
   * @创建时间：2018年11月5日 下午3:42:01
   * @版本信息：V1.0
 */
@Service("generateXmlService")
public class GenerateXmlServiceImpl implements GenerateXmlService {
	
	@Autowired
	private GenerateXmlMapper xmlMapper;

	
	/**
	 * 生成企业下发材料XML
	 * @throws Exception 
	 */
	@Override
	public void GenerateXmlToCompany() throws Exception {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rangeId", "ROOT");
		data.put("appId", "global");
		data.put("CONFIG_KEY", "cfg_downloadXml_url");
		String value = xmlMapper.getConfigValue(data);
		data.put("CONFIG_KEY", "cfg_group_type");
		String groupType = xmlMapper.getConfigValue(data);
		//查询企业列表
		List<Map<String, String>> companyList = xmlMapper.queryCompanyList();
		//初始下载文件地址
		String path = value;
		for (Map<String, String> company : companyList) {
			File[] srcFiles = new File[8];
			String companyId = company.get("companyId").toString();
			String companyName = company.get("companyName").toString();
			FileUtils.createFileUrl(path);
			//1.生成重大决策XML
			JSONObject catalog = GenerateCatalogXml();
			String content = "<?xml version='1.0' encoding='UTF-8'?> \n" + XML.toString(catalog);
			String catalogPath = path + "catalog.xml";
			File fileDir = new File(catalogPath);
			FileUtils.createFile(fileDir);
			try {
				//写入文件
				FileUtils.writeContent(catalogPath,content);
				srcFiles[0] = fileDir;
			} catch (IOException e) {
				e.printStackTrace();
			}
			//2.生成国资委事项清单XML
//			catalog = GenerateGzwIntemXml();
//			content = "<?xml version='1.0' encoding='UTF-8'?> \n" + XML.toString(catalog);
//			catalogPath = path + "item_sasac.xml";
//			fileDir = new File(catalogPath);
//			FileUtils.createFile(fileDir);
//			try {
//				//写入文件
//				FileUtils.writeContent(catalogPath,content);
//				srcFiles[1] = fileDir;
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			//3.生成会议类型XML
			catalog = GenerateMeetingTypeXml();
			content = "<?xml version='1.0' encoding='UTF-8'?> \n" + XML.toString(catalog);
			catalogPath = path + "meeting_type.xml";
			fileDir = new File(catalogPath);
			FileUtils.createFile(fileDir);
			try {
				//写入文件
				FileUtils.writeContent(catalogPath,content);
				srcFiles[1] = fileDir;
			} catch (IOException e) {
				e.printStackTrace();
			}
			//4.生成制度类型XML
			catalog = GenerateRegulationTypeXml();
			content = "<?xml version='1.0' encoding='UTF-8'?> \n" + XML.toString(catalog);
			catalogPath = path + "regulation_type.xml";
			fileDir = new File(catalogPath);
			FileUtils.createFile(fileDir);
			try {
				//写入文件
				FileUtils.writeContent(catalogPath,content);
				srcFiles[2] = fileDir;
			} catch (IOException e) {
				e.printStackTrace();
			}
			//5.生成任务来源XML
			catalog = GenerateSourceXml();
			content = "<?xml version='1.0' encoding='UTF-8'?> \n" + XML.toString(catalog);
			catalogPath = path + "source.xml";
			fileDir = new File(catalogPath);
			FileUtils.createFile(fileDir);
			try {
				//写入文件
				FileUtils.writeContent(catalogPath,content);
				srcFiles[3] = fileDir;
			} catch (IOException e) {
				e.printStackTrace();
			}
			//6.生成专项任务XML
			catalog = GenerateSpecialXml();
			content = "<?xml version='1.0' encoding='UTF-8'?> \n" + XML.toString(catalog);
			catalogPath = path + "special.xml";
			fileDir = new File(catalogPath);
			FileUtils.createFile(fileDir);
			try {
				//写入文件
				FileUtils.writeContent(catalogPath,content);
				srcFiles[4] = fileDir;
			} catch (IOException e) {
				e.printStackTrace();
			}
			//7.企业领导班子
//			catalog = GenerateCompanyMemberXml(companyId,groupType);
//			content = "<?xml version='1.0' encoding='UTF-8'?> \n" + XML.toString(catalog);
//			catalogPath = path + "company_member.xml";
//			fileDir = new File(catalogPath);
//			FileUtils.createFile(fileDir);
//			try {
//				//写入文件
//				FileUtils.writeContent(catalogPath,content);
//				srcFiles[6] = fileDir;
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			//8.制度表决方式
			catalog = GenerateVoteModeXml();
			content = "<?xml version='1.0' encoding='UTF-8'?> \n" + XML.toString(catalog);
			catalogPath = path + "vote_mode.xml";
			fileDir = new File(catalogPath);
			FileUtils.createFile(fileDir);
			try {
				//写入文件
				FileUtils.writeContent(catalogPath,content);
				srcFiles[5] = fileDir;
			} catch (IOException e) {
				e.printStackTrace();
			}
			//9.议题异常情况
			catalog = GenerateSubjectExceptionXml(companyId);
			content = "<?xml version='1.0' encoding='UTF-8'?> \n" + XML.toString(catalog);
			catalogPath = path + "subject_exception.xml";
			fileDir = new File(catalogPath);
			FileUtils.createFile(fileDir);
			try {
				//写入文件
				FileUtils.writeContent(catalogPath,content);
				srcFiles[6] = fileDir;
			} catch (IOException e) {
				e.printStackTrace();
			}
			//10.企业事项清单列表
			catalog = GenerateCompanyItemXml(companyId);
			content = "<?xml version='1.0' encoding='UTF-8'?> \n" + XML.toString(catalog);
			catalogPath = path + "item.xml";
			fileDir = new File(catalogPath);
			FileUtils.createFile(fileDir);
			try {
				//写入文件
				FileUtils.writeContent(catalogPath,content);
				srcFiles[7] = fileDir;
			} catch (IOException e) {
				e.printStackTrace();
			}
			File zipFile = new File(path + companyName + "_" + companyId + ".zip");
			//生成压缩包
			FileUtils.zipFiles(srcFiles,zipFile);
		}
	}
	
	/**
	 * 解析企业材料
	 */
	@Override
	public void parsingXmlToCompany(LoginUser loginUser, String fileName) throws Exception {
		String savePath = "D:\\企业材料\\";
		String urlStr = "http://192.168.137.45:8088/DataCollectorEnterpriseWeb/dataPushAction/downLoadFile.do?API_CODE=SZ01&BUSINESS_TYPE=0002&USER=admin&PASSWORD=admin";
		String pathName = "企业材料";
		List<File> files = XmlParsingBussiness.downloadFile(urlStr, savePath, pathName);
		//解析XML
		if(ListUtil.isNotBlank(files)){
			for (File file : files) {
				// 解析xml文件
				SAXReader reader = new SAXReader();
				Document document = null;
				try {
					document = reader.read(file);
				} catch (DocumentException e) {
					e.printStackTrace();
				}
				Element node = document.getRootElement();
				//事项目录
				if((fileName).equals(file.getName()) && ("catalog.xml").equals(file.getName())){
					saveCatalogData(node);
					break;
				}
				//事项清单
				if((fileName).equals(file.getName()) && ("item.xml").equals(file.getName())){
					saveItem(node,loginUser.getCompanyId());
					break;
				}
				//会议类型
				if((fileName).equals(file.getName()) && ("meeting_type.xml").equals(file.getName())){
					saveMeetingType(node);
					break;
				}
				//制度类型
				if((fileName).equals(file.getName()) && ("regulation_type.xml").equals(file.getName())){
					saveRegulationType(node);
					break;
				}
				//任务来源
				if((fileName).equals(file.getName()) && ("source.xml").equals(file.getName())){
					saveSource(node);
					break;
				}
				//专项任务
				if((fileName).equals(file.getName()) && ("special.xml").equals(file.getName())){
					saveSpecial(node);
					break;
				}
				//议题异常信息
				if((fileName).equals(file.getName()) && ("subject_exception.xml").equals(file.getName())){
					saveSubjectException(node);
					break;
				}
				//制度表决信息
				if((fileName).equals(file.getName()) && ("vote_mode.xml").equals(file.getName())){
					saveVoteModel(node);
					break;
				}
			}
		}
	}
	
	
	
	
	/**
	   * @功能描述: TODO 保存表决方式
	   * @参数: @param node
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月17日 上午10:44:12
	 */
	private void saveVoteModel(Element node) {
		List<Element> voteModeList = node.element("vote_mode_list").elements("vote_mode");
		for (Element voteMode : voteModeList) {
			Map<String, Object> voteModeMap = new HashMap<String, Object>();
			voteModeMap.put("mode_id", CommonUtil.getUUID());
			voteModeMap.put("mode_name", voteMode.elementText("mode_name"));
			voteModeMap.put("mode_radix", voteMode.elementText("mode_radix"));
			voteModeMap.put("mode_rate", voteMode.elementText("mode_rate"));
            //判断数据是否已存在 是跳过
            if(xmlMapper.checkVoteModelExist(voteMode.elementText("mode_name")) > 0){
                continue;
            }
			xmlMapper.saveVoteModel(voteModeMap);
		}
	}

	/**
	   * @功能描述: TODO 保存议题异常信息
	   * @参数: @param node
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月17日 上午10:34:59
	 */
	private void saveSubjectException(Element node) {
		List<Element> subjectExceptionList = node.element("subject_exception_list").elements("subject_exception");
		for (Element subjectException : subjectExceptionList) {
			Map<String, Object> subjectExceptionMap = new HashMap<String, Object>();
			subjectExceptionMap.put("exceptionId", CommonUtil.getUUID());
			subjectExceptionMap.put("subjectId", subjectException.elementText("subject_id"));
			subjectExceptionMap.put("exceptionType", subjectException.elementText("exception_type"));
			subjectExceptionMap.put("exceptionCause", subjectException.elementText("exception_cause"));
            //判断数据是否已存在 是跳过
            if(xmlMapper.checkSubjectExceptionExist(subjectException.elementText("subject_id")) > 0){
                continue;
            }
			xmlMapper.saveSubjectException(subjectExceptionMap);
		}
	}

	/**
	   * @功能描述: TODO 保存专项任务
	   * @参数: @param node
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月16日 下午10:23:22
	 */
	private void saveSpecial(Element node) {
		List<Element> specialList = node.element("special_list").elements("special_name");
		for (Element special : specialList) {
			String name = special.getData().toString();
			Map<String, Object> specialMap = new HashMap<String, Object>();
			specialMap.put("TYPE_ID", CommonUtil.getUUID());
			specialMap.put("special_name", name);
			//判断数据是否已存在 是跳过
            if(xmlMapper.checkSprcialExist(name) > 0){
                continue;
            }
			xmlMapper.saveSpecial(specialMap);
		}
	}

	/**
	   * @功能描述: TODO 保存任务来源
	   * @参数: @param node
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月16日 下午10:12:30
	 */
	private void saveSource(Element node) {
		List<Element> sourceList = node.element("source_list").elements("source");
		for (Element source : sourceList) {
			String name = source.getData().toString();
			Map<String, Object> sourceMap = new HashMap<String, Object>();
			sourceMap.put("TYPE_ID", CommonUtil.getUUID());
			sourceMap.put("TYPE_NAME", name);
            //判断数据是否已存在 是跳过
            if(xmlMapper.checkSourceExist(name) > 0){
                continue;
            }
			xmlMapper.saveSource(sourceMap);
		}
	}

	/**
	   * @功能描述: TODO 保存制度类型
	   * @参数: @param node
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月16日 下午10:07:03
	 */
	private void saveRegulationType(Element node) {
		List<Element> regulationTypeList = node.element("regulation_type_list").elements("regulation_type");
		int orderNumber = 1;
		for (Element regulationType : regulationTypeList) {
			String name = regulationType.getData().toString();
			Map<String, Object> groupMap = new HashMap<String, Object>();
			groupMap.put("TYPE_ID", CommonUtil.getUUID());
			groupMap.put("TYPE_NAME", name);
			groupMap.put("ORDER_NUMBER", orderNumber++);
            //判断数据是否已存在 是跳过
            if(xmlMapper.checkRegulationTypeExist(name) > 0){
                continue;
            }
			xmlMapper.saveRegulationType(groupMap);
		}
	}

	/**
	   * @功能描述: TODO 保存会议类型
	   * @参数: @param node
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月16日 下午9:31:41
	 */
	private void saveMeetingType(Element node) {
		List<Element> meetingTypeList = node.element("meeting_type_list").elements("meeting_type");
		int orderNumber = 1;
		for (Element meetingType : meetingTypeList) {
			Map<String, Object> groupMap = new HashMap<String, Object>();
			groupMap.put("TYPE_ID", CommonUtil.getUUID());
			groupMap.put("TYPE_NAME", meetingType.elementText("type_name"));
			groupMap.put("GROUP_TYPE", meetingType.elementText("group_type"));
			groupMap.put("ORDER_NUMBER", orderNumber++);
            //判断数据是否已存在 是跳过
            if(xmlMapper.checkMeetingTypeExist(meetingType.elementText("type_name")) > 0){
                continue;
            }
			xmlMapper.saveMeetingType(groupMap);
		}
	}

	/**
	   * @功能描述: TODO 保存事项清单
	   * @参数: @param node
	   * @参数: @param companyId
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月16日 下午8:11:34
	 */
	private void saveItem(Element node, String companyId) {
		List<Element> itemList = node.element("item_list").elements("item");
		int orderNumber = 1;
		for (Element item : itemList) {
			Map<String, Object> itemMap = new HashMap<String, Object>();
			String itemId = CommonUtil.getUUID();
			itemMap.put("itemId", itemId);
			itemMap.put("legal_flag", item.elementText("legal_flag"));
			itemMap.put("item_name", item.elementText("item_name"));
			itemMap.put("item_code", item.elementText("item_code"));
			String catalogCode = item.elementText("catalog_code");
			itemMap.put("catalog_id", xmlMapper.queryCatalogId(catalogCode));
			itemMap.put("orderNumber", orderNumber++);
			itemMap.put("companyId", companyId);
			itemMap.put("status", item.elementText("status"));
			//判断数据是否已存在 是跳过
			if(xmlMapper.checkExist(item.elementText("item_name")) > 0){
                continue;
            }
			xmlMapper.saveItem(itemMap);
			//事项清单会议排序
			List<Element> meetingNameList = item.element("item_meeting_list").elements("item_meeting");
			int order = 1;
			for (Element meeting : meetingNameList) {
				Map<String, Object> meetingData = new HashMap<String, Object>();
				meetingData.put("item_meeting_id", CommonUtil.getUUID());
				String meetingTypeName = meeting.getData().toString();
				if(StringUtils.isBlank(meetingTypeName)){
					continue;
				}
				// 查询会议类型ID
				String meetingTypeId = xmlMapper.queryMeetingTypeId(meetingTypeName);
				if(StringUtils.isBlank(meetingTypeId)){
					continue;
				}
				meetingData.put("meetingTypeId", meetingTypeId);
				meetingData.put("item_id", itemId);
				meetingData.put("order", order++);
				xmlMapper.saveItemMeeting(meetingData);
			}
		}
	}

	/**
	   * @param companyId 
	   * @功能描述: TODO 保存企业领导班子
	   * @参数: @param node
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月16日 下午7:54:07
	 */
	@SuppressWarnings("unchecked")
	private void saveCompanyMember(Element node, String companyId) {
		List<Element> groupList = node.element("group_list").elements("group");
		for (Element group : groupList) {
			String groupType = group.elementText("group_type");
			//保存一级事项目录
			List<Element> memberList = group.element("member_list").elements("member");
			int orderNumber = 1;
			for (Element member : memberList) {
				Map<String, Object> groupMap = new HashMap<String, Object>();
				groupMap.put("group_type", groupType);
				groupMap.put("member_id", CommonUtil.getUUID());
				groupMap.put("start_date", member.elementText("start_date"));
				groupMap.put("end_date", member.elementText("end_date"));
				groupMap.put("company_id", member.elementText("company_id"));
				groupMap.put("name", member.elementText("name"));
				groupMap.put("position", member.elementText("position"));
				groupMap.put("status", member.elementText("status"));
				groupMap.put("order_number", orderNumber++);
				xmlMapper.saveCompanyMember(groupMap);
			}
		}
	}

	/**
	   * @功能描述: TODO 保存事项目录
	   * @参数: @param xmlMessage
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月16日 下午5:35:22
	 */
	@SuppressWarnings("unchecked")
	private void saveCatalogData(Element node) {
		List<Element> catalogList = node.element("catalog_list").elements("catalog");
		for (Element catalog : catalogList) {
			System.out.println("保存一级事项目录:" + catalog);
			//保存一级事项目录
			Map<String, Object> catalogMap = new HashMap<String, Object>();
			String catalogId = CommonUtil.getUUID();
			catalogMap.put("catalogId", catalogId);
			catalogMap.put("CATALOG_PID", "ROOT");
			catalogMap.put("order_number", catalog.elementText("order_number"));
			catalogMap.put("catalog_code", catalog.elementText("catalog_code"));
			catalogMap.put("catalog_level", catalog.elementText("catalog_level"));
			catalogMap.put("catalog_name", catalog.elementText("catalog_name"));
			catalogMap.put("status", catalog.elementText("status"));
            //判断数据是否已存在 是跳过
            String CatalogDetail = xmlMapper.queryCatalogDetail(catalog.elementText("catalog_name"),catalog.elementText("catalog_level"));
            if(StringUtils.isBlank(CatalogDetail)){
                xmlMapper.saveCatalogData(catalogMap);
            }else{
                catalogId = CatalogDetail;
            }
			//继续解析
			List<Element> secondCatalog = catalog.element("catalog_list").elements("catalog");
			if(ListUtil.isNotBlank(secondCatalog)){
				for (Element second : secondCatalog) {
					System.out.println("保存二级事项目录:" + second);
					//保存二级事项目录
					String secondCatalogId = CommonUtil.getUUID();
					Map<String, Object> secondMap = new HashMap<String, Object>();
					secondMap.put("catalogId", secondCatalogId);
					secondMap.put("CATALOG_PID", catalogId);
					secondMap.put("order_number", second.elementText("order_number"));
					secondMap.put("catalog_code", second.elementText("catalog_code"));
					secondMap.put("catalog_level", second.elementText("catalog_level"));
					secondMap.put("catalog_name", second.elementText("catalog_name"));
					secondMap.put("status", second.elementText("status"));
                    //判断数据是否已存在 是跳过
                    String CatalogDetail_s = xmlMapper.queryCatalogDetail(second.elementText("catalog_name"),second.elementText("catalog_level"));
                    if(StringUtils.isBlank(CatalogDetail_s)){
                        xmlMapper.saveCatalogData(catalogMap);
                    }else{
                        secondCatalogId = CatalogDetail_s;
                    }
					xmlMapper.saveCatalogData(secondMap);
					List<Element> threeCatalog = second.element("catalog_list").elements("catalog");
					for (Element three : threeCatalog) {
						System.out.println("保存三级事项目录:" + three);
						Map<String, Object> threeMap = new HashMap<String, Object>();
						threeMap.put("catalogId", CommonUtil.getUUID());
						threeMap.put("CATALOG_PID", secondCatalogId);
						threeMap.put("order_number", three.elementText("order_number"));
						threeMap.put("catalog_code", three.elementText("catalog_code"));
						threeMap.put("catalog_level", three.elementText("catalog_level"));
						threeMap.put("catalog_name", three.elementText("catalog_name"));
						threeMap.put("status", three.elementText("status"));
                        String CatalogDetail_t = xmlMapper.queryCatalogDetail(second.elementText("catalog_name"),second.elementText("catalog_level"));
                        if(StringUtils.isBlank(CatalogDetail_t)){
                            xmlMapper.saveCatalogData(threeMap);
                        }
					}
				}
			}
		}
	}

	/**
	   * @功能描述: TODO 获取企业事项清单列表
	   * @参数: @param companyId
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月8日 下午4:05:37
	 */
	private JSONObject GenerateCompanyItemXml(String companyId) {
		JSONObject message = new JSONObject();//总结果
		JSONObject result = new JSONObject(); //一级结果
		JSONArray dataArry = new JSONArray();//结果数组
		JSONObject data = new JSONObject(); //二级结果
		List<Map<String, Object>> gzwItemList = xmlMapper.GenerateCompanyItemXml(companyId);
		if(gzwItemList.size() > 0 && gzwItemList != null){
			for (Map<String, Object> item : gzwItemList) {
				JSONObject second = new JSONObject(); //二级结果
				JSONArray secondArry = new JSONArray();//结果数组
				//查询开会顺序
				List<String> meetingTypeList = xmlMapper.GenerateItemMeetingTypeXml(item.get("itemId").toString());
				if(meetingTypeList != null && meetingTypeList.size() > 0){
					for (String meetingType : meetingTypeList) {
						secondArry.add(meetingType);
					}
				}
				item.remove("itemId");
				JSONObject jsonItem = new JSONObject(JSON.toJSONString(item));
				second.put("item_meeting", secondArry);
				jsonItem.put("item_meeting_list", second);
				dataArry.add(jsonItem);
			}
		}
		data.put("item", dataArry);
		result.put("item_list", data);
		message.put("tiol", result);
		return message;
	}

	/**
	   * @功能描述: TODO 查询企业异常
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月7日 下午3:08:12
	 */
	private JSONObject GenerateSubjectExceptionXml(String companyId) {
		JSONObject message = new JSONObject();//总结果
		JSONObject result = new JSONObject(); //一级结果
		JSONArray dataArry = new JSONArray();//结果数组
		JSONObject data = new JSONObject(); //二级结果
		List<Map<String, Object>> gzwItemList = xmlMapper.GenerateSubjectExceptionXml(companyId);
		if(gzwItemList.size() > 0 && gzwItemList != null){
			for (Map<String, Object> item : gzwItemList) {
				JSONObject jsonItem = new JSONObject(JSON.toJSONString(item));
				dataArry.add(jsonItem);
			}
		}
		data.put("subject_exception", dataArry);
		result.put("subject_exception_list", data);
		message.put("tiol", result);
		return message;
	}

	/**
	   * @功能描述: TODO 表决方式
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月7日 下午2:58:31
	 */
	private JSONObject GenerateVoteModeXml() {
		JSONObject message = new JSONObject();//总结果
		JSONObject result = new JSONObject(); //一级结果
		JSONArray dataArry = new JSONArray();//结果数组
		JSONObject data = new JSONObject(); //二级结果
		List<Map<String, Object>> gzwItemList = xmlMapper.GenerateVoteModeXml();
		if(gzwItemList.size() > 0 && gzwItemList != null){
			for (Map<String, Object> item : gzwItemList) {
				JSONObject jsonItem = new JSONObject(JSON.toJSONString(item));
				dataArry.add(jsonItem);
			}
		}
		data.put("vote_mode", dataArry);
		result.put("vote_mode_list", data);
		message.put("tiol", result);
		return message;
	}

	/**
	   * @功能描述: TODO 企业领导班子
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月7日 下午2:34:13
	 */
	private JSONObject GenerateCompanyMemberXml(String companyId,String groupType) {
		JSONObject message = new JSONObject();//总结果
		JSONObject result = new JSONObject(); //一级结果
		JSONArray dataArry = new JSONArray();//结果数组
		JSONObject data = new JSONObject(); //二级结果
		//初始领导班子类型
		String[] groupTypes = groupType.split(",");
		for (int i = 0; i < groupTypes.length; i++) {
			JSONObject companyType_dw = new JSONObject(); //二级结果
			JSONArray member_list = new JSONArray();//结果数组
			companyType_dw.put("group_type", groupTypes[i]);
			List<Map<String, Object>> companyMember = xmlMapper.GenerateCompanyMemberXml(groupTypes[i],companyId);
			JSONObject data_company_dw = new JSONObject();
			if(companyMember.size() > 0 && companyMember != null){
				for (Map<String, Object> item : companyMember) {
					JSONObject jsonItem = new JSONObject(JSON.toJSONString(item));
					member_list.add(jsonItem);
				}
			}
			
			data_company_dw.put("member", member_list);
			companyType_dw.put("member_list", data_company_dw);
			dataArry.add(companyType_dw);
		}
		data.put("group", dataArry);
		result.put("group_list", data);
		message.put("tiol", result);
		return message;
	}

	/**
	   * @功能描述: TODO 专项任务
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月6日 下午7:37:30
	 */
	private JSONObject GenerateSpecialXml() {
		JSONObject message = new JSONObject();//总结果
		JSONObject result = new JSONObject(); //一级结果
		JSONArray dataArry = new JSONArray();//结果数组
		JSONObject data = new JSONObject(); //二级结果
		List<String> gzwItemList = xmlMapper.GenerateSpecialXml();
		if(gzwItemList.size() > 0 && gzwItemList != null){
			for (String item : gzwItemList) {
				dataArry.add(item);
			}
		}
		data.put("special", dataArry);
		result.put("special_list", data);
		message.put("tiol", result);
		return message;
	}

	/**
	   * @功能描述: TODO 任务来源
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月6日 下午7:34:41
	 */
	private JSONObject GenerateSourceXml() {
		JSONObject message = new JSONObject();//总结果
		JSONObject result = new JSONObject(); //一级结果
		JSONArray dataArry = new JSONArray();//结果数组
		JSONObject data = new JSONObject(); //二级结果
		List<String> gzwItemList = xmlMapper.GenerateSourceXml();
		if(gzwItemList.size() > 0 && gzwItemList != null){
			for (String item : gzwItemList) {
				dataArry.add(item);
			}
		}
		data.put("source", dataArry);
		result.put("source_list", data);
		message.put("tiol", result);
		return message;
	}

	/**
	   * @功能描述: TODO 查询制度类型
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月6日 下午7:32:07
	 */
	private JSONObject GenerateRegulationTypeXml() {
		JSONObject message = new JSONObject();//总结果
		JSONObject result = new JSONObject(); //一级结果
		JSONArray dataArry = new JSONArray();//结果数组
		JSONObject data = new JSONObject(); //二级结果
		List<String> gzwItemList = xmlMapper.GenerateRegulationTypeXml();
		if(gzwItemList.size() > 0 && gzwItemList != null){
			for (String item : gzwItemList) {
				dataArry.add(item);
			}
		}
		data.put("regulation_type", dataArry);
		result.put("regulation_type_list", data);
		message.put("tiol", result);
		return message;
	}

	/**
	   * @功能描述: TODO 生成会议类型
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月6日 下午7:20:24
	 */
	private JSONObject GenerateMeetingTypeXml() {
		JSONObject message = new JSONObject();//总结果
		JSONObject result = new JSONObject(); //一级结果
		JSONArray dataArry = new JSONArray();//结果数组
		JSONObject data = new JSONObject(); //一级结果
		List<Map<String, Object>> gzwItemList = xmlMapper.GenerateMeetingTypeXml();
		if(gzwItemList.size() > 0 && gzwItemList != null){
			for (Map<String, Object> item : gzwItemList) {
				JSONObject jsonItem = new JSONObject(JSON.toJSONString(item));
				dataArry.add(jsonItem);
			}
		}
		data.put("meeting_type", dataArry);
		result.put("meeting_type_list", data);
		message.put("tiol", result);
		return message;
	}

	/**
	   * @功能描述: TODO 生成国资委事项清单
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月6日 下午7:01:01
	 */
	private JSONObject GenerateGzwIntemXml() {
		JSONObject message = new JSONObject();//总结果
		JSONObject result = new JSONObject(); //一级结果
		JSONArray dataArry = new JSONArray();//结果数组
		JSONObject data = new JSONObject(); //二级结果
		List<Map<String, Object>> gzwItemList = xmlMapper.getGzwItemList("GZW");
		if(gzwItemList.size() > 0 && gzwItemList != null){
			for (Map<String, Object> item : gzwItemList) {
				JSONObject jsonItem = new JSONObject(JSON.toJSONString(item));
				dataArry.add(jsonItem);
			}
		}
		data.put("item", dataArry);
		result.put("item_list", data);
		message.put("tiol", result);
		return message;
	}

	/**
	   * @功能描述: TODO 生成重大决策
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月5日 下午3:53:14
	 */
	private JSONObject GenerateCatalogXml() {
		//返回值
		JSONObject message = new JSONObject();//总结果
		JSONObject result = new JSONObject(); //一级结构
		JSONArray catalogJsonO = new JSONArray();//
		JSONObject datas = new JSONObject();//一级结果
		//参数值
		Map<String, Object> param = new HashMap<String, Object>();
		//顶级节点
		param.put("level", "1");
		List<Map<String, Object>> catalogTreeTop = xmlMapper.queryCatalogTree(param);
		if(catalogTreeTop.size() > 0 && catalogTreeTop != null){
			for (Map<String, Object> top : catalogTreeTop) {
				//查询二级节点
				JSONArray catalogJson1 = new JSONArray();
				JSONObject data1 = new JSONObject();
				param.put("level", "2");
				param.put("catalogPid", top.get("catalogId"));
				top.remove("catalogId");
				JSONObject jsonTop = new JSONObject(JSON.toJSONString(top));
				List<Map<String, Object>> catalogTreeSecondary = xmlMapper.queryCatalogTree(param);
				if(catalogTreeSecondary.size() > 0 && catalogTreeSecondary != null){
					for (Map<String, Object> secondary : catalogTreeSecondary) {
						JSONObject data3 = new JSONObject();
						JSONArray catalogJson2 = new JSONArray();
						param.put("level", "3");
						param.put("catalogPid", secondary.get("catalogId"));
						secondary.remove("catalogId");
						JSONObject jsonSecond = new JSONObject(JSON.toJSONString(secondary));
						List<Map<String, Object>> catalogTreeThreeary = xmlMapper.queryCatalogTree(param);
						if(catalogTreeThreeary.size() > 0 && catalogTreeThreeary != null){
							for (Map<String, Object> threeary : catalogTreeThreeary) {
								threeary.remove("catalogId");
								JSONObject jsonThree = new JSONObject(JSON.toJSONString(threeary));
								catalogJson2.add(jsonThree);
							}
						}
						data3.put("catalog", catalogJson2);
						jsonSecond.put("catalog_list", data3);
						catalogJson1.add(jsonSecond);
					}
				}
				data1.put("catalog", catalogJson1);
				jsonTop.put("catalog_list", data1);
				catalogJsonO.add(jsonTop);
			}
		}
		datas.put("catalog", catalogJsonO);
		result.put("catalog_list", datas);
		message.put("tiol", result);
		return message;
	}
}
