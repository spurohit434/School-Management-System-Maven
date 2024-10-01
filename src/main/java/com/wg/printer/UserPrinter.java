package com.wg.printer;

import java.util.List;

import com.wg.model.User;

public class UserPrinter {
//
//	private static final String HEADER_FORMAT = "%5s | %-15s | %-15s | %-5s | %-20s | %-15s | %-15s";
//	private static final String ROW_FORMAT = "%5d | %-15s | %-15s | %d | %-20s | %-15s | %-15s";

	private static final String HEADER_FORMAT = "%5s | %-15s | %-15s | %3s | %-30s | %-10s | %-15s | %-10s | %-10s | %-15s";
	private static final String ROW_FORMAT = "%5d | %-15s | %-15s | %3d | %-30s | %-10s | %-15s | %-10s | %-10s | %-15s";

	private static final String BOX_BORDER = "==================================================================================================================================================";
	private static final String DIVIDER_LINE = "------------------------------------------------------------------------------------------------------------------------------------------------";

	public static void printUsers(List<User> users) {
		System.out.println(BOX_BORDER);
		System.out.println(centerTextInBox("USER DETAILS"));
		System.out.println(BOX_BORDER);

		System.out.printf(HEADER_FORMAT, "S.No.", "Name", "Username", "Age", "Email", "Gender", "Contact Number",
				"Role", "Standard", "mentorOf");
		System.out.println();
		System.out.println(DIVIDER_LINE);

		int index = 1;
		for (User user : users) {
			try {
				System.out.printf(ROW_FORMAT, index++, user.getName(), user.getUsername(), user.getAge(),
						user.getEmail(), user.getGender(), user.getContactNumber(), user.getRole().toString(),
						user.getStandard(), user.getMentorOf());
				System.out.println();
				System.out.println(DIVIDER_LINE);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error occurred while printing user: " + user);
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
}