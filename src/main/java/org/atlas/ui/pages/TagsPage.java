package org.atlas.ui.pages;

import java.util.List;

import org.apache.atlas.seleniumtests.HomePageTest;
import org.apache.atlas.utilities.AtlasDriverUtility;
import org.apache.log4j.Logger;
import org.apcahe.atlas.pageobject.TagsPageElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class TagsPage extends AtlasDriverUtility {

	static WebDriver driver = getDriver();
	private static final Logger LOGGER = Logger.getLogger(HomePageTest.class);
	TagsPageElements tagsPageElements = null;
	
	public TagsPage() {
		tagsPageElements = PageFactory.initElements(driver,
				TagsPageElements.class);
	}

	public void navigateToTagsTab() {
		waitForPageLoad(driver, 60);
		AtlasDriverUtility.customWait(30);
		if (webElement.isElementExists(tagsPageElements.tagTabLink)) {
			WebElement e = tagsPageElements.tagTabLink;
			e.click();
			waitUntilPageRefresh(driver);
		} else {
			LOGGER.error("Tags tab not present");
		}
		waitForPageLoad(driver, 60);
	}

	public String getPageHeader() {
		return tagsPageElements.tagPageHeader.getText();
	}

	public boolean validateTagsSections() {
		String labelName = "";
		boolean isTagFieldDisplayed = false;

		int numberOfLables = tagsPageElements.labels.size();

		for (int index = 0; index < numberOfLables; index++) {
			labelName += tagsPageElements.labels.get(index).getText();
		}

		if (labelName.contains("Tag Name") && labelName.contains("Parent Tag")) {
			isTagFieldDisplayed = webElement
					.isElementExists(tagsPageElements.tagNameTextField)
					&& webElement
							.isElementExists(tagsPageElements.parentTagSelectionField);
		}
		if (isTagFieldDisplayed) {
			isTagFieldDisplayed = webElement
					.isElementEnabled(tagsPageElements.addAttributeButton)
					&& webElement.isElementExists(tagsPageElements.saveButton);
		} else {
			LOGGER.error("Tag label fields " + labelName + " not avaialble");
		}
		return isTagFieldDisplayed;
	}

	public TagsPage enterTagName(String tagName) {
		webElement.clearAndSendKeys(tagsPageElements.tagNameTextField, tagName);
		return this;
	}

	public TagsPage selectParentTag(String parentTagName) {
		Select selectParent = new Select(tagsPageElements.parentTagSelectionField);
		selectParent.selectByValue(parentTagName);
		return this;
	}

	public TagsPage addAddtribute() {
		tagsPageElements.addAttributeButton.click();
		waitUntilElementVisible(tagsPageElements.addAttributeName, 10);
		return this;
	}

	public TagsPage enterAttributeName(String attrName) {
		webElement
				.clearAndSendKeys(tagsPageElements.addAttributeName, attrName);
		return this;
	}

	public TagsPage saveTagName() {
		tagsPageElements.saveButton.click();
		return this;
	}

	private String tagName = "";
	
	public String getTagName(){
		return tagName;
	}

	
	public void createExistingTag() {
		AtlasDriverUtility.customWait(6);
		
				/*List<WebElement> parentTags = AtlasDriverUtility.waitUntilElementVisible(tagsPageElements.parentTagSelectionField
						.findElements(By.cssSelector(".ng-binding")), 50);*/
		
		List<WebElement> parentTags = tagsPageElements.parentTagSelectionField
				.findElements(By.cssSelector(".ng-binding"));
		if (parentTags.size() > 0) {
			tagName = parentTags.get(0).getText();
			enterTagName(tagName);
			webElement
			.clearAndSendKeys(tagsPageElements.addAttributeName, "AutomationTest");
			saveTagName();
		} else {
			LOGGER.warn("No existing tags to create.");
		}
	}
	
	public boolean validateParentTag(String parentTagName) {		
		boolean isTagDisplayed = false;
		for ( WebElement we: tagsPageElements.options) {	        
	        if ( we.getText().equals(parentTagName) ) isTagDisplayed = true;
	    }
		return isTagDisplayed;
	}
	
	public String getNotificationMessage(){
		AtlasDriverUtility.customWait(5);
		return tagsPageElements.notificationBanner.getText();
	}
}
