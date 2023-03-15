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
 * Aug 10, 2018
 */

public class ActivateCases extends SeleniumTestUp {

	LandingPage landing=null;
	OrderCreationPage order=null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	OrderHistoryPage orderHistory=null;
	ValidateReport repo=null;
		
	SoftAssert softAssert=new SoftAssert();
	ArrayList<String> valuesToPassForValidation=new ArrayList<String>();
	public String sheetName="TestData";
	
	Logger log=Logger.getLogger(ActivateCases.class);
	
	@Test(dataProvider = "getTestData", description = "Golden Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Activate_DoublePlay_Converged_PhInt(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Activate Phone and Internet on converged device");
			log.debug("Entering Activate_DoublePlay_Converged_PhInt");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			String phoneProduct=data.get("Phone_Product");
			String convergedHardware=data.get("Converged_hardware");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNo=ConvgdSrlNo;
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status Before Login");
			test.appendChild(childTest);
			landing=createChildTest(childTest, extent, landing);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate to login and open OE page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();
			
			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNo);
			order.addConvergedHardware1(convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch Window");
			//order.selectTodo();

			ExtentTest childTest5=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();
			
			ExtentTest childTest6=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest7=extent.startTest("Review and Finish");
			test.appendChild(childTest7);
			order=createChildTest(childTest7, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest8=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest8);
			order=createChildTest(childTest8, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest9=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest9);
			com=createChildTest(childTest9, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest10=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest10);
			order=createChildTest(childTest10, extent, order);
			com=createChildTest(childTest10, extent, com);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest11=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest12=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			som=createChildTest(childTest12, extent, som);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest13=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest13);
			repo=createChildTest(childTest13, extent, repo);
			order=createChildTest(childTest13, extent, order);
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNo));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC11", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
	
			order.navigateToOrderValidation(3, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNo));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC11", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest14=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest14);
			repo=createChildTest(childTest14, extent, repo);
			order=createChildTest(childTest14, extent, order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNo));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC11", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(convergedHardWareSerialNo));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC11", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNo));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC11", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest15=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest15);
			orderHistory=createChildTest(childTest15, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Activate_DoublePlay_Converged_PhInt");
		} catch (Exception e) {
			log.error("Error in Activate_DoublePlay_Converged_PhInt:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider = "getTestData", description = "Golden Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Activate_Phone_Internet_WithXB6_TV_BlueSkyBox_Portal(Hashtable<String, String> data) throws Exception {
		try {
			 test.log(LogStatus.INFO, "TestCase Name : ","Activate Phone and Internet on converged device and TV on Blue-sky Box + Portal");
			log.debug("Entering Activate_Phone_Internet_WithXB6_TV_BlueSkyBox_Portal");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNo=ConvgdSrlNo;
			String tvProduct=data.get("TV_Product");
			String internetProduct=data.get("Internet_Product");
			String phoneProduct=data.get("Phone_Product");
			TVBoxSrlNo=Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr=TVBoxSrlNo;
			TVPortalSrlNo=Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr=TVPortalSrlNo;
			String[] hardwareType={"ShawBlueSkyTVbox526","ShawBlueSkyTVportal416"};
			String[] serialNumber={tvBoxSerialNbr,tvPortalSerialNbr};
			String convergedHardware=data.get("Converged_hardware");
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest,extent,landing);
			order=createChildTest(childTest,extent,order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1,extent,order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);
			
			ExtentTest childTest4=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNo);
			order.addConvergedHardware1(convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch Window");

			ExtentTest childTest5=extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);

			
			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();
			//order.mocaFilterSelecttion("");
			
			ExtentTest childTest7=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.addServicesAndFeacturesTab();
 			order.selectTodo();

			ExtentTest childTest8=extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			
			ExtentTest childTest10=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			order=createChildTest(childTest11,extent,order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest13=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			som=createChildTest(childTest13,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest14=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest14);
			som=createChildTest(childTest14,extent,som);
			order=createChildTest(childTest14,extent,order);
			repo=createChildTest(childTest14,extent,repo);
			order.navigateToOrderValidation(10, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC12", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
						
			order.navigateToOrderValidation(11, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC12", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest15=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			repo=createChildTest(childTest15,extent,repo);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,phoneNbr,convergedHardWareSerialNo,tvBoxSerialNbr,tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC12", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(convergedHardWareSerialNo));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC12", 2, valuesToPassForValidation),"Attributes are not validated successfully");			
			order.navigateToOrderPage();
						
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,phoneNbr,convergedHardWareSerialNo,tvBoxSerialNbr,tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC12", 3, valuesToPassForValidation),"Attributes are not validated successfully");			
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest16);
			orderHistory=createChildTest(childTest16,extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Activate_Phone_Internet_WithXB6_TV_BlueSkyBox_Portal");
			
		} catch (Exception e) {
			log.error("Error in Activate_Phone_Internet_WithXB6_TV_BlueSkyBox_Portal:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void Activate_SinglePlay_Internet(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Activate a standalone Internet with Cisco/Hitron Device");
			log.debug("Entering Activate_SinglePlay_Internet");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			String internetHardware=data.get("Internet_hardware");
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
			String techAppointment=data.get( "Tech_Appointment" );
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest,extent,landing);
			order=createChildTest(childTest,extent,order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1,extent,order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add Internet Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			//order.deleteConvergedHardware();

			ExtentTest childTest5=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			
			ExtentTest childTest6=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest7=extent.startTest("Review and Finish");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest8=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest9=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest9);
			com=createChildTest(childTest9,extent,com);
			som=createChildTest(childTest9,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest10=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest10);
			repo=createChildTest(childTest10,extent,repo);
			order=createChildTest(childTest10,extent,order);
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC15", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(17, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC15", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest11=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest11);
			repo=createChildTest(childTest11,extent,repo);
			order=createChildTest(childTest11,extent,order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC15", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC15", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest12=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest12);
			orderHistory=createChildTest(childTest12,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Activate_SinglePlay_Internet");
		} catch (Exception e) {
			log.error("Error in Activate_SinglePlay_Internet:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void Activate_Internet_with_XB6_converged(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Activate Internet with XB6 converged device");
			log.debug("Entering Activate_Internet_with_XB6_converged");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			String convergedHardware=data.get("Converged_hardware");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNo=ConvgdSrlNo;
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest,extent,landing);
			order=createChildTest(childTest,extent,order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1,extent,order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNo);
			order.addConvergedHardware1(convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);

			ExtentTest childTest5=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			
			ExtentTest childTest6=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest7=extent.startTest("Review and Finish");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest8=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest9=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest9);
			com=createChildTest(childTest9,extent,com);
			som=createChildTest(childTest9,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest10=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest10);
			repo=createChildTest(childTest10,extent,repo);
			order=createChildTest(childTest10,extent,order);
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNo));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC16", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest11=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			repo=createChildTest(childTest11,extent,repo);
			order=createChildTest(childTest11,extent,order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNo));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC16", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC16", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
						
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNo));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC16", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
		
			ExtentTest childTest12=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest12);
			orderHistory=createChildTest(childTest12,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
        
			log.debug("Leaving Activate_Internet_with_XB6_converged");
		} catch (Exception e) {
			log.error("Error in Activate_Internet_with_XB6_converged:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider = "getTestData", description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void Activate_TV_With_DCT_700_HD_Guide(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Activate TV with DCT 700 and HD guide");
			log.debug("Entering Activate_TV_With_DCT_700_HD_Guide");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String tvProduct=data.get("TV_Product");
			TVDctSrlNo=Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr=TVDctSrlNo;
			TVHDSrlNo=Utility.generateHardwareSerailNum(TVHDSrlNo);
			String tvHardwareDCX3510SlNbr=TVHDSrlNo;
			String[] hardwareType={"DCT700","DCX3510HDGuide"};
			String[] serialNumber={tvHardwareSerialNbr,tvHardwareDCX3510SlNbr};
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest,extent,landing);
			order=createChildTest(childTest,extent,order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1,extent,order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add DCT and DCX 3510M TV Hardware");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);

			ExtentTest childTest4=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest5=extent.startTest("Review and Finish");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest6=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest7=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest7);
			com=createChildTest(childTest7,extent,com);
			som=createChildTest(childTest7,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest8=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest8);
			som=createChildTest(childTest8,extent,som);
			repo=createChildTest(childTest8,extent,repo);
			order=createChildTest(childTest8,extent,order);
			order.navigateToOrderValidation(10, "Yes", "No", "SOM");
			valuesToPassForValidation= new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr, "700DCT"));
			boolean is700DCT= repo.validateSOMIntegrationReportsSpcl(xls,"TC17", 1, valuesToPassForValidation);
			softAssert.assertTrue(is700DCT,"Attributes are not validated successfully");
			if(!is700DCT) 
			{
			   valuesToPassForValidation= new ArrayList<>(Arrays.asList(accntNum, tvHardwareDCX3510SlNbr, "3510dct"));
			   softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC17", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			 }   
			 order.navigateToOrderPage();
			 
			 order.navigateToOrderValidation(11, "Yes", "No", "SOM");
			if (is700DCT) {
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvHardwareDCX3510SlNbr, "3510dct"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC17", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			else {
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr, "700DCT"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC17", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			} 
			order.navigateToOrderPage();

			ExtentTest childTest9=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest9);
			com=createChildTest(childTest9,extent,com);
			repo=createChildTest(childTest9,extent,repo);
			order=createChildTest(childTest9,extent,order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation= new ArrayList<>(Arrays.asList(accntNum,tvHardwareSerialNbr,tvHardwareDCX3510SlNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC17", 1, valuesToPassForValidation),"Attributes are not validated successfully");

			valuesToPassForValidation= new ArrayList<>(Arrays.asList(tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC17", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation= new ArrayList<>(Arrays.asList(tvHardwareDCX3510SlNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC17", 3, valuesToPassForValidation),"Attributes are not validated successfully");

			ExtentTest childTest10=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest10);
			orderHistory=createChildTest(childTest10,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Activate_TV_With_DCT_700_HD_Guide");
			
		} catch (Exception e) {
			log.error("Error in Activate_TV_With_DCT_700_HD_Guide:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void Activate_TV_With_BlueSky_Xid_portals(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Activate TV with Blue-sky and Xid portals. Add Theme Packs, pick n pay channels and Seasonal package");
			log.debug("Entering Activate_TV_With_BlueSky_Xid_portals");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String tvProduct=data.get("TV_Product");
			String internetProduct=data.get("Internet_Product");
			String convergedHardware=data.get("Converged_hardware");
			TVBoxSrlNo=Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr=TVBoxSrlNo;
			TVPortalSrlNo=Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr=TVPortalSrlNo;
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardwareSerialNbr=ConvgdSrlNo;
			String[] hardwareType={"ShawBlueSkyTVbox526","ShawBlueSkyTVportal416"};
			String[] serialNumber={tvBoxSerialNbr,tvPortalSerialNbr};
			String techAppointment=data.get( "Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest,extent,landing);
			order=createChildTest(childTest,extent,order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1,extent,order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add TV Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceTV(tvProduct);

			ExtentTest childTest4=extent.startTest("Add Digital Channel Section,Theme and Pick10 pack1");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addDigitalChannelSectionThemePick10Pack1();

			ExtentTest childTest5=extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);

			ExtentTest childTest6=extent.startTest("Add Internet Product");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest7=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			//order.addConvergedHardware("Converged150", convergedHardwareSerialNbr);
			order.addConvergedHardware1(convergedHardwareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.selectTodoListCheckBox();

			ExtentTest childTest8=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			//order.mocaFilterSelecttion("");
			
			ExtentTest childTest9=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.addServicesAndFeacturesTab();
			order.selectTodo();

			ExtentTest childTest10=extent.startTest("Review and Finish");
			test.appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest11=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest11);
			order=createChildTest(childTest11,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest12=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			som=createChildTest(childTest12,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest13=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest13);
			repo=createChildTest(childTest13, extent, repo);
			order=createChildTest(childTest13, extent, order);
			order.navigateToOrderValidation(10, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway=repo.validateSOMIntegrationReportsSpcl(xls,"TC18", 1, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway, "Attributes are not validated successfully");
			if (!is4xgateway) {
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC18", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();
	
			order.navigateToOrderValidation(11, "Yes", "No", "SOM");
			if (is4xgateway) {
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC18", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			} else {
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC18", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			ExtentTest childTest14=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest14);
			repo=createChildTest(childTest14, extent, repo);
			order=createChildTest(childTest14, extent, order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardwareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC18", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(tvBoxSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC18", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC18", 3, valuesToPassForValidation),"Attributes are not validated successfully");

			ExtentTest childTest15= extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest15);
			orderHistory=createChildTest(childTest15, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Activate_TV_With_BlueSky_Xid_portals");
		} catch (Exception e) {
			log.error("Error in Activate_TV_With_BlueSky_Xid_portals:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider = "getTestData", description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void Activate_InternetConverged_TV_Bluesky(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Activate Internet on converged device and TV on Blue-sky Box + Portal");
			log.debug("Entering Activate_InternetConverged_TV_Bluesky");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			String convergedHardware=data.get("Converged_hardware");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardwareSerialNbr=ConvgdSrlNo;
			String tvProduct=data.get("TV_Product");
			TVBoxSrlNo=Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr=TVBoxSrlNo;
			TVPortalSrlNo=Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr=TVPortalSrlNo;
			String[] hardwareType={"ShawBlueSkyTVbox526","ShawBlueSkyTVportal416"};
			String[] serialNumber={tvBoxSerialNbr,tvPortalSerialNbr};
			String techAppointment=data.get( "Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest,extent,landing);
			order=createChildTest(childTest,extent,order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1,extent,order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			//order.addConvergedHardware("Converged150", convergedHardwareSerialNbr);
			order.addConvergedHardware1(convergedHardwareSerialNbr);
			order.selectConvergedHardware(convergedHardware);

			ExtentTest childTest5=extent.startTest("Add Service TV");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addServiceTV(tvProduct);

			ExtentTest childTest6=extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);
			order.selectTodoListCheckBox();

			ExtentTest childTest7=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			//order.mocaFilterSelecttion("");
			
			ExtentTest childTest8=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			
			ExtentTest childTest9=extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest11=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			som=createChildTest(childTest11,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest12=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest12);
			repo=createChildTest(childTest12,extent,repo);
			order=createChildTest(childTest12,extent,order);	
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC19", 1, valuesToPassForValidation),"Attributes are not validated successfully");		
			order.navigateToOrderPage();
				
			order.navigateToOrderValidation(10, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway=repo.validateSOMIntegrationReportsSpcl(xls,"TC19", 2, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway,"Attributes are not validated successfully");
			if(!is4xgateway) 
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC19", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(11, "Yes", "No", "SOM");
			if( is4xgateway ) 
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC19", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			else
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC19", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			ExtentTest childTest13=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest13);
			repo=createChildTest(childTest13,extent,repo);
			order=createChildTest(childTest13,extent,order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC19", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(tvBoxSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC19", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC19", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			
			order.navigateToOrderPage();
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardwareSerialNbr,tvBoxSerialNbr,tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC19", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
		
			ExtentTest childTest14=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest14);
			orderHistory=createChildTest(childTest14,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Activate_InternetConverged_TV_Bluesky");
		} catch (Exception e) {
			log.error("Error in Activate_InternetConverged_TV_Bluesky:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider = "getTestData", description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void Activate_TV_With_Moxi_Gateway(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Activate Tv with Moxi gateway");
			log.debug("Entering Activate_TV_With_Moxi_Gateway");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String tvProduct=data.get("TV_Product");
			TVMoxiSrlNo=Utility.generateHardwareSerailNum(TVMoxiSrlNo);
			String tvHardwareSerialNbr=TVMoxiSrlNo;
			String[] hardwareType={"ShawGatewayHDPVR"};
			String[] serialNumber={tvHardwareSerialNbr};
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest,extent,landing);
			order=createChildTest(childTest,extent,order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1,extent,order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add TV Hardware With Moxi Gateway");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);

			ExtentTest childTest4=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();
	
			ExtentTest childTest5=extent.startTest("Review and Finish");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest6=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",accntNum, "");

			ExtentTest childTest7=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest7);
			com=createChildTest(childTest7,extent,com);
			som=createChildTest(childTest7,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest8=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest8);
			som=createChildTest(childTest8,extent,som);
			repo=createChildTest(childTest8,extent,repo);
			order=createChildTest(childTest8,extent,order);
			order.navigateToOrderValidation(10, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC24", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest9=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest9);
			com=createChildTest(childTest9,extent,com);
			repo=createChildTest(childTest9,extent,repo);
			order=createChildTest(childTest9,extent,order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC24", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC24", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest10=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest10);
			orderHistory=createChildTest(childTest10,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Activate_TV_With_Moxi_Gateway");
			
		} catch (Exception e) {
			log.error("Error in Activate_TV_With_Moxi_Gateway:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider = "getTestData", description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void Activation_Fiber_Modem(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Validate the activation of  Fiber Modem");
			log.debug("Entering Activation_Fiber_Modem");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			IntFibrSrlNo=Utility.generateHardwareSerailNum(IntFibrSrlNo);
			String internetHardwareSerialNbr=IntFibrSrlNo;
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			
			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest,extent,landing);
			order=createChildTest(childTest,extent,order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationDropType("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order=createChildTest(childTest0,extent,order);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1,extent,order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);
			
			ExtentTest childTest4=extent.startTest("Add Internet Fibre Modem Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.internetFiberModemHardwareSelection(internetHardwareSerialNbr);
			
			ExtentTest childTest5=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.techAppointmentWithCurrentDate();
			
			ExtentTest childTest6=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();
			
			ExtentTest childTest7=extent.startTest("Review and Finish");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest8=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",accntNum, "");

			ExtentTest childTest9=extent.startTest("Navigate To New Interner hardware CFS Order");
			test.appendChild(childTest9);
			com=createChildTest(childTest9,extent,com);
			order=createChildTest(childTest9,extent,order);
			order.navigateToOrderValidation(8, "No", "No", "COM");
			com.verifySVGDiagram("");
			com.sendSerialNbrTask(internetHardwareSerialNbr);
			order.navigateToOrderPage();
			
			ExtentTest childTest10=extent.startTest("Wait For Tech Confirm Mark Finished");
			test.appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			order=createChildTest(childTest10,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest11=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			som=createChildTest(childTest11,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest12=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest12);
			som=createChildTest(childTest12,extent,som);
			repo=createChildTest(childTest12,extent,repo);
			order=createChildTest(childTest12,extent,order);
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC34", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest13=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			repo=createChildTest(childTest13,extent,repo);
			order=createChildTest(childTest13,extent,order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC34", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC34", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest14=extent.startTest("Navigate To QoS Value For Internet");
			test.appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			com.navigateToInternetQoSMapping();
			
			ExtentTest childTest15=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest15);
			orderHistory=createChildTest(childTest15,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
		
			log.debug("Leaving Activation_Fiber_Modem");
		} catch (Exception e) {
			log.error("Error in Activation_Fiber_Modem:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider = "getTestData", description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void Tax_exemption_for_TriplePlay_customer(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Validate Tax exemption functionality for a Triple Play customer");
			log.debug("Entering Tax_exemption_for_TriplePlay_customer");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			String convergedHardware=data.get("Converged_hardware");
			TVBoxSrlNo=Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr=TVBoxSrlNo;
			TVPortalSrlNo=Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr=TVPortalSrlNo;
			String[] hardwareType={"ShawBlueSkyTVbox526","ShawBlueSkyTVportal416"};
			String[] serialNumber={tvBoxSerialNbr,tvPortalSerialNbr};
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNo=ConvgdSrlNo;
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest,extent,landing);
			order=createChildTest(childTest,extent,order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1,extent,order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Select Tax Exemption");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.selectBillType("");
			order.clickBillingPreferencesMore();

			ExtentTest childTest4=extent.startTest("Add Phone and Internet");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5=extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);

			ExtentTest childTest6=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNo);
			order.addConvergedHardware1(convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);
			order.selectTodoListCheckBox();

			ExtentTest childTest7=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			//order.mocaFilterSelecttion("");
			
			ExtentTest childTest8=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest9=extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",accntNum, "Yes");

			ExtentTest childTest11=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest12=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest12);
			order=createChildTest(childTest12,extent,order);
			com=createChildTest(childTest12,extent,com);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest13=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest14=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			som=createChildTest(childTest14,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest15=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest15);
			repo=createChildTest(childTest15,extent,repo);
			order=createChildTest(childTest15,extent,order);
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNo, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC35", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
		
			ExtentTest childTest16=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest16);
			orderHistory=createChildTest(childTest16,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Tax_exemption_for_TriplePlay_customer");
		} catch (Exception e) {
			log.error("Error in Tax_exemption_for_TriplePlay_customer:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Retail_pickup_XB6_BlueSky_devices(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","verify the Retail order pickup functionality for a new activation- XB6 and Blue Sky devices");
			log.debug("Entering Retail_pickup_XB6_BlueSky_devices");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			String convergedHardware=data.get("Converged_hardware");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			TVBoxSrlNo=Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr=TVBoxSrlNo;
			TVPortalSrlNo=Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr=TVPortalSrlNo;
			String[] hardwareType={"ShawBlueSkyTVbox526","ShawBlueSkyTVportal416"};
			String[] serialNumber={tvBoxSerialNbr,tvPortalSerialNbr};
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest,extent,landing);
			order=createChildTest(childTest,extent,order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1,extent,order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			
			ExtentTest childTest4=extent.startTest("Add Internet Product");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.selectConvergedHardware("Internet");
			order.deleteHardware("Switch Window");
			
			ExtentTest childTest6=extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);
			
			ExtentTest childTest7=extent.startTest("Select Todo message for TV notification");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectTodoListCheckBox();
			
			ExtentTest childTest8=extent.startTest("Navigate To Tech Appointment with Retail pickup");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.selectInstallationOption("Self Connect Yes", "");
			order.techAppointmentNo("Retail Pickup", "", "");
			
			ExtentTest childTest9=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			
			ExtentTest childTest10=extent.startTest("Review and Finish");
			test.appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest11=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest11);
			order=createChildTest(childTest11,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",accntNum, "Yes");
		
			/*ExtentTest childTest12=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest12);
			orderHistory=createChildTest(childTest12,extent,orderHistory);
			order=createChildTest(childTest12,extent,order);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			orderHistory.navigateToOrderHistoryActivate();
			order.navigateToOrderPage();*/
		
			ExtentTest childTest13=extent.startTest("Verify COM, SOM Record counts");
			test.appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			som=createChildTest(childTest13,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest14=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest14);
			repo=createChildTest(childTest14,extent,repo);
			order=createChildTest(childTest14,extent,order);
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC53", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(10, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway=repo.validateSOMIntegrationReportsSpcl(xls,"TC53", 2, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway,"Attributes are not validated successfully");
			if(!is4xgateway) 
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC53", 2, valuesToPassForValidation),"Attributes are not validated successfully");		
			}
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(11, "Yes", "No", "SOM");
			if(is4xgateway) 
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC53", 3, valuesToPassForValidation),"Attributes are not validated successfully");				
			}
			else
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC53", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			ExtentTest childTest15=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest15);
			repo=createChildTest(childTest15,extent,repo);
			order=createChildTest(childTest15,extent,order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(convergedHardWareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC53", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,convergedHardWareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC53", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest16);
			orderHistory=createChildTest(childTest16,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Retail_pickup_XB6_BlueSky_devices");
		} catch (Exception e) {
			log.error("Error in Retail_pickup_XB6_BlueSky_devices:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Activate_Phone_DPT_Internet_Converged(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Activate DPT and Internet on converged device");
			log.debug("Entering Activate_Phone_DPT_Internet_Converged");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String phoneHardware=data.get("Phone_hardware");
			String internetProduct=data.get("Internet_Product");
			String convergedHardware=data.get("Converged_hardware");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNbr=PhSrlNo;
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest, extent, landing);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Phone and Phone Hardware");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addPhoneHardware(phoneSerialNbr, phoneHardware);
			
			ExtentTest childTest4=extent.startTest("Add Internet Product");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);
			order.deleteHardware("Switch Window");
		
			ExtentTest childTest5=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			
			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			
			ExtentTest childTest7=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest8=extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order=createChildTest(childTest8, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");
			order.refreshPage();

			ExtentTest childTest10=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com=createChildTest(childTest10, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11,extent,order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			com.verifyCLECRequestAfterJobRun();
			
			ExtentTest childTest13=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			som=createChildTest(childTest13,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			
			ExtentTest childTest14=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest14);
			repo=createChildTest(childTest14,extent,repo);
			order=createChildTest(childTest14,extent,order);
			order.navigateToOrderValidation(3, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC55", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC55", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest15=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest15);
			repo=createChildTest(childTest15,extent,repo);
			order=createChildTest(childTest15,extent,order);
	        order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneSerialNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC55", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC55", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC55", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneSerialNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC55", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest16);
			orderHistory=createChildTest(childTest16,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Activate_Phone_DPT_Internet_Converged");
		} catch (Exception e) {
			log.error("Error in Activate_Phone_DPT_Internet_Converged:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Future_Install_Order_Fiber_Modem(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Create a Future Dated install order for an account with Fiber Modem");
			log.debug("Entering Future_Install_Order_Fiber_Modem");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			IntFibrSrlNo=Utility.generateHardwareSerailNum(IntFibrSrlNo);
			String internetHardwareSerialNbr=IntFibrSrlNo;
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			
			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest,extent,landing);
			order=createChildTest(childTest,extent,order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationDropType("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order=createChildTest(childTest0,extent,order);
			landing=createChildTest(childTest0, extent, landing);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1,extent,order);
			order.customerInformation("Residential","Yes");
			
			ExtentTest childTest3=extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);
			
			ExtentTest childTest4=extent.startTest("Add Internet Fibre Modem Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.internetFiberModemHardwareSelection(internetHardwareSerialNbr);
			
			ExtentTest childTest5=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("Yes", "");
			
			ExtentTest childTest6=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();
			
			ExtentTest childTest7=extent.startTest("Review and Finish");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest8=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",accntNum, "");
			
			ExtentTest childTest9=extent.startTest("Wait For Effective Date Mark Finished Task");
			test.appendChild(childTest9);
			com=createChildTest(childTest9,extent,com);
			order=createChildTest(childTest9,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest10=extent.startTest("Wait for Provisioning Start Mark Finished Task");
			test.appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			order=createChildTest(childTest10,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest11=extent.startTest("Wait for tech Confirm Mark Finished Task");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			order=createChildTest(childTest11,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest12=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			som=createChildTest(childTest12,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest13=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest13);
			repo=createChildTest(childTest13,extent,repo);
			order=createChildTest(childTest13,extent,order);
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC64", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest14=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest14);
			repo=createChildTest(childTest14,extent,repo);
			order=createChildTest(childTest14,extent,order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC64", 1, valuesToPassForValidation),"Attributes are not validated successfully");			
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC64", 2, valuesToPassForValidation),"Attributes are not validated successfully");	
			order.navigateToOrderPage();
			
			ExtentTest childTest15=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest15);
			orderHistory=createChildTest(childTest15,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Future_Install_Order_Fiber_Modem");
		} catch (Exception e) {
			log.error("Error in Future_Install_Order_Fiber_Modem:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void New_Account_Activation_PortedTN_LifelineAlarm_No(Hashtable<String,String> data) throws Exception{
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Verify new account activation with Ported TN and Lifeline alarm=No");
			log.debug("Entering New_Account_Activation_PortedTN_LifelineAlarm_No");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String convergedHardware=data.get("Converged_hardware");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));			
			
			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest,extent,landing);
			order=createChildTest(childTest,extent,order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To LSC Response Job Paused");
			test.appendChild(childTest0);
			order=createChildTest(childTest0,extent,order);
			landing=createChildTest(childTest0, extent, landing);
			order.navigateToLSCResponseJobpaused();
			landing.openUrl();
	
			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1,extent,order);
			order.customerInformation("Residential","Yes");
			
			ExtentTest childTest3=extent.startTest("Add Phone");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String portPhoneNbr=order.portPhoneNumber("Phone Required", "", phoneProduct, "", "No", "No");

			ExtentTest childTest4=extent.startTest("Add Internet Product");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);

			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("Yes","");
			
			ExtentTest childTest7=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();
			
			ExtentTest childTest8=extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order=createChildTest(childTest8, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			
			ExtentTest childTest10=extent.startTest("Wait for Effective Date Mark Finish Task");
			test.appendChild(childTest10);
			com=createChildTest(childTest10, extent, com);
			order=createChildTest(childTest10, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest11=extent.startTest("Send serial number For Phone Hardware CFS Order");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("Send serial number for Internet Hardware CFS Order");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			order=createChildTest(childTest12, extent, order);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest13=extent.startTest("Navigate To LSC Job Run");
			test.appendChild(childTest13);
			com=createChildTest(childTest13, extent, com);
			order=createChildTest(childTest13, extent, order);
			com.navigateToJobMonitorURL("LSC");
			order.navigateToOrderPage();
			
			ExtentTest childTest14=extent.startTest("Wait for LSC Order finish Task");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			landing=createChildTest(childTest14, extent, landing);
			order.navigateToCLECOPSUser("true", "Yes");
			landing.loggingOffCLECOPSUser();
			landing.Login();
			order.navigateToOrderPage();
		
			ExtentTest childTest16=extent.startTest("Wait for Tech cnfm Mark Finish Task");
			test.appendChild(childTest16);
			com=createChildTest(childTest16, extent, com);
			order=createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest17=extent.startTest("Navigate to New SOM Phone product CFS order");
			test.appendChild(childTest17);
			com=createChildTest(childTest17, extent, com);
			order=createChildTest(childTest17, extent, order);			
			order.navigateToOrderValidation(2, "No", "No", "SOM");
			com.navigateToServiceInformationTab();
			Assert.assertTrue(com.portOptionDisplayed(), "Port Option is displaying for New Phone CFS order");
			order.navigateToOrderPage();
			
			ExtentTest childTest18=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest18);
			com=createChildTest(childTest18, extent, com);
			order=createChildTest(childTest18, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
		
			ExtentTest childTest19=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest19);
			com=createChildTest(childTest19, extent, com);
			som=createChildTest(childTest19, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest20=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest20);
			repo=createChildTest(childTest20, extent, repo);
			order=createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(3, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,portPhoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC84", 1, valuesToPassForValidation),"Attributes are not validated successfully");			
			order.navigateToOrderPage();
			
//			order.navigateToOrderValidation(24, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC84", 2, valuesToPassForValidation),"Attributes are not validated successfully");			
			order.navigateToOrderPage();
			
			ExtentTest childTest21=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest21);
			repo=createChildTest(childTest21, extent, repo);
			order=createChildTest(childTest21, extent, order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC84", 1, valuesToPassForValidation),"Attributes are not validated successfully");			
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, portPhoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC84", 2, valuesToPassForValidation),"Attributes are not validated successfully");			
			order.navigateToOrderPage();
			
			ExtentTest childTest22=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest22);
			orderHistory=createChildTest(childTest22, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving New_Account_Activation_PortedTN_LifelineAlarm_No");
		} catch (Exception e) {
			log.error("Error in New_Account_Activation_PortedTN_LifelineAlarm_No:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Retail_PickUp_Activate_Button(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Verify 'Activate' button for a new install order with 'Retail pickup' install type");
			log.debug("Entering Retail_PickUp_Activate_Button");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			String phoneHardware=data.get("Phone_hardware");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardWareSerialNbr=PhSrlNo;
			IntACSrlNo=Utility.generateHardwareSerailNum(IntACSrlNo);
			String internetHardwareSerialNbr=IntACSrlNo;
			TVBoxSrlNo=Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr=TVBoxSrlNo;
			TVPortalSrlNo=Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr=TVPortalSrlNo;
			String[] hardwareType={"ShawBlueSkyTVbox526","ShawBlueSkyTVportal416"};
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest, extent, landing);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();

			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Phone and Voice mail");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			
			ExtentTest childTest4=extent.startTest("Add Phone Hardware without Serial Num's");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addPhoneHardwareWithoutSlNos(phoneHardware);

			ExtentTest childTest5=extent.startTest("Add Internet Product");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6=extent.startTest("Add Internet Hardware without Serial Num's");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.addInternetHardwareWithoutSlNos("Cisco");

			ExtentTest childTest7=extent.startTest("Add TV Product");
			test.appendChild(childTest7);
			order=createChildTest(childTest7, extent, order);
			order.addServiceTV(tvProduct);

			ExtentTest childTest8=extent.startTest("Add TV Hardware without Serial Num's");
			test.appendChild(childTest8);
			order=createChildTest(childTest8, extent, order);
			order.addTVHardwareWithOutSrlNo(hardwareType);

			ExtentTest childTest9=extent.startTest("Navigate To Tech Appointment With Retail PickUp");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			order.selectInstallationOption("Self Connect Yes", "");
			order.techAppointmentNo("Retail Pickup", "", "");
			
			ExtentTest childTest10=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest11=extent.startTest("Review and Finish");
			test.appendChild(childTest11);
			order=createChildTest(childTest11, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest12=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest12);
			order=createChildTest(childTest12, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest13=extent.startTest("Navigate to Retail Pickup Page");
			test.appendChild(childTest13);
			orderHistory=createChildTest(childTest13, extent, orderHistory);
			orderHistory.navigateToOrderHistoryRetailPickupThreeLOB("true", accntNum, phoneHardWareSerialNbr,internetHardwareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr);

			ExtentTest childTest14=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest18=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest18);
			com=createChildTest(childTest18, extent, com);
			som=createChildTest(childTest18, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest19=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest19);
			orderHistory=createChildTest(childTest19, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Retail_PickUp_Activate_Button");
		} catch (Exception e) {
			log.error("Error in Retail_PickUp_Activate_Button:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Future_TriplePlay_DPT_Hitron_Moxi_AddPromos_Coupon(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Create a Future Dated install order for a triple play account- with DPT, Hitron and Moxi gateway, add promotion and coupon");
			log.debug("Entering Future_TriplePlay_DPT_Hitron_Moxi_Grandfathered_Promo_Coupon");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			String phoneHardware=data.get("Phone_hardware");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardWareSerialNbr=PhSrlNo;
			String internetHardware=data.get("Internet_hardware");
			IntACSrlNo=Utility.generateHardwareSerailNum(IntACSrlNo);
			String internetHardwareSerialNbr=IntACSrlNo;
			TVShawGatewaySrlNo=Utility.generateHardwareSerailNum(TVShawGatewaySrlNo);
			String tvShawGatewaySlrNbr=TVShawGatewaySrlNo;
			TVShawPortalSrlNo=Utility.generateHardwareSerailNum(TVShawPortalSrlNo);
			String tvShawPortalSlrNbr=TVShawPortalSrlNo;
			String techAppointment=data.get("Tech_Appointment");
			String[] hardwareType={"ShawGateway","ShawPortal"};
			String[] serialNumber={tvShawGatewaySlrNbr,tvShawPortalSlrNbr};
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest, extent, landing);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Phone and DPT hardware");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
		    order.addServicePhone("Add Product", phoneProduct);
			order.addPhoneHardware(phoneHardWareSerialNbr, phoneHardware);

			ExtentTest childTest4=extent.startTest("Add Internet And Cisco Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			
			ExtentTest childTest5=extent.startTest("Add TV With Shaw Gateway HDPVR and Shaw Gateway Portal");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);
			
			ExtentTest childTest6=extent.startTest("Add GrandFathered Promotions");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.addGrandfatheredPromotions();
		
			ExtentTest childTest7=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("", "");
			
			ExtentTest childTest8=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest9=extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",accntNum, "Yes");

			ExtentTest childTest11=extent.startTest("Wait For Effective Date Mark Finished Task");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			order=createChildTest(childTest11,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			//com.setMarkFinishedTask();
			com.setMarkFinishedSpecificTask("Wait for effective date");
			order.navigateToOrderPage();
			
			ExtentTest childTest12=extent.startTest("Send serial number For Phone Hardware CFS Order");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			order=createChildTest(childTest12,extent,order);
			order.navigateToOrderValidation(5, "No", "No", "COM");
			com.sendSerialNbrTask(phoneHardWareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest13=extent.startTest("Send serial number for Internet Hardware CFS Order");
			test.appendChild(childTest13);
			com=createChildTest(childTest13, extent, com);
			order=createChildTest(childTest13, extent, order);
			order.navigateToOrderValidation(8, "No", "No", "COM");
			com.sendSerialNbrTask(internetHardwareSerialNbr);
			order.navigateToOrderPage();
			
			ExtentTest childTest14=extent.startTest("Send Serial number for Shaw Gateway");
			test.appendChild(childTest14);
			com=createChildTest(childTest14, extent, com);
			order=createChildTest(childTest14, extent, order);
			order.navigateToOrderValidation(11, "No", "No", "COM");
			String tvHardwareType=com.getMoxiTVHardwareType();
			if (tvHardwareType.contains("MediaGateway")) {
				com.sendSerialNbrTask(tvShawGatewaySlrNbr);
			} else {
				com.sendSerialNbrTask(tvShawPortalSlrNbr);
			}
			order.navigateToOrderPage();
			
			ExtentTest childTest15=extent.startTest("Send Serial number for Shaw Portal");
			test.appendChild(childTest15);
			com=createChildTest(childTest15, extent, com);
			som=createChildTest(childTest15, extent, som);
			order=createChildTest(childTest15, extent, order);
			order.navigateToOrderValidation(12, "No", "No", "COM");
			String tvHardwareType1=com.getMoxiTVHardwareType();
			if (tvHardwareType1.contains("MediaPlayer")) {
				com.sendSerialNbrTask(tvShawPortalSlrNbr);	
			} else {
				com.sendSerialNbrTask(tvShawGatewaySlrNbr);
			}
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Wait For Tech Confirm Mark Finished");
			test.appendChild(childTest16);
			com=createChildTest(childTest16,extent,com);
			order=createChildTest(childTest16,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest17=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest17);
			com=createChildTest(childTest17,extent,com);
			com.verifyCLECRequestBeforeJobRun();
			
			ExtentTest childTest18=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest18);
			com=createChildTest(childTest18,extent,com);
			order=createChildTest(childTest18,extent,order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest19=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest19);
			com=createChildTest(childTest19,extent,com);
			com.verifyCLECRequestAfterJobRun();
			
			ExtentTest childTest20=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest20);
			com=createChildTest(childTest20, extent, com);
			som=createChildTest(childTest20, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest21=extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest21);
			repo=createChildTest(childTest21,extent,repo);
			order=createChildTest(childTest21,extent,order);
			order.navigateToOrderValidation(2, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC109", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC109", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(10, "Yes", "No", "SOM");
			valuesToPassForValidation= new ArrayList<>(Arrays.asList(accntNum, tvShawGatewaySlrNbr, "8MediaGateway"));
			boolean is8MediaGateway= repo.validateSOMIntegrationReportsSpcl(xls, "TC109", 3, valuesToPassForValidation);
			softAssert.assertTrue(is8MediaGateway, "Attributes are not validated successfully");
			if (!is8MediaGateway) {
				valuesToPassForValidation= new ArrayList<>(Arrays.asList(accntNum, tvShawPortalSlrNbr, "2000MediaPlayer"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC109", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(11, "Yes", "No", "SOM");
			if (is8MediaGateway) {
				valuesToPassForValidation= new ArrayList<>(Arrays.asList(accntNum, tvShawPortalSlrNbr, "2000MediaPlayer"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC109", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			} else {
				valuesToPassForValidation= new ArrayList<>(Arrays.asList(accntNum, tvShawGatewaySlrNbr, "8MediaGateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC109", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			ExtentTest childTest22=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest22);
			repo=createChildTest(childTest22,extent,repo); 
			order=createChildTest(childTest22,extent,order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneHardWareSerialNbr, internetHardwareSerialNbr, tvShawGatewaySlrNbr, tvShawPortalSlrNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC109", 1, valuesToPassForValidation),"Attributes are not validated successfully");
	    	order.navigateToOrderPage();
	    	
	    	order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneHardWareSerialNbr, internetHardwareSerialNbr, tvShawGatewaySlrNbr, tvShawPortalSlrNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC109", 2, valuesToPassForValidation),"Attributes are not validated successfully");
	    	order.navigateToOrderPage();
	    	
			ExtentTest childTest23=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest23);
			orderHistory=createChildTest(childTest23, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Future_TriplePlay_DPT_Hitron_Moxi_AddPromos_Coupon");
		} catch (Exception e) {
			log.error("Error in Future_TriplePlay_DPT_Hitron_Moxi_AddPromos_Coupon:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Agent_Enters_RentalSN_Purchased_Device(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Agent enters a Rental SN for purchased device");
			log.debug("Entering Agent_Enters_RentalSN_Purchased_Device");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			CustomerProvidedIntSrlNo=Utility.generateHardwareSerailNum(CustomerProvidedIntSrlNo);
			String internetHardwareSerialNbr=CustomerProvidedIntSrlNo;
			TVDcx3400SrlNo=Utility.generateHardwareSerailNum(TVDcx3400SrlNo);
			String tvDcx3400SlrNbr=TVDcx3400SrlNo;
			String techAppointment=data.get("Tech_Appointment");
			String[] hardwareType={"DCX3400_500GB_BUY"};
			String[] serialNumber={tvDcx3400SlrNbr};
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest, extent, landing);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");
			
			ExtentTest childTest3=extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add Purchased Hitron device for Internet");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			//order.addInternetHardware(internetHardwareSerialNbr,"Buy");
			order.addingCustomerProvidedEquipments("internet", internetHardwareSerialNbr);
//			order.deleteConvergedHardware();
			
			ExtentTest childTest5=extent.startTest("Add TV With Purchased DCX3400 Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);
			
			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			
			ExtentTest childTest7=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest8=extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",accntNum, "Yes");
			
			ExtentTest childTest10=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest10);
			com=createChildTest(childTest10, extent, com);
			som=createChildTest(childTest10, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest11=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest11);
			repo=createChildTest(childTest11,extent,repo);
			order=createChildTest(childTest11,extent,order);
			order.navigateToOrderValidation(8, "Yes", "No", "COM");	
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC117", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(11, "Yes", "No", "COM");	
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,tvDcx3400SlrNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC117", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest12=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest12);
			orderHistory=createChildTest(childTest12, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Agent_Enters_RentalSN_Purchased_Device");			
		} catch (Exception e) {
			log.error("Error in Agent_Enters_RentalSN_Purchased_Device:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
		
	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Submit_BtnDisplay_While_Adding_AnotherLOB_RetailPickup_Order(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Submit button should display/work for an existing double play account while adding another LOB for retail pickup order");
			log.debug("Entering Submit_BtnDisplay_While_Adding_AnotherLOB_RetailPickup_Order");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			String phoneHardware=data.get("Phone_hardware");
			String internetHardware=data.get("Internet_hardware");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardWareSerialNbr=PhSrlNo;
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
			TVDctSrlNo=Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr=TVDctSrlNo;
			TVDCXSrlNo=Utility.generateHardwareSerailNum(TVDCXSrlNo);
			String tvHardwareDCXSerialNbr=TVDCXSrlNo;
			TVDcx3400SrlNo=Utility.generateHardwareSerailNum(TVDcx3400SrlNo);
			String tv3400DCX500GBHardwareSrlNbr=TVDcx3400SrlNo;
			TVDcx3200P2MTCSrlNo=Utility.generateHardwareSerailNum(TVDcx3200P2MTCSrlNo);
			String tvDCX3200P2MTCSrlNbr=TVDcx3200P2MTCSrlNo;
			TV3400Dcx250GBSrlNo=Utility.generateHardwareSerailNum(TV3400Dcx250GBSrlNo);
			String tv3400DCX250GBSrlNbr=TV3400Dcx250GBSrlNo;
			TV3200HDGuideSrlNo=Utility.generateHardwareSerailNum(TV3200HDGuideSrlNo);
			String tvDCX3200HDGuideSrlNbr=TV3200HDGuideSrlNo;
			TVHDSrlNo=Utility.generateHardwareSerailNum(TVHDSrlNo); 
			String tvHardwareHDSerialNbr=TVHDSrlNo;
			String techAppointment=data.get("Tech_Appointment");
			String[] hardwareType={"DCT700", "DCX3200M", "DCX3200HDGuide", "DCX3200P2MTC", "DCX3400_500GB", "DCX3400_250GB", "DCX3510HDGuide"};
			int initialComCount=Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount=Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest, extent, landing);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
		
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");
			order.addServicesAndFeacturesTab();
			
			ExtentTest childTest3=extent.startTest("Add Phone Product And DPT Hardware");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addPhoneHardware(phoneHardWareSerialNbr, phoneHardware);

			ExtentTest childTest4=extent.startTest("Add Internet Product");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addServiceInternet(internetProduct);
			
			ExtentTest childTest5=extent.startTest("Add Internet Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			
			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest7=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			
			ExtentTest childTest8=extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			com=createChildTest(childTest9,extent,com);
			som=createChildTest(childTest9,extent,som);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			
			ExtentTest childTest10=extent.startTest("Navigate To Check COM & SOM Initial Record Counts");
			test.appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			som=createChildTest(childTest10,extent,som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);
			
			ExtentTest childTest11=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest12=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest12);
			order=createChildTest(childTest12, extent, order);
			com=createChildTest(childTest12, extent, com);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest13=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest13);
			com=createChildTest(childTest13, extent, com);
			com.verifyCLECRequestAfterJobRun();
			
			ExtentTest childTest14=extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest14);
			order=createChildTest(childTest14,extent,order);
			order.navigateToCustOrderPage("true",accntNum);
			
			ExtentTest childTest15=extent.startTest("Add TV With Max No of DCT Without Hardwares Devices");
			test.appendChild(childTest15);
			order=createChildTest(childTest15,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithOutSrlNo(hardwareType);
			order.selectTodoListCheckBox();
			
			ExtentTest childTest16=extent.startTest("Navigate To Tech Appointment with Retail pickup");
			test.appendChild(childTest16);
			order=createChildTest(childTest16,extent,order);
			order.selectInstallationOption("Self Connect Yes", "");
			order.techAppointmentNo("Retail Pickup", "", "");
			
			ExtentTest childTest17=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest17);
			order=createChildTest(childTest17,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			
			ExtentTest childTest18=extent.startTest("Review and Finish");
			test.appendChild(childTest18);
			order=createChildTest(childTest18,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest19=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest19);
			order=createChildTest(childTest19,extent,order);
			order.openCOMOrderPage("false", accntNum, "Yes");
			
			ExtentTest childTest20=extent.startTest("Navigate to Order History Page and Check Submit Button");
			test.appendChild(childTest20);
			orderHistory=createChildTest(childTest20,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			orderHistory.navigateToOrderHistoryRetailPickupTV(accntNum, tvHardwareSerialNbr, tvHardwareDCXSerialNbr, tvDCX3200HDGuideSrlNbr, tvDCX3200P2MTCSrlNbr, tv3400DCX500GBHardwareSrlNbr, tv3400DCX250GBSrlNbr, tvHardwareHDSerialNbr);
			
			ExtentTest childTest21=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest21);
			order=createChildTest(childTest21,extent,order);
			order.openCOMOrderPage("false", accntNum, "Yes");
			
			ExtentTest childTest22=extent.startTest("Navigate To Modify Cust Order and Check SVG Diagram");
			test.appendChild(childTest22);
			order=createChildTest(childTest22,extent,order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.verifySVGDiagram("Middle");
			order.navigateToOrderPage();
			
			/*
			 * ExtentTest
			 * childTest23=extent.startTest("Open CA in Order History page for Activation");
			 * test.appendChild(childTest23); orderHistory=createChildTest(childTest23,
			 * extent, orderHistory); orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			 * orderHistory.navigateToOrderHistoryActivate();
			 */
			
			ExtentTest childTest24=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest24);
			order=createChildTest(childTest24,extent,order);
			order.openCOMOrderPage("false", accntNum, "Yes");
			
			ExtentTest childTest25=extent.startTest("Verify COM and SOM & CLEC Record counts");
			test.appendChild(childTest25);
			com=createChildTest(childTest25,extent,com);
			som=createChildTest(childTest25,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			
			ExtentTest childTest26=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest26);
			repo=createChildTest(childTest26,extent,repo);
			order=createChildTest(childTest26,extent,order);
			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr, tvHardwareDCXSerialNbr, tvDCX3200HDGuideSrlNbr, tvDCX3200P2MTCSrlNbr, tv3400DCX500GBHardwareSrlNbr, tv3400DCX250GBSrlNbr, tvHardwareHDSerialNbr, phoneHardWareSerialNbr, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC118", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest27=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest27);
			orderHistory=createChildTest(childTest27, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Submit_BtnDisplay_While_Adding_AnotherLOB_RetailPickup_Order");	
		} catch (Exception e) {
			log.error("Error in Submit_BtnDisplay_While_Adding_AnotherLOB_RetailPickup_Order:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void VerifySMSNbr_AppoinmentTab_DefaultFee_NewAccount_Creation(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Verify SMS Number on appointment tab and Default installation fee during the New Account Creation");
			log.debug("Entering VerifySMSNbr_AppoinmentTab_DefaultFee_NewAccount_Creation");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			String internetHardware=data.get("Internet_hardware");
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
			TVDcx3400SrlNo=Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvDcx3400SlrNbr=TVDctSrlNo;
			String[] hardwareType={"DCT700"};
			String[] serialNumber={tvDcx3400SlrNbr};
			
			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest, extent, landing);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");
			
			ExtentTest childTest3=extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add Internet Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			
			ExtentTest childTest5=extent.startTest("Add TV With DCT Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			//order.selectTodo();
			
			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab Validations");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.selectInstallationOption("Self Connect Yes", "");
			order.navigateToCheckAppoinmentTabFeactures();
						
			ExtentTest childTest7=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest8=extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",accntNum, "");
			
			ExtentTest childTest10=extent.startTest("Wait for Provisioning start Mark Finish Task");
			test.appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			order=createChildTest(childTest10,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest11=extent.startTest("Wait For Tech Confirm Mark Finished");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			order=createChildTest(childTest11,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest12=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest12);
			repo=createChildTest(childTest12,extent,repo);
			order=createChildTest(childTest12,extent,order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC154", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest13=extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest13);
			order=createChildTest(childTest13,extent,order);
			order.navigateToCustOrderPage("false",accntNum);
			
			ExtentTest childTest14=extent.startTest("Change Internet Product");
			test.appendChild(childTest14);
			order=createChildTest(childTest14,extent,order);
			order.addServiceInternet("Fibre+ 150");
			
			ExtentTest childTest15=extent.startTest("Navigate To Tech Appointment Tab Validations");
			test.appendChild(childTest15);
			order=createChildTest(childTest15,extent,order);
			order.selectInstallationOption("", "Yes");
		
			ExtentTest childTest16=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest16);
			orderHistory=createChildTest(childTest16, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving VerifySMSNbr_AppoinmentTab_DefaultFee_NewAccount_Creation");			
		} catch (Exception e) {
			log.error("Error in VerifySMSNbr_AppoinmentTab_DefaultFee_NewAccount_Creation:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void New_IntegrationCall_NC_ERP_OrderPlaced_Multiple_TVDevices(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","New Integration call from NC -> ERP when order is placed with multiple TV devices");
			log.debug("Entering New_IntegrationCall_NC_ERP_OrderPlaced_Multiple_TVDevices");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			//String[] hardwareType={"DCT700","DCX3400_500GB"};
			String[] hardwareType={"DCT700","DCX3510HDGuide"};
			
			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest, extent, landing);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");
			
			ExtentTest childTest3=extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add Internet Hardware without Serial Num's");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addInternetHardwareWithoutSlNos("Hitron");
			
			ExtentTest childTest5=extent.startTest("Add TV and Hardware without Serial Num's");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithOutSrlNo(hardwareType);
			//order.selectTodo();
			
			ExtentTest childTest6=extent.startTest("Tech Appoinment With directFulfilment");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect Yes", "");
			order.techAppointmentNo("Mailout", "Service Location", "");
						
			ExtentTest childTest7=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest8=extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",accntNum, "");
			
			
			ExtentTest childTest10=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest10);
			repo=createChildTest(childTest10,extent,repo);
			order=createChildTest(childTest10,extent,order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC160", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC160", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC160", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC160", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			
			ExtentTest childTest11=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest11);
			orderHistory=createChildTest(childTest11, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving New_IntegrationCall_NC_ERP_OrderPlaced_Multiple_TVDevices");			
		} catch (Exception e) {
			log.error("Error in New_IntegrationCall_NC_ERP_OrderPlaced_Multiple_TVDevices:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Validate_Discount_ForNewResidental_Account_with_Internet_TV(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Validate_Discount_ForNewResidental_Account");
			log.debug("Entering Validate_Discount_ForNewResidental_Account_with_Internet_TV");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String phoneHardware=data.get("Phone_hardware");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNbr=PhSrlNo;
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			String internetProduct=data.get("Internet_Product");
//			String internetHardware=data.get("Internet_hardware");
//			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
//			String internetHardwareSerialNbr=IntHitronSrlNo;
			String internetHardware=data.get("Hitron");
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
			
			
			
			String tvProduct=data.get("TV_Product");
			TVDctSrlNo=Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr=TVDctSrlNo;
			TVHDSrlNo=Utility.generateHardwareSerailNum(TVHDSrlNo);
			String tvHardwareDCX3510SlNbr=TVHDSrlNo;
			String[] hardwareType={"DCX3510HDGuide"};
			String[] serialNumber={tvHardwareDCX3510SlNbr};
			String techAppointment=data.get("Tech_Appointment");
//			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
//			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest, extent, landing);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
//			order.openOEStubTochangeLocationCoaxial("false");
			landing.TestUser1Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();
			
			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");
			order.addServicesAndFeacturesTab();
			
			ExtentTest childTest2=extent.startTest("Add Phone and Phone Hardware");
			test.appendChild(childTest2);
			order=createChildTest(childTest2, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addPhoneHardware(phoneSerialNbr, phoneHardware);
			
			ExtentTest childTest3=extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServiceInternet("Fibre+ 150");
			
			ExtentTest childTest4=extent.startTest("Add Internet Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addInternetHardware("Hitron", internetHardwareSerialNbr);
			
			ExtentTest childTest5=extent.startTest("Add DCX 3510M TV Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceTV("Total TV");
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);
			
			
			ExtentTest childTest6=extent.startTest("Navigate to Promotion Page");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.addServicesAndFeacturesTab();
			order.ValidatePromotions();
//			order.ValidatePromotionsinRetail();
//			order.ValidatePromotionsinInboundSales();
			
			ExtentTest childTest7 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			
			ExtentTest childTest8 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest9 = extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();
			order.addServicesAndFeacturesTab();
			
			ExtentTest childTest10 = extent.startTest("Review and Finish");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.reviewAndFinishOrder();
			
			
			ExtentTest childTest11=extent.startTest("Navigate To COM, SOM, CLEC Orders");
			test.appendChild(childTest11);
			order=createChildTest(childTest11, extent, order);
			String accntNum=order.getAccountNumber();
//			String accntNum1=order.getAccountNumber();
//			String accntNum3=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			
//			ExtentTest childTest11=extent.startTest("Navigate To E911 Job Run");
//			test.appendChild(childTest11);
//			com=createChildTest(childTest11, extent, com);
//			order=createChildTest(childTest11, extent, order);
//			com.navigateToJobMonitorURL("E911");
//			order.navigateToOrderPage();
			
			ExtentTest childTest12=extent.startTest("Navigate To COM, SOM, CLEC Orders");
			test.appendChild(childTest12);
			order=createChildTest(childTest12, extent, order);
//			String accntNum=order.getAccountNumber();
//			String accntNum1=order.getAccountNumber();
//			String accntNum3=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			order.refreshPage();
			order.refreshPage();
			
			ExtentTest childTest13=extent.startTest("Navigate to OE Page");
			test.appendChild(childTest13);
			order=createChildTest(childTest13,extent,order);
//			String accntNum=order.getAccountNumber();
			order.navigateToCustOrderPage("true", accntNum);
			order.ValidatePromotionsinGenral();
			
			
			log.debug("Validate_Discount_ForNewResidental_Account_with_Internet_TV");
		} catch (Exception e) {
			log.error("Error in Validate_Discount_ForNewResidental_Account_with_Internet_TV:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void OE_Assign_DRs_Primary_And_Secondary_Line_To_Respective_Phone_Lines_When_TN_Inventory_API_Returns_Success_Response(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","OE_Assign_DRs_Primary_And_Secondary_Line_To_Respective_Phone_Lines_When_TN_Inventory_API_Returns_Success_Response");
			log.debug("Entering OE_Assign_DRs_Primary_And_Secondary_Line_To_Respective_Phone_Lines_When_TN_Inventory_API_Returns_Success_Response");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			
			String phoneProduct=data.get("Phone_Product");
			PhSrlNo = Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardwareSerialNbr = PhSrlNo;
			String phoneHardware = data.get("Phone_hardware");
//			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
//			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
//			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));
			
			ExtentTest childTest=extent.startTest("Navigate To Change PhoneNumber EndPoint Address");
			test.appendChild(childTest);
			landing=createChildTest(childTest,extent,landing);
			order=createChildTest(childTest,extent,order);
//			order.navigateFirstToOeStubServer();
//			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			order.ChangePhoneEndPointAddress("Incorrect");
			
			ExtentTest childTest0 = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			order = createChildTest(childTest0, extent, order);
			order.navigateFirstToOeStubServer();
//			order.openOEStubTochangeLocationCoaxial("false");
			order.navigateToCDIchangeAccountType("Residental/Staff");
			String cbsAccntNmb = order.CreateCBScustomer("Residential", "");
			order.AddTelephoneNumberToCOMX(cbsAccntNmb);
			landing.Login();
			
			ExtentTest childTest01 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest01);
			landing = createChildTest(childTest01, extent, landing);
			landing.NavigateAccountIdFromStubWithLocationId2(cbsAccntNmb);
			
			ExtentTest childTest2=extent.startTest("Add Phone Number");
			test.appendChild(childTest2);
			order=createChildTest(childTest2,extent,order);
			order.addServicesAndFeacturesTab();
			order.addPhoneHardware("", phoneHardware);
			order.phoneNumberConfiguration("", "Third Dropdown", phoneProduct);
			
			ExtentTest childTest3=extent.startTest("Add DR Phone Number for Migrate");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addDistinctiveRingForMigrate("One", "2 short rings");
			
			ExtentTest childTest4=extent.startTest("Download Files Winscp");
			test.appendChild(childTest4);
			com=createChildTest(childTest4, extent, com);
			com.downloadFileFromServerToLocal();
			
			ExtentTest childTest5=extent.startTest("Verify Files Winscp");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.StringMatchFromFile();
			
			ExtentTest childTest6=extent.startTest("Add DR Second Phone Number for Migrate");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.addServicesAndFeacturesTab();
			order.addDistinctiveRingForMigrate("Two", "2 short rings");
			order.addServicesAndFeacturesTab();
//			order.selectTodoListCheckBox();
//			order.selectTodoListCheckBox();
			
			ExtentTest childTest7=extent.startTest("Download Files Winscp");
			test.appendChild(childTest7);
			com=createChildTest(childTest7, extent, com);
			com.downloadFileFromServerToLocal();
			
			ExtentTest childTest8=extent.startTest("Verify Files Winscp");
			test.appendChild(childTest8);
			order=createChildTest(childTest8, extent, order);
			order.StringMatchFromFile();
			
			ExtentTest childTest9=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			order.selectInstallationOption("", "");
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();
			
			ExtentTest childTest10=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest11=extent.startTest("Review and Finish");
			test.appendChild(childTest11);
			order=createChildTest(childTest11, extent, order);
			order.reviewAndFinishOrder();
			
			ExtentTest childTest12=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest12);
			order=createChildTest(childTest12, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			
			ExtentTest childTest17=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest17);
			repo=createChildTest(childTest17,extent,repo);
			order=createChildTest(childTest17,extent,order);
//			String accntNum=order.getAccountNumber();
			order.navigateToOrderValidation(23, "Yes", "No", "COM");
 			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC175", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest18=extent.startTest("Navigate To Change PhoneNumber EndPoint Address");
			test.appendChild(childTest18);
			landing=createChildTest(childTest18,extent,landing);
			order=createChildTest(childTest18,extent,order);
//			order.navigateFirstToOeStubServer();
//			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			order.ChangePhoneEndPointAddress("Correct");
			
			
			log.debug("OE_Assign_DRs_Primary_And_Secondary_Line_To_Respective_Phone_Lines_When_TN_Inventory_API_Returns_Success_Response");
			} catch (Exception e) {
				log.error("Error in OE_Assign_DRs_Primary_And_Secondary_Line_To_Respective_Phone_Lines_When_TN_Inventory_API_Returns_Success_Response:" + e.getMessage());
				test.log(LogStatus.FAIL, "Test Failed");
				Assert.fail();
			}
		}
			
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Call_New_ESI_Service_During_Automated_Account_Migration(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Call_New_ESI_Service_During_Automated_Account_Migration");
			log.debug("Entering Call_New_ESI_Service_During_Automated_Account_Migration");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
	
			ExtentTest childTest0 = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			order = createChildTest(childTest0, extent, order);
			order.navigateFirstToOeStubServer();
//			order.openOEStubTochangeLocationCoaxial("false");
			order.navigateToCDIchangeAccountType("Residental/Staff");
			String cbsAccntNmb = order.CreateCBScustomer("Residential", "");
            landing.Login();
			
			ExtentTest childTest01 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest01);
			landing = createChildTest(childTest01, extent, landing);
			landing.NavigateAccountIdFromStubWithLocationId2(cbsAccntNmb);
			
			ExtentTest childTest2=extent.startTest("Download Files Winscp");
			test.appendChild(childTest2);
			com=createChildTest(childTest2, extent, com);
			com.downloadFileFromServerToLocal();
			
			ExtentTest childTest3=extent.startTest("Verify Files Winscp");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.StringMatchFromFile();
			
			ExtentTest childTest4=extent.startTest("Varify Todo Text And Radio Button");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addServicesAndFeacturesTab();
//			order.TodoTextValidation();
			order.ValidateInternetRadioBtn();
			
			ExtentTest childTest5=extent.startTest("Verify Internet Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.verifyInternetHardware();
			order.verifyInternetEmail();
			order.portPhoneNumber("", "", phoneProduct, "", "No", "No");
			
			ExtentTest childTest6=extent.startTest("Verify Customer ID");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.VerifyCustomerDetails();
			
			ExtentTest childTest7=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order=createChildTest(childTest7, extent, order);
			order.selectInstallationOption("", "");
			order.enteringChangesAuthorizedBy();
//			order.selectingSalesRepresentativeCheckBox();
			
			ExtentTest childTest8=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest9=extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			order.reviewAndFinishOrder();
			
			ExtentTest childTest13=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			
			ExtentTest childTest10=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest10);
			orderHistory=createChildTest(childTest10, extent, orderHistory);
//			String accntNum=order.getAccountNumber();
			orderHistory.navigateToOrderHistoryUrlLocationId2(accntNum, "");
			
			ExtentTest childTest11=extent.startTest("Navigate to Order History Page And Close CBS");
			test.appendChild(childTest11);
			orderHistory=createChildTest(childTest11, extent, orderHistory);
			order=createChildTest(childTest11, extent, order);
//			String accntNum=order.getAccountNumber();
			orderHistory.navigateToOrderHistoryUrlLocationId2(accntNum, "Product Summary");
			order.CloseCBS();
			order.refreshPage();
			
			ExtentTest childTest12=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest12);
			order=createChildTest(childTest12, extent, order);
//			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			
			log.debug("Call_New_ESI_Service_During_Automated_Account_Migration");
		} catch (Exception e) {
			log.error("Error in Call_New_ESI_Service_During_Automated_Account_Migration:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void New_ESI_API_Call_To_Retrieve_Email_And_Their_Status_For_Auto_Migration_Order(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","New_ESI_API_Call_To_Retrieve_Email_And_Their_Status_For_Auto_Migration_Order");
			log.debug("Entering New_ESI_API_Call_To_Retrieve_Email_And_Their_Status_For_Auto_Migration_Order");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			ExtentTest childTest0 = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			order = createChildTest(childTest0, extent, order);
			order.navigateFirstToOeStubServer();
//			order.openOEStubTochangeLocationCoaxial("false");
			order.navigateToCDIchangeAccountType("Residental/Staff");
			String cbsAccntNmb = order.CreateCBScustomer("Residential", "");
            landing.Login();
			
			ExtentTest childTest01 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest01);
			landing = createChildTest(childTest01, extent, landing);
			landing.NavigateAccountIdFromStubWithLocationId2(cbsAccntNmb);
			
			ExtentTest childTest2=extent.startTest("Download Files Winscp");
			test.appendChild(childTest2);
			com=createChildTest(childTest2, extent, com);
			com.downloadFileFromServerToLocal();
			
			ExtentTest childTest3=extent.startTest("Verify Files Winscp");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.StringMatchFromFile();
			
			ExtentTest childTest4=extent.startTest("Varify Todo Text And Radio Button");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addServicesAndFeacturesTab();
//			order.TodoTextValidation();
			String portPhoneNumber = order.portPhoneNumber("", "", phoneProduct, "", "No", "No");
			order.ValidateInternetRadioBtn();
			
			ExtentTest childTest5=extent.startTest("Verify Internet Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.verifyInternetHardware();
			order.verifyInternetEmail();
			
			ExtentTest childTest6=extent.startTest("Enable Radio Buttons");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
//			order.UnlockOperationOrder();
			
			ExtentTest childTest7=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order=createChildTest(childTest7, extent, order);
			order.selectInstallationOption("", "");
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();
			
			ExtentTest childTest8=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest9=extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			order.reviewAndFinishOrder();
			
			ExtentTest childTest10=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest10);
			orderHistory=createChildTest(childTest10, extent, orderHistory);
			String accntNum=order.getAccountNumber();
			orderHistory.navigateToOrderHistoryUrlLocationId2(accntNum, "");
			
			ExtentTest childTest11=extent.startTest("Navigate to Order History Page And Close CBS");
			test.appendChild(childTest11);
			orderHistory=createChildTest(childTest11, extent, orderHistory);
			order=createChildTest(childTest11, extent, order);
//			String accntNum=order.getAccountNumber();
			orderHistory.navigateToOrderHistoryUrlLocationId2(accntNum, "Product Summary");
			order.CloseCBS();
			order.refreshPage();
			
			log.debug("New_ESI_API_Call_To_Retrieve_Email_And_Their_Status_For_Auto_Migration_Order");
		} catch (Exception e) {
			log.error("Error in New_ESI_API_Call_To_Retrieve_Email_And_Their_Status_For_Auto_Migration_Order:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
			
	}
	
	public static void main (String args[])
	{
		InputStream log4j=ActivateCases.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
        PropertyConfigurator.configure(log4j);
	}
}


