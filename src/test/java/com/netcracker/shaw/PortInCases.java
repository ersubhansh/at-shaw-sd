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

public class PortInCases extends SeleniumTestUp {

	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	ValidateReport repo=null;
	OrderHistoryPage orderHistory=null;
	SoapUIRestAPIpage soapRest=null;
	SoftAssert softAssert=new SoftAssert();
	ArrayList<String> valuesToPassForValidation=new ArrayList<String>();

	Logger log=Logger.getLogger(PortInCases.class);
	
	@Test(dataProvider = "getTestData", description = "Golden Suite")
	@SuppressWarnings(value = { "rawtypes" })
	public void Port_PhoneNumber_DistinctiveRing(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Activate Phone line with Port-in TN and Distinctive Ring with a Port-in TN on a converged device when LSC is not received, Life Line Alarm = YES");
			log.debug("Entering Port_PhoneNumber_DistinctiveRing");
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
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1,extent,order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Port Phone Number with life-line Alarm as Yes");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();  
			String portPhoneNumber=order.portPhoneNumber("Phone Required", "", phoneProduct, "", "Yes", "No");
			
			ExtentTest childTest4=extent.startTest("Port Distinctive Ring Phone Number with life line Alarm as Yes");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			String distinctivePhoneNbr=order.portDistinctiveNumber();

			ExtentTest childTest5=extent.startTest("Add Internet Product");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order); 
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch Window");
			
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

			ExtentTest childTest10=extent.startTest("Navigate To COM,SOM and CLEC orders");
			test.appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("false",accntNum, "Yes");

