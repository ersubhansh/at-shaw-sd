package com.netcracker.shaw.at_shaw_sd.pageobject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.netcracker.shaw.at_shaw_sd.util.Constants;
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.netcracker.shaw.element.PageElement;
import com.netcracker.shaw.setup.SeleniumTestUp;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

public class BasePage<T extends PageElement> {
	protected WebDriver driver = SeleniumTestUp.DRIVER;
	public ExtentTest test;
	public long DEFAULT_TIME_OUT = Long.parseLong(Utility.getValueFromPropertyFile("timeout"));
	Logger log = Logger.getLogger(BasePage.class);

	public BasePage(ExtentTest test) {
		this.test = test;
	}

	public BasePage(ExtentTest test, WebDriver driver) {
		this.test = test;
		this.driver = driver;
	}

	JavascriptExecutor javascript = ((JavascriptExecutor) driver);
	Actions actions = new Actions(driver);

	public WebElement getWebElement(T element, String... placeholder) {
		WebElement e1 = driver.findElement(element.getBy(placeholder));
		return e1;
	}

	//Returns list of web elements
	public List<WebElement> getListElement(T element, String... placeholder) {
		List<WebElement> list = driver.findElements(element.getBy(placeholder));
		return list;
	}

	// clicking on the web element
	public void click(T element) {
		try {
			if (isElementPresent(element)) {
				getWebElement(element).click();
				test.log(LogStatus.PASS, "Click element", "Clicked " + element);
				
			} else {
				test.log(LogStatus.FAIL, "Could not click", "Element not Found  " + element);
				test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
				Assert.fail();
			}
		} catch (Exception e) {
			log.error("Error in click:" + e.getMessage());
			log.debug("The required element is not found");
			test.log(LogStatus.FAIL, "Could not click", "Element not Found  " + element);
		}
	}
	
