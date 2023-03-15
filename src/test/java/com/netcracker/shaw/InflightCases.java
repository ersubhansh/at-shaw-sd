package com.netcracker.shaw;

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
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.netcracker.shaw.setup.SeleniumTestUp;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 *         Aug 10, 2018
 */

public class InflightCases extends SeleniumTestUp {
	LandingPage landing = null;
	OrderCreationPage order = null;
	COMOrdersPage com = null;
	SOMOrderPage som = null;
	ValidateReport repo = null;
	OrderHistoryPage orderHistory = null;
	SoftAssert softAssert = new SoftAssert();
	ArrayList<String> valuesToPassForValidation = new ArrayList<String>();

	Logger log = Logger.getLogger(InflightCases.class);

	@Test(dataProvider = "getTestData", description = "Golden Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Cancel(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Validate Inflight cancel functionality for a pending install account");
			log.debug("Entering Inflight_Cancel");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			IntACSrlNo = Utility.generateHardwareSerailNum(IntACSrlNo);
			String internetHardwareSerialNbr = IntACSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Internet Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			// order.deleteConvergedHardware();

			ExtentTest childTest5 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("Yes", "");

			ExtentTest childTest6 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest7 = extent.startTest("Select default installation Todo msg");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest8 = extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");
			order.refreshPage();

			ExtentTest childTest10 = extent.startTest("Navigate To Customer Order Page");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			//order.navigateToCustOrderPage("true", accntNum);
			order.navigateToCustOrderPage("false", accntNum);
			order.cancelOrder();

