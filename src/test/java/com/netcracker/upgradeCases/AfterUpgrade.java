package com.netcracker.upgradeCases;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.netcracker.shaw.at_shaw_sd.pageobject.COMOrdersPage;
import com.netcracker.shaw.at_shaw_sd.pageobject.LandingPage;
import com.netcracker.shaw.at_shaw_sd.pageobject.OrderCreationPage;
import com.netcracker.shaw.at_shaw_sd.pageobject.OrderHistoryPage;
import com.netcracker.shaw.at_shaw_sd.pageobject.SOMOrderPage;
import com.netcracker.shaw.at_shaw_sd.pageobject.ValidateReport;
import com.netcracker.shaw.at_shaw_sd.soap.SoapUIRestAPIpage;
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.netcracker.shaw.setup.SeleniumTestUp;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 *         Aug 10, 2018
 */

public class AfterUpgrade extends SeleniumTestUp {

	LandingPage landing = null;
	OrderCreationPage order = null;
	COMOrdersPage com = null;
	SOMOrderPage som = null;
	ValidateReport repo = null;
	SoapUIRestAPIpage soapRest = null;
	OrderHistoryPage orderHistory = null;
	SoftAssert softAssert = new SoftAssert();
	ArrayList<String> valuesToPassForValidation = new ArrayList<String>();

