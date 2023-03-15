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

public class PremiseMoveCases extends SeleniumTestUp {

	LandingPage landing=null;
	OrderCreationPage order =null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	ValidateReport repo=null;
	OrderHistoryPage orderHistory=null;
	SoftAssert softAssert=new SoftAssert();
	ArrayList<String> valuesToPassForValidation=new ArrayList<String>();

	Logger log=Logger.getLogger(PremiseMoveCases.class);
	
	@Test (dataProvider="getTestData", description="Golden Suite")
	@SuppressWarnings(value={"rawtypes"})
	public void TriplePlay_PremiseMove_With_XB6_And_BlueSky(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Verify Intra Province Transfer with Gap from CGY to Vancover on a Triple Play account having XB6 [Phone + Internet] and Blue Sky TV");
			log.debug("Entering TriplePlay_PremiseMove_With_XB6_And_BlueSky");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
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
			
			ExtentTest childTest3=extent.startTest("Add Phone and Internet Service");
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
			
			
			ExtentTest childTest6=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest6);
			order=createChildTest(childTest6,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();
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

			ExtentTest childTest9=extent.startTest("Navigate To COM, CLEC & SOM Orders");
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
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			com.switchPreviousTab();

			ExtentTest childTest13=extent.startTest("Navigate to Vancover Location");
			test.appendChild(childTest13);
			order=createChildTest(childTest13,extent,order);
			order.navigateToVancoverLocationId(accntNum);

			ExtentTest childTest14=extent.startTest("Assign Phone number");
			test.appendChild(childTest14);
			order=createChildTest(childTest14,extent,order);
			order.addServicesAndFeacturesTab();
			String phoneNbr2=order.addServicePhone("Add Product", phoneProduct);

			ExtentTest childTest15=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest15);
			order=createChildTest(childTest15,extent,order);
			order.selectInstallationOption("Self Connect No", "Yes");
			order.techAppoinmentYes("Yes", "");
			order.techAppointmentWithGAP();
			
