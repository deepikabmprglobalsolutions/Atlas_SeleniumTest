package com.apache.atlas.listener;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.atlas.testHelper.AtlasConstants;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.log4testng.Logger;

import com.thoughtworks.selenium.Selenium;

@SuppressWarnings("deprecation")
public class AtlasTestReportListener extends TestListenerAdapter {

	Logger LOGGER = Logger.getLogger(AtlasTestReportListener.class);

	@Override
	public void onTestFailure(ITestResult result) {
		String testMethodName = result.getMethod().getMethodName();
		Reporter.setCurrentTestResult(result);
		Reporter.log("screenshot saved at " + AtlasConstants.PWD
				+ "\\reports\\" + testMethodName + ".jpg");
//		AtlasDriverUtility.getScreenshot(testMethodName);
		createScreenshot(result);
		/*Reporter.log("<a href='../"
				+ result.getName()
				+ ".jpg' <img src='../"
				+ result.getName()
				+ ".jpg' <span id=\"IL_AD5\" class=\"IL_AD\">hight</span>='100' width='100'/> </a>");
		Reporter.setCurrentTestResult(null);*/
	}

	public void createScreenshot(ITestResult tr) {
		String testName = tr.getTestClass().getRealClass().getName();
		String imagePath = "screenshot-" + testName + "-"
				+ (new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))
				+ ".png";
		try {
			Selenium sel = (Selenium) tr.getAttribute("selenium");
			LOGGER.info("Parameter selenium is set to :" + sel);
			if (sel != null) {
				String base64Screenshot = sel
						.captureEntirePageScreenshotToString("");
				byte[] decodedScreenshot = Base64.decodeBase64(base64Screenshot
						.getBytes());
				FileOutputStream fos = null;
				try {
					File screenShotFname = new File(AtlasConstants.PWD, imagePath);
					fos = new FileOutputStream(screenShotFname);
					fos.write(decodedScreenshot);
					LOGGER.info("Stored screenshot in file "
							+ screenShotFname.getAbsolutePath());
					reportLogScreenshot(screenShotFname);
				} finally {
					if (null != fos) {
						fos.close();
					}
				}
			} else {
				LOGGER.warn("selenium object is not set, cannot get screenshot");
				LOGGER.warn(new Exception("STACKTRACE"));
			}

		} catch (Exception e) {
		}
	}

	protected void reportLogScreenshot(File file) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");

		String absolute = file.getAbsolutePath();
		int beginIndex = absolute.indexOf(".");
		String relative = absolute.substring(beginIndex).replace(".\\", "");
		String screenShot = relative.replace('\\', '/');

		Reporter.log("<a href=\"" + screenShot
				+ "\"><p align=\"left\">Error screenshot at " + new Date()
				+ "</p>");
		Reporter.log("<p><img width=\"1024\" src=\"" + file.getAbsoluteFile()
				+ "\" alt=\"screenshot at " + new Date() + "\"/></p></a><br />");
	}
}
