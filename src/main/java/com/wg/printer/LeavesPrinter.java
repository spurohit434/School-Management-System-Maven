package com.wg.printer;

import java.util.List;

import com.wg.model.Leaves;

public class LeavesPrinter {
	private static final String HEADER_FORMAT = "%5s | %-30s | %-15s | %-15s | %-15s | %-15s | %-5s";
	private static final String ROW_FORMAT = "%5d | %-30s | %-15s | %-15s | %-15s | %-15s | %-5s";

	private static final String BOX_BORDER = "==================================================================================================================================================";
	private static final String DIVIDER_LINE = "------------------------------------------------------------------------------------------------------------------------------------------------";

	public static void printLeaves1(List<Leaves> Leaves, String name, String username, int index) {
		System.out.println();
		for (Leaves leave : Leaves) {
			try {
				System.out.printf(ROW_FORMAT, index++, leave.getContent(), name, username, leave.getStartDate(),
						leave.getEndDate(), leave.getStatus());
				System.out.println();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error occurred while printing user: " + leave);
			}
		}
	}

//	public static void printLeaves(List<Leaves> Leaves) {
//		System.out.println(BOX_BORDER);
//		System.out.println(centerTextInBox("Leaves DETAILS"));
//		System.out.println(BOX_BORDER);
//
//		System.out.printf(HEADER_FORMAT, "S.No.", "Content", "Start Date", "End Date", "Status");
//		System.out.println();
//		System.out.println(DIVIDER_LINE);
//
//		int index = 1;
//		for (Leaves leaves : Leaves) {
//			try {
//				System.out.printf(ROW_FORMAT, index++, leaves.getContent(), leaves.getStartDate(),
//						leaves.getStartDate(), leaves.getStatus());
//				System.out.println();
//				System.out.println(DIVIDER_LINE);
//			} catch (Exception e) {
//				e.printStackTrace();
//				System.out.println("Error occurred while printing user: " + leaves);
//			}
//		}
//	}

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

	public static void extractedHeader() {
		System.out.println(BOX_BORDER);
		System.out.println(centerTextInBox("Issue DETAILS"));
		System.out.println(BOX_BORDER);
		System.out.printf(HEADER_FORMAT, "S.No.", "Content", "Name", "Username", "Start Date", "End Date", "Status");
		System.out.println();
	}

	public static void extractedFooter() {
		System.out.println(DIVIDER_LINE);
	}
}
