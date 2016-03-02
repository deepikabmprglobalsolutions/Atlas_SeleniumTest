package org.apache.atlas.utilities;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.atlas.objectwrapper.WebDriverWrapper;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.atlas.testHelper.AtlasConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class AtlasDriverUtility  extends WebDriverWrapper {

	private static final Logger LOGGER = Logger
			.getLogger(AtlasDriverUtility.class);

	public static WebElement waitUntilElementVisible(WebElement element, int timeInSec) {
		WebDriverWait wait = new WebDriverWait(getDriver(), timeInSec);
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static void customWait(int timeInSec) {
		try {
			Thread.sleep(1000 * timeInSec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static boolean waitForPageLoad(WebDriver driver, int timeoutInSeconds) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript(
						"return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		return wait.until(pageLoadCondition);
	}

	public static void waitUntilPageRefresh(WebDriver driver) {
		long timeout = System.currentTimeMillis() + 10000 * 60;
		try {
			Thread.sleep(1000 * 3);
			WebElement spinner = driver.findElement(By
					.cssSelector(".search-spinner"));
			while (webElement.isElementExists(spinner)) {
				spinner = driver.findElement(By.cssSelector(".search-spinner"));
				Thread.sleep(1000);
				if (System.currentTimeMillis() > timeout) {
					LOGGER.info("Page refresh took "
							+ (System.currentTimeMillis() / 10000 * 60)
							+ " seconds");
					break;
				}
			}
		} catch (StaleElementReferenceException sere) {
		} catch (NoSuchElementException nsee) {
		} catch (InterruptedException ie) {
			System.err
					.println("AtlasDriverUtility: Intterupted Exception in page refresh");
		}
	}
	
	public static void testCaseExecutionTime(String testMethodName){
		long time = (System.currentTimeMillis() - AtlasConstants.START_TIME);
		LOGGER.info(TimeUnit.MILLISECONDS.toSeconds(time)
				+ " seconds took to '" + testMethodName + "'");
	}

	public static void pageLoadedTime(long startTime, String text) {
		long time = (System.currentTimeMillis() - startTime);
		LOGGER.info(TimeUnit.MILLISECONDS.toSeconds(time)
				+ " seconds took to '" + text + "'");
	}
	
	public static void testSuiteExecutionTime(long startTime, String text) {
		long time = (System.currentTimeMillis() - startTime);
		LOGGER.info(TimeUnit.MILLISECONDS.toSeconds(time)
				+ " seconds took to run entire suite");
	}
	
	public static void getScreenshot(String fileName) {
		LOGGER.info("ENTERED: getScreenshot with fileName " + fileName);
		TakesScreenshot screenshot = ((TakesScreenshot)driver);
		File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
		try {
			Reporter.log("<a href=" + fileName + " target='_blank' >" + fileName + "</a>");
			FileUtils.copyFile(sourceFile, new File(AtlasConstants.SCREENSHOTS_DIR
					+ fileName + ".jpeg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.info("EXITED: getScreenshot with fileName " + fileName);
	}
	
	public static void clickWebElement(String elem){
		
	}
	
}
