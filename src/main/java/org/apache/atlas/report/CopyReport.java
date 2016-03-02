package org.apache.atlas.report;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class CopyReport {
	private static final Logger log = Logger.getLogger(CopyReport.class);
	
	private String remoteDest;
	private File localSrc;
	
	public CopyReport(String destinationDir, File localFileDir){
		this.remoteDest = destinationDir;
		this.localSrc = localFileDir;
	}
	public void saveFilesToServer(String username, String password)
			throws IOException {
		FTPClient ftp = new FTPClient();
		ftp.connect("ftp.mprhost.com");
		if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
			ftp.disconnect();
			log.fatal("FTP not disconnected");
		} else {
			log.fatal("FTP connection failed");
			return;
		}

		ftp.login(username, password);
		ftp.enterLocalPassiveMode(); //mode to pass firewall
		
		log.info("Connected to server.");
		log.info(ftp.getReplyString());
		
		ftp.changeWorkingDirectory(remoteDest);
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);

		try {
			upload(localSrc, ftp);
		} finally {
			boolean loggedOut = ftp.logout();
			log.info(loggedOut ? "Successfully logged out" : "Unable to log out");
			ftp.disconnect();
			log.info("FTP disconnected");
		}
	}

	public void upload(File src, FTPClient ftp) throws IOException {
		if (src.isDirectory()) {
			ftp.makeDirectory(src.getName());
			ftp.changeWorkingDirectory(src.getName());
			for (File file : src.listFiles()) {
				upload(file, ftp);
			}
			ftp.changeToParentDirectory();
		} else {
			InputStream srcStream = null;
			try {
				srcStream = src.toURI().toURL().openStream();
				ftp.storeFile(src.getName(), srcStream);
			} finally {
				IOUtils.closeQuietly(srcStream);
			}
		}
	}
}
