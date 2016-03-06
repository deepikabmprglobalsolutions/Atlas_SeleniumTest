package org.atlas.ui.pages;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.atlas.objectwrapper.AtlasTableObjectWrapper;
import org.apache.atlas.utilities.AtlasDriverUtility;
import org.apache.atlas.utilities.AtlasFileUtils;
import org.apache.log4j.Logger;
import org.apcahe.atlas.pageobject.SearchPageElements;
import org.atlas.testHelper.AtlasConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

public class SearchPage extends AtlasDriverUtility {

	private static final Logger LOGGER = Logger.getLogger(SearchPage.class);
	static WebDriver driver = getDriver();
	Select select;

	public SearchPageElements searchPageElements;

	public SearchPage() {
		searchPageElements = PageFactory.initElements(driver,
				SearchPageElements.class);
	}

	public void navigateToSearchTab() {
		customWait(10);
		searchPageElements.searchTab.click();
		waitForPageLoad(driver, 10);
	}

	public void searchQuery(String text) {
		navigateToSearchTab();
		customWait(1);
		webElement.clearAndSendKeys(searchPageElements.searchBox, text);
		searchPageElements.searchBox.sendKeys(Keys.ENTER);
		long startTime = System.currentTimeMillis();
		waitUntilPageRefresh(driver);
		pageLoadedTime(startTime, text + " query");
		customWait(2);
	}

	public int getSearchResultCount() {
		return new AtlasTableObjectWrapper(webElement,
				searchPageElements.resultTable).getRowCount();
	}

	public String[] getSearchResultTableHeaders() {
		return new AtlasTableObjectWrapper(webElement,
				searchPageElements.resultTable).getTableHeaders();
	}

	public boolean isTableDisplayed() {
		return webElement.isElementExists(searchPageElements.resultTable);
	}

	public HashMap<String, HashMap<String, WebElement>> nameToTagsMap = new HashMap<String, HashMap<String, WebElement>>();
	public HashMap<String, WebElement> nameToTagElements = null;
	public HashMap<String, WebElement> nameToElement = new HashMap<String, WebElement>();

	private void getAllTagsFromSearchResultTable() {
		List<WebElement> tableRows = searchPageElements.resultTable
				.findElements(By.tagName("tr"));
		LOGGER.info("Search result count: " + tableRows.size());

		for (int index = 1; index < tableRows.size(); index++) {
			List<WebElement> tableCellData = tableRows.get(index).findElements(
					By.tagName("td"));
			WebElement firstCol = tableCellData.get(0).findElement(
					By.tagName("a"));
			String key = firstCol.getText();
			if (tableCellData.size() > 2) {
				List<WebElement> tagsCol = tableCellData.get(3).findElements(
						By.tagName("a"));
				if (tagsCol.size() > 0) {
					nameToTagElements = new HashMap<String, WebElement>();
					for (WebElement tagsColDetails : tagsCol) {
						String tagName = tagsColDetails.getText();
						nameToTagElements.put(tagName, tagsColDetails);
					}
				}
				nameToTagsMap.put(key, nameToTagElements);
			} else {
				nameToElement.put(key, firstCol);
			}

		}
	}

	boolean isTagFound = false;

	public boolean searchFromTags(String expectedTag, boolean isResultExpected) {
		List<WebElement> listOfTags = searchPageElements.tagsSection
				.findElements(By.cssSelector(".list-group a"));
		boolean isExpectedTagFound = false;
		for (WebElement tag : listOfTags) {
			if (tag.getText().equals(expectedTag)) {
				tag.click();
				AtlasDriverUtility.waitUntilPageRefresh(driver);
				isExpectedTagFound = true;
				LOGGER.info("Expected tag found in tags section");
				break;
			}
		}
		if (!isExpectedTagFound) {
			LOGGER.error("Expected tag " + expectedTag
					+ " not found in tags section");
		}
		int resultantData = getSearchResultCount();
		if (resultantData > 0 && isResultExpected) {
			return true;
		}
		return isExpectedTagFound;
	}

	private void searchTableForTag(String tagName) {
		String name = tagName.substring(0, tagName.indexOf(":"));
		String tagNameToClick = tagName.substring(tagName.indexOf(":") + 1,
				tagName.length());
		if (nameToTagsMap != null && nameToTagsMap.containsKey(name)) {
			HashMap<String, WebElement> tagsMap = nameToTagsMap.get(name);
			WebElement element = tagsMap.get(tagNameToClick);
			element.click();
			isTagFound = true;
			waitForPageLoad(driver, 120);
			waitUntilPageRefresh(driver);
		}
	}

	public static boolean isPreviousButtonDisabled = false;
	public static boolean isNextButtonDisabled = false;

