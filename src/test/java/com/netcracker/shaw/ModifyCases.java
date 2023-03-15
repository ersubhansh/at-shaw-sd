package com.netcracker.shaw;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.interactions.ClickAction;
import org.openqa.selenium.remote.server.handler.ClickElement;
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

public class ModifyCases extends SeleniumTestUp {

	LandingPage landing = null;
	OrderCreationPage order = null;
	COMOrdersPage com = null;
	SOMOrderPage som = null;
	ValidateReport repo = null;
	OrderHistoryPage orderHistory = null;
	SoftAssert softAssert = new SoftAssert();
	ArrayList<String> valuesToPassForValidation = new ArrayList<String>();

	Logger log = Logger.getLogger(ModifyCases.class);

	@Test(dataProvider = "getTestData", description = "Golden Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Modify_Internet75_to_Internet150_With_XB6(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify upgrading the offering from Internet 75 to internet 150 for an account with XB6 converged device");
			log.debug("Entering Modify_Internet75_to_Internet150_With_XB6");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			String internetProduct = data.get("Internet_Product");
			String convergedHardware = data.get("Converged_hardware");
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
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch window");

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

			ExtentTest childTest9 = extent.startTest("Verify COM, SOM Initial Record Counts");
			test.appendChild(childTest9);
			com = createChildTest(childTest9, extent, com);
			som = createChildTest(childTest9, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest10 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest11 = extent.startTest("Modify Fibre+ 75 to Fibre+ 150");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.addServiceInternet("Fibre+ 150");

			ExtentTest childTest12 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.navigatetoAppointmentTab();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest13 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest14 = extent.startTest("Review and Finish");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest15 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			som = createChildTest(childTest15, extent, som);
			//com.switchPreviousTab();
			order.openCOMOrderPage("true", accntNum, "");
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest16 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest16);
			repo = createChildTest(childTest16, extent, repo);
			order = createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(24, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC7", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest17 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest17);
			repo = createChildTest(childTest17, extent, repo);
			order = createChildTest(childTest17, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC7", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC7", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC7", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest18 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest18);
			orderHistory = createChildTest(childTest18, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Modify_Internet75_to_Internet150_With_XB6");
		} catch (Exception e) {
			log.error("Error in Modify_Internet75_to_Internet150_With_XB6:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Golden Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void VoiceMail_DistinctiveRing_Converged(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify adding Voice Mail , Distinctive Ring to an account with XB6 Converged");
			log.debug("Entering VoiceMail_DistinctiveRing_Converged");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
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
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch Window");

			ExtentTest childTest5 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest6 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.enteringChangesAuthorizedBy();

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
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest9 = extent.startTest("Navigate To Check COM & SOM initial Record Counts");
			test.appendChild(childTest9);
			com = createChildTest(childTest9, extent, com);
			som = createChildTest(childTest9, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

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
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest13 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest14 = extent.startTest("Add Distinctive Ring");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.addDistinctiveRing("One", "3 short rings");
			order.selectVoiceMail();

			ExtentTest childTest15 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.navigatetoAppointmentTab();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest16 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest17 = extent.startTest("Review and Finish");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.reviewAndFinishOrder();
			order.openCOMOrderPage("true", accntNum, "Yes");
			//order.switchPreviousTab();

			ExtentTest childTest18 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			som = createChildTest(childTest18, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest19 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			repo = createChildTest(childTest19, extent, repo);
			order.navigateToOrderValidation(20, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC8", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest20 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest20);
			repo = createChildTest(childTest20, extent, repo);
			order = createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC8", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(phoneNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC8", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC8", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest21);
			orderHistory = createChildTest(childTest21, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving VoiceMail_DistinctiveRing_Converged");
		} catch (Exception e) {
			log.error("Error in VoiceMail_DistinctiveRing_Converged:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Golden Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void DPT_to_XB6_Converged(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Verify changing the device from DPT to XB6-Converged");
			log.debug("Entering DPT_to_XB6_Converged");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String convergedHardware = data.get("Converged_hardware");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNo = PhSrlNo;
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			String phoneHardware = data.get("Phone_hardware");
			String techAppointment = data.get("Tech_Appointment");
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
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

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneSerialNo, phoneHardware);

			ExtentTest childTest5 = extent.startTest("Add Internet Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch Window");

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

			ExtentTest childTest9 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			com = createChildTest(childTest9, extent, com);
			som = createChildTest(childTest9, extent, som);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest10 = extent.startTest("Check COM, SOM Initial Counts");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

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
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest14 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest15 = extent.startTest("Remove Phone hardware");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.removePersonalPhoneHardware();

			ExtentTest childTest16 = extent.startTest("Add Converged Hardware for phone Product");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.selectConvergedHardware("Phone");

			ExtentTest childTest17 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.techAppointmentWithCurrentDate();
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest18 = extent.startTest("Review and Finish");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest19 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest20 = extent.startTest("Wait for Provisioning start Mark Finish Task");
			test.appendChild(childTest20);
			com = createChildTest(childTest20, extent, com);
			order = createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Wait For Tech Confirm Mark Finished");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			order = createChildTest(childTest21, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest22);
			com = createChildTest(childTest22, extent, com);
			som = createChildTest(childTest22, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest23 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest23);
			repo = createChildTest(childTest23, extent, repo);
			order = createChildTest(childTest23, extent, order);
			order.navigateToOrderValidation(33, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneSerialNo));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC9", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneSerialNo));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC9", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(4, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC9", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest24 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest24);
			order = createChildTest(childTest24, extent, order);
			com = createChildTest(childTest24, extent, com);
			repo = createChildTest(childTest24, extent, repo);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(phoneNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC9", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, phoneNbr, phoneSerialNo, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC9", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr, phoneSerialNo));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC9", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest25 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest25);
			orderHistory = createChildTest(childTest25, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving DPT_to_XB6_Converged");
		} catch (Exception e) {
			log.error("Error in DPT_to_XB6_Converged:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", priority = 26, description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void Modify_DCX_to_BlueSky(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify Deleting DCX 3200 and adding Blue SKY TV with Portals");
			log.debug("Entering Modify_DCX_to_BlueSky");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String internetHardware = data.get("Internet_hardware");
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			TVDCXSrlNo = Utility.generateHardwareSerailNum(TVDCXSrlNo);
			//TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr = TVDCXSrlNo;
			//String tvHardwareSerialNbr = TVDctSrlNo;
			TVBoxSrlNo = Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr = TVBoxSrlNo;
			TVPortalSrlNo = Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr = TVPortalSrlNo;
			String[] hardwareType = { "DCX3200M" };
			//String[] hardwareType = { "DCT700" };
			String[] serialNumber = { tvHardwareSerialNbr };
			String[] hardwareType1 = { "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber1 = { tvBoxSerialNbr, tvPortalSerialNbr };
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
			order = createChildTest(childTest0, extent, order);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Internet Hardware");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);

			ExtentTest childTest4 = extent.startTest("Add TV Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest5 = extent.startTest("Add Promotions");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addPromotionsServiceAgreement("", internetProduct, tvProduct, "");

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

			ExtentTest childTest9 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest10 = extent.startTest("Navigate To COM & SOM initial record counts");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest11 = extent.startTest("Navigate To SVG Diagram");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("");
			order.navigateToOrderPage();

			ExtentTest childTest12 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.navigateToCustOrderPage("true", accntNum);
			order.refreshPage();

			ExtentTest childTest13 = extent.startTest("Delete DCX3200M and add Blue Sky TV");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.deleteTVHardware();
			order.addTVHardwareWithSrlNo(hardwareType1, serialNumber1);

			ExtentTest childTest14 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.selectTodo();
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNo("On a date", "", "");

			ExtentTest childTest15 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest16 = extent.startTest("Review and Finish");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest17 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			com = createChildTest(childTest17, extent, com);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest18 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			som = createChildTest(childTest18, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest19 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest19);
			repo = createChildTest(childTest19, extent, repo);
			order = createChildTest(childTest19, extent, order);
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC26", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(35, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC26", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest20 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest20);
			repo = createChildTest(childTest20, extent, repo);
			order = createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, tvHardwareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC26", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(tvBoxSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC26", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC26", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, tvHardwareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC26", 4, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest21);
			orderHistory = createChildTest(childTest21, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Modify_DCX_to_BlueSky");
		} catch (Exception e) {
			log.error("Error in Modify_DCX_to_BlueSky:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void Downgrading_TotalTV_to_LimitedTV_with_BlueSky(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify Downgrading the offering from Total TV to Limited TV for an account with Blue Sky");
			log.debug("Entering Downgrading_LargeTV_to_MediumTV_with_BlueSky");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			String tvProduct = data.get("TV_Product");
			TVBoxSrlNo = Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr = TVBoxSrlNo;
			TVPortalSrlNo = Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr = TVPortalSrlNo;
			String[] hardwareType = { "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber = { tvBoxSerialNbr, tvPortalSerialNbr };
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

			ExtentTest childTest3 = extent.startTest("Add Internet hardware");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			// order.deleteConvergedHardware();

			ExtentTest childTest4 = extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest5 = extent.startTest("Add promotion and Coupon Selection");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addPromotionsServiceAgreement("", internetProduct, tvProduct, "");

			ExtentTest childTest6 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			//order.mocaFilterSelecttion("");

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

			ExtentTest childTest10 = extent.startTest("Verify COM, SOM Initial Counts");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest11 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest12 = extent.startTest("Downgrade of TV Service Total TV to Digital Classic");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.addServiceTV("Digital Classic");
			order.addTVPromotionsSrvAgreement("Digital Classic");

			ExtentTest childTest13 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.navigatetoAppointmentTab();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest14 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest15 = extent.startTest("Review and Finish");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest16 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest17 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest17);
			com = createChildTest(childTest17, extent, com);
			som = createChildTest(childTest17, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest18 = extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest18);
			repo = createChildTest(childTest18, extent, repo);
			order = createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC29", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(10, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway = repo.validateSOMIntegrationReportsSpcl(xls, "TC29", 2, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway, "Attributes are not validated successfully");
			if (!is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC29", 2, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			order.navigateToOrderValidation(11, "Yes", "No", "SOM");
			if (is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC29", 3, valuesToPassForValidation),
						"Attributes are not validated successfully");
			} else {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC29", 3, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			ExtentTest childTest19 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest19);
			repo = createChildTest(childTest19, extent, repo);
			order = createChildTest(childTest19, extent, order);
			order.navigateToOrderValidation(29, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC29", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, internetHardwareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC29", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest20 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest20);
			orderHistory = createChildTest(childTest20, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Downgrading_TotalTV_to_LimitedTV_with_BlueSky");
		} catch (Exception e) {
			log.error("Error in Downgrading_TotalTV_to_LimitedTV_with_BlueSky:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void Adding_Emails_with_XB6_Converged(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Verify adding e-mails for an account with XB6 converged");
			log.debug("Entering Adding_Emails_with_XB6_Converged");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
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

			ExtentTest childTest3 = extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
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

			ExtentTest childTest9 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			com = createChildTest(childTest9, extent, com);
			som = createChildTest(childTest9, extent, som);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest10 = extent.startTest("Verify COM,SOM Initial Counts");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest11 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest12 = extent.startTest("Add Internet Email");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.addInternetEmail("", "Calgary");

			ExtentTest childTest13 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest14 = extent.startTest("Review and Finish");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest15 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest16 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest16);
			com = createChildTest(childTest16, extent, com);
			som = createChildTest(childTest16, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest17 = extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest17);
			repo = createChildTest(childTest17, extent, repo);
			order = createChildTest(childTest17, extent, order);
			order.navigateToOrderValidation(16, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC30", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest18 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest18);
			repo = createChildTest(childTest18, extent, repo);
			order = createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC30", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC30", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");

			ExtentTest childTest19 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest19);
			orderHistory = createChildTest(childTest19, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Adding_Emails_with_XB6_Converged");
		} catch (Exception e) {
			log.error("Error in Adding_Emails_with_XB6_Converged:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void Upgrade_TV_offering_Grandfathered_promotion(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Upgrade Tv offering on an account with Grandfathered promotion");
			log.debug("Entering Upgrade_TV_offering_Grandfathered_promotion");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String tvProduct = data.get("TV_Product");
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

			ExtentTest childTest3 = extent.startTest("Add TV and TV Hardware");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest4 = extent.startTest("Add pramotion and Coupon Selection");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPromotions();

			ExtentTest childTest5 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest6 = extent.startTest("Review and Finish");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest7 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			com = createChildTest(childTest7, extent, com);
			som = createChildTest(childTest7, extent, som);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest8 = extent.startTest("Verify COM SOM Initial Counts");
			test.appendChild(childTest8);
			com = createChildTest(childTest8, extent, com);
			som = createChildTest(childTest, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest9 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest10 = extent.startTest("Upgrade TV, Digital Classic To Limited TV");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.addServiceTV("Limited TV");

			ExtentTest childTest11 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest12 = extent.startTest("Review and Finish");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest13 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest14 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			som = createChildTest(childTest14, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest15 = extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest15);
			som = createChildTest(childTest15, extent, som);
			repo = createChildTest(childTest15, extent, repo);
			order = createChildTest(childTest15, extent, order);
			order.navigateToOrderValidation(10, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC41", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest16 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest16);
			repo = createChildTest(childTest16, extent, repo);
			order = createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC41", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC41", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest17 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest17);
			orderHistory = createChildTest(childTest17, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Upgrade_TV_offering_Grandfathered_promotion");
		} catch (Exception e) {
			log.error("Error in Upgrade_TV_offering_Grandfathered_promotion:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void DowngradeTV_Pramotion_Expiring_Nextbill_Cycle(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Downgrade Tv offering on an account promotion is expiring in the next bill cycle");
			log.debug("Entering DowngradeTV_Pramotion_Expiring_Nextbill_Cycle");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
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
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
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

			ExtentTest childTest3 = extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest4 = extent.startTest("Add Internet with Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			// order.deleteHardware("Switch Window");
			order.selectTodoListCheckBox();

			ExtentTest childTest5 = extent.startTest("Add pramotion and Coupon Selection");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addTVPriceGuarenteePramotions("theme");

			ExtentTest childTest6 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			//order.mocaFilterSelecttion("");

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
			com = createChildTest(childTest9, extent, com);
			som = createChildTest(childTest9, extent, som);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest10 = extent.startTest("Verify COM SOM Record Counts");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest11 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest12 = extent.startTest("Downgrade Limited TV To Digital Classic");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.addServiceTV("Digital Classic");

			ExtentTest childTest13 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest14 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest15 = extent.startTest("Review and Finish");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest16 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest17 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest17);
			com = createChildTest(childTest17, extent, com);
			som = createChildTest(childTest17, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest18 = extent.startTest("Navigate To Quote Discounts");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			order = createChildTest(childTest18, extent, order);
			com.navigateToCOMOrder();
			com.navigateToCustomerDiscountsAndContracts("quote Section");
			order.navigateToOrderPage();

			ExtentTest childTest19 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest19);
			orderHistory = createChildTest(childTest19, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving DowngradeTV_Pramotion_Expiring_Nextbill_Cycle");
		} catch (Exception e) {
			log.error("Error in DowngradeTV_Pramotion_Expiring_Nextbill_Cycle:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Adding_VoiceMail_DistinctiveRing_account_with_DPT(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify adding Voice Mail , Distinctive Ring to an account with DPT");
			log.debug("Entering Adding_VoiceMail_DistinctiveRing_account_with_DPT");
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
			String techAppointment = data.get("Tech_Appointment");
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
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

			ExtentTest childTest3 = extent.startTest("Add Phone");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);

			ExtentTest childTest4 = extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneSerialNo, phoneHardware);

			ExtentTest childTest5 = extent.startTest("Add Internet and Hitron hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);

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
			com = createChildTest(childTest9, extent, com);
			som = createChildTest(childTest9, extent, som);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest10 = extent.startTest("Verify COM SOM Initial Counts");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

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
			order.refreshPage();

			ExtentTest childTest13 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest14 = extent.startTest("Add Voice mail And Distinctive Ring");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.navigateToCustOrderPage("false", accntNum);
			order.selectVoiceMail();
			order.addDistinctiveRing("One", "3 short rings");

			ExtentTest childTest15 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest16 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest17 = extent.startTest("Review and Finish");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest18 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.verifySVGDiagram("");
			order.navigateToOrderPage();

			ExtentTest childTest19 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			som = createChildTest(childTest19, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest20 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest20);
			repo = createChildTest(childTest20, extent, repo);
			order = createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(2, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneSerialNo));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC49", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest21);
			repo = createChildTest(childTest21, extent, repo);
			order = createChildTest(childTest21, extent, order);
			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, phoneNbr, phoneSerialNo, internetProduct));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC49", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest22);
			orderHistory = createChildTest(childTest22, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Adding_VoiceMail_DistinctiveRing_account_with_DPT");
		} catch (Exception e) {
			log.error("Error in Adding_VoiceMail_DistinctiveRing_account_with_DPT:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void AddRemove_channels_BlueSKY_TV(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify Add/Remove channels for Blue SKY TV device and theme packs and Seasonal packages");
			log.debug("Entering AddRemove_channels_BlueSKY_TV");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
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

			ExtentTest childTest3 = extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);
			order.deleteHardware("Switch Window");
			

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
			order.selectTodoListCheckBox();

			ExtentTest childTest6 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			//order.mocaFilterSelecttion("");

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
			com = createChildTest(childTest9, extent, com);
			som = createChildTest(childTest9, extent, som);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest10 = extent.startTest("Check COM, SOM Initial Counts");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest11 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest12 = extent.startTest("Add Digital Channel Section Pick10 Pack2");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.addingPick10pack2DigitalChannels("3rd Wrench");

			ExtentTest childTest13 = extent.startTest("Add South Asian4 pack1");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.addSouthAsian4Pack1();

			ExtentTest childTest14 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest15 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest16 = extent.startTest("Review and Finish");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest17 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			/*
			 * ExtentTest
			 * childTest18=extent.startTest("Charge Hardware Request for SN Mark Finish");
			 * test.appendChild(childTest18); com=createChildTest(childTest18,extent,com);
			 * order=createChildTest(childTest18,extent,order);
			 * order.navigateToOrderValidation(36, "No", "No", "COM");
			 * com.setMarkFinishedTask(); order.navigateToOrderPage();
			 */

			ExtentTest childTest19 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			som = createChildTest(childTest19, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest20 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest20);
			repo = createChildTest(childTest20, extent, repo);
			order = createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(10, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway = repo.validateSOMIntegrationReportsSpcl(xls, "TC50", 1, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway, "Attributes are not validated successfully");
			if (!is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC50", 1, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			order.navigateToOrderValidation(11, "Yes", "No", "SOM");
			if (is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC50", 2, valuesToPassForValidation),
						"Attributes are not validated successfully");
			} else {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC50", 2, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			order.navigateToOrderValidation(22, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway1 = repo.validateSOMIntegrationReportsSpcl(xls, "TC50", 3, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway1, "Attributes are not validated successfully");
			if (!is4xgateway1) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC50", 3, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			order.navigateToOrderValidation(23, "Yes", "No", "SOM");
			if (is4xgateway1) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC50", 4, valuesToPassForValidation),
						"Attributes are not validated successfully");
			} else {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC50", 4, valuesToPassForValidation),
						"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest21);
			repo = createChildTest(childTest21, extent, repo);
			order = createChildTest(childTest21, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC50", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC50", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest22);
			orderHistory = createChildTest(childTest22, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving AddRemove_channels_BlueSKY_TV");
		} catch (Exception e) {
			log.error("Error in AddRemove_channels_BlueSKY_TV:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Modification_Existing_Account_Fiber_Modem(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Verify modification of an existing account with Fiber Modem");
			log.debug("Entering Modification_Existing_Account_Fiber_Modem");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			IntFibrSrlNo = Utility.generateHardwareSerailNum(IntFibrSrlNo);
			String internetHardwareSerialNbr = IntFibrSrlNo;
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
//			order.openOEStubTochangeLocationDropType("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);
//			order.deleteHardware("Switch Window");

			ExtentTest childTest4 = extent.startTest("Add Internet Fibre Modem Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.internetFiberModemHardwareSelection(internetHardwareSerialNbr);

			ExtentTest childTest5 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.techAppointmentWithCurrentDate();

			ExtentTest childTest6 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest7 = extent.startTest("Review and Finish");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest8 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest9 = extent.startTest("Verify COM and SOM Record initial record counts");
			test.appendChild(childTest9);
			com = createChildTest(childTest9, extent, com);
			som = createChildTest(childTest9, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest10 = extent.startTest("Wait for provisioning start Send serial No");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			order = createChildTest(childTest10, extent, order);
			order.navigateToOrderValidation(8, "No", "No", "COM");
			com.sendSerialNbrTask(internetHardwareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest11 = extent.startTest("Wait For Tech Confirm Mark Finished");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			order = createChildTest(childTest11, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			com.verifyComOrderStatusConfirmed_98();
			order.refreshPage();

			ExtentTest childTest12 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest13 = extent.startTest("Modify Fibre+ 75 To Fibre+ 150");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet("Fibre+ 150");

			ExtentTest childTest14 = extent.startTest("Navigate To Tech Appointment Tab");
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
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest18 = extent.startTest("Wait For Tech Confirm Mark Finished");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			order = createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest19 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			som = createChildTest(childTest19, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest20 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest20);
			repo = createChildTest(childTest20, extent, repo);
			order = createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC54", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(24, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC54", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest21);
			repo = createChildTest(childTest21, extent, repo);
			order = createChildTest(childTest21, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC54", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC54", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest22);
			orderHistory = createChildTest(childTest22, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Modification_Existing_Account_Fiber_Modem");
		} catch (Exception e) {
			log.error("Error in Modification_Existing_Account_Fiber_Modem:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Addition_Secondary_PhoneLine_XB6_converged(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify addition of Secondary Line for an account with XB6 converged");
			log.debug("Entering Addition_Secondary_PhoneLine_XB6_converged");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardwareSerialNbr = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
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
			  test.appendChild(childTest0); landing = createChildTest(childTest0, extent,
			  landing); landing.openUrl();
			  
			  ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			  test.appendChild(childTest1); order = createChildTest(childTest1, extent,
			  order); order.customerInformation("Residential", "Yes");
			  
			  ExtentTest childTest3 = extent.startTest("Add Phone");
			  test.appendChild(childTest3); order = createChildTest(childTest3, extent,
			  order); order.addServicesAndFeacturesTab(); String phoneNbr =
			  order.addServicePhone("Add Product", phoneProduct); order.selectVoiceMail();
			  
			  ExtentTest childTest4 = extent.startTest("Add Internet");
			  test.appendChild(childTest4); order = createChildTest(childTest4, extent,
			  order); order.addServicesAndFeacturesTab();
			  order.addServiceInternet(internetProduct);
			order.deleteHardware("Switch Window");
			  
			  ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			  test.appendChild(childTest5); order = createChildTest(childTest5, extent,
			  order);
//			   order.addConvergedHardware("Converged150", convergedHardwareSerialNbr);
			  order.addConvergedHardware1(convergedHardwareSerialNbr);
			  order.selectConvergedHardware(convergedHardware);
			  
			  ExtentTest childTest6 = extent.startTest("Navigate To Tech Appointment Tab");
			  test.appendChild(childTest6); order = createChildTest(childTest6, extent,
			  order); order.selectInstallationOption("Self Connect No", techAppointment);
			  order.enteringChangesAuthorizedBy();
			  
			  ExtentTest childTest7 = extent.startTest("Select Method of confirmation");
			  test.appendChild(childTest7); order = createChildTest(childTest7, extent,
			  order); order.selectMethodOfConfirmation("Voice");
			  order.selectingSalesRepresentativeCheckBox();
			  
			  ExtentTest childTest8 = extent.startTest("Review and Finish");
			  test.appendChild(childTest8); order = createChildTest(childTest8, extent,
			  order); order.reviewAndFinishOrder();
			  
			  ExtentTest childTest9 = extent.startTest("Navigate To COM & SOM Orders");
			  test.appendChild(childTest9); order = createChildTest(childTest9, extent,
			  order); String accntNum = order.getAccountNumber();
			  order.openCOMOrderPage("true", accntNum, "Yes");
			  
			  ExtentTest childTest10 =
			  extent.startTest("Verify COM, SOM Initial Record Counts");
			  test.appendChild(childTest10); com = createChildTest(childTest10, extent,
			  com); som = createChildTest(childTest10, extent, som);
			  com.verifyRecordsCountInCOMOrder(initialComCount);
			  som.verifyRecordsCountInSOMOrder(initialSomCount);
			  
			  ExtentTest childTest11 = extent.startTest("CLEC Request Before Job Run");
			  test.appendChild(childTest11); com = createChildTest(childTest11, extent,
			  com); com.verifyCLECRequestBeforeJobRun();
			  
			  ExtentTest childTest12 = extent.startTest("Navigate To E911 Job Run");
			  test.appendChild(childTest12); com = createChildTest(childTest12, extent,
			  com); order = createChildTest(childTest12, extent, order);
			  com.navigateToJobMonitorURL("E911"); order.navigateToOrderPage();
			  
			  ExtentTest childTest13 = extent.startTest("CLEC Request After Job Run");
			  test.appendChild(childTest13); com = createChildTest(childTest13, extent,
			  com); com.verifyCLECRequestAfterJobRun();
			  order.refreshPage();
			  order.refreshPage();
			 
			
			

			ExtentTest childTest14 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest15 = extent.startTest("Add Secondary Phone Line");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.addSecondaryPhone(phoneProduct, "Second Dropdown");

			ExtentTest childTest16 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);

			ExtentTest childTest17 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest18 = extent.startTest("Review and Finish");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest19 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest20 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest20);
			com = createChildTest(childTest20, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest21 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			order = createChildTest(childTest21, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest22);
			com = createChildTest(childTest22, extent, com);
			com.verifyCLECRequestAfterJobRun();
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest23 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest23);
			com = createChildTest(childTest23, extent, com);
			som = createChildTest(childTest23, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest24 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest24);
			repo = createChildTest(childTest24, extent, repo);
			som = createChildTest(childTest24, extent, som);
			order = createChildTest(childTest24, extent, order);
			String custPhNbrOne = som.navigateToNewPhoneRFSPhNbr1();
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, custPhNbrOne, convergedHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC56", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();
			som.navigateToSOMOrder();

			String custPhNbrTwo = som.navigateToNewPhoneRFSPhNbr2();
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, custPhNbrTwo, convergedHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC56", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest25 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest25);
			repo = createChildTest(childTest25, extent, repo);
			order = createChildTest(childTest25, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC56", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, custPhNbrOne, custPhNbrTwo, convergedHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC56", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest26 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest26);
			orderHistory = createChildTest(childTest26, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Addition_Secondary_PhoneLine_XB6_converged");
		} catch (Exception e) {
			log.error("Error in Addition_Secondary_PhoneLine_XB6_converged:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void TN_change_Ported_To_Native_with_XB6_Converged(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify TN change from Ported to Native for an account with XB6 Converged");
			log.debug("Entering TN_change_Ported_To_Native_with_XB6_Converged");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardwareSerialNbr = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
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
			String portPhoneNbr = order.portPhoneNumber("Phone Required", "", phoneProduct, "", "Yes", "No");

			ExtentTest childTest4 = extent.startTest("Add Internet");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);
			order.deleteHardware("Switch Window");
			

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardwareSerialNbr);
			order.addConvergedHardware1(convergedHardwareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
//			 order.deleteConvergedHardware();

			ExtentTest childTest6 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNo("On a date", "", "");

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
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest10 = extent.startTest("Navigate To LSC Job Run");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			com.navigateToJobMonitorURL("LSC");
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest11 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			order = createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12 = extent.startTest("Verify COM, SOM Initial Record counts");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			som = createChildTest(childTest12, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest13 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest14 = extent.startTest("Cust mobile Nbr Change PortedTN To Native");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			String PhoneNbr = order.changeCustNbrPortedToNative(phoneProduct);
			order.selectPortOut();

			ExtentTest childTest15 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.NavigateTechAppointmentTab();
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();

			/*
			 * ExtentTest childTest16=extent.startTest("Remove portout number");
			 * test.appendChild(childTest16); order=createChildTest(childTest16, extent,
			 * order); order.addServicesAndFeacturesTab(); order.selectPortOut();
			 */

			ExtentTest childTest17 = extent.startTest("Review and Finish");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest18 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

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

			ExtentTest childTest21 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			som = createChildTest(childTest21, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest22 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest22);
			repo = createChildTest(childTest22, extent, repo);
			order = createChildTest(childTest22, extent, order);
			order.navigateToOrderValidation(3, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, portPhoneNbr, convergedHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC57", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(4, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, PhoneNbr, convergedHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC57", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC57", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest23 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest23);
			repo = createChildTest(childTest23, extent, repo);
			order = createChildTest(childTest23, extent, order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, portPhoneNbr, convergedHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC57", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, PhoneNbr, convergedHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC57", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, PhoneNbr, convergedHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC57", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest24 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest24);
			orderHistory = createChildTest(childTest24, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving TN_change_Ported_To_Native_with_XB6_Converged");
		} catch (Exception e) {
			log.error("Error in TN_change_Ported_To_Native_with_XB6_Converged:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Cancelling_ReSbmitting_order_XB6_Converged(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify Cancelling and Re-submitting an order on account with XB6 Converged");
			log.debug("Entering Cancelling_ReSbmitting_order_XB6_Converged");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardwareSerialNbr = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
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

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardwareSerialNbr);
			order.addConvergedHardware1(convergedHardwareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
//			order.deleteHardware("Switch Window");

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
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest9 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest9);
			com = createChildTest(childTest9, extent, com);
			som = createChildTest(childTest9, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

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
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest13 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest14 = extent.startTest("Add Secondary Phone Line");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.addSecondaryPhone(phoneProduct, "Second Dropdown");

			ExtentTest childTest15 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.selectInstallationOption("Self Connect No", "Yes");
			order.techAppoinmentYes("Yes", "");

			ExtentTest childTest16 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest17 = extent.startTest("Review and Finish");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest18 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest19 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.navigateToCustOrderPage("false", accntNum);
			order.cancelOrderTNNumber("Cancle Order", "CheckBox");

			ExtentTest childTest20 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest21 = extent.startTest("Retrieve order information");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.navigateToCustOrderPage("false", accntNum);
			order.retrieveOrderInformation();
			order.effectiveDateConfirmation("");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest22 = extent.startTest("Review and Finish");
			test.appendChild(childTest22);
			order = createChildTest(childTest22, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest23 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest24 = extent.startTest("Wait for Effective date Mark finish Task");
			test.appendChild(childTest24);
			com = createChildTest(childTest24, extent, com);
			order = createChildTest(childTest24, extent, order);
			order.navigateToOrderValidation(27, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest25 = extent.startTest("Wait for Provisioning start Mark Finish Task");
			test.appendChild(childTest25);
			com = createChildTest(childTest25, extent, com);
			order = createChildTest(childTest25, extent, order);
			order.navigateToOrderValidation(27, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest26 = extent.startTest("Wait For Tech Confirm Mark Finish task");
			test.appendChild(childTest26);
			com = createChildTest(childTest26, extent, com);
			order = createChildTest(childTest26, extent, order);
			order.navigateToOrderValidation(27, "No", "No", "COM");
			com.verifySVGDiagram("");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest27 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest27);
			com = createChildTest(childTest27, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest28 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest28);
			com = createChildTest(childTest28, extent, com);
			order = createChildTest(childTest28, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest29 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest29);
			com = createChildTest(childTest29, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest30 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest30);
			com = createChildTest(childTest30, extent, com);
			som = createChildTest(childTest30, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest31 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest31);
			orderHistory = createChildTest(childTest31, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Cancelling_ReSbmitting_order_XB6_Converged");
		} catch (Exception e) {
			log.error("Error in Cancelling_ReSbmitting_order_XB6_Converged:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Remove_Email_Hitron_Device(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Verify removal of e-mails for an account with Hitron device");
			log.debug("Entering Remove_Email_Hitron_Device");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
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

			ExtentTest childTest3 = extent.startTest("Add Internet And Email");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);
			order.addInternetEmail("", "Calgary");

			ExtentTest childTest4 = extent.startTest("Add Internet hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
//			 order.deleteConvergedHardware();

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
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest9 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest9);
			com = createChildTest(childTest9, extent, com);
			som = createChildTest(childTest9, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest10 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest11 = extent.startTest("Delete email address");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.emailDelete();

			ExtentTest childTest12 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest13 = extent.startTest("Review and Finish");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest14 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest15 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			som = createChildTest(childTest15, extent, som);
			//com.switchPreviousTab();
			order.openCOMOrderPage("true", accntNum, "");
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest16 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest16);
			repo = createChildTest(childTest16, extent, repo);
			order = createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(16, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC60", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest17 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest17);
			repo = createChildTest(childTest17, extent, repo);
			order = createChildTest(childTest17, extent, order);
			order.navigateToOrderValidation(39, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC60", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC60", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest18 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest18);
			orderHistory = createChildTest(childTest18, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Remove_Email_Hitron_Device");
		} catch (Exception e) {
			log.error("Error in Remove_Email_Hitron_Device:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Changing_Staff_To_Residential_XB6_BlueSky_TV(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify changing account type from Staff to Residential for an account with XB6 and Blue Sky TV");
			log.debug("Entering Changing_Staff_To_Residential_XB6_BlueSky_TV");
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
			String techAppointment = data.get("Tech_Appointment");
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
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

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			landing = createChildTest(childTest1, extent, landing);
			landing.openUrl();
			order.customerInformation("Staff", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest55 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest55);
			order = createChildTest(childTest55, extent, order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
//			 order.selectConvergedHardware("Internet");
//			order.deleteHardware("Switch Window");

			ExtentTest childTest4 = extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			order.selectTodoListCheckBox();

			ExtentTest childTest6 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			//order.mocaFilterSelecttion("");

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
			com = createChildTest(childTest9, extent, com);
			som = createChildTest(childTest9, extent, som);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest10 = extent.startTest("Verify COM, SOM initial Record counts");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest11 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
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

			ExtentTest childTest14 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest15 = extent.startTest("Changing Account type Staff To Residential");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.accountType("Residential");
			order.addServicesAndFeacturesTab();

			ExtentTest childTest16 = extent.startTest("Add Phone, Internet And TV");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.addPhoneType("Personal Phone");
			order.selectVoiceMail();
			order.addServiceInternet("Fibre+ 75");
			order.addServiceTV("Digital Classic");

			ExtentTest childTest17 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest18 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest19 = extent.startTest("Review and Finish");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest20 = extent.startTest("Navigate To COM Order");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest21 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			som = createChildTest(childTest21, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest22 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest22);
			orderHistory = createChildTest(childTest22, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Changing_Staff_To_Residential_XB6_BlueSky_TV");
		} catch (Exception e) {
			log.error("Error in Changing_Staff_To_Residential_XB6_BlueSky_TV:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Changing_NativeTN_NativeTN_DPT(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify changing Native TN to Native TN for an account with DPT");
			log.debug("Entering Changing_NativeTN_NativeTN_DPT");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			String internetProduct = data.get("Internet_Product");
			String convergedHardware = data.get("Converged_hardware");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNo = PhSrlNo;
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);
//			order.deleteHardware("Switch Window");

			ExtentTest childTest4 = extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneSerialNo, phoneHardware);

			ExtentTest childTest5 = extent.startTest("Add Internet Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
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
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest10 = extent.startTest("Verify COM, SOM Initial Record Counts");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest11 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			order = createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest13 = extent.startTest("Change phone number");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.changeMobileNbrPortedToNative(phoneProduct);

			ExtentTest childTest14 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest15 = extent.startTest("Remove portout number");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.addServicesAndFeacturesTab();
			order.selectPortOut();

			ExtentTest childTest16 = extent.startTest("Review and Finish");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest17 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest18 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			som = createChildTest(childTest18, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest19 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			repo = createChildTest(childTest19, extent, repo);
			String custPhNbrOne = som.navigateToNewPhoneRFSPhNbr1();
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, custPhNbrOne));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC62", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest20 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest20);
			repo = createChildTest(childTest20, extent, repo);
			order = createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneSerialNo));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC62", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, custPhNbrOne, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC62", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest21);
			orderHistory = createChildTest(childTest21, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Changing_NativeTN_NativeTN_DPT");
		} catch (Exception e) {
			log.error("Error in Changing_NativeTN_NativeTN_DPT:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Changing_NativeTN_NativeTN_Converged(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify changing Native TN to Native TN for an account with XB6 Converged");
			log.debug("Entering Changing_NativeTN_NativeTN_Converged");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
			int clecOrderCount = Math.round(Float.valueOf(data.get("CLEC_Order_Count")));
			int comOrderCount = Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount = Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);
			order.deleteHardware("Switch Window");

			ExtentTest childTest4 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);

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
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest9 = extent.startTest("Navigate To COM, SOM Initial Record Counts");
			test.appendChild(childTest9);
			com = createChildTest(childTest9, extent, com);
			som = createChildTest(childTest9, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest10 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			order = createChildTest(childTest10, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest11 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest12 = extent.startTest("Change phone number");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.changeMobileNbrPortedToNative(phoneProduct);

			ExtentTest childTest13 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest14 = extent.startTest("Remove portout number");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.addServicesAndFeacturesTab();
			order.selectPortOut();

			ExtentTest childTest15 = extent.startTest("Review and Finish");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest16 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest17 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest17);
			com = createChildTest(childTest17, extent, com);
			som = createChildTest(childTest17, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest18 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			som = createChildTest(childTest18, extent, som);
			repo = createChildTest(childTest18, extent, repo);
			String custPhNbrOne = som.navigateToNewPhoneRFSPhNbr1();
			order.navigateToOrderPage();
			order.navigateToOrderValidation(3, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, custPhNbrOne, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC63", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			String custPhNbrTwo = som.navigateToNewPhoneRFSPhNbr2();
			order.navigateToOrderPage();
			order.navigateToOrderValidation(4, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, custPhNbrTwo, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC63", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest19 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest19);
			repo = createChildTest(childTest19, extent, repo);
			order = createChildTest(childTest19, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC63", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC63", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest20 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest20);
			orderHistory = createChildTest(childTest20, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Changing_NativeTN_NativeTN_Converged");
		} catch (Exception e) {
			log.error("Error in Changing_NaviveTN_NativeTN_Converged:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Changing_Residential_To_Staff_XB6_BlueSky_TV(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify changing account type from Residential to Staff for an account with XB6 and Blue Sky TV");
			log.debug("Entering Changing_Residential_To_Staff_XB6_BlueSky_TV");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNo = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			TVBoxSrlNo = Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr = TVBoxSrlNo;
			TVPortalSrlNo = Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr = TVPortalSrlNo;
			String[] hardwareType = { "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber = { tvBoxSerialNbr, tvPortalSerialNbr };
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
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
			order.addServicesAndFeacturesTab();

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add TV With BlueSky");
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
			order.selectTodoListCheckBox();

			ExtentTest childTest6 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			//order.mocaFilterSelecttion("");

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
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest10 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest11 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest12 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			order = createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest13 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest13);
			com = createChildTest(childTest13, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest14 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.navigateToCustOrderPage("true", accntNum);
			order.accountType("Staff");

			ExtentTest childTest15 = extent.startTest("Add Phone, Internet and TV product");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.addServicesAndFeacturesTab();
			order.addPhoneType("Employee Personal Phone");
			order.selectVoiceMail();
			order.addServiceInternet("Employee Internet 75");
			order.addServiceTV("Employee TV");

			ExtentTest childTest16 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest17 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest18 = extent.startTest("Review and Finish");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest19 = extent.startTest("Navigate To COM Order");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest20 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest20);
			com = createChildTest(childTest20, extent, com);
			som = createChildTest(childTest20, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest21 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest21);
			orderHistory = createChildTest(childTest21, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Changing_Residential_To_Staff_XB6_BlueSky_TV");
		} catch (Exception e) {
			log.error("Error in Changing_Residential_To_Staff_XB6_BlueSky_TV:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void TN_change_Ported_To_Native_with_DPT(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify TN change from Ported to Native for an account with DPT");
			log.debug("Entering TN_change_Ported_To_Native_with_DPT");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNbr = PhSrlNo;
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
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
			landing = createChildTest(childTest0, extent, landing);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String portPhoneNbr = order.portPhoneNumber("Phone Required", "", phoneProduct, "", "No", "No");
			order.selectVoiceMail();

			ExtentTest childTest4 = extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneSerialNbr, phoneHardware);

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

			ExtentTest childTest11 = extent.startTest("Navigate To COM, SOM Initial Record Counts");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			som = createChildTest(childTest11, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest12 = extent.startTest("Navigate To LSC Job Run");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			order = createChildTest(childTest12, extent, order);
			com.navigateToJobMonitorURL("LSC");
			order.navigateToOrderPage();

			ExtentTest childTest13 = extent.startTest("Navigate to CLEC OPS task");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			landing = createChildTest(childTest13, extent, landing);
			order.navigateToCLECOPSUser("false", "Yes");
			landing.loggingOffCLECOPSUser();
			landing.Login();
			order.navigateToOrderPage();

			ExtentTest childTest14 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			order = createChildTest(childTest14, extent, order);
			com.navigateToCLECOrder();
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest15 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest16 = extent.startTest("Cust mobile Nbr Change PortedTN To Native");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			String PhoneNbr = order.changeCustNbrPortedToNative(phoneProduct);

			ExtentTest childTest17 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest18 = extent.startTest("Select portout numbers");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.addServicesAndFeacturesTab();
			order.selectPortOut();

			ExtentTest childTest19 = extent.startTest("Review and Finish");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest20 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest21 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			order = createChildTest(childTest21, extent, order);
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
			order.navigateToOrderValidation(3, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, portPhoneNbr, phoneSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC69", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest24 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest24);
			order = createChildTest(childTest24, extent, order);
			repo = createChildTest(childTest24, extent, repo);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, portPhoneNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC69", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, PhoneNbr, portPhoneNbr, internetHardwareSerialNbr, phoneSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC69", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest25 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest25);
			orderHistory = createChildTest(childTest25, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving TN_change_Ported_To_Native_with_DPT");
		} catch (Exception e) {
			log.error("Error in TN_change_Ported_To_Native_with_DPT:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Downgrade_Internet_To_Affordable(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Downgrade the Internet 150 unlimited to Affordable Internet on Triple Play Customer");
			log.debug("Entering Downgrade_Internet_To_Affordable");
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
			String techAppointment = data.get("Tech_Appointment");
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
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
			//order.selectTodoListCheckBox();
			order.selectTodo();

			ExtentTest childTest8 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			//order.mocaFilterSelecttion("");

			ExtentTest childTest9 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest10 = extent.startTest("Review and Finish");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest11 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest12 = extent.startTest("Navigate To COM, SOM Initial Counts");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			som = createChildTest(childTest12, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest13 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest13);
			com = createChildTest(childTest13, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest14 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			order = createChildTest(childTest14, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest15 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest16 = extent.startTest("Navigate to ISED Customer Order Page");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.navigateToCustOrderISEDPage("true", accntNum);

			ExtentTest childTest17 = extent.startTest("Verify ISED values in Customer Tab");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.addServiceInternet("Connecting Families");
			String ISEDpin = order.validateCustomerTabforISED();

			ExtentTest childTest18 = extent.startTest("Downgrade TV Internet300 to Affordable Internet");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet("Connecting Families");

			ExtentTest childTest19 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest20 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest21 = extent.startTest("Review and Finish");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest22 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest22);
			order = createChildTest(childTest22, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest23 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest23);
			com = createChildTest(childTest23, extent, com);
			som = createChildTest(childTest23, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest24 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest24);
			repo = createChildTest(childTest24, extent, repo);
			order = createChildTest(childTest24, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, ISEDpin));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC75", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(30, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, ISEDpin));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC75", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest25 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest25);
			orderHistory = createChildTest(childTest25, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Downgrade_Internet_To_Affordable");
		} catch (Exception e) {
			log.error("Error in Downgrade_Internet_To_Affordable:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Addition_Secondary_PhoneLine_XB6_DPT(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify addition of Secondary line for an account with DPT device");
			log.debug("Entering Addition_Secondary_PhoneLine_XB6_DPT");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardwareSerialNbr = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNbr = PhSrlNo;
			String phoneHardware = data.get("Phone_hardware");
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
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

			ExtentTest childTest3 = extent.startTest("Add Phone");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();

			ExtentTest childTest4 = extent.startTest("Add Internet");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardwareSerialNbr);
			order.addConvergedHardware1(convergedHardwareSerialNbr);
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
			order.addServicesAndFeacturesTab();
			order.reviewAndFinishOrder();

			ExtentTest childTest9 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest10 = extent.startTest("Verify COM & SOM Orders");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

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
			order.refreshPage();

			ExtentTest childTest14 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest15 = extent.startTest("Add Secondary Phone Line");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.addSecondaryPhone(phoneProduct, "Second Dropdown");

			ExtentTest childTest16 = extent.startTest("Add Phone hw for Secondary Ph Line");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.addPhoneHardware(phoneHardware, phoneSerialNbr);

			ExtentTest childTest17 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest18 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest19 = extent.startTest("Review and Finish");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest20 = extent.startTest("Navigate To COM Order");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest21 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest22 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest22);
			com = createChildTest(childTest22, extent, com);
			order = createChildTest(childTest22, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest23 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest23);
			com = createChildTest(childTest23, extent, com);
			com.verifyCLECRequestAfterJobRun();
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

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
			String custPhNbrOne = som.navigateToNewPhoneRFSPhNbr1();
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, custPhNbrOne, convergedHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC78", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest26 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest26);
			repo = createChildTest(childTest26, extent, repo);
			order = createChildTest(childTest26, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC78", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			String custPhNbrTwo = "";
			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, custPhNbrOne, custPhNbrTwo, convergedHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC78", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest27 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest27);
			orderHistory = createChildTest(childTest27, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Addition_Secondary_PhoneLine_XB6_DPT");
		} catch (Exception e) {
			log.error("Error in Addition_Secondary_PhoneLine_XB6_DPT:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Failure_FFMcall_Failure_Service_Call_Booking_Modify(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Failure of FFM call due to failure in service call booking during Modify");
			log.debug("Entering Failure_FFMcall_Failure_Service_Call_Booking_Modify");
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
			TVMOCASrlNo = Utility.generateHardwareSerailNum(TVMOCASrlNo);
			String tvMOCAHardwareSlNbr = TVMOCASrlNo;
			// String[] hardwareType1={"ShawBlueSkyTV4Kbox"};
			// String[] serialNumber1={tvMOCAHardwareSlNbr};
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

			ExtentTest childTest4 = extent.startTest("Add Internet");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			// order.deleteHardware("Switch Window");

			ExtentTest childTest6 = extent.startTest("Add TV and TV Hardware");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

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
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest11 = extent.startTest("Navigate To COM, SOM Initial Record Counts");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			som = createChildTest(childTest11, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest12 = extent.startTest("Navigate to Stub to fail FFM Service call");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.navigateToFieldForceManagementFail("true");
			order.switchFirstNewTab();

			ExtentTest childTest13 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest14 = extent.startTest("Add TV With MOCA Enable Hardware");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.addServiceTV(tvProduct);
			order.changeHwToMOCAEnabled(tvMOCAHardwareSlNbr);
			order.selectTodo();

			ExtentTest childTest15 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNo("On a date", "", "");

			ExtentTest childTest16 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			//order.selectDefaultInstallationCheckBox();

			ExtentTest childTest17 = extent.startTest("Review and Finish");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest18 = extent.startTest("Navigate To COM & SOM Order");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest19 = extent
					.startTest("Navigate To Order Entry will set the Service Call Required flag as YES");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.scrollDown600();
			com.scrollUp600();
			order.navigateToOrderPage();

			ExtentTest childTest20 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest20);
			com = createChildTest(childTest20, extent, com);
			som = createChildTest(childTest20, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest21 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest21);
			repo = createChildTest(childTest21, extent, repo);
			order = createChildTest(childTest21, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, tvHardwareSerialNbr, tvMOCAHardwareSlNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC80", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest22);
			orderHistory = createChildTest(childTest22, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Failure_FFMcall_Failure_Service_Call_Booking_Modify");
		} catch (Exception e) {
			log.error("Error in Failure_FFMcall_Failure_Service_Call_Booking_Modify:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Preferred_Payment_Modify(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify for existing customers, if the Preferred Payment date is being set as part of a Modify order, user can un-select it again before order is submitted. Verify if flag is unselected and order is submitted user can change flag");
			log.debug("Entering Preferred_Payment_Modify");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String tvProduct = data.get("TV_Product");
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

			ExtentTest childTest1 = extent.startTest("Fill Customer Information");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3 = extent.startTest("Add TV Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceTV(tvProduct);

			ExtentTest childTest4 = extent.startTest("Add TV Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest5 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest6 = extent.startTest("Review and Finish");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest7 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			com = createChildTest(childTest7, extent, com);
			som = createChildTest(childTest7, extent, som);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest8 = extent.startTest("Navigate To COM, SOM Initial Record Counts");
			test.appendChild(childTest8);
			com = createChildTest(childTest8, extent, com);
			som = createChildTest(childTest8, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest9 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest10 = extent.startTest("Check Preferred Flag");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.selectBillType("");
			order.checkPreferredPayment("No");

			ExtentTest childTest11 = extent.startTest("Review and Finish Order");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.navigatetoAppointmentTab();
			order.selectingSalesRepresentativeCheckBox();
			order.reviewAndFinishOrder();

			ExtentTest childTest12 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest13 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest14 = extent.startTest("Check Preferred Flag");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.selectBillType("");
			order.checkPreferredPayment("Yes");

			ExtentTest childTest15 = extent.startTest("Review and Finish Order");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.navigatetoAppointmentTab();
			order.selectingSalesRepresentativeCheckBox();
			order.reviewAndFinishOrder();

			ExtentTest childTest16 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest17 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest17);
			com = createChildTest(childTest17, extent, com);
			som = createChildTest(childTest17, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest18 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			repo = createChildTest(childTest18, extent, repo);
			order = createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(26, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC101", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest19 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest19);
			orderHistory = createChildTest(childTest19, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Preferred_Payment_Modify");
		} catch (Exception e) {
			log.error("Error in Preferred_Payment_Modify:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Modify_Service_Agreement_Service_Agreement_Changes(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Perform Modify on an account with Service Agreement, such that the service agreement changes");
			log.debug("Entering Modify_Service_Agreement_Service_Agreement_Changes");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			TVDCXSrlNo = Utility.generateHardwareSerailNum(TVDCXSrlNo);
			String tvHardwareSerialNbr = TVDCXSrlNo;
			String[] hardwareType = { "DCX3200M" };
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

			ExtentTest childTest4 = extent.startTest("Add Internet Product");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			// order.deleteHardware("Switch Window");

			ExtentTest childTest7 = extent.startTest("Add Promotions And service Agreement for Internet");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.addInternetPriceGuarentee("2YVP Int", internetProduct);

			ExtentTest childTest8 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest6 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest9 = extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			com = createChildTest(childTest10, extent, com);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest11 = extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest12 = extent.startTest("Add TV Product");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.addServiceTV(tvProduct);

			ExtentTest childTest13 = extent.startTest("Add TV Hardware");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest14 = extent.startTest("Add Promotions");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.addTVPromotionsSrvAgreement("Modify");

			ExtentTest childTest23 = extent.startTest("Add Promotions And service Agreement for Internet");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.addInternetPriceGuarentee("", "Fibre+ 75");

			ExtentTest childTest15 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNo("On a date", "", "");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest16 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.selectMethodOfConfirmation("Email");
			order.clickFinishOrder();

			ExtentTest childTest17 = extent.startTest("Review and Finish");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.selectDefaultInstallationCheckBox();
			order.reviewAndFinishOrder();

			ExtentTest childTest18 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			com = createChildTest(childTest18, extent, com);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest19 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			som = createChildTest(childTest19, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest20 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest20);
			repo = createChildTest(childTest20, extent, repo);
			order = createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(10, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC102", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC102", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest21);
			repo = createChildTest(childTest21, extent, repo);
			order = createChildTest(childTest21, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC102", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC102", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest22);
			orderHistory = createChildTest(childTest22, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Modify_Service_Agreement_Service_Agreement_Changes");
		} catch (Exception e) {
			log.error("Error in Modify_Service_Agreement_Service_Agreement_Changes:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Add_Purchased_XPOD_Package_Exisitng_Customer(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Add purchased xPOD package for an existing customer");
			log.debug("Entering Add_Purchased_XPOD_Package_Exisitng_Customer");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			String convergedHardware = data.get("Converged_hardware");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNbr = PhSrlNo;
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			XPODSrlNo = Utility.generateHardwareSerailNum(XPODSrlNo);
			String xpod1PKValue = XPODSrlNo;
			XPODSrlNo = Utility.generateHardwareSerailNum(XPODSrlNo);
			String xpodValue1 = XPODSrlNo;
			XPODSrlNo = Utility.generateHardwareSerailNum(XPODSrlNo);
			String xpodValue2 = XPODSrlNo;
			XPODSrlNo = Utility.generateHardwareSerailNum(XPODSrlNo);
			String xpodValue3 = XPODSrlNo;
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
			order.customerInformation("Staff", "Yes");
			order.addServicesAndFeacturesTab();

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneSerialNbr, phoneHardware);

			ExtentTest childTest5 = extent.startTest("Add Internet hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceInternet(internetProduct);
			order.addInternetHardwareForStaff(internetHardware, internetHardwareSerialNbr);

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

			ExtentTest childTest9 = extent.startTest("Navigate To COM and SOM Orders");
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

			ExtentTest childTest13 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest14 = extent.startTest("Delete Internet Hardware and add Converged Hardware");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			order.deleteHardware("Switch Window");

			ExtentTest childTest15 = extent.startTest("Add Purchased xPOD packages");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.addRentalXpods("Staff", xpodValue1, xpodValue2, xpodValue3, xpod1PKValue, "true");

			ExtentTest childTest16 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest17 = extent.startTest("Review and Finish");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest18 = extent.startTest("Navigate To COM and SOM Orders");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest19 = extent.startTest("Verify CLEC, COM and SOM Record counts");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			som = createChildTest(childTest19, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest20 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest20);
			repo = createChildTest(childTest20, extent, repo);
			order = createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC105", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");

			ExtentTest childTest21 = extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest21);
			orderHistory = createChildTest(childTest21, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Add_Purchased_XPOD_Package_Exisitng_Customer");
		} catch (Exception e) {
			log.error("Error in Add_Purchased_XPOD_Package_Exisitng_Customer:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Validate_Tenant_to_Residential_Account_Type_Change_during_Modify(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Validate Tenant to Residential Account Type Change during Modify");
			log.debug("Entering Validate_Tenant_to_Residential_Account_Type_Change_during_Modify");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneHardware = data.get("Phone_hardware");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardWareSerialNbr = PhSrlNo;
			String phoneProduct = data.get("Phone_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;

			String tvProduct = data.get("TV_Product");
			BlueCurveWirelessSrlNo = Utility.generateHardwareSerailNum(BlueCurveWirelessSrlNo);
			String BlueCurveWireless4KPlayer418SrlNum = BlueCurveWirelessSrlNo;
			String[] hardwareType = { "BlueCurve TV Wireless 4K Player" };
			String[] serialNumber = { BlueCurveWireless4KPlayer418SrlNum };
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
//			order.openOEStubTochangeLocationCoaxial("false");
			order.navigateToCDIchangeAccountType("All");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.mduCustomerInformation("BulkMaster", "", "Delay Crew", "Yes");

			ExtentTest childTest2 = extent.startTest("Add Internet and TV Product");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.addServicesAndFeacturesTab();
			
			order.addServiceBulkInternet("Bulk Fibre+ 300");
			
			order.addBulkInternetProduct("Base Price - Bulk Fibre+ 300",
					"Rental BlueCurve Gateway WiFi Modem (XB6) - 348");
			order.addServiceBulkTV("Bulk Total TV");
			order.addBulkTVProduct("Base Price - Bulk Total TV (Canada)", "BlueCurve TV Player Wireless 4K Rental");
			order.addPromotionsForBulkProduct();
			order.addProductPrices();
			

			ExtentTest childTest3 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.selectInstallationOption("", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest4 = extent.startTest("Review and Finish");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.reviewOrder();
			order.clickFinishOrder();

			ExtentTest childTest5 = extent.startTest("Navigate To COM, SOM, CLEC Orders");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest6 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest6);
			orderHistory = createChildTest(childTest6, extent, orderHistory);
//			String accntNum2=order.getAccountNumber();
			orderHistory.navigateToOrderHistoryUrl(accntNum, "Product Summary");

//			String accntNum = "58736237075";

			ExtentTest childTest7 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
//			String accntNum=order.getAccountNumber();
			order.navigateTomasterCustOrderForTenantAccount(accntNum);
//			order.warningokToClose();

			ExtentTest childTest8 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.customerInformation("", "");

			ExtentTest childTest9 = extent.startTest("Add TV Hardware");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.addServicesAndFeacturesTab();
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest10 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.addConvergedHardware1(convergedHardWareSerialNbr);

			ExtentTest childTest11 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.selectInstallationOption("", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.addServicesAndFeacturesTab();
			order.selectTodoListCheckBox();

			ExtentTest childTest12 = extent.startTest("Review and Finish");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest13 = extent.startTest("Navigate To COM, SOM, CLEC Orders");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			String accntNum1 = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum1, "Yes");
			order.refreshPage();
			order.refreshPage();

//			ExtentTest childTest14 = extent.startTest("Navigate to Complete New Customer Order");
//			test.appendChild(childTest14);
//			com = createChildTest(childTest14, extent, com);
//			order = createChildTest(childTest14, extent, order);
//			order.navigateToOrderValidation(1, "No", "No", "COM");
//			com.setMarkFinishedTask();
//			order.navigateToOrderPage();

			ExtentTest childTest15 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest15);
			orderHistory = createChildTest(childTest15, extent, orderHistory);
//			String accntNum2=order.getAccountNumber();
			orderHistory.navigateToOrderHistoryUrl(accntNum1, "Product Summary");

			ExtentTest childTest16 = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest16);
			landing = createChildTest(childTest16, extent, landing);
			order = createChildTest(childTest16, extent, order);
			order.navigateFirstToOeStubServer();
//			order.openOEStubTochangeLocationCoaxial("false");
			order.navigateToCDIchangeAccountType("Residental/Staff");
			landing.Login();

//			String accntNum1 = "58736237247";

			ExtentTest childTest17 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
//			String accntNum=order.getAccountNumber();
			order.navigateToCustOrderWithLocId2(accntNum1);
			order.warningokToClose();

			ExtentTest childTest18 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.accountType("Residential");
			order.addCustomerMailingAddress("Service Location", " ");

			ExtentTest childTest19 = extent.startTest("Review");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.reviewOrder();

			ExtentTest childTest20 = extent.startTest("Add Phone,Internet and TV Product");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.addServicesAndFeacturesTab();
			order.ValidatePromotionsinGenral();
			order.Phoneopt_outTextValidation();

			ExtentTest childTest21 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.customerInformationUpdate();

			ExtentTest childTest22 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest22);
			order = createChildTest(childTest22, extent, order);
			order.selectInstallationOption("Self Connect No", "No");
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest23 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest24 = extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest24);
			order = createChildTest(childTest24, extent, order);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();
			order.addServicesAndFeacturesTab();

			ExtentTest childTest25 = extent.startTest("Review and Finish");
			test.appendChild(childTest25);
			order = createChildTest(childTest25, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest26 = extent.startTest("Navigate To COM, SOM, CLEC Orders");
			test.appendChild(childTest26);
			order = createChildTest(childTest26, extent, order);
			String accntNum2 = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum2, "Yes");
			order.refreshPage();

			ExtentTest childTest27 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest27);
			orderHistory = createChildTest(childTest27, extent, orderHistory);
//			String accntNum2=order.getAccountNumber();
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Validate_Tenant_to_Residential_Account_Type_Change_during_Modify");
		} catch (Exception e) {
			log.error("Error in Validate_Tenant_to_Residential_Account_Type_Change_during_Modify:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Validate_Residental_to_Tenant_Account_Type_Change_during_Modify(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Validate_Residental_to_Tenant_Account_Type_Change_during_Modify");
			log.debug("Entering Validate_Residental_to_Tenant_Account_Type_Change_during_Modify");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneHardware = data.get("Phone_hardware");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardWareSerialNbr = PhSrlNo;
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			String tvProduct = data.get("TV_Product");
			TVHDSrlNo = Utility.generateHardwareSerailNum(TVHDSrlNo);
			String tvHardwareDCX3510SlNbr = TVHDSrlNo;
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String DCT700 = TVDctSrlNo;
			String[] hardwareType = { "DCX3510HDGuide" };
			String[] serialNumber = { tvHardwareDCX3510SlNbr };
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
//			order.openOEStubTochangeLocationCoaxial("false");
			order.navigateToCDIchangeAccountType("All");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrlWithLocationId2();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.mduCustomerInformation("BulkMaster", "", "Delay Crew", "Yes");

			ExtentTest childTest2 = extent.startTest("Add TV Product");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceBulkTV("Bulk Digital Basic");
			order.addBulkTVProduct("Base Price - Bulk Digital Basic (Canada)", "Rental DCT 700");
			order.addPromotionsForBulkProduct();

			ExtentTest childTest3 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.selectInstallationOption("", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest4 = extent.startTest("Review and Finish");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.reviewOrder();
			order.clickFinishOrder();

			ExtentTest childTest5 = extent.startTest("Navigate To COM, SOM, CLEC Orders");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest6 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest6);
			landing = createChildTest(childTest6, extent, landing);
			landing.openUrlWithLocationId2();

			ExtentTest childTest7 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest8 = extent.startTest("Add Internet Product");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet("Fibre+ 300");
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);

			ExtentTest childTest9 = extent.startTest("Add TV Hardware");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.addServiceTV("Small TV Prebuilt");
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			

			ExtentTest childTest10 = extent.startTest("Navigate to Promotion Page");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.addServicesAndFeacturesTab();
			order.addPromotionsSingleIntSrvAgreement("Internet");
			

			ExtentTest childTest11 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.selectInstallationOption("Self Connect No", "No");
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest12 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest13 = extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();
			order.addServicesAndFeacturesTab();

			ExtentTest childTest14 = extent.startTest("Review and Finish");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest15 = extent.startTest("Navigate To COM, SOM, CLEC Orders");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			String accntNum1 = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum1, "Yes");
			order.refreshPage();
			order.refreshPage();

//				String accntNum1 = "58736237768";
//				String accntNum = "58736237766";

			ExtentTest childTest16 = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest16);
			landing = createChildTest(childTest16, extent, landing);
			order = createChildTest(childTest16, extent, order);
			order.navigateFirstToOeStubServer();
//			order.openOEStubTochangeLocationCoaxial("false");
			order.navigateToCDIchangeAccountType("All");
			landing.mj1Login();

			ExtentTest childTest17 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
//				String accntNum=order.getAccountNumber();
			order.navigateToaccountTomasterAccountForPremise(accntNum1, accntNum);
			order.warningokToClose();

			ExtentTest childTest18 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.customerInformation("", "");

			ExtentTest childTest19 = extent.startTest("Add TV Hardware");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceTV("Digital Basic");
			order.addTVSrlNoWithOutHrdware("BlueCurve TV", DCT700);
			order.addOnDemandFeatures();
			order.ValidatePromotionsinGenral();

			ExtentTest childTest20 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.selectInstallationOption("Self Connect No", "No");
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest21 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest22 = extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest22);
			order = createChildTest(childTest22, extent, order);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();
			order.addServicesAndFeacturesTab();

			ExtentTest childTest23 = extent.startTest("Review and Finish");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest24 = extent.startTest("Navigate To COM, SOM, CLEC Orders");
			test.appendChild(childTest24);
			order = createChildTest(childTest24, extent, order);
			String accntNum2 = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum2, "Yes");
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest25 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest25);
			orderHistory = createChildTest(childTest25, extent, orderHistory);
//				String accntNum2=order.getAccountNumber();
			orderHistory.navigateToOrderHistoryUrl(accntNum2, "");

			log.debug("Leaving Validate_Residental_to_Tenant_Account_Type_Change_during_Modify");
		} catch (Exception e) {
			log.error("Error in Validate_Residental_to_Tenant_Account_Type_Change_during_Modify:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
		
	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Modify_Offering_Tripleplay_Account_Upgrading_Internet_Downgrading_TV(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Modify Offering on Tripleplay Account Upgrading Internet+Downgrading TV");
			log.debug("Entering Modify_Offering_Tripleplay_Account_Upgrading_Internet_Downgrading_TV");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}

			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String phoneHardware = data.get("Phone_hardware");
			String internetHardware = data.get("Internet_hardware");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardWareSerialNbr = PhSrlNo;
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			String convergedSerialNbr = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			TVHDSrlNo = Utility.generateHardwareSerailNum(TVHDSrlNo);
			String tvHardwareDCX3510SlNbr = TVHDSrlNo;
			String[] hardwareType = { "DCX3510HDGuide" };
			String[] serialNumber = { tvHardwareDCX3510SlNbr };

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
//			order.openOEStubTochangeLocationCoaxial("false");
			landing.A1Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Add Phone Product And DPT Hardware");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addPhoneHardware(phoneHardWareSerialNbr, phoneHardware);

			ExtentTest childTest3 = extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Internet Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);

			ExtentTest childTest5 = extent.startTest("Add TV Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceTV("Small TV Prebuilt");
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest6 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", "No");
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest7 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest8 = extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.enteringChangesAuthorizedBy();
//			order.selectingSalesRepresentativeCheckBox();
			order.addServicesAndFeacturesTab();

			ExtentTest childTest9 = extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest10);
			orderHistory = createChildTest(childTest10, extent, orderHistory);
			String accntNum = order.getAccountNumber();
			orderHistory.navigateToOrderHistoryUrl(accntNum, "Product Summary");

			ExtentTest childTest11 = extent.startTest("Navigate To COM, SOM, CLEC Orders");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
//			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest12 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			order = createChildTest(childTest12, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			
			ExtentTest childTest23 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest23);
			com = createChildTest(childTest23, extent, com);
			order = createChildTest(childTest23, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest13 = extent.startTest("LOGIN with UserA2 to Inventory");
			test.appendChild(childTest13);
			landing = createChildTest(childTest13, extent, landing);
			order = createChildTest(childTest13, extent, order);
			landing.A2Login();
			order.refreshPage();
			order.refreshPage();
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest14 = extent.startTest("Add Internet Product");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.addServiceInternet("Fibre+ 250");

			ExtentTest childTest15 = extent.startTest("Add TV Hardware");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.addServiceTV("Limited TV");

			ExtentTest childTest16 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.selectInstallationOption("Self Connect No", "No");
			order.enteringChangesAuthorizedBy();

			/*
			 * ExtentTest childTest17 = extent.startTest("Select Method of confirmation");
			 * test.appendChild(childTest17); order = createChildTest(childTest17, extent,
			 * order); order.selectMethodOfConfirmation("Voice");
			 */
			ExtentTest childTest18 = extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.enteringChangesAuthorizedBy();
//			order.selectingSalesRepresentativeCheckBox();
			order.addServicesAndFeacturesTab();

			ExtentTest childTest19 = extent.startTest("Review and Finish");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest20 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest20);
			orderHistory = createChildTest(childTest20, extent, orderHistory);
			String accntNum1 = order.getAccountNumber();
			orderHistory.navigateToOrderHistoryUrl(accntNum1, "Product Summary");

			ExtentTest childTest21 = extent.startTest("Navigate To COM, SOM, CLEC Orders");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
//			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum1, "Yes");

			ExtentTest childTest22 = extent.startTest("Check Quote Section");
			test.appendChild(childTest22);
			com = createChildTest(childTest22, extent, com);
//			com.verifyCLECRequestBeforeJobRun();
			com.navigateToCustomerDiscountsAndContracts("quote Section");

			log.debug("Leaving Modify_Offering_Tripleplay_Account_Upgrading_Internet_Downgrading_TV");
		} catch (Exception e) {
			log.error(
					"Error in Modify_Offering_Tripleplay_Account_Upgrading_Internet_Downgrading_TV:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	public static void main(String args[]) {
		InputStream log4j = ModifyCases.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
		PropertyConfigurator.configure(log4j);
	}
}
