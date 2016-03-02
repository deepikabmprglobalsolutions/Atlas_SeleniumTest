package org.apache.atlas.utilities;

import java.util.List;

import org.testng.ITestResult;
import org.uncommons.reportng.ReportNGUtils;

public class ReportUtils extends ReportNGUtils {

	public List<String> getTestOutput(ITestResult result) {

		List<String> output = super.getTestOutput(result);

		String screenshot = (String) result.getAttribute("screenshot");
		if (screenshot != null) {
			output.add("<a href=\"../" + screenshot + "\">Screenshot</a>");
		}

		return output;
	}
}
