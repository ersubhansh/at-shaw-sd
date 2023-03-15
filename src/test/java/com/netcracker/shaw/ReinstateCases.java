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

public class ReinstateCases extends SeleniumTestUp {

	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	ValidateReport repo=null;
	OrderHistoryPage orderHistory=null;
	SoftAssert softAssert=new SoftAssert();
	ArrayList<String> valuesToPassForValidation=new ArrayList<String>();

	Logger log=Logger.getLogger(ReinstateCases.class);
	
	@Test (dataProvider="getTestData", description="Priority-1")
	@SuppressWarnings(value={"rawtypes"})
	public void Disconnected_Resinstate_PastResource_CustOwned_Devices(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","For a fully disconnected account with DCT,DCX Device, perform a re-instate with past resources.(Use customer owned Devices)");
			log.debug("Entering Resinstate_Triple_Play_With_BlueSkyBox_Portal");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String tvProduct=data.get("TV_Product");
			CustomerProvidedTVSrlNo=Utility.generateHardwareSerailNum(CustomerProvidedTVSrlNo);
			String custOwnedTVSrlNbr=CustomerProvidedTVSrlNo;
			String techAppointment=data.get("Tech_Appointment");
			String disconnectReason=data.get("Disconnect_Reasons");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			
			ExtentTest childTest0=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0,extent,landing);
			order=createChildTest(childTest0,extent,order);
			order.navigateFirstToOeStubServer();
			landing.Login();
		