			ExtentTest childTest11 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			som = createChildTest(childTest11, extent, som);
			// com.switchPreviousTab();
			order.openCOMOrderPage("true", accntNum, "");
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest12 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest12);
			orderHistory = createChildTest(childTest12, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Inflight_Cancel");
		} catch (Exception e) {
			log.error("Error in Inflight_Cancel:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Golden Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Modify_Wifi_Fail(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Perform in-flight modify on a account when the Wi-Fi provisioning task of the order fails");
			log.debug("Entering Inflight_Modify_Wifi_Fail");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			String tvProduct = data.get("TV_Product");
			String internetHardware = data.get("Internet_hardware");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardwareSerialNbr = PhSrlNo;
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr = TVDctSrlNo;
			String[] hardwareType = { "DCT700" };
			String[] serialNumber = { tvHardwareSerialNbr };
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Information");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);

			ExtentTest childTest4 = extent.startTest("Add Phone Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneHardwareSerialNbr, phoneHardware);

			ExtentTest childTest5 = extent.startTest("Add TV Product");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);

			ExtentTest childTest6 = extent.startTest("Add TV Hardware");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest7 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest8 = extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest10 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			order = createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest13 = extent.startTest("Navigate To Stub to wifi fail");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.navigateToOeStubServerToFail("true", "Hpsa");
			order.navigateToChangeToggleValueToFail("Wifi");

			ExtentTest childTest14 = extent.startTest("Add Internet Product");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.switchPreviousTab();
			order.navigateToCustOrderPage("false", accntNum);
			order.addServiceInternet("Fibre+ 150");

			ExtentTest childTest15 = extent.startTest("Add Internet Hardware");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			// order.deleteConvergedHardware();

			ExtentTest childTest16 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.selectInstallationOption("Self Connect No", "Yes");
			order.techAppoinmentYes("Yes", "");

			ExtentTest childTest17 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest18 = extent.startTest("Select default installation fee Todo check box");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest19 = extent.startTest("Review and Finish");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest20 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest21 = extent.startTest("Navigate to Stub to Pass wifi");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.navigateToOeStubServerToPass("true", "Wifi");
			
			ExtentTest childTest22 = extent.startTest("Downgrade Fibre+ 150 to Fibre+ 75");
			test.appendChild(childTest22);
			order = createChildTest(childTest22, extent, order);
			order.switchPreviousTab();
			//order.navigateToCustOrderPage("false", accntNum);
			order.navigateToCustOrderPage("true", accntNum);
			order.addServiceInternet("Fibre+ 75");

			ExtentTest childTest23 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.techAppointmentWithCurrentDate();

			ExtentTest childTest24 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest24);
			order = createChildTest(childTest24, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.scrollToTop();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest25 = extent.startTest("Review and Finish");
			test.appendChild(childTest25);
			order = createChildTest(childTest25, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest26 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest26);
			order = createChildTest(childTest26, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest27 = extent.startTest("Send serial number for Internet to complete Provisioning");
			test.appendChild(childTest27);
			order = createChildTest(childTest27, extent, order);
			com = createChildTest(childTest27, extent, com);
			order.navigateToOrderValidation(57, "No", "No", "COM");
			com.sendSerialNbrTask(internetHardwareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest28 = extent.startTest("Retry the wifi provisioning task");
			test.appendChild(childTest28);
			order = createChildTest(childTest28, extent, order);
			com = createChildTest(childTest28, extent, com);
			order.navigateToOrderValidation(17, "No", "No", "SOM");
			com.completeFourceRetryTask();
			order.navigateToOrderPage();

			ExtentTest childTest29 = extent.startTest("Wait for tech confirm Mark finish Task");
			test.appendChild(childTest29);
			order = createChildTest(childTest29, extent, order);
			com = createChildTest(childTest29, extent, com);
			order.navigateToOrderValidation(58, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest30 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest30);
			com = createChildTest(childTest30, extent, com);
			som = createChildTest(childTest30, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest31 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest31);
			repo = createChildTest(childTest31, extent, repo);
			order = createChildTest(childTest31, extent, order);
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC6", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(17, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC6", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest32 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest32);
			repo = createChildTest(childTest32, extent, repo);
			order = createChildTest(childTest32, extent, order);
			order.navigateToOrderValidation(58, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC6", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(61, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneHardwareSerialNbr,
					tvHardwareSerialNbr, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC6", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest33 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest33);
			orderHistory = createChildTest(childTest33, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Inflight_Modify_Wifi_Fail");
		} catch (Exception e) {
			log.error("Error in Inflight_Modify_Wifi_Fail:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Modify_Existing_Active_Order(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Validate Inflight modify functionality for an existing account with active order");
			log.debug("Entering Inflight_Modify_Existing_Active_Order");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			String internetProduct = data.get("Internet_Product");
			String techAppointment = data.get("Tech_Appointment");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);

			ExtentTest childTest4 = extent.startTest("Add Internet Product");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);

			ExtentTest childTest6 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest7 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest8 = extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9 = extent.startTest("Navigate To COM and SOM Order");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest10 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			order = createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest13 = extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest14 = extent.startTest("Add Voicemail & Call Waiting");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.selectVoiceMail();

			ExtentTest childTest15 = extent.startTest("Downgrade Fibre+ 150 to Fibre+ 75");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.addServiceInternet("Fibre+ 75");

			ExtentTest childTest16 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.NavigateTechAppointmentTab();
			order.enteringChangesAuthorizedBy();
			order.forceAppointmentWithFutureDate("");

			ExtentTest childTest17 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest18 = extent.startTest("Review and Finish Order");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest19 = extent.startTest("Navigate To COM and SOM Order");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest20 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest21 = extent.startTest("Add home phone jack Install");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.addPhoneHardware(phoneHardware, "Home Phone Jack Install");
			order.addPhoneHardware("", "LD Calling Plans");

			ExtentTest childTest22 = extent.startTest("Upgrade Fibre+ 75 to Fibre+ 150");
			test.appendChild(childTest22);
			order = createChildTest(childTest22, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest23 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.selectInstallationOption("", "");

			ExtentTest childTest24 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest24);
			order = createChildTest(childTest24, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest25 = extent.startTest("Review and Finish Order");
			test.appendChild(childTest25);
			order = createChildTest(childTest25, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest26 = extent.startTest("Navigate To COM and SOM Order");
			test.appendChild(childTest26);
			order = createChildTest(childTest26, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest27 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest27);
			com = createChildTest(childTest27, extent, com);
			som = createChildTest(childTest27, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest28 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest28);
			repo = createChildTest(childTest28, extent, repo);
			order = createChildTest(childTest28, extent, order);
			order.navigateToOrderValidation(20, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC23", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC23", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest29 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest29);
			repo = createChildTest(childTest29, extent, repo);
			order = createChildTest(childTest29, extent, order);
			order.navigateToOrderValidation(58, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC23", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(61, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC23", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest30 = extent.startTest("Navigate to Order History Cancelled");
			test.appendChild(childTest30);
			orderHistory = createChildTest(childTest30, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Inflight_Modify_Existing_Active_Order");
		} catch (Exception e) {
			log.error("Error in Inflight_Modify_Existing_Active_Order:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Modify_Pending_Install_Account(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Validate Inflight modify functionality for a pending install account");
			log.debug("Entering Inflight_Modify_Pending_Install_Account");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String phoneHardware = data.get("Phone_hardware");
			String internetHardware = data.get("Internet_hardware");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNo = PhSrlNo;
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String diffPhHardwareSrlNbr = PhSrlNo;
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String diffIntHardwareSrlNbr = IntHitronSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Phone, Voice mail");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNum = order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();

			ExtentTest childTest4 = extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneSerialNo, phoneHardware);

			ExtentTest childTest5 = extent.startTest("Add Internet Product");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6 = extent.startTest("Add Internet hardware");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			// order.deleteConvergedHardware();

			ExtentTest childTest7 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("", "");

			ExtentTest childTest8 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest9 = extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest11 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest12 = extent.startTest("Add Distinctive Ring");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.navigateToCustOrderPage("false", accntNum);
			order.addDistinctiveRing("One", "3 short rings");

			ExtentTest childTest13 = extent.startTest("Add Internet Email and IP Address");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.addInternetEmail("", "Calgary");

			ExtentTest childTest14 = extent.startTest("Navigate To Tech Appointment With Current Date");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.techAppointmentWithCurrentDate();

			ExtentTest childTest15 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest16 = extent.startTest("Review and Finish");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest17 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest18 = extent.startTest("Navigate To New Phone Hardware CFS Order");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			com = createChildTest(childTest18, extent, com);
			order.navigateToOrderValidation(7, "No", "No", "COM");
			com.sendSerialNbrTask(diffPhHardwareSrlNbr);
			order.navigateToOrderPage();

			ExtentTest childTest19 = extent.startTest("Navigate To New Internet Hardware CFS Order");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			com = createChildTest(childTest19, extent, com);
			order.navigateToOrderValidation(9, "No", "No", "COM");
			com.sendSerialNbrTask(diffIntHardwareSrlNbr);
			order.navigateToOrderPage();

			ExtentTest childTest20 = extent.startTest("Wait for tech confirm mark finish task");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			com = createChildTest(childTest20, extent, com);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			com.verifyCLECRequestBeforeJobRun();
			com.verifyCLECRequestE911Record();

			ExtentTest childTest22 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest22);
			com = createChildTest(childTest22, extent, com);
			order = createChildTest(childTest21, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest23 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest23);
			com = createChildTest(childTest23, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest24 = extent.startTest("Verify COM and SOM Record counts");
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
			order.navigateToOrderValidation(3, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, diffPhHardwareSrlNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC27", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC27", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest26 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest26);
			repo = createChildTest(childTest26, extent, repo);
			order = createChildTest(childTest26, extent, order);
			order.navigateToOrderValidation(59, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, diffPhHardwareSrlNbr, phoneNum, diffIntHardwareSrlNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC27", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(60, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, diffPhHardwareSrlNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC27", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest27 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest27);
			orderHistory = createChildTest(childTest27, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Inflight_Modify_Pending_Install_Account");
		} catch (Exception e) {
			log.error("Error in Inflight_Modify_Pending_Install_Account:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Cancel_existing_account_with_pending_order(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Validate Inflight cancel functionality for an existing account with pending order");
			log.debug("Entering Inflight_Cancel_existing_account_with_pending_order");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String convergedHardware = data.get("Converged_hardware");
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr = TVDctSrlNo;
			String[] hardwareType = { "DCT700" };
			String[] serialNumber = { tvHardwareSerialNbr };
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNo = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add TV with DCT TV Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNo);
			order.addConvergedHardware1(convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);

			ExtentTest childTest6 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

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
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest10 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest11 = extent.startTest("Modify Internet and TV");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.addServiceInternet("Fibre+ 75");
			order.addServiceTV("Digital Classic");

			ExtentTest childTest12 = extent.startTest("Navigate To Tech Appointment Yes with Future date");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.selectInstallationOption("Self Connect No", "Yes");
			order.techAppoinmentYes("Yes", "");

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
			order.openCOMOrderPage("false", accntNum, "");
			order.refreshPage();

			ExtentTest childTest16 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.navigateToCustOrderPage("false", accntNum);
			order.cancelOrder();

			ExtentTest childTest17 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest18 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			som = createChildTest(childTest18, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest19 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest19);
			orderHistory = createChildTest(childTest19, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Inflight_Cancel_existing_account_with_pending_order");
		} catch (Exception e) {
			log.error("Error in Inflight_Cancel_existing_account_with_pending_order:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Modify_Addition_Phone_Jack(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "In-flight modify order with addition of phone jack");
			log.debug("Entering Inflight_Modify_Addition_Phone_Jack");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String convergedHardware = data.get("Converged_hardware");
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr = TVDctSrlNo;
			String[] hardwareType = { "DCT700" };
			String[] serialNumber = { tvHardwareSerialNbr };
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNo = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();

			ExtentTest childTest4 = extent.startTest("Add Internet Product");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5 = extent.startTest("Add TV with DCT TV Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest6 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);
			// order.selectConvergedHardware("Internet");
			// order.deleteHardware("Switch Window");

			ExtentTest childTest7 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest8 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest9 = extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest11 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest12 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			order = createChildTest(childTest12, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest13 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest13);
			com = createChildTest(childTest13, extent, com);
			com.verifyCLECRequestAfterJobRun();
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest14 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest15 = extent.startTest("Add Digital Channel Discounts");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.addDigitalChannelDiscounts();

			ExtentTest childTest16 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNoCurrentDate("", true);

			ExtentTest childTest17 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest18 = extent.startTest("Review and Finish");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest19 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest20 = extent.startTest("Navigate To Modify Customer Order");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			com = createChildTest(childTest20, extent, com);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.verifySVGDiagram("");
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest22 = extent.startTest("Add home phone jack Install");
			test.appendChild(childTest22);
			order = createChildTest(childTest22, extent, order);
			order.addPhoneHardware(phoneHardware, "Home Phone Jack Install");

			ExtentTest childTest23 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest25 = extent.startTest("Review and Finish");
			test.appendChild(childTest25);
			order = createChildTest(childTest25, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest26 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest26);
			order = createChildTest(childTest26, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest27 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest27);
			com = createChildTest(childTest27, extent, com);
			som = createChildTest(childTest27, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest28 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest28);
			orderHistory = createChildTest(childTest28, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Inflight_Modify_Addition_Phone_Jack");
		} catch (Exception e) {
			log.error("Error in Inflight_Modify_Addition_Phone_Jack:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Modify_Changing_InstallType_Retail_Pickup(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"In-flight modify by changing the install type to retail pickup");
			log.debug("Entering Inflight_Modify_Changing_InstallType_Retail_Pickup");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			IntACSrlNo = Utility.generateHardwareSerailNum(IntACSrlNo);
			String internetHardwareSerialNbr = IntACSrlNo;
			TVHDSrlNo = Utility.generateHardwareSerailNum(TVHDSrlNo);
			String tvHardwareSerialNbr = TVHDSrlNo;
			// String[] hardwareType={"ShawBlueSkyTVbox526","ShawBlueSkyTVportal416"};
			// String[] serialNumber={tvBoxSerialNbr,tvPortalSerialNbr};
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Internet Hardware without Serial Num's");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addInternetHardwareWithoutSlNos("Cisco");

			ExtentTest childTest5 = extent.startTest("Add TV Product");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);

			ExtentTest childTest6 = extent.startTest("Add TV Hardware without Serial Num's");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addServiceTVWithBlueSkyWithoutSlNos("Limited TV");

			ExtentTest childTest7 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectInstallationOption("Self Connect Yes", "");
			order.techAppointmentNo("Mailout", "Service Location", "");
//			order.techAppointmentNo("Mailout", "Special shipping", "");

			ExtentTest childTest8 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest9 = extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10 = extent.startTest("Navigate To COM and SOM Order");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest11 = extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest12 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.selectInstallationOption("Self Connect Yes", "");
			order.selectingSalesRepresentativeCheckBox();
			order.techAppointmentNo("Retail Pickup", "", "");

			ExtentTest childTest13 = extent.startTest("Review and Finish");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest14 = extent.startTest("Navigate To COM and SOM Order");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest15 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest15);
			orderHistory = createChildTest(childTest15, extent, orderHistory);
			order = createChildTest(childTest15, extent, order);
			orderHistory.navigateToOrderHistoryRetailPickupTwoLOBs("true", accntNum, internetHardwareSerialNbr,
					tvHardwareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest16 = extent.startTest("Navigate To COM and SOM Order");
			test.appendChild(childTest16);
			com = createChildTest(childTest16, extent, com);
			som = createChildTest(childTest16, extent, som);
			som.navigateToSOMOrder();
			com.navigateToCOMOrder();

			ExtentTest childTest17 = extent.startTest("Navigate To SVG Wait for Resume Provisioning");
			test.appendChild(childTest17);
			com = createChildTest(childTest17, extent, com);
			order = createChildTest(childTest17, extent, order);
			order.navigateToOrderValidation(59, "No", "No", "COM");
			com.verifySVGDiagram("");
			order.navigateToOrderPage();

			ExtentTest childTest18 = extent.startTest("Open CA in Order History page for Activation");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			orderHistory = createChildTest(childTest18, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			orderHistory.navigateToOrderHistoryActivate();
			order.navigateToOrderPage();

			ExtentTest childTest19 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			som = createChildTest(childTest19, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest20 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest20);
			orderHistory = createChildTest(childTest20, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			orderHistory.navigateToWorkOrdersLink();

			log.debug("Leaving Inflight_Modify_Changing_InstallType_Retail_Pickup");
		} catch (Exception e) {
			log.error("Error in Inflight_Modify_Changing_InstallType_Retail_Pickup:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void InFlight_Remove_Internet(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"In flight order should not be submitted when Internet is removed after Provisioning task is completed");
			log.debug("Entering InFlight_Remove_Internet");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr = TVDctSrlNo;
			String[] hardwareType = { "DCT700" };
			String[] serialNumber = { tvHardwareSerialNbr };
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);

			ExtentTest childTest5 = extent.startTest("Add TV Product");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);

			ExtentTest childTest6 = extent.startTest("Add TV Hardware");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest7 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("", "");

			ExtentTest childTest8 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest9 = extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");
			order.refreshPage();

			ExtentTest childTest11 = extent.startTest("Wait For Effective Date Mark Finish");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			com = createChildTest(childTest11, extent, com);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest12 = extent.startTest("Send serial number Converged Hardware");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			order = createChildTest(childTest12, extent, order);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest13 = extent.startTest("Send Serial number For TV");
			test.appendChild(childTest13);
			com = createChildTest(childTest13, extent, com);
			order = createChildTest(childTest13, extent, order);
			order.navigateToOrderValidation(11, "No", "No", "COM");
			com.sendSerialNbrTask(tvHardwareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest14 = extent.startTest("Navigate To Customer Order Page");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest15 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			som = createChildTest(childTest15, extent, som);
			// com.switchPreviousTab();
			order.openCOMOrderPage("true", accntNum, "");
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest16 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest16);
			com = createChildTest(childTest16, extent, com);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving InFlight_Remove_Internet");
		} catch (Exception e) {
			log.error("Error in InFlight_Remove_Internet:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Removing_BlueSKYTV_XB6_PremiseMove_CGY_CGY_WithGap(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Validate an account to change the disconnection & Activation dates only if LOBS are kept unchanged during in-flight modify of a premise move order");
			log.debug("Entering Inflight_Removing_BlueSKYTV_XB6_PremiseMove_CGY_CGY_WithGap");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String convergedHardware = data.get("Converged_hardware");
			String tvProduct = data.get("TV_Product");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			// added internet hardware for test purpouse
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			TVBoxSrlNo = Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr = TVBoxSrlNo;
			TVPortalSrlNo = Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr = TVPortalSrlNo;
			String[] hardwareType = { "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber = { tvBoxSerialNbr, tvPortalSerialNbr };
			String techAppointment = data.get("Tech_Appointment");
			String disconnectReason = data.get("Disconnect_Reasons");
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);

			ExtentTest childTest5 = extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			order.selectTodo();

			ExtentTest childTest6 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
//			order.mocaFilterSelecttion("");

			ExtentTest childTest7 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest8 = extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9 = extent.startTest("Navigate To COM, SOM Orders");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest10 = extent.startTest("Navigate To Check COM, SOM Initial Records Counts");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest11 = extent.startTest("Navigate to CGY Location");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.navigateToExistingAccountForPremise(accntNum);

			ExtentTest childTest12 = extent.startTest("Disconnect TV Product");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.disconnectLOBProducts("TV");

			ExtentTest childTest13 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			// order.enterInstallationFee("1.00");
			order.selectInstallationOption("Self Connect No", "Yes");
			order.techAppoinmentYes("Yes", "");
			order.selectingSalesRepresentativeCheckBox();
			order.techAppointmentWithGAP();

			ExtentTest childTest14 = extent.startTest("Select Disconnect Reason");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.selectDisconnectControllabeReasons(disconnectReason);

			ExtentTest childTest15 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.navigateToAgreementDetailsTab();
			order.selectMethodOfConfirmation("Voice");
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest16 = extent.startTest("Review and Finish");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.reviewAndFinishOrder();

			// new code
			ExtentTest childTest28 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest28);
			order = createChildTest(childTest28, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest36 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest36);
			order = createChildTest(childTest36, extent, order);
			// order.enterInstallationFee("1.00");
			// order.selectInstallationOption("Self Connect No", "Yes");
			// order.techAppoinmentYes("Yes", "");
			order.techAppoinmentYesFutureDate("Yes", "");
			order.selectingSalesRepresentativeCheckBox();
			order.techAppointmentWithGAP();

			ExtentTest childTest31 = extent.startTest("Review and Finish");
			test.appendChild(childTest31);
			order = createChildTest(childTest31, extent, order);
			order.reviewAndFinishOrder();

			// from here old code continue
			ExtentTest childTest17 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest18 = extent.startTest("Wait for Disconnection Date Mark Finished Task");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			order = createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest19 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest19);
			repo = createChildTest(childTest19, extent, repo);
			order = createChildTest(childTest19, extent, order);
			order.navigateToOrderValidation(30, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC111", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, convergedHardWareSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC111", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest20 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest20);
			repo = createChildTest(childTest20, extent, repo);
			order = createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC111", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Wait for effective date Mark Finish Task");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			order = createChildTest(childTest21, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("Send Serial Number for Converged Hardware");
			test.appendChild(childTest22);
			com = createChildTest(childTest22, extent, com);
			order = createChildTest(childTest22, extent, order);
			order.navigateToOrderValidation(31, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();

			// sending serial no for TV hardware
			/*
			 * ExtentTest childTest28 =
			 * extent.startTest("Send Serial Number for TV gateway");
			 * test.appendChild(childTest28); com = createChildTest(childTest28, extent,
			 * com); order = createChildTest(childTest28, extent, order);
			 * order.navigateToOrderValidation(28, "No", "No", "COM");
			 * com.sendSerialNbrTask(tvBoxSerialNbr); order.navigateToOrderPage();
			 * 
			 * ExtentTest childTest29 =
			 * extent.startTest("Send Serial Number for Portal TV");
			 * test.appendChild(childTest29); com = createChildTest(childTest29, extent,
			 * com); order = createChildTest(childTest29, extent, order);
			 * order.navigateToOrderValidation(29, "No", "No", "COM");
			 * com.sendSerialNbrTask(tvPortalSerialNbr); order.navigateToOrderPage();
			 */

			ExtentTest childTest23 = extent.startTest("Wait for Tech Confirm Mark Finished Task");
			test.appendChild(childTest23);
			com = createChildTest(childTest23, extent, com);
			order = createChildTest(childTest23, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest35 = extent.startTest("Wait for Tech Confirm Mark Finished Task");
			test.appendChild(childTest35);
			com = createChildTest(childTest35, extent, com);
			order = createChildTest(childTest35, extent, order);
			order.navigateToOrderValidation(36, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest38 = extent.startTest("Wait for Tech Confirm Mark Finished Task");
			test.appendChild(childTest38);
			com = createChildTest(childTest38, extent, com);
			order = createChildTest(childTest38, extent, order);
			order.navigateToOrderValidation(37, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest24 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest24);
			com = createChildTest(childTest24, extent, com);
			som = createChildTest(childTest24, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest25 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest25);
			repo = createChildTest(childTest25, extent, repo);
			order = createChildTest(childTest25, extent, order);
			order.navigateToOrderValidation(28, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC111", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			/*
			 * order.navigateToOrderValidation(35, "Yes", "No", "SOM");
			 * valuesToPassForValidation=new
			 * ArrayList<>(Arrays.asList(accntNum,tvBoxSerialNbr, "4xgateway")); boolean
			 * is4xgateway=repo.validateSOMIntegrationReportsSpcl(xls, "TC111", 4,
			 * valuesToPassForValidation);
			 * softAssert.assertTrue(is4xgateway,"Attributes are not validated successfully"
			 * ); if(!is4xgateway) { valuesToPassForValidation=new
			 * ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
			 * softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC111", 4,
			 * valuesToPassForValidation),"Attributes are not validated successfully"); }
			 * order.navigateToOrderPage();
			 * 
			 * order.navigateToOrderValidation(36, "Yes", "No", "SOM"); if(is4xgateway) {
			 * valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,
			 * tvPortalSerialNbr, "5xportal"));
			 * softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC111", 5,
			 * valuesToPassForValidation),"Attributes are not validated successfully"); }
			 * else { valuesToPassForValidation=new
			 * ArrayList<>(Arrays.asList(accntNum,tvBoxSerialNbr, "4xgateway"));
			 * softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC111", 5,
			 * valuesToPassForValidation),"Attributes are not validated successfully"); }
			 * order.navigateToOrderPage();
			 */

			ExtentTest childTest26 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest26);
			repo = createChildTest(childTest26, extent, repo);
			order = createChildTest(childTest26, extent, order);
			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC111", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest27 = extent.startTest("Navigate to Order History status");
			test.appendChild(childTest27);
			com = createChildTest(childTest27, extent, com);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Inflight_Removing_BlueSKYTV_XB6_PremiseMove_CGY_CGY_WithGap");
		} catch (Exception e) {
			log.error("Error in Inflight_Removing_BlueSKYTV_XB6_PremiseMove_CGY_CGY_WithGap:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Cancel_Activation_PremiseMove_Order(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Inflight cancel activation for premise move order");
			log.debug("Entering Inflight_Cancel_Activation_PremiseMove_Order");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String phoneHardware = data.get("Phone_hardware");
			String internetHardware = data.get("Internet_hardware");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardwareSerialNbr = PhSrlNo;
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr = TVDctSrlNo;
			String[] hardwareType = { "DCT700" };
			String[] serialNumber = { tvHardwareSerialNbr };
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);

			ExtentTest childTest4 = extent.startTest("Add Phone Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneHardwareSerialNbr, phoneHardware);

			ExtentTest childTest5 = extent.startTest("Add Internet Product");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6 = extent.startTest("Add Internet Hardware");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			// order.deleteConvergedHardware();

			ExtentTest childTest7 = extent.startTest("Add TV Product");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.addServiceTV(tvProduct);

			ExtentTest childTest8 = extent.startTest("Add TV Hardware");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest9 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest10 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest11 = extent.startTest("Review and Finish");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest12 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest13 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest13);
			com = createChildTest(childTest13, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest14 = extent.startTest("Navigate To E911 JOB RUN");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			order = createChildTest(childTest14, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest15 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			com.verifyCLECRequestAfterJobRun();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest16 = extent.startTest("Transfer Premisemove Account to 2777 Location with Gap");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.navigateToVancoverLocationId(accntNum);
			order.addServicePhone("", phoneProduct);

			ExtentTest childTest17 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.selectInstallationOption("Self Connect No", "Yes");
			order.techAppoinmentYes("", "");
			order.techAppointmentWithGAP();
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();
			order.selectPortOut();

			ExtentTest childTest18 = extent.startTest("Review and Finish");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest19 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest20 = extent.startTest("Wait for Disconnection Date Mark Finished Task");
			test.appendChild(childTest20);
			com = createChildTest(childTest20, extent, com);
			som = createChildTest(childTest20, extent, som);
			order = createChildTest(childTest10, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			com.navigateToCOMOrder();
			som.navigateToSOMOrder();
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest21 = extent.startTest("Navigate To Customer Order Page To Cancel the Order");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.navigateToRetrievePremiseMove(accntNum);
			order.enterInstallationFee("1.00");
			order.cancelOrderTNNumber("Cancle Transfer", "Yes");
			order.navigatetoAppointmentTab();
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest22 = extent.startTest("Review and Finish");
			test.appendChild(childTest22);
			order = createChildTest(childTest22, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest23 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest24 = extent.startTest("Wait for Effective Date Mark Finished Task");
			test.appendChild(childTest24);
			com = createChildTest(childTest24, extent, com);
			order = createChildTest(childTest24, extent, order);
			order.navigateToOrderValidation(58, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest25 = extent.startTest("Send serial Nbr for Phone Hardware");
			test.appendChild(childTest25);
			com = createChildTest(childTest25, extent, com);
			order = createChildTest(childTest25, extent, order);
			order.navigateToOrderValidation(48, "No", "No", "COM");
			com.sendSerialNbrTask(phoneHardwareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest26 = extent.startTest("Send serial Nbr for Internet Hardware");
			test.appendChild(childTest26);
			com = createChildTest(childTest26, extent, com);
			order = createChildTest(childTest26, extent, order);
			order.navigateToOrderValidation(45, "No", "No", "COM");
			com.sendSerialNbrTask(internetHardwareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest27 = extent.startTest("Send Serial number For TV");
			test.appendChild(childTest27);
			com = createChildTest(childTest27, extent, com);
			order = createChildTest(childTest27, extent, order);
			order.navigateToOrderValidation(51, "No", "No", "COM");
			com.sendSerialNbrTask(tvHardwareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest28 = extent.startTest("Wait for tech confirm mark finish Task");
			test.appendChild(childTest28);
			order = createChildTest(childTest28, extent, order);
			com = createChildTest(childTest28, extent, com);
			order.navigateToOrderValidation(58, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest29 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest29);
			com = createChildTest(childTest29, extent, com);
			order = createChildTest(childTest29, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest30 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest30);
			order = createChildTest(childTest30, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest31 = extent.startTest("Navigate To Vancover Location 2777");
			test.appendChild(childTest31);
			order = createChildTest(childTest31, extent, order);
			order.navigateToVancoverLocationId(accntNum);

			ExtentTest childTest32 = extent.startTest("Assign a Phone number to Each Line");
			test.appendChild(childTest32);
			order = createChildTest(childTest32, extent, order);
			order.navigateToVancoverLocationId(accntNum);
			order.addServicePhone("", phoneProduct);

			ExtentTest childTest33 = extent.startTest("Add Internet Product");
			test.appendChild(childTest33);
			order = createChildTest(childTest33, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet("Fibre+ 75");

			ExtentTest childTest34 = extent.startTest("Add TV Product");
			test.appendChild(childTest34);
			order = createChildTest(childTest34, extent, order);
			order.addServiceTV("Digital Classic");
			order.selectPortOut();

			ExtentTest childTest35 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest35);
			order = createChildTest(childTest35, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest36 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest36);
			order = createChildTest(childTest36, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest37 = extent.startTest("Review and Finish");
			test.appendChild(childTest37);
			order = createChildTest(childTest37, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest38 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest38);
			order = createChildTest(childTest38, extent, order);
			//order.openCOMOrderPage("false", accntNum, "Yes");
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest39 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest39);
			com = createChildTest(childTest39, extent, com);
			order = createChildTest(childTest9, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			com.navigateToCLECOrder();

			ExtentTest childTest40 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest40);
			com = createChildTest(childTest40, extent, com);
			som = createChildTest(childTest40, extent, som);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			// com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest41 = extent.startTest("Navigate to Order History status");
			test.appendChild(childTest41);
			com = createChildTest(childTest41, extent, com);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Inflight_Cancel_Activation_PremiseMove_Order");
		} catch (Exception e) {
			log.error("Error in Inflight_Cancel_Activation_PremiseMove_Order:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void DoNotAutoCancel_Inflight_Modify_DirectFulfilment_HWRecall_ForIntLOB(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Do not Auto-cancel for Inflight modify of Modify orders with Direct Fulfillment option and Hardware Recall checkbox selected for Internet LOB");
			log.debug("Entering DoNotAutoCancel_Inflight_Modify_DirectFulfilment_HWRecall_ForIntLOB");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String tvProduct = data.get("TV_Product");
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNo = ConvgdSrlNo;
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr = TVDctSrlNo;
			String[] hardwareType = { "DCT700" };
			String[] serialNumber = { tvHardwareSerialNbr };
			String techAppointment = data.get("Tech_Appointment");
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");
			order.addServicesAndFeacturesTab();

			ExtentTest childTest3 = extent.startTest("Add Internet With Hitron hardware");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			// order.deleteConvergedHardware();

			ExtentTest childTest4 = extent.startTest("Add TV With DCT Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest5 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest6 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest7 = extent.startTest("Review and Finish");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest8 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			com = createChildTest(childTest8, extent, com);
			som = createChildTest(childTest8, extent, som);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest9 = extent.startTest("Navigate To verify COM, SOM initial Counts ");
			test.appendChild(childTest9);
			com = createChildTest(childTest9, extent, com);
			som = createChildTest(childTest9, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest10 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest11 = extent.startTest("Delete Hitron And Add Xb6 Converged Hardware Without Srl Nbr");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			//order.addConvergedHardwareWithoutSlNbr();
			order.addConvergedHardware1(convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);			
			order.deleteHardware("Switch Window");

			ExtentTest childTest12 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.selectInstallationOption("Self Connect Yes", "");
			//order.techAppointmentNo("Mailout", "Service Location", "");
			//order.techAppointmentNo("Mailout", "Special shipping", "");
			order.techAppointmentNo("Retail Pickup", "", "");
			order.selectingSalesRepresentativeCheckBox();
			order.addServicesAndFeacturesTab();
			order.selectTodo();

			/*
			 * ExtentTest childTest13 = extent.startTest("Select Method of confirmation");
			 * test.appendChild(childTest13); order = createChildTest(childTest13, extent,
			 * order); order.selectMethodOfConfirmation("Voice");
			 * 
			 * ExtentTest childTest13 =
			 * extent.startTest("Checking the Hardware recall Button");
			 * test.appendChild(childTest13); order = createChildTest(childTest13, extent,
			 * order); order.verifyHardwareRecallBtnEnabled();
			 */

			ExtentTest childTest14 = extent.startTest("Review and Finish");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest15 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest16 = extent.startTest("Navigate to Putty and Time Shifting 15 days");
			test.appendChild(childTest16);
			com = createChildTest(childTest16, extent, com);
			com.navigateToConnectPuttyAndTimeShift("15");
			landing.Login();

			/*
			 * ExtentTest childTest17=extent.
			 * startTest("Navigate DB To Clear Reset Cache And NCDO Caches");
			 * test.appendChild(childTest17);
			 * order=createChildTest(childTest17,extent,order); landing.Login();
			 * order.navigateToConnectDB(); order.navigateToNCdoResetCaches();
			 */

			ExtentTest childTest18 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest19 = extent.startTest("Navigate To SVG Diagram");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			com = createChildTest(childTest19, extent, com);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.verifySVGDiagram("");
			order.navigateToOrderPage();
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest20 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest20);
			com = createChildTest(childTest20, extent, com);
			som = createChildTest(childTest20, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest21 = extent.startTest("Navigate to Order History status");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving DoNotAutoCancel_Inflight_Modify_DirectFulfilment_HWRecall_ForIntLOB");

		} catch (Exception e) {
			log.error("Error in DoNotAutoCancel_Inflight_Modify_DirectFulfilment_HWRecall_ForIntLOB:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void AutoCancel_AfterXDays_NewOrder_RetailPickup_Yes(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify Auto Cancel after X days for New order with Retail Pickup=Yes");
			log.debug("Entering AutoCancel_AfterXDays_NewOrder_RetailPickup_Yes");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String convergedHardware = data.get("Converged_hardware");
			String[] hardwareType = { "DCT700" };
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest1 = extent
					.startTest("Verify In integration setting parameters Auto Cancel for Retail Pickup Monitoring");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			landing = createChildTest(childTest1, extent, landing);
			order.navigateIntegrationSettingParamsAutoCancelforRetailPickup();
			landing.openUrl();

			ExtentTest childTest2 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest4 = extent.startTest("Add Phone and Internet Product");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware without Serial Nbr");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addConvergedHardwareWithoutSlNbr();
			order.selectConvergedHardware(convergedHardware);
			// order.selectConvergedHardware("Internet");
			// order.deleteHardware("Switch Window");

			ExtentTest childTest6 = extent.startTest("Add TV Without DCT Hardware");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithOutSrlNo(hardwareType);

			ExtentTest childTest7 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectInstallationOption("Self Connect Yes", "");
			order.techAppointmentNo("Retail Pickup", "", "");

			ExtentTest childTest8 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest9 = extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest11 = extent.startTest("Navigate to Putty and Time Shifting 15 days");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			com.navigateToConnectPuttyAndTimeShift("15");
			landing.Login();

			/*
			 * ExtentTest childTest12=extent.
			 * startTest("Navigate To DB to Clear Cache And NCDO Reset Caches");
			 * test.appendChild(childTest12);
			 * order=createChildTest(childTest12,extent,order); landing.Login();
			 * order.navigateToConnectDB(); order.navigateToNCdoResetCaches();
			 */

			ExtentTest childTest13 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			/*
			 * ExtentTest childTest14 = extent.startTest("CLEC Request Before Job Run");
			 * test.appendChild(childTest14); com = createChildTest(childTest14, extent,
			 * com); com.verifyCLECRequestBeforeJobRun();
			 * 
			 * ExtentTest childTest15 = extent.startTest("Navigate To E911 Job Run");
			 * test.appendChild(childTest15); com = createChildTest(childTest15, extent,
			 * com); order = createChildTest(childTest15, extent, order);
			 * com.navigateToJobMonitorURL("E911"); order.navigateToOrderPage();
			 * 
			 * ExtentTest childTest16 = extent.startTest("CLEC Request After Job Run");
			 * test.appendChild(childTest16); com = createChildTest(childTest16, extent,
			 * com); com.verifyCLECRequestAfterJobRun();
			 * com.verifyRecordsCountInCLECOrder(clecOrderCount);
			 */

			ExtentTest childTest17 = extent.startTest("Verify COM SOM Record Counts");
			test.appendChild(childTest17);
			com = createChildTest(childTest17, extent, com);
			som = createChildTest(childTest17, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest18 = extent.startTest("Navigate to Order History status");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving AutoCancel_AfterXDays_NewOrder_RetailPickup_Yes");
		} catch (Exception e) {
			log.error("Error in AutoCancel_AfterXDays_NewOrder_RetailPickup_Yes:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Generate_Store_Task_Should_Not_Fail_InFlight(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Generate/Store task should not fail for in-flight");
			log.debug("Entering Generate_Store_Task_Should_Not_Fail_InFlight");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String convergedHardware = data.get("Converged_hardware");
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();

			ExtentTest childTest1 = extent.startTest("Change stub to change AWS PUT mode to No response");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.navigateToChangeToggleValueToFail("AWS PUT");

			ExtentTest childTest2 = extent.startTest("Navigate to login OE page");
			test.appendChild(childTest2);
			landing = createChildTest(childTest2, extent, landing);
			landing.Login();
			landing.openUrl();

			ExtentTest childTest3 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest4 = extent.startTest("Add Internet Product");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5 = extent.startTest("Add Internet Hardware without Srl Nbr");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addConvergedHardwareWithoutSlNbr();
			order.selectConvergedHardware(convergedHardware);
			// order.selectTodo();

			ExtentTest childTest6 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("Yes", "");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest7 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest8 = extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest10 = extent.startTest("Navigate to Stub to success AWS PUT mode");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.navigateToOeStubServerToPass("false", "AWS PUT");

			ExtentTest childTest11 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest12 = extent.startTest("Adding IP address for internet");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.addEmailsIPAddressSimCardForInternet("", "IP Range");

			ExtentTest childTest13 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.NavigateTechAppointmentTab();
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest14 = extent.startTest("Review and Finish");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.reviewAndFinishOrder();
			order.navigateToOrderPage();

			ExtentTest childTest15 = extent.startTest("Verify COM SOM Record Counts");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			som = createChildTest(childTest15, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest16 = extent.startTest("Verify Generate/Store contract status");
			test.appendChild(childTest16);
			com = createChildTest(childTest16, extent, com);
			order = createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(59, "No", "Yes", "COM");
			com.verifyGenerateStoreContractStatus();

			ExtentTest childTest17 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest17);
			orderHistory = createChildTest(childTest17, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Generate_Store_Task_Should_Not_Fail_InFlight");
		} catch (Exception e) {
			log.error("Error in Generate_Store_Task_Should_Not_Fail_InFlight:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider = "getTestData", description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Modify_New_Install_Order(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Validate Inflight modify functionality for a new install order");
			log.debug("Entering Inflight_Modify_New_Install_Order");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNo = PhSrlNo;
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String diffPhHardwareSrlNbr = PhSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Add Phone");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.addServicesAndFeacturesTab();
			String portPhoneNbr = order.portPhoneNumber("Phone Required", "", phoneProduct, "", "No", "No");

			ExtentTest childTest3 = extent.startTest("Add Phone hardware");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addPhoneHardware(phoneSerialNo, phoneHardware);
//			order.selectTodoListCheckBox();

			ExtentTest childTest4 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);	
			order.changesAuthorizedBy();

			ExtentTest childTest5 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest6 = extent.startTest("Review and Finish");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest7 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			String accntNum = order.getAccountNumber();
			String orderid = order.getOrderNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest8 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest9 = extent.startTest("Add Phone");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.addServicesAndFeacturesTab();
			String portPhoneNbr1 = order.portPhoneNumber("", "", phoneProduct, "Native To Ported", "Yes", "No");
			order.portPhoneTextValidation();

			ExtentTest childTest10 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.changesAuthorizedBy();
			order.techAppoinmentYes("Yes", "");
			order.techAppointmentTextValidation();

			ExtentTest childTest11 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest12 = extent.startTest("Review and Finish");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.reviewAndFinishOrder();

//			String accntNum = "56311789238";
//			
//			  String orderid = "00015501";

			ExtentTest childTest13 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
//			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest14 = extent.startTest("Navigate to Putty and Time Shifting 5 days");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			com.navigateToConnectPuttyAndTimeShift("5");
			landing.Login();

			ExtentTest childTest15 = extent.startTest("Navigate To LSC Response Job Run");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			order = createChildTest(childTest15, extent, order);
			com.navigateToJobMonitorURL("LSC");
			order.navigateToOrderPage();

			ExtentTest childTest16 = extent.startTest("Send serial number For Phone Hardware CFS Order");
			test.appendChild(childTest16);
			com = createChildTest(childTest16, extent, com);
			order = createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(7, "No", "No", "COM");
			com.sendSerialNbrTask(phoneSerialNo);
			order.navigateToOrderPage();

			ExtentTest childTest17 = extent.startTest("Verify COM SOM Record Counts");
			test.appendChild(childTest17);
			com = createChildTest(childTest17, extent, com);
			som = createChildTest(childTest17, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest18 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest18);
			repo = createChildTest(childTest18, extent, repo);
			order = createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(59, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC163", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

//			order.navigateToOrderValidation(59, "Yes", "No", "COM");
//			valuesToPassForValidation = new ArrayList<>(Arrays.asList(orderid));
//			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC163", 2, valuesToPassForValidation),
//					"Attributes are not validated successfully");
//			order.navigateToOrderPage();

			ExtentTest childTest19 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest19);
			orderHistory = createChildTest(childTest19, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Inflight_Modify_New_Install_Order");
		} catch (Exception e) {
			log.error("Error in Inflight_Modify_New_Install_Order:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	
	public static void main(String args[]) {
		InputStream log4j = InflightCases.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
		PropertyConfigurator.configure(log4j);
	}
}
