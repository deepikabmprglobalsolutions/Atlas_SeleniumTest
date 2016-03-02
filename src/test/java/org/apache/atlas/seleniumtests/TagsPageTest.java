package org.apache.atlas.seleniumtests;

import org.apache.atlas.objectwrapper.WebDriverWrapper;
import org.apache.log4j.Logger;
import org.atlas.testHelper.AtlasConstants;
import org.atlas.ui.pages.TagsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

public class TagsPageTest extends WebDriverWrapper {

	private static final Logger LOGGER = Logger.getLogger(TagsPageTest.class);
	private TagsPage tagsPage = null;
	long testExecutionStartTime;

	@BeforeClass(description = "TagsPage Test Setup")
	public void loadTagsTest(XmlTest config){
		tagsPage = new TagsPage();
		tagsPage.launchApp();
	}
	
	@BeforeMethod
	public void beforeMethod(){
		AtlasConstants.START_TIME = System.currentTimeMillis();
	}
	
	@Test
	public void testPageElementsFromTagsTab() {
		LOGGER.info("STARTED: Test testPageElementsFromTagsTab");
		homePage.verifyPageLoadSuccessfully();
		LOGGER.info("ENDED: Test testPageElementsFromTagsTab");
	}
	
	@Test
	public void validateTagsPage(){
		LOGGER.info("STARTED: Test validateTagsPage");
		tagsPage.navigateToTagsTab();
		boolean isTagsSectionExist = tagsPage.validateTagsSections();
		Assert.assertTrue(isTagsSectionExist, "Validating tags page sections");
		LOGGER.info("ENDED: Test validateTagsPage");
	}
	
	@Test
	public void createTagWithAttribute(){
		LOGGER.info("STARTED: Test Create Attribute");
		tagsPage.navigateToTagsTab();
		tagsPage.
		enterTagName("TestInput").
		selectParentTag("Dimension").
		addAddtribute().
		enterAttributeName("TestAttr").
		saveTagName();
		LOGGER.info("ENDED: Test Create Attribute");
	}
	
	@Test
	public void duplicateTagName(){
		LOGGER.info("STARTED: Test duplicateTagName");
		tagsPage.createExistingTag();
		String actualMsg = tagsPage.getNotificationMessage().trim();
		Assert.assertEquals(actualMsg, "Cannot redefine type " + tagsPage.getTagName());
		LOGGER.info("ENDED: Test duplicateTagName");
	}
	
	@Test 
	public void addFunctionalTestTag(){
		LOGGER.info("STARTED: Test add Tag");
		tagsPage.navigateToTagsTab();
		tagsPage.enterTagName("FunctionalTestTag").
		saveTagName();
		LOGGER.info("ENDED: Test add Tag");
	}
	
	@Test
	public void validateFunctionalTestTag(){
		LOGGER.info("STARTED: Test validateFunctionalTestTag");
		tagsPage.navigateToTagsTab();
		Assert.assertTrue(tagsPage.validateParentTag("FunctionalTestTag"), "Validating tag in tags page sections");
		LOGGER.info("ENDED: Test validateFunctionalTestTag");
	}
}