			ExtentTest childTest1=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest1);
			landing=createChildTest(childTest1, extent, landing);
			landing.openUrl();

			ExtentTest childTest2=extent.startTest("Fill Customer Info");
			test.appendChild(childTest2);
			order=createChildTest(childTest2,extent,order);
			order.customerInformation("Residential","Yes");
			order.addServicesAndFeacturesTab();

			ExtentTest childTest4=extent.startTest("Add TV With Customer owned Equipment");
			test.appendChild(childTest4);
			order=createChildTest(childTest4,extent,order);
			order.addServiceTV(tvProduct);
			order.addingCustomerProvidedEquipments("tv", custOwnedTVSrlNbr);

			ExtentTest childTest5=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest5);
			order=createChildTest(childTest5,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest6=extent.startTest("Review and Finish");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest7=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",accntNum, "");
			
			ExtentTest childTest8=extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.navigateToCustOrderPage("true", accntNum);
			
			ExtentTest childTest9=extent.startTest("Disconnecting TV LOB");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			order.disconnectLOBProducts("TV");
			
			ExtentTest childTest10=extent.startTest("Navigate To Tech Appointment For Disconnect");
			test.appendChild(childTest10);
			order=createChildTest(childTest10, extent,order);
			order.selectCalenderCurrentDate();
			
			ExtentTest childTest11=extent.startTest("Select Disconnect Reason");
			test.appendChild(childTest11);
			order=createChildTest(childTest11, extent,order);
			order.selectDisconnectControllabeReasons(disconnectReason);
			order.selectingSalesRepresentativeCheckBox();
			
			ExtentTest childTest12=extent.startTest("Select Customer Mailing Address For Disconnection");
			test.appendChild(childTest12);
			order=createChildTest(childTest12, extent,order);
			order.addCustomerMailingAddress("Service Location", "");

			ExtentTest childTest13=extent.startTest("Review and Finish");
			test.appendChild(childTest13);
			order=createChildTest(childTest13,extent,order);
			order.reviewAndFinishOrder();
	
			ExtentTest childTest14=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest14);
			order=createChildTest(childTest14,extent,order);
			com=createChildTest(childTest14,extent,com);
			som=createChildTest(childTest14,extent,som);
			order.navigateToOrderPage();
			com.navigateToCOMOrder();
			som.navigateToSOMOrder();
			
			ExtentTest childTest15=extent.startTest("Wait for Effective date Mark Finish task");
			test.appendChild(childTest15);
			com=createChildTest(childTest15, extent, com);
			order=createChildTest(childTest15, extent, order);
			order.navigateToOrderValidation(35, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			order.refreshPage();
			order.refreshPage();
			
			ExtentTest childTest16=extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest16);
			order=createChildTest(childTest16,extent,order);
			order.navigateToCustOrderPage("false", accntNum);
			
			ExtentTest childTest17=extent.startTest("Select Reinstate Account with Past Resources");
			test.appendChild(childTest17);
			order=createChildTest(childTest17,extent,order);
			order.reinstatePastResource("Past Resources");
			
			ExtentTest childTest18=extent.startTest("Selecting billing preferences");
			test.appendChild(childTest18);
			order=createChildTest(childTest18,extent,order);
			order.selectBillType("Paper");
			
			ExtentTest childTest19=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest19);
			order=createChildTest(childTest19,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();
			//order.addServicesAndFeacturesTab();
			//order.selectTodo();
			
			ExtentTest childTest20=extent.startTest("Review and Finish");
			test.appendChild(childTest20);
			order=createChildTest(childTest20,extent,order);
			order.reviewAndFinishOrder();
			
			ExtentTest childTest21=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest21);
			order=createChildTest(childTest21,extent,order);
			order.openCOMOrderPage("false",accntNum, "");
			
			ExtentTest childTest22=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest22);
			com=createChildTest(childTest22,extent,com);
			som=createChildTest(childTest22,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest23=extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest23);
			repo=createChildTest(childTest23,extent,repo);
			order=createChildTest(childTest23,extent,order);
			order.navigateToOrderValidation(11, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,custOwnedTVSrlNbr)); 
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC22", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest24=extent.startTest("Validate COM Integration Report");
			test.appendChild(childTest24);
			repo=createChildTest(childTest24,extent,repo);
			order=createChildTest(childTest24,extent,order);
			order.navigateToOrderValidation(2, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, custOwnedTVSrlNbr)); 
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC22", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest25=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest25);
			orderHistory=createChildTest(childTest25,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Disconnected_Resinstate_PastResource_CustOwned_Devices");
		} catch (Exception e) {
			log.error("Error in Disconnected_Resinstate_PastResource_CustOwned_Devices:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Booking_Service_Call_Reinstate_MOCA_Enabled_Hardware(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Booking service call during Reinstate with MOCA enabled hardware");
			log.debug("Entering Booking_Service_Call_Reinstate_MOCA_Enabled_Hardware");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String tvProduct=data.get("TV_Product");
			TVMOCASrlNo=Utility.generateHardwareSerailNum(TVMOCASrlNo);
			String tvMOCAHardwareSlNbr=TVMOCASrlNo;
			String internetProduct=data.get("Internet_Product");
			String internetHardware=data.get("Internet_hardware");
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
			String[] hardwareType={"ShawBlueSkyTV4Kbox"};
			String[] serialNumber={tvMOCAHardwareSlNbr};
			String techAppointment=data.get("Tech_Appointment");
			String disconnectReason=data.get("Disconnect_Reasons");
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
			order.addServicesAndFeacturesTab();

			ExtentTest childTest3=extent.startTest("Add TV With MOCA Enable Hardware");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);
			
			ExtentTest childTest4=extent.startTest("Add Internet Product");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			
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

			ExtentTest childTest8=extent.startTest("Navigate To COM & SOM Order");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",accntNum, "");
			order.openCOMOrderPage("false",accntNum, "");

			ExtentTest childTest9=extent.startTest("Navigate To check COM & SOM initial record counts");
			test.appendChild(childTest9);
			com=createChildTest(childTest9,extent,com);
			som=createChildTest(childTest9,extent,som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);
			
			ExtentTest childTest10=extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			order.navigateToCustOrderPage("false",accntNum);
			
			ExtentTest childTest11=extent.startTest("Disconnect Internet and TV LOB's");
			test.appendChild(childTest11);
			order=createChildTest(childTest11,extent,order);
			order.disconnectLOBProducts("TV");
			order.disconnectLOBProducts("Internet");
			
			ExtentTest childTest12=extent.startTest("Select Customer Mailing Address For Disconnection");
			test.appendChild(childTest12);
			order=createChildTest(childTest12, extent,order);
			order.addCustomerMailingAddress("", "No");
			
			ExtentTest childTest13=extent.startTest("Disconnect appointment date");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent,order);
			order.disconnectAppointmentDate();
			
			ExtentTest childTest14=extent.startTest("Select Disconnect Reason");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent,order);
			order.selectDisconnectNonControllabeReasons(disconnectReason);
			order.selectingSalesRepresentativeCheckBox();
			
			ExtentTest childTest15=extent.startTest("Review and Finish");
			test.appendChild(childTest15);
			order=createChildTest(childTest15,extent,order);
			order.reviewAndFinishOrder();
			
			ExtentTest childTest31=extent.startTest("Navigate To COM & SOM Order");
			test.appendChild(childTest31);
			order=createChildTest(childTest31,extent,order);
			order.openCOMOrderPage("true",accntNum, "");
			order.openCOMOrderPage("false",accntNum, "");
			
			ExtentTest childTest29=extent.startTest("Charge Hardware finish task");
			test.appendChild(childTest29);
			com=createChildTest(childTest29, extent, com);
			order=createChildTest(childTest29, extent, order);
			order.navigateToOrderValidation(35, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest30=extent.startTest("Charge Hardware finish task");
			test.appendChild(childTest30);
			com=createChildTest(childTest30, extent, com);
			order=createChildTest(childTest30, extent, order);
			order.navigateToOrderValidation(36, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest32=extent.startTest("Charge Hardware finish task");
			test.appendChild(childTest32);
			com=createChildTest(childTest32, extent, com);
			order=createChildTest(childTest32, extent, order);
			order.navigateToOrderValidation(37, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Navigate To COM & SOM Order");
			test.appendChild(childTest16);
			order=createChildTest(childTest16,extent,order);
			order.openCOMOrderPage("false",accntNum, "");
			
			ExtentTest childTest17=extent.startTest("Navigate To Cust Order Disconnect Page");	
			test.appendChild(childTest17);
			order=createChildTest(childTest17,extent,order);
			order.navigateToCustOrderPage("false", accntNum);
			order.reinstatePastResource("Start New Order");
			
			ExtentTest childTest18=extent.startTest("Add TV With MOCA Enable Hardware");
			test.appendChild(childTest18);
			order=createChildTest(childTest18,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);
			
			ExtentTest childTest19=extent.startTest("Add Internet Product");
			test.appendChild(childTest19);
			order=createChildTest(childTest19, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			
			ExtentTest childTest20=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest20);
			order=createChildTest(childTest20,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNo("On a date", "", "");
			
			ExtentTest childTest21=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest21);
			order=createChildTest(childTest21,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
		
			ExtentTest childTest22=extent.startTest("Select Bill Type");
			test.appendChild(childTest22);
			order=createChildTest(childTest22, extent, order);
			order.selectBillType("Paper");
			
			ExtentTest childTest23=extent.startTest("Review and Finish");
			test.appendChild(childTest23);
			order=createChildTest(childTest23,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest24=extent.startTest("Navigate To COM & SOM Order");
			test.appendChild(childTest24);
			order=createChildTest(childTest24,extent,order);
			order.openCOMOrderPage("false",accntNum, "");
			
			ExtentTest childTest25=extent.startTest("Navigate To Order Entry will set the Service Call Required flag as YES");
			test.appendChild(childTest25);
			com=createChildTest(childTest25,extent,com);
			order.navigateToOrderValidation(2, "No", "No", "COM");
			com.scrollDown600();
			com.scrollUp600();
			com.navigateToServiceInformationTab();
			com.scrollDown600();
			com.scrollUp600();
			order.navigateToOrderPage();
			
			ExtentTest childTest26=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest26);
			com=createChildTest(childTest26,extent,com);
			som=createChildTest(childTest26,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest27=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest27);
			repo=createChildTest(childTest27,extent,repo);
			order=createChildTest(childTest27,extent,order);
			order.navigateToOrderValidation(2, "Yes", "No", "COM");
			/*valuesToPassForValidation = new ArrayList<>(Arrays.asList()); 
			softAssert.assertTrue(repo.validateCOMIntegrationReports("TC81", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList()); 
			softAssert.assertTrue(repo.validateCOMIntegrationReports("TC81", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(tvMOCAHardwareSlNbr)); 
			softAssert.assertTrue(repo.validateCOMIntegrationReports("TC81", 3, valuesToPassForValidation),"Attributes are not validated successfully");*/
			order.navigateToOrderPage();
		
			ExtentTest childTest28=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest28);
			orderHistory=createChildTest(childTest28,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Booking_Service_Call_Reinstate_MOCA_Enabled_Hardware");
		} catch (Exception e) {
			log.error("Error in Booking_Service_Call_Reinstate_MOCA_Enabled_Hardware:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Resinstate_Triple_Play_During_PremiseMove(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Reinstate during premise move");
			log.debug("Entering Resinstate_Triple_Play_During_PremiseMove");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			TVBoxSrlNo = Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr = TVBoxSrlNo;
			TVPortalSrlNo = Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr = TVPortalSrlNo;
			String[] hardwareType={"ShawBlueSkyTVbox526","ShawBlueSkyTVportal416"};
			String[] serialNumber={tvBoxSerialNbr,tvPortalSerialNbr};
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			String convergedHardware=data.get("Converged_hardware" );
			String techAppointment=data.get("Tech_Appointment");
			String disconnectReason=data.get("Disconnect_Reasons");
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

			ExtentTest childTest3=extent.startTest("Add Phone and Internet Products");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
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
			//order.selectConvergedHardware("Internet");

			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			//order.mocaFilterSelecttion("");
			
			ExtentTest childTest7=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
		//	order.selectDefaultInstallationCheckBox();

			ExtentTest childTest8=extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",accntNum, "Yes");
			
			ExtentTest childTest10=extent.startTest("Navigate Check COM & SOM initial record counts");
			test.appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			som=createChildTest(childTest10,extent,som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

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
			som=createChildTest(childTest13,extent,som);
			com.verifyCLECRequestAfterJobRun();
			order.navigateToOrderPage();
			som.navigateToSOMOrder();
			com.navigateToCOMOrder();
		
			ExtentTest childTest14=extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest14);
			order=createChildTest(childTest14,extent,order);
			order.navigateToCustOrderPage("true", accntNum);
			
			ExtentTest childTest15=extent.startTest("Disconnect Triple play");
			test.appendChild(childTest15);
			order=createChildTest(childTest15,extent,order);
			order.tempDisconnectLOBs();
			order.deleteConvergedHardware();
			
			ExtentTest childTest16=extent.startTest("Select Number to Portout");
			test.appendChild(childTest16);
			order=createChildTest(childTest16, extent,order);
			order.selectPortOut();
			
			ExtentTest childTest17=extent.startTest("Select Bill Type");
			test.appendChild(childTest17);
			order=createChildTest(childTest17, extent,order);
			order.addCustomerMailingAddress("", "No");
		
			ExtentTest childTest18=extent.startTest("Navigate To Tech Appointment For Disconnect");
			test.appendChild(childTest18);
			order=createChildTest(childTest18, extent,order);
			order.selectCalenderCurrentDate();

			ExtentTest childTest19=extent.startTest("Select Disconnect Reason");
			test.appendChild(childTest19);
			order=createChildTest(childTest19, extent,order);
			order.selectDisconnectNonControllabeReasons(disconnectReason);
			order.selectingSalesRepresentativeCheckBox();
			
			ExtentTest childTest20=extent.startTest("Review and Finish");
			test.appendChild(childTest20);
			order=createChildTest(childTest20,extent,order);
			order.reviewAndFinishOrder();
			
			ExtentTest childTest45=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest45);
			order=createChildTest(childTest45,extent,order);
			order.openCOMOrderPage("true",accntNum, "Yes");
			
			ExtentTest childTest41=extent.startTest("Wait for Effective date Mark Finish task");
			test.appendChild(childTest41);
			com=createChildTest(childTest41, extent, com);
			order=createChildTest(childTest41, extent, order);
			order.navigateToOrderValidation(35, "No", "No", "COM");
			com.setMarkFinishedSpecificTask("Wait for effective date");
			order.navigateToOrderPage();
			order.refreshPage();
			order.refreshPage();
			
			ExtentTest childTest42=extent.startTest("Wait for Grace period Mark Finish task");
			test.appendChild(childTest42);
			com=createChildTest(childTest42, extent, com);
			order=createChildTest(childTest42, extent, order);
			order.navigateToOrderValidation(36, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest43=extent.startTest("Wait for Grace period Mark Finish task");
			test.appendChild(childTest43);
			com=createChildTest(childTest43, extent, com);
			order=createChildTest(childTest43, extent, order);
			order.navigateToOrderValidation(37, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest44=extent.startTest("Wait for Grace period Mark Finish task");
			test.appendChild(childTest44);
			com=createChildTest(childTest44, extent, com);
			order=createChildTest(childTest44, extent, order);
			order.navigateToOrderValidation(38, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest21=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest21);
			som=createChildTest(childTest21,extent,som);
			com=createChildTest(childTest21,extent,com);
			order=createChildTest(childTest21,extent,order);
			order.openCOMOrderPage("false",accntNum, "Yes");
			
			ExtentTest childTest22=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest22);
			com=createChildTest(childTest22,extent,com);
			som=createChildTest(childTest22,extent,som);
			order=createChildTest(childTest22, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.openCOMOrderPage("false",accntNum, "Yes");
			
			ExtentTest childTest23=extent.startTest("Navigate to Vancover Location");
			test.appendChild(childTest23);
			order=createChildTest(childTest23,extent,order);
			order.navigateToVancoverLocationId(accntNum);
			order.reinstatePastResource("Start New Order");

			ExtentTest childTest25=extent.startTest("Add Phone and Internet Products");
			test.appendChild(childTest25);
			order=createChildTest(childTest25,extent,order);
			order.addServicesAndFeacturesTab();
			String phoneNbr1=order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest26=extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest26);
			order=createChildTest(childTest26,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);

			ExtentTest childTest27=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest27);
			order=createChildTest(childTest27,extent,order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			
			ExtentTest childTest28=extent.startTest("Select Todo list Check Box");
			test.appendChild(childTest28);
			order=createChildTest(childTest28,extent,order);
			order.selectTodoListCheckBox();
			
			ExtentTest childTest30=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest30);
			order=createChildTest(childTest30,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			
			ExtentTest childTest31=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest31);
			order=createChildTest(childTest31,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			
			ExtentTest childTest32=extent.startTest("Review and Finish");
			test.appendChild(childTest32);
			order=createChildTest(childTest32,extent,order);
			order.reviewAndFinishOrder();
			
			ExtentTest childTest33=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest33);
			order=createChildTest(childTest33,extent,order);
			order.openCOMOrderPage("false",accntNum, "Yes");

			ExtentTest childTest34=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest34);
			com=createChildTest(childTest34,extent,com);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest35=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest35);
			com=createChildTest(childTest35,extent,com);
			order=createChildTest(childTest35, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest36=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest36);
			com=createChildTest(childTest36,extent,com);
			com.verifyCLECRequestAfterJobRun();
			
			ExtentTest childTest37=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest37);
			com=createChildTest(childTest37,extent,com);
			som=createChildTest(childTest37,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			
			ExtentTest childTest38=extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest38);
			repo=createChildTest(childTest38,extent,repo);
			order=createChildTest(childTest38,extent,order);
			order.navigateToOrderValidation(4, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,phoneNbr1,convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC100", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(8, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC100", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(12, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway=repo.validateSOMIntegrationReportsSpcl(xls,"TC100", 3, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway,"Attributes are not validated successfully");
			if(!is4xgateway) 
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC100", 3, valuesToPassForValidation),"Attributes are not validated successfully");		
			}
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(13, "Yes", "No", "SOM");
			if(is4xgateway) 
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC100", 4, valuesToPassForValidation),"Attributes are not validated successfully");				
			}
			else
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC100", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();
			
			ExtentTest childTest39=extent.startTest("Validate COM Integration Report");
			test.appendChild(childTest39);
			repo=createChildTest(childTest39,extent,repo);
			order=createChildTest(childTest39,extent,order);
			order.navigateToOrderValidation(2, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr1, convergedHardWareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC100", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(18, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr1, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC100", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			
			ExtentTest childTest40=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest40);
			orderHistory=createChildTest(childTest40,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Resinstate_Triple_Play_During_PremiseMove");
		} catch (Exception e) {
			log.error("Error in Resinstate_Triple_Play_During_PremiseMove:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Fully_Disconnect_TriplePlay_Perform_Reinstate_New_Resources(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","For a fully disconnected Triple Play account, perform a re-instate with new resources");
			log.debug("Entering Fully_Disconnect_TriplePlay_Perform_Reinstate_New_Resources");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			String convergedHardware=data.get("Converged_hardware");
			TVBoxSrlNo = Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr = TVBoxSrlNo;
			TVPortalSrlNo = Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr = TVPortalSrlNo;
			String[] hardwareType={"ShawBlueSkyTVbox526","ShawBlueSkyTVportal416"};
			String[] serialNumber={tvBoxSerialNbr,tvPortalSerialNbr};
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
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
		
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1,extent,order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Phone and Internet Products");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
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

			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
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
			order=createChildTest(childTest8,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true",accntNum, "Yes");

			ExtentTest childTest10=extent.startTest("CLEC Request Befroe job run");
			test.appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest11=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11,extent,com);
			order=createChildTest(childTest11, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("CLEC Req After Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12,extent,com);
			som=createChildTest(childTest12,extent,som);
			com.verifyCLECRequestAfterJobRun();
			order.navigateToOrderPage();
			som.navigateToSOMOrder();
			com.navigateToCOMOrder();
			
			ExtentTest childTest13=extent.startTest("Navigate To Cust Order Page");
			test.appendChild(childTest13);
			order=createChildTest(childTest13,extent,order);
			order.navigateToCustOrderPage("true", accntNum);
			
			ExtentTest childTest14=extent.startTest("Disconnect Triple play");
			test.appendChild(childTest14);
			order=createChildTest(childTest14,extent,order);
			order.tempDisconnectLOBs();
			order.deleteConvergedHardware();
			
			ExtentTest childTest15=extent.startTest("Select Number to Portout");
			test.appendChild(childTest15);
			order=createChildTest(childTest15, extent,order);
			order.selectPortOut();
			
			ExtentTest childTest16=extent.startTest("Select Bill Type");
			test.appendChild(childTest16);
			order=createChildTest(childTest16, extent,order);
			order.addCustomerMailingAddress("Service Location", "No");
			
			ExtentTest childTest17=extent.startTest("Navigate To Tech Appointment For Disconnect");
			test.appendChild(childTest17);
			order=createChildTest(childTest17, extent,order);
			order.selectCalenderCurrentDate();

			ExtentTest childTest18=extent.startTest("Select Disconnect Reason");
			test.appendChild(childTest18);
			order=createChildTest(childTest18, extent,order);
			order.selectDisconnectNonControllabeReasons(disconnectReason);
			order.selectingSalesRepresentativeCheckBox();
			
			ExtentTest childTest19=extent.startTest("Review and Finish");
			test.appendChild(childTest19);
			order=createChildTest(childTest19,extent,order);
			order.reviewAndFinishOrder();
			
			ExtentTest childTest20=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest20);
			som=createChildTest(childTest20,extent,som);
			com=createChildTest(childTest20,extent,com);
			order=createChildTest(childTest20,extent,order);
			order.openCOMOrderPage("false",accntNum, "Yes");
			
			ExtentTest childTest37=extent.startTest("Wait For Tech Confirm Mark Finished");
			test.appendChild(childTest37);
			com=createChildTest(childTest37,extent,com);
			order=createChildTest(childTest37,extent,order);
			order.navigateToOrderValidation(35, "No", "No", "COM");
			com.verifySVGDiagram("");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest21=extent.startTest("Navigate TO E911 Job Run");
			test.appendChild(childTest21);
			com=createChildTest(childTest21,extent,com);
			order=createChildTest(childTest21, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.openCOMOrderPage("false",accntNum, "Yes");
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			
			ExtentTest childTest22=extent.startTest("Navigate To Cust Order Disconnect Page");
			test.appendChild(childTest22);
			order=createChildTest(childTest22,extent,order);
			order.navigateToCustOrderPage("false", accntNum);
			order.reinstatePastResource("Start New Order");
			
			ExtentTest childTest23=extent.startTest("Select Bill Type");
			test.appendChild(childTest23);
			order=createChildTest(childTest23,extent,order);
			order.selectBillType("Paper");
			order.addCustomerMailingAddress("Service Location", "No");

			ExtentTest childTest24=extent.startTest("Add Phone and Internet Products");
			test.appendChild(childTest24);
			order=createChildTest(childTest24,extent,order);
			order.addServicesAndFeacturesTab();
			String phoneNbr1=order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest25=extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest25);
			order=createChildTest(childTest25,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);

			ExtentTest childTest26=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest26);
			order=createChildTest(childTest26,extent,order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			order.selectTodoListCheckBox();
			
			ExtentTest childTest27=extent.startTest("Enter Installation Fee");
			test.appendChild(childTest27);
			order=createChildTest(childTest27,extent,order);
			order.enterInstallationFee("1.00");
			
			ExtentTest childTest28=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest28);
			order=createChildTest(childTest28,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppointmentNo("On a date", "", "");
			
			ExtentTest childTest29=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest29);
			order=createChildTest(childTest29,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();
			
			ExtentTest childTest30=extent.startTest("Review and Finish");
			test.appendChild(childTest30);
			order=createChildTest(childTest30,extent,order);
			order.reviewAndFinishOrder();
			
			ExtentTest childTest31=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest31);
			order=createChildTest(childTest31,extent,order);
			order.openCOMOrderPage("false",accntNum, "Yes");
			
			ExtentTest childTest32=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest32);
			com=createChildTest(childTest32,extent,com);
			order=createChildTest(childTest32, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			
			ExtentTest childTest33=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest33);
			com=createChildTest(childTest33,extent,com);
			som=createChildTest(childTest33,extent,som);
			//com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest34=extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest34);
			repo=createChildTest(childTest34,extent,repo);
			order=createChildTest(childTest34,extent,order);
			order.navigateToOrderValidation(2, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(phoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC113", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC113", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(10, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway=repo.validateSOMIntegrationReportsSpcl(xls,"TC113", 3, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway,"Attributes are not validated successfully");
			if(!is4xgateway)
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC113", 3, valuesToPassForValidation),"Attributes are not validated successfully");		
			}
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(11, "Yes", "No", "SOM");
			if(is4xgateway) 
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC113", 4, valuesToPassForValidation),"Attributes are not validated successfully");				
			}
			else
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC113", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(33, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr,convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC113", 5, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(34, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC113", 6, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(35, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway1=repo.validateSOMIntegrationReportsSpcl(xls,"TC113", 7, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway1,"Attributes are not validated successfully");
			if(!is4xgateway1) 
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC113", 7, valuesToPassForValidation),"Attributes are not validated successfully");		
			}
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(36, "Yes", "No", "SOM");
			if(is4xgateway1)
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC113", 8, valuesToPassForValidation),"Attributes are not validated successfully");				
			}
			else
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC113", 8, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(4, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr1,convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC113", 9, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(8, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC113", 10, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(12, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway2=repo.validateSOMIntegrationReportsSpcl(xls,"TC113", 11, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway2,"Attributes are not validated successfully");
			if(!is4xgateway2) 
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC113", 11, valuesToPassForValidation),"Attributes are not validated successfully");		
			}
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(13, "Yes", "No", "SOM");
			if(is4xgateway2)
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC113", 12, valuesToPassForValidation),"Attributes are not validated successfully");				
			}
			else
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC113", 12, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();
			
			ExtentTest childTest35=extent.startTest("Validate COM Integration Report");
			test.appendChild(childTest35);
			repo=createChildTest(childTest35,extent,repo);
			order=createChildTest(childTest35,extent,order);
			order.navigateToOrderValidation(2, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr1, convergedHardWareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC113", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList());
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(18, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr1, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC113", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			
			ExtentTest childTest36=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest36);
			orderHistory=createChildTest(childTest36,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Fully_Disconnect_TriplePlay_Perform_Reinstate_New_Resources");
		} catch (Exception e) {
			log.error("Error in Fully_Disconnect_TriplePlay_Perform_Reinstate_New_Resources:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	public static void main (String args[])
	{
		InputStream log4j=ReinstateCases.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
        PropertyConfigurator.configure(log4j);
	}
}	
