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

public class SuspendCases extends SeleniumTestUp {

	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	ValidateReport repo=null;
	OrderHistoryPage orderHistory=null;
	SoftAssert softAssert=new SoftAssert();
	ArrayList<String> valuesToPassForValidation=new ArrayList<String>();
    public static String orderPage=null;
	Logger log=Logger.getLogger(SuspendCases.class);
	
	@Test (dataProvider="getTestData", description="Golden Suite")
	@SuppressWarnings(value={"rawtypes"})
	public void Suspend_TriplePlay(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Verify AUP suspension of a Triple Play account with XB6-Converged device");
			log.debug("Entering Suspend_TriplePlay");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			String convergedHardware=data.get("Converged_hardware");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNo=ConvgdSrlNo;
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
			
			ExtentTest childTest4=extent.startTest("Add TV with TV Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest5=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);
			//order.selectConvergedHardware("Internet");
			//order.deleteHardware("Switch Window");
			
			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			
			ExtentTest childTest7=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			
			ExtentTest childTest8=extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.enteringChangesAuthorizedBy();

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

			ExtentTest childTest13=extent.startTest("CLEC Req After Job Run");
			test.appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest14=extent.startTest("Suspend Phone");
			test.appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			com.suspendLOBProducts("Phone Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("Phone"),"Active","Suspend Phone Status is not Correct");
			
			ExtentTest childTest15=extent.startTest("Suspend Internet");
			test.appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			com.suspendLOBProducts("Internet Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("Internet"),"Active","Suspend Internet Status is not Correct");
			
			ExtentTest childTest16=extent.startTest("Suspend TV");
			test.appendChild(childTest16);
			com=createChildTest(childTest16,extent,com);
			com.suspendLOBProducts("TV Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("TV"),"Active","Suspend TV Status is not Correct");

			ExtentTest childTest17=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest17);
			com=createChildTest(childTest17,extent,com);
			som=createChildTest(childTest17,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest18=extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest18);
			repo=createChildTest(childTest18,extent,repo);
			order=createChildTest(childTest18,extent,order);
			order.navigateToOrderValidation(29, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC1", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(30, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC1", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(31, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC1", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest19=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest19);
			orderHistory=createChildTest(childTest19,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Suspend_TriplePlay");
		} catch (Exception e) {
			log.error("Error in Suspend_TriplePlay:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test (dataProvider="getTestData", description="Priority-1")
	@SuppressWarnings(value={"rawtypes"})
	public void Suspend_TriplePlay_TCIS(Hashtable<String,String> data) throws Exception {
		try{
			test.log(LogStatus.INFO, "TestCase Name : ","Verify TCIS suspension of a Triple-Play Account with XB6-Converged and Blue-sky Device (Timeshifter Env)");
			log.debug("Entering Suspend_TriplePlay_TCIS");
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}
			String tvProduct=data.get("TV_Product");
			String internetProduct=data.get("Internet_Product");
			String phoneProduct=data.get("Phone_Product");
			String convergedHardware=data.get("Converged_hardware");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNo=ConvgdSrlNo;
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

			ExtentTest childTest3=extent.startTest("Add Phone, Internet Products");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add TV and TV Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);

			ExtentTest childTest5=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNo);
			order.addConvergedHardware1(convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);
			order.selectTodoListCheckBox();

			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNo("On a date", "", "");
			
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

			ExtentTest childTest13=extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest13);
			order=createChildTest(childTest13,extent,order);
			order.navigateToCustOrderPage("true", accntNum);

			ExtentTest childTest14=extent.startTest("Click On Operations");
			test.appendChild(childTest14);
			order=createChildTest(childTest14,extent,order);
			order.navigateToOperations("Current Date");

			ExtentTest childTest15=extent.startTest("Temporary Suspend Phone Internet and TV");
			test.appendChild(childTest15);
			order=createChildTest(childTest15,extent,order);
			order.tempSuspendTriplePlay();

			ExtentTest childTest16=extent.startTest("Enter Installation Fee");
			test.appendChild(childTest16);
			order=createChildTest(childTest16,extent,order);
			order.enterInstallationFee("0.00");
			order.changesAuthorizedBy();
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			
			ExtentTest childTest17=extent.startTest("Review and Finish");
			test.appendChild(childTest17);
			order=createChildTest(childTest17,extent,order);
			order.reviewAndFinishOrder();
			//order.switchPreviousTab();
			order.openCOMOrderPage("true",accntNum, "Yes");
			
			ExtentTest childTest19 = extent.startTest("Wait For Effective Date Mark Finish");
			test.appendChild(childTest19);
			com = createChildTest(childTest19, extent, com);
			order = createChildTest(childTest19, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask(); 
			order.navigateToOrderPage(); 			
			order.navigateToOrderValidation(26, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest18=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest18);
			com=createChildTest(childTest18,extent,com);
			som=createChildTest(childTest18,extent,som);
			com.navigateToCOMOrder();
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest22=extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest22);
			order=createChildTest(childTest22,extent,order);
			repo=createChildTest(childTest22,extent,repo);	
			order.navigateToOrderValidation(29, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum,phoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC21", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(30, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC21", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(32, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway=repo.validateSOMIntegrationReportsSpcl(xls,"TC21", 3, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway,"Attributes are not validated successfully");
			if(!is4xgateway) 
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC21", 3, valuesToPassForValidation),"Attributes are not validated successfully");		
			}
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(31, "Yes", "No", "SOM");
			if(is4xgateway) 
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC21", 4, valuesToPassForValidation),"Attributes are not validated successfully");				
			}
			else
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC21", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();
			
			ExtentTest childTest20=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest20);
			repo=createChildTest(childTest20,extent,repo); 
			order=createChildTest(childTest20,extent,order); 
			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum,phoneNbr,convergedHardWareSerialNo,tvBoxSerialNbr,tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC21", 1, valuesToPassForValidation),"Attributes are not validated successfully");		
			order.navigateToOrderPage();
			
			ExtentTest childTest21=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest21);
			orderHistory=createChildTest(childTest21,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Suspend_TriplePlay_TCIS");
		} catch (Exception e) {
			log.error("Error in Suspend_TriplePlay_TCIS:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Suspend_DPT_Hitron_Moxi(Hashtable<String,String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Validate AUP suspend for an account with- DPT, Hitron and Legacy Gateway(Moxi)");
			log.debug("Entering Suspend_DPT_Hitron_Moxi");
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String phoneHardware=data.get("Phone_hardware");
			String internetProduct=data.get("Internet_Product");
			String internetHardware=data.get("Internet_hardware");
			String tvProduct=data.get("TV_Product");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardWareSerialNbr=PhSrlNo;
			TVMoxiSrlNo=Utility.generateHardwareSerailNum(TVMoxiSrlNo);
			String tvHardwareSerialNbr=TVMoxiSrlNo;
			String[] hardwareType={"ShawGatewayHDPVR"};
			String[] serialNumber={tvHardwareSerialNbr};
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
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

			ExtentTest childTest3=extent.startTest("Add Phone Hardware");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addPhoneHardware(phoneHardWareSerialNbr,phoneHardware);
			
			ExtentTest childTest4=extent.startTest("Add Internet Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			//order.deleteConvergedHardware();
			
			ExtentTest childTest5=extent.startTest("Add TV Hardware");
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
			
			ExtentTest childTest10=extent.startTest("CLEC Request Before job Run");
			test.appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11=extent.startTest("Navigate to E911 Job run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			order=createChildTest(childTest11,extent,order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			
			ExtentTest childTest12=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest13=extent.startTest("Suspend Phone");
			test.appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			com.suspendLOBProducts("Phone Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("Phone"),"Active","Suspend Phone Status is not Correct");
			
			ExtentTest childTest14=extent.startTest("Suspend Internet");
			test.appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			com.suspendLOBProducts("Internet Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("Internet"),"Active","Suspend Internet Status is not Correct");
			
			ExtentTest childTest15=extent.startTest("Suspend TV");
			test.appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			com.suspendLOBProducts("TV Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("TV"),"Active","Suspend TV Status is not Correct");

			ExtentTest childTest16=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest16);
			com=createChildTest(childTest16,extent,com);
			som=createChildTest(childTest16,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest17=extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest17);
			repo=createChildTest(childTest17,extent,repo);
			order=createChildTest(childTest17,extent,order);
			order.navigateToOrderValidation(29, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC65", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			som.navigateToSOMOrder();
			
			order.navigateToOrderValidation(30, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC65", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			som.navigateToSOMOrder();
			
			order.navigateToOrderValidation(31, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC65", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest18=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest18);
			orderHistory=createChildTest(childTest18,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Suspend_DPT_Hitron_Moxi");
		} catch (Exception e) {
			log.error("Error in Suspend_DPT_Hitron_Moxi:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void TCIS_Suspension_TriplePlay_DPT_Hitron_LegacyTV(Hashtable<String,String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Verify modification of a TCIS suspension order for a Triple-Play Account with DPT, Hitron and TV Legacy device.Also validate the cancellation of the above order(Timeshifter Env)");
			log.debug("Entering TCIS_Suspension_TriplePlay_DPT_Hitron_LegacyTV");
			if(data.get("Run").equals("No")){
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
            String internetProduct=data.get("Internet_Product");
            String internetHardware=data.get("Internet_hardware");
            String tvProduct=data.get("TV_Product");
            PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
            String phoneHardWareSerialNbr=PhSrlNo;
            String phoneHardware=data.get("Phone_hardware");
            TVDctSrlNo=Utility.generateHardwareSerailNum(TVDctSrlNo); 
            String tvHardwareSerialNbr=TVDctSrlNo;
            String[] hardwareType={"DCT700"};
			String[] serialNumber={tvHardwareSerialNbr};
            IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
            String internetHardwareSerialNbr=IntHitronSrlNo;
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

            ExtentTest childTest3=extent.startTest("Add Phone and Phone Hardware");
            test.appendChild(childTest3);
            order=createChildTest(childTest3,extent,order);
            order.addServicesAndFeacturesTab();
            order.addServicePhone("Add Product", phoneProduct);
            order.selectVoiceMail();
            order.addPhoneHardware(phoneHardWareSerialNbr,phoneHardware);
            
            ExtentTest childTest4=extent.startTest("Add Internet and Hitron Hardware");
            test.appendChild(childTest4);
            order=createChildTest(childTest4,extent,order);
            order.addServiceInternet(internetProduct);
            order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
            //order.deleteConvergedHardware();
            
            ExtentTest childTest5=extent.startTest("Add TV and TV Hardware");
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

            ExtentTest childTest10=extent.startTest("CLEC");
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
            
            ExtentTest childTest13=extent.startTest("Verify COM and SOM Record counts");
            test.appendChild(childTest13);
            com=createChildTest(childTest13,extent,com);
            som=createChildTest(childTest13,extent,som);
            com.verifyRecordsCountInCOMOrder(comOrderCount);
            som.verifyRecordsCountInSOMOrder(somOrderCount);
            com.verifyRecordsCountInCLECOrder(clecOrderCount);
            
            ExtentTest childTest14=extent.startTest("Navigate to Order History Page");
            test.appendChild(childTest14);
            orderHistory=createChildTest(childTest14,extent,orderHistory);
            orderHistory.navigateToOrderHistoryUrl(accntNum, "");
            
			log.debug("Leaving TCIS_Suspension_TriplePlay_DPT_Hitron_LegacyTV");
		} catch (Exception e) {
			log.error("Error in TCIS_Suspension_TriplePlay_DPT_Hitron_LegacyTV:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Resumption_AUP_Suspended_TriplePlay_XB6Converged(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Verify resumption of a AUP Suspended Triple Play account with XB6-Converged device");
			log.debug("Entering Resumption_AUP_Suspended_TriplePlay_XB6Converged");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			String convergedHardware=data.get("Converged_hardware");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNo=ConvgdSrlNo;
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

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest,extent,landing);
			order=createChildTest(childTest,extent,order);
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
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNo);
			order.addConvergedHardware1(convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);

			ExtentTest childTest5 = extent.startTest("Add TV with Bluesky Hardwares");
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
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest10 = extent.startTest("Clec Request Before Job Run");
			test.appendChild(childTest10);
			com = createChildTest(childTest10, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11 = extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com = createChildTest(childTest11, extent, com);
			order = createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("Clec Request After Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest13=extent.startTest("Suspend Phone");
			test.appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			com.suspendLOBProducts("Phone Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("Phone"),"Active","Suspend Phone Status is not Correct");
			
			ExtentTest childTest14=extent.startTest("Suspend Internet");
			test.appendChild(childTest14);
			com=createChildTest(childTest14,extent,com);
			com.suspendLOBProducts("Internet Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("Internet"),"Active","Suspend Internet Status is not Correct");
			
			ExtentTest childTest15=extent.startTest("Suspend TV");
			test.appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			com.suspendLOBProducts("TV Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("TV"),"Active","Suspend TV Status is not Correct");

			ExtentTest childTest16=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest16);
			order=createChildTest(childTest16,extent,order);
			com=createChildTest(childTest16,extent,com);
			som=createChildTest(childTest16,extent,som);
			com.navigateToCOMOrder();
			som.navigateToSOMOrder();
			
			ExtentTest childTest17=extent.startTest("Resume Phone");
			test.appendChild(childTest17);
			com=createChildTest(childTest17,extent,com);
			com.resumeLOBProducts("Phone Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("Phone"),"Inactive","Resume Phone Status is not Correct");
			
			ExtentTest childTest18=extent.startTest("Resume Internet");
			test.appendChild(childTest18);
			com=createChildTest(childTest18,extent,com);
			com.resumeLOBProducts("Internet Record");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("Internet"),"Inactive","Resume Internet Status is not Correct");
			
			ExtentTest childTest19=extent.startTest("Resume TV Hardware1");
			test.appendChild(childTest19);
			com=createChildTest(childTest19,extent,com);
			com.resumeLOBProducts("TVHardware1");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("TV"),"Inactive","Suspend TV Status is not Correct");
			
			ExtentTest childTest20=extent.startTest("Resume TV Hardware2");
			test.appendChild(childTest20);
			com=createChildTest(childTest20,extent,com);
			com.resumeLOBProducts("TVHardware2");
			Assert.assertEquals(com.NavigateTosuspendStatusCheck("TV"),"Inactive","Suspend TV Status is not Correct");
			
			ExtentTest childTest21=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest21);
			com=createChildTest(childTest21,extent,com);
			som=createChildTest(childTest21,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest22=extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest22);
			order=createChildTest(childTest22,extent,order);
			repo=createChildTest(childTest22,extent,repo);	
			order.navigateToOrderValidation(25, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC107", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(28, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC107", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(26, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC107", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			/*order.navigateToOrderValidation(27, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC107", 4, valuesToPassForValidation),"Attributes are not validated successfully");				
			order.navigateToOrderPage();*/
			
			ExtentTest childTest23=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest23);
			repo=createChildTest(childTest23,extent,repo); 
			order=createChildTest(childTest23,extent,order); 
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNo, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC107", 1, valuesToPassForValidation),"Attributes are not validated successfully");		
			order.navigateToOrderPage();
			
			ExtentTest childTest24=extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest24);
			orderHistory=createChildTest(childTest24,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Resumption_AUP_Suspended_TriplePlay_XB6Converged");
		} catch (Exception e) {
			log.error("Error in Resumption_AUP_Suspended_TriplePlay_XB6Converged:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Services_Suspend_Provision_CustConfirmation_PremiseMove_withoutGAP(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Services Suspend on Order Submission for Provision on Customer Confirmation while doing Premise Move without a GAP");
			log.debug("Entering Services_Suspend_Provision_CustConfirmation_PremiseMove_withoutGAP");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			String internetHardware=data.get("Internet_hardware");
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
			TVDctSrlNo=Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr=TVDctSrlNo;
			String[] hardwareType={"DCT700"};
			String[] serialNumber={tvHardwareSerialNbr};
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

			ExtentTest childTest3=extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);
			
			ExtentTest childTest4=extent.startTest("Add Internet Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			
			ExtentTest childTest5=extent.startTest("Add TV with TV DCT Hardware");
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
			order.openCOMOrderPage("true", accntNum, "");
			
			ExtentTest childTest10=extent.startTest("Navigate To Check COM & SOM Initial Records Counts");
			test.appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			com=createChildTest(childTest10,extent,com);
			som=createChildTest(childTest10,extent,som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);
			
			ExtentTest childTest11=extent.startTest("Navigate Vancover New Location 2777");
			test.appendChild(childTest11);
			order=createChildTest(childTest11, extent, order);
			order.navigateToVancoverLocationId(accntNum);
			
			ExtentTest childTest12=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest12);
			order=createChildTest(childTest12,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNo("On confirmation", "", "");
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest13=extent.startTest("Review and Finish");
			test.appendChild(childTest13);
			order=createChildTest(childTest13,extent,order);
			order.reviewAndFinishOrder();
			
			ExtentTest childTest14=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest14);
			order=createChildTest(childTest14,extent,order);
			order.openCOMOrderPage("false", accntNum, "");
			
			ExtentTest childTest15=extent.startTest("Navigate To Modify Cust Order and Check SVG Diagram");
			test.appendChild(childTest15);
			order=createChildTest(childTest15,extent,order);
			com=createChildTest(childTest15,extent,com);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.verifySVGDiagram("Middle");
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Open CA in Order History page for Activation");
			test.appendChild(childTest16);
			orderHistory=createChildTest(childTest16, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
//			wait(2);
			orderHistory.navigateToOrderHistoryActivate();
			
			ExtentTest childTest17=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest17);
			order=createChildTest(childTest17,extent,order);
			order.openCOMOrderPage("false", accntNum, "Yes");
			
			ExtentTest childTest18=extent.startTest("Verify COM and SOM & CLEC Record counts");
			test.appendChild(childTest18);
			com=createChildTest(childTest18,extent,com);
			som=createChildTest(childTest18,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest19=extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest19);
			orderHistory=createChildTest(childTest19,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Services_Suspend_Provision_CustConfirmation_PremiseMove_withoutGAP");
		} catch (Exception e) {
			log.error("Error in Services_Suspend_Provision_CustConfirmation_PremiseMove_withoutGAP:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	public static void main (String args[])
	{
		InputStream log4j=SuspendCases.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
        PropertyConfigurator.configure(log4j);
	}	
}	

