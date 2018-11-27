package com.zefu.tiol.util.excel;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.zefu.tiol.vo.AnlizedResult;
import com.zefu.tiol.vo.TemplateType;
import com.zefu.tiol.vo.TiolItemVO;
import com.zefu.tiol.vo.TiolMeetingVO;
import com.zefu.tiol.vo.TiolRegulationVO;

public class ExcelUtil {

	public static List anlized(String path) {

		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(new File(path));
			int count = wb.getNumberOfSheets();
			Sheet sheet = null;
			for (int i = 0; i < count; i++) {
				sheet = wb.getSheetAt(i);
				String name = sheet.getSheetName();
				System.out.println("sheetName: " + name);
			}
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}

		return null;
	}

	public static AnlizedResult anlized(String path, TemplateType type) throws Exception {
		AnlizedResult result = new AnlizedResult();
		if (type == TemplateType.toilItem) {
			AnlizedTiolItemExcel excel = new AnlizedTiolItemExcel();
			List<TiolItemVO> list = excel.readExcelToObj(path);
			result.setErrorInfo(excel.getError());
			if (null == excel.getError() || "".equals(excel.getError()))
				result.setSuccess(true);
			result.setData(list);

		} else if (type == TemplateType.regulation) {
			AnlizedRegulationExcel excel = new AnlizedRegulationExcel();
			List<TiolRegulationVO> list = excel.readExcelToObj(path);
			result.setErrorInfo(excel.getError());
			if (null == excel.getError() || "".equals(excel.getError()))
				result.setSuccess(true);
			result.setData(list);

		} else if (type == TemplateType.meeting) {
			// 读取excel数据
			AnlizedMeetingExcel excel = new AnlizedMeetingExcel();
			List<TiolMeetingVO> list = excel.readExcelToObj(path);

			result.setErrorInfo(excel.getError());
			if (null == excel.getError() || "".equals(excel.getError()))
				result.setSuccess(true);
			result.setData(list);
		}
		return result;

	}

	public static String export(String templatePath, String destPath, List list, String[] dict, TemplateType type) throws Exception {
		if (type == TemplateType.toilItem) {
			ExportTiolItem export = new ExportTiolItem();
			export.exportExcel2007(templatePath, destPath, list, dict);
		} else if (type == TemplateType.regulation) {
			ExportTiolRegulation export = new ExportTiolRegulation();
			export.exportExcel2007(templatePath, destPath, list, dict);
		} else if (type == TemplateType.meeting) {
			ExportTiolMeeting export = new ExportTiolMeeting();
			export.exportExcel2007(templatePath, destPath, list, dict);
		}

		return null;
	}
}
