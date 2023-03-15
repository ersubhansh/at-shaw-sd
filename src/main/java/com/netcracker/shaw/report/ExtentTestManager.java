package com.netcracker.shaw.report;

import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

public class ExtentTestManager 
{
	static Map extentTestMap = new HashMap();
   //static ExtentReports extent = ExtentReportManager.getInstance();
 
	public static synchronized ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}

	public static synchronized void endTest(ExtentReports extent) {
		extent.endTest((ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId())));
		extent.flush();
	}
 /*
    @SuppressWarnings("unchecked")
	public static synchronized ExtentTest startTest(ExtentTest test,String testName, String category) 
	{
        test = extent.startTest(testName);
    	test.assignCategory(category);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }
*/
	/*@SuppressWarnings("unchecked")
	public static ExtentTest startChildTest(String testName) 
	{
		 ExtentTest childtest = extent.startTest(testName);
	        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
	        return childtest;
	}*/
	
	@SuppressWarnings("rawtypes")
	private static ThreadLocal parentTest = new ThreadLocal();
	private static ThreadLocal test = new ThreadLocal();

	/*@BeforeSuite
	public void beforeSuite() {
		extent = ExtentReportManager.getInstance();
	}
	
	@BeforeClass
	public synchronized void beforeClass() 
	{
	    ExtentTest parent = extent.startTest(getClass().getName());
	    parentTest.set(parent);
	}*/
}


