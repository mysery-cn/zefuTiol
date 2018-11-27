package com.zefu.tiol.service.impl;

import com.yr.gap.common.core.LoginUser;
import com.yr.gap.common.util.CommonUtil;
import com.yr.gap.common.util.DateUtil;
import com.yr.gap.common.util.StringUtils;
import com.zefu.tiol.mapper.oracle.ImportExcelMapper;
import com.zefu.tiol.mapper.oracle.MeetingMapper;
import com.zefu.tiol.mapper.oracle.SubjectMapper;
import com.zefu.tiol.pojo.*;
import com.zefu.tiol.service.ImportExcelService;
import com.zefu.tiol.util.ExcelBase;
import com.zefu.tiol.util.ExcelStringUtil;
import com.zefu.tiol.util.ListUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
   * @工程名 : szyd
   * @文件名 : ImportExcelServiceImpl.java
   * @工具包名：com.zefu.tiol.service.impl
   * @功能描述: TODO Excel文件导入业务层
   * @创建人 ：林鹏
   * @创建时间：2018年11月12日 下午8:17:50
   * @版本信息：V1.0
 */
@Service("importExcelService")
public class ImportExcelServiceImpl extends ExcelBase implements ImportExcelService{
	
	@Autowired
	private ImportExcelMapper excelMapper;
	
	@Autowired
	private MeetingMapper meetingMapper;
	
	@Autowired
	private SubjectMapper subjectMapper;
	
	public static final String SHEET_NAME =  "有关决策会议";
	
