package com.zefu.tiol.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import com.zefu.tiol.vo.TiolAttendanceVO;
import com.zefu.tiol.vo.TiolAttendeeVO;
import com.zefu.tiol.vo.TiolMeetingVO;
import com.zefu.tiol.vo.TiolSubjectVO;

/**
 * 导出事项清单
 * 
 * @author long
 *
 */
public class ExportTiolMeeting extends ExcelBase {

	public static final String SHEET_NAME = "有关决策会议";

	public static void main(String[] args) {
		String path = "e:\\develop_temp\\excel_template\\szyd-meeting-dest.xlsx";
		String templatePath = "e:\\develop_temp\\excel_template\\szyd-meeting.xlsx";
		// templatePath="e:\\develop_temp\\excel_template\\test.xls";

		List<TiolMeetingVO> list = new ArrayList<TiolMeetingVO>();
		TiolMeetingVO vo = new TiolMeetingVO();
		vo.setSno("abcc");
		vo.setName("法律");
		vo.setType("测试类型");
		vo.setTime(new Date());

		// 参会人员
		List<TiolAttendeeVO> attendeeList = new ArrayList<TiolAttendeeVO>();
		TiolAttendeeVO attendee = new TiolAttendeeVO();
		attendee.setName("王XX");
		TiolAttendeeVO attendee2 = new TiolAttendeeVO();
		attendee.setName("李xx");
		attendeeList.add(attendee);
		attendeeList.add(attendee2);
		vo.setAttendeeList(attendeeList);

		// 议题
		List<TiolSubjectVO> subjectList = new ArrayList<TiolSubjectVO>();
		TiolSubjectVO subject = new TiolSubjectVO();
		subject.setAdoptFlag("是");
		subject.setItemCode("d01-11");
		subject.setSourceName("专项任务");
		List<TiolAttendanceVO> attendanceList = new ArrayList<TiolAttendanceVO>();
		TiolAttendanceVO attendance = new TiolAttendanceVO();
		attendance.setName("郑xx");
		attendanceList.add(attendance);
		subject.setAttendanceList(attendanceList);
		subjectList.add(subject);

		TiolSubjectVO subjectO2 = new TiolSubjectVO();
		subjectO2.setAdoptFlag("是");
		subjectO2.setItemCode("d01-11");
		subjectO2.setSourceName("专项任务");
		subjectList.add(subjectO2);

		list.add(vo);

		vo.setSubjectList(subjectList);

		String[] meetingTypeDict = { "会议类型一", "会议类型二"

		};
		new ExportTiolMeeting().exportExcel2007(templatePath, path, list, meetingTypeDict);
	}

