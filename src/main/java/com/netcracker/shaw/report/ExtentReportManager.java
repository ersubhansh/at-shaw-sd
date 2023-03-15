package com.netcracker.shaw.report;

import java.io.File;
import java.util.Date;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.netcracker.shaw.at_shaw_sd.util.Constants;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

public class ExtentReportManager {
	public ExtentReports extent;
	static Logger log = Logger.getLogger(ExtentReportManager.class);
	public ExtentReports getInstance(String testName) {
		File theDir = new File(String.valueOf(Constants.REPORT_PATH)); 
		try {
			if (!theDir.exists()) { 
				System.out.println("Directory " + Constants.REPORT_PATH + " does not exist");
				boolean result = theDir.mkdir();
				if (result) {
					JOptionPane.showMessageDialog(null, "New Folder created!");
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		//if(extent == null) {
		Date d = new Date();
		String fileName = d.toString().replace(":", "_").replace(" ", "_") +"_"+testName+".html";
		log.debug("File name is :" +fileName);
		extent = new ExtentReports(theDir + "/" + fileName, true, DisplayOrder.OLDEST_FIRST);
		log.debug("Dir name is :"+theDir+" and File Name is: "+fileName);
		extent.loadConfig(new File(Constants.EXTENT_CONFIG_PATH));

		// optional
		extent.addSystemInfo("Selenium Version", "3.141.59");
		extent.addSystemInfo("Environment", "QA");
		extent.addSystemInfo("Application", "SHAW-BSS-Project");

		return extent;
	}
}

