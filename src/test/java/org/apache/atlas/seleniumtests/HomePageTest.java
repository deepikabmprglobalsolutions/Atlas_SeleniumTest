/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.atlas.seleniumtests;

import static org.atlas.testHelper.AtlasConstants.LINK_CLICKED;

import java.util.ArrayList;

import org.apache.atlas.objectwrapper.WebDriverWrapper;
import org.apache.atlas.utilities.AtlasDriverUtility;
import org.apache.atlas.utilities.UIAssert;
import org.apache.log4j.Logger;
import org.atlas.testHelper.Menu;
import org.atlas.ui.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.xml.XmlTest;

/**
 * Base class for test classes.
 */
public class HomePageTest extends WebDriverWrapper {

	private static final Logger LOGGER = Logger.getLogger(HomePageTest.class);

	private HomePage homePage = null;

	@BeforeSuite(description = "Test setup")
	public void setup(XmlTest config) {
		setupConfig(config);
	}

	@BeforeClass(description = "HomePage Test Setup")
	public void loadHomeTest(XmlTest config) {
		homePage = new HomePage();
		homePage.launchApp();
	}

	@AfterSuite
	public void tearDown() {
		//closeBrowser();
		/*
		 * SendReport.sendReportByEmail("fayazm@mprglobalsolutions.com",
		 * "Fayaz@786", "fayazm@mprglobalsolutions.com", "Test Excution Report",
		 * "");
		 */
		/*
		 * CopyReport cp = new CopyReport("/hw/atlas/TER/", new
		 * File(AtlasConstants.PWD + "\\test-output")); try {
		 * cp.saveFilesToServer("ananya@mprhost.com", "changeme12345"); } catch
		 * (IOException e) { e.printStackTrace(); }
		 */
	}

	
	
/*	@Test
	public void validateAtlasLogo() {
		LOGGER.info("STARTED: validateAtlasLogo");
		UIAssert.assertDisplayed(homePage.homePageElements.atlasLogo,
				"Atlas Logo");
		AtlasDriverUtility.waitForPageLoad(driver, 60);
		homePage.validateLogo();
		LOGGER.info("ENDED: validateAtlasLogo");
	}*/

	//Visibility of Atlas Logo on search Page
	
	  @Test(priority = 0)
	  public void validateLogoVisible() {
	  LOGGER.info("STARTED: validate AtlasLogo Visiblility on search Page");
	  AtlasDriverUtility.waitUntilElementVisible(
			  homePage.homePageElements.atlasLogo, 100);
	  
	  UIAssert.assertDisplayed(homePage.homePageElements.atlasLogo,
	  "Atlas Logo"); AtlasDriverUtility.waitForPageLoad(driver, 60);
	  homePage.validateLogoVisible();
	  LOGGER.info("ENDED: validate AtlasLogo Visibility"); }
	 

	@Test(priority = 1)
	public void validateMenuLinks() {
		LOGGER.info("STARTED: validatePageMenuBar");
		AtlasDriverUtility.waitForPageLoad(driver, 60);
		String[] actualMenuItems = homePage.getActualMenuLinksName();
		String[] expectedMenuItems = homePage.getExpectedPageMenuItems();
		Assert.assertEquals(actualMenuItems, expectedMenuItems,
				"ValidatePageMenuBar Items");
		LOGGER.info("ENDED: validatePageMenuBar");
	}

	@Test(priority = 2)
	public void validateHelpLink() {
		LOGGER.info("STARTED: validateHelpLink");
		AtlasDriverUtility.waitForPageLoad(driver, 60);
		
		Assert.assertTrue(homePage.validateLink(Menu.HELP.toString()),
				Menu.HELP.toString() + LINK_CLICKED);
		LOGGER.info("ENDED: validateHelpLink");
	}

	@Test(priority = 3)
	public void validateAboutLink() {
		LOGGER.info("STARTED: validateAboutLink");
		AtlasDriverUtility.waitForPageLoad(driver, 60);
		Assert.assertTrue(homePage.validateLink(Menu.ABOUT.toString()),
				Menu.ABOUT.toString() + LINK_CLICKED);
		homePage.homePageElements.OK.click();
		LOGGER.info("ENDED: validateAboutLink");
		AtlasDriverUtility.customWait(3);
		
	}
}