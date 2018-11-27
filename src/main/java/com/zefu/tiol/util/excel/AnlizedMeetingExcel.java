package com.zefu.tiol.util.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

import com.zefu.tiol.vo.TiolAttendanceVO;
import com.zefu.tiol.vo.TiolAttendeeVO;
import com.zefu.tiol.vo.TiolDeliberationVO;
import com.zefu.tiol.vo.TiolMeetingVO;
import com.zefu.tiol.vo.TiolSubjectVO;

/**
 * 会议解析
 * 
 * @author long
 *
 */
public class AnlizedMeetingExcel extends ExcelBase {
	public static final String SHEET_NAME = "有关决策会议";

	/**
	 * 读取excel数据
	 * 
	 * @param path
	 */
	public List<TiolMeetingVO> readExcelToObj(String path) throws Exception {
		Workbook wb = null;
		List<TiolMeetingVO> list = null;
		try {
			wb = WorkbookFactory.create(new File(path));
			int sheetIdx = getSheetIdx(wb, SHEET_NAME);// 获取序号
			if (sheetIdx == -1) {
				System.out.println("没有存在的sheet名称: " + SHEET_NAME + "+，使用第一个sheets");
				sheetIdx = 0;
			}
			String ret = readHead(wb, sheetIdx, 0, 2);

			if (!"".equals(ret)) {
				errorInfo.append(ret);
				return list;
			}

			list = readExcel(wb, sheetIdx, 3, 0);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
			errorInfo.append(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			errorInfo.append(e.getMessage());
		} catch (Exception e) {
			errorInfo.append(e.getMessage());
			e.printStackTrace();
		} finally {
			// if(null!=wb){
			// try {
			// wb.close();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			// }
		}
		// System.out.println("解析异常信息： "+errorInfo.toString());
		return list;
	}

	public String readHead(Workbook wb, int sheetIndex, int startReadLine, int count) {
		Sheet sheet = wb.getSheetAt(sheetIndex);
		Row row = null;
		// System.out.println("count:  "+count);
		row = sheet.getRow(startReadLine + 0);
		Cell titleCell = row.getCell(0);
		String title = getCellValue(titleCell);
		System.out.println(title);
		if (!"有关决策会议采集指标".equals(title)) {
			return "第一行表头已经被修改";
		}
		String headStrOne = "";
		String headStrTwo = "";

		row = sheet.getRow(startReadLine + 1);
		if (null == row)
			return "第" + (startReadLine + 1) + "行没有内容";
		headStrOne = getRowStr(row);
		row = sheet.getRow(startReadLine + 2);
		if (null == row)
			return "第" + (startReadLine + 2) + "行没有内容";
		headStrTwo = getRowStr(row);

		String stdHead = "序号,会议名称,会议类型,会议时间,主持人,参会人,会议通知,会议纪要,议题名称,任务来源,专项名称,事项编码,关联会议,关联议题,列席人员,审议结果,是否\n" + "通过,是否\n" + "报国资委,是否听取\n" + "意见,听取意见\n"
				+ "情况,议题\n" + "材料,议题决议,备注";
		stdHead = "序号,会议名称,会议类型,会议时间,主持人,参会人,会议通知,会议纪要,议题名称,任务来源,专项名称,事项编码,关联会议,关联议题,列席人员,审议结果,是否通过,是否报国资委,是否听取意见,听取意见情况,议题材料,议题决议,备注";
		headStrOne = headStrOne.replaceAll("\n", "");// 换行符不做判断
		// System.out.println(headStrOne);

		if (stdHead.equals(headStrOne) && ",,,,,,,,,,,,,,,,,,,,,,".equals(headStrTwo)) {
			return "";
		} else {
			return "第二行或第三行表头已经与模板不一致";
		}
	}

	/**
	 * 读取excel文件
	 * 
	 * @param wb
	 * @param sheetIndex
	 *            sheet页下标：从0开始
	 * @param startReadLine
	 *            开始读取的行:从0开始
	 * @param tailLine
	 *            去除最后读取的行
	 */
	public List<TiolMeetingVO> readExcel(Workbook wb, int sheetIndex, int startReadLine, int tailLine) {
		Sheet sheet = wb.getSheetAt(sheetIndex);
		Row row = null;
		List<TiolMeetingVO> list = new ArrayList<TiolMeetingVO>();
		TiolMeetingVO meeting = null;
		List<TiolSubjectVO> subjectList = null;
		for (int i = startReadLine; i < sheet.getLastRowNum() - tailLine + 1; i++) {
			row = sheet.getRow(i);
			if (null == row)
				continue;
			Cell cell = row.getCell(0);
			// String cellStr=cell.getRichStringCellValue().getString();
			// System.out.println(cellStr);
			String cellStr = getCellValue(cell);
			if (cellStr.startsWith("填表说明：")) {
				return list;
			}
			// CellRangeAddress rangeAddress= MergedRowInfo(sheet,i,0);
			CellRangeAddress rangeAddress = MergedRowInfo(sheet, i, 1);// 以名称为主

			if (null != rangeAddress) {
				// System.out.println("行合并数据: "+i);
				if (i == rangeAddress.getFirstRow()) {
					meeting = new TiolMeetingVO();
					meeting.setRow(row.getRowNum());
					subjectList = new ArrayList<TiolSubjectVO>();
					readMeetingFromRow(row, meeting);
				}
				if (i == rangeAddress.getLastRow()) {
					meeting.setSubjectList(subjectList);
					list.add(meeting);
				}
				TiolSubjectVO subject = readSubjectFromRow(row);
				subjectList.add(subject);
			} else {
				// System.out.println("单行: "+i);
				String meetingName = getCellValue(row, 1);
				if (null == meetingName || "".equals(meetingName))
					continue;
				meeting = new TiolMeetingVO();
				readMeetingFromRow(row, meeting);
				subjectList = new ArrayList<TiolSubjectVO>();
				TiolSubjectVO subject = readSubjectFromRow(row);
				subjectList.add(subject);
				meeting.setSubjectList(subjectList);
				list.add(meeting);
			}

		}

		return list;
	}

	/**
	 * 读取会议表的数据
	 * 
	 * @param row
	 * @param meeting
	 * @return
	 */
	public String readMeetingFromRow(Row row, TiolMeetingVO meeting) {
		int i = 0;
		String sno = getCellValue(row, i++);
		String name = getCellValue(row, i++);
		String type = getCellValue(row, i++);
		Date time = getCellDateValue(row, i++);// 获取日期日志
		String moderator = getCellValue(row, i++);
		String attendeeStr = getCellValue(row, i++);
		int rowNum = row.getRowNum();
		meeting.setRow(rowNum);
		meeting.setSno(sno);
		meeting.setName(name);
		meeting.setType(type);
		meeting.setTime(time);
		meeting.setModerator(moderator);
		// checkEmpty(rowNum,new int[]{0,1,2,4,5},new
		// String[]{sno,name,type,moderator,attendeeStr});//判断某行某列是否为空
		checkEmpty(rowNum, new int[] { 1, 2, 4, 5 }, new String[] { name, type, moderator, attendeeStr });// 判断某行某列是否为空

		List<TiolAttendeeVO> attendeeList = formatAttendee(attendeeStr, rowNum, 5);// 模板第五列的数据
		meeting.setAttendeeList(attendeeList);
		// System.out.println("attendeeStr: "+attendeeStr);
		// if(null!= attendeeList){
		// for(TiolAttendeeVO attendee: attendeeList){
		// System.out.println("参会人员： "+attendee.getName()+ "     "+
		// attendee.getAttendFlag()+"   原因： "+ attendee.getReason());
		// }
		// }

		return "";
	}

	/**
	 * 读取议题
	 * 
	 * @param row
	 * @return
	 */
	public TiolSubjectVO readSubjectFromRow(Row row) {
		TiolSubjectVO subject = new TiolSubjectVO();
		int start = 8;
		int i = start;
		subject.setRow(row.getRowNum());
		String name = getCellValue(row, i++);
		String sourceName = getCellValue(row, i++);
		String specialName = getCellValue(row, i++);
		String itemCode = getCellValue(row, i++);
		String relMeetingName = getCellValue(row, i++);
		String relSubjectName = getCellValue(row, i++);
		String attendanceStr = getCellValue(row, i++);
		String deliberationStr = getCellValue(row, i++);
		String passFlag = getCellValue(row, i++);
		String approvalFlag = getCellValue(row, i++);
		String adoptFlag = getCellValue(row, i++);
		i = i + 2;// 跳过听取意见情况和议题材料两列
		String subjectResult = getCellValue(row, i++);
		String remark = getCellValue(row, i++);
		int rowNum = row.getRowNum();
		// relMeetingName,relSubjectName
		checkEmpty(rowNum, start, new int[] { 0, 3, 7, 8, 9, 10, 11 }, new String[] { name, itemCode, deliberationStr, passFlag, approvalFlag,
				adoptFlag, adoptFlag });// 判断某行某列是否为空

		// sourceName= sourceName.replaceAll(" ", "").replaceAll("-", "");
		// specialName= specialName.replaceAll(" ", "").replaceAll("-", "");

		subject.setName(name);
		subject.setSourceName(sourceName);
		subject.setSpecialName(specialName);
		subject.setItemCode(itemCode);
		subject.setRelMeetingName(relMeetingName);
		subject.setRelSubjectName(relSubjectName);
		subject.setPassFlag(passFlag);
		subject.setApprovalFlag(approvalFlag);
		subject.setAdoptFlag(adoptFlag);
		subject.setSubjectResult(subjectResult);
		subject.setRemark(remark);

		List<TiolAttendanceVO> attendanceList = formatAttendance(attendanceStr, rowNum, 14);
		subject.setAttendanceList(attendanceList);
		List<TiolDeliberationVO> deliberationList = formatDeliberation(deliberationStr, rowNum, 15);
		subject.setDeliberationList(deliberationList);

		// for(TiolAttendanceVO att: attendanceList){
		// //
		// System.out.println("列序人员："+row.getRowNum()+"行:   "+att.getName()+"  "+att.getPosition());
		// }
		//
		// for(TiolDeliberationVO deli: deliberationList){
		// System.out.println("审议人员："+deli.getPersonnel()+"  "+deli.getResult());
		// }

		return subject;
	}

	/**
	 * 生成参会人员数组
	 * 
	 * @param src
	 * @return
	 */
	public List<TiolAttendeeVO> formatAttendee(String src, int rowNum, int col) {
		List<TiolAttendeeVO> list = new ArrayList<TiolAttendeeVO>();
		String dest = formatStr(src);// 统一格式化间隔
		String error = StringUtil.checkStr(dest);// 检测左右括号是否有闭合
		if (!"".equals(error)) {// 如果有异常信息
			addError(rowNum, col, error);
			return null;
		}
		if (!StringUtil.checkChsOrEh(dest)) {// 判断是否包含特殊字符
			addError(rowNum, col, "不参包含中含或英文以外的字符");
			return null;
		}

		String attStrs[] = dest.split(",");
		for (int i = 0; i < attStrs.length; i++) {
			String str = attStrs[i];
			if (null == str || "".equals(str))
				continue;// 如果为空,则跳过
			String temp[] = str.split("\\(");
			TiolAttendeeVO attendee = new TiolAttendeeVO();
			if (temp.length == 1) {
				attendee.setName(str);
				attendee.setAttendFlag("是");
			} else if (temp.length == 2) {
				String reason = temp[1].replaceAll("\\)", "").trim();
				String name = temp[0].trim();
				attendee.setName(name);
				if (!"".equals(reason)) {
					attendee.setReason(reason);
					attendee.setAttendFlag("否");
				} else {
					addError(rowNum, col, "填写的原因是空白");

				}
			} else {
				addError(rowNum, col, "有多余的括号," + str);
			}
			if (null != attendee.getName() && !"".equals(attendee.getName()))
				list.add(attendee);
		}

		return list;
	}

	/**
	 * 获取列席人员
	 * 
	 * @param src
	 * @param rowNum
	 * @param col
	 * @return
	 */
	public List<TiolAttendanceVO> formatAttendance(String src, int rowNum, int col) {
		List<TiolAttendanceVO> attendanceList = new ArrayList<TiolAttendanceVO>();
		String dest = formatStr(src);// 统一格式化间隔
		if ("".equals(dest)) {
			return attendanceList;
		}
		String error = StringUtil.checkStr(dest);// 检测左右括号是否有闭合
		if (!"".equals(error)) {// 如果有异常信息
			addError(rowNum, col, error);
			return null;
		}
		if (!StringUtil.checkChsOrEh(dest)) {// 判断是否包含特殊字符
			addError(rowNum, col, "不能包含中含或英文以外的字符");
			return null;
		}

		String attStrs[] = dest.split(",");
		for (int i = 0; i < attStrs.length; i++) {
			String str = attStrs[i];
			if (null == str || "".equals(str))
				continue;
			String temp[] = str.split("\\(");
			if (temp.length == 1) {
				TiolAttendanceVO attendance = new TiolAttendanceVO();
				attendance.setName(temp[0]);
				attendanceList.add(attendance);
			} else if (temp.length == 2) {
				TiolAttendanceVO attendance = new TiolAttendanceVO();
				attendance.setName(temp[0]);
				attendance.setPosition(temp[1].replaceAll("\\)", ""));
				attendanceList.add(attendance);
			} else {
				// System.out.println("有异常");
				addError(rowNum, col, "缺失左括号，或左括号有多余,或数据异常, " + str);
				// System.out.println("异常数据:"+str);
			}
		}

		return attendanceList;
	}

	public List<TiolDeliberationVO> formatDeliberation(String src, int rowNum, int col) {
		List<TiolDeliberationVO> attendanceList = new ArrayList<TiolDeliberationVO>();
		String dest = formatStr(src);// 统一格式化间隔
		String error = StringUtil.checkStr(dest);// 检测左右括号是否有闭合

		if ("".equals(dest.trim())) {
			addError(rowNum, col, "审议结果为空");
			return null;
		}
		if (!"".equals(error)) {// 如果有异常信息
			addError(rowNum, col, error);
			return null;
		}
		if (!StringUtil.checkChsOrEh(dest)) {// 判断是否包含特殊字符
			addError(rowNum, col, "不参包含中含或英文以外的字符");
			return null;
		}

		String attStrs[] = dest.split(",");
		for (int i = 0; i < attStrs.length; i++) {
			String str = attStrs[i];
			if (null == str || "".equals(str))
				continue;// 如果为空则跳过
			String temp[] = str.split("\\(");
			String person = temp[0].trim();
			if (temp.length == 2) {
				TiolDeliberationVO deliberation = new TiolDeliberationVO();
				String result = temp[1].replaceAll("\\)", "").trim();
				deliberation.setPersonnel(person);
				deliberation.setResult(result);
				attendanceList.add(deliberation);
			} else {
				// System.out.println("有异常");
				addError(rowNum, col, "缺失左括号，或左括号有多余," + str);

			}
		}

		return attendanceList;
	}

	// 字符串标准化
	public String formatStr(String src) {
		String dest = src;
		dest = dest.replaceAll("\n", ",").replaceAll("、", ",").replaceAll("（", "(").replaceAll("）", ")").replaceAll("，", ",");
		dest = dest.replaceAll(" ", "");// 去除空格
		return dest;
	}

}
