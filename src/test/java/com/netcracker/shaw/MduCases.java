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

public class MduCases extends SeleniumTestUp {

	LandingPage landing = null;
	OrderCreationPage order = null;
	COMOrdersPage com = null;
	SOMOrderPage som = null;
	OrderHistoryPage orderHistory = null;
	ValidateReport repo = null;

	SoftAssert softAssert = new SoftAssert();
	ArrayList<String> valuesToPassForValidation = new ArrayList<String>();
	// public String sheetName="TestData";

	Logger log = Logger.getLogger(MduCases.class);

	/*
	 * @Test(dataProvider = "getTestData", description = "Priority-2")
	 * 
	 * @SuppressWarnings(value = { "rawtypes" }) public void
	 * BulkMaster_TanentCreation_MajoraccountsRole_DelayCrew(Hashtable<String,
	 * String> data) throws Exception { try { test.log(LogStatus.INFO,
	 * "TestCase Name : "
	 * ,"Bulkmaster-Tenant Creation with Majoraccounts Role_Delay Crew");
	 * log.debug("Entering BulkMaster_TanentCreation_MajoraccountsRole_DelayCrew");
	 * if (data.get("Run").equals("No")) { throw new
	 * SkipException("Skipping the test as runmode is N"); } String
	 * internetProduct=data.get("Internet_Product"); //String
	 * phoneProduct=data.get("Phone_Product"); //String
	 * convergedHardware=data.get("Converged_hardware");
	 * //ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo); //String
	 * convergedHardWareSerialNo=ConvgdSrlNo; String
	 * techAppointment=data.get("Tech_Appointment"); int
	 * comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count"))); int
	 * somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count"))); //int
	 * clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));
	 * 
	 * ExtentTest childTest=extent.startTest("Check Stub Status Before Login");
	 * test.appendChild(childTest); landing=createChildTest(childTest, extent,
	 * landing); order=createChildTest(childTest, extent, order);
	 * //order.navigateFirstToOeStubServer(); landing.MDULogin();
	 * 
	 * ExtentTest childTest0=extent.startTest("Navigate to login and open OE page");
	 * test.appendChild(childTest0); landing=createChildTest(childTest0, extent,
	 * landing); landing.openUrl();
	 * 
	 * ExtentTest childTest1=extent.startTest("Fill Customer Info");
	 * test.appendChild(childTest1); order=createChildTest(childTest1, extent,
	 * order); order.mduCustomerInformation("BulkMaster","Organization","", "Yes");
	 * 
	 * ExtentTest childTest3=extent.startTest("Add Phone and Internet");
	 * test.appendChild(childTest3); order=createChildTest(childTest3, extent,
	 * order); order.addServicesAndFeacturesTab(); //String
	 * phoneNbr=order.addServicePhone("Add Product", phoneProduct);
	 * //order.selectVoiceMail(); order.addServiceInternet(internetProduct);
	 * 
	 * ExtentTest childTest4=extent.startTest("Add Converged Hardware");
	 * test.appendChild(childTest4); order=createChildTest(childTest4, extent,
	 * order); //order.addConvergedHardware("Converged150",
	 * convergedHardWareSerialNo);
	 * //order.addConvergedHardware1(convergedHardWareSerialNo);
	 * //order.selectConvergedHardware(convergedHardware);
	 * order.deleteHardware("Switch Window"); //order.selectTodo();
	 * 
	 * ExtentTest childTest5=extent.startTest("Navigate To Tech Appointment Tab");
	 * test.appendChild(childTest5); order=createChildTest(childTest5, extent,
	 * order); order.selectInstallationOption("Self Connect No", techAppointment);
	 * order.enteringChangesAuthorizedBy();
	 * order.selectingSalesRepresentativeCheckBox();
	 * 
	 * ExtentTest childTest6=extent.startTest("Select Method of confirmation");
	 * test.appendChild(childTest6); order=createChildTest(childTest6,extent,order);
	 * order.selectMethodOfConfirmation("Voice");
	 * 
	 * ExtentTest childTest7=extent.startTest("Review and Finish");
	 * test.appendChild(childTest7); order=createChildTest(childTest7, extent,
	 * order); order.reviewAndFinishOrder();
	 * 
	 * ExtentTest childTest8=extent.startTest("Navigate To COM & SOM Orders");
	 * test.appendChild(childTest8); order=createChildTest(childTest8, extent,
	 * order); String accntNum=order.getAccountNumber();
	 * order.openCOMOrderPage("true", accntNum, "Yes");
	 * 
	 * ExtentTest childTest9=extent.startTest("CLEC Request Before Job Run");
	 * test.appendChild(childTest9); com=createChildTest(childTest9, extent, com);
	 * com.verifyCLECRequestBeforeJobRun();
	 * 
	 * ExtentTest childTest10=extent.startTest("Navigate To E911 Job Run");
	 * test.appendChild(childTest10); order=createChildTest(childTest10, extent,
	 * order); com=createChildTest(childTest10, extent, com);
	 * com.navigateToJobMonitorURL("E911"); order.navigateToOrderPage();
	 * 
	 * ExtentTest childTest11=extent.startTest("CLEC Request After Job Run");
	 * test.appendChild(childTest11); com=createChildTest(childTest11, extent, com);
	 * com.verifyCLECRequestAfterJobRun();
	 * 
	 * ExtentTest childTest12=extent.startTest("Verify COM and SOM Record counts");
	 * test.appendChild(childTest12); com=createChildTest(childTest12, extent, com);
	 * som=createChildTest(childTest12, extent, som);
	 * //com.verifyRecordsCountInCLECOrder(clecOrderCount);
	 * com.verifyRecordsCountInCOMOrder(comOrderCount);
	 * som.verifyRecordsCountInSOMOrder(somOrderCount);
	 * 
	 * ExtentTest childTest15=extent.startTest("Navigate to Order History Page");
	 * test.appendChild(childTest15); orderHistory=createChildTest(childTest15,
	 * extent, orderHistory); orderHistory.navigateToOrderHistoryUrl(accntNum, "");
	 * 
	 * log.debug("Entering BulkMaster_TanentCreation_MajoraccountsRole_DelayCrew");
	 * } catch (Exception e) { log.
	 * error("Error in Entering BulkMaster_TanentCreation_MajoraccountsRole_DelayCrew:"
	 * + e.getMessage()); test.log(LogStatus.FAIL, "Test Failed"); Assert.fail(); }
	 * }
	 */

