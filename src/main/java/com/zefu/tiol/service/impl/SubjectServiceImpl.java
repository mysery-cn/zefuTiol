package com.zefu.tiol.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.dal.plugin.child.Page;
import com.yr.gap.engine.util.StringUtils;
import com.zefu.tiol.mapper.oracle.AttendanceMapper;
import com.zefu.tiol.mapper.oracle.CompanyMemberMapper;
import com.zefu.tiol.mapper.oracle.DeliberationMapper;
import com.zefu.tiol.mapper.oracle.ItemMapper;
import com.zefu.tiol.mapper.oracle.MeetingMapper;
import com.zefu.tiol.mapper.oracle.RegulationMapper;
import com.zefu.tiol.mapper.oracle.SubjectMapper;
import com.zefu.tiol.mapper.oracle.SubjectTodoMapper;
import com.zefu.tiol.mapper.oracle.VoteModelMapper;
import com.zefu.tiol.pojo.Attendance;
import com.zefu.tiol.pojo.Deliberation;
import com.zefu.tiol.pojo.Subject;
import com.zefu.tiol.service.BizDBService;
import com.zefu.tiol.service.SubjectService;
import com.zefu.tiol.util.MapTrunPojo;
import com.zefu.tiol.util.MeetingDataUtil;

@Service("subjectService")
public class SubjectServiceImpl implements SubjectService {

	private Logger logger = LoggerFactory.getLogger(SubjectServiceImpl.class);

	@Autowired
	private SubjectMapper subjectMapper;

	@Autowired
	private MeetingMapper meetingMapper;

	@Autowired
	private RegulationMapper regulationMapper;

	@Autowired
	private ItemMapper itemMapper;

	@Autowired
	private DeliberationMapper deliberationMapper;

	@Autowired
	private AttendanceMapper attendanceMapper;

	@Autowired
	private SubjectTodoMapper subjectTodoMapper;

	@Resource(name = "bizDBService")
	private BizDBService bizDBService;

	@Autowired
	private CompanyMemberMapper companyMemberMapper;

	@Autowired
	private VoteModelMapper voteModelMapper;

	@Override
	public List<Map<String, Object>> listSubjectByMeeting(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> subjectList = subjectMapper.listSubjectByMeeting(parameter);
		String companyId = parameter.get("companyId").toString();
		String meetingId = parameter.get("meetingId").toString();
		for (Map<String, Object> map : subjectList) {
			String subjectId = map.get("subjectId").toString();
			List<Map<String, Object>> deliberationList = subjectMapper.listDeliberationBySubject(companyId, subjectId,
					meetingId);
			map.put("deliberationList", deliberationList);
		}
		return subjectList;
	}

	@Override
	public Map<String, Object> getSubjectDetail(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		String companyId = parameter.get("companyId").toString();
		String meetingId = parameter.get("meetingId").toString();
		String subjectId = parameter.get("subjectId").toString();
		Map<String, Object> subject = subjectMapper.getSubjectDetail(parameter);
		Map<String, Object> meeting = meetingMapper.getMeetingDetailById(meetingId, companyId);
		List<Map<String, Object>> deliberationList = subjectMapper.listDeliberationBySubject(companyId, subjectId,
				meetingId);
		map.put("deliberationList", deliberationList);
		map.put("meeting", meeting);
		map.put("subject", subject);
		// 查询听取材料，议题材料
		parameter.put("businessId", subjectId);
		parameter.put("fileTypes", new String[] { "听取意见", "法律意见书" });
		List<Map<String, Object>> opinionFile = subjectMapper.getAttachmentByType(parameter);
		if (opinionFile.size() > 0 && opinionFile != null) {
			map.put("opinionFile", opinionFile);
		} else {
			map.put("opinionFile", "");
		}
		parameter.put("fileTypes", new String[] { "议题材料" });
		List<Map<String, Object>> subjectFile = subjectMapper.getAttachmentByType(parameter);
		if (subjectFile.size() > 0 && subjectFile != null) {
			map.put("subjectFile", subjectFile);
		} else {
			map.put("subjectFile", "");
		}

		List<Map<String, Object>> itemMeetingList = subjectMapper.getRelSubjectItem(parameter);
		for (Map<String, Object> item : itemMeetingList) {
			List<Map<String, Object>> typeMeeting = itemMapper.queryItemMeeting(item);
			item.put("typeMeeting", typeMeeting);
		}
		map.put("itemMeetingList", itemMeetingList);
		return map;
	}

