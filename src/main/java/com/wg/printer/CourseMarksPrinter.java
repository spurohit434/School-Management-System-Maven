package com.wg.printer;

import java.util.List;

import com.wg.model.CourseMarks;

public class CourseMarksPrinter {
	private static final String HEADER_FORMAT = "%-5s | %-30s | %-15s ";
	private static final String ROW_FORMAT = "%-5s | %-30s | %-15s";

	private static final String BOX_BORDER = "============================================================================";
	private static final String DIVIDER_LINE = "--------------------------------------------------------------------------";

//	private String userId;
//	private String courseId;
//	private double marks;

	public static void printCourseMarksDetails(List<CourseMarks> course, String courseName, int index) {
		if (course == null || course.size() == 0) {
			System.out.println("Nothing to print !");
			return;
		}
		// extractedHeader();
		System.out.println();
		// extractedFooter();
		// Print rows
		for (CourseMarks courses : course) {
			try {
				// Print each account row
				System.out.printf(ROW_FORMAT, index++, courseName, courses.getMarks());
				System.out.println();
				// extractedFooter();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error occurred while printing account: " + course);
			}
		}
	}

	public static void extractedFooter() {
		System.out.println(DIVIDER_LINE);
	}

	public static void extractedHeader() {
		System.out.println(BOX_BORDER);
		System.out.println(centerTextInBox("MARKS DETAILS"));
		System.out.println(BOX_BORDER);
		System.out.printf(HEADER_FORMAT, "S.No", "Course Name", "Marks");
	}

	private static String centerTextInBox(String text) {
		int boxWidth = BOX_BORDER.length();
		int textLength = text.length();
		int padding = (boxWidth - textLength) / 2;

		// Creating a line with centered text surrounded by spaces
		StringBuilder centeredText = new StringBuilder();
		centeredText.append(" ".repeat(padding));
		centeredText.append(text);
		centeredText.append(" ".repeat(padding));

		while (centeredText.length() < boxWidth) {
			centeredText.append(" ");
		}

		return centeredText.toString();
	}

}