	@Test(dataProvider = "getTestData", description = "Priority-2")
	@SuppressWarnings(value = { "rawtypes" })
	public void Validate_Tenant_Account_Auto_Migration_Master_Sponsored(Hashtable<String, String> data)
			throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ", "Validate_Tenant_Account_Auto_Migration_Master_Sponsored");
			log.debug("Entering Validate Tenant Account Auto Migration Master Sponsored");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}

			String disconnectReason = data.get("Disconnect_Reasons");
			String convergedHardware = data.get("Converged_hardware");
			ConvgdSrlNo = Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr = ConvgdSrlNo;
			String tvProduct = data.get("TV_Product");
			TVHDSrlNo = Utility.generateHardwareSerailNum(TVHDSrlNo);
			String tvHardwareDCX3510SlNbr = TVHDSrlNo;
			String[] hardwareType = { "DCX3510HDGuide" };
			String[] serialNumber = { tvHardwareDCX3510SlNbr };
			String techAppointment = data.get("Tech_Appointment");

			
			  ExtentTest childTest =
			  extent.startTest("Check Stub Status and LOGIN to Inventory");
			  test.appendChild(childTest); landing = createChildTest(childTest, extent,
			  landing); order = createChildTest(childTest, extent, order);
			  order.navigateFirstToOeStubServer(); //
			  order.openOEStubTochangeLocationCoaxial("false");
			  order.navigateToCDIchangeAccountType("All");
			  String cbsAccntNmb = order.CreateCBScustomer("Bulkmaster", ""); 
			  landing.Login();
			  
			  ExtentTest childTest0 = extent.startTest("Navigate To Open OE Page");
			  test.appendChild(childTest0); order = createChildTest(childTest0, extent,
			  order); // landing=createChildTest(childTest0, extent, landing); //
			  landing.openUrlWithLocationId2();
			  order.navigateToCBSaccountOpenUrlWithLocationId2(cbsAccntNmb);
			  order.VerifyCustomerDetails();
			  
			  // ExtentTest childTest1 = extent.startTest("Fill Customer Info"); 
			//  test.appendChild(childTest1); 
			  // order = createChildTest(childTest1, extent,  order); 
			  // order.MigrationInformation(cbsAccntNmb,"Calgary - CGY"); 
		//	  order.mduCustomerInformation("BulkMaster", "", "Mailout", "Yes");
			  
			  ExtentTest childTest2 = extent.startTest("Add Internet and TV Product");
			  test.appendChild(childTest2); order = createChildTest(childTest2, extent,
			  order); order.addServicesAndFeacturesTab();
			  order.addServiceBulkInternet("Bulk Fibre+ 25");
			  order.addBulkInternetProduct("Base Price - Bulk Fibre+ 25",
			  "Rental BlueCurve Gateway WiFi Modem (XB6) - 348");
			  order.addServiceBulkTV("Bulk Legacy Popular TV");
			  order.addBulkTVProduct("Base Price - Bulk Legacy Popular TV (Canada)",
			  "BlueCurve TV Player Wireless 4K Rental");
			  order.addPromotionsForBulkProduct(); order.addProductPrices();
			  
			  ExtentTest childTest3 = extent.startTest("Navigate To Tech Appointment Tab");
			  test.appendChild(childTest3); order = createChildTest(childTest3, extent,
			  order); order.selectInstallationOption("", techAppointment);
			  order.enteringChangesAuthorizedBy();
			  
			  ExtentTest childTest4 = extent.startTest("Review and Finish");
			  test.appendChild(childTest4); order = createChildTest(childTest4, extent,
			  order); order.reviewOrder(); order.clickFinishOrder();
			  
			  ExtentTest childTest5 =
			  extent.startTest("Navigate To COM, SOM, CLEC Orders");
			  test.appendChild(childTest5); order = createChildTest(childTest5, extent,
			  order); String accntNum = order.getAccountNumber();
			  order.openCOMOrderPage("true", accntNum, "Yes");
			  
			  ExtentTest childTest42 = extent.startTest("New customer mark finish task");
			  test.appendChild(childTest42); com = createChildTest(childTest42, extent,
			  com); order = createChildTest(childTest42, extent, order);
			  order.navigateToOrderValidation(1, "No", "No", "COM");
			  com.setMarkFinishedTask(); order.navigateToOrderPage();
			  
			  ExtentTest childTest6 = extent.startTest("Navigate to Order History Page");
			  test.appendChild(childTest6); orderHistory = createChildTest(childTest6,
			  extent, orderHistory); // String accntNum=order.getAccountNumber();
			  orderHistory.navigateToOrderHistoryUrlLocationId2(accntNum,
			  "Product Summary"); // order.CloseCBS(); // order.refreshPage();
			  
			  ExtentTest childTest7 = extent.startTest("Navigate To Open OE Page");
			  test.appendChild(childTest7); order = createChildTest(childTest7, extent,
			  order); // String accntNum=order.getAccountNumber();
			  order.navigateTomasterCustOrderForTenantAccountlocId2(accntNum); //
			  order.warningokToClose();
			  
			  ExtentTest childTest8 = extent.startTest("Fill Customer Info");
			  test.appendChild(childTest8); order = createChildTest(childTest8, extent,
			  order); order.customerInformation("", "Yes");
			  
			  ExtentTest childTest10 = extent.startTest("Add Converged Hardware");
			  test.appendChild(childTest10); order = createChildTest(childTest10, extent,
			  order); order.addServicesAndFeacturesTab();
			  order.addConvergedHardware1(convergedHardWareSerialNbr);
			  
			  ExtentTest childTest9 = extent.startTest("Add TV Hardware");
			  test.appendChild(childTest9); order = createChildTest(childTest9, extent,
			  order); order.deleteTVHardware(); order.addTVHardwareWithSrlNo(hardwareType,
			  serialNumber);
			  
			  ExtentTest childTest11 =
			  extent.startTest("Navigate To Tech Appointment Tab");
			  test.appendChild(childTest11); order = createChildTest(childTest11, extent,
			  order); order.selectInstallationOption("Self Connect No", "No");
			  order.enteringChangesAuthorizedBy(); // order.addServicesAndFeacturesTab();
			  // order.selectTodoListCheckBox();
			  
			  ExtentTest childTest12 = extent.startTest("Select Method of confirmation");
			  test.appendChild(childTest12); order = createChildTest(childTest12, extent,
			  order); order.selectMethodOfConfirmation("Voice");
			  
			  ExtentTest childTest13 = extent.startTest("Entering Changes authorized by");
			  test.appendChild(childTest13); order = createChildTest(childTest13, extent,
			  order); order.enteringChangesAuthorizedBy(); //
			  order.selectingSalesRepresentativeCheckBox();
			  order.addServicesAndFeacturesTab();
			  
			  ExtentTest childTest14 = extent.startTest("Review and Finish");
			  test.appendChild(childTest14); order = createChildTest(childTest14, extent,
			  order); order.reviewAndFinishOrder();
			  
			  ExtentTest childTest41 =
			  extent.startTest("Navigate To COM, SOM, CLEC Orders");
			  test.appendChild(childTest41); order = createChildTest(childTest41, extent,
			  order); String accntNum1 = order.getAccountNumber();
			  order.openCOMOrderPage("true", accntNum1, "Yes");
			  
			  ExtentTest childTest15 = extent.startTest("Navigate To Open OE Page");
			  test.appendChild(childTest15); order = createChildTest(childTest15, extent,
			  order); // String accntNum1=order.getAccountNumber();
			  order.navigateToTenantCustOrderForTenantAccountlocId2(accntNum1);
			  order.warningokToClose();
			  
			  // order.VerifyCustomerDetails();
			  
			  ExtentTest childTest16 =
			  extent.startTest("Disconnect  Internet and TV LOB's");
			  test.appendChild(childTest16); order = createChildTest(childTest16, extent,
			  order); order.disconnectLOBProducts("Internet");
			  order.disconnectLOBProducts("TV"); order.deleteConvergedHardware(); //
			  order.selectTodo(); // order.selectPortOut();
			  
			  ExtentTest childTest17 = extent.startTest("Disconnect appointment date");
			  test.appendChild(childTest17); order = createChildTest(childTest17, extent,
			  order); order.disconnectAppointmentDate();
			  
			  ExtentTest childTest18 = extent.startTest("Select Disconnect Reason");
			  test.appendChild(childTest18); order = createChildTest(childTest18, extent,
			  order); order.selectDisconnectControllabeReasons("Can No Longer Afford");
			  
			  ExtentTest childTest19 = extent.startTest("Entering Changes authorized by");
			  test.appendChild(childTest19); order = createChildTest(childTest19, extent,
			  order); // order.selectInstallationOption("", "");
			  order.enteringChangesAuthorizedBy();
			  order.selectingSalesRepresentativeCheckBox();
			  order.selectBillMailForDisconnect();
			  
			  ExtentTest childTest20 = extent.startTest("Review and Finish");
			  test.appendChild(childTest20); order = createChildTest(childTest20, extent,
			  order); order.reviewAndFinishOrder(); // clickFinishOrder();
			  
			  ExtentTest childTest21 = extent.startTest("Navigate to Order History Page");
			  test.appendChild(childTest21); orderHistory = createChildTest(childTest21,
			  extent, orderHistory); // String accntNum2=order.getAccountNumber();
			  orderHistory.navigateToOrderHistoryUrlLocationId2(accntNum1,
			  "Product Summary");
			  
			  ExtentTest childTest22 = extent.startTest("Navigate To COM & SOM Orders");
			  test.appendChild(childTest22); order = createChildTest(childTest22, extent,
			  order); order.openCOMOrderPage("false", accntNum1, "Yes");
			  
			  // ExtentTest childTest23 = extent.startTest("CLEC Request Before Job Run");
			  // test.appendChild(childTest23); // com = createChildTest(childTest23,
			 // extent, com); // com.verifyCLECRequestBeforeJobRun();
			  
			 /* // ExtentTest childTest24 = extent.startTest("Navigate To E911 Job Run"); //
			  test.appendChild(childTest24); // com = createChildTest(childTest24, extent,
			  com); // order = createChildTest(childTest24, extent, order); //
			  com.navigateToJobMonitorURL("E911"); // order.navigateToOrderPage(); // //
			  ExtentTest childTest25 = extent.startTest("CLEC Request After Job Run"); //
			  test.appendChild(childTest25); // com = createChildTest(childTest25, extent,
			  com); // com.verifyCLECRequestAfterJobRun(); //
			  order.openCOMOrderPage("true", accntNum2, "Yes");
			  */
			  ExtentTest childTest26 =
			  extent.startTest("Disconnect customer mark finish task");
			  test.appendChild(childTest26); com = createChildTest(childTest26, extent,
			  com); order = createChildTest(childTest26, extent, order);
			  order.navigateToOrderValidation(35, "No", "No", "COM");
			  com.setMarkFinishedTask(); order.navigateToOrderPage();
			  
			  ExtentTest childTest48 =
			  extent.startTest("Disconnect customer mark finish task");
			  test.appendChild(childTest48); com = createChildTest(childTest48, extent,
			  com); order = createChildTest(childTest48, extent, order);
			  order.navigateToOrderValidation(36, "No", "No", "COM");
			  com.setMarkFinishedTask(); order.navigateToOrderPage();
				/*
				 * ExtentTest childTest49 =
				 * extent.startTest("Disconnect customer mark finish task");
				 * test.appendChild(childTest49); com = createChildTest(childTest49, extent,
				 * com); order = createChildTest(childTest49, extent, order);
				 * order.navigateToOrderValidation(37, "No", "No", "COM");
				 * com.setMarkFinishedTask(); order.navigateToOrderPage();
				 */

		//String accntNum = "07132213278";
	//	String accntNum1 = "77132213281";