	public static boolean isPreviousButtonEnabled = true;
	public static boolean isNextButtonEnabled = true;

	public void clickOnTag(String tagName) {
		if (webElement.isElementExists(searchPageElements.paginationBoard)) {
			List<WebElement> paginationFields = searchPageElements.paginationBoard
					.findElements(By.tagName("li"));
			int size = paginationFields.size();
			for (int anchorTagIndex = 1; anchorTagIndex < size - 1; anchorTagIndex++) {
				WebElement listItem = paginationFields.get(anchorTagIndex);
				if (listItem.getAttribute("class").contains("active")) {
					if (paginationFields.get(anchorTagIndex - 1).getText()
							.equals("Previous")) {
						isPreviousButtonDisabled = !searchPageElements.paginationPrevious
								.isEnabled();
						isNextButtonDisabled = searchPageElements.paginationNext
								.isEnabled();
					}
					if (!isTagFound) {
						if (paginationFields.get(size - 1).getText()
								.equals("Next")) {
							isPreviousButtonEnabled = searchPageElements.paginationPrevious
									.isEnabled();
							boolean nextLinkEnabled = searchPageElements.paginationNext
									.isEnabled();
							if ((paginationFields.size()) == anchorTagIndex) {
								isNextButtonEnabled = !nextLinkEnabled;
							}
							isNextButtonEnabled = nextLinkEnabled;
						}
					}
				}
				getAllTagsFromSearchResultTable();
				searchTableForTag(tagName);
				if (isTagFound) {
					WebElement nextPage = listItem.findElement(By.tagName("a"));
					nextPage.click();
					break;
				}
				AtlasDriverUtility.waitUntilPageRefresh(driver);
			}
		}
	}

	public boolean validateSearchTagsTag(String tagsTagName) {

		
		boolean isTagDisplayed = false;
		customWait(1);
		for (WebElement we : searchPageElements.tagsSection.findElements(By
				.tagName("a"))) {
			customWait(1);
			if (we.getAttribute("title").equals(tagsTagName))
				isTagDisplayed = true;
		}
		return isTagDisplayed;
	}


	public void clickOnTool(String tagName) {
		if (webElement.isElementExists(searchPageElements.paginationBoard)) {
			List<WebElement> paginationFields = searchPageElements.paginationBoard
					.findElements(By.tagName("li"));
			int size = paginationFields.size();
			for (int anchorTagIndex = 1; anchorTagIndex < size - 1; anchorTagIndex++) {
				WebElement listItem = paginationFields.get(anchorTagIndex);
				if (listItem.getAttribute("class").contains("active")) {
					if (paginationFields.get(anchorTagIndex - 1).getText()
							.equals("Previous")) {
						isPreviousButtonDisabled = !searchPageElements.paginationPrevious
								.isEnabled();
						isNextButtonDisabled = searchPageElements.paginationNext
								.isEnabled();
					}
					if (paginationFields.get(size - 1).getText().equals("Next")) {
						isPreviousButtonEnabled = searchPageElements.paginationPrevious
								.isEnabled();
						boolean nextLinkEnabled = searchPageElements.paginationNext
								.isEnabled();
						isNextButtonEnabled = isTagFound ? nextLinkEnabled
								: !nextLinkEnabled;
					}
				}
				getAllToolsFromSearchResultTable(tagName);
				WebElement anchorTag = listItem.findElement(By.tagName("a"));
				anchorTag.click();
				AtlasDriverUtility.waitUntilPageRefresh(driver);
			}
		}
	}

	private void getAllToolsFromSearchResultTable(String colName) {
		List<WebElement> tableRows = searchPageElements.resultTable
				.findElements(By.tagName("tr"));
		for (int index = 1; index < tableRows.size(); index++) {
			List<WebElement> tableCellData = tableRows.get(index).findElements(
					By.tagName("td"));
			WebElement firstCol = tableCellData.get(0).findElement(
					By.tagName("a"));
			String key = firstCol.getText();
			if (key.equalsIgnoreCase(colName)) {
				if (tableCellData.size() > 2) {
					WebElement fifthCol = tableCellData.get(4).findElement(
							By.tagName("img"));
					fifthCol.click();
				}
			}
		}
		AtlasDriverUtility.customWait(5);
		handleModal();
	}