	/**
	 * 导入会议
	 */
	@Override
	public String importMeeting(InputStream inputFile, LoginUser loginUser) throws Exception{
	    String errMsg = "";
		// IO流读取文件  
		Workbook wb = WorkbookFactory.create(inputFile);
		// 获取会议所在序号
		int sheetIdx = getSheetIdx(wb,SHEET_NAME);
		if(sheetIdx == -1){
			throw new Exception("文件不包含会议信息Sheet");
		}
		//解析Excel
		List<Meeting> meetingList = readExcel(wb, sheetIdx, 3, 0);
		if(ListUtil.isNotBlank(meetingList)){
		    int row = 1;
			//导入数据
			for (Meeting meeting : meetingList) {
				//会议主键ID
				String uuid = CommonUtil.getUUID();
				//查询会议类型ID
				String typeId = excelMapper.queryMeetingType(meeting.getAlias());
				if(StringUtils.isBlank(typeId)){
					List<Map<String, String>> meetingTypeList = excelMapper.queryMeetingTypeList(meeting.getAlias());
					if(ListUtil.isNotBlank(meetingTypeList)){
						for (Map<String, String> map : meetingTypeList) {
							String[] alias = map.get("alias").split(",");
							for (int i = 0; i < alias.length; i++) {
								if(alias[i].equals(meeting.getAlias())){
									meeting.setTypeId(map.get("typeId"));
									break;
								}
							}
						}
					}
				}else{
					meeting.setTypeId(typeId);
				}
				//保存会议信息
				meeting.setFid(uuid);
				meeting.setMeetingId(uuid);
				meeting.setcCreaterid(loginUser.getUserId());
				meeting.setcCreatedeptid(loginUser.getDeptId());
				meeting.setCompanyId(loginUser.getCompanyId());
				meeting.setRegisterType("0");//录入类型批量导入
                //验证会议是否已经存在
                if(excelMapper.checkMeetingNameExist(meeting.getMeetingName(),loginUser.getCompanyId()) > 0){
                    errMsg = errMsg + row + ",";
                    row++;
                    continue;
                }
				meetingMapper.saveMeeting(meeting);
				//保存会议参会人员信息
				List<Attendee> attendees = meeting.getAttendees();
				if(ListUtil.isNotBlank(attendees)){
					for (Attendee attendee : attendees) {
						attendee.setMeetingId(uuid);
						attendee.setAttendeeId(CommonUtil.getUUID());
						excelMapper.saveAttendess(attendee);
					}
				}
				//保存会议议题信息
				List<Subject> subjectList = meeting.getSubjects();
				if(ListUtil.isNotBlank(subjectList)){
					for (Subject subject : subjectList) {
						//会议主键ID
						String subjectId = CommonUtil.getUUID();
						subject.setSubjectId(subjectId);
						subject.setMeetingId(uuid);
						//查询任务来源
						if(StringUtils.isNotBlank(subject.getSourceName())){
							subject.setSourceId(excelMapper.querySourceId(subject.getSourceName()));
						}
						//查询专项任务
						if(StringUtils.isNotBlank(subject.getSpecialName())){
							subject.setSpecialId(excelMapper.querySpecialId(subject.getSpecialName()));
						}
						//匹配通过状态
						if(StringUtils.isNotBlank(subject.getPassFlagName())){
							List<Map<String, String>> passFlag = excelMapper.queryPassFlagValue();
							for (Map<String, String> data : passFlag) {
								String[] value = data.get("CONFIG_VALUE").split(",");
								for (int i = 0; i < value.length; i++) {
									if(subject.getPassFlagName().equals(value[i])){
										if(("cfg_veto_name").equals(data.get("CONFIG_KEY"))){
											subject.setPassFlag("0");
										}else if(("cfg_defer_name").equals(data.get("CONFIG_KEY"))){
											subject.setPassFlag("2");
										}else if(("cfg_pass_name").equals(data.get("CONFIG_KEY"))){
											subject.setPassFlag("1");
										}
										break;
									}
								}
								if(StringUtils.isNotBlank(subject.getPassFlag())){
									break;
								}
							}
						}
						//保存议题
						subjectMapper.saveSubject(subject);
						//保存议题事项编码ID
						if(ListUtil.isNotBlank(subject.getSubjectItems())){
							List<SubjectItem> subjectItems = subject.getSubjectItems();
							for (SubjectItem subjectItem : subjectItems) {
								subjectItem.setSubjectId(subjectId);
								subjectItem.setRelevanceId(CommonUtil.getUUID());
								subjectItem.setItemId(excelMapper.queryItemId(subjectItem.getItemCode()));
								excelMapper.saveSubjectItem(subjectItem);
							}
						}
						//保存议题关联会议ID
						if(ListUtil.isNotBlank(subject.getSubjectRelevances())){
							List<SubjectRelevance> subjectRelevances = subject.getSubjectRelevances();
							for (SubjectRelevance subjectRelevance : subjectRelevances) {
								subjectRelevance.setSubjectId(subjectId);
								subjectRelevance.setRelevanceId(CommonUtil.getUUID());
								subjectRelevance.setRelMeetingId(excelMapper.queryMeetingId(subjectRelevance.getRelMeetingName(), loginUser.getCompanyId()));
								if(StringUtils.isNotBlank(subjectRelevance.getRelMeetingId())) {
									subjectRelevance.setRelSubjectId(excelMapper.querySubjectId(subjectRelevance.getRelSubjectName(),subjectRelevance.getRelMeetingId()));
									excelMapper.saveSubjectRelevance(subjectRelevance);
								}
							}
						}
						//保存列席人员
						if(ListUtil.isNotBlank(subject.getAttendances())){
							List<Attendance> attendances = subject.getAttendances();
							for (Attendance attendance : attendances) {
								attendance.setSubjectId(subjectId);
								attendance.setMeetingId(uuid);
								attendance.setAttendanceId(CommonUtil.getUUID());
								if(StringUtils.isNotBlank(attendance.getPosition())){
									if(attendance.getPosition().indexOf("法律顾问") != -1){
										attendance.setCounselType("法律顾问");
									}
								}
								excelMapper.saveAttendance(attendance);
							}
						}
						//保存审议结果
						if(ListUtil.isNotBlank(subject.getDeliberations())){
							List<Deliberation> deliberations = subject.getDeliberations();
							for (Deliberation deliberation : deliberations) {
								deliberation.setSubjectId(subjectId);
								deliberation.setMeetingId(uuid);
								deliberation.setDeliberationId(CommonUtil.getUUID());
								excelMapper.saveDeliberation(deliberation);
							}
						}
					}
				}
                row++;
			}
		}
		return errMsg;
	}
	
