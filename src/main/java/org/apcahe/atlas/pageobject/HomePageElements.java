package org.apcahe.atlas.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePageElements {

	/*@FindBy(css = "div[data-ng-controller='HeaderController']")
	public WebElement headerController;*/

	@FindBy(className= "main-logo")
	public WebElement mainLogo_exist;
	
	/*@FindBy(css = ".mainLogo")
	public WebElement atlasLogo;*/
	
	@FindBy(css = ".main-logo")
	public WebElement atlasLogo;
	
	/*@FindBy(css = ".menuBar")
	public WebElement menuBar;*/
	
	@FindBy(css = ".menu-bar")
	public WebElement menuBar;
	
	@FindBy(xpath = "//*[contains(text(), 'Help')]")
	public WebElement helpLink;
	
	/*@FindBy(linkText = "Help")
	public WebElement helpLink1;*/
	
	@FindBy(xpath = "//*[contains(text(), 'OK')]")
	public WebElement OK;
	
	@FindBy(xpath = "//*[contains(text(), 'About')]")
	public WebElement aboutLink;
	
	
	@FindBy(css = ".datatable")
	public WebElement resultTable;

	@FindBy(css=".pull-right .pagination")
	public WebElement paginationBoard;
	
	@FindBy(xpath = "//*[contains(@ng-class, 'noPrevious')]")
	public WebElement paginationPrevious;

	@FindBy(xpath = "//*[contains(@ng-class, 'noNext')]")
	public WebElement paginationNext;
}
