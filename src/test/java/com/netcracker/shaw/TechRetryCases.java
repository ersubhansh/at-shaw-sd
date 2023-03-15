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

public class TechRetryCases extends SeleniumTestUp {
	
	LandingPage landing=null;
	OrderCreationPage order=null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	ValidateReport repo=null;
	OrderHistoryPage orderHistory=null;
	SoftAssert softAssert=new SoftAssert();
	ArrayList<String> valuesToPassForValidation=new ArrayList<String>();

	Logger log=Logger.getLogger(TechRetryCases.class);

	@Test(dataProvider="getTestData", description="Golden Suite")
	@SuppressWarnings(value={"rawtypes"})
	public void TechRetry_Hardware_Swap_DPT_Hitron_LegacyTV(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Validate the Tech Retry functionality with different Serial Number during hardware swap for an existing account with DPT, Hitron and Legacy TV device through FFM");
			log.debug("Entering TechRetry_Hardware_Swap_DPT_Hitron_LegacyTV");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String phoneProduct=data.get("Phone_Product");
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			String phoneHardware=data.get("Phone_hardware");
			PhSrlNo=Utility.generateHardwareSerailNum(PhSrlNo);
			String phoneHardwareSerialNbr=PhSrlNo;
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
			TVDctSrlNo=Utility.generateHardwareSerailNum(TVDctSrlNo);
			String tvHardwareSerialNbr=TVDctSrlNo;
			String[] hardwareType={"DCT700"};
			String[] serialNumber={tvHardwareSerialNbr};
			String diffPhHardwareSrlNbr=PhSrlNo;
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String diffIntHardwareSrlNbr=IntHitronSrlNo;
			TVDctSrlNo=Utility.generateHardwareSerailNum(TVDctSrlNo); 
			String diffTvHardwareSrlNbr=TVDctSrlNo;
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

			ExtentTest childTest3 = extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr = order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();

			ExtentTest childTest4 = extent.startTest("Add Phone hardware without srl Nbr");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			order.addPhoneHardwareWithoutSlNo(phoneHardware, "1stHardware");

			ExtentTest childTest5 = extent.startTest("Add Internet Product");
			test.appendChild(childTest5);
			order = createChildTest(childTest5, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6 = extent.startTest("Add Internet hardware without srl Nbr");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.addInternetHardwareWithoutSlNos("Hitron");
			// order.deleteConvergedHardware();

			ExtentTest childTest7 = extent.startTest("Add TV product");
			test.appendChild(childTest7);
			order = createChildTest(childTest7, extent, order);
			order.addServiceTV(tvProduct);

			ExtentTest childTest8 = extent.startTest("Add TV Hardware without srl Nbr");
			test.appendChild(childTest8);
			order = createChildTest(childTest8, extent, order);
			order.addTVHardwareWithOutSrlNo(hardwareType);

			ExtentTest childTest9 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest9);
			order = createChildTest(childTest9, extent, order);
			order.selectInstallationOption("", techAppointment);
			order.techAppoinmentYes("", "");
			order.enteringChangesAuthorizedBy();

			ExtentTest childTest10 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest10);
			order = createChildTest(childTest10, extent, order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest11 = extent.startTest("Select Default installation fee Check box");
			test.appendChild(childTest11);
			order = createChildTest(childTest11, extent, order);
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest12 = extent.startTest("Review and Finish");
			test.appendChild(childTest12);
			order = createChildTest(childTest12, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest13 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest13);
			order = createChildTest(childTest13, extent, order);
			String accntNum = order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest14 = extent.startTest("Wait for Effective date Mark Finish task");
			test.appendChild(childTest14);
			com = createChildTest(childTest14, extent, com);
			order = createChildTest(childTest14, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedSpecificTask("Wait for effective date");
			order.navigateToOrderPage();
			
			ExtentTest childTest15=extent.startTest("Send serial number for Phone hardware");
			test.appendChild(childTest15);
			com=createChildTest(childTest15, extent, com);
			order=createChildTest(childTest15, extent, order);
			order.navigateToOrderValidation(5, "No", "No", "COM");
			com.sendSerialNbrTask(phoneHardwareSerialNbr);
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Send serial number for Internet hardware");
			test.appendChild(childTest16);
			com=createChildTest(childTest16, extent, com);
			order=createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(8, "No", "No", "COM");
			com.sendSerialNbrTask(internetHardwareSerialNbr);
			order.navigateToOrderPage();
			
			ExtentTest childTest17=extent.startTest("Send serial number for TV hardware");
			test.appendChild(childTest17);
			com=createChildTest(childTest17, extent, com);
			order=createChildTest(childTest17, extent, order);
			order.navigateToOrderValidation(11, "No", "No", "COM");
			com.sendSerialNbrTask(tvHardwareSerialNbr);
			order.navigateToOrderPage();
			
			ExtentTest childTest29 = extent.startTest("Navigate To send SN2 for Phone Hardware");
			test.appendChild(childTest29);
			com = createChildTest(childTest29, extent, com);
			order = createChildTest(childTest29, extent, order);
			order.navigateToOrderValidation(5, "No", "No", "COM");
			com.sendSerialNbrTask(diffPhHardwareSrlNbr);
			order.navigateToOrderPage();

			ExtentTest childTest30 = extent.startTest("Navigate To send SN2 for Internet hardware");
			test.appendChild(childTest30);
			com = createChildTest(childTest30, extent, com);
			order = createChildTest(childTest30, extent, order);
			order.navigateToOrderValidation(8, "No", "No", "COM");
			com.sendSerialNbrTask(diffIntHardwareSrlNbr);
			order.navigateToOrderPage();

			ExtentTest childTest31 = extent.startTest("Navigate To send SN2 for TV hardware");
			test.appendChild(childTest31);
			com = createChildTest(childTest31, extent, com);
			order = createChildTest(childTest31, extent, order);
			order.navigateToOrderValidation(11, "No", "No", "COM");
			com.sendSerialNbrTask(diffTvHardwareSrlNbr);
			order.navigateToOrderPage();

			ExtentTest childTest18 = extent.startTest("Wait For Tech Confirm Mark Finish Task");
			test.appendChild(childTest18);
			com = createChildTest(childTest18, extent, com);
			order = createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			 

			ExtentTest childTest19=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest19);
			com=createChildTest(childTest19, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest20=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest20);
			com=createChildTest(childTest20, extent, com);
			order=createChildTest(childTest20, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest21=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest21);
			com=createChildTest(childTest21, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest22=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest22);
			com=createChildTest(childTest22, extent, com);
			som=createChildTest(childTest22, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
		
			ExtentTest childTest23=extent.startTest("Validate SOM Integration Report");
			test.appendChild(childTest23);
			repo=createChildTest(childTest23,extent,repo);
			order=createChildTest(childTest23,extent,order);
			/*order.navigateToOrderValidation(33, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC4", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC4", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();*/
			
			order.navigateToOrderValidation(34, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC4", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC4", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(35, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC4", 5, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(3, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC4", 6, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC4", 7, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(10, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC4", 8, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest24 = extent.startTest("Validate COM Integration Report");
			test.appendChild(childTest24);
			repo = createChildTest(childTest24, extent, repo);
			order = createChildTest(childTest24, extent, order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneHardwareSerialNbr,
					internetHardwareSerialNbr, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC4", 1, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, phoneNbr, phoneHardwareSerialNbr,
					internetHardwareSerialNbr, tvHardwareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls, "TC4", 2, valuesToPassForValidation),
					"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest25 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest25);
			orderHistory = createChildTest(childTest25, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving TechRetry_Hardware_Swap_DPT_Hitron_LegacyTV");
		} catch (Exception e) {
			log.error("Error in TechRetry_Hardware_Swap_DPT_Hitron_LegacyTV:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider="getTestData", description="Golden Suite")
	@SuppressWarnings(value={ "rawtypes" })
	public void TechRetry_PhoneFail(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Tech Retry with different Serial Number when Internet provisioning is successful and Phone provisioning failed on a converged device");
			log.debug("Entering TechRetry_PhoneFail");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNo=ConvgdSrlNo;
			String internetProduct=data.get("Internet_Product");
			String phoneProduct=data.get("Phone_Product");
			String convergedHardware=data.get("Converged_hardware");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String swapConvergedHarwareNo=ConvgdSrlNo;
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));
			
			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			
			ExtentTest childTest1=extent.startTest("Navigate to Stub URL Fail");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			landing=createChildTest(childTest1, extent, landing);
			order.navigateToChangeToggleValueToFail("Phone");
			landing.Login();
			
			ExtentTest childTest2=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest2);
			landing=createChildTest(childTest2, extent, landing);
			landing.openUrl();

			ExtentTest childTest3=extent.startTest("Fill Customer Info");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest5=extent.startTest("Add Phone and Internet Products");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNo);
			order.addConvergedHardware1(convergedHardWareSerialNo);
			order.selectConvergedHardware(convergedHardware);
			//order.deleteHardware("Switch Window");

			ExtentTest childTest7=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest7);
			order=createChildTest(childTest7, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.techAppoinmentYes("Yes", "");
			order.selectingSalesRepresentativeCheckBox();
			
			ExtentTest childTest8=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest8);
			order=createChildTest(childTest8,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest9=extent.startTest("Review and Finish");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest10=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest10);
			order=createChildTest(childTest10, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			
			ExtentTest childTest11=extent.startTest("Wait for Effective date Mark Finish task");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			com.verifySVGDiagram("");
			order.navigateToOrderPage();
			
			ExtentTest childTest12=extent.startTest("Wait for Provisioning start Mark Finished Task");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			order=createChildTest(childTest12, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest13 = extent.startTest("Send same serial number for Converged Hardware");
			test.appendChild(childTest13);
			com = createChildTest(childTest13, extent, com);
			som = createChildTest(childTest13, extent, som);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNo);
			order.navigateToOrderPage();
			som.navigateToSOMOrder();
			com.navigateToCOMOrder();
		
			ExtentTest childTest14=extent.startTest("Navigate to Stub to Pass phone");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			order.navigateToOeStubToPassServices("Phone");
			order.navigateToOrderPage();

			ExtentTest childTest15=extent.startTest("Send Different serial number for Converged Hardware");
			test.appendChild(childTest15);
			order=createChildTest(childTest15, extent, order);
			com=createChildTest(childTest15, extent, com);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(swapConvergedHarwareNo);
			order.navigateToOrderPage();

			ExtentTest childTest16=extent.startTest("Wait For Tech Confirm Mark Finish Task");
			test.appendChild(childTest16);
			com=createChildTest(childTest16, extent, com);
			order=createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
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
		
			ExtentTest childTest20=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest20);
			com=createChildTest(childTest20, extent, com);
			som=createChildTest(childTest20, extent, som);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest21=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest21);
			repo=createChildTest(childTest21, extent, repo);
			order=createChildTest(childTest21,extent,order);
			order.navigateToOrderValidation(34, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNo));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC14", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNo));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC14", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(8, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, swapConvergedHarwareNo));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC14", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(4, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNo));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC14", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(5, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, swapConvergedHarwareNo));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC14", 5, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest22=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest22);
			repo=createChildTest(childTest22, extent, repo);
			order=createChildTest(childTest22,extent,order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, swapConvergedHarwareNo));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC14", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, swapConvergedHarwareNo));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC14", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest23=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest23);
			orderHistory=createChildTest(childTest23, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving TechRetry_PhoneFail");
		} catch (Exception e) {
			log.error("Error in TechRetry_PhoneFail:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-1")
	@SuppressWarnings(value={ "rawtypes" })
	public void TechRetry_InternetFail_Phone_Success_SameSlNo(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Tech Retry with same Serial Number when Internet provisioning failed and Phone provisioning is successful on a converged device");
			log.debug("Entering TechRetry_InternetFail_Phone_Success_SameSlNo");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			String internetProduct=data.get("Internet_Product");
			String phoneProduct=data.get("Phone_Product");
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
			
			ExtentTest childTest1=extent.startTest("Navigate to Stub URL To Fail");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			landing=createChildTest(childTest1, extent, landing);
			order.navigateToChangeToggleValueToFail("Internet");
			order.navigateToChangeToggleValueToFail("Converged Hardware");
			landing.Login();
			
			ExtentTest childTest2=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest2);
			landing=createChildTest(childTest2, extent, landing);
			landing.openUrl();

			ExtentTest childTest3=extent.startTest("Fill Customer Info");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest5=extent.startTest("Add Phone and Internet Products");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest6=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			//order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);

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

			ExtentTest childTest10=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest10);
			order=createChildTest(childTest10, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest11=extent.startTest("Wait for Effective date Mark Finish task");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			com.verifySVGDiagram("");
			order.navigateToOrderPage();
			
			ExtentTest childTest12=extent.startTest("Wait for Provisioning start Mark Finished Task");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			order=createChildTest(childTest12, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest13=extent.startTest("Send same serial number for Converged Hardware");
			test.appendChild(childTest13);
			com=createChildTest(childTest13, extent, com);
			som=createChildTest(childTest13, extent, som);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();
			som.navigateToSOMOrder();
			com.navigateToCOMOrder();
		
			ExtentTest childTest14=extent.startTest("Navigate to Stub to Pass Internet & Converged Hardware");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			order.navigateToOeStubToPassServices("Internet");
			order.navigateToOeStubToPassServices("Converged Hardware");
			order.navigateToOrderPage();

			ExtentTest childTest15=extent.startTest("Send same serial number for Converged Hardware");
			test.appendChild(childTest15);
			com=createChildTest(childTest15, extent, com);
			som=createChildTest(childTest15, extent, som);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();
			som.navigateToSOMOrder();
			com.navigateToCOMOrder();

			ExtentTest childTest16=extent.startTest("Wait For Tech Confirm Mark Finish Task");
			test.appendChild(childTest16);
			com=createChildTest(childTest16, extent, com);
			order=createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("");
//			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest17=extent.startTest("CLEC Request before job run");
			test.appendChild(childTest17);
			com=createChildTest(childTest17, extent, com);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
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

			ExtentTest childTest20=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest20);
			com=createChildTest(childTest20, extent, com);
			som=createChildTest(childTest20, extent, som);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest21=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest21);
			repo=createChildTest(childTest21, extent, repo);
			order=createChildTest(childTest21,extent,order);
			order.navigateToOrderValidation(3, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC32", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC32", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest22=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest22);
			repo=createChildTest(childTest22, extent, repo);
			order=createChildTest(childTest22,extent,order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC32", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, internetProduct, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC32", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest23=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest23);
			orderHistory=createChildTest(childTest23, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving TechRetry_InternetFail_Phone_Success_SameSlNo");
		} catch (Exception e) {
			log.error("Error in TechRetry_InternetFail_Phone_Success_SameSlNo:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-1")
	@SuppressWarnings(value={ "rawtypes" })
	public void TechRetry_Pending_Install_WithXB6_BlueSky_SameSlNo(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Validate the Tech Retry functionlaity with Same Serial Number for a pending install account with XB6-Converged and Blue-sky Device");
			log.debug("Entering TechRetry_Pending_Install_WithXB6_BlueSky_SameSlNo");
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
			order=createChildTest(childTest0,extent,order);
			landing=createChildTest(childTest0, extent, landing);
			landing.openUrl();

			ExtentTest childTest1=extent.startTest("Fill Customer Info");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest3=extent.startTest("Add Phone, Voice mail");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();

			ExtentTest childTest4=extent.startTest("Add Internet Product");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5=extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithOutSrlNo(hardwareType);

			ExtentTest childTest6=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.addConvergedHardwareWithoutSlNbr();
			order.selectConvergedHardware(convergedHardware);
			//order.selectConvergedHardware("Internet");
			//order.deleteHardware("Switch Window");
			order.selectTodoListCheckBox();

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

			ExtentTest childTest10=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest10);
			order=createChildTest(childTest10, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest11=extent.startTest("Wait for Effective date Mark Finished");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);
			//com.switchPreviousTab();
			order.openCOMOrderPage("true", accntNum, "Yes");
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("Send serial number for Converged hardware");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			order=createChildTest(childTest12, extent, order);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();
			
			ExtentTest childTest13=extent.startTest("Send Serial number for TV Box");
			test.appendChild(childTest13);
			com=createChildTest(childTest13, extent, com);
			order=createChildTest(childTest13, extent, order);
			order.navigateToOrderValidation(11, "No", "No", "COM");
			String tvHardwareType=com.getTVHardwareType();
			if (tvHardwareType.contains("Gateway")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();
			
			ExtentTest childTest14=extent.startTest("Send Serial number for TV Portal");
			test.appendChild(childTest14);
			com=createChildTest(childTest14, extent, com);
			order=createChildTest(childTest14, extent, order);
			order.navigateToOrderValidation(12, "No", "No", "COM");
			String tvHardwareType1=com.getTVHardwareType();
			if (tvHardwareType1.contains("Gateway")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();
			
			ExtentTest childTest15=extent.startTest("Again Send serial number for Converged hardware");
			test.appendChild(childTest15);
			com=createChildTest(childTest15, extent, com);
			order=createChildTest(childTest15, extent, order);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Again Send Serial number for TV Box");
			test.appendChild(childTest16);
			com=createChildTest(childTest16, extent, com);
			order=createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(11, "No", "No", "COM");
			String tvHardwareType2=com.getTVHardwareType();
			if (tvHardwareType2.contains("Gateway")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();
			
			ExtentTest childTest17=extent.startTest("Again Send Serial number for TV Portal");
			test.appendChild(childTest17);
			com=createChildTest(childTest17, extent, com);
			order=createChildTest(childTest17, extent, order);
			order.navigateToOrderValidation(11, "No", "No", "COM");
			String tvHardwareType3=com.getTVHardwareType();
			if (tvHardwareType3.contains("Gateway")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();
			
			ExtentTest childTest18=extent.startTest("Wait For Tech Confirm Mark Finished");
			test.appendChild(childTest18);
			com=createChildTest(childTest18, extent, com);
			order=createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("");
//			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest19=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest19);
			com=createChildTest(childTest19, extent, com);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest20=extent.startTest("Navigate to E911 Job Run");
			test.appendChild(childTest20);
			com=createChildTest(childTest20, extent, com);
			order=createChildTest(childTest20, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest21=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest21);
			com=createChildTest(childTest21, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest22=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest22);
			com=createChildTest(childTest22, extent, com);
			som=createChildTest(childTest22, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest23=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest23);
			som=createChildTest(childTest23, extent, som);
			repo=createChildTest(childTest23, extent, repo);
			order=createChildTest(childTest23,extent,order);
			order.navigateToOrderValidation(3, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC33", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC33", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			order.navigateToOrderValidation(10, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum));
			boolean is4xgateway=repo.validateSOMIntegrationReportsSpcl(xls,"TC33", 3, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway,"Attributes are not validated successfully");
			if(!is4xgateway) 
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC33", 3, valuesToPassForValidation),"Attributes are not validated successfully");		
			}
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(11, "Yes", "No", "SOM");
			if(is4xgateway) 
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC33", 4, valuesToPassForValidation),"Attributes are not validated successfully");				
			}
			else
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC33", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();
		
			ExtentTest childTest24=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest24);
			order=createChildTest(childTest24, extent, order);
			repo=createChildTest(childTest24, extent, repo);
			order.navigateToOrderPage();
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC33", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(tvBoxSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC33", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC33", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, internetProduct, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC33", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest25=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest25);
			orderHistory=createChildTest(childTest25, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving TechRetry_Pending_Install_WithXB6_BlueSky_SameSlNo");
		} catch (Exception e) {
			log.error("Error in TechRetry_Pending_Install_WithXB6_BlueSky_SameSlNo:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-1")
	@SuppressWarnings(value={ "rawtypes" })
	public void TechRetry_diff_device_provis_failed_swap_conved_PhInt(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Tech retry with different device when provisioning failed during swap of a converged device (Ph+In)");
			log.debug("Entering TechRetry_diff_device_provis_failed_swap_conved_PhInt");
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

			ExtentTest childTest3=extent.startTest("Add Phone Product");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();

			ExtentTest childTest4=extent.startTest("Add Internet Product");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addServiceInternet(internetProduct);

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
			order.openCOMOrderPage("true", accntNum, "Yes");

			ExtentTest childTest10=extent.startTest("Navigate To E911 job Run");
			test.appendChild(childTest10);
			com=createChildTest(childTest10, extent, com);
			order=createChildTest(childTest10, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest11=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest12=extent.startTest("Navigate to Stub URL Fail");
			test.appendChild(childTest12);
			order=createChildTest(childTest12, extent, order);
			order.navigateToOeStubServerToFail("true","Hpsa");
			order.navigateToChangeToggleValueToFail("Converged Hardware");
			order.switchPreviousTab();

			ExtentTest childTest13=extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			order.navigateToCustOrderPage("false", accntNum);

			ExtentTest childTest14=extent.startTest("Swap Converged Hardware");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			order.swapConvergedHardware(swapConvergedHarwareNbr);
			//order.selectTodoListCheckBox();

			ExtentTest childTest15=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest15);
			order=createChildTest(childTest15, extent, order);
			order.selectInstallationOption("Self Connect No", "Yes");
			order.techAppoinmentYes("", "");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();
			order.selectDefaultInstallationCheckBox();

			ExtentTest childTest16=extent.startTest("Review and Finish");
			test.appendChild(childTest16);
			order=createChildTest(childTest16, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest17=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest17);
			order=createChildTest(childTest17, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");

			ExtentTest childTest18=extent.startTest("Wait for Effective date Mark Finished Task");
			test.appendChild(childTest18);
			com=createChildTest(childTest18, extent, com);
			order=createChildTest(childTest18, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest19=extent.startTest("Wait for provising task Mark Finish");
			test.appendChild(childTest19);
			com=createChildTest(childTest19, extent, com);
			order=createChildTest(childTest19, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();

			ExtentTest childTest20=extent.startTest("Verify COM and SOM Orders");
			test.appendChild(childTest20);
			com=createChildTest(childTest20, extent, com);
			som=createChildTest(childTest20, extent, som);
			som.navigateToSOMOrder();
			com.navigateToCOMOrder();
			order.switchNextTab();

			ExtentTest childTest22=extent.startTest("Navigate to Stub URL Success");
			test.appendChild(childTest22);
			order=createChildTest(childTest22, extent, order);
			order.navigateToOeStubServerToPass("false", "Converged Hardware");
			order.switchPreviousTab();
			som.navigateToSOMOrder();
			com.navigateToCOMOrder();

			ExtentTest childTest23=extent.startTest("Navigate To New Converged hw CFS Order send SLNO task");
			test.appendChild(childTest23);
			com=createChildTest(childTest23, extent, com);
			order=createChildTest(childTest23, extent, order);
			order.navigateToOrderValidation(16, "No", "No", "COM");
			com.sendSerialNbrTask(swapConvergedHarwareNbr);
			order.navigateToOrderPage();

			ExtentTest childTest24=extent.startTest("Send Serial number for Converged Hardware");
			test.appendChild(childTest24);
			com=createChildTest(childTest24, extent, com);
			order=createChildTest(childTest24, extent, order);
			order.navigateToOrderValidation(16, "No", "No", "COM");
			com.sendSerialNbrTask(swapConvergedHarwareNbr);
			order.navigateToOrderPage();
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.verifySVGDiagram("");
			order.navigateToOrderPage();

			ExtentTest childTest25=extent.startTest("Wait for Tech confirm Mark Finish task");
			test.appendChild(childTest25);
			com=createChildTest(childTest25, extent, com);
			order=createChildTest(childTest25, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
//			com.setMarkFinishedTask();
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
			order=createChildTest(childTest27,extent,order);
			order.navigateToOrderValidation(33, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC46", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(34, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC46", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(4, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, swapConvergedHarwareNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC46", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(8, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, swapConvergedHarwareNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC46", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest28=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest28);
			repo=createChildTest(childTest28, extent, repo);
			order=createChildTest(childTest28,extent,order);
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr, swapConvergedHarwareNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC46", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr, swapConvergedHarwareNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC46", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest29=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest29);
			orderHistory=createChildTest(childTest29, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving TechRetry_diff_device_provis_failed_swap_conved_PhInt");
		} catch (Exception e) {
			log.error("Error in TechRetry_diff_device_provis_failed_swap_conved_PhInt:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider="getTestData", description="Priority-1")
	@SuppressWarnings(value={"rawtypes"})
	public void TechRetry_diff_SlNos_Bluesky_gateway_Portal_provis_fails(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Tech Retry with different Serial Number when Blue-sky gateway + Portal provisioning fails");
			log.debug("Entering TechRetry_diff_SlNos_Bluesky_gateway_Portal_provis_fails");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			String internetHardware=data.get("Internet_hardware");
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
			TVBoxSrlNo=Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String tvBoxSerialNbr=TVBoxSrlNo;
			TVPortalSrlNo=Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String tvPortalSerialNbr=TVPortalSrlNo;
			TVBoxSrlNo=Utility.generateHardwareSerailNum(TVBoxSrlNo);
			String[] hardwareType={"ShawBlueSkyTVbox526","ShawBlueSkyTVportal416"};
			String[] serialNumber={tvBoxSerialNbr,tvPortalSerialNbr};
			String diffTvBoxSerialNbr=TVBoxSrlNo;
			TVPortalSrlNo=Utility.generateHardwareSerailNum(TVPortalSrlNo);
			String diffTvPortalSerialNbr=TVPortalSrlNo;
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest, extent, landing);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			
			ExtentTest childTest1=extent.startTest("Navigate to Stub URL Fail");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			order.navigateToChangeToggleValueToFail("CableTV");
			order.navigateToChangeToggleValueToFail("SetCableTV");
			landing.Login();
			
			ExtentTest childTest2=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest2);
			landing=createChildTest(childTest2, extent, landing);
			landing.openUrl();

			ExtentTest childTest3=extent.startTest("Fill Customer Info");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.customerInformation("Residential","Yes");
			order.addServicesAndFeacturesTab();

			ExtentTest childTest5=extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);
			
			ExtentTest childTest6=extent.startTest("Add Internet Product");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.addServiceInternet(internetProduct);

			ExtentTest childTest7=extent.startTest("Add Internet hardware");
			test.appendChild(childTest7);
			order=createChildTest(childTest7, extent, order);
			order.addInternetHardware(internetHardware, internetHardwareSerialNbr);
			//order.deleteConvergedHardware();

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
			order.openCOMOrderPage("true", accntNum, "");
			
			ExtentTest childTest12=extent.startTest("Wait for Effective date task Mark Finished");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			order=createChildTest(childTest12, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			com.verifySVGDiagram("");
			order.navigateToOrderPage();

			ExtentTest childTest13=extent.startTest("Wait for provising task Mark Finish");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			com=createChildTest(childTest13, extent, com);
			som=createChildTest(childTest13, extent, som);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			som.navigateToSOMOrder();
			com.navigateToCOMOrder();
			
			ExtentTest childTest14=extent.startTest("Navigate to Stub URL Success");
			test.appendChild(childTest14);
			order=createChildTest(childTest14, extent, order);
			order.navigateToOeStubToPassServices("CableTV");
			order.navigateToOeStubToPassServices("SetCableTV");
			order.navigateToOrderPage();
			
			ExtentTest childTest15=extent.startTest("Send Different Serial number for TV Box");
			test.appendChild(childTest15);
			com=createChildTest(childTest15, extent, com);
			order=createChildTest(childTest15, extent, order);
			order.navigateToOrderValidation(11, "No", "No", "COM");
			String tvHardwareType=com.getTVHardwareType();
			if (tvHardwareType.contains("Gateway")) {
				com.sendSerialNbrTask(diffTvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(diffTvPortalSerialNbr);
			}
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Send Different Serial number for TV Portal");
			test.appendChild(childTest16);
			com=createChildTest(childTest16, extent, com);
			order=createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(12, "No", "No", "COM");
			String tvHardwareType1=com.getTVHardwareType();
			if (tvHardwareType1.contains("Gateway")) {
				com.sendSerialNbrTask(diffTvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(diffTvPortalSerialNbr);
			}
			order.navigateToOrderPage();
			
			ExtentTest childTest17=extent.startTest("Mark Finished Wait For Tech Confirm");
			test.appendChild(childTest17);
			com=createChildTest(childTest17, extent, com);
			order=createChildTest(childTest17, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.verifySVGDiagram("");
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
			repo=createChildTest(childTest19, extent, repo);
			order=createChildTest(childTest19,extent,order);
			order.navigateToOrderValidation(38, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC47", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC47", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(11, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, "4xgateway"));
			boolean is4xgateway=repo.validateSOMIntegrationReportsSpcl(xls,"TC47", 3, valuesToPassForValidation);
			softAssert.assertTrue(is4xgateway,"Attributes are not validated successfully");
			if(!is4xgateway) 
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC47", 3, valuesToPassForValidation),"Attributes are not validated successfully");		
			}
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(13, "Yes", "No", "SOM");
			if(is4xgateway) 
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, tvPortalSerialNbr, "5xportal"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC47", 4, valuesToPassForValidation),"Attributes are not validated successfully");				
			}
			else
			{
				valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,tvBoxSerialNbr, "4xgateway"));
				softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC47", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			}
			order.navigateToOrderPage();

			ExtentTest childTest20=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest20);
			com=createChildTest(childTest20, extent, com);
			repo=createChildTest(childTest20, extent, repo);
			order.navigateToOrderPage();
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr, diffTvBoxSerialNbr, diffTvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC47", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, internetHardwareSerialNbr, diffTvBoxSerialNbr, diffTvPortalSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC47", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest21=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest21);
			orderHistory=createChildTest(childTest21, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving TechRetry_diff_SlNos_Bluesky_gateway_Portal_provis_fails");
		} catch (Exception e) {
			log.error("Error in TechRetry_diff_SlNos_Bluesky_gateway_Portal_provis_fails:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Iterations_TechRetry_swap_Convrgd_PhInt(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Iterations of tech retry with same device during swap of a converged device (Ph+In) when provisioning is successful and failed during swap");
			log.debug("Entering Iterations_TechRetry_swap_Convrgd_PhInt");
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
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String diffConvergedHwSlNbr=ConvgdSrlNo;
			String techAppointment=data.get("Tech_Appointment");
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			landing=createChildTest(childTest, extent, landing);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			landing.Login();
			
			ExtentTest childTest1=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest1);
			landing=createChildTest(childTest1, extent, landing);
			landing.openUrl();

			ExtentTest childTest2=extent.startTest("Fill Customer Info");
			test.appendChild(childTest2);
			order=createChildTest(childTest2, extent, order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest4=extent.startTest("Add Phone and Internet Product");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

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
			order.openCOMOrderPage("true", accntNum, "Yes");
			
			ExtentTest childTest20=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest20);
			com=createChildTest(childTest20, extent, com);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest21=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest21);
			com=createChildTest(childTest21, extent, com);
			order=createChildTest(childTest21, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();

			ExtentTest childTest22=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest22);
			com=createChildTest(childTest22, extent, com);
			com.verifyCLECRequestAfterJobRun();

			ExtentTest childTest23=extent.startTest("Navigate to Stub URL Fail");
			test.appendChild(childTest23);
			order=createChildTest(childTest23, extent, order);
			order.navigateToOeStubServerToFail("true","Hpsa");
			order.navigateToChangeToggleValueToFail("Phone");
			order.navigateToChangeToggleValueToFail("Internet");
			order.switchPreviousTab();

			ExtentTest childTest24=extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest24);
			order=createChildTest(childTest24, extent, order);
			order.navigateToCustOrderPage("false", accntNum);
			
			ExtentTest childTest25=extent.startTest("Swap Converged Hardware");
			test.appendChild(childTest25);
			order=createChildTest(childTest25, extent, order);
			order.swapConvergedHardware(swapConvergedHarwareNbr);

			ExtentTest childTest26=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest26);
			order=createChildTest(childTest26, extent, order);
			order.selectInstallationOption("Self Connect No", "Yes");
			order.techAppoinmentYes("Yes", "");
			order.selectingSalesRepresentativeCheckBox();
			order.selectDefaultInstallationCheckBox();
			order.addServicesAndFeacturesTab();
			order.selectTodo();

			ExtentTest childTest27=extent.startTest("Review and Finish");
			test.appendChild(childTest27);
			order=createChildTest(childTest27, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest28=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest28);
			order=createChildTest(childTest28, extent, order);
			order.openCOMOrderPage("false", accntNum, "Yes");
			
			ExtentTest childTest29=extent.startTest("Wait for Effective date Mark Finish task");
			test.appendChild(childTest29);
			com=createChildTest(childTest29, extent, com);
			order=createChildTest(childTest29, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
			com.setMarkFinishedTask();
			com.verifySVGDiagram("");
			order.navigateToOrderPage();
			
			ExtentTest childTest30=extent.startTest("Send SN1 for Converged hardware");
			test.appendChild(childTest30);
			com=createChildTest(childTest30, extent, com);
			som=createChildTest(childTest30, extent, som);
			order=createChildTest(childTest30, extent, order);
			order.navigateToOrderValidation(16, "No", "No", "COM");
			com.sendSerialNbrTask(diffConvergedHwSlNbr);
			order.navigateToOrderPage();
			som.navigateToSOMOrder();
			com.navigateToCOMOrder();
			
			ExtentTest childTest31=extent.startTest("Send swap slnbr SN2 for Converged hardware");
			test.appendChild(childTest31);
			com=createChildTest(childTest31, extent, com);
			order=createChildTest(childTest31, extent, order);
			order.navigateToOrderValidation(16, "No", "No", "COM");
			com.sendSerialNbrTask(diffConvergedHwSlNbr);
			order.navigateToOrderPage();
			
			ExtentTest childTest32=extent.startTest("Send SN3 for Converged hardware");
			test.appendChild(childTest32);
			com=createChildTest(childTest32, extent, com);
			order=createChildTest(childTest32, extent, order);
			order.navigateToOrderValidation(16, "No", "No", "COM");
			com.sendSerialNbrTask(diffConvergedHwSlNbr);
			order.navigateToOrderPage();
			
			ExtentTest childTest33=extent.startTest("Navigate to Stub URL Success");
			test.appendChild(childTest33);
			order=createChildTest(childTest33, extent, order);
			order.navigateToOeStubServerToPass("true", "Phone");
			order.navigateToOeStubServerToPass("false", "Internet");
			order.switchPreviousTab();
			
			ExtentTest childTest34=extent.startTest("Send serial number for Converged hardware");
			test.appendChild(childTest34);
			com=createChildTest(childTest34, extent, com);
			order=createChildTest(childTest34, extent, order);
			order.navigateToOrderValidation(16, "No", "No", "COM");
			com.sendSerialNbrTask(diffConvergedHwSlNbr);
			order.navigateToOrderPage();
			
			ExtentTest childTest35=extent.startTest("Wait For Tech Confirm Mark Finish Task");
			test.appendChild(childTest35);
			com=createChildTest(childTest35, extent, com);
			order=createChildTest(childTest35, extent, order);
			order.navigateToOrderValidation(25, "No", "No", "COM");
//			com.setMarkFinishedTask();
			order.navigateToOrderPage();
			
			ExtentTest childTest36=extent.startTest("Verify COM, SOM and CLEC Record counts");
			test.appendChild(childTest36);
			com=createChildTest(childTest36, extent, com);
			som=createChildTest(childTest36, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest37=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest37);
			repo=createChildTest(childTest37, extent, repo);
			order=createChildTest(childTest37,extent,order);
			order.navigateToOrderValidation(33, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum,  phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC71", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(34, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC71", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();	
			
			order.navigateToOrderValidation(4, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC71", 3, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(8, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC71", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest38=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest38);
			repo=createChildTest(childTest38, extent, repo);
			order=createChildTest(childTest38,extent,order);
			order.navigateToOrderPage();
			order.navigateToOrderValidation(25, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, diffConvergedHwSlNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC71", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(32, "Yes", "No", "COM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, diffConvergedHwSlNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC71", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest39=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest39);
			orderHistory=createChildTest(childTest39, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Iterations_TechRetry_swap_Convrgd_PhInt");
		} catch (Exception e) {
			log.error("Error in Iterations_TechRetry_swap_Convrgd_PhInt:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	@Test(dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Error_Format_HPESA_Fails_IntPhTV_NewCust_TechRetry_SameSlNbr(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Verify error format when HPESA fails for Internet, Phone and TV for a New customer and tech retry with same serial number");
			log.debug("Entering Error_Format_HPESA_Fails_IntPhTV_NewCust_TechRetry_SameSlNbr");
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
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));

			ExtentTest childTest=extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			
			ExtentTest childTest1=extent.startTest("Navigate to Stub URL To Fail");
			test.appendChild(childTest1);
			order=createChildTest(childTest1, extent, order);
			landing=createChildTest(childTest1, extent, landing);
			order.navigateToChangeToggleValueToFail("CableTV");
			order.navigateToChangeToggleValueToFail("Converged Hardware");
			landing.Login();	
			
			ExtentTest childTest2=extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest2);
			landing=createChildTest(childTest2, extent, landing);
			landing.openUrl();

			ExtentTest childTest3=extent.startTest("Fill Customer Info");
			test.appendChild(childTest3);
			order=createChildTest(childTest3, extent, order);
			order.customerInformation("Residential","Yes");

			ExtentTest childTest5=extent.startTest("Add Phone and Internet Products");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addServicesAndFeacturesTab();
			String phoneNbr=order.addServicePhone("Add Product", phoneProduct);
			order.addServiceInternet(internetProduct);
			
			ExtentTest childTest6=extent.startTest("Add TV With BlueSky Without srlNbrs");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithOutSrlNo(hardwareType);

			ExtentTest childTest7=extent.startTest("Add Converged Hardware Without SrlNbr");
			test.appendChild(childTest7);
			order=createChildTest(childTest7, extent, order);
			order.addConvergedHardwareWithoutSlNbr();
			order.selectConvergedHardware(convergedHardware);
			//order.selectConvergedHardware("Internet");
			//order.deleteHardware("Switch Window");
			order.selectTodoListCheckBox();
		
			ExtentTest childTest8=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest8);
			order=createChildTest(childTest8, extent, order);
			order.techAppointmentWithCurrentDate();
			
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
			
			ExtentTest childTest12=extent.startTest("Send serial number for Converged hardware");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			order=createChildTest(childTest12, extent, order);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();
			
			ExtentTest childTest13 = extent.startTest("Send Serial number for TV Box");
			test.appendChild(childTest13);
			com = createChildTest(childTest13, extent, com);
			order = createChildTest(childTest13, extent, order);
			order.navigateToOrderValidation(11, "No", "No", "COM");
			String tvHardwareType=com.getTVHardwareType();
			if (tvHardwareType.contains("NGV Gateway")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();
			
			ExtentTest childTest14=extent.startTest("Send Serial number for TV Portal");
			test.appendChild(childTest14);
			com=createChildTest(childTest14, extent, com);
			som=createChildTest(childTest14, extent, som);
			order=createChildTest(childTest14, extent, order);
			order.navigateToOrderValidation(12, "No", "No", "COM");
			String tvHardwareType1=com.getTVHardwareType();
			if (tvHardwareType1.contains("NGV Portal")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();
			som.navigateToSOMOrder();
			com.navigateToCOMOrder();
			
			ExtentTest childTest15=extent.startTest("Again Send SameSlNbr for Converged hardware");
			test.appendChild(childTest15);
			com=createChildTest(childTest15, extent, com);
			order=createChildTest(childTest15, extent, order);
			order.navigateToOrderValidation(15, "No", "No", "COM");
			com.sendSerialNbrTask(convergedHardWareSerialNbr);
			order.navigateToOrderPage();
			
			ExtentTest childTest16=extent.startTest("Again Send SameSlNbr for TV Box");
			test.appendChild(childTest16);
			com=createChildTest(childTest16, extent, com);
			order=createChildTest(childTest16, extent, order);
			order.navigateToOrderValidation(11, "No", "No", "COM");
			String tvHardwareType2=com.getTVHardwareType();
			if (tvHardwareType2.contains("NGV Gateway")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();
			
			ExtentTest childTest17=extent.startTest("Again Send SameSlNbr for TV Portal");
			test.appendChild(childTest17);
			com=createChildTest(childTest17, extent, com);
			som=createChildTest(childTest17, extent, som);
			order=createChildTest(childTest17, extent, order);
			order.navigateToOrderValidation(12, "No", "No", "COM");
			String tvHardwareType3=com.getTVHardwareType();
			if (tvHardwareType3.contains("NGV Portal")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();
			som.navigateToSOMOrder();
			com.navigateToCOMOrder();
			
			ExtentTest childTest18=extent.startTest("Verify ERROR message for Phone");
			test.appendChild(childTest18);
			repo=createChildTest(childTest18, extent, repo);
			order=createChildTest(childTest18,extent,order);
			order.navigateToOrderValidation(2, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC108", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest19=extent.startTest("Verify ERROR message for Internet");
			test.appendChild(childTest19);
			repo=createChildTest(childTest19, extent, repo);
			order=createChildTest(childTest19,extent,order);
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC108", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			/*
			 * ExtentTest childTest20=extent.startTest("Verify ERROR message for TV");
			 * test.appendChild(childTest20); som=createChildTest(childTest20, extent, som);
			 * repo=createChildTest(childTest20, extent, repo);
			 * order=createChildTest(childTest20,extent,order);
			 * order.navigateToOrderValidation(10, "Yes", "No", "SOM");
			 * valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr,
			 * tvBoxSerialNbr));
			 * softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC108", 3,
			 * valuesToPassForValidation),"Attributes are not validated successfully");
			 * order.navigateToOrderPage();
			 */
			
			ExtentTest childTest21=extent.startTest("Navigate to Stub URL To Success TV");
			test.appendChild(childTest21);
			order=createChildTest(childTest21, extent, order);
			order.navigateToOeStubToPassServices("CableTV");
			order.navigateToOrderPage();
						
			ExtentTest childTest22 = extent.startTest("Again Send SameSlNbr for TV Box");
			test.appendChild(childTest22);
			com = createChildTest(childTest22, extent, com);
			order = createChildTest(childTest22, extent, order);
			order.navigateToOrderValidation(11, "No", "No", "COM");
		
			String tvHardwareType4=com.getTVHardwareType();
			if (tvHardwareType4.contains("NGV Gateway")) {
				com.sendSerialNbrTask(tvBoxSerialNbr);
			} else {
				order.navigateToOrderPage();
				order.navigateToOrderValidation(12, "No", "No", "COM");
				com.sendSerialNbrTask(tvBoxSerialNbr);
			}
			order.navigateToOrderPage();
			som.navigateToSOMOrder();
			com.navigateToCOMOrder();
			
			ExtentTest childTest23=extent.startTest("Navigate to Stub URL Fail");
			test.appendChild(childTest23);
			order=createChildTest(childTest23, extent, order);
			order.navigateToOeStubServerToFail("true","Hpsa");
			order.navigateToChangeToggleValueToFail("CableTV");
			//order.switchPreviousTab();
			order.navigateToOrderPage();
			
			ExtentTest childTest24=extent.startTest("Again Send SameSlNbr for TV Portal");
			test.appendChild(childTest24);
			com=createChildTest(childTest24, extent, com);
			som=createChildTest(childTest24, extent, som);
			order=createChildTest(childTest24, extent, order);
			order.navigateToOrderValidation(11, "No", "No", "COM");
			String tvHardwareType5=com.getTVHardwareType();
			if (tvHardwareType5.contains("NGV Portal")) {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			} else {
				order.navigateToOrderPage();
				order.navigateToOrderValidation(12, "No", "No", "COM");
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();
			som.navigateToSOMOrder();
			com.navigateToCOMOrder();
	
			ExtentTest childTest25=extent.startTest("Again Send SameSlNbr for TV Portal");
			test.appendChild(childTest25);
			com=createChildTest(childTest25, extent, com);
			som=createChildTest(childTest25, extent, som);
			order=createChildTest(childTest25, extent, order);
			order.navigateToOrderValidation(11, "No", "No", "COM");
			String tvHardwareType6=com.getTVHardwareType();
			if (tvHardwareType6.contains("NGV Portal")) {
				com.sendSerialNbrTask(tvPortalSerialNbr);
			} else {
				order.navigateToOrderPage();
				order.navigateToOrderValidation(12, "No", "No", "COM");
				com.sendSerialNbrTask(tvPortalSerialNbr);
			}
			order.navigateToOrderPage();
			som.navigateToSOMOrder();
			com.navigateToCOMOrder();
	
			ExtentTest childTest26=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest26);
			com=createChildTest(childTest26, extent, com);
			som=createChildTest(childTest26, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			
			/*ExtentTest childTest27=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest27);
			order=createChildTest(childTest27,extent,order);
			repo=createChildTest(childTest27, extent, repo);
			order.navigateToOrderValidation(40, "Yes", "No", "SOM");
			valuesToPassForValidation=new ArrayList<>(Arrays.asList(accntNum, phoneNbr, tvPortalSerialNbr));
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls, "TC108", 4, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();*/
			
			ExtentTest childTest28=extent.startTest("Navigate to Order History Status");
			test.appendChild(childTest28);
			orderHistory=createChildTest(childTest28, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Error_Format_HPESA_Fails_IntPhTV_NewCust_TechRetry_SameSlNbr");
		} catch (Exception e) {
			log.error("Error in Error_Format_HPESA_Fails_IntPhTV_NewCust_TechRetry_SameSlNbr:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
	
	public static void main(String args[]) {
		InputStream log4j=TechRetryCases.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
		PropertyConfigurator.configure(log4j);
	}
}
