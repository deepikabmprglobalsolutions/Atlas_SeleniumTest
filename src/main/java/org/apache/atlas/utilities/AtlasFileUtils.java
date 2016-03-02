package org.apache.atlas.utilities;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AtlasFileUtils {

	public static Iterator<Object[]> getData(String param) {
		// Get a list of String file content (line items) from the test file.
		List<String> testData = getFileContentList(param);

		// We will be returning an iterator of Object arrays so create that
		// first.
		List<Object[]> dataToBeReturned = new ArrayList<Object[]>();

		// Populate our List of Object arrays with the file content.
		for (String userData : testData) {
			dataToBeReturned.add(new Object[] { userData });
		}
		// return the iterator - testng will initialize the test class and calls
		// the test method with each of the content of this iterator.
		return dataToBeReturned.iterator();
	}
	/**
	 * Utility method to get the file content, any version of this can be
	 * adapted, this is just one way of achieving the result.
	 * 
	 * @param filenamePath
	 * @return
	 */
	public static List<String> getFileContentList(String filenamePath) {
		InputStream is;
		List<String> lines = new ArrayList<String>();
		try {
			is = new FileInputStream(new File(filenamePath));
			DataInputStream in = new DataInputStream(is);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				lines.add(strLine);
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
}
