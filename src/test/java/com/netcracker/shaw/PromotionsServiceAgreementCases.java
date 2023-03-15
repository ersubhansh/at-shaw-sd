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

public class PromotionsServiceAgreementCases extends SeleniumTestUp {

	LandingPage landing=null;
	OrderCreationPage order=null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	ValidateReport repo=null;
	OrderHistoryPage orderHistory=null;
	SoftAssert softAssert=new SoftAssert();
	ArrayList<String> valuesToPassForValidation=new ArrayList<String>();
	
	Logger log=Logger.getLogger(PromotionsServiceAgreementCases.class);
	
	@Test(dataProvider="getTestData", description="Priority-1")
	@SuppressWarnings(value={"rawtypes"})
	public void Activate_Int_conv_TVBoxPortal_2YVPInt_Tvagreement(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Activate Internet on converged device and TV on Blue-sky Box + Portal, add 2 YVP Internet + TV agreement and some TV Promotions");
			log.debug("Entering Activate_Int_conv_TVBoxPortal_2YVPInt_Tvagreement");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			String tvProduct=data.get("TV_Product");
			String phoneProduct=data.get("Phone_Product");
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
			int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));
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

			ExtentTest childTest4=extent.startTest("Add Phone and Internet");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServicePhone("Add Product", phoneProduct);
			order.selectVoiceMail();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest5=extent.startTest("Add Converged Hardware");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
