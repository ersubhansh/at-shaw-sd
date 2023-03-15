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

public class MailOutCases extends SeleniumTestUp  {

	LandingPage landing=null;
	OrderCreationPage order=null;
	COMOrdersPage com=null;
	SOMOrderPage som=null;
	OrderHistoryPage orderHistory=null;
	ValidateReport repo=null;
	SoapUIRestAPIpage soapRest=null;
	SoftAssert softAssert=new SoftAssert();
	ArrayList<String> valuesToPassForValidation=new ArrayList<String>();
	
	Logger log=Logger.getLogger(MailOutCases.class);
    
    @Test (dataProvider="getTestData", description="Priority-1")
	@SuppressWarnings(value={"rawtypes"})
	public void Activate_Standaldone_XB6_internet_via_direct_fullfilment(Hashtable<String, String> data)throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","Activate a standalone Xb6 internet device via direct fulfillment process");
			log.debug("Entering Activate_Standaldone_XB6_internet_via_direct_fullfilment");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			String convergedHardware=data.get("Converged_hardware");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
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

			ExtentTest childTest3 = extent.startTest("Add Internet Product");
			test.appendChild(childTest3);
			order = createChildTest(childTest3, extent, order);
			order.addServicesAndFeacturesTab();
			order.addServiceInternet(internetProduct);

			ExtentTest childTest4 = extent.startTest("Add Internet Converged Hardware");
			test.appendChild(childTest4);
			order = createChildTest(childTest4, extent, order);
			// order.addConvergedHardware("Converged150", convergedHardWareSerialNbr);
			order.addConvergedHardware1(convergedHardWareSerialNbr);
			order.selectConvergedHardware(convergedHardware);
			// order.deleteHardware("Switch Window");

			ExtentTest childTest6 = extent.startTest("Navigate To Tech Appoinment With directFulfilment");
			test.appendChild(childTest6);
			order = createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect Yes", "");
			order.techAppointmentNo("Mailout", "Service Location", "");
//			order.techAppointmentNo("Mailout", "Special shipping", "");

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

