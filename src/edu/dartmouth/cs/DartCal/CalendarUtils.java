/**
 * DartCal
 * File: CalendarUtils.java
 * Author: Lisa Luo
 * Modified: 5/21/14
 */

package edu.dartmouth.cs.DartCal;

// to help with parsing time from time periods
// and drawing blocks onto the calendar
public class CalendarUtils {

	// this function will get the data input by the user
	// from the edit profile activity
	public void retrieveCourseData() {
		
		

	}

	// this will draw the specific block for the period onto the calendar
	// spinner values are: Early Drill, 9L, 10, 10A, 11, 12, 2, 2A, 3A, 3B,
	// Afternoon Drill
	public void drawPeriod(int period) {

		switch (period) {
		case (0):
			// set start time
			
			// set end time
			
			// set days
			
		case (1):
			// set start time
			
			// set end time
			
			// set days
			
			// set xhours

		}

	}

	public void drawXhours(int period) {

	}

	// this function will draw the blocks onto the schedule.
	public void drawCourses(int course1, int course2, int course3, int course4) {

		// because the default / invalid will be -1
		if (course1 > 0)
			drawPeriod(course1);

		if (course2 > 0)
			drawPeriod(course2);

		if (course3 > 0)
			drawPeriod(course3);

		if (course4 > 0)
			drawPeriod(course4);

	}

}
