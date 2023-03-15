package com.netcracker.shaw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import java.io.InputStream;
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

public class ImmediateCases extends SeleniumTestUp {
	
	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	ValidateReport repo=null;
	OrderHistoryPage orderHistory=null;
	SoftAssert softAssert=new SoftAssert();
	ArrayList<String> valuesToPassForValidation=new ArrayList<String>();

	Logger log=Logger.getLogger(ImmediateCases.class);

	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Immediate_Scenarios_Hardware_Swap(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","From the immediate scenarios tab perform a Hardware Swap for an existing customers");
			log.debug("Entering Immediate_Scenarios_Hardware_Swap");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String convergedHardware=data.get("Converged_hardware");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String swapConvergedHarwareNbr=ConvgdSrlNo;
			String techAppointment=data.get("Tech_Appointment");
			int initialComCount=Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount=Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
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

			ExtentTest childTest3=extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.selectConvergedHardware("Internet");
			order.deleteHardware("Switch Window");

			ExtentTest childTest5=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest6=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

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
			com=createChildTest(childTest9,extent,com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest10=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			order=createChildTest(childTest10, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest11=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			com.verifyCLECRequestAfterJobRun();
			
			ExtentTest childTest12=extent.startTest("Verify COM, SOM Initial Record Counts");
			test.appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			som=createChildTest(childTest12,extent,som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest13=extent.startTest("Navigate to Immediate Scenario Location");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			order.navigateToImmediateChangesPage(accntNum);
			com.swapImmediateConvrgdHardware("FirstSwap", swapConvergedHarwareNbr);
			order.navigateToOrderPage();

			ExtentTest childTest14=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest15=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			som=createChildTest(childTest15,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest16=extent.startTest("Validate COM Integration Report");
			test.appendChild(childTest16);
			repo=createChildTest(childTest16,extent,repo);
			order=createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(44, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr, swapConvergedHarwareNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC51", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest17=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest17);
			orderHistory=createChildTest(childTest17,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Immediate_Scenarios_Hardware_Swap");
		} catch (Exception e) {
			log.error("Error in Immediate_Scenarios_Hardware_Swap:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Immediate_Scenarios_Add_Email(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","From the immediate scenarios tab add an email for an exisiting customer with Internet");
			log.debug("Entering Immediate_Scenarios_Hardware_Swap");
			log.debug("Entering Immediate_Scenarios_Add_Email");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			 String convergedHardware=data.get("Converged_hardware");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNo=ConvgdSrlNo;
			String techAppointment=data.get("Tech_Appointment");
			int initialComCount=Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount=Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
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

			ExtentTest childTest3=extent.startTest("Add Internet");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);
//			order.deleteHardware("Switch Window");
			
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
			order.openCOMOrderPage("true",accntNum, "");
			
			ExtentTest childTest9=extent.startTest("Check COM, SOM Initial Counts");
			test.appendChild(childTest9);
			com=createChildTest(childTest9,extent,com);
			som=createChildTest(childTest9,extent,som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);
			
			ExtentTest childTest10=extent.startTest("Navigate to Immediate Scenario Location");
			test.appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			order.navigateToImmediateChangesPage(accntNum);
			order.navigateToImmediateFeatures("Email");
        
			ExtentTest childTest11=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest11);
			order=createChildTest(childTest11,extent,order);
			order.openCOMOrderPage("false",accntNum, "");

			ExtentTest childTest12=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			som=createChildTest(childTest12,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			/*
			 * ExtentTest childTest13=extent.startTest("Validate SOM Integration Report");
			 * test.appendChild(childTest13); repo=createChildTest(childTest13,extent,repo);
			 * order=createChildTest(childTest13,extent,order);
			 * order.navigateToOrderValidation(16, "Yes", "No", "SOM");
			 * valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			 * softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC52", 1,
			 * valuesToPassForValidation),"Attributes are not validated successfully");
			 * order.navigateToOrderPage();
			 */
			
			/*
			 * ExtentTest childTest14=extent.startTest("Validate COM Integration Report");
			 * test.appendChild(childTest14); com=createChildTest(childTest14,extent,com);
			 * repo=createChildTest(childTest14,extent,repo);
			 * order=createChildTest(childTest14,extent,order); String
			 * immediateEmailAddress=com.navigateToAddEmailImmediateOrder();
			 * valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,
			 * immediateEmailAddress));
			 * softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC52", 1,
			 * valuesToPassForValidation),"Attributes are not validated successfully");
			 * order.navigateToOrderPage();
			 */
			
			/*
			 * order.navigateToOrderValidation(17, "Yes", "No", "COM");
			 * valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,
			 * convergedHardWareSerialNo));
			 * softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC52", 2,
			 * valuesToPassForValidation),"Attributes are not validated successfully");
			 * order.navigateToOrderPage();
			 */
			ExtentTest childTest15=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest15);
			orderHistory=createChildTest(childTest15,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Immediate_Scenarios_Add_Email");
		} catch (Exception e) {
			log.error("Error in Immediate_Scenarios_Add_Email:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Immediate_Tab_Add_Phone_Features_Exisiting_Cust_Converged(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","From the immediate scenarios tab add phone Features for an exisiting customer with Converged hardware");
			log.debug("Entering Immediate_Tab_Add_Phone_Features_Exisiting_Cust_Converged");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String convergedHardware=data.get("Converged_hardware");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNo=ConvgdSrlNo;
			String techAppointment=data.get("Tech_Appointment");
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

			ExtentTest childTest3=extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);
			//order.selectConvergedHardware("Internet");
			//order.deleteHardware("Switch Window");

			ExtentTest childTest5=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			
			ExtentTest childTest6=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest7=extent.startTest("Review and Finish");
			test.appendChild(childTest7);
			order=createChildTest(childTest7, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest8=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest8);
			order=createChildTest(childTest8, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			
			ExtentTest childTest9=extent.startTest("Verify COM, SOM Initial Record Counts");
			test.appendChild(childTest9);
			com=createChildTest(childTest9,extent,com);
			som=createChildTest(childTest9,extent,som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);
			
			ExtentTest childTest10=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
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
			order.refreshPage();
			order.refreshPage();
			
			ExtentTest childTest13=extent.startTest("Navigate to Immediate Scenario Location");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			order.navigateToImmediateChangesPage(accntNum);
			order.navigateToImmediateFeatures("Phone Features");

			ExtentTest childTest14=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest15=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			som=createChildTest(childTest15,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			
			ExtentTest childTest16=extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest16);
			repo=createChildTest(childTest16,extent,repo);
			order=createChildTest(childTest16,extent,order);
			order.navigateToOrderValidation(20, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNo));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC112", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(44, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(phoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC112", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest17=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest17);
			orderHistory=createChildTest(childTest17,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Immediate_Tab_Add_Phone_Features_Exisiting_Cust_Converged");	
		} catch (Exception e) {
			log.error("Error in Immediate_Tab_Add_Phone_Features_Exisiting_Cust_Converged:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Immediate_HW_Reset_Order_DPT_Hitron(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Hardware SN is visible in work order tab, after Immediate HW reset order - for Phone(DPT)+Internet(Hitron) account");
			log.debug("Entering Immediate_HW_Reset_Order_DPT_Hitron");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String phoneHardware=data.get("Phone_hardware");
			String internetProduct=data.get("Internet_Product");
			String internetHardware=data.get("Internet_hardware");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardwareSerialNbr=PhSrlNo;
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));
				 
			ExtentTest childTest0=extent.startTest("Prerequisite: checking all the stub toggeles");
			test.appendChild(childTest0);
			order=createChildTest(childTest0, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
		
			ExtentTest childTest1=extent.startTest("Navigate to login and open OE page");
			test.appendChild(childTest1);
			landing=createChildTest(childTest1, extent, landing);
			landing.Login();
			landing.openUrl();

			ExtentTest childTest2=extent.startTest("Fill Customer Info");
			test.appendChild(childTest2);
			order=createChildTest(childTest2, extent, order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			
			ExtentTest childTest4=extent.startTest("Add Phone Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneHardwareSerialNbr, phoneHardware);
			
			ExtentTest childTest5=extent.startTest("Add Internet Product");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6=extent.startTest("Add Internet Hardware");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);

			ExtentTest childTest7=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order=createChildTest(childTest7, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			
			ExtentTest childTest8=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest9=extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest10);
			order=createChildTest(childTest10, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			
			ExtentTest childTest11=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest12=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			order=createChildTest(childTest12,extent,order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest13=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			com.verifyCLECRequestAfterJobRun();
			
			ExtentTest childTest14=extent.startTest("Navigate to Immediate Scenario Location");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			order.navigateToImmediateChangesPage(accntNum);
			order.navigateToImmediateFeatures("Hardware");

			ExtentTest childTest15=extent.startTest("Navigate to Order History Page to check Hardware Serial Number");
			test.appendChild(childTest15);
			orderHistory=createChildTest(childTest15,extent,orderHistory);
			order=createChildTest(childTest15,extent,order);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "Reset");
			order.navigateToOrderPage();

			ExtentTest childTest16=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest16);
			com=createChildTest(childTest16,extent,com);
			som=createChildTest(childTest16,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			
			ExtentTest childTest17=extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest17);
			repo=createChildTest(childTest17,extent,repo);
			order=createChildTest(childTest17,extent,order);
			order.navigateToOrderValidation(24, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC91", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
	
			ExtentTest childTest18=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest18);
			orderHistory=createChildTest(childTest18,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Immediate_HW_Reset_Order_DPT_Hitron");	
		} catch (Exception e) {
			log.error("Error in Immediate_HW_Reset_Order_DPT_Hitron:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	public static void main(String args[]) {
		InputStream log4j=ModifyCases.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
		PropertyConfigurator.configure(log4j);
	}
}

