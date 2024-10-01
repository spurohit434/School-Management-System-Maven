package com.wg.printer;

public class MarksheetPrinter {
	private static final String HEADER_FORMAT = "%5s | %-15s | %-5s";

	private static final String BOX_BORDER = "============================================================================================";
	private static final String DIVIDER_LINE = "------------------------------------------------------------------------------------------";

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

	public static void extractedHeader() {
		System.out.println(BOX_BORDER);
		System.out.println(centerTextInBox("Marksheet"));
		System.out.println(BOX_BORDER);
		System.out.printf(HEADER_FORMAT, "S.No.", "Course Name", "Marks");
		System.out.println();
	}

	public static void extractedFooter() {
		System.out.println();
		System.out.println(DIVIDER_LINE);
	}

}
