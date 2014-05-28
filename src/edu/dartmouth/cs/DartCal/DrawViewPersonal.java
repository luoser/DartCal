/**
 * DartCal
 * File: DrawView.java
 * Author: Lisa Luo
 * Modified: 5/21/14
 * Description: Controls the drawing of schedules in the
 * 	Weekly and Friends fragments.
 */

package edu.dartmouth.cs.DartCal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseQuery;

// class to help draw things
public class DrawViewPersonal extends View {
	private Paint paint = new Paint();
	private Context context = WeeklyFragment.mContext;
	private ArrayList<Rect> rectangles = new ArrayList<Rect>();
	private ArrayList<Integer> rectangleTimes = new ArrayList<Integer>();
	private ArrayList<ArrayList<Event>> drawingMatrix;
	private EventDbHelper dbHelper = new EventDbHelper(context);

	public DrawViewPersonal(Context context) {
		super(context);
	}

	// allows for insertion into xml
	public DrawViewPersonal(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DrawViewPersonal(Context context, AttributeSet attrs, int defStyle) {
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
		measureSpec = 1001;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.UNSPECIFIED) {
			result = specSize;
		}
		return result;
	}

	/**
	 * Main method for handling all drawing interactions. Controlled by global
	 * booleans.
	 */
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// retrieve user profile information
		ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
		query.whereEqualTo("ownerName", Globals.USER);
		drawingMatrix = Globals.drawingMatrix;

		try {
			List<Event> eventList = query.find();

			// Check for the first time
			if (eventList.size() > 0) {

				if (Globals.drawPersonalEvents) {

					for (int i = 0; i < eventList.size(); i++) {

						// set color (defined in methods)
						paint.setStrokeWidth(0);

						int mint = getResources().getColor(R.color.mint_green);
						paint.setColor(mint);

						// fetch the custom events from the database
						System.out.println("drawevents on");

						long startTime = eventList.get(i).getStartTime();
						long endTime = eventList.get(i).getEndTime();
						long date = eventList.get(i).getDate();
						drawCustomEvent(startTime, endTime, date, canvas);

					}
				}

				System.out.println("draw friend events? "
						+ Globals.drawEventsOn);
				if (Globals.drawFriendsEventsOn) {

					drawingMatrix = Globals.drawingMatrix;
					paint.setStrokeWidth(0);

					for (int i = 0; i < drawingMatrix.size(); i++) {

						ArrayList<Event> friendEvents = drawingMatrix.get(i);

						for (int j = 0; j < friendEvents.size(); j++) {
							// grab the color from the friend
							int color = friendEvents.get(j).getColor();
							paint.setColor(color);

							long startTime = friendEvents.get(j).getStartTime();
							long endTime = friendEvents.get(j).getEndTime();
							long date = friendEvents.get(j).getDate();

							drawCustomEvent(startTime, endTime, date, canvas);
							canvas.save();
						}
					}

				}

			}

		} catch (ParseException e) {
			System.out.println("Parse did not work.");
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int touchX = (int) event.getX();
		int touchY = (int) event.getY();

		for (int i = 0; i < rectangles.size(); i++) {
			if (rectangles.get(i).contains(touchX, touchY)) {

				int time = rectangleTimes.get(i);
				if (time == Globals.PERIOD_8) {
					Toast.makeText(context, "Period 8\n7:45-8:35am",
							Toast.LENGTH_SHORT).show();
					return true;
				}

				if (time == Globals.PERIOD_9L) {
					Toast.makeText(context, "Period 9L\n8:45-9:50am",
							Toast.LENGTH_SHORT).show();
					return true;
				}
				if (time == Globals.PERIOD_9S) {
					Toast.makeText(context, "Period 9S\n9:00-9:50am",
							Toast.LENGTH_SHORT).show();
					return true;
				}
				if (time == Globals.PERIOD_10) {
					Toast.makeText(context, "Period 10\n10:00-11:05am",
							Toast.LENGTH_SHORT).show();
					return true;
				}
				if (time == Globals.PERIOD_10A) {
					Toast.makeText(context, "Period 10A\n10:100-11:50am",
							Toast.LENGTH_SHORT).show();
					return true;
				}
				if (time == Globals.PERIOD_11) {
					Toast.makeText(context, "Period 11\n11:15-12:20pm",
							Toast.LENGTH_SHORT).show();
					return true;
				}
				if (time == Globals.PERIOD_12) {
					Toast.makeText(context, "Period 12\n12:30-1:35PM",
							Toast.LENGTH_SHORT).show();
					return true;
				}
				if (time == Globals.PERIOD_2) {
					Toast.makeText(context, "Period 2\n1:45-2:50pm",
							Toast.LENGTH_SHORT).show();
					return true;
				}
				if (time == Globals.PERIOD_2A) {
					Toast.makeText(context, "Period 2A\n2:00-3:50pm",
							Toast.LENGTH_SHORT).show();
					return true;
				}
				if (time == Globals.PERIOD_3A) {
					Toast.makeText(context, "Period 3A\n3:00-4:50pm",
							Toast.LENGTH_SHORT).show();
					return true;
				}
				if (time == Globals.PERIOD_3B) {
					Toast.makeText(context, "Period 3B\n4:00-5:50pm",
							Toast.LENGTH_SHORT).show();
					return true;
				}

				if (time == Globals.PERIOD_8_X || time == Globals.PERIOD_9L_X
						|| time == Globals.PERIOD_9S_X
						|| time == Globals.PERIOD_10_X
						|| time == Globals.PERIOD_11_X
						|| time == Globals.PERIOD_10A_X
						|| time == Globals.PERIOD_12_X
						|| time == Globals.PERIOD_2_X
						|| time == Globals.PERIOD_2A_X
						|| time == Globals.PERIOD_3A_X
						|| time == Globals.PERIOD_3B_X) {
					Toast.makeText(context, "x-hour", Toast.LENGTH_SHORT)
							.show();
					return true;

				}
			}
		}

		return false;
	}

