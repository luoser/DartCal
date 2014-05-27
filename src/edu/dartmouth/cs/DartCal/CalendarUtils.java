/**
 * DartCal
 * File: CalendarUtils.java
 * Author: Lisa Luo
 * Modified: 5/27/14
 * Description: Helper functions to help handle 
 * 	calendar and user data.
 */

package edu.dartmouth.cs.DartCal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

import android.graphics.Color;

// to help with parsing time from time periods
// and drawing blocks onto the calendar
public class CalendarUtils {
	
	/**
	 * Helper function to return a random color for assignment to friend.
	 * 
	 * @return
	 */
	public static int generateRandomColor() {

		Random rand = new Random();
		int r = rand.nextInt(255);
		int g = rand.nextInt(255);
		int b = rand.nextInt(255);

		int randomColor = Color.argb(100, r, g, b);

		return randomColor;
	}

	/**
	 * From 1970 epoch time in seconds to a formatted time string
	 * 
	 * @param timeInMs
	 * @param context
	 * @return time in a formatted string
	 */
	public static String parseTime(long timeInMs) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(timeInMs);
		SimpleDateFormat dateFormat;
		dateFormat = new SimpleDateFormat(Globals.TIME_FORMAT, Locale.US);
		System.out.println("parsetime function" + calendar.getTime());

		int unroundedMinutes = calendar.get(Calendar.MINUTE);
		int mod = unroundedMinutes % 15;
		calendar.add(Calendar.MINUTE, mod < 8 ? -mod : (15 - mod));
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return dateFormat.format(calendar.getTime());
	}
	
	/**
	 * Helper function to grab the day from the epoch time
	 * @param timeInMs
	 * @return int the day of the week, starting at Sunday = 1, Saturday = 7
	 */
	public static int parseDayOfWeek(long timeInMs){
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(timeInMs);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * From a formatted time string to a long (just time!)
	 * 
	 * @param timeString
	 * @return time in long
	 * @throws ParseException
	 */
	public static long timeBackToLong(String timeString) throws ParseException {

		SimpleDateFormat dateFormat;
		dateFormat = new SimpleDateFormat(Globals.TIME_FORMAT, Locale.US);

		System.out.println("df " + dateFormat.parse(timeString));
		System.out.println("df " + dateFormat.parse(timeString).getTime());

		// Time t = Time.valueOf(timeString);
		// System.out.println("t " + t);
		// long l = t.getTime();
		// System.out.println("l " + l);
		// return l;
		return dateFormat.parse(timeString).getTime();

	}

}
