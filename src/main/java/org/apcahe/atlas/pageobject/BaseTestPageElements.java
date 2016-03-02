package org.apcahe.atlas.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BaseTestPageElements {

	@FindBy(css = "div[data-ng-controller='HeaderController']")
	public WebElement headerController;

	/*@FindBy(css = ".mainLogo")
	public WebElement atlasLogo;*/
	
	@FindBy(css = ".main-logo")
	public WebElement atlasLogo;

/*	@FindBy(css = ".menuBar")
	public WebElement menuBar;*/
	

	@FindBy(css = ".menu-bar")
	public WebElement menuBar;
}