	/*
	 * 解析Excel
	 */
	public List<Meeting> readExcel(Workbook wb, int sheetIndex, int startReadLine, int tailLine) {
		//读取表单
		Sheet sheet = wb.getSheetAt(sheetIndex);
		Row row = null;
		//初始对象
		List<Meeting> meetingList = new ArrayList<Meeting>();
		Meeting meeting = null;
		List<Subject> subjectList = null;
		Subject subject = null;
		List<SubjectRelevance> subjectRelevances = null;
		List<SubjectItem> subjectItems = null;
		//循环数据
		for(int i=startReadLine; i<sheet.getLastRowNum()-tailLine+1; i++) {
			row = sheet.getRow(i);
		    if(null == row) {
			   continue;
		    }
		    Cell cell = row.getCell(0);
		    String cellStr = getCellValue(cell);
			if (cellStr.startsWith("填表说明：")) {
				return meetingList;
			}
		    CellRangeAddress rangeAddress= MergedRowInfo(sheet,i,1);//以名称为主
			if (null != rangeAddress) {
				// System.out.println("行合并数据: "+i);
				if (i == rangeAddress.getFirstRow()) {
					meeting = new Meeting();
					subjectList = new ArrayList<Subject>();
					meeting = readMeetingFromRow(row, meeting);
				}
				if (i == rangeAddress.getLastRow()) {
					meeting.setSubjects(subjectList);
					meetingList.add(meeting);
				}
				// 判断议题是否有多条关联数据
				CellRangeAddress rangeSubject = MergedRowInfo(sheet,i,8);
				if(rangeSubject != null){
					if (i == rangeSubject.getFirstRow()) {
						subject = new Subject();
						subjectRelevances = new ArrayList<SubjectRelevance>();
						subjectItems = new ArrayList<SubjectItem>();
						subject = readSubjectFromRow(sheet,row);
					}
					if (i == rangeSubject.getLastRow()) {
						subject.setSubjectRelevances(subjectRelevances);
						subject.setSubjectItems(subjectItems);
						subjectList.add(subject);
					}
					SubjectItem subjectItem = readSubjectItemFromRow(sheet,row);
					SubjectRelevance subjectRelevance = readSubjectRelevanceFromRow(sheet,row);
					if(subjectRelevance != null){
						subjectRelevances.add(subjectRelevance);
					}
					subjectItems.add(subjectItem);
				}else{
					subject = readSubjectFromRow(sheet,row);
					subjectRelevances = new ArrayList<SubjectRelevance>();
					subjectItems = new ArrayList<SubjectItem>();
					SubjectItem subjectItem = readSubjectItemFromRow(sheet,row);
					SubjectRelevance subjectRelevance = readSubjectRelevanceFromRow(sheet,row);
					if(subjectRelevance != null){
						subjectRelevances.add(subjectRelevance);
					}
					subjectItems.add(subjectItem);
					subject.setSubjectRelevances(subjectRelevances);
					subject.setSubjectItems(subjectItems);
					subjectList.add(subject);
				}
			} else {
				// System.out.println("单行: "+i);
				String meetingName = getCellValue(row, 1);
				if (null == meetingName || "".equals(meetingName))
					continue;
				meeting = new Meeting();
				meeting = readMeetingFromRow(row, meeting);
				subjectList = new ArrayList<Subject>();
				subject = readSubjectFromRow(sheet,row);
				subjectRelevances = new ArrayList<SubjectRelevance>();
				subjectItems = new ArrayList<SubjectItem>();
				SubjectItem subjectItem = readSubjectItemFromRow(sheet,row);
				SubjectRelevance subjectRelevance = readSubjectRelevanceFromRow(sheet,row);
				if(subjectRelevance != null){
					subjectRelevances.add(subjectRelevance);
				}
				subjectItems.add(subjectItem);
				subject.setSubjectRelevances(subjectRelevances);
				subject.setSubjectItems(subjectItems);
				subjectList.add(subject);
				meeting.setSubjects(subjectList);
				meetingList.add(meeting);
			}
		}
		return meetingList;
	}