//			161449464905 &existingAccountId=161449464903&accountId=101449464907	
			ExtentTest childTest27 = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest27);
			landing = createChildTest(childTest27, extent, landing);
			order = createChildTest(childTest27, extent, order);
			order.navigateFirstToOeStubServer();
//			order.openOEStubTochangeLocationCoaxial("false");
			order.navigateToCDIchangeAccountType("All");
			String cbsAccntNmb1 = order.CreateCBScustomer("Tenant", accntNum1);
			landing.Login();

			ExtentTest childTest28 = extent.startTest("Navigate To Open OE Page");
			test.appendChild(childTest28);
			order = createChildTest(childTest28, extent, order);
			order.navigateTomasterAccountToExistingAccountToTenantLocationId2(accntNum, accntNum1, cbsAccntNmb1);
			order.warningokToClose();

			ExtentTest childTest29 = extent.startTest("Verify Internet Hardware");
			test.appendChild(childTest29);
			order = createChildTest(childTest29, extent, order);
			order.addServicesAndFeacturesTab();
			order.verifyInternetHardware();

			ExtentTest childTest30 = extent.startTest("Verify TV Hardware");
			test.appendChild(childTest30);
			order = createChildTest(childTest30, extent, order);
			order.verifyTVhardware();

			ExtentTest childTest44 = extent.startTest("Disconnect Phone LOB");
			test.appendChild(childTest44);
			order = createChildTest(childTest44, extent, order);
			order.deletePhoneLOB();

			ExtentTest childTest45 = extent.startTest("Remove Portout Number");
			test.appendChild(childTest45);
			order = createChildTest(childTest45, extent, order);
			order.selectPortOut();

			ExtentTest childTest31 = extent.startTest("Verify Customer Details");
			test.appendChild(childTest31);
			order = createChildTest(childTest31, extent, order);
			order.VerifyCustomerDetails();
			order.setUpBillingPreference();

			ExtentTest childTest32 = extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest32);
			order = createChildTest(childTest32, extent, order);
			order.selectInstallationOption("", "");
			order.enteringChangesAuthorizedBy();
