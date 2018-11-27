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
import org.apache.poi.ss.util.CellRangeAddress;

import com.zefu.tiol.vo.TiolRegulationVO;
import com.zefu.tiol.vo.TiolVoteModeVO;

/**
 * 解析制度excel
 * 
 * @author long
 *
 */
public class AnlizedRegulationExcel extends ExcelBase {
	public static final String SHEET_NAME = "有关制度";

	/**
	 * 读取excel数据
	 * 
	 * @param path
	 */
	public List<TiolRegulationVO> readExcelToObj(String path) {
		Workbook wb = null;
		List<TiolRegulationVO> list = null;
		try {
			wb = WorkbookFactory.create(new File(path));
			int sheetIdx = getSheetIdx(wb, SHEET_NAME);// 获取序号
			if (sheetIdx == -1) {
				// System.out.println("没有存在的sheet名称: "+SHEET_NAME+"+，使用第一个sheets");
				sheetIdx = 0;
			}

			String ret = readHead(wb, sheetIdx, 0, 2);
			if (!"".equals(ret)) {
				// System.out.println(ret);
				return list;
			}

			list = readExcel(wb, sheetIdx, 3, 0);
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
		System.out.println(title);
		if (!"有关制度采集指标".equals(title)) {
			addError(0, 0, "第一行表头已经被修改");
			return "第一行表头已经被修改";
		}

		List<String> colList = new ArrayList<String>();
		String headStrOne = "";
		String headStrTwo = "";

		row = sheet.getRow(startReadLine + 1);
		if (null == row) {
			addError(startReadLine + 1, 0, "数据为空");
			return "表头已经被修改";
		}
		headStrOne = getRowStr(row);
		row = sheet.getRow(startReadLine + 2);
		if (null == row) {
			addError(startReadLine + 2, 0, "数据为空");
			return "表头已经被修改";
		}
		headStrTwo = getRowStr(row);
		// System.out.println(headStrOne);
		// System.out.println(headStrTwo);

		if ("序号,制度名称,制度类型,审批时间,生效时间,合法合规性审查,,人数占比,事项编码,表决方式,正式文件,备注".equals(headStrOne) && ",,,,,是否经过审查,审查佐证材料,,,,,".equals(headStrTwo)) {
			return "";
		} else {
			addError(startReadLine + 2, 0, "第二行或第三行表头已经被修改");
			return "第二行或第三行表头已经被修改";
		}
	}

	public String getRowStr(Row row) {
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (Cell c : row) {
			String cellStr = "";
			// c.setCellType(CellType.STRING);
			// cellStr = c.getRichStringCellValue().getString();
			cellStr = getCellValue(c);
			if (i != 0)
				sb.append(",");
			sb.append(cellStr);
			i++;
		}
		return sb.toString();
	}

	/**
	 * 获取合并单元格序号
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	public int getMergeIdx(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		int idx;
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();

			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					idx = i;
					return idx;
				}
			}
		}
		return -1;
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
	public List<TiolRegulationVO> readExcel(Workbook wb, int sheetIndex, int startReadLine, int tailLine) {
		Sheet sheet = wb.getSheetAt(sheetIndex);
		Row row = null;
		List<TiolRegulationVO> list = new ArrayList<TiolRegulationVO>();
		TiolRegulationVO regulation = null;
		List<TiolVoteModeVO> voteModeList = null;
		for (int i = startReadLine; i < sheet.getLastRowNum() - tailLine + 1; i++) {
			row = sheet.getRow(i);
			if (null == row)
				continue;
			Cell cell = row.getCell(0);
			String cellStr = getCellValue(cell);
			// System.out.println(cellStr);
			if (cellStr.startsWith("填表说明：")) {
				return list;
			}
			// boolean isMerged=isMergedRow(sheet, i, 0);
			// CellRangeAddress rangeAddress= MergedRowInfo(sheet,i,0);
			CellRangeAddress rangeAddress = MergedRowInfo(sheet, i, 1);// 以名称合并行为主

			if (null != rangeAddress) {
				// System.out.println("行合并数据: "+i);
				if (i == rangeAddress.getFirstRow()) {
					regulation = new TiolRegulationVO();
					// regulation.setRow(row.getRowNum());
					voteModeList = new ArrayList<TiolVoteModeVO>();
					readRegulationFromRow(row, regulation);
				}
				if (i == rangeAddress.getLastRow()) {
					regulation.setVoteModeList(voteModeList);
					list.add(regulation);
				}
				TiolVoteModeVO voteMode = readVodeModeFromRow(row);
				voteModeList.add(voteMode);
			} else {
				// System.out.println("单行: "+i);
				Cell regNamecell = row.getCell(1);
				String regName = getCellValue(regNamecell);
				if (null == regName || "".equals(regName))
					continue;
				regulation = new TiolRegulationVO();
				readRegulationFromRow(row, regulation);
				voteModeList = new ArrayList<TiolVoteModeVO>();
				TiolVoteModeVO voteMode = readVodeModeFromRow(row);
				voteModeList.add(voteMode);
				regulation.setVoteModeList(voteModeList);
				list.add(regulation);
			}

		}

		return list;

	}

	// 读取制度表的数据
	public String readRegulationFromRow(Row row, TiolRegulationVO regulation) {
		regulation.setRow(row.getRowNum());

		String sno = getCellValue(row, 0);
		String name = getCellValue(row, 1);
		String type = getCellValue(row, 2);
		String approvalDate = getCellValue(row, 3);
		String effectiveDate = getCellValue(row, 4);
		String auditFlag = getCellValue(row, 5);
		String rate = getCellValue(row, 7);
		String remark = getCellValue(row, 11);
		regulation.setSno(sno);
		regulation.setName(name);
		regulation.setType(type);
		if (null != approvalDate)
			approvalDate = approvalDate.replaceAll(" ", "");
		if (null == approvalDate || "".equals(approvalDate)) {
			approvalDate = "-";
		}
		regulation.setApprovalDate(approvalDate);
		if (null != effectiveDate)
			effectiveDate = effectiveDate.replaceAll(" ", "");
		if (null == effectiveDate || "".equals(effectiveDate)) {
			effectiveDate = "-";
		}

		regulation.setEffectiveDate(effectiveDate);
		regulation.setAuditFlag(auditFlag);
		regulation.setRate(rate);
		regulation.setRemark(remark);
		checkEmpty(row.getRowNum(), new int[] { 1, 2, 5 }, new String[] { name, type, auditFlag });// 校验是否为空

		// System.out.println("sno:  "+regulation.getSno());
		return "";
	}

	// //读取制度表的数据
	// public String readRegulationFromRow(Row row,TiolRegulationVO regulation){
	// regulation.setRow(row.getRowNum());
	// for(Cell c : row) {
	// int curCol=c.getColumnIndex();
	// // c.setCellType(CellType.STRING);
	//
	// String cellStr = getCellValue(c);
	// if(curCol==0){
	// regulation.setSno(cellStr);
	// }else if(curCol == 1){
	// regulation.setName(cellStr);
	// }else if(curCol == 2){
	// // System.out.println("制度类型： "+cellStr);
	// regulation.setType(cellStr);
	// }else if(curCol == 3){
	// // cellStr=getCellValue(c);
	// if(null!=cellStr) cellStr=cellStr.replaceAll(" ", "");
	// if(null== cellStr || "".equals(cellStr) ) {
	// cellStr="-";
	// }
	// regulation.setApprovalDate(cellStr);
	// }else if(curCol == 4){
	// if(null!=cellStr) cellStr=cellStr.replaceAll(" ", "");
	// if(null== cellStr || "".equals(cellStr) ) {
	// cellStr="-";
	// }
	// // String dateStr=cellStr;
	// // if(!"-".equals(cellStr) && !"".equals(cellStr)) {
	// // SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
	// // SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy/M/d");
	// // try {
	// // Date date=sdf.parse(cellStr);
	// // dateStr=sdfDate.format(date);
	// //
	// // } catch (ParseException e) {
	// // addError(row.getRowNum(),c.getColumnIndex(),"日期格式错误："+dateStr);
	// // e.printStackTrace();
	// // }
	// // }
	// regulation.setEffectiveDate(cellStr);
	// }else if(curCol == 5){
	// regulation.setAuditFlag(cellStr);
	// }else if(curCol == 6 ){
	// // regulation.setRate(cellStr);//审查佐证材料
	// }else if(curCol == 7){
	// // cellStr= cellStr.replaceAll(" ", "").replaceAll("-", "");
	// regulation.setRate(cellStr);
	// }else if(curCol ==11){
	// regulation.setRemark(cellStr);
	// }
	// }
	// // System.out.println("sno:  "+regulation.getSno());
	// return "";
	// }

	// 读取表决方式
	public TiolVoteModeVO readVodeModeFromRow(Row row) {
		TiolVoteModeVO vm = new TiolVoteModeVO();
		vm.setRow(row.getRowNum());
		Cell itemCodeCell = row.getCell(8);
		Cell modeCell = row.getCell(9);
		// itemCodeCell.setCellType(CellType.STRING);
		if (null == modeCell || null == itemCodeCell)
			return null;
		String itemCode = getCellValue(itemCodeCell);
		itemCode = itemCode.replaceAll(" ", "");
		String mode = getCellValue(modeCell);
		vm.setItemCode(itemCode);
		vm.setMode(mode);
		return vm;
	}

}
