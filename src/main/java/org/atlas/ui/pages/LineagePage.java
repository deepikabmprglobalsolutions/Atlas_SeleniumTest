package org.atlas.ui.pages;

import java.util.HashMap;
import java.util.List;

import org.apache.atlas.utilities.AtlasDriverUtility;
import org.apcahe.atlas.pageobject.LineagePageElements;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.log4testng.Logger;

public class LineagePage extends HomePage {

	private static Logger LOGGER = Logger.getLogger(LineagePage.class);

	LineagePageElements lineagePageElements;
	SearchPage searchPage;

	public LineagePage() {
		searchPage = new SearchPage();
		lineagePageElements = PageFactory.initElements(driver,
				LineagePageElements.class);
	}

	public boolean validateBackToPageLink() {
		boolean isLinkEnabled = false;
		if (webElement.isElementExists(lineagePageElements.backToResultLink)) {
			isLinkEnabled = webElement
					.isElementEnabled(lineagePageElements.backToResultLink);
		} else {
			LOGGER.error("Back to Result link doesn't exist");
		}
		return isLinkEnabled;
	}

	public void clickOnBackToPageLink() {
		AtlasDriverUtility.customWait(5);
		if (webElement.isElementExists(lineagePageElements.backToResultLink)) {
			lineagePageElements.backToResultLink.click();
		}
		AtlasDriverUtility.customWait(5);
	}

	public boolean isPageDataDisplayed() {
		boolean isTagDetailsExists = false;
		AtlasDriverUtility.waitForPageLoad(driver, 10);
		if (webElement.isElementExists(lineagePageElements.tagDetailsSection)) {
			isTagDetailsExists = webElement
					.isElementEnabled(lineagePageElements.tagDetailsSection);
		}
		return isTagDetailsExists;
	}

	public boolean validateGraphSection() {
		AtlasDriverUtility.waitForPageLoad(driver, 10);
		AtlasDriverUtility.customWait(10);
		boolean isElementsLoadedProperly = false;
		WebElement graphSection = lineagePageElements.graphSection;
		if (webElement.isElementExists(graphSection)) {
			String resetButtonText = graphSection.findElement(
					By.tagName("button")).getText();
			if (resetButtonText != null && resetButtonText.equals("Reset")) {
				isElementsLoadedProperly = true;
			}
			if (!webElement
					.isElementExists(lineagePageElements.noLineageDataFound)) {
				List<WebElement> graphTags = graphSection.findElements(By
						.tagName("g"));
				int imgCount = 0;
				if (graphTags.size() > 0) {
					try {
						for (WebElement gTag : graphTags) {
							gTag.findElement(By.tagName("img"));
							imgCount++;
						}
					} catch (NoSuchElementException nsee){
					}
					isElementsLoadedProperly = (imgCount > 0) ? isElementsLoadedProperly = true
							: false;
				}
			}
		} else {
			LOGGER.error("Perhaps graph section not loaded");
		}
		return isElementsLoadedProperly;
	}

	public void goToLineagePageFor(String nameToLineage) {
		String name = nameToLineage.substring(0, nameToLineage.indexOf(":"));
		String lineageName = nameToLineage.substring(nameToLineage.indexOf(":") + 1,
				nameToLineage.length());
		searchPage.searchQuery(name);
		clickOnSearchData(lineageName);
		AtlasDriverUtility.customWait(5);
	}

	public static boolean isPreviousButtonDisabled = false;
	public static boolean isNextButtonDisabled = false;
	public static boolean isPreviousButtonEnabled = false;
	public static boolean isNextButtonEnabled = false;
	boolean isTagFound = false;

	public void clickOnSearchData(String tagName) {
		AtlasDriverUtility.customWait(5);
		if (webElement.isElementExists(lineagePageElements.paginationBoard)) {
			List<WebElement> paginationFields = lineagePageElements.paginationBoard
					.findElements(By.tagName("li"));
			int size = paginationFields.size();
			for (int anchorTagIndex = 1; anchorTagIndex < size - 1; anchorTagIndex++) {
				WebElement listItem = paginationFields.get(anchorTagIndex);
				if (listItem.getAttribute("class").contains("active")) {
					if (paginationFields.get(anchorTagIndex - 1).getText()
							.equals("Previous")) {
						isPreviousButtonDisabled = !lineagePageElements.paginationPrevious
								.isEnabled();
						isNextButtonDisabled = lineagePageElements.paginationNext
								.isEnabled();
					}
					if (!isTagFound) {
						if (paginationFields.get(size - 1).getText()
								.equals("Next")) {
							isPreviousButtonEnabled = lineagePageElements.paginationPrevious
									.isEnabled();
							boolean nextLinkEnabled = lineagePageElements.paginationNext
									.isEnabled();
							isNextButtonEnabled = isTagFound ? nextLinkEnabled
									: !nextLinkEnabled;
						}
					}
				}
				getAllTagsFromSearchResultTable();
				searchTableForTag(tagName);
				if (isTagFound) {
					nameToElement = null;
					nameToElement = new HashMap<String, WebElement>();
					isTagFound = false;
					break;
				}
				WebElement nextPage = listItem.findElement(By.tagName("a"));
				nextPage.click();
				AtlasDriverUtility.waitUntilPageRefresh(driver);
			}
		} else {
			LOGGER.error("Pagination is not displayed");
		}
	}

	public HashMap<String, WebElement> nameToElement = new HashMap<String, WebElement>();

	private void getAllTagsFromSearchResultTable() {
		List<WebElement> tableRows = lineagePageElements.resultTable
				.findElements(By.tagName("tr"));
		LOGGER.info("Search result count: " + tableRows.size());

		for (int index = 1; index < tableRows.size(); index++) {
			List<WebElement> tableCellData = tableRows.get(index).findElements(
					By.tagName("td"));
			WebElement firstCol = tableCellData.get(0).findElement(
					By.tagName("a"));
			String key = firstCol.getText();
			nameToElement.put(key, firstCol);
		}
	}

	private boolean searchTableForTag(String tagName) {
		if (nameToElement != null && nameToElement.containsKey(tagName)) {
			nameToElement.get(tagName).click();
			isTagFound = true;
			AtlasDriverUtility.customWait(3);
		} else {
			LOGGER.error("Map 'nameToElement:' "+nameToElement+ " doesn't contain key: " + tagName);
		}
		return isTagFound;
	}

	public boolean clickOnAllTabs() {
		List<WebElement> allTabs = lineagePageElements.tabs;
		boolean allTabsClicked = false;
		int tabsSize = allTabs.size();
		int tabsClicked = 0;
		for (WebElement tab : allTabs) {
			tab.click();
			tabsClicked++;
			AtlasDriverUtility.customWait(2);
		}
		if (tabsSize == tabsClicked) {
			allTabsClicked = true;
		}
		return allTabsClicked;
	}
}
