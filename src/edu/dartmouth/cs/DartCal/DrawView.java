/**
 * DartCal
 * File: DrawView.java
 * Author: Lisa Luo
 * Modified: 5/21/14
 */

package edu.dartmouth.cs.DartCal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

// class to help draw things
public class DrawView extends View {
	Paint paint = new Paint();

	private boolean isRotated = MainActivity.isRotated;
	private boolean xHoursOn = WeeklyFragment.xHoursOn;
	private int BLOCK_CEILING = 10;
	private int BLOCK_LEFT_BOUND = 30; // for sundays

	// unrotated time blocks
	private static int TIME_7AM_TOP = 19;
	private static int TIME_8_TOP = 70;
	private static int TIME_8_BOTTOM = 137;
	private static int TIME_9L_TOP = 147;
	private static int TIME_9L_BOTTOM = 227;
	private static int TIME_9AM_TOP = 164;
	private static int TIME_10AM_TOP = 237;
	private static int TIME_10_BOTTOM = 317;
	private static int TIME_10A_BOTTOM = 372;
	private static int TIME_11_TOP = 327;
	private static int TIME_11_BOTTOM = 407;
	private static int TIME_12_TOP = 417;
	private static int TIME_12PM_TOP = 382;
	private static int TIME_1250PM_BOTTOM = 449;
	private static int TIME_1PM_TOP = 456;
	private static int TIME_150PM_BOTTOM = 523;
	private static int TIME_12_BOTTOM = 497;
	private static int TIME_2PM_TOP = 530;
	private static int TIME_2A_BOTTOM = 665;
	private static int TIME_415 = 675;
	private static int TIME_505 = 739;
	private static int TIME_2_TOP = 507;
	private static int TIME_2_BOTTOM = 592;
	private static int TIME_3PM_TOP = 602;
	private static int TIME_3A_BOTTOM = 737;
	private static int TIME_5PM_TOP = 747;
	private static int TIME_550 = 810;
	private static int TIME_4PM_TOP = 675;
	private static int TIME_3B_BOTTOM = 808;

	// Day boundaries
	private static int MONDAY_LEFT = 117;
	private static int MONDAY_RIGHT = 199;
	private static int TUESDAY_LEFT = 224;
	private static int TUESDAY_RIGHT = 306;
	private static int WEDNESDAY_LEFT = 334;
	private static int WEDNESDAY_RIGHT = 416;
	private static int THURSDAY_LEFT = 441;
	private static int THURSDAY_RIGHT = 523;
	private static int FRIDAY_LEFT = 549;
	private static int FRIDAY_RIGHT = 631;

	public DrawView(Context context) {
		super(context);
		
	}

