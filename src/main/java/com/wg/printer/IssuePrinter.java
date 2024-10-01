package com.wg.printer;

import java.util.List;

import com.wg.model.Issue;

public class IssuePrinter {
	private static final String HEADER_FORMAT = "%5s | %-30s | %-15s | %-5s";
	private static final String ROW_FORMAT = "%5d | %-30s | %-15s | %15s";

	private static final String HEADER_FORMAT1 = "%5s | %-30s | %-15s | %-15s | %-15s  | %-5s";
	private static final String ROW_FORMAT1 = "%5d | %-30s | %-15s | %-15s |  %-15s | %15s";

	private static final String BOX_BORDER = "==================================================================================================================================================";
	private static final String DIVIDER_LINE = "------------------------------------------------------------------------------------------------------------------------------------------------";

	public static void printIssues(List<Issue> issues) {
		System.out.println(BOX_BORDER);
		System.out.println(centerTextInBox("Issue DETAILS"));
		System.out.println(BOX_BORDER);

		System.out.printf(HEADER_FORMAT, "S.No.", "Issue", "Issue Status", "Date");
		System.out.println();
		System.out.println(DIVIDER_LINE);

		int index = 1;
		for (Issue issue : issues) {
			try {
				System.out.printf(ROW_FORMAT, index++, issue.getMessage(), issue.getStatus(), issue.getCreatedAt());
				System.out.println();
				System.out.println(DIVIDER_LINE);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error occurred while printing user: " + issue);
			}
		}
	}

	private static String centerTextInBox(String text) {
		int boxWidth = BOX_BORDER.length();
		int textLength = text.length();
		int padding = (boxWidth - textLength) / 2;
		StringBuilder centeredText = new StringBuilder();
		centeredText.append(" ".repeat(padding));
		centeredText.append(text);
		centeredText.append(" ".repeat(padding));
		while (centeredText.length() < boxWidth) {
			centeredText.append(" ");
		}
		return centeredText.toString();
	}

	public static void printIssues1(List<Issue> i, String name, String username, int index) {
		System.out.println();
		for (Issue issue : i) {
			try {
				System.out.printf(ROW_FORMAT1, index++, issue.getMessage(), name, username, issue.getStatus(),
						issue.getCreatedAt());
				System.out.println();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error occurred while printing user: " + issue);
			}
		}
	}

	public static void extractedHeader() {
		System.out.println(BOX_BORDER);
		System.out.println(centerTextInBox("Issue DETAILS"));
		System.out.println(BOX_BORDER);
		System.out.printf(HEADER_FORMAT1, "S.No.", "Issue", "Name", "Username", "Issue Status", "Date");
		System.out.println();
	}

	public static void extractedFooter() {
		System.out.println(DIVIDER_LINE);
	}
}