	private void handleModal() {
		WebElement parentDiv1 = driver.findElement(By
				.xpath("//*[@class='modal-content']"));
		parentDiv1.click();
		String toolTitle = parentDiv1.findElement(By.tagName("h4")).getText();
		Assert.assertEquals(toolTitle, "Add tag");
		WebElement selectTag = driver.findElement(By.id("tagDefinition"));
		selectTag.click();
		Select slt = new Select(selectTag);
		slt.selectByValue("FTest");
		AtlasDriverUtility.customWait(10);
		WebElement saveBtn = driver.findElement(By
				.xpath("//*[@class='btn btn-success']"));
		saveBtn.click();
		AtlasDriverUtility.customWait(10);
		// error msg is showing after clicking on save button
		String errorMsg = "trait=FTest is already defined for entity=566dedbc-496d-4552-a702-2249298ca761";
		if (errorMsg != null) {
			WebElement cancelBtn = driver.findElement(By
					.xpath("//*[@class='btn btn-warning']"));
			cancelBtn.click();
		}
	}
	
	
	
	
	@DataProvider(name = AtlasConstants.INVALID_SEARCH_STRING)
	public static String[][] invalidSearchDataProvider(ITestContext context) {
		Map<String, String> testParams = context.getCurrentXmlTest()
				.getLocalParameters();
		return new String[][] { {testParams.get("invalidSearchData")} };
	}
	
	@DataProvider(name = AtlasConstants.LINEAGE_DATA)
	public static String[][] backToResultLink(ITestContext context) {
		Map<String, String> testParams = context.getCurrentXmlTest()
				.getLocalParameters();
		return new String[][] { {testParams.get("validateLineage")} };
	}

	@DataProvider(name = AtlasConstants.SEARCH_STRING)
	public static Iterator<Object[]> fileDataProvider(ITestContext context) {
		// Get the input file path from the ITestContext
		String inputFile = context.getCurrentXmlTest().getParameter(
				"searchQueries");
		return AtlasFileUtils.getData(inputFile);
	}
	
	@DataProvider(name = AtlasConstants.GUID)
	public static Iterator<Object[]> guidDataProvider(ITestContext context) {
		// Get the input file path from the ITestContext
		String inputFile = context.getCurrentXmlTest().getParameter("guid");
		return AtlasFileUtils.getData(inputFile);
	}
	

	@DataProvider(name = AtlasConstants.SEARCH_TABLE_HEADERS)
	public static String[][] tableHeaders() {
		String[][] object = new String[][] { {"Name", "Description", "Owner",
				"Tags", "Tools" } };
		return object;
	}
	
	
	
	
	public int getSizeList(){
		AtlasDriverUtility.waitUntilElementVisible(
				searchPageElements.selectOnSearchPage, 50); 
		
		 select = new 
				Select(searchPageElements.selectOnSearchPage);
	
		 AtlasDriverUtility.waitForPageLoad(driver, 60);
		List<WebElement> select_size = select.getOptions();
		int size=select_size.size();
		return size;
	}
	
	
	public void validateselectOptions(){
	
		
		int size =getSizeList();
		
		for(int i=0; i<size ;i++){
		select.selectByIndex(i);
		
		AtlasDriverUtility.customWait(2);
		
		}
		
				
	}
	
	
	
	
	public boolean tagSearchFunctionalityInSearchPage(boolean verifytagfunctionality, String str){
		String[] Tagstable = null; 
		//String str;
		
		if(webElement.isElementExists(searchPageElements.tagListInSearchPage)){
			List<WebElement> listOfTags = searchPageElements.tagListInSearchPage.findElements(By.cssSelector("a"));
			Tagstable = new String[listOfTags.size()];
		int	counter=listOfTags.size();
			for(int index = 0; index < counter; index++){
				Tagstable[index] = listOfTags.get(index).getText();
				LOGGER.info("Tagstable[ "+index+" ] = " +Tagstable[index]);
				
				/*
				if((!Tagstable[index].toLowerCase().contains(str.toLowerCase())) && (!Tagstable[index].equals("Load more ..."))) {
					verifytagfunctionality=false;
				}*/
				

				if(Tagstable[index].toLowerCase().contains(str.toLowerCase())){
					
					verifytagfunctionality=true;
				}
				
				else if(Tagstable[index].equals("Load more ..."))  {
											verifytagfunctionality=true;
					
				}
				
				else
					verifytagfunctionality=false;
				
				if(index==listOfTags.size()-1 && Tagstable[index].equals("Load more ..."))
				{
					System.out.println("Click");
					searchPageElements.loadMore.click();
					customWait(1);
					
					 listOfTags = searchPageElements.tagListInSearchPage.findElements(By.cssSelector("a"));
					Tagstable = new String[listOfTags.size()];
					
					counter=listOfTags.size()-1;
					System.out.println("counter == "+counter);
					index=index-1;
					
					
				}
				
			
				
				
			}
			
		
			
		}
		
	
		
		return verifytagfunctionality;
		
		
	}
	
	
}
