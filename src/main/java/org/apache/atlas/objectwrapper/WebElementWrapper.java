package org.apache.atlas.objectwrapper;

import org.apache.atlas.utilities.AtlasDriverUtility;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;


public class WebElementWrapper {

	private static final Logger LOGGER = Logger.getLogger(AtlasDriverUtility.class);
	
	public void click(WebElement element){
		element.click();
	}
	
	public void clearAndSendKeys(WebElement element, String text){
		if(isElementExists(element)) {
			element.clear();
			element.sendKeys(text);
		} else {
			LOGGER.error("Element is not exists");
		}
	}
	
	public boolean isElementExists(WebElement element){
		boolean isDisplayed = false;
		try {
			isDisplayed = element.isDisplayed();
		} catch (NoSuchElementException nsee){
			return false;
		}
		return isDisplayed;
	}
	
	public boolean isElementEnabled(WebElement element){
		boolean isEnabled = false;
		if(isElementExists(element)) {
			isEnabled =  element.isEnabled();
		}
		return isEnabled;
	}
}

