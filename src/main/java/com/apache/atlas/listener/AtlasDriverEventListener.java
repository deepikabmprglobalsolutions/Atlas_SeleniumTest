package com.apache.atlas.listener;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.testng.log4testng.Logger;

public class AtlasDriverEventListener extends AbstractWebDriverEventListener {
	private static final Logger LOGGER = Logger
			.getLogger(AtlasDriverEventListener.class);

	@Override
	public void afterClickOn(WebElement elementName, WebDriver arg1) {
		try {
			LOGGER.info(elementName.getTagName() + " tag with text / name "
					+ elementName.getText() + " clicked");
		} catch (StaleElementReferenceException sere) {
			LOGGER.info("After clicking element was dettached from DOM.");
		}
	}

	
}
