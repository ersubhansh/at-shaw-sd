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

public class OtherCases extends SeleniumTestUp {

	LandingPage landing = null;
	OrderCreationPage order = null;
	COMOrdersPage com = null;
	SOMOrderPage som = null;
	ValidateReport repo = null;
	OrderHistoryPage orderHistory = null;
	SoftAssert softAssert = new SoftAssert();
	ArrayList<String> valuesToPassForValidation = new ArrayList<String>();

	Logger log = Logger.getLogger(OtherCases.class);

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void New_TV_with_MOCA_Enabled_Hw_Future_Date(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Create new TV service with a MOCA enabled hw in future date");
			log.debug("Entering New_TV_with_MOCA_Enabled_Hw_Future_Date");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			TVMOCASrlNo = Utility.generateHardwareSerailNum(TVMOCASrlNo);
			String tvMOCAHardwareSlNbr = TVMOCASrlNo;
			String[] hardwareType = { "ShawBlueSkyTV4Kbox" };
			String[] serialNumber = { tvMOCAHardwareSlNbr };
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

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");
			order.addServicesAndFeacturesTab();

			ExtentTest childTest3 = extent.startTest("Add TV With MOCA Enable Hardware");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest4 = extent.startTest("Add Internet product");
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
			order.selectTodoListCheckBox();

			ExtentTest childTest6 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNoCurrentDate("Yes", true);

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

			ExtentTest childTest10 = extent
					.startTest("Navigate To Order Entry will set the Service Call Required flag as YES");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			order = createChildTest(childTest10, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.scrollDown600();
			order.navigateToOrderPage();

			ExtentTest childTest11 = extent.startTest("Wait for Effective date Mark Finish task");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			order = createChildTest(childTest11, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest12 = extent.startTest("Check FFM Service Call Status And ID");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			order = createChildTest(childTest12, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.NavigvateToCustOrderTasksTab();
			order.navigateToOrderPage();

			ExtentTest childTest13 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest13);
			com = createChildTest(childTest13, extent, com);
			som = createChildTest(childTest13, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest14 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest14);
			repo = createChildTest(childTest14, extent, repo);
			order = createChildTest(childTest14, extent, order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			/*
			 * valuesToPassForValidation= new ArrayList<>(Arrays.asList());
			 * softAssert.assertTrue(repo.validateCOMIntegrationReports("TC68", 1,
			 * valuesToPassForValidation),"Attributes are not validated successfully");
			 * valuesToPassForValidation= new ArrayList<>(Arrays.asList());
			 * softAssert.assertTrue(repo.validateCOMIntegrationReports("TC68", 2,
			 * valuesToPassForValidation),"Attributes are not validated successfully");
			 * valuesToPassForValidation= new ArrayList<>(Arrays.asList(accntNum));
			 * softAssert.assertTrue(repo.validateCOMIntegrationReports("TC68", 3,
			 * valuesToPassForValidation),"Attributes are not validated successfully");
			 * valuesToPassForValidation= new ArrayList<>(Arrays.asList());
			 * softAssert.assertTrue(repo.validateCOMIntegrationReports("TC68", 4,
			 * valuesToPassForValidation),"Attributes are not validated successfully");
			 */
			order.navigateToOrderPage();

			ExtentTest childTest15 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest15);
			orderHistory = createChildTest(childTest15, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving New_TV_with_MOCA_Enabled_Hw_Future_Date");
		} catch (Exception e) {
			log.error("Error in New_TV_with_MOCA_Enabled_Hw_Future_Date:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void NewTV_MOCA_enabled_HW_New_Customer_IFM(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Add new TV service with a MOCA enabled hw to new customer during IFM");
			log.debug("Entering NewTV_MOCA_enabled_HW_New_Customer_IFM");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			TVMOCASrlNo = Utility.generateHardwareSerailNum(TVMOCASrlNo);
			String tvMOCAHardwareSlNbr = TVMOCASrlNo;
			String[] hardwareType = { "ShawBlueSkyTV4Kbox" };
			String[] serialNumber = { tvMOCAHardwareSlNbr };
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

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");
			order.addServicesAndFeacturesTab();

			ExtentTest childTest3 = extent.startTest("Add Internet Hardware");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			// order.deleteHardware("Switch Window");

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

			ExtentTest childTest9 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest10 = extent.startTest("Add TV With MOCA Enable Hardware");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			order.selectTodo();

			ExtentTest childTest11 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.effectiveDateConfirmation("");

			ExtentTest childTest12 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest13 = extent.startTest("Review and Finish");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest14 = extent.startTest("Navigate To COM & SOM Order");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest15 = extent
					.startTest("Navigate To service Information checking Service Call Required flag");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			com = createChildTest(childTest15, extent, com);
			order.navigateToOrderValidation(59, "No", "No", "COM");
			com.scrollDown600();
			order.navigateToOrderPage();

			ExtentTest childTest16 = extent.startTest("Wait for Effective date Mark finish Task");
			test.appendChild(childTest16);
			com = createChildTest(childTest16, extent, com);
			order = createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(59, "No", "No", "COM");
			/*
			 * String orderStatus=com.getOrderStatus(); if
			 * (orderStatus.equalsIgnoreCase("Processing")) { com.verifySVGDiagram("");
			 */
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			/*
			 * } else { order.navigateToOrderValidation(2, "No", "No", "COM");
			 * com.verifySVGDiagram(""); com.setMarkFinishedTask();
			 * order.navigateToOrderPage(); }
			 */

			ExtentTest childTest17 = extent.startTest("Wait for Provisioning start Mark Finish Task");
			test.appendChild(childTest17);
			com = createChildTest(childTest17, extent, com);
			order = createChildTest(childTest17, extent, order);
			order.navigateToOrderValidation(59, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest18 = extent.startTest("Wait For Tech Confirm Mark Finish task");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			order = createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(59, "No", "No", "COM");
			com.verifySVGDiagram("");
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
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC72", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(10, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvMOCAHardwareSlNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC72", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest21);
			repo = createChildTest(childTest21, extent, repo);
			order = createChildTest(childTest21, extent, order);
			order.navigateToOrderValidation(59, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, convergedHardWareSerialNbr, tvMOCAHardwareSlNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC72", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(60, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, convergedHardWareSerialNbr, tvMOCAHardwareSlNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC72", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest22);
			orderHistory = createChildTest(childTest22, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving NewTV_MOCA_enabled_HW_New_Customer_IFM");
		} catch (Exception e) {
			log.error("Error in NewTV_MOCA_enabled_HW_New_Customer_IFM:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Add_Rental_XPOD_To_XB6_Device(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Add Rental xPOD Package to an account having XB6 device with active Internet service");
			log.debug("Entering Add_Rental_XPOD_To_XB6_Device");
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

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Phone, Voice mail");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();

			ExtentTest childTest4 = extent.startTest("Add Internet");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			// order.selectConvergedHardware("Internet");

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
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest14 = extent.startTest("Add Rental XPOD Package");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.addRentalXpods("Residential", xpodValue1, xpodValue2, xpodValue3, xpod1PKValue, "false");

			ExtentTest childTest15 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
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

			ExtentTest childTest19 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest20 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest20);
			com = createChildTest(childTest20, extent, com);
			som = createChildTest(childTest20, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest21 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest21);
			repo = createChildTest(childTest21, extent, repo);
			order = createChildTest(childTest21, extent, order);
			order.navigateToOrderValidation(51, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, xpodValue1, xpodValue2, xpodValue3));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC74", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(52, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, xpodValue1, xpodValue2, xpodValue3));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC74", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(53, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, xpodValue1, xpodValue2, xpodValue3));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC74", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest22);
			repo = createChildTest(childTest22, extent, repo);
			order = createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(20, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC74", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC74", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(21, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC74", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC74", 4, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(22, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC74", 5, valuesToPassForValidation),
					"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC74", 6, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, xpodValue1, xpodValue2, xpodValue3));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC74", 7, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest23 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest23);
			orderHistory = createChildTest(childTest23, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Add_Rental_XPOD_To_XB6_Device");
		} catch (Exception e) {
			log.error("Error in Add_Rental_XPOD_To_XB6_Device:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void TriplePlay_OM_Sends_HPESA(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Verify that OM sends Priority to HPESA for new order");
			log.debug("Entering TriplePlay_OM_Sends_HPESA");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
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

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

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

			ExtentTest childTest8 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

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

			ExtentTest childTest12 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
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

			ExtentTest childTest15 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			som = createChildTest(childTest15, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest16 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest16);
			repo = createChildTest(childTest16, extent, repo);
			order = createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(3, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC76", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC76", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(10, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC76", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC76", 4, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(17, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC76", 5, valuesToPassForValidation),
					"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC76", 6, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(19, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC76", 7, valuesToPassForValidation),
					"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC76", 8, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest17 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest17);
			orderHistory = createChildTest(childTest17, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving TriplePlay_OM_Sends_HPESA");
		} catch (Exception e) {
			log.error("Error in TriplePlay_OM_Sends_HPESA:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void City_Search_Contains_Condiction_Value(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify the city search fetches the value in SEARCH_CONTAINS_CONDITION_VALUE pattern");
			log.debug("Entering City_Search_Contains_Condiction_Value");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String tvProduct = data.get("TV_Product");
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

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");
			order.addCustomerMailingAddress("", "Yes");
			order.addServicesAndFeacturesTab();

			ExtentTest childTest3 = extent.startTest("Add TV And TV Hardware");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest4 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest5 = extent.startTest("Review and Finish");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest6 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest7 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest7);
			com = createChildTest(childTest7, extent, com);
			som = createChildTest(childTest7, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest8 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest8);
			repo = createChildTest(childTest8, extent, repo);
			order = createChildTest(childTest8, extent, order);
			order.navigateToOrderValidation(10, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC79", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest9 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest9);
			orderHistory = createChildTest(childTest9, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving City_Search_Contains_Condiction_Value");
		} catch (Exception e) {
			log.error("Error in City_Search_Contains_Condiction_Value:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Automatic_Retry_Failed_Phone_HPSA_7004(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify automatic retry of failed Modify Digital Phone Line order when failure is caused by error code HPSA-7004");
			log.debug("Entering Automatic_Retry_Failed_Phone_HPSA_7004");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			String internetHardware = data.get("Internet_hardware");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardwareSerialNbr = PhSrlNo;
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String swapPhoneHardwareSerialNbr = PhSrlNo;
			String internetProduct = data.get("Internet_Product");
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
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

			ExtentTest childTest0 = extent.startTest("Navigate To Check Auto Retry Task");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			landing = createChildTest(childTest0, extent, landing);
			order.autoRetryTask();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");
			order.addServicesAndFeacturesTab();

			ExtentTest childTest3 = extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);

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

			ExtentTest childTest7 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectInstallationOption("", techAppointment);
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

			ExtentTest childTest10 = extent.startTest("Navigate To COM, SOM and CLEC Orders");
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
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest14 = extent.startTest("Phone Stub failed to Off Hook");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.navigateToOeStubToPassServices("Phone");

			ExtentTest childTest15 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest16 = extent.startTest("Swap Phone Hardware");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.swapPhoneHardware(swapPhoneHardwareSerialNbr);

			ExtentTest childTest17 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.selectInstallationOption("", techAppointment);
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();
			order.addServicesAndFeacturesTab();
			order.selectTodo();

			ExtentTest childTest18 = extent.startTest("Review and Finish");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest19 = extent.startTest("Navigate To COM, SOM And CLEC Orders");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
//		    String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest20 = extent.startTest("Verify COM ,SOM, CLEC Record counts");
			test.appendChild(childTest20);
			com = createChildTest(childTest20, extent, com);
			som = createChildTest(childTest20, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest21 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest21);
			repo = createChildTest(childTest21, extent, repo);
			order = createChildTest(childTest21, extent, order);
			order.navigateToOrderValidation(2, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC99", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest22);
			repo = createChildTest(childTest22, extent, repo);
			order = createChildTest(childTest22, extent, order);
			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC99", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest23 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest23);
			orderHistory = createChildTest(childTest23, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Automatic_Retry_Failed_Phone_HPSA_7004");
		} catch (Exception e) {
			log.error("Error in Automatic_Retry_Failed_Phone_HPSA_7004:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Create_And_Validate_Regional_Segments(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Create_And_Validate_Regional_Segments");
			log.debug("Entering Create_And_Validate_Regional_Segments");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String[] hardwareType = { "DCX3510HDGuide" };
			TVHDSrlNo = Utility.generateHardwareSerailNum(TVHDSrlNo);
			String tvHardwareDCX3510SlNbr = TVHDSrlNo;
			String[] serialNumber = { tvHardwareDCX3510SlNbr };
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardwareSerialNbr = ConvgdSrlNo;
			String convergedHardware = data.get("Converged_hardware");
			String techAppointment = data.get("Tech_Appointment");
			// String internetProduct=data.get("Internet_Product");
			// String internetHardware=data.get("Internet_hardware");
			// IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			// String internetHardwareSerialNbr=IntHitronSrlNo;
			// String techAppointment=data.get( "Tech_Appointment" );
			// int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			// int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
//			order.openOEStubTochangeLocationCoaxial("false");
			order.navigateToCDIchangeAccountType("All");
			landing.of1Login();

			ExtentTest childTest0 = extent.startTest("Create Segments And Validate");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.NavigateToOfferfilteringDasboard();
			order.CreateRegionalSegment2();
			order.CreateRegionalSegment3();
			order.DeletedRegionalSegment2();
			order.CreateCustomerSegment();
			order.ValidateCustomerSegment();

			ExtentTest childTest4 = extent.startTest("Navigate and Add Discounts");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.NavigateToDiscountsMapping1();
			order.Discount2YVP_InternetTV_Agreement();

//			ExtentTest childTest1=extent.startTest("Create Discounts");
//			test.appendChild(childTest1);
//			order=createChildTest(childTest1, extent, order);
//			order.NavigateToDiscountsMapping();

			ExtentTest childTest1 = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest1);
			landing = createChildTest(childTest1, extent, landing);
			order = createChildTest(childTest1, extent, order);
			order.navigateFirstToOeStubServer();
//			order.openOEStubTochangeLocationCoaxial("false");
//			order.ChangeAccountSubType("All");
			landing.Login();
			;

			ExtentTest childTest2 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest2);
			landing = createChildTest(childTest2, extent, landing);
			landing.openUrlWithLocationId2();

			ExtentTest childTest3 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			landing = createChildTest(childTest3, extent, landing);
			order.newCustomerInformation("Residential", "Yes");
//			order.addServicesAndFeacturesTab();
//			String accnt= order.FindAccountNumber();
//			landing.openUrlAddAccountWithLocationId2(accnt);

			/*
			 * ExtentTest childTest5=extent.startTest("Verify Account View ");
			 * test.appendChild(childTest5); order=createChildTest(childTest5,extent,order);
			 * order.addServicesAndFeacturesTab(); order.VerifySwitchToAccountView();
			 */

			ExtentTest childTest7 = extent.startTest("Add DCX 3510M TV Hardware");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceTV("Small TV Prebuilt");
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest6 = extent.startTest("Add Internet Product");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest8 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardwareSerialNbr);
			order.addConvergedHardware1(convergedHardwareSerialNbr);
			order.selectConvergedHardware("Internet");

			ExtentTest childTest9 = extent.startTest("Adding Promotions");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.addServicesAndFeacturesTab();
//			order.VerifySwitchToAccountView();
			order.add2YVP_Internet_TV_Promotions();
//			order.add24M_2YVP_fibre_300_Promotions();
			order.addServicesAndFeacturesTab();
			order.addInternetPriceGuarentee("", "Fibre+ 300");
			order.addServicesAndFeacturesTab();
			order.addTVPriceGuarenteePramotions("Small TV Prebuilt");

			ExtentTest childTest10 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest11 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest12 = extent.startTest("Review and Finish");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest14 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest14);
			orderHistory = createChildTest(childTest14, extent, orderHistory);
			String accntNum = order.getAccountNumber();
			orderHistory.navigateToOrderHistoryUrlLocationId2(accntNum, "Product Summary");

			ExtentTest childTest13 = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest13);
			landing = createChildTest(childTest13, extent, landing);
			order = createChildTest(childTest13, extent, order);
			order.navigateFirstToOeStubServer();
//			order.openOEStubTochangeLocationCoaxial("false");
			order.navigateToCDIchangeAccountType("Residental/Staff");

			log.debug("Create_And_Validate_Regional_Segments");
		} catch (Exception e) {
			log.error("Error in Create_And_Validate_Regional_Segments:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Create_Segments_OE_Display_Promo_Hybrid_Segments_And_Validate(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Create Segments OE Display Promo Hybrid Segments And Validate");
			log.debug("Entering Create_Segments_OE_Display_Promo_Hybrid_Segments_And_Validate");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			//String convergedHardWareSerialNo = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			/*
			 * ExtentTest childTest11 =
			 * extent.startTest("Check Stub Status and LOGIN to Inventory");
			 * test.appendChild(childTest11); landing = createChildTest(childTest11, extent,
			 * landing); order = createChildTest(childTest11, extent, order);
			 * order.navigateFirstToOeStubServer(); //
			 * order.openOEStubTochangeLocationCoaxial("false");
			 * order.navigateToCDIchangeAccountType("All"); landing.of1Login();
			 * 
			 * 
			 * ExtentTest childTest12=extent.startTest("Create Segments And Validate");
			 * test.appendChild(childTest12); order=createChildTest(childTest12, extent,
			 * order); order.NavigateToOfferfilteringDasboard1();
			 * order.CreatePartnerSegment1(); order.CreatePartnerSegment2();
			 * order.CreatePartnerSegment3(); order.NavigateToOfferfilteringDasboard();
			 * order.CreateCustomerSegment(); order.CreateHybridSeg(); //
			 * order.ValidateCustomerSegment();
			 * 
			 * ExtentTest childTest13=extent.startTest("Navigate and Add Discounts");
			 * test.appendChild(childTest13); order=createChildTest(childTest13, extent,
			 * order); order.NavigateToDiscountsMapping1();
			 * order.DiscountFreedomInternet150();
			 */

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
//			order.openOEStubTochangeLocationCoaxial("false");
			order.ChangeAccountSubType("All");
			landing.fg1Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrlWithLocationId2();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			landing = createChildTest(childTest1, extent, landing);
			order.newCustomerInformation("Residential", "Yes");
			order.addServicesAndFeacturesTab();
			/*
			 * String accnt= order.FindAccountNumber();
			 * landing.openUrlAddAccountWithLocationId2(accnt);
			 */

			ExtentTest childTest2 = extent.startTest("Add Internet Product");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.addServiceInternet("Fibre+ 300");
			//order.addInternetHardware(internetHardware, internetHardwareSerialNbr);

			ExtentTest childTest3 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNo);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);

			ExtentTest childTest4 = extent.startTest("Add Promotion and Validate");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServicesAndFeacturesTab();
			order.SwitchToAccountView();

			ExtentTest childTest5 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
//			
//			ExtentTest childTest6 = extent.startTest("Select Method of confirmation");
//			test.appendChild(childTest6);
//			order = createChildTest(childTest6, extent, order);
//			order.selectMethodOfConfirmation("Voice");

//			ExtentTest childTest7 = extent.startTest("Entering Changes authorized by");
//			test.appendChild(childTest7);
//			order = createChildTest(childTest7, extent, order);
//			order.enteringChangesAuthorizedBy();
////			order.selectingSalesRepresentativeCheckBox();
//			order.addServicesAndFeacturesTab();

			ExtentTest childTest8 = extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.reviewOrder();
			order.clickFinishOrder();

			ExtentTest childTest9 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest9);
			orderHistory = createChildTest(childTest9, extent, orderHistory);
			String accntNum = order.getAccountNumber();
			orderHistory.navigateToOrderHistoryUrlLocationId2(accntNum, "Product Summary");

			ExtentTest childTest10 = extent.startTest("Navigate To Account Information");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
//			String accntNum = order.getAccountNumber();
			order.openAccountInformation("true", accntNum);

			log.debug("Create_Segments_OE_Display_Promo_Hybrid_Segments_And_Validate");
		} catch (Exception e) {
			log.error("Error in Create_Segments_OE_Display_Promo_Hybrid_Segments_And_Validate:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Create_Segments_OE_Display_Promo_Segments_Contain_Reg_And_Customer_And_Validate(
			Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Create_Segments_OE_Display_Promo_Segments_Contain_Reg_And_Customer_And_Validate");
			log.debug("Entering Create_Segments_OE_Display_Promo_Segments_Contain_Reg_And_Customer_And_Validate");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}

			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNo = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
//			order.openOEStubTochangeLocationCoaxial("false");
			order.ChangeAccountSubType("All");
			landing.fg1Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrlWithLocationId2();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			landing = createChildTest(childTest1, extent, landing);
			order.newCustomerInformation("Residential", "Yes");
			order.addServicesAndFeacturesTab();
			String accnt = order.FindAccountNumber();
			landing.openUrlAddAccountWithLocationId2(accnt);

			ExtentTest childTest2 = extent.startTest("Add Internet Product");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.addServiceInternet("Freedom Internet 150");

			ExtentTest childTest3 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNo);
			order.addConvergedHardwareWithSingleElement("Freedom Gateway", convergedHardWareSerialNo);
			order.selectConvergedHardware("Internet");

			ExtentTest childTest4 = extent.startTest("Add Promotion and Validate");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServicesAndFeacturesTab();
			order.SwitchToAccountViewAndPromotions();

			ExtentTest childTest5 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest8 = extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.reviewOrder();
			order.clickFinishOrder();

			ExtentTest childTest9 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest9);
			orderHistory = createChildTest(childTest9, extent, orderHistory);
			String accntNum = order.getAccountNumber();
			orderHistory.navigateToOrderHistoryUrlLocationId2(accntNum, "Product Summary");

