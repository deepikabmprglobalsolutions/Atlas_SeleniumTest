package org.apache.atlas.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MiscUtils {

	public static String getDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd HH mm ss");
		Calendar cal = Calendar.getInstance();
		String date = dateFormat.format(cal.getTime());
		return date.replaceAll(" ", "");
	}
}
