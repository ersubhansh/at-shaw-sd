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

public class DisconnectCases extends SeleniumTestUp {

	LandingPage landing = null;
	OrderCreationPage order = null;
	COMOrdersPage com = null;
	SOMOrderPage som = null;
	ValidateReport repo = null;
	SoapUIRestAPIpage soapRest = null;
	OrderHistoryPage orderHistory = null;
	SoftAssert softAssert = new SoftAssert();
	ArrayList<String> valuesToPassForValidation = new ArrayList<String>();

	Logger log = Logger.getLogger(DisconnectCases.class);

	@Test(dataProvider = "getTestData", description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void TriplePlay_XB6Converged_Bluesky_Disconnect_TVLOB(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"For a Triple Play account with XB6-Converged and Blue-sky Device, disconnect the TV LOB");
			log.debug("Entering TriplePlay_XB6Converged_Bluesky_Disconnect_TVLOB");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			TVBoxSrlNo = Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr = TVBoxSrlNo;
			TVPortalSrlNo = Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr = TVPortalSrlNo;
			String[] hardwareType = { "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber = { tvBoxSerialNbr, tvPortalSerialNbr };
			String disconnectReason = data.get("Disconnect_Reasons");
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

			ExtentTest childTest3 = extent.startTest("Add Phone, Voice mail");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();

			ExtentTest childTest4 = extent.startTest("Add Internet Product");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5 = extent.startTest("Add TV Product");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);

			ExtentTest childTest6 = extent.startTest("Add TV Hardware");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest7 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);

			ExtentTest childTest8 = extent.startTest("Select Todo List Check Box");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.selectTodoListCheckBox();

			ExtentTest childTest9 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techActivationOption("");