	/**
	   * @功能描述: TODO 查询Excel表名下标
	   * @参数: @param wb
	   * @参数: @param name
	   * @参数: @return
	   * @创建人 ：林鹏
	   * @创建时间：2018年11月12日 下午8:49:53
	 */
	public int getSheetIdx( Workbook wb,String name){
		int sheetIdx = -1; //初始为不存在
		int count = wb.getNumberOfSheets();
		Sheet sheet = null;
		for (int i = 0; i < count; i++) {
			sheet = wb.getSheetAt(i);
			String sheetName = sheet.getSheetName();
			if (sheetName.equals(name)) {
				return i;
			}
		}
		return sheetIdx;
    }
	
	
	/**
     * 读取会议表的数据
     * @param row
     * @param meeting
     * @return
     */
    public Meeting readMeetingFromRow(Row row,Meeting meeting){
    	int i=1;
    	String name=getCellValue(row,i++);
    	String type=getCellValue(row,i++);
    	Date time=getCellDateValue(row,i++);//获取日期日志
    	String moderator=getCellValue(row,i++);
    	String attendeeStr=getCellValue(row,i++);
    	int rowNum=row.getRowNum();
    	meeting.setMeetingName(name);
    	meeting.setAlias(type);
    	meeting.setMeetingTime(DateUtil.dateFormat(time, "yyyy-MM-dd"));
    	meeting.setModerator(moderator);
    	checkEmpty(rowNum,new int[]{1,2,4,5},new String[]{name,type,moderator,attendeeStr});//判断某行某列是否为空
    	List<Attendee> attendeeList= formatAttendee(attendeeStr,rowNum,5);//模板第五列的数据
    	meeting.setAttendees(attendeeList);
    	return meeting;
    }
    
    /**
     * 生成参会人员数组
     * @param src
     * @return
     */
    public List<Attendee> formatAttendee(String src,int rowNum,int col){
		List<Attendee> list = new ArrayList<Attendee>();
		String dest = ExcelStringUtil.formatStr(src);// 统一格式化间隔
		String error = ExcelStringUtil.checkStr(dest);// 检测左右括号是否有闭合
		if (!"".equals(error)) {// 如果有异常信息
			addError(rowNum, col, error);
			return null;
		}
		if (!ExcelStringUtil.checkChsOrEh(dest)) {// 判断是否包含特殊字符
			addError(rowNum, col, "不参包含中含或英文以外的字符");
			return null;
		}

		String attStrs[] = dest.split(",");
		for (int i = 0; i < attStrs.length; i++) {
			String str = attStrs[i];
			if (null == str || "".equals(str))
				continue;// 如果为空,则跳过
			String temp[] = str.split("\\(");
			Attendee attendee = new Attendee();
			if (temp.length == 1) {
				attendee.setAttendeeName(str);
				attendee.setAttendFlag("1");
			} else if (temp.length == 2) {
				String reason = temp[1].replaceAll("\\)", "").trim();
				String name = temp[0].trim();
				attendee.setAttendeeName(name);
				if (!"".equals(reason)) {
					attendee.setReason(reason);
					attendee.setAttendFlag("0");
				} else {
					addError(rowNum, col, "填写的原因是空白");
				}
			} else {
				addError(rowNum, col, "有多余的括号," + str);
			}
			if (null != attendee.getAttendeeName() && !"".equals(attendee.getAttendeeName()))
				list.add(attendee);
		}
		return list;
    }
    
