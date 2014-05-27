/**
 * DartCal
 * File: DrawView.java
 * Author: Lisa Luo
 * Modified: 5/21/14
 * Description: Controls the drawing of schedules in the
 * 	Weekly and Friends fragments.
 */

package edu.dartmouth.cs.DartCal;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

// class to help draw things
public class DrawView extends View {
	private Paint paint = new Paint();

	private boolean isRotated = MainActivity.isRotated;
	private Context context = WeeklyFragment.mContext;
	private Paint mPaint = new Paint();
	private Canvas mCanvas;

	private EventDbHelper dbHelper = new EventDbHelper(context);
	// private PersonalEventDbHelper

	int rColor = CalendarUtils.generateRandomColor();

	public DrawView(Context context) {
		super(context);
	}

	// allows for insertion into xml
	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DrawView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// setMeasuredDimension(measureWidth(widthMeasureSpec),
		// measureHeight(heightMeasureSpec));
		setMeasuredDimension(measureWidth(widthMeasureSpec),
				Globals.SCROLL_VIEW_HEIGHT);
	}

	/**
	 * Determines the width of this view
	 * 
	 * @param measureSpec
	 * @return The width of the view, honoring constraints from measureSpec
	 */
	private int measureWidth(int measureSpec) {
		int result = 0;
		// This is because of background image in relativeLayout, which is
		// 1000*1000px
		measureSpec = 1001;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.UNSPECIFIED) {
			// We were told how big to be
			result = specSize;
		}
		return result;
	}

	/**
	 * Determines the height of this view
	 * 
	 * @param measureSpec
	 * @return The height of the view, honoring constraints from measureSpec
	 */
	private int measureHeight(int measureSpec) {
		int result = 0;
		// This is because of background image in relativeLayout, which is
		// 1000*1000px
		measureSpec = 1001;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.UNSPECIFIED) {
			// Here we say how high to be
			result = specSize;
		}

		System.out.println("SCREEN HEIGHT: " + result);
		return result;
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// debugging hour blocks
		paint.setStrokeWidth(0);
		int dark = getResources().getColor(R.color.dark_green);
		paint.setColor(dark);

		canvas.drawRect(Globals.SATURDAY_LEFT, Globals.TIME_915AM,
				Globals.SATURDAY_RIGHT, Globals.TIME_530PM, paint);

		try {

			// retrieve user profile information
			Friend userData = dbHelper.fetchEntryByIndex(1);

			// check for first time use
			if (userData != null) {

				ArrayList<Event> courseBlocks = userData.getSchedule();

				if (courseBlocks.size() > 0) {

					// fetch course information from the database
					int course1Time = courseBlocks.get(0).getClassPeriod();
					int course2Time = courseBlocks.get(1).getClassPeriod();
					int course3Time = courseBlocks.get(2).getClassPeriod();
					int course4Time = courseBlocks.get(3).getClassPeriod();

					// set color (defined in methods)
					paint.setStrokeWidth(0);

					// need to distinguish how long to draw the time blocks
					// also distinguish rotation

					if (Globals.callOnDraw) {

						int mint = getResources().getColor(R.color.mint_green);
						paint.setColor(mint);

						drawCourse(course1Time, canvas);
						drawCourse(course2Time, canvas);
						drawCourse(course3Time, canvas);
						drawCourse(course4Time, canvas);
						canvas.save();
					}

					// turn the xhours on; for use in the WEEKLY fragment
					if (Globals.xHoursOn) {
						int green = getResources().getColor(R.color.dark_green);
						paint.setColor(green);

						canvas.save();
						drawXhour(course1Time, canvas);
						drawXhour(course2Time, canvas);
						drawXhour(course3Time, canvas);
						drawXhour(course4Time, canvas);

						canvas.restore();
						invalidate();
					}
				}

				// Draw friends data; for use in the FRIENDS fragment
				if (Globals.drawFriends) {

					int color = CalendarUtils.generateRandomColor();
					paint.setColor(color);

					ArrayList<Friend> friendData = dbHelper.fetchEntries();

					if (friendData != null) {

						for (int i = 0; i < friendData.size(); i++) {

							ArrayList<Event> friendCourses = friendData.get(i)
									.getSchedule();

							int course1 = friendCourses.get(0).getClassPeriod();
							int course2 = friendCourses.get(1).getClassPeriod();
							int course3 = friendCourses.get(2).getClassPeriod();
							int course4 = friendCourses.get(3).getClassPeriod();

							drawCourse(course1, canvas);
							drawCourse(course2, canvas);
							drawCourse(course3, canvas);
							drawCourse(course4, canvas);

							if (Globals.friendXhoursOn) {
								drawXhour(course1, canvas);
								drawXhour(course2, canvas);
								drawXhour(course3, canvas);
								drawXhour(course4, canvas);
							}
						}
					}
				}

				// DATABASE CHANGE: change for every 15 minute
				// increment.......!!!!!!....spinner....
				// Draw events; for use in the TERM fragment
				if (Globals.drawEventsOn) {

					// GET THE EVENTS FROM THE DATABASE

					long startTime = 0;
					long endTime = 0;
					long date = 0;

					// drawCustomEvent(startTime, endTime, date);

				}

				invalidate();
			}

		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// debugging random color generator
		paint.setStrokeWidth(0);
		paint.setColor(rColor);
		drawCourse(Globals.PERIOD_10, canvas);
	}

	public boolean onTouch(View v, MotionEvent event) {
		invalidate();
		return true;
	}

	/**
	 * Draws specified course block.
	 * 
	 * @param period
	 * @param canvas
	 */
	public void drawCourse(int period, Canvas canvas) {

		switch (period) {

		case Globals.EARLY_DRILL:

			break;

		case Globals.PERIOD_8:
			canvas.drawRect(Globals.MONDAY_LEFT, Globals.TIME_8_TOP,
					Globals.MONDAY_RIGHT, Globals.TIME_8_BOTTOM, paint);
			canvas.drawRect(Globals.TUESDAY_LEFT, Globals.TIME_8_TOP,
					Globals.TUESDAY_RIGHT, Globals.TIME_8_BOTTOM, paint);
			canvas.drawRect(Globals.THURSDAY_LEFT, Globals.TIME_8_TOP,
					Globals.THURSDAY_RIGHT, Globals.TIME_8_BOTTOM, paint);
			canvas.drawRect(Globals.FRIDAY_LEFT, Globals.TIME_8_TOP,
					Globals.FRIDAY_RIGHT, Globals.TIME_8_BOTTOM, paint);
			break;

		case Globals.PERIOD_9L:
			canvas.drawRect(Globals.MONDAY_LEFT, Globals.TIME_9L_TOP,
					Globals.MONDAY_RIGHT, Globals.TIME_9L_BOTTOM, paint);
			canvas.drawRect(Globals.WEDNESDAY_LEFT, Globals.TIME_9L_TOP,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_9L_BOTTOM, paint);
			canvas.drawRect(Globals.FRIDAY_LEFT, Globals.TIME_9L_TOP,
					Globals.FRIDAY_RIGHT, Globals.TIME_9L_BOTTOM, paint);
			break;

		case Globals.PERIOD_9S:
			canvas.drawRect(Globals.MONDAY_LEFT, Globals.TIME_9AM,
					Globals.MONDAY_RIGHT, Globals.TIME_9L_BOTTOM, paint);
			canvas.drawRect(Globals.TUESDAY_LEFT, Globals.TIME_9AM,
					Globals.TUESDAY_RIGHT, Globals.TIME_9L_BOTTOM, paint);
			canvas.drawRect(Globals.THURSDAY_LEFT, Globals.TIME_9AM,
					Globals.THURSDAY_RIGHT, Globals.TIME_9L_BOTTOM, paint);
			canvas.drawRect(Globals.FRIDAY_LEFT, Globals.TIME_9AM,
					Globals.FRIDAY_RIGHT, Globals.TIME_9L_BOTTOM, paint);
			break;

		case Globals.PERIOD_10:
			canvas.drawRect(Globals.MONDAY_LEFT, Globals.TIME_10AM,
					Globals.MONDAY_RIGHT, Globals.TIME_10_BOTTOM, paint);
			canvas.drawRect(Globals.WEDNESDAY_LEFT, Globals.TIME_10AM,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_10_BOTTOM, paint);
			canvas.drawRect(Globals.FRIDAY_LEFT, Globals.TIME_10AM,
					Globals.FRIDAY_RIGHT, Globals.TIME_10_BOTTOM, paint);
			break;

		case Globals.PERIOD_10A:
			canvas.drawRect(Globals.TUESDAY_LEFT, Globals.TIME_10AM,
					Globals.TUESDAY_RIGHT, Globals.TIME_10A_BOTTOM, paint);
			canvas.drawRect(Globals.THURSDAY_LEFT, Globals.TIME_10AM,
					Globals.THURSDAY_RIGHT, Globals.TIME_10A_BOTTOM, paint);
			break;

		case Globals.PERIOD_11:
			canvas.drawRect(Globals.MONDAY_LEFT, Globals.TIME_11_TOP,
					Globals.MONDAY_RIGHT, Globals.TIME_11_BOTTOM, paint);
			canvas.drawRect(Globals.WEDNESDAY_LEFT, Globals.TIME_11_TOP,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_11_BOTTOM, paint);
			canvas.drawRect(Globals.FRIDAY_LEFT, Globals.TIME_11_TOP,
					Globals.FRIDAY_RIGHT, Globals.TIME_11_BOTTOM, paint);
			break;

		case Globals.PERIOD_12:
			canvas.drawRect(Globals.MONDAY_LEFT, Globals.TIME_12_TOP,
					Globals.MONDAY_RIGHT, Globals.TIME_12_BOTTOM, paint);
			canvas.drawRect(Globals.WEDNESDAY_LEFT, Globals.TIME_12_TOP,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_12_BOTTOM, paint);
			canvas.drawRect(Globals.FRIDAY_LEFT, Globals.TIME_12_TOP,
					Globals.FRIDAY_RIGHT, Globals.TIME_12_BOTTOM, paint);
			break;

		case Globals.PERIOD_2:
			canvas.drawRect(Globals.MONDAY_LEFT, Globals.TIME_2_TOP,
					Globals.MONDAY_RIGHT, Globals.TIME_2_BOTTOM, paint);
			canvas.drawRect(Globals.WEDNESDAY_LEFT, Globals.TIME_2_TOP,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_2_BOTTOM, paint);
			canvas.drawRect(Globals.FRIDAY_LEFT, Globals.TIME_2_TOP,
					Globals.FRIDAY_RIGHT, Globals.TIME_2_BOTTOM, paint);
			break;

		case Globals.PERIOD_2A:
			canvas.drawRect(Globals.TUESDAY_LEFT, Globals.TIME_2PM,
					Globals.TUESDAY_RIGHT, Globals.TIME_2A_BOTTOM, paint);
			canvas.drawRect(Globals.THURSDAY_LEFT, Globals.TIME_2PM,
					Globals.THURSDAY_RIGHT, Globals.TIME_2A_BOTTOM, paint);
			break;

		case Globals.PERIOD_3A:
			canvas.drawRect(Globals.MONDAY_LEFT, Globals.TIME_3PM,
					Globals.MONDAY_RIGHT, Globals.TIME_3A_BOTTOM, paint);
			canvas.drawRect(Globals.THURSDAY_LEFT, Globals.TIME_4PM,
					Globals.THURSDAY_RIGHT, Globals.TIME_3B_BOTTOM, paint);
			break;

		case Globals.PERIOD_3B:
			canvas.drawRect(Globals.TUESDAY_LEFT, Globals.TIME_4PM,
					Globals.TUESDAY_RIGHT, Globals.TIME_3B_BOTTOM, paint);
			canvas.drawRect(Globals.THURSDAY_LEFT, Globals.TIME_4PM,
					Globals.THURSDAY_RIGHT, Globals.TIME_3B_BOTTOM, paint);
			break;

		case Globals.AFTERNOON_DRILL:
			break;

		case Globals.EVENING_DRILL:
			break;

		default:
			break;
		}

		invalidate();
	}

	/**
	 * Draws the x-hour associated with the specified course period.
	 * 
	 * @param period
	 * @param canvas
	 */
	public void drawXhour(int period, Canvas canvas) {

		switch (period) {

		// 8
		case Globals.PERIOD_8:
			canvas.drawRect(Globals.WEDNESDAY_LEFT, Globals.TIME_8_TOP,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_8_BOTTOM, paint);
			break;

		// 9L
		case Globals.PERIOD_9L:
			canvas.drawRect(Globals.THURSDAY_LEFT, Globals.TIME_9AM,
					Globals.THURSDAY_RIGHT, Globals.TIME_9L_BOTTOM, paint);
			break;

		// 9S
		case Globals.PERIOD_9S:
			canvas.drawRect(Globals.WEDNESDAY_LEFT, Globals.TIME_9AM,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_9L_BOTTOM, paint);
			break;

		// 10
		case Globals.PERIOD_10:
			canvas.drawRect(Globals.THURSDAY_LEFT, Globals.TIME_12PM,
					Globals.THURSDAY_RIGHT, Globals.TIME_1250PM, paint);
			break;

		// 10A
		case Globals.PERIOD_10A:
			canvas.drawRect(Globals.WEDNESDAY_LEFT, Globals.TIME_3PM,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_2A_BOTTOM, paint);
			break;

		// 11
		case Globals.PERIOD_11:
			canvas.drawRect(Globals.TUESDAY_LEFT, Globals.TIME_12PM,
					Globals.TUESDAY_RIGHT, Globals.TIME_1250PM, paint);
			break;

		// 12
		case Globals.PERIOD_12:
			canvas.drawRect(Globals.TUESDAY_LEFT, Globals.TIME_1PM,
					Globals.TUESDAY_RIGHT, Globals.TIME_150PM, paint);
			break;

		// 2
		case 8:
			canvas.drawRect(Globals.THURSDAY_LEFT, Globals.TIME_1PM,
					Globals.THURSDAY_RIGHT, Globals.TIME_150PM, paint);
			break;

		// 2A
		case Globals.PERIOD_2A:
			canvas.drawRect(Globals.WEDNESDAY_LEFT, Globals.TIME_415PM,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_505PM, paint);
			break;

		// 3A
		case Globals.PERIOD_3A:
			canvas.drawRect(Globals.MONDAY_LEFT, Globals.TIME_5PM,
					Globals.MONDAY_RIGHT, Globals.TIME_550PM, paint);
			break;

		// 3B
		case Globals.PERIOD_3B:
			canvas.drawRect(Globals.MONDAY_LEFT, Globals.TIME_5PM,
					Globals.MONDAY_RIGHT, Globals.TIME_550PM, paint);
			break;
		}

		invalidate();

	}

	/**
	 * For weeks activity in TERM fragment. Take the long time and translate to
	 * TOP variable for drawing.
	 * 
	 * @param startTime
	 * @return
	 */
	public float setStartTime(String startTime) {

		// parse the start time??

		float top = findTime(startTime);

		// take the long time and translate to TOP variable for drawing
		return top;
	}

	/**
	 * Takes the long time and translates to BOTTOM variable for drawing.
	 * 
	 * @param endTime
	 * @return
	 */
	public float setEndTime(long endTime) {

		int bottom;

		return bottom = 0;
	}

	/**
	 * Takes in an epoch time that contains start and end time, which includes
	 * the date.
	 * 
	 * @param startTime
	 * @param endTime
	 * @param canvas
	 */
	public void drawCustomEvent(long startTime, long endTime, Canvas canvas) {

		String start = CalendarUtils.parseTime(startTime);
		String end = CalendarUtils.parseTime(endTime);
		//
		// int top = setStartTime(startTime);
		// int bottom = setEndTime(endTime);

		// canvas.drawRect(left, right, top, bottom, paint);

	}

	/**
	 * Helper function to translate long time into pixel location to draw block.
	 * 
	 * @param time
	 * @return where to draw said time
	 */
	public float findTime(String time) {

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
		// WRAPAROUND HERE
		if (time == "6:30:00")
			return Globals.TIME_FLOOR;
		if (time == "6:45:00")
			return Globals.BLOCK_CEILING;

		// error code
		return -1;
	}

}