			ExtentTest childTest11=extent.startTest("Wait for Effective Date Mark Finish Task");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			order=createChildTest(childTest11,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			com.checkEffectiveDateStatus();
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("Wait for All LSC's Step Over");
		    test.appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			order=createChildTest(childTest12,extent,order);
			order.navigateToOrderValidation(24, "No", "No", "COM");
			com.navigateToWaitForAllLSCsTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest13=extent.startTest("Send serial number New Phone Hardware CFS Order");
			test.appendChild(childTest13);
			com=createChildTest(childTest13, extent, com);
			som=createChildTest(childTest13, extent, som);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();
			
		    ExtentTest childTest14=extent.startTest("Navigate To LSC Job Run");
			test.appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			com.navigateToJobMonitorURL("LSC");
			order.navigateToOrderPage();
			
			ExtentTest childTest15=extent.startTest("Navigate to CLEC OPS task");
			test.appendChild(childTest15);
			order=createChildTest(childTest15,extent,order);
			landing=createChildTest(childTest15,extent,landing);
			order.navigateToCLECOPSUser("false", "Yes");
			landing.loggingOffCLECOPSUser();
			landing.Login();
			order.navigateToOrderPage();

			ExtentTest childTest16 = extent.startTest("Wait for tech confirm Mark Finish Task");
			test.appendChild(childTest16);
			com = createChildTest(childTest16, extent, com);
			order = createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest17 = extent.startTest("Navigate to CLEC OPS task");
			test.appendChild(childTest17);
			landing = createChildTest(childTest17, extent, landing);
			order = createChildTest(childTest17, extent, order);
			order.navigateToCLECOPSUser("false", "Yes");
			landing.loggingOffCLECOPSUser();
			landing.Login();
			order.navigateToOrderPage();
			
			ExtentTest childTest18=extent.startTest("Distinctive CFS NPAC confirmation Mark Finish task");
			test.appendChild(childTest18);
			com=createChildTest(childTest18,extent,com);
			order=createChildTest(childTest18,extent,order);
			order.navigateToOrderValidation(23, "No", "No", "COM");
			com.setMarkFinishedTask();
			
			ExtentTest childTest19=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest19);
			com=createChildTest(childTest19,extent,com);
			order=createChildTest(childTest19, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			
			ExtentTest childTest20=extent.startTest("Navigate To BLIF Job Run");
			test.appendChild(childTest20);
			com=createChildTest(childTest20,extent,com);
			order=createChildTest(childTest20,extent,order);
			com.navigateToJobMonitorURL("BLIF");   
			order.navigateToOrderPage();
		
			ExtentTest childTest21=extent.startTest( "Navigate to New Phone product CFS order");
			test.appendChild(childTest21);
			com=createChildTest(childTest21,extent,com);
			order=createChildTest(childTest21,extent,order);            
			order.navigateToOrderValidation(4, "No", "No", "COM");
			com.navigateToServiceInformationTab();  
			Assert.assertTrue(com.portOptionDisplayed(),"Port Option is displaying for New Phone CFS order");
			
			ExtentTest childTest22=extent.startTest("Navigate To BLIF Job Run");
			test.appendChild(childTest22);
			com=createChildTest(childTest22,extent,com);
			order=createChildTest(childTest22,extent,order);
			com.navigateToJobMonitorURL("BLIF"); 
			
			ExtentTest childTest23=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest23);
			com=createChildTest(childTest23,extent,com);
			order=createChildTest(childTest23,extent,order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			com.verifyRecordsCountInCLECOrder(clecOrderCount); 
			
			ExtentTest childTest24=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest24);
			com=createChildTest(childTest24,extent,com);
			som=createChildTest(childTest24,extent,som);
			order.navigateToOrderPage();
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest25=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest25);
			repo=createChildTest(childTest25,extent,repo);
			order=createChildTest(childTest25,extent,order);
			order.navigateToOrderValidation(18, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, portPhoneNumber));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC13", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(1, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum,convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC13", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest26=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest26);
			repo=createChildTest(childTest26,extent,repo);
			order=createChildTest(childTest26,extent,order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");	
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr,distinctivePhoneNbr, portPhoneNumber));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC13", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();    
			
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr,distinctivePhoneNbr, portPhoneNumber));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC13", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest27=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest27);
			orderHistory=createChildTest(childTest27,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Port_PhoneNumber_DistinctiveRing");
		} catch (Exception e) {
			log.error("Error in Port_PhoneNumber_DistinctiveRing:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test (dataProvider="getTestData", description="Priority-1")
	@SuppressWarnings(value={"rawtypes"})
	public void Activate_Phone_Converged_NativeTN_PortedDR(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Activate Phone on converged device with native TN and Ported DR");
			log.debug("Entering Activate_Phone_Converged_NativeTN_PortedDR");
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
			
			ExtentTest childTest4=extent.startTest("Add Port Distinctive Ring Phone Number");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			String distinctivePhoneNbr=order.portDistinctiveNumber("No");

			ExtentTest childTest5=extent.startTest("Add Internet Product");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addServiceInternet(internetProduct);
		
			ExtentTest childTest6 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			
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
			
			ExtentTest childTest11=extent.startTest("Wait for Effective date Mark Finish Task");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			order=createChildTest(childTest11,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			//com.setMarkFinishedTask();
			com.setMarkFinishedSpecificTask("Wait for effective date");
			com.checkEffectiveDateStatus();
			order.navigateToOrderPage();
			
			ExtentTest childTest12=extent.startTest("Send Serial Number for Phone & Internet");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			order=createChildTest(childTest12, extent, order);
			som=createChildTest(childTest12, extent, som);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();
			som.navigateToSOMOrder();
			
			ExtentTest childTest13=extent.startTest("Wait for Provisioning Mark Finished Task");
			test.appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			order=createChildTest(childTest13,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest14=extent.startTest("Navigate To LSC Job Run");
			test.appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			com.navigateToCLECOrder();
			com.navigateToJobMonitorURL("LSC");
			
			ExtentTest childTest15=extent.startTest("Wait for LSC Order finish Task");
			test.appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			order=createChildTest(childTest15,extent,order);
			landing=createChildTest(childTest15,extent,landing);
			order.navigateToCLECOPSUser("false", "Yes");
			landing.loggingOffCLECOPSUser();
			landing.Login();
			order.navigateToOrderPage();
			
			/*ExtentTest childTest16=extent.startTest("Wait for NPAC Activation Confirmation");
			test.appendChild(childTest16);
			com=createChildTest(childTest16,extent,com);
			order=createChildTest(childTest16,extent,order);
			order.navigateToOrderValidation(23, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();*/
			
			ExtentTest childTest17=extent.startTest("Navigate To BLIF Job Run");
			test.appendChild(childTest17);
			com=createChildTest(childTest17,extent,com);
			order=createChildTest(childTest17,extent,order);
			com.navigateToCLECOrder();
			com.navigateToJobMonitorURL("BLIF");
			order.navigateToOrderPage();
		
			ExtentTest childTest18=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest18);
			com=createChildTest(childTest18,extent,com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest19=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest19);
			com=createChildTest(childTest19,extent,com);
			order=createChildTest(childTest19,extent,order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest20=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest20);
			com=createChildTest(childTest20,extent,com);
			com.verifyCLECRequestAfterJobRun();
			
			ExtentTest childTest21=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest21);
			com=createChildTest(childTest21,extent,com);
			som=createChildTest(childTest21,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest22=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest22);
			repo=createChildTest(childTest22,extent,repo);
			order=createChildTest(childTest22,extent,order);
			order.navigateToOrderValidation(3, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, distinctivePhoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC31", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC31", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(18, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, distinctivePhoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC31", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
		
			ExtentTest childTest23=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest23);
			repo=createChildTest(childTest23,extent,repo);
			order=createChildTest(childTest23,extent,order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum,convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC31", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum,convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC31", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest24=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest24);
			orderHistory=createChildTest(childTest24,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Activate_Phone_Converged_NativeTN_PortedDR");
		} catch (Exception e) {
			log.error("Error in Activate_Phone_Converged_NativeTN_PortedDR:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test (dataProvider="getTestData", description="Priority-1")
	@SuppressWarnings(value={"rawtypes"})
	public void Activate_Ph_PortinTN_DRing_LSCNotReceived_AlarmNO(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Activate Phone line with Port-in TN and Distinctive Ring with a native TN on a Converged when LSC is not received, Life Line Alarm = NO");
			log.debug("Entering Activate_Ph_PortinTN_DRing_LSCNotReceived_AlarmNO");
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

			ExtentTest childTest3=extent.startTest("Add Port Phone Number");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);    
			order.addServicesAndFeacturesTab();
			String portPhoneNbr=order.portPhoneNumber("Phone Required", "", phoneProduct, "", "No", "No");
			order.selectVoiceMail();
		
			ExtentTest childTest4=extent.startTest("Add Internet");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addServiceInternet(internetProduct);
			
			ExtentTest childTest5=extent.startTest("Add Distinctive Ring with NativeTN Phone Number");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addDistinctiveRing("One", "3 short rings");

			ExtentTest childTest6=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);

			ExtentTest childTest7=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("Yes", "");
			
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
			order.openCOMOrderPage("false",accntNum, "Yes");

			ExtentTest childTest11=extent.startTest("Wait for Effective date Mark Finish Task");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			order=createChildTest(childTest11,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			com.checkEffectiveDateStatus();
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("Navigate To New Converged hardware CFS Order");
			test.appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			order=createChildTest(childTest12,extent,order);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest13=extent.startTest("Navigate To LSC Job Run");
			test.appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			com.navigateToJobMonitorURL("LSC");  

			ExtentTest childTest14 = extent.startTest("Wait for LSC Order finish Task");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			order = createChildTest(childTest14, extent, order);
			landing = createChildTest(childTest14, extent, landing);
			order.navigateToCLECOPSUser("true", "Yes");
			landing.loggingOffCLECOPSUser();
			landing.Login();
			order.navigateToOrderPage();

			ExtentTest childTest15 = extent.startTest("Wait for Tech Confirm Mark Finished Task");
			test.appendChild(childTest15);
			com = createChildTest(childTest15, extent, com);
			order = createChildTest(childTest15, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("");
//			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			 

			ExtentTest childTest16=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest16);
			com=createChildTest(childTest16,extent,com);
			com.navigateToCLECOrder();
			order=createChildTest(childTest16,extent,order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
		
			ExtentTest childTest17=extent.startTest("Navigate To BLIF Job Run");
			test.appendChild(childTest17);
			com=createChildTest(childTest17,extent,com);
			order=createChildTest(childTest17,extent,order);
			com.navigateToJobMonitorURL("BLIF");
			order.navigateToOrderPage();
			
			ExtentTest childTest19=extent.startTest("Verify COM, SOM and CLEC Record counts");
			test.appendChild(childTest19);
			com=createChildTest(childTest19,extent,com);
			som=createChildTest(childTest19,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest20=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest20);
			repo=createChildTest(childTest20,extent,repo);
			order=createChildTest(childTest20,extent,order);
			order.navigateToOrderValidation(3, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, portPhoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC36", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(18, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, portPhoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC36", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest21=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest21);
			repo=createChildTest(childTest21,extent,repo);
			order=createChildTest(childTest21,extent,order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");	
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC36", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
		
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr, internetProduct));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC36", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest22=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest22);
			orderHistory=createChildTest(childTest22,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Activate_Ph_PortinTN_DRing_LSCNotReceived_AlarmNO");
		} catch (Exception e) {
			log.error("Error in Activate_Ph_PortinTN_DRing_LSCNotReceived_AlarmNO: " + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test (dataProvider="getTestData", description="Priority-1")
	@SuppressWarnings(value={"rawtypes"})
	public void ActivatePh_PortIN_TNs_TechRetry_LSC_came_beforeTR(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Activate Phone line with Port-in TNs with Tech Retry (LSC came before TR)");
			log.debug("Entering ActivatePh_PortIN_TNs_TechRetry_LSC_came_beforeTR");
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
			order.navigateToChangeToggleValueToFail("Phone");
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

			ExtentTest childTest3=extent.startTest("Add Port Phone Number");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);   
			order.addServicesAndFeacturesTab();
			String portPhoneNbr=order.portPhoneNumber("Phone Required", "", phoneProduct, "", "Yes", "No");
			order.selectVoiceMail();

			ExtentTest childTest4=extent.startTest("Add Internet Product");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order); 
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);

			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("", "");
			
			ExtentTest childTest7=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectMethodOfConfirmation("Voice");
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
			order.openCOMOrderPage("true",accntNum, "Yes");
			
			ExtentTest childTest10=extent.startTest("Wait for Effective date mark Finish Task");
			test.appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			order=createChildTest(childTest10,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest11=extent.startTest("Wait for Provisioning mark Finish Task");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			order=createChildTest(childTest11,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest12=extent.startTest("Wait for All LSC's Step Over");
			test.appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			order=createChildTest(childTest12,extent,order);
			order.navigateToOrderValidation(24, "No", "No", "COM");
			com.navigateToWaitForAllLSCsTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest13=extent.startTest("Navigate to Stub URL Success");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			order.navigateToOeStubToPassServices("Phone");
			
			ExtentTest childTest14=extent.startTest("Navigate To LSC Job Run");
			test.appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			com.navigateToJobMonitorURL("LSC");
			order.openCOMOrderPage("false",accntNum, "Yes");
			
			ExtentTest childTest15=extent.startTest("Navigate To New Converged hardware CFS Order");
			test.appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			order=createChildTest(childTest15,extent,order);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Navigate To New Converged hardware CFS Order");
			test.appendChild(childTest16);
			com=createChildTest(childTest16,extent,com);
			order=createChildTest(childTest16,extent,order);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();
			
			 ExtentTest childTest17=extent.startTest("Wait for NPAC Activation Confirmation");
			test.appendChild(childTest17);
			com=createChildTest(childTest17,extent,com);
			order.navigateToOrderValidation(4, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest18=extent.startTest("Navigate To BLIF Job Run");
			test.appendChild(childTest18);
			com=createChildTest(childTest18,extent,com);
			order=createChildTest(childTest18,extent,order);
			com.navigateToJobMonitorURL("BLIF");
			order.navigateToOrderPage();
			
			/*
			 * ExtentTest
			 * childTest19=extent.startTest("Wait for Tech Confirm Mark Finished Task");
			 * test.appendChild(childTest19); com=createChildTest(childTest19,extent,com);
			 * order=createChildTest(childTest19,extent,order);
			 * order.navigateToOrderValidation(1, "No", "No", "COM");
			 * com.verifySVGDiagram(""); com.setMarkFinishedTask();
			 * order.navigateToOrderPage();
			 */
		
			ExtentTest childTest20=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest20);
			com=createChildTest(childTest20,extent,com);
			order=createChildTest(childTest20,extent,order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			
			ExtentTest childTest21=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest21);
			com=createChildTest(childTest21,extent,com);
			com.navigateToCLECOrder();
			
			ExtentTest childTest22=extent.startTest("Navigate To BLIF Job Run");
			test.appendChild(childTest22);
			com=createChildTest(childTest22,extent,com);
			order=createChildTest(childTest22,extent,order);
			com.navigateToJobMonitorURL("BLIF");
			order.navigateToOrderPage();
					
			ExtentTest childTest23=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest23);
			com=createChildTest(childTest23,extent,com);
			som=createChildTest(childTest23,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest24=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest24);
			repo=createChildTest(childTest24,extent,repo);
			order=createChildTest(childTest24,extent,order);
			order.navigateToOrderValidation(3, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, portPhoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC37", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, portPhoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC37", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest25=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest25);
			repo=createChildTest(childTest25,extent,repo);
			order=createChildTest(childTest25,extent,order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");	
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC37", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr, internetProduct));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC37", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest26=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest26);
			orderHistory=createChildTest(childTest26,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving ActivatePh_PortIN_TNs_TechRetry_LSC_came_beforeTR");
		} catch (Exception e) {
			log.error("Error in ActivatePh_PortIN_TNs_TechRetry_LSC_came_beforeTR: " + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test (dataProvider="getTestData", description="Priority-1")
	@SuppressWarnings(value={"rawtypes"})
	public void ActivatePh_nativeTN_DRing_PortinTN_LSC_not_received(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Activate Phone line with native TN and Distinctive Ring with Port-in TN when LSC is not received");
			log.debug("Entering ActivatePh_nativeTN_DRing_PortinTN_LSC_not_received");
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

			ExtentTest childTest3=extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			
			ExtentTest childTest4=extent.startTest("Add Distinctive Ring with Port Phone Number");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			String distinctivePhoneNbr=order.portDistinctiveNumber("No");
			
			ExtentTest childTest5=extent.startTest("Add Internet");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order); 
			order.addServiceInternet(internetProduct);
			
			ExtentTest childTest6=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			//order.selectConvergedHardware("Internet");
			//order.deleteHardware("Switch Window");
		
			ExtentTest childTest7=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("Yes", "");
			
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
			
			ExtentTest childTest11=extent.startTest("Wait for Effective date Mark Finished Task");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			order=createChildTest(childTest11,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			com.checkEffectiveDateStatus();
			order.navigateToOrderPage();
			
			ExtentTest childTest12=extent.startTest("Navigate To New Converged hw CFS Order");
			test.appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			order=createChildTest(childTest12,extent,order);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();
			
			ExtentTest childTest13=extent.startTest("Navigate To LSC Job Run");
			test.appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			com.navigateToCLECOrder();
			com.navigateToJobMonitorURL("LSC");  
			
			ExtentTest childTest14=extent.startTest("Wait for LSC Order finih Task");
			test.appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			order=createChildTest(childTest14,extent,order);
			landing=createChildTest(childTest14,extent,landing);
			order.navigateToCLECOPSUser("false", "Yes");
			landing.loggingOffCLECOPSUser();
			landing.Login();
			order.navigateToOrderPage();
			
			ExtentTest childTest15=extent.startTest("Wait for Tech Confirm Mark Finish Task");
			test.appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			order=createChildTest(childTest15,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
//			com.setMarkFinishedTask();
			order.navigateToOrderPage();
		
			ExtentTest childTest16=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest16);
			com=createChildTest(childTest16,extent,com);
			order=createChildTest(childTest16,extent,order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			
			ExtentTest childTest17=extent.startTest("Wait for NPAC Activation Confirmation");
			test.appendChild(childTest17);
			com=createChildTest(childTest17,extent,com);
			order.navigateToOrderValidation(23, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest18=extent.startTest("Navigate To BLIF Job Run");
			test.appendChild(childTest18);
			com=createChildTest(childTest18,extent,com);
			order=createChildTest(childTest18,extent,order);
			com.navigateToJobMonitorURL("BLIF");
			order.navigateToOrderPage();
			
			ExtentTest childTest19=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest19);
			com=createChildTest(childTest19,extent,com);
			som=createChildTest(childTest19,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest20=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest20);
			repo=createChildTest(childTest20,extent,repo);
			order=createChildTest(childTest20,extent,order);
			order.navigateToOrderValidation(18, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum,distinctivePhoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC40", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage(); 
			
			order.navigateToOrderValidation(2, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, distinctivePhoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC40", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest21=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest21);
			repo=createChildTest(childTest21,extent,repo);
			order=createChildTest(childTest21,extent,order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(distinctivePhoneNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC40", 1, valuesToPassForValidation),"Attributes are not validated successfully");		
			order.navigateToOrderPage();                
			
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, distinctivePhoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC40", 2, valuesToPassForValidation),"Attributes are not validated successfully");	
			order.navigateToOrderPage();

			ExtentTest childTest22=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest22);
			orderHistory=createChildTest(childTest22,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving ActivatePh_nativeTN_DRing_PortinTN_LSC_not_received");
		} catch (Exception e) {
			log.error("Error in ActivatePh_nativeTN_DRing_PortinTN_LSC_not_received: " + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-1")
	@SuppressWarnings(value={"rawtypes"})
	public void Activation_PortedTN_LifelineAlarm_Yes(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Activate Phone on DPT with Ported TN (Lifeline Alarm=Yes)");
			log.debug("Entering Activation_PortedTN_LifelineAlarm_Yes");
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

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest, extent, landing);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Phone");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String portPhoneNbr=order.portPhoneNumber("Phone Required", "", phoneProduct, "", "Yes", "No");

			ExtentTest childTest4=extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneHardwareSerialNbr, phoneHardware);

			ExtentTest childTest5=extent.startTest("Add Internet Product");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6=extent.startTest("Add Internet hardware");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);

			ExtentTest childTest7=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order=createChildTest(childTest7, extent, order);
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
			order=createChildTest(childTest9, extent, order);
			order.reviewAndFinishOrder();
			
			ExtentTest childTest10=extent.startTest("Navigate To COM, SOM , CLEC Order");
			test.appendChild(childTest10);
			order=createChildTest(childTest10, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest11=extent.startTest("Mark finish for Wait for Effective date");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();

			ExtentTest childTest12=extent.startTest("Navigate To LSC Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			com.navigateToJobMonitorURL("LSC");
			order.navigateToOrderPage();
			
			ExtentTest childTest13=extent.startTest("Send serial number New Phone Hardware CFS Order");
			test.appendChild(childTest13);
			com=createChildTest(childTest13, extent, com);
			som=createChildTest(childTest13, extent, som);
			order.navigateToOrderValidation(5, "No", "No", "COM");	
			com.sendSerialNbrTask(phoneHardwareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest14=extent.startTest("Send serial number New Internet Hardware CFS Order");
			test.appendChild(childTest14);
			com=createChildTest(childTest14, extent, com);
			som=createChildTest(childTest14, extent, som);
			order.navigateToOrderValidation(8, "No", "No", "COM");
			com.sendSerialNbrTask(internetHardwareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest15=extent.startTest("Mark finish for Wait for tech confirm");
			test.appendChild(childTest15);
			com=createChildTest(childTest15, extent, com);
			order=createChildTest(childTest15, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Navigate to New SOM Phone product CFS order");
			test.appendChild(childTest16);
			com=createChildTest(childTest16, extent, com);
			order=createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(2, "No", "No", "SOM");
			com.navigateToServiceInformationTab();
			Assert.assertTrue(com.portOptionDisplayed(), "Port Option is displaying for New Phone CFS order");
			order.navigateToOrderPage();

			ExtentTest childTest17=extent.startTest("Navigate To BLIF Job Run");
			test.appendChild(childTest17);
			com=createChildTest(childTest17, extent, com);
			order=createChildTest(childTest17, extent, order);
			com.navigateToJobMonitorURL("BLIF");
			order.navigateToOrderPage();

			ExtentTest childTest18=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest18);
			com=createChildTest(childTest18, extent, com);
			com.verifyCLECRequestPortNUmber();
			
			ExtentTest childTest19=extent.startTest("Navigate To E911 & BLIF Job Run");
			test.appendChild(childTest19);
			com=createChildTest(childTest19, extent, com);
			order=createChildTest(childTest19, extent, order);
			com.navigateToJobMonitorURL("E911");
			com.navigateToJobMonitorURL("BLIF");
			order.navigateToOrderPage();

			ExtentTest childTest20=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest20);
			com=createChildTest(childTest20, extent, com);
			com.verifyCLECRequestPortNUmber();
			
			ExtentTest childTest21=extent.startTest("Verify COM, SOM, CLEC Record counts");
			test.appendChild(childTest21);
			com=createChildTest(childTest21, extent, com);
			som=createChildTest(childTest21, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest22=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest22);
			repo=createChildTest(childTest22,extent,repo);
			order=createChildTest(childTest22,extent,order);
			order.navigateToOrderValidation(2, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC42", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(2, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC42", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest23=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest23);
			repo=createChildTest(childTest23,extent,repo);
			order=createChildTest(childTest23,extent,order);
			order.navigateToOrderValidation(5, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(portPhoneNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC42", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
	
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, portPhoneNbr, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC42", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			
			ExtentTest childTest24=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest24);
			orderHistory=createChildTest(childTest24, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Activation_PortedTN_LifelineAlarm_Yes");
		} catch (Exception e) {
			log.error("Error in Activation_PortedTN_LifelineAlarm_Yes:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-1")
	@SuppressWarnings(value={"rawtypes"})
	public void Activation_PortedTN_LifelineAlarm_No(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Activate Phone on DPT with Ported TN (Lifeline Alarm=NO)");
			log.debug("Entering Activation_PortedTN_LifelineAlarm_No");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardwareSerialNbr=PhSrlNo;
			String phoneHardware=data.get("Phone_hardware");
			String internetHardware=data.get("Internet_hardware");
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
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
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Phone");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String portPhoneNbr=order.portPhoneNumber("Phone Required", "", phoneProduct, "", "No", "No");
			ExtentTest childTest4=extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneHardwareSerialNbr, phoneHardware);

			ExtentTest childTest5=extent.startTest("Add Internet Product");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6=extent.startTest("Add Internet hardware");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);

			ExtentTest childTest7=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order=createChildTest(childTest7, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("Yes", "");
			
			ExtentTest childTest8=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();	

			ExtentTest childTest9=extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10=extent.startTest("Navigate To COM, SOM And CLEC Orders");
			test.appendChild(childTest10);
			order=createChildTest(childTest10, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest11 =extent.startTest("Mark finish for Wait for Effective date");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();			   
			order.navigateToOrderPage();
			
			ExtentTest childTest12=extent.startTest("Send serial number New Phone Hardware CFS Order");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			som=createChildTest(childTest12, extent, som);
			com.navigateToCOMOrder();
			order.navigateToOrderValidation(5, "No", "No", "COM");	
			com.sendSerialNbrTask(phoneHardwareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest13=extent.startTest("Send serial number New Internet Hardware CFS Order");
			test.appendChild(childTest13);
			com=createChildTest(childTest13, extent, com);
			som=createChildTest(childTest13, extent, som);
			com.navigateToCOMOrder();
			order.navigateToOrderValidation(8, "No", "No", "COM");
			com.sendSerialNbrTask(internetHardwareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest14=extent.startTest("Navigate To LSC Job Run");
			test.appendChild(childTest14);
			com=createChildTest(childTest14, extent, com);
			com.navigateToJobMonitorURL("LSC");
			
			ExtentTest childTest15=extent.startTest("Wait for LSC Order finish Task");
			test.appendChild(childTest15);
			com=createChildTest(childTest15, extent, com);
			order=createChildTest(childTest15, extent, order);
			landing=createChildTest(childTest15, extent, landing);
			order.openCOMOrderPage("false", accntNum, "Yes");
			order.navigateToCLECOPSUser("false", "Yes");
			landing.loggingOffCLECOPSUser();
			landing.Login();
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Mark finish Wait for tech confirm");
			test.appendChild(childTest16);
			com=createChildTest(childTest16, extent, com);
			order=createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest17=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest17);
			com=createChildTest(childTest17, extent, com);
			order=createChildTest(childTest17,extent,order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			
			ExtentTest childTest18=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest18);
			com=createChildTest(childTest18, extent, com);
			com.verifyCLECRequestPortNUmber();

			ExtentTest childTest19=extent.startTest("Navigate to New SOM Phone product CFS order");
			test.appendChild(childTest19);
			com=createChildTest(childTest19, extent, com);
			order=createChildTest(childTest19, extent, order);			
			order.navigateToOrderValidation(2, "No", "No", "SOM");
			com.navigateToServiceInformationTab();
			Assert.assertTrue(com.portOptionDisplayed(), "Port Option is displaying for New Phone CFS order");
			order.navigateToOrderPage();

			ExtentTest childTest20=extent.startTest("Verify COM ,SOM, CLEC Record counts");
			test.appendChild(childTest20);
			com=createChildTest(childTest20, extent, com);
			som=createChildTest(childTest20, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest21=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest21);
			repo=createChildTest(childTest21, extent, repo);
			order=createChildTest(childTest21, extent, order);		
			order.navigateToOrderValidation(2, "Yes", "No", "SOM");	
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC43", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(2, "Yes", "No", "SOM");	
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC43", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest22=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest22);
			repo=createChildTest(childTest22, extent, repo);
			order=createChildTest(childTest22, extent, order);		
			order.navigateToOrderValidation(5, "Yes", "No", "COM");	
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(portPhoneNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC43", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
		
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, portPhoneNbr, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC43", 2, valuesToPassForValidation),"Attributes are not validated successfully");

			ExtentTest childTest23=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest23);
			orderHistory=createChildTest(childTest23, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Activation_PortedTN_LifelineAlarm_No");
		} catch (Exception e) {
			log.error("Error in Activation_PortedTN_LifelineAlarm_No:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Porting_LifelineYes_ShawService_ReqestYes(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Porting in a Phone number during Premise Move, the number should be ported in immediately when Alarm value value set to \"Yes\" and Disconnection/Activation address value is selected as \"Yes\"");
			log.debug("Entering Porting_LifelineYes_ShawService_ReqestYes");
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

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest, extent, landing);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Phone");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String portPhoneNbr=order.portPhoneNumber("Phone Required", "", phoneProduct, "", "Yes", "Yes");
			
			ExtentTest childTest4=extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneHardwareSerialNbr, phoneHardware);
			
			ExtentTest childTest5=extent.startTest("Add Internet Product");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6=extent.startTest("Add Internet Hardware");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			
			ExtentTest childTest7=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order=createChildTest(childTest7, extent, order);
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
			order=createChildTest(childTest9, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10=extent.startTest("Navigate To COM , SOM , CLEC Order");
			test.appendChild(childTest10);
			order=createChildTest(childTest10, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			
			ExtentTest childTest11=extent.startTest("Navigate to New Customer order for Port In Immediate option _Yes");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);			
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.navigateToServiceInformationTab();
			Assert.assertTrue(com.portInImmediately_Yes(), "Port In Immediate _Yes is displaying for New Customer order");
			
			ExtentTest childTest12 =extent.startTest("Wait for Effective date Mark Finished Task");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			order=createChildTest(childTest12, extent, order);
			com.setMarkFinishedTask(); 
			order.navigateToOrderPage();
			
			ExtentTest childTest13=extent.startTest("Wait for Provisining Start Mark Finished Task");
			test.appendChild(childTest13);
			com=createChildTest(childTest13, extent, com);
			order=createChildTest(childTest13, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask(); 
			order.navigateToOrderPage();
			
			ExtentTest childTest14=extent.startTest("Navigate To LSC Job Run");
			test.appendChild(childTest14);
			com=createChildTest(childTest14, extent, com);
			com.navigateToJobMonitorURL("LSC");
			
			ExtentTest childTest15 =extent.startTest("Wait for tech confrim Mark finished Task");
			test.appendChild(childTest15);
			com=createChildTest(childTest15, extent, com);
			order=createChildTest(childTest15, extent, order);
			order.openCOMOrderPage("true", accntNum, "Yes");
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			
			ExtentTest childTest16 =extent.startTest("Validating Port In Immediate Yes");
			test.appendChild(childTest16);
			com=createChildTest(childTest16, extent, com);
			order=createChildTest(childTest16, extent, order);
			com.navigateToServiceInformationTab();
			Assert.assertTrue(com.portInImmediately_Yes(), "Port In Immediate _Yes is displaying for New Customer order");
			
			ExtentTest childTest17=extent.startTest("Navigate To BLIF Job Run");
			test.appendChild(childTest17);
			com=createChildTest(childTest17, extent, com);
			com.navigateToJobMonitorURL("BLIF");
			
			ExtentTest childTest18=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest18);
			com=createChildTest(childTest18, extent, com);
			order=createChildTest(childTest18, extent, order);
			com.navigateToJobMonitorURL("E911");
			com.navigateToJobMonitorURL("BLIF");
			order.navigateToOrderPage();
			
			ExtentTest childTest19=extent.startTest("Verify COM ,SOM, CLEC Record counts");
			test.appendChild(childTest19);
			com=createChildTest(childTest19, extent, com);
			som=createChildTest(childTest19, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest20=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest20);
			repo=createChildTest(childTest20, extent, repo);
			order=createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(2, "Yes", "No", "SOM");	
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC87", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest21=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest21);
			repo=createChildTest(childTest21, extent, repo);
			order=createChildTest(childTest21,extent,order);
			order.navigateToOrderValidation(5, "Yes", "No", "COM");	
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC87", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC87", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC87", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC87", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest22=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest22);
			orderHistory=createChildTest(childTest22, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");  
			
			log.debug("Leaving Porting_LifelineYes_ShawService_ReqestYes");
		} catch (Exception e) {
			log.error("Error in Porting_LifelineYes_ShawService_ReqestYes:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Porting_LifeLineNo_ShawService_ReqestNo(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Porting in a Phone number during Premise Move, the number should be ported in immediately when Alarm value value set to \"No\" and Disconnection/Activation address value is selected as \"No\"");
			log.debug("Entering Porting_LifeLineNo_ShawService_ReqestNo");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardwareSerialNbr=PhSrlNo;
			String phoneHardware=data.get("Phone_hardware");
			String internetProduct=data.get("Internet_Product");
			String internetHardware=data.get("Internet_hardware");
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
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
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add Internet Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);

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

			ExtentTest childTest8=extent.startTest("Navigate To COM, SOM Orders");
			test.appendChild(childTest8);
			order=createChildTest(childTest8, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest9=extent.startTest("Navigate to CGY Location");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			order.navigateToExistingAccountForPremise(accntNum);

			ExtentTest childTest10=extent.startTest("Add Phone Product");
			test.appendChild(childTest10);
			order=createChildTest(childTest10, extent, order);
			order.addServicesAndFeacturesTab();
			order.portPhoneNumber("Phone Required", "", phoneProduct, "", "No", "No");

			ExtentTest childTest11=extent.startTest("Add Phone hardware");
			test.appendChild(childTest11);
			order=createChildTest(childTest11, extent, order);
			order.addPhoneHardware(phoneHardwareSerialNbr, phoneHardware);

			ExtentTest childTest12=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest12);
			order=createChildTest(childTest12, extent, order);
			order.selectInstallationOption("Self Connect No", "Yes");
			order.techAppoinmentYes("Yes", "");
			
			ExtentTest childTest13=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest13);
			order=createChildTest(childTest13,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest14=extent.startTest("Review and Finish");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest15=extent.startTest("Navigate To COM, SOM Orders");
			test.appendChild(childTest15);
			order=createChildTest(childTest15, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest16=extent.startTest("Navigate To LSC Job Run");
			test.appendChild(childTest16);
			com=createChildTest(childTest16, extent, com);
			com.navigateToJobMonitorURL("LSC");

			ExtentTest childTest17=extent.startTest("Check for Port In Immediate option Yes");
			test.appendChild(childTest17);
			com=createChildTest(childTest17, extent, com);
			order=createChildTest(childTest17, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.navigateToServiceInformationTab();
			Assert.assertTrue(com.portInImmediately_Yes(),"Port In Immediate _Yes is displaying for New Customer order");
			order.navigateToOrderPage();

			ExtentTest childTest18=extent.startTest("Mark finish Wait for Disconnection Date");
			test.appendChild(childTest18);
			com=createChildTest(childTest18, extent, com);
			order=createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			
			ExtentTest childTest19=extent.startTest("Wait for Effective date Mark Finished Task");
			test.appendChild(childTest19);
			com=createChildTest(childTest19, extent, com);
			order=createChildTest(childTest19, extent, order);
			com.setMarkFinishedTask(); 
			
			ExtentTest childTest20=extent.startTest("Wait for Provisining STart Mark Finished Task");
			test.appendChild(childTest20);
			com=createChildTest(childTest20, extent, com);
			order=createChildTest(childTest20, extent, order);
			com.setMarkFinishedTask(); 
			order.navigateToOrderPage();

			ExtentTest childTest21=extent.startTest("Send Serial Number For internet Hardware");
			test.appendChild(childTest21);
			com=createChildTest(childTest21, extent, com);
			order=createChildTest(childTest21, extent, order);
			order.navigateToOrderValidation(45, "No", "No", "COM");	
			com.sendSerialNbrTask(internetHardwareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest22=extent.startTest("Send Serial For Phone Hardware");
			test.appendChild(childTest22);
			com=createChildTest(childTest22, extent, com);
			order=createChildTest(childTest22, extent, order);
			order.navigateToOrderValidation(5, "No", "No", "COM");	
			com.sendSerialNbrTask(phoneHardwareSerialNbr);
			order.navigateToOrderPage();

			/*ExtentTest childTest23=extent.startTest("Wait for NPAC activation Mark finish");
			test.appendChild(childTest23);
			com=createChildTest(childTest23, extent, com);
			order.navigateToOrderValidation(4, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();*/

			ExtentTest childTest24=extent.startTest("Wait for Tech Confirm Mark Finished Task");
			test.appendChild(childTest24);
			com=createChildTest(childTest24, extent, com);
			order=createChildTest(childTest24, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();

			ExtentTest childTest25=extent.startTest("Navigate To BLIF Job Run");
			test.appendChild(childTest25);
			com=createChildTest(childTest25, extent, com);
			com.navigateToJobMonitorURL("BLIF");

			ExtentTest childTest26=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest26);
			com=createChildTest(childTest26, extent, com);
			order=createChildTest(childTest26, extent, order);
			com.navigateToJobMonitorURL("E911");
			com.navigateToJobMonitorURL("BLIF");
			order.navigateToOrderPage();

			ExtentTest childTest27=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest27);
			com=createChildTest(childTest27, extent, com);
			com.verifyCLECRequestPortNUmber();

			ExtentTest childTest28=extent.startTest("Verify COM ,SOM, CLEC Record counts");
			test.appendChild(childTest28);
			com=createChildTest(childTest28, extent, com);
			som=createChildTest(childTest28, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest29=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest29);
			repo=createChildTest(childTest29, extent, repo);
			order=createChildTest(childTest29, extent, order);
			order.navigateToOrderValidation(2, "Yes", "No", "SOM");	
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC88", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest30=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest30);
			order=createChildTest(childTest30, extent, order);
			order.navigateToOrderValidation(5, "Yes", "No", "COM");	
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC88", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC88", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC88", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC88", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest31=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest31);
			orderHistory=createChildTest(childTest31, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Porting_LifeLineNo_ShawService_ReqestNo");	
		} catch (Exception e) {
			log.error("Error in Porting_LifeLineNo_ShawService_ReqestNo:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void PortIn_No_Retail_PickUp_Yes(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Verify activating Phone + XB6 with Ported TN (Lifeline alarm = No), Internet with Portin/Disconnect, TV with Portin/Disconnect when moving to a new address with Retail Pickup = Yes");
			log.debug("Entering PortIn_No_Retail_PickUp_Yes");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			String convergedHardware=data.get("Converged_hardware");
			TVHDSrlNo=Utility.generateHardwareSerailNum(TVHDSrlNo);
			String tvHardwareSerialNbr=TVHDSrlNo;
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			String[] hardwareType={"DCX3510HDGuide"};
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
			order=createChildTest(childTest0,extent,order);
			landing=createChildTest(childTest0, extent, landing);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1,extent,order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Phone, Voice mail");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.portPhoneNumber("Phone Required", "", phoneProduct, "", "No", "Yes");
			order.selectVoiceMail();

			ExtentTest childTest4=extent.startTest("Add Internet Product");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addServiceInternet(internetProduct);
			
			ExtentTest childTest5=extent.startTest("Add converged Hardware without Serial Nbr");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addConvergedHardwareWithoutSlNbr();
			order.selectConvergedHardware(convergedHardware);

			ExtentTest childTest6=extent.startTest("Add TV Product");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.addServiceTV(tvProduct);
			
			ExtentTest childTest7=extent.startTest("Add TV Hardware without Serial Number");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.addTVHardwareWithOutSrlNo(hardwareType);
			
			ExtentTest childTest8=extent.startTest("Add PortIn/Disconnect Options for Internet");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.navigateToOperationsPortIn("Internet", "No", "Yes");
			
			ExtentTest childTest9=extent.startTest("Add PortIn/Disconnect Options for TV");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			order.navigateToOperationsPortIn("TV", "", "Yes");

			ExtentTest childTest10=extent.startTest("Navigate To Tech Appointment With Retail PickUp");
			test.appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			order.selectInstallationOption("Self Connect Yes", "");
			order.techAppointmentNo("Retail Pickup", "", "");
			
			ExtentTest childTest11=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest11);
			order=createChildTest(childTest11,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			
			ExtentTest childTest12=extent.startTest("Review and Finish");
			test.appendChild(childTest12);
			order=createChildTest(childTest12,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest13=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest13);
			order=createChildTest(childTest13,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",accntNum, "Yes");
			
			ExtentTest childTest14=extent.startTest("Navigate To LSC Job Run");
			test.appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			com.navigateToJobMonitorURL("LSC");
			order.navigateToOrderPage();
			com.navigateToCOMOrder();
			
			ExtentTest childTest15=extent.startTest("Navigate to Retail Pickup Page");
			test.appendChild(childTest15);
			orderHistory=createChildTest(childTest15,extent,orderHistory);
			orderHistory.navigateToOrderHistoryRetailPickupDCX3510("true", accntNum, convergedHardWareSerialNbr, tvHardwareSerialNbr);
			
			ExtentTest childTest16=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest16);
			order=createChildTest(childTest16,extent,order);
			order.openCOMOrderPage("false",accntNum, "Yes");
			
			/*ExtentTest childTest17=extent.startTest("Navigate to Work Order page For activation");
			test.appendChild(childTest17);
			orderHistory=createChildTest(childTest17,extent,orderHistory);
			order=createChildTest(childTest17,extent,order);
			orderHistory.navigateToOrderHistoryUrl(accntNum, ""); 
			orderHistory.navigateToOrderHistoryActivate();
			order.navigateToOrderPage();*/
			
			ExtentTest childTest18=extent.startTest("Navigate To BLIF Job Run");
			test.appendChild(childTest18);
			com=createChildTest(childTest18,extent,com);
			order=createChildTest(childTest18,extent,order);
			com.navigateToCLECOrder();
			com.navigateToJobMonitorURL("BLIF");
			order.navigateToOrderPage();
			
			ExtentTest childTest19=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest19);
			com=createChildTest(childTest19,extent,com);
			com.navigateToCLECOrder();

			ExtentTest childTest20=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest20);
			com=createChildTest(childTest20,extent,com);
			order=createChildTest(childTest20, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			
			ExtentTest childTest21=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest21);
			com=createChildTest(childTest21,extent,com);
			com.navigateToCLECOrder();
			
			ExtentTest childTest22=extent.startTest("Navigate To Verify SVG Diagram");
			test.appendChild(childTest22);
			com=createChildTest(childTest22,extent,com);
			order=createChildTest(childTest22,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("");
			order.navigateToOrderPage();
			
			ExtentTest childTest23=extent.startTest("Verify SVG Diagram for Waiting Tasks to be Completed in New Converged Hardware Order");
			test.appendChild(childTest23);
			com=createChildTest(childTest23,extent,com);
			order=createChildTest(childTest23,extent,order);
			order.navigateToOrderValidation(15, "No", "Yes", "COM");
			com.verifySVGDiagram("");
			order.navigateToOrderPage();
			order.navigateToOrderValidation(11, "No", "Yes", "COM");
			com.verifySVGDiagram("");
			order.navigateToOrderPage();
			
			ExtentTest childTest24=extent.startTest("Verify LSC and LSR Tasks Completion");
			test.appendChild(childTest24);
			com=createChildTest(childTest24,extent,com);
			order=createChildTest(childTest24,extent,order);
			com.navigateToLSROrder(1);
			com.verifySVGDiagram("");
			order.navigateToOrderPage();
			com.navigateToLSROrder(2);
			com.verifySVGDiagram("");
			order.navigateToOrderPage();
			com.navigateToLSROrder(3);
			com.verifySVGDiagram("");
			order.navigateToOrderPage();

			ExtentTest childTest25=extent.startTest("Verify SOM Orders for Phone, TV, Internet are completed");
			test.appendChild(childTest25);
			som=createChildTest(childTest25,extent,som);
			order=createChildTest(childTest25,extent,order);
			som.navigateToSOMOrder();
			order.navigateToOrderPage();
			
			ExtentTest childTest26=extent.startTest("Verify Waiting Tasks Completion");
			test.appendChild(childTest26);
			com=createChildTest(childTest26,extent,com);
			order=createChildTest(childTest26,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("");
			order.navigateToOrderPage();
			
			ExtentTest childTest27=extent.startTest("Navigate to OE page and view there LOB's");
			test.appendChild(childTest27);
			order=createChildTest(childTest27,extent,order);
			order.navigateToCustOrderPage("false",accntNum);
			order.reviewOrder();
			order.navigateToOrderPage();
			
			ExtentTest childTest28=extent.startTest("Verify COM, SOM and CLEC Record counts");
			test.appendChild(childTest28);
			com=createChildTest(childTest28,extent,com);
			som=createChildTest(childTest28,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest29=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest29);
			orderHistory=createChildTest(childTest29,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, ""); 

			log.debug("Leaving PortIn_No_Retail_PickUp_Yes");
		} catch (Exception e) {
			log.error("Error in PortIn_No_Retail_PickUp_Yes:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Porting_LifeLine_No_TechAppointment_Yes(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Verify whether the provisioning of ported number (with NO Lifeline/ Alarm) is successful with alternate work flow");
			log.debug("Entering Porting_LifeLine_No_TechAppointment_Yes");
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
			landing=createChildTest(childTest, extent, landing);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			landing.openUrl();
			
			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest4=extent.startTest("Add Phone");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addServicesAndFeacturesTab();
			order.portPhoneNumber("Phone Required", "", phoneProduct, "", "No", "No");
		    
			ExtentTest childTest3=extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServiceInternet(internetProduct);
			
			ExtentTest childTest5=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addConvergedHardwareWithoutSlNbr();
			order.selectConvergedHardware(convergedHardware);
			
			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("", "");
			
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

			ExtentTest childTest9=extent.startTest("Navigate To COM, SOM And CLEC Orders");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			
			ExtentTest childTest10=extent.startTest("Wait for Effective date Mark Finish Task");
			test.appendChild(childTest10);
			com=createChildTest(childTest10, extent, com);
			order=createChildTest(childTest10, extent, order);			
			order.navigateToOrderValidation(1, "No", "No", "COM");
			//com.setMarkFinishedTask(); 
			com.setMarkFinishedSpecificTask("Wait for effective date");
			order.navigateToOrderPage();
			
			ExtentTest childTest11 =extent.startTest("Send serial number for New converged Hardware");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();
			
			ExtentTest childTest12=extent.startTest("Navigate To LSC Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			com.navigateToCLECOrder();
			com.navigateToJobMonitorURL("LSC");
			
			ExtentTest childTest13=extent.startTest("Navigate To Initiate line activation for CLEC OPS task");
			test.appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			order=createChildTest(childTest13,extent,order);
			landing=createChildTest(childTest13,extent,landing);
			order.navigateToCLECOPSUser("false", "Yes");
			//order.openCOMOrderPage("false",accntNum, "Yes");
			landing.loggingOffCLECOPSUser();
			landing.Login();
			order.navigateToOrderPage();
			
			/*ExtentTest childTest14=extent.startTest("Manual port resolving step Mark finish Task");
			test.appendChild(childTest14);
			com=createChildTest(childTest14, extent, com);
			order=createChildTest(childTest14, extent, order);
			order.navigateToOrderValidation(24, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();*/
			
			ExtentTest childTest15 =extent.startTest("Wait for tech confirm Mark finish Task");
			test.appendChild(childTest15);
			com=createChildTest(childTest15, extent, com);
			order=createChildTest(childTest15, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			
			ExtentTest childTest16=extent.startTest("Navigate To BLIF Job Run");
			test.appendChild(childTest16);
			com=createChildTest(childTest16, extent, com);
			order=createChildTest(childTest16, extent, order);
			com.navigateToJobMonitorURL("BLIF");
			order.navigateToOrderPage();
			
			ExtentTest childTest17=extent.startTest("Verify COM ,SOM, CLEC Record counts");
			test.appendChild(childTest17);
			com=createChildTest(childTest17, extent, com);
			som=createChildTest(childTest17, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest18=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest18);
			repo=createChildTest(childTest18, extent, repo);
			order=createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(3, "Yes", "No", "SOM");	
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC90", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest19=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest19);
			repo=createChildTest(childTest19, extent, repo);
			order=createChildTest(childTest19, extent, order);
			order.navigateToOrderValidation(4, "Yes", "No", "COM");	
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC90", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC90", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC90", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC90", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest20=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest20);
			orderHistory=createChildTest(childTest20, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");  
			
			log.debug("Leaving Porting_LifeLine_No_TechAppointment_Yes");
		} catch (Exception e) {
			log.error("Error in Porting_LifeLine_No_TechAppointment_Yes:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
		
	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Ported_Num_Disconnect_After_TN_Change(Hashtable<String,String> data) throws Exception{
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Verify Update Billing on IFM Phone with ported number Disconnect after TN change");
			log.debug("Entering Ported_Num_Disconnect_After_TN_Change");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String phoneHardware=data.get("Phone_hardware");
			String internetHardware=data.get("Internet_hardware");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardwareSerialNbr=PhSrlNo;
			IntACSrlNo=Utility.generateHardwareSerailNum(IntACSrlNo);
			String internetHardwareSerialNbr=IntACSrlNo;
			String techAppointment=data.get("Tech_Appointment");
			String disconnectReason=data.get("Disconnect_Reasons");
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

			ExtentTest childTest3=extent.startTest("Add Port Phone Number");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);    
			order.addServicesAndFeacturesTab();
			String portPhoneNbr=order.portPhoneNumber("Phone Required", "", phoneProduct, "", "Yes", "No");
			order.selectVoiceMail();
			
			ExtentTest childTest4=extent.startTest("Add Phone Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addPhoneHardware(phoneHardwareSerialNbr,phoneHardware);
		
			ExtentTest childTest5=extent.startTest("Add Internet Product");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6=extent.startTest("Add Internet Hardware");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);

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
			
			ExtentTest childTest11=extent.startTest("Navigate To LSC & BLIF Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			com.navigateToJobMonitorURL("LSC");
			com.navigateToJobMonitorURL("BLIF");
			order.navigateToOrderPage();
			com.navigateToCLECOrder();
			
			ExtentTest childTest12=extent.startTest("Navigate To Verify SVG Diagram");
			test.appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			order=createChildTest(childTest12,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("");
			order.navigateToOrderPage();
			
			ExtentTest childTest13=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest13);
			com=createChildTest(childTest13, extent, com);
			order=createChildTest(childTest13, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			
			ExtentTest childTest14=extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest14);
			order=createChildTest(childTest14,extent,order);
			order.navigateToCustOrderPage("false",accntNum);		
	
			ExtentTest childTest15=extent.startTest("Change the Telephone Number from TN1 to TN2");
			test.appendChild(childTest15);
			order=createChildTest(childTest15,extent,order);
			order.changeCustNbrPortedToPorted("No");
			
			ExtentTest childTest16=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest16);
			order=createChildTest(childTest16,extent,order);
			order.selectInstallationOption("Self Connect No", "Yes");
			order.techAppoinmentYes("", "");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();
			order.selectPortOut();
			
			ExtentTest childTest17=extent.startTest("Review and Finish");
			test.appendChild(childTest17);
			order=createChildTest(childTest17,extent,order);
			order.reviewAndFinishOrder();
			
			ExtentTest childTest18=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest18);
			order=createChildTest(childTest18,extent,order);
			order.openCOMOrderPage("false",accntNum, "Yes");
			
			ExtentTest childTest19=extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest19);
			order=createChildTest(childTest19,extent,order);
			order.navigateToCustOrderPage("false",accntNum);		
			
			ExtentTest childTest20=extent.startTest("Disconnect Phone LOB");
			test.appendChild(childTest20);
			order=createChildTest(childTest20,extent,order);
			order.disconnectLOBProducts("Phone");
			order.selectPortOut();
		
			ExtentTest childTest21=extent.startTest("Navigate to tech Appoinment");
			test.appendChild(childTest21);
			order=createChildTest(childTest21, extent,order);
			order.navigatetoAppointmentTab();
			
			ExtentTest childTest22=extent.startTest("Select Disconnect Reason");
			test.appendChild(childTest22);
			order=createChildTest(childTest22, extent,order);
			order.selectDisconnectControllabeReasons(disconnectReason);
			
			ExtentTest childTest23=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest23);
			order=createChildTest(childTest23,extent,order);
			order.navigateToAgreementDetailsTab();
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			
			ExtentTest childTest24=extent.startTest("Review and Finish");
			test.appendChild(childTest24);
			order=createChildTest(childTest24,extent,order);
			order.reviewAndFinishOrder();
	
			ExtentTest childTest25=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest25);
			order=createChildTest(childTest25,extent,order);
			order.openCOMOrderPage("false",accntNum, "Yes");
			
			/*ExtentTest childTest26=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest26);
			com=createChildTest(childTest26, extent, com);
			order=createChildTest(childTest26, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
		
			ExtentTest childTest24=extent.startTest("Wait for Effective date Mark finish Task");
			test.appendChild(childTest24);
			com=createChildTest(childTest24,extent,com);
			order=createChildTest(childTest24,extent,order);
			order.navigateToOrderValidation(27, "No", "No", "COM");
			String orderStatus=com.getOrderStatus();
			if (orderStatus.equalsIgnoreCase("Processing")) {
				com.verifySVGDiagram("");
				com.setMarkFinishedTask();
			}
			else {
				order.navigateToOrderPage();
				order.navigateToOrderValidation(26, "No", "No", "COM");
				com.verifySVGDiagram("");
				com.setMarkFinishedTask();
			}
			order.navigateToOrderPage();*/
			
			ExtentTest childTest27=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest27);
			com=createChildTest(childTest27,extent,com);
			som=createChildTest(childTest27,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount); 
			
			ExtentTest childTest28=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest28);
			repo=createChildTest(childTest28,extent,repo);
			order=createChildTest(childTest28,extent,order);
			order.navigateToOrderValidation(61, "Yes", "No", "COM");	
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, portPhoneNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC103", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest29=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest29);
			orderHistory=createChildTest(childTest29,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Ported_Num_Disconnect_After_TN_Change");
		} catch (Exception e) {
			log.error("Error in Ported_Num_Disconnect_After_TN_Change:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Porting_PhNbr_AlarmNo_Disconnection_Address_Yes_PortIn_Immediately (Hashtable<String,String> data) throws Exception{
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Porting in a phone number, if Alarm value set to \"No\" and Disconnection/Activation address value is selected as \"Yes\", then Port-In should happen immediately");
			log.debug("Entering Porting_PhNbr_AlarmNo_Disconnection_Address_Yes_PortIn_Immediately");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String convergedHardware=data.get("Converged_hardware");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			String techAppointment=data.get("Tech_Appointment");
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

			ExtentTest childTest3=extent.startTest("Add Port Phone Number");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);    
			order.addServicesAndFeacturesTab();
			order.portPhoneNumber("Phone Required", "", phoneProduct, "", "No", "Yes");
			order.selectVoiceMail();
			
			ExtentTest childTest4=extent.startTest("Add Internet Peoduct");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addServiceInternet(internetProduct);
			
			ExtentTest childTest5=extent.startTest("Add Convrgd Hardware Without SlNbr");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addConvergedHardwareWithoutSlNbr();
			order.selectConvergedHardware(convergedHardware);
			
			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("Yes", "");
			
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

			ExtentTest childTest9=extent.startTest("Navigate To COM, SOM And CLEC Orders");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			
			ExtentTest childTest10=extent.startTest("Verify COM, SOM Initial Record Counts");
			test.appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			som=createChildTest(childTest10,extent,som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);
			
			ExtentTest childTest11=extent.startTest("Navigate To LSC Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			com.navigateToCLECOrder();
			com.navigateToJobMonitorURL("LSC");
		    order.navigateToOrderPage();
		    
		    ExtentTest childTest12=extent.startTest("Wait for Effective date Mark finish Task");
			test.appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			order=createChildTest(childTest12,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest13=extent.startTest("Send serial number for Converged hardware");
			test.appendChild(childTest13);
			com=createChildTest(childTest13, extent, com);
			order=createChildTest(childTest13, extent, order);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();
			
			/*ExtentTest childTest14=extent.startTest("Wait for NPAC Activation Confirmation");
			test.appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			order=createChildTest(childTest14,extent,order);
			order.navigateToOrderValidation(4, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();*/
			
			ExtentTest childTest15=extent.startTest("Navigate To BLIF Job Run");
			test.appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			order=createChildTest(childTest15,extent,order);
			com.navigateToJobMonitorURL("BLIF");
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Wait for tech confirm Mark Finish Task");
			test.appendChild(childTest16);
			com=createChildTest(childTest16,extent,com);
			order=createChildTest(childTest16,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest17=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest17);
			com=createChildTest(childTest17,extent,com);
			order=createChildTest(childTest17, extent, order);
			com.navigateToCLECOrder();
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			
			ExtentTest childTest18=extent.startTest("Verify Port In Immediately Value Should be Yes");
			test.appendChild(childTest18);
			com=createChildTest(childTest18,extent,com);
			order=createChildTest(childTest18,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.navigateToServiceInformationTab();
			order.navigateToOrderPage();
			
			ExtentTest childTest19=extent.startTest("Verify COM and SOM Record Counts");
			test.appendChild(childTest19);
			com=createChildTest(childTest19,extent,com);
			som=createChildTest(childTest19,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest20=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest20);
			repo=createChildTest(childTest20,extent,repo);
			order=createChildTest(childTest20,extent,order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");	
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC116", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC116", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest21=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest21);
			orderHistory=createChildTest(childTest21,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Porting_PhNbr_AlarmNo_Disconnection_Address_Yes_PortIn_Immediately");
		} catch (Exception e) {
			log.error("Error in Porting_PhNbr_AlarmNo_Disconnection_Address_Yes_PortIn_Immediately:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-1")
	@SuppressWarnings(value={"rawtypes"})
	public void Convert_PhNative_Ported_LSC_Cmpltd_DueDate_Reach_Cancle_Porting(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Verify that while converting a phone from native to ported and - when LSC tasks are completed, - due date has reached, user should be able to cancel the porting order");
			log.debug("Entering Convert_PhNative_Ported_LSC_Cmpltd_DueDate_Reach_Cancle_Porting");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String phoneHardware=data.get("Phone_hardware");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardwareSerialNbr=PhSrlNo;
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
			
			ExtentTest childTest0 = extent.startTest("Navigate To LSC Response Job Paused");
			test.appendChild(childTest0);
			order = createChildTest(childTest0, extent, order);
			landing = createChildTest(childTest0, extent, landing);
			order.navigateToLSCResponseJobpaused();
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

			ExtentTest childTest4 = extent.startTest("Voice mail and Add Distinctive Ring");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.selectVoiceMail();
			order.addDistinctiveRing("One", "3 short rings");

			ExtentTest childTest5 = extent.startTest("Add Phone hardware");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addPhoneHardware(phoneHardwareSerialNbr, phoneHardware);
			// order.selectTodo();

			ExtentTest childTest6 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
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

			ExtentTest childTest12 = extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.navigateToLSCResponseJobNormal();

			ExtentTest childTest13 = extent.startTest("Navigate To Integration Settings Change Porting Time");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.navigateToIntegrationSettings("Porting Time");
			
			ExtentTest childTest14 = extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.navigateToCustOrderPage("true",accntNum);
			
			ExtentTest childTest15 = extent.startTest("Navigate To Convert Phone From Native To Ported");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.portPhoneNumber("", "", phoneProduct, "Native To Ported", "", "No");
			order.selectPortOut();

			ExtentTest childTest16 = extent.startTest("Navigate To Tech Appointment With Current Date");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.techAppointmentWithCurrentDate();

			ExtentTest childTest17 = extent.startTest("Select default installation checkBox");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest18 = extent.startTest("Review and Finish");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest19 = extent.startTest("Navigate To COM,SOM and CLEC orders");
			test.appendChild(childTest19);
			order = createChildTest(childTest19, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest20 = extent.startTest("Wait for Effective date Mark Finish Task");
			test.appendChild(childTest20);
			com = createChildTest(childTest20, extent, com);
			order = createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			com.checkEffectiveDateStatus();
			order.navigateToOrderPage();

			ExtentTest childTest21=extent.startTest("Wait for All LSC's Step Over");
		    test.appendChild(childTest21);
			com=createChildTest(childTest21,extent,com);
			order=createChildTest(childTest21,extent,order);
			order.navigateToOrderValidation(24, "No", "No", "COM");
			com.navigateToWaitForAllLSCsTask();
			order.navigateToOrderPage();
	
			ExtentTest childTest22=extent.startTest("Navigate to Putty and Time Shifting 2 days");
			test.appendChild(childTest22);
			com=createChildTest(childTest22,extent,com);
			com.navigateToConnectPuttyAndTimeShift("2");
            landing.Login();
			
			ExtentTest childTest23=extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest23);
			order=createChildTest(childTest23,extent,order);
			order.navigateToCustOrderPage("false",accntNum);
			order.cancelOrder();
			
			ExtentTest childTest24=extent.startTest("Navigate To COM,SOM and CLEC orders");
			test.appendChild(childTest24);
			order=createChildTest(childTest24,extent,order);
			order.openCOMOrderPage("false",accntNum, "Yes");
			
			ExtentTest childTest25=extent.startTest("Verify COM and SOM Record Counts");
			test.appendChild(childTest25);
			com=createChildTest(childTest25,extent,com);
			som=createChildTest(childTest25,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest26=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest26);
			orderHistory=createChildTest(childTest26,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Convert_PhNative_Ported_LSC_Cmpltd_DueDate_Reach_Cancle_Porting");
		} catch (Exception e) {
			log.error("Error in Convert_PhNative_Ported_LSC_Cmpltd_DueDate_Reach_Cancle_Porting:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-1")
	@SuppressWarnings(value={"rawtypes"})
	public void Verify_FFM_Customer_Requested_Porting_PhoneLine(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Verify FFM request when Customer requested a porting for a phone line");
			log.debug("Entering Verify_FFM_Customer_Requested_Porting_PhoneLine");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String phoneHardware=data.get("Phone_hardware");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardwareSerialNbr=PhSrlNo;
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

			ExtentTest childTest3 = extent.startTest("Add Port Phone Number");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String portPhoneNbr = order.portPhoneNumber("Phone Required", "", phoneProduct, "", "No", "No");
			order.selectVoiceMail();

			ExtentTest childTest4 = extent.startTest("Add Phone hardware Without Serial Nbr");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPhoneHardwareWithoutSlNos(phoneHardware);
			//order.selectTodo();

			ExtentTest childTest5 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.selectInstallationOption("", techAppointment);
			order.techAppoinmentYes("Yes", "Yes");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest6 = extent.startTest("Review and Finish");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest7 = extent.startTest("Navigate To COM, SOM Orders");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			String accntNum = order.getAccountNumber();
			String orderNum = order.getOrderNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest8 = extent.startTest("Navigate To Verify SVG Diagram");
			test.appendChild(childTest8);
			com = createChildTest(childTest8, extent, com);
			order = createChildTest(childTest8, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("");
			order.navigateToOrderPage();

			ExtentTest childTest9 = extent.startTest("Navigate To hit Wiring Status Details");
			test.appendChild(childTest9);
			soapRest = createChildTest(childTest9, extent, soapRest);
			soapRest = createChildTest(childTest9, extent, soapRest);
			soapRest.navigateToHitWiringStatus("TC137", portPhoneNbr, orderNum);

			ExtentTest childTest10 = extent.startTest("Send serial number for Phone hardware");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			order = createChildTest(childTest10, extent, order);
			order.navigateToOrderValidation(5, "No", "No", "COM");
			com.sendSerialNbrTask(phoneHardwareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest11 = extent.startTest("Navigate To LSC Job Run");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			com.navigateToJobMonitorURL("LSC");
			order.navigateToOrderPage();

			ExtentTest childTest12 = extent.startTest("Navigate To Check Wiring Status in Phone CFS Order");
			test.appendChild(childTest12);
			com = createChildTest(childTest12, extent, com);
			com.navigateToCheckWiringStatusinPhoneCFS("One");
			order.navigateToOrderPage();

			ExtentTest childTest13 = extent.startTest("Wait for Tech Confirm Mark Finished Task");
			test.appendChild(childTest13);
			com = createChildTest(childTest13, extent, com);
			order = createChildTest(childTest13, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest14 = extent.startTest("Navigate To Verify SVG Diagram");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			order = createChildTest(childTest14, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("Middle");
			order.navigateToOrderPage();
		
			ExtentTest childTest15=extent.startTest("Navigate To Initiate line activation for CLEC OPS task");
			test.appendChild(childTest15);
			order=createChildTest(childTest15,extent,order);
			landing=createChildTest(childTest15,extent,landing);
			order.navigateToCLECOPSUser("false", "Yes");
			landing.loggingOffCLECOPSUser();
			landing.Login();
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest16);
			com=createChildTest(childTest16,extent,com);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
		
			ExtentTest childTest17=extent.startTest("Verify COM, SOM and CLEC Record counts");
			test.appendChild(childTest17);
			com=createChildTest(childTest17, extent, com);
			som=createChildTest(childTest17, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			
			ExtentTest childTest18=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest18);
			orderHistory=createChildTest(childTest18,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Verify_FFM_Customer_Requested_Porting_PhoneLine");
		} catch (Exception e) {
			log.error("Error in Verify_FFM_Customer_Requested_Porting_PhoneLine:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
		
	@Test(dataProvider="getTestData", description="Priority-1")
	@SuppressWarnings(value={"rawtypes"})
	public void Verify_FFM_Customer_Requested_Porting_Multiple_PhoneLines(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Verify FFM request when Customer requested a porting for multiple phone lines");
			log.debug("Entering Verify_FFM_Customer_Requested_Porting_Multiple_PhoneLines");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String phoneHardware=data.get("Phone_hardware");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardwareSerialNbr=PhSrlNo;
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
			
			ExtentTest childTest0=extent.startTest("Navigate To LSC Response Job Normal");
			test.appendChild(childTest0);
			order=createChildTest(childTest0,extent,order);
			landing=createChildTest(childTest0, extent, landing);
			order.navigateToLSCResponseJobNormal();
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Port Phone Number TN1");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			String portPhoneNbr1=order.portPhoneNumber("Phone Required", "", phoneProduct, "", "No", "No");
			
			ExtentTest childTest4=extent.startTest("Add Port Phone Number TN2");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			String portPhoneNbr2=order.portPhoneNumber("", "Secondary", phoneProduct, "", "No", "No");
			
			ExtentTest childTest5=extent.startTest("Add Phone hardware Without Serial Nbr");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addPhoneHardwareWithoutSlNo(phoneHardware, "3rdProduct");
			
			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.selectInstallationOption("", techAppointment);
			order.techAppoinmentYes("Yes", "Yes");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();
			

			ExtentTest childTest7=extent.startTest("Review and Finish");
			test.appendChild(childTest7);
			order=createChildTest(childTest7, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest8=extent.startTest("Navigate To COM, SOM Orders");
			test.appendChild(childTest8);
			order=createChildTest(childTest8, extent, order);
			String accntNum=order.getAccountNumber();
			String orderNum=order.getOrderNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			
			ExtentTest childTest9=extent.startTest("Navigate To Verify SVG Diagram");
			test.appendChild(childTest9);
			com=createChildTest(childTest9,extent,com);
			order=createChildTest(childTest9,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("");
			order.navigateToOrderPage();
			
			ExtentTest childTest10=extent.startTest("Navigate To Send Wiring Status with TN1");
			test.appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			soapRest=createChildTest(childTest10, extent, soapRest);
			soapRest.navigateToHitWiringStatus("TC138", portPhoneNbr1, orderNum);
			
			ExtentTest childTest11=extent.startTest("Validate wiring status in phone line CFS order1");
			test.appendChild(childTest11);
			order=createChildTest(childTest11,extent,order);
			com=createChildTest(childTest11, extent, com);
			com.navigateToCheckWiringStatusinPhoneCFS("One");
			order.navigateToOrderPage();
			
			ExtentTest childTest12=extent.startTest("Navigate To Send Wiring Status with TN2");
			test.appendChild(childTest12);
			order=createChildTest(childTest12,extent,order);
			soapRest=createChildTest(childTest12, extent, soapRest);
			soapRest.navigateToHitWiringStatus("TC138", portPhoneNbr2, orderNum);
			
			/*
			 * ExtentTest childTest13=extent.
			 * startTest("Validate wiring status in phone line CFS order2");
			 * test.appendChild(childTest13);
			 * order=createChildTest(childTest13,extent,order);
			 * com=createChildTest(childTest13, extent, com);
			 * com.navigateToCheckWiringStatusinPhoneCFS("Two");
			 * order.navigateToOrderPage();
			 */
			
			ExtentTest childTest14=extent.startTest("Wait for Provisioning Mark Finished Task");
			test.appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			order=createChildTest(childTest14,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest15=extent.startTest("Send serial number for Phone hardware");
			test.appendChild(childTest15);
			com=createChildTest(childTest15, extent, com);
			order=createChildTest(childTest15, extent, order);
			order.navigateToOrderValidation(5, "No", "No", "COM");
			com.sendSerialNbrTask(phoneHardwareSerialNbr);
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Navigate To LSC Job Run");
			test.appendChild(childTest16);
			com=createChildTest(childTest16,extent,com);
			com.navigateToJobMonitorURL("LSC");
			order.navigateToOrderPage();
			
			ExtentTest childTest17=extent.startTest("Wait for LSR due date Mark Finished Task");
			test.appendChild(childTest17);
			com=createChildTest(childTest17,extent,com);
			order=createChildTest(childTest17,extent,order);
			order.navigateToOrderValidation(4, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest18=extent.startTest("Navigate To BLIF Job Run");
			test.appendChild(childTest18);
			com=createChildTest(childTest18,extent,com);
			com.navigateToJobMonitorURL("BLIF");
			order.navigateToOrderPage();
			
			ExtentTest childTest19=extent.startTest("Wait for LSR due date Mark Finished Task");
			test.appendChild(childTest19);
			com=createChildTest(childTest19,extent,com);
			order=createChildTest(childTest19,extent,order);
			order.navigateToOrderValidation(55, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest20=extent.startTest("Navigate To BLIF Job Run");
			test.appendChild(childTest20);
			com=createChildTest(childTest20,extent,com);
			com.navigateToJobMonitorURL("BLIF");
			order.navigateToOrderPage();
			
			ExtentTest childTest21=extent.startTest("Wait for Tech Confirm Mark Finished Task");
			test.appendChild(childTest21);
			com=createChildTest(childTest21,extent,com);
			order=createChildTest(childTest21,extent,order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
		
			ExtentTest childTest22=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest22);
			com=createChildTest(childTest22,extent,com);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			
			ExtentTest childTest23=extent.startTest("Verify COM, SOM and CLEC Record counts");
			test.appendChild(childTest23);
			com=createChildTest(childTest23, extent, com);
			som=createChildTest(childTest23, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			
			ExtentTest childTest24=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest24);
			orderHistory=createChildTest(childTest24,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Verify_FFM_Customer_Requested_Porting_Multiple_PhoneLines");
		} catch (Exception e) {
			log.error("Error in Verify_FFM_Customer_Requested_Porting_Multiple_PhoneLines:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	public static void main (String args[])
	{
		InputStream log4j=PortInCases.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
        PropertyConfigurator.configure(log4j);
	}
}	