			ExtentTest childTest10 = extent.startTest("Navigate To Account Information");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
//			String accntNum = order.getAccountNumber();
			order.openAccountInformation("true", accntNum);

			log.debug("Create_Segments_OE_Display_Promo_Segments_Contain_Reg_And_Customer_And_Validate");
		} catch (Exception e) {
			log.error("Error in Create_Segments_OE_Display_Promo_Segments_Contain_Reg_And_Customer_And_Validate:"
					+ e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Validate_New_MC_When_MUT_Orders_Are_Processed(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Validate_New_MC_When_MUT_Orders_Are_Processed");
			log.debug("Entering Validate_New_MC_When_MUT_Orders_Are_Processed");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}

			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			// ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			// String convergedHardWareSerialNo=ConvgdSrlNo;
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			// int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			// int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));

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
			landing.openUrlWithLocationId2();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet("Fibre+ 75");

			ExtentTest childTest4 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNo);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware("Internet");

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

			// String accntNum = "64267382573";

			ExtentTest childTest8 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest12 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest12);
			orderHistory = createChildTest(childTest12, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrlLocationId2(accntNum, "Product Summary");
			orderHistory.navigateToOrderHistoryUrlLocationId2(accntNum, "Agreement Manager");

			ExtentTest childTest13 = extent.startTest("Navigate To OM Page for Get Id");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			// String accntNum=order.getAccountNumber();
			order.openCOMOrderPageToGetId("true", accntNum);

			ExtentTest childTest14 = extent.startTest("Upload The File");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			// String accntNum=order.getAccountNumber();
			order.NavigateToAccountUpdateSession();

			ExtentTest childTest15 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest15);
			orderHistory = createChildTest(childTest15, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrlLocationId2(accntNum, "Product Summary");
			orderHistory.navigateToOrderHistoryUrlLocationId2(accntNum, "Agreement Manager");

			ExtentTest childTest16 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest17 = extent.startTest("Validate Customer Portal Tab");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
//			String accntNum = order.getAccountNumber();
			order.openCustomerPortalInformation("true", accntNum);

			log.debug("Validate_New_MC_When_MUT_Orders_Are_Processed");
		} catch (Exception e) {
			log.error("Error in Validate_New_MC_When_MUT_Orders_Are_Processed:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	public static void main(String args[]) {
		InputStream log4j = OtherCases.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
		PropertyConfigurator.configure(log4j);
	}
}
