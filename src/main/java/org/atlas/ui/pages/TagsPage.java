package org.atlas.ui.pages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.atlas.seleniumtests.HomePageTest;
import org.apache.atlas.utilities.AtlasDriverUtility;
import org.apache.atlas.utilities.AtlasFileUtils;
import org.apache.log4j.Logger;
import org.apcahe.atlas.pageobject.TagsPageElements;
import org.atlas.testHelper.AtlasConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.thoughtworks.selenium.webdriven.commands.IsElementPresent;

public class TagsPage extends AtlasDriverUtility {

	static WebDriver driver = getDriver();
	List<String> list;
	private static final Logger LOGGER = Logger.getLogger(HomePageTest.class);
	TagsPageElements tagsPageElements = null;

	public TagsPage() {
		tagsPageElements = PageFactory.initElements(driver,
				TagsPageElements.class);
	}

	public void navigateToTagsTab() {
		waitForPageLoad(driver, 60);
		AtlasDriverUtility.customWait(3);
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
		Select selectParent = new Select(
				tagsPageElements.parentTagSelectionField);
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

	public String getTagName() {
		return tagName;
	}

	public void createExistingTag() {
		 AtlasDriverUtility.customWait(6);
	/*	AtlasDriverUtility.waitUntilElementVisible(
				tagsPageElements.parentTagSelectionField, 50);*/
		

		List<WebElement> parentTags = tagsPageElements.parentTagSelectionField
				.findElements(By.cssSelector(".ng-binding"));
		AtlasDriverUtility.customWait(3);
		if (parentTags.size() > 0) {
			AtlasDriverUtility.customWait(3);
			tagName = parentTags.get(0).getText();
			enterTagName(tagName);

			webElement.clearAndSendKeys(tagsPageElements.addAttributeName,
					"AutomationTest");
			saveTagName();
		} else {
			LOGGER.warn("No existing tags to create.");
		}
	}

	public boolean validateParentTag(String parentTagName) {
		boolean isTagDisplayed = false;
		for (WebElement we : tagsPageElements.options) {
			if (we.getText().equals(parentTagName))
				isTagDisplayed = true;
		}
		return isTagDisplayed;
	}

	public String getNotificationMessage() {
		AtlasDriverUtility.customWait(6);
		return tagsPageElements.notificationBanner.getText();
	}

	public void deleteAttribute() {

		/*
		 * AtlasDriverUtility.waitUntilElementVisible(
		 * tagsPageElements.addAttributeButton, 50);
		 * webElement.click(tagsPageElements.addAttributeButton);
		 */
		Assert.assertEquals(
				webElement.isElementExists(tagsPageElements.removeAttribute),
				true);
		// webElement.isElementExists(tagsPageElements.removeAttribute);

	}

	public void clickOnDeleteAttribute() {
		AtlasDriverUtility.waitUntilElementVisible(
				tagsPageElements.removeAttribute, 50);
		webElement.click(tagsPageElements.removeAttribute);

	}

	public void validateAttributeEnable() {
		// String tagNameValue;
		// tagsPageElements.tagNameTextField.sendKeys("testing1");
		// webElement.clearAndSendKeys(tagsPageElements.tagNameTextField,
		// "testing1");

		tagsPageElements.tagNameTextField.clear();
		Assert.assertEquals(webElement
				.isElementEnabled(tagsPageElements.addAttributeButton), false);
	}
	
	
	public String get_SelectedOption(WebElement element) {
		Select select = new Select(element);
		WebElement selectedElement = select.getFirstSelectedOption();
		
		String selectedOption = selectedElement.getText();
		return selectedOption;
	}
	
	public void autoRefreshParentTag(){
		
		String uniqueID = UUID.randomUUID().toString();
		
		
		System.out.println("unique id:" + uniqueID);
		webElement
		.clearAndSendKeys(tagsPageElements.tagNameTextField, uniqueID);

		webElement.click(tagsPageElements.saveButton);
		
		//Selecting uniqueID from the dropdown using text
		
		WebElement element = tagsPageElements.parentTagSelectionField;
		
		Select options=new Select(element);
		
		try{
			customWait(5);
		System.out.println(options.getAllSelectedOptions());	
			
			options.selectByValue(uniqueID);
		}
		
		catch (NoSuchElementException e) {
            System.out.println("Option value not find in dropdown");
        
        }
			
		//Verifying if selected value is displaying or not.
		Assert.assertEquals(uniqueID, get_SelectedOption(element), "Selected Value not displaying");
		
		
		
		
	}



	public void notificationDisappear() {
		webElement.click(tagsPageElements.tagNameTextField); // Highlighting
																// 'Tag Name'
																// field

		Assert.assertEquals(webElement
				.isElementEnabled(tagsPageElements.notificationBanner), false);
	}
	
	
	public void webElementTextCollection(){
	
		
		 list = new ArrayList<String>();
		
		 
		
		 LOGGER.info(tagsPageElements.apachelogo_Label.getText());
		 LOGGER.info(tagsPageElements.searchTab_Label.getText());
		 LOGGER.info(tagsPageElements.tagTabLink_Label.getText());
		 LOGGER.info(tagsPageElements.helpLink_Label.getText());
		 LOGGER.info(tagsPageElements.aboutLink_Label.getText());
		 LOGGER.info(tagsPageElements.tagPageHeader_Label.getText());
		 LOGGER.info(tagsPageElements.tagName_Label.getText());
		 LOGGER.info(tagsPageElements.parentTag_Label.getText());
		 
		 LOGGER.info(tagsPageElements.addAttribute_Label.getText());
		 LOGGER.info(tagsPageElements.save_Label.getText());
		 LOGGER.info(tagsPageElements.AttributeName_Label.getText());
		 
		 String arr[]= {tagsPageElements.apachelogo_Label.getText(), tagsPageElements.searchTab_Label.getText(),
				 tagsPageElements.tagTabLink_Label.getText(),	 tagsPageElements.helpLink_Label.getText(),
				 tagsPageElements.aboutLink_Label.getText(),tagsPageElements.tagPageHeader_Label.getText(), 
				 tagsPageElements.tagName_Label.getText(), tagsPageElements.parentTag_Label.getText(),
				 tagsPageElements.addAttribute_Label.getText(), tagsPageElements.save_Label.getText(),
				 tagsPageElements.AttributeName_Label.getText()
		 };
		 
		
		 for(int i=0;i<arr.length; i++){
			 customWait(4);
			 list.add(arr[i]);
			 
		 }
		 
		
			/*customWait(5);
		list.add(tagsPageElements.apachelogo_Label.getText());
		customWait(2);
		list.add(tagsPageElements.searchTab_Label.getText());
		customWait(2);
		list.add(tagsPageElements.tagTabLink_Label.getText());
		customWait(2);
		list.add(tagsPageElements.helpLink_Label.getText());
		customWait(2);
		list.add(tagsPageElements.aboutLink_Label.getText());
		customWait(2);
		list.add(tagsPageElements.tagPageHeader_Label.getText());
		customWait(2);
		
		list.add(tagsPageElements.tagName_Label.getText());
		customWait(2);
		list.add(tagsPageElements.parentTag_Label.getText());
		customWait(2);
		
		list.add(tagsPageElements.addAttribute_Label.getText());
		customWait(2);
		list.add(tagsPageElements.save_Label.getText());
		customWait(2);
		list.add(tagsPageElements.AttributeName_Label.getText());
		customWait(2);*/
		
		System.out.println(list);
		
	}
	
	public Boolean checkTokenExist(String token){
		LOGGER.info(list);
		for(String str: list) {
		    if(str.trim().contains(token))
		       return true;
		}
		return false;	
		
	}
	

	@DataProvider(name = AtlasConstants.SPELL_CHECKER)
	public static Iterator<Object[]> spellcheckerDataProvider(ITestContext context) {
		// Get the input file path from the ITestContext
		
		
		String inputFile = context.getCurrentXmlTest().getParameter(
				"spellchecker");
		return AtlasFileUtils.getData(inputFile);
	}
	
	
	

}
