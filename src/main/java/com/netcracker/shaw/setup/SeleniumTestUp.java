package com.netcracker.shaw.setup;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.netcracker.shaw.at_shaw_sd.pageobject.COMOrdersPage;
import com.netcracker.shaw.at_shaw_sd.pageobject.LandingPage;
import com.netcracker.shaw.at_shaw_sd.pageobject.OrderCreationPage;
import com.netcracker.shaw.at_shaw_sd.pageobject.OrderHistoryPage;
import com.netcracker.shaw.at_shaw_sd.pageobject.SOMOrderPage;
import com.netcracker.shaw.at_shaw_sd.pageobject.ValidateReport;
import com.netcracker.shaw.at_shaw_sd.soap.SoapUIRestAPIpage;
import com.netcracker.shaw.at_shaw_sd.util.Constants;
import com.netcracker.shaw.at_shaw_sd.util.ExcelOperation;
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.netcracker.shaw.report.ExecutionReport;
import com.netcracker.shaw.report.ExtentReportManager;

import com.netcracker.shaw.report.TestListener;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

public class SeleniumTestUp extends TestListenerAdapter {

	Date startDate = null;
	Date endDate = null;

	List<ITestNGMethod> passedtests = new ArrayList<ITestNGMethod>();
	List<ITestNGMethod> failedtests = new ArrayList<ITestNGMethod>();
	List<ITestNGMethod> skippedtests = new ArrayList<ITestNGMethod>();
	List<ITestNGMethod> totalTests = new ArrayList<ITestNGMethod>();

	public static String PhSrlNo=Utility.getValueFromPropertyFile("PhoneSrlNum");
	public static String IntHitronSrlNo=Utility.getValueFromPropertyFile("IntHitronSrlNum");
	public static String IntCiscoSrlNo=Utility.getValueFromPropertyFile("IntCiscoSrlNum");
	public static String IntACSrlNo=Utility.getValueFromPropertyFile("IntACSrlNum");
	public static String ConvgdSrlNo=Utility.getValueFromPropertyFile("ConvergedSrlNum");
	public static String TVBoxSrlNo=Utility.getValueFromPropertyFile("TVBoxSrlNum");
	public static String TVPortalSrlNo=Utility.getValueFromPropertyFile("TVPortalSrlNum");
	public static String TVDctSrlNo=Utility.getValueFromPropertyFile("TVDctSrlNum");
	public static String TVMoxiSrlNo=Utility.getValueFromPropertyFile("TVMoxiSrlNum");
	public static String TVHDSrlNo=Utility.getValueFromPropertyFile("TVHD3510SrlNum");
	public static String TVDcx3400SrlNo=Utility.getValueFromPropertyFile("TVDcx3400SrlNum");
	public static String TVDCXSrlNo=Utility.getValueFromPropertyFile("TVDcx3200SrlNum");
	public static String IntFibrSrlNo=Utility.getValueFromPropertyFile("IntFibrSrlNum");
	public static String TVMOCASrlNo=Utility.getValueFromPropertyFile("TVMOCASrlNum");
	public static String XPODSrlNo=Utility.getValueFromPropertyFile("XPODextenderSrlNum");
	public static String TVShawGatewaySrlNo=Utility.getValueFromPropertyFile("TVShawGatewaySrlNum");
	public static String TVShawPortalSrlNo=Utility.getValueFromPropertyFile("TVShawPortalSrlNum");
	public static String TV3400Dcx250GBSrlNo=Utility.getValueFromPropertyFile("TV3400DCX250GBSrlNum");
	public static String TVDcx3200P2MTCSrlNo=Utility.getValueFromPropertyFile("TVDCX3200P2MTCSrlNum");
	public static String TV3200HDGuideSrlNo=Utility.getValueFromPropertyFile("TVDCX3200HDGuideSrlNum");
	public static String MoxiPortal415SrlNo=Utility.getValueFromPropertyFile("MoxiPortal(415/410)SrlNum");
	public static String BlueCurveWirelessSrlNo=Utility.getValueFromPropertyFile("BlueCurveWireless4KPlayer418SrlNum");
	public static String BlueCurveTVplayerSrlNo=Utility.getValueFromPropertyFile("BlueCurvePlayer526SrlNum");
	public static String CustomerProvidedTVSrlNo=Utility.getValueFromPropertyFile("CustomerProvidedEquipment");
	public static String CustomerProvidedIntSrlNo=Utility.getValueFromPropertyFile("CustOwnedEquipmentForInt");
	
	TestListener obj = new TestListener();
	ExecutionReport ex = new ExecutionReport();

	public static String path;
	public static String sheet;
	public static WebDriver DRIVER;
	ExtentReportManager ereport = new ExtentReportManager();
	static int totalTestCount = 0;
	static int totalTestPassed = 0;
	static int totalTestFailed = 0;
	static int totalTestSkipped = 0;

	public static ExcelOperation xls;
	public ExtentReports extent;
	public ExtentTest test;
	public static String testName;
	public String tcid;
	public static String sheetName;
	protected static Logger log = Logger.getLogger(SeleniumTestUp.class);
	String appProcessName = Utility.getValueFromPropertyFile("processtokill");

	public SeleniumTestUp() {
	}

	public String getTcid() {
		return tcid;
	}

	public void setTcid(String tcid) {
		this.tcid = tcid;
	}

	public void setExelFile(ExcelOperation xls) {
		this.xls = xls;
	}

	@Override
	public void onFinish(ITestContext context) {
		log.debug("Test execution finished");
	}
	
