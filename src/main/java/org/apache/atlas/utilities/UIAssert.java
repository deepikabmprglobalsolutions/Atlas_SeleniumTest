package org.apache.atlas.utilities;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class UIAssert {

	private UIAssert() {
        throw new AssertionError("Instantiating utility class...");
    }
    private static final Logger LOGGER = Logger.getLogger(UIAssert.class);

    public static void assertDisplayed(WebElement element, String webElementName) {
        LOGGER.info(String.format("Checking if WebElement '%s' is displayed", webElementName));
        int timeoutSeconds = 2;
        for (int i = 0; !element.isDisplayed() && i < timeoutSeconds * 10; i++) {
            sleepSeconds(0.1);
        }
        LOGGER.info(String.format("WebElement '%s' is displayed", webElementName));
    }

    public static void assertNotDisplayed(WebElement clusterForm, String webElementName) {
        LOGGER.info(String.format("Checking if WebElement '%s' is displayed", webElementName));
        Assert.assertFalse(clusterForm.isDisplayed(),
            String.format("WebElement '%s' should NOT have been displayed", webElementName));
        LOGGER.info(String.format("WebElement '%s' is not displayed", webElementName));
    }
    
    public static void sleepSeconds(double seconds) {
        long ms = (long) (seconds * 1000);
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            LOGGER.info("Sleep was interrupted");
        }
    }
}