    /**
     * 读取议题
     * @param sheet 
     * @param row
     * @return
     */
    public Subject readSubjectFromRow(Sheet sheet, Row row){
		Subject subject = new Subject();
		int start = 8;
		String name = getCellValue(row, 8);
		String sourceName = getCellValue(row, 9);
		String specialName = getCellValue(row, 10);
		String attendanceStr = getCellValue(row, 14);
		String deliberationStr = getCellValue(row, 15);
		String passFlag = getCellValue(row, 16);
		String approvalFlag = getCellValue(row, 17);
		String adoptFlag = getCellValue(row, 18);
		String subjectResult = getCellValue(row, 21);
		String remark = getCellValue(row, 22);
		int rowNum = row.getRowNum();
		// 判断某行某列是否为空
		checkEmpty(rowNum, start, new int[] { 0, 7, 8, 9, 10, 11 },
				new String[] { name, deliberationStr, passFlag, approvalFlag, adoptFlag, adoptFlag });
		
		subject.setSubjectName(name);
		subject.setSourceName(sourceName);
		subject.setSpecialName(specialName);
		subject.setPassFlagName(passFlag);
		if(adoptFlag.equals("是")){
			subject.setAdoptFlag("1");
		}else if(adoptFlag.equals("否")){
			subject.setAdoptFlag("0");
		}
		subject.setApprovalFlagName(approvalFlag);
		subject.setSubjectResult(subjectResult);
		subject.setRemark(remark);

		List<Attendance> attendanceList = formatAttendance(attendanceStr, rowNum, 14);
		subject.setAttendances(attendanceList);
		List<Deliberation> deliberationList = formatDeliberation(deliberationStr, rowNum, 15);
		subject.setDeliberations(deliberationList);
		return subject;
    }
    
    
    /**
     * 获取列席人员
     * @param src
     * @param rowNum
     * @param col
     * @return
     */
    public List<Attendance> formatAttendance(String src,int rowNum,int col){
    	List<Attendance> attendanceList=new ArrayList<Attendance>();
    	String dest = ExcelStringUtil.formatStr(src);//统一格式化间隔
        if("".equals(dest)) {
        	return attendanceList;
        }
    	String error=ExcelStringUtil.checkStr(dest);//检测左右括号是否有闭合
    	if(!"".equals(error)){//如果有异常信息
    		addError(rowNum,col,error);
    		return null;
    	}
    	if(!ExcelStringUtil.checkChsOrEh(dest)){//判断是否包含特殊字符
			addError(rowNum,col,"不能包含中含或英文以外的字符");
    		return null;
    	}
    	
    	String attStrs[]=dest.split(",");
    	for(int i=0;i<attStrs.length;i++){
    		String str=attStrs[i];
    		if(null==str || "".equals(str)) continue;
    		String temp[]=str.split("\\(");
    		if(temp.length==1) {
    			Attendance attendance=new Attendance();
    			attendance.setAttendanceName(temp[0]);
    			attendanceList.add(attendance);
    		}else	 if(temp.length==2){
    			Attendance attendance=new Attendance();
    			attendance.setAttendanceName(temp[0]);
    			attendance.setPosition(temp[1].replaceAll("\\)", ""));
    			attendanceList.add(attendance);
    		}else{
    			addError(rowNum,col,"缺失左括号，或左括号有多余,或数据异常, "+str);
    		}
    	}
    	return attendanceList;
    }
    
    /**
       * @功能描述: TODO 审议结果
       * @参数: @param src
       * @参数: @param rowNum
       * @参数: @param col
       * @参数: @return
       * @创建人 ：林鹏
       * @创建时间：2018年11月13日 上午9:46:51
     */
    public List<Deliberation> formatDeliberation(String src,int rowNum,int col){
    	List<Deliberation> attendanceList=new ArrayList<Deliberation>();
    	String dest = ExcelStringUtil.formatStr(src);//统一格式化间隔
    	String error=ExcelStringUtil.checkStr(dest);//检测左右括号是否有闭合
    	
    	if("".equals(dest.trim())){
    		addError(rowNum,col,"审议结果为空");
    		return null;
    	}
    	if(!"".equals(error)){//如果有异常信息
    		addError(rowNum,col,error);
    		return null;
    	}
    	if(!ExcelStringUtil.checkChsOrEh(dest)){//判断是否包含特殊字符
			addError(rowNum,col,"不参包含中含或英文以外的字符");
    		return null;
    	}
    	
    	String attStrs[]=dest.split(",");
    	for(int i=0;i<attStrs.length;i++){
    		String str=attStrs[i];
    		if(null==str|| "".equals(str)) continue;//如果为空则跳过
    		String temp[]=str.split("\\(");
    		String person=temp[0].trim();
    		if(temp.length==2){
    			Deliberation deliberation=new Deliberation();
    			String result=temp[1].replaceAll("\\)", "").trim();
    			deliberation.setDeliberationPersonnel(person);
    			deliberation.setDeliberationResult(result);
    			attendanceList.add(deliberation);
    		}else{
    			addError(rowNum,col,"缺失左括号，或左括号有多余,"+str);
    			
    		}
    	}
    	return attendanceList;
    }
    
