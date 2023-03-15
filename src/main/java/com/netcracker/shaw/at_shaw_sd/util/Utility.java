package com.netcracker.shaw.at_shaw_sd.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

public class Utility {

	public static String sheetName;
	static Logger log = Logger.getLogger(Utility.class);
	private static final String TASKLIST = "tasklist";

	public WebDriver getRemoteBrowser() throws MalformedURLException {
		WebDriver driver = null;
		if (getValueFromPropertyFile("browser").equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", getValueFromPropertyFile("chromedriver_path"));
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("requireWindowFocus", true);
			driver = new RemoteWebDriver(new URL(getValueFromPropertyFile("Node")), capabilities);

		} else if (getValueFromPropertyFile("browser").equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver", getValueFromPropertyFile("iedriver_path"));
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability("requireWindowFocus", true);
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			driver = new InternetExplorerDriver();
		}
		return driver;
		
	}

	public WebDriver getBrowser() {
		WebDriver driver = null;
		if (getValueFromPropertyFile("browser_mode").equalsIgnoreCase("normal")) {
			System.setProperty("webdriver.chrome.driver", getValueFromPropertyFile("chromedriver_path"));
			driver = new ChromeDriver();

		} else if (getValueFromPropertyFile("browser_mode").equalsIgnoreCase("headless")) {
			System.setProperty("webdriver.chrome.driver", getValueFromPropertyFile("chromedriver_path"));
			ChromeOptions option = new ChromeOptions();
		    option.addArguments("--chrome://settings/clearBrowserData");
			option.addArguments("--window-size=1920,1080"); // this was added by Ramesh
			//option.addArguments("--window-size=2048,1536");  /// Added by Arun to decrase the screen resolution
			//below 2 line of code is to handle the floating screen resolution
			option.addArguments("force-device-scale-factor=0.75");
			option.addArguments("high-dpi-support=0.75");
			//additional arguments 3 lines
			option.addArguments("--disable-extensions");
		    option.addArguments("--proxy-server='direct://'");
		    option.addArguments("--proxy-bypass-list=*");
			option.addArguments("--start-maximized");
			option.addArguments("--headless");
			//additional one line argument
			option.addArguments("no-sandbox");
			driver = new ChromeDriver(option);

		} else if (getValueFromPropertyFile("browser").equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver", getValueFromPropertyFile("iedriver_path"));
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability("requireWindowFocus", true);
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			driver = new InternetExplorerDriver();
		}
		return driver;
	}

