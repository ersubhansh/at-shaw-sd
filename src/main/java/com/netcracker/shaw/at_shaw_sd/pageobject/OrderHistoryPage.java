package com.netcracker.shaw.at_shaw_sd.pageobject;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import static com.netcracker.shaw.element.pageor.OrderCreationPageElement.CANCEL_ORDER;
import static com.netcracker.shaw.element.pageor.OrderCreationPageElement.IMMEDIATE_REPROVISION_BTN2;
import static com.netcracker.shaw.element.pageor.OrderHistoryPageElement.*;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

@SuppressWarnings(value={"unchecked", "rawtypes"})
public class OrderHistoryPage<OrderHistoryPageElement> extends BasePage {
	
	public OrderHistoryPage(ExtentTest test)
	{
		super(test);
	}

	public OrderHistoryPage(ExtentTest test,WebDriver driver) 
	{
		super(test,driver);
	}

	public void setTest(ExtentTest test1) 
	{
		test=test1;
	}
	
	public void setDriver(WebDriver driver1) 
	{
		driver=driver1;
	}

	public void navigateToOrderHistoryUrl(String acctNbr, String Order) {
		try {
			log.debug("Entering navigateToOrderHistoryUrl");
			wait(1);
			String OrderHistoryUrl = Utility.getValueFromPropertyFile("basepage_url")+ "/oe.customerOrderHistory.nc?accountId=1" + acctNbr + "&locationId=221";
			navigate(OrderHistoryUrl);
			wait(1);
			test.log(LogStatus.INFO, "Order History URL: ","<a href=" + OrderHistoryUrl + " > " + OrderHistoryUrl + " </a>");
			test.log(LogStatus.PASS, "Navigate Order History Page:", "Navigated To Order History Page Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			if (Order.equals("Product Summary")) {
				click(PRODUCT_SUMMARY);
				wait(8);
				test.log(LogStatus.PASS, "Navigate To Cancel Work Orders:","Navigated to Work Orders and Cancled Successfully");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			if (Order.equals("Agreement Manager")) {
				click(AGREEMENT_MANAGER);
				wait(2);
				click(ADDITIONAL_INFORMATION_VIEW_MORE);
				wait(2);
				test.log(LogStatus.PASS, "Navigate To Work Orders:", "Navigated to Work Orders Successfully");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			if (Order.equals("Retail MailOut")) {
				click(RETAIL_PICKUP_MAILOUT);
				wait(1);
				test.log(LogStatus.PASS, "Retail MailOut:", "MailOut Shipping History Successfully Updated");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			
			if (Order.equals("Reset")){
				if (isElementPresent(HARDWARE_RESET)){
					test.log(LogStatus.INFO, "HW Serial Nbr: ","Hardware Serial Number is displayed in remarks column");
				}
				else{
					test.log(LogStatus.INFO, "HW Serial Nbr: ","Hardware Serial Number is displayed in remarks column");
					Assert.fail();
				}
			}
			log.debug("Leaving navigateToOrderHistoryUrl");
		} catch (Exception e) {
			log.error("Error in navigateToOrderHistoryUrl:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void navigateToOrderHistoryActivate() {
		try {
			log.debug("Entering navigateToOrderHistoryActivate");
			wait(1);
			click(PRODUCT_SUMMARY);
			wait(5);
			click(ORDER_STATUS_ACTIVATION_BTN);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			click(CONFIRM_ACTIVATION_BTN);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToOrderHistoryActivate");
		} catch (Exception e) {
			log.error("Error in navigateToOrderHistoryActivate:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void navigateToOrderHistoryRetailPickupTV(String acctNbr, String tvHardwareSerialNbr,
			String tvHardwareDCXSerialNbr, String tvHardwareMoxiSrlNbr, String tvDCX3200P2MTCSrlNbr,
			String TV3400Dcx500GBSrlNo, String tv3400DCX250GBSrlNbr, String tvHardwareHDSerialNbr) {
		try {
			log.debug("Entering navigateToOrderHistoryRetailPickupTV");
			wait(1);
			String OrderHistoryRetailPickUpURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/oe.customerOrderHistory.nc?accountId=1" + acctNbr + "&locationId=221";
			navigate(OrderHistoryRetailPickUpURL);
			wait(1);
			test.log(LogStatus.INFO, "Order History Retail Pickup URL: ",
					"<a href=" + OrderHistoryRetailPickUpURL + " > " + OrderHistoryRetailPickUpURL + " </a>");
			click(HARDWARE_FOR_RETAIL_PICKUP);
			wait(2);
			int count = 1;
			while ((isElementPresent(SUBMIT_SRLNBRS_DISABLE_BTN) && count <= 2)) {
				count++;
				log.debug("Submit serial number button is disabled and not clickable");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			inputText(DCT700_599_594_INPUT, tvHardwareSerialNbr);
			wait(1);
			inputText(DCX3200M_506_501_INPUT, tvHardwareDCXSerialNbr);
			wait(1);
			inputText(DCX3200_HDGUIDE_518_517_INPUT, tvHardwareMoxiSrlNbr);
			wait(1);
			inputText(DCX3200P2MTC_INPUT, tvDCX3200P2MTCSrlNbr);
			wait(1);
			inputText(DCX3400_500GB_INPUT, TV3400Dcx500GBSrlNo);
			wait(1);
			inputText(DCX3400_250GB_INPUT, tv3400DCX250GBSrlNbr);
			wait(1);
			inputText(DCX_TV_INPUT, tvHardwareHDSerialNbr);
			wait(1);
			click(SUBMIT_SERIAL_NUMBERS_BTN);
			wait(4);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToOrderHistoryRetailPickupTV");

		} catch (Exception e) {
			log.error("Error in navigateToOrderHistoryRetailPickupTV:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void navigateToWorkOrdersLink() {
		try {
			log.debug("Entering navigateToWorkOrderLink");
			wait(2);
			click(WORK_ORDER_LINK);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToWorkOrderLink");
		} catch (Exception e) {
			log.error("Error in navigateToWorkOrderLink:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void cancelOrderAndVerifyMsg(String ProductSummary) {
		try {
			log.debug("Entering cancelOrderAndVerifyMsg");
			if (ProductSummary.equals("Yes")) {
				wait(1);
				click(PRODUCT_SUMMARY);
			}
			wait(1);
			click(CANCEL_ORDER);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving cancelOrderAndVerifyMsg");
		} catch (Exception e) {
			log.error("Error in cancelOrderAndVerifyMsg:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void navigateToOrderHistoryRetailPickup(String newTabExists, String OrderType, String acctNbr, String convergedSerialNbr,
			String tvBoxSerialNbr, String tvPortalSerialNbr) {
		try {
			log.debug("Entering navigateToOrderHistoryRetailPickup");
			wait(2);
			if (newTabExists.equals("true"))
				switchSecondNewTab();
			String OrderHistoryRetailPickUpURL=Utility.getValueFromPropertyFile("basepage_url") + "/oe.customerOrderHistory.nc?accountId=1"+ acctNbr + "&locationId=221";
			navigate(OrderHistoryRetailPickUpURL);
			wait(1);
			test.log(LogStatus.INFO, "Order History Retail Pickup URL: ","<a href=" + OrderHistoryRetailPickUpURL +" > "+ OrderHistoryRetailPickUpURL +" </a>");
			click(HARDWARE_FOR_RETAIL_PICKUP);
			wait(2);
			if(OrderType.equals("Yes")){
				inputText(XB6_CONVERGED_INPUT, convergedSerialNbr);
				wait(2);
			}
			inputText(SHAW_BLUESKY_TVBOX_INPUT, tvBoxSerialNbr);
			wait(2);
			inputText(SHAW_BLUESKY_TVPORTAL_INPUT, tvPortalSerialNbr);
			wait(2);
			click(SUBMIT_SERIAL_NUMBERS_BTN);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToOrderHistoryRetailPickup");

		} catch (Exception e) {
			log.error("Error in navigateToOrderHistoryRetailPickup:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void navigateToOrderHistoryRetailPickupDCX3510(String newTabExists, String acctNbr,
			String convergedSerialNbr, String tvSerialNbr) {
		try {
			log.debug("Entering navigateToOrderHistoryRetailPickup");
			wait(2);
			if (newTabExists.equals("true"))
				switchSecondNewTab();
			String OrderHistoryRetailPickUpURL=Utility.getValueFromPropertyFile("basepage_url") + "/oe.customerOrderHistory.nc?accountId=1"+ acctNbr + "&locationId=221";
			navigate(OrderHistoryRetailPickUpURL);
			wait(1);
			test.log(LogStatus.INFO, "Retail Pickup URL: ","<a href=" + OrderHistoryRetailPickUpURL +" > "+ OrderHistoryRetailPickUpURL +" </a>");
			click(HARDWARE_FOR_RETAIL_PICKUP);
			wait(2);
			inputText(XB6_CONVERGED_INPUT, convergedSerialNbr);
			wait(2);
			inputText(DCX_TV_INPUT, tvSerialNbr);
			wait(2);
			click(SUBMIT_SERIAL_NUMBERS_BTN);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToOrderHistoryRetailPickup");

		} catch (Exception e) {
			log.error("Error in navigateToOrderHistoryRetailPickup:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void navigateToOrderHistoryRetailPickupPhone(String newTabExists, String acctNbr, String phoneSerialNbr) {
		try {
			log.debug("Entering navigateToOrderHistoryRetailPickupPhone");
			wait(2);
			if (newTabExists.equals("true"))
				switchSecondNewTab();
			String OrderHistoryRetailPickUpURL=Utility.getValueFromPropertyFile("basepage_url") + "/oe.customerOrderHistory.nc?accountId=1"+ acctNbr + "&locationId=221";
			navigate(OrderHistoryRetailPickUpURL);
			wait(1);
			test.log(LogStatus.INFO, "Order History Retail Pickup URL: ","<a href=" + OrderHistoryRetailPickUpURL +" > "+ OrderHistoryRetailPickUpURL +" </a>");
			click(HARDWARE_FOR_RETAIL_PICKUP);
			wait(2);
			inputText(PHONE_INPUT, phoneSerialNbr);
			wait(2);
			click(SUBMIT_SERIAL_NUMBERS_BTN);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToOrderHistoryRetailPickupPhone");

		} catch (Exception e) {
			log.error("Error in navigateToOrderHistoryRetailPickupPhone:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void navigateToOrderHistoryRetailPickupTwoLOBs(String newTabExists, String acctNbr, String internetSerialNbr,
			String tvHardwareSerialNbr) {
		try {
			log.debug("Entering navigateToOrderHistoryRetailPickupTwoLOBs");
			wait(2);
			if (newTabExists.equals("true"))
				switchSecondNewTab();
			String OrderHistoryRetailPickUpURL=Utility.getValueFromPropertyFile("basepage_url") + "/oe.customerOrderHistory.nc?accountId=1"+ acctNbr + "&locationId=221";
			navigate(OrderHistoryRetailPickUpURL);
			wait(1);
			test.log(LogStatus.INFO, "Order History Retail Pickup URL: ","<a href=" + OrderHistoryRetailPickUpURL +" > "+ OrderHistoryRetailPickUpURL +" </a>");
			click(HARDWARE_FOR_RETAIL_PICKUP);
			wait(2);
			inputText(INTERNET_INPUT, internetSerialNbr);
			wait(2);
			inputText(DCX_TV_INPUT, tvHardwareSerialNbr);
			wait(2);
			click(SUBMIT_SERIAL_NUMBERS_BTN);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			refreshPage();
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToOrderHistoryRetailPickupTwoLOBs");

		} catch (Exception e) {
			log.error("Error in navigateToOrderHistoryRetailPickupTwoLOBs:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void navigateToOrderHistoryRetailPickupThreeLOB(String newTabExists, String acctNbr, String phoneSerialNbr,
			String internetSerialNbr, String tvBoxSerialNbr, String tvPortalSerialNbr) {
		try {
			log.debug("Entering navigateToOrderHistoryRetailPickup");
			wait(2);
			if (newTabExists.equals("true"))
				switchSecondNewTab();
			String OrderHistoryRetailPickUpURL = Utility.getValueFromPropertyFile("basepage_url")+ "/oe.customerOrderHistory.nc?accountId=1" + acctNbr + "&locationId=221";
			wait(1);
			navigate(OrderHistoryRetailPickUpURL);
			test.log(LogStatus.INFO, "Order History Retail PickUp URL: ","<a href=" + OrderHistoryRetailPickUpURL +" > "+ OrderHistoryRetailPickUpURL +" </a>");
			click(HARDWARE_FOR_RETAIL_PICKUP);
			wait(2);
			inputText(PHONE_INPUT, phoneSerialNbr);
			wait(2);
			inputText(INTERNET_INPUT, internetSerialNbr);
			wait(2);
			inputText(TVBOX_INPUT, tvBoxSerialNbr);
			wait(2);
			inputText(TVPORTAL_INPUT, tvPortalSerialNbr);
			wait(2);
			click(SUBMIT_SERIAL_NUMBERS_BTN);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToOrderHistoryRetailPickup");

		} catch (Exception e) {
			log.error("Error in navigateToOrderHistoryRetailPickup:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void navigateToOrderHistoryUrlLocationId2(String acctNbr, String Order) {
		try {
			log.debug("Entering navigateToOrderHistoryUrl");
			wait(1);
			String OrderHistoryUrlLocationId2 = Utility.getValueFromPropertyFile("basepage_url")+ "/oe.customerOrderHistory.nc?locationId=2&accountId=1" + acctNbr + "";
			navigate(OrderHistoryUrlLocationId2);
			wait(1);
			test.log(LogStatus.INFO, "Order History URL: ","<a href=" + OrderHistoryUrlLocationId2 + " > " + OrderHistoryUrlLocationId2 + " </a>");
			test.log(LogStatus.PASS, "Navigate Order History Page:", "Navigated To Order History Page Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			if (Order.equals("Product Summary")) {
				click(PRODUCT_SUMMARY);
				wait(8);
				test.log(LogStatus.PASS, "Navigate To Cancel Work Orders:","Navigated to Work Orders and Cancled Successfully");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			if (Order.equals("Agreement Manager")) {
				click(AGREEMENT_MANAGER);
				wait(1);
				click(ADDITIONAL_INFORMATION_VIEW_MORE);
				wait(1);
				test.log(LogStatus.PASS, "Navigate To Work Orders:", "Navigated to Work Orders Successfully");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			if (Order.equals("Retail MailOut")) {
				click(RETAIL_PICKUP_MAILOUT);
				wait(1);
				test.log(LogStatus.PASS, "Retail MailOut:", "MailOut Shipping History Successfully Updated");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			
			if (Order.equals("Reset")){
				if (isElementPresent(HARDWARE_RESET)){
					test.log(LogStatus.INFO, "HW Serial Nbr: ","Hardware Serial Number is displayed in remarks column");
				}
				else{
					test.log(LogStatus.INFO, "HW Serial Nbr: ","Hardware Serial Number is displayed in remarks column");
					Assert.fail();
				}
			}
			log.debug("Leaving navigateToOrderHistoryUrl");
		} catch (Exception e) {
			log.error("Error in navigateToOrderHistoryUrl:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
}