//			order.addServicesAndFeacturesTab();
//			order.selectTodoListCheckBox();

			ExtentTest childTest33 = extent.startTest("Select Method of confirmation");
			test.appendChild(childTest33);
			order = createChildTest(childTest33, extent, order);
			order.selectMethodOfConfirmation("Voice");

			ExtentTest childTest34 = extent.startTest("Entering Changes authorized by");
			test.appendChild(childTest34);
			order = createChildTest(childTest34, extent, order);
			order.enteringChangesAuthorizedBy();
//			order.selectingSalesRepresentativeCheckBox();
			order.addServicesAndFeacturesTab();

			ExtentTest childTest35 = extent.startTest("Review and Finish");
			test.appendChild(childTest35);
			order = createChildTest(childTest35, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest36 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest36);
			order = createChildTest(childTest36, extent, order);
			String accntNum3 = order.getAccountNumber();
			order.openCOMOrderPage("false", accntNum3, "Yes");

			ExtentTest childTest46 = extent.startTest("Navigate to New Customer Service Information");
			test.appendChild(childTest46);
			com = createChildTest(childTest46, extent, com);
			order = createChildTest(childTest46, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.navigateToSuppressDeprovisioning();

			ExtentTest childTest37 = extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest37);
			orderHistory = createChildTest(childTest37, extent, orderHistory);
