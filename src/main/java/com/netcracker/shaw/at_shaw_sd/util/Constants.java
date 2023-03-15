package com.netcracker.shaw.at_shaw_sd.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

public class Constants {
	
	public static final String REGRESSION_SUITE=System.getProperty("user.dir")+"//testdata//RegressionSuite.xls";
	public static final String BEFORE_UPGRADE_SUITE=System.getProperty("user.dir")+"//testdata//UpgradePathSuite.xls";
	public static final String AFTER_UPGRADE_SUITE=System.getProperty("user.dir")+"//testdata//UpgradePathSuite.xls";
	public static final String TCID_COL="TCID";
	public static final String TEST_SHEET="TestData";
	public static final String TESTCASES_SHEET="TestCases";
	public static final String RUNMODE_COL="Runmode";
	public static final String EXECUTION_REPORT_PATH=System.getProperty("user.dir")+"//reportxml//executionReport.html";
	public static final String SCREENSHOT_PATH =System.getProperty("user.dir")+"//screenshot//";
	public static final String REPORT_PATH=System.getProperty("user.dir")+"//report";
	public static final String EXTENT_CONFIG_PATH=System.getProperty("user.dir")+"//reportxml//extentconfig.xml";
	public static final String CUSTOM_TESTNG_REPORT_PATH=System.getProperty("user.dir")+"//reportxml//customizedTestngReport.html";
	public static final String EXPECTED_SHEET=System.getProperty("user.dir")+"//ExpectedValues//RegressionSuite.xls";
	public static final String LANDING_PAGE_PROP_PATH=System.getProperty("user.dir")+"//pageLocators/landingPage.properties";
	public static final String COM_ORDER_PAGE_PROP_PATH=System.getProperty("user.dir")+"//pageLocators//comOrderPage.properties";
	public static final String SOM_ORDER_PAGE_PROP_PATH=System.getProperty("user.dir")+"//pageLocators//somOrderPage.properties";
	public static final String ORDER_CREATION_PAGE_PROP_PATH=System.getProperty("user.dir")+"//pageLocators//orderCreation.properties";
	public static final String ORDER_HISTORY_PAGE_PROP_PATH=System.getProperty("user.dir")+"//pageLocators//orderHistory.properties";
	public static final DateFormat UNIX_FOLDER_DATE_FORMAT = new SimpleDateFormat("yy-MM-dd");
	
}