			ExtentTest childTest16=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest16);
			order=createChildTest(childTest16,extent,order);
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();
			order.selectPortOut();
			
			ExtentTest childTest17=extent.startTest("Review and Finish");
			test.appendChild(childTest17);
			order=createChildTest(childTest17,extent,order);
			order.reviewAndFinishOrder();

			ExtentTest childTest18=extent.startTest("Navigate To COM, CLEC & SOM Orders");
			test.appendChild(childTest18);
			order=createChildTest(childTest18,extent,order);
			order.openCOMOrderPage("false",accntNum, "Yes");
			
			ExtentTest childTest19=extent.startTest("Wait for disconnection Mark Finish Task");
			test.appendChild(childTest19);
			com=createChildTest(childTest19,extent,com);
			order=createChildTest(childTest19,extent,order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest20=extent.startTest("Wait for Effective date Mark finish task");
			test.appendChild(childTest20);
			com=createChildTest(childTest20,extent,com);
			order=createChildTest(childTest20,extent,order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			com.checkEffectiveDateStatus();
			order.navigateToOrderPage();

			ExtentTest childTest21=extent.startTest("Send serial number for converged hardware");
			test.appendChild(childTest21);
			com=createChildTest(childTest21,extent,com);
			order=createChildTest(childTest21,extent,order);
			order.navigateToOrderValidation(31, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest22=extent.startTest("Send serial number for TV hardware1");
			test.appendChild(childTest22);
			com=createChildTest(childTest22,extent,com);
			order=createChildTest(childTest22,extent,order);
			order.navigateToOrderValidation(28, "No", "No", "COM");
			String tvSerialNbr1=com.getOldSerialNbr();
			com.sendSerialNbrTask(tvSerialNbr1);
			order.navigateToOrderPage();
			
			ExtentTest childTest23=extent.startTest("Send serial number for TV hardware2");
			test.appendChild(childTest23);
			com=createChildTest(childTest23,extent,com);
			order=createChildTest(childTest23,extent,order);
			order.navigateToOrderValidation(29, "No", "No", "COM");
			String tvSerialNbr2=com.getOldSerialNbr();
			com.sendSerialNbrTask(tvSerialNbr2);
			order.navigateToOrderPage();

			ExtentTest childTest24=extent.startTest("Wait for tech confirm Mark Finish task");
			test.appendChild(childTest24);
			com=createChildTest(childTest24,extent,com);
			order=createChildTest(childTest24,extent,order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest25=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest25);
			com=createChildTest(childTest25,extent,com);
			order=createChildTest(childTest25,extent,order);
			order=createChildTest(childTest25, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
		
			ExtentTest childTest26=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest26);
			com=createChildTest(childTest26,extent,com);
			som=createChildTest(childTest26,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest27=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest27);
			repo=createChildTest(childTest27,extent,repo);
			order=createChildTest(childTest27,extent,order);
			order.navigateToOrderValidation(29, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC10", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(30, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC10", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			
			/*som.navigateToSuspendTVProvisioningReport2();
			order.navigateToOrderValidation(32, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			if(!(repo.validateSOMIntegrationReports("TC10", 3, valuesToPassForValidation)))
			{
				order.NavigateCOMOrderPage(accntNum);
				//som.navigateToSOMOrder();
				///som.navigateToSuspendTVProvisioningReport1();
				order.navigateToOrderValidation(31, "Yes", "No", "SOM");
				softAssert.assertTrue(repo.validateSOMIntegrationReports("TC10", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			else
			{
				softAssert.assertTrue(repo.validateSOMIntegrationReports("TC10", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			
			//Assert.assertTrue(report.getActualPremiseMoveSuspendTVRFS_53(accntNum),"Attributes are not validated successfully");    
			order.navigateToOrderValidation(31, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports("TC10", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			*/
			
			order.navigateToOrderPage();
			order.navigateToOrderValidation(28, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC10", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(26, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway=repo.validateSOMIntegrationReportsSpcl(xls,"TC10", 5, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway,"Attributes are not validated successfully");
			if(!is4xgateway)
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC10", 5, valuesToPassForValidation),"Attributes are not validated successfully");		
			}
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(27, "Yes", "No", "SOM");
			if (is4xgateway) {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC10", 6, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			else {
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC10", 6, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(33, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC10", 7, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC10", 8, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(3, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC10", 9, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(4, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr2, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC10", 10, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest28=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest28);
			repo=createChildTest(childTest28,extent,repo); 
			order=createChildTest(childTest28,extent,order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr, phoneNbr, phoneNbr2));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC10", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr2, convergedHardWareSerialNbr, tvBoxSerialNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC10", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			
			ExtentTest childTest29=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest29);
			orderHistory=createChildTest(childTest29,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving TriplePlay_PremiseMove_With_XB6_And_BlueSky");
		} catch (Exception e) {
			log.error("Error in TriplePlay_PremiseMove_With_XB6_And_BlueSky:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void IntraProvince_PremiseMove_CGY_TO_CGY(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Verify Intra Province Premise Move from CGY to CGY on a Triple Play account having DPT + Hitron Internet +Legacy TV without Gap");
			log.debug("Entering IntraProvince_PremiseMove_CGY_TO_CGY");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String internetHardware=data.get("Internet_hardware");
			String tvProduct=data.get("TV_Product");
			String phoneHardware=data.get("Phone_hardware");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneSerialNo=PhSrlNo;
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
			TVDctSrlNo=Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr=TVDctSrlNo;
			String[] hardwareType={"DCT700"};
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

			ExtentTest childTest3=extent.startTest("Add Phone and VoiceMail");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();

			ExtentTest childTest4=extent.startTest("Add Phone hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addPhoneHardware(phoneSerialNo, phoneHardware);

			ExtentTest childTest5=extent.startTest("Add Internet Product");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6=extent.startTest("Add Internet hardware");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);

			ExtentTest childTest7=extent.startTest("Add TV");
			test.appendChild(childTest7);
			order=createChildTest(childTest7, extent, order);
			order.addServiceTV(tvProduct);

			ExtentTest childTest8=extent.startTest("Add TV Hardware");
			test.appendChild(childTest8);
			order=createChildTest(childTest8, extent, order);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest9=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			
			ExtentTest childTest10=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
	
			ExtentTest childTest11=extent.startTest("Review and Finish");
			test.appendChild(childTest11);
			order=createChildTest(childTest11, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest12=extent.startTest("Navigate To COM, SOM, CLEC Orders");
			test.appendChild(childTest12);
			order=createChildTest(childTest12, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest13=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest13);
			com=createChildTest(childTest13, extent, com);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest14=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest14);
			com=createChildTest(childTest14, extent, com);
			order=createChildTest(childTest14, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest15=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest15);
			com=createChildTest(childTest15, extent, com);
			com.verifyCLECRequestAfterJobRun();
			//com.switchPreviousTab();
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest16=extent.startTest("Navigate to CGY Location");
			test.appendChild(childTest16);
			order=createChildTest(childTest16, extent, order);
			order.navigateToExistingAccountForPremise(accntNum);

			ExtentTest childTest17=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest17);
			order=createChildTest(childTest17, extent, order);
			order.selectInstallationOption("Self Connect No", "Yes");
			order.techAppoinmentYes("", "");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest18=extent.startTest("Review and Finish");
			test.appendChild(childTest18);
			order=createChildTest(childTest18, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest19=extent.startTest("Navigate To COM, SOM, CLEC Orders");
			test.appendChild(childTest19);
			order=createChildTest(childTest19, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest20=extent.startTest("Wait for effectgive date Mark finish task");
			test.appendChild(childTest20);
			com=createChildTest(childTest20, extent, com);
			order=createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			com.checkEffectiveDateStatus();
			order.navigateToOrderPage();

			ExtentTest childTest21=extent.startTest("Send serial number for Phone Hardware");
			test.appendChild(childTest21);
			com=createChildTest(childTest21, extent, com);
			order=createChildTest(childTest21, extent, order);
			order.navigateToOrderValidation(41, "No", "No", "COM");
			com.sendSerialNbrTask(phoneSerialNo);
			order.navigateToOrderPage();

			ExtentTest childTest22=extent.startTest("Send serial number For Internet Hardware");
			test.appendChild(childTest22);
			com=createChildTest(childTest22, extent, com);
			order=createChildTest(childTest22, extent, order);
			order.navigateToOrderValidation(45, "No", "No", "COM");	
			com.sendSerialNbrTask(internetHardwareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest23=extent.startTest("Send serial number for TV Hardware");
			test.appendChild(childTest23);
			com=createChildTest(childTest23, extent, com);
			order=createChildTest(childTest23, extent, order);
			order.navigateToOrderValidation(28, "No", "No", "COM");
			com.sendSerialNbrTask(tvHardwareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest24=extent.startTest("Wait for tech confirm mark finish task");
			test.appendChild(childTest24);
			com=createChildTest(childTest24, extent, com);
			order=createChildTest(childTest24, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest25=extent.startTest("Navigate To E911 JOB Run");
			test.appendChild(childTest25);
			com=createChildTest(childTest25, extent, com);
			order=createChildTest(childTest25, extent, order);
			order=createChildTest(childTest25, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
		
			ExtentTest childTest26=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest26);
			com=createChildTest(childTest26, extent, com);
			som=createChildTest(childTest26, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest27=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest27);
			repo=createChildTest(childTest27, extent, repo);
			order=createChildTest(childTest27, extent, order);
			order.navigateToOrderValidation(29, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC82", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(30, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC82", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(31, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC82", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(25, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneSerialNo));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC82", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(28, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC82", 5, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(26, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC82", 6, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest28=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest28);
			repo=createChildTest(childTest28, extent, repo);
			order=createChildTest(childTest28, extent, order);
			order.navigateToOrderValidation(41, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneSerialNo, phoneNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC82", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(30, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr, phoneNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC82", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
		
			order.navigateToOrderValidation(28, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr, phoneNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC82", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			
			ExtentTest childTest29=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest29);
			orderHistory=createChildTest(childTest29, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving IntraProvince_PremiseMove_CGY_TO_CGY");
		} catch (Exception e) {
			log.error("Error in IntraProvince_PremiseMove_CGY_TO_CGY:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void PremiseMove_CGY_Affordable_Internet_TV(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Add \"Affordable internet offering\" on an existing residential TV customer in the premise move order");
			log.debug("Entering PremiseMove_CGY_Affordable_Internet_TV");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String tvProduct=data.get("TV_Product");
			String internetHardware=data.get("Internet_hardware");
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
			TVDctSrlNo=Utility.generateHardwareSerailNum(TVDctSrlNo); 
			String tvHardwareSerialNbr=TVDctSrlNo;
			String[] hardwareType={"DCT700"};
			String[] serialNumber={tvHardwareSerialNbr};
			String techAppointment=data.get("Tech_Appointment");
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

			ExtentTest childTest3=extent.startTest("Add TV");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceTV(tvProduct);

			ExtentTest childTest4=extent.startTest("Add TV Hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);

			ExtentTest childTest5=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest6=extent.startTest("Review and Finish");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest7=extent.startTest("Navigate To COM, SOM Orders");
			test.appendChild(childTest7);
			order=createChildTest(childTest7, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest8=extent.startTest("Navigate to ISD PIN & Service Portal CGY Location");
			test.appendChild(childTest8);
			order=createChildTest(childTest8, extent, order);
			order.navigateToCustOrderISEDPagePremise(accntNum);

			ExtentTest childTest9=extent.startTest("Add Internet hardware");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);

			ExtentTest childTest10=extent.startTest("validate ISED PIN & Postal");
			test.appendChild(childTest10);
			order=createChildTest(childTest10, extent, order);
			String ISEDcode=order.validateCustomerTabforISED();
			
			ExtentTest childTest11=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest11);
			order=createChildTest(childTest11, extent, order);
			order.addServicesAndFeacturesTab();
			order.selectInstallationOption("Self Connect No", "Yes");
			order.techAppoinmentYes("Yes", "");
			
			ExtentTest childTest12=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest12);
			order=createChildTest(childTest12,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();
			
			ExtentTest childTest13=extent.startTest("Review and Finish");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest14=extent.startTest("Navigate To COM, SOM Orders");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest15=extent.startTest("Mark Task Finish Wait for effectiove date");
			test.appendChild(childTest15);
			com=createChildTest(childTest15, extent, com);
			order=createChildTest(childTest15, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest16=extent.startTest("Send serial number Internet Hardware");
			test.appendChild(childTest16);
			com=createChildTest(childTest16, extent, com);
			order=createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(8, "No", "No", "COM");
			com.sendSerialNbrTask(internetHardwareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest17=extent.startTest("Send serial number TV Hardware");
			test.appendChild(childTest17);
			com=createChildTest(childTest17, extent, com);
			order=createChildTest(childTest17, extent, order);
			order.navigateToOrderValidation(28, "No", "No", "COM");
			com.sendSerialNbrTask(tvHardwareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest18=extent.startTest("Mark Task Finish wait for tech confrim");
			test.appendChild(childTest18);
			com=createChildTest(childTest18, extent, com);
			order=createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest19=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest19);
			com=createChildTest(childTest19, extent, com);
			som=createChildTest(childTest19, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest20=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest20);
			repo=createChildTest(childTest20, extent, repo);
			order=createChildTest(childTest20, extent, order);
			order.navigateToOrderValidation(31, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC83", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(26, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC83", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest21=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest21);
			repo=createChildTest(childTest21, extent, repo);
			order=createChildTest(childTest21, extent, order);
			order.navigateToOrderValidation(8, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,ISEDcode));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC83", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC83", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,ISEDcode));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC83", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			
			ExtentTest childTest22=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest22);
			orderHistory=createChildTest(childTest22, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving PremiseMove_CGY_Affordable_Internet_TV");
		} catch (Exception e) {
			log.error("Error in PremiseMove_CGY_Affordable_Internet_TV:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void TechSwap_Premise_Move_Modify_Order(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Tech swap during premise move/modify order");
			log.debug("Entering Techswap_Premise_Move_Modify_Order");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			String internetHardware=data.get("Internet_hardware");
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String diffIntHardwareSrlNbr=IntHitronSrlNo;
			TVHDSrlNo=Utility.generateHardwareSerailNum(TVHDSrlNo);
			String tvHardwareDCXSerialNbr=TVHDSrlNo;
			TVHDSrlNo=Utility.generateHardwareSerailNum(TVHDSrlNo);
			String diffTvHardwareSrlNbr=TVHDSrlNo;
			String[] hardwareType={"DCX3510HDGuide"};
			String[] serialNumber={tvHardwareDCXSerialNbr};
			String techAppointment=data.get("Tech_Appointment");
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
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add Internet hardware");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			//order.deleteConvergedHardware();

			ExtentTest childTest5=extent.startTest("Add TV Product");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);

			ExtentTest childTest6=extent.startTest("Add TV Hardware");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
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
			order.openCOMOrderPage("true", accntNum, "");
			
			ExtentTest childTest11=extent.startTest("Navigate to CGY Location");
			test.appendChild(childTest11);
			order=createChildTest(childTest11, extent, order);
			order.navigateToExistingAccountForPremise(accntNum);
			
			ExtentTest childTest12=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest12);
			order=createChildTest(childTest12, extent, order);
			order.selectInstallationOption("Self Connect No", "Yes");
			order.techAppoinmentYes("Yes", "");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest13=extent.startTest("Review and Finish");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			order.reviewAndFinishOrder();
			
			ExtentTest childTest14=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			order.openCOMOrderPage("false", accntNum, "");
			
			ExtentTest childTest15=extent.startTest("Wait for Disconnection date Mark Finish Task");
			test.appendChild(childTest15);
			com=createChildTest(childTest15, extent, com);
			order=createChildTest(childTest15, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Wait for effective date Mark Finish Task");
			test.appendChild(childTest16);
			com=createChildTest(childTest16, extent, com);
			order=createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest17=extent.startTest("Send SN2 for Internet Hardware CFS Order");
			test.appendChild(childTest17);
			com=createChildTest(childTest17,extent,com);
			order=createChildTest(childTest17,extent,order);
			order.navigateToOrderValidation(45, "No", "No", "COM");	
			com.sendSerialNbrTask(diffIntHardwareSrlNbr);
			order.navigateToOrderPage();
			
			ExtentTest childTest18=extent.startTest("Send SN2 for TV Hardware CFS Order");
			test.appendChild(childTest18);
			com=createChildTest(childTest18,extent,com);
			som=createChildTest(childTest18,extent,som);
			order=createChildTest(childTest18,extent,order);
			order.navigateToOrderValidation(28, "No", "No", "COM");
			com.sendSerialNbrTask(diffTvHardwareSrlNbr);
			order.navigateToOrderPage();
			som.navigateToSOMOrder();
			com.navigateToCOMOrder();
			
			ExtentTest childTest19=extent.startTest("Send SN1 for Internet Hardware CFS Order");
			test.appendChild(childTest19);
			com=createChildTest(childTest19,extent,com);
			order=createChildTest(childTest19,extent,order);
			order.navigateToOrderValidation(45, "No", "No", "COM");
			com.sendSerialNbrTask(internetHardwareSerialNbr);
			order.navigateToOrderPage();
			
			ExtentTest childTest20=extent.startTest("Send SN1 for TV Hardware CFS Order");
			test.appendChild(childTest20);
			com=createChildTest(childTest20,extent,com);
			order=createChildTest(childTest20,extent,order);
			order.navigateToOrderValidation(28, "No", "No", "COM");
			com.sendSerialNbrTask(tvHardwareDCXSerialNbr);
			order.navigateToOrderPage();
			
			ExtentTest childTest21=extent.startTest("Wait for tech confirm Mark finish Task");
			test.appendChild(childTest21);
			order=createChildTest(childTest21,extent,order);
			com=createChildTest(childTest21,extent,com);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest22=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest22);
			com=createChildTest(childTest22,extent,com);
			som=createChildTest(childTest22,extent,som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest23=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest23);
			repo=createChildTest(childTest23,extent,repo);	
			order=createChildTest(childTest23,extent,order);
			order.navigateToOrderValidation(34, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, diffIntHardwareSrlNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC95", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC95", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(35, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvHardwareDCXSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC95", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(11, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC95", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest24=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest24);
			repo=createChildTest(childTest24,extent,repo);
			order=createChildTest(childTest24, extent, order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr, tvHardwareDCXSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC95", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvHardwareDCXSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC95", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
		
		    ExtentTest childTest25=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest25);
			orderHistory=createChildTest(childTest25,extent,orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
		
			log.debug("Leaving Techswap_Premise_Move_Modify_Order");
		} catch (Exception e) {
			log.error("Error in Techswap_Premise_Move_Modify_Order:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Order_Decomposition_PremiseMove(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Order decomposition should be successful after the Internet level up during premise move without gap");
			log.debug("Entering Order_Decomposition_PremiseMove");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			String internetHardware=data.get("Internet_hardware");
			String convergedHardware=data.get("Converged_hardware");
			IntACSrlNo=Utility.generateHardwareSerailNum(IntACSrlNo);
			String internetHardwareSerialNbr=IntACSrlNo;
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			TVDctSrlNo=Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr=TVDctSrlNo;
			String[] hardwareType={"DCT700"};
			String[] serialNumber={tvHardwareSerialNbr};
			String techAppointment=data.get("Tech_Appointment");
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

			ExtentTest childTest8=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest8);
			order=createChildTest(childTest8, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");

			ExtentTest childTest9=extent.startTest("Transfer Account to 2777 Location");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			order.navigateToVancoverLocationId(accntNum);

			ExtentTest childTest10=extent.startTest("Remove Cisco modem and Add Converged Hardware");
			test.appendChild(childTest10);
			order=createChildTest(childTest10, extent, order);
			order.addServiceInternet(internetProduct);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			order.deleteHardware("");

			ExtentTest childTest11=extent.startTest("Add TV with DCT Hardware");
			test.appendChild(childTest11);
			order=createChildTest(childTest11, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);

			ExtentTest childTest12=extent.startTest("Select Appointment without Gap");
			test.appendChild(childTest12);
			order=createChildTest(childTest12, extent, order);
			order.selectInstallationOption("Self Connect No", "Yes");
			order.techAppoinmentYes("", "");
			
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

			ExtentTest childTest15=extent.startTest("Navigate To COM, SOM And CLEC Orders");
			test.appendChild(childTest15);
			order=createChildTest(childTest15, extent, order);
			order.openCOMOrderPage("false", accntNum, "");

			ExtentTest childTest16=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest16);
			com=createChildTest(childTest16, extent, com);
			som=createChildTest(childTest16, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest17=extent.startTest("Verify that Wait for De-provisioning (Transfer) task should not be present");
			test.appendChild(childTest17);
			order=createChildTest(childTest17, extent, order);
			com=createChildTest(childTest17, extent, com);
			com.navigateToCOMOrder();
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.verifySVGDiagram("");
			order.navigateToOrderPage();

			ExtentTest childTest18=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest18);
			orderHistory=createChildTest(childTest18, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Order_Decomposition_PremiseMove");
		} catch (Exception e) {
			log.error("Error in Order_Decomposition_PremiseMove:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Active_xFi_PremiseMove_With_Gap(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","An active xFi remains active during Premise Move with gap");
			log.debug("Entering Active_xFi_PremiseMove_With_Gap");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			String convergedHardware=data.get("Converged_hardware");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			String techAppointment=data.get("Tech_Appointment");
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
			order=createChildTest(childTest1,extent,order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);
			
			ExtentTest childTest4 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			
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
			
			ExtentTest childTest9=extent.startTest("Navigate to Vancover Location");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			order.navigateToVancoverLocationId(accntNum);
			order.enterInstallationFee("1.00");
			
			ExtentTest childTest10=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			order.selectInstallationOption("Self Connect No", "Yes");
			order.techAppoinmentYes("Yes", "");
			order.techAppointmentWithGAP();
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest11=extent.startTest("Review and Finish");
			test.appendChild(childTest11);
			order=createChildTest(childTest11,extent,order);
			order.reviewAndFinishOrder();
			
			ExtentTest childTest12=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest12);
			order=createChildTest(childTest12, extent, order);
			order.openCOMOrderPage("false", accntNum, "");
			
			ExtentTest childTest13=extent.startTest("Wait for Disconnection Date Mark Finished Task");
			test.appendChild(childTest13);
			com=createChildTest(childTest13,extent,com);
			order=createChildTest(childTest13,extent,order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest14=extent.startTest("Wait for effective date Mark Finish Task");
			test.appendChild(childTest14);
			com=createChildTest(childTest14, extent, com);
			order=createChildTest(childTest14, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest15=extent.startTest("Send Serial Number for Converged Hardware");
			test.appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			order=createChildTest(childTest15,extent,order);
			order.navigateToOrderValidation(31, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Verify COM and SOM Instances");
			test.appendChild(childTest16);
			com=createChildTest(childTest16,extent,com);
			order=createChildTest(childTest15,extent,order);
			com.VerifyComSomInstances();
			
			ExtentTest childTest17=extent.startTest("Wait for Tech Confirm Mark Finished Task");
			test.appendChild(childTest17);
			com=createChildTest(childTest17, extent, com);
			order=createChildTest(childTest17, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest18=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest18);
			com=createChildTest(childTest18, extent, com);
			som=createChildTest(childTest18, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest19=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest19);
			repo=createChildTest(childTest19,extent,repo);
			order=createChildTest(childTest19,extent,order);	
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC110", 1, valuesToPassForValidation),"Attributes are not validated successfully");		
			order.navigateToOrderPage();
			
			ExtentTest childTest20=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest20);
			repo=createChildTest(childTest20,extent,repo);
			order=createChildTest(childTest20,extent,order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC110", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC110", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest21=extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest21);
			orderHistory=createChildTest(childTest21, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Active_xFi_PremiseMove_With_Gap");
		} catch (Exception e) {
			log.error("Error in Active_xFi_PremiseMove_With_Gap:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Verify_Waitfor_EffectiveDate_Completion_Future_PremiseMove_Order(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ",
					"Verify 'Wait for effective date' task completion for future dated premise move order");
			log.debug("Entering Verify_Waitfor_EffectiveDate_Completion_Future_PremiseMove_Order");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct = data.get("Phone_Product");
			String internetProduct = data.get("Internet_Product");
			String tvProduct = data.get("TV_Product");
			String phoneHardware = data.get("Phone_hardware");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			TVDctSrlNo = Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr = TVDctSrlNo;
			String[] hardwareType = { "DCT700" };
			String[] serialNumber = { tvHardwareSerialNbr };
			String techAppointment = data.get("Tech_Appointment");
			int initialComCount = Math.round(Float.valueOf(data.get("COM_Initial_Count")));
			int initialSomCount = Math.round(Float.valueOf(data.get("SOM_Initial_Count")));
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
			  test.appendChild(childTest0); landing = createChildTest(childTest0, extent,
			  landing); landing.openUrl();
			  
			  ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			  test.appendChild(childTest1); order = createChildTest(childTest1, extent,
			  order); order.customerInformation("Residential", "Yes");
			  
			  ExtentTest childTest3 = extent.startTest("Add Internet Product");
			  test.appendChild(childTest3); order = createChildTest(childTest3, extent,
			  order); order.addServicesAndFeacturesTab();
			  order.addServiceInternet(internetProduct);
			  
			  ExtentTest childTest4 = extent.startTest("Add Converged Hardware");
			  test.appendChild(childTest4); order = createChildTest(childTest4, extent,
			  order); // order.addConvergedHardware("Converged150",	  convergedHardWareSerialNbr);
			  order.addConvergedHardware1(convergedHardWareSerialNbr);
			  order.selectConvergedHardware(convergedHardware);
			  //order.deleteHardware("Switch Window");
			  
			  ExtentTest childTest5 = extent.startTest("Add TV with DCT 700 Hardware");
			  test.appendChild(childTest5); order = createChildTest(childTest5, extent,
			  order); order.addServiceTV(tvProduct);
			  order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			  
			  ExtentTest childTest6 = extent.startTest("Navigate To Tech Appointment Tab");
			  test.appendChild(childTest6); order = createChildTest(childTest6, extent,
			  order); order.selectInstallationOption("Self Connect No", techAppointment);
			  order.enteringChangesAuthorizedBy();
			  
			  ExtentTest childTest7 = extent.startTest("Select Method of confirmation");
			  test.appendChild(childTest7); order = createChildTest(childTest7, extent,
			  order); order.selectMethodOfConfirmation("Voice");
			  order.selectingSalesRepresentativeCheckBox();
			  
			  ExtentTest childTest8 = extent.startTest("Review and Finish");
			  test.appendChild(childTest8); order = createChildTest(childTest8, extent,
			  order); order.reviewAndFinishOrder();
			  
			  ExtentTest childTest9 = extent.startTest("Navigate To  COM, SOM Orders");
			  test.appendChild(childTest9); order = createChildTest(childTest9, extent,
			  order); String accntNum = order.getAccountNumber();
			  order.openCOMOrderPage("true", accntNum, "");
			  
			  ExtentTest childTest10 =
			  extent.startTest("Navigate to Check COM, SOM Initial record Counts");
			  test.appendChild(childTest10); com = createChildTest(childTest10, extent,
			  com); som = createChildTest(childTest10, extent, som);
			  com.verifyRecordsCountInCOMOrder(initialComCount);
			  som.verifyRecordsCountInSOMOrder(initialSomCount);
			  
			  ExtentTest childTest11 = extent.startTest("Navigate to Vancover Location");
			  test.appendChild(childTest11); order = createChildTest(childTest11, extent,
			  order); order.navigateToExistingAccountForPremise(accntNum);
			  
			  ExtentTest childTest12 = extent.startTest("Add Phone and VoiceMail");
			  test.appendChild(childTest12); 
			  order = createChildTest(childTest12, extent,  order);
			  order.addServicePhone("Add Product", phoneProduct);
			  
			  ExtentTest childTest13 =
			  extent.startTest("Add Phone Hardware without Serial Num");
			  test.appendChild(childTest13); 
			  order = createChildTest(childTest13, extent,
			  order); 
			  order.addPhoneHardwareWithoutSlNos(phoneHardware);
			  
			  ExtentTest childTest14 =
			  extent.startTest("Navigate To Tech Appointment Tab");
			  test.appendChild(childTest14); 
			  order = createChildTest(childTest14, extent,
			  order); order.selectInstallationOption("Self Connect No", "Yes");
			  order.techAppoinmentYes("", ""); 
			  order.techAppointmentWithGAP();
			  order.selectingSalesRepresentativeCheckBox();
			  //order.selectDefaultInstallationCheckBox();
			  order.addServicesAndFeacturesTab(); order.selectTodoListCheckBox();
			  
			  ExtentTest childTest15 = extent.startTest("Review and Finish");
			  test.appendChild(childTest15); order = createChildTest(childTest15, extent,
			  order); order.reviewAndFinishOrder();
			 
			
			
			ExtentTest childTest16 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.openCOMOrderPage("false", accntNum, "");
			
			ExtentTest childTest17 =extent.startTest("Mark finish for Wait for Disconnection");
			test.appendChild(childTest17);
			com=createChildTest(childTest17, extent, com);
			order=createChildTest(childTest17, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest18=extent.startTest("Check Wait for effective date in Service Information Tab");
			test.appendChild(childTest18);
			com=createChildTest(childTest18, extent, com);
			order=createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.navigateToServiceInformationTab();
			order.scrollDown();
			order.navigateToOrderPage();
			
			ExtentTest childTest22=extent.startTest("Navigate to Putty and Time Shifting 3 days");
			test.appendChild(childTest22);
			com=createChildTest(childTest22,extent,com);
			com.navigateToConnectPuttyAndTimeShift("3");
			landing.Login();
			
			/*ExtentTest childTest23=extent.startTest("Navigate To DB to Clear Cache And NCDO Reset Caches");
			test.appendChild(childTest23);
			order=createChildTest(childTest23,extent,order);
			landing.Login();
			order.navigateToConnectDB();
			order.navigateToNCdoResetCaches();*/
			
			ExtentTest childTest24=extent.startTest("Navigate To WF_Info page and check Process Variables");
			test.appendChild(childTest24);
			order=createChildTest(childTest24,extent,order);
			com=createChildTest(childTest24,extent,com);
			com.navigateToWfInfoJspPage();
			
			ExtentTest childTest25=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest25);
			order=createChildTest(childTest25, extent, order);
			order.openCOMOrderPage("false", accntNum, "");
	
			ExtentTest childTest26=extent.startTest("Navigate To SVG Diagram");
			test.appendChild(childTest26);
			com=createChildTest(childTest26,extent,com);
			order=createChildTest(childTest26,extent,order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.verifySVGDiagram("");
			order.navigateToOrderPage();
			
			ExtentTest childTest27=extent.startTest("Verify COM and SOM and CLEC Record counts");
			test.appendChild(childTest27);
			com=createChildTest(childTest27, extent, com);
			som=createChildTest(childTest27, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			
			ExtentTest childTest28=extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest28);
			orderHistory=createChildTest(childTest28, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Verify_Waitfor_EffectiveDate_Completion_Future_PremiseMove_Order");
		} catch (Exception e) {
			log.error("Error in Verify_Waitfor_EffectiveDate_Completion_Future_PremiseMove_Order:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Verify_AutoResume_YDays_PremiseMove_WithoutGap_Self_Install(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Verify auto resume after Y days for a premise move order without Gap and with self install type");
			log.debug("Entering Verify_AutoResume_YDays_PremiseMove_WithoutGap_Self_Install");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			String convergedHardware=data.get("Converged_hardware");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			TVDctSrlNo=Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr=TVDctSrlNo;
			String[] hardwareType={"DCT700"};
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

			ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing = createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.customerInformation("Residential", "Yes");

			ExtentTest childTest3 = extent.startTest("Add Phone and Internet Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			// order.deleteHardware("Switch Window");

			ExtentTest childTest5 = extent.startTest("Add TV with DCT 700 Hardware");
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
			order.navigateToOrderPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest13 = extent.startTest("Transfer Created Account to the another location 2777");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.navigateToVancoverLocationId(accntNum);

			ExtentTest childTest14 = extent.startTest("Assign A phone number to each line");
			test.appendChild(childTest14);
			order = createChildTest(childTest14, extent, order);
			order.addServicePhone("", phoneProduct);
			order.selectPortOut();

			ExtentTest childTest15 = extent.startTest("Navigate To Tech Appointment With Retail PickUp");
			test.appendChild(childTest15);
			order = createChildTest(childTest15, extent, order);
			order.selectInstallationOption("Self Connect Yes", "");
			order.techAppointmentNo("Retail Pickup", "", "");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest16 = extent.startTest("Review and Finish");
			test.appendChild(childTest16);
			order = createChildTest(childTest16, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest17=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest17);
			order=createChildTest(childTest17, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");
			
			ExtentTest childTest18=extent.startTest("Check Effective date in Service Information Tab");
			test.appendChild(childTest18);
			com=createChildTest(childTest18, extent, com);
			order=createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			order.navigateToOrderPage();
			
			ExtentTest childTest19=extent.startTest("Navigate to Putty and Time Shifting 1 day");
			test.appendChild(childTest19);
			com=createChildTest(childTest19,extent,com);
			com.navigateToConnectPuttyAndTimeShift("1");
			landing.Login();
			
			/*ExtentTest childTest20=extent.startTest("Navigate To DB to Clear Cache And NCDO Reset Caches");
			test.appendChild(childTest20);
			order=createChildTest(childTest20,extent,order);
			landing.Login();
			order.navigateToConnectDB();
			order.navigateToNCdoResetCaches();*/
			
			ExtentTest childTest21=extent.startTest("Navigate To E911 JOB Run");
			test.appendChild(childTest21);
			com=createChildTest(childTest21,extent,com);
			order=createChildTest(childTest21, extent, order);
			com.navigateToJobMonitorURL("E911");
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			
			ExtentTest childTest22=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest22);
			com=createChildTest(childTest22, extent, com);
			som=createChildTest(childTest22, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);

			ExtentTest childTest23=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest23);
			orderHistory=createChildTest(childTest23, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Verify_AutoResume_YDays_PremiseMove_WithoutGap_Self_Install");
		} catch (Exception e) {
			log.error("Error in Verify_AutoResume_YDays_PremiseMove_WithoutGap_Self_Install:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Validate_Premise_Move_Residental_Account_with_Internet_TV(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Validate Premise move Residental account as Tenant");
			log.debug("Entering Validate_Premise_Move_Residental_Account_with_Internet_TV");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			String internetProduct=data.get("Internet_Product");
			String internetHardware=data.get("Internet_hardware");
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
			
			String tvProduct=data.get("TV_Product");
			BlueCurveWirelessSrlNo = Utility.generateHardwareSerailNum(BlueCurveWirelessSrlNo);
			String BlueCurveWireless4KPlayer418SrlNum = BlueCurveWirelessSrlNo;
			String[] hardwareType = { "BlueCurve TV Wireless 4K Player"};
			String[] serialNumber = {BlueCurveWireless4KPlayer418SrlNum};
		
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
//			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest, extent, landing);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.navigateToCDIchangeAccountType("All");
//			order.openOEStubTochangeLocationCoaxial("false");
			landing.Login();
			
			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();
			
			ExtentTest childTest1 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order = createChildTest(childTest1, extent, order);
			order.mduCustomerInformation("BulkMaster", "", "Mailout", "Yes");
			
			ExtentTest childTest2 = extent.startTest("Add Internet And TV Product");
			test.appendChild(childTest2);
			order = createChildTest(childTest2, extent, order);
			order.addServicesAndFeacturesTab();
 			order.addServiceBulkInternet("Bulk Fibre+ 300");
 			order.addBulkInternetProduct("Base Price - Bulk Fibre+ 300","Rental BlueCurve Gateway WiFi Modem (XB6) - 348");
 			order.addServiceBulkTV("Bulk Total TV");
 			order.addBulkTVProduct("Base Price - Bulk Total TV (Canada)","BlueCurve TV Player Wireless 4K Rental");
 			order.addPromotionsForBulkProduct();
 			
 			ExtentTest childTest3 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.selectInstallationOption("", techAppointment);
			order.enteringChangesAuthorizedBy();
 			
 			
 			ExtentTest childTest4 = extent.startTest("Review and Finish");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.reviewOrder();
			order.clickFinishOrder();
			
			ExtentTest childTest5=extent.startTest("Navigate To COM, SOM, CLEC Orders");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			order.refreshPage();
			order.refreshPage();

			ExtentTest childTest6=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest6);
			com=createChildTest(childTest6, extent, com);
			order=createChildTest(childTest6, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			
			ExtentTest childTest7 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest7);
			orderHistory = createChildTest(childTest7, extent, orderHistory);
//			String accntNum2=order.getAccountNumber();
			orderHistory.navigateToOrderHistoryUrl(accntNum, "Product Summary");
//			String accntNum="58736236731";
			
			ExtentTest childTest8=extent.startTest("Navigate to Tenant Account");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
//			String accntNum=order.getAccountNumber();
			order.navigateTomasterCustOrderForTenantAccount(accntNum);

//			ExtentTest childTest9 = extent.startTest("Add Tenant Product");
//			test.appendChild(childTest9);
//			order = createChildTest(childTest9, extent, order);
//			order.addServicesAndFeacturesTab();
//			order.addProductCategory();
			
			ExtentTest childTest10=extent.startTest("Add TV Hardware");
			test.appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceTV("Total TV");
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			
			ExtentTest childTest11 = extent.startTest("Add Converged Hardware");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			
			ExtentTest childTest12 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.selectInstallationOption("Self Connect No", "No");
			order.enteringChangesAuthorizedBy();
			
			ExtentTest childTest13 = extent.startTest("Review and Finish");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			order.reviewOrder();
			order.clickFinishOrder();
			
			ExtentTest childTest14=extent.startTest("Navigate To COM, SOM, CLEC Orders");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			String accntNum1=order.getAccountNumber();
			String accntNum3=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum1, "Yes");
			order.refreshPage();
			order.refreshPage();
			
			ExtentTest childTest15 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest15);
			orderHistory = createChildTest(childTest15, extent, orderHistory);
//			String accntNum2=order.getAccountNumber();
			orderHistory.navigateToOrderHistoryUrl(accntNum1, "Product Summary");
			
			ExtentTest childTest16 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest16);
			landing = createChildTest(childTest16, extent, landing);
			landing.openUrl();
			
			ExtentTest childTest17 = extent.startTest("Fill Customer Info");
			test.appendChild(childTest17);
			order = createChildTest(childTest17, extent, order);
			order.customerInformation("Residential", "Yes");
			
			ExtentTest childTest18 = extent.startTest("Add Internet Product");
			test.appendChild(childTest18);
			order = createChildTest(childTest18, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet("Fibre+ 300");
			
			ExtentTest childTest19=extent.startTest("Add Internet Hardware");
			test.appendChild(childTest19);
			order=createChildTest(childTest19, extent, order);
			order.addInternetHardware("Hitron", internetHardwareSerialNbr);
			
			ExtentTest childTest20 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest20);
			order = createChildTest(childTest20, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			
			ExtentTest childTest21 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest21);
			order = createChildTest(childTest21, extent, order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest22 = extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest22);
			order = createChildTest(childTest22, extent, order);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();
			order.addServicesAndFeacturesTab();
			
			ExtentTest childTest23 = extent.startTest("Review and Finish");
			test.appendChild(childTest23);
			order = createChildTest(childTest23, extent, order);
			order.reviewAndFinishOrder();
			
			ExtentTest childTest24 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest24);
			orderHistory = createChildTest(childTest24, extent, orderHistory);
//			String accntNum2=order.getAccountNumber();
			orderHistory.navigateToOrderHistoryUrl(accntNum3, "Product Summary");
			
			ExtentTest childTest25 = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest25);
			landing = createChildTest(childTest25, extent, landing);
			order = createChildTest(childTest25, extent, order);
			order.navigateFirstToOeStubServer();
//			order.openOEStubTochangeLocationCoaxial("false");
			order.navigateToCDIchangeAccountType("All");
			landing.major1Login();
			
//			String accntNum="58736236803";
//			String accntNum1="58736236805";
//			String accntNum3="58736236807";
			
			ExtentTest childTest26=extent.startTest("Navigate to Tenant Account");
			test.appendChild(childTest26);
			order=createChildTest(childTest26,extent,order);
//			String accntNum=order.getAccountNumber();
			order.navigateTomasterCustOrderToTenantAccount(accntNum3, accntNum, accntNum1);
			order.warningokToClose();
			
			ExtentTest childTest27 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest27);
			order = createChildTest(childTest27, extent, order);
			order.selectInstallationOption("Self Connect No", "No");
			order.enteringChangesAuthorizedBy();
			
			ExtentTest childTest28 = extent.startTest("Review and Finish");
			test.appendChild(childTest28);
			order = createChildTest(childTest28, extent, order);
			order.reviewOrder();
			order.clickFinishOrder();
			
			ExtentTest childTest29=extent.startTest("Navigate To COM, SOM, CLEC Orders");
			test.appendChild(childTest29);
			order=createChildTest(childTest29, extent, order);
//			String accntNum1=order.getAccountNumber();
			String accntNum4=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum4, "Yes");
			order.refreshPage();
			
			ExtentTest childTest30 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest30);
			orderHistory = createChildTest(childTest30, extent, orderHistory);
//			String accntNum2=order.getAccountNumber();
			orderHistory.navigateToOrderHistoryUrl(accntNum4, "Product Summary");
			
			log.debug("Validate_Premise_Move_Residental_Account_with_Internet_TV");
		} catch (Exception e) {
			log.error("Error in Validate_Premise_Move_Residental_Account_with_Internet_TV:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	
	
	public static void main(String args[]) 
	{
		InputStream log4j=PremiseMoveCases.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
		PropertyConfigurator.configure(log4j);
	}
}
