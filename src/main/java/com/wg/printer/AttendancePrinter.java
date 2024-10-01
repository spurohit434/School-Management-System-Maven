package com.wg.printer;

import java.util.List;

import com.wg.model.Attendance;

public class AttendancePrinter {

//	String studentId;
//	int standard;
//	LocalDate date;
//	Status status; // P, A

	// Define the format for headers and rows
	private static final String HEADER_FORMAT = "%-5s | %-30s | %-15s | %-15s";
	private static final String ROW_FORMAT = "%-5s | %-30s | %-15s | %-15s";

	private static final String HEADER_FORMAT1 = "%-5s | %-30s | %-15s | %-15s | %-15s";
	private static final String ROW_FORMAT1 = "%-5s | %-30s | %-15s | %-15s | %-15s";

	private static final String BOX_BORDER = "====================================================================================";
	private static final String DIVIDER_LINE = "------------------------------------------------------------------------------------";

	public static void printAttendanceDetails(List<Attendance> attendance) {
		if (attendance == null || attendance.size() == 0) {
			System.out.println("Nothing to print !");
			return;
		}
		System.out.println(BOX_BORDER);
		System.out.println(centerTextInBox("ATTENDANCE DETAILS"));
		System.out.println(BOX_BORDER);

		System.out.printf(HEADER_FORMAT, "S.No", "Standard", "Date", "Status");
		System.out.println();
		extractedFooter();
		int index = 1;
		for (Attendance attendee : attendance) {
			try {
				System.out.printf(ROW_FORMAT, index++, attendee.getStandard(), attendee.getDate(),
						attendee.getStatus());
				System.out.println();
				System.out.println(DIVIDER_LINE);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error occurred while printing account: " + attendee);
			}
		}
	}

	public static void printAttendanceDetails1(List<Attendance> attendance, String name, int index) {
		if (attendance == null || attendance.size() == 0) {
			System.out.println("Nothing to print !");
			return;
		}
		System.out.println();
		extractedFooter();
		// Print rows
		for (Attendance attendee : attendance) {
			try {
				// Print each account row
				System.out.printf(ROW_FORMAT1, index++, attendee.getStandard(), name, attendee.getDate(),
						attendee.getStatus());
				System.out.println();
//				extractedFooter();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error occurred while printing account: " + attendee);
			}
		}
	}

	public static void extractedFooter() {
		System.out.println(DIVIDER_LINE);
	}

	public static void extractedHeader() {
		System.out.println(BOX_BORDER);
		System.out.println(centerTextInBox("ATTENDANCE DETAILS"));
		System.out.println(BOX_BORDER);
		System.out.printf(HEADER_FORMAT1, "S.No", "Standard", "Name", "Date", "Status");

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