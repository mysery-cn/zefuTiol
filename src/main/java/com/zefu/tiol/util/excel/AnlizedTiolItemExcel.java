package com.zefu.tiol.util.excel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.zefu.tiol.vo.TiolItemMeetingVO;
import com.zefu.tiol.vo.TiolItemVO;

/**
 * 解析事项清单excel
 * 
 * @author long
 *
 */
public class AnlizedTiolItemExcel extends ExcelBase {
	public static final String SHEET_NAME = "“三重一大”事项清单";
	private int tiolMeetingCount = 0;

	/**
	 * 读取excel数据
	 * 
	 * @param path
	 */
	public List<TiolItemVO> readExcelToObj(String path) {
		Workbook wb = null;
		List<TiolItemVO> list = null;
		try {
			wb = WorkbookFactory.create(new File(path));
			int sheetIdx = getSheetIdx(wb, SHEET_NAME);// 获取序号
			if (sheetIdx == -1) {
				// System.out.println("没有存在的sheet名称: "+SHEET_NAME+"+，使用第一个sheets");
				sheetIdx = 0;
			}
			String ret = readHead(wb, sheetIdx, 0, 2);
			if (!"".equals(ret)) {
				System.out.println(ret);
				return list;
			}
			System.out.println("决策会议顺序：  " + tiolMeetingCount);
			int startCol = 2;// 合并开始列
			list = readExcel(wb, sheetIdx, 2, 0, startCol, startCol + tiolMeetingCount + 1);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public String readHead(Workbook wb, int sheetIndex, int startReadLine, int count) {
		Sheet sheet = wb.getSheetAt(sheetIndex);
		Row row = null;
		ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();

		// System.out.println("count:  "+count);
		row = sheet.getRow(startReadLine + 0);
		Cell titleCell = row.getCell(0);
		// String title= titleCell.getRichStringCellValue().getString();
		String title = getCellValue(titleCell);
		if (!"企业“三重一大”事项清单采集指标".equals(title)) {
			addError(0, 0, "表头已经被修改");
			return "表头已经被修改";
		}

		List<String> colList = new ArrayList<String>();

		row = sheet.getRow(startReadLine + 1);
		if (null == row) {
			addError(startReadLine + 1, 0, "表头已经被修改");
			return "表头已经被修改";
		}
		StringBuffer sb = new StringBuffer();
		for (Cell c : row) {
			String cellStr = "";
			// cellStr = c.getRichStringCellValue().getString();
			cellStr = getCellValue(c);
			colList.add(cellStr);
			if (sb.length() != 0)
				sb.append(",");
			sb.append(cellStr);
		}
		// System.out.println(sb.toString());
		String headStr = sb.toString();
		if ("序号,事项名称,事项编码,决策会议及顺序,,,是否需经法律审核,备注".equals(headStr)) {
			tiolMeetingCount = 3;
			return "";
		} else if ("序号,事项名称,事项编码,决策会议及顺序,,,,是否需经法律审核,备注".equals(headStr)) {
			tiolMeetingCount = 4;
			return "";
		} else {
			addError(startReadLine + 1, 0, "表头已经被修改");
			return "表头已经被修改";
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
	public List<TiolItemVO> readExcel(Workbook wb, int sheetIndex, int startReadLine, int tailLine, int startCol, int endCol) {
		Sheet sheet = wb.getSheetAt(sheetIndex);
		Row row = null;
		List<TiolItemVO> list = new ArrayList<TiolItemVO>();
		for (int i = startReadLine; i < sheet.getLastRowNum() - tailLine + 1; i++) {
			row = sheet.getRow(i);
			if (null == row)
				continue;
			TiolItemVO tiolitem = null;
			List<TiolItemMeetingVO> meetingList = new ArrayList<TiolItemMeetingVO>();
			int order = 0;

			String sno = getCellValue(row, 0);
			if (sno.startsWith("填表说明：")) {
				return list;
			}

			String name = getCellValue(row, 1);
			if (null == name || "".equals(name)) {// 没有名称的直接跳过
			// addError(row.getRowNum(),1,"数据为空");
				continue;
			}
			tiolitem = new TiolItemVO();
			tiolitem.setItemName(name);
			tiolitem.setRow(row.getRowNum());

			String itemCode = getCellValue(row, 2);

			if (null == itemCode || "".equals(itemCode)) {
				addError(row.getRowNum(), 2, "事项编码不能为空");
			}
			tiolitem.setItemCode(itemCode);

			for (int j = 0; j < tiolMeetingCount; j++) {
				String meetingName = getCellValue(row, 3 + j);
				if (null == meetingName || "".equals(meetingName)) {
					continue;// 决策会议顺序是空的，跳过
				}
				TiolItemMeetingVO meeting = new TiolItemMeetingVO();
				meeting.setName(meetingName);
				meeting.setOrder(order);
				meeting.setRow(row.getRowNum());
				order++;
				meetingList.add(meeting);
			}
			int LegalFlagCol = 3 + tiolMeetingCount;
			String LegalFlag = getCellValue(row, LegalFlagCol);
			if (null == LegalFlag || "".equals(LegalFlag)) {
				addError(row.getRowNum(), 3 + tiolMeetingCount, "是否需经法律审核不能为空");
			}
			String remark = getCellValue(row, LegalFlagCol + 1);
			tiolitem.setLegalFlag(LegalFlag);
			tiolitem.setRemark(remark);

			if (null != tiolitem) {
				if (meetingList.size() == 0) {
					addError(row.getRowNum(), 3, "至少要有一个决策会议");
				}
				tiolitem.setMeetingList(meetingList);
				list.add(tiolitem);
			}
		}
		return list;

	}

	// /**
	// * 读取excel文件
	// * @param wb
	// * @param sheetIndex sheet页下标：从0开始
	// * @param startReadLine 开始读取的行:从0开始
	// * @param tailLine 去除最后读取的行
	// */
	// public List<TiolItemVO> readExcel(Workbook wb,int sheetIndex, int
	// startReadLine, int tailLine,int startCol,int endCol) {
	// Sheet sheet = wb.getSheetAt(sheetIndex);
	// Row row = null;
	// List<TiolItemVO> list = new ArrayList<TiolItemVO>();
	// for(int i=startReadLine; i<sheet.getLastRowNum()-tailLine+1; i++) {
	// row = sheet.getRow(i);
	// if(null == row) continue;
	// TiolItemVO tiolitem=null;
	// List<TiolItemMeetingVO> meetingList=new ArrayList<TiolItemMeetingVO>();
	// int order=0;
	// for(Cell c : row) {
	// String cellStr = "";
	// int curCol=c.getColumnIndex();
	// // boolean isMerge = isMergedRegion(sheet, i,curCol);
	// // c.setCellType(CellType.STRING);
	// // cellStr = c.getRichStringCellValue().getString();
	// cellStr =getCellValue(c);
	//
	// if( curCol == 0){
	// if("".equals(cellStr.trim())){
	//
	// }else if(cellStr.startsWith("填表说明：")){
	// return list;
	// }
	// tiolitem=new TiolItemVO();
	// tiolitem.setSno(cellStr);
	// tiolitem.setRow(row.getRowNum());
	// }else if(curCol == 1){
	// if(null ==cellStr || "".equals(cellStr)) {
	// addError(row.getRowNum(),c.getColumnIndex(),"数据为空");
	// continue;
	// }
	// tiolitem.setItemName(cellStr);
	//
	// }else if(curCol == 2){
	// if(null ==cellStr || "".equals(cellStr)) {
	// addError(row.getRowNum(),c.getColumnIndex(),"事项编码不能为空");
	// }
	// tiolitem.setItemCode(cellStr);
	// }else if(curCol >startCol && curCol <endCol){
	// if(null ==cellStr ||"".equals(cellStr)){
	// continue;//决策会议顺序是空的，跳过
	// }
	// TiolItemMeetingVO meeting=new TiolItemMeetingVO();
	// meeting.setName(cellStr);
	// meeting.setOrder(order);
	// meeting.setRow(row.getRowNum());
	// order++;
	// meetingList.add(meeting);
	// }else if(curCol == endCol){
	// if(null ==cellStr ||"".equals(cellStr)){
	// addError(row.getRowNum(),c.getColumnIndex(),"是否需经法律审核不能为空");
	// }
	// tiolitem.setLegalFlag(cellStr);
	// }else if(curCol == endCol+1){
	// tiolitem.setRemark(cellStr);
	// }
	//
	// }
	// if(null != tiolitem){
	// if(meetingList.size()==0) {
	// addError(row.getRowNum(),3,"至少要有一个决策会议");
	// }
	// tiolitem.setMeetingList(meetingList);
	// list.add(tiolitem);
	// }
	// }
	// return list;
	//
	// }

}
