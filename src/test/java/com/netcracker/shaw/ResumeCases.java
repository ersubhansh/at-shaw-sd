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

public class ResumeCases extends SeleniumTestUp {

	LandingPage landing = null;
	OrderCreationPage order = null;
	COMOrdersPage com = null;
	SOMOrderPage som = null;
	ValidateReport repo = null;
	OrderHistoryPage orderHistory = null;
	SoftAssert softAssert = new SoftAssert();
	ArrayList<String> valuesToPassForValidation = new ArrayList<String>();

	Logger log = Logger.getLogger(ResumeCases.class);

	@Test(dataProvider = "getTestData", description = "Golden Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Resume_TriplePlay(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify resumption of a AUP Suspended Triple Play account with XB6-Converged device");
			log.debug("Entering Resume_TriplePlay");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNo = ConvgdSrlNo;
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

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet Products");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add TV with TV Hardware");
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
			// order.deleteHardware("Switch Window");

			ExtentTest childTest6 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();
			// order.mocaFilterSelecttion("");

			ExtentTest childTest7 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest8 = extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.enteringChangesAuthorizedBy();

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

			ExtentTest childTest14 = extent.startTest("Suspend Phone");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			com.suspendLOBProducts("Phone Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("Phone"), "Active",
					"Suspend Phone Status is not Correct");

			ExtentTest childTest15 = extent.startTest("Suspend Internet");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			com.suspendLOBProducts("Internet Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("Internet"), "Active",
					"Suspend Internet Status is not Correct");

			ExtentTest childTest16 = extent.startTest("Suspend TV");
			test.appendChild(childTest16);
			com = createChildTest(childTest16, extent, com);
			com.suspendLOBProducts("TV Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("TV"), "Active", "Suspend TV Status is not Correct");

			ExtentTest childTest17 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			com = createChildTest(childTest17, extent, com);
			som = createChildTest(childTest17, extent, som);
			com.navigateToCOMOrder();
			som.navigateToSOMOrder();

			ExtentTest childTest18 = extent.startTest("Resume Phone Product");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			com.resumeLOBProducts("Phone Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("Phone"), "Inactive",
					"Resume Phone Status is not Correct");

			ExtentTest childTest19 = extent.startTest("Resume Internet Product");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			com.resumeLOBProducts("Internet Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("Internet"), "Inactive",
					"Resume Internet Status is not Correct");

			ExtentTest childTest20 = extent.startTest("Resume TV Product");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			com = createChildTest(childTest20, extent, com);
			com.resumeLOBProducts("TVHardware1");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("TV"), "Inactive", "Suspend TV Status is not Correct");
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			som = createChildTest(childTest21, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest22 = extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest22);
			repo = createChildTest(childTest22, extent, repo);
			order = createChildTest(childTest22, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC2", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(28, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC2", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(26, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC2", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest23 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest23);
			orderHistory = createChildTest(childTest23, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Resume_TriplePlay");
		} catch (Exception e) {
			log.error("Error in Resume_TriplePlay:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void Resume_TriplePlay_TCIS(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify Resumption of a TCIS suspended Triple Play account with XB6-Converged and Blue-sky Device");
			log.debug("Entering Resume_TriplePlay_TCIS");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNo = ConvgdSrlNo;
			TVBoxSrlNo = Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr = TVBoxSrlNo;
			TVPortalSrlNo = Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr = TVPortalSrlNo;
			String[] hardwareType = { "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber = { tvBoxSerialNbr, tvPortalSerialNbr };
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

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet Products");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add TV And TV Hardware");
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
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();
			// order.mocaFilterSelecttion("");

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

			ExtentTest childTest14 = extent.startTest("Click On Operations");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.navigateToOperations("Current Date");

			ExtentTest childTest15 = extent.startTest("Temporary Suspend Phone Internet and TV");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.tempSuspendTriplePlay();

			ExtentTest childTest16 = extent.startTest("Enter Installation Fee");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.enterInstallationFee("1.00");
			order.navigatetoAppointmentTab();
			order.enteringChangesAuthorizedBy();
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest17 = extent.startTest("Review and Finish");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest18 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest28 = extent.startTest("Wait For Effective Date Mark Finish");
			test.appendChild(childTest28);
			com = createChildTest(childTest28, extent, com);
			order = createChildTest(childTest28, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
//			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			order.refreshPage();
			 

			ExtentTest childTest19 = extent.startTest("Wait For Effective Date Mark Finish");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			order = createChildTest(childTest19, extent, order);
			order.navigateToOrderValidation(26, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest20 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest20);
			com = createChildTest(childTest20, extent, com);
			som = createChildTest(childTest20, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest21 = extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest21);
			repo = createChildTest(childTest21, extent, repo);
			order = createChildTest(childTest21, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC25", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(28, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC25", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			/*
			 * order.navigateToOrderValidation(27, "Yes", "No", "SOM");
			 * valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,
			 * tvBoxSerialNbr, "4xgateway")); boolean
			 * is4xgateway=repo.validateSOMIntegrationReportsSpcl(xls, "TC25", 3,
			 * valuesToPassForValidation);
			 * softAssert.assertTrue(is4xgateway,"Attributes are not validated successfully"
			 * ); if(!is4xgateway) { valuesToPassForValidation=new
			 * ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
			 * softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC25", 3,
			 * valuesToPassForValidation),"Attributes are not validated successfully"); }
			 * order.navigateToOrderPage();
			 * 
			 * order.navigateToOrderValidation(26, "Yes", "No", "SOM"); if(is4xgateway) {
			 * valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,
			 * tvPortalSerialNbr, "5xportal"));
			 * softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC25", 4,
			 * valuesToPassForValidation),"Attributes are not validated successfully"); }
			 * else { valuesToPassForValidation=new
			 * ArrayList<>(Arrays.asList(accntNum,tvBoxSerialNbr, "4xgateway"));
			 * softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC25", 4,
			 * valuesToPassForValidation),"Attributes are not validated successfully"); }
			 * order.navigateToOrderPage();
			 */

			ExtentTest childTest22 = extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest22);
			com = createChildTest(childTest22, extent, com);
			repo = createChildTest(childTest22, extent, repo);
			order = createChildTest(childTest22, extent, order);
			order.navigateToOrderValidation(33, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(
					Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNo, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC25", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest23 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest23);
			orderHistory = createChildTest(childTest23, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Resume_TriplePlay_TCIS");
		} catch (Exception e) {
			log.error("Error in Resume_TriplePlay_TCIS:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Resume_DPT_Hitron_Moxi(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Validate AUP resumption for an account with- DPT, Hitron and Legacy Gateway(Moxi)");
			log.debug("Entering Resume_DPT_Hitron_Moxi");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String internetHardware = data.get("Internet_hardware");
			String tvProduct = data.get("TV_Product");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardWareSerialNbr = PhSrlNo;
			String phoneHardware = data.get("Phone_hardware");
			TVMoxiSrlNo = Utility.generateHardwareSerailNum(TVMoxiSrlNo);
			String tvHardwareSerialNbr = TVMoxiSrlNo;
			String[] hardwareType = { "ShawGatewayHDPVR" };
			String[] serialNumber = { tvHardwareSerialNbr };
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
			landing.Login();

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Phone and Phone Hardware");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addPhoneHardware(phoneHardWareSerialNbr, phoneHardware);

			ExtentTest childTest4 = extent.startTest("Add Internet and Internet Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			// order.deleteConvergedHardware();

			ExtentTest childTest5 = extent.startTest("Add TV and TV Hardware");
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

			ExtentTest childTest10 = extent.startTest("CLEC Req Before Job Run");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			order = createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12 = extent.startTest("CLEC Req After Job Run");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest13 = extent.startTest("Suspend Phone");
			test.appendChild(childTest13);
			com = createChildTest(childTest13, extent, com);
			com.suspendLOBProducts("Phone Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("Phone"), "Active",
					"Suspend Phone Status is not Correct");

			ExtentTest childTest14 = extent.startTest("Suspend Internet");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			com.suspendLOBProducts("Internet Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("Internet"), "Active",
					"Suspend Internet Status is not Correct");

			ExtentTest childTest15 = extent.startTest("Suspend TV");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			som = createChildTest(childTest15, extent, som);
			com.suspendLOBProducts("TV Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("TV"), "Active", "Suspend TV Status is not Correct");
			com.navigateToCOMOrder();
			som.navigateToSOMOrder();

			ExtentTest childTest16 = extent.startTest("Resume Phone");
			test.appendChild(childTest16);
			com = createChildTest(childTest16, extent, com);
			com.resumeLOBProducts("Phone Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("Phone"), "Inactive",
					"Resume Phone Status is not Correct");

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
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("TV"), "Inactive", "Resume TV Status is not Correct");

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
			order.navigateToOrderValidation(26, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC66", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(28, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC66", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(25, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC66", 3, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest21);
			orderHistory = createChildTest(childTest21, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Resume_DPT_Hitron_Moxi");
		} catch (Exception e) {
			log.error("Error in Resume_DPT_Hitron_Moxi:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Resume_TCIS_Suspension_Changing_Snowbird_EndDate(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Resume TCIS Suspension by changing snowbird end date");
			log.debug("Entering Resume_TCIS_Suspension_Changing_Snowbird_EndDate");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
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
//			order.deleteHardware("Switch Window");

			ExtentTest childTest4 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order); 
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNo);
			order.addConvergedHardware1(convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);

			ExtentTest childTest5 = extent.startTest("Add TV and TV Hardware");
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

			ExtentTest childTest10 = extent.startTest("Navigate To Check COM & SOM Initial Record counts");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			// String accntNum = "41185022285";

			ExtentTest childTest11 = extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest12 = extent.startTest("Click On Operations");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.navigateToOperations("Current Date");

			ExtentTest childTest13 = extent.startTest("Temporary Suspend Internet and TV");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.tempSuspendLOB("Internet");
			order.tempSuspendLOB("TV");
			order.enterInstallationFee("1.00");

			ExtentTest childTest14 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.navigatetoAppointmentTab();
			order.enteringChangesAuthorizedBy();
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

			ExtentTest childTest17 = extent.startTest("Wait for Effective date Mark finish Task");
			test.appendChild(childTest17);
			com = createChildTest(childTest17, extent, com);
			order = createChildTest(childTest17, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			order.openCOMOrderPage("false", accntNum, "");
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest18 = extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest19 = extent.startTest("Changing Snowbird End Date To Today's Date");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.navigateToChangeSnowbirdEndDateToTodayDate();

			ExtentTest childTest20 = extent.startTest("Add customer Email Address");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.AddcustomerEmailAddress();

			ExtentTest childTest21 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.navigatetoAppointmentTab();
			order.enteringChangesAuthorizedBy();
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest22 = extent.startTest("Review and finish Order");
			test.appendChild(childTest22);
			order = createChildTest(childTest22, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest23 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest24 = extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest24);
			com = createChildTest(childTest24, extent, com);
			som = createChildTest(childTest24, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest25 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest25);
			orderHistory = createChildTest(childTest25, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Resume_TCIS_Suspension_Changing_Snowbird_EndDate");
		} catch (Exception e) {
			log.error("Error in Resume_TCIS_Suspension_Changing_Snowbird_EndDate:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void AutoCancel_AfterYdays_ExistingCust_AddingNewHwd_Directfulfilment_Yes(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify Auto Cancel after Y days for Existing customer by adding a new Hardware with Direct Fulfillment=yes");
			log.debug("Entering AutoCancel_AfterYdays_ExistingCust_AddingNewHwd_Directfulfilment_Yes");
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
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String diffDCTSerialNbr = TVDctSrlNo;
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
			landing.Login();

			ExtentTest childTest1 = extent
					.startTest("Verify Integration setting parameters Auto resume for direct fulfillment");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			landing = createChildTest(childTest1, extent, landing);
			order.navigateToIntegrationSettings("Direct fulfillment Order Days");
			landing.openUrl();

			ExtentTest childTest2 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
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
			order.selectConvergedHardware(convergedHardware); //
			order.deleteHardware("Switch Window");

			ExtentTest childTest6 = extent.startTest("Add TV With DCT Hardware");
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

			ExtentTest childTest11 = extent.startTest("Navigate To Check COM SOM Initial Counts");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			som = createChildTest(childTest11, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest12 = extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest13 = extent.startTest("Add One More DCT Hardware Without Serial Number");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithOutSrlNo(hardwareType);

			ExtentTest childTest14 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.selectInstallationOption("Self Connect Yes", "");
			order.techAppointmentNo("Mailout", "Service Location", "");

			ExtentTest childTest15 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest16 = extent.startTest("Review and Finish");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.reviewAndFinishOrder();
			
			//String accntNum = "73401824468";

			ExtentTest childTest17 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest18 = extent.startTest("Navigate To SVG Diagram");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			com = createChildTest(childTest18, extent, com);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.verifySVGDiagram("");
			order.navigateToOrderPage();

			ExtentTest childTest19 = extent.startTest("Navigate To Active Shipping Tasks");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			order = createChildTest(childTest19, extent, order);
			com.navigateToShippingTasks("Yes", diffDCTSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest20 = extent.startTest("Navigate To SVG Diagram");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			com = createChildTest(childTest20, extent, com);
			order.openCOMOrderPage("false", accntNum, "");
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.verifySVGDiagram("");
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Navigate to Putty and Time Shifting 15 days");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			com.navigateToConnectPuttyAndTimeShift("15");
			landing.Login();

			/*
			 * ExtentTest childTest22=extent.
			 * startTest("Navigate DB To Clear Reset Cache And NCDO Caches");
			 * test.appendChild(childTest22);
			 * order=createChildTest(childTest22,extent,order); landing.Login();
			 * order.navigateToConnectDB(); order.navigateToNCdoResetCaches();
			 * order.navigateToOrderPage();
			 */

			ExtentTest childTest23 = extent.startTest("Verify COM SOM Record Counts");
			test.appendChild(childTest23);
			com = createChildTest(childTest23, extent, com);
			som = createChildTest(childTest23, extent, som);
			landing.Login();
			order.openCOMOrderPage("false", accntNum, "");
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest24 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest24);
			orderHistory = createChildTest(childTest24, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving AutoCancel_AfterYdays_ExistingCust_AddingNewHwd_Directfulfilment_Yes");
		} catch (Exception e) {
			log.error(
					"Error in AutoCancel_AfterYdays_ExistingCust_AddingNewHwd_Directfulfilment_Yes:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void AutoResume_AfterYdays_NewCust_ProvisionCust_Confirmation_Yes(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify auto resume after Y days for a new customer with 'Provision on customer confirmation'=Yes");
			log.debug("Entering AutoResume_AfterYdays_NewCust_ProvisionCust_Confirmation_Yes");
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

			ExtentTest childTest1 = extent
					.startTest("Verify Integration setting parameters Auto resume for customer confirmation");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			landing = createChildTest(childTest1, extent, landing);
			order.navigateToIntegrationSettings("Provision On Customer");
			landing.openUrl();

			ExtentTest childTest2 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.customerInformation("Residential", "Yes");
			order.addServicesAndFeacturesTab();

			ExtentTest childTest4 = extent.startTest("Add Internet and Internet Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			// order.deleteConvergedHardware();

			ExtentTest childTest5 = extent.startTest("Add TV With DCT Hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest6 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNo("On confirmation", "", "");

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

			ExtentTest childTest10 = extent.startTest("Navigate To Check COM SOM Initial Counts");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			som = createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest11 = extent.startTest("Navigate to Putty and Time Shifting 3 days");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			com.navigateToConnectPuttyAndTimeShift("3");
			landing.Login();

			/*
			 * ExtentTest childTest12=extent.
			 * startTest("Navigate DB To Clear Reset Cache And NCDO Caches");
			 * test.appendChild(childTest12);
			 * order=createChildTest(childTest12,extent,order); landing.Login();
			 * order.navigateToConnectDB(); order.navigateToNCdoResetCaches();
			 */

			ExtentTest childTest13 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest14 = extent.startTest("Verify COM SOM Record Counts");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			som = createChildTest(childTest14, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest15 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest15);
			orderHistory = createChildTest(childTest15, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving AutoResume_AfterYdays_NewCust_ProvisionCust_Confirmation_Yes");
		} catch (Exception e) {
			log.error("Error in AutoResume_AfterYdays_NewCust_ProvisionCust_Confirmation_Yes:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void AutoResume_Ydays_New_RetailPickupS_DF_Yes_InflightAfter_2Days(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify Auto Resume after Y days for New order with Retail Pickup=Yes and then changed to Direct Fulfillment =yes as part of in-flight modify after 2 days");
			log.debug("Entering AutoResume_Ydays_New_RetailPickupS_DF_Yes_InflightAfter_2Days");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct = data.get("Internet_Product");
			IntHitronSrlNo = Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSrlNbr = IntHitronSrlNo;
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

			
			
			  ExtentTest childTest1 = extent
			  .startTest("Verify Integration setting parameters Auto resume for customer confirmation"
			  ); test.appendChild(childTest1); order = createChildTest(childTest1, extent,
			  order); landing = createChildTest(childTest1, extent, landing);
			  order.navigateToIntegrationSettings("Provision On Customer");
			  landing.openUrl();
			  
			  ExtentTest childTest2 = extent.startTest("Fill Customer Info");
			  test.appendChild(childTest2); order = createChildTest(childTest2, extent,
			  order); order.customerInformation("Residential", "Yes");
			  
			  ExtentTest childTest4 = extent.startTest("Add Internet Product");
			  test.appendChild(childTest4); order = createChildTest(childTest4, extent,
			  order); order.addServicesAndFeacturesTab();
			  order.addServiceInternet(internetProduct);
			  
			  ExtentTest childTest5 =
			  extent.startTest("Add Internet Hardware without Serial Num's");
			  test.appendChild(childTest5); order = createChildTest(childTest5, extent,
			  order); order.addInternetHardwareWithoutSlNos("Hitron");
			  
			  ExtentTest childTest6 = extent.startTest("Navigate To Tech Appointment Tab");
			  test.appendChild(childTest6); order = createChildTest(childTest6, extent,
			  order); order.selectInstallationOption("Self Connect Yes", "");
			  order.techAppointmentNo("Retail Pickup", "", "");
			  
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
			  order.openCOMOrderPage("true", accntNum, "");
			  
			  ExtentTest childTest10 =
			  extent.startTest("Navigate To Check COM SOM Initial Counts");
			  test.appendChild(childTest10); com = createChildTest(childTest10, extent,
			  com); som = createChildTest(childTest10, extent, som);
			  com.verifyRecordsCountInCOMOrder(initialComCount);
			  som.verifyRecordsCountInSOMOrder(initialSomCount);
			  
			  ExtentTest childTest11 =
			  extent.startTest("Navigate to Putty and Time Shifting 2 days");
			  test.appendChild(childTest11); com = createChildTest(childTest11, extent,
			  com); com.navigateToConnectPuttyAndTimeShift("2"); landing.Login();
			 

				/*
				 * ExtentTest childTest12 =
				 * extent.startTest("Navigate To DB to Clear Cache And NCDO Reset Caches");
				 * test.appendChild(childTest12); order = createChildTest(childTest12, extent,
				 * order); landing.Login(); order.navigateToConnectDB();
				 * order.navigateToNCdoResetCaches();
				 */
				
				
				  ExtentTest childTest13 = extent.startTest("Navigate To Cust Order Page");
				  test.appendChild(childTest13); order = createChildTest(childTest13, extent,
				  order); order.navigateToCustOrderPage("false", accntNum);
				  
				  ExtentTest childTest14 =
				  extent.startTest("Navigate To Tech Appointment Tab");
				  test.appendChild(childTest14); order = createChildTest(childTest14, extent,
				  order); order.selectInstallationOption("Self Connect Yes", "");
				  order.techAppointmentNo("Mailout", "Service Location", "");
				  order.selectingSalesRepresentativeCheckBox();
				  
				  ExtentTest childTest15 = extent.startTest("Review and Finish");
				  test.appendChild(childTest15); order = createChildTest(childTest15, extent,
				  order); order.reviewAndFinishOrder();
				  
				  ExtentTest childTest16 = extent.startTest("Navigate To COM & SOM Orders");
				  test.appendChild(childTest16); order = createChildTest(childTest16, extent,
				  order); order.openCOMOrderPage("false", accntNum, "");
				  
				  ExtentTest childTest17 = extent.startTest("Navigate To SVG Diagram");
				  test.appendChild(childTest17); order = createChildTest(childTest17, extent,
				  order); com = createChildTest(childTest17, extent, com);
				  order.navigateToOrderValidation(59, "No", "No", "COM");
				  com.verifySVGDiagram(""); order.navigateToOrderPage();
				  
				  ExtentTest childTest18 =
				  extent.startTest("Navigate To Active Shipping Tasks");
				  test.appendChild(childTest18); com = createChildTest(childTest18, extent,
				  com); order = createChildTest(childTest18, extent, order);
				  com.navigateToShippingTasks("Yes", internetHardwareSrlNbr);
				  order.navigateToOrderPage();
				 
			
			//String accntNum = "41329887456";

				ExtentTest childTest19 = extent.startTest("Navigate To COM & SOM Orders");
				test.appendChild(childTest19);
				order = createChildTest(childTest19, extent, order);
				order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest20 = extent.startTest("Navigate To SVG Diagram");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			com = createChildTest(childTest20, extent, com);
			order.navigateToOrderValidation(59, "No", "No", "COM");
			com.verifySVGDiagram("Middle");
			order.navigateToOrderPage();

			ExtentTest childTest21 = extent.startTest("Navigate to Putty and Time Shifting 15 days");
			test.appendChild(childTest21);
			com = createChildTest(childTest21, extent, com);
			com.navigateToConnectPuttyAndTimeShift("15");
			landing.Login();

			/*
			 * ExtentTest childTest22=extent.
			 * startTest("Navigate To DB to Clear Cache And NCDO Reset Caches");
			 * test.appendChild(childTest22);
			 * order=createChildTest(childTest22,extent,order); landing.Login();
			 * order.navigateToConnectDB(); order.navigateToNCdoResetCaches();
			 */

			/*
			 * ExtentTest childTest23=extent.
			 * startTest("Navigate To WF_Info page and check Process Variables");
			 * test.appendChild(childTest23);
			 * order=createChildTest(childTest23,extent,order);
			 * com=createChildTest(childTest23,extent,com); com.navigateToWfInfoJspPage();
			 */

			ExtentTest childTest24 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest24);
			order = createChildTest(childTest24, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest25 = extent.startTest("Verify COM SOM Record Counts");
			test.appendChild(childTest25);
			com = createChildTest(childTest25, extent, com);
			som = createChildTest(childTest25, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest26 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest26);
			orderHistory = createChildTest(childTest26, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving AutoResume_Ydays_New_RetailPickupS_DF_Yes_InflightAfter_2Days");
		} catch (Exception e) {
			log.error("Error in AutoResume_Ydays_New_RetailPickupS_DF_Yes_InflightAfter_2Days:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	public static void main(String args[]) {
		InputStream log4j = ResumeCases.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
		PropertyConfigurator.configure(log4j);
	}
}
