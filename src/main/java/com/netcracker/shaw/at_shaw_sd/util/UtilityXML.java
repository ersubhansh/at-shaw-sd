package com.netcracker.shaw.at_shaw_sd.util;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

public class UtilityXML {
	static Logger log = Logger.getLogger(UtilityXML.class);
	public static void storeXmlFileForExpectedFile(Document doc, String previousFileName, String newStringFilePath) {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(newStringFilePath));
			// System.out.println( "result:" + result );
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
			log.debug("XML file updated successfully");
		} catch (TransformerException e1) {
			e1.printStackTrace();
			log.error("Error:" + e1.getMessage());
		}
	}
    
	public static void storeXmlFileForActual(Document doc, String newStringFileName) {
		String tempPath = Utility.getValueFromPropertyFile("temp_xml_path");
		tempPath = tempPath.replace("\\", "//");
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(tempPath + "//" + newStringFileName));
			// System.out.println( "result:" + result );
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
			log.debug("XML file updated successfully");
		} catch (TransformerException e1) {
			e1.printStackTrace();
			log.error("Error:" + e1.getMessage());
		}
	}
    
    public static void updateElementValue(Document doc, String rootNode, String desiredNode, String desiredNodeValue) {
        NodeList myElements = doc.getElementsByTagNameNS("*",rootNode);
         Element emp = null;
        //loop for each myElements
         for(int i=0; i<myElements.getLength();i++){
             emp = (Element) myElements.item(i);
             Node name = emp.getElementsByTagNameNS("*",desiredNode).item( 0 ).getFirstChild();
             name.setNodeValue(desiredNodeValue);
         }
     }
    
	public static void updateElementValue(Document doc, String desiredNode, String desiredNodeElement,String desiredNodeValue, String matchValue) {
		NodeList myElements = doc.getElementsByTagNameNS("*", desiredNode);
		Element emp = null;
		// loop for each myElements
		for (int i = 0; i < myElements.getLength(); i++) {
			emp = (Element) myElements.item(i);
			NodeList nameList = emp.getElementsByTagNameNS("*", desiredNodeElement);
			log.debug("nameList:" + nameList);
			for (int j = 0; j < nameList.getLength(); j++) {
				Node name = emp.getElementsByTagNameNS("*", desiredNodeElement).item(j).getFirstChild();
				if (name.getNodeValue().contains(matchValue))
					name.setNodeValue(desiredNodeValue);
			}
		}
	}

	public static void updateAttributeValue(Document doc, String desiredNode, String desiredAttributeName, String desiredAttributeValue) {
		NodeList myElements = doc.getElementsByTagNameNS("*", desiredNode);
		Element emp = null;
		// loop for each myElements
		for (int i = 0; i < myElements.getLength(); i++) {
			emp = (Element) myElements.item(i);
			if (emp.hasAttribute(desiredAttributeName)) {
				emp.setAttribute(desiredAttributeName, desiredAttributeValue);
			}
		}
	}
     
	public static String getAttributeValue(Document doc, String desiredNode, String desiredAttributeName) {
		NodeList myElements = doc.getElementsByTagNameNS("*", desiredNode);
		Element emp = null;
		String desiredAttributeValue = "";
		// loop for each myElements
		for (int i = 0; i < myElements.getLength(); i++) {
			emp = (Element) myElements.item(i);
			if (emp.hasAttribute(desiredAttributeName)) {
				desiredAttributeValue = emp.getAttribute(desiredAttributeName);
			}
		}
		return desiredAttributeValue;
	}
     
     public static void removeAll(Document doc, String rootNode) {
         Element element = (Element) doc.getElementsByTagNameNS("*",rootNode).item(0);
         Node parent = element.getParentNode();
         parent.removeChild(element);
         parent.normalize();
     }
     
	public static Document convertStringToDocument(String xmlStr) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
     
	public static String convertDocumentToString(Document doc) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			// below code to remove XML declaration
			// transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			String output = writer.getBuffer().toString();
			return output;
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		return null;
	}
}