//			order.selectConvergedHardware("Internet");
			//order.deleteHardware("Switch Window");

			ExtentTest childTest6=extent.startTest("Add TV With BlueSky");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);
			order.selectTodoListCheckBox();

			ExtentTest childTest7=extent.startTest("Add Promotions");
			test.appendChild(childTest7);
			order=createChildTest(childTest7, extent, order);
			order.addPromotionsServiceAgreement(phoneProduct, internetProduct, tvProduct, "");

			ExtentTest childTest8=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest8);
			order=createChildTest(childTest8, extent, order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			//order.mocaFilterSelecttion("");
			
			ExtentTest childTest9=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest9);
			order=createChildTest(childTest9,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest10=extent.startTest("Review and Finish");
			test.appendChild(childTest10);
			order=createChildTest(childTest10, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest11=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest11);
			order=createChildTest(childTest11, extent, order);
			com=createChildTest(childTest11, extent, com);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "Yes");
			
			ExtentTest childTest12=extent.startTest("CLEC Request Before Job Run");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			com.verifyCLECRequestBeforeJobRun();

			ExtentTest childTest17=extent.startTest("Navigate To E911 Job Run");
			test.appendChild(childTest17);
			com=createChildTest(childTest17, extent, com);
			order=createChildTest(childTest17, extent, order);
			com.navigateToJobMonitorURL("E911");
			order.navigateToOrderPage();
			
			ExtentTest childTest18=extent.startTest("CLEC Request After Job Run");
			test.appendChild(childTest18);
			com=createChildTest(childTest18, extent, com);
			com.verifyCLECRequestAfterJobRun();
			
			ExtentTest childTest19=extent.startTest("Verify COM, SOM and CLEC Record counts");
			test.appendChild(childTest19);
			com=createChildTest(childTest19, extent, com);
			som=createChildTest(childTest19, extent, som);
			com.verifyRecordsCountInCLECOrder(clecOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			
			ExtentTest childTest20=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest20);
			repo=createChildTest(childTest20, extent,repo);
			order=createChildTest(childTest20,extent,order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, tvBoxSerialNbr, tvPortalSerialNbr)); 
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC39", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			ExtentTest childTest21=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest21);
			orderHistory=createChildTest(childTest21, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Activate_Int_conv_TVBoxPortal_2YVPInt_Tvagreement");
		} catch (Exception e) {
			log.error("Error in Activate_Int_conv_TVBoxPortal_2YVPInt_Tvagreement:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

	@Test(dataProvider = "getTestData", description = "Priority-1")
	@SuppressWarnings(value = { "rawtypes" })
	public void Verify_ChangeVP_Agreement_1P2P_Generate_Agreement_Btn(Hashtable<String, String> data) throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Verify whether the change in VP agreement from 1P to 2P displays agreement button");
			log.debug("Entering Verify_ChangeVP_Agreement_1P2P_Generate_Agreement_Btn");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			String internetHardware=data.get("Internet_hardware");
			IntHitronSrlNo=Utility.generateHardwareSerailNum(IntHitronSrlNo);
			String internetHardwareSerialNbr=IntHitronSrlNo;
			String tvProduct=data.get("TV_Product");
			TVHDSrlNo=Utility.generateHardwareSerailNum(TVHDSrlNo); 
			String tvHardware3510HDSerialNbr=TVHDSrlNo;
			String[] hardwareType={"DCX3510HDGuide"};
			String[] serialNumber={tvHardware3510HDSerialNbr};
			String techAppointment=data.get( "Tech_Appointment" );
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
			//order.deleteConvergedHardware();
			
			ExtentTest childTest5=extent.startTest("Add Internet Promotions");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addPromotionsSingleIntSrvAgreement("Internet");
	
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
			
			ExtentTest childTest10=extent.startTest("Verify COM, SOM Initial Record Counts");
			test.appendChild(childTest10);
			com=createChildTest(childTest10,extent,com);
			som=createChildTest(childTest10,extent,som);
			com.verifyRecordsCountInCOMOrder(initialComCount);
			som.verifyRecordsCountInSOMOrder(initialSomCount);
			
			ExtentTest childTest11=extent.startTest("Navigate to Customer Order Page");
			test.appendChild(childTest11);
			order=createChildTest(childTest11,extent,order);
			order.navigateToCustOrderPage("true",accntNum);
			
			ExtentTest childTest12=extent.startTest("Add TV with 3510 HD Hardware");
			test.appendChild(childTest12);
			order=createChildTest(childTest12,extent,order);
			order.addServiceTV(tvProduct);
			order.addTVHardwareWithSrlNo(hardwareType,serialNumber);
			
			ExtentTest childTest13=extent.startTest("Adding TV Promotions");
			test.appendChild(childTest13);
			order=createChildTest(childTest13, extent, order);
			order.addPromotionsSingleIntSrvAgreement("Internet And TV");
			order.addTVPriceGuarenteePramotions(tvProduct);
			
			ExtentTest childTest14=extent.startTest("Navigate To Tech Appointment Tab");
			test.appendChild(childTest14);
			order=createChildTest(childTest14,extent,order);
			order.selectInstallationOption("Self Connect No", techAppointment);
			order.enteringChangesAuthorizedBy();
			
			ExtentTest childTest15=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest15);
			order=createChildTest(childTest15,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.selectingSalesRepresentativeCheckBox();

			ExtentTest childTest16=extent.startTest("Review and Finish");
			test.appendChild(childTest16);
			order=createChildTest(childTest16,extent,order);
			order.clickFinishOrder();
			order.selectTodo();
			order.reviewAndFinishOrder();

			ExtentTest childTest17=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest17);
			order=createChildTest(childTest17,extent,order);
			order.openCOMOrderPage("false", accntNum, "");
			
			ExtentTest childTest18=extent.startTest("Verify COM, SOM Record counts");
			test.appendChild(childTest18);
			com=createChildTest(childTest18, extent, com);
			som=createChildTest(childTest18, extent, som);
			som.verifyRecordsCountInSOMOrder(somOrderCount);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			
			ExtentTest childTest19=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest19);
			orderHistory=createChildTest(childTest19, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");
			
			log.debug("Leaving Verify_ChangeVP_Agreement_1P2P_Generate_Agreement_Btn");
		} catch (Exception e) {
			log.error("Error in Verify_ChangeVP_Agreement_1P2P_Generate_Agreement_Btn:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
		
		@Test(dataProvider="getTestData", description="Priority-1")
		@SuppressWarnings(value={"rawtypes"})
		public void Validate_2YVPInt_Tvagreement_disable_with_TCIS(Hashtable<String, String> data) throws Exception {
			try {
				test.log(LogStatus.INFO, "TestCase Name : ","promotion and coupons wrench should be disabled when TCIS is active on account");
				log.debug("Validate_2YVPInt_Tvagreement_disable_with_TCIS");
				if (data.get("Run").equals("No")) {
					throw new SkipException("Skipping the test as runmode is N");
				}
				String internetProduct=data.get("Internet_Product");
				String tvProduct=data.get("TV_Product");
				String phoneProduct=data.get("Phone_Product");
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
				int clecOrderCount=Math.round(Float.valueOf(data.get("CLEC_Order_Count")));
				int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));

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

				ExtentTest childTest4 = extent.startTest("Add Phone and Internet");
				test.appendChild(childTest4);
				order = createChildTest(childTest4, extent, order);
				order.addServicesAndFeacturesTab();
				order.addServicePhone("Add Product", phoneProduct);
				order.selectVoiceMail();
				order.addServiceInternet(internetProduct);

				ExtentTest childTest5 = extent.startTest("Add Converged Hardware");
				test.appendChild(childTest5);
				order = createChildTest(childTest5, extent, order);
				order.addConvergedHardware("Converged75", convergedHardWareSerialNbr);
				order.selectConvergedHardware(convergedHardware);

				// order.selectConvergedHardware("Internet");
				// order.deleteHardware("Switch Window");

				ExtentTest childTest6 = extent.startTest("Add TV With BlueSky");
				test.appendChild(childTest6);
				order = createChildTest(childTest6, extent, order);
				order.addServiceTV(tvProduct);
				order.addTVHardwareWithSrlNo(hardwareType, serialNumber);
				order.selectTodoListCheckBox();

				ExtentTest childTest7 = extent.startTest("Add Promotions");
				test.appendChild(childTest7);
				order = createChildTest(childTest7, extent, order);
				order.addPromotionsServiceAgreement(phoneProduct, internetProduct, tvProduct, "");

				ExtentTest childTest8 = extent.startTest("Navigate To Tech Appointment Tab");
				test.appendChild(childTest8);
				order = createChildTest(childTest8, extent, order);
				order.selectInstallationOption("Self Connect No", techAppointment);
				order.enteringChangesAuthorizedBy(); 
				// order.mocaFilterSelecttion("");

				ExtentTest childTest9 = extent.startTest("Select Method of confirmation");
				test.appendChild(childTest9);
				order = createChildTest(childTest9, extent, order);
				order.selectMethodOfConfirmation("Voice");
				order.selectingSalesRepresentativeCheckBox();

				ExtentTest childTest10 = extent.startTest("Review and Finish");
				test.appendChild(childTest10);
				order = createChildTest(childTest10, extent, order);
				order.reviewAndFinishOrder();

				ExtentTest childTest11 = extent.startTest("Navigate To COM & SOM Orders");
				test.appendChild(childTest11);
				order = createChildTest(childTest11, extent, order);
				com = createChildTest(childTest11, extent, com);
				String accntNum = order.getAccountNumber();
				order.openCOMOrderPage("true", accntNum, "Yes");

				ExtentTest childTest12 = extent.startTest("CLEC Request Before Job Run");
				test.appendChild(childTest12);
				com = createChildTest(childTest12, extent, com);
				com.verifyCLECRequestBeforeJobRun();

				ExtentTest childTest17 = extent.startTest("Navigate To E911 Job Run");
				test.appendChild(childTest17);
				com = createChildTest(childTest17, extent, com);
				order = createChildTest(childTest17, extent, order);
				com.navigateToJobMonitorURL("E911");
				order.navigateToOrderPage();

				ExtentTest childTest18 = extent.startTest("Navigate To Cust Order Page");
				test.appendChild(childTest18);
				order = createChildTest(childTest18, extent, order);
				order.navigateToCustOrderPage("false", accntNum);

				ExtentTest childTest19 = extent.startTest("Click On Operations");
				test.appendChild(childTest19);
				order = createChildTest(childTest19, extent, order);
				order.navigateToOperations("Current Date");

				ExtentTest childTest15 = extent.startTest("Temporary Suspend Phone Internet and TV");
				test.appendChild(childTest15);
				order = createChildTest(childTest15, extent, order);
				order.tempSuspendTriplePlay();

				ExtentTest childTest16 = extent.startTest("Enter Installation Fee");
				test.appendChild(childTest16);
				order = createChildTest(childTest16, extent, order);
				order.enterInstallationFee("1.00");
				order.navigatetoAppointmentTab();
				order.enteringChangesAuthorizedBy();
				order.selectMethodOfConfirmation("Voice");
				order.selectingSalesRepresentativeCheckBox();

				ExtentTest childTest21 = extent.startTest("Review and Finish");
				test.appendChild(childTest21);
				order = createChildTest(childTest21, extent, order);
				order.reviewAndFinishOrder();


				ExtentTest childTest22 = extent.startTest("Navigate To Cust Order Page");
				test.appendChild(childTest22);
				order = createChildTest(childTest22, extent, order);
				order.navigateToCustOrderPage("false", accntNum);
				order.promotionLinkValidation();
				

				log.debug("Validate_2YVPInt_Tvagreement_disable_with_TCIS");
			} catch (Exception e) {
				log.error("Validate_2YVPInt_Tvagreement_disable_with_TCIS:" + e.getMessage());
				test.log(LogStatus.FAIL, "Test Failed");
				Assert.fail();
			}
		}

	public static void main(String args[]) {
		InputStream log4j=PromotionsServiceAgreementCases.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
		PropertyConfigurator.configure(log4j);
	}
}
