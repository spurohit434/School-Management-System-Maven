package com.wg.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import com.wg.helper.PasswordUtil;
import com.wg.helper.PasswordValidator;
import com.wg.helper.Validator;
import com.wg.model.Role;
import com.wg.model.User;
import com.wg.services.UserService;

public class UserController {

	private final UserService userService = new UserService();
	Scanner scanner = new Scanner(System.in);

	public UserController() {
	}

	public User authenticateUser(String username, String password) {
		if (username == null || password == null) {
			System.out.println("Null values entered");
			return null;
		}
		PasswordValidator passwordValidator = new PasswordValidator();
		boolean isValidPassword = passwordValidator.isValidPassword(password);
		if (!isValidPassword) {
			System.out.println("Enter valid password");
			return null;
		}
		return userService.authenticateUser(username, password);
	}

	public boolean addUser(String username, String name, int age, String password, String email, String input,
			LocalDate date, String contactNumber, int standard, String gender, String rollNo, int mentorOf) {
		Role role = null;
		try {
			role = Role.valueOf(input);
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid role. Please enter a valid role.");
		}

		String randomString = UUID.randomUUID().toString();
		int desiredLength = 8;
		if (desiredLength > randomString.length()) {
			desiredLength = randomString.length();
		}
		String userId = randomString.substring(0, desiredLength);

		if (userId == null || username == null || age < 0) {
			System.out.println("Enter valid credentials");
			return false;
		}

		User user = new User(userId, username, name, age, password, email, role, date, contactNumber, standard, gender,
				rollNo, mentorOf);
		userService.addUser(user);
		return true;
	}

	public User getUserById(String userId) {
		User user = userService.getUserById(userId);
		return user;
	}

	public List<User> getClassDetails(int standard) {
		List<User> users = userService.getClassDetails(standard);
		return users;
	}

	public boolean deleteUser(String userId) {
		boolean flag = false;
		flag = userService.deleteUser(userId);
		return flag;
	}

	public List<User> getAllUser() {
		List<User> list = userService.getAllUser();
		return list;
	}

	public User getUserByUsername(String username) {
		User user = userService.getUserByUsername(username);
		return user;
	}

