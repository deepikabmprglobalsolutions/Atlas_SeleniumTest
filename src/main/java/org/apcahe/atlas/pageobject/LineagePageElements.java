package org.apcahe.atlas.pageobject;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class LineagePageElements extends HomePageElements {

	@FindBy(xpath = "//*[contains(text(), 'Back To Result')]")
	public WebElement backToResultLink;

	@FindBy(xpath = "//*[@role='tabpanel']")
	public WebElement tagDetailsSection;

	@FindBy(xpath = "//*[contains(@class, 'fa-spinner')]")
	public WebElement graphSpinner;

	@FindAll({ @FindBy(id = "lineageGraph"),
			@FindBy(xpath = "//*[@data-ng-controller='Lineage_ioController']") })
	public WebElement graphSection;

	@FindBy(xpath = "//*[contains(text(), 'No lineage data found')]")
	public WebElement noLineageDataFound;
	
	@FindBy(xpath = "//*[@class='ng-isolate-scope']//li//a")
	public List<WebElement> tabs;
	
	@FindBy(xpath = "//*[@ng-click='select()'][contains(text(), 'Tags')]")
	public WebElement tagsTab;
	
	@FindBy(xpath = "//*[contains(text(), 'Add Tag')]")
	public WebElement addTagButton;
}
