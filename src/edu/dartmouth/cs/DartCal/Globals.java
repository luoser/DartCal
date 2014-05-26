package edu.dartmouth.cs.DartCal;

public class Globals {

	// define the spinner types; class periods
	public final static int EARLY_DRILL = 0;
	public final static int PERIOD_8 = 1;
	public final static int PERIOD_9L = 2;
	public final static int PERIOD_9S = 3;
	public final static int PERIOD_10 = 4;
	public final static int PERIOD_10A = 5;
	public final static int PERIOD_11 = 6;
	public final static int PERIOD_12 = 7;
	public final static int PERIOD_2 = 8;
	public final static int PERIOD_2A = 9;
	public final static int PERIOD_3A = 10;
	public final static int PERIOD_3B = 11;
	public final static int AFTERNOON_DRILL = 12;
	public final static int EVENING_DRILL = 13;

	// spinner values
	public final static String COURSE1_TIME_KEY = "Course #1 Time";
	public final static String COURSE2_TIME_KEY = "Course #2 Time";
	public final static String COURSE3_TIME_KEY = "Course #3 Time";
	public final static String COURSE4_TIME_KEY = "Course #4 Time";

	// controlling the onDraw() method
	public static boolean callOnDraw = false;
	public static boolean xHoursOn = false;
	public static boolean drawEventsOn = false;
	public static boolean drawFriends = false;
	public static boolean isRotated;

	// unrotated time blocks
	public static int BLOCK_CEILING = 10;

	// clock times
	// define every 15 minutes
	public static int TIME_7AM = 19;
	public static int TIME_8AM = 92;
	public static int TIME_9AM = 165;
	public static int TIME_10AM = 238;
	public static int TIME_11AM = 311;
	public static int TIME_12PM = 384;
	public static int TIME_1250PM = 449;
	public static int TIME_1PM = 457;
	public static int TIME_150PM = 523;
	public static int TIME_2PM = 529;
	public static int TIME_3PM = 602;
	public static int TIME_4PM = 675;
	public static int TIME_415PM = 695;
	public static int TIME_5PM = 747;
	public static int TIME_505PM = 755;
	public static int TIME_550PM = 810;
	public static int TIME_6PM = 820;
	public static int TIME_7PM = 895;
	public static int TIME_8PM = 967;	// stops at 1000!!?!
	public static int TIME_9PM = 1039;
	public static int TIME_10PM = 1112;
	public static int TIME_11PM = 1185;
	public static int TIME_12AM = 1258;
	public static int TIME_1AM = 1331;
	public static int TIME_2AM = 1404;
	public static int TIME_3AM = 1477;
	public static int TIME_4AM = 1550;
	public static int TIME_5AM = 1623;
	public static int TIME_6AM = 1696;

	// course period times	
	public static int TIME_8_TOP = 70;
	public static int TIME_8_BOTTOM = 137;
	public static int TIME_9L_TOP = 147;
	public static int TIME_9L_BOTTOM = 227;
	public static int TIME_10_BOTTOM = 317;
	public static int TIME_10A_BOTTOM = 372;
	public static int TIME_11_TOP = 327;
	public static int TIME_11_BOTTOM = 407;
	public static int TIME_12_TOP = 417;
	public static int TIME_12_BOTTOM = 497;
	public static int TIME_2A_BOTTOM = 665;
	public static int TIME_2_TOP = 507;
	public static int TIME_2_BOTTOM = 592;
	public static int TIME_3A_BOTTOM = 737;
	public static int TIME_3B_BOTTOM = 808;

	// Day boundaries
	public static int SUNDAY_LEFT = 30; // for sundays
	public static int SUNDAY_RIGHT = 81; // this might be jank
	public static int MONDAY_LEFT = 117;
	public static int MONDAY_RIGHT = 199;
	public static int TUESDAY_LEFT = 224;
	public static int TUESDAY_RIGHT = 306;
	public static int WEDNESDAY_LEFT = 334;
	public static int WEDNESDAY_RIGHT = 416;
	public static int THURSDAY_LEFT = 441;
	public static int THURSDAY_RIGHT = 523;
	public static int FRIDAY_LEFT = 549;
	public static int FRIDAY_RIGHT = 631;
	public static int SATURDAY_LEFT = 657;
	public static int SATURDAY_RIGHT = 739;

}