	@Override
	public Map<String, Object> querySubjectMeetingProcess(Map<String, Object> parameter) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 议题决策会议列表查询
		List<Map<String, Object>> list = subjectMapper.getSubjectMeetingList(parameter);
		List<Map<String, Object>> levelNum = subjectMapper.countSubjectMeetingLevel(parameter);

		List<Map<String, Object>> links = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		Map<Integer, Integer> levelCount = new HashMap<Integer, Integer>();

		for (Map<String, Object> maps : list) {
			// 定义箭头指向
			if (maps.get("relMeetingId") != null && !"".equals(maps.get("relMeetingId"))) {
				String[] rels = maps.get("relMeetingId").toString().split(",");
				for (String string : rels) {
					Map<String, Object> link = new HashMap<String, Object>();
					link.put("source", string);
					link.put("target", maps.get("meetingId"));
					links.add(link);
				}
			}

			Map<String, Object> data = new HashMap<String, Object>();
			Integer level = (Integer) maps.get("level");
			Integer count = levelCount.get(level);
			// 定义会议决策y轴坐标
			for (Map<String, Object> nums : levelNum) {
				if (nums.get("levels") == level) {
					if ((Long) nums.get("levelNum") % 2 == 0) {
						if (count == null) {
							levelCount.put(level, 1);
							data.put("y", 150 - (Long) nums.get("levelNum") / 2 * 300);
						} else {
							levelCount.put(level, count + 1);
							data.put("y", (count - (Long) nums.get("levelNum") / 2) * 300 + 150);
						}
					} else {
						if (count == null) {
							levelCount.put(level, 1);
							data.put("y", -(Long) nums.get("levelNum") / 2 * 300);
						} else {
							levelCount.put(level, count + 1);
							data.put("y", (count - (Long) nums.get("levelNum") / 2) * 300);
						}
					}
				}
			}
			data.put("name", maps.get("meetingId"));
			data.put("value", maps.get("meetingName"));
			data.put("x", level * 600);
			if (maps.get("meetingId").equals(parameter.get("meetingId"))) {
				data.put("category", "now");
			}
			datas.add(data);
		}
		result.put("data", datas);
		result.put("link", links);
		return result;

	}

	@Override
	public List<Map<String, Object>> listDangjianSubject(Map<String, Object> parameter,
			Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		int maxRow = page.getCurrentPage() * page.getPageSize();
		int minRow = (page.getCurrentPage() - 1) * page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		List<Map<String, Object>> result = subjectMapper.listDangjianSubject(parameter);
		return result;
	}

	@Override
	public int countDangjianSubject(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		return subjectMapper.countDangjianSubject(parameter);
	}

	@Override
	public List<Map<String, Object>> querySubjectByPage(Map<String, Object> param, Page<Map<String, Object>> page) {
		// TODO Auto-generated method stub
		int maxRow = page.getCurrentPage() * page.getPageSize();
		int minRow = (page.getCurrentPage() - 1) * page.getPageSize();
		param.put("minRow", minRow);
		param.put("maxRow", maxRow);
		List<Map<String, Object>> result = subjectMapper.querySubjectByPage(param);
		return result;
	}

	@Override
	public int getSubjectTotalCount(Map<String, Object> param) {
		return subjectMapper.getSubjectTotalCount(param);
	}

	/**
	 * 平台获取议题列表
	 */
	@Override
	public List<Map<String, Object>> querySubjectList(Map<String, Object> parameter, Page<Map<String, Object>> page)
			throws Exception {
		int maxRow = page.getCurrentPage() * page.getPageSize();
		int minRow = (page.getCurrentPage() - 1) * page.getPageSize();
		parameter.put("minRow", minRow);
		parameter.put("maxRow", maxRow);
		return subjectMapper.querySubjectList(parameter);
	}

	/**
	 * 平台获取议题总数
	 */
	@Override
	public int querySubjectTotalCount(Map<String, Object> parameter) throws Exception {
		return subjectMapper.querySubjectTotalCount(parameter);
	}

	@Override
	public Map<String, Object> getDangjianSubjectCount(Map<String, Object> parameter) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		int totalNum = subjectMapper.countDangjianSubject(parameter);
		parameter.put("preFlag", "1");
		int exceptionNum = subjectMapper.countDangjianSubject(parameter);
		result.put("totalNum", totalNum);
		result.put("exceptionNum", exceptionNum);
		return result;
	}

	@Override
	public void updateSubject(Map<String, Object> param) {
		// TODO Auto-generated method stub
		subjectMapper.updateSubject(param);
	}

	@Override
	public void updateSubjectByMeetingSid(String meetingSid) {
		// TODO Auto-generated method stub
		subjectMapper.updateSubjectByMeetingSid(meetingSid);
	}

	@Override
	public Subject saveSubject(Map<String, Object> parameter) throws Exception {
		String subjectId = parameter.get("subjectId") + "";
		boolean isUpdate = (parameter.get("isUpdate") != null && "1".equals(parameter.get("isUpdate") + ""));// 是否为更新
		if (parameter.get("subjectId") != null && !"".equals(subjectId) && !"null".equals(subjectId)) {
			subjectTodoMapper.updateSubjectTodo(parameter);// 更新待办表
		} else {
			subjectId = CommonUtil.getUUID();
		}
		Subject subject = MeetingDataUtil.getSubjectFromParam(parameter, subjectId);// 获取议题实体

		String meetingId = subject.getMeetingId();
		String userString = parameter.get("subjectUser") + "";// 列席人员
		String deliberations = parameter.get("deliberations") + "";// 审议结果

		List<Attendance> attendanceList = MeetingDataUtil.getAttendanceList(userString, meetingId, subjectId);// 所有列席人员
		List<Deliberation> deliberationList = MeetingDataUtil.getDeliberationList(deliberations, meetingId, subjectId);// 所有审议结果
		List<Map<String, Object>> itemRelevanceList = MeetingDataUtil.getItemRelevance(parameter, subjectId);// 所有关联事项
		List<Map<String, Object>> subjectRelevanceList = MeetingDataUtil.getSubjectRelevance(parameter, subjectId);// 所有关联议题

		// 数据入库操作
		if (isUpdate) {
			// 清楚历史数据
			attendanceMapper.deleteAttendanceBySubejctId(subjectId);
			deliberationMapper.deleteDeliberationBySubejctId(subjectId);
			subjectMapper.removeItemRelevanceBySubejctId(subjectId);
			subjectMapper.removeSubjectRelevanceBySubejctId(subjectId);
			// 更新
			subjectMapper.updateSubject(MapTrunPojo.pojo2Map(subject));
		} else {
			subjectMapper.insertSubject(subject);// 议题入库
		}

		if (attendanceList != null)
			attendanceMapper.batchInsertAttendance(attendanceList);// 列席人员入库
		if (deliberationList != null)
			deliberationMapper.batchInsertDeliberation(deliberationList);// 审议结果入库
		if (itemRelevanceList != null)
			subjectMapper.batchInsertItemRelevance(itemRelevanceList);// 关联事项入库
		if (subjectRelevanceList != null)
			subjectMapper.batchInsertSubjectRelevance(subjectRelevanceList);// 关联议题入库
//		attendanceMapper.batchInsertAttendance(attendanceList);// 列席人员入库
//		deliberationMapper.batchInsertDeliberation(deliberationList);// 审议结果入库
//		if (itemRelevanceList != null)
//			subjectMapper.batchInsertItemRelevance(itemRelevanceList);// 关联事项入库
//		if (subjectRelevanceList != null)
//			subjectMapper.batchInsertSubjectRelevance(subjectRelevanceList);// 关联议题入库
		return subject;
	}

	@Override
	public List<Map<String, Object>> getSubjectByMeetingId(Map<String, Object> parameter) {
		return subjectMapper.getSubjectByMeetingId(parameter);
	}

	@Override
	public Map<String, Object> getSubjectById(Map<String, Object> parameter) {
		if (parameter.get("subjectId") == null || "".equals(parameter.get("subjectId").toString()))
			return null;
		String subjectId = parameter.get("subjectId").toString();
		Map<String, Object> subject = subjectMapper.getSubjectById(subjectId);// 议题信息
		// 列席人员
		List<Map<String, Object>> attendanceList = attendanceMapper.getAttendanceList(parameter);
		String attendances = "";
		for (int i = 0; i < attendanceList.size(); i++) {
			attendances = attendances + attendanceList.get(i).get("attendanceName");
			if (attendanceList.get(i).get("position") != null) {
				attendances = attendances + "(" + attendanceList.get(i).get("position") + "),";
			} else {
				attendances = attendances + ",";
			}
		}
		if (attendances.length() > 0)
			attendances = attendances.substring(0, attendances.length() - 1);
		subject.put("attendanceList", attendances);

		// 审议结果
		List<Map<String, Object>> deliberationList = deliberationMapper.getDeliberationList(parameter);
		String eliberations = "";
		for (int i = 0; i < deliberationList.size(); i++) {
			eliberations = eliberations + deliberationList.get(i).get("deliberationPersonnel");
			eliberations = eliberations + "(" + deliberationList.get(i).get("deliberationResult") + "),";
		}
		if (eliberations.length() > 0)
			eliberations = eliberations.substring(0, eliberations.length() - 1);
		subject.put("deliberationList", eliberations);

		// 关联事项
		List<Map<String, Object>> itemRelevanceList = subjectMapper.getItemRelevanceList(parameter);
		String itemRelevanceCodes = "";
		String itemRelevanceIds = "";
		for (int i = 0; i < itemRelevanceList.size(); i++) {
			if (itemRelevanceList.get(i).get("itemCode") != null) {
				itemRelevanceCodes = itemRelevanceCodes + itemRelevanceList.get(i).get("itemCode") + ",";
				itemRelevanceIds = itemRelevanceIds + itemRelevanceList.get(i).get("itemId") + ",";
			}
		}
		// 去除最后的逗号
		if (itemRelevanceCodes.length() > 0) {
			itemRelevanceCodes = itemRelevanceCodes.substring(0, itemRelevanceCodes.length() - 1);
			itemRelevanceIds = itemRelevanceIds.substring(0, itemRelevanceIds.length() - 1);
		}
		subject.put("itemRelevanceCodes", itemRelevanceCodes);
		subject.put("itemRelevanceIds", itemRelevanceIds);

		// 关联议题
		List<Map<String, Object>> subjectRelevanceList = subjectMapper.getSubjectRelevance(parameter);
		String meetingRelevanceIds = "";
		String subjectRelevanceIds = "";
		String meetingRelevanceNames = "";
		String subjectRelevanceNames = "";
		for (int i = 0; i < subjectRelevanceList.size(); i++) {
			meetingRelevanceIds = meetingRelevanceIds + subjectRelevanceList.get(i).get("relMeetingId") + ",";
			subjectRelevanceIds = subjectRelevanceIds + subjectRelevanceList.get(i).get("relSubjectId") + ",";
			meetingRelevanceNames = meetingRelevanceNames + subjectRelevanceList.get(i).get("relMeetingName") + ",";
			subjectRelevanceNames = subjectRelevanceNames + subjectRelevanceList.get(i).get("relSubjectName") + ",";
		}
		if (meetingRelevanceIds.length() > 0) {
			meetingRelevanceIds = meetingRelevanceIds.substring(0, meetingRelevanceIds.length() - 1);
			subjectRelevanceIds = subjectRelevanceIds.substring(0, subjectRelevanceIds.length() - 1);
			meetingRelevanceNames = meetingRelevanceNames.substring(0, meetingRelevanceNames.length() - 1);
			subjectRelevanceNames = subjectRelevanceNames.substring(0, subjectRelevanceNames.length() - 1);
		}
		subject.put("meetingRelevanceIds", meetingRelevanceIds);
		subject.put("subjectRelevanceIds", subjectRelevanceIds);
		subject.put("meetingRelevanceNames", meetingRelevanceNames);
		subject.put("subjectRelevanceNames", subjectRelevanceNames);

		subject.put("subjectResult", MeetingDataUtil
				.formatStr(subject.get("subjectResult") == null ? null : subject.get("subjectResult").toString()));
		return subject;
	}

	@Override
	public int insertAttachment(Map<String, Object> parameter) {
		return subjectMapper.insertAttachment(parameter);
	}

	@Override
	public List<Map<String, Object>> getSubjectFileById(Map<String, Object> parameter) {
		String subjectId = parameter.get("subjectId") + "";
		return subjectMapper.getSubjectFileById(subjectId);
	}

	@Override
	public int deleteSubjectFileByFileId(Map<String, Object> parameter) {
		String fileId = parameter.get("fileId") + "";
		return subjectMapper.deleteSubjectFileByFileId(fileId);
	}

	@Override
	public void checkSubject() {
		List<Map<String, Object>> exceptionList = new ArrayList<Map<String, Object>>();
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("verifyFlag", "0");
		parameter.put("status", "1");
		Set<String> deliberationAgreeSet = new HashSet<String>();
		// 缓存审议结果同意字典
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("CONFIG_KEY", "cfg_deliberation_agree");
		List<Map<String, Object>> list = bizDBService.get("T_BUSINESS_CONFIG", param);
		for (Map<String, Object> map : list) {
			String[] flagNames = map.get("CONFIG_VALUE").toString().split(",");
			for (String flagName : flagNames) {
				deliberationAgreeSet.add(flagName);
			}
		}
		try {
			// 查询未校验议题
			List<Map<String, Object>> subjectList = subjectMapper.querySubjectList(parameter);
			for (Map<String, Object> subject : subjectList) {
				parameter.clear();
				List<Map<String, Object>> itemList = null;// 议题关联事项列表
				List<Map<String, Object>> typeMeeting = null;// 事项会议顺序
				// 1.判断是否党委会未前置
				// 1.1 获取议题事项
				String subjectId = String.valueOf(subject.get("subjectId"));
				parameter.put("subjectId", subjectId);
				String meetingTypeName = String.valueOf(subject.get("typeName"));
				if ("党委会".equals(meetingTypeName) || "党组会".equals(meetingTypeName)) {// 如果是党委会或党组会，才判断 党委会未前置 异常
					itemList = subjectMapper.getRelSubjectItem(parameter);
					if (itemList.size() == 1) {// 只关联一个事项时，才判断 党委会未前置 异常
						String itemId = String.valueOf(itemList.get(0).get("itemId"));
						// 1.2获取事项会议顺序
						parameter.put("itemId", itemId);
						typeMeeting = itemMapper.queryItemMeeting(parameter);
						if (typeMeeting.size() > 1) {// 事项会议不只一个时，才判断 党委会未前置 异常
							// 1.3 通过判断是否有后续议题 来确定是否为 党委会未前置 异常
							List<Map<String, Object>> subjectAfterList = subjectMapper.getSubjectAfter(parameter);
							if (subjectAfterList.isEmpty()) {
								Map<String, Object> exception = new HashMap<String, Object>();
								exception.put("EXCEPTION_ID", CommonUtil.getUUID());
								exception.put("SUBJECT_ID", subjectId);
								exception.put("EXCEPTION_TYPE", "3");// 会议类型3:党委(党组)会未前置
								exception.put("EXCEPTION_CAUSE", "党委（党组）会未前置");
								exception.put("CONFIRM_FLAG", "0");
								exceptionList.add(exception);
							}
						}
					}
				}

				// 2.判断会议顺序
				// 2.1 获取事项
				if (itemList == null) {
					itemList = subjectMapper.getRelSubjectItem(parameter);
				}
				if (itemList.size() == 1) {// 议题只关联一个事项时，才判断 决策会议顺序不当 异常
					String itemId = String.valueOf(itemList.get(0).get("itemId"));
					if (typeMeeting == null) {
						parameter.put("itemId", itemId);
						typeMeeting = itemMapper.queryItemMeeting(parameter);
					}
					// 2.1获取议题经历的会议顺序
					List<String> meetingTypeList = getMeetingTypeListBF(subjectId);
					List<String> meetingTypeListAF = getMeetingTypeListAF(subjectId);
					if (null != meetingTypeList && null != meetingTypeListAF) {
						meetingTypeList.add(meetingTypeName);
						meetingTypeList.addAll(meetingTypeListAF);
						// 合并相同值
						List<String> typeList = new ArrayList<String>();
						String lastTypeName = "";
						for (String typename : meetingTypeList) {
							if (lastTypeName.equals(typename)) {
								continue;
							} else {
								lastTypeName = typename;
								typeList.add(typename);
							}
						}

						// 比较会议顺序是否一致
						if (typeMeeting.size() != typeList.size()) {
							Map<String, Object> exception = new HashMap<String, Object>();
							exception.put("EXCEPTION_ID", CommonUtil.getUUID());
							exception.put("SUBJECT_ID", subjectId);
							exception.put("EXCEPTION_TYPE", "2");// 会议类型2:决策会议顺序不当
							exception.put("EXCEPTION_CAUSE", "决策会议顺序不当");
							exception.put("CONFIRM_FLAG", "1");
							exceptionList.add(exception);
						} else {
							for (int i = 0; i < typeMeeting.size(); i++) {
								String meetingType1 = String.valueOf(typeMeeting.get(i).get("typeName"));
								String meetingType2 = typeList.get(i);
								if (!meetingType1.equals(meetingType2)) {
									Map<String, Object> exception = new HashMap<String, Object>();
									exception.put("EXCEPTION_ID", CommonUtil.getUUID());
									exception.put("SUBJECT_ID", subjectId);
									exception.put("EXCEPTION_TYPE", "2");// 会议类型2:决策会议顺序不当
									exception.put("EXCEPTION_CAUSE", "决策会议顺序不当");
									exception.put("CONFIRM_FLAG", "1");
									exceptionList.add(exception);
								}
							}
						}
					}
				}

				// 3.未经合法合规审查
				boolean legalFlag = false;
				// 3.1获取所有事项,并判断是否存在需要法律审核的事项
				for (Map<String, Object> item : itemList) {
					if ("1".equals(item.get("legalFlag"))) {
						legalFlag = true;
						break;
					}
				}
				if (legalFlag) {
					// 3.2判断是否有法律顾问参会
					List<Map<String, Object>> attendanceList = subjectMapper.getAttendanceList(parameter);
					boolean haveLegalAttendance = false;
					for (Map<String, Object> attendance : attendanceList) {
						String position = String.valueOf(attendance.get("POSITION"));
						if (!StringUtils.isEmpty(position)) {
							if (position.contains("法律顾问")) {
								haveLegalAttendance = true;
								break;
							}
						}
					}
					// 3.3判断是否有法律意见书
					boolean haveLegalOpion = false;
					if (!haveLegalAttendance) {// 如果没有法律顾问，则判断是否有法律意见书
						Map<String, Object> conParam = new HashMap<String, Object>();
						conParam.put("BUSINESS_ID", subject.get("subjectId"));
						List<Map<String, Object>> attachmentList = bizDBService.get("TIOL_ATTACHMENT", conParam);
						for (Map<String, Object> attachment : attachmentList) {
							String attachmentType = String.valueOf(attachment.get("ATTACHMENT_TYPE"));
							String fileName = String.valueOf(attachment.get("ATTACHMENT_NAME"));
							if ("法律意见书".equals(attachmentType) || fileName.contains("法律意见")) {
								haveLegalOpion = true;
								break;
							}
						}
						if (!haveLegalOpion) {
							Map<String, Object> exception = new HashMap<String, Object>();
							exception.put("EXCEPTION_ID", CommonUtil.getUUID());
							exception.put("SUBJECT_ID", subjectId);
							exception.put("EXCEPTION_TYPE", "1");// 会议类型1:未经合法合规审查
							exception.put("EXCEPTION_CAUSE", "未经合法合规审查");
							exception.put("CONFIRM_FLAG", "1");
							exceptionList.add(exception);
						}
					}

				}

				// 4.会议召开条件不合规
				// 获取参会人员列表
				List<Map<String, Object>> attendList = meetingMapper
						.queryAttendeeList(String.valueOf(subject.get("meetingId")));
				// 4.1 根据会议类型，查询对应的议事规则
				Map<String, Object> procedure = null;
				// 4.1.1获取企业的所有议事规则
				String companyId = String.valueOf(subject.get("companyId"));
				List<Map<String, Object>> regulationlist = regulationMapper.queryProcedure(companyId);
				// 4.1.2获取会议类型别名
				Map<String, Object> conParam = new HashMap<String, Object>();
				conParam.put("TYPE_NAME", meetingTypeName);
				List<String> meetingNameList = new ArrayList<String>();
				meetingNameList.add(meetingTypeName);
				List<Map<String, Object>> meetingType = bizDBService.get("BIZ_TIOL_MEETING_TYPE", conParam);
				String meetNameAlias = String.valueOf(meetingType.get(0).get("ALIAS"));
				if (!StringUtils.isEmpty(meetNameAlias)) {
					String[] aliases = meetNameAlias.split(",");
					for (String alias : aliases) {
						meetingNameList.add(alias);
					}
				}

				// 4.1.3 遍历制度，查找对应制度
				for (Map<String, Object> regulation : regulationlist) {
					for (String meetName : meetingNameList) {
						if (String.valueOf(regulation.get("REGULATION_NAME")).contains(meetName)) {
							procedure = regulation;
							break;
						}
					}
					if (procedure != null) {
						break;
					}
				}
				if (procedure == null) {
					logger.warn("议题[" + subjectId + "]找不到对应的议事规则");
				} else {
					// 4.2判断领导班子需出席人数
					String rate = String.valueOf(procedure.get("RATE"));// 需要出席的人数占比
					String groupType = String.valueOf(meetingType.get(0).get("GROUP_TYPE"));// 领导班子
					// 4.2.1获取领导班子总数
					conParam.clear();
					conParam.put("companyId", companyId);
					conParam.put("groupType", groupType);
					List<Map<String, Object>> memberList = companyMemberMapper.queryCompanyMemberListByGroup(conParam);
					int needAttend = 0;// 应参会人数
					int memSize = memberList.size();
					if (!"1".equals(rate)) {
						if (rate.contains("/")) {
							try {
								int sum = Integer.valueOf(rate.split("\\/")[0]);
								int total = Integer.valueOf(rate.split("\\/")[1]);
								if ((memSize * sum) % total == 0) {// 如果没有余数，则直接取计算结果
									needAttend = memSize * sum / total;
								} else {// 如果有余数，进一
									needAttend = memSize * sum / total;
									needAttend++;
								}
							} catch (Exception e) {
								logger.warn("议题[" + subjectId + "]对应的议事规则[" + procedure.get("REGULATION_ID") + "]人数占比["
										+ rate + "]无法解析");
							}
						} else {
							logger.warn("议题[" + subjectId + "]对应的议事规则[" + procedure.get("REGULATION_ID") + "]人数占比["
									+ rate + "]无法解析");
						}
					}
					if (needAttend != 0) {
						// 4.3 遍历参会人员，计算出席领导数
						int memAttend = 0;
						// 4.3.1
						// 4.3.2遍历参会人员，
						for (Map<String, Object> attend : attendList) {
							String attendName = String.valueOf(attend.get("ATTENDEE_NAME"));
							String attendFlag = String.valueOf(attend.get("ATTEND_FLAG"));
							// 4.3.3遍历领导班子列表，判断当前参会人员是否为领导班子成员
							for (Map<String, Object> leader : memberList) {
								String leaderName = String.valueOf(leader.get("NAME"));
								if (leaderName.equals(attendName)) {
									if ("1".equals(attendFlag)) {// 参会标志
										memAttend++;
										break;
									}
								}
							}
						}
						// 4.4 比较出席人数与需出席人数 判断 是否符合会议召开条件
						if (memAttend < needAttend) {
							Map<String, Object> exception = new HashMap<String, Object>();
							exception.put("EXCEPTION_ID", CommonUtil.getUUID());
							exception.put("SUBJECT_ID", subjectId);
							exception.put("EXCEPTION_TYPE", "4");// 会议类型4:不符合会议召开条件
							exception.put("EXCEPTION_CAUSE", "不符合会议召开条件");
							exception.put("CONFIRM_FLAG", "1");
							exceptionList.add(exception);
						}
					}

					// 5.表决结果不一致

					String passFlag = String.valueOf(subject.get("pass_flag"));
					if ("1".equals(passFlag)) {// 当议题表决结果为通过时才判断
						if (procedure != null) {// 当对应的议事规则制度存在时，才校验表决结果
							// 5.1根据事项ID，获取事项表决方式
							if (itemList.size() == 1) {// 当议题只关联一个事项时，才判断表决结果异常
								String itemId = String.valueOf(itemList.get(0).get("itemId"));
								// 5.1.1获取制度对应的表决方式列表
								conParam.clear();
								conParam.put("regulationID", procedure.get("REGULATION_ID"));
								List<Map<String, Object>> voteList = regulationMapper.queryVoteModeList(conParam);
								Map<String, Object> defaultVote = null;
								for (Map<String, Object> vote : voteList) {
									String voteItemId = String.valueOf(vote.get("itemId"));
									if (StringUtils.isEmpty(voteItemId)) {
										defaultVote = vote;
									}
									if (voteItemId.equals(itemId)) {
										defaultVote = vote;
										break;
									}
								}
								if (null == defaultVote) {
									logger.warn("议题[" + subjectId + "]对应的事项[" + itemId + "]找不到对应的表决方式");
								} else {
									// 5.2 计算赞成票数
									// 5.2.1 获取审议结果列表
									int agreeCount = 0;
									conParam.clear();
									conParam.put("subjectId", subjectId);
									List<Map<String, Object>> deliberationList = subjectMapper
											.getDeliberationList(conParam);
									for (Map<String, Object> deliberation : deliberationList) {
										String deliberationResult = String
												.valueOf(deliberation.get("DELIBERATION_RESULT"));
										if (deliberationAgreeSet.contains(deliberationResult)) {// 如果赞成描述字典中存在 则
											// 5.2.2累加赞成票
											agreeCount++;
										}
									}

									// 5.2.2 根据表决方式，判断应得赞成票数
									int agreeNeed = 0;
									String mode_id = String.valueOf(defaultVote.get("mode_id"));
									conParam.clear();
									conParam.put("modelId", mode_id);
									Map<String, Object> voteMode = voteModelMapper.queryVoteModelDetail(conParam);
									if (null != voteMode) {
										String modelRadix = String.valueOf(voteMode.get("modelRadix"));
										String modelRate = String.valueOf(voteMode.get("modelRate"));
										int radix = memberList.size();// 计算基数，默认取应到会人数
										if ("到会人数".equals(modelRadix)) {
											radix = attendList.size();
										}
										if ("1".equals(modelRate)) {
											agreeNeed = radix;
										} else {
											if (modelRate.contains("/")) {
												try {
													String[] rates = modelRate.split("\\/");
													int sum = Integer.valueOf(rates[0]);
													int total = Integer.valueOf(rates[1]);
													if ((radix * sum) % total == 0) {// 如果没有余数，则直接取计算结果
														agreeNeed = radix * sum / total;
													} else {// 如果有余数，进一
														agreeNeed = radix * sum / total;
														agreeNeed++;
													}
												} catch (Exception e) {
													logger.warn(
															"议题[" + subjectId + "]对应的表决方式[" + mode_id + "]无法计算应得通过票数");
												}
											} else {
												logger.warn("议题[" + subjectId + "]对应的表决方式[" + mode_id + "]无法计算应得通过票数");
											}
										}
										if (agreeCount < agreeNeed) {
											Map<String, Object> exception = new HashMap<String, Object>();
											exception.put("EXCEPTION_ID", CommonUtil.getUUID());
											exception.put("SUBJECT_ID", subjectId);
											exception.put("EXCEPTION_TYPE", "5");// 会议类型4:不符合会议召开条件
											exception.put("EXCEPTION_CAUSE", "表决结果不一致");
											exception.put("CONFIRM_FLAG", "1");
											exceptionList.add(exception);
										}
									} else {
										logger.warn("议题[" + subjectId + "]对应的表决方式[" + mode_id + "]找不到表决方式");
									}
								}
							} else {
								logger.info("议题[" + subjectId + "]存在多个事项");
							}
						}
					}
				}
				// 6.修改议题检查状态
				conParam.clear();
				conParam.put("SUBJECT_ID", subjectId);
				conParam.put("VERIFY_FLAG", "1");
				subjectMapper.updateSubject(conParam);

			}
			// 7.保存议题异常信息
			bizDBService.batchInsert("tiol_subject_exception", exceptionList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private List<String> getMeetingTypeListAF(String subjectId) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("subjectId", subjectId);
		List<Map<String, Object>> subjectRelevanceList = subjectMapper.getSubjectAfter(parameter);
		if (null == subjectRelevanceList || subjectRelevanceList.isEmpty()) {
			return new ArrayList<String>();
		} else {
			if (subjectRelevanceList.size() > 1) {
				return null;
			} else {
				subjectId = String.valueOf(subjectRelevanceList.get(0).get("subjectId"));
				String meetingType = String.valueOf(subjectRelevanceList.get(0).get("typeName"));
				List<String> typeList = getMeetingTypeListAF(subjectId);
				if (null == typeList) {
					return null;
				} else {
					List<String> list = new ArrayList<String>();
					list.add(meetingType);
					list.addAll(typeList);
					return list;
				}
			}
		}
	}

	// 根据议题关联顺序获取议题开会顺序(往前)
	private List<String> getMeetingTypeListBF(String subjectId) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("subjectId", subjectId);
		List<Map<String, Object>> subjectRelevanceList = subjectMapper.getSubjectRelevance(parameter);
		if (null == subjectRelevanceList || subjectRelevanceList.isEmpty()) {
			return new ArrayList<String>();
		} else {
			if (subjectRelevanceList.size() > 1) {
				return null;
			} else {
				String relSubjectId = String.valueOf(subjectRelevanceList.get(0).get("relSubjectId"));
				String meetingType = String.valueOf(subjectRelevanceList.get(0).get("meetingType"));
				List<String> typeList = getMeetingTypeListBF(relSubjectId);
				if (null == typeList) {
					return null;
				} else {
					typeList.add(meetingType);
					return typeList;
				}
			}
		}
	}
}