	public void exportExcel2007(String templatePath, String destPath, List<TiolMeetingVO> list, String[] meetingTypeDict) {

		// String
		// exportPath="e:\\develop_temp\\excel_template\\szyd-item-export.xlsx";
		File destFile = new File(destPath);
		File templateFile = new File(templatePath);
		String templateDir = destFile.getParentFile().getAbsolutePath();
		String tempPath = templateDir + File.separator + "temp_" + destFile.getName();
		System.out.println("临时文件目录： " + tempPath);
		File tempFile = new File(tempPath);// 临时文件
		InputStream is = null;
		Workbook wb = null;
		try {
			FileUtils.copyFile(templateFile, tempFile);
			is = new FileInputStream(tempFile);
			wb = WorkbookFactory.create(is);
			// wb=WorkbookFactory.create(tempFile);
			int sheetIdx = getSheetIdx(wb, SHEET_NAME);// 获取序号
			if (-1 == sheetIdx)
				sheetIdx = 0;
			Sheet sheet = wb.getSheetAt(sheetIdx);
			// 合并两列
			// sheet.addMergedRegion(new CellRangeAddress(0,0,0,1));
			int insertRowCount = 0;// 需要插入的行数
			// 先计算可能需要的行数
			for (int i = 0; i < list.size(); i++) {
				TiolMeetingVO vo = list.get(i);
				List<TiolSubjectVO> subjectList = vo.getSubjectList();
				if (null != subjectList && subjectList.size() > 1) {
					insertRowCount += subjectList.size() - 1;
				}
				insertRowCount++;
			}

			int startRow = 3;
			CellStyle style = initCellStyle(wb);
			// 定义Cell格式
			CellStyle dateStyle = wb.createCellStyle();// 设置日期格式
			CreationHelper creationHelper = wb.getCreationHelper();
			dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy/M/d"));

			insertRow(wb, sheet, startRow, insertRowCount);
			// insertRow(wb,sheet,startRow,10);

			int startCol = 8;
			Row row = null;
			int rowIdx = 0;// 当前行数
			for (int i = 0; i < list.size(); i++) {
				rowIdx = startRow + i;
				int startMergedRow = rowIdx;// 开始合并的行,如果没有合并的行，此参数无效

				row = sheet.createRow((int) rowIdx);
				TiolMeetingVO vo = list.get(i);
				setValue(row, 0, vo.getSno(), style);
				setValue(row, 1, vo.getName(), style);
				setValue(row, 2, vo.getType(), style);

				// setDateValue(row,3,vo.getTime(),"yyyy/M/d",dateStyle);
				setValue(row, 4, vo.getModerator(), style);
				setValue(row, 5, vo.getAttendeeStr(), style);
				setValue(row, 6, "", style);// 会议通知
				setValue(row, 7, "", style);// 会议纪要
				List<TiolSubjectVO> subjectList = vo.getSubjectList();

				if (null != subjectList) {
					for (int j = 0; j < subjectList.size(); j++) {
						rowIdx = rowIdx + j;
						if (j > 0) {
							row = sheet.createRow((int) rowIdx);
						}
						TiolSubjectVO subject = subjectList.get(j);
						setValue(row, 8, subject.getName(), style);
						setValue(row, 9, subject.getSourceName(), style);
						setValue(row, 10, subject.getSpecialName(), style);
						setValue(row, 11, subject.getItemCode(), style);
						setValue(row, 12, subject.getRelMeetingName(), style);
						setValue(row, 13, subject.getRelSubjectName(), style);
						setValue(row, 14, subject.getAttendanceStr(), style);// 获取列席人员
						setValue(row, 15, subject.getDeliberationStr(), style);// 获取审议结果
						setValue(row, 16, subject.getPassFlag(), style);// 获取审议结果
						setValue(row, 17, subject.getApprovalFlag(), style);// 获取审议结果
						setValue(row, 18, subject.getAdoptFlag(), style);// 获取审议结果

						setValue(row, 21, subject.getSubjectResult(), style);// 获取审议结果
						setValue(row, 22, subject.getRemark(), style);// 获取审议结果

					}

				}
				if (null == subjectList || subjectList.size() == 0) {
					// 设置边框样式
					for (int j = 0; j < 2; j++) {
						int cellIdx = startCol + j;
						Cell cell = row.getCell((short) cellIdx);
						if (null == cell) {// 如果不存在，则添加一个
							cell = row.createCell(cellIdx);
						}
						cell.setCellStyle(style);
					}
				} else {// 如果存多行的对行进行合并
					int endMergedRow = startMergedRow + subjectList.size() - 1;// 结束行
					int cols[] = { 0, 1, 2, 3, 4, 5, 6, 7 };// 需要合并的列
					for (int j = 0; j < cols.length; j++) {
						int startMergedCol = cols[j];
						int endMergedCol = cols[j];
						;
						CellRangeAddress cra = new CellRangeAddress(startMergedRow, endMergedRow, startMergedCol, endMergedCol);
						sheet.addMergedRegion(cra);
						// 使用RegionUtil类为合并后的单元格添加边框
						RegionUtil.setBorderBottom(1, cra, sheet, wb); // 下边框
						RegionUtil.setBorderLeft(1, cra, sheet, wb); // 左边框
						RegionUtil.setBorderRight(1, cra, sheet, wb); // 有边框
						RegionUtil.setBorderTop(1, cra, sheet, wb); // 上边框

					}
					// setValidation(sheet,voteModeDict,startMergedRow,endMergedRow,9,9);
				}
				// setValue(row,3+skipCol,vo.getLegalFlag(),style);
				// setValue(row,3+skipCol+1,vo.getRemark(),style);

			}

			// String text[]={"abc","111","222","333"};
			// setValidation(sheet,meetNameDict,2,10,3,5);
			// 增加单元格可选值
			String passFlag[] = { "是", "否" };// 是否审核
			String approvalFlag[] = { "否", "审批", "备案" };// 是否需报国资委审批
			String adoptFlag[] = { "是", "否" };// 是听取意见
			setValidation(sheet, meetingTypeDict, startRow, startRow + insertRowCount + 2, 2, 2);// 会议类型
			setValidation(sheet, passFlag, startRow, startRow + insertRowCount + 2, 16, 16);
			setValidation(sheet, approvalFlag, startRow, startRow + insertRowCount + 2, 17, 17);
			setValidation(sheet, adoptFlag, startRow, startRow + insertRowCount + 2, 18, 18);//
			// 合并第一列，第二三两行

			OutputStream os = new FileOutputStream(destFile);
			wb.write(os);
			os.flush();
			os.close();

		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != tempFile) {
				tempFile.delete();
			}
		}
	}

	public CellStyle initCellStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return style;
	}

}
