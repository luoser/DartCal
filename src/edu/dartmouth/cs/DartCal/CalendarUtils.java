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

		if (time == "7:00:00")
			return Globals.TIME_7AM;
		if (time == "7:15:00")
			return Globals.TIME_715AM;
		if (time == "7:30:00")
			return Globals.TIME_730AM;
		if (time == "7:45:00")
			return Globals.TIME_745AM;

		if (time == "8:00:00")
			return Globals.TIME_8AM;
		if (time == "8:15:00")
			return Globals.TIME_815AM;
		if (time == "8:30:00")
			return Globals.TIME_830AM;
		if (time == "8:45:00")
			return Globals.TIME_845AM;

		if (time == "9:00:00")
			return Globals.TIME_9AM;
		if (time == "9:15:00")
			return Globals.TIME_915AM;
		if (time == "9:30:00")
			return Globals.TIME_930AM;
		if (time == "9:45:00")
			return Globals.TIME_945AM;

		if (time == "10:00:00")
			return Globals.TIME_10AM;
		if (time == "10:15:00")
			return Globals.TIME_1015AM;
		if (time == "10:30:00")
			return Globals.TIME_1030AM;
		if (time == "10:45:00")
			return Globals.TIME_1045AM;

		if (time == "11:00:00")
			return Globals.TIME_11AM;
		if (time == "11:15:00")
			return Globals.TIME_1115AM;
		if (time == "11:30:00")
			return Globals.TIME_1130AM;
		if (time == "11:45:00")
			return Globals.TIME_1145AM;

		if (time == "12:00:00")
			return Globals.TIME_12PM;
		if (time == "12:15:00")
			return Globals.TIME_1215PM;
		if (time == "12:30:00")
			return Globals.TIME_1230PM;
		if (time == "12:45:00")
			return Globals.TIME_1245PM;

		if (time == "13:00:00")
			return Globals.TIME_1PM;
		if (time == "13:15:00")
			return Globals.TIME_115PM;
		if (time == "13:30:00")
			return Globals.TIME_130PM;
		if (time == "13:45:00")
			return Globals.TIME_145PM;

		if (time == "14:00:00")
			return Globals.TIME_2PM;
		if (time == "14:15:00")
			return Globals.TIME_215PM;
		if (time == "14:30:00")
			return Globals.TIME_230PM;
		if (time == "14:45:00")
			return Globals.TIME_245PM;

		if (time == "15:00:00")
			return Globals.TIME_3PM;
		if (time == "15:15:00")
			return Globals.TIME_315PM;
		if (time == "15:30:00")
			return Globals.TIME_330PM;
		if (time == "15:45:00")
			return Globals.TIME_345PM;

		if (time == "16:00:00")
			return Globals.TIME_4PM;
		if (time == "16:15:00")
			return Globals.TIME_415PM;
		if (time == "16:30:00")
			return Globals.TIME_430PM;
		if (time == "16:45:00")
			return Globals.TIME_445PM;

		if (time == "17:00:00")
			return Globals.TIME_5PM;
		if (time == "17:15:00")
			return Globals.TIME_515PM;
		if (time == "17:30:00")
			return Globals.TIME_530PM;
		if (time == "17:45:00")
			return Globals.TIME_545PM;

		if (time == "18:00:00")
			return Globals.TIME_6PM;
		if (time == "18:15:00")
			return Globals.TIME_615PM;
		if (time == "18:30:00")
			return Globals.TIME_630PM;
		if (time == "18:45:00")
			return Globals.TIME_645PM;

		if (time == "19:00:00")
			return Globals.TIME_7PM;
		if (time == "19:15:00")
			return Globals.TIME_715PM;
		if (time == "19:30:00")
			return Globals.TIME_730PM;
		if (time == "19:45:00")
			return Globals.TIME_745PM;

		if (time == "20:00:00")
			return Globals.TIME_8PM;
		if (time == "20:15:00")
			return Globals.TIME_815PM;
		if (time == "20:30:00")
			return Globals.TIME_830PM;
		if (time == "20:45:00")
			return Globals.TIME_845PM;

		if (time == "21:00:00")
			return Globals.TIME_9PM;
		if (time == "21:15:00")
			return Globals.TIME_915PM;
		if (time == "21:30:00")
			return Globals.TIME_930PM;
		if (time == "21:45:00")
			return Globals.TIME_945PM;

		if (time == "22:00:00")
			return Globals.TIME_10PM;
		if (time == "22:15:00")
			return Globals.TIME_1015PM;
		if (time == "22:30:00")
			return Globals.TIME_1030PM;
		if (time == "22:45:00")
			return Globals.TIME_1045PM;

		if (time == "23:00:00")
			return Globals.TIME_11PM;
		if (time == "23:15:00")
			return Globals.TIME_1115PM;
		if (time == "23:30:00")
			return Globals.TIME_1130PM;
		if (time == "23:45:00")
			return Globals.TIME_1145PM;

		if (time == "00:00:00")
			return Globals.TIME_12AM;
		if (time == "00:15:00")
			return Globals.TIME_1215AM;
		if (time == "00:30:00")
			return Globals.TIME_1230PM;
		if (time == "00:45:00")
			return Globals.TIME_1245PM;

		if (time == "1:00:00")
			return Globals.TIME_1AM;
		if (time == "1:15:00")
			return Globals.TIME_115AM;
		if (time == "1:30:00")
			return Globals.TIME_130AM;
		if (time == "1:45:00")
			return Globals.TIME_145AM;

		if (time == "2:00:00")
			return Globals.TIME_2AM;
		if (time == "2:15:00")
			return Globals.TIME_215AM;
		if (time == "2:30:00")
			return Globals.TIME_230AM;
		if (time == "2:45:00")
			return Globals.TIME_245AM;

		if (time == "3:00:00")
			return Globals.TIME_3AM;
		if (time == "3:15:00")
			return Globals.TIME_315AM;
		if (time == "3:30:00")
			return Globals.TIME_330AM;
		if (time == "3:45:00")
			return Globals.TIME_345AM;

		if (time == "4:00:00")
			return Globals.TIME_4AM;
		if (time == "4:15:00")
			return Globals.TIME_415AM;
		if (time == "4:30:00")
			return Globals.TIME_430AM;
		if (time == "4:45:00")
			return Globals.TIME_445AM;

		if (time == "5:00:00")
			return Globals.TIME_5AM;
		if (time == "5:15:00")
			return Globals.TIME_515AM;
		if (time == "5:30:00")
			return Globals.TIME_530AM;
		if (time == "5:45:00")
			return Globals.TIME_545AM;

		if (time == "6:00:00")
			return Globals.TIME_6AM;
		if (time == "6:15:00")
			return Globals.TIME_FLOOR;

		// Fix these these are probably jank
		// WRAPAROUND HERE?????
		if (time == "6:30:00")
			return Globals.TIME_FLOOR;
		if (time == "6:45:00")
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