//			String accntNum1=order.getAccountNumber();
			orderHistory.navigateToOrderHistoryUrlLocationId2(accntNum3, "Product Summary");
			order.CloseCBS();
			order.refreshPage();

			ExtentTest childTest50 = extent.startTest("Check Stub Status and LOGIN to Inventory");
			test.appendChild(childTest50);
			landing = createChildTest(childTest50, extent, landing);
			order = createChildTest(childTest50, extent, order);
			order.navigateFirstToOeStubServer();
//			order.openOEStubTochangeLocationCoaxial("false");
			order.navigateToCDIchangeAccountType("Residental/Staff");

			ExtentTest childTest47 = extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest47);
			order = createChildTest(childTest47, extent, order);
//			String accntNum3=order.getAccountNumber();
			order.openCOMOrderPage("false", accntNum3, "Yes");

			ExtentTest childTest51 = extent.startTest("New customer Error mark finish task");
			test.appendChild(childTest51);
			com = createChildTest(childTest51, extent, com);
			order = createChildTest(childTest51, extent, order);
			order.navigateToOrderValidation(1, "No", "No", "COM");
			com.completeFourceRetryTask();
			order.navigateToOrderPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();
			order.refreshPage();

			log.debug("Entering Validate_Tenant_Account_Auto_Migration_Master_Sponsored");
		} catch (Exception e) {
			log.error("Error in Validate Tenant Account Auto Migration Master Sponsored:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	public static void main(String args[]) {
		InputStream log4j = MduCases.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
		PropertyConfigurator.configure(log4j);
	}

}
