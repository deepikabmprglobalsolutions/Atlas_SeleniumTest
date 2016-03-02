package org.atlas.ui.pages;

import java.util.ArrayList;

import org.apache.atlas.utilities.AtlasDriverUtility;
import org.apcahe.atlas.pageobject.AboutDialogElements;
import org.openqa.selenium.support.PageFactory;

public class AboutPage extends HomePage {

	AboutDialogElements aboutDialogElements;
	private String aboutDialogTitle;

	ArrayList<String> actualData = new ArrayList<String>();
	
	public AboutPage() {
		aboutDialogElements = PageFactory.initElements(driver,
				AboutDialogElements.class);
	}

	public String getAboutDialogTitle() {
		return aboutDialogTitle;
	}

	public void handlePopup() {
		AtlasDriverUtility.customWait(5);
		aboutDialogElements.aboutDialog.click();
		aboutDialogTitle = aboutDialogElements.aboutDialogHeader.getText();
		String versionText = aboutDialogElements.version.getText();
		
		actualData.add(aboutDialogTitle);
		actualData.add(versionText);
	}
}