			ExtentTest childTest10=extent.startTest("Navigate To Complete Active Shipping Task");
			test.appendChild(childTest10);
			com=createChildTest(childTest10, extent, com);
			order=createChildTest(childTest10, extent, order);
			com.navigateToShippingTasks("", "");
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest12);
			com=createChildTest(childTest12, extent, com);
			som=createChildTest(childTest12, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest13=extent.startTest("Verify SOM Integration Report");
			test.appendChild(childTest13);
			repo=createChildTest(childTest13, extent, repo);
			order=createChildTest(childTest13, extent, order);
			order.navigateToOrderValidation(7, "Yes", "No", "SOM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList());
			softAssert.assertTrue(repo.validateSOMIntegrationReports(xls,"TC38", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest14=extent.startTest("Verify COM Integration Report");
			test.appendChild(childTest14);
			repo=createChildTest(childTest14, extent, repo);
			order=createChildTest(childTest14, extent, order);
			order.navigateToOrderValidation(1, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC38", 1, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();
			
			order.navigateToOrderValidation(17, "Yes", "No", "COM");
			valuesToPassForValidation = new ArrayList<>(Arrays.asList(accntNum, convergedHardWareSerialNbr));
			softAssert.assertTrue(repo.validateCOMIntegrationReports(xls,"TC38", 2, valuesToPassForValidation),"Attributes are not validated successfully");
			order.navigateToOrderPage();

			ExtentTest childTest15=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest15);
			orderHistory=createChildTest(childTest15, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Activate_Standaldone_XB6_internet_via_direct_fullfilment");
		} catch (Exception e) {
			log.error("Error in Activate_Standaldone_XB6_internet_via_direct_fullfilment:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}
    
    @Test (dataProvider="getTestData", description="Priority-2")
	@SuppressWarnings(value={"rawtypes"})
	public void Sim_Validations_For_New_Internet_Activation(Hashtable<String, String> data)throws Exception {
		try {
			test.log(LogStatus.INFO, "TestCase Name : ","SIM validations for a new Internet activation");
			log.debug("Entering Sim_Validations_For_New_Internet_Activation");
			if (data.get("Run").equals("No")) {
				throw new SkipException("Skipping the test as runmode is N");
			}
			String internetProduct=data.get("Internet_Product");
			String convergedHardware=data.get("Converged_hardware");
			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
			String convergedHardWareSerialNbr=ConvgdSrlNo;
			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
			
			ExtentTest childTest=extent.startTest("Prerequisite: checking all the stub toggeles");
			test.appendChild(childTest);
			landing=createChildTest(childTest, extent, landing);
			order=createChildTest(childTest, extent, order);
			order.navigateFirstToOeStubServer();
			order.openOEStubTochangeLocationCoaxial("false");
	
			ExtentTest childTest0=extent.startTest("Navigate to login and open OE page");
			test.appendChild(childTest0);
			landing=createChildTest(childTest0, extent, landing);
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
			
			ExtentTest childTest4=extent.startTest("Select max SIMs for Internet");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
			order.addEmailsIPAddressSimCardForInternet("", "Sim Card");

			ExtentTest childTest5=extent.startTest("Add Converged hardware without srlNbr");
			test.appendChild(childTest5);
			order=createChildTest(childTest5, extent, order);
			order.addConvergedHardwareWithoutSlNbr();
			
			ExtentTest childTest6=extent.startTest("Navigate To Tech Appoinment With MailOut");
			test.appendChild(childTest6);
			order=createChildTest(childTest6, extent, order);
			order.selectInstallationOption("Self Connect Yes", "");
			order.techAppointmentNo("Mailout", "Special shipping", "");
			
			ExtentTest childTest7=extent.startTest("Select Method of confirmation");
			test.appendChild(childTest7);
			order=createChildTest(childTest7,extent,order);
			order.selectMethodOfConfirmation("Voice");
			order.enteringChangesAuthorizedBy();
			order.selectingSalesRepresentativeCheckBox();
		
			ExtentTest childTest8=extent.startTest("Review and Finish");
			test.appendChild(childTest8);
			order=createChildTest(childTest8, extent, order);
			order.reviewAndFinishOrder();

			ExtentTest childTest9=extent.startTest("Navigate To COM & SOM Orders");
			test.appendChild(childTest9);
			order=createChildTest(childTest9, extent, order);
			String accntNum=order.getAccountNumber();
			order.openCOMOrderPage("true", accntNum, "");
			
			ExtentTest childTest10=extent.startTest("Validate the task details");
			test.appendChild(childTest10);
			order=createChildTest(childTest10, extent, order);
			order.navigateToOrderValidation(1, "No", "Yes", "COM");
		
			ExtentTest childTest11=extent.startTest("Navigate To Complete Active Shipping Task");
			test.appendChild(childTest11);
			com=createChildTest(childTest11, extent, com);
			order=createChildTest(childTest11, extent, order);
			com.navigateToShippingTasks("Yes", convergedHardWareSerialNbr);
			order.navigateToOrderPage();

			ExtentTest childTest12=extent.startTest("Confirm Order History Activation");
			test.appendChild(childTest12);
			orderHistory=createChildTest(childTest12, extent, orderHistory);
			order=createChildTest(childTest12, extent, order);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "Retail MailOut");
			orderHistory.navigateToOrderHistoryActivate();
			order.navigateToOrderPage();

			ExtentTest childTest13=extent.startTest("Verify COM and SOM Record counts");
			test.appendChild(childTest13);
			com=createChildTest(childTest13, extent, com);
			som=createChildTest(childTest13, extent, som);
			com.verifyRecordsCountInCOMOrder(comOrderCount);
			som.verifyRecordsCountInSOMOrder(somOrderCount);

			ExtentTest childTest14=extent.startTest("Navigate to Order History Page");
			test.appendChild(childTest14);
			orderHistory=createChildTest(childTest14, extent, orderHistory);
			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

			log.debug("Leaving Sim_Validations_For_New_Internet_Activation");
		} catch (Exception e) {
			log.error("Error in Sim_Validations_For_New_Internet_Activation:" + e.getMessage());
			test.log(LogStatus.FAIL, "Test Failed");
			Assert.fail();
		}
	}

    @Test (dataProvider="getTestData", description="Priority-2")
   	@SuppressWarnings(value={"rawtypes"})
   	public void Retail_Pickup_MailOut_UI_Changes(Hashtable<String, String> data)throws Exception {
   		try {
   			test.log(LogStatus.INFO, "TestCase Name : ","Validate \"Retail Pickup/Mailout\" UI changes");
   			log.debug("Entering Retail_Pickup_MailOut_UI_Changes");
   			if (data.get("Run").equals("No")) {
   				throw new SkipException("Skipping the test as runmode is N");
   			}
   			String internetProduct=data.get("Internet_Product");
   			ConvgdSrlNo=Utility.generateHardwareSerailNum(ConvgdSrlNo);
   			String convergedHardWareSerialNbr=ConvgdSrlNo;
   			XPODSrlNo=Utility.generateHardwareSerailNum(XPODSrlNo);
			String xpodValue1=XPODSrlNo;
			XPODSrlNo=Utility.generateHardwareSerailNum(XPODSrlNo);
			String xpodValue2=XPODSrlNo;
			XPODSrlNo=Utility.generateHardwareSerailNum(XPODSrlNo);
			String xpodValue3=XPODSrlNo;
			String xpod1PKValue=null;
   			int comOrderCount=Math.round(Float.valueOf(data.get("COM_Order_Count")));
   			int somOrderCount=Math.round(Float.valueOf(data.get("SOM_Order_Count")));
   			
   			ExtentTest childTest0=extent.startTest("Prerequisite: checking all the stub toggeles");
   			test.appendChild(childTest0);
   			landing=createChildTest(childTest0, extent, landing);
   			order=createChildTest(childTest0, extent, order);
   			order.navigateFirstToOeStubServer();
   			order.openOEStubTochangeLocationCoaxial("false");
   	
   			ExtentTest childTest1=extent.startTest("Navigate to login and open OE page");
   			test.appendChild(childTest1);
   			landing=createChildTest(childTest1, extent, landing);
   			landing.Login();
   			landing.openUrl();

   			ExtentTest childTest2=extent.startTest("Fill Customer Info");
   			test.appendChild(childTest2);
   			order=createChildTest(childTest2, extent, order);
   			order.customerInformation("Residential","Yes");
   			
   			ExtentTest childTest3=extent.startTest("Add Internet Product");
   			test.appendChild(childTest3);
   			order=createChildTest(childTest3, extent, order);
   			order.addServicesAndFeacturesTab();
   			order.addServiceInternet(internetProduct);
   			
   			ExtentTest childTest4=extent.startTest("Add Rental XPOD Package without Serial Nbrs");
			test.appendChild(childTest4);
			order=createChildTest(childTest4, extent, order);
//			order.addRentalXpodsWithoutSrlNbrs();
			order.addRentalXpods("Residential", xpodValue1, xpodValue2, xpodValue3, xpod1PKValue, "false");
			
   			ExtentTest childTest5=extent.startTest("Add Converged hardware without srlNbr");
   			test.appendChild(childTest5);
   			order=createChildTest(childTest5, extent, order);
   			order.addConvergedHardwareWithoutSlNbr();
   			
   			ExtentTest childTest6=extent.startTest("Navigate To Tech Appoinment With MailOut");
   			test.appendChild(childTest6);
   			order=createChildTest(childTest6, extent, order);
   			order.selectInstallationOption("Self Connect Yes", "");
   			order.techAppointmentNo("Mailout", "Service Location", "");
   			
   			ExtentTest childTest7=extent.startTest("Select Method of confirmation");
   			test.appendChild(childTest7);
   			order=createChildTest(childTest7,extent,order);
   			order.selectMethodOfConfirmation("Voice");
   			order.enteringChangesAuthorizedBy();
   		
   			ExtentTest childTest8=extent.startTest("Review and Finish");
   			test.appendChild(childTest8);
   			order=createChildTest(childTest8, extent, order);
   			order.reviewAndFinishOrder();

   			ExtentTest childTest9=extent.startTest("Navigate To COM & SOM Orders");
   			test.appendChild(childTest9);
   			order=createChildTest(childTest9, extent, order);
   			String accntNum=order.getAccountNumber();
   			order.openCOMOrderPage("true", accntNum, "");
   		
   			ExtentTest childTest11=extent.startTest("Navigate To Complete Active Shipping Tasks");
   			test.appendChild(childTest11);
   			com=createChildTest(childTest11, extent, com);
   			order=createChildTest(childTest11, extent, order);
   			com.navigateToActiveShippingMultipleTasks(xpodValue1, xpodValue2, xpodValue3, convergedHardWareSerialNbr);
   			order.navigateToOrderPage();

   			ExtentTest childTest12=extent.startTest("Confirm Order History Activation");
   			test.appendChild(childTest12);
   			orderHistory=createChildTest(childTest12, extent, orderHistory);
   			order=createChildTest(childTest12, extent, order);
   			orderHistory.navigateToOrderHistoryUrl(accntNum, "Retail MailOut");
   			order.navigateToOrderPage();

   			ExtentTest childTest13=extent.startTest("Verify COM and SOM Record counts");
   			test.appendChild(childTest13);
   			com=createChildTest(childTest13, extent, com);
   			som=createChildTest(childTest13, extent, som);
   			com.verifyRecordsCountInCOMOrder(comOrderCount);
   			som.verifyRecordsCountInSOMOrder(somOrderCount);

   			ExtentTest childTest14=extent.startTest("Navigate to Order History Page");
   			test.appendChild(childTest14);
   			orderHistory=createChildTest(childTest14, extent, orderHistory);
   			orderHistory.navigateToOrderHistoryUrl(accntNum, "");

   			log.debug("Leaving Retail_Pickup_MailOut_UI_Changes");
   		} catch (Exception e) {
   			log.error("Error in Retail_Pickup_MailOut_UI_Changes:" + e.getMessage());
   			test.log(LogStatus.FAIL, "Test Failed");
   			Assert.fail();
   		}
   	}
    
	public static void main(String args[]) {
		InputStream log4j=MailOutCases.class.getClassLoader().getResourceAsStream("resources/log4j.properties");
		PropertyConfigurator.configure(log4j);
	}
}
