package org.apcahe.atlas.pageobject;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchPageElements extends HomePageElements {

	@FindBy(css = ".mainSearch")
	public WebElement mainSearch;
	
	/*@FindBy(css = "a[data-ui-sref='search()'][class='menulink']")
	public WebElement searchTab;*/
	
	@FindBy(css = "a[data-ui-sref='search()'][class='menu-link']")
	public WebElement searchTab;
	
	
/*	@FindBy(css = "input[type='text']")
	public WebElement searchBox;*/
	
	@FindBy(xpath = "//div/input[@placeholder='Search: Table, DB, Column']")
	public WebElement searchBox;

	@FindBy(css = "button[type='submit']")
	public WebElement searchIcon;

	/*@FindBy(css = ".tabsearchResult")
	public WebElement resultCount;*/
	
	@FindBy(css = ".tabsearch-result")
	public WebElement resultCount;

	@FindBy(css = ".alert")
	public WebElement noResultFound;

/*	@FindBy(css = ".mainTags")
	public WebElement tagsSection;*/

	@FindBy(css = ".main-tags")
	public WebElement tagsSection;
	
	@FindBy(css = ".datatable .tabsearchanchor")
	public List<WebElement> searchResultsTags;
	
	
	@FindBy(xpath = "//select[@ng-model='searchTypeModel']")
	public WebElement selectOnSearchPage;
	
	@FindBy(xpath= "//input[@placeholder='Search for Tags…']")
	public WebElement tagSearchOnSearchPage;
	
	@FindBy(xpath = "//img[@title='Refresh Tags']")
	public WebElement refreshTags;
	
	@FindBy(xpath = "//a[@ng-click='showMore()']")
	public WebElement loadMore;
	
	@FindBy(xpath = "//div[@data-ng-controller='navigationController']/div[@class='list-group']")
	public WebElement tagListInSearchPage;
}
