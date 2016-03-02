package org.atlas.ui.pages;

import java.util.List;

import org.apache.atlas.utilities.AtlasDriverUtility;
import org.apache.log4j.Logger;
import org.apcahe.atlas.pageobject.PaginationElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class Pagination extends AtlasDriverUtility {

	public int Total = 0;
	public int no_of_rows;
	
	public int no_of_Pages = 1;
	public boolean firstPageValidation;
	public boolean MiddlePageValidation;
	public boolean lastPageValidation;
	public boolean row_PerPage;
	public int no_of_rows_exceptLastPage;
	private static final Logger LOGGER = Logger.getLogger(Pagination.class);

	public PaginationElements paginationElements;
	//public int no_of_boxes;

	public Pagination() {
		paginationElements = PageFactory.initElements(driver,
				PaginationElements.class);
	}

	public void getResultLabel() {

		String result;
		AtlasDriverUtility.waitForPageLoad(driver, 320);
		AtlasDriverUtility.waitUntilElementVisible(
				paginationElements.wb, 50);
		
		WebElement e= paginationElements.wb;
		
		e.sendKeys("Fact");

		paginationElements.submit.click();
		
		AtlasDriverUtility.waitUntilElementVisible(
				paginationElements.result, 50); 
		
		 result=	paginationElements.result.getText();
		
		
		System.out.println("String is:" + result);

		String arr[] = result.split(" ", 2);

		String firstWord = arr[0]; // First String
		String theRest = arr[1]; // Remaining String

		 no_of_rows = Integer.parseInt(firstWord);
		System.out.println("Number of rows in Table:" + no_of_rows);

		//return no_of_rows;
	}

	public void paginationFunction() {

		if (paginationElements.noNext1.getAttribute("class").toString()
				.contains("disabled") == true
				&& paginationElements.noPrevious1.getAttribute("class")
						.toString().contains("disabled") == true) // Condition
																	// to check
																	// whether
																	// result
																	// has
																	// single
																	// page or
																	// not.
		{
			System.out.println("There is only one Page");

		/*	List<WebElement> rowCount2 = driver.findElements(By
					.cssSelector(".datatable"));*/
			
			List<WebElement> rowCount2 =paginationElements.lastPageTable;

			
			AtlasDriverUtility.waitForPageLoad(driver, 320);

			for (WebElement row : rowCount2) {

				customWait(1);
				int cellsInRow = row.findElements(By.cssSelector("tr")).size() - 1;

				no_of_rows_exceptLastPage = cellsInRow;
				Total = cellsInRow + Total;
				System.out.println("No of rows, where we have only one page:"
						+ cellsInRow);
			}

			firstPageValidation = true;
			MiddlePageValidation = true;
			lastPageValidation = true;
			row_PerPage=true;

		}

		else {

			/*
			 * WebElement next = wait
			 * .until(ExpectedConditions.elementToBeClickable(By
			 * .xpath("//li[@class='ng-scope']/a[@ng-click='selectPage(page + 1)']"
			 * )));
			 */

			while (paginationElements.noNext.getAttribute("class").toString()
					.contains("disabled") == false) {

				// on First Page Previous Button should disabled and next page
				// enabled.
				if (no_of_Pages == 1) {

					try {
						Assert.assertEquals(
								paginationElements.noPrevious
										.getAttribute("class").toString()
										.contains("disabled"), true);
						Assert.assertEquals(
								paginationElements.noNext.getAttribute("class")
										.toString().contains("disabled"), false);
						firstPageValidation = true;

						LOGGER.info("Previous Button is  Disabled on Page no: "
								+ no_of_Pages
								+ "=="
								+ paginationElements.noPrevious
										.getAttribute("class").toString()
										.contains("disabled"));
						LOGGER.info("Next Button is Disabled on Page No. : "
								+ no_of_Pages
								+ "=="
								+ paginationElements.noNext
										.getAttribute("class").toString()
										.contains("disabled"));
					}

					catch (AssertionError e) {
						firstPageValidation = false;
					}

				}

				else {
					// on Middle Page, Previous and Next button should be
					// enabled.

					try {

						Assert.assertEquals(
								paginationElements.noPrevious
										.getAttribute("class").toString()
										.contains("disabled"), false);
						Assert.assertEquals(
								paginationElements.noNext.getAttribute("class")
										.toString().contains("disabled"), false);

						MiddlePageValidation = true;

					}

					catch (AssertionError e) {
						MiddlePageValidation = false;
					}

				}

				/*List<WebElement> rowCount1 = driver.findElements(By
						.cssSelector(".datatable"));*/
				
				List<WebElement> rowCount1 =	paginationElements.lastPageTable;

				try {
					Thread.sleep(4000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				for (WebElement row : rowCount1) {

					int cellsInRow = row.findElements(By.cssSelector("tr"))
							.size() - 1;

					no_of_rows_exceptLastPage = cellsInRow; // Storing number of
															// rows per page
															// Value
															// in variable
															// except
															// last page. In
															// future,
															// there can be
															// chances
															// that each Page
															// can
															// have other than
															// 10
															// rows.
					Total = cellsInRow + Total;
					System.out
							.println("No of rows in each Page except Last Page"
									+ cellsInRow);

					try {

						Assert.assertEquals(10, +cellsInRow); // checking
																// eachPage
																// Should have
																// 10
																// rows. If it
																// Success,
																// boolean
																// variable set
																// to
																// true else set
																// to
																// false.
						row_PerPage = true;

					} catch (AssertionError e) {
						row_PerPage = false;
					}

				}
				
				AtlasDriverUtility.waitUntilElementVisible(
						paginationElements.next, 50); 

				paginationElements.next.click();
				no_of_Pages++;

			}

			// below for loop to get number of rows on last Page.
			// List<WebElement> rowCount2 = driver.findElements(By
			// .cssSelector(".datatable"));

			List<WebElement> rowCount2 =  paginationElements.lastPageTable;
			
			/*List<WebElement> rowCount2 = driver.findElements(By
					.cssSelector(".datatable"));*/

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			for (WebElement row : rowCount2) {
				AtlasDriverUtility.waitForPageLoad(driver, 320);
				
				AtlasDriverUtility.waitUntilElementVisible(
						paginationElements.singleRow, 50); 
				

				int cellsInRow = row.findElements(By.cssSelector("tr")).size() - 1;

				System.out
						.println("Number of Rows in Last Page :" + cellsInRow);

				Total = cellsInRow + Total;

			}

			LOGGER.info("Number of rows in fINALTotal:" + Total);
			Assert.assertEquals(no_of_rows, Total);

			// On Last Page, Next button should be disabled.
			try {
				Assert.assertEquals(
						paginationElements.noPrevious.getAttribute("class")
								.toString().contains("disabled"), false);
				Assert.assertEquals(
						paginationElements.noNext.getAttribute("class")
								.toString().contains("disabled"), true);
				lastPageValidation = true;

				LOGGER.info("Previous Button is Disabled on Page No. (Last Page): "
						+ no_of_Pages
						+ "=="
						+ paginationElements.noPrevious.getAttribute("class")
								.toString().contains("disabled"));

				LOGGER.info("Next Button is Disabled on Page No. (Last Page) "
						+ no_of_Pages
						+ "=="
						+ paginationElements.noNext.getAttribute("class")
								.toString().contains("disabled"));

			} catch (AssertionError e) {
				lastPageValidation = false;
			}

		//	LOGGER.info("ENDED: Validate_NumberOfRows");

		}

	}

}