			ExtentTest childTest10 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest11 = extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest12 = extent.startTest("Review and Finish");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest13 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest14 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest15 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			order = createChildTest(childTest15, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest16 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest16);
			com = createChildTest(childTest16, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest17 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest18 = extent.startTest("Disconnect TV Product");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.disconnectLOBProducts("TV");

			ExtentTest childTest19 = extent.startTest("Navigate to Tech Appointment Tab");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.NavigateTechAppointmentTab();

			ExtentTest childTest20 = extent.startTest("Select Disconnect Reason");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.selectDisconnectControllabeReasons(disconnectReason);

			ExtentTest childTest21 = extent.startTest("Select Method of confirmation For Internet");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.navigateToAgreementDetailsTab();
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest22 = extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest22);
			order = createChildTest(childTest22, extent, order);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest23 = extent.startTest("Review and Finish");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest24 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest24);
			order = createChildTest(childTest24, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest25 = extent.startTest("Wait for Effective Mark Finish Task");
			test.appendChild(childTest25);
			com = createChildTest(childTest25, extent, com);
			order = createChildTest(childTest25, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest26 = extent.startTest("Wait for Grace Period 4xgateway Mark Finish Task");
			test.appendChild(childTest26);
			com = createChildTest(childTest26, extent, com);
			order = createChildTest(childTest26, extent, order);
			order.navigateToOrderValidation(36, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest27 = extent.startTest("Wait for Grace Period 5xportal Mark Finish Task");
			test.appendChild(childTest27);
			com = createChildTest(childTest27, extent, com);
			order = createChildTest(childTest27, extent, order);
			order.navigateToOrderValidation(37, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest28 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest28);
			com = createChildTest(childTest28, extent, com);
			som = createChildTest(childTest28, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest29 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest29);
			repo = createChildTest(childTest29, extent, repo);
			order = createChildTest(childTest29, extent, order);
			order.navigateToOrderValidation(35, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway = repo.validateSOMIntegrationReportsSpcl(xls, "TC20", 1, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway, "Attributes are not validated successfully");
			if (!is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC20", 2, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();
			order.navigateToOrderValidation(36, "Yes", "No", "SOM");
			if (is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC20", 2, valuesToPassForValidation),
						"Attributes are not validated successfully");
			} else {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC20", 1, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			ExtentTest childTest30 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest30);
			repo = createChildTest(childTest30, extent, repo);
			order = createChildTest(childTest30, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			is4xgateway = repo.validateCOMIntegrationReportsSpcl(xls, "TC20", 1, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway, "Attributes are not validated successfully");
			if (!is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC20", 1, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();
			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC20", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest31 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest31);
			orderHistory = createChildTest(childTest31, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving TriplePlay_XB6Converged_Bluesky_Disconnect_TVLOB");
		} catch (Exception e) {
			log.error("Error in TriplePlay_XB6Converged_Bluesky_Disconnect_TVLOB:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Tripleplay_DPT_Cisco_legacyTV_perform_Full_Disconnect(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"For a Triple play account with DPT, Cisco Advanced Wi-Fi modem and legacy TV device, perform a full account disconnect");
			log.debug("Entering Tripleplay_DPT_Cisco_legacyTV_perform_Full_Disconnect");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			String internetHardware = data.get("Internet_hardware");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardwareSerialNbr = PhSrlNo;
			String internetProduct = data.get("Internet_Product");
			IntACSrlNo = Utility.generateHardwareSerailNum(IntACSrlNo);
			String internetHardwareSerialNbr = IntACSrlNo;
			String tvProduct = data.get("TV_Product");
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr = TVDctSrlNo;
			String[] hardwareType = { "DCT700" };
			String[] serialNumber = { tvHardwareSerialNbr };
			String disconnectReason = data.get("Disconnect_Reasons");
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

			ExtentTest childTest3 = extent.startTest("Add Phone, Voice mail");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();

			ExtentTest childTest4 = extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneHardwareSerialNbr, phoneHardware);

			ExtentTest childTest5 = extent.startTest("Add Internet Product");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
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
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest9 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.selectInstallationOption("", techAppointment);

			ExtentTest childTest10 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest11 = extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest12 = extent.startTest("Review and Finish");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest13 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest14 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest15 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			order = createChildTest(childTest15, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest16 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest16);
			com = createChildTest(childTest16, extent, com);
			com.verifyCLECRequestAfterJobRun();
			order.openCOMOrderPage("true", accntNum, "Yes");
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest17 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest18 = extent.startTest("Disconnect Phone, Internet and TV LOB's");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.disconnectLOBProducts("Phone");
			order.disconnectLOBProducts("Internet");
			order.disconnectLOBProducts("TV");

			ExtentTest childTest19 = extent.startTest("Remove Portout Number");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.selectPortOut();

			ExtentTest childTest20 = extent.startTest("Select Customer Mailing Address For Disconnection");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.addCustomerMailingAddress("", "No");

			ExtentTest childTest21 = extent.startTest("Disconnect appointment date");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.disconnectAppointmentDate();

			ExtentTest childTest22 = extent.startTest("Select Disconnect Reason");
			test.appendChild(childTest22);
			order = createChildTest(childTest22, extent, order);
			order.selectDisconnectControllabeReasons(disconnectReason);

			ExtentTest childTest23 = extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest24 = extent.startTest("Remove Portout Numbers");
			test.appendChild(childTest24);
			order = createChildTest(childTest24, extent, order);
			order.addServicesAndFeacturesTab();
			order.selectPortOut();

			ExtentTest childTest25 = extent.startTest("Review and Finish");
			test.appendChild(childTest25);
			order = createChildTest(childTest25, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest26 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest26);
			order = createChildTest(childTest26, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest27 = extent.startTest("Disconnect customer mark finish task");
			test.appendChild(childTest27);
			com = createChildTest(childTest27, extent, com);
			order = createChildTest(childTest27, extent, order);
			order.navigateToOrderValidation(35, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			/*
			 * ExtentTest childTest28 =
			 * extent.startTest("Wait for Grace Period Internet Mark Finish Task");
			 * test.appendChild(childTest28); com = createChildTest(childTest28, extent,
			 * com); order = createChildTest(childTest28, extent, order);
			 * order.navigateToOrderValidation(37, "No", "No", "COM");
			 * com.setMarkFinishedTask(); order.navigateToOrderPage();
			 * 
			 * ExtentTest childTest29 =
			 * extent.startTest("Wait for Grace Period Phone Mark Finish Task");
			 * test.appendChild(childTest29); com = createChildTest(childTest29, extent,
			 * com); order = createChildTest(childTest29, extent, order);
			 * order.navigateToOrderValidation(38, "No", "No", "COM");
			 * com.setMarkFinishedTask(); order.navigateToOrderPage();
			 */
			ExtentTest childTest30 = extent.startTest("Verify CLEC, COM and SOM Record counts");
			test.appendChild(childTest30);
			com = createChildTest(childTest30, extent, com);
			som = createChildTest(childTest30, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest31 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest31);
			repo = createChildTest(childTest31, extent, repo);
			order = createChildTest(childTest31, extent, order);
			order.navigateToOrderValidation(33, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC58", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(34, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC58", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(35, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC58", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest32 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest32);
			repo = createChildTest(childTest32, extent, repo);
			order = createChildTest(childTest32, extent, order);
			order.navigateToOrderValidation(35, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneHardwareSerialNbr,
					internetHardwareSerialNbr, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC58", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC58", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneHardwareSerialNbr,
					internetHardwareSerialNbr, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC58", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest33 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest33);
			com = createChildTest(childTest33, extent, com);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Tripleplay_DPT_Cisco_legacyTV_perform_Full_Disconnect");
		} catch (Exception e) {
			log.error("Error in Tripleplay_DPT_Cisco_legacyTV_perform_Full_Disconnect:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void No_Notification_To_ESB_Disconnect_Affordable_Offering(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"No Notification to ESB when we disconnect the \"Affordable Internet Offering\" Service");
			log.debug("Entering No_Notification_To_ESB_Disconnect_Affordable_Offering");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			IntACSrlNo = Utility.generateHardwareSerailNum(IntACSrlNo);
			String internetHardwareSerialNbr = IntACSrlNo;
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr = TVDctSrlNo;
			String[] hardwareType = { "DCT700" };
			String[] serialNumber = { tvHardwareSerialNbr };
			String techAppointment = data.get("Tech_Appointment");
			String disconnectReason = data.get("Disconnect_Reasons");
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
			landing.openISEDPinUrl();

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
			order.addInternetHardware("Cisco", internetHardwareSerialNbr);

			ExtentTest childTest5 = extent.startTest("Add TV Product");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);

			ExtentTest childTest6 = extent.startTest("Add TV Hardware");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest8 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);

			ExtentTest childTest9 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest10 = extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest12 = extent.startTest("Review and Finish");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest13 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest14 = extent.startTest("Navigate To Cust Order Page without ISED Pin and PostalCode");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest15 = extent.startTest("Verify ISED values in Customer Tab");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			String ISEDpin = order.validateCustomerTabforISED();

			ExtentTest childTest16 = extent.startTest("Unselect Affordable Internet and Hardware");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.addServicesAndFeacturesTab();
			order.disconnectLOBProducts("Internet");

			ExtentTest childTest17 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.selectInstallationOption("", "Yes");
			order.techAppoinmentYes("Yes", "");

			ExtentTest childTest18 = extent.startTest("Select Disconnect Reason");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.selectDisconnectControllabeReasons(disconnectReason);
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest19 = extent.startTest("Select Default Installation Fee Check Box");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest20 = extent.startTest("Review and Finish");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest21 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest22 = extent.startTest("Wait For Effective Date Mark Finished Task");
			test.appendChild(childTest22);
			com = createChildTest(childTest22, extent, com);
			order = createChildTest(childTest22, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			/*
			 * ExtentTest childTest23=extent.
			 * startTest("Wait for Grace Period Internet Mark Finish Task");
			 * test.appendChild(childTest23); com=createChildTest(childTest23, extent, com);
			 * order=createChildTest(childTest23, extent, order);
			 * order.navigateToOrderValidation(36, "No", "No", "COM");
			 * com.setMarkFinishedTask(); order.navigateToOrderPage();
			 */

			ExtentTest childTest24 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest24);
			com = createChildTest(childTest24, extent, com);
			som = createChildTest(childTest24, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest25 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest25);
			som = createChildTest(childTest25, extent, som);
			repo = createChildTest(childTest25, extent, repo);
			order = createChildTest(childTest25, extent, order);
			order.navigateToOrderValidation(34, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC77", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest26 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest26);
			com = createChildTest(childTest26, extent, com);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving No_Notification_To_ESB_Disconnect_Affordable_Offering");

		} catch (Exception e) {
			log.error("Error in No_Notification_To_ESB_Disconnect_Affordable_Offering:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Disconnect_PhoneLOB_Number_Verification(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify Phone number on Overview page when Phone LOB is Disconnected");
			log.debug("Entering Disconnect_PhoneLOB_Number_Verification");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardWareSerialNbr = PhSrlNo;
			IntACSrlNo = Utility.generateHardwareSerailNum(IntACSrlNo);
			String internetHardwareSerialNbr = IntACSrlNo;
			String disconnectReason = data.get("Disconnect_Reasons");
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

			ExtentTest childTest3 = extent.startTest("Add Phone and DPT Hardware");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addPhoneHardware(phoneHardWareSerialNbr, phoneHardware);

			ExtentTest childTest4 = extent.startTest("Add Internet Service");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);

			ExtentTest childTest5 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.selectInstallationOption("", techAppointment);

			ExtentTest childTest6 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest7 = extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
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
			order.openCOMOrderPage("true", accntNum, "Yes");
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest13 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest14 = extent.startTest("Disconnect Phone LOB");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.disconnectLOBProducts("Phone");

			ExtentTest childTest15 = extent.startTest("Remove Portout Number");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.selectPortOut();

			ExtentTest childTest16 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.navigatetoAppointmentTab();

			ExtentTest childTest17 = extent.startTest("Select Disconnect Reason");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.selectDisconnectControllabeReasons(disconnectReason);

			ExtentTest childTest18 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.navigateToAgreementDetailsTab();
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest19 = extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest20 = extent.startTest("Review and Finish");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest21 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest22 = extent.startTest("Wait for Effective Mark Finish Task");
			test.appendChild(childTest22);
			com = createChildTest(childTest22, extent, com);
			order = createChildTest(childTest22, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			/*
			 * ExtentTest childTest23 =
			 * extent.startTest("Wait for Grace Period Phone Mark Finish Task");
			 * test.appendChild(childTest23); com = createChildTest(childTest23, extent,
			 * com); order = createChildTest(childTest23, extent, order);
			 * order.navigateToOrderPage(); order.navigateToOrderValidation(36, "No", "No",
			 * "COM"); com.setMarkFinishedTask();
			 */

			ExtentTest childTest24 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest24);
			com = createChildTest(childTest24, extent, com);
			order = createChildTest(childTest24, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest25 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest25);
			com = createChildTest(childTest25, extent, com);
			som = createChildTest(childTest25, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest26 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest26);
			com = createChildTest(childTest26, extent, com);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Disconnect_PhoneLOB_Number_Verification");
		} catch (Exception e) {
			log.error("Error in Disconnect_PhoneLOB_Number_Verification:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Disconnect_XPOD_Rental_Package_Exisitng_Customer(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Disconnect xPOD Rental package for an exisitng customer");
			log.debug("Entering Disconnect_XPOD_Rental_Package_Exisitng_Customer");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			XPODSrlNo = Utility.generateHardwareSerailNum(XPODSrlNo);
			String xpodValue1 = XPODSrlNo;
			XPODSrlNo = Utility.generateHardwareSerailNum(XPODSrlNo);
			String xpodValue2 = XPODSrlNo;
			XPODSrlNo = Utility.generateHardwareSerailNum(XPODSrlNo);
			String xpodValue3 = XPODSrlNo;
			String xpod1PKValue = null;
			String techAppointment = data.get("Tech_Appointment");
			String disconnectReason = data.get("Disconnect_Reasons");
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
			order.customerInformation("Staff", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Phone, Voice mail");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);

			ExtentTest childTest4 = extent.startTest("Add Internet Product");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);
//			order.deleteHardware("Switch Window");

			ExtentTest childTest5 = extent.startTest("Add Rental XPOD Package");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addRentalXpods("Staff", xpodValue1, xpodValue2, xpodValue3, xpod1PKValue, "false");

			
			  ExtentTest childTest6 = extent.startTest("Add Converged Hardware");
			  test.appendChild(childTest6); order = createChildTest(childTest6, extent, order);
			  // order.addConvergedHardware("Converged150",  convergedHardWareSerialNbr);
			  order.addConvergedHardware1(convergedHardWareSerialNbr);
			  order.selectConvergedHardware(convergedHardware); //
			 // order.selectConvergedHardware("Internet");
			 
			ExtentTest childTest7 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);

			ExtentTest childTest8 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest9 = extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest10 = extent.startTest("Review and Finish");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest11 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest12 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest13 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest13);
			com = createChildTest(childTest13, extent, com);
			order = createChildTest(childTest13, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest14 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest15 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest16 = extent.startTest("Disconnect Phone and Internet");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.disconnectLOBProducts("Phone");
			order.disconnectLOBProducts("Internet");
			order.deleteConvergedHardware();

			ExtentTest childTest17 = extent.startTest("Remove Portout Number");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.selectPortOut();

			ExtentTest childTest18 = extent.startTest("Select Customer Mailing Address For Disconnection");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.addCustomerMailingAddress("", "No");

			ExtentTest childTest19 = extent.startTest("Disconnect appointment date");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.disconnectAppointmentDate();

			ExtentTest childTest20 = extent.startTest("Select Disconnect Reason");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.selectDisconnectControllabeReasons(disconnectReason);

			ExtentTest childTest21 = extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest22 = extent.startTest("Review And Finish");
			test.appendChild(childTest22);
			order = createChildTest(childTest22, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest23 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest24 = extent.startTest("Wait for effective date Mark Finish Task");
			test.appendChild(childTest24);
			com = createChildTest(childTest24, extent, com);
			order = createChildTest(childTest24, extent, order);
			order.navigateToOrderValidation(35, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest25 = extent.startTest("Wait for Grace Period XPOD2 Mark Finish Task");
			test.appendChild(childTest25);
			com = createChildTest(childTest25, extent, com);
			order = createChildTest(childTest25, extent, order);
			order.navigateToOrderValidation(36, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest32 = extent.startTest("Wait for Grace Period XPOD1 Mark Finish Task");
			test.appendChild(childTest32);
			com = createChildTest(childTest32, extent, com);
			order = createChildTest(childTest32, extent, order);
			order.navigateToOrderValidation(37, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest26 = extent.startTest("Wait for Grace Period XPOD3 Mark Finish Task");
			test.appendChild(childTest26);
			com = createChildTest(childTest26, extent, com);
			order = createChildTest(childTest26, extent, order);
			order.navigateToOrderValidation(38, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest27 = extent.startTest("Wait for Grace Period Converged Mark Finish Task");
			test.appendChild(childTest27);
			com = createChildTest(childTest27, extent, com);
			order = createChildTest(childTest27, extent, order);
			order.navigateToOrderValidation(56, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest28 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest28);
			com = createChildTest(childTest28, extent, com);
			order = createChildTest(childTest28, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest29 = extent.startTest("Verify CLEC, COM and SOM Record counts");
			test.appendChild(childTest29);
			com = createChildTest(childTest29, extent, com);
			som = createChildTest(childTest29, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest30 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest30);
			repo = createChildTest(childTest30, extent, repo);
			order = createChildTest(childTest30, extent, order);
			order.navigateToOrderValidation(42, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC104", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");

			ExtentTest childTest31 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest31);
			com = createChildTest(childTest31, extent, com);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Disconnect_XPOD_Rental_Package_Exisitng_Customer");
		} catch (Exception e) {
			log.error("Error in Disconnect_XPOD_Rental_Package_Exisitng_Customer:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void Validate_New_MC_New_Customer_Hardware_Return(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Validate new MC for new customer when hardware is returned");
			log.debug("Entering Validate_New_MC_New_Customer_Hardware_Return");
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

			ExtentTest childTest4 = extent.startTest("Add Internet Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);

			ExtentTest childTest5 = extent.startTest("Add TV and TV Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			// order.selectTodo();

			ExtentTest childTest6 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest7 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest8 = extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest10 = extent.startTest("Navigate to Order History status");
			test.appendChild(childTest10);
			orderHistory = createChildTest(childTest10, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "Product Summary");
			order.navigateToOrderPage();

			ExtentTest childTest11 = extent
					.startTest("Trigger the Hardware Returned End point with WarehouseID ERP-5649");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			soapRest = createChildTest(childTest11, extent, soapRest);
			soapRest.navigateToHitHardwareReturnedEndpoint("TC131", tvHardwareSerialNbr);

			ExtentTest childTest12 = extent.startTest("Navigate to trigger disconnect hardware job");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			order = createChildTest(childTest12, extent, order);
			com.navigateToJobMonitorURL("Disconnect Returned Hardware");
			order.navigateToOrderPage();

			ExtentTest childTest15 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			som = createChildTest(childTest15, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest14 = extent.startTest("Check the Hardware Return Status");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			com.navigateToCustomerDiscountsAndContracts("Validate Contract");

			ExtentTest childTest16 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest16);
			orderHistory = createChildTest(childTest16, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "Agreement Manager");

			log.debug("Leaving Validate_New_MC_New_Customer_Hardware_Return");
		} catch (Exception e) {
			log.error("Error in Validate_New_MC_New_Customer_Hardware_Return:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	
	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Tripleplay_DPT_Phone_perform_Full_Disconnect(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"For a Triple play account with DPT, Cisco Advanced Wi-Fi modem and legacy TV device, perform a full account disconnect");
			log.debug("Entering Tripleplay_DPT_Phone_perform_Full_Disconnect");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
//			String phoneProduct = data.get("Phone_Product");
//			String phoneHardware = data.get("Phone_hardware");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNo = PhSrlNo;
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			String internetHardware = data.get("Internet_hardware");
			String convergedHardware = data.get("Converged_hardware");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardwareSerialNbr = PhSrlNo;
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String internetProduct = data.get("Internet_Product");
			IntACSrlNo = Utility.generateHardwareSerailNum(IntACSrlNo);
			String internetHardwareSerialNbr = IntACSrlNo;
			String tvProduct = data.get("TV_Product");
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr = TVDctSrlNo;
			TVDctSrlNo=Utility.generateHardwareSerailNum(TVDctSrlNo);
			TVHDSrlNo=Utility.generateHardwareSerailNum(TVHDSrlNo);
			TVHDSrlNo=Utility.generateHardwareSerailNum(TVHDSrlNo);
			String tvHardwareDCX3510SlNbr=TVHDSrlNo;
			String[] hardwareType = { "DCT700" ,"DCX3510HDGuide"};
			String[] serialNumber = { tvHardwareSerialNbr,tvHardwareDCX3510SlNbr };
			String convergedHardWareSerialNo = ConvgdSrlNo;
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String disconnectReason = data.get("Disconnect_Reasons");
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

			ExtentTest childTest2 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);
			order.addInternetEmail("", "Calgary");

			ExtentTest childTest3 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNo);
			order.addConvergedHardware1(convergedHardWareSerialNo);
			order.selectConvergedHardware("Both");
//				order.selectTodoListCheckBox();

			ExtentTest childTest4 = extent.startTest("Add TV Product");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);

			ExtentTest childTest5 = extent.startTest("Add TV Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest6 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest7 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest8 = extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();
			order.addServicesAndFeacturesTab();

			ExtentTest childTest9 = extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.reviewAndFinishOrder();
//				order.selectTodoListCheckBox();

//			  String accntNum = "58162552170";
//			  String orderid = "00019727";

			ExtentTest childTest10 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			String accntNum = order.getAccountNumber();
//			String orderid = order.getOrderNumber();
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
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest13 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest13);
			com = createChildTest(childTest13, extent, com);
			com.verifyCLECRequestAfterJobRun();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest14 = extent.startTest("Navigate To 360 View Page");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			order = createChildTest(childTest14, extent, order);
////				String accntNUM = order.getAccountNumber();
			com.navigateToCheck360ViewAndInsideTables();
////				order.refreshPage();

			ExtentTest childTest15 = extent.startTest("Navigate To Hardware Table");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			com.navigateToHardware();
			com.getCPEexpectedStatus();
			com.getCPEactualStatus();
			com.getIsCPEstatusConsistent();
			com.getHPEPMexpectedStatus();
			com.getHPEPMactualStatus();
			com.getIsHPEPMstatusConsistent();
			com.getExpectedStatusInRPIL();
			com.getActualStatusInRPIL();
			com.getIsRPILstatusConsistent();
			com.getBRMexpectedStatus();
			com.getBRMactualStatus();
			com.getIsBRMstatusConsistent();

			ExtentTest childTest16 = extent.startTest("Navigate To Phone Numbers Table");
			test.appendChild(childTest16);
			com = createChildTest(childTest16, extent, com);
			com.navigateToPhoneNumbersTable();
			com.getPhoneTNIexpectedStatus();
			com.getPhoneTNIactualStatus();
			com.getPhoneHPEPMexpectedStatus();
			com.getPhoneHPEPMactualStatus();
			com.getPhoneIsHPEPMstatusConsistent();
			com.getPhoneBRMexpectedStatus();
			com.getPhoneBRMactualStatus();
			com.getPhoneIsBRMstatusConsistent();

			ExtentTest childTest17 = extent.startTest("Navigate To Emails Table");
			test.appendChild(childTest17);
			com = createChildTest(childTest17, extent, com);
			com.navigateToEmailsTable();
			com.getEmailHPEPMexpectedStatus();
			com.getEmailHPEPMactualStatus();
			com.getEmailIsHPEPMstatusConsistent();
			
//			ExtentTest childTest18 = extent.startTest("Verify COM Integration Report");
//			test.appendChild(childTest18);
//			repo = createChildTest(childTest18, extent, repo);
//			order = createChildTest(childTest18, extent, order);
//			order.navigateToOrderValidation(1, "Yes", "No", "COM");
//			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
//			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC164", 1, valuesToPassForValidation),
//					"Attributes are not validated successfully");
//			order.navigateToOrderPage();

//			order.navigateToOrderValidation(1, "Yes", "No", "COM");
//			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
//			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC164", 2, valuesToPassForValidation),
//					"Attributes are not validated successfully");
//			order.navigateToOrderPage();
			
//			order.navigateToOrderValidation(1, "Yes", "No", "COM");
//			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
//			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC164", 2, valuesToPassForValidation),
//					"Attributes are not validated successfully");
//			order.navigateToOrderPage();


			ExtentTest childTest19 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest20 = extent.startTest("Remove Phone Number");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.addServicesAndFeacturesTab();
			order.changeCustNbrPortedToNative("Add Product");
			order.selectPhoneConvergedHardware();
			order.addPhoneHardware(phoneHardwareSerialNbr, phoneHardware);
			order.selectPortOut();

			ExtentTest childTest21 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectDisconnectControllabeReasons("secondaryReason");

			ExtentTest childTest22 = extent.startTest("Review and Finish");
			test.appendChild(childTest22);
			order = createChildTest(childTest22, extent, order);
			order.reviewAndFinishOrder();
//				
////				String accntNum = "69657609875";
////				  String orderid = "00019727";
//				String accntNum = "58736236398";

			ExtentTest childTest23 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

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
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest27 = extent.startTest("Navigate To 360 View Page");
			test.appendChild(childTest27);
			com = createChildTest(childTest27, extent, com);
			order = createChildTest(childTest27, extent, order);
//				String accntNUM = order.getAccountNumber();
			com.navigateToCheck360ViewAndInsideTables();

			ExtentTest childTest28 = extent.startTest("Navigate To Hardware Table");
			test.appendChild(childTest28);
			com = createChildTest(childTest28, extent, com);
			com.navigateToHardware();
			com.getCPEexpectedStatus();
			com.getCPEactualStatus();
			com.getIsCPEstatusConsistent();
			com.getHPEPMexpectedStatus();
			com.getHPEPMactualStatus();
			com.getIsHPEPMstatusConsistent();
			com.getExpectedStatusInRPIL();
			com.getActualStatusInRPIL();
			com.getIsRPILstatusConsistent();
			com.getBRMexpectedStatus();
			com.getBRMactualStatus();
			com.getIsBRMstatusConsistent();

			ExtentTest childTest29 = extent.startTest("Navigate To Phone Numbers Table");
			test.appendChild(childTest29);
			com = createChildTest(childTest29, extent, com);
			com.navigateToPhoneNumbersTable();
			com.getPhoneTNIexpectedStatus();
			com.getPhoneTNIactualStatus();
			com.getPhoneHPEPMexpectedStatus();
			com.getPhoneHPEPMactualStatus();
			com.getPhoneIsHPEPMstatusConsistent();
			com.getPhoneBRMexpectedStatus();
			com.getPhoneBRMactualStatus();
			com.getPhoneIsBRMstatusConsistent();

			ExtentTest childTest30 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest30);
			order = createChildTest(childTest30, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest31 = extent.startTest("Disconnect Phone, Internet and TV LOB's");
			test.appendChild(childTest31);
			order = createChildTest(childTest31, extent, order);
			order.removePersonalPhoneHardware();
			order.disconnectLOBProducts("Phone");
			order.disconnectLOBProducts("Internet");
			order.disconnectLOBProducts("TV");
			order.deleteConvergedHardware();
			order.selectTodo();
			order.selectPortOut();

			ExtentTest childTest32 = extent.startTest("Disconnect appointment date");
			test.appendChild(childTest32);
			order = createChildTest(childTest32, extent, order);
			order.disconnectAppointmentDate();

			ExtentTest childTest33 = extent.startTest("Select Disconnect Reason");
			test.appendChild(childTest33);
			order = createChildTest(childTest33, extent, order);
			order.selectDisconnectControllabeReasons(disconnectReason);

			ExtentTest childTest34 = extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest34);
			order = createChildTest(childTest34, extent, order);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest35 = extent.startTest("Review and Finish");
			test.appendChild(childTest35);
			order = createChildTest(childTest35, extent, order);
			order.reviewAndFinishOrder();
			
			ExtentTest childTest36 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest36);
			orderHistory = createChildTest(childTest36, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "Product Summary");

			ExtentTest childTest37 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest37);
			order = createChildTest(childTest37, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest38 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest38);
			com = createChildTest(childTest38, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest39 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest39);
			com = createChildTest(childTest39, extent, com);
			order = createChildTest(childTest39, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest40 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest40);
			com = createChildTest(childTest40, extent, com);
			com.verifyCLECRequestAfterJobRun();
			order.openCOMOrderPage("true", accntNum, "Yes");

//			ExtentTest childTest41 = extent.startTest("Disconnect customer mark finish task");
//			test.appendChild(childTest41);
//			com = createChildTest(childTest41, extent, com);
//			order = createChildTest(childTest41, extent, order);
//			order.navigateToOrderValidation(35, "No", "No", "COM");
//			com.setMarkFinishedTask();
//			order.navigateToOrderPage();

			ExtentTest childTest42 = extent.startTest("Navigate To 360 View Page");
			test.appendChild(childTest42);
			com = createChildTest(childTest42, extent, com);
			order = createChildTest(childTest42, extent, order);
			// String accntNUM = order.getAccountNumber();
			com.navigateToCheck360ViewAndInsideTables();

			ExtentTest childTest43 = extent.startTest("Navigate To Hardware Table");
			test.appendChild(childTest43);
			com = createChildTest(childTest43, extent, com);
			com.navigateToHardware();
			com.getCPEexpectedStatus();
			com.getCPEactualStatus();
			com.getIsCPEstatusConsistent();
			com.getHPEPMexpectedStatus();
			com.getHPEPMactualStatus();
			com.getIsHPEPMstatusConsistent();
			com.getExpectedStatusInRPIL();
			com.getActualStatusInRPIL();
			com.getIsRPILstatusConsistent();
			com.getBRMexpectedStatus();
			com.getBRMactualStatus();
			com.getIsBRMstatusConsistent();
			
			
//			ExtentTest childTest44 = extent.startTest("Navigate To Open OE Page");
//			test.appendChild(childTest44);
//			landing = createChildTest(childTest44, extent, landing);
//			landing.openUrlWithLocationId2();
//
//			ExtentTest childTest45 = extent.startTest("Fill Customer Info");
//			test.appendChild(childTest45);
//			order = createChildTest(childTest45, extent, order);
//			order.customerInformation("Residential", "Yes");
//
//			ExtentTest childTest46 = extent.startTest("Add Phone");
//			test.appendChild(childTest46);
//			order = createChildTest(childTest46, extent, order);
//			order.addServicesAndFeacturesTab();
//			String portPhoneNbr = order.portPhoneNumber("Phone Required", "", phoneProduct, "", "No", "No");
//
//			ExtentTest childTest47 = extent.startTest("Add Phone hardware");
//			test.appendChild(childTest47);
//			order = createChildTest(childTest47, extent, order);
//			order.addPhoneHardware(phoneSerialNo, phoneHardware);
//			
//			ExtentTest childTest48=extent.startTest("Add Internet Product");
//			test.appendChild(childTest48);
//			order=createChildTest(childTest48,extent,order);
//			order.addServiceInternet("Fibre+ 250");
//			
//			ExtentTest childTest49=extent.startTest("Add Internet Hardware");
//			test.appendChild(childTest49);
//			order=createChildTest(childTest49,extent,order);
//			order.addInternetHardware("Hitron", internetHardwareSerialNbr);
//			
//			ExtentTest childTest50=extent.startTest("Add DCX 3510M TV Hardware");
//			test.appendChild(childTest50);
//			order=createChildTest(childTest50,extent,order);
//			order.addServicesAndFeacturesTab();
//			order.addServiceTV("Total TV");
//			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);
//			
//			ExtentTest childTest51 = extent.startTest("Navigate To Tech Appointment Tab");
//			test.appendChild(childTest51);
//			order = createChildTest(childTest51, extent, order);
//			order.selectInstallationOption("Self Connect No", techAppointment);
//			order.enteringChangesAuthorizedBy();
//
//			ExtentTest childTest52 = extent.startTest("Select Method of confirmation");
//			test.appendChild(childTest52);
//			order = createChildTest(childTest52, extent, order);
//			order.selectMethodOfConfirmation("Voice");
//
//			ExtentTest childTest53 = extent.startTest("Entering Changes authorized by");
//			test.appendChild(childTest53);
//			order = createChildTest(childTest53, extent, order);
//			order.enteringChangesAuthorizedBy();
//			order.selectingSalesRepresentativeCheckBox();
//			order.addServicesAndFeacturesTab();
//
//			ExtentTest childTest54 = extent.startTest("Review and Finish");
//			test.appendChild(childTest54);
//			order = createChildTest(childTest54, extent, order);
//			order.reviewAndFinishOrder();
//
//			ExtentTest childTest55 = extent.startTest("Navigate To COM & SOM Orders");
//			test.appendChild(childTest55);
//			order = createChildTest(childTest55, extent, order);
//			String accntNum1 = order.getAccountNumber();
////			String orderid = order.getOrderNumber();
//			order.openCOMOrderPage("true", accntNum1, "Yes");
//
//			ExtentTest childTest56 = extent.startTest("CLEC Request Before Job Run");
//			test.appendChild(childTest56);
//			com = createChildTest(childTest56, extent, com);
//			com.verifyCLECRequestBeforeJobRun();
//
//			ExtentTest childTest57 = extent.startTest("Navigate To E911 Job Run");
//			test.appendChild(childTest57);
//			com = createChildTest(childTest57, extent, com);
//			order = createChildTest(childTest57, extent, order);
//			com.navigateToJobMonitorURL("E911");
//			order.navigateToOrderPage();
//
//			ExtentTest childTest58 = extent.startTest("CLEC Request After Job Run");
//			test.appendChild(childTest58);
//			com = createChildTest(childTest58, extent, com);
//			com.verifyCLECRequestAfterJobRun();
//			order.openCOMOrderPage("true", accntNum1, "Yes");
//
//			ExtentTest childTest59 = extent.startTest("Navigate To 360 View Page");
//			test.appendChild(childTest59);
//			com = createChildTest(childTest59, extent, com);
//			order = createChildTest(childTest59, extent, order);
//////				String accntNUM = order.getAccountNumber();
//			com.navigateToCheck360ViewAndInsideTables();
//////				order.refreshPage();
//
//			ExtentTest childTest60 = extent.startTest("Navigate To Hardware Table");
//			test.appendChild(childTest60);
//			com = createChildTest(childTest60, extent, com);
//			com.navigateToHardware();
//			com.getCPEexpectedStatus();
//			com.getCPEactualStatus();
//			com.getIsCPEstatusConsistent();
//			com.getHPEPMexpectedStatus();
//			com.getHPEPMactualStatus();
//			com.getIsHPEPMstatusConsistent();
//			com.getExpectedStatusInRPIL();
//			com.getActualStatusInRPIL();
//			com.getIsRPILstatusConsistent();
//			com.getBRMexpectedStatus();
//			com.getBRMactualStatus();
//			com.getIsBRMstatusConsistent();
//
//			ExtentTest childTest61 = extent.startTest("Navigate To Phone Numbers Table");
//			test.appendChild(childTest61);
//			com = createChildTest(childTest61, extent, com);
//			com.navigateToPhoneNumbersTable();
//			com.getPhoneTNIexpectedStatus();
//			com.getPhoneTNIactualStatus();
//			com.getPhoneHPEPMexpectedStatus();
//			com.getPhoneHPEPMactualStatus();
//			com.getPhoneIsHPEPMstatusConsistent();
//			com.getPhoneBRMexpectedStatus();
//			com.getPhoneBRMactualStatus();
//			com.getPhoneIsBRMstatusConsistent();
//
//			ExtentTest childTest62 = extent.startTest("Navigate To Emails Table");
//			test.appendChild(childTest62);
//			com = createChildTest(childTest62, extent, com);
//			com.navigateToEmailsTable();
//			com.getEmailHPEPMexpectedStatus();
//			com.getEmailHPEPMactualStatus();
//			com.getEmailIsHPEPMstatusConsistent();
//			
//			log.debug("Leaving Tripleplay_DPT_Phone_perform_Full_Disconnect");

		} catch (Exception e) {
			log.error("Error in Tripleplay_DPT_Phone_perform_Full_Disconnect:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	public static void main(String args[]) {
		InputStream log4j = DisconnectCases.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
		PropertyConfigurator.configure(log4j);
	}
}