	public void elementClick(T element, String... placeholder) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(element.getBy(placeholder)));
		getWebElement(element).click();
		test.log(LogStatus.PASS, "Click element", "Clicked " + element);
	}

	// sending value to the input text with WebDriver wait
	public void inputText1(T element, String value, String...placeholder) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(element.getBy(placeholder)));
		clear(element);
		getWebElement(element).click();
		getWebElement(element).sendKeys(value);
		test.log(LogStatus.PASS, " Enter Text", "Entered value  " + value + "in " + element);
	}
	
	// clearing the web element
	public void clear(T element) {
		getWebElement(element).clear();
	}

	// sending value to the input text
	public void inputText(T element, String value) {
		clear(element);
		getWebElement(element).click();
		getWebElement(element).sendKeys(value);
		test.log(LogStatus.PASS, " Enter Text", "Entered value  " + value + " in " + element);
	}

	public void clickAndEnterText(T element, String value) {
		wait("1");
		getWebElement(element).click();
		getWebElement(element).sendKeys(value);
		test.log(LogStatus.PASS, " Enter Text", "Entered value  " + value + " in " + element);
	}

	//Search text, Numerical value in WebPage
	public void searchValueInWebPage(String Value) {
		WebElement We = driver.findElement(By.xpath("//span[.='" + Value + "']"));
		test.log(LogStatus.PASS, "Search element", "Searched " + Value);
	}
		
	public void navigate(String url) {
		driver.get(url);
	}

	public String getCurrentURL() {
		return (driver.getCurrentUrl());
	}

	public void selectFromList(T element, String value) {
		new Select(getWebElement(element)).selectByVisibleText(value);
	}

	public boolean isElementPresent(T element) {
		List<WebElement> e = null;
		e = getListElement(element);
		if (e.size() == 0)
			return false;
		else
			return true;
	}

	public boolean isDynamicElementPresent(String keyword, String elementName) {
		String dynamicXpath = "//" + keyword + "[contains(text(),'" + elementName + "')]";
		List<WebElement> list = driver.findElements(By.xpath(dynamicXpath));
		if (list.size() == 0)
			return false;
		else
			return true;
	}

	public int countRowsinTable(T element) {
		return getListElement(element).size();
	}

	public boolean verifyElementPresent(T element) {
		boolean result = isElementPresent(element);
		return result;
	}

	public void wait(String timeout) {
		try {
			Thread.sleep(Long.parseLong(timeout));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getText(T element) {
		return getWebElement(element).getText();
	}

	public boolean isDisplayed(T element) {
		return getWebElement(element).isDisplayed();
	}

	
	
	public boolean isEnabled(T element) {
		return getWebElement(element).isEnabled();
	}

	public String getAttribue(T element, String value) {
		return getWebElement(element).getAttribute(value);
	}
	
	public String getCssValue(T element, String value) {
		return getWebElement(element).getCssValue(value);
	}

	public void switchWindow() {
		String mainWindowHnd = driver.getWindowHandle();
		Set<String> set = driver.getWindowHandles();
		for (String handle : set) {
			if (!(handle == mainWindowHnd)) {
				driver.switchTo().window(handle);
			}
		}
	}

	public void switchFirstNewTab() {
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(1));
	}

	public void switchSecondNewTab() {
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(2));
	}

	public void switchNextTab() throws Exception {
		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(2));
		wait(1);
	}

	public void switchThirdTab() throws Exception {
		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(3));
		wait(1);
	}
	
	public void switchPreviousTab() throws Exception {
		wait(2);
		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(1));
	}

	public void switchToSecondWindow() {
		Set<String> Handles = driver.getWindowHandles();
		String firstHandle = driver.getWindowHandle();
		Handles.remove(firstHandle);
		String winHandle = Handles.iterator().next();
		if (winHandle != firstHandle) {
			String secondWinHandle = winHandle;
			driver.switchTo().window(secondWinHandle);
		}
	}

	public void switchToChildAlert() {
		Set<String> windowHandle = driver.getWindowHandles();
		windowHandle.remove(windowHandle.iterator().next());
	}

	public void wait(int timeout) throws Exception {
		long milliSeconds = timeout * 1000;
		Thread.sleep(milliSeconds);
	}

	public String addScreenShot(String str) throws Exception {
		File directory = new File(String.valueOf(Constants.SCREENSHOT_PATH));

		if (!directory.exists()) {
			directory.mkdir();
		}
		System.out.println("Dir name is " + directory);
		DateFormat df = new SimpleDateFormat("yyyy_MMM_ hh_mm_ss a");
		Date d = new Date();
		String time = df.format(d);
		System.out.println(time);
		File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(f, new File(directory + "/" + str + time + ".png"));
		return directory + "/" + str + time + ".png";
	}

	public String addScreenshot() {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String encodedBase64 = null;
		FileInputStream fileInputStreamReader = null;
		try {
			fileInputStreamReader = new FileInputStream(scrFile);
			byte[] bytes = new byte[(int) scrFile.length()];
			fileInputStreamReader.read(bytes);
			encodedBase64 = new String(Base64.getEncoder().encodeToString(bytes));
			// encodedBase64 =new String(Base64.encodeBase64(bytes), "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "data:image/png;base64," + encodedBase64;
	}

	public boolean checkStatusComplete(T elementStatus) {
		return getText(elementStatus).equalsIgnoreCase("completed");
	}

	public boolean validateAttributeValue(T element, String attrName, String attrVal) {
		String responseXML = getText(element).trim().replaceAll("(\\s|\n)", "");
		String attr1 = attrName + ">" + attrVal;
		String attr2 = attrName + "=\"" + attrVal + "\"";
		String attr3 = attrName+ "</ns3:CharacteristicName><ns3:CharacteristicCategory><ns3:CategoryName>DeviceInfo</ns3:CategoryName></ns3:CharacteristicCategory><ns3:CharacteristicValue><ns3:Value>"+ attrVal;
		return (responseXML.contains(attr1) || responseXML.contains(attr2) || responseXML.contains(attr3));
	}

	public void scrollUp() throws Exception {
		wait(1);
		javascript.executeScript("window.scrollBy(0,-5000)", "");
		wait(1);
	}

	public void scrollUp600() throws Exception {
		wait(1);
		javascript.executeScript("window.scrollBy(0,-600)", "");
		wait(1);
	}

	public void scrollDown600() throws Exception {
		wait(1);
		javascript.executeScript("window.scrollBy(0,600)", "");
		wait(2);
		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
	}

	public void scrollToTop() throws Exception {
		wait(1);
		javascript.executeScript("window.scrollTo(0,0)", "");
	}

	public void scrollDown() throws Exception {
		wait(1);
		javascript.executeScript("window.scrollBy(0,5000)", "");
		test.log(LogStatus.INFO, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
	}

	public void scrollDownAndClick(T element) {
		actions.moveToElement(getWebElement(element));
		actions.click();
		actions.perform();
	}

	public void scrollDownToElement(T element) {
		actions.moveToElement(getWebElement(element));
		actions.perform();
	}

	public void moveToMouseOver(T element) {
		actions.moveToElement(getWebElement(element)).build().perform();
	}

	public void refreshPage() throws Exception {
		wait(6);
		driver.navigate().refresh();
	}

	public boolean validateAttributeValue(String responseXML, String attributeVal) {
		return responseXML.contains(attributeVal);
	}
}