	// Getting property based on key
	public static String getValueFromPropertyFile(String key) {
		Properties prop = new Properties();
		FileInputStream input = null;
		try {
			input = new FileInputStream(new File("config/config.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			prop.load(input);
		} catch (IOException e) {
			System.out.println("Not able to load");
		}
		return prop.getProperty(key);
		// return null;
	}

	// Getting property based on key and directory
	public static String getValueFromPropertyFile(String key, String propDir) {
		Properties prop = new Properties();
		FileInputStream input = null;
		try {
			input = new FileInputStream(new File(propDir));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(input);
		} catch (IOException e) {
			System.out.println("Not able to load");
		}
		return prop.getProperty(key);
		// return null;
	}

	@SuppressWarnings("deprecation")
	public static void setValueInPropertyFile(String filePath, String name, String val) {
		Properties prop = new Properties();
		FileInputStream input = null;

		try {
			input = new FileInputStream(new File(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(input);
		} catch (IOException e) {
			System.out.println("Not able to load");
		}
		
		try {
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileOutputStream output;
		try {
			output = new FileOutputStream(new File(filePath));
			prop.setProperty(name, val);
			prop.save(output, "");
			try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getValueFromUpgradeProp(String key) {
		Properties prop = new Properties();
		FileInputStream input = null;
		try {
			input = new FileInputStream(new File("config/upgradeCases.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(input);
		} catch (IOException e) {
			System.out.println("Not able to load");
		}
		return prop.getProperty(key);
		// return null;
	}

	public static String getValueFromSoapAPIProp(String key) {
		Properties prop = new Properties();
		FileInputStream input = null;
		try {
			input = new FileInputStream(new File("config/soapUICases.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(input);
		} catch (IOException e) {
			System.out.println("Not able to load");
		}
		return prop.getProperty(key);
		// return null;
	}

	public static String getValueFromSoapRestxml(String key) {
		Properties prop = new Properties();
		FileInputStream input = null;
		try {
			input = new FileInputStream(new File("config/soapUICases.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(input);
		} catch (IOException e) {
			System.out.println("Not able to load");
		}
		return prop.getProperty(key);
		// return null;
	}

	public static final String AES = "AES";

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase();
	}

	private static byte[] hexStringToByteArray(String s) {
		byte[] b = new byte[s.length() / 2];
		for (int i = 0; i < b.length; i++) {
			int index = i * 2;
			int v = Integer.parseInt(s.substring(index, index + 2), 16);
			b[i] = (byte) v;
		}
		return b;
	}

	public static String decryptPassword(String Key, String encryptPassword) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// String OriginalPassword="";
		byte[] bytekey = hexStringToByteArray(Key);
		SecretKeySpec sks = new SecretKeySpec(bytekey, Utility.AES);
		Cipher cipher = Cipher.getInstance(Utility.AES);
		cipher.init(Cipher.DECRYPT_MODE, sks);
		byte[] decrypted = cipher.doFinal(hexStringToByteArray(encryptPassword));
		String OriginalPassword = new String(decrypted);
		return OriginalPassword;
	}

	// Getting random value
	public static int getRandomValue() {
		Random random = new Random();
		int randomNumber = random.nextInt(499) + 1;
		return randomNumber;
	}

	public static boolean isProcessRunning(String serviceName) throws Exception {
		Process p = Runtime.getRuntime().exec(TASKLIST);
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.contains(serviceName)) {
				return true;
			}
		}
		return false;
	}

	public static int getRandomfixdigitNbr() {
		Random random = new Random();
		int randomNumber = random.nextInt(1000) + 1000;
		return randomNumber;
	}

	public static String generatePortPhoneNbr() {
		/*long slice1 = Math.round(Math.random() * Math.floor(1000));
        long slice2 = Math.round(Math.random() * Math.floor(1000));
        long slice3 = Math.round(Math.random() * Math.floor(10000));*/

		String slice1 = RandomStringUtils.randomNumeric(3).toString();
		String slice2 = RandomStringUtils.randomNumeric(3).toString();
		String slice3 = RandomStringUtils.randomNumeric(4).toString();

		return "(" + slice1 + ") " + slice2 + "-" + slice3;
	}
	
	public static String generateRandomEmailId() {
		String emailID = RandomStringUtils.randomAlphabetic(15).toString();
		return emailID ;
	}

	public static void killTheProcess(String serviceName) throws Exception {

		Runtime.getRuntime().exec("TASKKILL /F /IM" + serviceName);
	}

	public static void killProcess(String appProcessName) throws Exception {
		while (Utility.isProcessRunning(appProcessName)) {
			if (Utility.isProcessRunning(appProcessName)) {
				killTheProcess(appProcessName);
				System.out.println("Process for Chromedriver is killed");
			}
		}
	}

	// Comparing integer values
	public static boolean compareIntegerVals(int actualIntegerVal, int expectedIntegerVal) {
		SoftAssert Soft_Assert = new SoftAssert();
		try {
			// If this assertion will fail, It will throw exception and catch
			// block will be executed.
			Assert.assertEquals(actualIntegerVal, expectedIntegerVal);
		} catch (Throwable t) {
			Soft_Assert.fail("Actual Value '" + actualIntegerVal + "' And Expected Value '" + expectedIntegerVal
					+ "' Do Not Match.");
			// If Integer values will not match, return false.
			return false;
		}
		return true;
	}

	public static String getOrderStatusString(WebDriver driver, String webTableID) {
		String requiredString = "";
		// variables for from column, to column, ordername column and xmlstring
		int lastRow = getWebTableRowCount(driver, webTableID);
		// log.debug("Last row count is "+lastRow);
		WebElement orderStatusNameValue = driver
				.findElement(By.xpath("//table[@id='" + webTableID + "']/tbody/tr[" + lastRow + "]/td[1]"));
		WebElement orderStatusValValue = driver
				.findElement(By.xpath("//table[@id='" + webTableID + "']/tbody/tr[" + lastRow + "]/td[3]"));
		if (orderStatusNameValue.getText().contains("Order Management")) {
			requiredString = orderStatusValValue.getText();
		}
		return requiredString;
	}

	public static String generateHardwareSerailNum(String serialNumVal) {
		String srlNo = "";
		String[] splitString = serialNumVal.split("_");
		String[] prefix = (splitString[0]).split("(?<=\\D)(?=\\d)");
		// String numericVal=getNumber(splitString[0]);
		int incrementVal = (Integer.valueOf(prefix[1])) + 1;
		srlNo = prefix[0] + Integer.toString(incrementVal) + "_" + splitString[1];
		return srlNo;
	}

	// Getting XML String from data table
	public static String getXMLString(WebDriver driver, String webTableID, String fromColumnValue, String toColumnValue,
			String orderNameValue, String xmlStringContain) {
		log.debug("Values are: TableID= " + webTableID + ",FROM = " + fromColumnValue + ", TO= " + toColumnValue
				+ ", Order Name= " + orderNameValue + ", Uniq String=  " + xmlStringContain);
		String requiredXMLString = "";
		// variables for from column, to column, ordername column and xmlstring
		List<WebElement> fromColumnValues = driver
				.findElements(By.xpath("//table[@id='" + webTableID + "\']/tbody/tr/td[2]"));
		List<WebElement> toColumnValues = driver
				.findElements(By.xpath("//table[@id='" + webTableID + "\']/tbody/tr/td[3]"));
		List<WebElement> orderNameValues = driver
				.findElements(By.xpath("//table[@id='" + webTableID + "\']/tbody/tr/td[4]"));
		List<WebElement> messageValues = driver
				.findElements(By.xpath("//table[@id='" + webTableID + "\']/tbody/tr/td[6]"));

		// contain check in the message
		for (int m = 0; m < fromColumnValues.size(); m++) {
			if (fromColumnValues.get(m).getText().contains(fromColumnValue)) {
				if (toColumnValues.get(m).getText().contains(toColumnValue)) {
					if (orderNameValues.get(m).getText().contains(orderNameValue)) {
						if (messageValues.get(m).getText().contains(xmlStringContain)) {
							requiredXMLString = messageValues.get(m).getText();
						}
					}
				}
			}
		}
		return requiredXMLString;
	}

	// Getting table row count from data table
	public static int getWebTableRowCount(WebDriver driver, String webTableID) {
		int rowCount = 0;
		// getting row values
		String webTableRowXpath = "//table[@id='" + webTableID + "']/tbody/tr";
		List<WebElement> rows = driver.findElements(By.xpath(webTableRowXpath));
		rowCount = rows.size();
		return rowCount;
	}

	public static String getWebTableColValue(WebDriver driver, String webTableID, String rowName) {
		boolean val = false;
		String webTableRowXpath = "//table[@id='" + webTableID + "']/tbody/tr";
		List<WebElement> rows = driver.findElements(By.xpath(webTableRowXpath));
		String colVal = "";
		for (int row = 0; row < rows.size(); row++) {
			List<WebElement> Cols = rows.get(row).findElements(By.tagName("td"));

			if (Cols.get(2).getText().equalsIgnoreCase(rowName)) {
				colVal = Cols.get(5).getText();
			}
		}
		// getting row values
		return colVal;
	}

	public static boolean isSkip(ExcelOperation xls, String testName) {
		int rows = xls.getRowCount(Constants.TESTCASES_SHEET);
		for (int rNum = 2; rNum <= rows; rNum++) {
			String tcid = xls.getCellData(Constants.TESTCASES_SHEET, Constants.TCID_COL, rNum);
			if (tcid.equals(testName)) {
				String runmode = xls.getCellData(Constants.TESTCASES_SHEET, Constants.RUNMODE_COL, rNum);
				if (runmode.equals("Y"))
					return false;
				else
					return true;
			}
		}
		return true;// default
	}

	public static String getDataForTest(ExcelOperation xls, String rowName, String colName) {
		int rows = xls.getRowCount(Constants.TEST_SHEET);
		String colVal = null;
		for (int rNum = 2; rNum <= rows; rNum++) {
			String tcid = xls.getCellData(Constants.TEST_SHEET, "TCID", rNum);
			if (tcid.equals(rowName)) {

				colVal = (xls.getCellData(sheetName, colName, rNum));
			}
		}
		return colVal;
	}

	// public static String
	public static void setValueInColumnFortcId(ExcelOperation xls, String sheetName, String colName, String tcid,
			String data) {
		int rowNum = getRowNumbytcId(xls, tcid);
		xls.setCellData(sheetName, colName, rowNum, data);
	}

	public static void setValueInColumnForTestName(ExcelOperation xls, String sheetName, String colName, String tcName,
			String data) {
		int rowNum = getRowNumbytcName(xls, sheetName, tcName);
		xls.setCellData(sheetName, colName, rowNum, data);
	}

	public static void setValueInColumnFortheRow(ExcelOperation xls, String sheetName, int rowNum, String colName,
			int data) {
		xls.setCellData(sheetName, colName, rowNum, Integer.toString(data));
	}

	public static void setValueToExcelSheet(ExcelOperation xls, String sheetName, String colName, int rowNum,
			String data) {
		xls.setCellData(sheetName, colName, rowNum, data);
	}

	public static int getRowNumbytcName(ExcelOperation xls, String sheetName, String tcName) {
		String SheetName = sheetName;
		// reads data for only testCaseName

		int testStartRowNum = 1;
		// getDataForTest(xls,TCID,"Run")
		while (!xls.getCellData(sheetName, 1, testStartRowNum).equals(tcName)) {
			testStartRowNum++;
		}
		return testStartRowNum;
	}

	public static int getRowNumbytcId(ExcelOperation xls, String tcid) {
		String SheetName = sheetName;
		// reads data for only testCaseName

		int testStartRowNum = 1;

		// getDataForTest(xls,TCID,"Run")
		while (!xls.getCellData(sheetName, 0, testStartRowNum).equals(tcid)) {
			testStartRowNum++;
		}
		return testStartRowNum;
	}

	public static Object[][] getData(ExcelOperation xls, String sheetName, String tcName) {
		// String sheetName="TestData";
		// reads data for only testCaseName
		log.debug("Sheetname is:" + sheetName);
		int testStartRowNum = 1;

		// getDataForTest(xls,TCID,"Run")
		while (!xls.getCellData(sheetName, 1, testStartRowNum).equals(tcName)) {
			testStartRowNum++;
		}

		int cols = 0;
		while (!xls.getCellData(sheetName, cols, 1).equals("")) {
			cols++;
		}

		Object[][] data = new Object[1][1];
		int dataRow = 0;
		Hashtable<String, String> table = null;
		for (int rNum = testStartRowNum; rNum <= testStartRowNum; rNum++) {
			table = new Hashtable<String, String>();
			for (int cNum = 0; cNum < cols; cNum++) {
				String key = xls.getCellData(sheetName, cNum, 1);
				String value = xls.getCellData(sheetName, cNum, testStartRowNum);
				table.put(key, value);
			}
			data[dataRow][0] = table;
			dataRow++;
		}
		return data;
	}

	public static String[][] matchArrayvaluesInaString(String[] values, String string) {
		ArrayList<String> present = new ArrayList<>();
		ArrayList<String> missing = new ArrayList<>();
		String[][] valueLists = new String[2][];
		for (int i = 0; i < values.length; i++) {
			if (string.contains(values[i])) {
				present.add(values[i]);
			} else {
				missing.add(values[i]);
			}
		}
		String[] valuesPresent = present.toArray(new String[present.size()]);
		String[] valuesMissing = missing.toArray(new String[missing.size()]);
		log.debug("Total Parameters Matching = " + valuesPresent.length);
		log.debug("Total Parameters Missing = " + valuesMissing.length);
		valueLists[0] = valuesPresent;
		valueLists[1] = valuesMissing;
		/*
		 * if(valuesPresent.length==values.length){ return valuesPresent ; }
		 * else{ return valuesMissing ; }
		 */
		return valueLists;
	}

	public static boolean checkStringasNumeric(String value) {
		boolean numeric = true;
		try {
			Double num = Double.parseDouble(value);
		} catch (NumberFormatException e) {
			numeric = false;
		}
		return numeric;
	}

	public void printArrayValues(String[] values) {
		for (int i = 0; i < values.length; i++) {
			if (Utility.checkStringasNumeric(values[i])) {
				values[i] = String.valueOf(Math.round(Float.valueOf(values[i])));
				;
			}
			System.out.println(values[i]);
		}
	}

	public static String navigatteToPickRequestXMLFile(String xmlFileName, TagModel... tagNames) throws Exception {

		File file = new File(System.getProperty("user.dir") + "\\testdata\\Soap_RestAPI\\" + xmlFileName);
		InputStream is = new FileInputStream(file);

		XMLReader xr = new XMLFilterImpl(XMLReaderFactory.createXMLReader()) {
			private String tagName = "";

			@Override
			public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
				tagName = qName;
				super.startElement(uri, localName, qName, atts);
			}

			public void endElement(String uri, String localName, String qName) throws SAXException {
				tagName = "";
				super.endElement(uri, localName, qName);
			}

			@Override
			public void characters(char[] ch, int start, int length) throws SAXException {

				List<TagModel> tags = Arrays.asList(tagNames);
				for (TagModel tag : tags) {
					String name = tag.tagName;
					String data = tag.data;

					if (tagName.equals(name)) {
						ch = data.toCharArray();
						start = 0;
						length = ch.length;
					}
				}
				super.characters(ch, start, length);
			}
		};

		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		Source src = new SAXSource(xr, new InputSource(is));
		TransformerFactory.newInstance().newTransformer().transform(src, result);

		String fileAsString = writer.toString();

		return fileAsString;
	}

	public static class TagModel{
		public TagModel(String name,String data){
			this.tagName = name;
			this.data = data;
		}
		private String tagName;
		private String data;
	}
	
	public static boolean validateTestRun(Hashtable<String, String> data) {
		boolean result = true;
		if (data.get("Run").equals("No")) {
			result = false;
		}
		return result;
	}
}
