package org.apache.atlas.seleniumtests;

import org.apache.atlas.objectwrapper.WebDriverWrapper;
import org.apache.atlas.utilities.AtlasDriverUtility;
import org.apache.log4j.Logger;
import org.atlas.testHelper.AtlasConstants;

import org.atlas.ui.pages.Pagination;
import org.atlas.ui.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

public class PaginationTest extends WebDriverWrapper {

	private static final Logger LOGGER = Logger.getLogger(PaginationTest.class);

	private Pagination pagination = null;

	@BeforeSuite(description = "Test setup")
	public void setup(XmlTest config) {
		setupConfig(config);
	}

	@BeforeClass(description = "Pagination Test Setup")
	public void loadPaginationTest(XmlTest config) {
		pagination = new Pagination();
		pagination.launchApp1();
	}

	@AfterSuite
	public void tearDown() {
		closeBrowser();

	}

	@Test(priority = 4)
	public void Validate_NumberOfRows() {
		
		LOGGER.info("Pagination Test Cases begins:");
		
		LOGGER.info("STARTED: Validate_NumberOfRows");
		AtlasDriverUtility.waitForPageLoad(driver, 320);

		 pagination.getResultLabel();

		AtlasDriverUtility.waitForPageLoad(driver, 320);
		pagination.paginationFunction();

		LOGGER.info("ENDED: Validate_NumberOfRows");

		
		

	}



	
	@Test(priority = 5)
	public void Validate_NeumericboxAtBottom() {
		AtlasDriverUtility.waitForPageLoad(driver, 320);
		
		int no_of_boxes;
		LOGGER.info("\nSTARTED: Validate_NeumericboxAtBottom");

		// Calculating number of boxed listed at bottom for pagination.
		if ((pagination.Total % pagination.no_of_rows_exceptLastPage) == 0) {
			no_of_boxes = pagination.Total
					/ pagination.no_of_rows_exceptLastPage;

		}

		else {
			no_of_boxes = pagination.Total
					/ pagination.no_of_rows_exceptLastPage;
			no_of_boxes = no_of_boxes + 1;
		}

		Assert.assertEquals(no_of_boxes, pagination.no_of_Pages);

		LOGGER.info("Number of Boxes=" + no_of_boxes);
		LOGGER.info("Number of Pages= " + pagination.no_of_Pages);

		LOGGER.info("ENDED: Validate_NeumericboxAtBottom");

	}

	@Test(priority = 6)
	public void Validate_FirstPagePagination() {
		AtlasDriverUtility.waitForPageLoad(driver, 320);
		LOGGER.info("\nSTARTED: Validate_FirstPagePagination");
		Assert.assertEquals(true, pagination.firstPageValidation);
		
		if(pagination.firstPageValidation==true)
		{
			LOGGER.info("Verified : Previous button is not enable on First Page");
		}
		
		LOGGER.info("ENDED: Validate_FirstPagePagination");

	}

	@Test(priority = 7)
	public void Validate_LastPagePagination() {
		AtlasDriverUtility.waitForPageLoad(driver, 320);
		LOGGER.info("\nSTARTED: Validate_FirstPagePagination");
		Assert.assertEquals(true, pagination.lastPageValidation);
		if( pagination.lastPageValidation == true)
			
		{
			LOGGER.info("Verified : Next button is not enable on Last Page");
		}
		
		LOGGER.info("ENDED: Validate_LastPagePagination");
	}

	@Test(priority = 8)
	public void MiddlePageOfPaginationVarification() {
		AtlasDriverUtility.waitForPageLoad(driver, 320);
		LOGGER.info("\nSTARTED: Varification of Middle Page");
		Assert.assertEquals(true, pagination.MiddlePageValidation);
		
		if(pagination.MiddlePageValidation == true){
			
			LOGGER.info("Verified : Next and Previous button are enable on Middle Page");

			
		}
		LOGGER.info("ENDED: Varification of Middle Page");

	}

	@Test(priority = 9)
	public void Validate_NumberOfRowsPerPage() {
		AtlasDriverUtility.waitForPageLoad(driver, 320);
		LOGGER.info("\nSTARTED: Validate_NumberOfRowsPerPage");
		Assert.assertEquals(true, pagination.row_PerPage);
		
		if(pagination.row_PerPage == true)
		{
			LOGGER.info("Verified : Each Page has 10 rows where total number of rows >= 10, except Last Page");
		}
		
		LOGGER.info("ENDED: Validate_NumberOfRowsPerPage");

	}

}
