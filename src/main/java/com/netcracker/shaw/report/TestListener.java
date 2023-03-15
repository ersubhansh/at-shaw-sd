package com.netcracker.shaw.report;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import com.netcracker.shaw.at_shaw_sd.util.Constants;
import com.netcracker.shaw.at_shaw_sd.util.ExcelOperation;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

public class TestListener implements ITestListener, IInvokedMethodListener, IAnnotationTransformer {
	public static ExcelOperation xls;
	public static String sheetName;
	ExtentTestManager testmgr = new ExtentTestManager();
	public String category;
	static Logger log = Logger.getLogger(TestListener.class);
	
	@Override
	public void onStart(ITestContext Result) {
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult Result) {
	}

	// When Test case get Started, this method is called.
	@Override
	public void onTestStart(ITestResult Result) {
		log.debug(Result.getMethod().getMethodName() + " test case started");
		log.debug("Test Name is " + ExtentTestManager.getTest());
	}

	// When Test case get Success, this method is called.
	@Override
	public void onTestSuccess(ITestResult Result) {
		log.debug("The name of the testcase passed is :" + Result.getMethod().getMethodName());
		// testmgr.endTest(extent,test);
	}
	
	// When Test case get failed, this method is called.
	@Override
	public void onTestFailure(ITestResult Result) {
		log.debug("The name of the testcase failed is :" + Result.getMethod().getMethodName());
		// testmgr.endTest(extent,test);
	}

	// When Test case get Skipped, this method is called.
	@Override
	public void onTestSkipped(ITestResult Result) {
		log.debug("The name of the testcase Skipped is :" + Result.getMethod().getMethodName());
		// testmgr.endTest(extent,test);
	}

	public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
		String textMsg = "About to begin executing following method : " + arg0.getTestMethod().toString();
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult Result) {
		String textMsg = "Completed executing following method : " + method.getTestMethod().toString();
		// testmgr.endTest(extent,test);
	}

	public static void setTheDataExcel(String className){
		log.debug("Class Name is: "+className);
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
		}
	}
	
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		String cls = testMethod.getDeclaringClass().getSimpleName();
		setTheDataExcel(cls);

		if (xls.getDataForTestName(xls, sheetName, testMethod.getName(), "Run").equals("No")) {
			log.debug("Found Run as No");
			annotation.setEnabled(false);
			log.debug("Skipped Test Case: " + testMethod.getName());
		}
	}

	@Override
	public void onFinish(ITestContext context) {
		
	}
}