	/**
	 * Takes in an epoch time that contains start and end time, which includes
	 * the date.
	 * 
	 * @param startTime
	 * @param endTime
	 * @param canvas
	 */
	public void drawCustomEvent(long startLong, long endLong, long timeInMs,
			Canvas canvas) {

		System.out.println("startLong : " + startLong);
		System.out.println("endLong : " + endLong);
		System.out.println("timeINMs: " + timeInMs);

		// grab the start and end times
		String startString = CalendarUtils.parseTime(startLong);
		String endString = CalendarUtils.parseTime(endLong);

		System.out.println("Startstring " + startString);
		System.out.println("endstring " + endString);

		int top = CalendarUtils.setStartTime(startString);
		int bottom = CalendarUtils.setEndTime(endString);
		int left, right;

		// grab the day
		int date = CalendarUtils.parseDayOfWeek(timeInMs);
		switch (date) {
		case Calendar.SUNDAY:
			left = Globals.SUNDAY_LEFT;
			right = Globals.SUNDAY_RIGHT;
			canvas.drawRect(left, top, right, bottom, paint);

		case Calendar.MONDAY:
			left = Globals.MONDAY_LEFT;
			right = Globals.MONDAY_RIGHT;
			canvas.drawRect(left, top, right, bottom, paint);

			break;
		case Calendar.TUESDAY:
			left = Globals.TUESDAY_LEFT;
			right = Globals.TUESDAY_RIGHT;
			canvas.drawRect(left, top, right, bottom, paint);

			break;
		case Calendar.WEDNESDAY:
			System.out.println("it's wednesday bitch");
			left = Globals.WEDNESDAY_LEFT;
			right = Globals.WEDNESDAY_RIGHT;
			canvas.drawRect(left, top, right, bottom, paint);

			break;
		case Calendar.THURSDAY:
			left = Globals.THURSDAY_LEFT;
			right = Globals.THURSDAY_RIGHT;
			canvas.drawRect(left, top, right, bottom, paint);

			break;
		case Calendar.FRIDAY:
			left = Globals.FRIDAY_LEFT;
			right = Globals.FRIDAY_RIGHT;
			canvas.drawRect(left, top, right, bottom, paint);

			break;
		case Calendar.SATURDAY:
			left = Globals.SATURDAY_LEFT;
			right = Globals.SATURDAY_RIGHT;
			canvas.drawRect(left, top, right, bottom, paint);

			break;
		default:
			break;
		}

	}

}
