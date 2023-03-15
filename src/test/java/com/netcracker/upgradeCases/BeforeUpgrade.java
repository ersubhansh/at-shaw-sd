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
import com.netcracker.shaw.at_shaw_sd.util.ExcelOperation;
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.netcracker.shaw.setup.SeleniumTestUp;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 *         Aug 10, 2018
 */

public class BeforeUpgrade extends SeleniumTestUp {

	LandingPage landing = null;
	OrderCreationPage order = null;
	COMOrdersPage com = null;
	SOMOrderPage som = null;
	ValidateReport repo = null;
	ExcelOperation excel = null;
	SoapUIRestAPIpage soapRest = null;
	OrderHistoryPage orderHistory = null;
	SoftAssert softAssert = new SoftAssert();
	ArrayList<String> valuesToPassForValidation = new ArrayList<String>();

	Logger log = Logger.getLogger(BeforeUpgrade.class);

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Activation_Pending_Tripleplay_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Activation of pending triple play account after upgrade");
			log.debug("Entering Activation_Pending_Tripleplay_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
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
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");
		

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "phoneNumTC1", phoneNbr);
			

			ExtentTest childTest4 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			// order.selectConvergedHardware("Internet");
			//order.deleteHardware("Switch Window");
			//order.selectTodo();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC1",
					convergedHardWareSerialNbr);

			ExtentTest childTest5 = extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvBoxSlrNbrTC1", tvBoxSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvPortalSlrNbrTC1", tvPortalSerialNbr);

			ExtentTest childTest7 = extent.startTest("Tech Appointment");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.techAppointmentWithCurrentDate();
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest8 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectDefaultInstallationCheckBox();
			order.selectTodo();

			ExtentTest childTest9 = extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC1", accntNum);

			log.debug("Leaving Activation_Pending_Tripleplay_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Activation_Pending_Tripleplay_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Activate_2YVPInt_TvAgreement_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Activation of Internet on converged device and TV on Blue-sky Box + Portal, add 2 YVP Internet + TV agreement and some TV Promotions after Upgrade");
			log.debug("Entering Activate_2YVPInt_TvAgreement_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String phoneProduct = data.get("Phone_Product");
			String convergedHardware = data.get("Converged_hardware");
			TVBoxSrlNo = Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr = TVBoxSrlNo;
			TVPortalSrlNo = Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr = TVPortalSrlNo;
			String[] hardwareType = { "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber = { tvBoxSerialNbr, tvPortalSerialNbr };
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest4 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			// order.selectConvergedHardware("Internet");
			//order.deleteHardware("Switch Window");
			// order.selectTodo();
			//order.selectTodoListCheckBox();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC2",
					convergedHardWareSerialNbr);

			ExtentTest childTest6 = extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvBoxSlrNbrTC2", tvBoxSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvPortalSlrNbrTC2", tvPortalSerialNbr);

			ExtentTest childTest7 = extent.startTest("Add Promotions");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.addPromotionsServiceAgreement(phoneProduct, internetProduct, tvProduct, "");

			ExtentTest childTest8 = extent.startTest("Tech Appointment");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("Yes", "");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest9 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectDefaultInstallationCheckBox();
			order.selectTodo();

			ExtentTest childTest10 = extent.startTest("Review and Finish");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest11 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC2", accntNum);

			log.debug("Leaving Activate_2YVPInt_TvAgreement_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Activate_2YVPInt_TvAgreement_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Cancel_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Inflight cancel functionality for a pending install account");
			log.debug("Entering Inflight_Cancel_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			IntACSrlNo = Utility.generateHardwareSerailNum(IntACSrlNo);
			String internetHardwareSerialNbr = IntACSrlNo;
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

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
			//order.selectTodoListCheckBox();

			ExtentTest childTest5 = extent.startTest("Tech Appointment");
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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC3", accntNum);

			log.debug("Leaving Inflight_Cancel_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Inflight_Cancel_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Residential_To_Staff_XB6_BlueSky_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Changing account type from Residential to Staff for an account with XB6 and Blue Sky TV after Upgrade");
			log.debug("Entering Residential_To_Staff_XB6_BlueSky_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			TVBoxSrlNo = Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr = TVBoxSrlNo;
			TVPortalSrlNo = Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr = TVPortalSrlNo;
			String[] hardwareType = { "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber = { tvBoxSerialNbr, tvPortalSerialNbr };
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvBoxSlrNbrTC4", tvBoxSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvPortalSlrNbrTC4", tvPortalSerialNbr);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			//order.deleteHardware("Switch Window");
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			// order.selectTodo();
			//order.selectTodoListCheckBox();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC4",
					convergedHardWareSerialNbr);

			ExtentTest childTest6 = extent.startTest("Tech Appointment");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest7 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();
			//order.selectTodoListCheckBox();

			ExtentTest childTest8 = extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC4", accntNum);

			ExtentTest childTest10 = extent.startTest("Navigate To COM & SOM Initial Record Counts");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest11 = extent.startTest("CLEC Eequest Before Job Run");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest12 = extent.startTest("Navigate To E911 Response Job");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			order = createChildTest(childTest12, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest13 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest13);
			com = createChildTest(childTest13, extent, com);
			com.verifyCLECRequestAfterJobRun();

			log.debug("Leaving Residential_To_Staff_XB6_BlueSky_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Residential_To_Staff_XB6_BlueSky_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Staff_To_Residential_XB6_BlueSky_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Changing account type from Staff to Residential for an account with XB6 and Blue Sky TV");
			log.debug("Entering Staff_To_Residential_XB6_BlueSky_BeforeUG");
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
			order.customerInformation("Staff", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

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
			// order.selectConvergedHardware("Internet");
//			order.deleteHardware("Switch Window");
			// order.selectTodo();
			//order.selectTodoListCheckBox();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC5",
					convergedHardWareSerialNbr);

			ExtentTest childTest4 = extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			order.selectTodoListCheckBox();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvBoxSlrNbrTC5", tvBoxSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvPortalSlrNbrTC5", tvPortalSerialNbr);

			ExtentTest childTest6 = extent.startTest("Tech Appointment");
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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC5", accntNum);

			ExtentTest childTest10 = extent.startTest("Navigate To COM & SOM Initial Record Counts");
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

			log.debug("Leaving Staff_To_Residential_XB6_BlueSky_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Staff_To_Residential_XB6_BlueSky_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void TN_change_Ported_To_Native_DPT_BeforUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "TN change from Ported to Native for an account with DPT");
			log.debug("Entering TN_change_Ported_To_Native_DPT_BeforUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNbr = PhSrlNo;
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));

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
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.portPhoneNumber("Phone Required", "", phoneProduct, "", "Yes", "No");
			order.selectVoiceMail();

			ExtentTest childTest4 = extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneSerialNbr, phoneHardware);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "PhoneSrlNbrTC6", phoneSerialNbr);

			ExtentTest childTest5 = extent.startTest("Add Internet");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6 = extent.startTest("Add Internet hardware");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "IntHitronSrlNbrTC6",
					internetHardwareSerialNbr);
			// order.deleteConvergedHardware();
			//order.selectTodoListCheckBox();

			ExtentTest childTest7 = extent.startTest("Tech Appointment");
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
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC6", accntNum);

			ExtentTest childTest11 = extent.startTest("Navigate To Check COM SOM Initial Counts");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			som = createChildTest(childTest11, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			log.debug("Leaving TN_change_Ported_To_Native_DPT_BeforUG");
		} catch (Exception e) {
			log.error("Error in TN_change_Ported_To_Native_DPT_BeforUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Cancel_Resbmit_order_XB6Conv_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Cancelling and Re-submitting an order on account with XB6 Converged");
			log.debug("Entering Cancel_Resbmit_order_XB6Conv_BeforeUG");
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
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			//order.deleteHardware("Switch Window");
			order.addConvergedHardware1(convergedHardwareSerialNbr);
			// order.addConvergedHardware("Converged150", convergedHardwareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			// order.selectTodo();
			//order.selectTodoListCheckBox();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC7",
					convergedHardwareSerialNbr);

			ExtentTest childTest5 = extent.startTest("Tech Appointment");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();

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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC7", accntNum);

			ExtentTest childTest9 = extent.startTest("Navigate To Check COM SOM Initial Counts");
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

			log.debug("Leaving Cancel_Resbmit_order_XB6Conv_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Cancel_Resbmit_order_XB6Conv_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void AUP_Resume_TriplePlay_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "AUP: resume after upgrade");
			log.debug("Entering AUP_Resume_TriplePlay_BeforeUG");
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

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Phone, Internet and TV");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);
			order.addServiceTV(tvProduct);

			ExtentTest childTest4 = extent.startTest("Add TV Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvSDSrlNbrTC8", tvHardwareSerialNbr);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			//order.deleteHardware("Switch Window");
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			// order.selectTodo();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC8",
					convergedHardWareSerialNbr);

			ExtentTest childTest6 = extent.startTest("Tech Appointment");
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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC8", accntNum);

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

			log.debug("Leaving AUP_Resume_TriplePlay_BeforeUG");
		} catch (Exception e) {
			log.error("Error in AUP_Resume_TriplePlay_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Tripleplay_DPTCisco_legacyTV_Swap_OrderEntry_BeforUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"For a Triple play account with DPT, Cisco Advanced Wi-Fi modem and legacy TV device , Moxi gateway, perform hardware swaps through Order Entry");
			log.debug("Entering Tripleplay_DPTCisco_legacyTV_Swap_OrderEntry_BeforUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			String tvProduct = data.get("TV_Product");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNo = PhSrlNo;
			IntACSrlNo = Utility.generateHardwareSerailNum(IntACSrlNo);
			String internetHardwareSerialNbr = IntACSrlNo;
			TVMoxiSrlNo = Utility.generateHardwareSerailNum(TVMoxiSrlNo);
			String tvHardwareSerialNbr1 = TVMoxiSrlNo;
			//TVDCXSrlNo = Utility.generateHardwareSerailNum(TVDCXSrlNo);
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr2 = TVDctSrlNo;
			//String[] hardwareType = { "ShawGatewayHDPVR", "DCX3200M1" };
			String[] hardwareType = { "DCT700", "ShawGatewayHDPVR" };
			String[] serialNumber = { tvHardwareSerialNbr2, tvHardwareSerialNbr1 };
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "phoneNumTC9", phoneNbr);
			order.selectVoiceMail();

			ExtentTest childTest4 = extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneSerialNo, phoneHardware);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "PhoneSrlNbrTC9", phoneSerialNo);

			ExtentTest childTest5 = extent.startTest("Add Internet Product And Internet Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "IntHitronSrlNbrTC9",
					internetHardwareSerialNbr);

			ExtentTest childTest6 = extent.startTest("Add TV With MOXI Gateway and legecy Hardwares");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvMOXISlrNbrTC9", tvHardwareSerialNbr1);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvDCX3200SlrNbrTC9",
					tvHardwareSerialNbr2);

			ExtentTest childTest7 = extent.startTest("Tech Appointment");
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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC9", accntNum);

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

			log.debug("Leaving Tripleplay_DPTCisco_legacyTV_Swap_OrderEntry_BeforUG");
		} catch (Exception e) {
			log.error("Error in Tripleplay_DPTCisco_legacyTV_Swap_OrderEntry_BeforUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void TechRetry_InternetFail_Phone_Success_SameSlNo_BeforUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Tech Retry with same Serial Number when Internet provisioning failed and Phone provisioning is successful on a converged device");
			log.debug("Entering TechRetry_InternetFail_Phone_Success_SameSlNo_BeforUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			String internetProduct = data.get("Internet_Product");
			String phoneProduct = data.get("Phone_Product");
			String convergedHardware = data.get("Converged_hardware");
			String techAppointment = data.get("Tech_Appointment");

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
			landing.openUrl();

			ExtentTest childTest3 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest4 = extent.startTest("Select Bill Type");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.selectBillType("");

			ExtentTest childTest5 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "phoneNumTC10", phoneNbr);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			//order.deleteHardware("Switch Window");
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			// order.selectTodo();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC10",
					convergedHardWareSerialNbr);

			ExtentTest childTest7 = extent.startTest("Tech Appointment");
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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC10", accntNum);

			log.debug("Leaving TechRetry_InternetFail_Phone_Success_SameSlNo_BeforUG");
		} catch (Exception e) {
			log.error("Error in TechRetry_InternetFail_Phone_Success_SameSlNo_BeforUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Modify_Existing_Pending_Order_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Inflight modify functionality for an existing account with pending order");
			log.debug("Entering Inflight_Modify_Existing_Pending_Order_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String techAppointment = data.get("Tech_Appointment");
			String convergedHardware = data.get("Converged_hardware");
			//TVDCXSrlNo = Utility.generateHardwareSerailNum(TVDCXSrlNo);
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr = TVDctSrlNo;
			//String[] hardwareType = { "DCX3200M" };
			String[] hardwareType = { "DCT700" };
			String[] serialNumber = { tvHardwareSerialNbr };
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
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

			ExtentTest childTest3 = extent.startTest("Add TV Product with TV hardware");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvDCX3200SlrNbrTC11",
					tvHardwareSerialNbr);

			ExtentTest childTest4 = extent.startTest("Add Internet Product");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			//order.deleteHardware("Switch Window");
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			// order.selectTodo();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC11",
					convergedHardWareSerialNbr);

			ExtentTest childTest6 = extent.startTest("Tech Appointment");
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

			ExtentTest childTest9 = extent.startTest("Navigate To COM and SOM Order");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC11", accntNum);

			log.debug("Leaving Inflight_Modify_Existing_Pending_Order_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Inflight_Modify_Existing_Pending_Order_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Hitron_XB6_PLfrom_Native_Native_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Changing Hitron modem to XB6 converged after upgrade and PL from Native to Native");
			log.debug("Entering Hitron_XB6_PLfrom_Native_Native_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNo = PhSrlNo;
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "PhoneNbrTC12", phoneNbr);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneSerialNo, phoneHardware);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "PhoneSrlNbrTC12", phoneSerialNo);

			ExtentTest childTest5 = extent.startTest("Add Internet Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "IntHitronSrlNbrTC12",
					internetHardwareSerialNbr);
			// order.deleteConvergedHardware();

			ExtentTest childTest6 = extent.startTest("Tech Appointment");
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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC12", accntNum);

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

			ExtentTest childTest13 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest13);
			com = createChildTest(childTest13, extent, com);
			som = createChildTest(childTest13, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			log.debug("Leaving Hitron_XB6_PLfrom_Native_Native_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Hitron_XB6_PLfrom_Native_Native_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Adding_Channels_Promos_Pending_Order_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Adding channels and promotions to a pending order");
			log.debug("Entering Adding_Channels_Promos_Pending_Order_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String tvProduct = data.get("TV_Product");
			String convergedHardware = data.get("Converged_hardware");
			TVBoxSrlNo = Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr = TVBoxSrlNo;
			TVPortalSrlNo = Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr = TVPortalSrlNo;
			String[] hardwareType = { "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber = { tvBoxSerialNbr, tvPortalSerialNbr };
			String internetProduct = data.get("Internet_Product");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardwareSerialNbr = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardwareSerialNbr);
			order.addConvergedHardware1(convergedHardwareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch Window");
			// order.selectTodo();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC13",
					convergedHardwareSerialNbr);

			ExtentTest childTest5 = extent.startTest("Add TV Product");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);

			ExtentTest childTest6 = extent.startTest("Add TV with BlueSky Hardwares");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvBoxSlrNbrTC13", tvBoxSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvPortalSlrNbrTC13", tvPortalSerialNbr);

			ExtentTest childTest7 = extent.startTest("Tech Appointment");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("Yes", "");

			ExtentTest childTest8 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();
			order.selectTodo();

			ExtentTest childTest9 = extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC13", accntNum);

			ExtentTest childTest11 = extent.startTest("Navigate To Check COM, SOM Initial Counts");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			com = createChildTest(childTest11, extent, com);
			som = createChildTest(childTest11, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("");

			ExtentTest childTest12 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			//order.navigateToCustOrderPage("true", accntNum);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest13 = extent.startTest("Add Digital Channel,Theme and Pick10 pack2");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.addingPick10pack2DigitalChannels("3rd Wrench");
			order.addSouthAsian4Pack1();

			ExtentTest childTest14 = extent.startTest("Tech Appointment");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.forceAppointmentWithFutureDate("Manual Appoinment");

			ExtentTest childTest15 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();
			//order.selectTodo();

			ExtentTest childTest16 = extent.startTest("Review and Finish");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest17 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest18 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest19 = extent.startTest("Tech Appointment With Current Date");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.selectTodo();
			order.techAppointmentWithCurrentDate();
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();
			//order.selectTodo();

			ExtentTest childTest20 = extent.startTest("Review and Finish");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest21 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest22 = extent.startTest("Wait for provising Mark Finish Task");
			test.appendChild(childTest22);
			com = createChildTest(childTest22, extent, com);
			order = createChildTest(childTest22, extent, order);
			order.navigateToOrderValidation(67, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest23 = extent.startTest("Wait For Tech Confirm Mark Finish Task");
			test.appendChild(childTest23);
			com = createChildTest(childTest23, extent, com);
			order = createChildTest(childTest23, extent, order);
			order.navigateToOrderValidation(67, "No", "No", "COM");
//			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			log.debug("Leaving Adding_Channels_Promos_Pending_Order_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Adding_Channels_Promos_Pending_Order_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Adding_VoiceMail_Secondaryline_Existing_Account_BeforeUG(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Adding VoiceMail,DR and Secondary line to an existing account after upgrade");
			log.debug("Entering Adding_VoiceMail_Secondaryline_Existing_Account_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			String tvProduct = data.get("TV_Product");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNbr = PhSrlNo;
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr = TVDctSrlNo;
			String[] hardwareType = { "DCT700" };
			String[] serialNumber = { tvHardwareSerialNbr };
			String techAppointment = data.get("Tech_Appointment");
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Phone");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);

			ExtentTest childTest4 = extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneSerialNbr, phoneHardware);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "PhoneSrlNbrTC14", phoneSerialNbr);

			ExtentTest childTest5 = extent.startTest("Add TV with DCT Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvDCTSrlNbrTC14", tvHardwareSerialNbr);

			ExtentTest childTest6 = extent.startTest("Tech Appointment");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.selectingSalesRepresentativeCheckBox();
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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC14", accntNum);

			ExtentTest childTest9 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest9);
			com = createChildTest(childTest9, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest10 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			order = createChildTest(childTest10, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest11 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest12 = extent.startTest("Verify COM, SOM Initial Record Counts");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			som = createChildTest(childTest12, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			log.debug("Leaving Adding_VoiceMail_Secondaryline_Existing_Account_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Adding_VoiceMail_Secondaryline_Existing_Account_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Upgrade_Offering_Tripleplay_Account_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Upgrade offering for a triple play account after upgrade");
			log.debug("Entering Upgrade_Offering_Tripleplay_Account_BeforeUG");
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

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
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

			ExtentTest childTest4 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			// order.selectConvergedHardware("Internet");
			//order.deleteHardware("Switch Window");
			//order.selectTodo();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC15",
					convergedHardWareSerialNbr);

			ExtentTest childTest6 = extent.startTest("Add TV with BlueSky Hardwares");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			order.selectTodoListCheckBox();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvBoxSlrNbrTC15", tvBoxSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvPortalSlrNbrTC15", tvPortalSerialNbr);

			ExtentTest childTest7 = extent.startTest("Add Promotions");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.addPromotionsServiceAgreement(phoneProduct, internetProduct, tvProduct, "");

			ExtentTest childTest8 = extent.startTest("Tech Appointment");
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
			com = createChildTest(childTest11, extent, com);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC15", accntNum);

			ExtentTest childTest12 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest13 = extent.startTest("Navigate TO E911 Job Run");
			test.appendChild(childTest13);
			com = createChildTest(childTest13, extent, com);
			order = createChildTest(childTest13, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest14 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			com.verifyCLECRequestAfterJobRun();

			log.debug("Leaving Upgrade_Offering_Tripleplay_Account_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Upgrade_Offering_Tripleplay_Account_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Cancel_Pending_Account_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Inflight cancel functionality for an existing account with pending order");
			log.debug("Entering Inflight_Cancel_Pending_Account_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String internetHardware = data.get("Internet_hardware");
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			TVBoxSrlNo = Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr = TVBoxSrlNo;
			TVPortalSrlNo = Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr = TVPortalSrlNo;
			String[] hardwareType = { "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber = { tvBoxSerialNbr, tvPortalSerialNbr };
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
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

			ExtentTest childTest4 = extent.startTest("Add Internet Product");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6 = extent.startTest("Add Internet with Hitron hardware");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "IntHitronSrlNbrTC16",
					internetHardwareSerialNbr);

			ExtentTest childTest5 = extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvBoxSlrNbrTC16", tvBoxSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvPortalSlrNbrTC16", tvPortalSerialNbr);

			ExtentTest childTest7 = extent.startTest("Tech Appointment");
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
			// order.selectTodoListCheckBox();

			ExtentTest childTest9 = extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC16", accntNum);

			log.debug("Leaving Inflight_Cancel_Pending_Account_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Inflight_Cancel_Pending_Account_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Modify_New_Pending_Install_Account_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Inflight modify functionality for a new pending install account");
			log.debug("Entering Inflight_Modify_New_Pending_Install_Account_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String phoneHardware = data.get("Phone_hardware");
			String[] hardwareType = { "DCT700" };
			String techAppointment = data.get("Tech_Appointment");
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);

			ExtentTest childTest4 = extent.startTest("Add Phone and DPT without Srl Number");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPhoneHardwareWithoutSlNos(phoneHardware);

			ExtentTest childTest5 = extent.startTest("Add Internet and Hitron without Srl Number");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceInternet(internetProduct);
			order.addInternetHardwareWithoutSlNos("Hitron");

			ExtentTest childTest6 = extent.startTest("Add TV with Legecy Hardware without Srl Number");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithOutSrlNo(hardwareType);

			ExtentTest childTest7 = extent.startTest("Tech Appointment");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("Yes", "");

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
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC17", accntNum);

			ExtentTest childTest11 = extent.startTest("Check COM, SOM Initial Counts");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			som = createChildTest(childTest11, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest12 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			//order.navigateToCustOrderPage("true", accntNum);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest13 = extent.startTest("Add Distinctive Ring");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.navigateToCustOrderPage("false", accntNum);
			order.addDistinctiveRing("One", "3 short rings");

			ExtentTest childTest14 = extent.startTest("Add Internet Email and IP Address");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.addEmailsIPAddressSimCardForInternet("Two", "");
			order.addInternetEmail("", "Calgary");

			ExtentTest childTest15 = extent.startTest("Tech Appointment With Current Date");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.techAppointmentWithCurrentDate();

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
			order.scrollDown600();

			log.debug("Leaving Inflight_Cancel_Pending_Account_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Inflight_Cancel_Pending_Account_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Techretry_Different_SlNbr_TV_Phone_Internet_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Tech retry with different serial number for TV, Phone and Internet");
			log.debug("Entering Techretry_Different_SlNbr_TV_Phone_Internet_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			String tvProduct = data.get("TV_Product");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNbr = PhSrlNo;
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr = TVDctSrlNo;
			String[] hardwareType = { "DCT700" };
			String[] serialNumber = { tvHardwareSerialNbr };
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "phoneNumTC18", phoneNbr);

			ExtentTest childTest4 = extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneSerialNbr, phoneHardware);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "PhoneSrlNbrTC18", phoneSerialNbr);

			ExtentTest childTest5 = extent.startTest("Add Internet with Hitron hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "IntHitronSrlNbrTC18",
					internetHardwareSerialNbr);

			ExtentTest childTest6 = extent.startTest("Add TV and Legecy Hardware");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvDCTSrlNbrTC18", tvHardwareSerialNbr);

			ExtentTest childTest7 = extent.startTest("Tech Appointment");
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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC18", accntNum);

			log.debug("Leaving Techretry_Different_SlNbr_TV_Phone_Internet_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Techretry_Different_SlNbr_TV_Phone_Internet_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Reinstate_with_New_Resources_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Reinstate with with new resources");
			log.debug("Entering Reinstate_with_New_Resources_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
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
			String disconnectReason = data.get("Disconnect_Reasons");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
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
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			// order.selectConvergedHardware("Internet");
			//order.deleteHardware("Switch Window");
			// order.selectTodo();

			ExtentTest childTest6 = extent.startTest("Tech Appointment");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest7 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();
			//order.selectTodoListCheckBox();

			ExtentTest childTest8 = extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			String accntNum = order.getAccountNumber();
			//order.openCOMOrderPage("true", accntNum, "Yes");
			order.openCOMOrderPage("false", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC19", accntNum);

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

			ExtentTest childTest13 = extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			//order.navigateToCustOrderPage("true", accntNum);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest14 = extent.startTest("Disconnect Triple play");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.tempDisconnectLOBs();
			order.deleteConvergedHardware();

			ExtentTest childTest15 = extent.startTest("Select Number to Portout");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.selectPortOut();

			ExtentTest childTest16 = extent.startTest("Select Bill Type");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.selectBillMailForDisconnect();

			ExtentTest childTest17 = extent.startTest("Tech Appointment For Disconnect");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.selectCalenderCurrentDate();

			ExtentTest childTest18 = extent.startTest("Select Disconnect Reason");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.selectDisconnectControllabeReasons(disconnectReason);
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest19 = extent.startTest("Review and Finish");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.reviewAndFinishOrder();
			//order.switchPreviousTab();

			ExtentTest childTest20 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest20);
			som = createChildTest(childTest20, extent, som);
			com = createChildTest(childTest20, extent, com);
			order = createChildTest(childTest20, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest21 = extent.startTest("Mark finish Wait for effective date for disconnect cust order");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			com = createChildTest(childTest21, extent, com);
			order.navigateToOrderValidation(35, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest22);
			com = createChildTest(childTest22, extent, com);
			order = createChildTest(childTest22, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			log.debug("Leaving Reinstate_with_New_Resources_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Reinstate_with_New_Resources_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Reinstate_with_Past_Resources_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Reinstate with past resources");
			log.debug("Entering Reinstate_with_Past_Resources_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
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
			String disconnectReason = data.get("Disconnect_Reasons");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			// order.selectConvergedHardware("Internet");
			//order.deleteHardware("Switch Window");
			// order.selectTodo();

			ExtentTest childTest6 = extent.startTest("Tech Appointment");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest7 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();
			//order.selectTodoListCheckBox();

			ExtentTest childTest8 = extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC20", accntNum);

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

			ExtentTest childTest13 = extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			//order.navigateToCustOrderPage("true", accntNum);
			order.navigateToCustOrderPage("false", accntNum);
			
			ExtentTest childTest14 = extent.startTest("Disconnect Triple play");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.tempDisconnectLOBs();
			order.deleteConvergedHardware();

			ExtentTest childTest15 = extent.startTest("Select Number to Portout");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.selectPortOut();

			ExtentTest childTest16 = extent.startTest("Select Bill Type");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.selectBillMailForDisconnect();

			ExtentTest childTest17 = extent.startTest("Tech Appointment For Disconnect");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.selectCalenderCurrentDate();

			ExtentTest childTest18 = extent.startTest("Select Disconnect Reason");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.selectDisconnectControllabeReasons(disconnectReason);
			order.selectingSalesRepresentativeCheckBox();
			// order.selectDefaultInstallationCheckBox();

			ExtentTest childTest19 = extent.startTest("Review and Finish");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.reviewAndFinishOrder();
			//order.switchPreviousTab();

			ExtentTest childTest20 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest21 = extent.startTest("Mark finish Wait for effective date for disconnect cust order");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			com = createChildTest(childTest21, extent, com);
			order.navigateToOrderValidation(35, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest22 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest22);
			com = createChildTest(childTest22, extent, com);
			order = createChildTest(childTest22, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			log.debug("Leaving Reinstate_with_Past_Resources_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Reinstate_with_Past_Resources_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Disconnect_Staff_Internet_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Disconnect: Staff Internet");
			log.debug("Entering Reinstate_with_Past_Resources_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String internetHardware = data.get("Internet_hardware");
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr = TVDctSrlNo;
			String[] hardwareType = { "DCT700" };
			String[] serialNumber = { tvHardwareSerialNbr };
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Staff", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Internet and Hitron Hardware");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);
			order.addInternetHardwareForStaff(internetHardware, internetHardwareSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "IntHitronSrlNbrTC21",
					internetHardwareSerialNbr);

			ExtentTest childTest4 = extent.startTest("Add TV and TV Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvSDSrlNbrTC21", tvHardwareSerialNbr);

			ExtentTest childTest7 = extent.startTest("Tech Appointment");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest6 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC21", accntNum);

			log.debug("Leaving Disconnect_Staff_Internet_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Disconnect_Staff_Internet_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void IFC_Full_Disconnect_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "IFC: Full Disconnect");
			log.debug("Entering IFC_Full_Disconnect_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			String tvProduct = data.get("TV_Product");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardwareSerialNbr = PhSrlNo;
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			//TVDCXSrlNo = Utility.generateHardwareSerailNum(TVDCXSrlNo);
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr = TVDctSrlNo;
			//String[] hardwareType = { "DCX3200M" };
			String[] hardwareType = { "DCT700" };
			String[] serialNumber = { tvHardwareSerialNbr };
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "phoneNumTC22", phoneNbr);
			order.selectVoiceMail();

			ExtentTest childTest4 = extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneHardwareSerialNbr, phoneHardware);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "PhoneSrlNbrTC22", phoneHardwareSerialNbr);

			ExtentTest childTest5 = extent.startTest("Add Internet Product");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6 = extent.startTest("Add Internet Hardware");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "IntHitronSrlNbrTC22",
					internetHardwareSerialNbr);
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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvDCXSrlNbrTC22", tvHardwareSerialNbr);

			ExtentTest childTest9 = extent.startTest("Tech Appointment");
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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC22", accntNum);

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

			log.debug("Leaving IFC_Full_Disconnect_BeforeUG");
		} catch (Exception e) {
			log.error("Error in IFC_Full_Disconnect_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Activate_PhDPT_PortedTN_LifelineAlarm_No_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Activation of Phone on DPT with Ported TN (Lifeline Alarm=NO) after upgrade");
			log.debug("Entering Activate_PhDPT_PortedTN_LifelineAlarm_No_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			String internetProduct = data.get("Internet_Product");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNbr = PhSrlNo;
			IntACSrlNo = Utility.generateHardwareSerailNum(IntACSrlNo);
			String internetHardwareSerialNbr = IntACSrlNo;
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Paused");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobpaused();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Phone");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.portPhoneNumber("Phone Required", "", phoneProduct, "", "No", "No");

			ExtentTest childTest4 = extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPhoneHardwareWithoutSlNos(phoneHardware);

			ExtentTest childTest5 = extent.startTest("Add Internet");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6 = extent.startTest("Add Internet hardware");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addInternetHardwareWithoutSlNos("Cisco");
			// order.deleteConvergedHardware();

			ExtentTest childTest7 = extent.startTest("Tech Appointment");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("Yes", "");

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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC23", accntNum);

			ExtentTest childTest11 = extent.startTest("Wait for Effective Date Mark Finish Task");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			order = createChildTest(childTest11, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			// com.setMarkFinishedTask();
			com.setMarkFinishedSpecificTask("Entering setMarkFinishedTask");
			order.navigateToOrderPage();

			ExtentTest childTest12 = extent.startTest("Send serial number For Phone Hardware CFS Order");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			som = createChildTest(childTest12, extent, som);
			order = createChildTest(childTest12, extent, order);
			order.navigateToOrderValidation(5, "No", "No", "COM");
			com.sendSerialNbrTask(phoneSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest13 = extent.startTest("Send serial number for Internet Hardware CFS Order");
			test.appendChild(childTest13);
			com = createChildTest(childTest13, extent, com);
			order = createChildTest(childTest13, extent, order);
			order.navigateToOrderValidation(8, "No", "No", "COM");
			com.sendSerialNbrTask(internetHardwareSerialNbr);
			order.navigateToOrderPage();

			log.debug("Leaving Activate_PhDPT_PortedTN_LifelineAlarm_No_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Activate_PhDPT_PortedTN_LifelineAlarm_No_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Immediate_Scenarios_Add_Email_ExistingCust_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"From the immediate scenarios tab add an email for an existing customer with Internet");
			log.debug("Entering Immediate_Scenarios_Add_Email_ExistingCust_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Paused");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobpaused();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest5 = extent.startTest("Add Internet Product");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch Window");
			//order.selectTodo();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC24",
					convergedHardWareSerialNbr);

			ExtentTest childTest7 = extent.startTest("Tech Appointment");
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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC24", accntNum);

			log.debug("Leaving Immediate_Scenarios_Add_Email_ExistingCust_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Immediate_Scenarios_Add_Email_ExistingCust_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Immediate_Scenarios_Hardware_Swap_PendingCust_BeforeUG(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"From the immediate scenarios tab perform a Hardware Swap for a new customer with pending order");
			log.debug("Entering Immediate_Scenarios_Hardware_Swap_PendingCust_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNo = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNo);
			order.selectConvergedHardware("Internet");
			//order.deleteHardware("Switch Window");
			//order.selectTodo();

			ExtentTest childTest5 = extent.startTest("Tech Appointment");
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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC25", accntNum);

			log.debug("Leaving Immediate_Scenarios_Hardware_Swap_PendingCust_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Immediate_Scenarios_Hardware_Swap_PendingCust_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Immediate_Tab_Modify_PhFeacture_ConvHw_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"From the immediate scenarios tab modify phone Features for an existing customer with Converged hardware");
			log.debug("Entering Immediate_Tab_Modify_PhFeacture_ConvHw_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest4 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "phoneNumTC26", phoneNbr);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch Window");
			//order.selectTodo();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC26",
					convergedHardWareSerialNbr);

			ExtentTest childTest6 = extent.startTest("Tech Appointment");
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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC26", accntNum);

			ExtentTest childTest10 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11 = extent.startTest("Navigate To E911 JOB Run");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			order = createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			com.verifyCLECRequestAfterJobRun();

			log.debug("Leaving Immediate_Tab_Modify_PhFeacture_ConvHw_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Immediate_Tab_Modify_PhFeacture_ConvHw_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Immediate_Tab_Change_BillDay_Existing_Customer_BeforeUG(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"From the immediate scenarios tab change the Bill day for an existing customer");
			log.debug("Entering Immediate_Tab_Change_BillDay_Existing_Customer_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest4 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "phoneNumTC27", phoneNbr);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
		//	order.deleteHardware("Switch Window");
			//order.selectTodo();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC27",
					convergedHardWareSerialNbr);

			ExtentTest childTest6 = extent.startTest("Tech Appointment");
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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC27", accntNum);

			ExtentTest childTest10 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11 = extent.startTest("Navigate To E911 JOB Run");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			order = createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			com.verifyCLECRequestAfterJobRun();
			log.debug("Leaving Immediate_Tab_Change_BillDay_Existing_Customer_BeforeUG");

		} catch (Exception e) {
			log.error("Error in Immediate_Tab_Change_BillDay_Existing_Customer_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Retail_Self_Install_Order_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Retail self install order after upgrade path");
			log.debug("Entering Retail_Self_Install_Order_BeforeUG");
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
			//String tvHardwareSerialNbr = TVDctSrlNo;
			String tvHardwareSerialNbr = TVDCXSrlNo;
			String[] hardwareType = { "DCX3200M" };
			//String[] hardwareType = { "DCT700" };
			String[] serialNumber = { tvHardwareSerialNbr };
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Internet hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "IntHitronSrlNbrTC28",
					internetHardwareSerialNbr);
			// order.deleteConvergedHardware();

			ExtentTest childTest5 = extent.startTest("Add TV with TV hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest6 = extent.startTest("Tech Appointment");
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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC28", accntNum);

			log.debug("Leaving Retail_Self_Install_Order_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Retail_Self_Install_Order_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Modify_Change_OfferingTo_RetailPickUp_BeforeUG(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"In-flight modifications to change the Offering details after upgrade to Retail pick up order");
			log.debug("Entering Inflight_Modify_Change_OfferingTo_RetailPickUp_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String[] hardwareType = { "DCT700" };
			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6 = extent.startTest("Add Internet Hardware without Serial Num's");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addInternetHardwareWithoutSlNos("Hitron");
			// order.deleteConvergedHardware();

			ExtentTest childTest7 = extent.startTest("Add TV Product");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.addServiceTV(tvProduct);

			ExtentTest childTest8 = extent.startTest("Add TV Hardware without Serial Num's");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.addTVwithLegecyWithoutHardware(tvProduct);
			order.addTVHardwareWithOutSrlNo(hardwareType);

			ExtentTest childTest9 = extent.startTest("Tech Appointment With Retail PickUp");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.selectInstallationOption("Self Connect Yes", "");
			order.techAppointmentNo("Retail Pickup", "", "");

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
			order.openCOMOrderPage("true", accntNum, "");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC29", accntNum);

			log.debug("Leaving Inflight_Modify_Change_OfferingTo_RetailPickUp_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Inflight_Modify_Change_OfferingTo_RetailPickUp_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Reset_Email_PWD_Customer_Modify_Order_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Reset Email password for a customer by performing modify order");
			log.debug("Entering Reset_Email_PWD_Customer_Modify_Order_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			IntACSrlNo = Utility.generateHardwareSerailNum(IntACSrlNo);
			String internetHardwareSerialNbr = IntACSrlNo;
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Internet Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "IntCiscoSrlNbrTC30",
					internetHardwareSerialNbr);
			// order.deleteConvergedHardware();

			ExtentTest childTest5 = extent.startTest("Add Internet Email and IP Address");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addEmailsIPAddressSimCardForInternet("Two", "");
			order.addInternetEmail("", "Calgary");
			order.addInternetEmail("", "Edmonton");

			ExtentTest childTest6 = extent.startTest("Tech Appointment");
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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC30", accntNum);

			log.debug("Leaving Reset_Email_PWD_Customer_Modify_Order_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Reset_Email_PWD_Customer_Modify_Order_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Removing_Eexisting_Email_Add_NewEmail_TriplePlay_BeforeUG(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Removing existing email and adding new email after upgrade for a triple play customer");
			log.debug("Entering Removing_Eexisting_Email_Add_NewEmail_TriplePlay_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Internet Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "IntHitronSrlNbrTC31",
					internetHardwareSerialNbr);
			// order.deleteConvergedHardware();

			ExtentTest childTest5 = extent.startTest("Add Internet Email and IP Address");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addEmailsIPAddressSimCardForInternet("One", "");
			order.addInternetEmail("", "Calgary");
			order.addInternetEmail("", "Edmonton");

			ExtentTest childTest6 = extent.startTest("Tech Appointment");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

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

			ExtentTest childTest9 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC31", accntNum);

			log.debug("Leaving Removing_Eexisting_Email_Add_NewEmail_TriplePlay_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Removing_Eexisting_Email_Add_NewEmail_TriplePlay_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void AUP_Suspend_existing_customer_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "AUP Suspend an existing customer after upgrade");
			log.debug("Entering AUP_Suspend_existing_customer_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
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

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest4 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "phoneNumTC32", phoneNbr);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch Window");
			//order.selectTodo();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC32",
					convergedHardWareSerialNbr);

			ExtentTest childTest6 = extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvBoxSlrNbrTC32", tvBoxSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvPortalSlrNbrTC32", tvPortalSerialNbr);

			ExtentTest childTest7 = extent.startTest("Tech Appointment");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest8 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();
			//order.mocaFilterSelecttion("");
			// order.selectTodoListCheckBox();

			ExtentTest childTest9 = extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC32", accntNum);

			ExtentTest childTest11 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest12 = extent.startTest("Navigate To E911 JOB Run");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			order = createChildTest(childTest12, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest13 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest13);
			com = createChildTest(childTest13, extent, com);
			com.verifyCLECRequestAfterJobRun();

			log.debug("Leaving AUP_Suspend_existing_customer_BeforeUG");
		} catch (Exception e) {
			log.error("Error in AUP_Suspend_existing_customer_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void TCIS_Resume_TriplePlay_Account_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "TCIS: resume after upgrade for a triple play account");
			log.debug("Entering TCIS_Resume_TriplePlay_Account_BeforeUG");
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

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Phone, Internet and TV");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "phoneNumTC33", phoneNbr);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);
			order.addServiceTV(tvProduct);

			ExtentTest childTest4 = extent.startTest("Add TV Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvBoxSlrNbrTC33", tvBoxSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvPortalSlrNbrTC33", tvPortalSerialNbr);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch Window");
			order.selectTodoListCheckBox();
			//order.selectTodo();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC33",
					convergedHardWareSerialNbr);

			ExtentTest childTest6 = extent.startTest("Tech Appointment");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

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

			ExtentTest childTest9 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC33", accntNum);

			ExtentTest childTest10 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11 = extent.startTest("Navigate To E11 JOB Run");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			order = createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			com.verifyCLECRequestAfterJobRun();

			log.debug("Leaving TCIS_Resume_TriplePlay_Account_BeforeUG");
		} catch (Exception e) {
			log.error("Error in TCIS_Resume_TriplePlay_Account_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Modification_TCIS_Suspend_TriplePlay_Cancel_Order_BeforeUG(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Modification of a TCIS suspension order for a Triple-Play Account with DPT, Hitron and TV Legacy device and Cancelling the order");
			log.debug("Entering Modification_TCIS_Suspend_TriplePlay_Cancel_Order_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			String tvProduct = data.get("TV_Product");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardWareSerialNbr = PhSrlNo;
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr = TVDctSrlNo;
			String[] hardwareType = { "DCT700" };
			String[] serialNumber = { tvHardwareSerialNbr };
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Phone and Phone Hardware");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addPhoneHardware(phoneHardWareSerialNbr, phoneHardware);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "PhoneSrlNbrTC34", phoneHardWareSerialNbr);

			ExtentTest childTest4 = extent.startTest("Add Internet and Internet Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "IntHitronSrlNbrTC34",
					internetHardwareSerialNbr);

			ExtentTest childTest5 = extent.startTest("Add TV and TV Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvSDSrlNbrTC34", tvHardwareSerialNbr);

			ExtentTest childTest6 = extent.startTest("Tech Appointment");
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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC34", accntNum);

			ExtentTest childTest10 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11 = extent.startTest("Navigate to E911 JOB Run");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			order = createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest13 = extent.startTest("Navigate To Customer Order Page");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest14 = extent.startTest("Click On Operations");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.navigateToOperations("Future Date");

			ExtentTest childTest15 = extent.startTest("Temporary Suspend TV");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.tempSuspendLOB("TV");
			order.enterInstallationFee("0.00");

			ExtentTest childTest16 = extent.startTest("Navigate To Tech Appoinment Tab");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.NavigateTechAppointmentTab();
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

			ExtentTest childTest19 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			log.debug("Leaving Modification_TCIS_Suspend_TriplePlay_Cancel_Order_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Modification_TCIS_Suspend_TriplePlay_Cancel_Order_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void TechRetry_Same_SN_PremiseMove_Gap_BlueSKY_XB6_BeforeUG(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Tech retry with same SN during premise move with Gap on an account with Blue SKY and XB6");
			log.debug("Entering TechRetry_Same_SN_PremiseMove_Gap_BlueSKY_XB6_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
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
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "phoneNumTC35", phoneNbr);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch Window");
			//order.selectTodo();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC35",
					convergedHardWareSerialNbr);

			ExtentTest childTest5 = extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			order.selectTodoListCheckBox();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvBoxSlrNbrTC35", tvBoxSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvPortalSlrNbrTC35", tvPortalSerialNbr);

			ExtentTest childTest7 = extent.startTest("Tech Appointment");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest6 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
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
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC35", accntNum);

			ExtentTest childTest10 = extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11 = extent.startTest("Navigate To E911 JOB Run");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			order = createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			com.verifyCLECRequestAfterJobRun();

			log.debug("Leaving TechRetry_Same_SN_PremiseMove_Gap_BlueSKY_XB6_BeforeUG");
		} catch (Exception e) {
			log.error("Error in TechRetry_Same_SN_PremiseMove_Gap_BlueSKY_XB6_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Intra_Province_Gap_Change_LOB_During_Transfer_BeforeUG(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Intra Province transfer with gap and change in LOB during transfer");
			log.debug("Entering Intra_Province_Gap_Change_LOB_During_Transfer_BeforeUG");
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

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvBoxSlrNbrTC36", tvBoxSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvPortalSlrNbrTC36", tvPortalSerialNbr);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch Window");
			order.selectTodoListCheckBox();
			//order.selectTodo();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC36",
					convergedHardWareSerialNbr);

			ExtentTest childTest6 = extent.startTest("Tech Appointment");
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

			ExtentTest childTest9 = extent.startTest("Navigate To COM, SOM Orders");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC36", accntNum);

			log.debug("Leaving Intra_Province_Gap_Change_LOB_During_Transfer_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Intra_Province_Gap_Change_LOB_During_Transfer_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Removing_BlueSkyTV_XB6_Future_Dated_InterProvince_BeforeUG(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Inflight changes by removing Blue SKY TV device from an account with Blue SKY TV and XB6 during Future dated Inter Province transfer");
			log.debug("Entering Inflight_Removing_BlueSkyTV_XB6_Future_Dated_InterProvince_BeforeUG");
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

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvBoxSlrNbrTC37", tvBoxSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvPortalSlrNbrTC37", tvPortalSerialNbr);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch Window");
			order.selectTodoListCheckBox();
			//order.selectTodo();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC37",
					convergedHardWareSerialNbr);

			ExtentTest childTest6 = extent.startTest("Tech Appointment");
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

			ExtentTest childTest9 = extent.startTest("Navigate To COM, SOM Orders");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC37", accntNum);

			log.debug("Leaving Inflight_Removing_BlueSkyTV_XB6_Future_Dated_InterProvince_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Inflight_Removing_BlueSkyTV_XB6_Future_Dated_InterProvince_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Inflight_Removing_PhoneDevice_DPT_Hitron_TV_PremiseMove_CGY_CGY_BeforeUG(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Inflight changes by removing Phone from an account with DPT + Hitron + Legacy TV during Premise move from CGY to CGY without gap");
			log.debug("Entering Inflight_Removing_PhoneDevice_DPT_Hitron_TV_PremiseMove_CGY_CGY_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String phoneHardware = data.get("Phone_hardware");
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			String tvProduct = data.get("TV_Product");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNbr = PhSrlNo;
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr = IntHitronSrlNo;
			TVHDSrlNo = Utility.generateHardwareSerailNum(TVHDSrlNo);
			String tvHardwareSerialNbr = TVHDSrlNo;
			String[] hardwareType = { "DCX3510HDGuide" };
			String[] serialNumber = { tvHardwareSerialNbr };
			String techAppointment = data.get("Tech_Appointment");
			String disconnectReason = data.get("Disconnect_Reasons");

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
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "phoneNumTC38", phoneNbr);

			ExtentTest childTest4 = extent.startTest("Add Phone DPT hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneSerialNbr, phoneHardware);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "PhoneSrlNbrTC38", phoneSerialNbr);

			ExtentTest childTest5 = extent.startTest("Add Internet Product");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6 = extent.startTest("Add Internet Hitron hardware");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "IntHitronSrlNbrTC38",
					internetHardwareSerialNbr);
			// order.deleteConvergedHardware();

			ExtentTest childTest7 = extent.startTest("Add TV Product");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.addServiceTV(tvProduct);

			ExtentTest childTest8 = extent.startTest("Add TV with DCX3510 Hardware");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvDCX3510SrlNbrTC38",
					tvHardwareSerialNbr);

			ExtentTest childTest9 = extent.startTest("Tech Appointment");
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

			ExtentTest childTest12 = extent.startTest("Navigate To COM order Page");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			String accntNum = order.getAccountNumber();
			// order.openCOMOrderPage("true", accntNum, "Yes");
			order.openCOMOrderPage("false", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC38", accntNum);

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
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest15 = extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			com.verifyCLECRequestAfterJobRun();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest17 = extent.startTest("Navigate to CGY Another Location");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.navigateToExistingAccountForPremise(accntNum);

			ExtentTest childTest18 = extent.startTest("Remove Phone hardware");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.removePersonalPhoneHardware();

			ExtentTest childTest19 = extent.startTest("Disconnect Phone LOB");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.disconnectLOBProducts("Phone");

			ExtentTest childTest20 = extent.startTest("Tech Appointment");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.selectPortOut();
			order.selectInstallationOption("Self Connect No", "Yes");
			order.techAppoinmentYes("Yes", "");

			ExtentTest childTest21 = extent.startTest("Select Disconnect Reason");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.selectDisconnectControllabeReasons(disconnectReason);

			ExtentTest childTest22 = extent.startTest("Select Method of confirmation & Default ToDo Msg");
			test.appendChild(childTest22);
			order = createChildTest(childTest22, extent, order);
			order.navigateToAgreementDetailsTab();
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest23 = extent.startTest("Review and Finish");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.reviewAndFinishOrder();
			
			ExtentTest childTest16 = extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest24 = extent.startTest("Navigate To COM Order");
			test.appendChild(childTest24);
			order = createChildTest(childTest24, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			log.debug("Leaving Inflight_Removing_PhoneDevice_DPT_Hitron_TV_PremiseMove_CGY_CGY_BeforeUG");

		} catch (Exception e) {
			log.error("Error in Inflight_Removing_PhoneDevice_DPT_Hitron_TV_PremiseMove_CGY_CGY_BeforeUG:"
					+ e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Disconnect_Premisemove_TriplePlay_ServicesNotAvailable_targetLoc_BeforeUG(
			Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Disconnect during premise move on an account with triple play when the services are not available in the target location");
			log.debug("Entering TechRetry_Same_SN_PremiseMove_Gap_BlueSKY_XB6_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
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
			String disconnectReason = data.get("Disconnect_Reasons");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "phoneNumTC39", phoneNbr);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch Window");
			//order.selectTodo();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC39",
					convergedHardWareSerialNbr);

			ExtentTest childTest5 = extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvBoxSlrNbrTC39", tvBoxSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvPortalSlrNbrTC39", tvPortalSerialNbr);

			ExtentTest childTest7 = extent.startTest("Tech Appointment");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest6 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest8 = extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC39", accntNum);

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
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest14 = extent.startTest("Navigate To Stub, To Change Service Availability NO-TV mode");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.openOEStubTochangeNoTV();

			ExtentTest childTest15 = extent.startTest("Navigate to CGY Another Location 2777");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.navigateToVancoverLocationId(accntNum);

			ExtentTest childTest18 = extent.startTest("Disconnect TV LOB");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.disconnectLOBProducts("TV");

			ExtentTest childTest19 = extent.startTest("Assign a phone number to each line");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.addServicePhone("", phoneProduct);

			ExtentTest childTest20 = extent.startTest("Navigate to Tech Appointment");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.selectInstallationOption("Self Connect No", "Yes");
			order.techAppoinmentYes("Yes", "");
			order.techAppointmentWithGAP();

			ExtentTest childTest21 = extent.startTest("Select Disconnect Reason");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.selectDisconnectControllabeReasons(disconnectReason);

			ExtentTest childTest22 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest22);
			order = createChildTest(childTest22, extent, order);
			order.navigateToAgreementDetailsTab();
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest23 = extent.startTest("Select Portout and Default Installation Chack Box");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.selectDefaultInstallationCheckBox();
			order.selectPortOut();

			ExtentTest childTest24 = extent.startTest("Review and Finish");
			test.appendChild(childTest24);
			order = createChildTest(childTest24, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest25 = extent.startTest("Navigate To COM Order");
			test.appendChild(childTest25);
			order = createChildTest(childTest25, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			log.debug("Leaving Disconnect_Premisemove_TriplePlay_ServicesNotAvailable_targetLoc_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Disconnect_Premisemove_TriplePlay_ServicesNotAvailable_targetLoc_BeforeUG:"
					+ e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Collection_Suspend_Existing_Customer_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Collection Suspend an existing customer after upgrade");
			log.debug("Entering Collection_Suspend_Existing_Customer_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			TVBoxSrlNo = Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr = TVBoxSrlNo;
			TVPortalSrlNo = Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr = TVPortalSrlNo;
			String[] hardwareType = { "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber = { tvBoxSerialNbr, tvPortalSerialNbr };
			String convergedHardware = data.get("Converged_hardware");
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "phoneNumTC40", phoneNbr);

			ExtentTest childTest4 = extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvBoxSlrNbrTC40", tvBoxSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvPortalSlrNbrTC40", tvPortalSerialNbr);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch Window");
			order.selectTodoListCheckBox();
			//order.selectTodo();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC40",
					convergedHardWareSerialNbr);

			ExtentTest childTest6 = extent.startTest("Tech Appointment");
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

			ExtentTest childTest9 = extent.startTest("Navigate To COM & SOM Order");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC40", accntNum);

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

			log.debug("Leaving Collection_Suspend_Existing_Customer_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Collection_Suspend_Existing_Customer_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Before Upgrade Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Collection_Resume_VOD_PPV_BeforeUG(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Collection: resume after upgrade for VOD and PPV");
			log.debug("Entering Collection_Resume_VOD_PPV_BeforeUG");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			TVBoxSrlNo = Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr = TVBoxSrlNo;
			TVPortalSrlNo = Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr = TVPortalSrlNo;
			String[] hardwareType = { "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber = { tvBoxSerialNbr, tvPortalSerialNbr };
			String convergedHardware = data.get("Converged_hardware");
			String techAppointment = data.get("Tech_Appointment");

			ExtentTest childTest = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing = createChildTest(childTest, extent, landing);
			order = createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2 = extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvBoxSlrNbrTC41", tvBoxSerialNbr);
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "tvPortalSlrNbrTC41", tvPortalSerialNbr);

			ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch Window");
			order.selectTodoListCheckBox();
			//order.selectTodo();
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "convergedSrlNbrTC41",
					convergedHardWareSerialNbr);

			ExtentTest childTest6 = extent.startTest("Tech Appointment");
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

			ExtentTest childTest9 = extent.startTest("Navigate To COM & SOM Order");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/upgradeCases.properties", "acctNumTC41", accntNum);

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

			ExtentTest childTest13 = extent.startTest("Trigger the Collection Suspend via RestAPI");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			soapRest = createChildTest(childTest13, extent, soapRest);
			soapRest.navigateToHitCollectionSuspension("TC41", accntNum);

			ExtentTest childTest14 = extent.startTest("Navigate To COM & SOM Order");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			som = createChildTest(childTest14, extent, som);
			com.navigateToCOMOrder();
			som.navigateToSOMOrder();

			ExtentTest childTest15 = extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest15);
			repo = createChildTest(childTest15, extent, repo);
			order = createChildTest(childTest15, extent, order);
			order.navigateToOrderValidation(29, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC41", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(30, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC41", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			/*
			 * order.navigateToOrderValidation(31, "Yes", "No", "SOM");
			 * valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,
			 * tvBoxSerialNbr, "4xgateway")); boolean
			 * is4xgateway=repo.validateSOMIntegrationReportsSpcl(xls, "TC41", 3,
			 * valuesToPassForValidation);
			 * softAssert.assertTrue(is4xgateway,"Attributes are not validated successfully"
			 * ); if(!is4xgateway) { valuesToPassForValidation=new
			 * ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
			 * softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC41", 3,
			 * valuesToPassForValidation),"Attributes are not validated successfully"); }
			 * order.navigateToOrderPage();
			 * 
			 * order.navigateToOrderValidation(13, "Yes", "No", "SOM"); if(is4xgateway) {
			 * valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,
			 * tvPortalSerialNbr, "5xportal"));
			 * softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC41", 4,
			 * valuesToPassForValidation),"Attributes are not validated successfully"); }
			 * else { valuesToPassForValidation=new
			 * ArrayList<>(Arrays.asList(accntNum,tvBoxSerialNbr, "4xgateway"));
			 * softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC41", 4,
			 * valuesToPassForValidation),"Attributes are not validated successfully"); }
			 * order.navigateToOrderPage();
			 */

			log.debug("Leaving Collection_Resume_VOD_PPV_BeforeUG");
		} catch (Exception e) {
			log.error("Error in Collection_Resume_VOD_PPV_BeforeUG:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	public static void main(String args[]) {
		InputStream log4j = BeforeUpgrade.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
		PropertyConfigurator.configure(log4j);
	}
}
