package com.netcracker.shaw.at_shaw_sd.pageobject;

import static com.netcracker.shaw.element.pageor.SOMOrderPageElement.*;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

@SuppressWarnings(value={"unchecked", "rawtypes"})
public class SOMOrderPage<SOMOrdersPageElement> extends BasePage {
	public SOMOrderPage(ExtentTest test) {
		super(test);
	}

	public SOMOrderPage(ExtentTest test, WebDriver driver) {
		super(test, driver);
	}

	public void setTest(ExtentTest test1) {
		test = test1;
	}

	public void setDriver(WebDriver driver1) {
		driver = driver1;
	}
	
	Logger log=Logger.getLogger(SOMOrderPage.class);

	public void verifyRecordsCountInSOMOrder(int expectedRows) {
		try {
			log.debug("Entering verifyRecordsCountInSOMOrder");
			click(SOM_ORDER_TAB);
			wait(2);
			int record=countRowsinTable(ROWS_IN_SOM_ORDER);
			int counter=0;
			while (!(record == expectedRows) && (counter <= 2)) {
				wait(60);
				refreshPage();
				record=countRowsinTable(ROWS_IN_SOM_ORDER);
				log.debug("Refreshed Page  : " + counter + " times " + " and record count reached to : " + record);
				counter++;
			}
			log.debug("Wait Time At SOM Tab : " + (counter) + "Minutes");
			if ((record == expectedRows)) {
				test.log(LogStatus.PASS, "Record count match", "Total Number of records in SOM page is: " + record);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			} else {
				log.error("Expected Count mismatched in SOM orders TAB");
				test.log(LogStatus.FAIL, "Record count mis-matched in SOM","Expected record Count does not match with actual count in SOM orders TAB");
				test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				Assert.assertTrue(false, "Expected Count mismatched in SOM orders TAB");
			}
			wait(3);
			log.debug("Leaving verifyRecordsCountInSOMOrder");
		} catch (Exception e) {
			log.error("Error in verifyRecordsCountInSOMOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.INFO, "Record count match", "Expected Records not displaying in SOM order");
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public SOMOrderPage navigateToSOMOrder() {
		try {
			log.debug("Entering navigateToSOMOrder");
			wait(1);
			click(SOM_ORDER_TAB);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			refreshPage();
			log.debug("Leaving navigateToSOMOrder");
		} catch (Exception e) {
			log.error("Error in navigateToSOMOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return new SOMOrderPage(test);
	}
	
	public String navigateToNewPhoneRFSPhNbr1() {
		String custPhNbrOne="";
		try {
			log.debug("Entering navigateToNewPhoneRFSPhNbr1");
			click(NEW_PHONE_PROVISIONING_RFS_ORDER1);
			wait(3);
			javascript.executeScript("window.scrollBy(0,900)", "");
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			custPhNbrOne=getText(CUST_PHONE_NBR);
			wait(1);
			log.debug("Leaving navigateToNewPhoneRFSPhNbr1");
		} catch (Exception e) {
			log.error("Error in navigateToNewPhoneRFSPhNbr1:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return custPhNbrOne;
	}
	
	public String navigateToNewPhoneRFSPhNbr2() {
		String custPhNbrTwo="";
		try {
			log.debug("Entering navigateToNewPhoneRFSPhNbr2");
			click(SOM_ORDER_TAB);
			wait(1);
			click(NEW_PHONE_PROVISIONING_RFS_ORDER2);
			wait(3);
			javascript.executeScript("window.scrollBy(0,900)", "");
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			custPhNbrTwo=getText(CUST_PHONE_NBR);
			wait(1);
			log.debug("Leaving navigateToNewPhoneRFSPhNbr2");
		} catch (Exception e) {
			log.error("Error in navigateToNewPhoneRFSPhNbr2:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return custPhNbrTwo;
	}
}
