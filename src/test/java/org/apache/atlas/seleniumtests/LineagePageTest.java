package org.apache.atlas.seleniumtests;

import org.apache.atlas.objectwrapper.WebDriverWrapper;
import org.apache.log4j.Logger;
import org.atlas.testHelper.AtlasConstants;
import org.atlas.ui.pages.LineagePage;
import org.atlas.ui.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

public class LineagePageTest extends WebDriverWrapper {

	private static final Logger LOGGER = Logger.getLogger(LineagePageTest.class);
	private LineagePage lineagePage = null;
	private SearchPage searchPage = null;
	long testExecutionStartTime;

	@BeforeClass(description = "LineagePage Test Setup")
	public void loadLineagePageTest(XmlTest config) {
		lineagePage = new LineagePage();
		searchPage = new SearchPage();
		lineagePage.launchApp();
	}
	
	@Test
	public void testPageElementsFromSearchPage() {
		LOGGER.info("STARTED: Test testPageElements from Lineage Page");
		homePage.verifyPageLoadSuccessfully();
		LOGGER.info("ENDED: Test testPageElements from Lineage Page");
	}	
	
/*	@Test(dataProvider = AtlasConstants.LINEAGE_DATA, dataProviderClass = SearchPage.class)
	public void validateBackToPageFunctionality(String query) {
		LOGGER.info("STARTED: validateBackToPageLink");
		lineagePage.goToLineagePageFor(query);
		Assert.assertEquals(lineagePage.validateBackToPageLink(), true, "BackToPage link enabled");
		lineagePage.clickOnBackToPageLink();
		Assert.assertEquals(searchPage.isTableDisplayed(), true,
				"Table Validation after navigating back from lineage page");
		LOGGER.info("ENDED: validateBackToPageLink");
	}
	
	@Test(dataProvider = AtlasConstants.GUID, dataProviderClass = SearchPage.class)
	public void validateLineagePage(String queryToGuid) {
		LOGGER.info("STARTED: validateLineagePage");
		lineagePage.goToLineagePageFor(queryToGuid);
		Assert.assertEquals(true, lineagePage.validateGraphSection(),
				"Graphs Section loaded");
		Assert.assertEquals(true, lineagePage.isPageDataDisplayed(),
				"Details section displayed");
		LOGGER.info("ENDED: validateLineagePage");
	}
	*/
	
	@Test(dataProvider = AtlasConstants.LINEAGE_DATA, dataProviderClass = SearchPage.class)
	public void validateLineagePageTabs(String query) {
		LOGGER.info("STARTED: validateLineagePageTabs");
		lineagePage
				.goToLineagePageFor(query);
		boolean allTabsClicked = lineagePage.clickOnAllTabs();
		Assert.assertTrue(allTabsClicked, "All tabs clicked in lineage page");
		LOGGER.info("ENDED: validateLineagePageTabs");
	}
	
}
