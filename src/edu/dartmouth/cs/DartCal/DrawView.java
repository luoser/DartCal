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
	private int BLOCK_CEILING = 10;
	private int BLOCK_LEFT_BOUND = 30;	// for sundays
	
	// unrotated
	private int TIME_7AM_TOP = 19;
	private int TIME_9L_TOP = 144;
	private int TIME_9L_BOTTOM = 224;
	private int TIME_10AM_TOP = 237;
	private int TIME_10_BOTTOM = 317;
	private int TIME_10A_BOTTOM = 370;
	private int TIME_11_TOP = 330;
	private int TIME_11_BOTTOM = 410;
	private int TIME_12_TOP = 423;
	private int TIME_12_BOTTOM = 503;
	private int TIME_2PM_TOP = 530;
	private int TIME_2A_BOTTOM = 663;
	private int TIME_2_TOP = 513;
	private int TIME_2_BOTTOM = 593;
	private int TIME_3PM_TOP = 603;
	private int TIME_3A_BOTTOM = 735;
	
	private int MONDAY_LEFT = 117;
	private int MONDAY_RIGHT = 199;
	
	private int TUESDAY_LEFT = 224;
	private int TUESDAY_RIGHT = 306;
	
	private int WEDNESDAY_LEFT = 334;
	private int WEDNESDAY_RIGHT = 416;
	
	private int THURSDAY_LEFT = 441;
	private int THURSDAY_RIGHT = 523;
	
	private int FRIDAY_LEFT = 549;
	private int FRIDAY_RIGHT = 631;

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
		
//		setMeasuredDimension(500, 500);

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
		int course1Time = 2; // 10
		int course2Time = 3; // 10A
		int course3Time = 1;
		int course4Time = 4;

		// default user color
		int mint = getResources().getColor(R.color.mint_green);

		paint.setStrokeWidth(0);
		paint.setColor(mint);

		// need to distinguish how long to draw the time blocks
		// also distinguish rotation
		if (!isRotated) {
			
			// draw course 1
			drawCourse(course1Time, canvas);
			
			// draw course 2
			drawCourse(course2Time, canvas);

			// draw course 3
			drawCourse(course3Time, canvas);

			// draw course 4
			drawCourse(course4Time, canvas);
			
			drawCourse(0, canvas);
			drawCourse(5, canvas);
			drawCourse(6, canvas);
			drawCourse(7, canvas);
			drawCourse(8, canvas);
			drawCourse(9, canvas);
			drawCourse(10, canvas);
			drawCourse(11, canvas);

		}

	}

	public void drawCourse(int period, Canvas canvas) {
		switch (period) {

		// Early drill
		case 0:
			

		// 9L
		case 1:
			canvas.drawRect(MONDAY_LEFT, TIME_9L_TOP, MONDAY_RIGHT, TIME_9L_BOTTOM, paint);
			canvas.drawRect(WEDNESDAY_LEFT, TIME_9L_TOP, WEDNESDAY_RIGHT, TIME_9L_BOTTOM, paint);
			canvas.drawRect(FRIDAY_LEFT, TIME_9L_TOP, FRIDAY_RIGHT, TIME_9L_BOTTOM, paint);

		// 10
		case 2:
			// monday
			canvas.drawRect(MONDAY_LEFT, TIME_10AM_TOP, MONDAY_RIGHT, TIME_10_BOTTOM, paint);
//			canvas.drawText("10:00 AM", MONDAY_LEFT, TIME_10AM_TOP - 5, paint);
			
			// wednesday
			canvas.drawRect(WEDNESDAY_LEFT, TIME_10AM_TOP, WEDNESDAY_RIGHT, TIME_10_BOTTOM, paint);
			
			// friday
			canvas.drawRect(FRIDAY_LEFT, TIME_10AM_TOP, FRIDAY_RIGHT, TIME_10_BOTTOM, paint);
			
		// 10A
		case 3:
			
			canvas.drawRect(TUESDAY_LEFT, TIME_10AM_TOP, TUESDAY_RIGHT, TIME_10A_BOTTOM, paint);
			canvas.drawRect(THURSDAY_LEFT, TIME_10AM_TOP, THURSDAY_RIGHT, TIME_10A_BOTTOM, paint);
		
		// 11	
		case 4:
			canvas.drawRect(MONDAY_LEFT, TIME_11_TOP, MONDAY_RIGHT, TIME_11_BOTTOM, paint);
			canvas.drawRect(WEDNESDAY_LEFT, TIME_11_TOP, WEDNESDAY_RIGHT, TIME_11_BOTTOM, paint);
			canvas.drawRect(FRIDAY_LEFT, TIME_11_TOP, FRIDAY_RIGHT, TIME_11_BOTTOM, paint);
		
		// 12
		case 5:
			canvas.drawRect(MONDAY_LEFT, TIME_12_TOP, MONDAY_RIGHT, TIME_12_BOTTOM, paint);
			
		// 2	
		case 6:
			canvas.drawRect(MONDAY_LEFT, TIME_2_TOP, MONDAY_RIGHT, TIME_2_BOTTOM, paint);

			
		// 2A
		case 7:
			canvas.drawRect(TUESDAY_LEFT, TIME_2PM_TOP, TUESDAY_RIGHT, TIME_2A_BOTTOM, paint);
			canvas.drawRect(THURSDAY_LEFT, TIME_2PM_TOP, THURSDAY_RIGHT, TIME_2A_BOTTOM, paint);
		
		// 3A	
		case 8:
			canvas.drawRect(MONDAY_LEFT,  TIME_3PM_TOP, MONDAY_RIGHT, TIME_3A_BOTTOM, paint);
			
		// 3B	
		case 9:
			
		// Afternoon Drill
		case 10:
		
		// Evening Drill
		case 11:
			
		default:
			break;
		}

	}
	
}
