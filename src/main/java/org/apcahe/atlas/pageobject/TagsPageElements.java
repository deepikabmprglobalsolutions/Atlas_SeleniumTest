package org.apcahe.atlas.pageobject;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TagsPageElements extends HomePageElements {

	@FindBy(css = "a[href='#!/tags']")
	public WebElement tagTabLink;

	/*
	 * @FindBy(css = ".appForm h4") public WebElement tagPageHeader;
	 */

	@FindBy(css = ".app-form h4")
	public WebElement tagPageHeader;

	@FindBy(css = ".control-label")
	public List<WebElement> labels;

	@FindBy(id = "typeName")
	public WebElement tagNameTextField;

	@FindBy(id = "ParentTag")
	public WebElement parentTagSelectionField;

	@FindBy(className = "addAttr")
	public WebElement addAttributeButton;

	@FindBy(id = "attributeId_0")
	public WebElement addAttributeName;

	@FindBy(css = ".saveAttr")
	public WebElement saveButton;

	@FindBy(css = "span[class='ng-binding ng-scope']")
	public WebElement notificationBanner;

	@FindBy(xpath = ".//*[@id='ParentTag']/option")
	public List<WebElement> options;

	@FindBy(className = "remove")
	public WebElement removeAttribute;

	/*@FindBy(xpath = "//div[@data-close='close(notification)")
	public WebElement notification;*/
	
	
	//Web Element For Spell Checker...
	
	@FindBy(className ="main-logo")
	public WebElement apachelogo_Label;
	
	@FindBy(css = "a[data-ui-sref='search()'][class='menu-link']")
	public WebElement searchTab_Label;
	
	@FindBy(css = "a[href='#!/tags']")
	public WebElement tagTabLink_Label;
	
	@FindBy(xpath = "//*[contains(text(), 'Help')]")
	public WebElement helpLink_Label;
	

	@FindBy(xpath = "//*[contains(text(), 'About')]")
	public WebElement aboutLink_Label;
	
	@FindBy(css = ".app-form h4")
	public WebElement tagPageHeader_Label;
	
	@FindBy(xpath ="//label[@for='typeName']")
	public WebElement tagName_Label;
	
	
	@FindBy(xpath = "//label[@for='ParentTag']")
	public WebElement parentTag_Label;
	
	
	@FindBy(className = "addAttr")
	public WebElement addAttribute_Label;
	
	@FindBy(css = ".saveAttr")
	public WebElement save_Label;
	
	@FindBy(xpath="//label[@for='attributeId_0']")
	public WebElement AttributeName_Label;
	
	
	
	
	
}
