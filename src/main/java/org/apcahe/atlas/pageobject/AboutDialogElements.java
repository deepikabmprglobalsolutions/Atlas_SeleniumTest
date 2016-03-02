package org.apcahe.atlas.pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AboutDialogElements {

	@FindBy(xpath = "//*[@class='modal-content']")
	public WebElement aboutDialog;

	@FindBy(tagName = "h3")
	public WebElement aboutDialogHeader;

	@FindBy(xpath = "//*[@class='modal-body ng-scope']//div[2]")
	public WebElement version;
}
