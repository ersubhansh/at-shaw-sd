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

public class HardwareSwapCases extends SeleniumTestUp {

	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	ValidateReport repo=null;
	OrderHistoryPage orderHistory=null;
	SoftAssert softAssert=new SoftAssert();
	ArrayList<String> valuesToPassForValidation=new ArrayList<String>();
	
	Logger log=Logger.getLogger(HardwareSwapCases.class);

	@Test (dataProvider="getTestData", description="Golden Suite")
	@SuppressWarnings(value={"rawtypes"})
	public void Triple_Play_Hardware_Swap(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","For a Triple Play account with XB6-Converged and Blue-sky Device, perform hardware swaps through FFM");
			log.debug("Entering Triple_Play_Hardware_Swap");
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
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String swapConvergedHarwareNbr=ConvgdSrlNo;
			TVBoxSrlNo=Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSwapSerialNbr=TVBoxSrlNo;
			TVPortalSrlNo=Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSwapSerialNbr=TVPortalSrlNo;
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

			ExtentTest childTest4=extent.startTest("Add TV With BlueSky");
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
			//order.deleteHardware("Switch Window");
						
			ExtentTest childTest6=extent.startTest("Navigate To select Todo msg");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.selectTodoListCheckBox();

			ExtentTest childTest7=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techActivationOption("");
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

			ExtentTest childTest10=extent.startTest("Navigate To COM & SOM Order");
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
			order=createChildTest(childTest12,extent,order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest13=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			com.verifyCLECRequestAfterJobRun();
		
			ExtentTest childTest14=extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest14);
			order=createChildTest(childTest14,extent,order);
			order.navigateToCustOrderPage("true",accntNum);

			ExtentTest childTest15=extent.startTest("Swap Converged Hardware");
			test.appendChild(childTest15);
			order=createChildTest(childTest15,extent,order);
			order.swapConvergedHardware(swapConvergedHarwareNbr);

			ExtentTest childTest16=extent.startTest("Swap Bluesky TV Hardware");
			test.appendChild(childTest16);
			order=createChildTest(childTest16,extent,order);
			order.addServicesAndFeacturesTab();
			order.swapServiceTVBlueSky(tvBoxSwapSerialNbr,tvPortalSwapSerialNbr);
			//order.selectTodoListCheckBox();
			order.addServicesAndFeacturesTab();
			order.selectTodo();
			
			ExtentTest childTest17=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest17);
			order=createChildTest(childTest17,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techActivationOption("");
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest18=extent.startTest("Review and Finish");
			test.appendChild(childTest18);
			order=createChildTest(childTest18,extent,order);
			order.reviewAndFinishOrder();
			//com.switchPreviousTab();
			order.openCOMOrderPage("true",accntNum, "Yes");

			ExtentTest childTest19=extent.startTest("Verify COM, SOM & CLEC Record Counts");
			test.appendChild(childTest19);
			com=createChildTest(childTest19,extent,com);
			som=createChildTest(childTest19,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest20=extent.startTest("Verify SOM Integration Reports");
			test.appendChild(childTest20);
			repo=createChildTest(childTest20,extent,repo);
			order=createChildTest(childTest20,extent,order);
			order.navigateToOrderValidation(33, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,phoneNbr,convergedHardWareSerialNbr));
			boolean isDETACH=repo.validateSOMIntegrationReportsSpcl(xls,"TC3", 1, valuesToPassForValidation);
			if (!isDETACH)
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,convergedHardWareSerialNbr));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC3", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC3", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(34, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,convergedHardWareSerialNbr));
			isDETACH=repo.validateSOMIntegrationReportsSpcl(xls,"TC3", 3, valuesToPassForValidation);
			if (!isDETACH)
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,convergedHardWareSerialNbr));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC3", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC3", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(35, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway=repo.validateSOMIntegrationReportsSpcl(xls,"TC3", 5, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway,"Attributes are not validated successfully");
			if(!is4xgateway)
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC3", 5, valuesToPassForValidation),"Attributes are not validated successfully");		
			}
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(36, "Yes", "No", "SOM");
			if(is4xgateway) 
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC3", 6, valuesToPassForValidation),"Attributes are not validated successfully");				
			}
			else
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC3", 6, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();
			
			ExtentTest childTest21=extent.startTest("Verify COM Integration Reports");
			test.appendChild(childTest21);
			repo=createChildTest(childTest21,extent,repo);
			order=createChildTest(childTest21,extent,order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,phoneNbr,convergedHardWareSerialNbr,swapConvergedHarwareNbr,tvBoxSerialNbr,tvPortalSerialNbr,tvBoxSwapSerialNbr,tvPortalSwapSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC3", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,phoneNbr,convergedHardWareSerialNbr,swapConvergedHarwareNbr,tvBoxSerialNbr,tvPortalSerialNbr,tvBoxSwapSerialNbr,tvPortalSwapSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC3", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest22=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest22);
			orderHistory=createChildTest(childTest22,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Triple_Play_Hardware_Swap");
		} catch (Exception e) {
			log.error("Error in Triple_Play_Hardware_Swap:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Tripleplay_DPT_Cisco_legacyTV_Swap_Through_Order_Entry(Hashtable<String, String> data)throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","For a Triple play account with DPT, Cisco Advanced Wi-Fi modem and legacy TV device , perform hardware swaps through Order Entry");
			log.debug("Entering Tripleplay_DPT_Cisco_legacyTV_Swap_Through_Order_Entry");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String phoneHardware=data.get("Phone_hardware");
			String internetHardware=data.get("Internet_hardware");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNbr=PhSrlNo;
			String internetProduct=data.get("Internet_Product");
			IntACSrlNo=Utility.generateHardwareSerailNum(IntACSrlNo);
			String internetHardwareSerialNbr=IntACSrlNo;
			String tvProduct=data.get("TV_Product");
			TVMoxiSrlNo=Utility.generateHardwareSerailNum(TVMoxiSrlNo);
			String tvHardwareSerialNbr1=TVMoxiSrlNo;
			//TVDctSrlNo=Utility.generateHardwareSerailNum(TVDCXSrlNo);
			TVDctSrlNo=Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr2=TVDctSrlNo;
			String[] hardwareType={"DCT700", "ShawGatewayHDPVR"};
			String[] serialNumber={tvHardwareSerialNbr2, tvHardwareSerialNbr1};
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String swapPhoneHardwareSerialNbr=PhSrlNo;
			IntACSrlNo=Utility.generateHardwareSerailNum(IntACSrlNo);
			String swapInternetHardwareSerialNbr=IntACSrlNo;
			TVMoxiSrlNo=Utility.generateHardwareSerailNum(TVMoxiSrlNo);
			String swapTVHardwareSlNbr1=TVMoxiSrlNo;
			TVDctSrlNo=Utility.generateHardwareSerailNum(TVDctSrlNo);
			String swapTVHardwareSlNbr2=TVDctSrlNo;
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

			ExtentTest childTest3=extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();

			ExtentTest childTest4=extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneSerialNbr, phoneHardware);

			ExtentTest childTest5=extent.startTest("Add Internet and Internet Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);

			ExtentTest childTest6=extent.startTest("Add TV With MOXI Gateway and legecy Hardwares");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

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
			com=createChildTest(childTest11, extent, com);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
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
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			order.openCOMOrderPage("true", accntNum, "Yes");
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest14=extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			order.navigateToCustOrderPage("true", accntNum);
		
			ExtentTest childTest15=extent.startTest("Swap Phone Hardware");
			test.appendChild(childTest15);
			order=createChildTest(childTest15, extent, order);
			order.swapPhoneHardware(swapPhoneHardwareSerialNbr);

			ExtentTest childTest16=extent.startTest("Swap Internet Hardware");
			test.appendChild(childTest16);
			order=createChildTest(childTest16, extent, order);
			order.swapInternetHardware(swapInternetHardwareSerialNbr, internetProduct);

			ExtentTest childTest17=extent.startTest("Swap TV MOXI Gateway and legecy Hardwares");
			test.appendChild(childTest17);
			order=createChildTest(childTest17, extent, order);
			order.swapTVMOXIandLegecyHardwares(swapTVHardwareSlNbr1, swapTVHardwareSlNbr2);

			ExtentTest childTest18=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest18);
			order=createChildTest(childTest18, extent, order);
			order.addServicesAndFeacturesTab();
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();
			order.addServicesAndFeacturesTab();
			order.selectTodo();

			ExtentTest childTest19=extent.startTest("Review and Finish");
			test.appendChild(childTest19);
			order=createChildTest(childTest19, extent, order);
			order.reviewAndFinishOrder();
			
			ExtentTest childTest20=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest20);
			order=createChildTest(childTest20, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest21=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest21);
			com=createChildTest(childTest21, extent, com);
			som=createChildTest(childTest21, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest22=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest22);
			repo=createChildTest(childTest22, extent, repo);
			order=createChildTest(childTest22,extent,order);
			order.navigateToOrderValidation(33, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC48", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
//			order.navigateToOrderValidation(34, "Yes", "No", "SOM");
//			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
//			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC48", 2, valuesToPassForValidation),"Attributes are not validated successfully");
//			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(35, "Yes", "No", "SOM");
			order.navigateToOrderPage();
			
//			order.navigateToOrderValidation(8, "Yes", "No", "SOM");
//			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, swapInternetHardwareSerialNbr));
//			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC48", 5, valuesToPassForValidation),"Attributes are not validated successfully");
//			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(12, "Yes", "No", "SOM");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(13, "Yes", "No", "SOM");
			order.navigateToOrderPage();
			
			ExtentTest childTest23=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest23);
			repo=createChildTest(childTest23, extent, repo);
			order=createChildTest(childTest23,extent,order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneSerialNbr, internetHardwareSerialNbr,tvHardwareSerialNbr1, tvHardwareSerialNbr2, swapPhoneHardwareSerialNbr,swapInternetHardwareSerialNbr, swapTVHardwareSlNbr1, swapTVHardwareSlNbr2));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC48", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();	
			
			order.navigateToOrderValidation(6, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC48", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(9, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC48", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(14, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(tvHardwareSerialNbr2));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC48", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(tvHardwareSerialNbr1));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC48", 5, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest24=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest24);
			orderHistory=createChildTest(childTest24, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Tripleplay_DPT_Cisco_legacyTV_Swap_Through_Order_Entry");
		} catch (Exception e) {
			log.error("Error in Tripleplay_DPT_Cisco_legacyTV_Swap_Through_Order_Entry:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Swap_XB6_Account_xPOD_Package(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Swap XB6 on an account with xPOD package");
			log.debug("Entering Swap_XB6_Account_xPOD_Package");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			String convergedHardware=data.get("Converged_hardware");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			XPODSrlNo=Utility.generateHardwareSerailNum(XPODSrlNo);
			String xpod1PKValue=XPODSrlNo;
			XPODSrlNo=Utility.generateHardwareSerailNum(XPODSrlNo);
			String xpodValue1=XPODSrlNo;
			XPODSrlNo=Utility.generateHardwareSerailNum(XPODSrlNo);
			String xpodValue2=XPODSrlNo;
			XPODSrlNo=Utility.generateHardwareSerailNum(XPODSrlNo);
			String xpodValue3=XPODSrlNo;
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String swapConvergedHarwareNbr=ConvgdSrlNo;
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
			order.customerInformation("Staff","Yes");
			
			ExtentTest childTest3=extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);		
			
			ExtentTest childTest4 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
