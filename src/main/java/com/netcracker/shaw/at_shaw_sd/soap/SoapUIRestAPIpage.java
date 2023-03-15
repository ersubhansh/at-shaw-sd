package com.netcracker.shaw.at_shaw_sd.soap;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.netcracker.shaw.at_shaw_sd.pageobject.BasePage;
import com.netcracker.shaw.at_shaw_sd.util.Utility;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

public class SoapUIRestAPIpage extends BasePage {

	private static final TimeUnit SECONDS = null;

	Logger log = Logger.getLogger(SoapUIRestAPIpage.class);

	public SoapUIRestAPIpage(ExtentTest test) {
		super(test);
	}

	public SoapUIRestAPIpage(ExtentTest test, WebDriver driver) {
		super(test, driver);
	}

	public static ExtentTest test;

	public ExtentTest getTest() {
		return test;
	}

	public void setTest(ExtentTest test) {
		this.test = test;
	}

	public void setDriver(WebDriver driver1) {
		driver = driver1;
	}

	private String escapeFormatXml(String s) {
		String formattedString = s;
		try {
			final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			builderFactory.setNamespaceAware(true);
			final InputStream stream = new ByteArrayInputStream(s.getBytes());
			final Document doc = builderFactory.newDocumentBuilder().parse(stream);

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "html");
			Source source = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			Result output = new StreamResult(writer);
			transformer.transform(source, output);

			formattedString = writer.toString();
		} catch (TransformerException | SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		formattedString = formattedString.replaceAll("(<\\/\\s*\\S*>)", "$1<br/>");
		formattedString = formattedString.replaceAll("&", "&amp;").replaceAll(">", "&gt;").replaceAll("<", "&lt;")
				.replaceAll("\"", "&quot;").replaceAll("'", "&apos;");
		formattedString = formattedString.replaceAll("&gt;([\\d*\\-*\\w*\\s*:*]*)&lt;",
				"&gt;<span style='font-weight:bold;'>$1</span>&lt;");

		return "<br/>" + formattedString.replaceAll("&lt;br\\/&gt;", "<br/>");
	}

	public static RequestSpecification addHeaders(String contentType) {
		RequestSpecification httpRequest = RestAssured.given();
		HashMap<String, String> headers = new HashMap<>();
		headers.put("X_SHAW_ORIGINATING_USER_ID", "SJRB\\1");
		headers.put("X_SHAW_ORIGINATING_IP_ADDRESS", "1");
		headers.put("X_SHAW_TRANSACTION_ID", "7" + Utility.getRandomfixdigitNbr());
		headers.put("X_SHAW_ORIGINATING_HOST_NAME", "1");
		headers.put("X_SHAW_ORIGINAL_MODULE_ID", "1");
		StringBuilder resultView = new StringBuilder();
		for (String key : headers.keySet()) {
			resultView.append(key + "=" + headers.get(key));
			resultView.append("\r");
		}
		headers.forEach((key, value) -> test.log(LogStatus.INFO, "Headers: " + (key + " : " + value)));
		if (contentType.equals("vnd.shaw.order")) {
			headers.put("Content-Type", "application/vnd.shaw.order+xml");
			httpRequest.headers(headers);
			test.log(LogStatus.INFO, "Content-Type: ", "application/vnd.shaw.order+xml");
		}
		if (contentType.equals("application/xml")) {
			headers.put("Content-Type", "application/xml");
			httpRequest.headers(headers);
			test.log(LogStatus.INFO, "Content-Type: ", "application/xml");
		}
		if (contentType.equals("application/soap+xml")) {
			headers.put("Content-Type", "application/soap+xml");
			httpRequest.headers(headers);
			test.log(LogStatus.INFO, "Content-Type: ", "application/soap+xml");
		}
		return httpRequest;
	}

