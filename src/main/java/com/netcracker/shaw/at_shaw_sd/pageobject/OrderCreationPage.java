package com.netcracker.shaw.at_shaw_sd.pageobject;

import static com.netcracker.shaw.element.pageor.LandingPageElement.*;
import static com.netcracker.shaw.element.pageor.OrderCreationPageElement.*;
import static com.netcracker.shaw.element.pageor.COMOrdersPageElement.*;
import static com.netcracker.shaw.element.pageor.SOMOrderPageElement.*;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
//import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.IfFunc;
import org.apache.poi.util.SystemOutLogger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.ClickAction;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.remote.server.handler.ClickElement;
import org.openqa.selenium.remote.server.handler.SendKeys;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 *         Aug 10, 2018
 */

@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class OrderCreationPage<OrderCreationPageElement> extends BasePage {

	private static final TimeUnit SECONDS = null;
	public boolean isOrderCreated = false;
	public String phoneNbr = null;
	public String portPhoneNumber = null;
	public String distinctivePhoneNbr = null;
	public String emailAddress = null;
	public String secondaryPhoneNbr = null;
	public static String orderPageLink = null;
	public static String comorderPagelink = null;
	public static String somorderPagelink = null;
	public static String clecorderPagelink = null;
	public static List<String> phonenumList = new ArrayList<String>();

	Logger log = Logger.getLogger(OrderCreationPage.class);
	JavascriptExecutor jse = (JavascriptExecutor) driver;

	public OrderCreationPage(ExtentTest test) {
		super(test);
	}

	public OrderCreationPage(ExtentTest test, WebDriver driver) {
		super(test, driver);
	}

	public void setTest(ExtentTest test1) {
		test = test1;
	}

	public void setDriver(WebDriver driver1) {
		driver = driver1;
	}

	public void customerInformation(String accntType, String FirstLastName) {
		try {
			log.debug("Entering customerInformation");
			wait(1);
			click(CUSTOMER_AND_ID_TAB);
			log.debug("Navigating to Customer Information Tab");
			wait(1);
			if (accntType.equalsIgnoreCase("Residential")) {
				wait(1);
				selectFromList(ACCOUNT_TYPE, "Residential");
				wait(1);
				int counter = 1;
				while (!isElementPresent(ADD_SERVICES_AND_FEATURES_TAB) && counter <= 10) {
					wait(4);
					counter++;

				}
			}
			if (accntType.equalsIgnoreCase("Staff")) {
				wait(1);
				selectFromList(ACCOUNT_TYPE, "Staff");
				wait(2);
			}
			if (FirstLastName.equals("Yes")) {
				inputText1(FIRSTNAME, Utility.getValueFromPropertyFile("accountHolderFirstName"));
				log.debug("Entering First Name");
				wait(1);
				inputText1(LASTNAME, Utility.getValueFromPropertyFile("accountHolderLastName"));
				log.debug("Entering Last Name");
				wait(1);
			}
			wait(1);
			inputText1(CUST_EMAIL_ADDRESS, Utility.getValueFromPropertyFile("customerEmailAddress"));
			log.debug("Entering Customer Email Address");
			wait(4);
			WebElement e1 = getWebElement(CUST_PHONENUMBER);
			jse.executeScript("arguments[0].value='(987) 654-3210';", e1);
			log.debug("Entering Phone Number");
			wait(3);
			selectFromList(AUTHENCTICATION_TYPE, "PIN");
			log.debug("Selecting Authentication type");
			wait(3);
			inputText(PIN1, "500081");
			log.debug("Entering PIN Number");
			wait(3);
			click(BILLING_PREFERENCE_TAB);
			wait(6);
			click(CUSTOMER_AND_ID_TAB);
			wait(5);
			click(VALIDATE_PHNBR_BTN);
			wait(2);
			inputText(PIN1, "500081");
			wait(2);
			click(VALIDATE_EMAIL_BTN);
			wait(2);
			test.log(LogStatus.PASS, "Fill Cust Info", "Filled Customer Info Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving customerInformation");
		} catch (Exception e) {
			log.error("Error in customerInformation:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void newCustomerInformation(String accntType, String FirstLastName) {
		try {
			log.debug("Entering newCustomerInformation");
			wait(1);
			click(CUSTOMER_AND_ID_TAB);
			log.debug("Navigating to Customer Information Tab");
			wait(1);
			if (accntType.equalsIgnoreCase("Residential")) {
				wait(1);
				selectFromList(ACCOUNT_TYPE, "Residential");
				wait(1);
				int counter = 1;
				while (!isElementPresent(ADD_SERVICES_AND_FEATURES_TAB) && counter <= 10) {
					wait(1);
					counter++;

				}
			}
			if (accntType.equalsIgnoreCase("Staff")) {
				wait(1);
				selectFromList(ACCOUNT_TYPE, "Staff");
				wait(2);
			}
			if (FirstLastName.equals("Yes")) {
				inputText1(FIRSTNAME, Utility.getValueFromPropertyFile("customerFirstName"));
				log.debug("Entering First Name");
				wait(2);
				inputText1(LASTNAME, Utility.getValueFromPropertyFile("customerLastName"));
				log.debug("Entering Last Name");
				wait(1);
			}
			wait(1);
			inputText1(CUST_EMAIL_ADDRESS, Utility.getValueFromPropertyFile("customerEmailAddress"));
			log.debug("Entering Customer Email Address");
			wait(1);
			WebElement e1 = getWebElement(CUST_PHONENUMBER);
			jse.executeScript("arguments[0].value='(987) 654-3210';", e1);
			log.debug("Entering Phone Number");
			wait(1);
			selectFromList(AUTHENCTICATION_TYPE, "PIN");
			log.debug("Selecting Authentication type");
			wait(5);
			inputText(PIN1, "500081");
			log.debug("Entering PIN Number");
			wait(1);
			click(BILLING_PREFERENCE_TAB);
			wait(7);
			click(CUSTOMER_AND_ID_TAB);
			wait(4);
			click(VALIDATE_PHNBR_BTN);
			wait(2);
			inputText(PIN1, "500081");
			wait(1);
			click(VALIDATE_EMAIL_BTN);
			wait(1);
			test.log(LogStatus.PASS, "Fill Cust Info", "Filled New Customer Info Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving newCustomerInformation");
		} catch (Exception e) {
			log.error("Error in newCustomerInformation:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void customerInformationUpdate() {
		try {
			log.debug("Entering updateCustomerDetails");
			click(CUSTOMER_AND_ID_TAB);
			wait(2);
			WebElement e1 = getWebElement(CUST_PHONENUMBER);
			jse.executeScript("arguments[0].value='(845) 555-7836';", e1);
			log.debug("Entering Phone Number");
			wait(1);
			click(CUST_PHONENUMBER);
			wait(1);
			click(VALIDATE_PHNBR_BTN);
			wait(2);
			inputText(FIRSTNAME, "Guru");
			log.debug("Entering First Name To Update");
			wait(1);
			inputText(LASTNAME, "Prasad");
			log.debug("Entering Last Name");
			wait(1);
			inputText(CUST_EMAIL_ADDRESS, "guruprasad@gmail.com");
			log.debug("Entering Email Address");
			wait(1);
			click(VALIDATE_EMAIL_BTN);
			wait(1);

			selectFromList(AUTHENCTICATION_TYPE, "PIN");
			log.debug("Selecting Authentication type");
			wait(5);
			inputText(PIN1, "500081");
			log.debug("Entering PIN Number");
			wait(1);
			click(VALIDATE_PHNBR_BTN);
			wait(1);
			click(VALIDATE_EMAIL_BTN);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving updateCustomerDetails");
		} catch (Exception e) {
			log.error("Error in updateCustomerDetails:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void mduCustomerInformation(String accntType, String accntHolder, String installOption,
			String FirstLastName) {
		try {
			log.debug("Entering customerInformation");
			wait(1);
			if (isDisplayed(CUSTOMER_AND_ID_TAB)) {
				click(CUSTOMER_AND_ID_TAB);
			}
			// click(CUSTOMER_AND_ID_TAB);
			log.debug("Navigating to Customer Information Tab");
			wait(5);
			if (accntType.equalsIgnoreCase("Residential")) {
				wait(2);
				selectFromList(ACCOUNT_TYPE, "Residential");
				wait(2);
				int counter = 1;
				while (!isElementPresent(ADD_SERVICES_AND_FEATURES_TAB) && counter <= 10) {
					wait(2);
					counter++;

				}
			}
			if (accntType.equalsIgnoreCase("Staff")) {
				wait(1);
				selectFromList(ACCOUNT_TYPE, "Staff");
				wait(2);
			}

			if (accntType.equalsIgnoreCase("BulkMaster")) {
				wait(1);
				selectFromList(ACCOUNT_TYPE, "BulkMaster");
				wait(2);
			}

			if (accntType.equalsIgnoreCase("Tenant")) {
				wait(1);
				selectFromList(ACCOUNT_TYPE, "Tenant");
				wait(2);
			}

			if (FirstLastName.equals("Yes")) {
				inputText1(FIRSTNAME, Utility.getValueFromPropertyFile("accountHolderFirstName"));
				log.debug("Entering First Name");
				wait(3);
				inputText1(LASTNAME, Utility.getValueFromPropertyFile("accountHolderLastName"));
				log.debug("Entering Last Name");
				wait(3);
			}
			wait(1);
			inputText1(CUST_EMAIL_ADDRESS, Utility.getValueFromPropertyFile("customerEmailAddress"));
			log.debug("Entering Customer Email Address");
			wait(2);
			WebElement e1 = getWebElement(CUST_PHONENUMBER);
			jse.executeScript("arguments[0].value='(654) 877-3210';", e1);
			log.debug("Entering Phone Number");
			wait(3);
			selectFromList(AUTHENCTICATION_TYPE, "PIN");
			log.debug("Selecting Authentication type");
			wait(5);

			inputText(PIN1, "500081");
			log.debug("Entering PIN Number");
			wait(3);
			click(BILLING_PREFERENCE_TAB);
			wait(8);
			click(CUSTOMER_AND_ID_TAB);
			wait(4);
			click(VALIDATE_PHNBR_BTN);
			wait(2);
			click(VALIDATE_EMAIL_BTN);
			wait(1);
			inputText(PIN1, "500081");
			wait(1);
			// switchPreviousTab();
			if (accntHolder.equalsIgnoreCase("Organization")) {
				wait(1);
				click(ACCOUNT_HOLDER);
				wait(2);
				inputText1(BUSINESS_NAME, "IT Company");
				wait(1);
				inputText1(BUSINESS_FIRST_NAME, "Andrew");
				wait(1);
				inputText1(BUSINESS_LASTNAME, "Gomes");
				wait(1);
				inputText1(PERSONAL_ID_PIN, "12351");
				wait(1);
				WebElement e2 = getWebElement(CUST_MOBILE);
				jse.executeScript("arguments[0].value='(987) 654-3210';", e2);
				log.debug("Entering Phone Number");
				wait(1);
				inputText1(CUST_EMAIL, "test@test.com");
				wait(1);
			}
			if (installOption.equalsIgnoreCase("Delay Crew")) {
				wait(1);
				selectFromList(INSTALL_OPTION, "Delay Crew");
				wait(2);
			} else {
				selectFromList(INSTALL_OPTION, "Mailout");
				wait(2);
			}
			test.log(LogStatus.PASS, "Fill Cust Info", "Filled Customer Info Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving customerInformation");
		} catch (Exception e) {
			log.error("Error in customerInformation:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addProductCategory() {
		try {
			log.debug("Entering addProductCategory");
			wait(2);
			click(TENANT_PRODUCT_CATEGORY_LIST);
			wait(1);
			if (isElementPresent(TENANT_DROPDOWN)) {
				wait(1);
				click(TENANT_DROPDOWN);
			}
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addProductCategory");
		} catch (Exception e) {
			log.error("Error in addProductCategory:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addOnDemandFeatures() {
		try {
			log.debug("Entering addOnDemandFeatures");
			wait(2);
			click(APP_MUS_OPTION);
			wait(2);
			if (isElementPresent(APP_MUS_OPTION)) {
				wait(2);
				click(APP_MUS_OPTION);
				wait(4);
				click(APP_MUS_OPTION);

			}
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addOnDemandFeatures");
		} catch (Exception e) {
			log.error("Error in addOnDemandFeatures:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void AddcustomerEmailAddress() {
		try {
			log.debug("Entering AddcustomerEmailAddress");
			wait(2);
			click(CUSTOMER_AND_ID_TAB);
			wait(3);
			inputText(CUST_EMAIL_ADDRESS, Utility.getValueFromPropertyFile("customerEmailAddress"));
			wait(1);
			wait(4);
			click(VALIDATE_EMAIL_BTN);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving AddcustomerEmailAddress");
		} catch (Exception e) {
			log.error("Error in AddcustomerEmailAddress:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void accountType(String accntType) {
		try {
			log.debug("Entering accountType");
			wait(1);
			click(CUSTOMER_AND_ID_TAB);
			wait(3);
			log.debug("Navigating to accountType in Customer information Tab");
			if (accntType.equals("Residential")) {
				click(ACCOUNT_TYPE_RESIDENTIAL);
				wait(8);
			}
			if (accntType.equals("Staff")) {
				click(ACCOUNT_TYPE_STAFF);
				wait(8);
			}
			wait(4);
			if (isElementPresent(CLICK_OK_BUTTON)) {
				click(CLICK_OK_BUTTON);
			}

			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		} catch (Exception e) {
			log.error("Error in accountType:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void selectBillType(String BillType) {
		try {
			log.debug("Entering selectBillType");
			wait(1);
			click(BILLING_PREFERENCE_TAB);
			wait(3);
			if (BillType.equals("Paper")) {
				click(BILL_TYPE_PAPER);
				wait(3);
			}
			test.log(LogStatus.PASS, "Select Bill Type", "Selected Billing Preference Type Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving selectBillType");
		} catch (Exception e) {
			log.error("Error in selectBillType:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void selectBillMailForDisconnect() {
		try {
			log.debug("Entering selectBillMailForDisconnect");
			click(BILLING_PREFERENCE_TAB);
			wait(1);
			click(BILLING_ADDRESS);
			wait(1);
			test.log(LogStatus.PASS, "Select Bill Address", "Selected Billig address Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving selectBillMailForDisconnect");
		} catch (Exception e) {
			log.error("Error in selectBillMailForDisconnect:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void setUpBillingPreference() {
		try {
			log.debug("Entering setUpBillingPreference");
			click(BILLING_PREFERENCE_TAB);
			wait(1);
			click(MORE);
			wait(1);
			test.log(LogStatus.PASS, "Select Bill Address", "Selected More option Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			if (isElementPresent(DUE_DATE_CHECK_BOX)) {
				click(DUE_DATE_CHECK_BOX);
			}

			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(CLICK_OK_BUTTON);
			wait(1);
			log.debug("Leaving setUpBillingPreferenc");
		} catch (Exception e) {
			log.error("Error in setUpBillingPreferenc:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addCustomerMailingAddress(String MailAddress, String searchCity) {
		try {
			log.debug("Entering addCustomerMailingAddress");
			wait(1);
			click(BILLING_PREFERENCE_TAB);
			wait(1);
			if (MailAddress.equals("Service Location")) {
				wait(1);
				click(SERVICE_LOCATION_RADIO_BTN);
				wait(1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			} else {
				wait(1);
				click(MAILLING_ADDRESS_RADIO_BTN);
				wait(2);
				inputText(MAILING_ADDRESS_APT_UNIT, "22");
				wait(1);
				inputText(HOUSE_NUMBER, "217");
				wait(1);
				inputText(SUFFIX_NUMBER, "1");
				wait(1);
				inputText(STREET_ADDRESS, "217 Birch Ave");
				wait(1);
				click(STREET_TYPE_LIST);
				wait(1);
				click(STREET_TYPE);
				wait(1);
				click(DIRECTIONS_LIST);
				wait(1);
				click(DIRECTIONS);
				wait(1);
				if (searchCity.equals("Yes")) {
					inputText(MAILING_ADDRESS_CITY, "100");
					wait(1);
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					wait(1);
					inputText(MAILING_ADDRESS_CITY, "MILE");
					wait(1);
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					wait(1);
					click(MAILING_ADDRESS_CITY_VALUE);
					wait(1);
				}
				if (searchCity.equals("No")) {
					click(MAILING_ADDRESS_CITY);
					wait(1);
					click(MAILING_ADDRESS_CITY_VALUE);
					wait(1);
				}
				inputText(POSTAL_CODE, "V0K 2E3");
				wait(1);
				click(SEARCH_BUTTON1);
				wait(2);
				click(SELECT_BUTTON);
				wait(4);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			log.debug("Leaving addCustomerMailingAddress");

		} catch (Exception e) {
			log.error("Error in addCustomerMailingAddress:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void clickBillingPreferencesMore() {
		try {
			log.debug("Entering clickBillingPreferencesMore");
			wait(2);
			click(BILLING_PREFERENCES_MORE);
			wait(2);
			switchToChildAlert();
			wait(4);
			click(ADD_TEX_EXEMPTION_BTN);
			wait(5);
			click(GST_RADIO_BTN);
			wait(5);
			click(PST_RADIO_BTN);
			wait(2);
			click(HST_RADIO_BTN);
			wait(2);
			click(EXEMPTION_TYPE);
			wait(2);
			click(FIRST_NATIONS_ON_RESERVE);
			wait(2);
			click(TAX_JURISDITION_TYPE);
			wait(2);
			click(TAX_SELECT_CANADA);
			wait(2);
			inputText(TAX_REFERENCE_ID, "1465");
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(OK_BUTTON_TO_CLOSE);
			log.debug("Leaving clickBillingPreferencesMore");

		} catch (Exception e) {
			log.error("Error in clickBillingPreferencesMore:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addServicesAndFeacturesTab() {

		try {
			System.out.println("Entering in method");
			log.debug("Entering addServicesAndFeacturesTab");
			wait(5);
			if (isElementPresent(ADD_SERVICES_AND_FEATURES_TAB)) {
				scrollToTop();
			}
			click(ADD_SERVICES_AND_FEATURES_TAB);
			int counter = 1;
			while (!isElementPresent(PROMOTIONS_LINK) && counter <= 15) {
				wait(4);
				counter++;
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			log.debug("Leaving addServicesAndFeacturesTab");
		} catch (Exception e) {
			log.error("Error in selectProduct:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public String FindAccountNumber() {
		String acnt = null;
		try {
//			System.out.println("Entering in NavigateAccountNumber");
			log.debug("Entering FindAccountNumber");
			wait(1);

			if (isDisplayed(OE_ACCOUNT_NUMBER)) {
				acnt = getText(OE_ACCOUNT_NUMBER);

			}

			log.debug("Leaving FindAccountNumber");
		} catch (Exception e) {
			log.error("Error in FindAccountNumber:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return acnt;
	}

	public String addServicePhone(String SelectNumber, String phoneProd) {
		try {
			log.debug("Entering addServicePhone");
			wait(1);
			if (SelectNumber.equals("Add Product")) {
				wait(4);
				do {
					wait(3);
				} while (!isDisplayed(ADD_PHONE_PROD));
				wait(4);
				click(ADD_PHONE_PROD);
				wait(4);
				selectFromList(PHONE_PROD_LIST, phoneProd);
				wait(4);
			}
			wait(2);
			click(PHONE_WRENCH_BUTTON);
			wait(3);
			switchToChildAlert();
			wait(4);
			click(CHOOSENUMBER);
			wait(10);
			do {
				wait(5);
			} while (!isDisplayed(PHONENUMBER_FROM_LIST));
			wait(1);
			click(PHONENUMBER_FROM_LIST);
			phoneNbr = convertPhoneToString(getText(PHONENUMBER_FROM_LIST));
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(5);
			click(ASSIGN_BUTTON);
			wait(10);

			click(LISTINGTYPE_LIST);
			wait(5);

			click(NO_LISTING);
			wait(4);

			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(OK_BUTTON_TO_CLOSE);
			wait(5);
			test.log(LogStatus.PASS, "Add Phone Service", "Added Phone Service Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addServicePhone");
		} catch (Exception e) {
			log.error("Error in addServicePhone:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return phoneNbr;
	}

	public String addSecondaryPhone(String prod, String PhoneList) {
		try {
			log.debug("Entering addServicePhone");
			wait(2);
			click(SECONDARY_PHONE_PRODUCT);
			wait(4);
			if (PhoneList.equals("Second Dropdown")) {
				wait(2);
				selectFromList(SECOND_PHONE_PROD_LIST, prod);
				wait(4);
			}
			if (PhoneList.equals("Third Dropdown")) {
				selectFromList(SELECT_PHONE_PROD3, prod);
				wait(2);
			}
			click(SELECT_NUMBER);
			wait(2);
			switchToChildAlert();
			click(CHOOSENUMBER);
			wait(2);
			click(PHONENUMBER_FROM_LIST);
			secondaryPhoneNbr = convertPhoneToString(getText(PHONENUMBER_FROM_LIST));
			wait(2);
			click(ASSIGN_BUTTON);
			wait(2);
			click(LISTINGTYPE_LIST);
			wait(2);
			click(NO_LISTING);
			wait(2);
			click(OK_BUTTON_TO_CLOSE);
			wait(2);
			test.log(LogStatus.PASS, "Add Phone Service", "Added Phone Service Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addServicePhone");
		} catch (Exception e) {
			log.error("Error in addServicePhone:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return phoneNbr;
	}

	public String convertPhoneToString(String phoneNbr) {
		phoneNbr = phoneNbr.replaceAll("[^\\dA-Za-z ]", "").replaceAll("\\s+", "");
		return phoneNbr;
	}

	public void selectVoiceMail() {
		try {
			log.debug("Entering selectVoiceMail");
			wait(2);
			click(VOICEMAIL_BUTTON);
			wait(5);
			test.log(LogStatus.PASS, "Select Voice Mail", "Selected Voice Mail Successfully");
			log.debug("Leaving selectVoiceMail");
		} catch (Exception e) {
			log.error("Error in selectVoiceMail:" + e.getMessage());
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addPhoneHardware(String phoneSerialNo, String phoneHardware) {
		try {
			log.debug("Entering addPhoneHardware");
			wait(2);
			click(ADD_PHONE_PROD);
			wait(2);
			if (phoneHardware.equalsIgnoreCase("Hardware")) {
				selectFromList(PHONE_HARDWARE_DROPDOWN, phoneHardware);
				wait(2);
				click(PHONEHARDWARE);
				wait(2);
				switchToChildAlert();
				wait(1);
				if (isElementPresent(PH_SERIALNUMBERFIELD)) {
					inputText(PH_SERIALNUMBERFIELD, phoneSerialNo);
					wait(1);
					click(VALIDATE_BUTTON);
				}
				do {
					wait(2);
				} while (!isDisplayed(HARDWARE_OK_BUTTON));
				test.log(LogStatus.PASS, "Add Phone Hardware", "Added Phone Hardware Successfully");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				click(HARDWARE_OK_BUTTON);
				wait(2);
			}
			if (phoneHardware.equalsIgnoreCase("Home Phone Jack Install")) {
				selectFromList(PHONE_HARDWARE_DROPDOWN, phoneHardware);
				test.log(LogStatus.PASS, "Add Phone Jack Install", "Added Phone Jack Install Successfully");
			}
			if (phoneHardware.equalsIgnoreCase("LD Calling Plans")) {
				selectFromList(HARDWARE2, phoneHardware);
				test.log(LogStatus.PASS, "Add LD Calling Plans", "Added LD Calling Plans Successfully");
			}
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addPhoneHardware");
		} catch (Exception e) {
			log.error("Error in addPhoneHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void removePersonalPhoneHardware() {
		try {
			log.debug("Entering removePersonalPhoneHardware");
			wait(1);
			click(REMOVE_PERSONAL_PHONE_HARDWARE);
			wait(2);
			switchToChildAlert();
			wait(1);
			click(CONFIRM_PRODUCT_REMOVE);
			wait(2);
			test.log(LogStatus.PASS, "Remove Phone Hardware", "Removed Phone Hardware Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving removePersonalPhoneHardware");
		} catch (Exception e) {
			log.error("Error in removePersonalPhoneHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addDistinctiveRing(String IncrementBtn, String RingPattern) {
		try {
			log.debug("Entering addDistinctiveRing");
			wait(1);
			if (IncrementBtn.equals("One")) {
				click(DISTINCTIVE_RING_INCREMENT1);
				wait(4);
			}
			if (IncrementBtn.equals("Two")) {
				click(DISTINCTIVE_RING_INCREMENT2);
				wait(2);
			}
			click(DR_SELECT_NUMBER);
			wait(4);
			switchToChildAlert();
			click(CHOOSENUMBER);
			wait(4);
			click(PHONENUMBER_FROM_LIST);
			wait(4);
			click(ASSIGN_BUTTON);
			wait(4);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(LISTINGTYPE_LIST);
			wait(2);
			click(NO_LISTING);
			wait(2);
			click(FEATURES_TAB);
			wait(2);
			click(RING_PATTERN_DROP_DOWN);
			wait(2);
			if (RingPattern.equals("3 short rings")) {
				wait(1);
				click(TYPE_3_RING_SHORT);
				wait(1);
			}
			if (RingPattern.equals("2 short rings")) {
				wait(1);
				click(TYPE_2_RING_SHORT);
				wait(1);
			}
			test.log(LogStatus.PASS, "Add Distinctive Ring", "Added Distinctive Ring Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(OK_BUTTON_TO_CLOSE);
			log.debug("Leaving addDistinctiveRing");
		} catch (Exception e) {
			log.error("Error in addDistinctiveRing:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public String changeCustNbrPortedToNative(String NativeNbr) {
		try {
			log.debug("Entering changeCustNbrPortedToNative");
			click(PHONE_WRENCH_BUTTON);
			wait(2);
			switchToChildAlert();
			wait(1);
			click(CHANGE_CUST_NBR);
			wait(1);
			click(CHOOSENUMBER);
			wait(2);
			click(PHONENUMBER_FROM_LIST);
			phoneNbr = convertPhoneToString(getText(PHONENUMBER_FROM_LIST));
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(ASSIGN_BUTTON);
			wait(2);
			click(LISTINGTYPE_LIST);
			wait(2);
			click(NO_LISTING);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(OK_BUTTON_TO_CLOSE);
			wait(2);
			test.log(LogStatus.PASS, "Change Native Phone Nbr", "Changed Native Number Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addServicePhone");
		} catch (Exception e) {
			log.error("Error in changeCustNbrPortedToNative:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return phoneNbr;
	}

	public String addCustPhoneNbr() {
		try {
			log.debug("Entering changeCustNbrPortedToNative");
			click(PHONE_WRENCH_BUTTON);
			wait(2);
			switchToChildAlert();
			wait(1);
			click(CHOOSENUMBER);
			wait(2);
			click(PHONENUMBER_FROM_LIST);
			phoneNbr = convertPhoneToString(getText(PHONENUMBER_FROM_LIST));
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(ASSIGN_BUTTON);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(OK_BUTTON_TO_CLOSE);
			wait(2);
			test.log(LogStatus.PASS, "Change Native Phone Nbr", "Changed Native Number Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addServicePhone");
		} catch (Exception e) {
			log.error("Error in changeCustNbrPortedToNative:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return phoneNbr;
	}

	public String changeMobileNbrPortedToNative(String nativeNbr) {
		try {
			log.debug("Entering changeMobileNbrPortedToNative");
			wait(1);
			click(PHONE_NBR_WRENCH);
			wait(3);
			switchToChildAlert();
			click(CHANGE_PH_NBR_BTN);
			wait(2);
			click(CHOOSENUMBER);
			wait(2);
			click(PHONENUMBER_FROM_LIST);
			phoneNbr = convertPhoneToString(getText(PHONENUMBER_FROM_LIST));
			wait(2);
			click(ASSIGN_BUTTON);
			wait(2);
			click(LISTINGTYPE_LIST);
			wait(2);
			click(NO_LISTING);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(OK_BUTTON_TO_CLOSE);
			wait(2);
			log.debug("Leaving changeMobileNbrPortedToNative");

		} catch (Exception e) {
			log.error("Error in changeMobileNbrPortedToNative:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return nativeNbr;
	}

	public String portDistinctiveNumber() {
		try {
			log.debug("Entering portDistinctiveNumber");
			wait(2);
			click(DISTINCTIVE_RING_INCREMENT1);
			wait(2);
			click(DR_SELECT_NUMBER);
			wait(2);
			switchToChildAlert();
			click(PORT_NUMBER_RADIO_BUTTON);
			wait(2);
			WebElement e1 = getWebElement(PORTED_NUMBER);
			distinctivePhoneNbr = Utility.generatePortPhoneNbr();
			jse.executeScript("arguments[0].value='" + distinctivePhoneNbr + "';", e1);
			// jse.executeScript("arguments[0].value='(890) 567-1234';", e1);
			wait(2);
			click(PORT_NOW);
			wait(5);
			click(LANDLINE_RADIO_BUTTON);
			wait(2);
			inputText(PREVIOUS_PROVIDER_ACCOUNT_NUMBER, "9848098480");
			wait(2);
			click(RESELLER_DROP_DOWN);
			wait(2);
			click(RESELLER_DROP_DOWN);
			click(RESELLER_VALUE);
			wait(2);
			click(CUSTOMER_LIFELINE_YES);
			wait(2);
			click(SHAW_SERVICE_REQUESTED_NO);
			wait(2);
			click(OK_BUTTON_TO_CLOSE);
			wait(2);
			click(LISTINGTYPE_LIST1);
			wait(2);
			// click(LISTINGTYPE411ONLY);
			// wait(2);
			click(FEATURES_TAB);
			wait(2);
			click(RING_PATTERN_DROP_DOWN);
			wait(2);
			click(TYPE_3_RING_SHORT);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(OK_BUTTON_TO_CLOSE);
			wait(2);
			distinctivePhoneNbr = convertPhoneToString(getText(DISTINCTIVE_RING_NBR_FIELD));
			log.debug("Leaving portDistinctiveNumber");
		} catch (Exception e) {
			log.error("Error in portDistinctiveNumber:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return distinctivePhoneNbr;
	}

	public void addPhoneHardwareWithoutSlNos(String phoneHardware) {
		try {
			log.debug("Entering addPhoneHardwareWithoutSlNos");
			wait(2);
			click(ADD_PHONE_PROD);
			wait(2);
			if (phoneHardware.equalsIgnoreCase("Hardware")) {
				selectFromList(PHONE_HARDWARE_DROPDOWN, phoneHardware);
				wait(2);
				click(PHONEHARDWARE);
				wait(2);
				switchToChildAlert();
				wait(1);
				do {
					wait(1);
				} while (!isDisplayed(HARDWARE_OK_BUTTON));
				test.log(LogStatus.PASS, "Add Phone Hardware", "Added Phone Hardware Successfully");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				click(HARDWARE_OK_BUTTON);
				wait(2);
			}
			if (phoneHardware.equalsIgnoreCase("Home Phone Jack Install")) {
				selectFromList(PHONE_HARDWARE_DROPDOWN, phoneHardware);
			}
			if (phoneHardware.equalsIgnoreCase("LD Calling Plans")) {
				selectFromList(HARDWARE2, phoneHardware);
			}
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addPhoneHardwareWithoutSlNos");
		} catch (Exception e) {
			log.error("Error in addPhoneHardwareWithoutSlNos:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addPhoneHardwareWithoutSlNo(String phoneHardware, String hardware) {
		try {
			log.debug("Entering addPhoneHardwareWithoutSlNo");
			wait(4);
			click(ADD_PHONE_PROD);
			wait(5);
			if (phoneHardware.equalsIgnoreCase("Hardware")) {
				if (hardware.equals("1stHardware")) {
					selectFromList(PHONE_HARDWARE_DROPDOWN, phoneHardware);
				}
				if (hardware.equals("3rdProduct")) {
					selectFromList(HARDWARE2, phoneHardware);
				}
				wait(2);
				click(PHONEHARDWARE);
				wait(2);
				switchToChildAlert();
				wait(1);
				do {
					wait(1);
				} while (!isDisplayed(HARDWARE_OK_BUTTON));
				test.log(LogStatus.PASS, "Add Phone Hardware", "Added Phone Hardware Successfully");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				click(HARDWARE_OK_BUTTON);
				wait(1);
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addPhoneHardwareWithoutSlNo");
		} catch (Exception e) {
			log.error("Error in addPhoneHardwareWithoutSlNo:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public String addBulkServicePhone(String phoneHardware) {
		try {
			log.debug("Entering addBulkServicePhone");
			wait(2);
			click(ADD_PHONE_PROD);
			wait(2);
			if (phoneHardware.equalsIgnoreCase("Bulk Personal Phone")) {
				selectFromList(BULK_PHONE_HARDWARE_DROPDOWN, phoneHardware);
				wait(2);
				click(BULK_PERSONAL_PHONE);
				wait(2);
			}
			test.log(LogStatus.PASS, "Add Phone Service", "Added Phone Service Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addBulkServicePhone");
		} catch (Exception e) {
			log.error("Error in addBulkServicePhone:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return phoneNbr;
	}

	public void checkPreferredPayment(String ischangeCycleDate) {
		try {
			log.debug("Entering checkPreferredPayment");
			wait(1);
			click(BILLING_PREFERENCES_MORE);
			wait(2);
			switchToChildAlert();
			wait(1);
			test.log(LogStatus.INFO, "Preferred Payement View at first: " + test.addScreenCapture(addScreenshot()));
			click(SET_PREFERRED_PAYMENT_BTN);
			test.log(LogStatus.INFO,
					"Preferred Payement after clicking checkbox: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			if (ischangeCycleDate.equalsIgnoreCase("Yes")) {
				click(BILL_CYCLE_INC_BTN);
				test.log(LogStatus.INFO, "After Increment in BillCycle: " + test.addScreenCapture(addScreenshot()));
			} else {
				click(SET_PREFERRED_PAYMENT_BTN);
				test.log(LogStatus.INFO, "Preferred Payement after uncheck: " + test.addScreenCapture(addScreenshot()));
			}
			wait(2);
			click(OK_BUTTON_TO_CLOSE);

		} catch (Exception e) {
			log.error("Error in checkPreferredPayment:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public String changeCustNbrPortedToPorted(String shawServiceRequest) {
		try {
			log.debug("Entering changeCustNbrPortedToPorted");
			wait(1);
			if (isElementPresent(SELECT_NUMBER)) {
				wait(1);
				click(SELECT_NUMBER);
				wait(1);
			} else {
				wait(1);
				click(PHONE_WRENCH_BUTTON);
				wait(2);
			}
			switchToChildAlert();
			wait(1);
			click(CHANGE_CUST_NBR);
			wait(2);
			click(PORT_NUMBER_RADIO_BUTTON);
			wait(4);
			WebElement e1 = getWebElement(PORTED_NUMBER);
			portPhoneNumber = Utility.generatePortPhoneNbr();
			jse.executeScript("arguments[0].value='" + portPhoneNumber + "';", e1);
			// jse.executeScript("arguments[0].value='(987) 123-6540';", e1);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(PORT_NOW);
			wait(7);
			click(LANDLINE_RADIO_BUTTON);
			wait(2);
			inputText(PREVIOUS_PROVIDER_ACCOUNT_NUMBER, "1301140105");
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(RESELLER_DROP_DOWN);
			wait(2);
			click(RESELLER_DROP_DOWN);
			click(RESELLER_VALUE);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(3);
			if (shawServiceRequest.equalsIgnoreCase("Yes")) {
				click(SHAW_SERVICE_REQUESTED_YES);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(1);
				click(SELECT_ADDRESS);
				wait(1);
				inputText(OLD_HOUSE_NBR_TXT, "630");
				wait(2);
				click(OLD_STREET_NAME);
				wait(2);
				inputText(OLD_STREET_NAME, "Ave SW");
				wait(2);
				click(OLD_CITY);
				wait(2);
				inputText(OLD_CITY, "Calgary");
				wait(2);
				click(OLD_POSTAL_CODE);
				wait(2);
				inputText(OLD_POSTAL_CODE, "T2P4L4");
				wait(2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			if (shawServiceRequest.equalsIgnoreCase("No")) {

				click(SHAW_SERVICE_REQUESTED_NO);
				wait(2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			click(OK_BUTTON_TO_CLOSE);
			wait(3);
			click(LISTINGTYPE_LIST);
			wait(3);
			click(NO_LISTING);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(OK_BUTTON_TO_CLOSE);
			wait(1);
			portPhoneNumber = convertPhoneToString(getText(PERSONAL_PHONE_NBR_FIELD));
			log.debug("Leaving changeCustNbrPortedToPorted");
		} catch (Exception e) {
			log.error("Error in changeCustNbrPortedToPorted:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return portPhoneNumber;
	}

	public void addServiceInternet(String InternetProd) {
		try {
			log.debug("Entering addServiceInternet");
			if (InternetProd.equals("Fibre+ 150")) {
				wait(7);
				boolean staleElement = true;
				while (staleElement) {
					try {
						click(FIBRE_150);
						wait(2);
						staleElement = false;
					} catch (StaleElementReferenceException e) {
						staleElement = true;
					}
				}
			}
			if (InternetProd.equals("Fibre+ 75")) {
				wait(1);
				click(FIBRE_75);
			}
			if (InternetProd.equals("Fibre+ 25")) {
				wait(1);
				click(FIBRE_25);
			}
			if (InternetProd.equals("Fibre+ 300")) {
				wait(1);
				click(FIBRE_300);
			}

			if (InternetProd.equals("Fibre+ 250")) {
				wait(1);
				click(FIBRE_250);
			}
			if (InternetProd.equals("Freedom Internet 150")) {
				wait(1);
				click(FREEDOM_INTERNET_150);
			}

			// if (InternetProd.equals("Fibre+ 150")) {
			// wait(1);
			// click(FIBRE_150);
			// }
			if (InternetProd.equals("Employee Internet 75")) {
				wait(1);
				click(EMPLOYEE_INTERNET_75);
			}
			if (InternetProd.equals("Employee Internet 300")) {
				wait(1);
				click(EMPLOYEE_INTERNET300);
			}
			if (InternetProd.equals("Connecting Families")) {
				wait(1);
				click(CONNECTING_FAMILIES);
				wait(2);
			}
			wait(5);
			test.log(LogStatus.PASS, "Add Internet Product", "Added Internet Product Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addServiceInternet");

		} catch (Exception e) {
			log.error("Error in addServiceInternet:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addServiceBulkInternet(String prod) {
		try {
			wait(1);
			log.debug("Entering addServiceBulkInternet");
			if (prod.equals("Bulk Fibre+ 300")) {
				wait(2);
				click(BULK_FIBRE_300);
				wait(3);
			}
			if (prod.equals("Bulk Fibre+ 150")) {
				wait(2);
				click(BULK_FIBRE_150);
				wait(3);
			}
			if (prod.equals("Bulk Fibre+ 25")) {
				wait(2);
				click(BULK_FIBRE_25);
				wait(3);
			}
			wait(1);
			test.log(LogStatus.PASS, "Add Bulk Internet Product", "Added Bulk Internet Product Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addServiceBulkInternet");
		} catch (Exception e) {
			log.error("Error in addServiceBulkInternet:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public String addInternetEmail(String Email, String branchValue) {
		String emailAddress = "";
		try {
			log.debug("Entering addInternetEmail");
			wait(2);
			click(INTERNET_EMAIL_WRENCH);
			wait(2);
			switchToChildAlert();
			if (Email.equals("Yes")) {
				click(DELETE_HARDWARE);
			}
			wait(1);
			click(EMAIL_TRANSFER_OPTIONS);
			wait(1);
			click(RETAIL_PICKUP_DROPDOWN);
			wait(4);
			if (branchValue.equals("Calgary")) {
				click(BRANCH_DROPDOWN_VALUE);
				wait(1);
			}
			if (branchValue.equals("Edmonton")) {
				click(BRANCH_DROPDOWN_VALUE2);
				wait(1);
			}
			WebElement e1 = getWebElement(EMAIL_ADDRESS_AC_NBR);
			wait(1);
			jse.executeScript("arguments[0].value='987-6543-2109';", e1);
			inputText(ADD_EMAIL_ADDRESS_INPUT, Utility.generateRandomEmailId());
			wait(2);
			emailAddress = getText(ADD_EMAIL_ADDRESS_INPUT);
			wait(1);
			click(EMAIL_VALIDATE);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(EMAIL_OK_BUTTON);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(EMAIL_OK_BUTTON);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addInternetEmail");
		} catch (Exception e) {
			log.error("Error in addInternetEmail:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return emailAddress;
	}

	public void addInternetHardware(String internetRentSelection, String internetHardwareSerialNo) {
		try {
			log.debug("Entering addingInternetHardware");

			if (internetRentSelection.equalsIgnoreCase("Cisco")) {
				wait(5);
				click(INTERNET_HARDWARE);
				wait(5);
				click(INTERNET150_HITRONRENT);
				wait(6);
			}
			if (internetRentSelection.equalsIgnoreCase("Hitron")) {
				wait(5);
				click(INTERNET_HARDWARE);
				wait(5);
				click(INTERNET150_HITRONRENT);
				wait(6);
			}
			if (internetRentSelection.equalsIgnoreCase("3nCisco")) {
				wait(5);
				click(INTERNET_HARDWARE);
				wait(5);
				click(INTERNET75_RENT);
				wait(2);
			}
			if (internetRentSelection.equalsIgnoreCase("Buy")) {
				wait(5);
				click(INTERNET_HARDWARE);
				wait(5);
				click(INTERNET_HITRON_BUY_BTN);
				wait(2);
			}
			wait(2);
			inputText(INTERNET150_SERIAL_NBR_FIELD, internetHardwareSerialNo);
			wait(4);
			click(INTERNET_SRLNBR_VALIDATE);
			wait(1);
			do {
				wait(2);
			} while (!isDisplayed(HARDWARE_OK_BUTTON));
			test.log(LogStatus.PASS, "Added Internet Harware", "Added Internet Harware Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(HARDWARE_OK_BUTTON);
			log.debug("Leaving addingInternetHardware");
		} catch (Exception e) {
			log.error("Error in addingInternetHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addInternetHardwareForStaff(String internetRentSelection, String internetHardwareSerialNo) {
		try {
			log.debug("Entering addingInternetHardware");

			if (internetRentSelection.equalsIgnoreCase("Cisco")) {
				wait(2);
				click(INTERNET_HARDWARE);
				wait(2);
				click(INTERNET150_HITRONRENT);
			}
			if (internetRentSelection.equalsIgnoreCase("Hitron")) {
				wait(2);
				click(INTERNET_HARDWARE);
				wait(2);
				click(INTERNET150_HITRONRENT);
			}
			if (internetRentSelection.equalsIgnoreCase("3nCisco")) {
				wait(2);
				click(INTERNET_HARDWARE);
				wait(2);
				click(INTERNET75_RENT);
			}
			if (internetRentSelection.equalsIgnoreCase("Buy")) {
				wait(2);
				click(INTERNET_HARDWARE);
				wait(2);
				click(INTERNET_HITRON_BUY_BTN);
			}
			wait(2);
			inputText(INTERNET150_SERIAL_NBR_FIELD_Staff, internetHardwareSerialNo);
			wait(2);
			click(INTERNET_SRLNBR_VALIDATE);
			wait(1);
			do {
				wait(2);
			} while (!isDisplayed(HARDWARE_OK_BUTTON));
			test.log(LogStatus.PASS, "Added Internet Harware", "Added Internet Harware Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(HARDWARE_OK_BUTTON);
			log.debug("Leaving addingInternetHardware");
		} catch (Exception e) {
			log.error("Error in addingInternetHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void emailDelete() {
		try {
			log.debug("Entering emailDelete");
			wait(2);
			click(INTERNET_EMAIL_WRENCH);
			wait(2);
			switchToChildAlert();
			click(DELETE_HARDWARE);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			click(EMAIL_OK_BUTTON);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving emailDelete");
		} catch (Exception e) {
			log.error("Error in emailDelete:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addBulkInternetProduct(String order, String bulkinternethardware) {
		try {
			log.debug("Entering addingBulkInternetProduct");
			wait(3);
			if (order.equals("Base Price - Bulk Fibre+ 300")) {
				click(BULK_FIBER_300_WRENCH_BTN);
			}
			wait(3);
			if (order.equals("Base Price - Bulk Fibre+ 150")) {
				click(BULK_FIBER_150_WRENCH_BTN);
			}
			wait(3);
			if (order.equals("Base Price - Bulk Fibre+ 25")) {
				click(BULK_FIBER_25_WRENCH_BTN);
			}
			wait(2);
			switchToChildAlert();
			wait(2);

			if (isElementPresent(BULK_HARDWARE)) {
				click(BULK_HARDWARE);
			}

			if (bulkinternethardware.equalsIgnoreCase("Rental BlueCurve Gateway WiFi Modem (XB6) - 348")) {
				wait(2);
				click(RENTAL_BLUE_CURVE348_UPARROW);
				wait(2);
			}

			click(BULK_INTERNET_HARDWARE_OK_BTN);
			log.debug("Leaving addingBulkInternetProduct");
		} catch (Exception e) {
			log.error("Error in addingBulkInternetProduct:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addBulkPhoneProduct(String bulkphonehardware) {
		try {
			log.debug("Entering addingBulkPhoneProduct");
			wait(3);
			click(BULK_PERSONAL_PHONE_WRENCH_BTN);
			wait(2);
			switchToChildAlert();
			wait(2);

			if (isElementPresent(BULK_HARDWARE)) {
				click(BULK_HARDWARE);
			}

			if (bulkphonehardware.equalsIgnoreCase("Rental Phone Terminal")) {
				wait(2);
				click(RENTAL_PHONE_TERMINAL_UPARROW);
				wait(2);
			}

			click(BULK_INTERNET_HARDWARE_OK_BTN);
			log.debug("Leaving addingBulkPhoneProduct");
		} catch (Exception e) {
			log.error("Error in addingBulkPhoneProduct:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	/***************************************************
	 * Add services & features Tab (TV Related)
	 ***************************************************/

	public void addServiceTV(String prod) {
		try {
			wait(1);
			log.debug("Entering addServiceTV");
			if (prod.equals("Limited TV")) {
				wait(10);
				click(LIMITED_TV);
				wait(30);
			}
			if (prod.equals("Digital Classic")) {
				wait(2);
				scrollDownAndClick(DIGITAL_CLASSIC);
				click(DIGITAL_CLASSIC);
				wait(30);
			}
			if (prod.equals("Digital Basic")) {
				wait(2);
				click(DIGITAL_BASIC);
				wait(15);
			}

			if (prod.equals("Small TV Prebuilt")) {
				wait(5);
				click(SMALL_TV_PREBUILT);
				wait(8);
			}
			if (prod.equals("Medium TV Prebuilt")) {
				wait(2);
				click(MEDIUM_TV_PREBUILT);
				wait(3);
			}
			if (prod.equals("Total TV")) {
				wait(2);
				click(TOTAL_TV);
				wait(5);
			}
			if (prod.equals("Employee TV")) {
				wait(2);
				click(EMPLOYEE_TV);
				wait(3);
			}
			wait(1);
			test.log(LogStatus.PASS, "Add TV Product", "Added TV Product Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addServiceTV");
		} catch (Exception e) {
			log.error("Error in addServiceTV:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addServiceBulkTV(String prod) {
		try {
			wait(1);
			log.debug("Entering addServiceBulkTV");
			if (prod.equals("Bulk Total TV")) {
				wait(2);
				click(BULK_TOTAL_TV);
				wait(3);
			}
			if (prod.equals("Bulk Legacy Popular TV")) {
				wait(2);
				click(BULK_LEGACY_POPULAR_TV);
				wait(3);
			}
			if (prod.equals("Bulk Digital Basic")) {
				wait(2);
				click(BULK_DIGITAL_BASIC_TV);
				wait(3);
			}
			wait(1);
			test.log(LogStatus.PASS, "Add Bulk TV Product", "Added Bulk TV Product Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addServiceBulkTV");
		} catch (Exception e) {
			log.error("Error in addServiceBulkTV:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void selectMediumTvPick8() {
		try {
			log.debug("Entering selectMediumTvPick8");
			wait(2);
			click(MEDIUM_TV_PREBUILT);
			wait(3);
			click(TV_PICK_OPTION_WRENCH);
			wait(2);
			switchToChildAlert();
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(NEWS_NEWS1);
			wait(2);
			click(SPORTS_TSNPACK);
			wait(2);
			click(TIME_SHIFT);
			wait(2);
			click(MOVIES_VARIETY);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(EMAIL_OK_BUTTON);
			wait(1);
		} catch (Exception e) {
			log.error("Error in selectMediumTvPick8:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addTVPriceGuarenteePramotions(String pramotion) {
		try {
			log.debug("Entering addTVPriceGuarentee");
			wait(2);
			click(PROMOTIONS_LINK);
			wait(3);
			switchToChildAlert();
			wait(2);
			click(TV_PROMOTION_BTN);
			wait(5);
			if (pramotion.equals("Limited TV")) {
				click(TWOYVP24M_LIMITEDTV_PRICEGURANTEE);
				wait(2);
			}
			if (pramotion.equals("Small TV Prebuilt")) {
				click(TWOYVP24M_SMALLTV_PRICEGURANTEE);
				wait(2);
			}
			if (pramotion.equals("Digital Classic")) {
				click(TWOYVP24M_DIGITAL_CLASSICTV_PRICEGURANTEE90);
				wait(2);
			}
			if (pramotion.equals("theme")) {
				wait(2);
				click(THREEM_LARGETV55);
				wait(5);
			}
			click(OK_BUTTON_TO_CLOSE);
			log.debug("Leaving addTVPriceGuarentee");
		} catch (Exception e) {
			log.error("Error in addTVPriceGuarentee:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addPromotions() {
		try {
			log.debug("Entering addPromotions");
			wait(2);
			click(PROMOTIONS_LINK);
			wait(1);
			switchToChildAlert();
			wait(1);
			if (isElementPresent(TWO_YVP_INTERNET_TV_AGGREEMENT)) {
				wait(1);
				click(TWO_YVP_INTERNET_TV_AGGREEMENT);
				wait(1);
				if (isElementPresent(TWENTYFOUR_M_INT_50PROMO_70)) {
					wait(1);
					click(TWENTYFOUR_M_INT_50PROMO_70);
					wait(1);
				}
			} else if (isElementPresent(ONEY_TWOP_BULKMASTER_TERMRATE_AGREEMENT)) {
				wait(1);
				click(ONEY_TWOP_BULKMASTER_TERMRATE_AGREEMENT);
			} else {
				click(DCT_700_DISCOUNT);
				wait(10);
			}

			click(OK_BUTTON_TO_CLOSE);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addPromotions");
		} catch (Exception e) {
			log.error("Error in addPromotions:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void add2YVP_Internet_TV_Promotions() {
		try {
			log.debug("Entering add 2YVP Internet TV Aggreement Promotions");
			wait(2);
			click(PROMOTIONS_LINK);
			wait(1);
			switchToChildAlert();
//			wait(1);
			wait(2);
//			click(ACCOUNT_VIEW_TAB);
			if (isElementPresent(TWO_YVP_INTERNET_TV_AGGREEMENT)) {
				wait(1);
				click(TWO_YVP_INTERNET_TV_AGGREEMENT);
				wait(1);
			}
			wait(2);
			click(TWO_YVP_INTERNET_TV_AGREEMENT_INFO);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(CLOSE_BUTTON);
			wait(2);
			click(ROLE_VIEW_TAB);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(OK_BUTTON_TO_CLOSE);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving add 2YVP Internet TV Aggreement Promotions");
		} catch (Exception e) {
			log.error("Error in add 2YVP Internet TV Aggreement Promotions:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void add24M_2YVP_fibre_300_Promotions() {
		try {
			log.debug("Entering add 24M 2YVP Fibre300 Promotions");
			wait(2);
			click(PROMOTIONS_LINK);
			wait(1);
			switchToChildAlert();
//			wait(1);
			wait(2);
			click(ACCOUNT_VIEW_TAB);
			if (isElementPresent(TWO_YVP_INTERNET_TV_AGGREEMENT)) {
				wait(1);
				click(TWO_YVP_INTERNET_TV_AGGREEMENT);
				wait(1);
			}
			wait(2);
			click(TWO_YVP_INTERNET_TV_AGREEMENT_INFO);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(CLOSE_BUTTON);
			wait(2);
			click(ROLE_VIEW_TAB);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(OK_BUTTON_TO_CLOSE);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving add 24M 2YVP Fibre300 Promotions");
		} catch (Exception e) {
			log.error("Error in add 24M 2YVP Fibre300 Promotions:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addPromotionsForBulkProduct() {
		try {
			log.debug("Entering addPromotions");
			wait(2);
			click(PROMOTIONS_LINK);
			wait(1);
			switchToChildAlert();
			wait(1);
			click(PHONE_PROMOTION_BTN);
			wait(1);
			click(BUNDLES_PROMO_BTN);
			wait(2);
			if (isElementPresent(ONEY_TWOP_BULKMASTER_TERMRATE_AGREEMENT)) {
				wait(1);
				click(ONEY_TWOP_BULKMASTER_TERMRATE_AGREEMENT);
			}
			if (isElementPresent(ONEY_THREEP_BULKMASTER_TERMRATE_AGREEMENT)) {
				wait(1);
				click(ONEY_THREEP_BULKMASTER_TERMRATE_AGREEMENT);
			}
			if (isElementPresent(ONEY_ONEP_TV_BULKMASTER_TERMRATE_AGREEMENT)) {
				wait(1);
				click(ONEY_ONEP_TV_BULKMASTER_TERMRATE_AGREEMENT);
			}
			wait(2);
			click(PROMOTION_INFO_BTN);
			wait(6);
			if (isElementPresent(CONTRACTED_UNIT)) {
				wait(2);
				inputText(CONTRACTED_UNIT, "4");
			}
			wait(1);
			click(PROMOTION_CLOSE_BTN);
			wait(3);
			click(OK_BUTTON_TO_CLOSE_PROMOTION);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addPromotions For BulkProduct");
		} catch (Exception e) {
			log.error("Error in addPromotionsForBulkProduct:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addProductPrices() {
		try {
			log.debug("Entering addProductPrices");

			if (isElementPresent(BULK_FIBER_300_PRICE_INPUT)) {
				wait(2);
				inputText(BULK_FIBER_300_PRICE_INPUT, "30");
			}
			if (isElementPresent(BULK_TOTAL_TV_PRICE_INPUT)) {
				wait(2);
				inputText(BULK_TOTAL_TV_PRICE_INPUT, "20");
			}
			wait(1);
			click(OK_BUTTON_TO_CLOSE_PROMOTION);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addProductPrices For BulkProduct");
		} catch (Exception e) {
			log.error("Error in addProductPrices:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addBulkTVProduct(String order, String bulkTVhardware) {
		try {
			log.debug("Entering addingBulkTVProduct");
			wait(3);
			if (order.equals("Base Price - Bulk Total TV (Canada)")) {
				click(BULK_TOTAL_TV_WRENCH_BTN);
			}
			wait(3);
			if (order.equals("Base Price - Bulk Legacy Popular TV (Canada)")) {
				click(BULK_LEGACY_POPULAR_TV_WRENCH_BTN);
			}
			wait(3);
			if (order.equals("Base Price - Bulk Digital Basic (Canada)")) {
				click(BULK_DIGITAL_BASIC_WRENCH_BTN);
			}
			wait(2);
			switchToChildAlert();
			wait(2);

			if (isElementPresent(BULK_HARDWARE)) {
				click(BULK_HARDWARE);
			}
			if (bulkTVhardware.equalsIgnoreCase("BlueCurve TV Player Wireless 4K Rental")) {
				wait(5);
				click(RENTAL_BLUE_CURVE_TV_4K_UPARROW);
				wait(2);
			}
			if (bulkTVhardware.equalsIgnoreCase("XG1v3 BlueCurve TV Player Rental")) {
				wait(2);
				click(XG1V3_BLUE_CURVE_TV_PLAYER_RENTAL_UPARROW);
				wait(2);
			}
			if (bulkTVhardware.equalsIgnoreCase("Rental DCT 700")) {
				wait(2);
				click(BULK_RENTAL_DCT_700_UPARROW);
				wait(2);
			}
			click(BULK_TV_HARDWARE_OK_BTN);
			wait(4);
			log.debug("Leaving addingBulkTVProduct");
		} catch (Exception e) {
			log.error("Error in addingBulkTVProduct:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addConvergedHardware(String order, String serialNo) {
		try {
			log.debug("Entering addConvergedHardware");
			wait(6);
			click(CONVERGED_HARDWARE_PROD);
			wait(7);
			click(CONVERGED_HARDWARE_WRENCH);
			wait(4);
			if (order.equals("Converged150")) {
				click(CONVERGED_HARWARE_RENT150);
				wait(2);
				inputText(CONVERGED_HARDWARE_SERIAL_NUMBER_FIELD150, serialNo);
				wait(2);
			}
			if (order.equals("Converged75")) {
				click(CONVERGED_HARWARE_RENT75);
				wait(5);
				inputText(CONVERGED_HARDWARE_SERIAL_NUMBER_FIELD75, serialNo);
				wait(5);
			}
			click(VALIDATE_BUTTON);
			do {
				wait(3);
			} while (!isDisplayed(HARDWARE_OK_BUTTON));
			wait(4);
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(HARDWARE_OK_BUTTON);
			wait(4);
			test.log(LogStatus.INFO, "Added Converged Harware", "Added Converged Harware Successfully");
			log.debug("Leaving addConvergedHardware");
		} catch (Exception e) {
			log.error("Error in addConvergedHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addConvergedHardware1(String serialNo) {
		try {
			log.debug("Entering addConvergedHardware");
			wait(6);
			click(CONVERGED_HARDWARE_PROD);
			wait(9);
			click(CONVERGED_HARDWARE_WRENCH);
			wait(7);

			click(CONVERGED_HARWARE_RENT75);
			wait(4);
			inputText(CONVERGED_HARDWARE_SERIAL_NUMBER_FIELD75, serialNo);
			wait(10);
			click(VALIDATE_BUTTON);
			do {
				wait(10);
			} while (!isDisplayed(HARDWARE_OK_BUTTON));
			wait(10);
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(HARDWARE_OK_BUTTON);
			wait(6);
			test.log(LogStatus.INFO, "Added Converged Harware", "Added Converged Harware Successfully");
			log.debug("Leaving addConvergedHardware");
		} catch (Exception e) {
			log.error("Error in addConvergedHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addConvergedHardwareWithSingleElement(String order, String serialNo) {
		try {
			log.debug("Entering addConvergedHardware");
			wait(2);
			click(CONVERGED_HARDWARE_PROD);
			wait(2);
			click(CONVERGED_HARDWARE_WRENCH);
			wait(2);
			if (order.equals("Converged150")) {
				click(CONVERGED_HARWARE_RENT150_SINGLE_ELEMENT);
				wait(2);
				inputText(CONVERGED_HARDWARE_SERIAL_NUMBER_FIELD150_SINGLE_ELEMENT, serialNo);
				wait(2);
			}
			if (order.equals("Converged75")) {
				click(CONVERGED_HARWARE_RENT75);
				wait(2);
				inputText(CONVERGED_HARDWARE_SERIAL_NUMBER_FIELD75, serialNo);
				wait(2);
			}
			if (order.equals("Freedom Gateway")) {
				click(FREEDOM_GATEWAY_WIFI_MODEM_RENT);
				wait(2);
				inputText(FREEDOM_GATEWAY_WIFI_MODEM_INPUT, serialNo);
				wait(2);
			}

			click(VALIDATE_BUTTON);
			do {
				wait(1);
			} while (!isDisplayed(HARDWARE_OK_BUTTON));
			wait(1);
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(HARDWARE_OK_BUTTON);
			wait(2);
			test.log(LogStatus.INFO, "Added Converged Harware", "Added Converged Harware Successfully");
			log.debug("Leaving addConvergedHardware");
		} catch (Exception e) {
			log.error("Error in addConvergedHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addConvergedHardwareWithoutSlNbr() {
		try {
			log.debug("Entering addConvergedHardware");
			wait(5);
			click(CONVERGED_HARDWARE_PROD);
			wait(5);
			click(CONVERGED_HARDWARE_WRENCH);
			wait(1);
			switchToChildAlert();
			wait(1);
			// click(CONVERGED_HARWARE_RENT150);
			wait(1);
			click(CONVERGED_HARWARE_RENT150_SINGLE_ELEMENT);
			wait(2);
			do {
				wait(1);
			} while (!isDisplayed(HARDWARE_OK_BUTTON));
			wait(1);
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(HARDWARE_OK_BUTTON);
			wait(2);
			test.log(LogStatus.INFO, "Added Converged Harware", "Added Converged Harware Successfully");
			log.debug("Leaving addConvergedHardware");
		} catch (Exception e) {
			log.error("Error in addConvergedHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void selectConvergedHardware(String convergedHardware) {
		try {
			log.debug("Entering selectConvergedHardware");

			if (convergedHardware.equalsIgnoreCase("Phone")) {
				wait(4);
				click(SELECT_PHONE_SERVICE);
			}
			if (convergedHardware.equalsIgnoreCase("Internet")) {
				wait(4);
				click(SELECT_INTERNET_SERVICE);
			}
			if (convergedHardware.equalsIgnoreCase("Both")) {
				wait(8);
				click(SELECT_PHONE_SERVICE);
				wait(10);
				click(SELECT_INTERNET_SERVICE);
			}
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			test.log(LogStatus.PASS, "Select Phone/Internet Service", "Selected Phone/Internet Service Successfully");

			log.debug("Leaving selectConvergedHardware");
		} catch (Exception e) {
			log.error("Error in selectConvergedHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void deleteConvergedHardware() {
		try {
			log.debug("Entering deleteConvergedHardware");
			wait(3);
			click(DELETE_CONVERGED_HARDWARE);
			wait(20);
			click(REMOVE_CONFIRM_YES);
			wait(7);
			log.debug("Leaving deleteConvergedHardware");
		} catch (Exception e) {
			log.error("Error in deleteConvergedHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void deleteTVHardware() {
		try {
			log.debug("Entering deleteTVHardware");
			wait(2);
			click(TV_HARDWARE_WRENCH);
			wait(2);
			click(DELETE_HARDWARE);
			wait(1);
			do {
				wait(2);
			} while (!isDisplayed(HARDWARE_OK_BUTTON));
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(HARDWARE_OK_BUTTON);
			wait(1);
			log.debug("Leaving deleteTVHardware");
		} catch (Exception e) {
			log.error("Error in deleteTVHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void selectPhoneConvergedHardware() {
		try {
			log.debug("Entering selectPhoneConvergedHardware");
			click(SELECT_PHONE_SERVICE);
			wait(3);
			test.log(LogStatus.PASS, "Added Phone Converged Harware", "Added Phone Converged Harware Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving selectPhoneConvergedHardware");
		} catch (Exception e) {
			log.error("Error in selectPhoneConvergedHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addDigitalChannelSectionThemePick10Pack1() {
		try {
			log.debug("Entering addDigitalChannelSectionTheme");
			wait(5);
			click(DIGITAL_CHANNEL_WRENCH);
			wait(5);
			switchToChildAlert();
			click(PICK10_PACK1);
			wait(5);
			click(PICK10_PACK1_WRENCH);
			switchToChildAlert();
			wait(5);
			click(ASIDE);
			wait(5);
			click(DOCUMENTRY);
			wait(5);
			click(DEJA_VIEW);
			wait(5);
			click(ESPN_CLASSIC);
			wait(5);
			click(FIGHT_NETWORK);
			wait(5);
			click(GSN_GAMESHOW_NETWORK);
			wait(5);
			click(LOVE_NATURE);
			wait(5);
			click(MAKEFUL);
			wait(5);
			click(ONE_GET_FIT);
			wait(5);
			click(SPORTS_MAN);
			wait(5);
			click(OK_BTN_TO_CLOSE);
			// click(DIGITAL_CHANNEL_OK);
			wait(5);
			click(DIGITAL_CHANNEL_THEME);
			wait(5);
			click(ENT3MEDIUM_POTPOURRI2_MUSICPOP_CULTURE);
			wait(5);
			test.log(LogStatus.PASS, "Add Digital Theme", "Added Digital Theme Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(OK_BTN_TO_CLOSE);
			wait(5);
			log.debug("Leaving addDigitalChannelSectionTheme");
		} catch (Exception e) {
			log.error("Error in addDigitalChannelSectionTheme:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addingPick10pack2DigitalChannels(String TC) throws Exception {
		try {
			log.debug("Entering addingPick10pack2DigitalChannels");
			wait(9);
			WebElement wrenchBtn = getWebElement(DIGITAL_CHANNEL_WRENCH);
			jse.executeScript("arguments[0].click();", wrenchBtn);
			click(DIGITAL_CHANNEL_WRENCH);
			wait(10);
			switchToChildAlert();
			click(PICK10_PACK2);
			wait(5);
			if (TC.equals("2nd Wrench")) {
				click(PICK10_PACK2_WRENCH2);
				wait(3);
			}
			if (TC.equals("3rd Wrench")) {
				click(PICK10_PACK2_WRENCH3);
				wait(3);
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			switchToChildAlert();
			wait(1);
			click(ASIDE);
			wait(8);
			click(DOCUMENTRY);
			wait(8);
			click(DEJA_VIEW);
			wait(8);
			click(ESPN_CLASSIC);
			wait(8);
			click(FIGHT_NETWORK);
			wait(8);
			click(GSN_GAMESHOW_NETWORK);
			wait(7);
			click(LOVE_NATURE);
			wait(8);
			click(MAKEFUL);
			wait(9);
			click(ONE_GET_FIT);
			wait(9);
			click(SPORTS_MAN);
			wait(9);
			do {
				wait(1);
			} while (!isDisplayed(PICK_10_OK_BUTTON));
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(PICK_10_OK_BUTTON);
			wait(9);
			test.log(LogStatus.PASS, "adding Pick10 pack2 Digital Channels",
					"Added Pick10 pack2 Digital Channels Successfully");
			log.debug("Leaving addingPick10pack2DigitalChannels");
		} catch (Exception e) {
			log.error("Error in addingPick10pack2DigitalChannels:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addDigitalChannelDiscounts() {
		try {
			log.debug("Entering addDigitalChannelDiscounts");
			wait(1);
			click(DIGITAL_CHANNEL_WRENCH);
			wait(2);
			switchToChildAlert();
			click(FIGHT_NETWORK);
			wait(3);
			click(FOX_NEWS);
			wait(3);
			click(ESPN_CLASSIC);
			wait(2);
			test.log(LogStatus.PASS, "Add Digital Theme", "Added Digital Theme Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(OK_BTN_TO_CLOSE);
			wait(2);
			log.debug("Leaving addDigitalChannelDiscounts");
		} catch (Exception e) {
			log.error("Error in addDigitalChannelDiscounts:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void clickRentAndEnterSrlNo(String hardwareType, String srlNo) {
		log.debug("Hardware type is :" + hardwareType + " and srl No is:" + srlNo);
		try {
			switch (hardwareType) {
			case "DCT700":
				wait(4);
				click(RENT_DCT_700);
				wait(6);
				inputText(DCT_700_SLNO_INPUT1, srlNo);
				break;

			case "DCX3200M":
				wait(2);
				click(RENT_DCX_3200M);
				wait(7);
				inputText(DCX_3200M_SLNO_INPUT, srlNo);
				break;

			case "DCX3200M1":
				wait(2);
				click(RENT_DCX_3200M);
				wait(7);
				inputText(DCX_3200M_SLNO_INPUT1, srlNo);
				break;

			case "DCX3200HDGuide":
				click(RENT_DCX_3200_HDGUIDE);
				wait(7);
				inputText(DCX_3200_HDGUIDE_SLNO_INPUT, srlNo);
				break;

			case "DCX3200P2MTC":
				wait(2);
				click(RENT_DCX_3200_P2MTC);
				wait(7);
				inputText(DCX_3200_P2MTC_SLNO_INPUT, srlNo);
				break;

			case "DCX3400_500GB":
				wait(2);
				click(RENT_DCX_DCX3400_500GB);
				wait(5);
				inputText(DCX_DCX3400_500GB_SLNO_INPUT, srlNo);
				break;

			case "DCX3400_500GB_BUY":
				wait(2);
				click(DCX3400_BUY_BTN);
				wait(5);
				inputText(DCX_DCX3400_500GB_SLNO_INPUT, srlNo);
				break;

			case "DCX3400_250GB":
				wait(2);
				click(RENT_DCX_DCX3400_250GB);
				wait(5);
				inputText(DCX_DCX3400_250GB_SLNO_INPUT, srlNo);
				break;

			case "DCX3510HDGuide":
				wait(5);
				click(RENT_DCX3510_HDGUIDE);
				wait(8);
				inputText(DCX3510_HDGUIDE_SLNO_INPUT, srlNo);
				break;

			case "ShawGatewayHDPVR":
				wait(6);
				click(RENT_SHAW_GATEWAY_HDPVR);
				wait(6);
				inputText(SHAW_GATEWAY_HDPVR_SLNO_INPUT, srlNo);
				break;

			case "ShawGatewayPortal":
				wait(2);
				click(RENT_SHAW_GATEWAY_PORTAL);
				wait(5);
				inputText(SHAW_GATEWAY_PORTAL_SLNO_INPUT, srlNo);
				break;

			case "ShawGateway":
				wait(2);
				click(RENT_SHAW_GATEWAY);
				wait(5);
				inputText(SHAW_GATEWAY_SLNO_INPUT, srlNo);
				break;

			case "ShawPortal":
				wait(2);
				click(RENT_SHAW_PORTAL);
				wait(5);
				inputText(SHAW_PORTAL_SLNO_INPUT, srlNo);
				break;

			case "ShawBlueSkyTVbox526":
				wait(2);
				click(RENT_SHAW_BLUESKY_TVBOX526);
				wait(10);
				inputText(SHAW_BLUESKY_TVBOX526_SLNO_INPUT, srlNo);
				break;

			case "ShawBlueSkyTVportal416":
				wait(2);
				scrollDownAndClick(RENT_SHAW_BLUESKY_TVPORTAL416);
				wait(10);
				inputText(SHAW_BLUESKY_TVPORTAL416_SLNO_INPUT, srlNo);
				break;

			case "ShawBlueSkyTV4KWirelessBox":
				wait(2);
				click(RENT_SHAW_BLUESKY_TV_4KWIRELESS_BOX);
				wait(5);
				inputText(SHAW_BLUESKY_TV_4KWIRELESS_BOX_INPUT, srlNo);
				break;

			case "ShawBlueSkyTV4Kbox":
				wait(2);
				scrollDownAndClick(RENT_SHAW_BLUESKY_TV4KBOX_530);
				wait(5);
				inputText(SHAW_BLUESKY_TV4KBOX_530_SLNO_INPUT, srlNo);
				break;

			case "MoxiGatewayHDPVR_Buy":
				wait(2);
				scrollDownAndClick(MOXIGATEWAY_HDPVR525_BUY);
				wait(5);
				inputText(MOXIGATEWAY_HDPVR525_BUY_INPUT, srlNo);
				break;

			case "BlueCurve TV Wireless 4K Player":
				wait(2);
				inputText(BLUECURVE_TV_WIRELESS_4KPLAYER418_INPUT, srlNo);
				break;

			case "BlueCurve TV Player 526(Fibre+ 300 and below only)":
				wait(2);
				click(BLUECURVE_TV_PLAYER526_RENT_BTN);
				wait(2);
				inputText(BLUECURVE_TV_PLAYER526_INPUT, srlNo);
				break;

			default:
				log.debug("Did not match Hardware type");
			}
		} catch (Exception e) {
			log.error("Error in clickRentAndEnterSrlNo:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addTVHardwareWithSrlNo(String[] hardWareType, String[] serialNum) {
		try {
			wait(3);
			click(TV_HARDWARE_WRENCH);
			wait(5);
			switchToChildAlert();
			wait(4);
			for (int i = 0; i < hardWareType.length; i++) {
				log.debug("HW:" + hardWareType[i]);
				log.debug("SRL:" + serialNum[i]);
				clickRentAndEnterSrlNo(hardWareType[i], serialNum[i]);
				wait(5);
				if (isElementPresent(VALIDATE_SRL_NO)) {
					click(VALIDATE_SRL_NO);
				}
				wait(5);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				/*
				 * if (isElementPresent(VALIDATE_SRL_NO)){ Assert.fail(); }
				 */
			}
			do {
				wait(4);
			} while (!isElementPresent(HARDWARE_OK_BUTTON));
			wait(2);
			scrollDownAndClick(HARDWARE_OK_BUTTON);
			wait(5);
		} catch (Exception e) {
			log.error("Error in addTVHardwareWithSrlNo:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addTVSrlNoWithOutHrdware(String value, String srlNo) {
		try {
			wait(2);
			click(TV_HARDWARE_WRENCH);
			wait(1);
			switchToChildAlert();
			if (value.equals("BlueCurve TV")) {
				wait(1);
				click(RENT_DCT_700);
				wait(1);
				inputText(BLUECURVE_TV_WIRELESS_4KPLAYER418_INPUT, srlNo);
			}
			wait(4);
			click(VALIDATE_SRL_NO);
			wait(5);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			wait(3);
			scrollDownAndClick(HARDWARE_OK_BUTTON);
		} catch (Exception e) {
			log.error("Error in addTVSerialNoWithOutHrdware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addTVHardwareWithOutSrlNo(String[] hardWareType) {
		try {
			wait(2);
			click(TV_HARDWARE_WRENCH);
			wait(5);
			switchToChildAlert();
			for (int i = 0; i < hardWareType.length; i++) {
				clickRentAndEnterSrlNo(hardWareType[i], "");
				wait(4);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			wait(1);
			scrollDownAndClick(HARDWARE_OK_BUTTON);
		} catch (Exception e) {
			log.error("Error in addTVHardwareWithOutSrlNo:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addTVService(String prod, String[] hardWareType, String[] serialNum) {
		addServiceTV(prod);
		addTVHardwareWithSrlNo(hardWareType, serialNum);
	}

	public void addTVWithNoOfShawPortalHardwares(String SrlNbr1, String SrlNbr2, String SrlNbr3, String SrlNbr4,
			String SrlNbr5) {
		try {
			log.debug("Entering addTVWithNoOfHardwares");
			wait(1);
			click(TV_HARDWARE_WRENCH);
			wait(1);
			switchToChildAlert();
			wait(1);
			click(RENT_SHAW_PORTAL);
			wait(1);
			inputText(SHAW_PORTAL_SLNO_INPUT, SrlNbr1);
			wait(1);
			click(VALIDATE_SRL_NO);
			wait(1);
			click(RENT_SHAW_PORTAL);
			wait(1);
			inputText(SHAW_PORTAL_SLNO_INPUT, SrlNbr2);
			wait(1);
			click(VALIDATE_SRL_NO);
			wait(1);
			click(RENT_SHAW_PORTAL);
			wait(1);
			inputText(SHAW_PORTAL_SLNO_INPUT, SrlNbr3);
			wait(1);
			click(VALIDATE_SRL_NO);
			wait(1);
			click(RENT_SHAW_PORTAL);
			wait(1);
			inputText(SHAW_PORTAL_SLNO_INPUT, SrlNbr4);
			wait(1);
			click(VALIDATE_SRL_NO);
			wait(1);
			click(RENT_SHAW_PORTAL);
			wait(1);
			inputText(SHAW_PORTAL_SLNO_INPUT, SrlNbr5);
			wait(1);
			click(VALIDATE_SRL_NO);
			wait(1);
			test.log(LogStatus.PASS, "Add TV Hardwares",
					"Added TV Shaw Gateway And Shaw Portal(SMG) Devices Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(HARDWARE_OK_BUTTON);
			wait(1);
			log.debug("Leaving addTVWithNoOfHardwares");
		} catch (Exception e) {
			log.error("Error in addTVWithNoOfHardwares:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addTVBlueCurveHardwares(String SrlNbr1) {
		try {
			log.debug("Entering addTVBlueCurveHardwares");
			wait(1);
			click(TV_HARDWARE_WRENCH);
			wait(1);
			switchToChildAlert();
			wait(1);
			inputText(BLUECURVE_TV_WIRELESS_4KPLAYER418_INPUT, SrlNbr1);
			wait(1);
			click(VALIDATE_SRL_NO);
			wait(1);
			test.log(LogStatus.PASS, "Add TV Hardwares", "Added BlueCurve TV Devices Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(HARDWARE_OK_BUTTON);
			wait(1);
			log.debug("Leaving addTVBlueCurveHardwares");
		} catch (Exception e) {
			log.error("Error in addTVBlueCurveHardwares:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addTVWithShawPortalCustOwnedDevices(String SrlNbr1, String SrlNbr2, String SrlNbr3) {
		try {
			log.debug("Entering addTVWithShawPortalCustOwnedDevices");
			wait(1);
			click(TV_HARDWARE_WRENCH);
			wait(1);
			switchToChildAlert();
			inputText(CUSTOMER_OWNED_INPUT, SrlNbr1);
			wait(1);
			click(EMAIL_VALIDATE);
			wait(1);
			selectFromList(CUSTOMER_OWNED_DROPDOWN, "Moxi Gateway Portal XG1v3 (415/410)");
			wait(2);
			inputText(CUSTOMER_OWNED_INPUT, SrlNbr2);
			wait(1);
			click(EMAIL_VALIDATE);
			wait(1);
			selectFromList(CUSTOMER_OWNED_DROPDOWN, "Moxi Gateway Portal XG1v3 (415/410)");
			wait(2);
			inputText(CUSTOMER_OWNED_INPUT, SrlNbr3);
			wait(1);
			click(EMAIL_VALIDATE);
			wait(1);
			selectFromList(CUSTOMER_OWNED_DROPDOWN, "Moxi Gateway Portal XG1v3 (415/410)");
			wait(2);
			test.log(LogStatus.PASS, "Add TV Hardwares",
					"Added TV Shaw Gateway And Shaw Portal(SMG) Devices Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(HARDWARE_OK_BUTTON);
			wait(1);
			log.debug("Leaving addTVWithShawPortalCustOwnedDevices");
		} catch (Exception e) {
			log.error("Error in addTVWithShawPortalCustOwnedDevices:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addingCustomerProvidedEquipments(String typeOfProduct, String SrlNbr) {
		try {
			log.debug("Entering addingCustomerProvidedEquipments");
			wait(1);
			if (typeOfProduct.equals("tv")) {
				click(TV_HARDWARE_WRENCH);
				wait(1);
				switchToChildAlert();
				inputText(CUSTOMER_OWNED_INPUT, SrlNbr);
				wait(2);
				click(EMAIL_VALIDATE);
				wait(4);
			}
			if (typeOfProduct.equals("internet")) {
				click(INTERNET_HARDWARE);
				wait(1);
				switchToChildAlert();
				inputText(CUSTOMER_OWNED_INPUT, SrlNbr);
				wait(2);
				click(EMAIL_VALIDATE);
				wait(4);
				selectFromList(CUSTOMER_OWNED_DROPDOWN, "Legacy Advanced WiFi Modem (Hitron/Cisco) - 326/327");
				wait(2);
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(OK_BUTTON_TO_CLOSE);
			log.debug("Leaving addingCustomerProvidedEquipments");
		} catch (Exception e) {
			log.error("Error in addingCustomerProvidedEquipments:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void swapConvergedHardware(String serialNum) {
		try {
			log.debug("Entering swapConvergedHardware");
			wait(1);
			click(CONVERGED_HARDWARE_WRENCH);
			wait(5);
			switchToChildAlert();
			wait(1);
			click(CONVERGED_HARDWARE_SWAP);
			wait(10);
			click(TECHNICIAN_RADIO_BUTTON);
			wait(4);
			inputText(CONVERGED_HARDWARE_TECH_ID, "46501");
			wait(3);
			click(CONVERGED_HARDWARE_VALIDATE_TECH_ID);
			wait(2);
			inputText(CONVERGED_HARDWARE_NEW_SERIAL_NO, serialNum);
			wait(2);
			click(CONVERGED_HARDWARE_NEW_SERIAL_NO_VALIDATE);
			do {
				wait(2);
			} while (!isDisplayed(CONVERGED_HARDWARE_NEW_SERIAL_NO_OK_BUTTON));
			test.log(LogStatus.PASS, "Swap Converged Hardware", "Swapped Converged Hardware Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(CONVERGED_HARDWARE_NEW_SERIAL_NO_OK_BUTTON);
			wait(3);
			log.debug("Leaving swapConvergedHardware");
		} catch (Exception e) {
			log.error("Error in swapConvergedHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void swapServiceTVBlueSky(String boxserialNum, String portalSerialNum) {
		try {
			wait(2);
			log.debug("Entering swapServiceTVBlueSky");
			click(TV_HARDWARE_WRENCH);
			wait(5);
			click(SWAP_HARDWARE);
			wait(10);
			click(TECH_RADIO_BTN);
			wait(3);
			inputText(TECHNICIAN_ID, "46501");
			wait(3);
			click(TECH_ID_VALIDATE);
			wait(2);
			inputText(TV_BLUESKY_HARDWARE_INPUT, boxserialNum);
			wait(2);
			click(TV_HARDWARE_VALIDATE_BTN);
			wait(2);
			click(TV_HARDWARE_SWAP);
			wait(8);
			click(PORTAL_TECH_RADIO_BTN);
			wait(7);
			inputText(TECHNICIAN_ID, "46501");
			wait(5);
			click(TECH_ID_VALIDATE);
			wait(2);
			inputText(TV_BLUESKY_HARDWARE_INPUT, portalSerialNum);
			wait(2);
			click(TV_HARDWARE_VALIDATE_BTN);
			wait(2);

			test.log(LogStatus.PASS, "Swap TV Service Bluesky", "Swapped TV Service Bluesky Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(HARDWARE_OK_BUTTON);
			wait(2);
			log.debug("Leaving swapServiceTVBlueSky");
		} catch (Exception e) {
			log.error("Error in swapServiceTVBlueSky:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void swapTvHardwareSrlNo(String firstSrlNum, String secondSrlNum) {
		try {
			log.debug("Entering swapTvHardwareSrlNo");
			click(TV_HARDWARE_WRENCH);
			wait(2);
			click(TV_HARDWARE_SWAP);
			wait(2);
			click(TECH_RADIO_BTN);
			wait(3);
			inputText(TECHNICIAN_ID, "46501");
			wait(3);
			click(TECH_ID_VALIDATE);
			wait(2);
			inputText(TV_BLUESKY_HARDWARE_INPUT, firstSrlNum);
			wait(2);
			click(TV_HARDWARE_VALIDATE_BTN);
			wait(2);
			click(TV_HARDWARE_SWAP);
			wait(2);
			click(TECH_RADIO_BTN);
			wait(2);
			inputText(TECHNICIAN_ID, "46501");
			wait(2);
			click(TECH_ID_VALIDATE);
			wait(2);
			inputText(TV_BLUESKY_HARDWARE_INPUT, secondSrlNum);
			wait(2);
			click(TV_HARDWARE_VALIDATE_BTN);
			wait(2);
			test.log(LogStatus.PASS, "Swap TV Service Bluesky", "Swapped TV Service Bluesky Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(HARDWARE_OK_BUTTON);
			wait(2);
			log.debug("Leaving swapServiceTVBlueSky");
		} catch (Exception e) {
			log.error("Error in swapServiceTVBlueSky:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	/****************************
	 * To-do list Related
	 ****************************/

	public void selectPortOut() {
		try {
			log.debug("Entering selectPortOut");
			wait(1);
			javascript.executeScript("window.scrollBy(0,-600)", "");
			wait(1);
			if (isElementPresent(PHONE_PORT_OUT)) {
				scrollDownAndClick(PHONE_PORT_OUT);
				wait(2);
				switchToChildAlert();
				click(OK_BUTTON_TO_CLOSE);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}

			log.debug("Leaving selectPortOut");
		} catch (Exception e) {
			log.error("Error in selectPortOut:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	/*****************************************
	 * Appointments, dates & reasons Tab
	 *****************************************/

	public void NavigateTechAppointmentTab() {
		try {
			log.debug("Entering tech Appointment Tab");
			wait(2);
			click(APPOINTMENT);
			wait(1);
			int count = 1;
			while (!isElementPresent(SELF_CONNECT_YES_RADIO_BTN) && count <= 10) {
				wait(1);
				count++;
			}
			test.log(LogStatus.PASS, "Entering Tech Appointment", "Entered Tech Appointment Tab Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			log.debug("Leaving tech Appointment Tab");
		} catch (Exception e) {
			log.error("Error in tech Appointment Tab:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void selectInstallationOption(String InstallationOption, String TechAppoinment) {
		try {
			log.debug("Entering selectInstallationOption");
			wait(3);
			click(APPOINTMENT);
			wait(8);
			int count = 1;
			while (!isElementPresent(SELF_CONNECT_YES_RADIO_BTN) && count <= 10) {
				wait(1);
				count++;
			}
			wait(2);
			if (InstallationOption.equals("Self Connect Yes")) {
				click(SELF_CONNECT_YES_RADIO_BTN);
				wait(1);
			}
			javascript.executeScript("window.scrollBy(0,-600)", "");
			wait(2);
			if (InstallationOption.equals("Self Connect No")) {
				click(SELF_CONNECT_NO_RADIO_BTN);
				wait(4);
			}
			if (TechAppoinment.equalsIgnoreCase("No")) {
				wait(2);
				int counter = 1;
				while (!isElementPresent(TECH_APPOINMENT_NO) && counter <= 5) {
					wait(1);
					counter++;
				}
				wait(2);
				click(TECH_APPOINMENT_NO);
				wait(2);
			}
			if (TechAppoinment.equalsIgnoreCase("Yes")) {
				wait(1);
				do {
					wait(1);
				} while (!isDisplayed(TECH_APPOINMENT_YES));
				wait(2);
				click(TECH_APPOINMENT_YES);
				wait(2);
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving selectInstallationOption");

		} catch (Exception e) {
			log.error("Error in selectInstallationOption:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void selectInstallationOptionAndAppointmentDate(String InstallationOption, String TechAppoinment) {
		try {
			log.debug("Entering selectInstallationOption");
			wait(3);
			click(APPOINTMENT);
			wait(8);
			int count = 1;
			while (!isElementPresent(SELF_CONNECT_YES_RADIO_BTN) && count <= 10) {
				wait(1);
				count++;
			}
			wait(2);
			if (InstallationOption.equals("Self Connect Yes")) {
				click(SELF_CONNECT_YES_RADIO_BTN);
				wait(1);
				click(SMS_NBR_CHECK_BOX);
			}
			javascript.executeScript("window.scrollBy(0,-600)", "");
			wait(2);
			if (InstallationOption.equals("Self Connect No")) {
				click(SELF_CONNECT_NO_RADIO_BTN);
				wait(4);
			}
			if (TechAppoinment.equalsIgnoreCase("No")) {
				wait(2);
				int counter = 1;
				while (!isElementPresent(TECH_APPOINMENT_NO) && counter <= 5) {
					wait(1);
					counter++;
				}
				wait(2);
				click(TECH_APPOINMENT_NO);
				wait(2);
			}
			if (TechAppoinment.equalsIgnoreCase("Yes")) {
				wait(1);
				do {
					wait(1);
				} while (!isDisplayed(TECH_APPOINMENT_YES));
				wait(2);
				click(TECH_APPOINMENT_YES);
				wait(2);
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving selectInstallationOption");

		} catch (Exception e) {
			log.error("Error in selectInstallationOption:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void techActivationOption(String Order) {
		try {
			log.debug("Entering techActivationOption");
			wait(2);
			if (Order.equals("On confirmation")) {
				click(ACTIVATE_OPTIONON_CONFIRMATION);
				wait(2);
				test.log(LogStatus.PASS, "Select activation As On confirmation:",
						"Note: Service Activation will happen on customer confirmation only");
			}
			if (Order.equals("On a date")) {
				click(ACTIVATE_OPTION_ONDATE);
				wait(2);
				test.log(LogStatus.PASS, "select activation As On A Date:",
						"The take effect date is defaulted to the current date. If the date is not changed, the order will take effect immediately upon completion. If a different changes effect date is selected, the order will take effect on the selected date");
			}
			if (isElementPresent(MOCA_FILTER_RADIO_BTN)) {
				wait(1);
				click(MOCA_FILTER_RADIO_BTN);
				wait(1);
			}
			test.log(LogStatus.PASS, "Add Tech Appointment", "Added Tech Appointment Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			log.debug("Leaving techActivationOption");
		} catch (Exception e) {
			log.error("Error in techActivationOption:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void techAppointmentNo(String Order, String MailAddress, String PremiseMoveActivationType) {
		try {
			log.debug("Entering techAppointmentNo");
			wait(2);
			if (Order.equals("On confirmation")) {
				click(ACTIVATE_OPTIONON_CONFIRMATION);
				wait(2);
				test.log(LogStatus.PASS, "Select activation As On confirmation:",
						"Note: Service Activation will happen on customer confirmation only");
			}
			if (Order.equals("On a date")) {
				click(ACTIVATE_OPTION_ONDATE);
				wait(2);
				test.log(LogStatus.PASS, "select activation As On A Date:",
						"The take effect date is defaulted to the current date. If the date is not changed, the order will take effect immediately upon completion. If a different changes effect date is selected, the order will take effect on the selected date");
			}
			if (Order.equals("Retail Pickup")) {
				wait(1);
				click(RETAIL_PICKUP_RADIO_BTN);
				wait(2);
				click(RETAIL_PICKUP_DROPDOWN);
				wait(2);
				click(RETAIL_PICKUP_LIST_SELECTION);
				wait(1);
				test.log(LogStatus.PASS, "Self Connect Yes", "Added Self Connect Yes With Retail Pickup Successfully");
			}
			if (Order.equals("Mailout")) {
				wait(2);
				click(MAIL_OUT_RADIO_BTN);
				wait(1);
				test.log(LogStatus.PASS, "Retail Pickup Validation Message:", " This order qualifies for Self Connect");

				if (MailAddress.equals("Service Location")) {
					wait(1);
					click(SERVICE_LOCATION_ADDRESS);
					wait(1);
					click(OPT_OUT);
				}
				if (MailAddress.equals("Special shipping")) {
					wait(4);
					click(SPECIAL_SHIPPING_ADDRESS_BTN);
					wait(9);
					click(SPECIAL_SHIPPING_ADDRESSL1);
					wait(9);
					getWebElement(SPECIAL_SHIPPING_ADDRESSL1).sendKeys("217 Birch Ave");
//					getWebElement(SPECIAL_SHIPPING_ADDRESSL1).sendKeys("1-12 Albert Ave, Etobicoke, ON, M8V 2L4");
//					selectFromList(SPECIAL_SHIPPING_ADDRESSL1, "1-12 Albert Ave, Etobicoke, ON, M8V 2L4");
//					 inputText(SPECIAL_SHIPPING_ADDRESSL1, "217 Birch Ave");
					wait(5);
//					 added code to press below key.

//					  Robot robot = new Robot(); robot.keyPress(1);

					wait(2);
					click(SPECIAL_SHIPPING_CITY_TOWN);
					wait(1);
					getWebElement(SPECIAL_SHIPPING_CITY_TOWN).sendKeys("Canada");
					wait(1);
					click(SPECIAL_SHIPPING_ADDRESS_CODE);
					wait(1);
					getWebElement(SPECIAL_SHIPPING_ADDRESS_CODE).sendKeys("V0K 2E3");
					wait(1);
					// added new
					getWebElement(BUZZER_CODE).sendKeys("1234");
					wait(1);
					click(OPT_OUT);
					// WebElement e1 = getWebElement(CONTACT_NUMBER);
					// jse.executeScript("arguments[0].value='(987) 654-3212';", e1);
					wait(1);

				}
				click(CO_FOR_SHIPPING);
				wait(1);
				getWebElement(CO_FOR_SHIPPING).sendKeys("Integration Test");
				wait(1);
				inputText(DELIVERY_INSTRUCTION_INPUT, "## ** InTEGratIoN  #* TeSt *#88##");
				wait(1);
				test.log(LogStatus.PASS, "Self Connect Yes", "Added Self Connect Yes With Mailout Successfully");
			}

			if (Order.equals("Existing HW Premise Move")) {
				click(EXISTING_HW_PREMISEMOVE_RADIO_BTN);
				wait(2);
				test.log(LogStatus.PASS, "Retail Pickup Validation Message:", " This order qualifies for Self Connect");
				test.log(LogStatus.PASS, "Self Connect Yes",
						"Added Self Connect Yes With Existing H/W (Premise Move) Successfully");

				if (PremiseMoveActivationType.equals("On confirmation")) {
					click(ACTIVATE_OPTIONON_CONFIRMATION);
					wait(2);
					String RetailPickUpValidationMsg = getText(RETAILPICKUP_VALIDATION_MSG);
					Assert.assertEquals("This order qualifies for Self Connect", RetailPickUpValidationMsg);
					test.log(LogStatus.PASS, "Retail Pickup Validation Message:",
							"This order qualifies for Self Connect");
					String OnConfirmationMsg = getText(ACTIVCATION_ON_CNFM_MSG);
					Assert.assertEquals("Note: Service Activation will happen on customer confirmation only",
							OnConfirmationMsg);
					test.log(LogStatus.PASS, "Select activation As On confirmation:",
							"Note: Service Activation will happen on customer confirmation only");
				}
				if (PremiseMoveActivationType.equals("On a date")) {
					click(ACTIVATE_OPTION_ONDATE);
					wait(2);
					String RetailPickUpValidationMsg = getText(RETAILPICKUP_VALIDATION_MSG);
					Assert.assertEquals("This order qualifies for Self Connect", RetailPickUpValidationMsg);
					test.log(LogStatus.PASS, "Retail Pickup Validation Message:",
							"This order qualifies for Self Connect");
				}
			}
			if (Order.equals("Hardware Drop Off")) {
				click(HARDWARE_DROPOFF_RADIO_BTN);
				wait(2);
				String RetailPickUpValidationMsg = getText(RETAILPICKUP_VALIDATION_MSG);
				Assert.assertEquals("This order qualifies for Self Connect", RetailPickUpValidationMsg);
				test.log(LogStatus.PASS, "Retail Pickup Validation Message:", " This order qualifies for Self Connect");
				String OnConfirmationMsg = getText(ACTIVCATION_ON_CNFM_MSG);
				Assert.assertEquals("Note: Service Activation will happen on customer confirmation only",
						OnConfirmationMsg);
				test.log(LogStatus.PASS, "Select Hardware Drop Off (In Home Only):",
						"Note: Service Activation will happen on customer confirmation only");
				wait(2);
			}
			if (isElementPresent(MOCA_FILTER_RADIO_BTN)) {
				wait(1);
				click(MOCA_FILTER_RADIO_BTN);
				wait(1);
			}
			inputText(CHANGEAUTHORIZEDBY, "Integration Test");
			test.log(LogStatus.PASS, "Add Tech Appointment", "Added Tech Appointment Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			log.debug("Leaving techAppointmentNo");
		} catch (Exception e) {
			log.error("Error in techAppointmentNo:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void techAppoinmentYes(String SMSNumber, String ForceAppointment) {
		try {
			log.debug("Entering techAppoinmentYes");
			wait(2);
			if (SMSNumber.equals("Yes")) {
				WebElement e1 = getWebElement(SMS_NBR_FIELD);
				jse.executeScript("arguments[0].value='(425) 763-9510';", e1);
				wait(2);
				click(SMS_NBR_FIELD);
				wait(1);
			} else {
				wait(2);
				click(SMS_NBR_CHECK_BOX);
				wait(1);
			}
			click(TECH_APPOINTMENT_DATE);
			wait(1);
			if (ForceAppointment.equals("Yes")) {
				click(FORCE_APPOINTMENT_CHECKBOX);
				wait(2);
				click(CLICK_CALENDAR);
				wait(2);
				getWebElement(CLICK_CALENDAR).sendKeys(Keys.ENTER);
				wait(2);
			}
			if (isElementPresent(MOCA_FILTER_RADIO_BTN)) {
				wait(1);
				click(MOCA_FILTER_RADIO_BTN);
				wait(1);
			}
			inputText(CHANGEAUTHORIZEDBY, "Integration Test");
			wait(1);
			test.log(LogStatus.PASS, "Add Tech Appointment Yes", "Added Tech Appointment Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving techAppoinmentYes");
		} catch (Exception e) {
			log.error("Error in techAppoinmentYes:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void techAppoinmentYesFutureDate(String SMSNumber, String ForceAppointment) {
		try {
			log.debug("Entering techAppoinmentYes");
			wait(2);
			click(APPOINTMENT);
			wait(5);
			if (SMSNumber.equals("Yes")) {
				WebElement e1 = getWebElement(SMS_NBR_FIELD);
				jse.executeScript("arguments[0].value='(425) 763-9510';", e1);
				wait(2);
				click(SMS_NBR_FIELD);
				wait(1);
			} else {
				wait(2);
				click(SMS_NBR_CHECK_BOX);
				wait(1);
			}
			click(CLICK_CALENDAR);
			wait(2);
			if (!datePicker2().equals("1")) {
				getWebElement(ACTIVATION_DATE).findElement(By.xpath("//*[text()='" + datePicker2() + "']")).click();
			} else {
				wait(2);
				click(NEXT_MONTH_ARROW);
				wait(2);
				click(FIRST_DATE);
				wait(1);
			}
			click(SCHEDULE_ANOTHER_APPOINTMENT);
			wait(5);
			click(TECH_APPOINTMENT_DATE);
			wait(1);
			if (ForceAppointment.equals("Yes")) {
				click(FORCE_APPOINTMENT_CHECKBOX);
				wait(2);
				click(CLICK_CALENDAR);
				wait(2);
				getWebElement(CLICK_CALENDAR).sendKeys(Keys.ENTER);
				wait(2);
			}
			if (isElementPresent(MOCA_FILTER_RADIO_BTN)) {
				wait(1);
				click(MOCA_FILTER_RADIO_BTN);
				wait(1);
			}
			inputText(CHANGEAUTHORIZEDBY, "Integration Test");
			wait(1);
			test.log(LogStatus.PASS, "Add Tech Appointment Yes", "Added Tech Appointment Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving techAppoinmentYes");
		} catch (Exception e) {
			log.error("Error in techAppoinmentYes:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void techAppointmentNoCurrentDate(String ActivateOption, boolean futureDate) {
		try {
			log.debug("Entering techAppointmentNoCurrentDate");
			wait(1);
			if (ActivateOption.equals("Yes")) {
				wait(1);
				click(ACTIVATE_OPTION_ONDATE);
				wait(2);
			}
			if (isElementPresent(MOCA_FILTER_RADIO_BTN)) {
				wait(1);
				click(MOCA_FILTER_RADIO_BTN);
				wait(1);
			}
			click(CLICK_CALENDAR);
			wait(2);
			if (futureDate) {
				click(NEXT_MONTH_SNOWBIRD);
				wait(2);
				click(FIRST_DATE);
			} else {
				click(PREV_MONTH_SNOWBIRD);
				wait(2);
				getWebElement(CLICK_CALENDAR).findElement(By.xpath("//*[text()='" + returnCurrentDate() + "']"))
						.click();
			}
			wait(2);
			inputText(CHANGEAUTHORIZEDBY, "Integration Test");
			test.log(LogStatus.PASS, "Add Tech Appointment With CurrentDate",
					"Added Tech Appointment With CurrentDate Successfully");
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			log.debug("Leaving techAppointmentNoCurrentDate");
		} catch (Exception e) {
			log.error("Error in techAppointmentNoCurrentDate:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void techAppointmentTextValidation() {
		try {
			log.debug("Appointmentdate text validation");

			String remindertext = getText(REMINDER_TEXT);
			if (remindertext.equals("We cannot keep the booked appointment.\r\n"
					+ "Schedule another appointment from the list below.")) {

				test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				test.log(LogStatus.INFO, "Appointment Validation For Port Phone: " + remindertext);
			}
			log.debug("Appointmentdate text validation");
		} catch (Exception e) {
			log.error("Error in Appointmentdate text validation:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(true, e.getMessage());
		}
	}

	public void navigateToAgreementDetailsTab() {
		try {
			log.debug("Entering navigateToAgreementDetailsTab");
			wait(1);
			click(AGREEMENT_DETAIL_TAB);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToAgreementDetailsTab");
		} catch (Exception e) {
			log.error("Error in navigateToAgreementDetailsTab:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	/************************************
	 * changes Authorized By Tab
	 ************************************/

	public void enteringChangesAuthorizedBy() {
		try {
			wait(2);
			log.debug("Entering enteringChangesAuthorizedBy");
			wait(1);
			javascript.executeScript("window.scrollBy(0,-600)", "");
			inputText(CHANGEAUTHORIZEDBY, "Integration Test");
			log.debug("Leaving enteringChangesAuthorizedBy");
		} catch (Exception e) {
			log.error("Error in enteringChangesAuthorizedBy:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void changesAuthorizedBy() {
		try {
			log.debug("Entering changesAuthorizedBy");
			wait(2);
			click(APPOINTMENT);
			wait(10);
			inputText(CHANGEAUTHORIZEDBY, "Integration Test");
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving changesAuthorizedBy");
		} catch (Exception e) {
			log.error("Error in changesAuthorizedBy:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void mocaFilterSelecttion(String radio) {
		try {
			if (radio == "Yes") {
				log.debug("Entering mocaFilterSelecttion");
				wait(1);
				click(MOCAFILTERYES);
				wait(1);
			} else {
				wait(1);
				click(MOCAFILTERNO);
				wait(1);
			}

			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving changesAuthorizedBy");
		} catch (Exception e) {
			log.error("Error in mocaFilterSelecttion:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void techAppointmentWithCurrentDate() {
		try {
			log.debug("Entering techAppointmentCurrentDate");
			wait(2);
			click(APPOINTMENT);
			wait(10);
			click(SELF_CONNECT_NO_RADIO_BTN);
			wait(1);
			do {
				wait(1);
			} while (!isDisplayed(TECH_APPOINMENT_YES));
			wait(1);
			click(TECH_APPOINMENT_YES);
			wait(3);
			WebElement e1 = getWebElement(SMS_NBR_FIELD);
			jse.executeScript("arguments[0].value='(209) 465-7238';", e1);
			wait(2);
			click(FORCE_APPOINTMENT_CHECKBOX);
			wait(2);
			click(CLICK_CALENDAR);
			wait(2);
			getWebElement(CLICK_CALENDAR).sendKeys(Keys.ENTER);
			wait(1);
			if (isElementPresent(MOCA_FILTER_RADIO_BTN)) {
				wait(1);
				click(MOCA_FILTER_RADIO_BTN);
				wait(1);
			}
			inputText(CHANGEAUTHORIZEDBY, "Integration Test");
			test.log(LogStatus.PASS, "Add Tech Appointment With CurrentDate",
					"Added Tech Appointment With CurrentDate Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			log.debug("Leaving techAppointmentCurrentDate");
		} catch (Exception e) {
			log.error("Error in techAppointmentCurrentDate:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void forceAppointmentWithFutureDate(String Order) {
		try {
			log.debug("Entering forceAppointmentWithFutureDate");
			wait(1);
			click(APPOINTMENT);
			wait(7);
			if (Order.equals("Manual Appoinment")) {
				click(FORCE_APPOINTMENT_CHECKBOX);
				wait(2);
			}
			click(CLICK_CALENDAR);
			wait(1);
			if (!datePicker().equals("1")) {
				getWebElement(CLICK_CALENDAR).findElement(By.xpath("//*[text()='" + datePicker() + "']")).click();
			} else {
				wait(2);
				click(NEXT_MONTH_ARROW);
				wait(2);
				click(FIRST_DATE);
				wait(1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			test.log(LogStatus.PASS, "Add Tech Appointment with future date",
					"Added Tech Appointment with future date Successfully");

			log.debug("Leaving forceAppointmentWithFutureDate");
		} catch (Exception e) {
			log.error("Error in forceAppointmentWithFutureDate:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void techAppointmentWithGAP() {
		try {
			log.debug("Entering techAppointmentWithGAP");
			wait(6);
			click(PREMISE_MOVE_SERVICE_NUMBER);
			wait(10);
			click(PREMISE_MOVE_DISCONNECTION_DATE);
			wait(5);
			if (!datePicker().equals("1")) {
				getWebElement(PREMISE_MOVE_DISCONNECTION_DATE)
						.findElement(By.xpath("//*[text()='" + datePicker() + "']")).click();
			} else {
				wait(2);
				click(NEXT_MONTH_ARROW);
				wait(2);
				click(FIRST_DATE);
				wait(1);
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			test.log(LogStatus.PASS, "Add Tech Appointment WithGap", "Added Tech Appointment WithGap Successfully");
			log.debug("Leaving techAppointmentWithGAP");
		} catch (Exception e) {
			log.error("Error in techAppointmentWithGAP:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void techAppointmentWithGAP1() {
		try {
			log.debug("Entering techAppointmentWithGAP");
			wait(1);
			click(PREMISE_MOVE_SERVICE_NUMBER);
			wait(2);
			click(PREMISE_MOVE_DISCONNECTION_DATE);
			wait(2);
			if (!datePicker1().equals("1")) {
				getWebElement(PREMISE_MOVE_DISCONNECTION_DATE)
						.findElement(By.xpath("//*[text()='" + datePicker1() + "']")).click();
			} else {
				wait(2);
				click(NEXT_MONTH_ARROW);
				wait(2);
				click(FIRST_DATE);
				wait(1);
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			test.log(LogStatus.PASS, "Add Tech Appointment WithGap", "Added Tech Appointment WithGap Successfully");
			log.debug("Leaving techAppointmentWithGAP");
		} catch (Exception e) {
			log.error("Error in techAppointmentWithGAP:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void selectCalenderCurrentDate() {
		try {
			log.debug("Entering selectCalenderCurrentDate");
			wait(2);
			click(APPOINTMENT);
			wait(9);
			// click(SELF_CONNECT_NO_RADIO_BTN);
			// wait(2);
			click(CLICK_CALENDAR);
			wait(2);
			getWebElement(CLICK_CALENDAR).sendKeys(Keys.ENTER);
			wait(2);
			inputText(CHANGEAUTHORIZEDBY, "Integration Test");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			log.debug("Leaving selectCalenderCurrentDate");
		} catch (Exception e) {
			log.error("Error in selectCalenderCurrentDate:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void generateAgreementForTech(String generateAgreementForTech) {
		try {
			log.debug("Entering generateAgreementForTech");
			wait(2);
			if (generateAgreementForTech.equalsIgnoreCase("No")) {
				wait(1);
				click(GENERATE_AGREEMENT_NO);
				wait(1);
			}
			if (generateAgreementForTech.equalsIgnoreCase("Yes")) {
				wait(1);
				click(GENERATE_AGREEMENT_YES);
				wait(1);
			}
			log.debug("Leaving generateAgreementForTech");
		} catch (Exception e) {
			log.error("Error in generateAgreementForTech:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public String datePicker() {
		String newDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("d");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		newDate = sdf.format(cal.getTime());
		return newDate;
	}

	public String datePicker1() {
		String newDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("d");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 2);
		newDate = sdf.format(cal.getTime());
		return newDate;
	}

	public String datePicker2() {
		String newDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("d");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		newDate = sdf.format(cal.getTime());
		return newDate;
	}

	public String returnCurrentDate() {
		String newDate = "";
		SimpleDateFormat sdf = new SimpleDateFormat("d");
		Calendar cal = Calendar.getInstance();
		newDate = sdf.format(cal.getTime());
		return newDate;
	}

	// @author ${RAMESH REDDY K}
	public String getAccountNumber() {
		return getText(ACCOUNTNUMBER);
	}

	// @author ${RAMESH REDDY K}
	public String getOrderNumber() {
		return getText(ORDERNUMBER);
	}

	public void reviewAndFinishOrder() throws Exception {
		String orderStatus = "";
		try {
			log.debug("Entering reviewAndFinishOrder");
			wait(4);
			click(REVIEW);
			wait(5);
			javascript.executeScript("window.scrollBy(0,-800)", "");
			wait(4);
			if (isElementPresent(SEND_SIMMARY_RADIO_BTN_NO)) {
				click(SEND_SIMMARY_RADIO_BTN_NO);
			}
			wait(10);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			int count = 1;
			while (!isElementPresent(FINISH) && count <= 15) {
				wait(1);
				count++;
			}
			click(FINISH);
			wait(20);

		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.fail();
			wait(1);
		}
		// added a code to handle the pop-up

		try {
			if (isDisplayed(EMAIL_OK_BUTTON)) {
				click(EMAIL_OK_BUTTON);
				// wait(2);
				wait(8);
			}
		} catch (Exception e) {
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
		}
		try {
			int counter = 1;
			int count = 1;
			while (!isElementPresent(FINISHDISABLE) && count <= 35) {
				wait(1);
				count++;
			}
			orderStatus = Utility.getOrderStatusString(driver, ORDER_WEB_TABLE.getValue());
			while (!orderStatus.equalsIgnoreCase("Started") && counter <= 60) {
				wait(1);
				counter++;
				orderStatus = Utility.getOrderStatusString(driver, ORDER_WEB_TABLE.getValue());
			}
		} catch (Exception e) {
			log.debug("Required page could not load in 1 Minuite");
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.fail();
		}
		Assert.assertEquals(orderStatus, "Started", "Order Status is not Correct");
		test.log(LogStatus.PASS, "Order creation", "Order Created Successfully with AcctNbr: " + getAccountNumber());
		test.log(LogStatus.PASS, "Order creation", "Order Created Successfully with OrderNbr: " + getOrderNumber());
		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving reviewAndFinishOrder");
	}

	public void enterInstallationFee(String FeeRequired) {
		try {
			log.debug("Entering enterInstallationFee");
			wait(1);
			if (FeeRequired.equals("1.00")) {
				clickAndEnterText(INSTALLATIONFEE, "1.00");
				wait(1);
			}
			if (FeeRequired.equals("0.00")) {
				clickAndEnterText(INSTALLATIONFEE, "0.00");
				wait(1);
			}
			test.log(LogStatus.PASS, "Add Installation Fee", "Added Installation Fee Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving enterInstallationFee");
		} catch (Exception e) {
			log.error("Error in enterInstallationFee:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void selectTodo() {
		try {
			log.debug("Entering selectTodo");
			wait(1);
			javascript.executeScript("window.scrollBy(0,-600)", "");
//			click(TODO_LIST_CHECKBOX);
			wait(2);
			if (isElementPresent(TODO_LIST_CHECKBOX)) {
				wait(1);
				javascript.executeScript("window.scrollBy(0,-600)", "");
				click(TODO_LIST_CHECKBOX);
				wait(1);
			}
			log.debug("Leaving selectTodo");
		} catch (Exception e) {
			log.error("Error in selectTodo:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void selectTodoListCheckBox() {
		try {
			log.debug("Entering selectTodoListCheckBox");
			wait(1);
			javascript.executeScript("window.scrollBy(0,-600)", "");
			wait(1);
			if (isElementPresent(TODO_LIST_CHECKBOX)) {
				click(TODO_LIST_CHECKBOX);
			}
			wait(1);
			log.debug("Leaving selectTodoListCheckBox");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		} catch (Exception e) {
			log.error("Error in selectTodoListCheckBox:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void selectDefaultInstallationCheckBox() {
		try {
			log.debug("Entering selectDefaultInstallationCheckBox");
			wait(1);
			click(ADD_SERVICES_AND_FEATURES_TAB);
			int counter = 1;
			while (!isElementPresent(PROMOTIONS_LINK) && counter <= 10) {
				wait(1);
				counter++;
			}
			wait(1);
			if (isElementPresent(TODO_LIST_CHECKBOX)) {

				scrollDownToElement(TODO_LIST_CHECKBOX);
				wait(1);
				click(TODO_LIST_CHECKBOX);
			}
			wait(1);
			test.log(LogStatus.INFO, "Defult Todo list: ", "Selected default Todo msg successfully");
			log.debug("Leaving selectDefaultInstallationCheckBox");
		} catch (Exception e) {
			log.error("Error in selectDefaultInstallationCheckBox:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public boolean isOrderCreated() {
		try {
			log.debug("Entering isOrderCreated");
			wait(1);
			if (isDisplayed(ORDER_MGMT_STAT))
				isOrderCreated = true;
			log.debug("Leaving isOrderCreated");
		} catch (Exception e) {
			log.error("Error in isOrderCreated:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return isOrderCreated;
	}

	public void navigateToCustOrderPage(String newTabExists, String acctNbr) {
		try {
			log.debug("Entering navigateToCustOrderPage");
			String custOrderPageUrl = Utility.getValueFromPropertyFile("basepage_url")
					+ "/oe.newCustomerDesktop.nc?accountId=1" + acctNbr + "&locationId=221";
			if (newTabExists.equalsIgnoreCase("true"))
				switchSecondNewTab();
			navigate(custOrderPageUrl);
			wait(1);
			test.log(LogStatus.INFO, "Server OE URL: ",
					"<a href=" + custOrderPageUrl + " > " + custOrderPageUrl + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			log.debug("Leaving navigateToCustOrderPage");
		} catch (Exception e) {
			log.error("Error in navigateToCustOrderPage:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToOperations(String SnowBoardDate) {
		try {
			log.debug("Entering navigateToOperations");
			click(OPERATIONS_TAB);
			wait(2);
			click(SNOWBIRD);
			wait(2);
			if (isElementPresent(SKIP)) {
				wait(2);
				click(SKIP);
			}
			wait(2);
			switchToChildAlert();
			click(ENABLE_SNOWBIRD);
			wait(5);
			if (SnowBoardDate.equalsIgnoreCase("Current Date")) {
				click(SNOWBIRD_START_DATE);
				wait(5);
				getWebElement(SNOWBIRD_START_DATE).sendKeys(Keys.ENTER);
				wait(4);
				click(SNOWBIRD_END_DATE);
				wait(2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				click(NEXT_MONTH_SNOWBIRD);
				wait(2);
				click(NEXT_MONTH_SNOWBIRD);
				wait(2);
				click(FIRST_DATE);
				wait(2);
			}
			if (SnowBoardDate.equalsIgnoreCase("Future Date")) {
				wait(1);
				click(SNOWBIRD_START_DATE);
				wait(2);
				if (!datePicker().equals("1")) {
					getWebElement(SNOWBIRD_START_DATE).findElement(By.xpath("//*[text()='" + datePicker() + "']"))
							.click();
					wait(2);
					click(SNOWBIRD_END_DATE);
					wait(2);
					click(NEXT_MONTH_SNOWBIRD);
					wait(2);
					click(NEXT_MONTH_SNOWBIRD);
					wait(2);
					click(FIRST_DATE);
					wait(2);
				}
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(OK_BUTTON_TO_CLOSE);
			wait(5);

		} catch (Exception e) {
			log.error("Error in navigateToOperations:" + e.getMessage());
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToChangeSnowbirdEndDateToTodayDate() {
		try {
			log.debug("Entering navigateToOperations");
			click(OPERATIONS_TAB);
			wait(2);
			click(SNOWBIRD);
			wait(2);
			switchToChildAlert();
			wait(1);
			click(SNOWBIRD_END_DATE);
			wait(2);
			click(PREVIOUS_MONTH_ARROW);
			wait(2);
			click(PREVIOUS_MONTH_ARROW);
			wait(2);
			getWebElement(PREVIOUS_MONTH_ARROW).findElement(By.xpath("//*[text()='" + returnCurrentDate() + "']"))
					.click();
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(OK_BUTTON_TO_CLOSE);
			wait(3);

		} catch (Exception e) {
			log.error("Error in navigateToOperations:" + e.getMessage());
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void tempSuspendTriplePlay() {
		try {
			log.debug("Entering tempSuspendTriplePlay");
			wait(5);
			selectFromList(PHONE_PROD_LIST, "Temporary Suspend Phone");
			wait(9);
			click(TEMP_SUSPEND_INTERNET);
			wait(8);
			click(TEMP_SUSPEND_TV);
			wait(6);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		} catch (Exception e) {
			log.error("Error in tempSuspendTriplePlay:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void tempDisconnectLOBs() {
		try {
			log.debug("Entering tempDisconnectTriplePlay");
			wait(3);
			selectFromList(PHONE_PROD_LIST, "Not selected");
			wait(2);
			click(INTERNET_NOT_SELECTED);
			wait(4);
			click(TV_NOT_SELECTED);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		} catch (Exception e) {
			log.error("Error in tempDisconnectTriplePlay:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void reinstatePastResource(String Reinstate) {
		try {
			log.debug("Entering reinstatePastResource");

			if (Reinstate.equalsIgnoreCase("Past Resources")) {
				wait(1);
				click(REINSTATE_PAST_RESOURCE);
				wait(2);
			}
			if (Reinstate.equalsIgnoreCase("Start New Order")) {
				wait(1);
				click(REINSTATE_START_NEW_ORDER);
				wait(3);
			}
			if (Reinstate.equalsIgnoreCase("Retrive order information")) {
				wait(1);
				click(RETRIVE_ORDER_INFORMATION);
				wait(2);
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		} catch (Exception e) {
			log.error("Error in reinstatePastResource:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void cancelOrder() {
		try {
			log.debug("Entering cancelOrder");
			click(REVIEW);
			wait(2);
			click(CANCEL_ORDER);
			wait(1);
			switchToChildAlert();
			wait(2);
			click(OK_BTN_TO_CLOSE);
			wait(2);
			click(CONFIRM_PRODUCT_REMOVE);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			do {
				wait(1);
			} while (!isElementPresent(OK_BTN_TO_CLOSE));
			wait(1);
			click(OK_BTN_TO_CLOSE);
			wait(1);
			test.log(LogStatus.PASS, "Cancel Order", "Cancelled Order Successfully");
			log.debug("Leaving cancelOrder");
		} catch (Exception e) {
			log.error("Error in cancelOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void cancelOrderTNNumber(String Link, String Reconfirm) {
		try {
			log.debug("Entering cancelOrderTNNumber");
			click(REVIEW);
			wait(2);
			if (Link.equals("Cancle Order")) {
				click(CANCEL_ORDER);
				wait(2);
				click(OK_BUTTON_TO_CLOSE);
				wait(2);
			}
			if (Link.equals("Cancle Transfer")) {
				click(CANCEL_TRANSFER);
				wait(2);
				click(OK_BUTTON_TO_CLOSE);
				wait(2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				switchToChildAlert();
			}
			if (Reconfirm.equals("Yes")) {
				wait(2);
				click(CONFIRM_PRODUCT_REMOVE);
				wait(1);
				test.log(LogStatus.PASS, "Cancel Order", "Cancelled Order Successfully");
			}
			if (Reconfirm.equals("CheckBox")) {
				wait(1);
				click(REASSIGN_CHECKBOX);
				wait(2);
				click(OK_BUTTON_TO_CLOSE);
				wait(2);
				do {
					wait(2);
				} while (!isDisplayed(OK_BTN_TO_CLOSE));
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				switchToChildAlert();
				click(OK_BTN_TO_CLOSE);
				wait(2);
				test.log(LogStatus.PASS, "Cancel Order", "Cancelled Order Successfully");
			}
			log.debug("Leaving cancelOrderTNNumber");
		} catch (Exception e) {
			log.error("Error in cancelOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void swapPhoneHardware(String swapPhonehardwareSerialNbr) {
		try {
			log.debug("Entering swapPhoneHardware");
			wait(2);
			click(PHONEHARDWARE);
			wait(1);
			switchToChildAlert();
			wait(1);
			click(SWAP_HARDWARE);
			wait(4);
			click(TECH_RADIO_BTN);
			wait(4);
			inputText(TECHNICIAN_ID, "46520");
			wait(2);
			click(VALIDATE_TECH_ID);
			wait(5);
			inputText(SWAP_SL_NO, swapPhonehardwareSerialNbr);
			wait(2);
			click(VALIDATE_SWAP_SLNO);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			do {
				wait(1);
			} while (!isDisplayed(HARDWARE_OK_BUTTON));
			wait(1);
			click(HARDWARE_OK_BUTTON);
			wait(2);
			test.log(LogStatus.PASS, "Swap Phone Hardware", "Swapped Phone Hardware Successfully");
			log.debug("Leaving swapPhoneHardware");
			wait(2);
		} catch (Exception e) {
			log.error("Error in swapPhoneHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void swapInternetHardware(String swapInternetHardwareSrlNbr, String digitalChannel) {
		try {
			log.debug("Entering swapHitronInternetHardware");
			wait(2);
			if (digitalChannel.equalsIgnoreCase("Fibre+ 150")) {
				do {
					wait(1);
				} while (!isDisplayed(INTERNET_HARDWARE));
				wait(1);
				click(INTERNET_HARDWARE);
				wait(2);
			}
			if (digitalChannel.equalsIgnoreCase("Fibre+ 75")) {
				do {
					wait(1);
				} while (!isDisplayed(INTERNET_HARDWARE));
				wait(1);
				click(INTERNET_HARDWARE);
				wait(2);
			}
			switchToChildAlert();
			wait(1);
			click(SWAP_HARDWARE);
			wait(4);
			click(TECH_RADIO_BTN);
			wait(2);
			inputText(TECHNICIAN_ID, "46529");
			wait(2);
			click(VALIDATE_TECH_ID);
			wait(2);
			inputText(SWAP_SL_NO, swapInternetHardwareSrlNbr);
			wait(2);
			click(VALIDATE_SWAP_SLNO);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			do {
				wait(1);
			} while (!isDisplayed(HARDWARE_OK_BUTTON));
			wait(1);
			click(HARDWARE_OK_BUTTON);
			test.log(LogStatus.PASS, "Swap Hitron Internet Hardware", "Swapped Hitron Internet Hardware Successfully");
			log.debug("Leaving swapHitronInternetHardware");
		} catch (Exception e) {
			log.error("Error in swapHitronInternetHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void swapTVDCXHardware(String swapTVDCXHardwareSrlNbr) {
		try {
			log.debug("Entering swapTVDCXHardware");
			wait(2);
			click(TV_DCX_WRENCH_BTN);
			wait(2);
			switchToChildAlert();
			click(SWAP_HARDWARE);
			wait(2);
			click(TECH_RADIO_BTN);
			wait(2);
			inputText(TECHNICIAN_ID, "46501");
			wait(2);
			click(VALIDATE_TECH_ID);
			wait(2);
			inputText(SWAP_SL_NO, swapTVDCXHardwareSrlNbr);
			wait(2);
			click(VALIDATE_SWAP_SLNO);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			do {
				wait(2);
			} while (!isDisplayed(HARDWARE_OK_BUTTON));
			wait(1);
			click(HARDWARE_OK_BUTTON);
			test.log(LogStatus.PASS, "Swap TV DCX Hardware", "Swapped TV DCX Hardware Successfully");
			log.debug("Leaving swapTVDCXHardware");
		} catch (Exception e) {
			log.error("Error in swapTVDCXHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToOeStubServerToFail(String newTabExists, String value) {
		try {
			log.debug("Entering navigateToOeStubServerToFail");
			if (newTabExists == "true")
				switchSecondNewTab();
			wait(1);
			String StubURL = Utility.getValueFromPropertyFile("oestub_url");
			navigate(StubURL);
			wait(1);
			test.log(LogStatus.INFO, "Open Stub URL: ", "<a href=" + StubURL + " > " + StubURL + " </a>");
			if (value.equalsIgnoreCase("Hpsa")) {
				click(HPSA);
				wait(1);
			}
			if (value.equalsIgnoreCase("Field Force Management")) {
				click(FIELD_FORCE_MANAGEMENT);
				wait(1);
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			log.debug("Leaving navigateToOeStubServerToFail");
		} catch (Exception e) {
			log.error("Error in navigateToOeStubServerToFail:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateFirstToOeStubServer() {
		try {
			log.debug("Entering navigateFirstToOeStubServer");
			driver.manage().window().maximize();
			String StubURL = Utility.getValueFromPropertyFile("oestub_url");
			navigate(StubURL);
			wait(1);
			test.log(LogStatus.INFO, "Stub URL: ", "<a href=" + StubURL + " > " + StubURL + " </a>");
			wait(1);
			scrollDownAndClick(HPSA);
			wait(1);
			scrollDownToElement(CONVERGED_HARDWARE_STUB);
			wait(2);
			if (getLastVal(getWebElement(HPSA_CABLE_TV).getAttribute("class")).equalsIgnoreCase("fail")) {
				click(HPSA_CABLE_TV);
			}
			if (getLastVal(getWebElement(HPSA_INTERNET).getAttribute("class")).equalsIgnoreCase("fail")) {
				click(HPSA_INTERNET);
			}
			if (getLastVal(getWebElement(HPSA_WIFI).getAttribute("class")).equalsIgnoreCase("fail")) {
				click(HPSA_WIFI);
			}
			if (getLastVal(getWebElement(HPSA_WIFI).getAttribute("class")).equalsIgnoreCase("no_response")) {
				click(HPSA_WIFI);
				wait(1);
				click(HPSA_WIFI);
			}
			if (getLastVal(getWebElement(HPSA_PHONE_LINE).getAttribute("class")).equalsIgnoreCase("fail")) {
				click(HPSA_PHONE_LINE);
			} else if (getLastVal(getWebElement(HPSA_PHONE_LINE).getAttribute("class"))
					.equalsIgnoreCase("hpsa_off_hook")) {
				click(HPSA_PHONE_LINE);
				wait(1);
				click(HPSA_PHONE_LINE);
				wait(1);
				click(HPSA_PHONE_LINE);
			}
			if (getLastVal(getWebElement(CONVERGED_HARDWARE_STUB).getAttribute("class")).equalsIgnoreCase("fail")) {
				click(CONVERGED_HARDWARE_STUB);
			}
			if (getLastVal(getWebElement(SET_CABLE_TV).getAttribute("class")).equalsIgnoreCase("fail")) {
				click(SET_CABLE_TV);
			}
			if (getLastVal(getWebElement(ENGINEERING_NO_TV_BTN).getAttribute("class")).equalsIgnoreCase("no_tv")) {
				click(ENGINEERING_NO_TV_BTN);
				wait(1);
				click(ENGINEERING_NO_TV_BTN);
				wait(1);
				click(ENGINEERING_NO_TV_BTN);
			}
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			scrollDownAndClick(FIELD_FORCE_MANAGEMENT);
			wait(2);
			scrollDownToElement(FFM_SERVICE_CALL);
			wait(1);
			if (getLastVal(getWebElement(FFM_SERVICE_CALL).getAttribute("class")).equalsIgnoreCase("fail")) {
				click(FFM_SERVICE_CALL);
			}
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateFirstToOeStubServer");
		} catch (Exception e) {
			log.error("Error in navigateFirstToOeStubServer:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToFieldForceManagementSuccess(String newTabExists) {
		try {
			log.debug("Entering navigateToFieldForceManagementSuccess");
			if (newTabExists == "true")
				switchSecondNewTab();
			String StubURL = Utility.getValueFromPropertyFile("oestub_url");
			navigate(StubURL);
			wait(1);
			test.log(LogStatus.INFO, "Open Stub URL: ", "<a href=" + StubURL + " > " + StubURL + " </a>");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			wait(4);
			click(FIELD_FORCE_MANAGEMENT);
			javascript.executeScript("window.scrollBy(0,500)", "");
			wait(2);
			if (getLastVal(getWebElement(FFM_SERVICE_CALL).getAttribute("class")).equalsIgnoreCase("fail")) {
				click(FFM_SERVICE_CALL);
			}
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToFieldForceManagement");
		} catch (Exception e) {
			log.error("Error in navigateToFieldForceManagement:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToFieldForceManagementFail(String newTabExists) {
		try {
			log.debug("Entering navigateToOeStubServerToFail");
			if (newTabExists == "true")
				switchSecondNewTab();
			String StubURL = Utility.getValueFromPropertyFile("oestub_url");
			navigate(StubURL);
			wait(1);
			test.log(LogStatus.INFO, "Open Stub URL: ", "<a href=" + StubURL + " > " + StubURL + " </a>");
			wait(3);
			click(FIELD_FORCE_MANAGEMENT);
			wait(2);
			javascript.executeScript("window.scrollBy(0,-600)", "");
			click(FFM_SERVICE_CALL);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			log.debug("Leaving navigateToOeStubServerToFail");
		} catch (Exception e) {
			log.error("Error in navigateToOeStubServerToFail:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void NavigateESICusAcEmailEndpoint() {
		try {
			log.debug("Entering NavigateESICusAcEmailEndpoint");
			String StubURL = Utility.getValueFromPropertyFile("oestub_url");
			navigate(StubURL);
			wait(1);
			test.log(LogStatus.INFO, "Open Stub URL: ", "<a href=" + StubURL + " > " + StubURL + " </a>");
			wait(2);
			scrollDownAndClick(ESI);
			wait(1);
			scrollDownAndClick(GET_ESI_EMAIL);
			int counter = 1;
			while (!(getLastVal(getWebElement(CHANGE_MODE_FOR_EMAIL).getAttribute("class"))
					.equalsIgnoreCase("esi_customer_account_email")) && counter <= 5) {
				click(CHANGE_MODE_FOR_EMAIL);
				wait(1);
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			test.log(LogStatus.PASS, "Customer Account Email operation: ",
					"Successfully Set No matching email properties");
			log.debug("Leaving NavigateESICusAcEmailEndpoint");
		} catch (Exception e) {
			log.error("Error in NavigateESICusAcEmailEndpoint:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public String getLastVal(String element) {
		String lastStr = "";
		Scanner scanner = new Scanner(element);
		while (scanner.hasNext())
			lastStr = scanner.next();
		return lastStr;
	}

	public void navigateToChangeToggleValueToFail(String value) {
		try {
			log.debug("Entering navigateToChangeToggleValueToFail");
			if (value.equalsIgnoreCase("Phone")) {
				click(HPSA_PHONE_LINE);
				wait(1);
				click(HPSA_PHONE_LINE);
				wait(1);
				click(HPSA_PHONE_LINE);
				// additional code
				wait(1);
				click(HPSA_PHONE_LINE);
			}
			if (value.equalsIgnoreCase("Internet")) {
				click(HPSA_INTERNET);
				wait(1);
				click(HPSA_INTERNET);
				wait(1);
			}
			if (value.equalsIgnoreCase("CableTV")) {
				click(HPSA_CABLE_TV);
				wait(1);
				click(HPSA_CABLE_TV);
				wait(1);
			}
			if (value.equalsIgnoreCase("Wifi")) {
				click(HPSA_WIFI);
				wait(2);
				click(HPSA_WIFI);
				wait(3);
			}
			if (value.equalsIgnoreCase("Converged Hardware")) {
				click(CONVERGED_HARDWARE_STUB);
				wait(2);
				click(CONVERGED_HARDWARE_STUB);
				wait(3);
			}
			if (value.equalsIgnoreCase("SetCableTV")) {
				click(SET_CABLE_TV);
				wait(2);
				click(SET_CABLE_TV);
				wait(3);
			}
			if (value.equalsIgnoreCase("Field Force Management")) {
				click(FIELD_FORCE_MANAGEMENT);
				wait(1);
				javascript.executeScript("window.scrollBy(0,-600)", "");
				click(FFM_SERVICE_CALL);
				wait(1);
			}
			if (value.equalsIgnoreCase("LSR")) {
				click(STUB_CLEC_TAB);
				wait(2);
				click(CLECTAB_LSR_BTN);
				wait(2);
				click(CLECTAB_LSR_BTN);
				wait(1);
			}

			if (value.equalsIgnoreCase("AWS PUT")) {
				scrollDownAndClick(ESI);
				wait(2);
				scrollDownToElement(STUB_AWS_PUT_MODE);
				wait(2);
				while (!(getLastVal(getWebElement(STUB_AWS_PUT_MODE).getAttribute("class"))
						.equalsIgnoreCase("no_response"))) {
					click(STUB_AWS_PUT_MODE);
					wait(1);
				}
			}

//			if (value.equalsIgnoreCase("CDI")) {
//				scrollDownAndClick(CDI);
//				wait(2);
//				getWebElement(CDI_ACCOUNT_TYPE_BTN);
//				wait(2);
//				while (!(getLastVal(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"))
//						.equalsIgnoreCase("cdi_customer_account_type_all"))) {
//					click(CDI_ACCOUNT_TYPE_BTN);
//					wait(1);
//				}
//			}

			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			log.debug("Leaving navigateToChangeToggleValueToFail");
		} catch (Exception e) {
			log.error("Error in navigateToChangeToggleValueToFail:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToCDIchangeAccountType(String value) {
		try {
			log.debug("Entering navigateToCDIchangeAccountType");
			if (isElementPresent(CDI_STUB)) {
				click(CDI_STUB);
			}
			wait(5);
			if (value.equals("All")) {
				wait(3);
				getWebElement(CDI_ACCOUNT_TYPE_BTN);
				wait(4);
				while (!(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"))
						.contains("cdi_customer_account_type_all")) {
//					System.out.println(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"));
					wait(1);
					click(CDI_ACCOUNT_TYPE_BTN);
					wait(2);
				}
			}

			if (value.equals("Tenant")) {
				wait(3);
				getWebElement(CDI_ACCOUNT_TYPE_BTN);
				wait(4);
				while (!(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"))
						.contains("cdi_customer_account_type_tenant")) {
//					System.out.println(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"));
					wait(1);
					click(CDI_ACCOUNT_TYPE_BTN);
					wait(1);
				}
			}

			if (value.equals("Business")) {
				wait(3);
				getWebElement(CDI_ACCOUNT_TYPE_BTN);
				wait(4);
				while (!(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"))
						.contains("cdi_customer_account_type_business")) {
//					System.out.println(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"));
					wait(1);
					click(CDI_ACCOUNT_TYPE_BTN);
					wait(1);
				}
			}

			if (value.equals("Bulkmaster")) {
				wait(3);
				getWebElement(CDI_ACCOUNT_TYPE_BTN);
				wait(4);
				while (!(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"))
						.contains("cdi_customer_account_type_bulkmaster")) {
//					System.out.println(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"));
					wait(1);
					click(CDI_ACCOUNT_TYPE_BTN);
					wait(1);
				}
			}

			if (value.equals("Staff")) {
				wait(3);
				getWebElement(CDI_ACCOUNT_TYPE_BTN);
				wait(4);
				while (!(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"))
						.contains("cdi_customer_account_type_staff")) {
//					System.out.println(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"));
					wait(1);
					click(CDI_ACCOUNT_TYPE_BTN);
					wait(1);
				}
			}

			if (value.equals("Residental")) {
				wait(3);
				getWebElement(CDI_ACCOUNT_TYPE_BTN);
				wait(4);
				while (!(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"))
						.contains("cdi_customer_account_type_residential")) {
//					System.out.println(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"));
					wait(1);
					click(CDI_ACCOUNT_TYPE_BTN);
					wait(1);
				}
			}

			if (value.equals("Residental/Staff")) {
				wait(3);
				getWebElement(CDI_ACCOUNT_TYPE_BTN);
				wait(4);
				while (!(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"))
						.contains("cdi_customer_account_type_residential_staff")) {
//					System.out.println(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"));
					wait(1);
					click(CDI_ACCOUNT_TYPE_BTN);
					wait(1);
				}
			}

			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			log.debug("Leaving navigateToCDIchangeAccountType");
		} catch (Exception e) {
			log.error("Error in navigateToCDIchangeAccountType:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void ChangeAccountSubType(String value) {
		try {
			log.debug("Entering Change Account SubType");
			if (isElementPresent(CDI_STUB)) {
				click(CDI_STUB);
			}
			wait(5);
			if (value.equals("All")) {
				wait(3);
				getWebElement(CDI_ACCOUNT_SUB_TYPE_BTN);
				wait(4);
				while (!(getWebElement(CDI_ACCOUNT_SUB_TYPE_BTN).getAttribute("class"))
						.contains("cdi_account_type_all")) {
//					System.out.println(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"));
					wait(1);
					click(CDI_ACCOUNT_SUB_TYPE_BTN);
					wait(2);
				}
			}

			if (value.equals("Tenant")) {
				wait(3);
				getWebElement(CDI_ACCOUNT_TYPE_BTN);
				wait(4);
				while (!(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"))
						.contains("cdi_customer_account_type_tenant")) {
//					System.out.println(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"));
					wait(1);
					click(CDI_ACCOUNT_TYPE_BTN);
					wait(1);
				}
			}

			if (value.equals("Business")) {
				wait(3);
				getWebElement(CDI_ACCOUNT_TYPE_BTN);
				wait(4);
				while (!(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"))
						.contains("cdi_customer_account_type_business")) {
//					System.out.println(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"));
					wait(1);
					click(CDI_ACCOUNT_TYPE_BTN);
					wait(1);
				}
			}

			if (value.equals("Bulkmaster")) {
				wait(3);
				getWebElement(CDI_ACCOUNT_TYPE_BTN);
				wait(4);
				while (!(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"))
						.contains("cdi_customer_account_type_bulkmaster")) {
//					System.out.println(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"));
					wait(1);
					click(CDI_ACCOUNT_TYPE_BTN);
					wait(1);
				}
			}

			if (value.equals("Staff")) {
				wait(3);
				getWebElement(CDI_ACCOUNT_TYPE_BTN);
				wait(4);
				while (!(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"))
						.contains("cdi_customer_account_type_staff")) {
//					System.out.println(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"));
					wait(1);
					click(CDI_ACCOUNT_TYPE_BTN);
					wait(1);
				}
			}

			if (value.equals("Residental")) {
				wait(3);
				getWebElement(CDI_ACCOUNT_TYPE_BTN);
				wait(4);
				while (!(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"))
						.contains("cdi_customer_account_type_residential")) {
//					System.out.println(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"));
					wait(1);
					click(CDI_ACCOUNT_TYPE_BTN);
					wait(1);
				}
			}

			if (value.equals("Residental/Staff")) {
				wait(3);
				getWebElement(CDI_ACCOUNT_TYPE_BTN);
				wait(4);
				while (!(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"))
						.contains("cdi_customer_account_type_residential_staff")) {
//					System.out.println(getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class"));
					wait(1);
					click(CDI_ACCOUNT_TYPE_BTN);
					wait(1);
				}
			}

			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			log.debug("Leaving navigateToCDIchangeAccountType");
		} catch (Exception e) {
			log.error("Error in navigateToCDIchangeAccountType:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public String CreateCBScustomer(String value, String acctNbr) {
		String msg = null;
		try {
			log.debug("Entering CreateCBScustomer");
			click(CBS_CUSTOMER_ACCOUNT_TYPE);
			if (value.equalsIgnoreCase("Bulkmaster")) {
				wait(2);
				click(CBS_BULKMASTER_ACCOUNT);
			}
			if (value.equalsIgnoreCase("Tenant")) {
				wait(2);
				click(CBS_TENANT_ACCOUNT);
			}

			if (value.equalsIgnoreCase("Residential")) {
				wait(2);
				click(CBS_RESIDENTAL_ACCOUNT);
			}

			if (isElementPresent(CBS_MASTER_ACCOUNT_INPUT)) {
				wait(2);
				inputText(CBS_MASTER_ACCOUNT_INPUT, acctNbr);
			}

			if (isElementPresent(LOCATIONID_TEXTBOX)) {
				wait(2);
				inputText(LOCATIONID_TEXTBOX, "2");
			}
			wait(2);
			click(CREATE_BTN_STUB);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);

			if (isDisplayed(CBS_ACCOUNT_ID)) {
				msg = getText(CBS_ACCOUNT_ID);
			}
			log.debug("Leaving CreateCBScustomer");
		} catch (Exception e) {
			log.error("Error in CreateCBScustomer:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return msg;
	}

	public void navigateToOeStubServerToPass(String newTabExists, String value) {
		try {
			log.debug("Entering navigateToOeStubServerToPass");
			if (newTabExists == "true")
				switchNextTab();
			wait(1);
			if (value.equalsIgnoreCase("Phone")) {
				wait(1);
				click(HPSA_PHONE_LINE);
			}
			if (value.equalsIgnoreCase("Internet")) {
				wait(1);
				click(HPSA_INTERNET);
			}
			if (value.equalsIgnoreCase("CableTV")) {
				wait(1);
				click(HPSA_CABLE_TV);
			}
			if (value.equalsIgnoreCase("Wifi")) {
				wait(1);
				click(HPSA_WIFI);
				wait(2);
			}
			if (value.equalsIgnoreCase("Converged Hardware")) {
				wait(1);
				click(CONVERGED_HARDWARE_STUB);
			}
			if (value.equalsIgnoreCase("SetCableTV")) {
				wait(1);
				click(SET_CABLE_TV);
			}
			if (value.equalsIgnoreCase("Field Force Management")) {
				wait(1);
				click(FIELD_FORCE_MANAGEMENT);
				javascript.executeScript("window.scrollBy(0,-600)", "");
				click(FFM_SERVICE_CALL);
				wait(1);
			}
			if (value.equalsIgnoreCase("AWS PUT")) {
				String StubURL = Utility.getValueFromPropertyFile("oestub_url");
				navigate(StubURL);
				wait(1);
				test.log(LogStatus.INFO, "Stub URL: ", "<a href=" + StubURL + " > " + StubURL + " </a>");
				wait(1);
				scrollDownAndClick(ESI);
				wait(1);
				scrollDownAndClick(STUB_AWS_PUT_MODE);
				wait(2);
				while (!(getLastVal(getWebElement(STUB_AWS_PUT_MODE).getAttribute("class"))
						.equalsIgnoreCase("success"))) {
					click(STUB_AWS_PUT_MODE);
					wait(1);
				}
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			log.debug("Leaving navigateToOeStubServerToPass");
		} catch (Exception e) {
			log.error("Error in navigateToOeStubServerToPass:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToOeStubToPassServices(String value) {
		try {
			log.debug("Entering navigateToOeStubToPassServices");
			String StubURL = Utility.getValueFromPropertyFile("oestub_url");
			navigate(StubURL);
			wait(1);
			test.log(LogStatus.INFO, "Open Stub URL: ", "<a href=" + StubURL + " > " + StubURL + " </a>");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			wait(2);
			click(HPSA);
			javascript.executeScript("window.scrollBy(0,500)", "");
			wait(3);
			if (value.equalsIgnoreCase("Phone")) {
				wait(1);
				click(HPSA_PHONE_LINE);
				// additional code
				wait(1);
				click(HPSA_PHONE_LINE);
				wait(1);
				click(HPSA_PHONE_LINE);
				wait(1);
				click(HPSA_PHONE_LINE);
			}
			if (value.equalsIgnoreCase("Internet")) {
				wait(1);
				click(HPSA_INTERNET);
			}
			if (value.equalsIgnoreCase("CableTV")) {
				wait(1);
				click(HPSA_CABLE_TV);
			}
			if (value.equalsIgnoreCase("Wifi")) {
				wait(1);
				click(HPSA_WIFI);
			}
			if (value.equalsIgnoreCase("Converged Hardware")) {
				wait(1);
				click(CONVERGED_HARDWARE_STUB);
			}
			if (value.equalsIgnoreCase("SetCableTV")) {
				wait(1);
				click(SET_CABLE_TV);
			}
			if (value.equalsIgnoreCase("Field Force Management")) {
				wait(1);
				click(FIELD_FORCE_MANAGEMENT);
				javascript.executeScript("window.scrollBy(0,-600)", "");
				click(FFM_SERVICE_CALL);
				wait(1);
			}
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			log.debug("Leaving navigateToOeStubToPassServices");
		} catch (Exception e) {
			log.error("Error in navigateToOeStubCableTVToPass:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToExistingAccountForPremise(String accountNum) {
		try {
			log.debug("Entering navigateToExistingAccountForPremise");
			String existingAccountUrl = Utility.getValueFromPropertyFile("basepage_url")
					+ "/oe.newCustomerDesktop.nc?accountId=1" + accountNum + "&locationId=221&newLocationId=155";
			navigate(existingAccountUrl);
			wait(1);
			test.log(LogStatus.INFO, "OE URL with LocationId 155: ",
					"<a href=" + existingAccountUrl + " > " + existingAccountUrl + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			log.debug("Leaving navigateToExistingAccountForPremise");
		} catch (Exception e) {
			log.error("Error in navigateToExistingAccountForPremise:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public COMOrdersPage openCOMOrderPage(String newTabExists, String acctNbr, String clecRequestTab) {
		try {
			log.debug("Entering openCOMOrderPage");

//			  String accountPageurl = Utility.getValueFromPropertyFile("basepage_url") +
//			  "/common/search.jsp?explorer_mode=disable&object=7121040918013857702&return=%2Fcommon%2Fsearch.jsp%3Fdo_search%3Dyes%26_vname%3D1"
//			  + acctNbr +
//			  "%26o%3D1000%26fast_search%3Dyes%2522%253B%26object%3D7121040918013857702%26search_mode%3Dsearch%26_rname%3Din%26explorer_mode%3Ddisable&o=1000&_v_1=1"
//			  + acctNbr + "&_r_1=in&search_mode=search&do_search=yes&fast_search=yes";

			
			  String accountPageurl = Utility.getValueFromPropertyFile("basepage_url") +
			  "/common/search.jsp?explorer_mode=disable&object=7121040918013857702&o=7022760707013150025&project=7022760707013150024&_v_1=1"
			  + acctNbr +
			  "&_r_1=in&search_mode=search&do_search=yes&fast_search=yes&resultID=9160628372913865140"
			  ;
			 

//			  for server 8150

				/*
				 * String accountPageurl = Utility.getValueFromPropertyFile("basepage_url") +
				 * "/common/search.jsp?search_mode=search&resultID=9163808681313156785&performed=true&fast_search=no&do_search=yes&ctrl=t4122758596013619618_4122758596013619619&tab=0&return=%2Fcommon%2Fuobject.jsp%3Ftab%3D_360%2BView%26object%3D9163808481013149222&project=7022760707013150024&currobject=o&system_index_on=false&collapse_but=no&profile_id=7121040918013857702&object=7121040918013857702&o=9163808481013149222&explorer_mode=disable&resultID=9163808681313156785&property_ishidden_group_-10=no&_r_1=eq&_v_1=1"
				 * + acctNbr +
				 * "&_r_2=eq&property_ishidden_widget_scope_options=no&currproject=current&property_ishidden_widget_templates=no&cfadmin=QZN9-JJSt-L0g2-7hJF-kWk7-hNHE";
				 */
			log.debug(accountPageurl);
			wait(2);
			if (newTabExists.equals("true"))
				wait(1);
			switchFirstNewTab();
			wait(2);
			navigate(accountPageurl);
			wait(2);

			selectFromList(GENERIC_SEARCH, "equals");
			wait(1);
			click(SEACH_BUTTON);
			wait(4);

			do {
				wait(2);
			} while (!isDisplayed(ACCOUNT_LINK));
			click(ACCOUNT_LINK);
			wait(3);
			orderPageLink = getCurrentURL();
			wait(2);
			click(SOM_ORDER_TAB);
			wait(1);
			somorderPagelink = getCurrentURL();
			test.log(LogStatus.INFO, "SOM Orders URL: ",
					"<a href=" + somorderPagelink + " > " + somorderPagelink + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			if (clecRequestTab.equals("Yes")) {
				click(CLEC_REQUEST_TAB);
				wait(1);
				clecorderPagelink = getCurrentURL();
				test.log(LogStatus.INFO, "CLEC Orders URL: ",
						"<a href=" + clecorderPagelink + " > " + clecorderPagelink + " </a>");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			wait(1);
			click(COM_ORDER_TAB);
			wait(1);
			comorderPagelink = getCurrentURL();
			test.log(LogStatus.INFO, "COM Orders URL: ",
					"<a href=" + comorderPagelink + " > " + comorderPagelink + " </a>");
			int count = 1;
			while (count <= 5) {
				wait(2);
				refreshPage();
				count++;
			}
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving openCOMOrderPage");
		} catch (Exception e) {
			log.error("Error in openCOMOrderPage:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return new COMOrdersPage(test);
	}

	public COMOrdersPage openAccountInformation(String newTabExists, String acctNbr) {
		try {
			log.debug("Entering openAccountInformation");

			/*
			 * String accountPageurl = Utility.getValueFromPropertyFile("basepage_url") +
			 * "/common/search.jsp?explorer_mode=disable&object=7121040918013857702&return=%2Fcommon%2Fsearch.jsp%3Fdo_search%3Dyes%26_vname%3D1"
			 * + acctNbr +
			 * "%26o%3D1000%26fast_search%3Dyes%2522%253B%26object%3D7121040918013857702%26search_mode%3Dsearch%26_rname%3Din%26explorer_mode%3Ddisable&o=1000&_v_1=1"
			 * + acctNbr + "&_r_1=in&search_mode=search&do_search=yes&fast_search=yes";
			 */

			String accountPageurl = Utility.getValueFromPropertyFile("basepage_url")
					+ "/common/search.jsp?explorer_mode=disable&object=7121040918013857702&o=7022760707013150025&project=7022760707013150024&_v_1=1"
					+ acctNbr
					+ "&_r_1=in&search_mode=search&do_search=yes&fast_search=yes&resultID=9160628372913865140";

//			  for server 8150

//					  String accountPageurl = Utility.getValueFromPropertyFile("basepage_url") +
//					  "/common/search.jsp?search_mode=search&resultID=9163808681313156785&performed=true&fast_search=no&do_search=yes&ctrl=t4122758596013619618_4122758596013619619&tab=0&return=%2Fcommon%2Fuobject.jsp%3Ftab%3D_360%2BView%26object%3D9163808481013149222&project=7022760707013150024&currobject=o&system_index_on=false&collapse_but=no&profile_id=7121040918013857702&object=7121040918013857702&o=9163808481013149222&explorer_mode=disable&resultID=9163808681313156785&property_ishidden_group_-10=no&_r_1=eq&_v_1=1"
//					  + acctNbr +
//					  "&_r_2=eq&property_ishidden_widget_scope_options=no&currproject=current&property_ishidden_widget_templates=no&cfadmin=QZN9-JJSt-L0g2-7hJF-kWk7-hNHE";

			log.debug(accountPageurl);
			wait(2);
			if (newTabExists.equals("true"))
				wait(1);
			switchFirstNewTab();
			wait(2);
			navigate(accountPageurl);
			wait(2);

			// selectFromList(GENERIC_SEARCH, "equals");
			// wait(1);
			// click(SEACH_BUTTON);
			// wait(4);

			do {
				wait(2);
			} while (!isDisplayed(ACCOUNT_LINK));
			click(ACCOUNT_LINK);
			wait(3);
			orderPageLink = getCurrentURL();
			wait(2);
			click(ACCOUNT_INFORMATION_TAB);
			wait(1);
			scrollDownToElement(IS_IMMEDIATE_LOCKED);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving openAccountInformation");
		} catch (Exception e) {
			log.error("Error in openAccountInformation:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return new COMOrdersPage(test);
	}

	@SuppressWarnings({})
	public COMOrdersPage openCOMOrderPageLogin(String newTabExists, String acctNbr) {
		try {
			log.debug("Entering openCOMOrderPage");
			String accountPageurl = Utility.getValueFromPropertyFile("basepage_url")
					+ "/common/search.jsp?explorer_mode=disable&object=7121040918013857702&o=1000&_vname=1" + acctNbr
					+ "&_rname=in&search_mode=search&do_search=yes&fast_search=yes";
			wait(1);
			if (newTabExists.equals("true"))
				switchFirstNewTab();
			navigate(accountPageurl);
			test.log(LogStatus.INFO, "COM Order URL: ", "<a href=" + accountPageurl + " > " + accountPageurl + " </a>");
			wait(1);
			inputText(USER_NAME, Utility.getValueFromPropertyFile("user"));
			log.debug("Entering User Name");
			wait(1);
			inputText(PASSWORD, Utility.getValueFromPropertyFile("password"));
			log.debug("Entering Password");
			wait(1);
			click(LOGIN_BTN);
			log.debug("Clicking Login Button");
			wait(2);
			click(ACCOUNT_LINK);
			wait(6);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving openCOMOrderPage");
		} catch (Exception e) {
			log.error("Error in openCOMOrderPage:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return new COMOrdersPage(test);
	}

	public void navigateToVancoverLocationId(String accountNum) {
		try {
			log.debug("Entering navigateToVancoverLocationId");
			String VancoverLocURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/oe.newCustomerDesktop.nc?accountId=1" + accountNum + "&locationId=221&newLocationId=2777";
			navigate(VancoverLocURL);
			wait(1);
			test.log(LogStatus.INFO, "Vancover Location URL: ",
					"<a href=" + VancoverLocURL + " > " + VancoverLocURL + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			log.debug("Leaving navigateToVancoverLocationId");
		} catch (Exception e) {
			log.error("Error in navigateToVancoverLocationId:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void disconnectLOBProducts(String disconnectLOB) {
		try {
			log.debug("Entering disconnectLOBProduct");

			if (disconnectLOB.equalsIgnoreCase("Phone")) {
				wait(1);
				selectFromList(PHONE_PROD_LIST, "Not selected");
				wait(1);
				test.log(LogStatus.PASS, "Disconnect Phone Product: ", "Successfully Disconnected Phone Product");
			}
			if (disconnectLOB.equalsIgnoreCase("Internet")) {
				wait(5);
				click(INTERNET_NOT_SELECTED);
				wait(8);
				test.log(LogStatus.PASS, "Disconnect Internet Product: ", "Successfully Disconnected Internet Product");
			}
			if (disconnectLOB.equalsIgnoreCase("TV")) {
				wait(5);
				click(TV_NOT_SELECTED);
				wait(8);
				test.log(LogStatus.PASS, "Disconnect TV Product: ", "Disconnected TV Product Successfully");
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving disconnectLOBProduct");
		} catch (Exception e) {
			log.error("Error in disconnectLOBProduct:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public String getOrderPageLink() {
		return orderPageLink;
	}

	public void navigateToOrderPage() throws Exception {
		navigate(orderPageLink);
		wait(1);
		test.log(LogStatus.PASS, "Navigate back to COM", "Navigate to COM order tab successfully");
		log.debug("Navigate back to COM order tab successfully");
	}

	public void selectDisconnectControllabeReasons(String secondaryReason) {
		try {
			log.debug("Entering selectDisconnectControllabeReasons");
			wait(1);
			if (secondaryReason.equals("Can No Longer Afford")) {
				click(CAN_NO_LONGER_AFFORD);
			}
			if (secondaryReason.equals("Competitive Promotion")) {
				click(COMPITATIVE_PRAMOTIONS);
			}
			if (secondaryReason.equals("Customer Care Issues")) {
				click(CUSTOMERCARE_ISSUES);
			}
			if (secondaryReason.equals("Doesn't use enough to justify the cost")) {
				click(DOESNT_USE);
			}
			if (secondaryReason.equals("Port-Out (Phone/Internet/TV)")) {
				click(PORT_OUT);
			}
			if (secondaryReason.equals("Promotion Ended")) {
				click(PROMOTION_ENDED);
			}
			if (secondaryReason.equals("Technical Issues")) {
				click(TECHNICAL_ISSUES);
			}
			wait(1);
			inputText(CHANGEAUTHORIZEDBY, "Integration Test");
			wait(1);
			test.log(LogStatus.PASS, "Adding Disconnect Controllable Reason", "Added Disconnect Reason Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving selectDisconnectControllabeReasons");
		} catch (Exception e) {
			log.error("Error in selectDisconnectControllabeReasons:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void selectDisconnectNonControllabeReasons(String secondaryReason) {
		try {
			log.debug("Entering selectDisconnectNonControllabeReasons");
			wait(1);
			click(REASON_CATEGORY_DROPDOWN);
			wait(1);
			click(NON_CONTROLLABLE);
			wait(2);
			if (secondaryReason.equals("Customer Declined to say")) {
				click(CUSTOMER_DECLINED_TO_SAY);
			}
			if (secondaryReason.equals("Deceased")) {
				click(DECEASED);
			}
			if (secondaryReason.equals("Large-scale Unforeseen Event")) {
				click(LARGE_SCALE);
			}
			if (secondaryReason.equals("Move - Address with Service included")) {
				click(MOVE_ADDRESS);
			}
			if (secondaryReason.equals("Move - Outside Shaw Territory")) {
				click(MOVE_OUTSIDE_SHAW);
			}
			if (secondaryReason.equals("Non-Pay")) {
				click(NON_PAY);
			}
			wait(1);
			inputText(CHANGEAUTHORIZEDBY, "Integration Test");
			wait(1);
			test.log(LogStatus.PASS, "Adding Disconnect Non-Controllable Reason",
					"Added Disconnect Reason Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving selectDisconnectNonControllabeReasons");
		} catch (Exception e) {
			log.error("Error in selectDisconnectNonControllabeReasons:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public String portPhoneNumber(String AddPhone, String phoneLine, String prod, String Number, String lifeLineAlarm,
			String shawServiceRequest) {
		try {
			log.debug("Entering portPhoneNumber");
			javascript.executeScript("window.scrollBy(0,-600)", "");
			if (AddPhone.equals("Phone Required")) {
				wait(4);
				click(ADD_PHONE_PROD);
				wait(4);
				selectFromList(PHONE_PROD_LIST, prod);
				wait(2);
				click(PHONE_WRENCH_BUTTON);
				wait(2);
			}
			if (phoneLine.equals("Secondary")) {
				wait(1);
				click(ADD_PHONE_PROD);
				wait(2);
				selectFromList(SELECT_PHONE_PROD2, prod);
				wait(2);
				click(PHONE_WRENCH_BUTTON2);
				wait(2);
			}
			switchToChildAlert();
			if (Number.equals("Native To Ported")) {
				wait(4);
				click(PHONE_WRENCH_BUTTON);
				wait(4);
				click(CHANGE_CUST_NBR);
				wait(4);
			}
			click(PHONE_WRENCH_BUTTON);
			wait(4);
			click(PORT_NUMBER_RADIO_BUTTON);
			wait(4);
			WebElement e1 = getWebElement(PORTED_NUMBER);
			wait(3);
			portPhoneNumber = Utility.generatePortPhoneNbr();
			jse.executeScript("arguments[0].value='" + portPhoneNumber + "';", e1);
			// jse.executeScript("arguments[0].value='(410) 186-7932';", e1);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(PORT_NOW);
			wait(7);
			click(LANDLINE_RADIO_BUTTON);
			wait(2);
			inputText(PREVIOUS_PROVIDER_ACCOUNT_NUMBER, "5046810793");
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			click(RESELLER_DROP_DOWN);
			wait(1);
			click(RESELLER_VALUE);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);

			if (lifeLineAlarm.equalsIgnoreCase("Yes")) {
				click(CUSTOMER_LIFELINE_YES);
				wait(2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			if (lifeLineAlarm.equalsIgnoreCase("No")) {
				click(CUSTOMER_LIFELINE_NO);
				wait(2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			if (shawServiceRequest.equalsIgnoreCase("Yes")) {
				wait(1);
				click(SHAW_SERVICE_REQUESTED_YES);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(2);
				click(SELECT_ADDRESS);
				wait(2);
				inputText(OLD_HOUSE_NBR_TXT, "630");
				wait(2);
				click(OLD_STREET_NAME);
				wait(2);
				inputText(OLD_STREET_NAME, "Ave SW");
				wait(2);
				click(OLD_CITY);
				wait(2);
				inputText(OLD_CITY, "Calgary");
				wait(2);
				click(OLD_POSTAL_CODE);
				wait(2);
				inputText(OLD_POSTAL_CODE, "T2P4L4");
				wait(2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			if (shawServiceRequest.equalsIgnoreCase("No")) {
				wait(1);
				click(SHAW_SERVICE_REQUESTED_NO);
				wait(2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			click(OK_BUTTON_TO_CLOSE);
			wait(3);
			click(LISTINGTYPE_LIST);
			wait(3);
			click(NO_LISTING);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(OK_BUTTON_TO_CLOSE);
			wait(1);
			portPhoneNumber = convertPhoneToString(getText(PERSONAL_PHONE_NBR_FIELD));
			log.debug("Leaving portPhoneNumber");
		} catch (Exception e) {
			log.error("Error in portPhoneNumber:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return portPhoneNumber;
	}

	public String portDistinctiveNumber(String lifeLineAlarm) {
		try {
			log.debug("Entering portDistinctiveNumber");
			wait(2);
			click(DISTINCTIVE_RING_INCREMENT1);
			wait(4);
			click(DR_SELECT_NUMBER);
			wait(4);
			switchToChildAlert();
			click(PORT_NUMBER_RADIO_BUTTON);
			wait(4);
			WebElement e1 = getWebElement(PORTED_NUMBER);
			distinctivePhoneNbr = Utility.generatePortPhoneNbr();
			jse.executeScript("arguments[0].value='" + distinctivePhoneNbr + "';", e1);
			// jse.executeScript("arguments[0].value='(123) 986-4567';", e1);
			wait(4);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(PORT_NOW);
			wait(4);
			click(LANDLINE_RADIO_BUTTON);
			wait(4);
			inputText(PREVIOUS_PROVIDER_ACCOUNT_NUMBER, "6666666666");
			wait(4);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(3);
			click(RESELLER_DROP_DOWN);
			wait(4);
			click(RESELLER_DROP_DOWN);
			click(RESELLER_VALUE);
			wait(4);
			if (lifeLineAlarm.equalsIgnoreCase("Yes"))
				click(CUSTOMER_LIFELINE_YES);
			else
				click(CUSTOMER_LIFELINE_NO);
			wait(2);
			click(SHAW_SERVICE_REQUESTED_NO);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			click(OK_BUTTON_TO_CLOSE);
			wait(2);
			// click(LISTINGTYPE_LIST);
			// wait(2);
			click(LISTINGTYPE_LIST1);
			wait(4);
			// click(LISTINGTYPE411ONLY);
			// wait(2);
			click(FEATURES_TAB);
			wait(4);
			click(RING_PATTERN_DROP_DOWN);
			wait(4);
			click(TYPE_3_RING_SHORT);
			wait(4);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(3);
			click(OK_BUTTON_TO_CLOSE);
			wait(4);
			distinctivePhoneNbr = convertPhoneToString(getText(DISTINCTIVE_RING_NBR_FIELD));
			log.debug("Leaving portDistinctiveNumber");
		} catch (Exception e) {
			log.error("Error in portDistinctiveNumber:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return distinctivePhoneNbr;
	}

	public void portPhoneTextValidation() {
		try {
			log.debug("Addservices text validation");

			String porttext = getText(PORT_TEXT);
			if (porttext.equals("The order has been updated. Please re-validate appointment time.")) {

				test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				test.log(LogStatus.INFO, "Text Validation For Port Phone: " + porttext);

			}
			log.debug("Addservices text validation");
		} catch (Exception e) {
			log.error("Error in Addservices text validation:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void TodoTextValidation() {
		try {
			log.debug("Todo text validation");

			String todotext = getText(TODO_TEXT);
			if (todotext.equals("The order has been updated. Please re-validate appointment time.")) {

				test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				test.log(LogStatus.INFO, "Text Validation in ToDo: " + todotext);

			}
			log.debug("Todo text validation");
		} catch (Exception e) {
			log.error("Error in Todo text validation:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void ValidateInternetRadioBtn() {
		try {
			log.debug("Varify Internet Radio Button");

			if (isEnabled(RADIO_BTN)) {
				System.out.println("Internet Radio Button is Enabled");
			} else {
				System.out.println("Internet Radio Button is Disable");
			}
			log.debug("Varify Internet Radio Button");
		} catch (Exception e) {
			log.error("Error in Varify Internet Radio Button:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void Phoneopt_outTextValidation() {
		try {
			log.debug("Addservices text validation");

			String phoneopt = getText(PHONE_OPT_OUT);
			if (phoneopt.equals("Contact Phone opt-out selection is required in the Customer & ID section.")) {
				scrollDownAndClick(PHONE_OPT_OUT);
				test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				test.log(LogStatus.INFO, "Text Validation For Phone opt_out: " + phoneopt);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			}
			log.debug("Addservices text validation");
		} catch (Exception e) {
			log.error("Error in Addservices text validation:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToCLECOPSUser(String newTabExists, String lineInitiation) {
		try {
			log.debug("Entering navigateToCLECOPSUser");
			wait(1);
			if (newTabExists.equals("true"))
				switchFirstNewTab();
			String CLECOPSUserURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/admin/users/groups.jsp?tab=_All+Users&group=9140829068113548736&object=9140829068113548736";
			navigate(CLECOPSUserURL);
			wait(1);
			test.log(LogStatus.INFO, "CLECOPS User URL: ",
					"<a href=" + CLECOPSUserURL + " > " + CLECOPSUserURL + " </a>");
			wait(1);
			click(CLECOPS_FILTER);
			wait(2);
			inputText(CLECOPS_SEARCH_BOX, "CLECOPS");
			wait(2);
			click(APPLY_FILTER);
			wait(2);
			int ClecOpsCount = driver.findElements(By.xpath("//span[contains(text(),'CLECOPS')]")).size();
			if (ClecOpsCount >= 1) {
				click(CLEC_OPS_USER_NAME);
				wait(3);
			} else {
				click(NEW_USER);
				wait(2);
				wait(2);
				inputText(USER_NAME_BOX, "CLECOPS");
				wait(2);
				inputText(NEW_PASSWORD, "123");
				wait(1);
				inputText(CONFIRM_PASSWORD, "123");
				wait(2);
				click(CREATE_BTN);
				wait(2);
				switchToChildAlert();
				wait(1);
				inputText(USER_NAME1, "Administrator");
				wait(1);
				inputText(PASSWORD, "netcracker");
				wait(1);
				click(CLECOPS_FILTER);
				wait(2);
				inputText(CLECOPS_SEARCH_BOX, "CLECOPS");
				wait(2);
				click(APPLY_FILTER);
				wait(2);
				click(CLEC_OPS_USER_NAME);
				wait(2);
				click(PARAMETERS_TAB);
				wait(2);
				click(INTEGRATION_SETTINGS_EDIT_BTN);
				wait(2);
				scrollDownAndClick(ADDING_ROLES_DROPDOWN);
				wait(1);
				clickAndEnterText(ADDING_ROLES_DROPDOWN, "CLEC OPS Role");
				wait(1);
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_DOWN);
				wait(1);
				robot.keyPress(KeyEvent.VK_ENTER);
				wait(1);
				clickAndEnterText(ADDING_ROLES_DROPDOWN, "CLEC OPS");
				robot.keyPress(KeyEvent.VK_DOWN);
				wait(1);
				robot.keyPress(KeyEvent.VK_ENTER);
				wait(1);
				clickAndEnterText(ADDING_ROLES_DROPDOWN, "CLEC_Communication_Error");
				wait(1);
				robot.keyPress(KeyEvent.VK_DOWN);
				wait(1);
				robot.keyPress(KeyEvent.VK_ENTER);
				wait(2);
				scrollToTop();
				wait(1);
				click(PORTING_TIME_UPDATE_BTN);
				wait(2);
			}
			javascript.executeScript("window.scrollBy(2000,0)", "");
			click(LOGOFF_BTN);
			wait(2);
			clickAndEnterText(USER_NAME, Utility.getValueFromPropertyFile("CLECOPSUser"));
			wait(2);
			clickAndEnterText(PASSWORD, Utility.getValueFromPropertyFile("CLECOPSPassword"));
			wait(2);
			click(LOGIN);
			wait(1);
			navigate(CLECOPSUserURL);
			wait(1);
			test.log(LogStatus.INFO, "CLECOPS User URL: ",
					"<a href=" + CLECOPSUserURL + " > " + CLECOPSUserURL + " </a>");
			wait(1);
			click(CLECOPS_FILTER);
			wait(2);
			inputText(CLECOPS_SEARCH_BOX, "CLECOPS");
			wait(2);
			click(APPLY_FILTER);
			wait(2);
			click(CLEC_OPS_USER_NAME);
			wait(3);
			click(GROUP_TASKS_TAB);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			if (lineInitiation.equals("Yes")) {
				wait(6);
				click(INITIATE_LINE_ACTIVATION);
				wait(5);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				click(LSC_COMPLETE_BUTTON);
				wait(7);
			}
			log.debug("Leaving navigateToCLECOPSUser");
		} catch (Exception e) {
			log.error("Error in navigateToCLECOPSUser:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToCreate_ATO_Or_SRE_UserRole(String Order) {
		try {
			log.debug("Entering navigateToCreate_ATO_Or_SRE_UserRole");
			if (Order.equals("User With ATO Role")) {
				wait(1);
				String ATOUserURL = Utility.getValueFromPropertyFile("basepage_url")
						+ "/admin/users/groups.jsp?tab=_Users+%26+Groups&group=9151173092813712373&object=9151173092813712373";
				navigate(ATOUserURL);
				wait(1);
				test.log(LogStatus.INFO, "ATO User URL: ", "<a href=" + ATOUserURL + " > " + ATOUserURL + " </a>");
				click(NEW_USER);
				wait(2);
				inputText(USER_NAME_BOX, "ato_user");
				wait(2);
			}
			if (Order.equals("User With SRE Role")) {
				wait(1);
				String SREUserURL = Utility.getValueFromPropertyFile("basepage_url")
						+ "/admin/users/groups.jsp?tab=_Users+%26+Groups&group=9154889126213243939&object=9154889126213243939";
				navigate(SREUserURL);
				wait(1);
				test.log(LogStatus.INFO, "SRE User URL: ", "<a href=" + SREUserURL + " > " + SREUserURL + " </a>");
				click(NEW_USER);
				wait(2);
				inputText(USER_NAME_BOX, "sre_user");
				wait(2);
			}
			inputText(OLD_PASSWORD, "netcracker");
			wait(2);
			inputText(NEW_PASSWORD, "123");
			wait(1);
			inputText(CONFIRM_PASSWORD, "123");
			wait(2);
			selectFromList(USER_STATUS, "Normal");
			wait(2);
			click(CREATE_BTN);
			wait(2);
			test.log(LogStatus.PASS, "Create ATO/SRE User", "Created ATO/SRE user Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToCreate_ATO_Or_SRE_UserRole");
		} catch (Exception e) {
			log.error("Error in navigateToCreate_ATO_Or_SRE_UserRole:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void loginWithUser_ATO_Or_SRE_Role(String User) {
		try {
			log.debug("Entering loginWithUser_ATO_Or_SRE_Role");
			click(LOGOUT);
			wait(2);
			if (User.equals("ATO")) {
				inputText(USER_NAME, "ato_user");
			}
			if (User.equals("SRE")) {
				inputText(USER_NAME, "sre_user");
			}
			log.debug("Entering User Name");
			wait(1);
			inputText(PASSWORD, "123");
			log.debug("Entering Password");
			wait(1);
			click(LOGIN);
			log.debug("Clicking Login Button");
			wait(2);
			log.debug("Title::" + driver.getTitle());
			test.log(LogStatus.PASS, "Login", "Logged IN Successfully With New User");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving loginWithUser_ATO_Or_SRE_Role");

		} catch (Exception e) {
			log.error("Error in loginWithUser_ATO_Or_SRE_Role:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void openOEStubTochangeLocationDropType(String newTabExists) {
		try {
			log.debug("Entering openOEStubTochangeLocationDropType");
			wait(1);
			if (newTabExists.equals("true")) {
				String oeStubPageurl = Utility.getValueFromPropertyFile("oestub_url");
				switchSecondNewTab();
				navigate(oeStubPageurl);
				wait(1);
				test.log(LogStatus.INFO, "Location Drop Type URL: ",
						"<a href=" + oeStubPageurl + " > " + oeStubPageurl + " </a>");
				javascript.executeScript("window.scrollBy(0,1500)", "");
				click(SERVICE_AVILABILITY);
				wait(2);
				javascript.executeScript("window.scrollBy(0,800)", "");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				click(LOCATION_DROP_TYPE);
				wait(2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(1);
			} else {
				wait(2);
				click(SERVICE_AVILABILITY);
				if (!getLastVal(getWebElement(LOCATION_DROP_TYPE).getAttribute("class")).equalsIgnoreCase("f_fibre")) {
					wait(1);
					javascript.executeScript("window.scrollBy(0,800)", "");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					wait(1);
					click(LOCATION_DROP_TYPE);
					wait(1);
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					wait(1);
				}
			}
			log.debug("Leaving openOEStubTochangeLocationDropType");
		} catch (Exception e) {
			log.error("Error in openOEStubTochangeLocationDropType:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void openOEStubTochangeLocationCoaxial(String newTabExists) {
		try {
			log.debug("Entering openOEStubTochangeLocationCoaxial");
			wait(1);
			if (newTabExists.equals("true")) {
				String oeStubPageurl = Utility.getValueFromPropertyFile("oestub_url");
				switchSecondNewTab();
				navigate(oeStubPageurl);
				wait(1);
				test.log(LogStatus.INFO, "Location Drop Type URL: ",
						"<a href=" + oeStubPageurl + " > " + oeStubPageurl + " </a>");

				javascript.executeScript("window.scrollBy(0,1500)", "");
				click(SERVICE_AVILABILITY);
				wait(2);
//				javascript.executeScript("window.scrollBy(0,800)", "");
//				wait(1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				click(LOCATION_DROP_TYPE);
				wait(2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			} else {
				wait(2);
				click(SERVICE_AVILABILITY);
				if (!getLastVal(getWebElement(LOCATION_DROP_TYPE).getAttribute("class"))
						.equalsIgnoreCase("c_coaxial")) {
					wait(2);
					javascript.executeScript("window.scrollBy(0,800)", "");
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					wait(1);
					click(LOCATION_DROP_TYPE);
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					wait(1);
				}
			}

			log.debug("Leaving openOEStubTochangeLocationCoaxial");
		} catch (Exception e) {
			log.error("Error in openOEStubTochangeLocationCoaxial:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

//	public void getCDIaccountType(String accountType) {
//		
//		click(CDI);
////		wait(2);
//		getCDIaccountType(accountType);
//		WebElement accountTypeBtn = getWebElement(CDI_ACCOUNT_TYPE_BTN);
//		while (getWebElement(CDI_ACCOUNT_TYPE_BTN).getAttribute("class").contains(accountType));
//			
//			click(CDI_ACCOUNT_TYPE_BTN);
//			
//		}

	public void internetFiberModemHardwareSelection(String internetHardwareSerialNo) {
		try {
			log.debug("Entering internetFiberModemHardwareSelection");
			click(INTERNET_HARDWARE);
			wait(6);
			switchToChildAlert();
			wait(4);
			click(FIBER_MODEM_RENT_BTN);
			wait(4);
			inputText(FIBER_MODEM_INPUT, internetHardwareSerialNo);
			wait(1);
			click(VALIDATE_BUTTON);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(HARDWARE_OK_BUTTON);
			log.debug("Leaving internetFiberModemHardwareSelection");

		} catch (Exception e) {
			log.error("Error in internetFiberModemHardwareSelection:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToLSCResponseJobpaused() {
		try {
			log.debug("Entering navigateToLSCResponseJobpaused");
			String LSCResponseJobURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/common/uobject.jsp?tab=_Triggers&object=9139068419913379360";
			navigate(LSCResponseJobURL);
			wait(1);
			test.log(LogStatus.INFO, "LSC Response Job URL: ",
					"<a href=" + LSCResponseJobURL + " > " + LSCResponseJobURL + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			String LSCResponseJob = null;
			LSCResponseJob = getText(LSC_RESPONSEJOB_STATUS);
			if (LSCResponseJob.equals("Normal")) {
				click(LSC_RESPONSE_JOB_ITEM);
				wait(2);
				click(LSE_RESPONSE_PAUSE_BTN);
				wait(1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			log.debug("Leaving navigateToLSCResponseJobpaused");
		} catch (Exception e) {
			log.error("Error in navigateToLSCResponseJobpaused:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToLSCResponseJobNormal() {
		try {
			log.debug("Entering navigateToLSCResponseJobNormal");
			String LSCResponseJobURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/common/uobject.jsp?tab=_Triggers&object=9139068419913379360";
			navigate(LSCResponseJobURL);
			wait(1);
			test.log(LogStatus.INFO, "LSC Response Job URL: ",
					"<a href=" + LSCResponseJobURL + " > " + LSCResponseJobURL + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			String LSCResponseJob = null;
			LSCResponseJob = getText(LSC_RESPONSEJOB_STATUS);
			if (LSCResponseJob.equals("Paused")) {
				click(LSC_RESPONSE_JOB_ITEM);
				wait(2);
				click(LSE_RESPONSE_RESUME_BTN);
				wait(1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			log.debug("Leaving navigateToLSCResponseJobNormal");
		} catch (Exception e) {
			log.error("Error in navigateToLSCResponseJobNormal:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addPromotionsServiceAgreement(String Phone, String Internet, String TV, String Order) {
		try {
			log.debug("Entering addPromotionsServiceAgreement");
			wait(2);
			click(PROMOTIONS_LINK);
			wait(1);
			switchToChildAlert();
			wait(4);
			click(TWOYVP_INTERNET_TVAGREEMENT);
			wait(9);
			if (Internet.equals("Fibre+ 75")) {
				click(TWOYVP24M_FIBRE_75_PRICEGURANTEE85);
				wait(8);
			}
			if (Internet.equals("Fibre+ 150")) {
				click(TWENTYFOURM_FIBRE150_PRICE_GUARANTEE95);
				wait(5);
			}
			if (Order.equals("Modify")) {
				click(TWOYVP_INTERNET_AGREEMENT);
				wait(2);
				click(TWO_YVP_INTERNET_TV_AGGREEMENT);
				wait(5);
			}
			wait(1);
			click(TV_PROMOTION_BTN);
			wait(4);
			if (TV.equals("Large TV")) {
				scrollDownAndClick(LARGETV_PREBUILT_PRICE_GUARANTE95);
				// click(LARGETV_PREBUILT_PROMO10);
				wait(2);
			}
			if (TV.equals("Limited TV")) {
				click(TWOYVP24M_LIMITEDTV_PRICEGURANTEE);
				wait(2);
			}
			if (TV.equals("Small TV Prebuilt")) {
				click(TWOYVP24M_SMALLTV_PRICEGURANTEE);
				wait(5);
			}
			if (TV.equals("Digital Classic")) {
				click(TWOYVP24M_DIGITAL_CLASSICTV_PRICEGURANTEE90);
				wait(2);
			}
			if (TV.equals("Total TV")) {
				click(TWOYVP24M_TOTALTV_PRICEGURANTEE95);
				wait(2);
			}
			click(PHONE_PROMOTION_BTN);
			wait(1);
			if (Phone.equals("Personal Phone")) {
				// click(THREEM_PERSONAL_PHONE15);
				wait(1);
				click(PERSONAL_PHONE_PG20);
				wait(1);
			}
			do {
				wait(1);
			} while (!isDisplayed(OK_BUTTON_TO_CLOSE));
			wait(1);
			click(OK_BUTTON_TO_CLOSE);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addPromotionsServiceAgreement");
		} catch (Exception e) {
			log.error("Error in addPromotionsServiceAgreement:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addInternetPriceGuarentee(String internet, String order) {
		try {
			log.debug("Entering addPromotionsAndInternetServiceAgreement");
			wait(2);
			click(PROMOTIONS_LINK);
			wait(1);
			switchToChildAlert();
			wait(1);
			if (internet.equals("2YVP Int")) {
				click(TWOYVP_INTERNET_AGREEMENT);
				wait(2);
			}
			if (order.equals("Fibre+ 75")) {
				wait(1);
				click(INTERNET_PROMOTION_BTN);
				wait(2);
				click(TWOYVP24M_FIBRE_75_PRICEGURANTEE85);
			}
			if (order.equals("Fibre+ 300")) {
				wait(1);
				click(INTERNET_PROMOTION_BTN);
				wait(2);
				click(TWOYVP24M_FIBRE_300_PRICEGURANTEE100);
			}
			if (order.equals("Fibre+ 150")) {
				wait(1);
				click(INTERNET_PROMOTION_BTN);
				wait(2);
				click(TWENTYFOURM_FIBRE150_PRICE_GUARANTEE95);
			}
			wait(2);
			click(OK_BUTTON_TO_CLOSE);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addPromotionsAndInternetServiceAgreement");
		} catch (Exception e) {
			log.error("Error in addPromotionsAndInternetServiceAgreement:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addTVPromotionsSrvAgreement(String TV) {
		try {
			log.debug("Entering addTVPromotionsSrvAgreement");
			wait(2);
			click(PROMOTIONS_LINK);
			switchToChildAlert();
			wait(3);
			if (TV.equals("Medium TV")) {
				click(TV_PROMOTION_BTN);
				wait(2);
				click(MEDIUM_TV_PRICE_GUARANTE);
				wait(2);
			}
			if (TV.equals("Digital Classic")) {
				click(TV_PROMOTION_BTN);
				wait(7);
				click(DIGITAL_CLASSIC_TV_PRICE_GUARANTE);
				wait(9);
			}
			if (TV.equals("Modify")) {
				click(TWOYVP_INTERNET_AGREEMENT2);
				wait(2);
				if (isElementPresent(TWO_YVP_INTERNET_TV_AGGREEMENT)) {
					wait(1);
					click(TWO_YVP_INTERNET_TV_AGGREEMENT);
				} else {
					selectFromList(FUNCTIONAL_OPTIONS_DROPDOWNN, "Inbound Sales");
					wait(2);
					click(TWO_YVP_INTERNET_TV_AGGREEMENT);
				}
				wait(2);
				click(TV_PROMOTION_BTN);
				wait(1);
				if (isElementPresent(LARGETV_PREBUILT_PRICE_GUARANTE95)) {
					wait(1);
					click(LARGETV_PREBUILT_PRICE_GUARANTE95);
				}
				if (isElementPresent(TWOYVP24M_LIMITEDTV_PRICEGURANTEE)) {
					wait(1);
					click(TWOYVP24M_LIMITEDTV_PRICEGURANTEE);
				}
				if (isElementPresent(TWOYVP24M_SMALLTV_PRICEGURANTEE)) {
					wait(1);
					click(TWOYVP24M_SMALLTV_PRICEGURANTEE);
				}
				wait(4);
			}
			click(OK_BUTTON_TO_CLOSE);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addTVPromotionsSrvAgreement");
		} catch (Exception e) {
			log.error("Error in addTVPromotionsSrvAgreement:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void largeTVPickOptions11of12() {
		try {
			log.debug("Entering largeTVPickOptions11of12");
			wait(2);
			click(DIGITAL_CLASSIC);
			wait(2);
			click(TV_PICK_OPTION_WRENCH);
			wait(1);
			switchToChildAlert();
			wait(1);
			click(ENT3_POTPOURRI2_RADIO_BTN);
			wait(2);
			click(EMAIL_OK_BUTTON);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving largeTVPickOptions11of12");
		} catch (Exception e) {
			log.error("Error in largeTVPickOptions11of12:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void promotionLinkValidation() {
		try {

			log.debug("Entering promotionLinkValidation");
			wait(2);
			if (isEnabled(PROMOTIONS_LINK) == false) {

				test.log(LogStatus.PASS, "Disabled Promotion link: " + test.addScreenCapture(addScreenshot()));
				log.debug("Leaving promotionLinkValidation");
			}
		} catch (Exception e) {
			log.error("Error in promotionLinkValidation:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void ValidatePromotions() {
		try {
			log.debug("Entering ValidatePromotions");
			wait(2);
			click(PROMOTIONS_LINK);
			switchToChildAlert();
			wait(1);
			if (isElementPresent(FUNCTIONAL_OPTIONS_DROPDOWNN)) {
				selectFromList(FUNCTIONAL_OPTIONS_DROPDOWNN, "General");
//				click(FUNCTIONAL_OPTIONS_DROPDOWNN);
//				wait(1);
//				click(GENERAL_DROPDOWNN);
				wait(1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			
			click(INTERNET_PRODUCT_BTN);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(TWOYVP_INTERNET_TVAGREEMENT);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(TWENTYFOUR_M_TWOYVP_FIBRE_150_PRICEGURANTEE95_CHECK_BOX);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);

			click(TV_PRODUCT_BTN);
			wait(2);
			click(TWENTYFOUR_M_TWOYVP_TOTAL_TV_PRICE_GUARANTEE_95_CHECK_BOX);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			
			/*
			 * wait(1); click(INTERNET_PRODUCT_BTN); wait(1); test.log(LogStatus.INFO,
			 * "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			 * 
			 * click(TV_PRODUCT_BTN); wait(1); test.log(LogStatus.INFO, "Snapshot Below: " +
			 * test.addScreenCapture(addScreenshot()));
			 * 
			 * selectFromList(FUNCTIONAL_OPTIONS_DROPDOWNN, "Inbound Sales"); wait(1);
			 * test.log(LogStatus.INFO, "Snapshot Below: " +
			 * test.addScreenCapture(addScreenshot())); wait(1);
			 * 
			 * click(INTERNET_PRODUCT_BTN); wait(1); test.log(LogStatus.INFO,
			 * "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			 * click(TWOYVP_INTERNET_TVAGREEMENT); wait(2); test.log(LogStatus.INFO,
			 * "Snapshot Below: " + test.addScreenCapture(addScreenshot())); wait(2);
			 * click(TWENTYFOUR_M_TWOYVP_FIBRE_150_PRICEGURANTEE95_CHECK_BOX); wait(1);
			 * 
			 * test.log(LogStatus.INFO, "Snapshot Below: " +
			 * test.addScreenCapture(addScreenshot())); wait(1);
			 * 
			 * click(TV_PRODUCT_BTN); wait(2);
			 * click(TWENTYFOUR_M_TWOYVP_TOTAL_TV_PRICE_GUARANTEE_95_CHECK_BOX); wait(2);
			 * test.log(LogStatus.INFO, "Snapshot Below: " +
			 * test.addScreenCapture(addScreenshot()));
			 */

			click(OK_BUTTON_TO_CLOSE);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving ValidatePromotions");

		} catch (Exception e) {
			log.error("Error in ValidatePromotions:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void ValidatePromotionsinGenral() {
		try {
			log.debug("Entering ValidatePromotionsinGeneral");
			wait(2);
			javascript.executeScript("window.scrollBy(0,-600)", "");
			if (isElementPresent(PROMOTIONS_LINK)) {
				click(PROMOTIONS_LINK);
			}

			switchToChildAlert();
			wait(1);
			if (isElementPresent(FUNCTIONAL_OPTIONS_DROPDOWNN)) {
				selectFromList(FUNCTIONAL_OPTIONS_DROPDOWNN, "General");
//				click(FUNCTIONAL_OPTIONS_DROPDOWNN);
//				wait(1);
//				click(GENERAL_DROPDOWNN);
				wait(1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			wait(1);
			click(INTERNET_PRODUCT_BTN);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			click(TV_PRODUCT_BTN);
			wait(3);
			click(OK_BUTTON_TO_CLOSE);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addTVPromotionsSrvAgreement");

			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		} catch (Exception e) {
			log.error("Error in ValidatePromotionsinGeneral:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void ValidatePromotionsinRetail() {
		try {
			log.debug("Entering ValidatePromotionsinRetail");

			if (isElementPresent(FUNCTIONAL_OPTIONS_DROPDOWNN)) {
				click(FUNCTIONAL_OPTIONS_DROPDOWNN);
				wait(1);
				click(RETAIL_DROPDOWNN);
				wait(1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			wait(1);
			click(INTERNET_PRODUCT_BTN);
//			selectFromList(CONVERGED_HARDWARE_SERIAL_NUMBER_FIELD150_SINGLE_ELEMENT, clecorderPagelink);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			click(TV_PRODUCT_BTN);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);

			click(OK_BUTTON_TO_CLOSE);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addTVPromotionsSrvAgreement");

		} catch (Exception e) {
			log.error("Error in ValidatePromotionsinRetail:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void swapTVMOXIandLegecyHardwares(String swapTVHardwareSlNbr1, String swapTVHardwareSlNbr2) {
		try {
			log.debug("Entering swapTVMOXIandLegecyHardwares");
			wait(3);
			click(TV_HARDWARE_WRENCH);
			wait(1);
			switchToChildAlert();
			wait(1);
			click(SHAW_GATEWAY_HDPVR_524_525_SWAP);
			wait(4);
			click(TECH_RADIO_BTN);
			wait(2);
			inputText(TECHNICIAN_ID, "46501");
			wait(2);
			click(VALIDATE_TECH_ID);
			wait(2);
			inputText(SWAP_SL_NO, swapTVHardwareSlNbr1);
			wait(2);
			click(VALIDATE_SWAP_SLNO);
			wait(2);
			// click(DCX_3200M_501_506_SWAP);
			click(DCT_700_Swap);
			wait(4);
			click(TV_MOXI_RADIO_TECH_BTN);
			wait(2);
			inputText(TECHNICIAN_ID, "46501");
			wait(2);
			click(VALIDATE_TECH_ID);
			wait(2);
			inputText(SWAP_SL_NO, swapTVHardwareSlNbr2);
			wait(2);
			click(VALIDATE_SWAP_SLNO);
			wait(2);
			test.log(LogStatus.PASS, "Swap TV MOXI Legecy Hardware", "Swapped TV MOXI Legecy Hardware Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			do {
				wait(2);
			} while (!isDisplayed(HARDWARE_OK_BUTTON));
			click(HARDWARE_OK_BUTTON);
			wait(1);

			log.debug("Leaving swapTVMOXIandLegecyHardwares");
		} catch (Exception e) {
			log.error("Error in swapTVMOXIandLegecyHardwares:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addPendingPromotions() {
		try {
			log.debug("Entering addPendingPromotions");
			wait(2);
			click(PROMOTIONS_LINK);
			wait(1);
			switchToChildAlert();
			wait(1);
			click(INTERNET_PRODUCT_PROMO);
			wait(7);
			click(TWO_YVP_INTERNET_TV_AGGREEMENT);
			wait(12);
			click(TWENTYFOURM_FIBRE150_PRICE_GUARANTEE95);
			wait(10);
			click(PHONE_PROMOTION_BTN);
			wait(10);
			click(TWO_YVP_PERSONAL_PHONE_PRICE_GURANTEE);
			wait(10);
			click(TV_PROMOTION_BTN);
			wait(10);
			click(TWOYVP24M_DIGITAL_CLASSICTV_PRICEGURANTEE90);
			wait(10);
			click(OK_BUTTON_TO_CLOSE);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addPendingPromotions");
		} catch (Exception e) {
			log.error("Error in addPendingPromotions:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addDigitalChannelSectionTheme() {
		try {
			log.debug("Entering addDigitalChannelSectionTheme_47");
			click(DIGITAL_CHANNEL_THEME);
			wait(9);
			click(HOLLYWOOD_SUITE);
			wait(8);
			click(THEME_BUTTON);
			wait(7);
			switchToChildAlert();
			wait(7);
			log.debug("Leaving addDigitalChannelSectionTheme_47");
		} catch (Exception e) {
			log.error("Error in addDigitalChannelSectionTheme_47:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addPromoMethod(String AgreementType, String methodConfirmation, String Internet) {
		try {
			log.debug("Entering addPromoMethod");
			if (AgreementType.equals("Internet And TV")) {
				wait(1);
				click(TWO_YVP_INTERNET_TV_AGGREEMENT_METHOD);
				wait(2);
			}
			if (isElementPresent(CONFIRM_PROMOTION_RESTART_BTN)) {
				wait(1);
				click(CONFIRM_PROMOTION_RESTART_BTN);
				wait(1);
			}
			if (AgreementType.equals("Internet")) {
				wait(1);
				click(CONFIRM_PROMOTION_BTN);
				wait(2);
			}
			click(METHOD_OF_CONFIRMATION);
			wait(2);
			if (methodConfirmation.equals("Voice")) {
				wait(1);
				selectFromList(METHOD_OF_CONFIRMATION, "Voice");
			}
			if (methodConfirmation.equals("Email")) {
				wait(1);
				selectFromList(METHOD_OF_CONFIRMATION, "Email");
			}
			if (methodConfirmation.equals("Electronic Signature")) {
				wait(1);
				selectFromList(METHOD_OF_CONFIRMATION, "Electronic Signature");
			}
			wait(2);
			inputText(ACCEPTANCE_ID_TEXT, "Ramesh Automation");
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(METHOD_CLOSE);
			wait(1);
			if (Internet.equals("Internet300")) {
				wait(1);
				click(PROMOTIONS_LINK);
				wait(1);
				switchToChildAlert();
				wait(1);
				if (isElementPresent(TYVP_INTERNET300_PG85)) {
					wait(1);
					scrollDownAndClick(TYVP_INTERNET300_PG85);
				}
				wait(2);
				click(OK_BUTTON_TO_CLOSE);
				wait(1);
			}
			if (Internet.equals("Internet 50")) {
				wait(1);
				click(PROMOTIONS_LINK);
				wait(1);
				switchToChildAlert();
				wait(1);
				scrollDownAndClick(TWENTYFOUR_M_INT_50PROMO_70);
				wait(2);
				click(OK_BUTTON_TO_CLOSE);
				wait(1);
			}
			if (Internet.equals("Modify Confirmation")) {
				wait(1);
				click(TWO_YVP_INTERNET_TV_AGGREEMENT_METHOD1);
				wait(1);
			}
			log.debug("Leaving addPromoMethod");

		} catch (Exception e) {
			log.error("Error in addPromoMethod:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addSouthAsian4Pack1() {
		try {
			log.debug("Entering addSouthAsian4Pack1");
			wait(3);
			click(MULTICULTURAL_TAB);
			wait(3);
			click(SOUTHASIAN4_PACK1_CHECKBOX);
			wait(3);
			click(SOUTHASIAN4_PACK1_WRENCH);
			wait(2);
			switchToChildAlert();
			wait(4);
			click(ATNASIAN_TELEVISION_NW_CHECKBOX);
			wait(4);
			click(AAPKA_COLOURS_CHECKBOX);
			wait(4);
			click(MH1_CHECKBOX);
			wait(6);
			click(ZEETV_CHECKBOX);
			wait(6);
			click(SOUTH_ASSIAN_PACK_OKBTN);
			wait(6);
			click(ONDEMAND_TAB);
			wait(4);
			click(SATURDAY_NYT_CHECKBOX);
			wait(4);
			click(EMAIL_OK_BUTTON);
			log.debug("Leaving addSouthAsian4Pack1");

		} catch (Exception e) {
			log.error("Error in addSouthAsian4Pack1:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addServiceTVWithBlueSkyWithoutSlNos(String prod) {
		try {
			log.debug("Entering addServiceTVWithBlueSkyWithoutSlNos");
			if (prod.equals("Limited TV")) {
				click(TV_HARDWARE_WRENCH);
				wait(1);
				switchToChildAlert();
				click(RENT_DCX3510_HDGUIDE);
				wait(2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				click(HARDWARE_OK_BUTTON);
			} else {
				if (prod.equals("Limited TV")) {
					click(TV_HARDWARE_WRENCH);
				}
				if (prod.equals("Small TV Prebuilt")) {
					click(TV_HARDWARE_WRENCH);
				}
				wait(1);
				switchToChildAlert();
				click(RENT_SHAW_BLUESKY_TVBOX526);
				wait(2);
				click(RENT_SHAW_BLUESKY_TVPORTAL416);
				wait(2);
				test.log(LogStatus.PASS, "Add TV BlueSky Product", "Added TV Product WithBlueSky Successfully");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				click(HARDWARE_OK_BUTTON);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(2);
			}
			log.debug("Leaving addServiceTVWithBlueSkyWithoutSlNos");
		} catch (Exception e) {
			log.error("Error in addServiceTVWithBlueSkyWithoutSlNos:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToImmediateFeatures(String immediateTab) {
		try {
			log.debug("Entering navigateToImmediateFeatures");
			if (immediateTab.equalsIgnoreCase("Billing Preferences")) {
				wait(4);
				click(IMMEDIATE_BILLING_PREFERENCES);
				wait(6);
				click(BILLING_PREFERENCES_MORE);
				wait(5);
				click(SET_PRFERRED_PAYMENT_DUE_DATE);
				wait(5);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				inputText(BILL_ONTHIS_DAY_OF_MONTH, "2");
				wait(1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				click(OK_BUTTON_TO_CLOSE);
			}
			if (immediateTab.equalsIgnoreCase("Phone Features")) {
				click(IMMEDIATE_PHONE_FEATURE);
				wait(10);
				click(LONG_DISTANCE_PRIVILEGES);
				wait(2);
				click(LONG_DISTANCE_OPTION);
				wait(2);
				click(BILL_TO_3RD_ENABLE_RADIO_BTN);
				wait(2);
				click(COLLEECT_CALLS_DISABLE_RADIO_BTN);
				wait(2);
				click(DIRECTORY_ASSISTANCE_DISABLE_RADIO_BTN);
				wait(2);
				inputText(IMMEDIATE_CHANGE_AUTHORIZED_TEXT, "Integration Test");
				wait(2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				click(APPLY_CHANGES);
				wait(3);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			if (immediateTab.equalsIgnoreCase("Email")) {
				click(IMMEDIATE_EMAIL_LINK);
				wait(9);
				click(CREATE_TRANSFER_EMAIL_BTN);
				wait(4);
				inputText(IMMEDIATE_EMAIL_ADDRESS, Utility.generateRandomEmailId());
				wait(4);
				emailAddress = getText(IMMEDIATE_EMAIL_ADDRESS);
				wait(4);
				click(IMMEDIATE_EMAIL_VALIDATE);
				wait(3);
				inputText(IMMEDIATE_FIRST_NAME, "AutoFirst");
				wait(1);
				inputText(IMMEDIATE_LAST_NAME, "Autolast");
				wait(1);
				inputText(IMMEDIATE_CHANGE_AUTHORIZED_TEXT, "Integration Test");
				wait(1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				click(APPLY_CHANGES);
				wait(2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(2);
			}
			if (immediateTab.equalsIgnoreCase("Hardware")) {
				click(IMMEDIATE_HARDWARE_LINK);
				wait(6);
				if (isElementPresent(IMMEDIATE_REPROVISION_BTN2)) {
					test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
					test.log(LogStatus.INFO, "Phone ReProvision Btn: ",
							"'Reprovision' button should be available only for Internet device & should not be available for DPT device");
					Assert.fail();
				}
				wait(3);
				click(IMMEDIATE_REPROVISION_BTN);
				wait(2);
				if (isDisplayed(IMMEDIATE_REPROVISION_BTN_MSG)) {
					String msg = getText(IMMEDIATE_REPROVISION_BTN_MSG);
					Assert.assertEquals(msg, "This hardware will be reset");
				} else {
					test.log(LogStatus.FAIL, "ReProvision Btn Msg: ",
							"In correct or ReProvision Button Msg not displayed");
					Assert.fail();
				}
				wait(1);
				if (isDisplayed(IMMEDIATE_UNDO_REPROVISION_BTN)) {
					test.log(LogStatus.INFO, "Undo ReProvision Btn: ", "Undo ReProvision Btn displayed");
				}

				inputText(IMMEDIATE_CHANGE_AUTHORIZED_TEXT, "Integration Test");
				wait(1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				click(APPLY_CHANGES);
				wait(2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				log.debug("Leaving navigateToImmediateFeatures");
			}
		} catch (Exception e) {
			log.error("Error in navigateToImmediateFeatures:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToImmediateChangesPage(String accountNum) {
		try {
			log.debug("Entering navigateToImmediateChangesPage");
			String immediateScreenURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/oe.immediateChanges.nc?accountId=1" + accountNum + "&locationId=221";
			navigate(immediateScreenURL);
			wait(1);
			test.log(LogStatus.INFO, "Immediate Screen URL: ",
					"<a href=" + immediateScreenURL + " > " + immediateScreenURL + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(3);
			log.debug("Leaving navigateToImmediateChangesPage");
		} catch (Exception e) {
			log.error("Error in navigateToImmediateChangesPage:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void disconnectAppointmentDate() {
		try {
			log.debug("Entering disconnectAppointmentDate");
			wait(2);
			click(APPOINTMENT);
			wait(7);
			click(CLICK_CALENDAR);
			wait(2);
			getWebElement(CLICK_CALENDAR).sendKeys(Keys.ENTER);
			wait(2);
			inputText(CHANGEAUTHORIZEDBY, "Integration Test");
			test.log(LogStatus.PASS, "Add Disconnect Appointment Date",
					"Disconnect Appointment date added Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			log.debug("Leaving disconnectAppointmentDate");
		} catch (Exception e) {
			log.error("Error in disconnectAppointmentDate:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void retrieveOrderInformation() {
		try {
			log.debug("Entering retrieveOrderInformation");
			wait(1);
			click(RETRIEVE_ORDER_INFORMATION);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			// click(FINISH);
			log.debug("Leaving retrieveOrderInformation");
		} catch (Exception e) {
			log.error("Error in retrieveOrderInformation:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void changeHwToMOCAEnabled(String serialNoBox) {
		try {
			log.debug("Entering changeHwToMOCAEnabled");
			wait(2);
			click(TV_HARDWARE_WRENCH);
			wait(1);
			switchToChildAlert();
			click(DELETE_HARDWARE);
			wait(2);
			click(RENT_SHAW_BLUESKY_TV4KBOX_530);
			wait(2);
			inputText(SHAW_BLUESKY_TV4KBOX_530_SLNO_INPUT, serialNoBox);
			wait(2);
			click(VALIDATE_SRL_NO);
			wait(1);
			do {
				wait(2);
			} while (!isDisplayed(HARDWARE_OK_BUTTON));
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			click(HARDWARE_OK_BUTTON);
			wait(1);
			test.log(LogStatus.PASS, "Add TV BlueSky Product", "Added TV Product With MOCA BlueSky Successfully");
			log.debug("Leaving changeHwToMOCAEnabled");
		} catch (Exception e) {
			log.error("Error in changeHwToMOCAEnabled:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToErrorTaskProcesses() {
		try {
			log.debug("Entering navigateToErrorTaskProcesses");
			wait(1);
			String errorTaskURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/common/uobject.jsp?tab=_Erroneous+Processes&object=9070642380013265348";
			navigate(errorTaskURL);
			wait(1);
			test.log(LogStatus.INFO, "Immediate Screen URL: ",
					"<a href=" + errorTaskURL + " > " + errorTaskURL + " </a>");
			click(ERRONEOUS_PROCESS_TASK);
			wait(2);
			click(ERRORS_TAB);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(ERRORS_IN_FFM_SERVICE_CALL);
			wait(2);
			click(PARAMETERS_TAB);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToErrorTaskProcesses");
		} catch (Exception e) {
			log.error("Error in navigateToErrorTaskProcesses:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addRentalXpods(String account, String xpod1, String xpod2, String xpod3, String xpod4,
			String xpodPack) {
		try {
			log.debug("Entering addRentalXpods");
			wait(2);
			click(INTERNET_HARDWARE);
			wait(2);
			switchToChildAlert();
			wait(2);
			if (account.equals("Staff")) {
				click(XPOD_3PK_RENT);
				wait(1);
				inputText(XPOD1_3PK_INPUT, xpod1);
				wait(1);
				click(VALIDATE_SRL_NO);
				wait(2);
				inputText(XPOD2_3PK_INPUT, xpod2);
				wait(1);
				click(VALIDATE_SRL_NO);
				wait(2);
				inputText(XPOD3_3PK_INPUT, xpod3);
				wait(1);
				click(VALIDATE_SRL_NO);
				wait(2);
			}
			if (account.equals("Residential")) {
				click(BLUECURVE_PODSV1_3PK_RENT_BTN);
				wait(1);
				inputText(BLUECURVE_PODSV1_INPUT, xpod1);
				wait(1);
				click(VALIDATE_SRL_NO);
				wait(2);
				inputText(BLUECURVE_PODSV1_INPUT, xpod2);
				wait(1);
				click(VALIDATE_SRL_NO);
				wait(2);
				inputText(BLUECURVE_PODSV1_INPUT, xpod3);
				wait(1);
				click(VALIDATE_SRL_NO);
				wait(2);
			}
			if (xpodPack.equals("true")) {
				click(PODS1PK_RENT);
				wait(1);
				inputText(PODS1PK_INPUT, xpod4);
				wait(2);
				click(VALIDATE_SRL_NO);
				wait(2);
			}
			do {
				wait(1);
			} while (!isDisplayed(HARDWARE_OK_BUTTON));
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			click(HARDWARE_OK_BUTTON);
		} catch (Exception e) {
			log.error("Error in addRentalXpods:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addRentalXpodsWithoutSrlNbrs() {
		try {
			log.debug("Entering addRentalXpodsWithoutSrlNbrs");
			wait(1);
			click(INTERNET_HARDWARE);
			wait(1);
			switchToChildAlert();
			wait(1);
			click(BLUECURVE_PODSV1_3PK_RENT_BTN);
			wait(1);
			do {
				wait(1);
			} while (!isDisplayed(HARDWARE_OK_BUTTON));
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			click(HARDWARE_OK_BUTTON);
		} catch (Exception e) {
			log.error("Error in addRentalXpods:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToCustOrderISEDPagePremise(String acctNbr) {
		try {
			log.debug("Entering navigateToCustOrderISEDPagePremise");
			String ISEDcustOrderPageUrl = Utility.getValueFromPropertyFile("basepage_url")
					+ "/oe.newCustomerDesktop.nc?accountId=1" + acctNbr
					+ "&locationId=221&newLocationId=155&isedPostalCode=ABC&isedPin=123";
			navigate(ISEDcustOrderPageUrl);
			wait(1);
			test.log(LogStatus.INFO, "PremiseMove ISED Cust URL: ",
					"<a href=" + ISEDcustOrderPageUrl + " > " + ISEDcustOrderPageUrl + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(3);
			log.debug("Leaving navigateToCustOrderISEDPagePremise");
		} catch (Exception e) {
			log.error("Error in navigateToCustOrderISEDPagePremise:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public String validateCustomerTabforISED() throws Exception {
		String ISEDPin = "";
		log.debug("Entering validateCustomerTabforISED");
		wait(2);
		click(CUSTOMER_AND_ID_TAB);
		log.debug("Navigating to Customer Information Tab");
		wait(3);
		ISEDPin = convertPhoneToString(getText(ISED_PIN_VALUE));
		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		log.debug("Leaving validateCustomerTabforISED");
		return ISEDPin;
	}

	public void customerIDEmailAdress() {
		try {
			log.debug("Entering customerIDEmailAdress");
			wait(3);
			inputText(CUST_EMAIL_ADDRESS, Utility.getValueFromPropertyFile("customerEmailAddress"));
			wait(2);
			log.debug("Leaving customerIDEmailAdress");
		} catch (Exception e) {
			log.error("Error in customerIDEmailAdress:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void deleteHardware(String LOB) {
		try {
			log.debug("Entering deleteHardware");
			wait(2);
			click(INTERNET_HARDWARE);
			wait(2);
			if (LOB.equals("Switch Window")) {
				switchToChildAlert();
			}
			wait(1);
			click(DELETE_HARDWARE);
			wait(2);
			click(HARDWARE_OK_BUTTON);
			wait(1);
			log.debug("Leaving deleteHardware");
		} catch (Exception e) {
			log.error("Error in deleteHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addNetFlixPromo(String prod) {
		try {
			log.debug("Entering addNetFlixPromo");
			wait(2);
			click(PROMOTIONS_LINK);
			switchToChildAlert();
			wait(2);
			if (prod.equalsIgnoreCase("Bundles")) {
				click(BUNDLES_LINK);
				wait(2);
				click(TAWELM_2YVP_NETFLIX_PROMO);
				wait(2);
			}
			if (prod.equalsIgnoreCase("Internet")) {
				click(INTERNET_PRODUCT_PROMO);
				wait(2);
				click(TWOYVP_INTERNET_AGREEMENT1);
				wait(2);
				scrollDownAndClick(TWENTYFOURM_PRICE_GUARANTEE90);
				wait(1);
			}
			click(OK_BUTTON_TO_CLOSE);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addNetFlixPromo");
		} catch (Exception e) {
			log.error("Error in addNetFlixPromo:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void clickFinishOrder() {
		try {
			log.debug("Entering clickFinishOrder");
			click(REVIEW);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(FINISH);
			wait(30);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving clickFinishOrder");
		} catch (Exception e) {
			log.error("Error in clickFinishOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToOperationsPortIn(String selectType, String webHosting, String shawServiceRequest) {
		try {
			log.debug("Entering navigateToOperationsPortIn");
			wait(1);
			click(OPERATIONS_TAB);
			wait(4);
			click(PORTIN_OPTION);
			wait(4);
			switchToChildAlert();
			click(PORTIN_DISCONNECT);
			wait(4);
			selectFromList(SELECTTYPE_LIST, selectType);
			wait(4);
			inputText(PROVIDER_ACCOUNT_NUMBER, "9898262661");
			wait(4);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(RESELLER_DROP_DOWN);
			wait(4);
			// click(RESELLER_DROP_DOWN);
			wait(3);
			if (selectType.equalsIgnoreCase("Internet")) {
				wait(3);
				click(RESELLER_VALUE_INTERNET);
			} else if (selectType.equalsIgnoreCase("Tv")) {
				wait(3);
				click(RESELLER_VALUE_TV);
			}
			wait(3);
			if (selectType.equalsIgnoreCase("Internet")) {
				if (webHosting.equalsIgnoreCase("Yes")) {
					wait(1);
					click(WEBHOSTING_YES);
					wait(2);
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				} else {
					click(WEBHOSTING_NO);
					wait(2);
					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				}
			}
			if (shawServiceRequest.equalsIgnoreCase("Yes")) {

				click(SHAW_SERVICE_REQUESTED_YES);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(1);
				click(SELECT_ADDRESS);
				wait(1);
				inputText(OLD_HOUSE_NBR_TXT, "630");
				wait(2);
				click(OLD_STREET_NAME);
				wait(2);
				inputText(OLD_STREET_NAME, "Ave SW");
				wait(2);
				click(OLD_CITY);
				wait(2);
				inputText(OLD_CITY, "Calgary");
				wait(2);
				click(OLD_POSTAL_CODE);
				wait(2);
				inputText(OLD_POSTAL_CODE, "T2P4L4");
				wait(2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			} else {
				click(SHAW_SERVICE_REQUESTED_NO);
				wait(2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			click(OK_BUTTON_TO_CLOSE);
			wait(3);

		} catch (Exception e) {
			log.error("Error in navigateToOperationsPortIn:" + e.getMessage());
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void reviewOrder() {
		try {
			log.debug("Entering reviewOrder");
			click(REVIEW);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving reviewOrder");
		} catch (Exception e) {
			log.error("Error in reviewOrder:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToOrderValidation(int orderLink, String reportRequired, String taskTabRequired,
			String orderType) {
		try {
			log.debug("Entering navigateToOrderValidation");
			if (orderType.equalsIgnoreCase("COM")) {
				log.debug("Entering to click COM orders link");
				wait(1);
				click(COM_ORDER_TAB);
				wait(1);
				switch (orderLink) {
				case 1:
					click(NEW_CUSTOMER_ORDER);
					break;
				case 2:
					click(NEW_CUSTOMER_ORDER_2);
					break;
				case 3:
					click(NEW_CUSTOMER_ORDER_3);
					break;
				case 4:
					click(NEW_PHONELINE_CFS_ORDER1);
					break;
				case 5:
					click(NEW_PHONE_HARDWARE_CFS_ORDER1);
					break;
				case 6:
					click(NEW_PHONE_HARDWARE_CFS_ORDER2);
					break;
				case 7:
					click(NEW_PHONE_HARDWARE_CFS_ORDER3);
					break;
				case 8:
					click(NEW_INTERNET_HARDWARE_CFS_ORDER1);
					break;
				case 9:
					click(NEW_INTERNET_HARDWARE_CFS_ORDER2);
					break;
				case 10:
					click(NEW_INTERNET_HARDWARE_CFS_ORDER3);
					break;
				case 11:
					click(NEW_TV_HARDWARE_CFS_ORDER1);
					break;
				case 12:
					click(NEW_TV_HARDWARE_CFS_ORDER2);
					break;
				case 13:
					click(NEW_TV_HARDWARE_CFS_ORDER3);
					break;
				case 14:
					click(NEW_TV_HARDWARE_CFS_ORDER4);
					break;
				case 15:
					click(NEW_CONVERGED_HARDWARE_CFS_ORDER);
					break;
				case 16:
					click(NEW_CONVERGED_HARDWARE_CFS_ORDER2);
					break;
				case 17:
					click(NEW_BILLING_SERVICE_ORDER);
					break;
				case 18:
					click(NEW_BILLING_SERVICE_ORDER_2);
					break;
				case 19:
					click(NEW_BILLING_SERVICE_ORDER_3);
					break;
				case 20:
					click(NEW_NON_PROVISIONABLE_HARWARE_ORDER1);
					break;
				case 21:
					click(NEW_NON_PROVISIONABLE_HARWARE_ORDER2);
					break;
				case 22:
					click(NEW_NON_PROVISIONABLE_HARWARE_ORDER3);
					break;
				case 23:
					click(NEW_DISTINCTIVE_RING_CFS_ORDER);
					break;
				case 24:
					click(NEW_CLEC_CFS_ORDER);
					break;
				case 25:
					click(MODIFY_CUSTOMER_ORDER1);
					break;
				case 26:
					click(MODIFY_CUSTOMER_ORDER2);
					break;
				case 27:
					click(MODIFY_CUSTOMER_ORDER3);
					break;
				case 28:
					click(MODIFY_TV_HARDWARE_CFS_ORDER_1);
					break;
				case 29:
					click(MODIFY_TV_HARDWARE_CFS_ORDER_2);
					break;
				case 30:
					click(MODIFY_INTERNET_CFS_ORDER);
					break;
				case 31:
					click(MODIFY_CONVERGRED_HARDWARE_CFS_ORDER_LINK);
					break;
				case 32:
					click(MODIFY_BILLING_SERVICE_ORDER1);
					break;
				case 33:
					click(MODIFY_BILLING_SERVICE_ORDER2);
					break;
				case 34:
					click(MODIFY_BILLING_SERVICE_ORDER3);
					break;
				case 35:
					click(DISCONNECT_CUSTOMER_ORDER);
					break;
				case 36:
					click(CHARGE_HARDWARE_REQUEST_FOR_SN1);
					break;
				case 37:
					click(CHARGE_HARDWARE_REQUEST_FOR_SN2);
					break;
				case 38:
					click(CHARGE_HARDWARE_REQUEST_FOR_SN3);
					break;
				case 39:
					click(SUSPEND_EMAIL_CFS_ORDER);
					break;
				case 40:
					click(PHONE_LINE_IMMEDIATE_ORDER);
					break;
				case 41:
					click(MODIFY_PHONE_HARDWARE_CFS_ORDER1);
					break;
				case 42:
					click(DISCONNECT_BILLING_SERVICE_ORDER);
					break;
				case 43:
					click(NEW_BILLING_SERVICE_ORDER_5);
					break;
				case 44:
					click(SWAP_HARDWAREIMMEDIATE_ORDER);
					break;
				case 45:
					click(MODIFY_INTERNET_HARDWARE_CFS_ORDER1);
					break;
				case 46:
					click(MODIFY_INTERNET_HARDWARE_CFS_ORDER2);
					break;
				case 47:
					click(MODIFY_PHONE_HARDWARE_CFS_ORDER2);
					break;
				case 48:
					click(MODIFY_PHONE_HARDWARE_CFS_ORDER3);
					break;
				case 49:
					click(MODIFY_CUSTOMER_ORDER4);
					break;
				case 50:
					click(MODIFY_INTERNET_HARDWARE_CFS_ORDER3);
					break;
				case 51:
					click(MODIFY_TV_HARDWARE_CFS_ORDER_3);
					break;
				case 52:
					scrollDownAndClick(NEW_CUSTOMER_ORDER_5);
					break;
				case 53:
					click(HARDWARE_RETURN_ORDER);
					break;
				case 54:
					click(CANCEL_NEW_CUSTOMER_ORDER);
					break;
				case 55:
					click(NEW_PHONELINE_CFS_ORDER2);
					break;
				case 56:
					click(CHARGE_HARDWARE_REQUEST_FOR_SN4);
					break;
				case 57:
					click(INFLIGHT_NEW_INT_HW_CFS_ORDER);
					break;
				case 58:
					click(INFLIGHT_MODIFY_CUST_ORDER);
					break;
				case 59:
					click(INFLIGHT_NEW_CUST_ORDER);
					break;
				case 60:
					click(INFLIGHT_NEW_BILLING_SERVICE_ORDER);
					break;
				case 61:
					click(INFLIGHT_MODIFY_BILLING_ORDER);
					break;
				case 62:
					click(INFLIGHT_NEW_CONV_HW_CFS_ORDER);
					break;
				case 63:
					scrollDownToElement(NEW_PHONE_HARDWARE_CFS_ORDER7);
					click(NEW_PHONE_HARDWARE_CFS_ORDER7);
					break;
				case 64:
					scrollDownToElement(NEW_TV_HARDWARE_CFS_ORDER5);
					click(NEW_TV_HARDWARE_CFS_ORDER5);
					break;
				case 65:
					scrollDownToElement(INFLIGHT_NEW_CUST_ORDER3);
					click(INFLIGHT_NEW_CUST_ORDER3);
					break;
				case 66:
					click(INFLIGHT_NEW_BILLING_SERVICE_ORDER3);
					break;
				case 67:
					click(INFLIGHT_NEW_CUST_ORDER2);
					break;

				default:
					log.debug("No link provided. as the link number not found on Page");
				}
				wait(1);
				if (reportRequired.equalsIgnoreCase("Yes")) {
					wait(1);
					click(ORDER_PARAMETERS);
					wait(1);
					click(INTEGRATION_REPORT);
					wait(3);
					String comOrderPage = getCurrentURL();
					test.log(LogStatus.INFO, "Integration Report URL: ",
							"<a href=" + comOrderPage + " > " + comOrderPage + " </a>");
				}
				if (taskTabRequired.equalsIgnoreCase("Yes")) {
					wait(1);
					click(TASK_TAB);
					test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				}
				wait(1);
				test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			} else if (orderType.equalsIgnoreCase("SOM")) {
				log.debug("Entering To click SOM orders link");
				wait(1);
				click(SOM_ORDER_TAB);
				wait(1);
				switch (orderLink) {
				case 1:
					click(NEW_SOM_PHONE_LINE_CFS_ORDER);
					break;
				case 2:
					click(NEW_SOM_PHONE_LINE_RFS_ORDER);
					break;
				case 3:
					click(NEW_PHONE_PROVISIONING_RFS_ORDER1);
					break;
				case 4:
					click(NEW_PHONE_PROVISIONING_RFS_ORDER2);
					break;
				case 5:
					click(NEW_PHONE_PROVISIONING_RFS_ORDER3);
					break;
				case 6:
					click(NEW_PHONE_PROVISIONING_RFS_ORDER4);
					break;
				case 7:
					click(NEW_INTERNET_PROVISIONING_RFS_ORDER1);
					break;
				case 8:
					click(NEW_INTERNET_PROVISIONING_RFS_ORDER2);
					break;
				case 9:
					click(NEW_INTERNET_PROVISIONING_RFS_ORDER3);
					break;
				case 10:
					click(NEW_TV_PROVISIONING_RFS_ORDER_1);
					break;
				case 11:
					click(NEW_TV_PROVISIONING_RFS_ORDER_2);
					break;
				case 12:
					click(NEW_TV_PROVISIONING_RFS_ORDER_3);
					break;
				case 13:
					click(NEW_TV_PROVISIONING_RFS_ORDER_4);
					break;
				case 14:
					click(NEW_TV_PROVISIONING_RFS_ORDER_5);
					break;
				case 15:
					click(NEW_TV_PROVISIONING_RFS_ORDER_6);
					break;
				case 16:
					click(NEW_EMAIL_PROVISIONING_RFS_ORDER);
					break;
				case 17:
					click(NEW_WIFI_PROVISIONING_RFS_ORDER);
					break;
				case 18:
					click(NEW_DISTINCTIVE_RING_RFS_ORDER);
					break;
				case 19:
					click(NEW_VOICEMAIL_PROVISIONING_RFS_ORDER1);
					break;
				case 20:
					click(MODIFY_PHONE_PROVISIONING_RFS_ORDER);
					break;
				case 21:
					click(MODIFY_PHONE_PROVISIONING_RFS_ORDER2);
					break;
				case 22:
					click(MODIFY_TV_PROVISING_RFS_ORDER1);
					break;
				case 23:
					click(MODIFY_TV_PROVISING_RFS_ORDER2);
					break;
				case 24:
					click(MODIFY_INTERNET_PROVISIONING_RFS_ORDER);
					break;
				case 25:
					click(RESUME_PHONE_PROVISIONING_RFS_ORDER);
					break;
				case 26:
					click(RESUME_TV_PROVISIONING_RFS_ORDER1);
					break;
				case 27:
					click(RESUME_TV_PROVISIONING_RFS_ORDER2);
					break;
				case 28:
					click(RESUME_INTERNET_PROVISIONING_RFS_ORDER);
					break;
				case 29:
					click(SUSPEND_PHONE_PROVISIONING_RFS_ORDER);
					// scrollDownAndClick(SUSPEND_PHONE_PROVISIONING_RFS_ORDER);
					break;
				case 30:
					click(SUSPEND_INTERNET_PROVISIONING_RFS_ORDER);
					break;
				case 31:
					click(SUSPEND_TV_PROVISIONING_RFS_ORDER1);
					break;
				case 32:
					click(SUSPEND_TV_PROVISIONING_RFS_ORDER2);
					break;
				case 33:
					click(DISCONNECT_PHONE_PROVISIONING_RFS_ORDER);
					break;
				case 34:
					click(DISCONNECT_INTERNET_PROVISIONING_RFS_ORDER);
					break;
				case 35:
					click(DISCONNECT_TV_PROVISIONING_RFS_ORDER1);
					break;
				case 36:
					click(DISCONNECT_TV_PROVISIONING_RFS_ORDER2);
					break;
				case 37:
					click(DISCONNECT_WIFI_PROVISIONING_RFS_ORDER);
					break;
				case 38:
					click(CANCLE_TV_PROVISIONONG_ORDER);
					break;
				case 39:
					click(CANCEL_WIFI_PROVISIONING_RFS_ORDER);
					break;
				case 40:
					scrollDownAndClick(NEW_TV_PROVISIONING_RFS_ORDER_9);
					break;
				case 41:
					click(SUSPEND_EMAIL_PROVISIONING_RFS_ORDER);
					break;
				case 42:
					click(RESUME_TV_PROVISIONING_RFS_ORDER3);
					break;
				case 43:
					click(RESUME_TV_PROVISIONING_RFS_ORDER4);
					break;
				case 44:
					click(MODIFY_SOM_PHONELINE_BNSRFS_ORDER);
					break;
				case 45:
					click(NEW_EMAIL_PROVISIONING_RFS_ORDER_3);
					break;
				case 46:
					click(DISCONNECT_TV_HARDWARE_CFS_ORDER1);
					break;
				case 47:
					click(DISCONNECT_TV_HARDWARE_CFS_ORDER2);
					break;
				case 48:
					click(DISCONNECT_TV_HARDWARE_CFS_ORDER3);
					break;
				case 49:
					click(DISCONNECT_TV_HARDWARE_CFS_ORDER4);
					break;
				case 50:
					click(DISCONNECT_TV_HARDWARE_CFS_ORDER5);
					break;
				case 51:
					click(NON_PROVISIONABLE_HW_RFS_ORDER1);
					break;
				case 52:
					click(NON_PROVISIONABLE_HW_RFS_ORDER2);
					break;
				case 53:
					click(NON_PROVISIONABLE_HW_RFS_ORDER3);
					break;

				default:
					log.debug("No link provided as the link number not found on Page");
				}
				wait(1);
				if (reportRequired.equalsIgnoreCase("Yes")) {
					wait(1);
					click(ORDER_PARAMETERS);
					wait(1);
					click(INTEGRATION_REPORT);
					wait(3);
					String somOrderPage = getCurrentURL();
					test.log(LogStatus.INFO, "Integration Report URL: ",
							"<a href=" + somOrderPage + " > " + somOrderPage + " </a>");
				}
				if (taskTabRequired.equalsIgnoreCase("Yes")) {
					click(TASK_TAB);
					test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				}
				test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			} else {
				log.debug("Please validate the parameters passed to navigateToOrderValidation");
			}
			log.debug("Leaving Method navigateToOrderValidation");
		} catch (Exception e) {
			log.error("Error in navigateToOrderValidation:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addInternetHardwareWithoutSlNos(String internetRentSelection) {
		try {
			log.debug("Entering addInternetHardwareWithoutSlNos");

			if (internetRentSelection.equalsIgnoreCase("Cisco")) {
				wait(2);
				click(INTERNET_HARDWARE);
				wait(2);
				click(INTERNET150_HITRONRENT);
			}
			if (internetRentSelection.equalsIgnoreCase("Hitron")) {
				wait(2);
				click(INTERNET_HARDWARE);
				wait(2);
				click(INTERNET150_HITRONRENT);
			}
			if (internetRentSelection.equalsIgnoreCase("3nCisco")) {
				wait(2);
				click(INTERNET_HARDWARE);
				wait(2);
				click(INTERNET75_RENT);
			}
			wait(1);
			do {
				wait(2);
			} while (!isDisplayed(HARDWARE_OK_BUTTON));
			test.log(LogStatus.PASS, "Added Internet Harware", "Added Internet Harware Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(HARDWARE_OK_BUTTON);
			log.debug("Leaving addInternetHardwareWithoutSlNos");
		} catch (Exception e) {
			log.error("Error in addInternetHardwareWithoutSlNos:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addPhoneType(String prod) {
		try {
			log.debug("Entering addPhoneType");
			wait(2);
			selectFromList(PHONE_PROD_LIST, prod);
			wait(2);
			if (prod.equalsIgnoreCase("Personal Phone")) {
				click(PERSONAL_PHONE_SELECT);
				wait(2);
			}
			if (prod.equalsIgnoreCase("Employee Personal Phone")) {
				click(EMPLOYEE_PERSONAL_PHONE_SELECT);
				wait(2);
			}
			test.log(LogStatus.PASS, "Add Phone Service", "Added Phone Service Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addServicePhone");
		} catch (Exception e) {
			log.error("Error in addPhoneType:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToCustOrderISEDPage(String newTabExists, String acctNbr) {
		try {
			log.debug("Entering navigateToCustOrderISEDPage");
			String ISEDcustOrderPageUrl = Utility.getValueFromPropertyFile("basepage_url")
					+ "/oe.newCustomerDesktop.nc?isedPostalCode=pin&isedPin=4444&accountId=1" + acctNbr
					+ "&locationId=221";
			if (newTabExists.equalsIgnoreCase("true"))
				switchSecondNewTab();
			navigate(ISEDcustOrderPageUrl);
			wait(1);
			test.log(LogStatus.INFO, "ISED Customer URL: ",
					"<a href=" + ISEDcustOrderPageUrl + " > " + ISEDcustOrderPageUrl + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			log.debug("Leaving navigateToCustOrderISEDPage");
		} catch (Exception e) {
			log.error("Error in navigateToCustOrderISEDPage:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void autoRetryTask() {
		try {
			log.debug("Entering autoRetryTask");
			String AutoRetryURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/ncobject.jsp?id=9151527495813597627";
			navigate(AutoRetryURL);
			wait(1);
			test.log(LogStatus.INFO, "Auto Retry URL: ", "<a href=" + AutoRetryURL + " > " + AutoRetryURL + " </a>");
			String nbrOfRetry = getText(NBR_OF_RETRY);
			log.debug("Number of Retries=" + nbrOfRetry);
			test.log(LogStatus.INFO, "Number of Retries=" + nbrOfRetry);
			wait(1);
			String timeIncrement = getText(TIME_INCREAMENT);
			log.debug("Time Increment=" + timeIncrement);
			test.log(LogStatus.INFO, "Time Increment=" + timeIncrement);
			wait(1);
			String sleepIntervalSec = getText(SLEEP_INTERVAL_SECOND);
			log.debug("Sleep Internal Second=" + sleepIntervalSec);
			test.log(LogStatus.INFO, "Sleep Internal Second=" + sleepIntervalSec);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving autoRetryTask");
		} catch (Exception e) {
			log.error("Error in autoRetryTask:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigatetoAppointmentTab() {
		try {
			log.debug("Entering navigatetoAppointmentTab");
			wait(2);
			click(APPOINTMENT);
			wait(7);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigatetoAppointmentTab");
		} catch (Exception e) {
			log.error("Error in navigatetoAppointmentTab:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void removePick10Packs() {
		try {
			log.debug("Entering removePick10Packs");
			wait(2);
			click(DIGITAL_CHANNEL_WRENCH);
			wait(2);
			switchToChildAlert();
			click(PICK10_PACK2);
			wait(2);
			click(MULTICULTURAL_TAB);
			wait(2);
			click(SOUTHASIAN4_PACK1_CHECKBOX);
			wait(2);
			click(ONDEMAND_TAB);
			wait(2);
			click(SATURDAY_NYT_CHECKBOX);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(OK_BTN_TO_CLOSE);
			wait(1);
			log.debug("Leaving removePick10Packs");
		} catch (Exception e) {
			log.error("Error in removePick10Packs:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void deletePhoneLOB() {
		try {
			log.debug("Entering deletePhoneLOB");
			wait(3);
			click(DELETE_PHONE_LOB);
			wait(2);
			switchToChildAlert();
			wait(1);
			click(REMOVE_CONFIRM_YES);
			wait(2);
			log.debug("Leaving deletePhoneLOB");
		} catch (Exception e) {
			log.error("Error in deletePhoneLOB:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void updateCustomerDetails() {
		try {
			log.debug("Entering updateCustomerDetails");
			click(CUSTOMER_AND_ID_TAB);
			wait(2);
			inputText(FIRSTNAME, "ShawBSS");
			log.debug("Entering First Name To Update");
			wait(1);
			inputText(LASTNAME, "Autotest");
			log.debug("Entering Last Name");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving updateCustomerDetails");
		} catch (Exception e) {
			log.error("Error in updateCustomerDetails:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void emailPasswordReset() {
		try {
			log.debug("Entering emailPasswordReset");
			wait(2);
			click(INTERNET_EMAIL_WRENCH);
			wait(2);
			switchToChildAlert();
			wait(2);
			click(EXISTING_EMAIL_TWO);
			wait(3);
			click(RESET_PWD_CHECK_BOX);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(OK_BUTTON_TO_CLOSE);
			wait(1);
			click(OK_BUTTON_TO_CLOSE);
			log.debug("Leaving emailPasswordReset");
		} catch (Exception e) {
			log.error("Error in emailPasswordReset:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void tempSuspendLOB(String CustLOBs) {
		try {
			log.debug("Entering tempSuspendTriplePlay");
			wait(4);
			if (CustLOBs.equalsIgnoreCase("Phone")) {
				selectFromList(PHONE_PROD_LIST, "Temporary Suspend Phone");
				// selectFromList(PHONE_PROD_LIST, "Vacation Suspension Phone");
				wait(2);
			}
			if (CustLOBs.equalsIgnoreCase("Internet")) {
				wait(1);
				click(TEMP_SUSPEND_INTERNET);
				wait(2);
			}
			if (CustLOBs.equalsIgnoreCase("TV")) {
				wait(1);
				click(TEMP_SUSPEND_TV);
				wait(3);
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
		} catch (Exception e) {
			log.error("Error in tempSuspendTriplePlay:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToCustOrderWithLocId2(String acctNbr) {
		try {
			log.debug("Entering navigateToCustOrderWithLocId2");
			String CalgaryLocId2URL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/oe.newCustomerDesktop.nc?locationId=221&accountId=1" + acctNbr + "&newLocationId=2";
			navigate(CalgaryLocId2URL);
			wait(1);
			test.log(LogStatus.INFO, "Calgary LocationId 2 URL: ",
					"<a href=" + CalgaryLocId2URL + " > " + CalgaryLocId2URL + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			log.debug("Leaving navigateToCustOrderWithLocId2");
		} catch (Exception e) {
			log.error("Error in navigateToCustOrderWithLocId2:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToTenantCustOrderForTenantAccountlocId2(String acctNbr) {
		try {
			log.debug("Entering navigateToTenantCustOrderForTenantAccount");
			String CalgaryLocId2URL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/oe.newCustomerDesktop.nc?accountId=1" + acctNbr + "&locationId=2";
			navigate(CalgaryLocId2URL);
			wait(1);
			test.log(LogStatus.INFO, "Calgary LocationId 2 URL: ",
					"<a href=" + CalgaryLocId2URL + " > " + CalgaryLocId2URL + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			log.debug("Leaving navigateToTenantCustOrderForTenantAccount");
		} catch (Exception e) {
			log.error("Error in navigateToTenantCustOrderForTenantAccount:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateTomasterCustOrderForTenantAccountlocId2(String acctNbr) {
		try {
			log.debug("Entering navigateTomasterCustOrderForTenantAccount");
			String CalgaryLocId2URL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/oe.newCustomerDesktop.nc?masterAccountId=1" + acctNbr + "&locationId=2";
			navigate(CalgaryLocId2URL);
			wait(1);
			test.log(LogStatus.INFO, "Calgary LocationId 2 URL: ",
					"<a href=" + CalgaryLocId2URL + " > " + CalgaryLocId2URL + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			log.debug("Leaving navigateTomasterCustOrderForTenantAccount");
		} catch (Exception e) {
			log.error("Error in navigateTomasterCustOrderForTenantAccount:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToCBSaccountOpenUrlWithLocationId2(String cbsAccntNmb) {
		try {
			log.debug("Entering navigateToCBSaccountOpenUrlWithLocationId2");
			String CalagryURLwithLocationId2 = Utility.getValueFromPropertyFile("basepage_url")
					+ "/oe.newCustomerDesktop.nc?locationId=2&accountId=" + cbsAccntNmb + "";
			navigate(CalagryURLwithLocationId2);
			wait(1);
			test.log(LogStatus.PASS, "Calagry URL with LocId 2:",
					"<a href=" + CalagryURLwithLocationId2 + " > " + CalagryURLwithLocationId2 + " </a>");
			test.log(LogStatus.PASS, "Open Browser", "Order Creation Page opened WithLocationID2");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToCBSaccountOpenUrlWithLocationId2");
		} catch (Exception e) {
			log.error("Error in Login:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateTomasterCustOrderForTenantAccount(String acctNbr) {
		try {
			log.debug("Entering navigateTomasterCustOrderForTenantAccount");
			String CalgaryLocId2URL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/oe.newCustomerDesktop.nc?&locationId=221&masterAccountId=1" + acctNbr + "";
			navigate(CalgaryLocId2URL);
			wait(1);
			test.log(LogStatus.INFO, "Calgary LocationId 2 URL: ",
					"<a href=" + CalgaryLocId2URL + " > " + CalgaryLocId2URL + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			log.debug("Leaving navigateTomasterCustOrderForTenantAccount");
		} catch (Exception e) {
			log.error("Error in navigateTomasterCustOrderForTenantAccount:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateTomasterCustOrderToTenantAccount(String acctNbr, String acctNbr1, String acctNbr3) {
		try {
			log.debug("Entering navigateTomasterCustOrderToTenantAccount");
			String CalgaryLocId2URL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/oe.newCustomerDesktop.nc?locationId=221&accountId=1" + acctNbr
					+ "&newLocationId=2&masterAccountId=1" + acctNbr1 + "&existingAccountId=1" + acctNbr3;
			navigate(CalgaryLocId2URL);
			wait(1);
			test.log(LogStatus.INFO, "Calgary LocationId 2 URL: ",
					"<a href=" + CalgaryLocId2URL + " > " + CalgaryLocId2URL + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			log.debug("Leaving navigateTomasterCustOrderToTenantAccount");
		} catch (Exception e) {
			log.error("Error in navigateTomasterCustOrderToTenantAccount:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateTomasterCustOrderWithExistingAccountForPremise(String acctNbr) {
		try {
			log.debug("Entering navigateTomasterCustOrderWithExistingAccountForPremise");
			String CalgaryLocId2URL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/oe.newCustomerDesktop.nc?masterAccountId=&existingAccountId=1" + acctNbr
					+ "&locationId=221&newLocationId=2";
			navigate(CalgaryLocId2URL);
			wait(1);
			test.log(LogStatus.INFO, "Calgary LocationId 2 URL: ",
					"<a href=" + CalgaryLocId2URL + " > " + CalgaryLocId2URL + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			log.debug("Leaving navigateTomasterCustOrderWithExistingAccountForPremise");
		} catch (Exception e) {
			log.error("Error in navigateTomasterCustOrderWithExistingAccountForPremise:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateTomasterAccountToExistingAccountForPremise(String acctNbr, String acctNbr1) {
		try {
			log.debug("Entering navigateTomasterAccountToExistingAccountForPremise");
			String CalgaryLocId2URL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/oe.newCustomerDesktop.nc?newLocationId=2&masterAccountId=1" + acctNbr1 + " &existingAccountId=1"
					+ acctNbr + "";
			navigate(CalgaryLocId2URL);
			wait(1);
			test.log(LogStatus.INFO, "Calgary LocationId 2 URL: ",
					"<a href=" + CalgaryLocId2URL + " > " + CalgaryLocId2URL + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			log.debug("Leaving navigateTomasterAccountToExistingAccountForPremise");
		} catch (Exception e) {
			log.error("Error in navigateTomasterAccountToExistingAccountForPremise:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateTomasterAccountToExistingAccountToTenantLocationId2(String acctNbr, String acctNbr1,
			String cbsAcctNbr) {
		try {
			log.debug("Entering navigateTomasterAccountToExistingAccountForPremise");
			String CalgaryLocId2URL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/oe.newCustomerDesktop.nc?locationId=2&masterAccountId=1" + acctNbr1 + " &existingAccountId=1"
					+ acctNbr + "&accountId=" + cbsAcctNbr + "";
			navigate(CalgaryLocId2URL);
			wait(1);
			test.log(LogStatus.INFO, "Calgary LocationId 2 URL: ",
					"<a href=" + CalgaryLocId2URL + " > " + CalgaryLocId2URL + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			log.debug("Leaving navigateTomasterAccountToExistingAccountForPremise");
		} catch (Exception e) {
			log.error("Error in navigateTomasterAccountToExistingAccountForPremise:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToaccountTomasterAccountForPremise(String acctNbr, String acctNbr1) {
		try {
			log.debug("Entering navigateToaccountTomasterAccountForPremise");
			String CalgaryLocId2URL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/oe.newCustomerDesktop.nc?locationId=2&accountId=1" + acctNbr + "&masterAccountId=1" + acctNbr1
					+ "";
			navigate(CalgaryLocId2URL);
			wait(1);
			test.log(LogStatus.INFO, "Calgary LocationId 2 URL: ",
					"<a href=" + CalgaryLocId2URL + " > " + CalgaryLocId2URL + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			log.debug("Leaving navigateToaccountTomasterAccountForPremise");
		} catch (Exception e) {
			log.error("Error in navigateToaccountTomasterAccountForPremise:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToRetrievePremiseMove(String acctNbr) {
		try {
			log.debug("Entering navigateToRetrievePremiseMove");
			String PremiseMoveLocId221URL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/oe.newCustomerDesktop.nc?locationId=221&accountId=1" + acctNbr + "&newLocationId=221";
			navigate(PremiseMoveLocId221URL);
			wait(1);
			test.log(LogStatus.INFO, "Retrieve PremiseMove URL: ",
					"<a href=" + PremiseMoveLocId221URL + " > " + PremiseMoveLocId221URL + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			log.debug("Leaving navigateToRetrievePremiseMove");
		} catch (Exception e) {
			log.error("Error in navigateToRetrievePremiseMove:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addEmailsIPAddressSimCardForInternet(String prod, String range) {
		try {
			log.debug("Entering addEmailsIPAddressSimCardForInternet");
			if (prod.equalsIgnoreCase("One")) {
				wait(1);
				click(EMAIL_RANGE_BUTTON);
				wait(2);
			}
			if (prod.equalsIgnoreCase("Two")) {
				wait(1);
				click(EMAIL_RANGE_BUTTON);
				wait(3);
				click(EMAIL_RANGE_BUTTON);
				wait(2);
			}
			if (range.equalsIgnoreCase("IP Range")) {
				wait(1);
				inputText(IP_ADDRESS_RANGE_BUTTON, "2");
				wait(2);
			}
			if (range.equalsIgnoreCase("Sim Card")) {
				wait(1);
				inputText(SIMCARD_SHAW_MOBILE_RANGE, "2");
				wait(2);
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addEmailsIPAddressSimCardForInternet");
		} catch (Exception e) {
			log.error("Error in addEmailsIPAddressSimCardForInternet:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addTVwithLegecyWithoutHardware(String prod) {
		try {
			log.debug("Entering addTVwithLegecyWithoutHardware");
			if (prod.equals("Limited TV")) {
				click(TV_HARDWARE_WRENCH);
			}
			wait(1);
			switchToChildAlert();
			click(RENT_DCT_700);
			do {
				wait(1);
			} while (!isDisplayed(HARDWARE_OK_BUTTON));
			wait(1);
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(HARDWARE_OK_BUTTON);
			wait(1);
			log.debug("Leaving addTVwithLegecyWithoutHardware");
		} catch (Exception e) {
			log.error("Error in addTVwithLegecyWithoutHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void openOEStubTochangeNoTV() {
		try {
			log.debug("Entering openOEStubTochangeLocationDropType");
			wait(1);
			String oeStubPageurl = Utility.getValueFromPropertyFile("oestub_url");
			navigate(oeStubPageurl);
			wait(1);
			test.log(LogStatus.INFO, "Stub URL: ", "<a href=" + oeStubPageurl + " > " + oeStubPageurl + " </a>");
			javascript.executeScript("window.scrollBy(0,1500)", "");
			click(SERVICE_AVILABILITY);
			wait(2);
			while (!(getLastVal(getWebElement(ENGINEERING_NO_TV_BTN).getAttribute("class"))
					.equalsIgnoreCase("no_tv"))) {
				click(ENGINEERING_NO_TV_BTN);
				wait(1);
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
		} catch (Exception e) {
			log.error("Error in openOEStubTochangeLocationDropType:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addShawGatewayHardwares(String ShawGatewaySlNbr, String ShawPortalSlNbr) {
		try {
			log.debug("Entering addShawGatewayHardwares");
			click(TV_HARDWARE_WRENCH);
			wait(2);
			switchToChildAlert();
			click(SHAW_GATEWAY_RENTBTN);
			wait(2);
			inputText(SHAW_BLUESKY_TV_BOX526_SLNO_INPUT, ShawGatewaySlNbr);
			wait(2);
			click(SHAW_BLUESKY_TV_SLNO_VALIDATE);
			wait(2);
			click(SHAW_PORTAL_RENTBTN);
			wait(2);
			inputText(SHAW_BLUESKY_TV_BOX416_SLNO_INPUT, ShawPortalSlNbr);
			wait(2);
			click(SHAW_BLUESKY_TV_SLNO_VALIDATE);
			wait(2);
			test.log(LogStatus.PASS, "Add TV Shaw Hardwarwes", "Added TV Shaw Hardwares Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(HARDWARE_OK_BUTTON);
			wait(2);
			log.debug("Leaving addShawGatewayHardwares");
		} catch (Exception e) {
			log.error("Error in addShawGatewayHardwares:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addGrandfatheredPromotions() {
		try {
			log.debug("Entering addGrandfatheredPromotions");
			wait(2);
			click(PROMOTIONS_LINK);
			wait(1);
			switchToChildAlert();
			wait(4);
			click(TWOYVP_INTERNET_TVAGREEMENT);
			wait(4);
			scrollDownAndClick(TWOYVP24M_FIBRE_75_PRICEGURANTEE85);
			wait(4);
			click(TV_PROMOTION_BTN);
			wait(4);
			click(PORTAL_PROMO24M_CHECKBTN);
			wait(4);
			click(TWOYVP24M_SMALLTV_PRICEGURANTEE);
			wait(4);
			click(OK_BUTTON_TO_CLOSE);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addGrandfatheredPromotions");
		} catch (Exception e) {
			log.error("Error in addGrandfatheredPromotions:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addCoupons() {
		try {
			log.debug("Entering addCoupons");
			wait(1);
			click(PROMOTIONS_LINK);
			wait(1);
			switchToChildAlert();
			wait(2);
			click(COUPON_SECTIONS);
			wait(1);
			click(MAKE_GOOD_COUPON_CHECKBOX);
			wait(2);
			inputText(COUPON_INCREASE_BTN, "3");
			wait(1);
			click(OK_BUTTON_TO_CLOSE);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving addCoupons");
		} catch (Exception e) {
			log.error("Error in addCoupons:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void SwitchToAccountView() {
		try {
			log.debug("Entering SwitchToAccountView");
			wait(1);
			click(PROMOTIONS_LINK);
			wait(1);
			switchToChildAlert();
			wait(2);
			click(ACCOUNT_VIEW_TAB);
			wait(1);
			if (isElementPresent(SEGMENT_FILTER1)) {

				moveToMouseOver(SEGMENT_FILTER1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
//				wait(2);
//				moveToMouseOver(SEGMENT_FILTER2);
//				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
//				wait(2);
//				moveToMouseOver(SEGMENT_FILTER3);
//				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
//				wait(2);
//				moveToMouseOver(SEGMENT_FILTER4);
//				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
//				wait(2);
//				moveToMouseOver(SEGMENT_FILTER5);
//				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			wait(2);

			click(FREEDOM_INTRNET150_DISCOUNT_INFO);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(CLOSE_BUTTON);
			wait(2);

			click(ROLE_VIEW_TAB);
			wait(2);
			if (isElementPresent(FUNCTIONAL_DROPDOWNN)) {
				click(FUNCTIONAL_DROPDOWNN);
				wait(1);
				selectFromList(FUNCTIONAL_DROPDOWNN, "Retail");
				wait(1);
				click(FREEDOM_INTRNET150_DISCOUNT_CHECK_BOX);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}

			wait(2);
			click(FREEDOM_INTRNET150_DISCOUNT_INFO1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(CLOSE_BUTTON);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			click(OK_BUTTON_TO_CLOSE);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving SwitchToAccountView");
		} catch (Exception e) {
			log.error("Error in SwitchToAccountView:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void SwitchToAccountViewAndPromotions() {
		try {
			log.debug("Entering SwitchToAccountViewAndPromotions");
			wait(1);
			click(PROMOTIONS_LINK);
			wait(1);
			switchToChildAlert();
			wait(2);
			click(ACCOUNT_VIEW_TAB);
			wait(1);
			if (isElementPresent(SEGMENT_FILTER1)) {

				moveToMouseOver(SEGMENT_FILTER1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(2);
			}
			if (isElementPresent(SEGMENT_FILTER2)) {
				moveToMouseOver(SEGMENT_FILTER2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(2);
			}
			if (isElementPresent(SEGMENT_FILTER3)) {
				moveToMouseOver(SEGMENT_FILTER3);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(2);
			}
			if (isElementPresent(SEGMENT_FILTER4)) {
				moveToMouseOver(SEGMENT_FILTER4);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(2);
			}
			if (isElementPresent(SEGMENT_FILTER5)) {
				moveToMouseOver(SEGMENT_FILTER5);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			wait(2);
			if (isElementPresent(FREEDOM_INTRNET150_DISCOUNT_INFO)) {
				click(FREEDOM_INTRNET150_DISCOUNT_INFO);
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(CLOSE_BUTTON);
			wait(2);

			if (isElementPresent(FREEDOM_INTRNET150_DISCOUNT_CHECK_BOX)) {

				click(FREEDOM_INTRNET150_DISCOUNT_CHECK_BOX);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}

			wait(2);
			click(FREEDOM_INTRNET150_DISCOUNT_INFO1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(CLOSE_BUTTON);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			click(OK_BUTTON_TO_CLOSE);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving SwitchToAccountViewAndPromotions");
		} catch (Exception e) {
			log.error("Error in SwitchToAccountViewAndPromotions:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void VerifySwitchToAccountView() {
		try {
			log.debug("Entering VerifySwitchToAccountView");
			wait(1);
			click(PROMOTIONS_LINK);
			wait(1);
			switchToChildAlert();
			wait(2);
			click(ACCOUNT_VIEW_TAB);
			wait(1);
			if (isElementPresent(SEGMENT_FILTER1)) {

				moveToMouseOver(SEGMENT_FILTER1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(2);
			}
			if (isElementPresent(SEGMENT_FILTER2)) {
				moveToMouseOver(SEGMENT_FILTER2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(2);
			}
			if (isElementPresent(SEGMENT_FILTER3)) {
				moveToMouseOver(SEGMENT_FILTER3);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(2);
			}
			if (isElementPresent(SEGMENT_FILTER4)) {
				moveToMouseOver(SEGMENT_FILTER4);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(2);
			}
			if (isElementPresent(SEGMENT_FILTER5)) {
				moveToMouseOver(SEGMENT_FILTER5);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			click(OK_BUTTON_TO_CLOSE);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving VerifySwitchToAccountView");
		} catch (Exception e) {
			log.error("Error in VerifySwitchToAccountView:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToStubCDI() {
		try {
			log.debug("Entering navigateToStubCDI");
			String StubCDIUrl = Utility.getValueFromPropertyFile("oestub_url");
			navigate(StubCDIUrl);
			wait(1);
			test.log(LogStatus.INFO, "Stub URL: ", "<a href=" + StubCDIUrl + " > " + StubCDIUrl + " </a>");
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			wait(2);
			click(HPSA);
			javascript.executeScript("window.scrollBy(0,600)", "");
			wait(3);
			click(CDI_STUB);
			wait(2);
			inputText(LOCATIONID_TEXTBOX, "221");
			wait(2);
			click(CREATE_BTN_STUB);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToStubCDI");
		} catch (Exception e) {
			log.error("Error in navigateToStubCDI:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToFillListingTypeCBSCustomer() {
		try {
			log.debug("Entering navigateToFillListingTypeCBSCustomer");
			click(PHONE_WRENCH_BUTTON);
			wait(1);
			switchToChildAlert();
			wait(2);
			click(OK_BUTTON_TO_CLOSE);
			wait(1);
			click(DISTINCTIVERING_WRENCH1);
			wait(1);
			switchToChildAlert();
			wait(1);
			click(LISTINGTYPE_LIST);
			wait(2);
			click(NO_LISTING);
			wait(2);
			click(OK_BUTTON_TO_CLOSE);
			wait(1);
			click(DISTINCTIVERING_WRENCH2);
			wait(1);
			switchToChildAlert();
			wait(1);
			click(LISTINGTYPE_LIST);
			wait(2);
			click(NO_LISTING);
			wait(2);
			click(OK_BUTTON_TO_CLOSE);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToFillListingTypeCBSCustomer");
		} catch (Exception e) {
			log.error("Error in navigateToFillListingTypeCBSCustomer:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToIntegrationSettings(String Attribute) {
		try {
			log.debug("Entering navigateToIntegrationSettings");
			String IntegrationSettingsUrl = Utility.getValueFromPropertyFile("basepage_url")
					+ "/common/uobject.jsp?tab=_Parameters&object=9124284297113879582";
			navigate(IntegrationSettingsUrl);
			wait(1);
			test.log(LogStatus.INFO, "Integration Settings URL: ",
					"<a href=" + IntegrationSettingsUrl + " > " + IntegrationSettingsUrl + " </a>");
			scrollDown600();
			wait(1);
			if (Attribute.equals("Porting Time")) {
				Attribute = getText(MINIUM_PORTING_TIME);
				if (Attribute.equals("5")) {
					log.debug("Min Porting Time is : " + Attribute);
					wait(1);
				} else {
					wait(1);
					scrollToTop();
					click(INTEGRATION_SETTINGS_EDIT_BTN);
					wait(2);
					scrollDownAndClick(MINIUM_PORTING_TIME_INPUT);
					wait(1);
					inputText(MINIUM_PORTING_TIME_INPUT, "5");
					wait(1);
					scrollToTop();
					wait(1);
					click(PORTING_TIME_UPDATE_BTN);
					wait(3);
					scrollDown600();
				}
			}
			if (Attribute.equals("Direct fulfillment Order Days")) {
				Attribute = getText(DIRECTFULFILLMENT_ORDER_DAYS);
				if (Attribute.equals("15")) {
					log.debug("Direct fulfillment Orders Date is : " + Attribute);
					wait(1);
					scrollDownToElement(NEED_TO_SCROLL_ELEMENT);
					wait(1);
				} else {
					wait(1);
					scrollToTop();
					click(INTEGRATION_SETTINGS_EDIT_BTN);
					wait(2);
					scrollDownAndClick(DIRECTFULFILLMENT_ORDER_DAYS_INPUT);
					wait(1);
					inputText(DIRECTFULFILLMENT_ORDER_DAYS_INPUT, "15");
					wait(1);
					scrollToTop();
					wait(1);
					click(PORTING_TIME_UPDATE_BTN);
					wait(3);
					scrollDownToElement(NEED_TO_SCROLL_ELEMENT);
					wait(1);
				}
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}

			if (Attribute.equals("Provision On Customer")) {
				Attribute = getText(PROVISION_ON_CUST_ORDER_DAYS);
				if (Attribute.equals("3")) {
					log.debug("Provision on Customer Confirmation is : " + Attribute);
					wait(1);
					scrollDown600();
					wait(1);
				} else {
					wait(1);
					scrollToTop();
					click(INTEGRATION_SETTINGS_EDIT_BTN);
					wait(2);
					scrollDownAndClick(PROVISION_ON_CUST_ORDER_INPUT);
					wait(1);
					inputText(PROVISION_ON_CUST_ORDER_INPUT, "3");
					wait(1);
					scrollToTop();
					wait(1);
					click(PORTING_TIME_UPDATE_BTN);
					wait(3);
					scrollDown600();
				}
			}
		} catch (Exception e) {
			log.error("Error in navigateToLSCResponseJobpaused:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void addPromotionsSingleIntSrvAgreement(String Agreement) {
		try {
			log.debug("Entering addPromotionsIntSrvAgreement");
			wait(2);
			scrollUp();
			click(PROMOTIONS_LINK);
			wait(1);
			switchToChildAlert();
			wait(1);
			if (Agreement.equals("Internet")) {
				click(TWOYVP_INTERNET_AGREEMENT);
				wait(6);

//				scrollDownToElement(TWOYVP24M_FIBRE_300_PRICEGURANTEE100);

				if ((isElementPresent(TWOYVP24M_FIBRE_300_PRICEGURANTEE100))) {
					wait(2);
					scrollDownAndClick(TWOYVP24M_FIBRE_300_PRICEGURANTEE100);
				}
				if (isElementPresent(TWENTYFOURM_FIBRE150_PRICE_GUARANTEE95)) {
					scrollDownAndClick(TWENTYFOURM_FIBRE150_PRICE_GUARANTEE95);
					wait(4);
				}

				if (Agreement.equals("Internet And TV")) {
					click(TWOYVP_INTERNET_AGREEMENT2);
					wait(2);
					if (isElementPresent(TWOYVP_INTERNET_TVAGREEMENT)) {
						click(TWOYVP_INTERNET_TVAGREEMENT);
						wait(2);
					}
					if (!isElementPresent(TWOYVP_INTERNET_TVAGREEMENT)) {
						selectFromList(FUNCTIONAL_OPTIONS_DROPDOWNN, "Inbound Sales");
						wait(4);
						click(TWOYVP_INTERNET_TVAGREEMENT);
					}
					wait(2);
					if (isElementPresent(TWOYVP24M_FIBRE_300_PRICEGURANTEE100)) {
						click(TWOYVP24M_FIBRE_300_PRICEGURANTEE100);
						wait(2);
					}
					if (isElementPresent(TWENTYFOURM_FIBRE150_PRICE_GUARANTEE95)) {
						click(TWENTYFOURM_FIBRE150_PRICE_GUARANTEE95);
						wait(2);
					}

					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				}
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			click(OK_BUTTON_TO_CLOSE);
			wait(2);
			log.debug("Leaving addPromotionsIntSrvAgreement");
		} catch (Exception e) {
			log.error("Error in addPromotionsIntSrvAgreement:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateIntegrationSettingParamsAutoCancelforRetailPickup() {
		try {
			log.debug("Entering navigateIntegrationSettingParamsAutoCancelforRetailPickup");
			String AutoCancelRetailPickUp = Utility.getValueFromPropertyFile("basepage_url")
					+ "/ncobject.jsp?id=8103158174013898720";
			navigate(AutoCancelRetailPickUp);
			wait(1);
			test.log(LogStatus.INFO, "Auto Cancel Retail Pickup URL: ",
					"<a href=" + AutoCancelRetailPickUp + " > " + AutoCancelRetailPickUp + " </a>");
			String RetailPickupMonitoring = null;
			RetailPickupMonitoring = getText(AUTO_CANCEL_FORDF_MONITORING);
			if (RetailPickupMonitoring.equals("015-00:00")) {
				System.out.println("Retail Pickup monitoring duration is :" + RetailPickupMonitoring);
				log.debug("Retail Pickup Monitoring duration is :" + RetailPickupMonitoring);
				wait(1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			} else {
				wait(1);
				click(STANDARD_DURATION_TIME);
				wait(2);
				click(SHIPPING_HW_DROPDOWN);
				wait(2);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(1);
				inputText(STANDARD_DURATION_TIME, "015-00:00");
				wait(1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				click(SAVE_LINK);
				wait(3);
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateIntegrationSettingParamsAutoCancelforRetailPickup");
		} catch (Exception e) {
			log.error("Error in navigateIntegrationSettingParamsAutoCancelforRetailPickup:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	// @author ${RAMESH REDDY K}
	public void verifyReassignLevelUpButtonsForShawPortalSMG() {
		try {
			log.debug("Entering verifyReassignLevelUpButtonsForShawPortalSMG");
			wait(1);
			click(TV_HARDWARE_WRENCH);
			wait(2);
			switchToChildAlert();
			wait(1);
			moveToMouseOver(REASSIGN_LEVELUP_BTN1);
			wait(2);
			test.log(LogStatus.INFO, "ToolTip message is: ",
					"Moves the portals to the newly added Gateway OR moves Blue Curve Modem to the Converged Hardware section");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			moveToMouseOver(REASSIGN_LEVELUP_BTN2);
			wait(2);
			test.log(LogStatus.INFO, "ToolTip message is: ",
					"Moves the portals to the newly added Gateway OR moves Blue Curve Modem to the Converged Hardware section");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			moveToMouseOver(REASSIGN_LEVELUP_BTN3);
			wait(2);
			test.log(LogStatus.INFO, "ToolTip message is: ",
					"Moves the portals to the newly added Gateway OR moves Blue Curve Modem to the Converged Hardware section");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			moveToMouseOver(REASSIGN_LEVELUP_BTN4);
			wait(2);
			test.log(LogStatus.INFO, "ToolTip message is: ",
					"Moves the portals to the newly added Gateway OR moves Blue Curve Modem to the Converged Hardware section");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			moveToMouseOver(REASSIGN_LEVELUP_BTN5);
			wait(2);
			test.log(LogStatus.INFO, "ToolTip message is: ",
					"Moves the portals to the newly added Gateway OR moves Blue Curve Modem to the Converged Hardware section");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving verifyReassignLevelUpButtonsForShawPortalSMG");
		} catch (Exception e) {
			log.error("Error in verifyReassignLevelUpButtonsForShawPortalSMG:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToClickOnLevelUpButtons() {
		try {
			log.debug("Entering navigateToClickOnLevelUpButtons");
			wait(1);
			scrollDownAndClick(REASSIGN_LEVELUP_BTN1);
			wait(2);
			scrollDownAndClick(REASSIGN_LEVELUP_BTN1);
			wait(2);
			scrollDownAndClick(REASSIGN_LEVELUP_BTN1);
			wait(2);
			scrollDownAndClick(REASSIGN_LEVELUP_BTN1);
			wait(2);
			scrollDownAndClick(REASSIGN_LEVELUP_BTN1);
			wait(2);
			click(DELETE_HARDWARE);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToClickOnLevelUpButtons");
		} catch (Exception e) {
			log.error("Error in navigateToClickOnLevelUpButtons:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void effectiveDateConfirmation(String Order) {
		try {
			log.debug("Entering navigateToClickOnLevelUpButtons");
			wait(3);
			click(APPOINTMENT);
			wait(7);
			click(EFFECTIVE_DATE_CONFIRMATION);
			wait(2);
			if (Order.equals("Yes")) {
				click(VALIDATE_SALES_REPRESENTATIVE);
				wait(1);
			}
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving navigateToClickOnLevelUpButtons");
		} catch (Exception e) {
			log.error("Error in navigateToClickOnLevelUpButtons:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void navigateToCheckAppoinmentTabFeactures() {
		try {
			log.debug("Entering navigateToCheckAppoinmentTabFeactures");
			wait(2);
			click(RETAIL_PICKUP_RADIO_BTN);
			wait(2);
			if (isElementPresent(SMS_NBR_FIELD)) {
				test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			} else {
				test.log(LogStatus.PASS, "Verify Validation for Retail Pickup: ",
						"SMS Text Field and Customer Opted out check box doesn't Appear on Appointment.");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			click(MAIL_OUT_RADIO_BTN);
			wait(2);
			if (isElementPresent(SMS_NBR_FIELD)) {
				test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			} else {
				test.log(LogStatus.PASS, "Verify Validation for MailOut: ",
						"SMS Text Field and Customer Opted out check box doesn't Appear on Appointment.");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}

			click(EXISTING_HW_PREMISEMOVE_RADIO_BTN);
			wait(2);
			if (isElementPresent(SMS_NBR_FIELD)) {
				test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			} else {
				test.log(LogStatus.PASS, "Verify Validation for Existing HW PremiseMove: ",
						"SMS Text Field and Customer Opted out check box doesn't Appear on Appointment.");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			click(SELF_CONNECT_NO_RADIO_BTN);
			wait(2);
			click(TECH_APPOINMENT_NO);
			wait(1);
			if (isElementPresent(SMS_NBR_FIELD)) {
				test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			} else {
				test.log(LogStatus.PASS, "Verify Validation for Appoinment No: ",
						"SMS Text Field and Customer Opted out check box doesn't Appear on Appointment.");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			click(SELF_CONNECT_NO_RADIO_BTN);
			wait(2);
			click(TECH_APPOINMENT_YES);
			wait(1);
			if (isElementPresent(SMS_NBR_FIELD)) {
				test.log(LogStatus.PASS, "Verify Validation for Appoinment Yes: ",
						"SMS Number field is shown along with Customer Opted out check box.SMS Number Text Field is appear like (__)_.____ and Customer Opted out check box is unchecked.");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			} else {
				test.log(LogStatus.FAIL, "Verify Validation for Appoinment Yes: ", "SMS Text Field is not present.");
			}
			click(FORCE_APPOINTMENT_CHECKBOX);
			wait(1);
			inputText(CHANGEAUTHORIZEDBY, "Integration Test");
			wait(1);
			click(ADD_SERVICES_AND_FEATURES_TAB);
			wait(2);
			if (isElementPresent(DEFAULT_INSTALLATION_MSG)) {
				String defaultInstallFee = getText(DEFAULT_INSTALLATION_MSG);
				Assert.assertEquals(
						"A default installation fee has been applied to this order. Check this box only after discussing the new fee with customer",
						defaultInstallFee);
				test.log(LogStatus.PASS, "Verify the ac knowledgeable to-do message is displayed in to-do list: ",
						"A default installation fee has been applied to this order. Check this box only after discussing the new fee with customer.");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			} else {
				test.log(LogStatus.FAIL, "Verify the acknowledgeable to-do message is displayed in to-do list: ",
						"Default installation fee to-do list notification is not present.");
			}
			click(TODO_LIST_CHECKBOX);
			wait(2);
			click(FINISH);
			wait(1);
			test.log(LogStatus.INFO, "SMS Nbr Error Msg: ", "Enter SMS number under Appointments, dates & reasons tab");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(APPOINTMENT);
			wait(2);
			WebElement e1 = getWebElement(SMS_NBR_FIELD);
			jse.executeScript("arguments[0].value='(436) 968-7851';", e1);
			wait(1);
			log.debug("Leaving navigateToCheckAppoinmentTabFeactures");
		} catch (Exception e) {
			log.error("Error in navigateToCheckAppoinmentTabFeactures:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void NavigateToChangeActiveDirectoryID() {
		try {
			log.debug("Entering NavigateToChangeActiveDirectoryID");
			wait(1);
			click(ACTIVATE_DIRECTORYID_REMOVEBTN);
			wait(2);
			inputText(ACTIVATE_DIRECTORYID_INPUT, "lruser");
			wait(1);
			click(EMAIL_VALIDATE);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving NavigateToChangeActiveDirectoryID");
		} catch (Exception e) {
			log.error("Error in NavigateToChangeActiveDirectoryID:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void selectMethodOfConfirmation(String Confirmation) {
		try {
			javascript.executeScript("window.scrollBy(0,800)", "");
			wait(1);
			log.debug("Entering selectMethodOfConfirmation");
			click(AGREEMENT_DETAIL_TAB);
			wait(1);
			if (Confirmation.equals("Voice")) {
				selectFromList(METHOD_OF_CONFIRMATION, "Voice");
			}
			wait(1);
			if (Confirmation.equals("Electronic Signature")) {
				selectFromList(METHOD_OF_CONFIRMATION, "Electronic Signature");
			}
			wait(1);
			if (Confirmation.equals("Email")) {
				selectFromList(METHOD_OF_CONFIRMATION, "Email");
			}
			wait(1);
			inputText(CONFIRMATION_ADDITIONAL, "noreply@nc.com");
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving selectMethodOfConfirmation");
		} catch (Exception e) {
			log.error("Error in selectMethodOfConfirmation:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void selectingSalesRepresentativeCheckBox() {
		try {
			log.debug("Entering selectingSalesRepresentativeCheckBox");
			wait(1);
			click(VALIDATE_SALES_REPRESENTATIVE);
			wait(2);
			log.debug("Leaving selectingSalesRepresentativeCheckBox");
		} catch (Exception e) {
			log.error("Error in selectingSalesRepresentativeCheckBox:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void resetPramotions() {
		try {
			log.debug("Entering resetPramotions");
			wait(1);
			click(TWO_YVP_INTERNET_TV_AGGREEMENT_METHOD);
			wait(2);
			click(CONFIRM_PROMOTION_RESTART_BTN);
			wait(1);
			click(CLOSE_BUTTON);
			wait(1);
			click(PROMOTIONS_LINK);
			wait(2);
			click(TWENTYFOURM_PRICE_GUARANTEE95);
			wait(2);
			click(OK_BUTTON_TO_CLOSE);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving resetPramotions");
		} catch (Exception e) {
			log.error("Error in resetPramotions:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void okToClose() {
		try {
			log.debug("Entering Ok to close popup");
			wait(2);
			switchToSecondWindow();
			// switchToChildAlert();
			wait(1);
			click(EMAIL_OK_BUTTON);
			wait(2);
			log.debug("Leaving Ok to close popup");
		} catch (Exception e) {
			log.error("Error in Closing pop-up:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void warningokToClose() {
		try {
			log.debug("Entering Ok to close popup");
//			wait(2);
//			switchToSecondWindow();
			// switchToChildAlert();
			wait(1);
			if (isElementPresent(WARNING_POPUP_OK_BTN)) {

				click(WARNING_POPUP_OK_BTN);
			} else {
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}

			wait(4);
			log.debug("Leaving Ok to close popup");
			System.out.println("Exiting from method");
		} catch (Exception e) {
			log.error("Error in Closing pop-up:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void CreatA1User() {
		try {
			log.debug("Entering CreatA1User");
			String NewUserURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/admin/users/groups.jsp?tab=_All+Users&group=101&object=101";
			navigate(NewUserURL);
			wait(1);
			test.log(LogStatus.INFO, "ATO User URL: ", "<a href=" + NewUserURL + " > " + NewUserURL + " </a>");
			wait(2);
			wait(2);
			click(NEW_USER);
			wait(2);
			wait(2);
			inputText(LOGIN_USER_NAME, "A1");
			wait(2);
			inputText(NEW_PASSWORD, "123");
			wait(1);
			inputText(CONFIRM_PASSWORD, "123");
			wait(2);
			click(CREATE_BTN);
			wait(5);
			switchToChildAlert();
			wait(5);
			inputText(USER_NAME, "Administrator");
			wait(4);
			inputText(PASSWORD, "netcracker");

			wait(1);
			click(CLECOPS_FILTER);
			wait(2);
			inputText(CLECOPS_SEARCH_BOX, "CLECOPS");
			wait(2);
			click(APPLY_FILTER);

			log.debug("Leaving Entering CreatA1User");
		} catch (Exception e) {
			log.error("Error in Entering CreatA1User:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void MigrationInformation(String msg, String branchType) {
		try {
			log.debug("Entering MigrationInformation");
			wait(1);
			click(CUSTOMER_AND_ID_TAB);
			log.debug("Navigating to Customer Information Tab");
			wait(5);
			if (isElementPresent(TRANSFER_CBS_CHECKBOX)) {
				wait(1);
				click(TRANSFER_CBS_CHECKBOX);
			}

			if (isElementPresent(CBS_ACCOUNT_NBR)) {
				wait(1);
				WebElement e1 = getWebElement(CBS_ACCOUNT_NBR);
				jse.executeScript("arguments[0].value='" + msg.substring(1) + "';", e1);
			}
			if (branchType.equalsIgnoreCase("Calgary - CGY")) {
				wait(1);
				selectFromList(CBS_BRANCH_DROPDOWN, "Calgary - CGY");
				wait(2);
			}
			if (isElementPresent(CBS_ACCNT_VALIDATION_BTN)) {
				wait(1);
				click(CBS_ACCNT_VALIDATION_BTN);
			}

//			if (accntType.equalsIgnoreCase("Residential")) {
//				wait(1);
//				selectFromList(ACCOUNT_TYPE, "Residential");
//				wait(1);
//			}
//			if (accntType.equalsIgnoreCase("Staff")) {
//				wait(1);
//				selectFromList(ACCOUNT_TYPE, "Staff");
//				wait(2);
//			}
//
//			if (accntType.equalsIgnoreCase("BulkMaster")) {
//				wait(2);
//				selectFromList(ACCOUNT_TYPE, "BulkMaster");
//				wait(4);
//			}
//
//			if (accntType.equalsIgnoreCase("Tenant")) {
//				wait(1);
//				selectFromList(ACCOUNT_TYPE, "Tenant");
//				wait(2);
//			}
//			
//			inputText1(CUST_EMAIL_ADDRESS, Utility.getValueFromPropertyFile("customerEmailAddress"));
//			log.debug("Entering Customer Email Address");
//			wait(1);
//			WebElement e1 = getWebElement(CUST_PHONENUMBER);
//			jse.executeScript("arguments[0].value='(987) 654-3210';", e1);
//			log.debug("Entering Phone Number");
//			wait(1);
//			selectFromList(AUTHENCTICATION_TYPE, "PIN");
//			log.debug("Selecting Authentication type");
//			wait(5);
//
//			inputText(PIN1, "500081");
//			log.debug("Entering PIN Number");
//			wait(2);
//			click(BILLING_PREFERENCE_TAB);
//			wait(5);
//			click(CUSTOMER_AND_ID_TAB);
//			wait(1);
//			click(VALIDATE_PHNBR_BTN);
//			wait(1);
//			click(VALIDATE_EMAIL_BTN);
//			wait(1);
//			inputText(PIN1, "500081");
//			wait(2);
//			// switchPreviousTab();
//			if (accntHolder.equalsIgnoreCase("Organization")) {
//				wait(1);
//				click(ACCOUNT_HOLDER);
//				wait(2);
//				inputText1(BUSINESS_NAME, "IT Company");
//				wait(1);
//				inputText1(BUSINESS_FIRST_NAME, "Andrew");
//				wait(1);
//				inputText1(BUSINESS_LASTNAME, "Gomes");
//				wait(1);
//				inputText1(PERSONAL_ID_PIN, "12351");
//				wait(1);
//				WebElement e2 = getWebElement(CUST_MOBILE);
//				jse.executeScript("arguments[0].value='(987) 654-3210';", e2);
//				log.debug("Entering Phone Number");
//				wait(1);
//				inputText1(CUST_EMAIL, "test@test.com");
//				wait(1);
//			}
//			if (installOption.equalsIgnoreCase("Delay Crew")) {
//				wait(1);
//				selectFromList(INSTALL_OPTION, "Delay Crew");
//				wait(2);
//			} else {
//				selectFromList(INSTALL_OPTION, "Mailout");
//				wait(2);
//			}
			test.log(LogStatus.PASS, "Fill Migration Info", "Filled Migration Info Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving migrationInformation");
		} catch (Exception e) {
			log.error("Error in migrationInformation:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void verifyTVhardware() {
		try {
			log.debug("Entering verifyTVhardware");
			wait(2);
			click(TV_HARDWARE_WRENCH);
			wait(5);
			switchToChildAlert();
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(3);
			scrollDownAndClick(HARDWARE_OK_BUTTON);
			log.debug("Leaving verifyTVhardware");
		} catch (Exception e) {
			log.error("Error in verifyTVhardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void verifyInternetHardware() {
		try {
			log.debug("Entering verifyInternetHardware");
			wait(1);
			click(INTERNET_HARDWARE);
			wait(4);
			switchToChildAlert();
			wait(3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(4);
			scrollDownAndClick(HARDWARE_OK_BUTTON);
			log.debug("Leaving verifyInternetHardware");
		} catch (Exception e) {
			log.error("Error in verifyInternetHardware:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void verifyInternetEmail() {
		try {
			log.debug("Entering Verify Internet Email");
			wait(2);
			click(INTERNET_EMAIL_WRENCH);
			wait(2);
			switchToChildAlert();
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			click(EMAIL_OK_BUTTON);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving Verify Internet Email");
		} catch (Exception e) {
			log.error("Error in Verify Internet Email:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void VerifyCustomerDetails() {
		try {
			log.debug("Entering VerifyCustomerDetails");
			click(CUSTOMER_AND_ID_TAB);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			click(BILLING_PREFERENCE_TAB);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving VerifyCustomerDetails");
		} catch (Exception e) {
			log.error("Error in VerifyCustomerDetails:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void CreateRegionalSegment2() {
		try {
			log.debug("Entering Create Regional Segment2");

			wait(2);
			javascript.executeScript("window.scrollBy(0,1500)", "");
			wait(1);
			wait(2);
			if (isElementPresent(NEW_REGIONAL_SEGMENT)) {
				click(NEW_REGIONAL_SEGMENT);
			}
//						click(NEW_REGIONAL_SEGMENT);
			wait(3);
			inputText(REGIONAL_SEGMENT_NAME_INPUT, "New Regional Segment2");
			wait(4);
			inputText(REGIONAL_SEGMENT_ID_INPUT, "Test1");
			wait(4);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(4);
			click(ACTIVE_START_DATE_ICON);
			wait(3);
			click(ACTIVE_START_DATE_OK_BTN);
			wait(2);
			inputText(TIME_INPUT, "03");
			wait(2);
			click(RESTRICTED_TO_NEW_CUSTOMER);
			wait(4);
			click(RESTRICTED_ADD_BTN);

			wait(1);
			click(RESTRICTED_TO_EXIST_CUSTOMER);
			wait(4);
			click(RESTRICTED_ADD_BTN);
			wait(4);

//					click(REGION_DROP_DOWN);
			wait(4);
			inputText(REGION_TEXT_AREA, "SK");
			wait(5);
			click(SK);
			wait(2);
			click(CREATE_BTN);
			scrollToTop();
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
//					wait(4);
//					javascript.executeScript("window.scrollBy(0,-600)", "");
//					wait(2);
//					test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			inputText(REGIONAL_SEGMENT_ID_INPUT, "Test2");
			click(CREATE_BTN);
			scrollToTop();
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			log.debug("Leaving Regional Segment2");
		} catch (Exception e) {
			log.error("Error in Regional Segment2:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void CreatePartnerSegment1() {
		try {
			log.debug("Entering Create Partner Segment1");

			wait(2);
			javascript.executeScript("window.scrollBy(0,1500)", "");
			wait(1);
			wait(2);
			if (isElementPresent(NEW_PARTNER_SEGMENT)) {
				click(NEW_PARTNER_SEGMENT);
			}
//				click(NEW_REGIONAL_SEGMENT);
			wait(3);
			inputText(REGIONAL_SEGMENT_NAME_INPUT, "New Partner Segment11");
			wait(4);
			inputText(REGIONAL_SEGMENT_ID_INPUT, "11");
			wait(4);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(4);
			click(ACTIVE_START_DATE_ICON);
			wait(3);
			click(ACTIVE_START_DATE_OK_BTN);
			wait(2);
			inputText(TIME_INPUT, "03");
			wait(2);
			click(PARTNER_FIDO);
			wait(4);
			click(PARTNER_NAME_ADD_BTN);
			wait(2);
			click(PARTNER_ROGER);
			wait(4);
			click(PARTNER_NAME_ADD_BTN);
			wait(2);
			click(RESTRICTED_TO_NEW_CUSTOMER);
			wait(4);
			click(RESTRICTED_ADD_BTN);
			wait(1);
			click(RESTRICTED_TO_EXIST_CUSTOMER);
			wait(4);
			click(RESTRICTED_ADD_BTN);
			wait(4);
//			click(REGION_DROP_DOWN);
//			wait(4);
//			inputText(REGION_TEXT_AREA, "SK");
//			wait(5);
//			click(SK);
//			wait(2);
			click(CREATE_BTN);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving Partner Segment1");
		} catch (Exception e) {
			log.error("Error in Partner Segment1:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void CreatePartnerSegment2() {
		try {
			log.debug("Entering Create Partner Segment2");

			wait(2);
			javascript.executeScript("window.scrollBy(0,1500)", "");
			wait(1);
			wait(2);
			if (isElementPresent(NEW_PARTNER_SEGMENT)) {
				click(NEW_PARTNER_SEGMENT);
			}
//				click(NEW_REGIONAL_SEGMENT);
			wait(3);
			inputText(REGIONAL_SEGMENT_NAME_INPUT, "New Partner Segment12");
			wait(4);
			inputText(REGIONAL_SEGMENT_ID_INPUT, "12");
			wait(4);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(4);
			click(ACTIVE_START_DATE_ICON);
			wait(3);
			click(ACTIVE_START_DATE_OK_BTN);
			wait(2);
			inputText(TIME_INPUT, "03");
			wait(2);
			click(PARTNER_FIDO);
			wait(4);
			click(PARTNER_NAME_ADD_BTN);
			wait(2);
			click(PARTNER_SHAW_MOBILE);
			wait(4);
			click(PARTNER_NAME_ADD_BTN);
			wait(2);
			click(RESTRICTED_TO_NEW_CUSTOMER);
			wait(4);
			click(RESTRICTED_ADD_BTN);
			wait(1);
			click(RESTRICTED_TO_EXIST_CUSTOMER);
			wait(4);
			click(RESTRICTED_ADD_BTN);
			wait(4);
//			click(REGION_DROP_DOWN);
//			wait(4);
//			inputText(REGION_TEXT_AREA, "SK");
//			wait(5);
//			click(SK);
//			wait(2);
			click(CREATE_BTN);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving Partner Segment2");
		} catch (Exception e) {
			log.error("Error in Partner Segment2:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void CreatePartnerSegment3() {
		try {
			log.debug("Entering Create Partner Segment3");

			wait(2);
			javascript.executeScript("window.scrollBy(0,1500)", "");
			wait(1);
			wait(2);
			if (isElementPresent(NEW_PARTNER_SEGMENT)) {
				click(NEW_PARTNER_SEGMENT);
			}
//				click(NEW_REGIONAL_SEGMENT);
			wait(3);
			inputText(REGIONAL_SEGMENT_NAME_INPUT, "New Partner Segment13");
			wait(4);
			inputText(REGIONAL_SEGMENT_ID_INPUT, "13");
			wait(4);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(4);
			click(ACTIVE_START_DATE_ICON);
			wait(3);
			click(ACTIVE_START_DATE_OK_BTN);
			wait(2);
			inputText(TIME_INPUT, "03");
			wait(2);
			click(PARTNER_ROGER);
			wait(4);
			click(PARTNER_NAME_ADD_BTN);
			wait(2);
			click(PARTNER_FREEDOM);
			wait(4);
			click(PARTNER_NAME_ADD_BTN);
			wait(2);
			click(RESTRICTED_TO_NEW_CUSTOMER);
			wait(4);
			click(RESTRICTED_ADD_BTN);
			wait(1);
			click(RESTRICTED_TO_EXIST_CUSTOMER);
			wait(4);
			click(RESTRICTED_ADD_BTN);
			wait(4);
//			click(REGION_DROP_DOWN);
//			wait(4);
//			inputText(REGION_TEXT_AREA, "SK");
//			wait(5);
//			click(SK);
//			wait(2);
			click(CREATE_BTN);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving Partner Segment3");
		} catch (Exception e) {
			log.error("Error in Partner Segment3:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void CreateHybridSeg() {
		try {
			log.debug("Entering Create HybridSeg");

			wait(2);
			javascript.executeScript("window.scrollBy(0,1500)", "");
			wait(1);
			wait(2);
			if (isElementPresent(NEW_HYBRID_SEG)) {
				click(NEW_HYBRID_SEG);
			}
//				click(NEW_REGIONAL_SEGMENT);
			wait(3);
//			inputText(REGIONAL_SEGMENT_NAME_INPUT, "New Partner Segment11");
			wait(4);
			inputText(FUNCTIONAL_GROUPS_INPUT, "Retail");
			wait(2);
//			click(RETAIL_FUNCTION);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(4);
			inputText(SEGMENTS_INPUT, "New Customer Segment1");
//			click(SK);
			wait(2);
			inputText(SEGMENTS_INPUT, "New Regional Segment");
			wait(2);
			inputText(SEGMENTS_DEP_INPUT, "New Partner Segment11");
			wait(1);
			inputText(SEGMENTS_DEP_INPUT, "New Partner Segment12");
			wait(1);
			inputText(SEGMENTS_DEP_INPUT, "New Partner Segment13");
//			click(SK);
			wait(2);
//			click(HYBRID);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(CREATE_BTN);
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(HYBRID_PUBLIC_CHANGES);
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving to Create HybridSeg");
		} catch (Exception e) {
			log.error("Error in to Create HybridSeg:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void NavigateToOfferfilteringDasboard1() {
		try {
			log.debug("Entering Navigate To Offer filtering Dasboard");
			String OfferFilteringURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/common/uobject.jsp?tab=_Roles+%26+Segments&object=9152345600313574780";
			navigate(OfferFilteringURL);
			wait(1);
			test.log(LogStatus.INFO, "ATO User URL: ",
					"<a href=" + OfferFilteringURL + " > " + OfferFilteringURL + " </a>");
			wait(2);
			javascript.executeScript("window.scrollBy(0,1500)", "");
			wait(1);
			wait(2);

			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
//				wait(2);
//				scrollToTop();
//				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
//				wait(2);
			log.debug("Leaving Offer filtering Dasboard");
		} catch (Exception e) {
			log.error("Error in Offer filtering Dasboard:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void NavigateToOfferfilteringDasboard() {
		try {
			log.debug("Entering Navigate To Offer filtering Dasboard");
			String OfferFilteringURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/common/uobject.jsp?tab=_Roles+%26+Segments&object=9152345600313574780";
			navigate(OfferFilteringURL);
			wait(1);
			test.log(LogStatus.INFO, "ATO User URL: ",
					"<a href=" + OfferFilteringURL + " > " + OfferFilteringURL + " </a>");
			wait(2);
			javascript.executeScript("window.scrollBy(0,1500)", "");
			wait(1);
			wait(2);
			if (isElementPresent(NEW_REGIONAL_SEGMENT)) {
				click(NEW_REGIONAL_SEGMENT);
			}
//				click(NEW_REGIONAL_SEGMENT);

			wait(2);
			inputText(REGIONAL_SEGMENT_NAME_INPUT, "New Regional Segment1");
			wait(2);
			inputText(REGIONAL_SEGMENT_ID_INPUT, "Test1");

			wait(2);
			click(ACTIVE_START_DATE_ICON);
			wait(3);
			click(ACTIVE_START_DATE_OK_BTN);
			wait(2);
			inputText(TIME_INPUT, "02");
			wait(2);
			click(RESTRICTED_TO_NEW_CUSTOMER);
			wait(2);
			click(RESTRICTED_ADD_BTN);

			wait(1);
			click(RESTRICTED_TO_EXIST_CUSTOMER);
			wait(2);
			click(RESTRICTED_ADD_BTN);
			wait(2);
			click(REGION_DROP_DOWN);
			wait(2);
			click(AB);
			wait(2);
			click(CREATE_BTN);
			scrollToTop();
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
//			wait(2);
//			scrollToTop();
//			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
//			wait(2);
			log.debug("Leaving Offer filtering Dasboard");
		} catch (Exception e) {
			log.error("Error in Offer filtering Dasboard:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void NavigateToDiscountsMapping1() {
		try {
			log.debug("Entering Navigate To Discounts Mapping");
			String DiscountMappingURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/common/uobject.jsp?tab=_Discounts+Mapping&object=9152345600313574780";
			navigate(DiscountMappingURL);
			wait(1);
			test.log(LogStatus.INFO, "ATO User URL: ",
					"<a href=" + DiscountMappingURL + " > " + DiscountMappingURL + " </a>");
			wait(2);
			javascript.executeScript("window.scrollBy(0,1500)", "");

			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			log.debug("Leaving Discount Mapping");
		} catch (Exception e) {
			log.error("Error in Discount Mapping:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void Discount2YVP_InternetTV_Agreement() {
		try {
			log.debug("Entering to Add 2YVP Internet TV Agreement");

			wait(1);
			wait(2);
			if (isElementPresent(NEW_DISCONNECT_MAPPING)) {
				click(NEW_DISCONNECT_MAPPING);
			}
//				click(NEW_REGIONAL_SEGMENT);
			wait(3);
			inputText(DISCOUNT_INPUT, "*2YVP Internet + TV Agreement");
			wait(5);
			click(TWO_YVP_INTERNET_TV_AGREEMENT_DISCOUNT);
			wait(2);
			inputText(SEGMENTS_INPUT, "New Regional Segment1");
//			click(SK);
			wait(2);
			inputText(SEGMENTS_DEP_INPUT, "New Customer Segment1");
//			click(SK);
			wait(2);
			click(HYBRID);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(CREATE_BTN);
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(PUBLIC_CHANGES);
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving to Add 2YVP Internet TV Agreement");
		} catch (Exception e) {
			log.error("Error in to Add 2YVP Internet TV Agreement:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void DiscountFreedomInternet150() {
		try {
			log.debug("Entering to Add FreedomInternet150");

			wait(1);
			wait(2);
			if (isElementPresent(NEW_DISCONNECT_MAPPING)) {
				click(NEW_DISCONNECT_MAPPING);
			}
//				click(NEW_REGIONAL_SEGMENT);
			wait(3);
			inputText(DISCOUNT_INPUT, "Freedom Internet 150 Discount $5");
			wait(2);
			click(FREEDOM_INTERNET_150_DISCOUNT);
			wait(2);
			inputText(HYBRID_SEG_INPUT, "New Hybrid(Seg + FG)");
//			
			wait(2);
			click(HYBRID);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(CREATE_BTN);
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(PUBLIC_CHANGES);
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving to Add 2YVP Internet TV Agreement");
		} catch (Exception e) {
			log.error("Error in to Add 2YVP Internet TV Agreement:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void CreateRegionalSegment3() {
		try {
			log.debug("Entering Create Regional Segment3");

			wait(2);
			javascript.executeScript("window.scrollBy(0,1500)", "");
			wait(1);
			wait(2);
			if (isElementPresent(NEW_REGIONAL_SEGMENT)) {
				click(NEW_REGIONAL_SEGMENT);
			}
//				click(NEW_REGIONAL_SEGMENT);

			wait(3);
			inputText(REGIONAL_SEGMENT_NAME_INPUT, "New Regional Segment3");
			wait(4);
			inputText(REGIONAL_SEGMENT_ID_INPUT, "Test3");

			wait(4);
			click(ACTIVE_START_DATE_ICON);
			wait(3);
			click(ACTIVE_START_DATE_OK_BTN);
			wait(4);
			inputText(TIME_INPUT, "03");
			wait(4);
			click(RESTRICTED_TO_NEW_CUSTOMER);
			wait(3);
			click(RESTRICTED_ADD_BTN);

			wait(2);
			click(RESTRICTED_TO_EXIST_CUSTOMER);
			wait(3);
			click(RESTRICTED_ADD_BTN);
			wait(3);

			inputText(POSTAL_CODE_INPUT, "V8V4G6");
			wait(2);
			click(POSTAL_CODE_ADD_BTN);
			wait(2);
			click(CREATE_BTN);
			scrollToTop();
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
//			wait(2);
//			javascript.executeScript("window.scrollBy(0,-600)", "");
//			wait(2);
//			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
//			wait(2);
			log.debug("Leaving Regional Segment3");
		} catch (Exception e) {
			log.error("Error in Regional Segment3:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void CreateCustomerSegment() {
		try {
			log.debug("Entering Create Customer Segment");

			wait(2);
			javascript.executeScript("window.scrollBy(0,1500)", "");
			wait(1);
			wait(2);
			if (isElementPresent(NEW_CUSTOMER_SEGMENT)) {
				click(NEW_CUSTOMER_SEGMENT);
			}
//				click(NEW_REGIONAL_SEGMENT);

			wait(2);
			inputText(REGIONAL_SEGMENT_NAME_INPUT, "New Customer Segment1");
			wait(3);
			inputText(REGIONAL_SEGMENT_ID_INPUT, "CustomerSegment1");

			wait(2);
			click(ACTIVE_START_DATE_ICON);
			wait(2);
			click(ACTIVE_START_DATE_OK_BTN);
			wait(2);
			inputText(TIME_INPUT, "03");
			wait(9);
			click(CREATE_BTN);
			wait(4);
			//click(CREATE_BTN);
			scrollToTop();
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(SEGMENT_PUBLIC_CHANGES);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
//			javascript.executeScript("window.scrollBy(0,-600)", "");
//			wait(2);
//			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
//			wait(2);
			log.debug("Leaving Customer Segment");
		} catch (Exception e) {
			log.error("Error in Customer Segment:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void ValidateCustomerSegment() {
		try {
			log.debug("Entering Validate Customer Segment");

			wait(2);
			javascript.executeScript("window.scrollBy(0,1500)", "");
			wait(1);
			scrollDownToElement(CUSTOMER_SEGMENT);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			if (isElementPresent(CUSTOMER_SEGMENT)) {
				click(CUSTOMER_SEGMENT);
			}
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving Customer Segment");
		} catch (Exception e) {
			log.error("Error in Customer Segment:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void DeletedRegionalSegment2() {
		try {
			log.debug("Entering Regional Segment2 For Delete");

			wait(2);
			javascript.executeScript("window.scrollBy(0,1500)", "");
			wait(1);
			scrollDownToElement(REGIONAL_SEGMENT3);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			if (isElementPresent(REGIONAL_SEGMENT2_CHECK_BOX)) {
				click(REGIONAL_SEGMENT2_CHECK_BOX);
			}
//				click(NEW_REGIONAL_SEGMENT);
			wait(3);
			click(DELETE_BTN);
			wait(2);
			driver.switchTo().alert().accept();

			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving Delete Regional Segment2");
		} catch (Exception e) {
			log.error("Error in Regional Segment2 Delete:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void NavigateToDiscountsMapping() {
		try {
			log.debug("Entering Navigate To Discount Mapping");
			String DiscountMappingURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/ncobject.jsp?id=9152345600313574780&tab=_Discounts%20Mapping";
			navigate(DiscountMappingURL);
			wait(1);
			test.log(LogStatus.INFO, "ATO User URL: ",
					"<a href=" + DiscountMappingURL + " > " + DiscountMappingURL + " </a>");
//			wait(2);
			wait(2);
//			javascript.executeScript("window.scrollBy(0,1500)", "");
			wait(1);
			wait(2);
			if (isElementPresent(NEW_DISCONNECT_MAPPING)) {
				click(NEW_DISCONNECT_MAPPING);
			}
//				click(NEW_REGIONAL_SEGMENT);
			wait(3);
			inputText(DISCOUNT_INPUT, "*2YVP Internet + TV Agreement");
			wait(2);
//			click(SK);

			inputText(SEGMENTS_INPUT, "New Regional Segment1");
//			click(SK);
			wait(2);
			inputText(SEGMENTS_DEP_INPUT, "New Customer Segment1");
//			click(SK);
			wait(2);
			click(HYBRID);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(CREATE_BTN);
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving Discount Mapping");
		} catch (Exception e) {
			log.error("Error in Discount Mapping:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public COMOrdersPage openCOMOrderPageToGetId(String newTabExists, String acctNbr) {
		try {
			log.debug("Entering openCOMOrderPage");

			/*
			 * String accountPageurl = Utility.getValueFromPropertyFile("basepage_url") +
			 * "/common/search.jsp?explorer_mode=disable&object=7121040918013857702&return=%2Fcommon%2Fsearch.jsp%3Fdo_search%3Dyes%26_vname%3D1"
			 * + acctNbr +
			 * "%26o%3D1000%26fast_search%3Dyes%2522%253B%26object%3D7121040918013857702%26search_mode%3Dsearch%26_rname%3Din%26explorer_mode%3Ddisable&o=1000&_v_1=1"
			 * + acctNbr + "&_r_1=in&search_mode=search&do_search=yes&fast_search=yes";
			 */

			String accountPageurl = Utility.getValueFromPropertyFile("basepage_url")
					+ "/common/search.jsp?explorer_mode=disable&object=7121040918013857702&o=7022760707013150025&project=7022760707013150024&_v_1=1"
					+ acctNbr
					+ "&_r_1=in&search_mode=search&do_search=yes&fast_search=yes&resultID=9160628372913865140";

//			  for server 8150

//					  String accountPageurl = Utility.getValueFromPropertyFile("basepage_url") +
//					  "/common/search.jsp?search_mode=search&resultID=9163808681313156785&performed=true&fast_search=no&do_search=yes&ctrl=t4122758596013619618_4122758596013619619&tab=0&return=%2Fcommon%2Fuobject.jsp%3Ftab%3D_360%2BView%26object%3D9163808481013149222&project=7022760707013150024&currobject=o&system_index_on=false&collapse_but=no&profile_id=7121040918013857702&object=7121040918013857702&o=9163808481013149222&explorer_mode=disable&resultID=9163808681313156785&property_ishidden_group_-10=no&_r_1=eq&_v_1=1"
//					  + acctNbr +
//					  "&_r_2=eq&property_ishidden_widget_scope_options=no&currproject=current&property_ishidden_widget_templates=no&cfadmin=QZN9-JJSt-L0g2-7hJF-kWk7-hNHE";

			log.debug(accountPageurl);
			wait(2);
			if (newTabExists.equals("true"))
				wait(1);
			switchFirstNewTab();
			wait(2);
			navigate(accountPageurl);
			wait(2);

			// selectFromList(GENERIC_SEARCH, "equals");
			// wait(1);
			// click(SEACH_BUTTON);
			// wait(4);

			do {
				wait(2);
			} while (!isDisplayed(ACCOUNT_LINK));
			click(ACCOUNT_LINK);
			wait(3);
			orderPageLink = getCurrentURL();
			test.log(LogStatus.INFO, "ACCOUNT LINK URL: ",
					"<a href=" + orderPageLink + " > " + orderPageLink + " </a>");

			String str = getCurrentURL();
			String[] arr = str.split("\\=");
			String id = arr[1];

			CSVReader csvReader = new CSVReader(new FileReader("C:\\SHAW\\Upload\\MUT_Data.csv"));
			List<String[]> allData = csvReader.readAll();
			allData.get(1)[0] = id;

			CSVWriter csvWriter = new CSVWriter(new FileWriter(new File("C:\\SHAW\\Upload\\MUT_Data.csv")));
			csvWriter.writeAll(allData);
			csvWriter.flush();

			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving openCOMOrderPage");
		} catch (Exception e) {
			log.error("Error in openCOMOrderPage:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return new COMOrdersPage(test);
	}

	public void NavigateToAccountUpdateSession() {
		try {
			log.debug("Entering Navigate To Account Update Session");
			String AccountUpdateSessionURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/common/uobject.jsp?object=9147500477313241874";
			navigate(AccountUpdateSessionURL);
			wait(1);
			test.log(LogStatus.INFO, "ATO User URL: ",
					"<a href=" + AccountUpdateSessionURL + " > " + AccountUpdateSessionURL + " </a>");
			wait(2);
			// javascript.executeScript("window.scrollBy(0,1500)", "");
			wait(1);
			wait(2);
			if (isElementPresent(NEW_MASS_ACCOUNT_UPDATE)) {
				click(NEW_MASS_ACCOUNT_UPDATE);
			}
			wait(7);
			click(UPDATE_QUOTE_LINK);
			wait(4);
			Alert alert = driver.switchTo().alert();
			alert.accept();
			orderPageLink = getCurrentURL();
			test.log(LogStatus.INFO, "Update Quote URL: ",
					"<a href=" + orderPageLink + " > " + orderPageLink + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(EDIT_BTN);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(ADD_FILE_BTN);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			Runtime.getRuntime().exec("C:\\AccountJsFile\\js.exe");
			wait(5);
			switchToChildAlert();
			wait(5);
			click(UPLOAD_BTN);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(QUOTE_UPLOAD_BTN);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(4);
			click(MASS_UPDATE_TAB);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			orderPageLink = getCurrentURL();
			test.log(LogStatus.INFO, "Mass Update LINK: ",
					"<a href=" + orderPageLink + " > " + orderPageLink + " </a>");
			click(EDIT_BTN);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(3);
			scrollDownToElement(ACCOUNT_DATA);
			wait(3);
			click(ACCOUNT_DATA_ADD_FILE_BTN);
			wait(2);
			Runtime.getRuntime().exec("C:\\MUTdataFile\\MUTdata.exe");
			wait(5);
			switchToChildAlert();
			wait(7);
			click(UPLOAD_BTN);
			wait(4);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			inputText(PARALLEL_ORDER_INPUT, "2");
			wait(2);
			inputText(PARALLEL_ORDER_DELAY_INPUT, "2");
			wait(2);
			inputText(BATCH_SIZE_INPUT, "2");
			wait(2);
			inputText(ERRONEOUS_ORDER_INPUT, "2");
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(RUN_BTN);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(7);
			click(SAVE_BTN);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(8);
			scrollDownToElement(STATUS_TEXT);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			String MassUpdate = getCurrentURL();
			test.log(LogStatus.INFO, "Mass Update URL: ", "<a href=" + MassUpdate + " > " + MassUpdate + " </a>");
			wait(1);
//			scrollDownToElement(DETAILED_REPORT_BTN);
			click(DETAILED_REPORT_BTN);
			wait(1);
			switchThirdTab();
			wait(1);
//			switchPreviousTab();
			wait(1);
//			switchNextTab();

			wait(5);
			refreshPage();
			wait(1);
//			String orderStatus = getText(COMPLETED_TEXT);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			orderPageLink = getCurrentURL();
			test.log(LogStatus.INFO, "Detailed Report URL: ",
					"<a href=" + orderPageLink + " > " + orderPageLink + " </a>");
			String orderStatus = getText(COMPLETED_TEXT);
			if (orderStatus.equals("Completed")) {

				test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				test.log(LogStatus.INFO, "Order Status: " + orderStatus);
			}

			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			log.debug("Leaving Account Update Session");
		} catch (Exception e) {
			log.error("Error in Account Update Session:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public COMOrdersPage openCustomerPortalInformation(String newTabExists, String acctNbr) {
		try {
			log.debug("Entering openCustomerPortalInformation");

			/*
			 * String accountPageurl = Utility.getValueFromPropertyFile("basepage_url") +
			 * "/common/search.jsp?explorer_mode=disable&object=7121040918013857702&return=%2Fcommon%2Fsearch.jsp%3Fdo_search%3Dyes%26_vname%3D1"
			 * + acctNbr +
			 * "%26o%3D1000%26fast_search%3Dyes%2522%253B%26object%3D7121040918013857702%26search_mode%3Dsearch%26_rname%3Din%26explorer_mode%3Ddisable&o=1000&_v_1=1"
			 * + acctNbr + "&_r_1=in&search_mode=search&do_search=yes&fast_search=yes";
			 */

			String accountPageurl = Utility.getValueFromPropertyFile("basepage_url")
					+ "/common/search.jsp?explorer_mode=disable&object=7121040918013857702&o=7022760707013150025&project=7022760707013150024&_v_1=1"
					+ acctNbr
					+ "&_r_1=in&search_mode=search&do_search=yes&fast_search=yes&resultID=9160628372913865140";

//			  for server 8150

//					  String accountPageurl = Utility.getValueFromPropertyFile("basepage_url") +
//					  "/common/search.jsp?search_mode=search&resultID=9163808681313156785&performed=true&fast_search=no&do_search=yes&ctrl=t4122758596013619618_4122758596013619619&tab=0&return=%2Fcommon%2Fuobject.jsp%3Ftab%3D_360%2BView%26object%3D9163808481013149222&project=7022760707013150024&currobject=o&system_index_on=false&collapse_but=no&profile_id=7121040918013857702&object=7121040918013857702&o=9163808481013149222&explorer_mode=disable&resultID=9163808681313156785&property_ishidden_group_-10=no&_r_1=eq&_v_1=1"
//					  + acctNbr +
//					  "&_r_2=eq&property_ishidden_widget_scope_options=no&currproject=current&property_ishidden_widget_templates=no&cfadmin=QZN9-JJSt-L0g2-7hJF-kWk7-hNHE";

			log.debug(accountPageurl);
			wait(2);
			if (newTabExists.equals("true"))
				wait(1);
			switchFirstNewTab();
			wait(2);
			navigate(accountPageurl);
			wait(2);

			// selectFromList(GENERIC_SEARCH, "equals");
			// wait(1);
			// click(SEACH_BUTTON);
			// wait(4);

			do {
				wait(2);
			} while (!isDisplayed(ACCOUNT_LINK));
			click(ACCOUNT_LINK);
			wait(3);
			orderPageLink = getCurrentURL();
			wait(2);
			click(CUSTOMER_PORTAL_TAB);
			wait(1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			orderPageLink = getCurrentURL();
			test.log(LogStatus.INFO, "Customer Portal URL: ",
					"<a href=" + orderPageLink + " > " + orderPageLink + " </a>");
			scrollDownToElement(FIBRE75_TAB);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(FIBRE75_TAB);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			orderPageLink = getCurrentURL();
			test.log(LogStatus.INFO, "Fibre75 Page: ", "<a href=" + orderPageLink + " > " + orderPageLink + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			scrollDownToElement(ACTIVE_DOCUMENT_TAB);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(ACTIVE_DOCUMENT_TAB);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			orderPageLink = getCurrentURL();
			test.log(LogStatus.INFO, "Active Document Page: ",
					"<a href=" + orderPageLink + " > " + orderPageLink + " </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			scrollDownToElement(DID_NOT_REQUEST_TEXT);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			log.debug("Leaving Customer Portal Information");
		} catch (Exception e) {
			log.error("Error in Customer Portal Information:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return new COMOrdersPage(test);
	}

	public String ChangePhoneEndPointAddress(String value) {
		String endPoint = null;
		try {
			log.debug("Entering ChangePhoneEndPointAddress");
			String EndPointAddressrURL = Utility.getValueFromPropertyFile("basepage_url")
					+ "/common/uobject.jsp?object=9151404620013189249";
			navigate(EndPointAddressrURL);
			wait(1);
			test.log(LogStatus.INFO, "ATO User URL: ",
					"<a href=" + EndPointAddressrURL + " > " + EndPointAddressrURL + " </a>");
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(EDIT_BTN);
			wait(1);
			endPoint = getText(END_POINT_ADDRESS);
			wait(1);
			clear(END_POINT_ADDRESS);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			if (value.equals("Incorrect")) {
				wait(2);
				inputText(END_POINT_ADDRESS,
						"@@http://qaapp843cn.netcracker.com:8155/esi/telephonenumbermigration/rese");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}

			if (value.equals("Correct")) {
				wait(2);
				inputText(END_POINT_ADDRESS, endPoint);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			}
			wait(1);
			click(END_POINT_UPDATE_BTN);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			log.debug("Leaving Change Phone EndPointAddress");
		} catch (Exception e) {
			log.error("Error in Change Phone EndPointAddress:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return endPoint;
	}

	public static String generatePhoneNumber() {

		Random r = new Random();

		String numbers = 1000000000L + (long) (r.nextDouble() * 99999999L) + "";

		return numbers;
	}

	public void AddTelephoneNumberToCOMX(String AccntNm) {

		try {
			log.debug("Entering To Add Telephone Number To COMX Context");
			wait(1);
			click(COMX);
			log.debug("Navigating to COMX Tab");
			wait(5);
			int count = 1;
			while (isDisplayed(ADD_BTN) && count <= 4) {
				wait(2);
				count++;
				if (isElementPresent(CBS_ACCOUNT_NUMBER_INPUT)) {
					wait(1);
					click(CBS_ACCOUNT_NUMBER_INPUT);

					WebElement a1 = getWebElement(CBS_ACCOUNT_NUMBER_INPUT);
					jse.executeScript("arguments[0].value='" + AccntNm.substring(1) + "';", a1);
				}
				if (isElementPresent(TELEPHONE_NUMBER_INPUT)) {
					wait(1);
					click(TELEPHONE_NUMBER_INPUT);
					wait(2);
				}

				String phnumber = generatePhoneNumber();
				phonenumList.add(phnumber);
				System.out.println(phonenumList.size());
//				phonenumList.get(0);
				WebElement e1 = getWebElement(TELEPHONE_NUMBER_INPUT);
				jse.executeScript("arguments[0].value='" + phnumber + "';", e1);
//				

				// inputText(TELEPHONE_NUMBER_INPUT,str1);

				click(ADD_BTN);

			}
			test.log(LogStatus.PASS, "Add Telephone Number To COMX Context", "Telephone Number Added Successfully");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving To Add Telephone Number To COMX Context");
		} catch (Exception e) {
			log.error("Error in To Add Telephone Number To COMX Context:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}

	}

	public String phoneNumberConfiguration(String AddPhone, String PhoneList, String prod) {
		try {
			log.debug("Entering phoneNumberConfiguration");
			if (AddPhone.equals("Add Phone")) {
				wait(4);
				click(ADD_PHONE_PROD);
				wait(4);
			}
			if (PhoneList.equals("First Dropdown")) {
				wait(2);
				selectFromList(PHONE_PROD_LIST, prod);
				wait(2);
				click(PHONE_WRENCH_BUTTON);
				wait(3);
			}
			if (PhoneList.equals("Second Dropdown")) {
				selectFromList(SECOND_PHONE_PROD_LIST, prod);
				wait(2);
				click(PHONE_WRENCH_BUTTON);
				wait(3);
			}
			if (PhoneList.equals("Third Dropdown")) {
				selectFromList(THIRD_PHONE_PROD_LIST, prod);
				wait(2);
				click(PHONE_WRENCH_BUTTON);
				wait(3);
			}

			switchToChildAlert();
			click(FIRST_SELECT_NUMBER);
			wait(2);
			click(MIGRATE_RADIO_BTN);
			wait(5);
			WebElement e1 = getWebElement(MIGRATE_PHN_NBR_INPUT);
			jse.executeScript("arguments[0].value='" + phonenumList.get(2) + "';", e1);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(3);
			click(MIGRATE_NOW_BTN);
//			inputText(, phonenumList.get(2));
			wait(5);
			click(LISTINGTYPE_LIST);
			wait(3);
			click(FOUR_ONE_ONE_ONLY);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
//			click(OK_BUTTON_TO_CLOSE);
			wait(2);
			click(SECOND_SELECT_NUMBER);
			wait(2);
			click(MIGRATE_RADIO_BTN);
			wait(2);
			WebElement e2 = getWebElement(MIGRATE_PHN_NBR_INPUT);
			jse.executeScript("arguments[0].value='" + phonenumList.get(3) + "';", e2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(MIGRATE_NOW_BTN);
			wait(2);
			click(LISTINGTYPE_LIST);
			wait(3);
			click(FOUR_ONE_ONE_ONLY);
			wait(2);
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			click(OK_BUTTON_TO_CLOSE);
			wait(1);
			log.debug("Leaving phoneNumberConfiguration");
		} catch (Exception e) {
			log.error("Error in phoneNumberConfiguration:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
		return prod;
	}

	public void addDistinctiveRingForMigrate(String IncrementBtn, String RingPattern) {
		try {
			log.debug("Entering addDistinctiveRingForMigrate");
			wait(1);
			if (IncrementBtn.equals("One")) {
				click(DISTINCTIVE_RING_INCREMENT1);
				wait(2);
				click(DR_SELECT_NUMBER);
				wait(2);
				switchToChildAlert();
				click(MIGRATE_RADIO_BTN);
				wait(4);
				WebElement e1 = getWebElement(MIGRATE_PHN_NBR_INPUT);
				jse.executeScript("arguments[0].value='" + phonenumList.get(1) + "';", e1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(2);
				click(MIGRATE_NOW_BTN);
				wait(4);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				click(LISTINGTYPE_LIST);
				wait(2);
				click(FOUR_ONE_ONE_ONLY);
				wait(2);
				click(FEATURES_TAB);
				wait(2);
				click(RING_PATTERN_DROP_DOWN);
				wait(2);
				if (RingPattern.equals("3 short rings")) {
					wait(1);
					click(TYPE_3_RING_SHORT);
					wait(1);
				}
				if (RingPattern.equals("2 short rings")) {
					wait(1);
					click(TYPE_2_RING_SHORT);
					wait(1);
				}
				test.log(LogStatus.PASS, "Add Distinctive Ring For Migrate", "Added Distinctive Ring Successfully");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(2);
				click(OK_BUTTON_TO_CLOSE);
			}
			if (IncrementBtn.equals("Two")) {
				click(DISTINCTIVE_RING_INCREMENT2);
				wait(2);
				click(DR_SELECT_NUMBER);
				wait(2);
				switchToChildAlert();
				click(MIGRATE_RADIO_BTN);
				wait(4);
				WebElement e1 = getWebElement(MIGRATE_PHN_NBR_INPUT);
				jse.executeScript("arguments[0].value='" + phonenumList.get(0) + "';", e1);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(2);
				click(MIGRATE_NOW_BTN);
				wait(4);
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(2);
				click(DIRECTORY_LISTING_TAB);
				wait(1);
				click(LISTINGTYPE_LIST);
				wait(2);
				click(FOUR_ONE_ONE_ONLY);
				wait(2);
				click(FEATURES_TAB);
				wait(2);
				click(RING_PATTERN_DROP_DOWN);
				wait(2);
				if (RingPattern.equals("3 short rings")) {
					wait(1);
					click(TYPE_3_RING_SHORT);
					wait(1);
				}
				if (RingPattern.equals("2 short rings")) {
					wait(1);
					click(TYPE_2_RING_SHORT);
					wait(1);
				}
				test.log(LogStatus.PASS, "Add Distinctive Ring For Migrate", "Added Distinctive Ring Successfully");
				test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				wait(2);
				click(OK_BUTTON_TO_CLOSE);
			}

			log.debug("Leaving addDistinctiveRingForMigrate");
		} catch (Exception e) {
			log.error("Error in addDistinctiveRingForMigrate:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void StringMatchFromFile() {
		System.out.println("Entering to read file");
		File file = new File("C:/SHAW/Upload/integration_clust1.log");
		System.out.println("Finding file");
		String line = null;
		ArrayList<String> fileContents = new ArrayList<>();
		try {
			FileReader fReader = new FileReader(file);
			BufferedReader fileBuff = new BufferedReader(fReader);
			System.out.println("reading file");
			while ((line = fileBuff.readLine()) != null) {
				fileContents.add(line);
//                  System.out.println("Read file");
			}
			fileBuff.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println(fileContents.toString().contains("systemFrom='NETCRACKER', systemTo='ESI'"));
	}

	public void UnlockOperationOrder() {
		try {
			log.debug("Entering Unlock Order");

			if (isElementPresent(OPERATION)) {
				click(OPERATION);
			}
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			moveToMouseOver(UNLOCK_ORDER);
			wait(1);
			click(UNLOCK_ORDER);
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(2);
			click(YES_BTN);
			wait(2);
//		driver.switchTo().alert().accept();

			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving Unlock Order");
		} catch (Exception e) {
			log.error("Error in Unlock Order:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}

	public void CloseCBS() {
		try {
			log.debug("Entering Close CBS");
			wait(2);
			if (isElementPresent(CLOSE_CBS)) {
				click(CLOSE_CBS);
				wait(4);
			}
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(1);
			click(CONFIRM_ACTIVATION);
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));

			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving Close CBS");
		} catch (Exception e) {
			log.error("Error in Close CBS:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
}