	@BeforeClass
	public void beforeClass(){
		String className=this.getClass().getSimpleName().toString();
		log.debug("Class name is :"+className);
		switch(className){
		
		case "BeforeUpgrade":
			  xls= new ExcelOperation(Constants.BEFORE_UPGRADE_SUITE);
			  sheetName="BeforeUpgrade";	
			  break;
			  
		case "AfterUpgrade":
			  xls= new ExcelOperation(Constants.AFTER_UPGRADE_SUITE);
			  sheetName="AfterUpgrade";
			  break;
			  
		default:
			  xls=new ExcelOperation(Constants.REGRESSION_SUITE);
			  sheetName="TestData";
			  log.debug("Fle path is "+Constants.REGRESSION_SUITE);
		}
	}
	
	@AfterClass
	public void updateReport() {
		Utility.setValueInColumnFortheRow(xls, "Execution Report", 2, "Passed", totalTestPassed);
		Utility.setValueInColumnFortheRow(xls, "Execution Report", 2, "Failed", totalTestFailed);
		Utility.setValueInColumnFortheRow(xls, "Execution Report", 2, "Skipped", totalTestSkipped);
		Utility.setValueInColumnFortheRow(xls, "Execution Report", 2, "Executed", totalTestCount);
		String className = this.getClass().getSimpleName().toString();
		log.debug("Please find below the execution report for class :" + className);
	}

	@BeforeMethod
	public void beforeMethod(Method method, ITestContext testobj) throws MalformedURLException {
		startDate = testobj.getStartDate();
		log.debug("Before Method-> Start Date is:" + startDate);
		extent = ereport.getInstance(method.getName());
		test = extent.startTest(method.getName());
		Utility util = new Utility();
		if (Utility.getValueFromPropertyFile("grid").equalsIgnoreCase("true"))
			DRIVER = util.getRemoteBrowser();
		else
			DRIVER = util.getBrowser();
	}

	@BeforeSuite
	public void killActiveProcess() throws Exception {
		// startDate=testobj.getStartDate();
		Runtime.getRuntime().exec("TASKKILL /IM chromedriver.exe /F");
	}

	@AfterSuite
	public void generateSummary() {
		endDate = new Date();
		log.debug("Start Dt is:" + startDate);
		log.debug("Endt Date of suite execution is:" + endDate);
		ex.generateExecutionReport(totalTestCount, totalTestPassed, totalTestFailed, totalTestSkipped, startDate,endDate);
	}

	@AfterMethod
	public void afterMethod(ITestResult result, Method method, ITestContext obj) {
		try {
			//log.debug("entering after method: Total counts is "+ totalTestCount + " SheetName : " + sheetName);
			totalTestCount++;
			if (result.getStatus() == ITestResult.SUCCESS) {
				totalTestPassed++;
				Utility.setValueInColumnForTestName(xls, sheetName, "Status", method.getName(), "PASSED");
			} else if (result.getStatus() == ITestResult.FAILURE) {
				totalTestFailed++;
				Utility.setValueInColumnForTestName(xls, sheetName, "Status", method.getName(), "FAILED");
			} else if (result.getStatus() == ITestResult.SKIP) {
				totalTestSkipped++;
				Utility.setValueInColumnForTestName(xls, sheetName, "Status", method.getName(), "SKIPPED");
			}
			extent.endTest(test);
			extent.flush();
			DRIVER.quit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ValidateReport createChildTest(ExtentTest test, ExtentReports rep, ValidateReport report) {
		report = new ValidateReport(test);
		report.setTest(test);
		return report;
	}

	public OrderCreationPage createChildTest(ExtentTest test, ExtentReports rep, OrderCreationPage o,
			WebDriver driver) {
		o = new OrderCreationPage(test, driver);
		o.setTest(test);
		o.setDriver(driver);
		return o;
	}

	public LandingPage createChildTest(ExtentTest test, ExtentReports rep, LandingPage l, WebDriver driver) {
		l = new LandingPage(test, driver);
		l.setTest(test);
		l.setDriver(driver);
		return l;
	}

	public COMOrdersPage createChildTest(ExtentTest test, ExtentReports rep, COMOrdersPage com, WebDriver driver) {
		com = new COMOrdersPage(test, driver);
		com.setTest(test);
		com.setDriver(driver);
		return com;
	}

	public SOMOrderPage createChildTest(ExtentTest test, ExtentReports rep, SOMOrderPage som, WebDriver driver) {
		som = new SOMOrderPage(test, driver);
		som.setTest(test);
		som.setDriver(driver);
		return som;
	}

	public OrderCreationPage createChildTest(ExtentTest test, ExtentReports rep, OrderCreationPage o) {
		o = new OrderCreationPage(test);
		o.setTest(test);
		return o;
	}

	public LandingPage createChildTest(ExtentTest test, ExtentReports rep, LandingPage l) {
		l = new LandingPage(test);
		l.setTest(test);
		return l;
	}

	public COMOrdersPage createChildTest(ExtentTest test, ExtentReports rep, COMOrdersPage com) {
		com = new COMOrdersPage(test);
		com.setTest(test);
		return com;
	}

	public SOMOrderPage createChildTest(ExtentTest test, ExtentReports rep, SOMOrderPage som) {
		som = new SOMOrderPage(test);
		som.setTest(test);
		return som;
	}
	
	public OrderHistoryPage createChildTest(ExtentTest test, ExtentReports rep, OrderHistoryPage orderHistory) {
		orderHistory = new OrderHistoryPage(test);
		orderHistory.setTest(test);
		return orderHistory;
	}
	
	public SoapUIRestAPIpage createChildTest(ExtentTest test, ExtentReports rep, SoapUIRestAPIpage soapRest) {
		soapRest = new SoapUIRestAPIpage(test);
		soapRest.setTest(test);
		return soapRest;
	}
	
	@DataProvider
	public static Object[][] getTestData(Method method) {

		return Utility.getData(xls, sheetName, method.getName());
	}
}