	public void updateUser(String userId) {
		try {
			User user = userService.getUserById(userId);
			if (user != null) {
				if (user.getRole().toString().equals("Admin")) {
					System.out.println("Admin Can not be updated");
					return;
				}
				boolean continueUpdating = true;
				while (continueUpdating) {
					displayUpdateMenu();

					System.out.print("Select an option ");
					int choice = Validator.getUserChoice();

					String columnToUpdate = "";

					switch (choice) {
					case 1:
						columnToUpdate = "name";
						System.out.print("Enter name: ");
						String name = scanner.next();
						Boolean validName = false;
						while (!validName) {
							if (Validator.isValidName(name)) {
								validName = true;
							} else {
								System.out.println("Enter valid Name: ");
								name = scanner.next();
								validName = false;
							}
						}
						user.setName(name);
						break;
					case 2:
						columnToUpdate = "email";
						boolean validInput = false;
						String email = null;
						System.out.print("Enter email: ");
						while (!validInput) {
							try {
								email = scanner.next();
								if (Validator.isValid(email)) {
									validInput = true;
								} else {
									System.out.print("Enter valid email: ");
									validInput = false;
								}
							} catch (InputMismatchException e) {
								System.out.println("Invalid input. Please enter a valid Email: ");
								scanner.next();
							}
						}
						user.setEmail(email);
						break;
					case 3:
						columnToUpdate = "username";
						System.out.print("Enter new Username (AlphaNumeric(4-30) characters):");
						String username = scanner.next();
						boolean validUserName = false;
						while (!validUserName) {
							if (Validator.isValidUsername(username)) {
								validUserName = true;
							} else {
								System.out.println("Enter valid username: ");
								username = scanner.next();
								validUserName = false;
							}
						}
						user.setUsername(username);
						break;
					case 4:
						columnToUpdate = "password";
						System.out.print(
								"Enter new password: (Atleast one UpperCase character, one Special character, one Integer digit, 8 characters): ");
						PasswordValidator passwordValidator = new PasswordValidator();
						String password = null;
						boolean isValidPassword = false;
						while (!isValidPassword) {
							password = scanner.next();
							if (passwordValidator.isValidPassword(password)) {
								isValidPassword = true;
							} else {
								System.out.println("Enter valid password: ");
								isValidPassword = false;
							}
						}
						PasswordUtil passwordUtil = new PasswordUtil();
						password = passwordUtil.hashPassword(password);
						user.setPassword(password);
						break;
					case 5:
						columnToUpdate = "age";
						int age = 0;
						boolean validInputAge = false;
						while (!validInputAge) {
							System.out.print("Enter age: ");
							try {
								age = scanner.nextInt();
								if (Validator.isValidAge(age)) {
									validInputAge = true;
								} else {
									System.out.println("Invalid Age. Enter a valid Age: ");
									validInputAge = false; // If input is valid, exit loop
								}
							} catch (InputMismatchException e) {
								System.out.println("Invalid input. Please enter a valid integer for age.");
								scanner.next(); // Clear invalid input
							}
						}
						user.setAge(age);
						// scanner.nextLine();
						break;
					case 6:
						columnToUpdate = "gender";
						System.out.println("Enter the gender (M for Male, F for Female): ");
						String gender = null;
						boolean validGender = false;
						while (!validGender) {
							gender = scanner.next();
							if (gender.equals("M") || gender.equals("F") || gender.equals("m") || gender.equals("f")) {
								validGender = true;
							} else {
								System.out.println("Invalid Input. Please enter a valid gender (M, F): ");
								validGender = false;
							}
						}
						gender = gender.toUpperCase();
						user.setGender(gender);
						break;
					case 7:
						columnToUpdate = "contactNumber";
						System.out.print("Enter new contact number: ");
						String contactNumber = null;
						boolean validNumber = false;
						while (!validNumber) {
							contactNumber = scanner.next();
							if (Validator.isValidContactNo(contactNumber)) {
								validNumber = true;
							} else {
								System.out.println("Enter valid Mobile Number: ");
								validNumber = false;
							}
						}
						user.setContactNumber(contactNumber);
						break;
					case 8:
						columnToUpdate = "address";
						System.out.print("Enter new address: ");
						System.out.println(
								"Enter your address (5-100 characters, avoid special characters). Example: 123 Main St., Apt 4B/5, New York, NY 10001:");
						String address = null;
						boolean validAddress = false;
						while (!validAddress) {
							address = scanner.nextLine();
							if (Validator.isValidAddress(address)) {
								validAddress = true;
								break;
							} else {
								System.out.println("Invalid address. Please follow the guidelines and try again.");
								validAddress = false;
							}
						}
						user.setAddress(address);
						break;
					case 9:
						columnToUpdate = "role";
						String roleInput = "";
						boolean validInput11 = false;
						while (!validInput11) {
							System.out.print("Enter new role (Student, Faculty): ");
							try {
								roleInput = scanner.nextLine().trim();
								if (roleInput.equals("Admin") || roleInput.equals("admin")
										|| roleInput.equals("aDMIN")) {
									System.out.println("Role can not be Admin");
									validInput11 = false;
								} else {
									validInput11 = true;
								}
							} catch (InputMismatchException e) {
								System.out.println("Invalid input. Please enter a valid Role.");
								scanner.next();
							}
						}

						try {
							Role newRole = Role.valueOf(roleInput); // Convert input to Role enum
							user.setRole(newRole); // Update the role in User object
							System.out.println("Role updated successfully.");
						} catch (IllegalArgumentException e) {
							System.out.println("Invalid role. Please enter one of the following: Student, Faculty.");
						}
						break;
					case 10:
						columnToUpdate = "dob";
						System.out.print("Enter new Date of Birth (yyyy-mm-dd): ");
						LocalDate date = null;
						while (date == null) {
							try {
								boolean validDate = false;
								while (!validDate) {
									String input2 = scanner.next();
									date = LocalDate.parse(input2);
									if (date.isBefore(LocalDate.now())) {
										validDate = true;
									} else {
										validDate = false;
										System.out.println("Please Enter valid DOB Date");
									}
								}
							} catch (DateTimeParseException e) {
								System.out.println("Invalid date format. Please enter the date in yyyy-mm-dd format:");
							}
						}
						user.setDOB(date);
						break;
					case 11:
						if (user.getRole().toString().equals("Admin") || user.getRole().toString().equals("Faculty")) {
							System.out.println("This field can be updated for Student only");
							return;
						}
						columnToUpdate = "rollNo";
						String rollNo = null;
						System.out.println("Enter new roll number (Alpha Numeric 4 chars only):");
						boolean validRollNo = false;
						while (!validRollNo) {
							rollNo = scanner.next();
							if (Validator.isValidRollNumber(rollNo)) {
								validRollNo = true;
							} else {
								System.out.println("Enter valid roll number: ");
								validRollNo = false;
							}
						}
						user.setRollNo(rollNo);
						break;
					case 12:
						if (user.getRole().toString().equals("Admin") || user.getRole().toString().equals("Student")) {
							System.out.println("This field can be updated for Faculty only");
							return;
						}
						columnToUpdate = "mentorOf";
						int mentorOf = 0;
						boolean validStandard = false;
						System.out.println("Enter new mentor of field(1-12): ");
						while (!validStandard) {
							mentorOf = scanner.nextInt();
							try {
								if (mentorOf >= 1 && mentorOf <= 12) {
									validStandard = true;
								} else {
									System.out.println("Enter valid field(1-12): ");
									validStandard = false;
								}
							} catch (InputMismatchException e) {
								System.out.println("Invalid input. Please enter a valid integer for mentor of: ");
								scanner.next();
							}
						}
						user.setMentorOf(mentorOf);
						break;
					case 13:
						if (user.getRole().toString().equals("Admin") || user.getRole().toString().equals("Faculty")) {
							System.out.println("This field can be updated for Student only");
							return;
						}
						columnToUpdate = "standard";
						boolean validStandard1 = false;
						int standard = 0;
						while (!validStandard1) {
							System.out.println("Enter the standard (1-12): ");
							try {
								standard = scanner.nextInt(); // Try to read the integer
								if (standard >= 1 && standard <= 12) {
									validStandard1 = true; // Input is valid
								} else {
									System.out.println("Invalid input. Enter a number between 1 and 12.");
								}
							} catch (InputMismatchException e) {
								System.out.println("Invalid input. Please enter a valid integer for standard.");
								scanner.next(); // Clear the invalid input
							}
						}
						user.setStandard(standard);
						break;
					case 14:
						continueUpdating = false;
						break;
					default:
						System.out.println("Invalid option. Please try again.");
					}
					if (!continueUpdating) {
						break;
					}
					userService.updateUser(user, userId, columnToUpdate);
					System.out.println("User updated successfully.");
				}
			} else {
				System.out.println("User not found.");
			}
		} catch (Exception e) {
			System.out.println("Error updating user: " + e.getMessage());
		}
	}

	private void displayUpdateMenu() {
		System.out.println("\nWhich field would you like to update?");
		System.out.println("1. Name");
		System.out.println("2. Email");
		System.out.println("3. Username");
		System.out.println("4. Password");
		System.out.println("5. Age");
		System.out.println("6. Gender");
		System.out.println("7. Contact Number");
		System.out.println("8. Address");
		System.out.println("9. Role");
		System.out.println("10. Date of Birth");
		System.out.println("11. Roll Number");
		System.out.println("12. Mentor of Field");
		System.out.println("13. Standard");
		System.out.println("14. Done");
	}
}