/**
 * DartCal
 * File: Globals.java
 * Author: Lisa Luo
 * Modified: 5/27/14
 * Description: Global variables for use
 * 	throughout application.
 */

package edu.dartmouth.cs.DartCal;

public class Globals {
	
	public static final String TAG = "CS 65";
	public static final String DATE_FORMAT = "H:mm:ss MMM d yyyy";
	public static final String TIME_FORMAT = "H:mm:ss";
	public static final int SCROLL_VIEW_HEIGHT = 1700;

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
	public static boolean friendXhoursOn = false;

	// unrotated time blocks
	public static int BLOCK_CEILING = 10;

	// clock times, defined every 15 minutes
	// in relation to drawing location
	// These are used as TOP / BOTTOM variables
	public static int TIME_7AM = 19;
	public static int TIME_715AM = 36;
	public static int TIME_730AM = 56;
	public static int TIME_745AM = 70;
	
	public static int TIME_8AM = 92;
	public static int TIME_815AM = 110;
	public static int TIME_830AM = 127;
	public static int TIME_845AM = 144;
	
	public static int TIME_9AM = 165;
	public static int TIME_915AM = 183; // + 18
	public static int TIME_930AM = 202; // + 19
	public static int TIME_945AM = 219; // + 19

	public static int TIME_10AM = 238;
	public static int TIME_1015AM = 256;
	public static int TIME_1030AM = 275;
	public static int TIME_1045AM = 292;
	
	public static int TIME_11AM = 311;
	public static int TIME_1115AM = 329;
	public static int TIME_1130AM = 348;
	public static int TIME_1145AM = 365;
	
	public static int TIME_12PM = 384;
	public static int TIME_1215PM = 402;
	public static int TIME_1230PM = 421;
	public static int TIME_1245PM = 438;
	public static int TIME_1250PM = 449;
	
	public static int TIME_1PM = 457;
	public static int TIME_115PM = 475;
	public static int TIME_130PM = 494;
	public static int TIME_145PM = 511;
	public static int TIME_150PM = 523;
	
	public static int TIME_2PM = 529;
	public static int TIME_215PM = 547;
	public static int TIME_230PM = 566;
	public static int TIME_245PM = 583;
	
	public static int TIME_3PM = 602;
	public static int TIME_315PM = 620;
	public static int TIME_330PM = 639;
	public static int TIME_345PM = 656;
	
	public static int TIME_4PM = 675;
	public static int TIME_415PM = 695;
	public static int TIME_430PM = 711;
	public static int TIME_445PM = 729;
	
	public static int TIME_5PM = 747;
	public static int TIME_505PM = 755;
	public static int TIME_515PM = 765;
	public static int TIME_530PM = 785;
	public static int TIME_545PM = 801;
	public static int TIME_550PM = 810;
	
	public static int TIME_6PM = 820;
	public static int TIME_615PM = 838;
	public static int TIME_630PM = 857;
	public static int TIME_645PM = 874;
	
	public static int TIME_7PM = 895;
	public static int TIME_715PM = 913;
	public static int TIME_730PM = 932;
	public static int TIME_745PM = 949;
	
	public static int TIME_8PM = 967;	// stops at 1000!!?!
	public static int TIME_815PM = 985;
	public static int TIME_830PM = 1004;
	public static int TIME_845PM = 1021;
	
	public static int TIME_9PM = 1041;
	public static int TIME_915PM = 1059;
	public static int TIME_930PM = 1078;
	public static int TIME_945PM = 1095;
	
	public static int TIME_10PM = 1115;
	public static int TIME_1015PM = 1133;
	public static int TIME_1030PM = 1152;
	public static int TIME_1045PM = 1169;
	
	public static int TIME_11PM = 1187;
	public static int TIME_1115PM = 1205;
	public static int TIME_1130PM = 1224;
	public static int TIME_1145PM = 1241;
	
	public static int TIME_12AM = 1260;
	public static int TIME_1215AM = 1278;
	public static int TIME_1230AM = 1297;
	public static int TIME_1245AM = 1314;
	
	public static int TIME_1AM = 1333;
	public static int TIME_115AM = 1351;
	public static int TIME_130AM = 1370;
	public static int TIME_145AM = 1387;
	
	public static int TIME_2AM = 1406;
	public static int TIME_215AM = 1424;
	public static int TIME_230AM = 1443;
	public static int TIME_245AM = 1460;
	
	public static int TIME_3AM = 1479;
	public static int TIME_315AM = 1497;
	public static int TIME_330AM = 1516;
	public static int TIME_345AM = 1533;
	
	public static int TIME_4AM = 1552;
	public static int TIME_415AM = 1570;
	public static int TIME_430AM = 1589;
	public static int TIME_445AM = 1606;
	
	public static int TIME_5AM = 1625;
	public static int TIME_515AM = 1643;
	public static int TIME_530AM = 1662;
	public static int TIME_545AM = 1679;
	
	public static int TIME_6AM = 1698;
	public static int TIME_FLOOR = 1700;

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