    /**
       * @功能描述: TODO 事项编码
       * @参数: @param sheet
       * @参数: @param row
       * @参数: @return
       * @创建人 ：林鹏
       * @创建时间：2018年11月13日 下午2:21:47
     */
    public SubjectItem readSubjectItemFromRow(Sheet sheet, Row row){
    	SubjectItem subjectItem = new SubjectItem();
		String itemCode = getCellValue(row, 11);
		subjectItem.setItemCode(itemCode);
		return subjectItem;
    }
    
    /**
       * @功能描述: TODO 关联会议议题
       * @参数: @param sheet
       * @参数: @param row
       * @参数: @return
       * @创建人 ：林鹏
       * @创建时间：2018年11月13日 下午2:23:45
     */
    public SubjectRelevance readSubjectRelevanceFromRow(Sheet sheet, Row row) {
    	SubjectRelevance relevance = new SubjectRelevance();
    	String relMeetingName = getCellValue(row, 12);
    	if(StringUtils.isBlank(relMeetingName)){
    		return null;
    	}
    	String relSubjectName = getCellValue(row, 13);
    	if(StringUtils.isBlank(relSubjectName)){
    		return null;
    	}
    	relevance.setRelMeetingName(relMeetingName);
    	relevance.setRelSubjectName(relSubjectName);
		return relevance;
	}

