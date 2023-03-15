package com.netcracker.shaw.at_shaw_sd.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.util.NumberToTextConverter;

import com.netcracker.shaw.report.TestListener;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

public class ExcelOperation {
	
	public String filename;
	public  String path;
	public  String sheetName;
	static Logger log = Logger.getLogger(TestListener.class);
	
	public  FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null;
	private HSSFRow row   =null;
	private HSSFCell cell = null;
	private FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
	private DataFormatter objDefaultFormat = new DataFormatter();
	double value = 0.0;
	java.text.DecimalFormat formatter = null;
	java.text.FieldPosition fPosition = null;
	String formattingString = null;
	String resultString = null;
	StringBuffer buffer = null;
	
	public ExcelOperation(){
	}
	
	public ExcelOperation(String path) {
		this.path=path;
		try {
			fis = new FileInputStream(path);
			workbook = new HSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void setSheet(String sheet) {
		sheetName = sheet;
	}
	
	public int getRowCount(String sheetName){
		int index = workbook.getSheetIndex(sheetName);
		if(index==-1)
			return 0;
		else{
		sheet = workbook.getSheetAt(index);
		int number=sheet.getLastRowNum()+1;
		return number;
		}
	}
	
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";
			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(col_Num);
			if (cell == null)
				return "";
			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				String cellText = new BigDecimal(cell.getNumericCellValue()).toPlainString();
				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());

		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}

	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null)
				return "";
			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				String cellText = String.valueOf(cell.getNumericCellValue());
				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}
	}

	public String getCellValue(String sheetName, int colNum, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1)
				return "";
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			String cellValueStr = objDefaultFormat.formatCellValue(cell);
			return cellValueStr;
		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}
	}
	
	public String formatData(Cell cell) {
		value = cell.getNumericCellValue();
		formatter.format(value, buffer, fPosition);
		resultString = buffer.toString();
		return resultString;
	}
	
	public static String getCellValueAsString(Cell cell) {
		String strCellValue = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				strCellValue = cell.toString();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				strCellValue = NumberToTextConverter.toText(cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				strCellValue = new String(new Boolean(cell.getBooleanCellValue()).toString());
				break;
			case Cell.CELL_TYPE_BLANK:
				strCellValue = "";
				break;
			}
		}
		return strCellValue;
	}
	
	public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		try {
			fis = new FileInputStream(path);
			workbook = new HSSFWorkbook(fis);
			if (rowNum <= 0)
				return false;
			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1)
				return false;
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum = i;
			}
			if (colNum == -1)
				return false;

			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);
			cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);
			CellStyle cs = workbook.createCellStyle();
			cs.setWrapText(true);
			cell.setCellStyle(cs);
			cell.setCellValue(data);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean addSheet(String sheetname) {
		FileOutputStream fileOut;
		try {
			workbook.createSheet(sheetname);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean addColumn(String sheetName, String colName) {
		try {
			fis = new FileInputStream(path);
			workbook = new HSSFWorkbook(fis);
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1)
				return false;
			HSSFCellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			if (row == null)
				row = sheet.createRow(0);
			if (row.getLastCellNum() == -1)
				cell = row.createCell(0);
			else
				cell = row.createCell(row.getLastCellNum());
			cell.setCellValue(colName);
			cell.setCellStyle(style);

			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean isSheetExist(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			index = workbook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1)
				return false;
			else
				return true;
		} else
			return true;
	}

	public int getColumnCount(String sheetName) {
		if (!isSheetExist(sheetName))
			return -1;
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);
		if (row == null)
			return -1;
		return row.getLastCellNum();
	}
	
	public int getCellRowNum(String sheetName,String colName,String cellValue){
		for(int i=2;i<=getRowCount(sheetName);i++){
	    	if(getCellData(sheetName,colName , i).equalsIgnoreCase(cellValue)){
	    		return i;
	    	}
	    }
		return -1;	
	}
	
	public String getCellDataFor(ExcelOperation xls, String rowName, String colName) {
		String colValue = null;
		int rows = xls.getRowCount(Constants.TESTCASES_SHEET);
		for (int rNum = 2; rNum <= rows; rNum++) {
			String tcid = xls.getCellData(Constants.TESTCASES_SHEET, "TCID", rNum);
			if (tcid.equals(rowName)) {
				colValue = xls.getCellData(Constants.TESTCASES_SHEET, colName, rNum);
			}
		}
		return colValue;
	}
	
	public String getDataForTest(ExcelOperation xls, String rowName, String colName) {
		int rows = xls.getRowCount(Constants.TEST_SHEET);
		String colVal = null;
		for (int rNum = 2; rNum <= rows; rNum++) {
			String tcid = xls.getCellData(Constants.TEST_SHEET, "TCID", rNum);
			if (tcid.equals(rowName)) {
				colVal = (xls.getCellData(sheetName, colName, rNum));
			}
		}
		return colVal;
	}
	
	public String getDataForTestName(ExcelOperation xls, String Sheet, String rowName, String colName) {
		int rows = xls.getRowCount(Sheet);
		String colVal = null;
		for (int rNum = 2; rNum <= rows; rNum++) {
			String tcName = xls.getCellData(Sheet, "TC_Name", rNum);
			if (tcName.equals(rowName)) {
				colVal = (xls.getCellData(Sheet, colName, rNum));			
				break;
			}
		}
		return colVal;
	}

	public String getValueForRowName(ExcelOperation xls, String rowName, String colName,String colName1) {
		int rows = xls.getRowCount(Constants.EXPECTED_SHEET);
		String colVal = null;
		for (int rNum = 2; rNum <= rows; rNum++) {
			String tcName = xls.getCellData(Constants.EXPECTED_SHEET, colName, rNum);
			if (tcName.equals(rowName)) {
				colVal = (xls.getCellData(Constants.EXPECTED_SHEET, colName1, rNum));
				break;
			}
		}
		return colVal;
	}
	
	public String getExpectedDataForColumn(ExcelOperation xls, String rowName, String colName) {
		int rows = xls.getRowCount(Constants.EXPECTED_SHEET);
		String colVal = null;
		for (int rNum = 2; rNum <= rows; rNum++) {
			String tcName = xls.getCellData(Constants.EXPECTED_SHEET, colName, rNum);

			if (tcName.equals(rowName)) {
				colVal = (xls.getCellData(Constants.EXPECTED_SHEET, colName, rNum));
				break;
			}
		}
		return colVal;
	}
	
	public String [] getDataForTheRow(ExcelOperation xls,String tcid){
		String sheetName=Constants.EXPECTED_SHEET;
		int testStartRowNum=1;
		while(!xls.getCellData(sheetName, 0, testStartRowNum).equals(tcid)){
			testStartRowNum++;
		}	
		int cols=0;
		while(!xls.getCellData(sheetName, cols, 1).equals("")){
			cols++;
		}
		ArrayList<String> colValueList=new ArrayList<>();
		int dataRow=0;
		
		for(int rNum=testStartRowNum;rNum<=testStartRowNum;rNum++){
			for(int cNum=6;cNum<cols;cNum++){
				String value= xls.getCellData(sheetName, cNum, testStartRowNum);
				colValueList.add(value);
			}
		}
		String[] colValues= colValueList.toArray(new String[colValueList.size()]);
		return colValues;
	}
	
	public String [] getDataForTheRowNum(ExcelOperation xls,int rowNum,String sheetName){
		// reads data for only testCaseName	
		int cols=0;
		while(!xls.getCellData(sheetName, cols, 1).equals("")){
			cols++;
		}
		ArrayList<String> colValueList=new ArrayList<>();		
		for (int cNum = 1; cNum < cols; cNum++) {
			String value = xls.getCellValue(sheetName, cNum, rowNum);
			if (!(value.equals("")))
				colValueList.add(value);
		}
		String[] colValues = colValueList.toArray(new String[colValueList.size()]);
		return colValues;
	}
	public String [] getDataForTheRowAndColNum(ExcelOperation xls,String rowName,int testStartRowNum,int colNum){
		String sheetName=Constants.EXPECTED_SHEET;
		while(!xls.getCellData(sheetName, colNum, testStartRowNum).equals(rowName)){
			testStartRowNum++;
		}
		System.out.println("Row start num is "+testStartRowNum);		
		int cols=1;
		while(!xls.getCellData(sheetName, cols, 1).equals("")){
			cols++;
		}
		System.out.println("Column num is "+cols);
		ArrayList<String> colValueList=new ArrayList<>();
		int dataRow=0;
		for(int rNum=testStartRowNum;rNum<=testStartRowNum;rNum++){
			for(int cNum=1;cNum<cols;cNum++){
				String value= xls.getCellValue(sheetName, cNum, testStartRowNum);
				if(!(value.equals(""))){
				colValueList.add(value);
				}
			}
		}
		String[] colValues= colValueList.toArray(new String[colValueList.size()]);
		return colValues;
	}

	public String [] getDataForTheColumn(ExcelOperation xls,String ReportType,String colName){
		String sheetName=Constants.EXPECTED_SHEET;
		int testStartRowNum=1;
		while(!xls.getCellData(sheetName, 0, testStartRowNum).equals(ReportType)){
			testStartRowNum++;
		}
		System.out.println("Row start num is "+testStartRowNum); 
				
		int colNum=0;
		while(!xls.getCellData(sheetName, colNum, 1).equals(colName)){
			colNum++;
		}	
		System.out.println("Required column num is "+colNum); 
		int rowCount=testStartRowNum;
		while(!xls.getCellData(sheetName, colNum, rowCount).equals("")){
			rowCount++;
		}
		System.out.println("Row count num is "+rowCount); 		
		ArrayList<String> colValueList=new ArrayList<>();
		int dataRow=0;
		for(int rNum=testStartRowNum; rNum<=rowCount-1; rNum++){
				String value= xls.getCellData(sheetName, colNum, rNum);
				colValueList.add(value);
		}
		String[] colValues= colValueList.toArray(new String[colValueList.size()]);
		System.out.println("Total num of rows is : "+colValues.length); 
		return colValues;
	}
	
	public String [] getAllDataForTheReport(ExcelOperation xls,String reportType,String[] reportNames,String colName){
		String sheetName=Constants.EXPECTED_SHEET;
		int testStartRowNum=1;
		while(!xls.getCellData(sheetName, 0, testStartRowNum).equals(reportType)){
			testStartRowNum++;	
		}
		System.out.println("Row start num is "+testStartRowNum); 
				
		int colNum=0;
		while(!xls.getCellData(sheetName, colNum, 1).equals(colName)){
			colNum++;
		}	
		System.out.println("Required column num is "+colNum); 
		int rowCount=testStartRowNum;
		while(!xls.getCellData(sheetName, colNum, rowCount).equals("")){
			rowCount++;
		}
		System.out.println("Row count num is "+rowCount); 		
		ArrayList<String> colValueList=new ArrayList<>();
		
		int dataRow=0;
		for(int rNum=testStartRowNum;rNum<=rowCount-1;rNum++){
				String value= xls.getCellData(sheetName, colNum, rNum);
				colValueList.add(value);
		}
		String[] colValues= colValueList.toArray(new String[colValueList.size()]);
		System.out.println("Total num of rows is : "+colValues.length); 
		return colValues;
	}
}
