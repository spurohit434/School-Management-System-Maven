package com.wg.helper;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	static Scanner scanner = new Scanner(System.in);

	public static boolean isValid(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null) {
			return false;
		}
		return pat.matcher(email).matches();
	}

	public static boolean isValidString(String str) {
		if (str == null) {
			return false;
		}
		return !str.trim().isEmpty();
	}

	public static boolean isValidAge(int age) {
		if (age > 0 && age <= 150) {
			return true;
		}
		return false;
	}

	public static boolean isValidGender(String gender) {
		if (gender == null) {
			return false;
		}
		return gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F");
	}

	public static boolean isValidContactNo(String contactNo) {
		if (contactNo == null) {
			return false;
		}
		Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\d{10}$");
		Matcher matcher = PHONE_NUMBER_PATTERN.matcher(contactNo);

		return matcher.matches() && !contactNo.equals("0000000000");
	}

	public static boolean isValidRole(String role) {
		if (role == null) {
			return false;
		}
		return role.equalsIgnoreCase("Admin") || role.equalsIgnoreCase("Student") || role.equalsIgnoreCase("Faculty");
	}

	public static int getUserChoice() {
		while (!scanner.hasNextInt()) {
			System.out.println("Invalid input. Please enter a Valid Input");
			scanner.next();
			System.out.print("Enter your choice: ");
		}
		return scanner.nextInt();
	}

	public static boolean isValidDate(LocalDate endDate) {
		return false;
	}

	public static boolean isValidIndex(int index, int limit) {
		if (index > 0 && index <= limit) {
			return true;
		}
		return false;
	}

	public static LocalDate ValidateDate(LocalDate startDate) {
		LocalDate endDate = null;
		boolean validDate = false;

		while (!validDate) {
			System.out.println("Enter Leave till date (YYYY-MM-DD):");
			String dateString = scanner.next();
			scanner.nextLine();
			try {
				endDate = LocalDate.parse(dateString);
				if (endDate.isAfter(startDate)) {
					validDate = true;
				} else {
					System.out.println("Error: End date must be after the start date. Please enter a valid date.");
				}
			} catch (DateTimeParseException e) {
				System.out.println("Error: Invalid date format. Please enter the date in YYYY-MM-DD format.");
			}
		}
		return endDate;
	}

	private static final Pattern USERNAME_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{4,30}$");

	public static boolean isValidUsername(String username) {
		if (username == null) {
			return false;
		}
		Matcher matcher = USERNAME_PATTERN.matcher(username);
		return matcher.matches();
	}

	public static boolean isValidName(String name) {
		if (name == null) {
			return false;
		}
		// Trim the name to remove any leading or trailing whitespace
		String trimmedName = name.trim();
		// Check if the trimmed name is not empty and contains only alphabetic
		// characters
		return !trimmedName.isEmpty() && trimmedName.matches("[a-zA-Z]+");
	}

	private static final Pattern ROLL_NUMBER_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)[A-Za-z\\d]{4}$");

	public static boolean isValidRollNumber(String rollNo) {
		if (rollNo == null) {
			return false;
		}
		return rollNo.length() == 4 && ROLL_NUMBER_PATTERN.matcher(rollNo).matches();
	}

	public static boolean isValidAddress(String address) {
		if (address == null) {
			return false;
		}
		// Trim the address to remove any leading or trailing whitespace
		String trimmedAddress = address.trim();
		// Check if the trimmed address is not empty and is within a reasonable length
		// For example, consider a reasonable length between 5 and 100 characters
		if (trimmedAddress.isEmpty() || trimmedAddress.length() < 5 || trimmedAddress.length() > 100) {
			return false;
		}
		String addressPattern = "^[a-zA-Z0-9\\s,.-/]+$";
		return trimmedAddress.matches(addressPattern);
	}

}
