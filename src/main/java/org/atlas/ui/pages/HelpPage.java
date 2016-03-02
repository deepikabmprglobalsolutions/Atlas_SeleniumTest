package org.atlas.ui.pages;

import java.util.Set;

import org.apache.atlas.objectwrapper.WebDriverWrapper;
import org.apache.atlas.utilities.AtlasDriverUtility;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class HelpPage extends AtlasDriverUtility {

	private static final Logger LOGGER = Logger.getLogger(HelpPage.class);

	public static void validateHelpPage() {
		WebDriver driver = getDriver();
		String parentWindow = driver.getWindowHandle();
		Set<String> winHandles = driver.getWindowHandles();
		WebDriver childWindow = null;
		if (winHandles.size() > 0) {
			for (String handler : winHandles) {
				childWindow = driver.switchTo().window(handler);
			}
		}
		AtlasDriverUtility.waitForPageLoad(childWindow, 30);
		if (childWindow != null) {
			childWindow.close();
		} else {
			LOGGER.fatal("Child window didn't create");
			System.err.println("child window null");
		}
		driver = childWindow.switchTo().window(parentWindow);
		WebDriverWrapper.setDriver(new EventFiringWebDriver(driver));
	}

}
