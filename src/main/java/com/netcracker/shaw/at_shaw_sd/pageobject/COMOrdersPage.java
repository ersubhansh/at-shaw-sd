package com.netcracker.shaw.at_shaw_sd.pageobject;

import static com.netcracker.shaw.element.pageor.COMOrdersPageElement.*;
import static com.netcracker.shaw.element.pageor.OrderCreationPageElement.ADD_PHONE_PROD;
import static com.netcracker.shaw.element.pageor.OrderCreationPageElement.APPLY_CHANGES;
import static com.netcracker.shaw.element.pageor.OrderCreationPageElement.FINISH;
import static com.netcracker.shaw.element.pageor.OrderCreationPageElement.IMMEDIATE_CHANGE_AUTHORIZED_TEXT;
import static com.netcracker.shaw.element.pageor.OrderCreationPageElement.SWAP_SL_NO;
import static com.netcracker.shaw.element.pageor.OrderCreationPageElement.TECHNICIAN_ID;
import static com.netcracker.shaw.element.pageor.OrderCreationPageElement.TECH_RADIO_BTN;
import static com.netcracker.shaw.element.pageor.OrderCreationPageElement.VALIDATE_SWAP_SLNO;
import static com.netcracker.shaw.element.pageor.OrderCreationPageElement.VALIDATE_TECH_ID;
import static com.netcracker.shaw.element.pageor.SOMOrderPageElement.INTEGRATION_REPORT;
import static com.netcracker.shaw.element.pageor.SOMOrderPageElement.ORDER_PARAMETER_TAB;
import static org.testng.Assert.assertEquals;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static com.netcracker.shaw.element.pageor.OrderCreationPageElement.OPERATIONS_DROPDOWN;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.touch.ScrollAction;
import org.openqa.selenium.remote.server.handler.RefreshPage;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import com.google.common.io.CharStreams;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.netcracker.shaw.at_shaw_sd.util.PuttyConnector;
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 *         Aug 10, 2018
 */

