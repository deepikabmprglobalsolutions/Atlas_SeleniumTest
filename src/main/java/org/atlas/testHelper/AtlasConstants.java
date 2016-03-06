package org.atlas.testHelper;

public class AtlasConstants {

	public final static int PAGELOAD_TIMEOUT_THRESHOLD = 10;
	public final static int DRIVER_TIMEOUT = 30;
	
	public static long START_TIME;
	
	public static String UI_URL;

	public final static String GUID = "guid";
	public final static String LINEAGE_DATA = "lineageData";
	public final static String SEARCH_STRING = "searchDataFeed";
	public final static String INVALID_SEARCH_STRING = "invalidSearchDataFeed";
	public final static String SEARCH_TABLE_HEADERS = "searchTableHeaders";
	
	public final static String BUTTON_ATTRIBUTE_AS_DISBALED = "disabled";
	public final static String PWD = System.getProperty("user.dir");
	public final static String SCREENSHOTS_DIR = PWD + "\\test-output\\screenshots\\";
	public final static String REPORT_FILE_PATH = PWD + "\\test-output\\emailable-report.html";
	public final static String CHROME_DRIVER_PATH = PWD + "\\src\\test\\resource\\chromedriver.exe";
	
	public static final String LINK_CLICKED = "link clicked";
	public static final String BROWSER_TYPE = "firefox";
	
	public static final String SPELL_CHECKER="atlasspellchecker";
	
}
