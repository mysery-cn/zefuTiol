package com.zefu.tiol.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.zefu.tiol.vo.TiolItemMeetingVO;
import com.zefu.tiol.vo.TiolItemVO;

/**
 * 导出事项清单
 * 
 * @author long
 *
 */
public class ExportTiolItem extends ExcelBase {
	public static final String SHEET_NAME = "“三重一大”事项清单";

	public static void main(String[] args) {
		String path = "e:\\develop_temp\\excel_template\\szyd-item-dest.xlsx";
		String templatePath = "e:\\develop_temp\\excel_template\\szyd-item.xlsx";

		List<TiolItemVO> list = new ArrayList<TiolItemVO>();
		TiolItemVO vo = new TiolItemVO();
		vo.setItemCode("abcdd");
		vo.setItemName("test");
		vo.setLegalFlag("是");
		vo.setRemark("备注");
		list.add(vo);
		String[] meetNameDict = { "党委会", "董事会" };
		new ExportTiolItem().exportExcel2007(templatePath, path, list, meetNameDict);
	}

	public void exportExcel2007(String templatePath, String destPath, List<TiolItemVO> list, String[] meetNameDict) {

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
			insertRow(wb, sheet, 3, 1);
			CellStyle style = initCellStyle(wb);

			int startRow = 2;
			Row row = null;
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow((int) startRow + i);
				TiolItemVO vo = list.get(i);
				setValue(row, 0, vo.getSno(), style);
				setValue(row, 1, vo.getItemName(), style);
				setValue(row, 2, vo.getItemCode(), style);
				List<TiolItemMeetingVO> meetingList = vo.getMeetingList();
				int skipCol = 3;
				if (null != meetingList) {
					for (int j = 0; j < meetingList.size(); j++) {
						TiolItemMeetingVO meetingVO = meetingList.get(j);
						setValue(row, 3 + j, meetingVO.getName(), style);
					}
					if (meetingList.size() == 4)
						skipCol = 4;
				}
				// 设置边框样式
				for (int j = 0; j < skipCol; j++) {
					int cellIdx = 3 + j;
					Cell cell = row.getCell((short) cellIdx);
					if (null == cell) {// 如果不存在，则添加一个
						cell = row.createCell(cellIdx);
					}
					cell.setCellStyle(style);
				}

				setValue(row, 3 + skipCol, vo.getLegalFlag(), style);
				setValue(row, 3 + skipCol + 1, vo.getRemark(), style);

			}

			// String text[]={"abc","111","222","333"};
			// setValidation(sheet,meetNameDict,2,10,3,5);
			// 增加单元格可选值
			String LegalFlag[] = { "是", "否" };
			setValidation(sheet, meetNameDict, 2, 10, 3, 5);
			setValidation(sheet, LegalFlag, 2, 10, 6, 6);
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