	Logger log = Logger.getLogger(AfterUpgrade.class);

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Activation_Pending_Tripleplay_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Activation of pending triple play account after upgrade");
			log.debug("Entering Activation_Pending_Tripleplay_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneNbr = Utility.getValueFromUpgradeProp("phoneNumTC1");
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC1");
			String tvBoxSerialNbr = Utility.getValueFromUpgradeProp("tvBoxSlrNbrTC1");
			String tvPortalSerialNbr = Utility.getValueFromUpgradeProp("tvPortalSlrNbrTC1");
			String convergedHardWareSerialNbr = Utility.getValueFromUpgradeProp("convergedSrlNbrTC1");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest0 = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			order = createChildTest(childTest0, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest1 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest2 = extent.startTest("Wait for Effective date Mark Finish task");
			test.appendChild(childTest2);
			com = createChildTest(childTest2, extent, com);
			order = createChildTest(childTest2, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			com.verifySVGDiagram("");
			order.navigateToOrderPage();

			ExtentTest childTest3 = extent.startTest("Send same serial number for Converged Hardware");
			test.appendChild(childTest3);
			com = createChildTest(childTest3, extent, com);
			som = createChildTest(childTest3, extent, som);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest4 = extent.startTest("Send Serial number for TV Box");
			test.appendChild(childTest4);
			com = createChildTest(childTest4, extent, com);
			order = createChildTest(childTest4, extent, order);
			order.navigateToOrderValidation(11, "No", "No", "COM");
			String tvHardwareType = com.getTVHardwareType();
			if (tvHardwareType.contains("NGV Gateway")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();

			ExtentTest childTest5 = extent.startTest("Send Serial number for TV Portal");
			test.appendChild(childTest5);
			com = createChildTest(childTest5, extent, com);
			order = createChildTest(childTest5, extent, order);
			order.navigateToOrderValidation(12, "No", "No", "COM");
			String tvHardwareType1 = com.getTVHardwareType();
			if (tvHardwareType1.contains("NGV Portal")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();

			ExtentTest childTest6 = extent.startTest("Wait For Tech Confirm Mark Finished");
			test.appendChild(childTest6);
			com = createChildTest(childTest6, extent, com);
			order = createChildTest(childTest6, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest7 = extent.startTest("CLEC Request Before JOB Run");
			test.appendChild(childTest7);
			com = createChildTest(childTest7, extent, com);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest8 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest8);
			com = createChildTest(childTest8, extent, com);
			order = createChildTest(childTest8, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest9 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest9);
			com = createChildTest(childTest9, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest10 = extent.startTest("Verify COM, SOM and CLEC Record counts");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest11 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest11);
			som = createChildTest(childTest11, extent, som);
			repo = createChildTest(childTest11, extent, repo);
			order = createChildTest(childTest11, extent, order);
			order.navigateToOrderValidation(2, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC1", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC1", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(11, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway = repo.validateSOMIntegrationReportsSpcl(xls, "TC1", 3, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway, "Attributes are not validated successfully");
			if (!is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC1", 3, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			order.navigateToOrderValidation(12, "Yes", "No", "SOM");
			if (is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC1", 4, valuesToPassForValidation),
						"Attributes are not validated successfully");
			} else {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC1", 4, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			ExtentTest childTest12 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			repo = createChildTest(childTest12, extent, repo);
			order = createChildTest(childTest12, extent, order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC1", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, phoneNbr, tvBoxSerialNbr, tvPortalSerialNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC1", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest13 = extent.startTest("Navigate to Order History status completed");
			test.appendChild(childTest13);
			orderHistory = createChildTest(childTest13, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Activation_Pending_Tripleplay_AfterUG");
		} catch (Exception e) {
			log.error("Error in Activation_Pending_Tripleplay_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Activate_2YVPInt_TvAgreement_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Activation of Internet on converged device and TV on Blue-sky Box + Portal, add 2 YVP Internet + TV agreement and some TV Promotions after Upgrade");
			log.debug("Entering Activate_2YVPInt_TvAgreement_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC2");
			String tvBoxSerialNbr = Utility.getValueFromUpgradeProp("tvBoxSlrNbrTC2");
			String tvPortalSerialNbr = Utility.getValueFromUpgradeProp("tvPortalSlrNbrTC2");
			String convergedHardWareSerialNbr = Utility.getValueFromUpgradeProp("convergedSrlNbrTC2");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest1 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest2 = extent.startTest("Wait for Effective date Mark Finish task");
			test.appendChild(childTest2);
			com = createChildTest(childTest2, extent, com);
			order = createChildTest(childTest2, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			com.verifySVGDiagram("");
			order.navigateToOrderPage();

			ExtentTest childTest3 = extent.startTest("Send same serial number for Converged Hardware");
			test.appendChild(childTest3);
			com = createChildTest(childTest3, extent, com);
			som = createChildTest(childTest3, extent, som);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest4 = extent.startTest("Send Serial number for TV Box");
			test.appendChild(childTest4);
			com = createChildTest(childTest4, extent, com);
			order = createChildTest(childTest4, extent, order);
			order.navigateToOrderValidation(11, "No", "No", "COM");
			String OfferingID = com.getOldSerialNbr();
			if (OfferingID.contains("box")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();

			ExtentTest childTest5 = extent.startTest("Send Serial number for TV Portal");
			test.appendChild(childTest5);
			com = createChildTest(childTest5, extent, com);
			order = createChildTest(childTest5, extent, order);
			order.navigateToOrderValidation(12, "No", "No", "COM");
			String OfferingID1 = com.getOldSerialNbr();
			if (OfferingID1.contains("box")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();

			ExtentTest childTest6 = extent.startTest("Wait For Tech Confirm Mark Finished");
			test.appendChild(childTest6);
			com = createChildTest(childTest6, extent, com);
			order = createChildTest(childTest6, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest7 = extent.startTest("CLEC Request Before JOB Run");
			test.appendChild(childTest7);
			com = createChildTest(childTest7, extent, com);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest8 = extent.startTest("Navigate to E911 job Run");
			test.appendChild(childTest8);
			com = createChildTest(childTest8, extent, com);
			order = createChildTest(childTest8, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest9 = extent.startTest("CLEC REQ AFTER JOB RUN");
			test.appendChild(childTest9);
			com = createChildTest(childTest9, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest10 = extent.startTest("Verify COM, SOM and CLEC Record counts");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest11 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest11);
			repo = createChildTest(childTest11, extent, repo);
			order = createChildTest(childTest11, extent, order);
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC2", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC2", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest12 = extent.startTest("Navigate to Order History status completed");
			test.appendChild(childTest12);
			orderHistory = createChildTest(childTest12, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Activate_2YVPInt_TvAgreement_AfterUG");
		} catch (Exception e) {
			log.error("Error in Activate_2YVPInt_TvAgreement_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Cancel_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Inflight cancel functionality for a pending install account");
			log.debug("Entering Inflight_Cancel_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC3");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest8 = extent.startTest("Navigate To Customer Order Page");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.navigateToCustOrderPage("false", accntNum);
			order.cancelOrder();

			ExtentTest childTest9 = extent.startTest("Navigate To COM Order");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest10 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest11 = extent.startTest("Navigate to Order History status completed");
			test.appendChild(childTest11);
			orderHistory = createChildTest(childTest11, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Inflight_Cancel_AfterUG");
		} catch (Exception e) {
			log.error("Error in Inflight_Cancel_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Residential_To_Staff_XB6_BlueSky_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Changing account type from Residential to Staff for an account with XB6 and Blue Sky TV after Upgrade");
			log.debug("Entering Residential_To_Staff_XB6_BlueSky_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String techAppointment = data.get("Tech_Appointment");
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC4");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest1 = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest1);
			landing = createChildTest(childTest1, extent, landing);
			order = createChildTest(childTest1, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest2 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest3 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest4 = extent.startTest("Change Account type Residential to Staff");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.accountType("Staff");

			ExtentTest childTest5 = extent.startTest("Add Employee Personal Phone");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServicesAndFeacturesTab();
			order.addPhoneType(phoneProduct);
			order.selectVoiceMail();

			ExtentTest childTest6 = extent.startTest("Add Employee Internet Product");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest7 = extent.startTest("Add Employee TV Product");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.addServiceTV(tvProduct);

			ExtentTest childTest8 = extent.startTest("Tech Appointment");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			// order.enterInstallationFee("1.00");
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNo("", "", "");

			ExtentTest childTest9 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.selectMethodOfConfirmation("Voice");

			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest10 = extent.startTest("Review and Finish");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest11 = extent.startTest("Navigate To COM Order");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest12 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			som = createChildTest(childTest12, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest13 = extent.startTest("Navigate to Order History status completed");
			test.appendChild(childTest13);
			orderHistory = createChildTest(childTest13, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Residential_To_Staff_XB6_BlueSky_AfterUG");
		} catch (Exception e) {
			log.error("Error in Residential_To_Staff_XB6_BlueSky_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Staff_To_Residential_XB6_BlueSky_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Changing account type from Staff to Residential for an account with XB6 and Blue Sky TV");
			log.debug("Entering Staff_To_Residential_XB6_BlueSky_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC5");
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest1 = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest1);
			landing = createChildTest(childTest1, extent, landing);
			order = createChildTest(childTest1, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest2 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest3 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest4 = extent.startTest("Change Account type Staff to Residential");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.accountType("Residential");

			ExtentTest childTest5 = extent.startTest("Add Personal Phone");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServicesAndFeacturesTab();
			order.addPhoneType(phoneProduct);
			order.selectVoiceMail();

			ExtentTest childTest6 = extent.startTest("Add Internet Product");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest7 = extent.startTest("Add TV Product");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.addServiceTV(tvProduct);
			// order.enterInstallationFee("1.00");

			ExtentTest childTest8 = extent.startTest("Tech Appointment");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.changesAuthorizedBy();

			ExtentTest childTest9 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest10 = extent.startTest("Review and Finish");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest11 = extent.startTest("Navigate To COM Order");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest12 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			som = createChildTest(childTest12, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest13 = extent.startTest("Navigate to Order History status completed");
			test.appendChild(childTest13);
			orderHistory = createChildTest(childTest13, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			ExtentTest childTest14 = extent.startTest("Navigate to Order History status completed");
			test.appendChild(childTest14);
			orderHistory = createChildTest(childTest14, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Staff_To_Residential_XB6_BlueSky_AfterUG");
		} catch (Exception e) {
			log.error("Error in Staff_To_Residential_XB6_BlueSky_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void TN_change_Ported_To_Native_DPT_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "TN change from Ported to Native for an account with DPT");
			log.debug("Entering TN_change_Ported_To_Native_DPT_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC6");
			String phoneSerialNbr = Utility.getValueFromUpgradeProp("PhoneSrlNbrTC6");
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest10 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest11 = extent.startTest("Cust mobile Nbr Changing PortedTN To Native");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			String PhoneNbr = order.changeCustNbrPortedToNative(phoneProduct);

			ExtentTest childTest12 = extent.startTest("Tech Appointment");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNo("", "", "");
			order.selectingSalesRepresentativeCheckBox();
			order.addServicesAndFeacturesTab();
			// order.selectDefaultInstallationCheckBox();
			order.selectPortOut();

			ExtentTest childTest13 = extent.startTest("Review and Finish");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest14 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest15 = extent.startTest("CLEC Request before Job run");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest16 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest16);
			com = createChildTest(childTest16, extent, com);
			order = createChildTest(childTest16, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest17 = extent.startTest("Navigate To E911 JOB Run");
			test.appendChild(childTest17);
			com = createChildTest(childTest17, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest18 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			som = createChildTest(childTest18, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest19 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest19);
			som = createChildTest(childTest19, extent, som);
			repo = createChildTest(childTest19, extent, repo);
			order = createChildTest(childTest19, extent, order);
			order.navigateToOrderValidation(3, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC6", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest20 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest20);
			com = createChildTest(childTest20, extent, com);
			repo = createChildTest(childTest20, extent, repo);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, PhoneNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC6", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC6", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Navigate to Order History status completed");
			test.appendChild(childTest21);
			orderHistory = createChildTest(childTest21, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving TN_change_Ported_To_Native_DPT_AfterUG");
		} catch (Exception e) {
			log.error("Error in TN_change_Ported_To_Native_DPT_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Cancel_ReSbmit_order_XB6Conv_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Cancelling and Re-submitting an order on account with XB6 Converged");
			log.debug("Entering Cancel_ReSbmit_order_XB6Conv_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC7");
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest1 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest2 = extent.startTest("Add Secondary Phone Line");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.addSecondaryPhone(phoneProduct, "Second Dropdown");

			ExtentTest childTest3 = extent.startTest("Tech Appointment");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.enterInstallationFee("1.00");
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("Yes", "");

			ExtentTest childTest4 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest14 = extent.startTest("Review and Finish");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest15 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest16 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.navigateToCustOrderPage("false", accntNum);
			order.cancelOrderTNNumber("Cancle Order", "CheckBox");

			ExtentTest childTest17 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.openCOMOrderPage("false", accntNum, "yes");

			ExtentTest childTest18 = extent.startTest("Retrieve order information");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.navigateToCustOrderPage("false", accntNum);
			order.retrieveOrderInformation();
			// order.selectTodo();
			order.effectiveDateConfirmation("Yes");

			ExtentTest childTest19 = extent.startTest("Review and Finish");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest20 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest21 = extent.startTest("Wait for Effective date Mark finish Task");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			order = createChildTest(childTest21, extent, order);
			order.navigateToOrderValidation(27, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("Wait for Provisioning start Mark Finish Task");
			test.appendChild(childTest22);
			com = createChildTest(childTest22, extent, com);
			order = createChildTest(childTest22, extent, order);
			order.navigateToOrderValidation(27, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest23 = extent.startTest("Wait For Tech Confirm Mark Finish task");
			test.appendChild(childTest23);
			com = createChildTest(childTest23, extent, com);
			order = createChildTest(childTest23, extent, order);
			order.navigateToOrderValidation(27, "No", "No", "COM");
			com.verifySVGDiagram("");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest24 = extent.startTest("CLEC REQ BEFORE JOB RUN");
			test.appendChild(childTest24);
			com = createChildTest(childTest24, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest25 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest25);
			com = createChildTest(childTest25, extent, com);
			order = createChildTest(childTest25, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest26 = extent.startTest("CLEC REQ AFTER JOB RUN");
			test.appendChild(childTest26);
			com = createChildTest(childTest26, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest27 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest27);
			com = createChildTest(childTest27, extent, com);
			som = createChildTest(childTest27, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest28 = extent.startTest("Navigate to Order History status completed");
			test.appendChild(childTest28);
			orderHistory = createChildTest(childTest28, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Cancel_ReSbmit_order_XB6Conv_AfterUG");
		} catch (Exception e) {
			log.error("Error in Cancel_ReSbmit_order_XB6Conv_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void AUP_Resume_TriplePlay_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "AUP: resume after upgrade");
			log.debug("Entering AUP_Resume_TriplePlay_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC8");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest11 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			
			  ExtentTest childTest12 = extent.startTest("Suspend Phone");
			  test.appendChild(childTest12); com = createChildTest(childTest12, extent,
			  com); com.suspendLOBProducts("Phone Record");
			  Assert.assertEquals(com.NavigateTosuspendStatusCheck("Phone"), "Active",
			  "Suspend Phone Status is not Correct");
			 

			
			  ExtentTest childTest13 = extent.startTest("Suspend Internet");
			  test.appendChild(childTest13); com = createChildTest(childTest13, extent,
			  com); com.suspendLOBProducts("Internet Record");
			  Assert.assertEquals(com.NavigateTosuspendStatusCheck("Internet"), "Active",
			  "Suspend Internet Status is not Correct");
			  
			  ExtentTest childTest14 = extent.startTest("Suspend TV");
			  test.appendChild(childTest14); com = createChildTest(childTest14, extent,
			  com); com.suspendLOBProducts("TV Record");
			  Assert.assertEquals(com.NavigateTosuspendStatusCheck("TV"), "Active",
			  "Suspend TV Status is not Correct");
			 

			ExtentTest childTest15 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			com = createChildTest(childTest15, extent, com);
			som = createChildTest(childTest15, extent, som);
			com.navigateToCOMOrder();
			som.navigateToSOMOrder();

			/*
			 * ExtentTest childTest16 = extent.startTest("Resume Phone");
			 * test.appendChild(childTest16); com = createChildTest(childTest16, extent,
			 * com); com.resumeLOBProducts("Phone Record");
			 * Assert.assertEquals(com.NavigateTosuspendStatusCheck("Phone"), "Inactive",
			 * "Resume Phone Status is not Correct");
			 */
			ExtentTest childTest17 = extent.startTest("Resume Internet");
			test.appendChild(childTest17);
			com = createChildTest(childTest17, extent, com);
			com.resumeLOBProducts("Internet Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("Internet"), "Inactive",
					"Resume Internet Status is not Correct");

			ExtentTest childTest18 = extent.startTest("Resume TV");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			com.resumeLOBProducts("TVHardware1");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("TV"), "Inactive", "Suspend TV Status is not Correct");

			ExtentTest childTest19 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			som = createChildTest(childTest19, extent, som);
			com.navigateToCOMOrder();
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest20 = extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest20);
			som = createChildTest(childTest20, extent, som);
			repo = createChildTest(childTest20, extent, repo);
			order = createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC8", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(28, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC8", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(26, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC8", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Navigate to Order History status completed");
			test.appendChild(childTest21);
			orderHistory = createChildTest(childTest21, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving AUP_Resume_TriplePlay_AfterUG");
		} catch (Exception e) {
			log.error("Error in AUP_Resume_TriplePlay_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Tripleplay_DPTCisco_legacyTV_Swap_OrderEntry_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"For a Triple play account with DPT, Cisco Advanced Wi-Fi modem and legacy TV device , Moxi gateway, perform hardware swaps through Order Entry");
			log.debug("Entering Tripleplay_DPTCisco_legacyTV_Swap_OrderEntry_BeforUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String swapPhoneHardwareSerialNbr = PhSrlNo;
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String swapInternetHardwareSerialNbr = IntHitronSrlNo;
			TVMoxiSrlNo = Utility.generateHardwareSerailNum(TVMoxiSrlNo);
			String swapTVHardwareSlNbr1 = TVMoxiSrlNo;
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String swapTVHardwareSlNbr2 = TVDctSrlNo;
			String internetProduct = data.get("Internet_Product");
			String techAppointment = data.get("Tech_Appointment");
			String[] hardwareType = { "ShawGatewayHDPVR", "DCX3200HDGuide" };
			String[] serialNumber = { swapTVHardwareSlNbr1, swapTVHardwareSlNbr2 };
			String phoneNbr = Utility.getValueFromUpgradeProp("phoneNumTC9");
			String phoneSerialNbr = Utility.getValueFromUpgradeProp("PhoneSrlNbrTC9");
			String internetHardwareSerialNbr = Utility.getValueFromUpgradeProp("IntHitronSrlNbrTC9");
			String tvHardwareSerialNbr1 = Utility.getValueFromUpgradeProp("tvMOXISlrNbrTC9");
			String tvHardwareSerialNbr2 = Utility.getValueFromUpgradeProp("tvDCX3200SlrNbrTC9");
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC9");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest13 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest14 = extent.startTest("Swap Phone Hardware");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.swapPhoneHardware(swapPhoneHardwareSerialNbr);

			ExtentTest childTest15 = extent.startTest("Swap Internet Hardware");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.swapInternetHardware(swapInternetHardwareSerialNbr, internetProduct);

			ExtentTest childTest16 = extent.startTest("Swap TV MOXI Gateway and legecy Hardwares");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.swapTVMOXIandLegecyHardwares(swapTVHardwareSlNbr1, swapTVHardwareSlNbr2);

			ExtentTest childTest17 = extent.startTest("Tech Appointment");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNo("On a date", "", "");
			order.selectingSalesRepresentativeCheckBox();
			order.addServicesAndFeacturesTab();
			order.selectTodo();

			/*
			 * ExtentTest childTest18=extent.startTest("Select Method of confirmation");
			 * test.appendChild(childTest18);
			 * order=createChildTest(childTest18,extent,order);
			 * order.selectMethodOfConfirmation("Voice");
			 * order.selectDefaultInstallationCheckBox();
			 */

			ExtentTest childTest19 = extent.startTest("Review and Finish");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.reviewAndFinishOrder();
			// order.okToClose();

			ExtentTest childTest20 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest21 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			som = createChildTest(childTest21, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest22 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest22);
			repo = createChildTest(childTest22, extent, repo);
			order = createChildTest(childTest22, extent, order);
			order.navigateToOrderValidation(33, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC9", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(34, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC9", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(35, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC9", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(36, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC9", 4, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(8, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC9", 5, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(12, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC9", 6, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(13, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC9", 7, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest23 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest23);
			repo = createChildTest(childTest23, extent, repo);
			order = createChildTest(childTest23, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneSerialNbr,
					internetHardwareSerialNbr, tvHardwareSerialNbr1, tvHardwareSerialNbr2, swapPhoneHardwareSerialNbr,
					swapInternetHardwareSerialNbr, swapTVHardwareSlNbr1, swapTVHardwareSlNbr2));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC9", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneSerialNbr,
					internetHardwareSerialNbr, tvHardwareSerialNbr1, tvHardwareSerialNbr2, swapPhoneHardwareSerialNbr,
					swapInternetHardwareSerialNbr, swapTVHardwareSlNbr1, swapTVHardwareSlNbr2));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC9", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest24 = extent.startTest("Navigate to Order History status completed");
			test.appendChild(childTest24);
			orderHistory = createChildTest(childTest24, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Tripleplay_DPTCisco_legacyTV_Swap_OrderEntry_AfterUG");
		} catch (Exception e) {
			log.error("Error in Tripleplay_DPTCisco_legacyTV_Swap_OrderEntry_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void TechRetry_InternetFail_Phone_Success_SameSlNo_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Tech Retry with same Serial Number when Internet provisioning failed and Phone provisioning is successful on a converged device");
			log.debug("Entering TechRetry_InternetFail_Phone_Success_SameSlNo_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String phoneNbr = Utility.getValueFromUpgradeProp("phoneNumTC10");
			String convergedHardWareSerialNbr = Utility.getValueFromUpgradeProp("convergedSrlNbrTC10");
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC10");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();

			ExtentTest childTest1 = extent.startTest("Navigate to Stub URL To Fail");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.navigateToChangeToggleValueToFail("Internet");
			order.navigateToChangeToggleValueToFail("Converged Hardware");
			landing.Login();

			ExtentTest childTest2 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest9 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest10 = extent.startTest("Wait for Effective date Mark Finish task");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			order = createChildTest(childTest10, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			com.verifySVGDiagram("");
			order.navigateToOrderPage();

			ExtentTest childTest11 = extent.startTest("Wait for Provisioning start Mark Finished Task");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			order = createChildTest(childTest11, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest12 = extent.startTest("Send same serial number for Converged Hardware");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			som = createChildTest(childTest12, extent, som);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();
			som.navigateToSOMOrder();
			com.navigateToCOMOrder();

			ExtentTest childTest13 = extent.startTest("Navigate to Stub to Pass Internet & Converged Hardware");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.navigateToOeStubToPassServices("Internet");
			order.navigateToOeStubToPassServices("Converged Hardware");
			order.navigateToOrderPage();

			ExtentTest childTest14 = extent.startTest("Send same serial number for Converged Hardware");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			som = createChildTest(childTest14, extent, som);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();
			som.navigateToSOMOrder();
			com.navigateToCOMOrder();

			ExtentTest childTest15 = extent.startTest("Wait For Tech Confirm Mark Finish Task");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			order = createChildTest(childTest15, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest16 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest16);
			com = createChildTest(childTest16, extent, com);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest17 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest17);
			com = createChildTest(childTest17, extent, com);
			order = createChildTest(childTest17, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest18 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest19 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			som = createChildTest(childTest19, extent, som);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest20 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest20);
			repo = createChildTest(childTest20, extent, repo);
			order = createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(3, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC10", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC10", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			repo = createChildTest(childTest21, extent, repo);
			order = createChildTest(childTest21, extent, order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC10", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, phoneNbr, internetProduct, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC10", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("Navigate to Order History status completed");
			test.appendChild(childTest22);
			orderHistory = createChildTest(childTest22, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving TechRetry_InternetFail_Phone_Success_SameSlNo_AfterUG");
		} catch (Exception e) {
			log.error("Error in TechRetry_InternetFail_Phone_Success_SameSlNo_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Modify_Existing_Pending_Order_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Inflight modify functionality for an existing account with pending order");
			log.debug("Entering Inflight_Modify_Existing_Pending_Order_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC11");
			String convergedHardWareSerialNbr = Utility.getValueFromUpgradeProp("convergedSrlNbrTC11");
			String tvHardwareSerialNbr = Utility.getValueFromUpgradeProp("tvDCX3200SlrNbrTC11");
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest9 = extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest10 = extent.startTest("Add Phone Product and link Converged Hardware");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			order.selectConvergedHardware("Phone");

			ExtentTest childTest11 = extent.startTest("Tech Appointment");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.enterInstallationFee("1.00");
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("", "");

			ExtentTest childTest12 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest13 = extent.startTest("Review and Finish Order");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest14 = extent.startTest("Navigate To COM and SOM Order");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest15 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.navigateToCustOrderPage("false", accntNum);
			order.cancelOrderTNNumber("Cancle Order", "CheckBox");

			ExtentTest childTest16 = extent.startTest("Navigate To COM and SOM Order");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest17 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest17);
			com = createChildTest(childTest17, extent, com);
			som = createChildTest(childTest17, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest18 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest18);
			repo = createChildTest(childTest18, extent, repo);
			order = createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC11", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(10, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC11", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest19 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest19);
			repo = createChildTest(childTest19, extent, repo);
			order = createChildTest(childTest19, extent, order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC11", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC11", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest20 = extent.startTest("Navigate to Order History Cancelled");
			test.appendChild(childTest20);
			orderHistory = createChildTest(childTest20, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "Yes");

			log.debug("Leaving Inflight_Modify_Existing_Pending_Order_AfterUG");

		} catch (Exception e) {
			log.error("Error in Inflight_Modify_Existing_Pending_Order_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Hitron_XB6_PLfrom_Native_Native_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Changing Hitron modem to XB6 converged after upgrade and PL from Native to Native");
			log.debug("Entering Hitron_XB6_PLfrom_Native_Native_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC12");
			String phoneNbr = Utility.getValueFromUpgradeProp("PhoneNbrTC12");
			String convergedHardware = data.get("Converged_hardware");
			String phoneHardWareSlrNbr = Utility.getValueFromUpgradeProp("PhoneSrlNbrTC12");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			String phoneProduct = data.get("Phone_Product");
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest13 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest14 = extent.startTest("Remove Phone hardware");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.removePersonalPhoneHardware();

			ExtentTest childTest15 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.addConvergedHardwareWithSingleElement("Converged150", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);

			ExtentTest childTest16 = extent.startTest("Add Phone Number Native To Native");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.changeCustNbrPortedToNative(phoneProduct);
			order.selectPortOut();

			ExtentTest childTest17 = extent.startTest("Tech Appointment");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.enterInstallationFee("1.00");
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNo("On a date", "", "");

			ExtentTest childTest18 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest19 = extent.startTest("Review and Finish");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest20 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest21 = extent.startTest("Navigatge To E911 Job Run");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			order = createChildTest(childTest21, extent, order);
			com.navigateToCLECOrder();
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest22);
			com = createChildTest(childTest22, extent, com);
			som = createChildTest(childTest22, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest23 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest23);
			repo = createChildTest(childTest23, extent, repo);
			order = createChildTest(childTest23, extent, order);
			order.navigateToOrderValidation(33, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneHardWareSlrNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC12", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest24 = extent.startTest("Navigate to Order History Completed");
			test.appendChild(childTest24);
			orderHistory = createChildTest(childTest24, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Hitron_XB6_PLfrom_Native_Native_AfterUG");
		} catch (Exception e) {
			log.error("Error in Hitron_XB6_PLfrom_Native_Native_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Adding_Channels_Promos_Pending_Order_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Adding channels and promotions to a pending order");
			log.debug("Entering Adding_Channels_Promos_Pending_Order_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String techAppointment = data.get("Tech_Appointment");
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC13");
			String tvBoxSerialNbr = Utility.getValueFromUpgradeProp("tvBoxSlrNbrTC13");
			String tvPortalSerialNbr = Utility.getValueFromUpgradeProp("tvPortalSlrNbrTC13");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest1 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest2 = extent.startTest("Remove Pick10 Packs and Add Pramotions");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.removePick10Packs();
			order.addInternetPriceGuarentee("2YVP Int", internetProduct);

			ExtentTest childTest3 = extent.startTest("Tech Appointment");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.changesAuthorizedBy();
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest4 = extent.startTest("Review and Finish");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest5 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest6 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest6);
			com = createChildTest(childTest6, extent, com);
			som = createChildTest(childTest6, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest7 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest7);
			repo = createChildTest(childTest7, extent, repo);
			order = createChildTest(childTest7, extent, order);
			order.navigateToOrderValidation(22, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			boolean is4xgateway = repo.validateSOMIntegrationReportsSpcl(xls, "TC13", 1, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway, "Attributes are not validated successfully");
			if (!is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC13", 1, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			order.navigateToOrderValidation(23, "Yes", "No", "SOM");
			if (is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC13", 2, valuesToPassForValidation),
						"Attributes are not validated successfully");
			} else {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC13", 2, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			ExtentTest childTest8 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest8);
			repo = createChildTest(childTest8, extent, repo);
			order = createChildTest(childTest8, extent, order);
			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC13", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest9 = extent.startTest("Navigate to Order History status");
			test.appendChild(childTest9);
			orderHistory = createChildTest(childTest9, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Adding_Channels_Promos_Pending_Order_AfterUG");
		} catch (Exception e) {
			log.error("Error in Adding_Channels_Promos_Pending_Order_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Adding_VoiceMail_Secondaryline_Existing_Account_AfterUG(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Adding VoiceMail,DR and Secondary line to an existing account after upgrade");
			log.debug("Entering Adding_VoiceMail_Secondaryline_Existing_Account_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNbr = PhSrlNo;
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC14");
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest1 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest2 = extent.startTest("Add Secondary Phone And Distinctive Ring");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.addSecondaryPhone(phoneProduct, "Third Dropdown");
			order.addDistinctiveRing("Two", "3 short rings");

			ExtentTest childTest17 = extent.startTest("Tech Appointment");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.selectInstallationOption("", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest18 = extent.startTest("Review and Finish");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest5 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest10 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			order = createChildTest(childTest10, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest19 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			som = createChildTest(childTest19, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest20 = extent.startTest("Navigate to Order History status completed");
			test.appendChild(childTest20);
			orderHistory = createChildTest(childTest20, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Adding_VoiceMail_Secondaryline_Existing_Account_AfterUG");
		} catch (Exception e) {
			log.error("Error in Adding_VoiceMail_Secondaryline_Existing_Account_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Upgrade_Offering_Tripleplay_Account_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Upgrade offering for a triple play account after upgrade");
			log.debug("Entering Upgrade_Offering_Tripleplay_Account_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC15");
			String convergedHardWareSerialNbr = Utility.getValueFromUpgradeProp("convergedSrlNbrTC15");
			String techAppointment = data.get("Tech_Appointment");
			String internetProduct = data.get("Internet_Product");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest1 = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest1);
			landing = createChildTest(childTest1, extent, landing);
			order = createChildTest(childTest1, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest2 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest3 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest4 = extent.startTest("Modify Internet 75 to 150");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5 = extent.startTest("Adding price guarantee pramotions");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addInternetPriceGuarentee("", internetProduct);

			ExtentTest childTest6 = extent.startTest("Tech Appointment");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.enterInstallationFee("1.00");
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNo("", "", "");

			ExtentTest childTest7 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest8 = extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest10 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest11 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest11);
			repo = createChildTest(childTest11, extent, repo);
			order = createChildTest(childTest11, extent, order);
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC15", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(24, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC15", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest12 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest12);
			repo = createChildTest(childTest12, extent, repo);
			order = createChildTest(childTest12, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC15", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC15", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest13 = extent.startTest("Navigate to Order History status completed");
			test.appendChild(childTest13);
			orderHistory = createChildTest(childTest13, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Upgrade_Offering_Tripleplay_Account_AfterUG");
		} catch (Exception e) {
			log.error("Error in Upgrade_Offering_Tripleplay_Account_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Modify_New_Pending_Install_Account_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Inflight modify functionality for a new pending install account");
			log.debug("Entering Inflight_Modify_New_Pending_Install_Account_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC17");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardWareSerialNbr = PhSrlNo;
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String IntHardwareSrlNbr = IntHitronSrlNo;
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr = TVDctSrlNo;
			String[] hardwareType = { "DCT700" };
			String[] serialNumber = { tvHardwareSerialNbr };
			TVBoxSrlNo = Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvHardwareSerialNbr1 = TVBoxSrlNo;
			String[] hardwareType1 = { "ShawBlueSkyTVbox526" };
			String[] serialNumber1 = { tvHardwareSerialNbr };
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest1 = extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest2 = extent.startTest("Modify internet product");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order = createChildTest(childTest2, extent, order);
			order.addServiceInternet("Fibre+ 150");

			ExtentTest childTest17 = extent.startTest("Modify Internet Hardware Hiron to XB6 Converged");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.addConvergedHardwareWithoutSlNbr();
			order.selectConvergedHardware("Internet");
			order.deleteHardware("Switch Window");

			ExtentTest childTest18 = extent.startTest("Modify TV Product");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.addServiceTV("Small TV Prebuilt");

			ExtentTest childTest19 = extent.startTest("Modify TV Hardware");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.deleteTVHardware();
			order.addTVHardwareWithOutSrlNo(hardwareType);

			ExtentTest childTest20 = extent.startTest("Navigate To Tech Appointment with Retail pickup");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.selectInstallationOption("Self Connect Yes", "");
			order.techAppointmentNo("Retail Pickup", "", "");
			
			ExtentTest childTest21 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest22 = extent.startTest("Review and Finish");
			test.appendChild(childTest22);
			order = createChildTest(childTest22, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest23 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest24 = extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest24);
			order = createChildTest(childTest24, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest25 = extent.startTest("Changing TV LOB with Hardware");
			test.appendChild(childTest25);
			order = createChildTest(childTest25, extent, order);
			order.addServiceTV("Digital Classic");
			order.deleteTVHardware();
			order.addTVHardwareWithOutSrlNo(hardwareType1);
			order.selectTodoListCheckBox();

			ExtentTest childTest26 = extent.startTest("Navigate Tech Appointment");
			test.appendChild(childTest26);
			order = createChildTest(childTest26, extent, order);
			order.techAppointmentWithCurrentDate();
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest27 = extent.startTest("Review and Finish");
			test.appendChild(childTest27);
			order = createChildTest(childTest27, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest28 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest28);
			order = createChildTest(childTest28, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest29 = extent.startTest("Send serial number For Phone Hardware CFS Order");
			test.appendChild(childTest29);
			com = createChildTest(childTest29, extent, com);
			order = createChildTest(childTest29, extent, order);
			order.navigateToOrderValidation(63, "No", "No", "COM");
			com.sendSerialNbrTask(phoneHardWareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest30 = extent.startTest("Send serial number for Converged hardware");
			test.appendChild(childTest30);
			order = createChildTest(childTest30, extent, order);
			com = createChildTest(childTest30, extent, com);
			order.navigateToOrderValidation(62, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest31 = extent.startTest("Send serial number for TV hardware");
			test.appendChild(childTest31);
			order = createChildTest(childTest31, extent, order);
			com = createChildTest(childTest31, extent, com);
			order.navigateToOrderValidation(64, "No", "No", "COM");
			com.sendSerialNbrTask(tvHardwareSerialNbr1);
			order.navigateToOrderPage();

			ExtentTest childTest32 = extent.startTest("Wait For Tech Confirm Mark Finish task");
			test.appendChild(childTest32);
			com = createChildTest(childTest32, extent, com);
			order = createChildTest(childTest32, extent, order);
			order.navigateToOrderValidation(65, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest33 = extent.startTest("Navigate to E911 job run");
			test.appendChild(childTest33);
			com = createChildTest(childTest33, extent, com);
			order = createChildTest(childTest33, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest34 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest34);
			com = createChildTest(childTest34, extent, com);
			som = createChildTest(childTest34, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest35 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest35);
			repo = createChildTest(childTest35, extent, repo);
			order = createChildTest(childTest35, extent, order);
			order.navigateToOrderValidation(3, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC17", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC17", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(8, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC17", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest36 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest36);
			repo = createChildTest(childTest36, extent, repo);
			order = createChildTest(childTest36, extent, order);
			order.navigateToOrderValidation(65, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, phoneHardWareSerialNbr, convergedHardWareSerialNbr, tvHardwareSerialNbr1));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC17", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(66, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, phoneHardWareSerialNbr, convergedHardWareSerialNbr, tvHardwareSerialNbr1));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC17", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest37 = extent.startTest("Navigate to Order History status completed");
			test.appendChild(childTest37);
			orderHistory = createChildTest(childTest37, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Inflight_Modify_New_Pending_Install_Account_AfterUG");
		} catch (Exception e) {
			log.error("Error in Inflight_Modify_New_Pending_Install_Account_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Techretry_Different_SlNbr_TV_Phone_Internet_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Tech retry with different serial number for TV, Phone and Internet");
			log.debug("Entering Techretry_Different_SlNbr_TV_Phone_Internet_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC18");
			String phoneNbr = Utility.getValueFromUpgradeProp("phoneNumTC18");
			String phoneSerialNbr = Utility.getValueFromUpgradeProp("PhoneSrlNbrTC18");
			String internetHardwareSerialNbr = Utility.getValueFromUpgradeProp("IntHitronSrlNbrTC18");
			String tvHardwareSerialNbr = Utility.getValueFromUpgradeProp("tvDCTSrlNbrTC18");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String diffPhHardwareSrlNbr = PhSrlNo;
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String diffIntHardwareSrlNbr = IntHitronSrlNo;
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String diffTvHardwareSrlNbr = TVDctSrlNo;
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest11 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest12 = extent.startTest("Wait for Effective date Mark Finish task");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			order = createChildTest(childTest12, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			com.verifySVGDiagram("");
			order.navigateToOrderPage();

			ExtentTest childTest13 = extent.startTest("Send SN1 for New Phone Hardware CFS Order");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			com = createChildTest(childTest13, extent, com);
			order.navigateToOrderValidation(5, "No", "No", "COM");
			com.sendSerialNbrTask(phoneSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest14 = extent.startTest("Send SN1 for New Internet Hardware CFS Order");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			com = createChildTest(childTest14, extent, com);
			order.navigateToOrderValidation(8, "No", "No", "COM");
			com.sendSerialNbrTask(internetHardwareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest15 = extent.startTest("Send SN1 for New TV hardware CFS Order");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			order = createChildTest(childTest15, extent, order);
			som = createChildTest(childTest15, extent, som);
			order.navigateToOrderValidation(11, "No", "No", "COM");
			com.sendSerialNbrTask(tvHardwareSerialNbr);
			order.navigateToOrderPage();
			som.navigateToSOMOrder();

			ExtentTest childTest16 = extent.startTest("Send SN2 for New Phone Hardware CFS Order");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			com = createChildTest(childTest16, extent, com);
			order.navigateToOrderValidation(5, "No", "No", "COM");
			com.sendSerialNbrTask(diffPhHardwareSrlNbr);
			order.navigateToOrderPage();

			ExtentTest childTest17 = extent.startTest("Send SN2 for New Internet Hardware CFS Order");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			com = createChildTest(childTest17, extent, com);
			order.navigateToOrderValidation(8, "No", "No", "COM");
			com.sendSerialNbrTask(diffIntHardwareSrlNbr);
			order.navigateToOrderPage();

			ExtentTest childTest18 = extent.startTest("Send SN2 for New TV hardware CFS Order");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			order = createChildTest(childTest18, extent, order);
			som = createChildTest(childTest18, extent, som);
			order.navigateToOrderValidation(11, "No", "No", "COM");
			com.sendSerialNbrTask(diffTvHardwareSrlNbr);
			order.navigateToOrderPage();

			ExtentTest childTest19 = extent.startTest("Wait For Tech Confirm Mark Finished");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			order = createChildTest(childTest19, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest20 = extent.startTest("CLEC REQ BEFORE JOB RUN");
			test.appendChild(childTest20);
			com = createChildTest(childTest20, extent, com);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest21 = extent.startTest("Navigate to E911 job run");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			order = createChildTest(childTest21, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("CLEC REQ AFTER JOB RUN");
			test.appendChild(childTest22);
			com = createChildTest(childTest22, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest23 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest23);
			com = createChildTest(childTest23, extent, com);
			som = createChildTest(childTest23, extent, som);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest24 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest24);
			som = createChildTest(childTest24, extent, som);
			repo = createChildTest(childTest24, extent, repo);
			order = createChildTest(childTest24, extent, order);
			order.navigateToOrderValidation(33, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC18", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(34, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC18", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(35, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC18", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(4, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, diffPhHardwareSrlNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC18", 4, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(8, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, diffIntHardwareSrlNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC18", 5, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(12, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, diffTvHardwareSrlNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC18", 6, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest26 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest26);
			com = createChildTest(childTest26, extent, com);
			repo = createChildTest(childTest26, extent, repo);
			order = createChildTest(childTest26, extent, order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, diffPhHardwareSrlNbr,
					diffIntHardwareSrlNbr, diffTvHardwareSrlNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC18", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, diffPhHardwareSrlNbr, diffIntHardwareSrlNbr, diffTvHardwareSrlNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC18", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest27 = extent.startTest("Navigate to Order History status completed");
			test.appendChild(childTest27);
			orderHistory = createChildTest(childTest27, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Techretry_Different_SlNbr_TV_Phone_Internet_AfterUG");
		} catch (Exception e) {
			log.error("Error in Techretry_Different_SlNbr_TV_Phone_Internet_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Cancel_Pending_Account_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Inflight modify functionality for a new pending install account");
			log.debug("Entering Inflight_Cancel_Pending_Account_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC16");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest1 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.navigateToCustOrderPage("false", accntNum);
			// order.retrieveOrderInformation();

			ExtentTest childTest2 = extent.startTest("In Review screen Cancel this Order");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.cancelOrder();

			ExtentTest childTest3 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest9 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest9);
			com = createChildTest(childTest9, extent, com);
			som = createChildTest(childTest9, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest12 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest12);
			repo = createChildTest(childTest12, extent, repo);
			order = createChildTest(childTest12, extent, order);
			order.navigateToOrderValidation(54, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC16", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest10 = extent.startTest("Navigate to Order History status completed");
			test.appendChild(childTest10);
			orderHistory = createChildTest(childTest10, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Inflight_Cancel_Pending_Account_AfterUG");
		} catch (Exception e) {
			log.error("Error in Inflight_Cancel_Pending_Account_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Reinstate_with_New_Resources_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Reinstate with with new resources");
			log.debug("Entering Reinstate_with_New_Resources_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC19");
			String internetProduct = data.get("Internet_Product");
			String phoneProduct = data.get("Phone_Product");
			String tvProduct = data.get("TV_Product");
			String convergedHardware = data.get("Converged_hardware");
			TVBoxSrlNo = Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr = TVBoxSrlNo;
			TVPortalSrlNo = Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr = TVPortalSrlNo;
			String[] hardwareType = { "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber = { tvBoxSerialNbr, tvPortalSerialNbr };
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest1 = extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest2 = extent.startTest("Select Reinstate Account and Start New Order");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.reinstatePastResource("Start New Order");

			ExtentTest childTest4 = extent.startTest("Select Bill Type");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.selectBillType("Paper");

			ExtentTest childTest5 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6 = extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest7 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			// order.deleteHardware("Switch Window");

			ExtentTest childTest8 = extent.startTest("Tech Appointment");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.enterInstallationFee("1.00");
			order.selectTodo();
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNo("On a date", "", "");

			ExtentTest childTest9 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest10 = extent.startTest("Review and Finish");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest11 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest12 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			order = createChildTest(childTest12, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest13 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest13);
			com = createChildTest(childTest13, extent, com);
			som = createChildTest(childTest13, extent, som);
			// com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest18 = extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest18);
			repo = createChildTest(childTest18, extent, repo);
			order = createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(4, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC19", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(8, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC19", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(12, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway = repo.validateSOMIntegrationReportsSpcl(xls, "TC19", 3, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway, "Attributes are not validated successfully");
			if (!is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC19", 3, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			order.navigateToOrderValidation(13, "Yes", "No", "SOM");
			if (is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC19", 4, valuesToPassForValidation),
						"Attributes are not validated successfully");
			} else {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC19", 4, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			ExtentTest childTest19 = extent.startTest("Validate COM Integration Report");
			test.appendChild(childTest19);
			repo = createChildTest(childTest19, extent, repo);
			order = createChildTest(childTest19, extent, order);
			order.navigateToOrderValidation(2, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC19", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(18, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC19", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest20 = extent.startTest("Navigate to Order History status completed");
			test.appendChild(childTest20);
			orderHistory = createChildTest(childTest20, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Reinstate_with_New_Resources_AfterUG");
		} catch (Exception e) {
			log.error("Error in Reinstate_with_New_Resources_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Reinstate_with_Past_Resources_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Reinstate with past resources");
			log.debug("Entering Reinstate_with_Past_Resources_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC20");
			String phoneProduct = data.get("Phone_Product");
			String convergedHardware = data.get("Converged_hardware");
			TVBoxSrlNo = Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr = TVBoxSrlNo;
			TVPortalSrlNo = Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr = TVPortalSrlNo;
			String[] hardwareType = { "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber = { tvBoxSerialNbr, tvPortalSerialNbr };
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest20 = extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest21 = extent.startTest("Navigate To Cust Page and select Past resources");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.reinstatePastResource("Past Resources");
			order.deletePhoneLOB();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			order.selectConvergedHardware(convergedHardware);

			ExtentTest childTest23 = extent.startTest("Enter installment Fee");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.enterInstallationFee("1.00");
			order.selectTodo();

			ExtentTest childTest24 = extent.startTest("Select Bill Type");
			test.appendChild(childTest24);
			order = createChildTest(childTest24, extent, order);
			order.selectBillType("Paper");

			ExtentTest childTest25 = extent.startTest("Tech Appointment");
			test.appendChild(childTest25);
			order = createChildTest(childTest25, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNo("On a date", "", "");

			ExtentTest childTest26 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest26);
			order = createChildTest(childTest26, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest27 = extent.startTest("Review and Finish");
			test.appendChild(childTest27);
			order = createChildTest(childTest27, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest28 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest28);
			order = createChildTest(childTest28, extent, order);
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest29 = extent.startTest("Navigate To E911 JOB Run");
			test.appendChild(childTest29);
			com = createChildTest(childTest29, extent, com);
			order = createChildTest(childTest29, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest30 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest30);
			com = createChildTest(childTest30, extent, com);
			som = createChildTest(childTest30, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest31 = extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest31);
			som = createChildTest(childTest31, extent, som);
			repo = createChildTest(childTest31, extent, repo);
			order = createChildTest(childTest31, extent, order);
			order.navigateToOrderValidation(4, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC20", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(8, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC20", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(12, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			boolean is4xgateway = repo.validateSOMIntegrationReportsSpcl(xls, "TC20", 3, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway, "Attributes are not validated successfully");
			if (!is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC20", 3, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			order.navigateToOrderValidation(13, "Yes", "No", "SOM");
			if (is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC20", 4, valuesToPassForValidation),
						"Attributes are not validated successfully");
			} else {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC20", 4, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			ExtentTest childTest32 = extent.startTest("Validate COM Integration Report");
			test.appendChild(childTest32);
			com = createChildTest(childTest32, extent, com);
			repo = createChildTest(childTest32, extent, repo);
			order = createChildTest(childTest32, extent, order);
			order.navigateToOrderValidation(2, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC20", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(18, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC20", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest33 = extent.startTest("Navigate to Order History status completed");
			test.appendChild(childTest33);
			orderHistory = createChildTest(childTest33, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Reinstate_with_Past_Resources_AfterUG");
		} catch (Exception e) {
			log.error("Error in Reinstate_with_Past_Resources_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Disconnect_Staff_Internet_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Disconnect: Staff Internet");
			log.debug("Entering Disconnect_Staff_Internet_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC21");
			String internetHardwareSerialNbr = Utility.getValueFromUpgradeProp("IntHitronSrlNbrTC21");
			String techAppointment = data.get("Tech_Appointment");
			String disconnectReason = data.get("Disconnect_Reasons");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest15 = extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest16 = extent.startTest("Disconnect Employee Internet");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.disconnectLOBProducts("Internet");

			ExtentTest childTest17 = extent.startTest("Tech Appointment");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.enterInstallationFee("1.00");
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNo("", "", "");

			ExtentTest childTest18 = extent.startTest("Select Disconnect Reason");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.selectDisconnectControllabeReasons(disconnectReason);
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest19 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order); // order.selectMethodOfConfirmation("Voice");
			// order.selectDefaultInstallationCheckBox();

			ExtentTest childTest20 = extent.startTest("Review and Finish");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest21 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest22 = extent.startTest("Wait for Effective date Mark Finish task");
			test.appendChild(childTest22);
			com = createChildTest(childTest22, extent, com);
			order = createChildTest(childTest22, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest23 = extent.startTest("Charge Hardware Request for SN Mark Finish");
			test.appendChild(childTest23);
			com = createChildTest(childTest23, extent, com);
			order = createChildTest(childTest23, extent, order);
			order.navigateToOrderValidation(36, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest24 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest24);
			com = createChildTest(childTest24, extent, com);
			som = createChildTest(childTest24, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest25 = extent.startTest("Validate COM Integration Report");
			test.appendChild(childTest25);
			repo = createChildTest(childTest25, extent, repo);
			order = createChildTest(childTest25, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC21", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest26 = extent.startTest("Navigate to Order History status completed");
			test.appendChild(childTest26);
			orderHistory = createChildTest(childTest26, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Disconnect_Staff_Internet_AfterUG");
		} catch (Exception e) {
			log.error("Error in Disconnect_Staff_Internet_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void IFC_Full_Disconnect_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "IFC: Full Disconnect");
			log.debug("Entering IFC_Full_Disconnect_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC22");
			String phoneNbr = Utility.getValueFromUpgradeProp("phoneNumTC22");
			String phoneHardwareSerialNbr = Utility.getValueFromUpgradeProp("PhoneSrlNbrTC22");
			String internetHardwareSerialNbr = Utility.getValueFromUpgradeProp("IntHitronSrlNbrTC22");
			String tvHardwareSerialNbr = Utility.getValueFromUpgradeProp("tvDCXSrlNbrTC22");
			String disconnectReason = data.get("Disconnect_Reasons");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest15 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest16 = extent.startTest("Disconnect Phone, Internet and TV LOB's");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.disconnectLOBProducts("Phone");
			order.disconnectLOBProducts("Internet");
			order.disconnectLOBProducts("TV");
			// --order.selectTodo();

			ExtentTest childTest17 = extent.startTest("Select Customer Mailing Address For Disconnection");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.addCustomerMailingAddress("", "No");

			ExtentTest childTest18 = extent.startTest("Disconnect appointment date");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.disconnectAppointmentDate();

			ExtentTest childTest19 = extent.startTest("Select Disconnect Reason");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.selectDisconnectControllabeReasons(disconnectReason);
			order.selectingSalesRepresentativeCheckBox();

			/*
			 * ExtentTest childTest20 = extent.startTest("Select Method of confirmation");
			 * test.appendChild(childTest20); order = createChildTest(childTest20, extent,
			 * order); order.selectMethodOfConfirmation("Voice");
			 */

			ExtentTest childTest21 = extent.startTest("Remove portout number");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.addServicesAndFeacturesTab();
			order.selectPortOut();

			ExtentTest childTest22 = extent.startTest("Review and Finish");
			test.appendChild(childTest22);
			order = createChildTest(childTest22, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest23 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest28 = extent.startTest("Wait for effective date Mark Finish in disconnect order");
			test.appendChild(childTest28);
			com = createChildTest(childTest28, extent, com);
			order = createChildTest(childTest28, extent, order);
			order.navigateToOrderValidation(35, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest29 = extent.startTest("Charge Hardware Request for SN Mark Finish");
			test.appendChild(childTest29);
			com = createChildTest(childTest29, extent, com);
			order = createChildTest(childTest29, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");
			order.openCOMOrderPage("false", accntNum, "Yes");
			order.navigateToOrderValidation(36, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest24 = extent.startTest("Verify CLEC, COM and SOM Record counts");
			test.appendChild(childTest24);
			com = createChildTest(childTest24, extent, com);
			som = createChildTest(childTest24, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest25 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest25);
			repo = createChildTest(childTest25, extent, repo);
			order = createChildTest(childTest25, extent, order);
			order.navigateToOrderValidation(33, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC22", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(34, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC22", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(35, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC22", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest26 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest26);
			repo = createChildTest(childTest26, extent, repo);
			order = createChildTest(childTest26, extent, order);
			order.navigateToOrderValidation(35, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneHardwareSerialNbr,
					internetHardwareSerialNbr, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC22", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC22", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneHardwareSerialNbr,
					internetHardwareSerialNbr, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC22", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest27 = extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest27);
			orderHistory = createChildTest(childTest27, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving IFC_Full_Disconnect_AfterUG");
		} catch (Exception e) {
			log.error("Error in IFC_Full_Disconnect_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Activate_PhDPT_PortedTN_LifelineAlarm_No_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Activation of Phone on DPT with Ported TN (Lifeline Alarm=NO) after upgrade");
			log.debug("Entering Activate_PhDPT_PortedTN_LifelineAlarm_No_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String convergedHardWareSerialNbr = Utility.getValueFromUpgradeProp("convergedSrlNbrTC23");
			String portPhoneNbr = Utility.getValueFromUpgradeProp("portPhoneNbrTC23");
			String phoneSerialNbr = Utility.getValueFromUpgradeProp("PhoneSrlNbrTC23");
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC23");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest8 = extent.startTest("Navigate To COM Order");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest13 = extent.startTest("Navigate To LSC job Run");
			test.appendChild(childTest13);
			com = createChildTest(childTest13, extent, com);
			order = createChildTest(childTest13, extent, order);
			com.navigateToJobMonitorURL("LSC");
			order.navigateToOrderPage();

			ExtentTest childTest14 = extent.startTest("Wait for LSC Order finish Task");
			test.appendChild(childTest14);
			landing = createChildTest(childTest14, extent, landing);
			order = createChildTest(childTest14, extent, order);
			order.navigateToCLECOPSUser("true", "Yes");
			landing.loggingOffCLECOPSUser();
			landing.Login();
			order.navigateToOrderPage();

//			ExtentTest childTest15 = extent.startTest("Wait NPAC Confirm Mark finish Task");
//			test.appendChild(childTest15);
//			com = createChildTest(childTest15, extent, com);
//			order = createChildTest(childTest15, extent, order);
//			order.navigateToOrderValidation(4, "No", "No", "COM");
//			com.verifySVGDiagram("");
//			com.setMarkFinishedTask();
//			order.navigateToOrderPage();

			ExtentTest childTest16 = extent.startTest("Wait for Tech confirm Mark Finish Task");
			test.appendChild(childTest16);
			com = createChildTest(childTest16, extent, com);
			order = createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			 
			ExtentTest childTest17 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest17);
			com = createChildTest(childTest17, extent, com);
			order = createChildTest(childTest17, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest18 = extent.startTest("Navigate To BLIF Job Run");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			order = createChildTest(childTest18, extent, order);
			com.navigateToJobMonitorURL("BLIF");
			order.navigateToOrderPage();

			ExtentTest childTest19 = extent.startTest("Navigate to New SOM Phone product CFS order");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			order = createChildTest(childTest19, extent, order);
			order.navigateToOrderValidation(2, "No", "No", "SOM");
			com.navigateToServiceInformationTab();
			Assert.assertTrue(com.portOptionDisplayed(), "Port Option is displaying for New Phone CFS order");
			order.navigateToOrderPage();

			ExtentTest childTest20 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest20);
			com = createChildTest(childTest20, extent, com);
			som = createChildTest(childTest20, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest11 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest11);
			repo = createChildTest(childTest11, extent, repo);
			order = createChildTest(childTest11, extent, order);
			order.navigateToOrderValidation(2, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, portPhoneNbr, phoneSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC23", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC23", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest22);
			repo = createChildTest(childTest22, extent, repo);
			order = createChildTest(childTest22, extent, order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC23", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, portPhoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC23", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest23 = extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest23);
			orderHistory = createChildTest(childTest23, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Activate_PhDPT_PortedTN_LifelineAlarm_No_AfterUG");
		} catch (Exception e) {
			log.error("Error in Activate_PhDPT_PortedTN_LifelineAlarm_No_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Immediate_Scenarios_Add_Email_ExistingCust_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"From the immediate scenarios tab add an email for an existing customer with Internet");
			log.debug("Entering Immediate_Scenarios_Add_Email_ExistingCust_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String convergedHardWareSerialNbr = Utility.getValueFromUpgradeProp("convergedSrlNbrTC24");
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC24");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest19 = extent.startTest("Navigate to change toggle Set 'No matching email properties'");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			// order.NavigateESICusAcEmailEndpoint();

			ExtentTest childTest8 = extent.startTest("Navigate to Immediate Scenario Location To add Email");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.navigateToImmediateChangesPage(accntNum);
			order.navigateToImmediateFeatures("Email");

			ExtentTest childTest9 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest10 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest11 = extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest11);
			repo = createChildTest(childTest11, extent, repo);
			order = createChildTest(childTest11, extent, order);
			order.navigateToOrderValidation(16, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC24", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest12 = extent.startTest("Validate COM Integration Report");
			test.appendChild(childTest12);
			repo = createChildTest(childTest12, extent, repo);
			order = createChildTest(childTest12, extent, order);
			String immediateEmailAddress = com.navigateToAddEmailImmediateOrder();
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, immediateEmailAddress));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC24", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC24", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest13 = extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest13);
			orderHistory = createChildTest(childTest13, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Immediate_Scenarios_Add_Email_ExistingCust_AfterUG");
		} catch (Exception e) {
			log.error("Error in Immediate_Scenarios_Add_Email_ExistingCust_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Immediate_Scenarios_Hardware_Swap_PendingCust_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"From the immediate scenarios tab perform a Hardware Swap for a new customer with pending order");
			log.debug("Immediate_Scenarios_Hardware_Swap_PendingCust_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC25");
			String convergedHardWareSerialNo = Utility.getValueFromUpgradeProp("acctNumTC25");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String swapConvergedHarwareNbr = ConvgdSrlNo;
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest1 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest8 = extent.startTest("Navigate to Immediate Scenario Location");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.navigateToImmediateChangesPage(accntNum);

			ExtentTest childTest13 = extent.startTest("Swap Interent Converged Hardware");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			com = createChildTest(childTest13, extent, com);
			com.swapImmediateConvrgdHardware("FirstSwap", swapConvergedHarwareNbr);
			order.navigateToOrderPage();

			ExtentTest childTest19 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			som = createChildTest(childTest19, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest20 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest20);
			com = createChildTest(childTest20, extent, com);
			repo = createChildTest(childTest20, extent, repo);
			order = createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(44, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNo));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC25", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest21);
			orderHistory = createChildTest(childTest21, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Immediate_Scenarios_Hardware_Swap_PendingCust_AfterUG");
		} catch (Exception e) {
			log.error("Error in Immediate_Scenarios_Hardware_Swap_PendingCust_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Immediate_Tab_Modify_PhFeacture_ConvHw_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"From the immediate scenarios tab modify phone Features for an existing customer with Converged hardware");
			log.debug("Entering Immediate_Tab_Modify_PhFeacture_ConvHw_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneNbr = Utility.getValueFromUpgradeProp("phoneNumTC26");
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC26");
			String ConvergedHarwareNbr = Utility.getValueFromUpgradeProp("convergedSrlNbrTC26");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest1 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest8 = extent.startTest("Navigate to Immediate Scenario Location");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.navigateToImmediateChangesPage(accntNum);

			ExtentTest childTest9 = extent.startTest("Navigate to Immediate Phone Feacture");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.navigateToImmediateFeatures("Phone Features");
			order.navigateToOrderPage();

			ExtentTest childTest10 = extent.startTest("Verify COM, SOM and CLEC Record counts");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest20 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest20);
			som = createChildTest(childTest20, extent, som);
			repo = createChildTest(childTest20, extent, repo);
			order = createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(20, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, ConvergedHarwareNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC26", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(phoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC26", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest21);
			orderHistory = createChildTest(childTest21, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Immediate_Tab_Modify_PhFeacture_ConvHw_AfterUG");
		} catch (Exception e) {
			log.error("Error in Immediate_Tab_Modify_PhFeacture_ConvHw_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Immediate_Tab_Change_BillDay_Existing_Customer_AfterUG(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"From the immediate scenarios tab change the Bill day for an existing customer");
			log.debug("Entering Immediate_Tab_Change_BillDay_Existing_Customer_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC27");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest8 = extent.startTest("Navigate to Immediate Scenario Location");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.navigateToImmediateChangesPage(accntNum);

			ExtentTest childTest9 = extent.startTest("Navigate to Immediate Billing Preferences");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.navigateToImmediateFeatures("Billing Preferences");

			ExtentTest childTest10 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest11 = extent.startTest("Update Customer Details");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.updateCustomerDetails();
			order.addServicesAndFeacturesTab();
			// order.selectTodo();

			ExtentTest childTest12 = extent.startTest("Review and Finish");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest13 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest14 = extent.startTest("Navigate to run E911 Job");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			order = createChildTest(childTest14, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest15 = extent.startTest("Verify COM, SOM and CLEC Record counts");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			som = createChildTest(childTest15, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest16 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest16);
			som = createChildTest(childTest16, extent, som);
			repo = createChildTest(childTest16, extent, repo);
			order = createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(20, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC27", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest21);
			orderHistory = createChildTest(childTest21, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Immediate_Tab_Change_BillDay_Existing_Customer_AfterUG");
		} catch (Exception e) {
			log.error("Error in Immediate_Tab_Change_BillDay_Existing_Customer_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Retail_Self_Install_Order_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Retail self install order after upgrade path");
			log.debug("Entering Retail_Self_Install_Order_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String PhoneHardwareSerialNbr = PhSrlNo;
			String phoneHardware = data.get("Phone_hardware");
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC28");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			
			
			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest9 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest10 = extent.startTest("Add Phone Product");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);

			ExtentTest childTest11 = extent.startTest("Add Phone Hardware without Serial Num's");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.addPhoneHardwareWithoutSlNos(phoneHardware);

			ExtentTest childTest12 = extent.startTest("Add Tech Appointment With Retail PickUp");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.enterInstallationFee("1.00");
			order.selectInstallationOption("Self Connect Yes", "");
			order.techAppointmentNo("Retail Pickup", "", "");

			ExtentTest childTest13 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			 order.selectDefaultInstallationCheckBox();
			
			ExtentTest childTest14 = extent.startTest("Review and Finish");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest15 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest16 = extent.startTest("Navigate to Retail Pickup Page");
			test.appendChild(childTest16);
			orderHistory = createChildTest(childTest16, extent, orderHistory);
			orderHistory.navigateToOrderHistoryRetailPickupPhone("false", accntNum, PhoneHardwareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest17 = extent.startTest("Open CA in Order History page for Activation");
			test.appendChild(childTest17);
			orderHistory = createChildTest(childTest17, extent, orderHistory);
			order = createChildTest(childTest17, extent, order);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			orderHistory.navigateToOrderHistoryActivate();
			order.navigateToOrderPage();

			ExtentTest childTest18 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest19 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			order = createChildTest(childTest19, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest20 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest20);
			com = createChildTest(childTest20, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest21 = extent.startTest("Verify COM, SOM Record counts");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			com = createChildTest(childTest21, extent, com);
			som = createChildTest(childTest21, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest22 = extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest22);
			orderHistory = createChildTest(childTest22, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Retail_Self_Install_Order_AfterUG");
		} catch (Exception e) {
			log.error("Error in Retail_Self_Install_Order_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Modify_Change_OfferingTo_RetailPickUp_AfterUG(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"In-flight modifications to change the Offering details after upgrade to Retail pick up order");
			log.debug("Entering Inflight_Modify_Change_OfferingTo_RetailPickUp_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC29");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest9 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.navigateToCustOrderPage("false", accntNum);
			// order.retrieveOrderInformation();

			ExtentTest childTest11 = extent.startTest("Downgrade Internet 150 to Internet 75");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest12 = extent.startTest("Tech Appointment With Retail PickUp");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.selectInstallationOption("Self Connect Yes", "");
			order.techAppointmentNo("Retail Pickup", "", "");

			ExtentTest childTest13 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest14 = extent.startTest("Review and Finish");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest15 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest16 = extent.startTest("Verify COM, SOM Record counts");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			com = createChildTest(childTest16, extent, com);
			som = createChildTest(childTest16, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest17 = extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest17);
			orderHistory = createChildTest(childTest17, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Inflight_Modify_Change_OfferingTo_RetailPickUp_AfterUG");
		} catch (Exception e) {
			log.error("Error in Inflight_Modify_Change_OfferingTo_RetailPickUp_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Reset_Email_PWD_Customer_Modify_Order_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Reset Email password for a customer by performing modify order");
			log.debug("Entering Reset_Email_PWD_Customer_Modify_Order_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String techAppointment = data.get("Tech_Appointment");
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC30");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest9 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest10 = extent.startTest("Reset The Password by Cliking on Email");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.emailPasswordReset();

			ExtentTest childTest11 = extent.startTest("Tech Appointment");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.enterInstallationFee("1.00");
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNo("", "", "");
			order.selectingSalesRepresentativeCheckBox();
			/* order.selectDefaultInstallationCheckBox(); */

			ExtentTest childTest15 = extent.startTest("Review and Finish");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest16 = extent.startTest("Navigate To COM Order");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest17 = extent.startTest("Verify COM, SOM Record counts");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			com = createChildTest(childTest17, extent, com);
			som = createChildTest(childTest17, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest18 = extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest18);
			orderHistory = createChildTest(childTest18, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Reset_Email_PWD_Customer_Modify_Order_AfterUG");
		} catch (Exception e) {
			log.error("Error in Reset_Email_PWD_Customer_Modify_Order_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Removing_Eexisting_Email_Add_NewEmail_TriplePlay_AfterUG(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Removing existing email and adding new email after upgrade for a triple play customer");
			log.debug("Entering Removing_Eexisting_Email_Add_NewEmail_TriplePlay_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String techAppointment = data.get("Tech_Appointment");
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC31");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest9 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest10 = extent.startTest("Delete Existing Email And add New Email");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			String emailAddress = order.addInternetEmail("Yes", "Calgary");

			ExtentTest childTest11 = extent.startTest("Tech Appointment");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.enterInstallationFee("1.00");
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNo("", "", "");
			order.selectingSalesRepresentativeCheckBox();

			/*
			 * ExtentTest childTest12 = extent.startTest("Select Method of confirmation");
			 * test.appendChild(childTest12); order = createChildTest(childTest12, extent,
			 * order); order.selectMethodOfConfirmation("Voice");
			 * order.selectDefaultInstallationCheckBox();
			 */

			ExtentTest childTest13 = extent.startTest("Review and Finish");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest16 = extent.startTest("Navigate To COM SOM Orders");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest17 = extent.startTest("Verify COM, SOM Record counts");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			com = createChildTest(childTest17, extent, com);
			som = createChildTest(childTest17, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest18 = extent.startTest("Verify SOM Integration Reports");
			test.appendChild(childTest18);
			som = createChildTest(childTest18, extent, som);
			repo = createChildTest(childTest18, extent, repo);
			order = createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(16, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC31", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(45, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC31", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest19 = extent.startTest("Verify COM Integration Reports");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			repo = createChildTest(childTest19, extent, repo);
			order = createChildTest(childTest19, extent, order);
			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC31", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest20 = extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest20);
			orderHistory = createChildTest(childTest20, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Removing_Eexisting_Email_Add_NewEmail_TriplePlay_AfterUG");
		} catch (Exception e) {
			log.error("Error in Removing_Eexisting_Email_Add_NewEmail_TriplePlay_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void AUP_Suspend_existing_customer_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "AUP Suspend an existing customer after upgrade");
			log.debug("Entering AUP_Suspend_existing_customer_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC32");
			String phoneNbr = Utility.getValueFromUpgradeProp("phoneNumTC32");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest11 = extent.startTest("Navigate To COM, SOM Orders");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest12 = extent.startTest("Suspend Phone");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			com.suspendLOBProducts("Phone Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("Phone"), "Active",
					"Suspend Phone Status is not Correct");

			ExtentTest childTest13 = extent.startTest("Suspend Internet");
			test.appendChild(childTest13);
			com = createChildTest(childTest13, extent, com);
			com.suspendLOBProducts("Internet Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("Internet"), "Active",
					"Suspend Internet Status is not Correct");

			ExtentTest childTest14 = extent.startTest("Suspend TV");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			com.suspendLOBProducts("TV Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("TV"), "Active", "Suspend TV Status is not Correct");
			 

			ExtentTest childTest15 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			som = createChildTest(childTest15, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest16 = extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest16);
			som = createChildTest(childTest16, extent, som);
			repo = createChildTest(childTest16, extent, repo);
			order = createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(29, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC32", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(30, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC32", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(31, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC32", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest20 = extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest20);
			orderHistory = createChildTest(childTest20, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving AUP_Suspend_existing_customer_AfterUG");
		} catch (Exception e) {
			log.error("Error in AUP_Suspend_existing_customer_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void TCIS_Resume_TriplePlay_Account_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "TCIS: resume after upgrade for a triple play account");
			log.debug("Entering TCIS_Resume_TriplePlay_Account_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC33");
			String phoneNbr = Utility.getValueFromUpgradeProp("phoneNumTC33");
			String tvBoxSerialNbr = Utility.getValueFromUpgradeProp("tvBoxSlrNbrTC33");
			String tvPortalSerialNbr = Utility.getValueFromUpgradeProp("tvPortalSlrNbrTC33");
			String convergedHardWareSerialNbr = Utility.getValueFromUpgradeProp("convergedSrlNbrTC33");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest12 = extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest13 = extent.startTest("Click On Operations");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.navigateToOperations("Current Date");

			ExtentTest childTest14 = extent.startTest("Temporary Suspend Phone Internet and TV");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.tempSuspendLOB("Phone");
			order.tempSuspendLOB("Internet");
			order.tempSuspendLOB("TV");
			order.enterInstallationFee("1.00");

			ExtentTest childTest15 = extent.startTest("Navigate To appoinment tab");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.navigatetoAppointmentTab();
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.changesAuthorizedBy();

			ExtentTest childTest16 = extent.startTest("Review and Finish");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest17 = extent.startTest("Navigate To COM, SOM Orders");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest18 = extent.startTest("Mark Finish Task For Resume Order");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			order = createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(26, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			order.refreshPage();
			order.refreshPage();
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest19 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			som = createChildTest(childTest19, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest20 = extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest20);
			repo = createChildTest(childTest20, extent, repo);
			order = createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC33", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(28, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC33", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(27, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway = repo.validateSOMIntegrationReportsSpcl(xls, "TC33", 3, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway, "Attributes are not validated successfully");
			if (!is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC33", 3, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			order.navigateToOrderValidation(26, "Yes", "No", "SOM");
			if (is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC33", 4, valuesToPassForValidation),
						"Attributes are not validated successfully");
			} else {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC33", 4, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			repo = createChildTest(childTest21, extent, repo);
			order = createChildTest(childTest21, extent, order);
			order.navigateToOrderValidation(33, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC33", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest22);
			orderHistory = createChildTest(childTest22, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving TCIS_Resume_TriplePlay_Account_AfterUG");
		} catch (Exception e) {
			log.error("Error in TCIS_Resume_TriplePlay_Account_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Modification_TCIS_Suspend_TriplePlay_Cancel_Order_AfterUG(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Modification of a TCIS suspension order for a Triple-Play Account with DPT, Hitron and TV Legacy device and Cancelling the order");
			log.debug("Entering Modification_TCIS_Suspend_TriplePlay_Cancel_Order_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String tvProduct = data.get("TV_Product");
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC34");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest12 = extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest13 = extent.startTest("Unselect TV LOB and Select internet LOB for TCIS suspension");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.addServiceTV(tvProduct);
			order.tempSuspendLOB("Internet");

			ExtentTest childTest14 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.changesAuthorizedBy();
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest15 = extent.startTest("Review and Finish");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest16 = extent.startTest("Navigate To COM, SOM Orders");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest17 = extent.startTest("Navigate To Customer Order Page");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.navigateToCustOrderPage("false", accntNum);
			order.cancelOrder();
			order.navigateToOrderPage();

			ExtentTest childTest18 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			som = createChildTest(childTest18, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest21 = extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest21);
			orderHistory = createChildTest(childTest21, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Modification_TCIS_Suspend_TriplePlay_Cancel_Order_AfterUG");
		} catch (Exception e) {
			log.error("Error in Modification_TCIS_Suspend_TriplePlay_Cancel_Order_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void TechRetry_Same_SN_PremiseMove_Gap_BlueSKY_XB6_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Tech retry with same SN during premise move with Gap on an account with Blue SKY and XB6");
			log.debug("Entering TechRetry_Same_SN_PremiseMove_Gap_BlueSKY_XB6_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC35");
			String convergedHardWareSerialNbr = Utility.getValueFromUpgradeProp("convergedSrlNbrTC35");
			String tvBoxSerialNbr = Utility.getValueFromUpgradeProp("tvBoxSlrNbrTC35");
			String tvPortalSerialNbr = Utility.getValueFromUpgradeProp("tvPortalSlrNbrTC35");
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest12 = extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest13 = extent.startTest("Navigate to Premise move with Gap another location in Calgary");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.navigateToCustOrderWithLocId2(accntNum);

			ExtentTest childTest14 = extent.startTest("Tech Appointment");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("Yes", "");
			order.selectingSalesRepresentativeCheckBox();
			order.techAppointmentWithGAP();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest15 = extent.startTest("Review and Finish");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest16 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest17 = extent.startTest("Wait for Disconnection date Mark Finish task");
			test.appendChild(childTest17);
			com = createChildTest(childTest17, extent, com);
			order = createChildTest(childTest17, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest18 = extent.startTest("Wait for Effective date Mark Finish task");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			order = createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			com.verifySVGDiagram("");
			order.navigateToOrderPage();

			ExtentTest childTest19 = extent.startTest("Navigate to Stub URL Fail");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.navigateToOeStubServerToFail("true", "Hpsa");
			order.navigateToChangeToggleValueToFail("Phone");
			order.navigateToChangeToggleValueToFail("CableTV");
			order.switchPreviousTab();

			ExtentTest childTest20 = extent.startTest("Send same serial number for Converged Hardware");
			test.appendChild(childTest20);
			com = createChildTest(childTest20, extent, com);
			som = createChildTest(childTest20, extent, som);
			order.navigateToOrderValidation(31, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Send Same Serial number for TV Box");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			order = createChildTest(childTest21, extent, order);
			order.navigateToOrderValidation(28, "No", "No", "COM");
			String OfferingID = com.getTVHardwareType();
			if (OfferingID.contains("Gateway")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("Send Same Serial number for TV Portal");
			test.appendChild(childTest22);
			com = createChildTest(childTest22, extent, com);
			order = createChildTest(childTest22, extent, order);
			order.navigateToOrderValidation(29, "No", "No", "COM");
			String OfferingID1 = com.getTVHardwareType();
			if (OfferingID1.contains("Gateway")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();

			ExtentTest childTest23 = extent.startTest("Navigate to Stub URL Success");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.navigateToOeStubServerToPass("true", "Phone");
			order.navigateToOeStubServerToPass("false", "CableTV");
			order.switchPreviousTab();

			ExtentTest childTest24 = extent.startTest("Send same serial number for Converged Hardware");
			test.appendChild(childTest24);
			com = createChildTest(childTest24, extent, com);
			som = createChildTest(childTest24, extent, som);
			order.navigateToOrderValidation(31, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest25 = extent.startTest("Send Same Serial number for TV Box");
			test.appendChild(childTest25);
			com = createChildTest(childTest25, extent, com);
			order = createChildTest(childTest25, extent, order);
			order.navigateToOrderValidation(28, "No", "No", "COM");
			String OfferingID2 = com.getTVHardwareType();
			if (OfferingID2.contains("Gateway")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();

			ExtentTest childTest26 = extent.startTest("Send Same Serial number for TV Portal");
			test.appendChild(childTest26);
			com = createChildTest(childTest26, extent, com);
			order = createChildTest(childTest26, extent, order);
			order.navigateToOrderValidation(29, "No", "No", "COM");
			String OfferingID3 = com.getTVHardwareType();
			if (OfferingID3.contains("Gateway")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();

			ExtentTest childTest27 = extent.startTest("Wait For Tech Confirm Mark Finished");
			test.appendChild(childTest27);
			com = createChildTest(childTest27, extent, com);
			order = createChildTest(childTest27, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.verifySVGDiagram("");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest28 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest28);
			com = createChildTest(childTest28, extent, com);
			order = createChildTest(childTest28, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest29 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest29);
			com = createChildTest(childTest29, extent, com);
			som = createChildTest(childTest29, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest30 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest30);
			som = createChildTest(childTest30, extent, som);
			repo = createChildTest(childTest30, extent, repo);
			order = createChildTest(childTest30, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC35", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(28, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC35", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(26, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway = repo.validateSOMIntegrationReportsSpcl(xls, "TC35", 3, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway, "Attributes are not validated successfully");
			if (!is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC35", 3, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			order.navigateToOrderValidation(27, "Yes", "No", "SOM");
			if (is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC35", 4, valuesToPassForValidation),
						"Attributes are not validated successfully");
			} else {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC35", 4, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			order.navigateToOrderValidation(12, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway1 = repo.validateSOMIntegrationReportsSpcl(xls, "TC35", 5, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway1, "Attributes are not validated successfully");
			if (!is4xgateway1) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC35", 5, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			order.navigateToOrderValidation(13, "Yes", "No", "SOM");
			if (is4xgateway1) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC35", 6, valuesToPassForValidation),
						"Attributes are not validated successfully");
			} else {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC35", 6, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			ExtentTest childTest31 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest31);
			com = createChildTest(childTest31, extent, com);
			repo = createChildTest(childTest31, extent, repo);
			order = createChildTest(childTest31, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, convergedHardWareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC35", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, convergedHardWareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC35", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");

			ExtentTest childTest32 = extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest32);
			orderHistory = createChildTest(childTest32, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving TechRetry_Same_SN_PremiseMove_Gap_BlueSKY_XB6_AfterUG");
		} catch (Exception e) {
			log.error("Error in TechRetry_Same_SN_PremiseMove_Gap_BlueSKY_XB6_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Intra_Province_Gap_Change_LOB_During_Transfer_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Intra Province transfer with gap and change in LOB during transfer");
			log.debug("Entering Intra_Province_Gap_Change_LOB_During_Transfer_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC36");
			String convergedHardWareSerialNbr = Utility.getValueFromUpgradeProp("convergedSrlNbrTC36");
			String tvBoxSerialNbr = Utility.getValueFromUpgradeProp("tvBoxSlrNbrTC36");
			String tvPortalSerialNbr = Utility.getValueFromUpgradeProp("tvPortalSlrNbrTC36");
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest11 = extent.startTest("Navigate to Vancover Location");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.navigateToVancoverLocationId(accntNum);

			ExtentTest childTest12 = extent.startTest("Add Phone Product");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);

			ExtentTest childTest13 = extent.startTest("Add Converged Hardware for Phone");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.selectConvergedHardware("Phone");

			ExtentTest childTest14 = extent.startTest("Tech Appointment");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order); //
			order.enterInstallationFee("1.00");
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("", "");
			order.techAppointmentWithGAP();

			ExtentTest childTest15 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.navigateToAgreementDetailsTab();
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest16 = extent.startTest("Review and Finish");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest17 = extent.startTest("Navigate To COM, SOM & CLEC Orders");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest18 = extent.startTest("Wait for Disconnection Date Mark Finish task");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			order = createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest19 = extent.startTest("Wait for Effective date Mark Finish Task");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			order = createChildTest(childTest19, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			com.checkEffectiveDateStatus();
			order.navigateToOrderPage();

			ExtentTest childTest20 = extent.startTest("Send same serial number for Converged Hardware");
			test.appendChild(childTest20);
			com = createChildTest(childTest20, extent, com);
			order = createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(31, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Send Serial number for TV Box");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			order = createChildTest(childTest21, extent, order);
			order.navigateToOrderValidation(28, "No", "No", "COM");
			String OfferingID = com.getTVHardwareType();
			if (OfferingID.contains("Gateway")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("Send Serial number for TV Portal");
			test.appendChild(childTest22);
			com = createChildTest(childTest22, extent, com);
			order = createChildTest(childTest22, extent, order);
			order.navigateToOrderValidation(29, "No", "No", "COM");
			String OfferingID1 = com.getTVHardwareType();
			if (OfferingID1.contains("Gateway")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();

			ExtentTest childTest23 = extent.startTest("Wait for tect confirm Mark Finish Task");
			test.appendChild(childTest23);
			com = createChildTest(childTest23, extent, com);
			order = createChildTest(childTest23, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest24 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest24);
			com = createChildTest(childTest24, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest25 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest25);
			com = createChildTest(childTest25, extent, com);
			order = createChildTest(childTest25, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest26 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest26);
			com = createChildTest(childTest26, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest27 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest27);
			com = createChildTest(childTest27, extent, com);
			som = createChildTest(childTest27, extent, som);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest28 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest28);
			repo = createChildTest(childTest28, extent, repo);
			order = createChildTest(childTest28, extent, order);
			order.navigateToOrderValidation(30, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC36", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(31, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC36", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(28, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC36", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(26, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway = repo.validateSOMIntegrationReportsSpcl(xls, "TC36", 4, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway, "Attributes are not validated successfully");
			if (!is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC36", 4, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			order.navigateToOrderValidation(27, "Yes", "No", "SOM");
			if (is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC36", 5, valuesToPassForValidation),
						"Attributes are not validated successfully");
			} else {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC36", 5, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			ExtentTest childTest29 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest29);
			repo = createChildTest(childTest29, extent, repo);
			order = createChildTest(childTest29, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, convergedHardWareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC36", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, convergedHardWareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC36", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");

			ExtentTest childTest30 = extent.startTest("Navigate to Order History status");
			test.appendChild(childTest30);
			orderHistory = createChildTest(childTest30, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Intra_Province_Gap_Change_LOB_During_Transfer_AfterUG");
		} catch (Exception e) {
			log.error("Error in Intra_Province_Gap_Change_LOB_During_Transfer_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Removing_BlueSkyTV_XB6_Future_Dated_InterProvince_AfterUG(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Inflight changes by removing Blue SKY TV device from an account with Blue SKY TV and XB6 during Future dated Inter Province transfer");
			log.debug("Entering Inflight_Removing_BlueSkyTV_XB6_Future_Dated_InterProvince_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC37");
			String tvBoxSerialNbr = Utility.getValueFromUpgradeProp("tvBoxSlrNbrTC37");
			String tvPortalSerialNbr = Utility.getValueFromUpgradeProp("tvPortalSlrNbrTC37");
			String convergedHardWareSerialNbr = Utility.getValueFromUpgradeProp("convergedSrlNbrTC37");
			String disconnectReason = data.get("Disconnect_Reasons");
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest12 = extent.startTest("Navigate To Another Location in Calgary");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.navigateToCustOrderWithLocId2(accntNum);

			ExtentTest childTest13 = extent.startTest("Select Not selected for TV LOB");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.disconnectLOBProducts("TV");

			ExtentTest childTest14 = extent.startTest("Tech Appointment");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.enterInstallationFee("1.00");
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("Yes", "");
			order.techAppointmentWithGAP();

			ExtentTest childTest15 = extent.startTest("Select Disconnect Reason");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.selectDisconnectControllabeReasons(disconnectReason);

			ExtentTest childTest16 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.navigateToAgreementDetailsTab();
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest17 = extent.startTest("Review and Finish");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest18 = extent.startTest("Navigate To COM Order");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest19 = extent.startTest("Wait for Disconnection Date Mark Finish task");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			order = createChildTest(childTest19, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest20 = extent.startTest("Wait for Effective date Mark Finish task");
			test.appendChild(childTest20);
			com = createChildTest(childTest20, extent, com);
			order = createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Send same serial number for Converged Hardware");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			order.navigateToOrderValidation(31, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("Wait For Tech Confirm Mark Finished");
			test.appendChild(childTest22);
			com = createChildTest(childTest22, extent, com);
			order = createChildTest(childTest22, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.verifySVGDiagram("");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest23 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest23);
			com = createChildTest(childTest23, extent, com);
			som = createChildTest(childTest23, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest24 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest24);
			repo = createChildTest(childTest24, extent, repo);
			order = createChildTest(childTest24, extent, order);
			order.navigateToOrderValidation(30, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC37", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC37", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(31, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC37", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC37", 4, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(35, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC37", 5, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(36, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC37", 6, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest25 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest25);
			repo = createChildTest(childTest25, extent, repo);
			order = createChildTest(childTest25, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, convergedHardWareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC37", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC37", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC37", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest26 = extent.startTest("Navigate to Order History status");
			test.appendChild(childTest26);
			orderHistory = createChildTest(childTest26, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Inflight_Removing_BlueSkyTV_XB6_Future_Dated_InterProvince_AfterUG");
		} catch (Exception e) {
			log.error("Error in Inflight_Removing_BlueSkyTV_XB6_Future_Dated_InterProvince_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Removing_PhoneDevice_DPT_Hitron_TV_PremiseMove_CGY_CGY_AfterUG(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Inflight changes by removing Phone from an account with DPT + Hitron + Legacy TV during Premise move from CGY to CGY without gap");
			log.debug("Entering Inflight_Removing_PhoneDevice_DPT_Hitron_TV_PremiseMove_CGY_CGY_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC38");
			String phoneNbr = Utility.getValueFromUpgradeProp("phoneNumTC38");
			String phoneHardWareSerialNbr = Utility.getValueFromUpgradeProp("PhoneSrlNbrTC38");
			String internetHardWareSerialNbr = Utility.getValueFromUpgradeProp("IntHitronSrlNbrTC38");
			String tvHardWareSerialNbr = Utility.getValueFromUpgradeProp("tvDCX3510SrlNbrTC38");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest23 = extent.startTest("Navigate To COM Order");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest24 = extent.startTest("Wait for Disconnection Date Mark Finish task");
			test.appendChild(childTest24);
			com = createChildTest(childTest24, extent, com);
			order = createChildTest(childTest24, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			com.verifySVGDiagram("");
			order.navigateToOrderPage();

			ExtentTest childTest25 = extent.startTest("Wait for Effective date Mark Finish task");
			test.appendChild(childTest25);
			com = createChildTest(childTest25, extent, com);
			order = createChildTest(childTest25, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			com.verifySVGDiagram("");
			order.navigateToOrderPage();

			ExtentTest childTest26 = extent.startTest("Send same serial number for Internet Hardware");
			test.appendChild(childTest26);
			com = createChildTest(childTest26, extent, com);
			som = createChildTest(childTest26, extent, som);
			order.navigateToOrderValidation(45, "No", "No", "COM");
			com.sendSerialNbrTask(internetHardWareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest27 = extent.startTest("Send same serial number for TV Hardware");
			test.appendChild(childTest27);
			com = createChildTest(childTest27, extent, com);
			som = createChildTest(childTest27, extent, som);
			order.navigateToOrderValidation(28, "No", "Yes", "COM");
			com.sendSerialNbrTask(tvHardWareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest28 = extent.startTest("Wait For Tech Confirm Mark Finished");
			test.appendChild(childTest28);
			com = createChildTest(childTest28, extent, com);
			order = createChildTest(childTest28, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest29 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest29);
			com = createChildTest(childTest29, extent, com);
			som = createChildTest(childTest29, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest30 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest30);
			repo = createChildTest(childTest30, extent, repo);
			order = createChildTest(childTest30, extent, order);
			order.navigateToOrderValidation(33, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC38", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(29, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC38", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(30, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC38", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(31, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC38", 4, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(28, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, internetHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC38", 5, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(26, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC38", 6, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest31 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest31);
			com = createChildTest(childTest31, extent, com);
			repo = createChildTest(childTest31, extent, repo);
			order = createChildTest(childTest31, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneHardWareSerialNbr,
					internetHardWareSerialNbr, tvHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC38", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, internetHardWareSerialNbr, tvHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC38", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest32 = extent.startTest("Navigate to Order History status");
			test.appendChild(childTest32);
			orderHistory = createChildTest(childTest32, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Inflight_Removing_PhoneDevice_DPT_Hitron_TV_PremiseMove_CGY_CGY_AfterUG");
		} catch (Exception e) {
			log.error("Error in Inflight_Removing_PhoneDevice_DPT_Hitron_TV_PremiseMove_CGY_CGY_AfterUG:"
					+ e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Disconnect_Premisemove_TriplePlay_ServicesNotAvailable_targetLoc_AfterUG(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Disconnect during premise move on an account with triple play when the services are not available in the target location");
			log.debug("Entering Disconnect_Premisemove_TriplePlay_ServicesNotAvailable_targetLoc_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC39");
			String convergedHardWareSerialNbr = Utility.getValueFromUpgradeProp("convergedSrlNbrTC39");
			String tvBoxSerialNbr = Utility.getValueFromUpgradeProp("tvBoxSlrNbrTC39");
			String tvPortalSerialNbr = Utility.getValueFromUpgradeProp("tvPortalSlrNbrTC39");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest16 = extent.startTest("Navigate To COM Order");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest17 = extent.startTest("Wait for Disconnection date Mark Finish task");
			test.appendChild(childTest17);
			com = createChildTest(childTest17, extent, com);
			order = createChildTest(childTest17, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest18 = extent.startTest("Wait for Effective date Mark Finish task");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			order = createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			com.verifySVGDiagram("");
			order.navigateToOrderPage();

			ExtentTest childTest23 = extent.startTest("Send same serial number for Converged Hardware");
			test.appendChild(childTest23);
			com = createChildTest(childTest23, extent, com);
			som = createChildTest(childTest23, extent, som);
			order.navigateToOrderValidation(31, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest26 = extent.startTest("Wait For Tech Confirm Mark Finished");
			test.appendChild(childTest26);
			com = createChildTest(childTest26, extent, com);
			order = createChildTest(childTest26, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.verifySVGDiagram("");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest27 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest27);
			com = createChildTest(childTest27, extent, com);
			som = createChildTest(childTest27, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest28 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest28);
			som = createChildTest(childTest28, extent, som);
			repo = createChildTest(childTest28, extent, repo);
			order = createChildTest(childTest28, extent, order);
			order.navigateToOrderValidation(35, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			boolean is4xgateway = repo.validateSOMIntegrationReportsSpcl(xls, "TC39", 1, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway, "Attributes are not validated successfully");
			if (!is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC39", 1, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			order.navigateToOrderValidation(36, "Yes", "No", "SOM");
			if (is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC39", 2, valuesToPassForValidation),
						"Attributes are not validated successfully");
			} else {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC39", 2, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			ExtentTest childTest29 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest29);
			com = createChildTest(childTest29, extent, com);
			repo = createChildTest(childTest29, extent, repo);
			order = createChildTest(childTest29, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, convergedHardWareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC39", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC39", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest30 = extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest30);
			orderHistory = createChildTest(childTest30, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Disconnect_Premisemove_TriplePlay_ServicesNotAvailable_targetLoc_AfterUG");
		} catch (Exception e) {
			log.error("Error in Disconnect_Premisemove_TriplePlay_ServicesNotAvailable_targetLoc_AfterUG:"
					+ e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "After Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Collection_Suspend_Existing_Customer_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Collection Suspend an existing customer after upgrade");
			log.debug("Entering Collection_Suspend_Existing_Customer_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC40");
			String phoneNbr = Utility.getValueFromUpgradeProp("phoneNumTC40");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest1 = extent.startTest("Navigate To COM Order");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest2 = extent.startTest("Trigger the Collection Suspend via RestAPI");
			test.appendChild(childTest2);
			soapRest = createChildTest(childTest2, extent, soapRest);
			soapRest.navigateToHitCollectionSuspension("TC40", accntNum);

			ExtentTest childTest3 = extent.startTest("Navigate To COM Order");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest4 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest4);
			com = createChildTest(childTest4, extent, com);
			som = createChildTest(childTest4, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest5 = extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest5);
			repo = createChildTest(childTest5, extent, repo);
			order = createChildTest(childTest5, extent, order);
			order.navigateToOrderValidation(29, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC40", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(30, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC40", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			/*
			 * order.navigateToOrderValidation(31, "Yes", "No", "SOM");
			 * valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum)); boolean
			 * is4xgateway=repo.validateSOMIntegrationReportsSpcl(xls, "TC40", 3,
			 * valuesToPassForValidation);
			 * softAssert.assertTrue(is4xgateway,"Attributes are not validated successfully"
			 * ); if(!is4xgateway) { valuesToPassForValidation=new
			 * ArrayList<>(Arrays.asList(accntNum));
			 * softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC40", 3,
			 * valuesToPassForValidation),"Attributes are not validated successfully"); }
			 * order.navigateToOrderPage();
			 * 
			 * order.navigateToOrderValidation(13, "Yes", "No", "SOM"); if(is4xgateway) {
			 * valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			 * softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC40", 4,
			 * valuesToPassForValidation),"Attributes are not validated successfully"); }
			 * else { valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			 * softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC40", 4,
			 * valuesToPassForValidation),"Attributes are not validated successfully"); }
			 * order.navigateToOrderPage();
			 */

			ExtentTest childTest6 = extent.startTest("Navigate to Order History status");
			test.appendChild(childTest6);
			orderHistory = createChildTest(childTest6, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Collection_Suspend_Existing_Customer_AfterUG");
		} catch (Exception e) {
			log.error("Error in Collection_Suspend_Existing_Customer_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Collection_Resume_VOD_PPV_AfterUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Collection: resume after upgrade for VOD and PPV");
			log.debug("Entering Collection_Resume_VOD_PPV_AfterUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String accntNum = Utility.getValueFromUpgradeProp("acctNumTC41");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest1 = extent.startTest("Navigate To COM Order");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest2 = extent.startTest("Trigger the Resume Request via RestAPI");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			soapRest = createChildTest(childTest2, extent, soapRest);
			soapRest.navigateToHitCollectionResume("TC41", accntNum);

			ExtentTest childTest3 = extent.startTest("Navigate To COM Order");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest4 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest4);
			com = createChildTest(childTest4, extent, com);
			som = createChildTest(childTest4, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest5 = extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest5);
			repo = createChildTest(childTest5, extent, repo);
			order = createChildTest(childTest5, extent, order);
			order.navigateToOrderValidation(29, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC41", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(30, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC41", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(31, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC41", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(25, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC41", 4, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(26, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC41", 5, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(27, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC41", 6, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest6 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest6);
			orderHistory = createChildTest(childTest6, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Collection_Resume_VOD_PPV_AfterUG");
		} catch (Exception e) {
			log.error("Error in Collection_Resume_VOD_PPV_AfterUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	public static void main(String args[]) {
		InputStream log4j = AfterUpgrade.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
		PropertyConfigurator.configure(log4j);
	}
}
