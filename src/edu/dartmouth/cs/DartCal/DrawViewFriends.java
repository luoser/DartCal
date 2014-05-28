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
public class DrawViewFriends extends View {
	private Paint paint = new Paint();
	private Context context = WeeklyFragment.mContext;
	private ArrayList<Rect> rectangles = new ArrayList<Rect>();
	private ArrayList<Integer> rectangleTimes = new ArrayList<Integer>();
	private ArrayList<ArrayList<Event>> drawingMatrix;
	private EventDbHelper dbHelper = new EventDbHelper(context);

	public DrawViewFriends(Context context) {
		super(context);
	}

	// allows for insertion into xml
	public DrawViewFriends(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DrawViewFriends(Context context, AttributeSet attrs, int defStyle) {
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

		System.out.println("entered ondraw");

		// debugging hour blocks
		paint.setStrokeWidth(0);

		// retrieve user profile information
		ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
		query.whereEqualTo("ownerName", Globals.USER);

		try {
			List<Event> eventList = query.find();

			if (eventList.size() > 0) {

				for (int i = 0; i < eventList.size(); i++) {

					int courseTime = eventList.get(i).getClassPeriod();

					// set color (defined in methods)
					paint.setStrokeWidth(0);

					int mint = getResources().getColor(R.color.mint_green);
					paint.setColor(mint);
					drawCourse(courseTime, canvas);
					canvas.save();

				}

				// turn the xhours on; for use in the WEEKLY fragment
				if (Globals.xHoursOn) {
					int green = getResources().getColor(R.color.dark_green);
					paint.setColor(green);
					System.out.println("drawing x hour....");

					for (int i = 0; i < eventList.size(); i++) {

						// canvas.save();
						int courseTime = eventList.get(i).getClassPeriod();
						drawXhour(courseTime, canvas);
						canvas.save();
					}
				}
			}

			// Draw friends data; for use in the FRIENDS fragment
			if (Globals.drawFriends) {

				System.out.println("drawing friemds");
				// this matrix: the arraylist within is the
				// array of a user's schedule / events, the outer array
				// is the array of total users
				drawingMatrix = Globals.drawingMatrix;
				paint.setStrokeWidth(0);

				if (drawingMatrix.size() > 0) {

					for (int i = 0; i < drawingMatrix.size(); i++) {
						ArrayList<Event> friendCourses = drawingMatrix.get(i);

						for (int j = 0; j < friendCourses.size(); j++) {
							// grab the color from the friend
							int color = friendCourses.get(j).getColor();
							paint.setColor(color);

							int course = friendCourses.get(j).getClassPeriod();
							drawCourse(course, canvas);
							canvas.save();
						}
					}
				}
			}

			System.out.println("draw friends x hours ? "
					+ Globals.friendXhoursOn);
			if (Globals.friendXhoursOn) {

				System.out.println("entered drawxhours");
				drawingMatrix = Globals.drawingMatrix;
				paint.setStrokeWidth(0);

				for (int i = 0; i < drawingMatrix.size(); i++) {
					ArrayList<Event> friendCourses = drawingMatrix.get(i);

					for (int j = 0; j < friendCourses.size(); j++) {
						// grab the color from the friend
						int color = friendCourses.get(j).getColor();
						paint.setColor(color);

						int course = friendCourses.get(j).getClassPeriod();
						drawXhour(course, canvas);
						canvas.save();
					}
				}

			}

			// Draw events; for use in the TERM fragment
			if (Globals.drawEventsOn) {

				drawingMatrix = Globals.drawingMatrix;
				paint.setStrokeWidth(0);

				for (int i = 0; i < drawingMatrix.size(); i++) {
					ArrayList<Event> friendCourses = drawingMatrix.get(i);

					for (int j = 0; j < friendCourses.size(); j++) {
						// grab the color from the friend
						int color = friendCourses.get(j).getColor();
						paint.setColor(color);

						long startTime = friendCourses.get(j).getStartTime();
						long endTime = friendCourses.get(j).getEndTime();
						long date = friendCourses.get(j).getDate();

						drawCustomEvent(startTime, endTime, date, canvas);
						canvas.save();
					}
				}

			}

		} catch (ParseException e) {
			System.out.println("Parse did not work.");
		}
	}

	// invalidate();
	// }

	//
	// } catch (StreamCorruptedException e) {
	// e.printStackTrace();
	// } catch (ClassNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }

	// debugging random color generator
	// paint.setStrokeWidth(0);
	// paint.setColor(rColor);
	// drawCourse(Globals.PERIOD_10, canvas);
	// }

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
	 * Draws specified course block.
	 * 
	 * @param period
	 * @param canvas
	 */
	public void drawCourse(int period, Canvas canvas) {

		Rect rect1, rect2, rect3, rect4;

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

			// add the rectangle objects, to be clicked on
			rect1 = new Rect(Globals.MONDAY_LEFT, Globals.TIME_8_TOP,
					Globals.MONDAY_RIGHT, Globals.TIME_8_BOTTOM);
			rect2 = new Rect(Globals.TUESDAY_LEFT, Globals.TIME_8_TOP,
					Globals.TUESDAY_RIGHT, Globals.TIME_8_BOTTOM);
			rect3 = new Rect(Globals.THURSDAY_LEFT, Globals.TIME_8_TOP,
					Globals.THURSDAY_RIGHT, Globals.TIME_8_BOTTOM);
			rect4 = new Rect(Globals.FRIDAY_LEFT, Globals.TIME_8_TOP,
					Globals.FRIDAY_RIGHT, Globals.TIME_8_BOTTOM);

			rectangles.add(rect1);
			rectangles.add(rect2);
			rectangles.add(rect3);
			rectangles.add(rect4);

			rectangleTimes.add(Globals.PERIOD_8);
			rectangleTimes.add(Globals.PERIOD_8);
			rectangleTimes.add(Globals.PERIOD_8);
			rectangleTimes.add(Globals.PERIOD_8);

			break;

		case Globals.PERIOD_9L:
			canvas.drawRect(Globals.MONDAY_LEFT, Globals.TIME_9L_TOP,
					Globals.MONDAY_RIGHT, Globals.TIME_9L_BOTTOM, paint);
			canvas.drawRect(Globals.WEDNESDAY_LEFT, Globals.TIME_9L_TOP,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_9L_BOTTOM, paint);
			canvas.drawRect(Globals.FRIDAY_LEFT, Globals.TIME_9L_TOP,
					Globals.FRIDAY_RIGHT, Globals.TIME_9L_BOTTOM, paint);

			rect1 = new Rect(Globals.MONDAY_LEFT, Globals.TIME_9L_TOP,
					Globals.MONDAY_RIGHT, Globals.TIME_9L_BOTTOM);
			rect2 = new Rect(Globals.WEDNESDAY_LEFT, Globals.TIME_9L_TOP,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_9L_BOTTOM);
			rect3 = new Rect(Globals.FRIDAY_LEFT, Globals.TIME_9L_TOP,
					Globals.FRIDAY_RIGHT, Globals.TIME_9L_BOTTOM);

			rectangles.add(rect1);
			rectangles.add(rect2);
			rectangles.add(rect3);

			rectangleTimes.add(Globals.PERIOD_9L);
			rectangleTimes.add(Globals.PERIOD_9L);
			rectangleTimes.add(Globals.PERIOD_9L);

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

			rect1 = new Rect(Globals.MONDAY_LEFT, Globals.TIME_9AM,
					Globals.MONDAY_RIGHT, Globals.TIME_9L_BOTTOM);
			rect2 = new Rect(Globals.TUESDAY_LEFT, Globals.TIME_9AM,
					Globals.TUESDAY_RIGHT, Globals.TIME_9L_BOTTOM);
			rect3 = new Rect(Globals.THURSDAY_LEFT, Globals.TIME_9AM,
					Globals.THURSDAY_RIGHT, Globals.TIME_9L_BOTTOM);
			rect4 = new Rect(Globals.FRIDAY_LEFT, Globals.TIME_9AM,
					Globals.FRIDAY_RIGHT, Globals.TIME_9L_BOTTOM);

			rectangles.add(rect1);
			rectangles.add(rect2);
			rectangles.add(rect3);
			rectangles.add(rect4);

			rectangleTimes.add(Globals.PERIOD_9S);
			rectangleTimes.add(Globals.PERIOD_9S);
			rectangleTimes.add(Globals.PERIOD_9S);
			rectangleTimes.add(Globals.PERIOD_9S);

			break;

		case Globals.PERIOD_10:
			canvas.drawRect(Globals.MONDAY_LEFT, Globals.TIME_10AM,
					Globals.MONDAY_RIGHT, Globals.TIME_10_BOTTOM, paint);
			canvas.drawRect(Globals.WEDNESDAY_LEFT, Globals.TIME_10AM,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_10_BOTTOM, paint);
			canvas.drawRect(Globals.FRIDAY_LEFT, Globals.TIME_10AM,
					Globals.FRIDAY_RIGHT, Globals.TIME_10_BOTTOM, paint);

			rect1 = new Rect(Globals.MONDAY_LEFT, Globals.TIME_10AM,
					Globals.MONDAY_RIGHT, Globals.TIME_10_BOTTOM);
			rect2 = new Rect(Globals.WEDNESDAY_LEFT, Globals.TIME_10AM,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_10_BOTTOM);
			rect3 = new Rect(Globals.FRIDAY_LEFT, Globals.TIME_10AM,
					Globals.FRIDAY_RIGHT, Globals.TIME_10_BOTTOM);

			rectangles.add(rect1);
			rectangles.add(rect2);
			rectangles.add(rect3);

			rectangleTimes.add(Globals.PERIOD_10);
			rectangleTimes.add(Globals.PERIOD_10);
			rectangleTimes.add(Globals.PERIOD_10);

			break;

		case Globals.PERIOD_10A:
			canvas.drawRect(Globals.TUESDAY_LEFT, Globals.TIME_10AM,
					Globals.TUESDAY_RIGHT, Globals.TIME_10A_BOTTOM, paint);
			canvas.drawRect(Globals.THURSDAY_LEFT, Globals.TIME_10AM,
					Globals.THURSDAY_RIGHT, Globals.TIME_10A_BOTTOM, paint);

			rect1 = new Rect(Globals.TUESDAY_LEFT, Globals.TIME_10AM,
					Globals.TUESDAY_RIGHT, Globals.TIME_10A_BOTTOM);
			rect2 = new Rect(Globals.THURSDAY_LEFT, Globals.TIME_10AM,
					Globals.THURSDAY_RIGHT, Globals.TIME_10A_BOTTOM);

			rectangles.add(rect1);
			rectangles.add(rect2);

			rectangleTimes.add(Globals.PERIOD_10A);
			rectangleTimes.add(Globals.PERIOD_10A);

			break;

		case Globals.PERIOD_11:
			canvas.drawRect(Globals.MONDAY_LEFT, Globals.TIME_11_TOP,
					Globals.MONDAY_RIGHT, Globals.TIME_11_BOTTOM, paint);
			canvas.drawRect(Globals.WEDNESDAY_LEFT, Globals.TIME_11_TOP,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_11_BOTTOM, paint);
			canvas.drawRect(Globals.FRIDAY_LEFT, Globals.TIME_11_TOP,
					Globals.FRIDAY_RIGHT, Globals.TIME_11_BOTTOM, paint);

			rect1 = new Rect(Globals.MONDAY_LEFT, Globals.TIME_11_TOP,
					Globals.MONDAY_RIGHT, Globals.TIME_11_BOTTOM);
			rect2 = new Rect(Globals.WEDNESDAY_LEFT, Globals.TIME_11_TOP,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_11_BOTTOM);
			rect3 = new Rect(Globals.FRIDAY_LEFT, Globals.TIME_11_TOP,
					Globals.FRIDAY_RIGHT, Globals.TIME_11_BOTTOM);

			rectangles.add(rect1);
			rectangles.add(rect2);
			rectangles.add(rect3);

			rectangleTimes.add(Globals.PERIOD_11);
			rectangleTimes.add(Globals.PERIOD_11);
			rectangleTimes.add(Globals.PERIOD_11);

			break;

		case Globals.PERIOD_12:
			canvas.drawRect(Globals.MONDAY_LEFT, Globals.TIME_12_TOP,
					Globals.MONDAY_RIGHT, Globals.TIME_12_BOTTOM, paint);
			canvas.drawRect(Globals.WEDNESDAY_LEFT, Globals.TIME_12_TOP,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_12_BOTTOM, paint);
			canvas.drawRect(Globals.FRIDAY_LEFT, Globals.TIME_12_TOP,
					Globals.FRIDAY_RIGHT, Globals.TIME_12_BOTTOM, paint);

			rect1 = new Rect(Globals.MONDAY_LEFT, Globals.TIME_12_TOP,
					Globals.MONDAY_RIGHT, Globals.TIME_12_BOTTOM);
			rect2 = new Rect(Globals.WEDNESDAY_LEFT, Globals.TIME_12_TOP,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_12_BOTTOM);
			rect3 = new Rect(Globals.FRIDAY_LEFT, Globals.TIME_12_TOP,
					Globals.FRIDAY_RIGHT, Globals.TIME_12_BOTTOM);

			rectangles.add(rect1);
			rectangles.add(rect2);
			rectangles.add(rect3);

			rectangleTimes.add(Globals.PERIOD_12);
			rectangleTimes.add(Globals.PERIOD_12);
			rectangleTimes.add(Globals.PERIOD_12);

			break;

		case Globals.PERIOD_2:
			canvas.drawRect(Globals.MONDAY_LEFT, Globals.TIME_2_TOP,
					Globals.MONDAY_RIGHT, Globals.TIME_2_BOTTOM, paint);
			canvas.drawRect(Globals.WEDNESDAY_LEFT, Globals.TIME_2_TOP,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_2_BOTTOM, paint);
			canvas.drawRect(Globals.FRIDAY_LEFT, Globals.TIME_2_TOP,
					Globals.FRIDAY_RIGHT, Globals.TIME_2_BOTTOM, paint);

			rect1 = new Rect(Globals.MONDAY_LEFT, Globals.TIME_2_TOP,
					Globals.MONDAY_RIGHT, Globals.TIME_2_BOTTOM);
			rect2 = new Rect(Globals.WEDNESDAY_LEFT, Globals.TIME_2_TOP,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_2_BOTTOM);
			rect3 = new Rect(Globals.FRIDAY_LEFT, Globals.TIME_2_TOP,
					Globals.FRIDAY_RIGHT, Globals.TIME_2_BOTTOM);

			rectangles.add(rect1);
			rectangles.add(rect2);
			rectangles.add(rect3);

			rectangleTimes.add(Globals.PERIOD_2);
			rectangleTimes.add(Globals.PERIOD_2);
			rectangleTimes.add(Globals.PERIOD_2);

			break;

		case Globals.PERIOD_2A:
			canvas.drawRect(Globals.TUESDAY_LEFT, Globals.TIME_2PM,
					Globals.TUESDAY_RIGHT, Globals.TIME_2A_BOTTOM, paint);
			canvas.drawRect(Globals.THURSDAY_LEFT, Globals.TIME_2PM,
					Globals.THURSDAY_RIGHT, Globals.TIME_2A_BOTTOM, paint);

			rect1 = new Rect(Globals.TUESDAY_LEFT, Globals.TIME_2PM,
					Globals.TUESDAY_RIGHT, Globals.TIME_2A_BOTTOM);
			rect2 = new Rect(Globals.THURSDAY_LEFT, Globals.TIME_2PM,
					Globals.THURSDAY_RIGHT, Globals.TIME_2A_BOTTOM);

			rectangles.add(rect1);
			rectangles.add(rect2);

			rectangleTimes.add(Globals.PERIOD_2A);
			rectangleTimes.add(Globals.PERIOD_2A);

			break;

		case Globals.PERIOD_3A:
			canvas.drawRect(Globals.MONDAY_LEFT, Globals.TIME_3PM,
					Globals.MONDAY_RIGHT, Globals.TIME_3A_BOTTOM, paint);
			canvas.drawRect(Globals.THURSDAY_LEFT, Globals.TIME_4PM,
					Globals.THURSDAY_RIGHT, Globals.TIME_3B_BOTTOM, paint);

			rect1 = new Rect(Globals.MONDAY_LEFT, Globals.TIME_3PM,
					Globals.MONDAY_RIGHT, Globals.TIME_3A_BOTTOM);
			rect2 = new Rect(Globals.THURSDAY_LEFT, Globals.TIME_4PM,
					Globals.THURSDAY_RIGHT, Globals.TIME_3B_BOTTOM);

			rectangles.add(rect1);
			rectangles.add(rect2);

			rectangleTimes.add(Globals.PERIOD_3A);
			rectangleTimes.add(Globals.PERIOD_3A);

			break;

		case Globals.PERIOD_3B:
			canvas.drawRect(Globals.TUESDAY_LEFT, Globals.TIME_4PM,
					Globals.TUESDAY_RIGHT, Globals.TIME_3B_BOTTOM, paint);
			canvas.drawRect(Globals.THURSDAY_LEFT, Globals.TIME_4PM,
					Globals.THURSDAY_RIGHT, Globals.TIME_3B_BOTTOM, paint);

			rect1 = new Rect(Globals.TUESDAY_LEFT, Globals.TIME_4PM,
					Globals.TUESDAY_RIGHT, Globals.TIME_3B_BOTTOM);
			rect2 = new Rect(Globals.THURSDAY_LEFT, Globals.TIME_4PM,
					Globals.THURSDAY_RIGHT, Globals.TIME_3B_BOTTOM);

			rectangles.add(rect1);
			rectangles.add(rect2);

			rectangleTimes.add(Globals.PERIOD_3B);
			rectangleTimes.add(Globals.PERIOD_3B);

			break;

		case Globals.ARR:
			break;

		default:
			break;
		}

		Globals.callOnDraw = false;
		// invalidate();
	}