@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class COMOrdersPage<COMOrdersPageElement> extends BasePage {
	public COMOrdersPage(ExtentTest test) {
		super(test);
	}

	public COMOrdersPage(ExtentTest test, WebDriver driver) {
		super(test, driver);
	}

	public void setTest(ExtentTest test1) {
		test = test1;
	}

	public void setDriver(WebDriver driver1) {
		driver = driver1;
	}

	JavascriptExecutor javascript = ((JavascriptExecutor) driver);
	boolean isAllOrdersPresent = false;
	boolean clecKOrderStatus = false;
	boolean COMOrderStatus = false;

	Logger log = Logger.getLogger(COMOrdersPage.class);

	public COMOrdersPage navigateToCOMOrder() {
		try {
			log.debug("Entering navigateToCOMOrder");
			wait(1);
			click(COM_ORDER_TAB);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToCOMOrder");
		} catch (Exception e) {
			log.error("Error in navigateToCOMOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return new COMOrdersPage(test);
	}

	public COMOrdersPage navigateToCLECOrder() {
		try {
			log.debug("Entering navigateToCLECOrder");
			click(CLEC_REQUEST_TAB);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			log.debug("Leaving navigateToCLECOrder");
		} catch (Exception e) {
			log.error("Error in navigateToCLECOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return new COMOrdersPage(test);
	}

	public void verifyRecordsCountInCOMOrder(int expectedRows) {
		log.debug("Entering verifyRecordsCountInCOMOrder");
		try {
			wait(2);
			click(COM_ORDER_TAB);
			int record = countRowsinTable(ROWS_IN_COM_ORDER);
			int counter = 0;
			while (!(record == expectedRows) && (counter <= 2)) {
				wait(60);
				refreshPage();
				record = countRowsinTable(ROWS_IN_COM_ORDER);
				log.debug("Refreshed Page  : " + counter + " times " + " and record count reached to : " + record);
				counter++;
			}
			log.debug("Wait Time At COM Tab : " + (counter) + "Minutes");
			if ((record == expectedRows)) {
				test.log(LogStatus.PASS, "Record count match", "Total Number of records in COM page is: " + record);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			} else {
				log.error("Expected Count mismatched in COM orders TAB");
				test.log(LogStatus.FAIL, "Record count mis-matched in COM",
						"Expected Count mismatched in COM orders TAB");
				test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				Assert.assertTrue(false, "Expected Count mismatched in COM orders TAB");
			}
			log.debug("Leaving verifyRecordsCountInCOMOrder");
		} catch (Exception e) {
			log.error("Error in verifyRecordsCountInCOMOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.INFO, "Record count match", "Expected Records not displaying in COM order");
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void checkEffectiveDateStatus() {
		try {
			log.debug("Entering checkEffectiveDateStatus");
			wait(2);
			if (!getText(EFFECTIVE_DATE_STATUS).equalsIgnoreCase("Completed")) {
				setMarkFinishedTask();
			}
			log.debug("Leaving checkEffectiveDateStatus");
		} catch (Exception e) {
			log.error("Error in checkEffectiveDateStatus:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void verifyCLECRequestBeforeJobRun() {
		try {
			log.debug("Entering verifyCLECRequestBeforeJobRun");
			wait(1);
			click(CLEC_REQUEST_TAB);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			int count = 1;
			log.debug("Record count " + (countRowsinTable(CLEC_TAB_TABLE)));
			while ((countRowsinTable(CLEC_TAB_TABLE) < 2) && (count <= 5)) {
				wait(60);
				refreshPage();
				log.debug("Count value before inc " + count);
				count++;
			}
			if (!isDisplayed(E911ADD)) {
				log.debug("Failed to find E911 record after waiting for " + count + "minutes");
			}
			String blif_Req_Status = getText(BLIF_REQUEST_STATUS);
			String E911ADD_Status = getText(E911ADD_STATUS);
			if (blif_Req_Status.equalsIgnoreCase("confirmed") && E911ADD_Status.equalsIgnoreCase("In Process")) {
				test.log(LogStatus.PASS, "CLEC order Status", "CLEC order status matches");
			}
			log.debug("Leaving verifyCLECRequestBeforeJobRun");
		} catch (Exception e) {
			log.error("Error in verifyCLECRequestBeforeJobRun:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.INFO, "CLEC order Status", "CLEC order status does not match");
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void suspendLOBProducts(String Product) {
		try {
			log.debug("Entering suspendLOBProducts");
			click(SUSPENSION_HISTORY);
			wait(2);
			click(SUSPEND_BUTTON);
			wait(3);
			if (Product.equals("Phone Record")) {
				wait(1);
				click(SUSPEND_DIGITAL_PHONE);
			}
			if (Product.equals("Internet Record")) {
				wait(1);
				click(SUSPEND_INTERNET_TEXT);
			}
			if (Product.equals("TV Record")) {
				wait(1);
				click(SUSPEND_TV);

			}
			wait(2);
			inputText(SUSPENSION_NOTES, "Suspend");
			wait(1);
			inputText(AUTHORIZED_BY, "ATTest");
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(SUBMIT_BUTTON);
			wait(10);
			do {
				wait(2);
			} while (!isDisplayed(RETURN_TO_SUSPENSION_HISTORY));
			wait(1);
			click(RETURN_TO_SUSPENSION_HISTORY);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving suspendLOBProducts");
		} catch (Exception e) {
			log.error("Error in suspendLOBProducts:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void resumeLOBProducts(String Product) {
		try {
			log.debug("Entering resumeLOBProducts");
			wait(1);
			click(SUSPENSION_HISTORY);
			if (Product.equals("Phone Record")) {
				wait(2);
				click(SUSPESNTION_PHONE_HISTORY_RECORD);
			}
			if (Product.equals("Internet Record")) {
				wait(2);
				click(SUSPESNTION_INTERNET_HISTORY_RECORD);
			}
			if (Product.equals("TVHardware1")) {
				wait(2);
				click(SUSPESNTION_TV_HISTORY_RECORD1);
			}
			if (Product.equals("TVHardware2")) {
				wait(2);
				click(SUSPESNTION_TV_HISTORY_RECORD2);
			}
			wait(2);
			click(RESUME_BUTTON);
			wait(2);
			inputText(RESUME_NOTE, "Resume");
			wait(1);
			inputText(RESUME_AUTHORIZED, "TA Test");
			wait(1);
			click(SUBMIT_BUTTON);
			wait(5);
			click(RETURN_TO_SUSPENSION_HISTORY_RECORD);
			wait(2);
			click(LINK_TO_COM_PAGE);
			wait(2);
			click(SUSPENSION_HISTORY);
			wait(2);
			getText(SUSPENSION_STATUS);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving resumeLOBProducts");
		} catch (Exception e) {
			log.error("Error in resumeLOBProducts:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void verifyCLECRequestE911Record() {
		try {
			log.debug("Entering verifyCLECRequestE911Record");
			wait(1);
			do {
				wait(2);
				refreshPage();
			} while (!isDisplayed(E911ADD_STATUS2));
			wait(1);
			log.debug("Leaving verifyCLECRequestE911Record");
		} catch (Exception e) {
			log.error("Error in verifyCLECRequestE911Record:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.INFO, "CLEC order Status", "CLEC order status does not match");
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void verifyCLECRequestAfterJobRun() {
		try {
			log.debug("Entering verifyCLECRequestAfterJobRun");
			click(CLEC_REQUEST_TAB);
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			String blif_Req_Status = getText(BLIF_REQUEST_STATUS);
			String E911ADD_Status = getText(E911ADD_STATUS);
			if (isDisplayed(BLIF_REQUEST) && isDisplayed(E911ADD) && blif_Req_Status.equalsIgnoreCase("confirmed")
					&& E911ADD_Status.equalsIgnoreCase("Confirmed")) {
				test.log(LogStatus.PASS, "CLEC order Status", "CLEC order status matches");
				wait(1);
			}
			log.debug("Leaving verifyCLECRequestAfterJobRun");
		} catch (Exception e) {
			log.error("Error in verifyCLECRequestAfterJobRun:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.INFO, "CLEC order Status", "CLEC order status does not match");
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public String suspendStatus() {
		log.debug("Entering suspendStatus");
		String suspensionStatus = "";
		try {
			suspensionStatus = getText(SUSPENSION_STATUS);
			if (suspensionStatus.equalsIgnoreCase("Active")) {
				test.log(LogStatus.PASS, "Status", "Status is : " + suspensionStatus);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			log.debug("Leaving suspendStatus");
		} catch (Exception e) {
			log.error("Error in suspendStatus:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return suspensionStatus;
	}

	public void verifyRecordsCountInCLECOrder(int expectedRows) {
		try {
			log.debug("Entering verifyRecordsCountInCLECOrder");
			click(CLEC_REQUEST_TAB);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			int record = countRowsinTable(ROWS_IN_CLEC_ORDER);
			int counter = 1;
			while (!(record == expectedRows) && (counter <= 5)) {
				refreshPage();
				record = countRowsinTable(ROWS_IN_CLEC_ORDER);
				counter++;
			}
			if (!(record == expectedRows)) {
				log.error("CLECOrder record count mismatched");
			}
			if (Utility.compareIntegerVals(record, expectedRows)) {
				test.log(LogStatus.PASS, "Record count match", "Total Number of records in CLEC Tab is: " + record);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			log.debug("Leaving verifyRecordsCountInCLECOrder");
		} catch (Exception e) {
			log.error("Error in verifyRecordsCountInCLECOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.INFO, "Record count match", "Expected Records not displaying in CLEC order");
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToServiceInformationTab() {
		try {
			log.debug("Entering navigateToServiceInformationTab");
			wait(2);
			click(SERVICE_INFORMATION_TAB);
			wait(1);
			javascript.executeScript("window.scrollBy(0,-500)", "");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToServiceInformationTab");
		} catch (Exception e) {
			log.error("Error in navigateToServiceInformationTab:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public boolean portOptionDisplayed() {
		boolean portOption = false;
		try {
			log.debug("Entering portOptionDisplayed");
			if (isDisplayed(PORT_OPTION)) {
				portOption = true;
			}
			log.debug("Port Option is displayed:" + portOption);
			log.debug("Leaving portOptionDisplayed");
		} catch (Exception e) {
			portOption = true;
			log.error("Error in port Option Displayed:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return portOption;
	}

	public void NavigvateToCustOrderTasksTab() {
		try {
			log.debug("Entering NavigvateToCustOrderTasksTab");
			click(NEW_CUSTOMER_ORDER);
			wait(2);
			click(TASK_TAB);
			wait(1);
			javascript.executeScript("window.scrollBy(0, 600)", "");
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			javascript.executeScript("window.scrollBy(0, -600)", "");
			wait(1);
			click(SERVICE_INFORMATION_TAB);
			wait(1);
			javascript.executeScript("window.scrollBy(0, 600)", "");
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			javascript.executeScript("window.scrollBy(0, -600)", "");
			wait(1);
			log.debug("Leaving NavigvateToCustOrderTasksTab");
		} catch (Exception e) {
			log.error("Error in NavigvateToCustOrderTasksTab:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void verifySVGDiagram(String SVGDiagram) {
		try {
			log.debug("Entering verifySVGDiagram");
			wait(2);
			click(ORDER_PARAMETERS);
			wait(2);
			click(SVG_DIAGRAM);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			if (SVGDiagram.equals("Middle")) {
				scrollDown600();
				wait(1);
			} else {
				scrollDown();
				wait(2);
				driver.navigate().back();
			}
			log.debug("Leaving verifySVGDiagram");
		} catch (Exception e) {
			log.error("Error in verifySVGDiagram:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public String getOldSerialNbr() {
		String oldSerialNbr = "";
		try {
			log.debug("Entering getOldSerialNbr");
			click(SERVICE_INFORMATION);
			wait(2);
			JavascriptExecutor javascript = ((JavascriptExecutor) driver);
			javascript.executeScript("window.scrollBy(0,3000)", "");
			wait(4);
			oldSerialNbr = getText(PROVIONING_REGISTRATION_KEY);
			javascript.executeScript("window.scrollBy(0,-3000)", "");
			log.debug("Leaving getOldSerialNbr");
		} catch (Exception e) {
			log.error("Error in getOldSerialNbr:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return oldSerialNbr;
	}

	public String getOrderStatus() {
		String orderStatus = "";
		try {
			log.debug("Entering getOrderStatus");
			JavascriptExecutor javascript = ((JavascriptExecutor) driver);
			javascript.executeScript("window.scrollBy(0,1000)", "");
			wait(4);
			orderStatus = getText(ORDER_STATUS);
			javascript.executeScript("window.scrollBy(0,-1000)", "");
			log.debug("Leaving getOrderStatus");
		} catch (Exception e) {
			log.error("Error in getOrderStatus:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return orderStatus;
	}

	public void setMarkFinishedTask() {
		try {
			log.debug("Entering setMarkFinishedTask");
			click(TASK_TAB);
			wait(2);
			click(TASK_FILTER_STATUS);
			wait(2);
			click(TASK_WAITING_CHECKBOX);
			wait(4);
			javascript.executeScript("document.getElementsByClassName('selectAll')[0].click()");
			wait(4);
			click(MARK_FINISHED);
			wait(4);
			switchToChildAlert();
			wait(2);
			inputText(MARK_FINISH_DESCRIPTION, "Waiting tasks need to mark finish");
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(MARK_FINISH_DESCRIPTION_SEND_BTN);
			wait(1);
			log.debug("Leaving setMarkFinishedTask");
		} catch (Exception e) {
			log.error("Error in setMarkFinishedTask:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void setMarkFinishedSpecificTask(String task) {
		try {
			log.debug("Entering setMarkFinishedTask");
			click(TASK_TAB);
			wait(2);
			click(TASK_FILTER_STATUS);
			wait(2);
			click(TASK_WAITING_CHECKBOX);
			wait(2);
			if (task.equalsIgnoreCase("Wait for effective date")) {
				click(TASK_WAITING_EFFECTIVEDATE);
				wait(4);

			} else {

				javascript.executeScript("document.getElementsByClassName('selectAll')[0].click()");
				wait(4);
			}

			click(MARK_FINISHED);
			wait(4);
			switchToChildAlert();
			wait(2);
			inputText(MARK_FINISH_DESCRIPTION, "Waiting tasks need to mark finish");
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(MARK_FINISH_DESCRIPTION_SEND_BTN);
			wait(1);
			log.debug("Leaving setMarkFinishedTask");
		} catch (Exception e) {
			log.error("Error in setMarkFinishedTask:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void completeFourceRetryTask() {
		try {
			log.debug("Entering completeFourceRetryTask");
			click(TASK_TAB);
			wait(2);
			click(TASK_FILTER_STATUS);
			wait(2);
			click(TASK_ERROR_CHECKBOX);
			wait(2);
			javascript.executeScript("document.getElementsByClassName('selectAll')[0].click()");
			wait(2);
			click(TASK_FORCE_RETRY);
			wait(2);
			switchToChildAlert();
			inputText(MARK_FINISH_DESCRIPTION, "Error tasks need to Force Retry");
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(MARK_FINISH_DESCRIPTION_SEND_BTN);
			wait(1);
			log.debug("Leaving completeFourceRetryTask");
		} catch (Exception e) {
			log.error("Error in completeFourceRetryTask:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void sendSerialNbrTask(String serialNbr) {
		try {
			log.debug("Entering sendSerialNbrTask");
			wait(1);
			click(TASK_TAB);
			wait(4);
			do {
				wait(4);
			} while (!isDisplayed(TASK_SEND_SERIAL_NBR));
			click(TASK_SEND_SERIAL_NBR);
			wait(5);
			switchToChildAlert();
			inputText(TASK_TECHNICIAN_ID, "46501");
			wait(2);
			inputText(TASK_SERIAL_NBR, serialNbr);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(TASK_SEND_BUTTON);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			do {
				wait(3);
			} while (!isDisplayed(TASK_OK_BUTTON));
			click(TASK_OK_BUTTON);
			wait(2);
			log.debug("Leaving sendSerialNbrTask");
		} catch (Exception e) {
			log.error("Error in sendSerialNbrTask:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToJobMonitorURL(String JobType) {
		try {
			log.debug("Entering navigateToJobMonitorURL");
			String JobMonitorURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/common/uobject.jsp?object=6092066043013856097";
			navigate(JobMonitorURL);
			wait(1);
			test.log(LogStatus.INFO, "Job Monitor URL: ", "<a href=" + JobMonitorURL + " > " + JobMonitorURL + " </a>");
			if (JobType.equals("E911")) {
				wait(1);
				log.debug("Entering E911 Response Job");
				// added by Arun
				scrollDownToElement(E911_RESPONSE);
				wait(1);
				click(E911_RESPONSE);
				// scrollDownAndClick(E911_RESPONSE);
				wait(2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				scrollToTop();
				wait(1);
				click(RUN_JOB_BUTTON);
				wait(2);
				log.debug("Leaving E911 Response Job");
			}
			if (JobType.equals("LSC")) {
				wait(2);
				log.debug("Entering LSC Response Job");
				scrollDownToElement(LSC_RESPONSE_JOB);
				wait(1);
				click(LSC_RESPONSE_JOB);
				wait(1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(1);
				scrollToTop();
				click(RUN_JOB_BUTTON);
				wait(2);
				log.debug("Leaving LSC Response Job");
			}
			if (JobType.equals("BLIF")) {
				wait(2);
				log.debug("Entering BLIF Response Job");
				click(BLIF_RESPONSE_JOB);
				wait(2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(1);
				click(RUN_JOB_BUTTON);
				wait(2);
				log.debug("Leaving BLIF Response Job");
			}
			if (JobType.equals("Disconnect Returned Hardware")) {
				wait(2);
				log.debug("Entering Disconnect Returned Hardware Job");
				click(DISCONNECT_RETURN_HW_JOB);
				wait(2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(1);
				click(RUN_JOB_BUTTON);
				wait(2);
				log.debug("Leaving Disconnect Returned Hardware Job");
			}

		} catch (Exception e) {
			log.error("Error in navigateToJobMonitorURL:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public boolean verifyCLECRequestAfterPortedNumber() {
		try {
			log.debug("Entering verifyCLECRequestAfterPortedNumber");
			click(CLEC_REQUEST_TAB);
			wait(2);
			String blif_Req_Status1 = getText(BLIF_REQUEST_STATUS1);
			String blif_Req_Status2 = getText(BLIF_REQUEST_STATUS2);
			String LSR_Allstream_P1 = getText(LSR_ALLSTRREAM_P_STATUS1);
			String LSR_Allstream_P2 = getText(LSR_AllSTRREAM_P_STATUS2);
			String NPAC_Request_Status1 = getText(NPAC_REQUEST_STATUS1);
			String NPAC_Request_Status2 = getText(NPAC_REQUEST_STATUS2);
			String E911_Request_Status = getText(E911_REQUEST_STATUS);

			if (isDisplayed(BLIF_REQUEST_LINK1) && isDisplayed(BLIF_REQUEST_LINK2)
					&& isDisplayed(LSR_ALLSTREAM_P1_LINK1) && isDisplayed(LSR_ALLSTREAM_P1_LINK2)
					&& isDisplayed(NPAC_REQUEST_LINK1) && isDisplayed(NPAC_REQUEST_LINK2)
					&& isDisplayed(E911_REQUEST_LINK) && blif_Req_Status1.equalsIgnoreCase("Confirmed")
					&& blif_Req_Status2.equalsIgnoreCase("Confirmed") && LSR_Allstream_P1.equalsIgnoreCase("Confirmed")
					&& LSR_Allstream_P2.equalsIgnoreCase("Confirmed")
					&& NPAC_Request_Status1.equalsIgnoreCase("Confirmed")
					&& NPAC_Request_Status2.equalsIgnoreCase("Confirmed")
					&& E911_Request_Status.equalsIgnoreCase("Confirmed")) {
				clecKOrderStatus = true;
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving verifyCLECRequestAfterPortedNumber");
		} catch (Exception e) {
			log.error("Error in verifyCLECRequestAfterPortedNumber:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return clecKOrderStatus;
	}

	public void navigateToErrors() {
		try {
			log.debug("Entering navigateToErrors");
			wait(1);
			click(ERRORS);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToErrors");
		} catch (Exception e) {
			log.error("Error in navigateToErrors:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToOrderClose() {
		try {
			log.debug("Entering navigateToOrderClose");
			wait(2);
			click(ORDER_CLOSE);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToOrderClose");
		} catch (Exception e) {
			log.error("Error in navigateToOrderClose:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public String NavigateTosuspendStatusCheck(String LOBstatus) {
		String suspensionStatus = "";
		try {
			log.debug("Entering NavigateTosuspendStatusCheck");
			wait(1);
			int count = 1;
			if (LOBstatus.equals("Phone")) {
				do {
					wait(1);
				} while (!isDisplayed(SUSPESNTION_PHONE_HARDWARE_STATUS) && count <= 5);
				suspensionStatus = getText(SUSPESNTION_PHONE_HARDWARE_STATUS);
			}
			if (LOBstatus.equals("Internet")) {
				do {
					wait(1);
				} while (!isDisplayed(SUSPESNTION_INTERNET_HARDWARE_STATUS) && count <= 5);
				suspensionStatus = getText(SUSPESNTION_INTERNET_HARDWARE_STATUS);
			}
			if (LOBstatus.equals("TV")) {
				do {
					wait(1);
				} while (!isDisplayed(SUSPESNTION_TV_HARDWARE_STATUS) && count <= 5);
				suspensionStatus = getText(SUSPESNTION_TV_HARDWARE_STATUS);
			}
			wait(1);
			if (suspensionStatus.equalsIgnoreCase("Active")) {
				test.log(LogStatus.PASS, "Check The Status", "Status is : " + suspensionStatus);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			log.debug("Leaving NavigateTosuspendStatusCheck");
		} catch (Exception e) {
			log.error("Error in NavigateTosuspendStatusCheck:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return suspensionStatus;
	}

	public void navigateToCustomerDiscountsAndContracts(String order) {
		try {
			log.debug("Entering navigateToCustomerDiscountsAndContracts");
			click(CUSTOMER_PORTAL_TAB);
			wait(1);
			if (order.equalsIgnoreCase("quote Section")) {
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				click(CUSTOMER_QUOTE_SELECTION);
				wait(1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				click(QUOTE_DISCOUNTS);
				wait(1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			if (order.equalsIgnoreCase("Validate Contract")) {
				wait(1);
				int counter = 1;
				while (!isDisplayed(FIBRE150_CONTRACTS_STATUS) && (counter <= 5)) {
					wait(1);
					counter++;
				}
				if (getText(FIBRE150_CONTRACTS_STATUS).equals("Cancelled")) {
					test.log(LogStatus.PASS, "Fibre+ 150 contract status :", "Fibre+ 150 contract status is Cancelled");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				} else {
					test.log(LogStatus.FAIL, "Fibre+ 150 contract status :",
							"Expected status Cancelled but found" + getText(FIBRE150_CONTRACTS_STATUS));
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}
				wait(1);
				click(FIBRE150_ACTIVE_CONTRACTS);
				wait(2);
				if (getText(METHOD_OF_CONFIRMATION_STATUS).equals("Shaw Initiated")
						&& (getText(ACCEPTANCE_ID_STATUS).equals("Hardware returned to warehouse"))) {
					test.log(LogStatus.PASS, "Method of confirmation & Acceptancd ID status :",
							"Shaw Initiated, Hardware returned to warehouse");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				} else {
					test.log(LogStatus.FAIL, "Method of confirmation & Acceptancd ID status :",
							"Expected status Shaw Initiated, Hardware returned to warehouse. but found"
									+ getText(METHOD_OF_CONFIRMATION_STATUS) + getText(ACCEPTANCE_ID_STATUS));
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}
			}
			log.debug("Leaving navigateToCustomerDiscountsAndContracts");
		} catch (Exception e) {
			log.error("Error in navigateToCustomerDiscountsAndContracts:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public boolean verifyCLECRequestPortNUmber() {
		try {
			log.debug("Entering verifyCLECRequestAfterPortedNumber");
			click(CLEC_REQUEST_TAB);
			wait(2);
			String blif_Req_Status = getText(PORT_BLIF_REQUEST_STATUS);
			String NPAC_Request_Status = getText(NPAC_REQUEST_STATUS1);
			String LSR_Allstream_P = getText(LSR_ALLSTRREAM_P_STATUS1);
			// String E911_Request_Status=getText(E911_REQUEST_STATUS_PORT);

			if (isDisplayed(BLIF_REQUEST_LINK1) && isDisplayed(LSR_ALLSTREAM_P1_LINK1)
					&& isDisplayed(NPAC_REQUEST_LINK1) /* && isDisplayed(E911_REQUEST_LINK) */
					&& blif_Req_Status.equalsIgnoreCase("Confirmed")
					&& NPAC_Request_Status.equalsIgnoreCase("Confirmed")
					&& LSR_Allstream_P.equalsIgnoreCase("Confirmed")
			/* && E911_Request_Status.equalsIgnoreCase("Confirmed") */) {
				clecKOrderStatus = true;
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving verifyCLECRequestAfterPortedNumber");
		} catch (Exception e) {
			log.error("Error in verifyCLECRequestAfterPortedNumber:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return clecKOrderStatus;
	}

	public boolean verifyComOrderStatusConfirmed_98() {
		try {
			log.debug("Entering verifyComOrderStatusConfirmed_98");
			String New_Billing_Service_Status = getText(NEW_BILLING_SERVICE_ORDER);
			String New_CDI_CFS_Status = getText(NEW_CDI_CFS_ORDER);
			String New_Customer_Status = getText(NEW_CUSTOMER_ORDER);
			String New_Internet_CFS_STatus = getText(NEW_INTERNET_CFS_ORDER);
			String New_Int_Hw_CFS_Status = getText(NEW_INTERNET_HARDWARE_CFS_ORDER1);
			String New_Int_Product_Status = getText(NEW_INTERNET_PRODUCT_ORDER);
			String New_Wifi_CFS_Status = getText(NEW_WIFI_CFS_ORDER);

			if (isDisplayed(NEW_BILLING_SERVICE_ORDER) && isDisplayed(NEW_CDI_CFS_ORDER)
					&& isDisplayed(NEW_CUSTOMER_ORDER) && isDisplayed(NEW_INTERNET_CFS_ORDER)
					&& isDisplayed(NEW_INTERNET_HARDWARE_CFS_ORDER1) && isDisplayed(NEW_INTERNET_PRODUCT_ORDER)
					&& isDisplayed(NEW_WIFI_CFS_ORDER) && New_Billing_Service_Status.equalsIgnoreCase("Confirmed")
					&& New_CDI_CFS_Status.equalsIgnoreCase("Confirmed")
					&& New_Customer_Status.equalsIgnoreCase("Confirmed")
					&& New_Internet_CFS_STatus.equalsIgnoreCase("Confirmed")
					&& New_Int_Hw_CFS_Status.equalsIgnoreCase("Confirmed")
					&& New_Int_Product_Status.equalsIgnoreCase("Confirmed")
					&& New_Wifi_CFS_Status.equalsIgnoreCase("Confirmed")) {
				clecKOrderStatus = true;
			}

			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving verifyComOrderStatusConfirmed_98");
		} catch (Exception e) {
			log.error("Error in verifyComOrderStatusConfirmed_98:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return clecKOrderStatus;
	}

	public void navigateToWaitForAllLSCsTask() {
		try {
			log.debug("Entering navigateToWaitForAllLSCsTask");
			click(TASK_TAB);
			wait(2);
			click(WAIT_FOR_ALL_LSCS_LINK);
			wait(2);
			click(WAIT_FOR_LSC_FILTER);
			wait(2);
			click(TASK_WAITING_CHECKBOX);
			wait(2);
			click(WAIT_FOR_ALL_LSCS_TASK);
			wait(4);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(STEP_OVER_LINK);
			wait(4);
			switchToChildAlert();
			inputText(MARK_FINISH_DESCRIPTION, "Wait for all LSCs tasks Need to Step Over");
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(MARK_FINISH_DESCRIPTION_SEND_BTN);
			wait(4);
			log.debug("Leaving navigateToWaitForAllLSCsTask");
		} catch (Exception e) {
			log.error("Error in navigateToWaitForAllLSCsTask:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public String navigateToAddEmailImmediateOrder() {
		String immediateEmailAddress = "";
		try {
			click(ADD_EMAIL_IMMEDIATE_ORDER);
			wait(3);
			click(SERVICE_INFORMATION);
			wait(2);
			scrollDown();
			immediateEmailAddress = getText(EMAIL_ADDRESS);
			scrollUp();
			click(ORDER_PARAMETER_TAB);
			wait(2);
			click(INTEGRATION_REPORT);
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToPhoneLineImmediateOrder");
		} catch (Exception e) {
			log.error("Error in navigateToPhoneLineImmediateOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return immediateEmailAddress;
	}

	public String navigateToSuppressDeprovisioning() {
		String SuppressDeprovisioning = "";
		try {
			click(NEW_CUSTOMER_ORDER);
			wait(3);
			click(SERVICE_INFORMATION);
			wait(2);
			scrollDownToElement(FALSE_TEXT);
			SuppressDeprovisioning = getText(FALSE_TEXT);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToSuppressDeprovisioning");
		} catch (Exception e) {
			log.error("Error in navigateToSuppressDeprovisioning:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return SuppressDeprovisioning;
	}
	
	public void navigateToInternetQoSMapping() {
		try {
			log.debug("Entering navigateToInternetQoSMapping");
			String QoSMappingURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/ncobject.jsp?id=9145138850013389263";
			wait(1);
			navigate(QoSMappingURL);
			test.log(LogStatus.INFO, "QoS Mapping URL: ", "<a href=" + QoSMappingURL + " > " + QoSMappingURL + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			log.debug("Leaving navigateToInternetQoSMapping");
		} catch (Exception e) {
			log.error("Error in navigateToInternetQoSMapping:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToShippingTasks(String EnterSrlNbr, String HardwareSerialNbr) {
		try {
			log.debug("Entering navigateToShippingTasks");
			String ShippingTaskURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/common/uobject.jsp?tab=_Active+Shipping+Tasks&object=9137754422113767327";
			navigate(ShippingTaskURL);
			wait(1);
			test.log(LogStatus.INFO, "Shipping Tasks URL: ",
					"<a href=" + ShippingTaskURL + " > " + ShippingTaskURL + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(SHIPPING_TASKS_SELECT);
			wait(2);
			if (EnterSrlNbr.equals("Yes")) {
				click(SRLNBR_MOUSE_OVER);
				wait(2);
				click(SRL_NBR_DROPDOWN);
				wait(1);
				inputText(SRL_NBR_INPUT, HardwareSerialNbr);
				wait(2);
				click(SAVE_LINK);
				wait(2);
			}
			click(START_SHIPPING_LINK);
			wait(2);
			click(SHIPPING_TASKS_SELECT);
			wait(5);
			click(HARDWARE_MOUSE_OVER);
			wait(5);
			click(SHIPPING_HW_DROPDOWN);
			wait(5);
			click(SHIPPING_HW_VALUE);
			wait(5);
			click(TRACKING_NUMBER);
			wait(1);
			click(SHIPPING_HW_DROPDOWN);
			wait(1);
			inputText(TRACKING_INPUT_VALUE, "7869473");
			wait(2);
			click(SAVE_LINK);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(COMPLETE_SHIPPING);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToShippingTasks");
		} catch (Exception e) {
			log.error("Error in navigateToShippingTasks:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToOpenShippingTasks() {
		try {
			log.debug("Entering navigateToOpenShippingTasks");
			String ShippingTaskURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/common/uobject.jsp?tab=_Active+Shipping+Tasks&object=9137754422113767327";
			navigate(ShippingTaskURL);
			wait(1);
			test.log(LogStatus.INFO, "Shipping Tasks URL: ",
					"<a href=" + ShippingTaskURL + " > " + ShippingTaskURL + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToOpenShippingTasks");
		} catch (Exception e) {
			log.error("Error in navigateToOpenShippingTasks:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToActiveShippingMultipleTasks(String xpodValue1, String xpodValue2, String xpodValue3,
			String HardwareSerialNbr) {
		try {
			log.debug("Entering navigateToActiveShippingMultipleTasks");
			String ShippingTaskURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/common/uobject.jsp?tab=_Active+Shipping+Tasks&object=9137754422113767327";
			navigate(ShippingTaskURL);
			wait(1);
			test.log(LogStatus.INFO, "Shipping Tasks URL: ",
					"<a href=" + ShippingTaskURL + " > " + ShippingTaskURL + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(SHIPPING_TASKS_SELECT);
			wait(2);
			click(SRLNBR_MOUSE_OVER);
			wait(2);
			click(SRL_NBR_DROPDOWN);
			wait(1);
			inputText(SRL_NBR_INPUT, xpodValue1);
			wait(2);
			click(SAVE_LINK);
			wait(2);
			click(START_SHIPPING_LINK);
			wait(2);
			click(SHIPPING_TASKS_SELECT);
			wait(2);
			click(HARDWARE_MOUSE_OVER);
			wait(2);
			click(SHIPPING_HW_DROPDOWN);
			wait(2);
			click(SHIPPING_HW_VALUE);
			wait(2);
			click(TRACKING_NUMBER);
			wait(1);
			click(SHIPPING_HW_DROPDOWN);
			wait(1);
			inputText(TRACKING_INPUT_VALUE, "7869473");
			wait(2);
			click(SAVE_LINK);
			wait(2);
			click(COMPLETE_SHIPPING);
			wait(2);
			click(SHIPPING_TASKS_SELECT);
			wait(2);
			click(SRLNBR_MOUSE_OVER);
			wait(2);
			click(SRL_NBR_DROPDOWN);
			wait(1);
			inputText(SRL_NBR_INPUT, xpodValue2);
			wait(2);
			click(SAVE_LINK);
			wait(2);
			click(START_SHIPPING_LINK);
			wait(2);
			click(SHIPPING_TASKS_SELECT);
			wait(2);
			click(HARDWARE_MOUSE_OVER);
			wait(2);
			click(SHIPPING_HW_DROPDOWN);
			wait(2);
			click(SHIPPING_HW_VALUE);
			wait(2);
			click(TRACKING_NUMBER);
			wait(1);
			click(SHIPPING_HW_DROPDOWN);
			wait(1);
			inputText(TRACKING_INPUT_VALUE, "123456");
			wait(2);
			click(SAVE_LINK);
			wait(2);
			click(COMPLETE_SHIPPING);
			wait(2);
			click(SHIPPING_TASKS_SELECT);
			wait(2);
			click(SRLNBR_MOUSE_OVER);
			wait(2);
			click(SRL_NBR_DROPDOWN);
			wait(1);
			inputText(SRL_NBR_INPUT, xpodValue3);
			wait(2);
			click(SAVE_LINK);
			wait(2);
			click(START_SHIPPING_LINK);
			wait(2);
			click(SHIPPING_TASKS_SELECT);
			wait(2);
			click(HARDWARE_MOUSE_OVER);
			wait(2);
			click(SHIPPING_HW_DROPDOWN);
			wait(2);
			click(SHIPPING_HW_VALUE);
			wait(2);
			click(TRACKING_NUMBER);
			wait(1);
			click(SHIPPING_HW_DROPDOWN);
			wait(1);
			inputText(TRACKING_INPUT_VALUE, "987654");
			wait(2);
			click(SAVE_LINK);
			wait(2);
			click(COMPLETE_SHIPPING);
			wait(2);
			click(SHIPPING_TASKS_SELECT);
			wait(2);
			click(SRLNBR_MOUSE_OVER);
			wait(2);
			click(SRL_NBR_DROPDOWN);
			wait(1);
			inputText(SRL_NBR_INPUT, HardwareSerialNbr);
			wait(2);
			click(SAVE_LINK);
			wait(2);
			click(START_SHIPPING_LINK);
			wait(2);
			click(SHIPPING_TASKS_SELECT);
			wait(2);
			click(HARDWARE_MOUSE_OVER);
			wait(2);
			click(SHIPPING_HW_DROPDOWN);
			wait(2);
			click(SHIPPING_HW_VALUE);
			wait(2);
			click(TRACKING_NUMBER);
			wait(1);
			click(SHIPPING_HW_DROPDOWN);
			wait(1);
			inputText(TRACKING_INPUT_VALUE, "987654");
			wait(2);
			click(SAVE_LINK);
			wait(2);
			click(COMPLETE_SHIPPING);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToActiveShippingMultipleTasks");
		} catch (Exception e) {
			log.error("Error in navigateToActiveShippingMultipleTasks:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public String getTVHardwareType() {
		String tvHardwareType = "";
		try {
			log.debug("Entering getTVHardwareType");
			click(SERVICE_INFORMATION);
			wait(2);
			javascript.executeScript("window.scrollBy(0,600)", "");
			wait(2);
			tvHardwareType = getText(HARDWARE_TYPE);
			javascript.executeScript("window.scrollBy(0,-600)", "");
			log.debug("Leaving getTVHardwareType");
		} catch (Exception e) {
			log.error("Error in getTVHardwareType:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return tvHardwareType;
	}

	public String getMoxiTVHardwareType() {
		String tvHardwareType = "";
		try {
			log.debug("Entering getOldSerialNumber");
			click(SERVICE_INFORMATION);
			wait(2);
			tvHardwareType = getText(MOXI_HARDWARE_TYPE);
			wait(1);
			log.debug("Leaving getOldSerialNumber");
		} catch (Exception e) {
			log.error("Error in getOldSerialNumber:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return tvHardwareType;
	}

	public String getOfferingID() {
		String OfferingID = "";
		try {
			log.debug("Entering getOfferingID");
			click(SERVICE_INFORMATION);
			wait(2);
			javascript.executeScript("window.scrollBy(0,3000)", "");
			wait(3);
			OfferingID = getText(OFFERING_ID);
			javascript.executeScript("window.scrollBy(0,-3000)", "");
			log.debug("Leaving getOfferingID");
		} catch (Exception e) {
			log.error("Error in getOfferingID:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return OfferingID;
	}

	public void navigateToLSROrder(int orderLink) {
		try {
			log.debug("Entering navigateToLSROrder");
			switch (orderLink) {
			case 1:
				click(LSR_ORDER1);
				break;
			case 2:
				click(LSR_ORDER2);
				break;
			case 3:
				click(LSR_ORDER3);
				break;
			default:
				log.debug("No Link Provided as the Link Number not found on Page");
			}
			wait(1);
			click(TASK_TAB);
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToLSROrder");
		} catch (Exception e) {
			log.error("Error in navigateToLSROrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void swapImmediateConvrgdHardware(String SwapBtn, String swapConvrgdHardwareSrlNbr) {
		try {
			log.debug("Entering swapImmediateConvrgdHardware");
			click(IMMEDIATE_TAB_HARDWARE);
			wait(8);
			if (SwapBtn.equalsIgnoreCase("FirstSwap")) {
				click(IMMEDIATE_CONVRGD_SWAP1);
				wait(7);
			}
			if (SwapBtn.equalsIgnoreCase("SecondSwap")) {
				click(IMMEDIATE_CONVRGD_SWAP2);
				wait(2);
			}
			click(TECH_RADIO_BTN);
			wait(2);
			inputText(TECHNICIAN_ID, "46501");
			wait(2);
			click(VALIDATE_TECH_ID);
			wait(2);
			inputText(SWAP_SL_NO, swapConvrgdHardwareSrlNbr);
			wait(2);
			click(VALIDATE_SWAP_SLNO);
			wait(1);
			test.log(LogStatus.PASS, "Swap XB6 Converged Hardware", "Swapped XB6 Converged Hardware Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			inputText(IMMEDIATE_CHANGE_AUTHORIZED_TEXT, "Admin");
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(APPLY_CHANGES);
			wait(3);
			log.debug("Leaving swapImmediateConvrgdHardware");
		} catch (Exception e) {
			log.error("Error in swapImmediateConvrgdHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void VerifyComSomInstances() {
		try {
			log.debug("Entering VerifyComSomInstances");
			click(COM_INSTANCES);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(SOM_INSTANCES);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving VerifyComSomInstances");
		} catch (Exception e) {
			log.error("Error in VerifyComSomInstances:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public boolean portInImmediately_Yes() {
		boolean portImmediately = false;
		try {
			log.debug("Entering portInImmediately_Yes");
			if (isDisplayed(PORT_IN_IMMEDIATELY_YES)) {
				portImmediately = true;
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Port immediatley is displayed:" + portImmediately);
			log.debug("Leaving portInImmediately_Yes");
		} catch (Exception e) {
			portImmediately = true;
			log.error("Error in portInImmediately_Yes:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return portImmediately;
	}

	public void verifyGenerateStoreContractStatus() {
		try {
			log.debug("Entering verifyGenerateStoreContractStatus");
			scrollDownToElement(GENERATE_STORE_CONTRACT_STATUS);
			wait(1);
			if (getText(GENERATE_STORE_CONTRACT_STATUS).equals("Completed")) {
				test.log(LogStatus.PASS, "Generate Store Contract Status :", getText(GENERATE_STORE_CONTRACT_STATUS));
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			} else {
				test.log(LogStatus.FAIL, "Generate Store Contract Status :",
						"Expected status Completed but found" + getText(GENERATE_STORE_CONTRACT_STATUS));
				test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				Assert.fail();
			}
			log.debug("Leaving verifyGenerateStoreContractStatus");
		} catch (Exception e) {
			log.error("Error in verifyGenerateStoreContractStatus:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToWfInfoJspPage() {
		try {
			log.debug("Entering navigateToWfInfoJspPage");
			String WFinfoJspURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/common/uobject.jsp?tab=_Process+instances&object=9154626725813154243";
			navigate(WFinfoJspURL);
			wait(1);
			test.log(LogStatus.INFO, "WS Info JSP URL: ", "<a href=" + WFinfoJspURL + " > " + WFinfoJspURL + " </a>");
			click(DF_REQUEST_LINK);
			wait(2);
			click(OPERATIONS_DROPDOWN);
			wait(1);
			click(WF_INFO);
			wait(2);
			scrollDown600();
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToWfInfoJspPage");
		} catch (Exception e) {
			log.error("Error in navigateToWfInfoJspPage:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToConnectPuttyAndTimeShift(String Days) {
		log.debug("Entering navigateToConnectPuttyAndTimeShift");
		try {
			String result = "";
			String newDate = "";
			String host = Utility.getValueFromPropertyFile("putty_Server_Host");
			String user = Utility.getValueFromPropertyFile("putty_Login_User");
			String pasword = Utility.getValueFromPropertyFile("putty_Login_Password");

			PuttyConnector putty = new PuttyConnector(host, user, pasword);

			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			config.put("PreferredAuthentications", "password");
			JSch jsch = new JSch();
			JSch.setConfig(config);

			Session session = null;
			session = jsch.getSession(user, host, 22);
			session.setPassword(pasword);
			session.connect();
			test.log(LogStatus.PASS, "Connecting Putty :", "Successfully Connected to Putty");

			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp channelSftp = (ChannelSftp) channel;
			channelSftp.cd("/u02/netcracker/toms/u46_6800");
			log.debug("Navigate To Server path: " + "/u02/netcracker/toms/u46_6800");
			test.log(LogStatus.PASS, "Navigate To Server path :",
					"Successfully Navigated To Server path : /u02/netcracker/toms/u46_6800");

			Channel channel1 = session.openChannel("exec");
			((ChannelExec) channel1).setCommand("date");
			channel1.setInputStream(null);
			InputStream output = channel1.getInputStream();
			channel1.connect();
			result = CharStreams.toString(new InputStreamReader(output));
			channel1.disconnect();
			log.debug("Current date in server is :  " + result);
			test.log(LogStatus.PASS, "Enter the date Command and the Current Date is : " + result);

			DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
			Date date = (Date) formatter.parse(result);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			if (Days.equals("1")) {
				cal.add(Calendar.DATE, +1);
			}
			if (Days.equals("2")) {
				cal.add(Calendar.DATE, +2);
			}
			if (Days.equals("3")) {
				cal.add(Calendar.DATE, +3);
			}
			if (Days.equals("5")) {
				cal.add(Calendar.DATE, +5);
			}

			if (Days.equals("10")) {
				cal.add(Calendar.DATE, +10);
			}
			if (Days.equals("15")) {
				cal.add(Calendar.DATE, +15);
			}
			log.debug("Time Shifting No. Of days : " + Days);
			test.log(LogStatus.PASS, "Time Shifting No.Of days : " + Days + "Days");
			newDate = formatter.format(cal.getTime());
			Channel channel2 = session.openChannel("exec");
			((ChannelExec) channel2).setCommand("sudo date -s " + "\"" + newDate + "\"");
			channel2.setInputStream(null);
			InputStream output1 = channel2.getInputStream();
			channel2.connect();
			result = CharStreams.toString(new InputStreamReader(output1));
			channel2.disconnect();
			log.debug("After Time shifting, Now the Server Date : " + result);
			test.log(LogStatus.PASS, "After Time shifting, Now the date in Server : " + result);
			test.log(LogStatus.PASS, "Time Shifting status : ", "Successfully Time Shifted to " + Days + "Days");
			log.debug("Leaving navigateToConnectPuttyAndTimeShift");
		} catch (Exception e) {
			log.error("Error in navigateToConnectPuttyAndTimeShift:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void downloadFileFromServerToLocal() {
		log.debug("Entering navigateToConnectWinSCP");
		try {
			String host = Utility.getValueFromPropertyFile("WinSCP_Server_Host");
			String user = Utility.getValueFromPropertyFile("WinSCP_Login_User");
			String pasword = Utility.getValueFromPropertyFile("WinSCP_Login_Password");

			PuttyConnector putty = new PuttyConnector(host, user, pasword);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			config.put("PreferredAuthentications", "password");

			JSch jsch = new JSch();
			JSch.setConfig(config);

			Session session = null;
			session = jsch.getSession(user, host, 22);
			session.setPassword(pasword);
			session.connect();
			test.log(LogStatus.PASS, "Connecting WinSCP :", "Successfully Connected to WinSCP");

			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp channelSftp = (ChannelSftp) channel;
			
				channelSftp.get("/u02/netcracker/toms/u46_q843_8150/logs/integration_clust1.log",
						"C:/Users/vosn0721/Documents/new code backup/SHAW/Upload/integration_clust1.log");
				channel.disconnect();
			
			log.debug("Leaving navigateToConnectWinSCP");
		} catch (Exception e) {
			log.error("Error in navigateToConnectWinSCP:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToCheck360ViewAndInsideTables() {
		try {
			log.debug("Entering navigateToCheck360ViewAndInsideTables");
			click(VIEW_360_TAB);

			int count = 1;
			while (isDisplayed(HARDWARE_TABLE) && count <= 2) {
				refreshPage();
				wait(60);
				count++;
			}

			wait(10);
			if (isElementPresent(VIEW_360_TAB)) {

				test.log(LogStatus.PASS, "360 View Table:", "360 View Table is Present");
			} else {
				test.log(LogStatus.FAIL, "360 View Table:", "360 View Table should not Present");
			}

			String[] arrayPageLocators = { "Customer Orders", "Tasks", "Manual Task Interventions",
					"Manual Object Interventions", "Data Fixes", "Hardware", "Phone Numbers", "Emails" };

			for (String tableName : arrayPageLocators) {
				if (isDynamicElementPresent("label", tableName)) {
					test.log(LogStatus.PASS, tableName + " Table is Present");
				} else {
					test.log(LogStatus.FAIL, tableName + " Table is not Present");
				}

			}
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToCheck360ViewAndInsideTables");
		} catch (Exception e) {
			log.error("Error in navigateToCheck360ViewAndInsideTables:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToCheckCustomerOrder() {
		try {
			log.debug("Entering navigateToCheckCustomerOrderTable");
			// click(CUSTOMER_ORDER_TAB);
			wait(2);
			if (isElementPresent(CUSTOMER_ORDER_TAB)) {
				test.log(LogStatus.PASS, "Cutomer Orders:", "Order Details are Present");
			} else {
				test.log(LogStatus.FAIL, "Cutomer Orders:", "Order Details are not Present");
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToCheckCustomerOrderTable");
		} catch (Exception e) {
			log.error("Error in navigateToCheckCustomerOrderTable:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToHardware() {
		try {
			log.debug("Entering navigateToHardwareTable");
			click(HARDWARE_TAB);
			wait(2);
			if (isElementPresent(HARDWARE_TAB)) {
				test.log(LogStatus.PASS, "Hardware:", "Hardware is Present");
			} else {
				test.log(LogStatus.FAIL, "Hardware:", "Hardware is not Present");
			}

			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToHardwareTable");
		} catch (Exception e) {
			log.error("Error in navigateToHardwareTable:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getCPEexpectedStatus() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179250\"]/div/table"));
			if (isElementPresent(CPE_EXPECTED_STATUS_TEXT4)) {
				scrollDownToElement(CPE_EXPECTED_STATUS_TEXT4);
			} else {
				scrollDownToElement(CPE_EXPECTED_STATUS_TEXT3);
			}

			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179250\"]/div/table/tbody/tr/td[11]"));

			for (int i = 0; i < rows.size(); i++) {

				if (i == rows.size() - 1) {
					if ((rows.get(i).getText()).equals("Not Present")) {
						test.log(LogStatus.PASS, "CPE Expected status is :", "Not Present");
						test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

					} else {
						test.log(LogStatus.FAIL, "CPE Expected Status Not Present not found" + rows.get(i).getText());
						test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
						Assert.fail();
					}

				} else {
					if ((rows.get(i).getText()).equals("Active")) {
						test.log(LogStatus.PASS, "CPE Expected status is :", "Active");
						test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

					} else if ((rows.get(i).getText()).equals("Reserved")) {
						test.log(LogStatus.PASS, "CPE Expected status is :", "Reserved");
						test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

					}

					else if ((rows.get(i).getText()).equals("Unreserved")) {
						test.log(LogStatus.PASS, "CPE Expected status is :", "Unreserved");
						test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

					} else {
						test.log(LogStatus.FAIL, "CPE Expected Status  not found" + rows.get(i).getText());
						test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
						Assert.fail();
					}

				}
			}

		} catch (Exception e) {
			log.error("Error in navigateTogetCPEexpectedStatus:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getCPEactualStatus() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179250\"]/div/table"));
			if (isElementPresent(CPE_ACTUAL_STATUS_TEXT4)) {
				scrollDownToElement(CPE_ACTUAL_STATUS_TEXT4);
			} else {
				scrollDownToElement(CPE_ACTUAL_STATUS_TEXT3);
			}
			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179250\"]/div/table/tbody/tr/td[12]"));

			for (int i = 0; i < rows.size(); i++) {

				if (i == rows.size() - 1) {
					if ((rows.get(i).getText()).equals("Not Present")) {
						test.log(LogStatus.PASS, "CPE Actual status is :", "Not Present");
						test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

					} else {
						test.log(LogStatus.FAIL, "CPE Actual Status not found" + rows.get(i).getText());
						test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
						Assert.fail();
					}

				} else {
					if ((rows.get(i).getText()).equals("Active")) {
						test.log(LogStatus.PASS, "CPE Actual status is :", "Active");
						test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

					} else if ((rows.get(i).getText()).equals("Reserved")) {
						test.log(LogStatus.PASS, "CPE Actual status is :", "Reserved");
						test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

					} else {
						test.log(LogStatus.FAIL, "CPE Actual Status  not found" + rows.get(i).getText());
						test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
						Assert.fail();
					}

				}
			}

		} catch (Exception e) {
			log.error("Error in navigateTogetCPEactualStatus:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getIsCPEstatusConsistent() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179250\"]/div/table"));
			if (isElementPresent(IS_CPE_STATUS_CONSISTENT_TEXT4)) {
				scrollDownToElement(IS_CPE_STATUS_CONSISTENT_TEXT4);
			} else {
				scrollDownToElement(IS_CPE_STATUS_CONSISTENT_TEXT3);
			}

			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179250\"]/div/table/tbody/tr/td[13]"));

			for (int i = 0; i < rows.size(); i++) {

				if ((rows.get(i).getText()).equals("Yes")) {
					test.log(LogStatus.PASS, "Is CPE Status Consistent is :", "Yes");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else if ((rows.get(i).getText()).equals("No")) {
					test.log(LogStatus.PASS, "Is CPE Status Consistent is :", "No");
					test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else {
					test.log(LogStatus.FAIL, "Is CPE Status Consistent is not found" + rows.get(i).getText());
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}
			}

		} catch (Exception e) {
			log.error("Error in navigateTogetIsCPEstatusConsistent:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getHPEPMexpectedStatus() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179250\"]/div/table"));
			scrollDownToElement(HPE_PM_EXPECTED_STATUS_COL);
			if (isElementPresent(HPE_PM_EXPECTED_STATUS_TEXT4)) {
				scrollDownToElement(HPE_PM_EXPECTED_STATUS_TEXT4);
			} else {
				scrollDownToElement(HPE_PM_EXPECTED_STATUS_TEXT4);
			}

			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179250\"]/div/table/tbody/tr/td[14]"));

			for (int i = 0; i < rows.size(); i++) {

				if (i == rows.size() - 1) {
					if ((rows.get(i).getText()).equals("Not Present")) {
						test.log(LogStatus.PASS, "HPE PM Expected status is :", "Not Present");
						test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

					} else {
						test.log(LogStatus.FAIL,
								"HPE PM Expected status Not Present not found" + rows.get(i).getText());
						test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
						Assert.fail();
					}

				} else {
					if ((rows.get(i).getText()).equals("Active")) {
						test.log(LogStatus.PASS, "HPE PM Expected status is :", "Active");
						test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

					} else if ((rows.get(i).getText()).equals("Not Present")) {
						test.log(LogStatus.PASS, "HPE PM Expected status is :", "Not Present");
						test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

					} else {
						test.log(LogStatus.FAIL, "HPE PM Expected status Active not found" + rows.get(i).getText());
						test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
						Assert.fail();
					}

				}
			}

		} catch (Exception e) {
			log.error("Error in navigateTogetHPEPMexpectedStatus:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getHPEPMactualStatus() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179250\"]/div/table"));
			scrollDownToElement(HPE_PM_ACTUAL_STATUS_COL);
			if (isElementPresent(HPE_PM_ACTUAL_STATUS_TEXT4)) {
				scrollDownToElement(HPE_PM_ACTUAL_STATUS_TEXT4);
			} else {
				scrollDownToElement(HPE_PM_ACTUAL_STATUS_TEXT3);
			}
			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179250\"]/div/table/tbody/tr/td[15]"));

			for (int i = 0; i < rows.size(); i++) {

				if ((rows.get(i).getText()).equals("Active")) {
					test.log(LogStatus.PASS, "HPE PM Actual status is :", "Active");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else if ((rows.get(i).getText()).equals("Not Present")) {
					test.log(LogStatus.PASS, "HPE PM Actual status is :", "Not Present");
					test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else {
					test.log(LogStatus.FAIL, "HPE PM Actual status Active not found" + rows.get(i).getText());
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}

			}

		} catch (Exception e) {
			log.error("Error in navigateTogetHPEPMactualStatus:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getIsHPEPMstatusConsistent() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179250\"]/div/table"));
			WebElement scrollRight = getWebElement(IS_HPE_PM_STATUS_CONSISTENT_COL, "");
			javascript.executeScript("", "scrollRight");
			if (isElementPresent(IS_HPE_PM_STATUS_CONSISTENT_TEXT4)) {
				scrollDownToElement(IS_HPE_PM_STATUS_CONSISTENT_TEXT4);
			} else {
				scrollDownToElement(IS_HPE_PM_STATUS_CONSISTENT_TEXT3);
			}
			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179250\"]/div/table/tbody/tr/td[16]"));

			for (int i = 0; i < rows.size(); i++) {

				if ((rows.get(i).getText()).equals("Yes")) {
					test.log(LogStatus.PASS, "Is HPE PM Status Consistent is :", "Yes");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else if ((rows.get(i).getText()).equals("No")) {
					test.log(LogStatus.PASS, "Is HPE PM Status Consistent is :", "No");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else {
					test.log(LogStatus.FAIL, "Is HPE PM Status Consistent is not found" + rows.get(i).getText());
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}

			}

		} catch (Exception e) {
			log.error("Error in navigateTogetIsHPEPMstatusConsistent:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getExpectedStatusInRPIL() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179250\"]/div/table"));
			if (isElementPresent(EXPECTED_STATUS_IN_RPIL_TEXT4)) {
				scrollDownToElement(EXPECTED_STATUS_IN_RPIL_TEXT4);
			} else {
				scrollDownToElement(EXPECTED_STATUS_IN_RPIL_TEXT3);
			}
			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179250\"]/div/table/tbody/tr/td[17]"));

			for (int i = 0; i < rows.size(); i++) {

				if ((rows.get(i).getText()).equals("Active")) {
					test.log(LogStatus.PASS, "Expected Status in RPIL is :", "Active");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else if ((rows.get(i).getText()).equals("Not Present")) {
					test.log(LogStatus.PASS, "Expected Status in RPIL is :", "Not Present");
					test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else {
					test.log(LogStatus.FAIL, "Expected Status in RPIL is not found" + rows.get(i).getText());
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}

			}

		} catch (Exception e) {
			log.error("Error in navigateTogetExpectedStatusInRPIL:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getActualStatusInRPIL() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179250\"]/div/table"));
			WebElement scrollRight = getWebElement(ACTUAL_STATUS_IN_RPIL_COL, "");
			javascript.executeScript("", "scrollRight");
			if (isElementPresent(ACTUAL_STATUS_IN_RPIL_TEXT4)) {
				scrollDownToElement(ACTUAL_STATUS_IN_RPIL_TEXT4);
			} else {
				scrollDownToElement(ACTUAL_STATUS_IN_RPIL_TEXT3);
			}
			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179250\"]/div/table/tbody/tr/td[18]"));

			for (int i = 0; i < rows.size(); i++) {

				if ((rows.get(i).getText()).equals("Active")) {
					test.log(LogStatus.PASS, "Actual Status in RPIL is :", "Active");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else if ((rows.get(i).getText()).equals("Not Present")) {
					test.log(LogStatus.PASS, "Actual Status in RPIL is :", "Not Present");
					test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else {
					test.log(LogStatus.FAIL, "Expected Status in RPIL is not found" + rows.get(i).getText());
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}

			}

		} catch (Exception e) {
			log.error("Error in navigateTogetActualStatusInRPIL:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getIsRPILstatusConsistent() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179250\"]/div/table"));
			WebElement scrollRight = getWebElement(IS_RPIL_STATUS_CONSISTENT_COL, "");
			javascript.executeScript("", "scrollRight");
			if (isElementPresent(IS_RPIL_STATUS_CONSISTENT_TEXT4)) {
				scrollDownToElement(IS_RPIL_STATUS_CONSISTENT_TEXT4);
			} else {
				scrollDownToElement(IS_RPIL_STATUS_CONSISTENT_TEXT3);
			}
			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179250\"]/div/table/tbody/tr/td[19]"));

			for (int i = 0; i < rows.size(); i++) {

				if ((rows.get(i).getText()).equals("Yes")) {
					test.log(LogStatus.PASS, "Is RPIL Status Consistent is :", "Yes");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else if ((rows.get(i).getText()).equals("No")) {
					test.log(LogStatus.PASS, "Is RPIL Status Consistent is :", "No");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else {
					test.log(LogStatus.FAIL, "Is RPIL Status Consistent is not found" + rows.get(i).getText());
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}

			}

		} catch (Exception e) {
			log.error("Error in navigateTogetIsRPILstatusConsistent:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getBRMexpectedStatus() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179250\"]/div/table"));
			WebElement scrollRight = getWebElement(BRM_EXPECTED_STATUS_COL, "");
			javascript.executeScript("", "scrollRight");
			if (isElementPresent(BRM_EXPECTED_STATUS_TEXT4)) {
				scrollDownToElement(BRM_EXPECTED_STATUS_TEXT4);
			} else {
				scrollDownToElement(BRM_EXPECTED_STATUS_TEXT3);
			}
			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179250\"]/div/table/tbody/tr/td[20]"));

			for (int i = 0; i < rows.size(); i++) {

				if ((rows.get(i).getText()).equals("Active")) {
					test.log(LogStatus.PASS, "BRM Expected Status is :", "Active");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else if ((rows.get(i).getText()).equals("Not Present")) {
					test.log(LogStatus.PASS, "BRM Expected Status is :", "Not Present");
					test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else if ((rows.get(i).getText()).equals("Not Present/Disconnected")) {
					test.log(LogStatus.PASS, "BRM Expected Status is :", "Not Present/Disconnected");
					test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else {
					test.log(LogStatus.FAIL, "BRM Expected Status is not found" + rows.get(i).getText());
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}

			}

		} catch (Exception e) {
			log.error("Error in navigateTogetBRMexpectedStatus:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getBRMactualStatus() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179250\"]/div/table"));
			WebElement scrollRight = getWebElement(BRM_ACTUAL_STATUS_COL, "");
			javascript.executeScript("", "scrollRight");
			if (isElementPresent(BRM_ACTUAL_STATUS_TEXT4)) {
				scrollDownToElement(BRM_ACTUAL_STATUS_TEXT4);
			} else {
				scrollDownToElement(BRM_ACTUAL_STATUS_TEXT3);
			}
			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179250\"]/div/table/tbody/tr/td[21]"));

			for (int i = 0; i < rows.size(); i++) {

				if ((rows.get(i).getText()).equals("Active")) {
					test.log(LogStatus.PASS, "BRM Actual Status is :", "Active");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else if ((rows.get(i).getText()).equals("Not Present")) {
					test.log(LogStatus.PASS, "BRM Actual Status is :", "Not Present");
					test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else if ((rows.get(i).getText()).equals("Not Present/Disconnected")) {
					test.log(LogStatus.PASS, "BRM Actual Status is :", "Not Present/Disconnected");
					test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else {
					test.log(LogStatus.FAIL, "BRM Actual Status is not found" + rows.get(i).getText());
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}

			}

		} catch (Exception e) {
			log.error("Error in navigateTogetBRMactualStatus:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getIsBRMstatusConsistent() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179250\"]/div/table"));
			WebElement scrollRight = getWebElement(IS_BRM_STATUS_CONSISTENT_COL, "");
			javascript.executeScript("", "scrollRight");
			if (isElementPresent(IS_BRM_STATUS_CONSISTENT_TEXT4)) {
				scrollDownToElement(IS_BRM_STATUS_CONSISTENT_TEXT4);
			} else {
				scrollDownToElement(IS_BRM_STATUS_CONSISTENT_TEXT3);
			}
			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179250\"]/div/table/tbody/tr/td[22]"));

			for (int i = 0; i < rows.size(); i++) {

				if ((rows.get(i).getText()).equals("Yes")) {
					test.log(LogStatus.PASS, "Is BRM Status Consistent is :", "Yes");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else if ((rows.get(i).getText()).equals("No")) {
					test.log(LogStatus.PASS, "Is BRM Status Consistent is :", "No");
					test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else {
					test.log(LogStatus.FAIL, "Is BRM Status Consistent is not found" + rows.get(i).getText());
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}

			}

		} catch (Exception e) {
			log.error("Error in navigateTogetIsBRMstatusConsistent:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToPhoneNumbersTable() {
		try {
			log.debug("Entering navigateToPhoneNumbersTable");
			click(PHONE_NUMBERS_TABLE);
			if (isElementPresent(PHONE_NUMBERS_TABLE)) {
				test.log(LogStatus.PASS, "Phone Numbers:", "Phone Numbers is Present");
			} else {
				test.log(LogStatus.FAIL, "Phone Numbers:", "Phone Numbers is not Present");
			}

			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToPhoneNumbersTable");
		} catch (Exception e) {
			log.error("Error in navigateToPhoneNumbersTable:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getPhoneTNIexpectedStatus() {
		try {
			// scrollDownToElement(PHONE_NUMBERS_TABLE);
			// wait(2);
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179288\"]/div/table"));
			WebElement scrollRight = getWebElement(PHONE_TNI_EXPECTED_STATUS_COL, "");
			javascript.executeScript("", "scrollRight");
			if (isElementPresent(PHONE_TNI_EXPECTED_STATUS_TEXT2)) {
				scrollDownToElement(PHONE_TNI_EXPECTED_STATUS_TEXT2);
			} else {
				scrollDownToElement(PHONE_TNI_EXPECTED_STATUS_TEXT1);
			}
			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179288\"]/div/table/tbody/tr/td[8]"));

			for (int i = 0; i < rows.size(); i++) {

				if ((rows.get(i).getText()).equals("Active")) {
					test.log(LogStatus.PASS, "TNI Expected status is :", "Active");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else if ((rows.get(i).getText()).equals("Ordered")) {
					test.log(LogStatus.PASS, "TNI Expected status is :", "Ordered");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				}

				else if ((rows.get(i).getText()).equals("Unreserved")) {
					test.log(LogStatus.PASS, "TNI Expected status is :", "Ordered");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else {
					test.log(LogStatus.FAIL, "TNI Expected status not found" + rows.get(i).getText());
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}
			}

		} catch (Exception e) {
			log.error("Error in navigateTogetTNIexpectedStatus:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getPhoneTNIactualStatus() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179288\"]/div/table"));
			WebElement scrollRight = getWebElement(PHONE_TNI_ACTUAL_STATUS_COL, "");
			javascript.executeScript("", "scrollRight");
			if (isElementPresent(PHONE_TNI_ACTUAL_STATUS_TEXT2)) {
				scrollDownToElement(PHONE_TNI_ACTUAL_STATUS_TEXT2);
			} else {
				scrollDownToElement(PHONE_TNI_ACTUAL_STATUS_TEXT1);
			}
			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179288\"]/div/table/tbody/tr/td[9]"));

			for (int i = 0; i < rows.size(); i++) {

				if ((rows.get(i).getText()).equals("Active")) {
					test.log(LogStatus.PASS, "TNI Actual status is :", "Active");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else if ((rows.get(i).getText()).equals("Ordered")) {
					test.log(LogStatus.PASS, "TNI Actual status is :", "Ordered");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else if ((rows.get(i).getText()).equals("Unreserved")) {
					test.log(LogStatus.PASS, "TNI Actual status is :", "Ordered");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else {
					test.log(LogStatus.FAIL, "TNI Actual status not found" + rows.get(i).getText());
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}
			}

		} catch (Exception e) {
			log.error("Error in navigateTogetTNIactualStatus:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getPhoneHPEPMexpectedStatus() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179288\"]/div/table"));
			WebElement scrollRight = getWebElement(PHONE_HPE_PM_EXPECTED_STATUS_COL, "");
			javascript.executeScript("", "scrollRight");
			if (isElementPresent(PHONE_HPE_PM_EXPECTED_STATUS_TEXT2)) {
				scrollDownToElement(PHONE_HPE_PM_EXPECTED_STATUS_TEXT2);
			} else {
				scrollDownToElement(PHONE_HPE_PM_EXPECTED_STATUS_TEXT1);
			}
			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179288\"]/div/table/tbody/tr/td[11]"));

			for (int i = 0; i < rows.size(); i++) {

				if ((rows.get(i).getText()).equals("Active")) {
					test.log(LogStatus.PASS, "HPE PM Expected status is :", "Active");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else if ((rows.get(i).getText()).equals("Not Present/Disconnected")) {
					test.log(LogStatus.PASS, "HPE PM Expected status is :", "Not Present/Disconnected");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else {
					test.log(LogStatus.FAIL, "HPE PM Expected status is not found" + rows.get(i).getText());
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}
			}

		} catch (Exception e) {
			log.error("Error in navigateTogetPhoneHPEPMexpectedStatus:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getPhoneHPEPMactualStatus() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179288\"]/div/table"));
			// WebElement scrollRight = getWebElement(PHONE_HPE_PM_ACTUAL_STATUS_COL, "");
			// javascript.executeScript("", "scrollRight");
			if (isElementPresent(PHONE_HPE_PM_ACTUAL_STATUS_TEXT2)) {
				scrollDownToElement(PHONE_HPE_PM_ACTUAL_STATUS_TEXT2);
			} else {
				scrollDownToElement(PHONE_HPE_PM_ACTUAL_STATUS_TEXT1);
			}
			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179288\"]/div/table/tbody/tr/td[12]"));

			for (int i = 0; i < rows.size(); i++) {

				if ((rows.get(i).getText()).equals("Active")) {
					test.log(LogStatus.PASS, "HPE PM Actual status is :", "Active");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else if ((rows.get(i).getText()).equals("Not Present/Disconnected")) {
					test.log(LogStatus.PASS, "HPE PM Actual status is :", "Not Present/Disconnected");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else {
					test.log(LogStatus.FAIL, "HPE PM Actual status is not found" + rows.get(i).getText());
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}
			}

		} catch (Exception e) {
			log.error("Error in navigateTogetPhoneHPEPMactualStatus:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getPhoneIsHPEPMstatusConsistent() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179288\"]/div/table"));
			WebElement scrollRight = getWebElement(PHONE_IS_HPE_PM_STATUS_COL, "");
			javascript.executeScript("", "scrollRight");
			if (isElementPresent(PHONE_IS_HPE_PM_STATUS_TEXT2)) {
				scrollDownToElement(PHONE_IS_HPE_PM_STATUS_TEXT2);
			} else {
				scrollDownToElement(PHONE_IS_HPE_PM_STATUS_TEXT1);
			}
			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179288\"]/div/table/tbody/tr/td[13]"));

			for (int i = 0; i < rows.size(); i++) {

				if ((rows.get(i).getText()).equals("Yes")) {
					test.log(LogStatus.PASS, "Is HPE PM Status Consistent is :", "Yes");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else if ((rows.get(i).getText()).equals("No")) {
					test.log(LogStatus.PASS, "Is HPE PM Status Consistent is :", "No");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else {
					test.log(LogStatus.FAIL, "Is HPE PM Status Consistent is not found" + rows.get(i).getText());
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}
			}

		} catch (Exception e) {
			log.error("Error in navigateTogetPhoneIsHPEPMstatusConsistent:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getPhoneBRMexpectedStatus() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179288\"]/div/table"));
			WebElement scrollRight = getWebElement(PHONE_BRM_EXPECTED_STATUS_COL, "");
			javascript.executeScript("", "scrollRight");
			if (isElementPresent(PHONE_BRM_EXPECTED_STATUS_TEXT2)) {
				scrollDownToElement(PHONE_BRM_EXPECTED_STATUS_TEXT2);
			} else {
				scrollDownToElement(PHONE_BRM_EXPECTED_STATUS_TEXT1);
			}
			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179288\"]/div/table/tbody/tr/td[14]"));

			for (int i = 0; i < rows.size(); i++) {

				if ((rows.get(i).getText()).equals("Active")) {
					test.log(LogStatus.PASS, "BRM Expected status is :", "Active");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else if ((rows.get(i).getText()).equals("Not Present/Disconnected")) {
					test.log(LogStatus.PASS, "BRM Expected status is :", "Not Present/Disconnected");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else {
					test.log(LogStatus.FAIL, "BRM Expected status is not found" + rows.get(i).getText());
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}
			}

		} catch (Exception e) {
			log.error("Error in navigateTogetPhoneBRMexpectedStatus:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getPhoneBRMactualStatus() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179288\"]/div/table"));
			// WebElement scrollRight = getWebElement(PHONE_BRM_ACTUAL_STATUS_COL, "");
			// javascript.executeScript("", "scrollRight");
			if (isElementPresent(PHONE_BRM_ACTUAL_STATUS_TEXT2)) {
				scrollDownToElement(PHONE_BRM_ACTUAL_STATUS_TEXT2);
			} else {
				scrollDownToElement(PHONE_BRM_ACTUAL_STATUS_TEXT1);
			}
			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179288\"]/div/table/tbody/tr/td[15]"));

			for (int i = 0; i < rows.size(); i++) {

				if ((rows.get(i).getText()).equals("Active")) {
					test.log(LogStatus.PASS, "BRM Actual status is :", "Active");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else if ((rows.get(i).getText()).equals("Not Present/Disconnected")) {
					test.log(LogStatus.PASS, "BRM Actual status is :", "Not Present/Disconnected");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else {
					test.log(LogStatus.FAIL, "BRM Actual status is not found" + rows.get(i).getText());
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}
			}

		} catch (Exception e) {
			log.error("Error in navigateTogetPhoneBRMactualStatus:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getPhoneIsBRMstatusConsistent() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179288\"]/div/table"));
			WebElement scrollRight = getWebElement(PHONE_IS_BRM_STATUS_CONSISTENT_COL, "");
			javascript.executeScript("", "scrollRight");
			if (isElementPresent(PHONE_IS_BRM_STATUS_CONSISTENT_TEXT2)) {
				scrollDownToElement(PHONE_IS_BRM_STATUS_CONSISTENT_TEXT2);
			} else {
				scrollDownToElement(PHONE_IS_BRM_STATUS_CONSISTENT_TEXT1);
			}
			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179288\"]/div/table/tbody/tr/td[16]"));

			for (int i = 0; i < rows.size(); i++) {

				if ((rows.get(i).getText()).equals("Yes")) {
					test.log(LogStatus.PASS, "Is BRM Status Consistent is :", "Yes");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else {
					test.log(LogStatus.FAIL, "Is BRM Status Consistent is not found" + rows.get(i).getText());
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}
			}

		} catch (Exception e) {
			log.error("Error in navigateTogetPhoneIsBRMstatusConsistent:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToEmailsTable() {
		try {
			log.debug("Entering navigateToEmailsTable");
			click(EMAILS_TABLE);
			if (isElementPresent(EMAILS_TABLE)) {
				test.log(LogStatus.PASS, "Emails:", "Emails is Present");
			} else {
				test.log(LogStatus.FAIL, "Emails:", "Emails is not Present");
			}

			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToEmailsTable");
		} catch (Exception e) {
			log.error("Error in navigateToEmailsTable:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getEmailHPEPMexpectedStatus() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179278\"]/div/table"));
			WebElement scrollRight = getWebElement(EMAIL_HPE_PM_EXPECTED_STATUS_COL, "");
			javascript.executeScript("", "scrollRight");
			scrollDownToElement(EMAIL_HPE_PM_EXPECTED_STATUS_TEXT);
			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179278\"]/div/table/tbody/tr/td[7]"));

			for (int i = 0; i < rows.size(); i++) {

				if ((rows.get(i).getText()).equals("Active")) {
					test.log(LogStatus.PASS, "HPE PM Expected status is :", "Active");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else {
					test.log(LogStatus.FAIL, "HPE PM Expected status is not found" + rows.get(i).getText());
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}
			}

		} catch (Exception e) {
			log.error("Error in navigateTogetEmailHPEPMexpectedStatus:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getEmailHPEPMactualStatus() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179278\"]/div/table"));
			WebElement scrollRight = getWebElement(EMAIL_HPE_PM_ACTUAL_STATUS_COL, "");
			javascript.executeScript("", "scrollRight");
			scrollDownToElement(EMAIL_HPE_PM_ACTUAL_STATUS_TEXT);
			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179278\"]/div/table/tbody/tr/td[8]"));

			for (int i = 0; i < rows.size(); i++) {

				if ((rows.get(i).getText()).equals("Active")) {
					test.log(LogStatus.PASS, "HPE PM Actual status is :", "Active");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else {
					test.log(LogStatus.FAIL, "HPE PM Actual status is not found" + rows.get(i).getText());
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}
			}

		} catch (Exception e) {
			log.error("Error in navigateTogetEmailHPEPMactualStatus:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void getEmailIsHPEPMstatusConsistent() {
		try {
			WebElement table = driver.findElement(By.xpath("//*[@id=\"9154765291013179278\"]/div/table"));
			WebElement scrollRight = getWebElement(EMAIL_IS_HPE_PM_STATUS_CONSISTENT_COL, "");
			javascript.executeScript("", "scrollRight");
			scrollDownToElement(EMAIL_IS_HPE_PM_STATUS_CONSISTENT_TEXT);
			List<WebElement> rows = table
					.findElements(By.xpath("//*[@id=\"9154765291013179278\"]/div/table/tbody/tr/td[9]"));

			for (int i = 0; i < rows.size(); i++) {

				if ((rows.get(i).getText()).equals("Yes")) {
					test.log(LogStatus.PASS, "Is HPE PM Status Consistent is :", "Yes");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

				} else {
					test.log(LogStatus.FAIL, "Is HPE PM Status Consistent is not found" + rows.get(i).getText());
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					Assert.fail();
				}
			}

		} catch (Exception e) {
			log.error("Error in navigateTogetEmailIsHPEPMstatusConsistent:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToCheckWiringStatusinPhoneCFS(String record) {
		try {
			log.debug("Entering navigateToCheckWiringStatusinPhoneCFS");
			wait(1);
			if (record.equals("One")) {
				click(NEW_PHONELINE_CFS_ORDER1);
				wait(2);
			}
			if (record.equals("Two")) {
				click(NEW_PHONELINE_CFS_ORDER2);
				wait(2);
			}
			click(SERVICE_INFORMATION_TAB);
			wait(1);
			scrollDown600();
			wait(1);
			if (getText(WIRING_STATUS).equalsIgnoreCase("Port Switched")) {
				test.log(LogStatus.PASS, "Wiring Status in New PhoneLine CFS Order is :", "Port Switched");
			} else if (getText(WIRING_STATUS).equalsIgnoreCase("Wired to Shaw")) {
				test.log(LogStatus.PASS, "Wiring Status in New PhoneLine CFS Order is :", "Wired to Shaw");
			} else if (getText(WIRING_STATUS).equalsIgnoreCase("Dual Jacked")) {
				test.log(LogStatus.PASS, "Wiring Status in New PhoneLine CFS Order is :", "Dual Jacked");
			} else if (getText(WIRING_STATUS).equalsIgnoreCase("Wired to Previous Provider")) {
				test.log(LogStatus.PASS, "Wiring Status in New PhoneLine CFS Order is :", "Wired to Previous Provider");
			}
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToCheckWiringStatusinPhoneCFS");
		} catch (Exception e) {
			log.error("Error in navigateToCheckWiringStatusinPhoneCFS:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToDualJackedWiredtoPreviousProviderReport() {
		try {
			log.debug("Entering navigateToDualJackedWiredtoPreviousProviderReport");
			String ReportURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/report/reports.jsp?report=9154928370213245799&object=101&parent=9154928370213245799";
			navigate(ReportURL);
			wait(1);
			click(DUAL_JACK_REPORT);
			wait(1);
			test.log(LogStatus.INFO, "Dual Jack/Wired To Previous Provider Report URL: ",
					"<a href=" + ReportURL + " > " + ReportURL + " </a>");
			if (isElementPresent(PON)) {
				test.log(LogStatus.PASS, "Validation For PON: ", "PON Parameter is Present");
			} else {
				test.log(LogStatus.FAIL, "Validation For PON: ", "PON Parameter should not Present");
			}
			if (isElementPresent(TASK_VERSION)) {
				test.log(LogStatus.PASS, "Validation For Task Version: ", "Task Version parameter is Present");
			} else {
				test.log(LogStatus.FAIL, "Validation For Task Version: ", "Task Version parameter should not Present");
			}
			if (isElementPresent(CN_TYPE)) {
				test.log(LogStatus.PASS, "Validation For CNType:", "CNType parameter is Present");
			} else {
				test.log(LogStatus.FAIL, "Validation For CNType:", "CNType parameter should not Present");
			}
			if (isElementPresent(DUE_DATE)) {
				test.log(LogStatus.PASS, "Validation for Due Date:", "Due Date parameter is Present");
			} else {
				test.log(LogStatus.FAIL, "Validation for Due Date:", "Due Date parameter should not Present");
			}
			if (isElementPresent(TN)) {
				test.log(LogStatus.PASS, "Validation for TN:", "TN parameter is Present");
			} else {
				test.log(LogStatus.FAIL, "Validation for TN:", "TN parameter should not Present");
			}
			if (isElementPresent(ONSP)) {
				test.log(LogStatus.PASS, "Validation for ONSP:", "ONSP parameter is Present");
			} else {
				test.log(LogStatus.FAIL, "Validation for ONSP:", "ONSP parameter should not Present");
			}
			if (isElementPresent(ACCOUNT_ID)) {
				test.log(LogStatus.PASS, "Validation for Account ID:", "Account ID parameter is Present");
			} else {
				test.log(LogStatus.FAIL, "Validation for Account ID:", "Account ID parameter should not Present");
			}
			if (isElementPresent(WORKORDER_NUMBER)) {
				test.log(LogStatus.PASS, "Validation for Work Order Number:", "Work Order Number parameter is Present");
			} else {
				test.log(LogStatus.FAIL, "Validation for Work Order Number:",
						"Work Order Number parameter should not Present");
			}
			if (isElementPresent(NPAC_ACTIVATE_COMPLETED)) {
				test.log(LogStatus.PASS, "Validation for NPAC Activate Completed:",
						"NPAC Activate Completed parameter is Present");
			} else {
				test.log(LogStatus.FAIL, "Validation for NPAC Activate Completed:",
						"NPAC Activate Completed parameter should not Present");
			}
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToWfInfoJspPage");
		} catch (Exception e) {
			log.error("Error in navigateToWfInfoJspPage:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
}
