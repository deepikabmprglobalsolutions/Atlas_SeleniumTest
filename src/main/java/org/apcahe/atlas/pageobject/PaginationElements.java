package org.apcahe.atlas.pageobject;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PaginationElements {

	
	@FindBy(css = "button[type='submit']")
	public WebElement submit;
	
	@FindBy(xpath = "//*[contains(@ng-class, 'noNext')]")
	public WebElement noNext1;
	
	@FindBy(xpath = "//*[contains(@ng-class, 'noPrevious')]")
	public WebElement noPrevious1;
	
	@FindBy(xpath = "//li[@class='ng-scope']/a[@ng-click='selectPage(page + 1)']")
	public WebElement next;
	
	@FindBy(xpath = "//*[contains(@ng-class, 'noNext')]")
	public WebElement noNext;
	
	@FindBy(xpath = "//*[contains(@ng-class, 'noPrevious')]")
	public WebElement noPrevious;
	
	@FindBy(css = ".datatable")
	public List<WebElement> lastPageTable;
	
	/*@FindBy(className = "tabsearchResult")
	public WebElement result;*/
	
	@FindBy(className = "tabsearch-result")
	public WebElement result;
	
	
	/*@FindBy(css = "input[type='text']")
	public WebElement wb;
	*/
	
	/*@FindBy(xpath = "//div/input[@placeholder='Search: Table, DB, Column']")
	public WebElement wb;*/
	
	@FindBy(xpath = "//div/input[@data-ng-model='query']")
	public WebElement wb;
	
	@FindBy(css = "tr")
	public WebElement singleRow;
	
	
}
