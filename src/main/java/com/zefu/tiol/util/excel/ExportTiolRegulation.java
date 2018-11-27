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
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import com.zefu.tiol.vo.TiolRegulationVO;
import com.zefu.tiol.vo.TiolVoteModeVO;

/**
 * 导出有关制度
 * 
 * @author long
 *
 */
public class ExportTiolRegulation extends ExcelBase {

	public static final String SHEET_NAME = "有关制度";

	public static void main(String[] args) {
		String path = "e:\\develop_temp\\excel_template\\szyd-regulation-dest.xlsx";
		String templatePath = "e:\\develop_temp\\excel_template\\szyd-regulation.xlsx";
		templatePath = "e:\\develop_temp\\excel_template\\test.xls";

		List<TiolRegulationVO> list = new ArrayList<TiolRegulationVO>();
		TiolRegulationVO vo = new TiolRegulationVO();
		vo.setSno("abcc");
		vo.setName("法律");
		vo.setRate("1/2");
		vo.setApprovalDate("2010/1/1");
		vo.setEffectiveDate("2010/1/1");

		List<TiolVoteModeVO> voteModeList = new ArrayList<TiolVoteModeVO>();
		TiolVoteModeVO voteModeVO = new TiolVoteModeVO();
		voteModeVO.setItemCode("测试");
		voteModeVO.setMode("测试投票模式");
		voteModeList.add(voteModeVO);
		// vo.setVoteModeList(voteModeList);
		// list.add(vo);
		TiolVoteModeVO voteModeVO2 = new TiolVoteModeVO();
		voteModeVO2.setItemCode("测试");
		voteModeVO2.setMode("测试投票模式");
		voteModeList.add(voteModeVO2);
		vo.setVoteModeList(voteModeList);
		list.add(vo);
		String[] voteModeDict = { "全票通过", "赞成票超过应到会成员人数的4/5", "赞成票超过应到会成员人数的3/4", "赞成票超过应到会成员人数的2/3", "赞成票超过应到会成员人数的1/2", "赞成票超过到会成员人数的4/5",
				"赞成票超过到会成员人数的3/4", "赞成票超过到会成员人数的2/3", "赞成票超过到会成员人数的1/2", "其他" };
		new ExportTiolRegulation().exportExcel2007(templatePath, path, list, voteModeDict);
	}

	public void exportExcel2007(String templatePath, String destPath, List<TiolRegulationVO> list, String[] voteModeDict) {

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
				TiolRegulationVO vo = list.get(i);
				List<TiolVoteModeVO> voteModeList = vo.getVoteModeList();
				if (null != voteModeList && voteModeList.size() > 1) {
					insertRowCount += voteModeList.size() - 1;
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

			int startCol = 8;
			Row row = null;
			int rowIdx = 0;// 当前行数
			for (int i = 0; i < list.size(); i++) {
				rowIdx = startRow + i;
				int startMergedRow = rowIdx;// 开始合并的行,如果没有合并的行，此参数无效

				row = sheet.createRow((int) rowIdx);
				TiolRegulationVO vo = list.get(i);
				setValue(row, 0, vo.getSno(), style);
				setValue(row, 1, vo.getName(), style);
				setValue(row, 2, vo.getType(), style);
				setValue(row, 3, vo.getApprovalDate(), dateStyle);// 审批时间
				setValue(row, 4, vo.getEffectiveDate(), dateStyle);// 生效时间
				setValue(row, 5, vo.getAuditFlag(), style);
				setValue(row, 6, "", style);// 佐证材料
				setValue(row, 7, vo.getRate(), style);
				setValue(row, 10, "", style);
				setValue(row, 11, vo.getRemark(), style);
				List<TiolVoteModeVO> voteModeList = vo.getVoteModeList();

				if (null != voteModeList) {
					for (int j = 0; j < voteModeList.size(); j++) {
						rowIdx = rowIdx + j;
						if (j > 0) {
							row = sheet.createRow((int) rowIdx);
						}
						TiolVoteModeVO voteModeVO = voteModeList.get(j);
						setValue(row, 8, voteModeVO.getItemCode(), style);
						setValue(row, 9, voteModeVO.getMode(), style);
					}

				}
				if (null == voteModeList || voteModeList.size() == 0) {
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
					int endMergedRow = startMergedRow + voteModeList.size() - 1;// 结束行
					int cols[] = { 0, 1, 2, 3, 4, 5, 6, 7, 10, 11 };// 需要合并的列
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
					setValidation(sheet, voteModeDict, startMergedRow, endMergedRow, 9, 9);
				}
				// setValue(row,3+skipCol,vo.getLegalFlag(),style);
				// setValue(row,3+skipCol+1,vo.getRemark(),style);

			}

			// String text[]={"abc","111","222","333"};
			// setValidation(sheet,meetNameDict,2,10,3,5);
			// 增加单元格可选值
			String auditFlag[] = { "是", "否" };
			// setValidation(sheet,meetNameDict,startRow,10,3,5);
			setValidation(sheet, auditFlag, startRow, startRow + insertRowCount + 2, 5, 5);
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