    /**
       * @功能描述:  生成采集数据异常信息Excel数据
       * @创建人 ：林鹏
       * @创建时间：2018/11/25 14:55
    */
    @Override
    public HSSFWorkbook generateExceptionaExcel() {
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        //生成事项清单异常sheet
        HSSFSheet itemSheet = wb.createSheet("事项清单异常");
        //在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow itemRow = itemSheet.createRow(0);
        //声明列对象
        HSSFCell itemCell = null;
        //excel标题
        String[] itemTitle = {"事项ID","事项名称","事项编码","企业名称","导入时间","错误信息"};
        //创建标题
        for(int i=0;i<itemTitle.length;i++){
            itemCell = itemRow.createCell(i);
            itemCell.setCellValue(itemTitle[i]);
            itemCell.setCellStyle(style);
        }
        //查询事项清单异常数据
        List<Map<String,Object>> preItem = excelMapper.queryPreItemList();
        //创建内容
        for(int i=0;i<preItem.size();i++){
            itemRow = itemSheet.createRow(i + 1);
            //将内容按顺序赋给对应的列对象
            itemRow.createCell(0).setCellValue(preItem.get(i).get("itemId").toString());
            itemRow.createCell(1).setCellValue(preItem.get(i).get("itemName").toString());
            itemRow.createCell(2).setCellValue(preItem.get(i).get("itemCode").toString());
            itemRow.createCell(3).setCellValue(preItem.get(i).get("companyName").toString());
            itemRow.createCell(4).setCellValue(preItem.get(i).get("createTime").toString());
            itemRow.createCell(5).setCellValue(preItem.get(i).get("errMsg").toString());
        }
        //生成会议异常sheet
        HSSFSheet meetingSheet = wb.createSheet("会议异常");
        //在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow meetingRow = meetingSheet.createRow(0);
        //声明列对象
        HSSFCell meetingCell = null;
        //excel标题
        String[] meetingTitle = {"会议ID","会议名称","会议类型","企业名称","导入时间","错误信息"};
        //创建标题
        for(int i=0;i<meetingTitle.length;i++){
            meetingCell = meetingRow.createCell(i);
            meetingCell.setCellValue(meetingTitle[i]);
            meetingCell.setCellStyle(style);
        }
        //查询事项清单异常数据
        List<Map<String,Object>> preMeeting = excelMapper.queryPreMeetingList();
        //创建内容
        for(int i=0;i<preMeeting.size();i++){
            meetingRow = meetingSheet.createRow(i + 1);
            //将内容按顺序赋给对应的列对象
            meetingRow.createCell(0).setCellValue(preMeeting.get(i).get("meetingId").toString());
            meetingRow.createCell(1).setCellValue(preMeeting.get(i).get("meetingName").toString());
            meetingRow.createCell(2).setCellValue(preMeeting.get(i).get("meetingType").toString());
            meetingRow.createCell(3).setCellValue(preMeeting.get(i).get("companyName").toString());
            meetingRow.createCell(4).setCellValue(preMeeting.get(i).get("createTime").toString());
            meetingRow.createCell(5).setCellValue(preMeeting.get(i).get("errMsg").toString());
        }
        //生成议题异常sheet
        HSSFSheet subjectSheet = wb.createSheet("议题异常");
        //在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow subjectRow = subjectSheet.createRow(0);
        //声明列对象
        HSSFCell subjectCell = null;
        //excel标题
        String[] subjectTitle = {"议题ID","会议名称","议题名称","任务来源","专项任务","事项编码","导入时间","错误信息"};
        //创建标题
        for(int i=0;i<subjectTitle.length;i++){
            subjectCell = subjectRow.createCell(i);
            subjectCell.setCellValue(subjectTitle[i]);
            subjectCell.setCellStyle(style);
        }
        //查询事项清单异常数据
        List<Map<String,Object>> preSubject = excelMapper.queryPreSubjectList();
        //创建内容
        for(int i=0;i<preSubject.size();i++){
            subjectRow = subjectSheet.createRow(i + 1);
            //将内容按顺序赋给对应的列对象
            subjectRow.createCell(0).setCellValue(preSubject.get(i).get("subjectId").toString());
            subjectRow.createCell(1).setCellValue(preSubject.get(i).get("meetingName").toString());
            subjectRow.createCell(2).setCellValue(preSubject.get(i).get("subjectName").toString());
            if(preSubject.get(i).get("sourceName") != null){
                subjectRow.createCell(3).setCellValue(preSubject.get(i).get("sourceName").toString());
            }
            if(preSubject.get(i).get("specialName") != null){
                subjectRow.createCell(4).setCellValue(preSubject.get(i).get("specialName").toString());
            }
            if(preSubject.get(i).get("itemCode") != null){
                subjectRow.createCell(5).setCellValue(preSubject.get(i).get("itemCode").toString());
            }
            subjectRow.createCell(6).setCellValue(preSubject.get(i).get("createTime").toString());
            subjectRow.createCell(7).setCellValue(preSubject.get(i).get("errMsg").toString());
        }
        //生成制度异常sheet
        HSSFSheet regulationSheet = wb.createSheet("制度异常");
        //在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        HSSFRow regulationtRow = regulationSheet.createRow(0);
        //声明列对象
        HSSFCell regulationCell = null;
        //excel标题
        String[] regulationTitle = {"制度ID","制度名称","制度类型","企业名称","导入时间","错误信息"};
        //创建标题
        for(int i=0;i<regulationTitle.length;i++){
            regulationCell = regulationtRow.createCell(i);
            regulationCell.setCellValue(regulationTitle[i]);
            regulationCell.setCellStyle(style);
        }
        //查询事项清单异常数据
        List<Map<String,Object>> preRegulationt = excelMapper.queryPreRegulationtList();
        //创建内容
        for(int i=0;i<preSubject.size();i++){
            regulationtRow = regulationSheet.createRow(i + 1);
            //将内容按顺序赋给对应的列对象
            regulationtRow.createCell(0).setCellValue(preRegulationt.get(i).get("regulationId").toString());
            regulationtRow.createCell(1).setCellValue(preRegulationt.get(i).get("regulationName").toString());
            if(preRegulationt.get(i).get("typeName") != null){
                regulationtRow.createCell(2).setCellValue(preRegulationt.get(i).get("typeName").toString());
            }
            regulationtRow.createCell(3).setCellValue(preRegulationt.get(i).get("companyName").toString());
            regulationtRow.createCell(4).setCellValue(preRegulationt.get(i).get("createTime").toString());
            regulationtRow.createCell(5).setCellValue(preRegulationt.get(i).get("errMsg").toString());
        }
        return wb;
    }
}