	// allows for insertion into xml
	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec),
				measureHeight(heightMeasureSpec));

		// setMeasuredDimension(500, 500);

	}

	/**
	 * Determines the width of this view
	 * 
	 * @param measureSpec
	 *            A measureSpec packed into an int
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
	 *            A measureSpec packed into an int
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
		return result;
	}

	@Override
	public void onDraw(Canvas canvas) {
		
		// fetch course information from the database
		int course1Time = 2;
		int course2Time = 3;
		int course3Time = 1;
		int course4Time = 4;

		// default user color
		paint.setStrokeWidth(0);

		// need to distinguish how long to draw the time blocks
		// also distinguish rotation
		if (!isRotated) {
	

			// draw course 1
			drawCourse(course1Time, canvas);
//			drawXhour(course1Time, canvas);

			// draw course 2
			drawCourse(course2Time, canvas);
//			drawXhour(course2Time, canvas);

			// draw course 3
			drawCourse(course3Time, canvas);
//			drawXhour(course3Time, canvas);

			// draw course 4
			drawCourse(course4Time, canvas);
//			drawXhour(course4Time, canvas);

			drawCourse(0, canvas);
			drawCourse(5, canvas);
			drawCourse(6, canvas);
			drawCourse(7, canvas);
			drawCourse(8, canvas);
			drawCourse(9, canvas);
			drawCourse(10, canvas);
			drawCourse(11, canvas);
			drawCourse(12, canvas);
			drawCourse(13, canvas);
						
			
			invalidate();
			if (xHoursOn){
				displayXhours(canvas);
				canvas.restore();
			}

		}
		
	}

	public void drawCourse(int period, Canvas canvas) {
				
		int mint = getResources().getColor(R.color.mint_green);
		paint.setColor(mint);

		switch (period) {

		// Early drill
		case 0:

			// 8
		case 1:
			canvas.drawRect(MONDAY_LEFT, TIME_8_TOP, MONDAY_RIGHT,
					TIME_8_BOTTOM, paint);
			canvas.drawRect(TUESDAY_LEFT, TIME_8_TOP, TUESDAY_RIGHT,
					TIME_8_BOTTOM, paint);
			canvas.drawRect(THURSDAY_LEFT, TIME_8_TOP, THURSDAY_RIGHT,
					TIME_8_BOTTOM, paint);
			canvas.drawRect(FRIDAY_LEFT, TIME_8_TOP, FRIDAY_RIGHT,
					TIME_8_BOTTOM, paint);

			// 9L
		case 2:
			canvas.drawRect(MONDAY_LEFT, TIME_9L_TOP, MONDAY_RIGHT,
					TIME_9L_BOTTOM, paint);
			canvas.drawRect(WEDNESDAY_LEFT, TIME_9L_TOP, WEDNESDAY_RIGHT,
					TIME_9L_BOTTOM, paint);
			canvas.drawRect(FRIDAY_LEFT, TIME_9L_TOP, FRIDAY_RIGHT,
					TIME_9L_BOTTOM, paint);

			// 9S
		case 3:
			canvas.drawRect(MONDAY_LEFT, TIME_9AM_TOP, MONDAY_RIGHT,
					TIME_9L_BOTTOM, paint);
			canvas.drawRect(TUESDAY_LEFT, TIME_9AM_TOP, TUESDAY_RIGHT,
					TIME_9L_BOTTOM, paint);
			canvas.drawRect(THURSDAY_LEFT, TIME_9AM_TOP, THURSDAY_RIGHT,
					TIME_9L_BOTTOM, paint);
			canvas.drawRect(FRIDAY_LEFT, TIME_9AM_TOP, FRIDAY_RIGHT,
					TIME_9L_BOTTOM, paint);

			// 10
		case 4:
			canvas.drawRect(MONDAY_LEFT, TIME_10AM_TOP, MONDAY_RIGHT,
					TIME_10_BOTTOM, paint);
			// canvas.drawText("10:00 AM", MONDAY_LEFT, TIME_10AM_TOP - 5,
			// paint);
			canvas.drawRect(WEDNESDAY_LEFT, TIME_10AM_TOP, WEDNESDAY_RIGHT,
					TIME_10_BOTTOM, paint);
			canvas.drawRect(FRIDAY_LEFT, TIME_10AM_TOP, FRIDAY_RIGHT,
					TIME_10_BOTTOM, paint);

			// 10A
		case 5:
			canvas.drawRect(TUESDAY_LEFT, TIME_10AM_TOP, TUESDAY_RIGHT,
					TIME_10A_BOTTOM, paint);
			canvas.drawRect(THURSDAY_LEFT, TIME_10AM_TOP, THURSDAY_RIGHT,
					TIME_10A_BOTTOM, paint);

			// 11
		case 6:
			canvas.drawRect(MONDAY_LEFT, TIME_11_TOP, MONDAY_RIGHT,
					TIME_11_BOTTOM, paint);
			canvas.drawRect(WEDNESDAY_LEFT, TIME_11_TOP, WEDNESDAY_RIGHT,
					TIME_11_BOTTOM, paint);
			canvas.drawRect(FRIDAY_LEFT, TIME_11_TOP, FRIDAY_RIGHT,
					TIME_11_BOTTOM, paint);

			// 12
		case 7:
			canvas.drawRect(MONDAY_LEFT, TIME_12_TOP, MONDAY_RIGHT,
					TIME_12_BOTTOM, paint);
			canvas.drawRect(WEDNESDAY_LEFT, TIME_12_TOP, WEDNESDAY_RIGHT,
					TIME_12_BOTTOM, paint);
			canvas.drawRect(FRIDAY_LEFT, TIME_12_TOP, FRIDAY_RIGHT,
					TIME_12_BOTTOM, paint);

			// 2
		case 8:
			canvas.drawRect(MONDAY_LEFT, TIME_2_TOP, MONDAY_RIGHT,
					TIME_2_BOTTOM, paint);
			canvas.drawRect(WEDNESDAY_LEFT, TIME_2_TOP, WEDNESDAY_RIGHT,
					TIME_2_BOTTOM, paint);
			canvas.drawRect(FRIDAY_LEFT, TIME_2_TOP, FRIDAY_RIGHT,
					TIME_2_BOTTOM, paint);

			// 2A
		case 9:
			canvas.drawRect(TUESDAY_LEFT, TIME_2PM_TOP, TUESDAY_RIGHT,
					TIME_2A_BOTTOM, paint);
			canvas.drawRect(THURSDAY_LEFT, TIME_2PM_TOP, THURSDAY_RIGHT,
					TIME_2A_BOTTOM, paint);

			// 3A
		case 10:
			canvas.drawRect(MONDAY_LEFT, TIME_3PM_TOP, MONDAY_RIGHT,
					TIME_3A_BOTTOM, paint);
			canvas.drawRect(THURSDAY_LEFT, TIME_4PM_TOP, THURSDAY_RIGHT,
					TIME_3B_BOTTOM, paint);

			// 3B
		case 11:
			canvas.drawRect(TUESDAY_LEFT, TIME_4PM_TOP, TUESDAY_RIGHT,
					TIME_3B_BOTTOM, paint);
			canvas.drawRect(THURSDAY_LEFT, TIME_4PM_TOP, THURSDAY_RIGHT,
					TIME_3B_BOTTOM, paint);

			// Afternoon Drill
		case 12:

			// Evening Drill
		case 13:

		default:
			break;
		}
		
		invalidate();
	}
	
	public void displayXhours(Canvas canvas){
		
		drawXhour(1, canvas);
		drawXhour(2, canvas);
		drawXhour(3, canvas);
		drawXhour(4, canvas);
		drawXhour(5, canvas);
		drawXhour(6, canvas);

		invalidate();
	}

	public void drawXhour(int period, Canvas canvas) {
		
		int green = getResources().getColor(R.color.spring_green);
		paint.setColor(green);

		switch (period) {

		// 8
		case 1:
			canvas.drawRect(WEDNESDAY_LEFT, TIME_8_TOP, WEDNESDAY_RIGHT,
					TIME_8_BOTTOM, paint);
			// 9L
		case 2:
			canvas.drawRect(THURSDAY_LEFT, TIME_9AM_TOP, THURSDAY_RIGHT,
					TIME_9L_BOTTOM, paint);

			// 9S
		case 3:
			canvas.drawRect(WEDNESDAY_LEFT, TIME_9AM_TOP, WEDNESDAY_RIGHT,
					TIME_9L_BOTTOM, paint);

			// 10
		case 4:
			canvas.drawRect(THURSDAY_LEFT, TIME_12PM_TOP, THURSDAY_RIGHT,
					TIME_1250PM_BOTTOM, paint);

			// 10A
		case 5:
			canvas.drawRect(WEDNESDAY_LEFT, TIME_3PM_TOP, WEDNESDAY_RIGHT,
					TIME_2A_BOTTOM, paint);

			// 11
		case 6:
			canvas.drawRect(TUESDAY_LEFT, TIME_12PM_TOP, TUESDAY_RIGHT,
					TIME_1250PM_BOTTOM, paint);

			// 12
		case 7:
			canvas.drawRect(TUESDAY_LEFT, TIME_1PM_TOP, TUESDAY_RIGHT,
					TIME_150PM_BOTTOM, paint);

			// 2
		case 8:
			canvas.drawRect(THURSDAY_LEFT, TIME_1PM_TOP, THURSDAY_RIGHT,
					TIME_150PM_BOTTOM, paint);

			// 2A
		case 9:
			canvas.drawRect(WEDNESDAY_LEFT, TIME_415, WEDNESDAY_RIGHT,
					TIME_505, paint);

			// 3A
		case 10:
			canvas.drawRect(MONDAY_LEFT, TIME_5PM_TOP, MONDAY_RIGHT, TIME_550,
					paint);

			// 3B
		case 11:
			canvas.drawRect(MONDAY_LEFT, TIME_5PM_TOP, MONDAY_RIGHT, TIME_550,
					paint);
		}

	}

}
