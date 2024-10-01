package com.wg.helper;

public class PasswordValidator {
	public boolean isValidPassword(String password) {
		boolean checkUpperCase = false;
		boolean checkSpecialCharacter = false;
		boolean checkDigit = false;

		if (password.length() < 8) {
			return false;
		}
		for (char character : password.toCharArray()) {
			if (Character.isUpperCase(character)) {
				checkUpperCase = true;
			}
			if (Character.isDigit(character)) {
				checkDigit = true;
			}
			if (password.contains("$") || password.contains("_") || password.contains("@") || password.contains("?")) {
				checkSpecialCharacter = true;
			}
		}

		if (checkUpperCase == true && checkSpecialCharacter == true && checkDigit == true) {
			return true;
		}
		return false;
	}
}