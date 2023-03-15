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

public class LevelUpCases extends SeleniumTestUp {

	LandingPage landing=null;
	OrderCreationPage order=null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	ValidateReport repo=null;
	OrderHistoryPage orderHistory=null;
	SoftAssert softAssert=new SoftAssert();
	ArrayList<String> valuesToPassForValidation=new ArrayList<String>();
	public String sheetName="TestData";
	
	Logger log=Logger.getLogger(LevelUpCases.class);
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Reassign_levelupBtn_Display_OE_ShawPortal_SMG_Devices_ModifyOrder(Hashtable<String, String> data) throws Exception {
		try {
			log.debug("Entering Reassign_levelupBtn_Display_OE_ShawPortal_SMG_Devices_NewOrder");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String tvProduct=data.get("TV_Product");
			TVMoxiSrlNo=Utility.generateHardwareSerailNum(TVMoxiSrlNo);
			String tvMoxiSrlNbr=TVMoxiSrlNo;
			TVShawGatewaySrlNo=Utility.generateHardwareSerailNum(TVShawGatewaySrlNo);
			String tvHardwareSerialNbr=TVShawGatewaySrlNo;
			TVShawPortalSrlNo=Utility.generateHardwareSerailNum(TVShawPortalSrlNo);
			String tvShawPortalSlrNbr1=TVShawPortalSrlNo;
			TVShawPortalSrlNo=Utility.generateHardwareSerailNum(TVShawPortalSrlNo);
			String tvShawPortalSlrNbr2=TVShawPortalSrlNo;
			TVShawPortalSrlNo=Utility.generateHardwareSerailNum(TVShawPortalSrlNo);
			String tvShawPortalSlrNbr3=TVShawPortalSrlNo;
			TVShawPortalSrlNo=Utility.generateHardwareSerailNum(TVShawPortalSrlNo);
			String tvShawPortalSlrNbr4=TVShawPortalSrlNo;
			TVShawPortalSrlNo=Utility.generateHardwareSerailNum(TVShawPortalSrlNo);
			String tvShawPortalSlrNbr5=TVShawPortalSrlNo;
			
			String[] hardwareType={"ShawGateway"};
			String[] serialNumber={tvHardwareSerialNbr};
			
			String[] hardwareType1={"ShawGatewayHDPVR"};
			String[] serialNumber1={tvMoxiSrlNbr};
			
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
			
			ExtentTest childTest3=extent.startTest("Add TV With Shaw Gateway And Shaw Portal(SMG) Devices");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			order.addTVWithNoOfShawPortalHardwares(tvShawPortalSlrNbr1, tvShawPortalSlrNbr2, tvShawPortalSlrNbr3, tvShawPortalSlrNbr4, tvShawPortalSlrNbr5);

			ExtentTest childTest4=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest5=extent.startTest("Review and Finish");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest6=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			
			ExtentTest childTest7=extent.startTest("Verify COM, SOM Initial Record Counts");
			test.appendChild(childTest7);
			com=createChildTest(childTest7,extent,com);
			som=createChildTest(childTest7,extent,som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest8=extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.navigateToCustOrderPage("true",accntNum);
			
			ExtentTest childTest9=extent.startTest("Verify re-assign/level up buttons for shaw portal(SMG)");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			order.verifyReassignLevelUpButtonsForShawPortalSMG();
			
			ExtentTest childTest10=extent.startTest("Click on level up button for all portal devices");
			test.appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			order.navigateToClickOnLevelUpButtons();
			
			ExtentTest childTest11=extent.startTest("Delete SMG gateway And Add Moxi Gateway");
			test.appendChild(childTest11);
			order=createChildTest(childTest11,extent,order);
			order.addTVHardwareWithSrlNo(hardwareType1,serialNumber1);
			
			ExtentTest childTest12=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest12);
			order=createChildTest(childTest12, extent, order);
			order.techAppointmentWithCurrentDate();
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
			
			ExtentTest childTest15=extent.startTest("Wait for Provisioning start Mark Finish Task");
			test.appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			order=createChildTest(childTest15,extent,order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Wait For Tech Confirm Mark Finish task");
			test.appendChild(childTest16);
			com=createChildTest(childTest16,extent,com);
			order=createChildTest(childTest16,extent,order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
	
			ExtentTest childTest17=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest17);
			com=createChildTest(childTest17, extent, com);
			som=createChildTest(childTest17, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest18=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest18);
			repo=createChildTest(childTest18, extent, repo);
			order=createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(46, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC129", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
	
			order.navigateToOrderValidation(47, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC129", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(48, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC129", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(49, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC129", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(50, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC129", 5, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest19=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest19);
			orderHistory=createChildTest(childTest19, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Reassign_levelupBtn_Display_OE_ShawPortal_SMG_Devices_ModifyOrder");
		} catch (Exception e) {
			log.error("Error in Reassign_levelupBtn_Display_OE_ShawPortal_SMG_Devices_ModifyOrder:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={ "rawtypes" })
	public void Reassign_levelupBtn_Display_OE_ShawGatewayPortal_Moxi_CustOwned_Devices(Hashtable<String, String> data) throws Exception {
		try {
			log.debug("Entering Reassign_levelupBtn_Display_OE_ShawGatewayPortal_Moxi_CustOwned_Devices");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String tvProduct=data.get("TV_Product");
			TVMoxiSrlNo=Utility.generateHardwareSerailNum(TVMoxiSrlNo);
			String tvMoxiGatewaySrlNbr=TVMoxiSrlNo;
			TVShawGatewaySrlNo=Utility.generateHardwareSerailNum(TVShawGatewaySrlNo);
			String TVMoxiHDPVR515SrlNbr=TVShawGatewaySrlNo;
			
			MoxiPortal415SrlNo=Utility.generateHardwareSerailNum(MoxiPortal415SrlNo);
			String tvShawPortalSlrNbr1=MoxiPortal415SrlNo;
			MoxiPortal415SrlNo=Utility.generateHardwareSerailNum(MoxiPortal415SrlNo);
			String tvShawPortalSlrNbr2=MoxiPortal415SrlNo;
			MoxiPortal415SrlNo=Utility.generateHardwareSerailNum(MoxiPortal415SrlNo);
			String tvShawPortalSlrNbr3=MoxiPortal415SrlNo;
			
			String[] hardwareType={"MoxiGatewayHDPVR_Buy"};
			String[] serialNumber={tvMoxiGatewaySrlNbr};
			
			String[] hardwareType1={"ShawGatewayHDPVR"};
			String[] serialNumber1={TVMoxiHDPVR515SrlNbr};
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

			ExtentTest childTest3=extent.startTest("Add TV With Shaw Gateway And Shaw Portal(SMG) Devices");
			test.appendChild(childTest3);
			order=createChildTest(childTest3,extent,order);
			order.addServicesAndFeacturesTab();
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
			order.addTVWithShawPortalCustOwnedDevices(tvShawPortalSlrNbr1, tvShawPortalSlrNbr2, tvShawPortalSlrNbr3);

			ExtentTest childTest4=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.selectingSalesRepresentativeCheckBox();
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest5=extent.startTest("Review and Finish");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest6=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			
			ExtentTest childTest7=extent.startTest("Verify COM, SOM Initial Record Counts");
			test.appendChild(childTest7);
			com=createChildTest(childTest7,extent,com);
			som=createChildTest(childTest7,extent,som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);

			ExtentTest childTest8=extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.navigateToCustOrderPage("true",accntNum);
			
			ExtentTest childTest9=extent.startTest("Verify re-assign/level up buttons for shaw portal(SMG)");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			order.verifyReassignLevelUpButtonsForShawPortalSMG();
			
			ExtentTest childTest10=extent.startTest("Click on level up button for all portal devices");
			test.appendChild(childTest10);
			order=createChildTest(childTest10,extent,order);
			order.navigateToClickOnLevelUpButtons();
			
			ExtentTest childTest11=extent.startTest("Delete Moxi gateway 525 And Add Moxi Gateway 515");
			test.appendChild(childTest11);
			order=createChildTest(childTest11,extent,order);
			order.addTVHardwareWithSrlNo(hardwareType1,serialNumber1);
			
			ExtentTest childTest12=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest12);
			order=createChildTest(childTest12, extent, order);
			order.techAppointmentWithCurrentDate();
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
			
			ExtentTest childTest15=extent.startTest("Wait for Provisioning start Mark Finish Task");
			test.appendChild(childTest15);
			com=createChildTest(childTest15,extent,com);
			order=createChildTest(childTest15,extent,order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Wait For Tech Confirm Mark Finish task");
			test.appendChild(childTest16);
			com=createChildTest(childTest16,extent,com);
			order=createChildTest(childTest16,extent,order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
	
			ExtentTest childTest17=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest17);
			com=createChildTest(childTest17, extent, com);
			som=createChildTest(childTest17, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			ExtentTest childTest18=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest18);
			repo=createChildTest(childTest18, extent, repo);
			order=createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(46, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC129", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
	
			order.navigateToOrderValidation(47, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC129", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(48, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC129", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(49, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC129", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(50, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC129", 5, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest19=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest19);
			orderHistory=createChildTest(childTest19, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Reassign_levelupBtn_Display_OE_ShawGatewayPortal_Moxi_CustOwned_Devices");
		} catch (Exception e) {
			log.error("Error in Reassign_levelupBtn_Display_OE_ShawGatewayPortal_Moxi_CustOwned_Devices:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	public static void main(String args[]) {
		InputStream log4j = LevelUpCases.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
		PropertyConfigurator.configure(log4j);
	}
}

