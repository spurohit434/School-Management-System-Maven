package com.wg.printer;

import java.util.List;

import com.wg.model.Course;

public class CoursePrinter {

	// Define the format for headers and rows
	private static final String HEADER_FORMAT = "%-5s | %-30s | %-15s ";
	private static final String ROW_FORMAT = "%-5s | %-30s | %-15s";

	private static final String BOX_BORDER = "============================================================================";
	private static final String DIVIDER_LINE = "--------------------------------------------------------------------------";

	public static void printCourseDetails(List<Course> course) {
		if (course == null || course.size() == 0) {
			System.out.println("Nothing to print !");
			return;
		}
		System.out.println(BOX_BORDER);
		System.out.println(centerTextInBox("COURSE DETAILS"));
		System.out.println(BOX_BORDER);

		// Print header
		System.out.printf(HEADER_FORMAT, "S.No", "Course Name", "Standard");
		System.out.println();
		System.out.println(DIVIDER_LINE);
		int index = 1;
		// Print rows
		for (Course courses : course) {
			try {
				// Print each account row
				System.out.printf(ROW_FORMAT, index++, courses.getCourseName(), courses.getStandard());
				System.out.println();
				System.out.println(DIVIDER_LINE);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error occurred while printing account: " + courses);
			}
		}
	}

	// Helper method to center the text within a box
	private static String centerTextInBox(String text) {
		int boxWidth = BOX_BORDER.length();
		int textLength = text.length();
		int padding = (boxWidth - textLength) / 2;

		// Creating a line with centered text surrounded by spaces
		StringBuilder centeredText = new StringBuilder();
		centeredText.append(" ".repeat(padding));
		centeredText.append(text);
		centeredText.append(" ".repeat(padding));

		// Ensure the line is exactly as wide as the box, accounting for odd width
		while (centeredText.length() < boxWidth) {
			centeredText.append(" ");
		}

		return centeredText.toString();
	}
}