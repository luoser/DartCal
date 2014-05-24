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
	
	// define every 15 minutes

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
	
	private Context context;
    private Paint mPaint = new Paint();
    private Canvas  mCanvas;

	public DrawView(Context context) {
		super(context);
		init(context);
	}

	// allows for insertion into xml
	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public DrawView(Context context, AttributeSet attrs, int defStyle) 
	{
	    super(context, attrs, defStyle);
	    init(context);
	}
	
	private void init(Context context) {
	    this.context = context;
	    setFocusable(true);
	    setFocusableInTouchMode(true);

	    mPaint = new Paint();
	    mPaint.setAntiAlias(true);
	    mPaint.setDither(true);
	    mPaint.setColor(Color.BLACK);
	    mPaint.setStyle(Paint.Style.STROKE);
	    mPaint.setStrokeJoin(Paint.Join.ROUND);
	    mPaint.setStrokeCap(Paint.Cap.ROUND);
	    mPaint.setStrokeWidth(6);
	    mCanvas = new Canvas();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec),
				measureHeight(heightMeasureSpec));
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
		super.onDraw(canvas);

		// fetch course information from the database
		int course1Time = Globals.PERIOD_9L;
		int course2Time = Globals.PERIOD_2A;
		int course3Time = Globals.PERIOD_11;
		int course4Time = Globals.PERIOD_3B;

		// default user color
		paint.setStrokeWidth(0);

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

			if (xHoursOn) {
				drawXhour(course1Time, canvas);
				drawXhour(course2Time, canvas);
				drawXhour(course3Time, canvas);
				drawXhour(course4Time, canvas);
				
				canvas.restore();
			}
		}
	}

	public void drawCourse(int period, Canvas canvas) {

		int mint = getResources().getColor(R.color.mint_green);
		paint.setColor(mint);

		switch (period) {

		case Globals.EARLY_DRILL:
			break;

		case Globals.PERIOD_8:
			canvas.drawRect(MONDAY_LEFT, TIME_8_TOP, MONDAY_RIGHT,
					TIME_8_BOTTOM, paint);
			canvas.drawRect(TUESDAY_LEFT, TIME_8_TOP, TUESDAY_RIGHT,
					TIME_8_BOTTOM, paint);
			canvas.drawRect(THURSDAY_LEFT, TIME_8_TOP, THURSDAY_RIGHT,
					TIME_8_BOTTOM, paint);
			canvas.drawRect(FRIDAY_LEFT, TIME_8_TOP, FRIDAY_RIGHT,
					TIME_8_BOTTOM, paint);
			break;

		case Globals.PERIOD_9L:
			canvas.drawRect(MONDAY_LEFT, TIME_9L_TOP, MONDAY_RIGHT,
					TIME_9L_BOTTOM, paint);
			canvas.drawRect(WEDNESDAY_LEFT, TIME_9L_TOP, WEDNESDAY_RIGHT,
					TIME_9L_BOTTOM, paint);
			canvas.drawRect(FRIDAY_LEFT, TIME_9L_TOP, FRIDAY_RIGHT,
					TIME_9L_BOTTOM, paint);
			break;

		case Globals.PERIOD_9S:
			canvas.drawRect(MONDAY_LEFT, TIME_9AM_TOP, MONDAY_RIGHT,
					TIME_9L_BOTTOM, paint);
			canvas.drawRect(TUESDAY_LEFT, TIME_9AM_TOP, TUESDAY_RIGHT,
					TIME_9L_BOTTOM, paint);
			canvas.drawRect(THURSDAY_LEFT, TIME_9AM_TOP, THURSDAY_RIGHT,
					TIME_9L_BOTTOM, paint);
			canvas.drawRect(FRIDAY_LEFT, TIME_9AM_TOP, FRIDAY_RIGHT,
					TIME_9L_BOTTOM, paint);
			break;

		case Globals.PERIOD_10:
			canvas.drawRect(MONDAY_LEFT, TIME_10AM_TOP, MONDAY_RIGHT,
					TIME_10_BOTTOM, paint);
			canvas.drawRect(WEDNESDAY_LEFT, TIME_10AM_TOP, WEDNESDAY_RIGHT,
					TIME_10_BOTTOM, paint);
			canvas.drawRect(FRIDAY_LEFT, TIME_10AM_TOP, FRIDAY_RIGHT,
					TIME_10_BOTTOM, paint);
			break;

		case Globals.PERIOD_10A:
			canvas.drawRect(TUESDAY_LEFT, TIME_10AM_TOP, TUESDAY_RIGHT,
					TIME_10A_BOTTOM, paint);
			canvas.drawRect(THURSDAY_LEFT, TIME_10AM_TOP, THURSDAY_RIGHT,
					TIME_10A_BOTTOM, paint);
			break;

		case Globals.PERIOD_11:
			canvas.drawRect(MONDAY_LEFT, TIME_11_TOP, MONDAY_RIGHT,
					TIME_11_BOTTOM, paint);
			canvas.drawRect(WEDNESDAY_LEFT, TIME_11_TOP, WEDNESDAY_RIGHT,
					TIME_11_BOTTOM, paint);
			canvas.drawRect(FRIDAY_LEFT, TIME_11_TOP, FRIDAY_RIGHT,
					TIME_11_BOTTOM, paint);
			break;

		case Globals.PERIOD_12:
			canvas.drawRect(MONDAY_LEFT, TIME_12_TOP, MONDAY_RIGHT,
					TIME_12_BOTTOM, paint);
			canvas.drawRect(WEDNESDAY_LEFT, TIME_12_TOP, WEDNESDAY_RIGHT,
					TIME_12_BOTTOM, paint);
			canvas.drawRect(FRIDAY_LEFT, TIME_12_TOP, FRIDAY_RIGHT,
					TIME_12_BOTTOM, paint);
			break;

		case Globals.PERIOD_2:
			canvas.drawRect(MONDAY_LEFT, TIME_2_TOP, MONDAY_RIGHT,
					TIME_2_BOTTOM, paint);
			canvas.drawRect(WEDNESDAY_LEFT, TIME_2_TOP, WEDNESDAY_RIGHT,
					TIME_2_BOTTOM, paint);
			canvas.drawRect(FRIDAY_LEFT, TIME_2_TOP, FRIDAY_RIGHT,
					TIME_2_BOTTOM, paint);
			break;

		case Globals.PERIOD_2A:
			canvas.drawRect(TUESDAY_LEFT, TIME_2PM_TOP, TUESDAY_RIGHT,
					TIME_2A_BOTTOM, paint);
			canvas.drawRect(THURSDAY_LEFT, TIME_2PM_TOP, THURSDAY_RIGHT,
					TIME_2A_BOTTOM, paint);
			break;

		case Globals.PERIOD_3A:
			canvas.drawRect(MONDAY_LEFT, TIME_3PM_TOP, MONDAY_RIGHT,
					TIME_3A_BOTTOM, paint);
			canvas.drawRect(THURSDAY_LEFT, TIME_4PM_TOP, THURSDAY_RIGHT,
					TIME_3B_BOTTOM, paint);
			break;

		case Globals.PERIOD_3B:
			canvas.drawRect(TUESDAY_LEFT, TIME_4PM_TOP, TUESDAY_RIGHT,
					TIME_3B_BOTTOM, paint);
			canvas.drawRect(THURSDAY_LEFT, TIME_4PM_TOP, THURSDAY_RIGHT,
					TIME_3B_BOTTOM, paint);
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

	public void drawXhour(int period, Canvas canvas) {

		int green = getResources().getColor(R.color.spring_green);
		paint.setColor(green);

		switch (period) {

		// 8
		case Globals.PERIOD_8:
			canvas.drawRect(WEDNESDAY_LEFT, TIME_8_TOP, WEDNESDAY_RIGHT,
					TIME_8_BOTTOM, paint);
			break;
			
			// 9L
		case Globals.PERIOD_9L:
			canvas.drawRect(THURSDAY_LEFT, TIME_9AM_TOP, THURSDAY_RIGHT,
					TIME_9L_BOTTOM, paint);
			break;

			// 9S
		case Globals.PERIOD_9S:
			canvas.drawRect(WEDNESDAY_LEFT, TIME_9AM_TOP, WEDNESDAY_RIGHT,
					TIME_9L_BOTTOM, paint);
			break;

			// 10
		case Globals.PERIOD_10:
			canvas.drawRect(THURSDAY_LEFT, TIME_12PM_TOP, THURSDAY_RIGHT,
					TIME_1250PM_BOTTOM, paint);
			break;

			// 10A
		case Globals.PERIOD_10A:
			canvas.drawRect(WEDNESDAY_LEFT, TIME_3PM_TOP, WEDNESDAY_RIGHT,
					TIME_2A_BOTTOM, paint);
			break;

			// 11
		case Globals.PERIOD_11:
			canvas.drawRect(TUESDAY_LEFT, TIME_12PM_TOP, TUESDAY_RIGHT,
					TIME_1250PM_BOTTOM, paint);
			break;

			// 12
		case Globals.PERIOD_12:
			canvas.drawRect(TUESDAY_LEFT, TIME_1PM_TOP, TUESDAY_RIGHT,
					TIME_150PM_BOTTOM, paint);
			break;

			// 2
		case 8:
			canvas.drawRect(THURSDAY_LEFT, TIME_1PM_TOP, THURSDAY_RIGHT,
					TIME_150PM_BOTTOM, paint);
			break;

			// 2A
		case Globals.PERIOD_2A:
			canvas.drawRect(WEDNESDAY_LEFT, TIME_415, WEDNESDAY_RIGHT,
					TIME_505, paint);
			break;

			// 3A
		case Globals.PERIOD_3A:
			canvas.drawRect(MONDAY_LEFT, TIME_5PM_TOP, MONDAY_RIGHT, TIME_550,
					paint);
			break;

			// 3B
		case Globals.PERIOD_3B:
			canvas.drawRect(MONDAY_LEFT, TIME_5PM_TOP, MONDAY_RIGHT, TIME_550,
					paint);
			break;
		}

	}

	// for weeks activity in TERM FRAGMENT
	// be able to set the start time
	public int setStartTime(long startTime) {

		int top = 0;

		// take the long time and translate to TOP variable for drawing

		return top;
	}

	public int setEndTime(long endTime) {

		int bottom;
		// take the long time and translate to BOTTOM variable for drawing

		return bottom = 0;
	}

	// takes
	public void setCustomEvent(long startTime, long endTime) {

		int top = setStartTime(startTime);
		int bottom = setEndTime(endTime);

	}

}