	public void executeCPEendPointGetSrlNumber() {
		log.debug("Entering executeCPEendPointGetSrlNumber");
		try {
			String ServerURL = Utility.getValueFromPropertyFile("oestub_url");
			String convergedSrlNbr = Utility.getValueFromSoapAPIProp("convergedSrlNbrTC144");
			test.log(LogStatus.INFO, "End Point URL : ", "<a href=" + ServerURL + " > " + ServerURL + " </a>");
			RestAssured.baseURI = ServerURL;
			RequestSpecification httpRequest = addHeaders("vnd.shaw.order");

			Response response = httpRequest.request(Method.GET, "/cpe/Equipments/SerialNumber/" + convergedSrlNbr);
			test.log(LogStatus.INFO, "Method Type : ", "GET");
			String EndPointResourceUrl = ("/cpe/Equipments/SerialNumber/" + convergedSrlNbr);
			test.log(LogStatus.PASS, "End Point Resource URL : ",
					"<a href=" + EndPointResourceUrl + " > " + EndPointResourceUrl + " </a>");
			test.log(LogStatus.INFO, "Request Body : ", "Empty");

			String responseBody = response.getBody().asString();
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Response: " + escapeFormatXml(responseBody));

			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
			test.log(LogStatus.PASS, "Status Code Is : " + statusCode);

			String statusLine = response.getStatusLine();
			Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
			test.log(LogStatus.PASS, "Status line is : " + statusLine);

			log.debug("Leaving executeCPEendPointGetSrlNumber");
		} catch (Exception e) {
			log.error("Error in executeCPEendPointGetSrlNumber:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(e.getMessage(), false);
		}
	}

	public void getAccountIDEndPointX1Gateway_TC141() {
		log.debug("Entering getAccountIDEndPointX1Gateway");
		try {
			String accntNum = Utility.getValueFromSoapAPIProp("acctNumTC141");
			String ServerURL = Utility.getValueFromPropertyFile("basepage_url");
			test.log(LogStatus.INFO, "End Point URL : ", "<a href=" + ServerURL + " > " + ServerURL + " </a>");
			RestAssured.baseURI = ServerURL;
			RequestSpecification httpRequest = addHeaders("vnd.shaw.order");

			Response response = httpRequest.request(Method.GET, "/services/omservice/account/1" + accntNum);
			test.log(LogStatus.INFO, "Method Type : ", "GET");
			String EndPointResourceUrl = ("/services/omservice/account/1" + accntNum);
			test.log(LogStatus.PASS, "End Point Resource URL : ",
					"<a href=" + EndPointResourceUrl + " > " + EndPointResourceUrl + " </a>");
			test.log(LogStatus.INFO, "Request Body : ", "Empty");

			String responseBody = response.getBody().asString();
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Response: " + escapeFormatXml(responseBody));

			String contentType = response.header("Content-Type");
			Assert.assertEquals(contentType, "application/vnd.shaw.account+xml");
			test.log(LogStatus.INFO, "Accept content Type is : " + contentType);

			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
			test.log(LogStatus.PASS, "Status Code Is : " + statusCode);

			String statusLine = response.getStatusLine();
			Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
			test.log(LogStatus.PASS, "Status line is : " + statusLine);

			log.debug("Leaving getAccountIDEndPointX1Gateway");
		} catch (Exception e) {
			log.error("Error in getAccountIDEndPointX1Gateway:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(e.getMessage(), false);
		}
	}

	public void navigateToHitCollectionSuspension(String requestXml, String accntNum) {
		log.debug("Entering navigateToHitCollectionSuspension");
		try {
			String fileAsString = "";
			String ServerURL = Utility.getValueFromPropertyFile("basepage_url");
			test.log(LogStatus.INFO, "End Point URL : ", "<a href=" + ServerURL + " > " + ServerURL + " </a>");
			RestAssured.baseURI = ServerURL;
			RequestSpecification httpRequest = addHeaders("vnd.shaw.order");

			if (requestXml.equals("TC40")) {
				fileAsString = Utility.navigatteToPickRequestXMLFile("TC40_CollectionSuspensionAfterUG.xml",
						new Utility.TagModel("accountNumber", accntNum));
			}
			if (requestXml.equals("TC41")) {
				fileAsString = Utility.navigatteToPickRequestXMLFile("TC41_CollectionSuspensionBeforeUG.xml",
						new Utility.TagModel("accountNumber", accntNum));
			}
			if (requestXml.equals("TC142")) {
				fileAsString = Utility.navigatteToPickRequestXMLFile("TC142_CollectionSuspension.xml",
						new Utility.TagModel("accountNumber", accntNum));
			}
			if (requestXml.equals("TC150")) {
				fileAsString = Utility.navigatteToPickRequestXMLFile("TC150_ResumptionCollectionSuspend.xml",
						new Utility.TagModel("accountNumber", accntNum));
			}
			if (requestXml.equals("TC151")) {
				fileAsString = Utility.navigatteToPickRequestXMLFile("TC151_ImmediateSuspend.xml",
						new Utility.TagModel("accountNumber", accntNum));
			}
			if (requestXml.equals("TC157")) {
				fileAsString = Utility.navigatteToPickRequestXMLFile("TC157_ImmediateSuspendTriplePlay.xml",
						new Utility.TagModel("accountNumber", accntNum));
			}
			test.log(LogStatus.INFO, "Method Type : ", "PUT");
			String EndPointResourceUrl = ("/services/omservice/suspendResumeImmediate/suspend");
			test.log(LogStatus.PASS, "End Point URL : ",
					"<a href=" + EndPointResourceUrl + " > " + EndPointResourceUrl + " </a>");

			httpRequest.body(fileAsString);
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Request: " + escapeFormatXml(fileAsString));

			Response response = httpRequest.request(Method.PUT, "/services/omservice/suspendResumeImmediate/suspend");

			String responseBody = response.getBody().asString();
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Response: " + escapeFormatXml(responseBody));

			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
			test.log(LogStatus.PASS, "Status Code is : " + statusCode);

			String statusLine = response.getStatusLine();
			Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
			test.log(LogStatus.PASS, "Status line is : " + statusLine);

			log.debug("Leaving navigateToHitCollectionSuspension");
		} catch (Exception e) {
			log.error("Error in navigateToHitCollectionSuspension:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(e.getMessage(), false);
		}
	}

	public void navigateToHitCollectionResume(String requestxml, String accntNum) {
		log.debug("Entering navigateToHitCollectionResume");
		try {
			String fileAsString = "";
			String ServerURL = Utility.getValueFromPropertyFile("basepage_url");
			test.log(LogStatus.INFO, "End Point URL : ", "<a href=" + ServerURL + " > " + ServerURL + " </a>");
			RestAssured.baseURI = ServerURL;
			RequestSpecification httpRequest = addHeaders("vnd.shaw.order");

			if (requestxml.equals("TC41")) {
				fileAsString = Utility.navigatteToPickRequestXMLFile("TC41_CollectionResumptionAfterUG.xml",
						new Utility.TagModel("accountNumber", accntNum));
			}
			if (requestxml.equals("TC150")) {
				fileAsString = Utility.navigatteToPickRequestXMLFile("TC150_ResumptionCollectionResume.xml",
						new Utility.TagModel("accountNumber", accntNum));
			}
			if (requestxml.equals("TC157")) {
				fileAsString = Utility.navigatteToPickRequestXMLFile("TC157_ImmediateResumeTriplePlay.xml",
						new Utility.TagModel("accountNumber", accntNum));
			}
			test.log(LogStatus.INFO, "Method Type : ", "PUT");
			String EndPointResourceUrl = ("/services/omservice/suspendResumeImmediate/resume");
			test.log(LogStatus.PASS, "End Point Resource URL : ",
					"<a href=" + EndPointResourceUrl + " > " + EndPointResourceUrl + " </a>");

			httpRequest.body(fileAsString);
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Request: " + escapeFormatXml(fileAsString));

			Response response = httpRequest.request(Method.PUT, "/services/omservice/suspendResumeImmediate/resume");

			String responseBody = response.getBody().asString();
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Response: " + escapeFormatXml(responseBody));

			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
			test.log(LogStatus.PASS, "Status Code Is : " + statusCode);

			String statusLine = response.getStatusLine();
			Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
			test.log(LogStatus.PASS, "Status line is : " + statusLine);

			log.debug("Leaving navigateToHitCollectionResume");
		} catch (Exception e) {
			log.error("Error in navigateToHitCollectionResume:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(e.getMessage(), false);
		}
	}

	public void navigateToHitWiringStatus(String requestxml, String portPhoneNbr, String orderNum) {
		log.debug("Entering navigateToHitWiringStatus");
		try {
			String fileAsString = "";
			String ServerURL = Utility.getValueFromPropertyFile("basepage_url");
			test.log(LogStatus.INFO, "End Point URL : ", "<a href=" + ServerURL + " > " + ServerURL + " </a>");
			RestAssured.baseURI = ServerURL;
			RequestSpecification httpRequest = addHeaders("vnd.shaw.order");

			if (requestxml.equals("TC137")) {
				fileAsString = Utility.navigatteToPickRequestXMLFile("TC137_WiringStatus.xml",
						new Utility.TagModel("phoneNumber", portPhoneNbr));
			}
			if (requestxml.equals("TC138")) {
				fileAsString = Utility.navigatteToPickRequestXMLFile("TC138_WiringStatus.xml",
						new Utility.TagModel("phoneNumber", portPhoneNbr));
			}
			if (requestxml.equals("TC153")) {
				fileAsString = Utility.navigatteToPickRequestXMLFile("TC153_WiringStatus.xml",
						new Utility.TagModel("phoneNumber", portPhoneNbr));
			}
			if (requestxml.equals("TC155")) {
				fileAsString = Utility.navigatteToPickRequestXMLFile("TC155_DualJackedWiringStatus.xml",
						new Utility.TagModel("phoneNumber", portPhoneNbr));
			}
			test.log(LogStatus.INFO, "Method Type : ", "PUT");
			String EndPointResourceUrl = ("/services/omservice/wiringstatus/" + orderNum);
			test.log(LogStatus.PASS, "End Point Resource URL : ","<a href=" + EndPointResourceUrl + " > " + EndPointResourceUrl + " </a>");

			httpRequest.body(fileAsString);
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Request: " + escapeFormatXml(fileAsString));

			Response response = httpRequest.request(Method.PUT, "/services/omservice/wiringstatus/" + orderNum);

			String responseBody = response.getBody().asString();
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Response: " + escapeFormatXml(responseBody));

			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
			test.log(LogStatus.PASS, "Status Code Is : " + statusCode);

			String statusLine = response.getStatusLine();
			Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
			test.log(LogStatus.PASS, "Status line is : " + statusLine);

			log.debug("Leaving navigateToHitWiringStatus");
		} catch (Exception e) {
			log.error("Error in navigateToHitWiringStatus:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(e.getMessage(), false);
		}
	}

	public void navigateToHitHardwareReturnedEndpoint(String requestxml, String serialNbr) {
		log.debug("Entering navigateToHitHardwareReturnedEndpoint");
		try {
			String fileAsString = "";
			String ServerURL = Utility.getValueFromPropertyFile("basepage_url");
			test.log(LogStatus.INFO, "End Point URL : ", "<a href=" + ServerURL + " > " + ServerURL + " </a>");
			RestAssured.baseURI = ServerURL;
			RequestSpecification httpRequest = addHeaders("vnd.shaw.order");

			if (requestxml.equals("TC131")) {
				fileAsString = Utility.navigatteToPickRequestXMLFile("TC131_HardwareReturnEndPoint.xml",
						new Utility.TagModel("serialNumber", serialNbr));
			}
			
			if (requestxml.equals("TC133")) {
				fileAsString = Utility.navigatteToPickRequestXMLFile("TC133_ERPDisconnect_HardwareReturn.xml",
						new Utility.TagModel("serialNumber", serialNbr));
			}
			if (requestxml.equals("TC134")) {
				fileAsString = Utility.navigatteToPickRequestXMLFile("TC134_HardwareReturn_TwoActiveAcs.xml",
						new Utility.TagModel("serialNumber", serialNbr));
			}
			test.log(LogStatus.INFO, "Method Type : ", "PUT");
			String EndPointResourceUrl = ("/services/omservice/hardwareReturned");
			test.log(LogStatus.PASS, "End Point Resource URL : ",
					"<a href=" + EndPointResourceUrl + " > " + EndPointResourceUrl + " </a>");

			httpRequest.body(fileAsString);
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Request: " + escapeFormatXml(fileAsString));

			Response response = httpRequest.request(Method.PUT, "/services/omservice/hardwareReturned");

			String responseBody = response.getBody().asString();
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Response: " + escapeFormatXml(responseBody));

			String contentType = response.header("Content-Type");
			Assert.assertEquals(contentType, "application/vnd.shaw.order+xml");
			test.log(LogStatus.INFO, "Accept content Type is : " + contentType);

			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
			test.log(LogStatus.PASS, "Status Code Is : " + statusCode);

			String statusLine = response.getStatusLine();
			Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
			test.log(LogStatus.PASS, "Status line is : " + statusLine);

			log.debug("Leaving navigateToHitHardwareReturnedEndpoint");
		} catch (Exception e) {
			log.error("Error in navigateToHitHardwareReturnedEndpoint:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(e.getMessage(), false);
		}
	}

	public void navigateToHitGetProdDetailsByCustAc(String accountId) {
		log.debug("Entering navigateToHitGetProdDetailsByCustAc");
		try {
			String ServerURL = Utility.getValueFromPropertyFile("basepage_url");
			test.log(LogStatus.INFO, "End Point URL : ", "<a href=" + ServerURL + " > " + ServerURL + " </a>");
			RestAssured.baseURI = ServerURL;

			RequestSpecification httpRequest = addHeaders("vnd.shaw.order");
			String fileAsString = Utility.navigatteToPickRequestXMLFile("TC143_GetProdDetailsByCustAc.xml",
					new Utility.TagModel("cial:AccountId", "1" + accountId));

			String EndPointResourceUrl = ("/sslservices/occ/productsdetails");
			test.log(LogStatus.PASS, "End Point Resource URL : ",
					"<a href=" + EndPointResourceUrl + " > " + EndPointResourceUrl + " </a>");

			httpRequest.body(fileAsString);
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Request: " + escapeFormatXml(fileAsString));

			Response response = httpRequest.request(Method.PUT, "/sslservices/occ/productsdetails");

			String responseBody = response.getBody().asString();
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Response: " + escapeFormatXml(responseBody));

			String contentType = response.header("Content-Type");
			Assert.assertEquals(contentType, "application/vnd.shaw.order+xml");
			test.log(LogStatus.INFO, "Accept content Type is : " + contentType);

			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
			test.log(LogStatus.PASS, "Status Code Is : " + statusCode);

			String statusLine = response.getStatusLine();
			Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
			test.log(LogStatus.PASS, "Status line is : " + statusLine);

			log.debug("Leaving navigateToHitGetProdDetailsByCustAc");
		} catch (Exception e) {
			log.error("Error in navigateToHitGetProdDetailsByCustAc:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(e.getMessage(), false);
		}
	}

	public void getservicesDisconnecteddueToNonPay_TC152(String accntNum) {
		log.debug("Entering getservicesDisconnecteddueToNonPay_TC152");
		try {
			String ServerURL = Utility.getValueFromPropertyFile("basepage_url");
			test.log(LogStatus.INFO, "End Point URL : ", "<a href=" + ServerURL + " > " + ServerURL + " </a>");
			RestAssured.baseURI = ServerURL;

			RequestSpecification httpRequest = addHeaders("application/xml");

			String fileAsString = Utility.navigatteToPickRequestXMLFile("TC152_ServicesDisconnectNonPay.xml", new Utility.TagModel("accountNumber", accntNum));

			httpRequest.body(fileAsString);
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Request: " + escapeFormatXml(fileAsString));

			Response response = httpRequest.request(Method.POST, "/sslservices/oeservices/quote/submit");
			test.log(LogStatus.INFO, "Method Type : ", "POST");
			String EndPointResourceUrl = ("/sslservices/oeservices/quote/submit");
			test.log(LogStatus.PASS, "End Point Resource URL : ","<a href=" + EndPointResourceUrl + " > " + EndPointResourceUrl + " </a>");

			String responseBody = response.getBody().asString();
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Response: " + escapeFormatXml(responseBody));

			String contentType = response.header("Content-Type");
			Assert.assertEquals(contentType, "application/xml");
			test.log(LogStatus.INFO, "Accept content Type is : " + contentType);

			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
			test.log(LogStatus.PASS, "Status Code Is : " + statusCode);

			String statusLine = response.getStatusLine();
			Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
			test.log(LogStatus.PASS, "Status line is : " + statusLine);

			log.debug("Leaving getservicesDisconnecteddueToNonPay_TC152");
		} catch (Exception e) {
			log.error("Error in getservicesDisconnecteddueToNonPay_TC152:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(e.getMessage(), false);
		}
	}

	public void getAccountIDEndPointDetails_TC135() {
		log.debug("Entering getAccountIDEndPointDetails_TC135");
		try {
			String ServerURL = Utility.getValueFromPropertyFile("basepage_url");
			String accntNum = Utility.getValueFromSoapAPIProp("acctNumTC135");
			String orderNum = Utility.getValueFromSoapAPIProp("orderNumTC135");
			test.log(LogStatus.INFO, "End Point URL : ", "<a href=" + ServerURL + " > " + ServerURL + " </a>");
			RestAssured.baseURI = ServerURL;
			RequestSpecification httpRequest = RestAssured.given();

			HashMap<String, String> headers = new HashMap<>();
			headers.put("X_SHAW_ORIGINATING_USER_ID", "SJRB\1");
			headers.put("X_SHAW_ORIGINATING_IP_ADDRESS", "1");
			headers.put("X_SHAW_TRANSACTION_ID", orderNum);
			headers.put("X_SHAW_ORIGINATING_HOST_NAME", "1");
			headers.put("X_SHAW_ORIGINAL_MODULE_ID", "1");
			StringBuilder resultView = new StringBuilder();
			for (String key : headers.keySet()) {
				resultView.append(key + "=" + headers.get(key));
				resultView.append("\r");
			}
			headers.forEach((key, value) -> test.log(LogStatus.INFO, "The Headers are : " + (key + " : " + value)));
			httpRequest.headers(headers);

			Response response = httpRequest.request(Method.GET, "/services/omservice/account/1" + accntNum);
			test.log(LogStatus.INFO, "Method Type : ", "GET");
			String EndPointResourceUrl = ("/services/omservice/account/1" + accntNum);
			test.log(LogStatus.PASS, "End Point Resource URL : ",
					"<a href=" + EndPointResourceUrl + " > " + EndPointResourceUrl + " </a>");
			test.log(LogStatus.INFO, "Request Body : ", "Empty");

			String responseBody = response.getBody().asString();
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Response: " + escapeFormatXml(responseBody));

			String contentType = response.header("Content-Type");
			Assert.assertEquals(contentType, "application/vnd.shaw.account+xml");
			test.log(LogStatus.INFO, "Accept content Type is : " + contentType);

			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
			test.log(LogStatus.PASS, "Status Code Is : " + statusCode);

			String statusLine = response.getStatusLine();
			Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
			test.log(LogStatus.PASS, "Status line is : " + statusLine);

			log.debug("Leaving getAccountIDEndPointDetails");
		} catch (Exception e) {
			log.error("Error in getAccountIDEndPointDetails:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(e.getMessage(), false);
		}
	}

	public void getOrderIdEndPointDetails() {
		log.debug("Entering getOrderIdEndPointDetails");
		try {
			String orderNum = Utility.getValueFromSoapAPIProp("orderNumTC136");
			String ServerURL = Utility.getValueFromPropertyFile("basepage_url");
			test.log(LogStatus.INFO, "End Point URL : ", "<a href=" + ServerURL + " > " + ServerURL + " </a>");
			RestAssured.baseURI = ServerURL;
			RequestSpecification httpRequest = RestAssured.given();

			HashMap<String, String> headers = new HashMap<>();
			headers.put("X_SHAW_ORIGINATING_USER_ID", "SJRB\\1");
			headers.put("X_SHAW_ORIGINATING_IP_ADDRESS", "1");
			headers.put("X_SHAW_TRANSACTION_ID", orderNum);
			headers.put("X_SHAW_ORIGINATING_HOST_NAME", "1");
			headers.put("X_SHAW_ORIGINAL_MODULE_ID", "1");

			StringBuilder resultView = new StringBuilder();
			for (String key : headers.keySet()) {
				resultView.append(key + "=" + headers.get(key));
				resultView.append("\r");
			}
			headers.forEach((key, value) -> test.log(LogStatus.INFO, "The Headers are : " + (key + " : " + value)));
			httpRequest.headers(headers);

			Response response = httpRequest.request(Method.GET, "/services/omservice/order/" + orderNum);
			test.log(LogStatus.INFO, "Method Type : ", "GET");
			String EndPointResourceUrl = ("/services/omservice/order/" + orderNum);
			test.log(LogStatus.PASS, "End Point Resource URL : ",
					"<a href=" + EndPointResourceUrl + " > " + EndPointResourceUrl + " </a>");
			test.log(LogStatus.INFO, "Request Body : ", "Empty");

			String responseBody = response.getBody().asString();
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Response: " + escapeFormatXml(responseBody));

			String contentType = response.header("Content-Type");
			Assert.assertEquals(contentType, "application/vnd.shaw.order+xml");
			test.log(LogStatus.INFO, "Accept content Type is : " + contentType);

			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
			test.log(LogStatus.PASS, "Status Code Is : " + statusCode);

			String statusLine = response.getStatusLine();
			Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
			test.log(LogStatus.PASS, "Status line is : " + statusLine);

			log.debug("Leaving getOrderIdEndPointDetails");
		} catch (Exception e) {
			log.error("Error in getOrderIdEndPointDetails:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(e.getMessage(), false);
		}
	}
	
	/*
	 * public void triggerSoapRequests(String requestXml, String accntNum) {
	 * log.debug("Entering triggerSoapRequests"); try { String ServerURL =
	 * Utility.getValueFromPropertyFile("basepage_url"); test.log(LogStatus.INFO,
	 * "End Point URL : ", "<a href=" + ServerURL + " > " + ServerURL + " </a>");
	 * RestAssured.baseURI = ServerURL; if (requestXml.equals("TC158")) { String
	 * fileAsString =
	 * Utility.navigatteToPickRequestXMLFile("TC158_GetQuoteDetailsTriplePlay.xml",
	 * new Utility.TagModel("AccountId", accntNum)); File soapRequestFile = new
	 * File(fileAsString);
	 * 
	 * CloseableHttpClient client = HttpClients.createDefault(); HttpPost request =
	 * new HttpPost(ServerURL + "/sslservices/myaccount/quotedetails");
	 * request.addHeader("Content-Type", "text/xml"); request.setEntity(new
	 * InputStreamEntity(new FileInputStream(
	 * "C:\\Users\\raka0617\\workspace\\at-shaw-sd\\testdata\\Soap_RestAPI\\TC158_GetQuoteDetailsTriplePlay.xml"
	 * )));
	 * 
	 * CloseableHttpResponse response = client.execute(request); int statusCode =
	 * response.getStatusLine().getStatusCode();
	 * 
	 * Assert.assertEquals(200, statusCode); test.log(LogStatus.PASS,
	 * "Status code is : " + statusCode);
	 * 
	 * String responseString = EntityUtils.toString(response.getEntity()); XmlPath
	 * xmlpath=new XmlPath(responseString); test.log(LogStatus.INFO,
	 * "<span style='font-weight:bold;'> Response: " +
	 * escapeFormatXml(responseString));
	 * 
	 * //String rate=xmlpath.getString("GetQuoteDetailsByCustomerAccountResponse");
	 * } log.debug("Leaving triggerSoapRequests"); } catch (Exception e) {
	 * log.error("Error in triggerSoapRequests:" + e.getMessage());
	 * test.log(LogStatus.ERROR, e); test.log(LogStatus.FAIL, "Snapshot Below: " +
	 * test.addScreenCapture(addScreenshot())); Assert.assertTrue(e.getMessage(),
	 * false); } }
	 */

	public void navigateToHitTheWiringStatus_TC137(String PhoneNbr) {
		log.debug("Entering navigateToHitTheWiringStatus_TC137");
		try {
			String orderNum = Utility.getValueFromSoapAPIProp("orderNumTC137");
			String ServerURL = Utility.getValueFromPropertyFile("basepage_url");
			test.log(LogStatus.INFO, "End Point URL : ", "<a href=" + ServerURL + " > " + ServerURL + " </a>");
			RestAssured.baseURI = ServerURL;

			RequestSpecification httpRequest = addHeaders("vnd.shaw.order");
			String fileAsString = Utility.navigatteToPickRequestXMLFile("TC137_WiringStatus.xml",
					new Utility.TagModel("phoneNumber", PhoneNbr));

			test.log(LogStatus.INFO, "Method Type : ", "PUT");
			String EndPointResourceUrl = ("/services/omservice/wiringstatus/" + orderNum);
			test.log(LogStatus.PASS, "End Point Resource URL : ",
					"<a href=" + EndPointResourceUrl + " > " + EndPointResourceUrl + " </a>");

			httpRequest.body(fileAsString);
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Request: " + escapeFormatXml(fileAsString));

			Response response = httpRequest.request(Method.PUT, "/services/omservice/wiringstatus/" + orderNum);

			String responseBody = response.getBody().asString();
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Response: " + escapeFormatXml(responseBody));

			String contentType = response.header("Content-Type");
			Assert.assertEquals(contentType, "application/vnd.shaw.order+xml");
			test.log(LogStatus.INFO, "Accept content Type is : " + contentType);

			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
			test.log(LogStatus.PASS, "Status Code Is : " + statusCode);

			String statusLine = response.getStatusLine();
			Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
			test.log(LogStatus.PASS, "Status line is : " + statusLine);

			log.debug("Leaving navigateToHitTheWiringStatus_TC137");
		} catch (Exception e) {
			log.error("Error in navigateToHitTheWiringStatus_TC137:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(e.getMessage(), false);
		}
	}

	public void navigateToHitTheWiringStatus2() {
		log.debug("Entering navigateToHitTheWiringStatus2");
		try {
			String orderNum = Utility.getValueFromSoapAPIProp("orderNumTC138");
			String ServerURL = Utility.getValueFromPropertyFile("basepage_url");
			test.log(LogStatus.INFO, "End Point URL : ", "<a href=" + ServerURL + " > " + ServerURL + " </a>");
			RestAssured.baseURI = ServerURL;
			RequestSpecification httpRequest = RestAssured.given();

			HashMap<String, String> headers = new HashMap<>();
			headers.put("X_SHAW_ORIGINATING_USER_ID", "SJRB\\1");
			headers.put("X_SHAW_ORIGINATING_IP_ADDRESS", "1");
			headers.put("X_SHAW_TRANSACTION_ID", "02" + Utility.getRandomValue());
			headers.put("X_SHAW_ORIGINATING_HOST_NAME", "1");
			headers.put("X_SHAW_ORIGINAL_MODULE_ID", "1");

			StringBuilder resultView = new StringBuilder();
			for (String key : headers.keySet()) {
				resultView.append(key + "=" + headers.get(key));
				resultView.append("\r");
			}
			headers.forEach((key, value) -> test.log(LogStatus.INFO, "The Headers are : " + (key + " : " + value)));
			headers.put("Content-Type", "application/vnd.shaw.order+xml");
			httpRequest.headers(headers);
			test.log(LogStatus.INFO, "Content-Type: ", "application/vnd.shaw.order+xml");

			File file = new File(System.getProperty("user.dir") + "\\testdata\\Soap_RestAPI\\TC138_WiringStatus.xml");
			InputStream is = new FileInputStream(file);
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			String line = buf.readLine();
			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line).append("\n");
				line = buf.readLine();
			}
			String fileAsString = sb.toString();
			buf.close();
			is.close();

			test.log(LogStatus.INFO, "Method Type : ", "PUT");
			String EndPointResourceUrl = ("/services/omservice/wiringstatus" + orderNum);
			test.log(LogStatus.PASS, "End Point Resource URL : ",
					"<a href=" + EndPointResourceUrl + " > " + EndPointResourceUrl + " </a>");

			httpRequest.body(fileAsString);
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Request: " + escapeFormatXml(fileAsString));

			Response response = httpRequest.request(Method.PUT, "/services/omservice/wiringstatus/" + orderNum);

			String responseBody = response.getBody().asString();
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Response: " + escapeFormatXml(responseBody));

			String contentType = response.header("Content-Type");
			Assert.assertEquals(contentType, "application/vnd.shaw.order+xml");
			test.log(LogStatus.INFO, "Accept content Type is : " + contentType);

			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
			test.log(LogStatus.PASS, "Status Code Is : " + statusCode);

			String statusLine = response.getStatusLine();
			Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
			test.log(LogStatus.PASS, "Status line is : " + statusLine);

			log.debug("Leaving navigateToHitTheWiringStatus2");
		} catch (Exception e) {
			log.error("Error in navigateToHitTheWiringStatus2:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(e.getMessage(), false);
		}
	}

	public void navigateToHitQuoteSubmissionDeltaAPI(String accntNum) {
		log.debug("Entering navigateToHitQuoteSubmissionDeltaAPI");
		try {
			String fileAsString = "";
			String ServerURL = Utility.getValueFromPropertyFile("basepage_url");
			test.log(LogStatus.INFO, "End Point URL : ", "<a href=" + ServerURL + " > " + ServerURL + " </a>");
			RestAssured.baseURI = ServerURL;
			RequestSpecification httpRequest = RestAssured.given();

			HashMap<String, String> headers = new HashMap<>();
			headers.put("X_SHAW_ORIGINATING_USER_ID", "SJRB\\1");
			headers.put("X_SHAW_ORIGINATING_IP_ADDRESS", "1");
			headers.put("X_SHAW_TRANSACTION_ID", "2" + Utility.getRandomfixdigitNbr());
			headers.put("X_SHAW_ORIGINATING_HOST_NAME", "1");
			headers.put("X_SHAW_ORIGINAL_MODULE_ID", "1");

			StringBuilder resultView = new StringBuilder();
			for (String key : headers.keySet()) {
				resultView.append(key + "=" + headers.get(key));
				resultView.append("\r");
			}
			headers.forEach((key, value) -> test.log(LogStatus.INFO, "The Headers are : " + (key + " : " + value)));
			headers.put("Content-Type", "application/xml");
			httpRequest.headers(headers);
			test.log(LogStatus.INFO, ": ", "application/xml");

			fileAsString = Utility.navigatteToPickRequestXMLFile("TC139_QuoteSubmissionDeltaAPI.xml",
					new Utility.TagModel("accountNumber", accntNum));

			test.log(LogStatus.INFO, "Method Type : ", "POST");
			String EndPointResourceUrl = ("/oeservices/quote/submit/delta");
			test.log(LogStatus.PASS, "End Point Resource URL : ",
					"<a href=" + EndPointResourceUrl + " > " + EndPointResourceUrl + " </a>");

			httpRequest.body(fileAsString);
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Request: " + escapeFormatXml(fileAsString));

			Response response = httpRequest.request(Method.POST, "/oeservices/quote/submit/delta");

			String responseBody = response.getBody().asString();
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Response: " + escapeFormatXml(responseBody));

			String contentType = response.header("Content-Type");
			Assert.assertEquals(contentType, "application/xml");
			test.log(LogStatus.INFO, "Accept content Type is : " + contentType);

			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
			test.log(LogStatus.PASS, "Status Code Is : " + statusCode);

			String statusLine = response.getStatusLine();
			Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
			test.log(LogStatus.PASS, "Status line is : " + statusLine);

			log.debug("Leaving navigateToHitQuoteSubmissionDeltaAPI");
		} catch (Exception e) {
			log.error("Error in navigateToHitQuoteSubmissionDeltaAPI:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(e.getMessage(), false);
		}
	}

	public void navigateToHitCollectionSuspension1(String accntNum) {
		log.debug("Entering navigateToHitCollectionSuspension");
		try {
			String ServerURL = Utility.getValueFromPropertyFile("basepage_url");
			test.log(LogStatus.INFO, "End Point URL : ", "<a href=" + ServerURL + " > " + ServerURL + " </a>");
			RestAssured.baseURI = ServerURL;
			String fileAsString = "";

			RequestSpecification httpRequest = addHeaders("vnd.shaw.order");
			fileAsString = Utility.navigatteToPickRequestXMLFile("TC41_CollectionResumptionAfterUG.xml",
					new Utility.TagModel("accountNumber", accntNum));

			test.log(LogStatus.INFO, "Method Type : ", "PUT");
			String EndPointResourceUrl = ("/services/omservice/suspendResumeImmediate/suspend");
			test.log(LogStatus.PASS, "End Point Resource URL : ",
					"<a href=" + EndPointResourceUrl + " > " + EndPointResourceUrl + " </a>");

			httpRequest.body(fileAsString);
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Request: " + escapeFormatXml(fileAsString));

			Response response = httpRequest.request(Method.PUT, "/services/omservice/suspendResumeImmediate/suspend");

			String responseBody = response.getBody().asString();
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Response: " + escapeFormatXml(responseBody));

			String contentType = response.header("Content-Type");
			Assert.assertEquals(contentType, "application/vnd.shaw.order+xml");
			test.log(LogStatus.INFO, "Accept content Type is : " + contentType);

			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
			test.log(LogStatus.PASS, "Status Code Is : " + statusCode);

			String statusLine = response.getStatusLine();
			Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
			test.log(LogStatus.PASS, "Status line is : " + statusLine);

			log.debug("Leaving navigateToHitCollectionSuspension");
		} catch (Exception e) {
			log.error("Error in navigateToHitCollectionSuspension:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(e.getMessage(), false);
		}
	}

	public void navigateToMyAcQuoteValidateFibre(String requestXml, String accountId) {
		log.debug("Entering navigateToMyAcQuoteValidateFibre");
		try {
			String fileAsString = "";
			String ServerURL = Utility.getValueFromPropertyFile("basepage_url");
			test.log(LogStatus.INFO, "End Point URL : ", "<a href=" + ServerURL + " > " + ServerURL + " </a>");
			RestAssured.baseURI = ServerURL;
			RequestSpecification httpRequest = addHeaders("application/xml");

			if (requestXml.equals("TC140")) {
				fileAsString = Utility.navigatteToPickRequestXMLFile("TC140_MyAcQuoteValidateFiber.xml",new Utility.TagModel("v1:AccountId", "1" + accountId));
			}
			if (requestXml.equals("TC146")) {
				fileAsString = Utility.navigatteToPickRequestXMLFile("TC146_QuoteValidationDPTHitronMoxi.xml",new Utility.TagModel("v1:AccountId", "1" + accountId));
			}
			
			Response response = httpRequest.request(Method.POST, "/sslservices/myaccount/quotedetails");
			test.log(LogStatus.INFO, "Method Type : ", "POST");
			String EndPointResourceUrl = ("/sslservices/myaccount/quotedetails");
			test.log(LogStatus.PASS, "End Point Resource URL : ","<a href=" + EndPointResourceUrl + " > " + EndPointResourceUrl + " </a>");

			httpRequest.body(fileAsString);
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Request: " + escapeFormatXml(fileAsString));

			String responseBody = response.getBody().asString();
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Response: " + escapeFormatXml(responseBody));

			String contentType = response.header("Content-Type");
			Assert.assertEquals(contentType, "application/xml");
			test.log(LogStatus.INFO, "Accept content Type is : " + contentType);

			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
			test.log(LogStatus.PASS, "Status Code Is : " + statusCode);

			String statusLine = response.getStatusLine();
			Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
			test.log(LogStatus.PASS, "Status line is : " + statusLine);

			log.debug("Leaving navigateToMyAcQuoteValidateFibre");
		} catch (Exception e) {
			log.error("Error in navigateToMyAcQuoteValidateFibre:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(e.getMessage(), false);
		}
	}

	public void navigateToHitGetProductHighLevelDetails(String accntNum) {
		log.debug("Entering navigateToHitGetProductHighLevelDetails");
		try {
			String fileAsString = "";
			String ServerURL = Utility.getValueFromPropertyFile("basepage_url");
			test.log(LogStatus.INFO, "End Point URL : ", "<a href=" + ServerURL + " > " + ServerURL + " </a>");
			RestAssured.baseURI = ServerURL;
			RequestSpecification httpRequest = addHeaders("vnd.shaw.order");

			fileAsString = Utility.navigatteToPickRequestXMLFile("TC145_GetProdHighLevelEndPoint.xml",
					new Utility.TagModel("cial:AccountId", "1" + accntNum));

			test.log(LogStatus.INFO, "Method Type : ", "POST");
			String EndPointResourceUrl = ("/sslservices/occ/products");
			test.log(LogStatus.PASS, "End Point Resource URL : ",
					"<a href=" + EndPointResourceUrl + " > " + EndPointResourceUrl + " </a>");

			httpRequest.body(fileAsString);
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Request: " + escapeFormatXml(fileAsString));

			Response response = httpRequest.request(Method.POST, "/sslservices/occ/products");

			String responseBody = response.getBody().asString();
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Response: " + escapeFormatXml(responseBody));

			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
			test.log(LogStatus.PASS, "Status Code Is : " + statusCode);

			String statusLine = response.getStatusLine();
			Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
			test.log(LogStatus.PASS, "Status line is : " + statusLine);

			log.debug("Leaving navigateToHitGetProductHighLevelDetails");
		} catch (Exception e) {
			log.error("Error in navigateToHitGetProductHighLevelDetails:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(e.getMessage(), false);
		}
	}

	public void navigateToHitSoapRequestwithAccountNbr_148() {
		log.debug("Entering navigateToHitSoapRequestwithAccountNbr_148");
		try {
			String orderNum = Utility.getValueFromSoapAPIProp("orderNumTC148");
			String ServerURL = Utility.getValueFromPropertyFile("basepage_url");
			test.log(LogStatus.INFO, "End Point URL : ", "<a href=" + ServerURL + " > " + ServerURL + " </a>");
			RestAssured.baseURI = ServerURL;
			RequestSpecification httpRequest = RestAssured.given();

			HashMap<String, String> headers = new HashMap<>();
			headers.put("X_SHAW_ORIGINATING_USER_ID", "SJRB\\1");
			headers.put("X_SHAW_ORIGINATING_IP_ADDRESS", "1");
			headers.put("X_SHAW_TRANSACTION_ID", orderNum);
			headers.put("X_SHAW_ORIGINATING_HOST_NAME", "1");
			headers.put("X_SHAW_ORIGINAL_MODULE_ID", "1");

			StringBuilder resultView = new StringBuilder();
			for (String key : headers.keySet()) {
				resultView.append(key + "=" + headers.get(key));
				resultView.append("\r");
			}
			headers.forEach((key, value) -> test.log(LogStatus.INFO, "The Headers are : " + (key + " : " + value)));
			headers.put("Content-Type", "application/soap+xml");
			httpRequest.headers(headers);
			test.log(LogStatus.INFO, "Content-Type: ", "application/soap+xml");

			File file = new File(System.getProperty("user.dir")
					+ "\\testdata\\Soap_RestAPI\\TC148_GetOrderDetailsByCustAccntEndpoint.xml");
			InputStream is = new FileInputStream(file);
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			String line = buf.readLine();
			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line).append("\n");
				line = buf.readLine();
			}
			String fileAsString = sb.toString();
			buf.close();
			is.close();

			httpRequest.body(fileAsString);
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Request: " + escapeFormatXml(fileAsString));

			Response response = httpRequest.request("/oeservices/quote/submit/delta");

			String responseBody = response.getBody().asString();
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Response: " + escapeFormatXml(responseBody));

			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
			test.log(LogStatus.PASS, "Status Code Is : " + statusCode);

			String statusLine = response.getStatusLine();
			Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
			test.log(LogStatus.PASS, "Status line is : " + statusLine);

			log.debug("Leaving navigateToHitSoapRequestwithAccountNbr_148");
		} catch (Exception e) {
			log.error("Error in navigateToHitSoapRequestwithAccountNbr_148:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(e.getMessage(), false);
		}
	}
	
	public void triggerGetOrderIdEndPointDetails(String accntNum) {
		log.debug("Entering triggerGetOrderIdEndPointDetails");
		try {
			String ServerURL = Utility.getValueFromPropertyFile("oestub_url");
			test.log(LogStatus.INFO, "End Point URL : ", "<a href=" + ServerURL + " > " + ServerURL + " </a>");
			RestAssured.baseURI = ServerURL;
			RequestSpecification httpRequest = addHeaders("application/soap+xml");

			String fileAsString = Utility.navigatteToPickRequestXMLFile("TC159_CustServiceAgreement.xml",
					new Utility.TagModel("AccountId", "1" + accntNum), new Utility.TagModel("AccountNumber", accntNum));

			test.log(LogStatus.INFO, "Method Type : ", "POST");
			String EndPointResourceUrl = ("/esi/agreementservice/ncfacade");
			test.log(LogStatus.PASS, "End Point URL : ",
					"<a href=" + EndPointResourceUrl + " > " + EndPointResourceUrl + " </a>");

			httpRequest.body(fileAsString);
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Request: " + escapeFormatXml(fileAsString));

			Response response = httpRequest.request(Method.POST, "/esi/agreementservice/ncfacade");

			String responseBody = response.getBody().asString();
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Response: " + escapeFormatXml(responseBody));

			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
			test.log(LogStatus.PASS, "Status Code is : " + statusCode);

			String statusLine = response.getStatusLine();
			Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
			test.log(LogStatus.PASS, "Status line is : " + statusLine);

			log.debug("Leaving triggerGetOrderIdEndPointDetails");
		} catch (Exception e) {
			log.error("Error in triggerGetOrderIdEndPointDetails:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(e.getMessage(), false);
		}
	}
	
	public void tbapiGraphQLSanityTest() {
		log.debug("Entering getOrderIdEndPointDetails");
		try {
			String orderNum = Utility.getValueFromSoapAPIProp("TBAPIID");
			String ServerURL = Utility.getValueFromPropertyFile("tbapi_url");
			test.log(LogStatus.INFO, "End Point URL : ", "<a href=" + ServerURL + " > " + ServerURL + " </a>");
			RestAssured.baseURI = ServerURL;
			RequestSpecification httpRequest = RestAssured.given();

			HashMap<String, String> headers = new HashMap<>();
			headers.put("X_SHAW_ORIGINATING_USER_ID", "SJRB\\1");
			headers.put("X_SHAW_ORIGINATING_IP_ADDRESS", "1");
			headers.put("X_SHAW_TRANSACTION_ID", orderNum);
			headers.put("X_SHAW_ORIGINATING_HOST_NAME", "1");
			headers.put("X_SHAW_ORIGINAL_MODULE_ID", "1");

			StringBuilder resultView = new StringBuilder();
			for (String key : headers.keySet()) {
				resultView.append(key + "=" + headers.get(key));
				resultView.append("\r");
			}
			headers.forEach((key, value) -> test.log(LogStatus.INFO, "The Headers are : " + (key + " : " + value)));
			httpRequest.headers(headers);

			Response response = httpRequest.request(Method.GET, "/services/omservice/order/" + orderNum);
			test.log(LogStatus.INFO, "Method Type : ", "GET");
			String EndPointResourceUrl = ("/services/omservice/order/" + orderNum);
			test.log(LogStatus.PASS, "End Point Resource URL : ",
					"<a href=" + EndPointResourceUrl + " > " + EndPointResourceUrl + " </a>");
			test.log(LogStatus.INFO, "Request Body : ", "Empty");

			String responseBody = response.getBody().asString();
			test.log(LogStatus.INFO, "<span style='font-weight:bold;'> Response: " + escapeFormatXml(responseBody));

			String contentType = response.header("Content-Type");
			Assert.assertEquals(contentType, "application/vnd.shaw.order+xml");
			test.log(LogStatus.INFO, "Accept content Type is : " + contentType);

			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 200);
			test.log(LogStatus.PASS, "Status Code Is : " + statusCode);

			String statusLine = response.getStatusLine();
			Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
			test.log(LogStatus.PASS, "Status line is : " + statusLine);

			log.debug("Leaving getOrderIdEndPointDetails");
		} catch (Exception e) {
			log.error("Error in getOrderIdEndPointDetails:" + e.getMessage());
			test.log(LogStatus.ERROR, e);
			test.log(LogStatus.FAIL, "Snapshot Below: " + test.addScreenCapture(addScreenshot()));
			Assert.assertTrue(e.getMessage(), false);
		}
	}
}
