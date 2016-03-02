package org.apache.atlas.objectwrapper;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AtlasTableObjectWrapper {

	WebElementWrapper webElement ;
	WebElement resultTable;
	
	public AtlasTableObjectWrapper(WebElementWrapper webElementWrapper, WebElement resultTableElement){
		webElement = webElementWrapper;
		this.resultTable = resultTableElement;
	}
	
	public int getRowCount(){
		int rowCount = 0;
		if(webElement.isElementExists(resultTable)){
			rowCount = resultTable.findElements(By.cssSelector("tr")).size() - 1;
		}
		return rowCount;
	}
	
	public String[] getTableHeaders(){
		String[] tableHeaders = null; 
		if(webElement.isElementExists(resultTable)){
			List<WebElement> listOfHeaders = resultTable.findElements(By.cssSelector("thead th"));
			tableHeaders = new String[listOfHeaders.size()];
			for(int index = 0; index < listOfHeaders.size(); index++){
				tableHeaders[index] = listOfHeaders.get(index).getText();
			}
		}
		return tableHeaders;
	}
	
	HashMap<String, Integer> colToIndex = new HashMap<String, Integer>();
	
	public void getColNameToIndex(){
		List<WebElement> tableHeaders = resultTable.findElements(By.tagName("th"));
		for(int index = 0; index < tableHeaders.size(); index++){
			WebElement header = tableHeaders.get(index);
			colToIndex.put(header.getText(), index);
		}
	}
}