//			order.deleteHardware("Switch Window");

 			ExtentTest childTest5=extent.startTest("Add Rental xPODS");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.addRentalXpods("Staff", xpodValue1, xpodValue2, xpodValue3, xpod1PKValue, "true");
					
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
			
			ExtentTest childTest9=extent.startTest("Navigate To COM & SOM Order");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			com=createChildTest(childTest9,extent,com);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",accntNum, "");
			com.navigateToCOMOrder();
			
			ExtentTest childTest10=extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			order.navigateToCustOrderPage("false",accntNum);
			
			ExtentTest childTest11=extent.startTest("Swap Converged Hardware");
			test.appendChild(childTest11);
			order=createChildTest(childTest11,extent,order);
			order.swapConvergedHardware(swapConvergedHarwareNbr);
			//order.selectTodoListCheckBox();
			order.selectTodo();
			
			ExtentTest childTest12=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest12);
			order=createChildTest(childTest12,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest13=extent.startTest("Review and Finish");
			test.appendChild(childTest13);
			order=createChildTest(childTest13,extent,order);
			order.clickFinishOrder();
			
			ExtentTest childTest14=extent.startTest("Navigate To COM & SOM Order");
			test.appendChild(childTest14);
			order=createChildTest(childTest14,extent,order);
			order.openCOMOrderPage("false",accntNum, "");
			
			ExtentTest childTest15=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			som=createChildTest(childTest15,extent,som);
			//com.switchPreviousTab();
			order.openCOMOrderPage("false",accntNum, "");
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest16=extent.startTest("Verify COM Integration Reports");
			test.appendChild(childTest16);
			repo=createChildTest(childTest16,extent,repo);
			order=createChildTest(childTest16,extent,order);
//			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC73", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, swapConvergedHarwareNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC73", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
//			Sorder.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr, swapConvergedHarwareNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC73", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest17=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest17);
			orderHistory=createChildTest(childTest17,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Swap_XB6_Account_xPOD_Package");
		} catch (Exception e) {
			log.error("Error in Swap_XB6_Account_xPOD_Package:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void HardwareSwap_From_Immediate_ScenariosTab_BRM_SOA(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Verify that for a Hardware swap order from the immediate scenarios tab, OM will include a new field 'order completion date' in the 'Customer Order' integration call to BRM-SOA. Order completion date will be set to current date and time");
			log.debug("Entering HardwareSwap_From_Immediate_ScenariosTab_BRM_SOA");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			String convergedHardware=data.get("Converged_hardware");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String swapConvergedHarwareNbr=ConvgdSrlNo;
			TVDCXSrlNo=Utility.generateHardwareSerailNum(TVDCXSrlNo);
			String tvHardwareSerialNbr=TVDCXSrlNo;
			String[] hardwareType={"DCX3200M"};
			String[] serialNumber={tvHardwareSerialNbr};
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

			ExtentTest childTest3=extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			
			ExtentTest childTest4=extent.startTest("Add Internet with Converged Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);

			ExtentTest childTest5=extent.startTest("Add TV With TV Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);

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

			ExtentTest childTest13=extent.startTest("Navigate to Immediate Scenario Location");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			com=createChildTest(childTest13, extent, com);
			order.navigateToImmediateChangesPage(accntNum);

			ExtentTest childTest14=extent.startTest("Swap Interent Converged Hardware");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			com=createChildTest(childTest14, extent, com);
			com.swapImmediateConvrgdHardware("SecondSwap", swapConvergedHarwareNbr);
			order.navigateToOrderPage();

			ExtentTest childTest15=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest15);
			com=createChildTest(childTest15, extent, com);
			som=createChildTest(childTest15, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest16=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest16);
			repo=createChildTest(childTest16, extent, repo);
			order=createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC106", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest17=extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest17);
			orderHistory=createChildTest(childTest17, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving HardwareSwap_From_Immediate_ScenariosTab_BRM_SOA");
		} catch (Exception e) {
			log.error("Error in HardwareSwap_From_Immediate_ScenariosTab_BRM_SOA:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	public static void main (String args[])
	{
		InputStream log4j=HardwareSwapCases.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
		PropertyConfigurator.configure(log4j);
	}
}	
