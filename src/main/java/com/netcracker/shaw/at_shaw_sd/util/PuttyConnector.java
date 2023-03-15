package com.netcracker.shaw.at_shaw_sd.util;

import java.util.Date;
import java.util.Properties;
import org.apache.log4j.Logger;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Ramesh Reddy K (raka0617)
 *
 * Aug 10, 2018
 */

@SuppressWarnings(value = {"unchecked", "rawtypes"})
public class PuttyConnector{
	
	private ExtentTest test;
	
	private static final Logger log = Logger.getLogger(PuttyConnector.class);
	
	public static void main(String[] args) {
		PuttyConnector puttyConnector = new PuttyConnector("qaapp030cn.netcracker.com", "netcrk", "n3w_netcrk");
		String folderPath = "/home/netcrk/QA/"+Constants.UNIX_FOLDER_DATE_FORMAT.format(new Date());
		boolean folderExists = puttyConnector.checkFolderInBox(folderPath, "No");
		folderExists = puttyConnector.checkFileInBox(folderPath+"/test.txt");
		folderExists = puttyConnector.checkFileInBox(folderPath+"/test.zip");
		System.out.println(folderExists);
		puttyConnector.closeConnection();
	}
	ChannelSftp channelSftp;
	Session session;
	
	public PuttyConnector(String host, String user, String password, ExtentTest test) {
		this(host, user, password);
		this.test = test;
	}
	
	public PuttyConnector(String host, String user, String password) {
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		config.put("PreferredAuthentications", "password");
		JSch.setConfig(config);
		JSch jsch = new JSch();
		try {
			log.debug("Attempting Connection to " + host + ", with User: " + user);
			session = jsch.getSession(user, host, 22);
			session.setPassword(password);
			session.connect();
			log.debug("Successfully Established Connection to : " + host);
			Channel channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}
	
	public void closeConnection() {
		if (channelSftp != null)
			channelSftp.exit();
		if (session != null)
			session.disconnect();
	}
	
	public boolean checkFolderInBox(String folderPath, String createDir) {
		boolean folderExists = false;
		try {
			log.debug("Checking for Path : " + folderPath);
			if (test != null)
				test.log(LogStatus.INFO, "At Path : " + folderPath);
			SftpATTRS attrs = null;
			try {
				attrs = channelSftp.stat(folderPath);
			} catch (Exception e) {
				log.error(folderPath + " not found");
			}

			if (attrs != null) {
				log.debug(folderPath + " exists in the session");
				test.log(LogStatus.INFO, folderPath + " exists in the session");
				if (attrs.isDir()) {
					folderExists = true;
					String lastModiDate = channelSftp.lstat(folderPath).getMtimeString();
					test.log(LogStatus.INFO, "Modified Date of " + folderPath + " : " + lastModiDate);
				}
			} else {

				if (createDir.equalsIgnoreCase("Yes")) {
					log.debug("Directory doesn't exist, hence creating it");
					test.log(LogStatus.INFO, folderPath + " does not exists in the session. Hence Creating it");
					channelSftp.mkdir(folderPath);
				}
				folderExists = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return folderExists;
	}

	public boolean checkFileInBox(String filePath) {
		boolean fileExists = false;
		try {
			test.log(LogStatus.INFO, "Checking for existance of File: " + filePath);
			SftpATTRS attrs = null;
			try {
				attrs = channelSftp.lstat(filePath);
			} catch (Exception e) {
				log.error(filePath + " not found");
			}
			if (attrs == null) {
				test.log(LogStatus.INFO, " File does not exist ");
			} else {
				test.log(LogStatus.INFO, " File Exists ");
				String lastModiDate = channelSftp.lstat(filePath).getMtimeString();
				test.log(LogStatus.INFO, "Latest Modified Date of File : " + lastModiDate);
				fileExists = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return fileExists;
	}
}
