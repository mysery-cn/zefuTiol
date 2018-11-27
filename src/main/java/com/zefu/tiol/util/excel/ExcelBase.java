package com.zefu.tiol.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelBase {
	public StringBuffer errorInfo=new StringBuffer();//记录错误信息
	
    public int getSheetIdx( Workbook wb,String name){
    	int sheetIdx = -1;//没有系统为-1，
    	if(null == wb) return -1;
        try {
            int count=wb.getNumberOfSheets();
            Sheet sheet =null;
            for(int i=0;i<count;i++){
            	sheet = wb.getSheetAt(i);
            	String sheetName=sheet.getSheetName();
            	if(sheetName.equals(name)){
            		return i;
            	}
            }
        }finally{
        }
    	
    	return sheetIdx;
    }
	
	
    public String getRowStr(Row row){
    	StringBuffer sb=new StringBuffer();
    	int i=0;
    	for(Cell c : row) {
		    String cellStr = "";
//		 	c.setCellType(CellType.STRING);
//		 	cellStr = c.getRichStringCellValue().getString();
		    cellStr = getCellValue(c);
		 	if(i!=0)  sb.append(",");
		 	sb.append(cellStr);
		 	i++;
		 }
    	return sb.toString();
    }
    
    
    /**
     * 获取合并单元格序号
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public int getMergeIdx(Sheet sheet ,int row , int column){
    	 int sheetMergeCount = sheet.getNumMergedRegions();
         int idx;
         for(int i = 0 ; i < sheetMergeCount ; i++){
             CellRangeAddress ca = sheet.getMergedRegion(i);
             int firstColumn = ca.getFirstColumn();
             int lastColumn = ca.getLastColumn();
             int firstRow = ca.getFirstRow();
             int lastRow = ca.getLastRow();
             
             if(row >= firstRow && row <= lastRow){
                 if(column >= firstColumn && column <= lastColumn){
                	 idx=i;
                     return idx;
                 }
             }
         }
         return -1;
    }
    
    
    
    /**
     * 获取合并单元格的值
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public String getMergedRegionValue(Sheet sheet ,int row , int column){
        int sheetMergeCount = sheet.getNumMergedRegions();
        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if(row >= firstRow && row <= lastRow){

                if(column >= firstColumn && column <= lastColumn){
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell) ;
                }
            }
        }

        return null ;
    }

    /**
     * 判断合并了行
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    private boolean isMergedRow(Sheet sheet,int row ,int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row == firstRow && row == lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }

    /*获取合并单元格和信息*/
    public CellRangeAddress MergedRowInfo(Sheet sheet,int row ,int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
//            System.out.println("row:    "+row + "    "+ firstColumn+"   "+ lastColumn +"     "+firstRow  +"    " +lastRow);
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
//                	System.out.println("range");
                    return range;
                }
            }
        }
        return null;
    }
    
    
    /**
     * 判断指定的单元格是否是合并单元格
     * @param sheet
     * @param row 行下标
     * @param column 列下标
     * @return
     */
    private boolean isMergedRegion(Sheet sheet,int row ,int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断sheet页中是否含有合并单元格
     * @param sheet
     * @return
     */
    private boolean hasMerged(Sheet sheet) {
        return sheet.getNumMergedRegions() > 0 ? true : false;
    }

    /**
     * 合并单元格
     * @param sheet
     * @param firstRow 开始行
     * @param lastRow 结束行
     * @param firstCol 开始列
     * @param lastCol 结束列
     */
    private void mergeRegion(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
        sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
    }

    public String getCellValue(Row row,int col){
    	if(row==null) return null;
    	Cell cell= row.getCell(col);
    	return getCellValue(cell);
    	
    }
    public String getCellValue(Cell cell) {
        if (cell == null)
            return "";
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return cell.getCellFormula();
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            short format = cell.getCellStyle().getDataFormat();
//            System.out.println("format:"+format+";;;;;value:"+cell.getNumericCellValue());
            SimpleDateFormat sdf = null;
            if (format == 14 || format == 31 || format == 57 || format == 58  
                    || (176<=format && format<=178) || (182<=format && format<=196) 
                    || (210<=format && format<=213) || (208==format ) ) { // 日期
                sdf = new SimpleDateFormat("yyyy/M/d");
            } else if (format == 20 || format == 32 || format==183 || (200<=format && format<=209) ) { // 时间
                sdf = new SimpleDateFormat("HH:mm");
            } else { // 不是日期格式
                return String.valueOf(cell.getNumericCellValue());
            }
            double value = cell.getNumericCellValue();
            Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
            if(date==null || "".equals(date)){
                return "";
            }
            String result="";
            try {
                result = sdf.format(date);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
            return result;
        }
        return "";
    }
    
    public Date getCellDateValue(Row row,int col){
    	Cell cell= row.getCell(col);
    	return getCellDateValue(cell);
    	
    }
    public Date getCellDateValue(Cell cell) {
    	 Date date =null;
        if (cell == null)
            return null;
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            short format = cell.getCellStyle().getDataFormat();
//            System.out.println("format:"+format+";;;;;value:"+cell.getNumericCellValue());
            SimpleDateFormat sdf = null;
            if (format == 14 || format == 31 || format == 57 || format == 58  
                    || (176<=format && format<=178) || (182<=format && format<=196) 
                    || (210<=format && format<=213) || (208==format ) ) { // 日期
                sdf = new SimpleDateFormat("yyyy/M/d");
            } else if (format == 20 || format == 32 || format==183 || (200<=format && format<=209) ) { // 时间
                sdf = new SimpleDateFormat("HH:mm");
            } else { // 不是日期格式
//            	System.out.println("不是日期格式");
            	 addError(cell.getRowIndex(),cell.getColumnIndex(),"不是日期格式");
                return null;
            }
            double value = cell.getNumericCellValue();
            date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
             
            return date;
        }
        return date;
    }
    
    
    /**
     * 从excel读取内容
     */
    public static void readContent(String fileName)  {
        boolean isE2007 = false;    //判断是否是excel2007格式
        if(fileName.endsWith("xlsx"))
            isE2007 = true;
        try {
            InputStream input = new FileInputStream(fileName);  //建立输入流
            Workbook wb  = null;
            //根据文件格式(2003或者2007)来初始化
            if(isE2007)
                wb = new XSSFWorkbook(input);
            else
                wb = new HSSFWorkbook(input);
            Sheet sheet = wb.getSheetAt(0);     //获得第一个表单
            Iterator<Row> rows = sheet.rowIterator(); //获得第一个表单的迭代器
            while (rows.hasNext()) {
                Row row = rows.next();  //获得行数据
                System.out.println("Row #" + row.getRowNum());  //获得行号从0开始
                Iterator<Cell> cells = row.cellIterator();    //获得第一行的迭代器
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    System.out.println("Cell #" + cell.getColumnIndex());
                    switch (cell.getCellType()) {   //根据cell中的类型来输出数据
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            System.out.println(cell.getNumericCellValue());
                            break;
                        case HSSFCell.CELL_TYPE_STRING:
                            System.out.println(cell.getStringCellValue());
                            break;
                        case HSSFCell.CELL_TYPE_BOOLEAN:
                            System.out.println(cell.getBooleanCellValue());
                            break;
                        case HSSFCell.CELL_TYPE_FORMULA:
                            System.out.println(cell.getCellFormula());
                            break;
                        default:
                            System.out.println("unsuported sell type======="+cell.getCellType());
                            break;
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Excel column index begin 1
     * @param colStr
     * @param length
     * @return
     */
    public static int excelColStrToNum(String colStr, int length) {
        int num = 0;
        int result = 0;
        for(int i = 0; i < length; i++) {
            char ch = colStr.charAt(length - i - 1);
            num = (int)(ch - 'A' + 1) ;
            num *= Math.pow(26, i);
            result += num;
        }
        return result;
    }

    /**
     * Excel column index begin 1
     * @param columnIndex
     * @return
     */
    public static String excelColIndexToStr(int columnIndex) {
        if (columnIndex <= 0) {
            return null;
        }
        String columnStr = "";
        columnIndex--;
        do {
            if (columnStr.length() > 0) {
                columnIndex--;
            }
            columnStr = ((char) (columnIndex % 26 + (int) 'A')) + columnStr;
            columnIndex = (int) ((columnIndex - columnIndex % 26) / 26);
        } while (columnIndex > 0);
        return columnStr;
    }
 
    
    public String getError(){
    	return errorInfo.toString();
    }
	
    public String toViewRow(int num){
    	return String.valueOf(num+1);
    }
    
    /**
     * 转成从1开始的顺序号
     * @param num
     * @return
     */
    public String toViewCol(int num){
	  return String.valueOf(num+1);
    }
    
    /**
     * 转成字母组合列
     * @param num
     * @return
     */
    public String toViewColStr(int num){
  	  return excelColIndexToStr(num+1);
     }
      
    
    
    public void addError(int rowNum,int col,String error){
    	errorInfo.append("第");
		errorInfo.append(toViewRow(rowNum));
		errorInfo.append("行");
		errorInfo.append(toViewColStr(col));
		errorInfo.append("列数据异常:");
		errorInfo.append(error);
		errorInfo.append("\n");
    }
    
    
    public void checkEmpty(int rowNum,int [] cols,String[] data){
    	checkEmpty(rowNum,0,cols,data);
    }
    public void checkEmpty(int rowNum,int colStart,int [] cols,String[] data){
    	if(null==cols || null== data) return;
    	if(data.length != cols.length ) {
    		System.out.println("数据与列数不一致");
    		return ;
    	}
//    	System.out.println(data.length +"   "+ cols.length );
    	for(int i=0;i<data.length;i++){
    		 int idx=cols[i];
    		 if(null == data[i] || "".equals(data[i])) {
    			 addError(rowNum,colStart+idx,"数据为空");
    		 }
    		 
    	}
    }
    
    
    //excel 插入行
 	//  Parameters:
 	//   startRow - the row to start shifting
 	//   endRow - the row to end shifting
 	//   n - the number of rows to shift
 	//   copyRowHeight - whether to copy the row height during the shift
 	//   resetOriginalRowHeight - whether to set the original row's height to the default

 	public static void insertRow(Workbook wb, Sheet sheet, int starRow,int rows) {
// 		System.out.println("start:"+(starRow + 1));  
// 		System.out.println("getLastRowNum: "+sheet.getLastRowNum());  
 		sheet.shiftRows(starRow + 1, sheet.getLastRowNum(), rows,true,false);
 	      
 		  starRow = starRow - 1;

 		  for (int i = 0; i < rows; i++) {

 		   Row sourceRow = null;
 		   Row targetRow = null;
 		   Cell sourceCell = null;
 		   Cell targetCell = null;
 		   short m;

 		   starRow = starRow + 1;
 		   sourceRow = sheet.getRow(starRow);
 		   targetRow = sheet.createRow(starRow + 1);
 		   targetRow.setHeight(sourceRow.getHeight());

 		   for (m = sourceRow.getFirstCellNum(); m < sourceRow.getLastCellNum(); m++) {

 		    sourceCell = sourceRow.getCell(m);
 		    targetCell = targetRow.createCell(m);

// 		    targetCell.setEncoding(sourceCell.);
 		    targetCell.setCellStyle(sourceCell.getCellStyle());
 		    targetCell.setCellType(sourceCell.getCellType());

 		   }
 		  }

 		 }
 		 
 	
 	
 	
 	
 	   /**
      * 设置某些列的值只能输入预制的数据,显示下拉框.
      * 
      * @param sheet
      *            要设置的sheet.
      * @param textlist
      *            下拉框显示的内容
      * @param firstRow
      *            开始行
      * @param endRow
      *            结束行
      * @param firstCol
      *            开始列
      * @param endCol
      *            结束列
      * @return 设置好的sheet.
      */
     public static Sheet setValidation(Sheet sheet,
             String[] textlist, int firstRow, int endRow, int firstCol,
             int endCol) {
         CellRangeAddressList addressList = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
//         String[]pos = {}; 
         DataValidationHelper helper = sheet.getDataValidationHelper();
         DataValidationConstraint constraint = helper.createExplicitListConstraint(textlist);  
         DataValidation dataValidation = helper.createValidation(constraint, addressList);
         //处理Excel兼容性问题  
         if(dataValidation instanceof XSSFDataValidation) {  
             dataValidation.setSuppressDropDownArrow(true);  
             dataValidation.setShowErrorBox(true);  
         }else {  
             dataValidation.setSuppressDropDownArrow(false);  
         }  
           
         sheet.addValidationData(dataValidation);  
         
         return sheet;
     }
  
     
     
     
 	/**
 	 * 把值写入设置日期格式
 	 * @param cell
 	 * @param value
 	 * @param pattern
 	 * @param style
 	 */
 	public static void setDateValue(Row row,int colIdx,Date value,String pattern,CellStyle style){
 		 Cell cell =row.createCell(colIdx);
 		 setDateValue(cell,value,pattern,style);
 	}
     
 	/**
 	 * 把值写入设置日期格式
 	 * @param cell
 	 * @param value
 	 * @param pattern
 	 * @param style
 	 */
 	public static void setDateValue(Cell cell,Date value,String pattern,CellStyle style){
 		 String textValue=null;
 		 if(null!=value){
 	         Date date = (Date) value;  
 	         SimpleDateFormat sdf = new SimpleDateFormat(pattern);  
 	         textValue = sdf.format(date);  
 		 }
          if(null!=style){
         	 cell.setCellStyle(style);
          }
          cell.setCellValue(textValue);
 	}
 	
 	/**
 	 * 
 	 * @param row
 	 * @param cellIdx
 	 * @param value
 	 * @param style
 	 */
 	public void setValue(Row row,int cellIdx,String value,CellStyle style){
 		Cell cell=row.createCell((short)cellIdx);
 		cell.setCellValue(value);
 		cell.setCellStyle(style);
 	}
  
}
