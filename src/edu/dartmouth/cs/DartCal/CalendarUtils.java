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

		// modulus to round the inputted time to the nearest 15 minutes
		int unroundedMinutes = calendar.get(Calendar.MINUTE);
		int mod = unroundedMinutes % 15;
		calendar.add(Calendar.MINUTE, mod < 8 ? -mod : (15 - mod));
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return dateFormat.format(calendar.getTime());
	}

	/**
	 * Helper function to grab the day from the epoch time
	 * 
	 * @param timeInMs
	 * @return int the day of the week, starting at Sunday = 1, Saturday = 7
	 */
	public static int parseDayOfWeek(long timeInMs) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(timeInMs);
		System.out.println("parsedayofweek: "
				+ calendar.get(Calendar.DAY_OF_WEEK));
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * From a formatted time string to a long (just the time)
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

		return dateFormat.parse(timeString).getTime();

	}

	/**
	 * Helper function to translate long time into pixel location to draw block.
	 * 
	 * @param time
	 * @return where to draw said time
	 */
	public static int findTime(String time) {

		System.out.println("finding time..." + time);

		if (time.equals("7:00:00"))
			return Globals.TIME_7AM;
		if (time.equals("7:15:00"))
			return Globals.TIME_715AM;
		if (time.equals("7:30:00"))
			return Globals.TIME_730AM;
		if (time.equals("7:45:00"))
			return Globals.TIME_745AM;

		if (time.equals("8:00:00"))
			return Globals.TIME_8AM;
		if (time.equals("8:15:00"))
			return Globals.TIME_815AM;
		if (time.equals("8:30:00"))
			return Globals.TIME_830AM;
		if (time.equals("8:45:00"))
			return Globals.TIME_845AM;

		if (time.equals("9:00:00"))
			return Globals.TIME_9AM;
		if (time.equals("9:15:00"))
			return Globals.TIME_915AM;
		if (time.equals("9:30:00"))
			return Globals.TIME_930AM;
		if (time.equals("9:45:00"))
			return Globals.TIME_945AM;

		if (time.equals("10:00:00"))
			return Globals.TIME_10AM;
		if (time.equals("10:15:00"))
			return Globals.TIME_1015AM;
		if (time.equals("10:30:00"))
			return Globals.TIME_1030AM;
		if (time.equals("10:45:00"))
			return Globals.TIME_1045AM;

		if (time.equals("11:00:00"))
			return Globals.TIME_11AM;
		if (time.equals("11:15:00"))
			return Globals.TIME_1115AM;
		if (time.equals("11:30:00"))
			return Globals.TIME_1130AM;
		if (time.equals("11:45:00"))
			return Globals.TIME_1145AM;

		if (time.equals("12:00:00"))
			return Globals.TIME_12PM;
		if (time.equals("12:15:00"))
			return Globals.TIME_1215PM;
		if (time.equals("12:30:00"))
			return Globals.TIME_1230PM;
		if (time.equals("12:45:00"))
			return Globals.TIME_1245PM;

		if (time.equals("13:00:00"))
			return Globals.TIME_1PM;
		if (time.equals("13:15:00"))
			return Globals.TIME_115PM;
		if (time.equals("13:30:00"))
			return Globals.TIME_130PM;
		if (time.equals("13:45:00"))
			return Globals.TIME_145PM;

		if (time.equals("14:00:00"))
			return Globals.TIME_2PM;
		if (time.equals("14:15:00"))
			return Globals.TIME_215PM;
		if (time.equals("14:30:00"))
			return Globals.TIME_230PM;
		if (time.equals("14:45:00"))
			return Globals.TIME_245PM;

		if (time.equals("15:00:00"))
			return Globals.TIME_3PM;
		if (time.equals("15:15:00"))
			return Globals.TIME_315PM;
		if (time.equals("15:30:00"))
			return Globals.TIME_330PM;
		if (time.equals("15:45:00"))
			return Globals.TIME_345PM;

		if (time.equals("16:00:00"))
			return Globals.TIME_4PM;
		if (time.equals("16:15:00"))
			return Globals.TIME_415PM;
		if (time.equals("16:30:00"))
			return Globals.TIME_430PM;
		if (time.equals("16:45:00"))
			return Globals.TIME_445PM;

		if (time.equals("17:00:00"))
			return Globals.TIME_5PM;
		if (time.equals("17:15:00"))
			return Globals.TIME_515PM;
		if (time.equals("17:30:00"))
			return Globals.TIME_530PM;
		if (time.equals("17:45:00"))
			return Globals.TIME_545PM;

		if (time.equals("18:00:00"))
			return Globals.TIME_6PM;
		if (time.equals("18:15:00"))
			return Globals.TIME_615PM;
		if (time.equals("18:30:00"))
			return Globals.TIME_630PM;
		if (time.equals("18:45:00"))
			return Globals.TIME_645PM;

		if (time.equals("19:00:00"))
			return Globals.TIME_7PM;
		if (time.equals("19:15:00"))
			return Globals.TIME_715PM;
		if (time.equals("19:30:00"))
			return Globals.TIME_730PM;
		if (time.equals("19:45:00"))
			return Globals.TIME_745PM;

		if (time.equals("20:00:00"))
			return Globals.TIME_8PM;
		if (time.equals("20:15:00"))
			return Globals.TIME_815PM;
		if (time.equals("20:30:00"))
			return Globals.TIME_830PM;
		if (time.equals("20:45:00"))
			return Globals.TIME_845PM;

		if (time.equals("21:00:00"))
			return Globals.TIME_9PM;
		if (time.equals("21:15:00"))
			return Globals.TIME_915PM;
		if (time.equals("21:30:00"))
			return Globals.TIME_930PM;
		if (time.equals("21:45:00"))
			return Globals.TIME_945PM;

		if (time.equals("22:00:00"))
			return Globals.TIME_10PM;
		if (time.equals("22:15:00"))
			return Globals.TIME_1015PM;
		if (time.equals("22:30:00"))
			return Globals.TIME_1030PM;
		if (time.equals("22:45:00"))
			return Globals.TIME_1045PM;

		if (time.equals("23:00:00"))
			return Globals.TIME_11PM;
		if (time.equals("23:15:00"))
			return Globals.TIME_1115PM;
		if (time.equals("23:30:00"))
			return Globals.TIME_1130PM;
		if (time.equals("23:45:00"))
			return Globals.TIME_1145PM;

		if (time.equals("0:00:00"))
			return Globals.TIME_12AM;
		if (time.equals("0:15:00"))
			return Globals.TIME_1215AM;
		if (time.equals("0:30:00"))
			return Globals.TIME_1230AM;
		if (time.equals("0:45:00")) {
			System.out.println("found " + time);
			return Globals.TIME_1245AM;
		}
		if (time.equals("1:00:00")) {
			System.out.println("found " + time);
			return Globals.TIME_1AM;
		}
		if (time.equals("1:15:00")) {
			System.out.println("found " + time);

			return Globals.TIME_115AM;
		}
		if (time.equals("1:30:00"))
			return Globals.TIME_130AM;
		if (time.equals("1:45:00"))
			return Globals.TIME_145AM;

		if (time.equals("2:00:00"))
			return Globals.TIME_2AM;
		if (time.equals("2:15:00"))
			return Globals.TIME_215AM;
		if (time.equals("2:30:00"))
			return Globals.TIME_230AM;
		if (time.equals("2:45:00"))
			return Globals.TIME_245AM;

		if (time.equals("3:00:00"))
			return Globals.TIME_3AM;
		if (time.equals("3:15:00"))
			return Globals.TIME_315AM;
		if (time.equals("3:30:00"))
			return Globals.TIME_330AM;
		if (time.equals("3:45:00"))
			return Globals.TIME_345AM;

		if (time.equals("4:00:00"))
			return Globals.TIME_4AM;
		if (time.equals("4:15:00"))
			return Globals.TIME_415AM;
		if (time.equals("4:30:00"))
			return Globals.TIME_430AM;
		if (time.equals("4:45:00"))
			return Globals.TIME_445AM;

		if (time.equals("5:00:00"))
			return Globals.TIME_5AM;
		if (time.equals("5:15:00"))
			return Globals.TIME_515AM;
		if (time.equals("5:30:00"))
			return Globals.TIME_530AM;
		if (time.equals("5:45:00"))
			return Globals.TIME_545AM;

		if (time.equals("6:00:00"))
			return Globals.TIME_6AM;
		if (time.equals("6:15:00"))
			return Globals.TIME_FLOOR;

		// Fix these these are probably jank
		// WRAPAROUND HERE?????
		if (time.equals("6:30:00"))
			return Globals.TIME_FLOOR;
		if (time.equals("6:45:00"))
			return Globals.BLOCK_CEILING;

		// error code
		return -1;
	}

	/**
	 * For weeks activity in TERM fragment. Take the long time and translate to
	 * TOP variable for drawing.
	 * 
	 * @param startTime
	 * @return
	 */
	public static int setStartTime(String startTime) {

		int top = findTime(startTime);

		// take the long time and translate to TOP variable for drawing
		return top;
	}

	/**
	 * Takes the long time and translates to BOTTOM variable for drawing.
	 * 
	 * @param endTime
	 * @return
	 */
	public static int setEndTime(String endTime) {
		int bottom = findTime(endTime);
		return bottom;
	}

}
