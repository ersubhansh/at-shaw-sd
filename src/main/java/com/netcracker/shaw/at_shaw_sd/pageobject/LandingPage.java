package com.netcracker.shaw.at_shaw_sd.pageobject;

import static com.netcracker.shaw.element.pageor.LandingPageElement.LOGIN;
import static com.netcracker.shaw.element.pageor.LandingPageElement.PASSWORD;
import static com.netcracker.shaw.element.pageor.LandingPageElement.USER_NAME;
import static com.netcracker.shaw.element.pageor.LandingPageElement.LOGOFF_BTN;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

@SuppressWarnings(value = {"unchecked", "rawtypes"})
public class LandingPage<LandingPageElement> extends BasePage {
	public ExtentTest test;
	Logger log = Logger.getLogger(LandingPage.class);

	public LandingPage(ExtentTest test) {
		super(test);
	}

	public LandingPage(ExtentTest test, WebDriver driver) {
		super(test, driver);
	}

	public void setTest(ExtentTest test1) {
		test = test1;
	}

	public void setDriver(WebDriver driver1) {
		driver = driver1;
	}
	
	public void Login() {
		String ExpectedTitle = "Inventory - NetCracker";
		try {
			log.debug("Entering Login");
			String ServerURL=Utility.getValueFromPropertyFile("basepage_url");
			navigate(ServerURL);
			test.log(LogStatus.PASS, "Server URL: ","<a href=" + ServerURL +" > "+ ServerURL +" </a>");
			driver.manage().window().maximize();
			log.debug("Opening the URL");
			if (isDisplayed(LOGIN))
				test.log(LogStatus.PASS, "Browser opened Successfully", "LOGIN button is visisble");
			
			else
				test.log(LogStatus.FAIL, "Browser didn't open");
			inputText1(USER_NAME, Utility.getValueFromPropertyFile("user"));
			log.debug("Entering User Name");
			getWebElement(PASSWORD).sendKeys(Utility.decryptPassword(Utility.getValueFromPropertyFile("encypt_Key"), Utility.getValueFromPropertyFile("encypt_Password")));
			log.debug("Entering Password");
			elementClick(LOGIN);
			log.debug("Clicking Login Button");
			log.debug("Title::" + driver.getTitle());
			test.log(LogStatus.PASS, "Login", "Logged IN Successfully");
			log.debug("Leaving Login");
		} catch (Exception e) {
			log.error("Error in Login:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void MDULogin() {
		String ExpectedTitle = "Inventory - NetCracker";
		try {
			log.debug("Entering Login");
			String ServerURL=Utility.getValueFromPropertyFile("basepage_url");
			navigate(ServerURL);
			test.log(LogStatus.PASS, "Server URL: ","<a href=" + ServerURL +" > "+ ServerURL +" </a>");
			driver.manage().window().maximize();
			log.debug("Opening the URL");
			if (isDisplayed(LOGIN))
				test.log(LogStatus.PASS, "Browser opened Successfully", "LOGIN button is visisble");
			
			else
				test.log(LogStatus.FAIL, "Browser didn't open");
			inputText1(USER_NAME, Utility.getValueFromPropertyFile("MDUuser"));
			log.debug("Entering User Name");
			inputText1(PASSWORD, Utility.getValueFromPropertyFile("MDUuserpwd"));
			log.debug("Entering Password");
			elementClick(LOGIN);
			log.debug("Clicking Login Button");
			log.debug("Title::" + driver.getTitle());
			test.log(LogStatus.PASS, "Login", "Logged IN Successfully");
			log.debug("Leaving Login");
		} catch (Exception e) {
			log.error("Error in Login:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void openUrl() {
		try {
			log.debug("Entering openUrl");
			wait(5);
			String openOEURl=Utility.getValueFromPropertyFile("basepage_url")+"/oe.newCustomerDesktop.nc?accountId=&locationId=221";
			log.debug("Opening OE Page");
			navigate(openOEURl);
			test.log(LogStatus.PASS, "OE Screen URL: ","<a href=" + openOEURl +" > "+ openOEURl +" </a>");
			test.log(LogStatus.PASS, "Open Browser", "Order Creation Page opened");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving openUrl");
		} catch (Exception e) {
			log.error("Error in Login:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void loggingOffCLECOPSUser() {
		try {
			log.debug("Entering loggingOffCLECOPSUser");
			wait(6);
			click(LOGOFF_BTN);
			wait(8);
			test.log(LogStatus.PASS, "Login", "Logged off Successfully");
			log.debug("Leaving loggingOffCLECOPSUser");
		} catch (Exception e) {
			log.error("Error in Login:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void openISEDPinUrl() {
		try {
			log.debug("Entering openISEDPinUrl");
			String ISEDcustOrderPageUrl = Utility.getValueFromPropertyFile("basepage_url")+ "/oe.newCustomerDesktop.nc?isedPostalCode=pin&isedPin=4444&locationId=221&accountId=";
			navigate(ISEDcustOrderPageUrl);
			wait(1);
			test.log(LogStatus.PASS, "ISED Pin URL: ","<a href=" + ISEDcustOrderPageUrl +" > "+ ISEDcustOrderPageUrl +" </a>");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			wait(3);
			log.debug("Leaving openISEDPinUrl");
		} catch (Exception e) {
			log.error("Error in openISEDPinUrl:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void openUrlWithLocationId2() {
		try {
			log.debug("Entering openUrlLocationId2");
			String CalagryURLwithLocationId2=Utility.getValueFromPropertyFile("basepage_url")+"/oe.newCustomerDesktop.nc?locationId=2&accountId=";
			navigate(CalagryURLwithLocationId2);
			wait(1);
			test.log(LogStatus.PASS, "Calagry URL with LocId 2:","<a href=" + CalagryURLwithLocationId2 +" > "+ CalagryURLwithLocationId2 +" </a>");
			test.log(LogStatus.PASS, "Open Browser", "Order Creation Page opened WithLocationID2");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving openUrlLocationId2");
		} catch (Exception e) {
			log.error("Error in Login:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void openUrlAddAccountWithLocationId2(String accnt) {
		try {
			log.debug("Entering openUrlLocationId2");
			String CalagryURLwithLocationId2=Utility.getValueFromPropertyFile("basepage_url")+"/oe.newCustomerDesktop.nc?locationId=2&accountId=1" + accnt +" ";
			navigate(CalagryURLwithLocationId2);
			wait(1);
			test.log(LogStatus.PASS, "Calagry URL with LocId 2:","<a href=" + CalagryURLwithLocationId2 +" > "+ CalagryURLwithLocationId2 +" </a>");
			test.log(LogStatus.PASS, "Open Browser", "Order Creation Page opened WithLocationID2");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving openUrlLocationId2");
		} catch (Exception e) {
			log.error("Error in Login:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void NavigateAccountIdFromStubWithLocationId2(String accnt) {
		try {
			log.debug("Entering openUrlLocationId2");
			String CalagryURLwithLocationId2=Utility.getValueFromPropertyFile("basepage_url")+"/oe.newCustomerDesktop.nc?locationId=2&accountId=" + accnt +" ";
			navigate(CalagryURLwithLocationId2);
			wait(3);
			test.log(LogStatus.PASS, "Calagry URL with LocId 2:","<a href=" + CalagryURLwithLocationId2 +" > "+ CalagryURLwithLocationId2 +" </a>");
			test.log(LogStatus.PASS, "Open Browser", "Order Creation Page opened WithLocationID2");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving openUrlLocationId2");
		} catch (Exception e) {
			log.error("Error in Login:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	
	public void openAdminUrl() {
		try {
			log.debug("Entering openAdminUrl");
			String openAdminURl=Utility.getValueFromPropertyFile("basepage_url")+"/admin/users/groups.jsp?tab=_All+Users&group=101&object=101";
			log.debug("Opening Admin Tool Page");
			navigate(openAdminURl);
			test.log(LogStatus.PASS, "Admin Tool Screen URL: ","<a href=" + openAdminURl +" > "+ openAdminURl +" </a>");
			test.log(LogStatus.PASS, "Open Browser", "User Creation Page opened");
			test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving openAdminUrl");
		} catch (Exception e) {
			log.error("Error in Login:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void A1Login() {
		String ExpectedTitle = "Inventory - NetCracker";
		try {
			log.debug("Entering Login");
			String ServerURL=Utility.getValueFromPropertyFile("basepage_url");
			navigate(ServerURL);
			test.log(LogStatus.PASS, "Server URL: ","<a href=" + ServerURL +" > "+ ServerURL +" </a>");
			driver.manage().window().maximize();
			log.debug("Opening the URL");
			if (isDisplayed(LOGIN))
				test.log(LogStatus.PASS, "Browser opened Successfully", "LOGIN button is visisble");
			
			else
				test.log(LogStatus.FAIL, "Browser didn't open");
			inputText1(USER_NAME, Utility.getValueFromPropertyFile("A1user"));
			log.debug("Entering User Name");
			inputText1(PASSWORD, Utility.getValueFromPropertyFile("A1userpwd"));
			log.debug("Entering Password");
			elementClick(LOGIN);
			log.debug("Clicking Login Button");
			log.debug("Title::" + driver.getTitle());
			test.log(LogStatus.PASS, "Login", "Logged IN Successfully");
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving Login");
		} catch (Exception e) {
			log.error("Error in Login:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void A2Login() {
		String ExpectedTitle = "Inventory - NetCracker";
		try {
			log.debug("Entering Login");
			String ServerURL=Utility.getValueFromPropertyFile("basepage_url");
			navigate(ServerURL);
			test.log(LogStatus.PASS, "Server URL: ","<a href=" + ServerURL +" > "+ ServerURL +" </a>");
			driver.manage().window().maximize();
			log.debug("Opening the URL");
			if (isDisplayed(LOGIN))
				test.log(LogStatus.PASS, "Browser opened Successfully", "LOGIN button is visisble");
			
			
			else
				test.log(LogStatus.FAIL, "Browser didn't open");
			inputText1(USER_NAME, Utility.getValueFromPropertyFile("A2user"));
			log.debug("Entering User Name");
			inputText1(PASSWORD, Utility.getValueFromPropertyFile("A2userpwd"));
			log.debug("Entering Password");
			elementClick(LOGIN);
			log.debug("Clicking Login Button");
			log.debug("Title::" + driver.getTitle());
			test.log(LogStatus.PASS, "Login", "Logged IN Successfully");
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving Login");
		} catch (Exception e) {
			log.error("Error in Login:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void TestUser1Login() {
		String ExpectedTitle = "Inventory - NetCracker";
		try {
			log.debug("Entering Login");
			String ServerURL=Utility.getValueFromPropertyFile("basepage_url");
			navigate(ServerURL);
			test.log(LogStatus.PASS, "Server URL: ","<a href=" + ServerURL +" > "+ ServerURL +" </a>");
			driver.manage().window().maximize();
			log.debug("Opening the URL");
			if (isDisplayed(LOGIN))
				test.log(LogStatus.PASS, "Browser opened Successfully", "LOGIN button is visisble");
			
			else
				test.log(LogStatus.FAIL, "Browser didn't open");
			inputText1(USER_NAME, Utility.getValueFromPropertyFile("TestUser1"));
			log.debug("Entering User Name");
			inputText1(PASSWORD, Utility.getValueFromPropertyFile("TestUser1Psw"));
			log.debug("Entering Password");
			elementClick(LOGIN);
			log.debug("Clicking Login Button");
			log.debug("Title::" + driver.getTitle());
			test.log(LogStatus.PASS, "Login", "Logged IN Successfully");
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving Login");
		} catch (Exception e) {
			log.error("Error in Login:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void mj1Login() {
		String ExpectedTitle = "Inventory - NetCracker";
		try {
			log.debug("Entering Login");
			String ServerURL=Utility.getValueFromPropertyFile("basepage_url");
			navigate(ServerURL);
			test.log(LogStatus.PASS, "Server URL: ","<a href=" + ServerURL +" > "+ ServerURL +" </a>");
			driver.manage().window().maximize();
			log.debug("Opening the URL");
			if (isDisplayed(LOGIN))
				test.log(LogStatus.PASS, "Browser opened Successfully", "LOGIN button is visisble");
			
			else
				test.log(LogStatus.FAIL, "Browser didn't open");
			inputText1(USER_NAME, Utility.getValueFromPropertyFile("mjuser1"));
			log.debug("Entering User Name");
			inputText1(PASSWORD, Utility.getValueFromPropertyFile("mjpsw"));
			log.debug("Entering Password");
			elementClick(LOGIN);
			log.debug("Clicking Login Button");
			log.debug("Title::" + driver.getTitle());
			test.log(LogStatus.PASS, "Login", "Logged IN Successfully");
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving Login");
		} catch (Exception e) {
			log.error("Error in Login:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void major1Login() {
		String ExpectedTitle = "Inventory - NetCracker";
		try {
			log.debug("Entering Login");
			String ServerURL=Utility.getValueFromPropertyFile("basepage_url");
			navigate(ServerURL);
			test.log(LogStatus.PASS, "Server URL: ","<a href=" + ServerURL +" > "+ ServerURL +" </a>");
			driver.manage().window().maximize();
			log.debug("Opening the URL");
			if (isDisplayed(LOGIN))
				test.log(LogStatus.PASS, "Browser opened Successfully", "LOGIN button is visisble");
			
			else
				test.log(LogStatus.FAIL, "Browser didn't open");
			inputText1(USER_NAME, Utility.getValueFromPropertyFile("mjuser1"));
			log.debug("Entering User Name");
			inputText1(PASSWORD, Utility.getValueFromPropertyFile("mjpsw"));
			log.debug("Entering Password");
			elementClick(LOGIN);
			log.debug("Clicking Login Button");
			log.debug("Title::" + driver.getTitle());
			test.log(LogStatus.PASS, "Login", "Logged IN Successfully");
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving Login");
		} catch (Exception e) {
			log.error("Error in Login:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	public void of1Login() {
		String ExpectedTitle = "Inventory - NetCracker";
		try {
			log.debug("Entering Login");
			String ServerURL=Utility.getValueFromPropertyFile("basepage_url");
			navigate(ServerURL);
			test.log(LogStatus.PASS, "Server URL: ","<a href=" + ServerURL +" > "+ ServerURL +" </a>");
			driver.manage().window().maximize();
			log.debug("Opening the URL");
			if (isDisplayed(LOGIN))
				test.log(LogStatus.PASS, "Browser opened Successfully", "LOGIN button is visisble");
			
			else
				test.log(LogStatus.FAIL, "Browser didn't open");
			inputText1(USER_NAME, Utility.getValueFromPropertyFile("OFuser1"));
			log.debug("Entering User Name");
			inputText1(PASSWORD, Utility.getValueFromPropertyFile("OFpsw"));
			log.debug("Entering Password");
			elementClick(LOGIN);
			log.debug("Clicking Login Button");
			log.debug("Title::" + driver.getTitle());
			test.log(LogStatus.PASS, "Login", "Logged IN Successfully");
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving Login");
		} catch (Exception e) {
			log.error("Error in Login:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
	
	
	public void fg1Login() {
		String ExpectedTitle = "Inventory - NetCracker";
		try {
			log.debug("Entering Login");
			String ServerURL=Utility.getValueFromPropertyFile("basepage_url");
			navigate(ServerURL);
			test.log(LogStatus.PASS, "Server URL: ","<a href=" + ServerURL +" > "+ ServerURL +" </a>");
			driver.manage().window().maximize();
			log.debug("Opening the URL");
			if (isDisplayed(LOGIN))
				test.log(LogStatus.PASS, "Browser opened Successfully", "LOGIN button is visisble");
			
			else
				test.log(LogStatus.FAIL, "Browser didn't open");
			inputText1(USER_NAME, Utility.getValueFromPropertyFile("FGuser1"));
			log.debug("Entering User Name");
			inputText1(PASSWORD, Utility.getValueFromPropertyFile("FGpsw"));
			log.debug("Entering Password");
			elementClick(LOGIN);
			log.debug("Clicking Login Button");
			log.debug("Title::" + driver.getTitle());
			test.log(LogStatus.PASS, "Login", "Logged IN Successfully");
			test.log(LogStatus.PASS, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			log.debug("Leaving Login");
		} catch (Exception e) {
			log.error("Error in Login:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(false, e.getMessage());
		}
	}
}
