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
 * Aug 10, 2018
 */

public class SoupUIRestAPICases extends SeleniumTestUp {

	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	ValidateReport repo=null;
	SoapUIRestAPIpage soapRest=null;
	OrderHistoryPage orderHistory=null;
	SoftAssert softAssert=new SoftAssert();
	ArrayList<String> valuesToPassForValidation=new ArrayList<String>();

	Logger log=Logger.getLogger(SoupUIRestAPICases.class);
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void ERP_Disconnect_Order_Submission_SinglePlay(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","ERP Disconnect order submission for a Single play account");
			log.debug("Entering ERP_Disconnect_Order_Submission_SinglePlay");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			String internetHardware=data.get("Internet_hardware");
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
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
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3=extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add Internet Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			//order.selectTodo();

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
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest9=extent.startTest("Verify COM, SOM Initial Record Counts");
			test.appendChild(childTest9);
			com=createChildTest(childTest9, extent, com);
			som=createChildTest(childTest9, extent, som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest10=extent.startTest("Navigate to Order History status");
			test.appendChild(childTest10);
			orderHistory=createChildTest(childTest10, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			ExtentTest childTest11=extent.startTest("Trigger the Hardware Returned Endpoint");
			test.appendChild(childTest11);
			order=createChildTest(childTest11, extent, order);
			soapRest=createChildTest(childTest11, extent, soapRest);
			soapRest.navigateToHitHardwareReturnedEndpoint("TC133", internetHardwareSerialNbr);
			order.navigateToOrderPage();
		
			ExtentTest childTest12=extent.startTest("Check the Hardware Return Status");
			test.appendChild(childTest12);
			order=createChildTest(childTest12, extent, order);
			com=createChildTest(childTest12, extent, com);
			order.navigateToOrderValidation(53, "No", "No", "COM");
			com.navigateToServiceInformationTab();

			ExtentTest childTest13=extent.startTest("Navigate To Run Disconnect Returned Hardware Job");
			test.appendChild(childTest13);
			com=createChildTest(childTest13, extent, com);
			order=createChildTest(childTest13, extent, order);
			com.navigateToJobMonitorURL("Disconnect Returned Hardware");

			ExtentTest childTest14=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			com=createChildTest(childTest14, extent, com);
			som=createChildTest(childTest14, extent, som);
			order.openCOMOrderPage("false", accntNum, "");
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest15=extent.startTest("Verify COM and SOM Instances");
			test.appendChild(childTest15);
			com=createChildTest(childTest15, extent, com);
			com.VerifyComSomInstances();

			ExtentTest childTest16=extent.startTest("Navigate to Order History status");
			test.appendChild(childTest16);
			orderHistory=createChildTest(childTest16, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving ERP_Disconnect_Order_Submission_SinglePlay");
		} catch (Exception e) {
			log.error("Error in ERP_Disconnect_Order_Submission_SinglePlay:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void ERP_Disconnect_Order_Submission_SinglePlay_Two_Active_Devices(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","ERP Disconnect order submission for a single device when there are 2 active devices");
			log.debug("Entering ERP_Disconnect_Order_Submission_SinglePlay_Two_Active_Devices");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String phoneHardware=data.get("Phone_hardware");
			String internetProduct=data.get("Internet_Product");
			String internetHardware=data.get("Internet_hardware");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNbr=PhSrlNo;
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
			String techAppointment=data.get( "Tech_Appointment" );
			int initialComCount=Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount=Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
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
			
			ExtentTest childTest3=extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();

			ExtentTest childTest4=extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneSerialNbr, phoneHardware);
	
			ExtentTest childTest5=extent.startTest("Add Internet Product");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6=extent.startTest("Add Internet Hardware");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			//order.selectTodo();

			ExtentTest childTest7=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();
			
			ExtentTest childTest8=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest9=extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			String accntNum=order.getAccountNumber();
			String orderNum=order.getOrderNumber();
			order.openCOMOrderPage("true", accntNum, "");
			
			ExtentTest childTest11=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest12=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			order=createChildTest(childTest12,extent,order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest13=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest13);
			com=createChildTest(childTest13, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest14=extent.startTest("Verify COM, SOM Initial Record Counts");
			test.appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			som=createChildTest(childTest14,extent,som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);
			
			ExtentTest childTest15=extent.startTest("Trigger the Hardware Returned Endpoint");
			test.appendChild(childTest15);
			order=createChildTest(childTest15, extent, order);
			soapRest=createChildTest(childTest15, extent, soapRest);
			soapRest.navigateToHitHardwareReturnedEndpoint("TC134", phoneSerialNbr);
			order.navigateToOrderPage();
		
			ExtentTest childTest16=extent.startTest("Check the Hardware Return Status");
			test.appendChild(childTest16);
			order=createChildTest(childTest16,extent,order);
			com=createChildTest(childTest16,extent,com);
			order.navigateToOrderValidation(53, "No", "No", "COM");
			com.navigateToServiceInformationTab();
			
			ExtentTest childTest17=extent.startTest("Navigate To Run Disconnect Returned Hardware Job");
			test.appendChild(childTest17);
			com=createChildTest(childTest17,extent,com);
			order=createChildTest(childTest17,extent,order);
			com.navigateToJobMonitorURL("Disconnect Returned Hardware");
			order.navigateToOrderPage();
			
			ExtentTest childTest18=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest18);
			com=createChildTest(childTest18, extent, com);
			order=createChildTest(childTest18,extent,order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			
			ExtentTest childTest19=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest19);
			com=createChildTest(childTest19,extent,com);
			som=createChildTest(childTest19,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			
			ExtentTest childTest20=extent.startTest("Verify COM and SOM Instances");
			test.appendChild(childTest20);
			com=createChildTest(childTest20,extent,com);
			com.VerifyComSomInstances();
			
			ExtentTest childTest21=extent.startTest("Navigate to Order History status");
			test.appendChild(childTest21);
			orderHistory=createChildTest(childTest21,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving ERP_Disconnect_Order_Submission_SinglePlay_Two_Active_Devices");
		} catch (Exception e) {
			log.error("Error in ERP_Disconnect_Order_Submission_SinglePlay_Two_Active_Devices:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Execute_GetAc_Equipment_SlNBR_Endpoint_GetDetails_TriplePlayXB6_X1Gateway(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Execute the Get Account by Equipment Serial Number endpoint to get details of a Triple Play customer with XB6 converged hardware and X1 Gateway");
			log.debug("Entering Execute_GetAc_Equipment_SlNBR_Endpoint_GetDetails_TriplePlayXB6_X1Gateway");
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
			String[] hardwareType={ "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber={ tvBoxSerialNbr, tvPortalSerialNbr };
			String convergedHardware=data.get("Converged_hardware");
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));
			
			ExtentTest childTest=extent.startTest("Login and Open OE page");
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
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3=extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);
			
			ExtentTest childTest4=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNo);
			order.addConvergedHardware1(convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);
			
			ExtentTest childTest5=extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			order.selectTodoListCheckBox();
			//order.selectTodo();

			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();
			//order.mocaFilterSelecttion("");
			
			
			ExtentTest childTest7=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest8=extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order=createChildTest(childTest8, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			String accntNum=order.getAccountNumber();
			String orderNum=order.getOrderNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/soapUICases.properties","acctNumTC135", accntNum);
			Utility.setValueInPropertyFile("config/soapUICases.properties", "orderNumTC135", orderNum);
			
			ExtentTest childTest10=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com=createChildTest(childTest10, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			com.verifyCLECRequestAfterJobRun();
			
			ExtentTest childTest13=extent.startTest("Trigger the Request via RestAPI with Method as GET");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			soapRest=createChildTest(childTest13, extent, soapRest);
			soapRest.getAccountIDEndPointDetails_TC135();
			order.navigateToOrderPage();
		
			ExtentTest childTest14=extent.startTest("Verify COM, SOM and CLEC Record counts");
			test.appendChild(childTest14);
			com=createChildTest(childTest14, extent, com);
			som=createChildTest(childTest14, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest15=extent.startTest("Navigate to Order History status");
			test.appendChild(childTest15);
			orderHistory=createChildTest(childTest15, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Execute_GetAc_Equipment_SlNBR_Endpoint_GetDetails_TriplePlayXB6_X1Gateway");
		} catch (Exception e) {
			log.error("Error in Execute_GetAc_Equipment_SlNBR_Endpoint_GetDetails_TriplePlayXB6_X1Gateway:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
			
	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Execute_Get_OrderbyID_Endpoint_Tripleplay_Customer(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Execute the Get order by ID endpoint for a Triple play customer");
			log.debug("Entering Execute_Get_OrderbyID_Endpoint_Tripleplay_Customer");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
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
		
			ExtentTest childTest0=extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			order=createChildTest(childTest0,extent,order);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1,extent,order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add TV With BlueSky Hardwares");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);

			ExtentTest childTest5=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			order.selectTodoListCheckBox();
			//order.selectTodo();

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
			//order.addServicesAndFeacturesTab();
			//order.selectTodo();

			ExtentTest childTest8=extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			String accntNum=order.getAccountNumber();
			String orderNum=order.getOrderNumber();
			order.openCOMOrderPage("true",accntNum, "Yes");
			Utility.setValueInPropertyFile("config/soapUICases.properties", "orderNumTC136", orderNum);

			ExtentTest childTest10=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			order=createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			com.verifyCLECRequestAfterJobRun();
			
			ExtentTest childTest13=extent.startTest("Trigger the Request via RestAPI with Method as GET");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			soapRest=createChildTest(childTest13, extent, soapRest);
			soapRest.getOrderIdEndPointDetails();
			order.navigateToOrderPage();
			
			ExtentTest childTest14=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			som=createChildTest(childTest14,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest15=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest15);
			orderHistory=createChildTest(childTest15, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Execute_Get_OrderbyID_Endpoint_Tripleplay_Customer");
		} catch (Exception e) {
			log.error("Error in Execute_Get_OrderbyID_Endpoint_Tripleplay_Customer:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Add_Single_ActiveTVCustomer_Quote_Submission_DeltaAPI(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Add a single Channel on Active TV Customer using Quote Submission Delta API");
			log.debug("Entering Add_Single_ActiveTVCustomer_Quote_Submission_DeltaAPI");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String tvProduct=data.get("TV_Product");
			TVDctSrlNo=Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr=TVDctSrlNo;
			String[] hardwareType={"DCT700"};
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

			ExtentTest childTest4=extent.startTest("Add TV With DCT Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);
			
			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest7=extent.startTest("Review and Finish");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest8=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",accntNum, "Yes");
			
			ExtentTest childTest9=extent.startTest("Trigger the Request via RestAPI");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			soapRest=createChildTest(childTest9, extent, soapRest);
			soapRest.navigateToHitQuoteSubmissionDeltaAPI(accntNum);
			order.navigateToOrderPage();
			
			ExtentTest childTest22=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest22);
			com=createChildTest(childTest22,extent,com);
			som=createChildTest(childTest22,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest15=extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest15);
			repo=createChildTest(childTest15,extent,repo);
			order=createChildTest(childTest15,extent,order);
			order.navigateToOrderValidation(22, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC139", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest16);
			repo=createChildTest(childTest16,extent,repo);
			order=createChildTest(childTest16,extent,order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC139", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest23=extent.startTest("Navigate to Order History status");
			test.appendChild(childTest23);
			orderHistory=createChildTest(childTest23, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Add_Single_ActiveTVCustomer_Quote_Submission_DeltaAPI");
		} catch (Exception e) {
			log.error("Error in Add_Single_ActiveTVCustomer_Quote_Submission_DeltaAPI:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Execute_QuoteValidation_EndPoint_SSLServices_TriplePlay_FiberModem(Hashtable<String, String> data)throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Execute the My Account Quote Validation endpoint(sslservices/myaccount/quotedetails) for a triple play customer with Fiber Modem");
			log.debug("Entering Execute_QuoteValidation_EndPoint_SSLServices_TriplePlay_FiberModem");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String phoneHardware=data.get("Phone_hardware");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNbr=PhSrlNo;
			IntFibrSrlNo=Utility.generateHardwareSerailNum(IntFibrSrlNo);
			String internetHardwareSerialNbr=IntFibrSrlNo;
			TVShawGatewaySrlNo=Utility.generateHardwareSerailNum(TVShawGatewaySrlNo);
			String tvShawGatewaySlrNbr=TVShawGatewaySrlNo;
			TVShawPortalSrlNo=Utility.generateHardwareSerailNum(TVShawPortalSrlNo);
			String tvShawPortalSlrNbr=TVShawPortalSrlNo;
			String[] hardwareType={"ShawGateway","ShawPortal"};
			String[] serialNumber={tvShawGatewaySlrNbr,tvShawPortalSlrNbr};
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest, extent, landing);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationDropType("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);

			ExtentTest childTest4=extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneSerialNbr, phoneHardware);
			
			ExtentTest childTest5=extent.startTest("Add Internet with Fibre Modem Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addServiceInternet(internetProduct);
			order.internetFiberModemHardwareSelection(internetHardwareSerialNbr);
			
			ExtentTest childTest6=extent.startTest("Add TV With Shaw Gateway HDPVR and Shaw Gateway Portal");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);
			
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

			ExtentTest childTest10=extent.startTest("Navigate To COM, SOM Orders");
			test.appendChild(childTest10);
			order=createChildTest(childTest10, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			
			ExtentTest childTest11=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest12=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			order=createChildTest(childTest12, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest13=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest13);
			com=createChildTest(childTest13, extent, com);
			com.verifyCLECRequestAfterJobRun();
			
			ExtentTest childTest14=extent.startTest("Trigger the Request via SOAP API");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			soapRest=createChildTest(childTest14, extent, soapRest);
			soapRest.navigateToMyAcQuoteValidateFibre("TC140", accntNum);
			order.navigateToOrderPage();
		
			ExtentTest childTest20=extent.startTest("Verify COM, SOM and CLEC Record counts");
			test.appendChild(childTest20);
			com=createChildTest(childTest20, extent, com);
			som=createChildTest(childTest20, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest21=extent.startTest("Navigate to Order History status");
			test.appendChild(childTest21);
			orderHistory=createChildTest(childTest21, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Execute_QuoteValidation_EndPoint_SSLServices_TriplePlay_FiberModem");
		} catch (Exception e) {
			log.error("Error in Execute_QuoteValidation_EndPoint_SSLServices_TriplePlay_FiberModem:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Execute_GetAccount_ByID_TriplePlay_XB6Converged_X1Gateway(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Execute the Get Account by ID endpoint to get details of a Triple Play customer with XB6 converged hardware and X1 Gateway");
			log.debug("Entering Execute_GetAccount_ByID_TriplePlay_XB6Converged_X1Gateway");
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
			String[] hardwareType={ "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber={ tvBoxSerialNbr, tvPortalSerialNbr };
			String convergedHardware=data.get("Converged_hardware");
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));
			
			ExtentTest childTest=extent.startTest("Login and Open OE page");
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
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3=extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);
			
			ExtentTest childTest4=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);
			//order.selectConvergedHardware("Internet");
			//order.deleteHardware("Switch Window");
			
			ExtentTest childTest5=extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			order.selectTodoListCheckBox();
			//order.selectTodo();

			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();
			//order.mocaFilterSelecttion("");
			
			ExtentTest childTest7=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest8=extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order=createChildTest(childTest8, extent, order);
			order.reviewAndFinishOrder();
			
			ExtentTest childTest9=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/soapUICases.properties","acctNumTC141", accntNum);
			
			ExtentTest childTest10=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com=createChildTest(childTest10, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			com.verifyCLECRequestAfterJobRun();
			
			ExtentTest childTest13=extent.startTest("Trigger the GetAccountId Request via RestAPI");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			soapRest=createChildTest(childTest13, extent, soapRest);
			soapRest.getAccountIDEndPointX1Gateway_TC141();
			order.navigateToOrderPage();
		
			ExtentTest childTest14=extent.startTest("Verify COM, SOM and CLEC Record counts");
			test.appendChild(childTest14);
			com=createChildTest(childTest14, extent, com);
			som=createChildTest(childTest14, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest15=extent.startTest("Navigate to Order History status");
			test.appendChild(childTest15);
			orderHistory=createChildTest(childTest15, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Execute_GetAccount_ByID_TriplePlay_XB6Converged_X1Gateway");
		} catch (Exception e) {
			log.error("Error in Execute_GetAccount_ByID_TriplePlay_XB6Converged_X1Gateway:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Collection_Suspension_TriplePlay_XB6Converged_BlueSky(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Collection suspension of a triple play account with XB6-Converged and Blue-sky Device");
			log.debug("Entering Collection_Suspension_TriplePlay_XB6Converged_BlueSky");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			TVBoxSrlNo=Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr=TVBoxSrlNo;
			String[] hardwareType={ "ShawBlueSkyTVbox526" };
			String[] serialNumber={ tvBoxSerialNbr };
			String convergedHardware=data.get("Converged_hardware");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNo=ConvgdSrlNo;
			String techAppointment=data.get("Tech_Appointment");
			int initialComCount=Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount=Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));
			
			ExtentTest childTest=extent.startTest("Login and Open OE page");
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
			order.customerInformation("Residential", "Yes");

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
			
			ExtentTest childTest5=extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			//order.selectTodoListCheckBox();
			order.selectTodo();

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
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/soapUICases.properties","acctNumTC135", accntNum);
			
			ExtentTest childTest10=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com=createChildTest(childTest10, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			com.verifyCLECRequestAfterJobRun();
			
			ExtentTest childTest13=extent.startTest("Verify COM, SOM Initial Record Counts");
			test.appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			som=createChildTest(childTest13,extent,som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			
			ExtentTest childTest14=extent.startTest("Trigger the Suspend Request via RestAPI");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			soapRest=createChildTest(childTest14, extent, soapRest);
			soapRest.navigateToHitCollectionSuspension("TC142", accntNum);
			order.navigateToOrderPage();
		
			ExtentTest childTest15=extent.startTest("Verify COM, SOM and CLEC Record counts");
			test.appendChild(childTest15);
			com=createChildTest(childTest15, extent, com);
			som=createChildTest(childTest15, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest16=extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest16);
			repo=createChildTest(childTest16,extent,repo);
			order=createChildTest(childTest16,extent,order);
			order.navigateToOrderValidation(29, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC142", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(30, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC142", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(31, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC142", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest17=extent.startTest("Navigate to Order History status");
			test.appendChild(childTest17);
			orderHistory=createChildTest(childTest17, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Collection_Suspension_TriplePlay_XB6Converged_BlueSky");
		} catch (Exception e) {
			log.error("Error in Collection_Suspension_TriplePlay_XB6Converged_BlueSky:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Execute_GetProduct_ByID_TriplePlay_XB6Converged_X1Gateway(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Execute the Get Product Details By Customer Account endpoint for a triple play customer with XB6 converged hardware and X1 Gateway");
			log.debug("Entering Execute_GetProduct_ByID_TriplePlay_XB6Converged_X1Gateway");
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
			String[] hardwareType={ "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber={ tvBoxSerialNbr, tvPortalSerialNbr };
			String convergedHardware=data.get("Converged_hardware");
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));
			
			ExtentTest childTest=extent.startTest("Login and Open OE page");
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
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3=extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);
			
			ExtentTest childTest4=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNo);
			order.addConvergedHardware1(convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);
			
			ExtentTest childTest5=extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			//order.selectTodoListCheckBox();
			order.selectTodo();

			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			//order.mocaFilterSelecttion("");
						
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
			order.openCOMOrderPage("true", accntNum, "Yes");
		
			ExtentTest childTest10=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com=createChildTest(childTest10, extent, com);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			com.verifyCLECRequestAfterJobRun();
			
			ExtentTest childTest13=extent.startTest("Trigger the Request via SoapAPI");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			soapRest=createChildTest(childTest13, extent, soapRest);
			soapRest.navigateToHitGetProdDetailsByCustAc(accntNum);
			order.navigateToOrderPage();
		
			ExtentTest childTest14=extent.startTest("Verify COM, SOM and CLEC Record counts");
			test.appendChild(childTest14);
			com=createChildTest(childTest14, extent, com);
			som=createChildTest(childTest14, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest15=extent.startTest("Navigate to Order History status");
			test.appendChild(childTest15);
			orderHistory=createChildTest(childTest15, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Execute_GetProduct_ByID_TriplePlay_XB6Converged_X1Gateway");
		} catch (Exception e) {
			log.error("Error in Execute_GetProduct_ByID_TriplePlay_XB6Converged_X1Gateway:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Execute_CPE_EndPoint_GetEquipment_BySerialNbr(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Execute the CPE endpoint to Get Equipment by Serial Number");
			log.debug("Entering Execute_CPE_EndPoint_GetEquipment_BySerialNbr");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			String convergedHardware=data.get("Converged_hardware");
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));
			
			ExtentTest childTest=extent.startTest("Login and Open OE page");
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
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3=extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);
			
			ExtentTest childTest4=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.selectConvergedHardware("Internet");
			//order.deleteHardware("Switch Window");
			Utility.setValueInPropertyFile("config/soapUICases.properties","convergedSrlNbrTC144", convergedHardWareSerialNbr);
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
			com=createChildTest(childTest10, extent, com);
			order=createChildTest(childTest10, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest11=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest11);
			order=createChildTest(childTest11, extent, order);
			com=createChildTest(childTest11, extent, com);
			com.verifyCLECRequestAfterJobRun();
			order.navigateToOrderPage();
			
			ExtentTest childTest12=extent.startTest("Trigger the End PointRequest via RestAPI");
			test.appendChild(childTest12);
			soapRest=createChildTest(childTest12, extent, soapRest);
			soapRest.executeCPEendPointGetSrlNumber();
		
			ExtentTest childTest13=extent.startTest("Verify COM, SOM and CLEC Record counts");
			test.appendChild(childTest13);
			com=createChildTest(childTest13, extent, com);
			som=createChildTest(childTest13, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest14=extent.startTest("Navigate to Order History status");
			test.appendChild(childTest14);
			orderHistory=createChildTest(childTest14, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Execute_CPE_EndPoint_GetEquipment_BySerialNbr");
		} catch (Exception e) {
			log.error("Error in Execute_CPE_EndPoint_GetEquipment_BySerialNbr:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Execute_GetProduct_HighLevelEndpoint_TriplePlayXB6_X1Gateway(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Execute the Get Product High-Level Details By Customer Account endpoint for a triple play customer with XB6 converged hardware and X1 Gateway");
			log.debug("Entering Execute_GetProduct_HighLevelEndpoint_TriplePlayXB6_X1Gateway");
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
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));
			
			ExtentTest childTest=extent.startTest("Login and Open OE page");
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
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3=extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);
			
			ExtentTest childTest4=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware("Phone");
			order.selectConvergedHardware("Internet");

			ExtentTest childTest5=extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);
			order.selectTodo();
			
			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();
			//order.mocaFilterSelecttion("");
			
			ExtentTest childTest7=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectMethodOfConfirmation("Voice");
			//order.selectDefaultInstallationCheckBox();

			ExtentTest childTest8=extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order=createChildTest(childTest8, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/soapUICases.properties","accntNumTC145", accntNum);
			
			ExtentTest childTest10=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com=createChildTest(childTest10, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			order=createChildTest(childTest12, extent, order);
			com=createChildTest(childTest12, extent, com);
			com.verifyCLECRequestAfterJobRun();
			order.navigateToOrderPage();
			
			ExtentTest childTest13=extent.startTest("Trigger the End Point Get Product High-Level Details");
			test.appendChild(childTest13);
			soapRest=createChildTest(childTest13, extent, soapRest);
			soapRest.navigateToHitGetProductHighLevelDetails(accntNum);
		
			ExtentTest childTest14=extent.startTest("Verify COM, SOM and CLEC Record counts");
			test.appendChild(childTest14);
			com=createChildTest(childTest14, extent, com);
			som=createChildTest(childTest14, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest15=extent.startTest("Navigate to Order History status");
			test.appendChild(childTest15);
			orderHistory=createChildTest(childTest15, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Execute_GetProduct_HighLevelEndpoint_TriplePlayXB6_X1Gateway");
		} catch (Exception e) {
			log.error("Error in Execute_GetProduct_HighLevelEndpoint_TriplePlayXB6_X1Gateway:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void QuoteValidation_EndPoint_SSLServices_DPT_Hitron_Moxi_PickAndPay(Hashtable<String, String> data)throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Execute the My Account Quote Validation endpoint (sslservices/myaccount/quotedetails) for a triple play customer with Hitron hardware, Moxi gateway and DPT. The account must have Theme Packs and Pick n pay channels");
			log.debug("Entering QuoteValidation_EndPoint_SSLServices_DPT_Hitron_Moxi_PickAndPay");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String phoneHardware=data.get("Phone_hardware");
			String internetProduct=data.get("Internet_Product");
			String internetHardware=data.get("Internet_hardware");
			String tvProduct=data.get("TV_Product");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNbr=PhSrlNo;
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
			TVShawGatewaySrlNo=Utility.generateHardwareSerailNum(TVShawGatewaySrlNo);
			String tvShawGatewaySlrNbr=TVShawGatewaySrlNo;
			TVShawPortalSrlNo=Utility.generateHardwareSerailNum(TVShawPortalSrlNo);
			String tvShawPortalSlrNbr=TVShawPortalSrlNo;
			String[] hardwareType={"ShawGateway","ShawPortal"};
			String[] serialNumber={tvShawGatewaySlrNbr,tvShawPortalSlrNbr};
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest, extent, landing);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationDropType("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);

			ExtentTest childTest4=extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneSerialNbr, phoneHardware);
			
			ExtentTest childTest5=extent.startTest("Add Internet with Hitron Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			
			ExtentTest childTest6=extent.startTest("Add TV With Shaw Gateway HDPVR and Shaw Gateway Portal");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);
			
			ExtentTest childTest7=extent.startTest("Add Digital Channel Theme, Pick and Pay channels");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.addDigitalChannelSectionThemePick10Pack1();
			
			ExtentTest childTest8=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest8);
			order=createChildTest(childTest8, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			
			ExtentTest childTest9=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest10=extent.startTest("Review and Finish");
			test.appendChild(childTest10);
			order=createChildTest(childTest10, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest11=extent.startTest("Navigate To COM, SOM Orders");
			test.appendChild(childTest11);
			order=createChildTest(childTest11, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/soapUICases.properties","acctNumTC140", accntNum);
			
			ExtentTest childTest12=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest13=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest13);
			com=createChildTest(childTest13, extent, com);
			order=createChildTest(childTest13, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest14=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest14);
			com=createChildTest(childTest14, extent, com);
			com.verifyCLECRequestAfterJobRun();
			
			ExtentTest childTest15=extent.startTest("Trigger the Request via SoapUI");
			test.appendChild(childTest15);
			order=createChildTest(childTest15, extent, order);
			soapRest=createChildTest(childTest14, extent, soapRest);
			soapRest.navigateToMyAcQuoteValidateFibre("TC146",accntNum);
			order.navigateToOrderPage();
		
			ExtentTest childTest16=extent.startTest("Verify COM, SOM and CLEC Record counts");
			test.appendChild(childTest16);
			com=createChildTest(childTest16, extent, com);
			som=createChildTest(childTest16, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest17=extent.startTest("Navigate to Order History status");
			test.appendChild(childTest17);
			orderHistory=createChildTest(childTest17, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving QuoteValidation_EndPoint_SSLServices_DPT_Hitron_Moxi_PickAndPay");
		} catch (Exception e) {
			log.error("Error in QuoteValidation_EndPoint_SSLServices_DPT_Hitron_Moxi_PickAndPay:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Execute_QuoteValidation_EndPoint_TriplePlay_XB6_Bluesky(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Execute the Quote Validation endpoint for a triple play customer with XB6 converged hardware and Bluesky");
			log.debug("Entering Execute_QuoteValidation_EndPoint_TriplePlay_XB6_Bluesky");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
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

			ExtentTest childTest3=extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			
			ExtentTest childTest4=extent.startTest("Add Internet Product & IP Address");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);
			order.addEmailsIPAddressSimCardForInternet("", "IP Range");

			ExtentTest childTest5=extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);

			ExtentTest childTest6=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.selectConvergedHardware("Phone");
			order.selectTodoListCheckBox();

			ExtentTest childTest7=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			
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
			com=createChildTest(childTest12,extent,com);
			order=createChildTest(childTest12, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest13=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			com.verifyCLECRequestAfterJobRun();
			
			ExtentTest childTest14=extent.startTest("Trigger the SOAP call to get quote validation");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			soapRest=createChildTest(childTest14, extent, soapRest);
			soapRest.getOrderIdEndPointDetails();
			order.navigateToOrderPage();
			
			ExtentTest childTest15=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			som=createChildTest(childTest15,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest16=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest16);
			orderHistory=createChildTest(childTest16, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Execute_QuoteValidation_EndPoint_TriplePlay_XB6_Bluesky");
		} catch (Exception e) {
			log.error("Error in Execute_QuoteValidation_EndPoint_TriplePlay_XB6_Bluesky:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Execute_GetOrderDetails_ByCustAccnt_EndPoint_TriplePlay_XB6_X1Gateway(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Execute the Get Order Details By Customer Account endpoint for a triple play customer with XB6 converged hardware and X1 Gateway");
			log.debug("Entering Execute_GetOrderDetails_ByCustAccnt_EndPoint_TriplePlay_XB6_X1Gateway");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
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
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add TV With BlueSky Hardwares");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);

			ExtentTest childTest5=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch Window");
			order.selectTodoListCheckBox();

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

			ExtentTest childTest10=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			order=createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			com.verifyCLECRequestAfterJobRun();
			
			ExtentTest childTest13=extent.startTest("Trigger the Cust Account EndPoint Request via SoapAPI");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			soapRest=createChildTest(childTest13, extent, soapRest);
			soapRest.getOrderIdEndPointDetails();
			order.navigateToOrderPage();
			
			ExtentTest childTest14=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			som=createChildTest(childTest14,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest15=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest15);
			orderHistory=createChildTest(childTest15, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Execute_GetOrderDetails_ByCustAccnt_EndPoint_TriplePlay_XB6_X1Gateway");
		} catch (Exception e) {
			log.error("Error in Execute_GetOrderDetails_ByCustAccnt_EndPoint_TriplePlay_XB6_X1Gateway:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Execute_GetOrderList_ByCustAccnt_EndPoint_TriplePlay_XB6_X1Gateway(Hashtable<String, String> data)throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Execute the Get Order List by Customer Account endpoint for a triple play customer with XB6 converged hardware and X1 Gateway");
			log.debug("Entering Execute_GetOrderList_ByCustAccnt_EndPoint_TriplePlay_XB6_X1Gateway");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			TVBoxSrlNo=Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr=TVBoxSrlNo;
			TVPortalSrlNo=Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr=TVPortalSrlNo;
			String[] hardwareType={ "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber={ tvBoxSerialNbr, tvPortalSerialNbr };
			String convergedHardware=data.get("Converged_hardware");
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
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3=extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add TV With BlueSky Hardwares");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest5=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch Window");
			order.selectTodoListCheckBox();

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
			order.openCOMOrderPage("true", accntNum, "Yes");
			
			ExtentTest childTest10=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com=createChildTest(childTest10, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest13=extent.startTest("Trigger the Request via RestAPI with Method as POST");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			soapRest=createChildTest(childTest13, extent, soapRest);
			soapRest.getOrderIdEndPointDetails();
			order.navigateToOrderPage();

			ExtentTest childTest14=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest14);
			com=createChildTest(childTest14, extent, com);
			som=createChildTest(childTest14, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest15=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest15);
			orderHistory=createChildTest(childTest15, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Execute_GetOrderList_ByCustAccnt_EndPoint_TriplePlay_XB6_X1Gateway");
		} catch (Exception e) {
			log.error("Error in Execute_GetOrderList_ByCustAccnt_EndPoint_TriplePlay_XB6_X1Gateway:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Resumption_Collection_Suspended_TriplePlay_XB6_Bluesky(Hashtable<String, String> data)throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Verify resumption of a Collection Suspended Triple Play account with XB6-Converged and Blue-sky Device");
			log.debug("Entering Resumption_Collection_Suspended_TriplePlay_XB6_Bluesky");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			TVBoxSrlNo=Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr=TVBoxSrlNo;
			TVPortalSrlNo=Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr=TVPortalSrlNo;
			String[] hardwareType={"ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416"};
			String[] serialNumber={tvBoxSerialNbr, tvPortalSerialNbr};
			String convergedHardware=data.get("Converged_hardware");
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
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3=extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add TV With BlueSky Hardwares");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest5=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			order.selectTodoListCheckBox();
			//order.selectTodo();

			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			//order.mocaFilterSelecttion("");
			
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
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/soapUICases.properties", "accntNumTC150", accntNum);

			ExtentTest childTest10=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com=createChildTest(childTest10, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest13=extent.startTest("Trigger the Suspend Request via RestAPI");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			soapRest=createChildTest(childTest13, extent, soapRest);
			soapRest.navigateToHitCollectionSuspension("TC150", accntNum);
			order.navigateToOrderPage();
			
			ExtentTest childTest14=extent.startTest("Verify COM, SOM Initial Record Counts");
			test.appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			som=createChildTest(childTest14,extent,som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);
			
			ExtentTest childTest15=extent.startTest("Trigger the Resume Request via RestAPI");
			test.appendChild(childTest15);
			order=createChildTest(childTest15, extent, order);
			soapRest=createChildTest(childTest15, extent, soapRest);
			soapRest.navigateToHitCollectionResume("TC150", accntNum);
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest16);
			com=createChildTest(childTest16, extent, com);
			som=createChildTest(childTest16, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest17=extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest17);
			repo=createChildTest(childTest17,extent,repo);
			order=createChildTest(childTest17,extent,order);
			order.navigateToOrderValidation(29, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC150", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(30, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC150", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(31, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC150", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(25, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC150", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(28, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC150", 5, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			/*
			 * order.navigateToOrderValidation(26, "Yes", "No", "SOM");
			 * valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			 * softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC150", 6,
			 * valuesToPassForValidation),"Attributes are not validated successfully");
			 * order.navigateToOrderPage();
			 */
			
			ExtentTest childTest18=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest18);
			orderHistory=createChildTest(childTest18, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Resumption_Collection_Suspended_TriplePlay_XB6_Bluesky");
		} catch (Exception e) {
			log.error("Error in Resumption_Collection_Suspended_TriplePlay_XB6_Bluesky:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Immediate_Suspend_EndPoint_TriplePlay_XB6_X1Gateway(Hashtable<String, String> data)throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Execute the Immediate Suspend endpoint for triple play customer with XB6 converged hardware and X1 Gateway");
			log.debug("Entering Immediate_Suspend_EndPoint_TriplePlay_XB6_X1Gateway");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			TVBoxSrlNo=Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr=TVBoxSrlNo;
			TVPortalSrlNo=Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr=TVPortalSrlNo;
			String[] hardwareType={"ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416"};
			String[] serialNumber={tvBoxSerialNbr, tvPortalSerialNbr};
			String convergedHardware=data.get("Converged_hardware");
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
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3=extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.selectConvergedHardware("Internet");
			//order.deleteHardware("Switch Window");
			
			ExtentTest childTest4=extent.startTest("Add TV With BlueSky Hardwares");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			//order.selectTodoListCheckBox();
			order.selectTodo();

			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();
			//order.mocaFilterSelecttion("");
			
			ExtentTest childTest7=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest8=extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order=createChildTest(childTest8, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/soapUICases.properties", "accntNumTC151", accntNum);

			ExtentTest childTest10=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com=createChildTest(childTest10, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest13=extent.startTest("Trigger the Immediate Suspend Request via RestAPI");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			soapRest=createChildTest(childTest13, extent, soapRest);
			soapRest.navigateToHitCollectionSuspension("TC151", accntNum);
			order.navigateToOrderPage();
			
			ExtentTest childTest14=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest14);
			com=createChildTest(childTest14, extent, com);
			som=createChildTest(childTest14, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest15=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest15);
			orderHistory=createChildTest(childTest15, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Immediate_Suspend_EndPoint_TriplePlay_XB6_X1Gateway");
		} catch (Exception e) {
			log.error("Error in Immediate_Suspend_EndPoint_TriplePlay_XB6_X1Gateway:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void OMSends_PriorityHPSA_ServicesDisconnect_Contracted_DueTo_NonPay(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Verify that OM sends Priority to HPESA when services are disconnected for an account which has contracted services due to non pay");
			log.debug("Entering OMSends_PriorityHPSA_ServicesDisconnect_Contracted_DueTo_NonPay");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String phoneHardware=data.get("Phone_hardware");
			String internetProduct=data.get("Internet_Product");
			String internetHardware=data.get("Internet_hardware");
			String tvProduct=data.get("TV_Product");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNbr=PhSrlNo;
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
			TVDctSrlNo=Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr=TVDctSrlNo;
			String[] hardwareType={"DCT700"};
			String[] serialNumber={tvHardwareSerialNbr};
			String techAppointment=data.get( "Tech_Appointment" );
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest, extent, landing);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);

			ExtentTest childTest4=extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneSerialNbr, phoneHardware);
			
			ExtentTest childTest5=extent.startTest("Add Internet with Hitron Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);

			ExtentTest childTest6=extent.startTest("Add TV with TV Hardware");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest7=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			
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
			com=createChildTest(childTest12,extent,com);
			order=createChildTest(childTest12, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest13=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			com.verifyCLECRequestAfterJobRun();
			
			ExtentTest childTest14=extent.startTest("Trigger the Services Disconnected Request via RestAPI");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			soapRest=createChildTest(childTest14, extent, soapRest);
			soapRest.getservicesDisconnecteddueToNonPay_TC152(accntNum);
			order.navigateToOrderPage();

			ExtentTest childTest15=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			som=createChildTest(childTest15,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest16=extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest16);
			som=createChildTest(childTest16,extent,som);
			repo=createChildTest(childTest16,extent,repo);
			order=createChildTest(childTest16,extent,order);
			order.navigateToOrderValidation(33, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC151", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC151", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(34, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC151", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(34, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC151", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest17=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest17);
			orderHistory=createChildTest(childTest17, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving OMSends_PriorityHPSA_ServicesDisconnect_Contracted_DueTo_NonPay");
		} catch (Exception e) {
			log.error("Error in OMSends_PriorityHPSA_ServicesDisconnect_Contracted_DueTo_NonPay:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void CLECOPS_Skipped_ForPortedNbr_SwitchedWiring_Status_NewOrder(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","CLEC Ops Confirmation task should be skipped for Ported no with Port Switched wiring status for a new order");
			log.debug("Entering CLECOPS_Skipped_ForPortedNbr_SwitchedWiring_Status_NewOrder");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String phoneHardware=data.get("Phone_hardware");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNbr=PhSrlNo;
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest, extent, landing);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationDropType("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");
			
			ExtentTest childTest3=extent.startTest("Add Port Phone Number");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);    
			order.addServicesAndFeacturesTab();
			String portPhoneNbr=order.portPhoneNumber("Phone Required", "", phoneProduct, "", "No", "No");
			
			ExtentTest childTest4=extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addPhoneHardwareWithoutSlNo(phoneHardware, "1stHardware");
			
			ExtentTest childTest5=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.selectInstallationOption("", techAppointment);
			order.techAppoinmentYes("", "");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();
			
			
			ExtentTest childTest6=extent.startTest("Review and Finish");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest7=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			String accntNum=order.getAccountNumber();
			String orderNum=order.getOrderNumber();
			order.openCOMOrderPage("true",accntNum, "Yes");
			
			ExtentTest childTest8=extent.startTest("Send the Request with wiring status details with Method as PUT");
			test.appendChild(childTest8);
			soapRest=createChildTest(childTest8, extent, soapRest);
			order=createChildTest(childTest8,extent,order);
			soapRest.navigateToHitWiringStatus("TC153", portPhoneNbr, orderNum);
			order.navigateToOrderPage();
			
			ExtentTest childTest9=extent.startTest("Validate wiring status in phone line CFS order");
			test.appendChild(childTest9);
			com=createChildTest(childTest9, extent, com);
			order=createChildTest(childTest9, extent, order);
			com.navigateToCheckWiringStatusinPhoneCFS("One");
			order.navigateToOrderPage();
			
			ExtentTest childTest10=extent.startTest("Wait for effective date mark finish task");
			test.appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			order=createChildTest(childTest10,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest11=extent.startTest("Wait for provisioning, Send serial Nbr for Phone line");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			order=createChildTest(childTest11,extent,order);
			order.navigateToOrderValidation(5, "No", "No", "COM");
			com.sendSerialNbrTask(phoneSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("Navigate To LSC Response Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			order=createChildTest(childTest12, extent, order);
			com.navigateToJobMonitorURL("LSC");
			order.navigateToOrderPage();
			
			ExtentTest childTest13=extent.startTest("Login to CLECOPS and validate Create user task");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			landing=createChildTest(childTest13, extent, landing);
			order.navigateToCLECOPSUser("true", "");
			landing.loggingOffCLECOPSUser();
			landing.Login();
			order.navigateToOrderPage();
			
			ExtentTest childTest14=extent.startTest("Wait for effective date mark finish task");
			test.appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			order=createChildTest(childTest14,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
		
			ExtentTest childTest15=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			som=createChildTest(childTest15,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest16=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest16);
			orderHistory=createChildTest(childTest16, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving CLECOPS_Skipped_ForPortedNbr_SwitchedWiring_Status_NewOrder");
		} catch (Exception e) {
			log.error("Error in CLECOPS_Skipped_ForPortedNbr_SwitchedWiring_Status_NewOrder:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void DualJack_WiredToPreviousProvider_ReportGen_ActivePhoneLine(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Dual-jacked/Wired to Previous Provider Report generation for an active phone line");
			log.debug("Entering DualJack_WiredToPreviousProvider_ReportGen_ActivePhoneLine");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String phoneHardware=data.get("Phone_hardware");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNbr=PhSrlNo;
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

			ExtentTest childTest3=extent.startTest("Add Port Phone Number");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);    
			order.addServicesAndFeacturesTab();
			String portPhoneNbr=order.portPhoneNumber("Phone Required", "", phoneProduct, "", "No", "No");
			
			ExtentTest childTest4=extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneSerialNbr, phoneHardware);
			//order.selectTodo();
			
			ExtentTest childTest7=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("Yes", "");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();
			
			ExtentTest childTest8=extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			String accntNum=order.getAccountNumber();
			String orderNum=order.getOrderNumber();
			order.openCOMOrderPage("true",accntNum, "Yes");
			Utility.setValueInPropertyFile("config/soapUICases.properties","orderNumTC155", orderNum);
			
			ExtentTest childTest10=extent.startTest("Navigate To Verify SVG Diagram");
			test.appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			order=createChildTest(childTest10,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("");
			
			ExtentTest childTest11=extent.startTest("Send the request with wiring status details");
			test.appendChild(childTest11);
			soapRest=createChildTest(childTest11, extent, soapRest);
			order=createChildTest(childTest11,extent,order);
			soapRest.navigateToHitWiringStatus("TC155", portPhoneNbr, orderNum);
			order.navigateToOrderPage();
			
			ExtentTest childTest12=extent.startTest("Validate wiring status in phone line CFS order");
			test.appendChild(childTest12);
			order=createChildTest(childTest12,extent,order);
			com=createChildTest(childTest12, extent, com);
			com.navigateToCheckWiringStatusinPhoneCFS("One");
			order.navigateToOrderPage();
			
			ExtentTest childTest13=extent.startTest("Wait for Effective date Mark finish Task");
			test.appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			order=createChildTest(childTest13,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest14=extent.startTest("Wait for provisioning, Send serial Nbr for Phone line");
			test.appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			order=createChildTest(childTest14,extent,order);
			order.navigateToOrderValidation(5, "No", "No", "COM");
			com.sendSerialNbrTask(phoneSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest15=extent.startTest("Navigate To LSC Response Job Run");
			test.appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			order=createChildTest(childTest15, extent, order);
			com.navigateToJobMonitorURL("LSC");
			order.navigateToOrderPage();

			ExtentTest childTest16=extent.startTest("Navigate To CLEC Ops confirmation task");
			test.appendChild(childTest16);
			order=createChildTest(childTest16, extent, order);
			landing=createChildTest(childTest16, extent, landing);
			order.navigateToCLECOPSUser("true", "Yes");
			landing.loggingOffCLECOPSUser();
			landing.Login();
			order.navigateToOrderPage();
			
				
			ExtentTest childTest17=extent.startTest("Wait for Tech cnfm Mark Finish Task");
			test.appendChild(childTest17);
			com=createChildTest(childTest17, extent, com);
			order=createChildTest(childTest17, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest18=extent.startTest("Navigate To BLIF Response Job Run");
			test.appendChild(childTest18);
			com=createChildTest(childTest18,extent,com);
			order=createChildTest(childTest18, extent, order);
			com.navigateToJobMonitorURL("BLIF");
			
			ExtentTest childTest19=extent.startTest("Navigate To E911 Response Job Run");
			test.appendChild(childTest19);
			com=createChildTest(childTest19,extent,com);
			order=createChildTest(childTest19, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			
			ExtentTest childTest20=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest20);
			com=createChildTest(childTest20,extent,com);
			som=createChildTest(childTest20,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			
			ExtentTest childTest21=extent.startTest("Navigate To Dual Jacked Wired to Previous Provider Report");
			test.appendChild(childTest21);
			com=createChildTest(childTest21, extent, com);
			com.navigateToDualJackedWiredtoPreviousProviderReport();
			
			ExtentTest childTest22=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest22);
			orderHistory=createChildTest(childTest22, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving DualJack_WiredToPreviousProvider_ReportGen_ActivePhoneLine");
		} catch (Exception e) {
			log.error("Error in DualJack_WiredToPreviousProvider_ReportGen_ActivePhoneLine:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Execute_NPD_EndPoint_TriplePlay_XB6_X1Gateway(Hashtable<String, String> data)throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Execute the NPD endpoint for triple play customer with XB6 converged hardware and X1 Gateway");
			log.debug("Entering Execute_NPD_EndPoint_TriplePlay_XB6_X1Gateway");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			TVBoxSrlNo=Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr=TVBoxSrlNo;
			TVPortalSrlNo=Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr=TVPortalSrlNo;
			String[] hardwareType={ "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber={ tvBoxSerialNbr, tvPortalSerialNbr };
			String convergedHardware=data.get("Converged_hardware");
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
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3=extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add TV With BlueSky Hardwares");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest5=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			order.selectTodoListCheckBox();

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
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/soapUICases.properties", "accntNumTC156", accntNum);

			ExtentTest childTest10=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com=createChildTest(childTest10, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest13=extent.startTest("Trigger the Request via RestAPI with Method as POST");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			soapRest=createChildTest(childTest13, extent, soapRest);
			soapRest.getOrderIdEndPointDetails();
			order.navigateToOrderPage();

			ExtentTest childTest14=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest14);
			com=createChildTest(childTest14, extent, com);
			som=createChildTest(childTest14, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest15=extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest15);
			order=createChildTest(childTest15,extent,order);
			order.navigateToCustOrderPage("false",accntNum);

			ExtentTest childTest16=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest16);
			orderHistory=createChildTest(childTest16, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Execute_NPD_EndPoint_TriplePlay_XB6_X1Gateway");
		} catch (Exception e) {
			log.error("Error in Execute_NPD_EndPoint_TriplePlay_XB6_X1Gateway:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Immediate_Resume_EndPoint_Tripleplay_XB6_X1Gateway(Hashtable<String, String> data)throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Execute the Immediate Resume endpoint for triple play customer with XB6 converged hardware and X1 Gateway");
			log.debug("Entering Immediate_Resume_EndPoint_Tripleplay_XB6_X1Gateway");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			TVBoxSrlNo=Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr=TVBoxSrlNo;
			TVPortalSrlNo=Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr=TVPortalSrlNo;
			String[] hardwareType={ "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber={ tvBoxSerialNbr, tvPortalSerialNbr };
			String convergedHardware=data.get("Converged_hardware");
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
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest2=extent.startTest("Add Phone Product");
			test.appendChild(childTest2);
			order=createChildTest(childTest2, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			
			ExtentTest childTest3=extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServiceInternet(internetProduct);
			
			ExtentTest childTest4=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.selectTodoListCheckBox();

			ExtentTest childTest5=extent.startTest("Add TV With BlueSky Hardwares");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			order.selectTodo();

			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			//order.mocaFilterSelecttion("");
						
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
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest10=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com=createChildTest(childTest10, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest13=extent.startTest("Trigger the Immediate Suspend via RestAPI");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			soapRest=createChildTest(childTest13, extent, soapRest);
			soapRest.navigateToHitCollectionSuspension("TC157", accntNum);
			order.navigateToOrderPage();
			
			ExtentTest childTest14=extent.startTest("Verify COM, SOM Initial Record Counts");
			test.appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			som=createChildTest(childTest14,extent,som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);
			
			ExtentTest childTest15=extent.startTest("Trigger the Immediate Resume via RestAPIT");
			test.appendChild(childTest15);
			order=createChildTest(childTest15, extent, order);
			soapRest=createChildTest(childTest15, extent, soapRest);
			soapRest.navigateToHitCollectionResume("TC157", accntNum);
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest16);
			com=createChildTest(childTest16, extent, com);
			som=createChildTest(childTest16, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest17=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest17);
			orderHistory=createChildTest(childTest17, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Immediate_Resume_EndPoint_Tripleplay_XB6_X1Gateway");
		} catch (Exception e) {
			log.error("Error in Immediate_Resume_EndPoint_Tripleplay_XB6_X1Gateway:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void GetQuote_Details_EndPoint_TriplePlay_XB6_X1Gateway(Hashtable<String, String> data)throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Execute the Get Quote Details by Customer Account endpoint for a triple play customer with XB6 converged hardware and X1 Gateway");
			log.debug("Entering GetQuote_Details_EndPoint_TriplePlay_XB6_X1Gateway");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			TVBoxSrlNo=Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr=TVBoxSrlNo;
			TVPortalSrlNo=Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr=TVPortalSrlNo;
			String[] hardwareType={ "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber={ tvBoxSerialNbr, tvPortalSerialNbr };
			String convergedHardware=data.get("Converged_hardware");
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
			order.customerInformation("Residential", "Yes");
			
			ExtentTest childTest2=extent.startTest("Add Phone Product");
			test.appendChild(childTest2);
			order=createChildTest(childTest2, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			
			ExtentTest childTest3=extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServiceInternet(internetProduct);
		
			ExtentTest childTest5=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			
			ExtentTest childTest4=extent.startTest("Add TV With BlueSky Hardwares");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			order.selectTodoListCheckBox();

			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			//order.mocaFilterSelecttion("");
			
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
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest10=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest10);
			com=createChildTest(childTest10, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			com.verifyCLECRequestAfterJobRun();
			
			ExtentTest childTest13=extent.startTest("Trigger Get Quote Details by Customer Account endpoint");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			soapRest=createChildTest(childTest13, extent, soapRest);
			//soapRest.triggerSoapRequests("TC158", accntNum);
			order.navigateToOrderPage();
			
			ExtentTest childTest14=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest14);
			com=createChildTest(childTest14, extent, com);
			som=createChildTest(childTest14, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest15=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest15);
			orderHistory=createChildTest(childTest15, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving GetQuote_Details_EndPoint_TriplePlay_XB6_X1Gateway");
		} catch (Exception e) {
			log.error("Error in GetQuote_Details_EndPoint_TriplePlay_XB6_X1Gateway:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Execute_CustServiceAgreement_Generate_ServiceAgreement_TriplePay(Hashtable<String, String> data)throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Execute the 'Customer Service Agreement' endpoint to generate a Service agreement for a Triple Play account");
			log.debug("Entering Execute_CustServiceAgreement_Generate_ServiceAgreement_TriplePay");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			TVBoxSrlNo=Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr=TVBoxSrlNo;
			TVPortalSrlNo=Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr=TVPortalSrlNo;
			String[] hardwareType={ "ShawBlueSkyTVbox526", "ShawBlueSkyTVportal416" };
			String[] serialNumber={ tvBoxSerialNbr, tvPortalSerialNbr };
			String convergedHardware=data.get("Converged_hardware");
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
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3=extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add TV With BlueSky Hardwares");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest5=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch Window");
			//order.selectTodoListCheckBox();
			order.selectTodo();
			
			ExtentTest childTest6=extent.startTest("Add Promotions");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.addPromotionsServiceAgreement("Personal Phone", "Fibre+ 75", "Small TV Prebuilt", "");

			ExtentTest childTest7=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order=createChildTest(childTest7, extent, order);
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
			order=createChildTest(childTest9, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest10);
			order=createChildTest(childTest10, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			Utility.setValueInPropertyFile("config/soapUICases.properties", "accntNumTC159", accntNum);

			ExtentTest childTest11=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest12=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			order=createChildTest(childTest12, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest13=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest13);
			com=createChildTest(childTest13, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest14=extent.startTest("Trigger GenerateServiceAgreement request through RestAPI With Method as POST");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			soapRest=createChildTest(childTest14, extent, soapRest);
			soapRest.triggerGetOrderIdEndPointDetails(accntNum);
			order.navigateToOrderPage();
			
			ExtentTest childTest15=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest15);
			com=createChildTest(childTest15, extent, com);
			som=createChildTest(childTest15, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest16=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest16);
			orderHistory=createChildTest(childTest16, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Execute_CustServiceAgreement_Generate_ServiceAgreement_TriplePay");
		} catch (Exception e) {
			log.error("Error in Execute_CustServiceAgreement_Generate_ServiceAgreement_TriplePay:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	public static void main (String args[])
	{
		InputStream log4j=SoupUIRestAPICases.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
        PropertyConfigurator.configure(log4j);
	}
}	

