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

public class PendingOrderCases extends SeleniumTestUp {
	
	LandingPage landing=null;
	OrderCreationPage order=null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	ValidateReport repo=null;
	OrderHistoryPage orderHistory=null;
	SoftAssert softAssert=new SoftAssert();
	ArrayList<String> valuesToPassForValidation=new ArrayList<String>();
	
	Logger log=Logger.getLogger(PendingOrderCases.class);

	@Test(dataProvider="getTestData", description="Priority-1")
	@SuppressWarnings(value={ "rawtypes" })
	public void Pending_Triple_Play_Theme_Channels(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name: ","Create a Future Dated install order for a triple play account- with XB6 converged hardware and X1 Gateway and Service agreement. Also add theme packs and Pick n Pay channels");
			log.debug("Entering Pending_Triple_Play_Theme_Channels");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			String phoneProduct=data.get("Phone_Product");
			String tvProduct=data.get("TV_Product");
			String convergedHardware=data.get("Converged_hardware");
			TVBoxSrlNo=Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr=TVBoxSrlNo;
			TVPortalSrlNo=Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr=TVPortalSrlNo;
			String[] hardwareType={"ShawBlueSkyTVbox526","ShawBlueSkyTVportal416"};
			String[] serialNumber={tvBoxSerialNbr,tvPortalSerialNbr};
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
			landing.Login();

			ExtentTest childTest0=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest2=extent.startTest("Select Bill Type");
			test.appendChild(childTest2);
			order=createChildTest(childTest2, extent, order);
			order.selectBillType("");

			ExtentTest childTest3=extent.startTest("Add Phone and Internet");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4=extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);

			ExtentTest childTest5=extent.startTest("Add Digital Channel Section Pick10 Pack2");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addingPick10pack2DigitalChannels("2nd Wrench");
			order.addDigitalChannelSectionTheme();
			
			ExtentTest childTest6=extent.startTest("Add Promotions And Coupons");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.addPendingPromotions();

			ExtentTest childTest7=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest7);
			order=createChildTest(childTest7, extent, order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			order.selectTodoListCheckBox();

			ExtentTest childTest8=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest8);
			order=createChildTest(childTest8, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("Yes", "");
			
			ExtentTest childTest9=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest10=extent.startTest("Review and Finish");
			test.appendChild(childTest10);
			order=createChildTest(childTest10, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest11=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest11);
			order=createChildTest(childTest11, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest12=extent.startTest("Wait for Effective date Mark finish");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			order=createChildTest(childTest12, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			//com.setMarkFinishedTask();
			com.setMarkFinishedSpecificTask("Wait for effective date");
			order.navigateToOrderPage();

			ExtentTest childTest13=extent.startTest("Send converged hardware serial number");
			test.appendChild(childTest13);
			com=createChildTest(childTest13, extent, com);
			order=createChildTest(childTest13, extent, order);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest14=extent.startTest("Send Serial number for TV Box");
			test.appendChild(childTest14);
			com=createChildTest(childTest14, extent, com);
			order=createChildTest(childTest14, extent, order);
			order.navigateToOrderValidation(11, "No", "No", "COM");
			String tvHardwareType=com.getTVHardwareType();
			if (tvHardwareType.contains("NGV Gateway")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();
			
			ExtentTest childTest15=extent.startTest("Send Serial number for TV Portal");
			test.appendChild(childTest15);
			com=createChildTest(childTest15, extent, com);
			order=createChildTest(childTest13, extent, order);
			order.navigateToOrderValidation(12, "No", "No", "COM");
			String tvHardwareType1=com.getTVHardwareType();
			if (tvHardwareType1.contains("NGV Portal")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();
		
			ExtentTest childTest16=extent.startTest("Wait for Tech Confirm Mark finish Task");
			test.appendChild(childTest16);
			com=createChildTest(childTest16, extent, com);
			order=createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest17=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest17);
			com=createChildTest(childTest17, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest18=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest18);
			com=createChildTest(childTest18, extent, com);
			order=createChildTest(childTest18, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			
			ExtentTest childTest19=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest19);
			com=createChildTest(childTest19, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest20=extent.startTest("Navigate To Customer Portal");
			test.appendChild(childTest20);
			com=createChildTest(childTest20, extent, com);
			order=createChildTest(childTest20, extent, order);
			com.navigateToCustomerDiscountsAndContracts("quote Section");
			order.navigateToOrderPage();

			ExtentTest childTest21=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest21);
			com=createChildTest(childTest21, extent, com);
			som=createChildTest(childTest21, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest22=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest22);
			repo=createChildTest(childTest22, extent, repo);
			order=createChildTest(childTest22, extent, order);
			order.navigateToOrderValidation(3, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC45", 1, valuesToPassForValidation),"Attributes are not validated successfully");	
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC45", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(11, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway = repo.validateSOMIntegrationReportsSpcl(xls,"TC45", 3, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway,"Attributes are not validated successfully");
			if(!is4xgateway)
			{
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC45",3, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();
			order.navigateToOrderValidation(12, "Yes", "No", "SOM");
			if(is4xgateway)
			{
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum,tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC45", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			else
			{
				valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum,tvBoxSerialNbr,"4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC45", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();
			
			ExtentTest childTest23=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest23);
			repo=createChildTest(childTest23, extent, repo);
			order=createChildTest(childTest23, extent, order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr,tvBoxSerialNbr,tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC45", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr, tvBoxSerialNbr,tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC45", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest24=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest24);
			orderHistory=createChildTest(childTest24, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Pending_Triple_Play_Theme_Channels");
		} catch (Exception e) {
			log.error("Error in Pending_Triple_Play_Theme_Channels:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	public static void main(String args[]) {
		InputStream log4j=PendingOrderCases.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
		PropertyConfigurator.configure(log4j);
	}
}