	/**
	 * Draws the x-hour associated with the specified course period.
	 * 
	 * @param period
	 * @param canvas
	 */
	public void drawXhour(int period, Canvas canvas) {
		Rect rect;

		switch (period) {

		// 8
		case Globals.PERIOD_8:
			canvas.drawRect(Globals.WEDNESDAY_LEFT, Globals.TIME_8_TOP,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_8_BOTTOM, paint);
			rect = new Rect(Globals.WEDNESDAY_LEFT, Globals.TIME_8_TOP,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_8_BOTTOM);
			rectangles.add(rect);
			rectangleTimes.add(Globals.PERIOD_8_X);
			break;

		// 9L
		case Globals.PERIOD_9L:
			canvas.drawRect(Globals.THURSDAY_LEFT, Globals.TIME_9AM,
					Globals.THURSDAY_RIGHT, Globals.TIME_9L_BOTTOM, paint);
			rect = new Rect(Globals.THURSDAY_LEFT, Globals.TIME_9AM,
					Globals.THURSDAY_RIGHT, Globals.TIME_9L_BOTTOM);
			rectangles.add(rect);
			rectangleTimes.add(Globals.PERIOD_9L_X);
			break;

		// 9S
		case Globals.PERIOD_9S:
			canvas.drawRect(Globals.WEDNESDAY_LEFT, Globals.TIME_9AM,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_9L_BOTTOM, paint);
			rect = new Rect(Globals.WEDNESDAY_LEFT, Globals.TIME_9AM,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_9L_BOTTOM);
			rectangles.add(rect);
			rectangleTimes.add(Globals.PERIOD_9S_X);
			break;

		// 10
		case Globals.PERIOD_10:
			canvas.drawRect(Globals.THURSDAY_LEFT, Globals.TIME_12PM,
					Globals.THURSDAY_RIGHT, Globals.TIME_1250PM, paint);
			rect = new Rect(Globals.THURSDAY_LEFT, Globals.TIME_12PM,
					Globals.THURSDAY_RIGHT, Globals.TIME_1250PM);
			rectangles.add(rect);
			rectangleTimes.add(Globals.PERIOD_10_X);
			break;

		// 10A
		case Globals.PERIOD_10A:
			canvas.drawRect(Globals.WEDNESDAY_LEFT, Globals.TIME_3PM,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_2A_BOTTOM, paint);
			rect = new Rect(Globals.WEDNESDAY_LEFT, Globals.TIME_3PM,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_2A_BOTTOM);
			rectangles.add(rect);
			rectangleTimes.add(Globals.PERIOD_10A_X);
			break;

		// 11
		case Globals.PERIOD_11:
			canvas.drawRect(Globals.TUESDAY_LEFT, Globals.TIME_12PM,
					Globals.TUESDAY_RIGHT, Globals.TIME_1250PM, paint);
			rect = new Rect(Globals.TUESDAY_LEFT, Globals.TIME_12PM,
					Globals.TUESDAY_RIGHT, Globals.TIME_1250PM);
			rectangles.add(rect);
			rectangleTimes.add(Globals.PERIOD_11_X);
			break;

		// 12
		case Globals.PERIOD_12:
			canvas.drawRect(Globals.TUESDAY_LEFT, Globals.TIME_1PM,
					Globals.TUESDAY_RIGHT, Globals.TIME_150PM, paint);
			rect = new Rect(Globals.TUESDAY_LEFT, Globals.TIME_1PM,
					Globals.TUESDAY_RIGHT, Globals.TIME_150PM);
			rectangles.add(rect);
			rectangleTimes.add(Globals.PERIOD_12_X);
			break;

		// 2
		case 8:
			canvas.drawRect(Globals.THURSDAY_LEFT, Globals.TIME_1PM,
					Globals.THURSDAY_RIGHT, Globals.TIME_150PM, paint);
			rect = new Rect(Globals.THURSDAY_LEFT, Globals.TIME_1PM,
					Globals.THURSDAY_RIGHT, Globals.TIME_150PM);
			rectangles.add(rect);
			rectangleTimes.add(Globals.PERIOD_2_X);
			break;

		// 2A
		case Globals.PERIOD_2A:
			canvas.drawRect(Globals.WEDNESDAY_LEFT, Globals.TIME_415PM,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_505PM, paint);
			rect = new Rect(Globals.WEDNESDAY_LEFT, Globals.TIME_415PM,
					Globals.WEDNESDAY_RIGHT, Globals.TIME_505PM);
			rectangles.add(rect);
			rectangleTimes.add(Globals.PERIOD_2A_X);
			break;

		// 3A
		case Globals.PERIOD_3A:
			canvas.drawRect(Globals.MONDAY_LEFT, Globals.TIME_5PM,
					Globals.MONDAY_RIGHT, Globals.TIME_550PM, paint);
			rect = new Rect(Globals.MONDAY_LEFT, Globals.TIME_5PM,
					Globals.MONDAY_RIGHT, Globals.TIME_550PM);
			rectangles.add(rect);
			rectangleTimes.add(Globals.PERIOD_3A_X);
			break;

		// 3B
		case Globals.PERIOD_3B:
			canvas.drawRect(Globals.MONDAY_LEFT, Globals.TIME_5PM,
					Globals.MONDAY_RIGHT, Globals.TIME_550PM, paint);
			rect = new Rect(Globals.MONDAY_LEFT, Globals.TIME_5PM,
					Globals.MONDAY_RIGHT, Globals.TIME_550PM);
			rectangles.add(rect);
			rectangleTimes.add(Globals.PERIOD_3B_X);
			break;
		}

		Globals.xHoursOn = false;
		// invalidate();
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
