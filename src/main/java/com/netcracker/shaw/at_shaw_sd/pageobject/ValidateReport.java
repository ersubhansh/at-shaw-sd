package com.netcracker.shaw.at_shaw_sd.pageobject;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import com.netcracker.shaw.at_shaw_sd.util.ExcelOperation;
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

public class ValidateReport extends BasePage {
	Logger log = Logger.getLogger(ValidateReport.class);
	String actualStringXmlVal = "";
	String expectedStringXmlVal = "";
	ArrayList<String> dynamicValues=new ArrayList<String>();
	SoftAssert softAssert = new SoftAssert();

	public ValidateReport(ExtentTest test) {
		super(test);
	}

	public ValidateReport(ExtentTest test, WebDriver driver) {
		super(test, driver);
	}

	public void setTest(ExtentTest test1) {
		test = test1;
	}

	public void setDriver(WebDriver driver1) {
		driver = driver1;
	}

	public String getReportXML(String sender, String receiver, String orderName, String uniqVal) {
		String requiredXMLString = Utility.getXMLString(driver, "Asyncbody_t", sender, receiver, orderName, uniqVal);
		return requiredXMLString;
	}

	public boolean validateCOMIntegrationReports(ExcelOperation xl,String tcid, int count,ArrayList<String> values){
		boolean val=false;
		dynamicValues=values;
		log.debug("Entering validateCOMIntegrationReport:");
		String SheetName = "ExpectedValues";
		
		String report = tcid + "-" + "COM";
		val = validateIntegrationReports(xl, report, count);

		if (val == false) {
			test.log(LogStatus.FAIL, "The Report Validation Failed For: " + report);
		}
		return val;
	}
	
	public boolean validateCOMIntegrationReportsSpcl(ExcelOperation xls,String tcid, int count,ArrayList<String> values){
		boolean val=false;
		dynamicValues=values;
		log.debug("Entering validateSOMIntegrationReport:");
		String SheetName = "ExpectedValues";

		String report = tcid + "-" + "COM";
		val = validateIntegrationReports(xls,report, count);
		
		return val;
	}
	
	public boolean validateSOMIntegrationReports(ExcelOperation xls,String tcid, int count,ArrayList<String> values){
		boolean val=false;
		dynamicValues=values;
		log.debug("Entering validateSOMIntegrationReport:");
		String SheetName = "ExpectedValues";
		String report = tcid + "-" + "SOM";
		val = validateIntegrationReports(xls,report, count);
		if (val == false) {
			test.log(LogStatus.FAIL, "The Report Validation Failed For: " + report);
		}
		return val;
	}
	
	public boolean validateSOMIntegrationReportsSpcl(ExcelOperation xls,String tcid, int count,ArrayList<String> values){
		boolean val=false;
		dynamicValues=values;
		log.debug("Entering validateSOMIntegrationReport:");
		String SheetName = "ExpectedValues";
		String report = tcid + "-" + "SOM";
		val = validateIntegrationReports(xls,report, count);
		return val;
	}
	
	public boolean validateIntegrationReports(ExcelOperation xls,String ReportType, int rowNum) {
		String SheetName = "ExpectedValues";
		log.debug("Entering Method validateIntegrationReport");
        log.debug("Report type is " +ReportType);
		boolean val = false;
		int rowStartnum = xls.getCellRowNum("ExpectedValues","Report_Name",ReportType) + rowNum;
		String reportName =ReportType+" "+ xls.getCellData("ExpectedValues", "Report_Name", rowStartnum);
		log.debug("Row start num is: " + rowStartnum + " and Report Name is: " + reportName);
		int rowCount = rowStartnum;
		val = validateReportforTheLink(xls, reportName, rowStartnum);
		return val;
	}

	public boolean validateReportforTheLink(ExcelOperation xls, String reportName, int rowNum) {
		String SheetName = "ExpectedValues";
		log.debug("Entering Method validateOneReportforOneLink");
		boolean val = false;
		
		log.debug("Validating Report For " + reportName);
		String[] paramValues = xls.getDataForTheRowNum(xls, rowNum, SheetName);
		String sender = paramValues[1];
		String receiver = paramValues[2];
		String orderName = paramValues[3];
		String xmlHeader = paramValues[4];
		String[] dynamicVals=dynamicValues.toArray(new String[dynamicValues.size()]);
		String[] valueList1 = Arrays.copyOfRange(paramValues, 5, paramValues.length);
		String[] valueList = ArrayUtils.addAll(valueList1,dynamicVals);
		String reportXml = Utility.getXMLString(driver, "Asyncbody_t", sender, receiver, orderName, xmlHeader);
		test.log(LogStatus.INFO, "Report Name: " + reportName);
		if (reportXml.equals("")) {
			log.debug("The XML string is not found in the Report");
			test.log(LogStatus.INFO, "The Required String is not found for this Report");
			val = false;
		} else {
			log.debug("The Report XML string :" + reportXml);
			String[][] valuesFoundOrMissing = Utility.matchArrayvaluesInaString(valueList, reportXml);
			if (valuesFoundOrMissing[0].length == valueList.length) {
				String matchingValues = String.join(" ," , valuesFoundOrMissing[0]);
				log.debug("All"+" "+ valuesFoundOrMissing[0].length+" values are matched in report: " + reportName + " and printed below : " + matchingValues);
				test.log(LogStatus.INFO, "All values are matched in report and are printed Below : " + matchingValues);
				val = true;
			} else {
				String missingValues = String.join(" ," , valuesFoundOrMissing[1]);
				log.debug(valuesFoundOrMissing[1].length + "values are missing in report: "+reportName+" and Printed Below " +missingValues);
				test.log(LogStatus.INFO, "Below values are missing in report: " + missingValues);
     		}
		}
		return val;
	}

	public void printParametersinLogs(Logger log, String[] values) {
		log.debug("Inside Method to Print Parameter values:");
		log.debug("Total values: " + values.length);
		String valuestoPrint = String.join(" ," , values);
		log.debug(valuestoPrint);
		test.log(LogStatus.INFO, valuestoPrint);
	}
	public void updateExpectedData(String[] rowNames,int rowNum){
		for(int i=0;i<rowNames.length;i++){
			
		}
	}
}
