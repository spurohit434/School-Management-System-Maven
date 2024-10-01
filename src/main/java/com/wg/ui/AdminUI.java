package com.wg.ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.wg.app.App;
import com.wg.constants.StandardWiseCourse;
import com.wg.constants.StringConstants;
import com.wg.helper.InvalidFeeAmountException;
import com.wg.helper.PasswordUtil;
import com.wg.helper.PasswordValidator;
import com.wg.helper.Validator;
import com.wg.model.Course;
import com.wg.model.Issue;
import com.wg.model.Notification;
import com.wg.model.User;
import com.wg.printer.CoursePrinter;
import com.wg.printer.IssuePrinter;
import com.wg.printer.UserPrinter;

public class AdminUI extends BaseUI {
	public void manageUser() {
		while (true) {
			System.out.println(" ");
			System.out.println(StringConstants.MANAGE_USER);
			System.out.println(" ");
			System.out.println(StringConstants.ENTER_YOUR_CHOICE);
			int choice = Validator.getUserChoice();
			switch (choice) {
			case 1:
				addUser();
				break;
			case 2:
				getUserById();
				break;
			case 3:
				getUserByUsername();
				break;
			case 4:
				getAllUser();
				break;
			case 5:
				deleteUser();
				break;
			case 6:
				updateUser();
				break;
			case 7:
				return;
			default:
				System.out.println(StringConstants.ENTER_VALID_CHOICE);
			}
		}
	}

	public void manageFees() {
		while (true) {
			System.out.println(" ");
			System.out.println(StringConstants.MANAGE_FEES);
			System.out.println(" ");
			System.out.println(StringConstants.ENTER_YOUR_CHOICE);
			int choice = Validator.getUserChoice();
			switch (choice) {
			case 1:
				addFees();
				break;
			case 2:
				calculateFine();
				break;
			case 3:
				return;
			default:
				System.out.println(StringConstants.ENTER_VALID_CHOICE);
			}
		}
	}

	public void manageLeaves(String role) {
		while (true) {
			System.out.println(" ");
			System.out.println(StringConstants.MANAGE_LEAVES_MENU);
			System.out.println(" ");
			System.out.println(StringConstants.ENTER_YOUR_CHOICE);
			int choice = Validator.getUserChoice();
			switch (choice) {
			case 1:
				viewAllLeave();
				break;
			case 2:
				approveLeave(role);
				break;
			case 3:
				rejectLeave(role);
				break;
			case 4:
				return;
			default:
				System.out.println(StringConstants.ENTER_VALID_CHOICE);
			}
		}
	}

	public void manageIssues() {
		while (true) {
			System.out.println(" ");
			System.out.println(StringConstants.MANAGE_ISSUES_MENU);
			System.out.println(" ");
			System.out.println(StringConstants.ENTER_YOUR_CHOICE);
			int choice = Validator.getUserChoice();
			switch (choice) {
			case 1:
				viewAllIssues();
				break;
			case 2:
				resolveIssue();
				break;
			case 3:
				return;
			default:
				System.out.println(StringConstants.ENTER_VALID_CHOICE);
			}
		}
	}

	public void manageCourse() {
		while (true) {
			System.out.println(StringConstants.MANAGE_COURSES_MENU);
			System.out.println(StringConstants.ENTER_YOUR_CHOICE);
			int choice = Validator.getUserChoice();
			switch (choice) {
			case 1:
				addCourse();
				break;
			case 2:
				getCourse();
				break;
			case 3:
				deleteCourse();
				break;
			case 4:
				updateCourse();
				break;
			case 5:
				getAllCourses();
				break;
			case 6:
				return;
			default:
				System.out.println(StringConstants.ENTER_VALID_CHOICE);
			}
		}
	}

	public void manageAttendance() {
		while (true) {
			System.out.println(StringConstants.MANAGE_ATTENDANCE_MENU);
			System.out.println(StringConstants.ENTER_YOUR_CHOICE);
			int choice = Validator.getUserChoice();
			switch (choice) {
			case 1:
				addAttendance();
				break;
			case 2:
				viewAttendanceByStandard();
				break;
			case 3:
				viewAttendanceById();
				break;
			case 4:
				return;
			default:
				System.out.println(StringConstants.ENTER_VALID_CHOICE);
			}
		}
	}

	public void getAllCourses() {
		List<Course> list = courseController.getAllCourses();
		if (list.isEmpty()) {
			System.out.println(StringConstants.NO_COURSES_FOUND);
			return;
		}
		CoursePrinter.printCourseDetails(list);
	}

	public void updateCourse() {
		List<Course> course = courseController.getAllCourses();
		if (course.isEmpty()) {
			System.out.println(StringConstants.NO_COURSES_FOUND);
			return;
		}
		int index = 0;
		CoursePrinter.printCourseDetails(course);
		System.out.println(StringConstants.ENTER_THE_USER_INDEX);
		boolean validateIndex = false;
		int limit = course.size();
		while (!validateIndex) {
			try {
				index = scanner.nextInt();
				if (Validator.isValidIndex(index, limit)) {
					validateIndex = true;
				} else {
					System.out.println(StringConstants.ENTER_VALID_USER_INDEX);
					validateIndex = false;
				}
			} catch (InputMismatchException e) {
				System.out.println(StringConstants.ENTER_VALID_INPUT);
				scanner.next();
			}
		}
		String courseId = course.get(index - 1).getCourseId();
		courseController.updateCourse(courseId);
	}

	public void deleteCourse() {
		List<Course> list = courseController.getAllCourses();
		if (list.isEmpty()) {
			System.out.println(StringConstants.NO_COURSES_FOUND);
			return;
		}
		int index = 0;
		CoursePrinter.printCourseDetails(list);
		System.out.println(StringConstants.ENTER_THE_USER_INDEX);
		boolean validateIndex = false;
		int limit = list.size();
		while (!validateIndex) {
			try {
				index = scanner.nextInt();
				if (Validator.isValidIndex(index, limit)) {
					validateIndex = true;
				} else {
					System.out.println(StringConstants.ENTER_VALID_USER_INDEX);
					validateIndex = false;
				}
			} catch (InputMismatchException e) {
				System.out.println(StringConstants.ENTER_VALID_INPUT);
				scanner.next();
			}
		}
		String courseId = list.get(index - 1).getCourseId();
		boolean flag = courseController.deleteCourse(courseId);
		if (flag == true) {
			System.out.println(StringConstants.COURSE_DELETED_SUCCESSFULLY);
		} else {
			System.out.println(StringConstants.COURSE_DELETION_UNSUCCESSFUL);
		}
	}

//	public void addCourse() {
//		boolean validStandard = false;
//		System.out.println(StringConstants.ENTER_THE_STANDARD_1_12);
//		int standard = scanner.nextInt();
//		while (!validStandard) {
//			try {
//				if (standard >= 1 && standard <= 12) {
//					validStandard = true;
//				} else {
//					System.out.println(StringConstants.ENTER_VALID_STANDARD_1_12);
//					standard = scanner.nextInt();
//					validStandard = false;
//				}
//			} catch (InputMismatchException e) {
//				System.out.println(StringConstants.INVALID_INPUT_PLEASE_ENTER_A_VALID_INTEGER_FOR_STANDARD2);
//				scanner.next();
//			}
//		}
//
//		int courseCount = StandardWiseCourse.getCourseCount(standard);
//		// for (int count = 0; count < courseCount; count++) {
//		System.out.println(StringConstants.ENTER_COURSE_NAME);
//		String courseName = "";
//		while (true) {
//			try {
//				courseName = scanner.nextLine();
//				courseName.trim();
//				if (courseName.matches("^[a-zA-Z\\s]+$") && !courseName.isEmpty()) {
//					break;
//				} else {
//					System.out.println(StringConstants.INVALID_INPUT);
//				}
//			} catch (InputMismatchException e) {
//				System.out.println(StringConstants.INVALID_INPUT);
//				scanner.next();
//			}
//		}
//		courseController.addCourse(courseName, standard);
//		// }
//	}

	public void addCourse() {
		boolean validStandard = false;
		System.out.println(StringConstants.ENTER_THE_STANDARD_1_12);
		int standard = 0;
		int limit = 0;
		while (!validStandard) {
			try {
				standard = scanner.nextInt();
				if (standard >= 1 && standard <= 12) {
					List<Course> courses = courseController.getAllCourses();
					int standard1 = standard;
					courses = courses.stream().filter(course -> course.getStandard() == standard1)
							.collect(Collectors.toList());
					limit = courses.size();
					validStandard = true;
				} else {
					System.out.println(StringConstants.ENTER_VALID_STANDARD_1_12);
					validStandard = false;
				}
			} catch (InputMismatchException e) {
				System.out.println(StringConstants.INVALID_INPUT_PLEASE_ENTER_A_VALID_INTEGER_FOR_STANDARD2);
				scanner.next();
			}
		}

		int courseCount = StandardWiseCourse.getCourseCount(standard);
		int courseToBeAdded = 0;
		if (courseCount == limit) {
			System.out.println("No more courses can be added for this standard");
			return;
		} else {
			courseToBeAdded = courseCount - limit;
		}
		System.out.println("You need to add " + courseToBeAdded + " courses for standard " + standard);
		scanner.nextLine();

		for (int courseAdded = 0; courseAdded < courseToBeAdded; courseAdded++) {
			System.out.println();
			System.out.println(StringConstants.ENTER_COURSE_NAME);
			String courseName = "";
			boolean isValidCourse = false;
			while (!isValidCourse) {
				courseName = scanner.nextLine();
				if (Validator.isValidString(courseName)) {
					isValidCourse = true;
				} else {
					System.out.println(StringConstants.INVALID_INPUT);
					isValidCourse = false;
				}
			}
			courseController.addCourse(courseName, standard);
		}
	}

	public void getCourse() {
		List<Course> list = courseController.getAllCourses();
		if (list.isEmpty()) {
			System.out.println(StringConstants.NO_COURSES_FOUND);
			return;
		}
		int index = 0;
		CoursePrinter.printCourseDetails(list);
		System.out.println(StringConstants.ENTER_THE_COURSE_INDEX);
		boolean validateIndex = false;
		int limit = list.size();
		while (!validateIndex) {
			try {
				index = scanner.nextInt();
				if (Validator.isValidIndex(index, limit)) {
					validateIndex = true;
				} else {
					System.out.println(StringConstants.ENTER_VALID_COURSE_INDEX);
					validateIndex = false;
				}
			} catch (InputMismatchException e) {
				System.out.println(StringConstants.ENTER_VALID_INPUT);
				scanner.next();
			}
		}
		String courseId = list.get(index - 1).getCourseId();
		Course course = courseController.getCourse(courseId);
		List<Course> singleCourse = new ArrayList<Course>();
		singleCourse.add(course);
		CoursePrinter.printCourseDetails(singleCourse);
	}

	public void resolveIssue() {
		List<Issue> issue = issueController.viewAllIssues();
		if (issue.isEmpty()) {
			System.out.println(StringConstants.NO_ISSUES_FOUND);
			return;
		}
		int index = 1;
		IssuePrinter.extractedHeader();
		for (Issue i : issue) {
			List<Issue> issueList = new ArrayList<Issue>();
			String userId = i.getUserId();
			User user = userController.getUserById(userId);
			String name = user.getName();
			String username = user.getUsername();
			issueList.add(i);
			IssuePrinter.printIssues1(issueList, name, username, index);
			index++;
		}
		IssuePrinter.extractedFooter();
		System.out.println("Enter the Issue index");
		boolean validateIndex = false;
		int limit = issue.size();
		while (!validateIndex) {
			try {
				index = scanner.nextInt();
				if (Validator.isValidIndex(index, limit)) {
					validateIndex = true;
				} else {
					System.out.println("Enter valid Issue Index:");
					validateIndex = false;
				}
			} catch (InputMismatchException e) {
				System.out.println("Enter valid Issue Index:");
				scanner.next();
			}
		}

		String userId = issue.get(index - 1).getUserId();
		String Status = issue.get(index - 1).getStatus().toString();
		if (Status.equals("RESOLVED")) {
			System.out.println("Issue already resolved");
			return;
		}
		issueController.resolveIssue(userId);
	}

	public void viewAllIssues() {
		List<Issue> issue = issueController.viewAllIssues();
		if (issue.isEmpty()) {
			System.out.println(StringConstants.NO_ISSUES_FOUND);
			return;
		}
		int index = 1;
		IssuePrinter.extractedHeader();
		for (Issue i : issue) {
			List<Issue> issueList = new ArrayList<Issue>();
			String userId = i.getUserId();
			User user = userController.getUserById(userId);
			String name = user.getName();
			String username = user.getUsername();
			issueList.add(i);
			IssuePrinter.printIssues1(issueList, name, username, index);
			index++;
		}
		IssuePrinter.extractedFooter();
	}

	public void sendNotification() {
		try {
			List<User> users = userController.getAllUser();
			System.out.println(StringConstants.LIST_OF_ALL_USERS);
			int index = 0;
			UserPrinter.printUsers(users);

			System.out.println(StringConstants.ENTER_THE_USER_INDEX2);
			boolean validateIndex = false;
			int limit = users.size();
			while (!validateIndex) {
				index = scanner.nextInt();
				if (Validator.isValidIndex(index, limit)) {
					validateIndex = true;
				} else {
					System.out.println(StringConstants.ENTER_VALID_USER_INDEX);
					validateIndex = false;
				}
			}
			String userId = users.get(index - 1).getUserId();
			System.out.println(StringConstants.ENTER_TITLE);
			String type = null;// scanner.next();
			scanner.nextLine();
			while (true) {
				try {
					type = scanner.nextLine();
					if (type.matches("[a-zA-Z]+")) {
						break; // Exit the loop if valid
					} else {
						System.out.println(StringConstants.INVALID_INPUT);
					}
				} catch (InputMismatchException e) {
					System.out.println(StringConstants.INVALID_INPUT);
					scanner.next();
				}
			}
			System.out.println(StringConstants.ENTER_MESSAGE);
			String description = null;// scanner.nextLine();
			String message = scanner.nextLine();
			while (!Validator.isValidString(message)) {
				System.out.println(StringConstants.INVALID_INPUT);
				System.out.println(StringConstants.ENTER_MESSAGE);
				message = scanner.nextLine();
			}

//			while (true) {
//				try {
//					description = scanner.nextLine();
//					// Check if the title is alphabetic only
//					if (description.matches("[a-zA-Z0-9]+")) {
//						break; // Exit the loop if valid
//					} else {
//						System.out.println(StringConstants.INVALID_INPUT);
//					}
//				} catch (InputMismatchException e) {
//					System.out.println(StringConstants.INVALID_INPUT);
//					scanner.next();
//				}
//			}

			String randomString = UUID.randomUUID().toString();
			int desiredLength = 7;
			if (desiredLength > randomString.length()) {
				desiredLength = randomString.length();
			}
			String notificationId = randomString.substring(0, desiredLength);
			LocalDate todayDate = LocalDate.now();
			notificationId = 'N' + notificationId;
			Notification notification = new Notification(notificationId, userId, type, description, todayDate);
			boolean sendStatus = notificationController.sendNotification(notification);
			if (sendStatus) {
				System.out.println(StringConstants.NOTIFICATION_SENT_SUCCESSFULLY);
			} else {
				System.out.println(StringConstants.NOTIFICATION_NOT_SENT);
			}
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
			// e.printStackTrace();
		}
	}

	public void addUser() {
		System.out.print(StringConstants.ENTER_USERNAME_ALPHA_NUMERIC_4_30_CHARACTERS);
		String username = null;
		boolean validUserName = false;
		while (!validUserName) {
			username = scanner.next();
			if (Validator.isValidUsername(username)) {
				validUserName = true;
			} else {
				System.out.println(StringConstants.ENTER_VALID_USERNAME);
				username = scanner.next();
				validUserName = false;
			}
		}

		System.out.print(StringConstants.ENTER_NAME);
		String name = null;
		Boolean validName = false;
		while (!validName) {
			name = scanner.next();
			if (Validator.isValidName(name)) {
				validName = true;
			} else {
				System.out.println(StringConstants.ENTER_VALID_NAME);
				name = scanner.next();
				validName = false;
			}
		}
		int age = 0;
		boolean validInput = false;
		while (!validInput) {
			System.out.print(StringConstants.ENTER_AGE);
			try {
				age = scanner.nextInt();
				if (Validator.isValidAge(age)) {
					validInput = true;
				} else {
					System.out.println(StringConstants.INVALID_AGE_ENTER_A_VALID_AGE);
					validInput = false; // If input is valid, exit loop
				}
			} catch (InputMismatchException e) {
				System.out.println(StringConstants.INVALID_INPUT_PLEASE_ENTER_A_VALID_INTEGER_FOR_AGE);
				scanner.next(); // Clear invalid input
			}
		}

		System.out.print(StringConstants.ENTER_PASSWORD);
		PasswordValidator passwordValidator = new PasswordValidator();
		String password = null;
		boolean isValidPassword = false;
		while (!isValidPassword) {
			password = scanner.next();
			if (passwordValidator.isValidPassword(password)) {
				isValidPassword = true;
			} else {
				System.out.println(StringConstants.ENTER_VALID_PASSWORD);
				isValidPassword = false;
			}
		}
		PasswordUtil passwordUtil = new PasswordUtil();
		String hashedPassword = passwordUtil.hashPassword(password);
		validInput = false;
		String email = null;
		System.out.print(StringConstants.ENTER_EMAIL);
		while (!validInput) {
			try {
				email = scanner.next();
				if (Validator.isValid(email)) {
					validInput = true;
				} else {
					System.out.print(StringConstants.ENTER_VALID_EMAIL);
					validInput = false;
				}
			} catch (InputMismatchException e) {
				System.out.println(StringConstants.INVALID_INPUT_PLEASE_ENTER_A_VALID_EMAIL);
				scanner.next(); // Clear invalid input
			}
		}

		String input = "";
		boolean validInput1 = false;
		while (!validInput1) {
			System.out.print(StringConstants.ENTER_ROLE_STUDENT_FACULTY_CASE_SENSITIVE);
			try {
				input = scanner.next().trim();
				String input1 = input.toUpperCase();
				if (input1.equals("STUDENT")) {
					input = "Student";
					validInput1 = true;
				}
				if (input1.equals("FACULTY")) {
					input = "Faculty";
					validInput1 = true;
				}
				if (input1.equals("ADMIN")) {
					System.out.println(StringConstants.ROLE_CAN_NOT_BE_ADMIN);
					validInput1 = false;
				}
			} catch (InputMismatchException e) {
				System.out.println(StringConstants.INVALID_INPUT_PLEASE_ENTER_A_VALID_ROLE);
				scanner.next();
			}
		}
		LocalDate date = null;
		while (date == null) {
			System.out.println(StringConstants.ENTER_THE_DATE_OF_BIRTH_YYYY_MM_DD);
			try {
				boolean validDate = false;
				while (!validDate) {
					String input2 = scanner.next();
					date = LocalDate.parse(input2);
					if (date.isBefore(LocalDate.now())) {
						validDate = true;
					} else {
						validDate = false;
						System.out.println(StringConstants.PLEASE_ENTER_VALID_DOB_DATE);
					}
				}
			} catch (DateTimeParseException e) {
				System.out.println(StringConstants.INVALID_DATE_FORMAT);
			}
		}
		String contactNumber = null;
		System.out.println(StringConstants.ENTER_CONTACT_NUMBER);
		boolean validNumber = false;
		while (!validNumber) {
			contactNumber = scanner.next();
			if (Validator.isValidContactNo(contactNumber)) {
				validNumber = true;
			} else {
				System.out.println(StringConstants.ENTER_VALID_MOBILE_NUMBER);
				validNumber = false;
			}
		}

		int standard = 0;
		if (input.equals(StringConstants.STUDENT)) {
			boolean validStandard1 = false;
			while (!validStandard1) {
				System.out.println(StringConstants.ENTER_THE_STANDARD);
				try {
					standard = scanner.nextInt(); // Try to read the integer
					if (standard >= 1 && standard <= 12) {
						validStandard1 = true; // Input is valid
					} else {
						System.out.println(StringConstants.INVALID_INPUT_ENTER_A_NUMBER_BETWEEN_1_AND_12);
					}
				} catch (InputMismatchException e) {
					System.out.println(StringConstants.INVALID_INPUT_PLEASE_ENTER_A_VALID_INTEGER_FOR_STANDARD);
					scanner.next(); // Clear the invalid input
				}
			}
		}

		System.out.println(StringConstants.ENTER_THE_GENDER_M_FOR_MALE_F_FOR_FEMALE);
		String gender = null;
		boolean validGender = false;
		while (!validGender) {
			try {
				gender = scanner.next();
				if (gender.equals("M") || gender.equals("F") || gender.equals("m") || gender.equals("f")) {
					validGender = true;
				} else {
					System.out.println(StringConstants.INVALID_INPUT_PLEASE_ENTER_A_VALID_GENDER_M_F);
					validGender = false;
				}
			} catch (InputMismatchException e) {
				System.out.println(StringConstants.ENTER_VALID_INPUT);
				scanner.next();
			}
		}
		gender = gender.toUpperCase();

		String rollNo = null;
		if (input.equals(StringConstants.STUDENT)) {
			System.out.println(StringConstants.ENTER_THE_ROLL_NUMBER);
			boolean validRollNo = false;
			while (!validRollNo) {
				try {
					rollNo = scanner.next();
					if (Validator.isValidRollNumber(rollNo)) {
						validRollNo = true;
					} else {
						System.out.println(StringConstants.ENTER_VALID_ROLL_NUMBER);
						validRollNo = false;
					}
				} catch (InputMismatchException e) {
					System.out.println(StringConstants.ENTER_VALID_INPUT);
					scanner.next();
				}
			}
		}

		int mentorOf = 0;
		if (input.equals(StringConstants.FACULTY)) {
			boolean validStandard = false;
			System.out.println(StringConstants.ENTER_THE_MENTOR_OF_FIELD_1_12);
			while (!validStandard) {
				mentorOf = scanner.nextInt();
				try {
					if (mentorOf >= 1 && mentorOf <= 12) {
						validStandard = true;
					} else {
						System.out.println(StringConstants.ENTER_VALID_FIELD_1_12);
						validStandard = false;
					}
				} catch (InputMismatchException e) {
					System.out.println(StringConstants.INVALID_INPUT_PLEASE_ENTER_A_VALID_INTEGER_FOR_MENTOR_OF);
					scanner.next();
				}
			}
		}

		boolean flag = userController.addUser(username, name, age, hashedPassword, email, input, date, contactNumber,
				standard, gender, rollNo, mentorOf);
		if (flag) {
			System.out.println(StringConstants.USER_ADDED_SUCCESSFULLY);
		} else {
			System.out.println(StringConstants.USER_NOT_ADDED);
		}
	}

	public void getUserById() {
		System.out.println(StringConstants.LIST_OF_ALL_USERS);
		List<User> list = userController.getAllUser();
		if (list.isEmpty()) {
			System.out.println(StringConstants.NO_USERS_FOUND3);
			return;
		}
		int index = 0;
		UserPrinter.printUsers(list);
		boolean validateIndex = false;
		int limit = list.size();
		System.out.println(StringConstants.ENTER_THE_USER_INDEX);
		while (!validateIndex) {
			try {
				index = scanner.nextInt();
				if (Validator.isValidIndex(index, limit)) {
					validateIndex = true;
				} else {
					System.out.println(StringConstants.ENTER_VALID_USER_INDEX);
					validateIndex = false;
				}
			} catch (InputMismatchException e) {
				System.out.println(StringConstants.ENTER_VALID_INPUT);
				scanner.next();
			}
		}
		String userId = list.get(index - 1).getUserId();
		User user = userController.getUserById(userId);
		if (user == null) {
			System.out.println(StringConstants.USER_NOT_FOUND);
			return;
		}
		List<User> singleUser = new ArrayList<User>();
		singleUser.add(user);
		UserPrinter.printUsers(singleUser);
	}

	public void getUserByUsername() {
		System.out.println(StringConstants.LIST_OF_ALL_USERS);
		List<User> list = userController.getAllUser();
		if (list.isEmpty()) {
			System.out.println(StringConstants.NO_USERS_FOUND4);
			return;
		}
		UserPrinter.printUsers(list);
		System.out.print(StringConstants.ENTER_USER_USERNAME);
		String username = scanner.next();
		User user = userController.getUserByUsername(username);
		if (user == null) {
			System.out.println(StringConstants.USER_NOT_FOUND);
			return;
		}
		List<User> singleUser = new ArrayList<User>();
		singleUser.add(user);
		UserPrinter.printUsers(singleUser);
	}

	public void getClassDetails() {
		boolean validStandard1 = false;
		int standard = 0;
		while (!validStandard1) {
			System.out.println(StringConstants.ENTER_THE_STANDARD);
			try {
				standard = scanner.nextInt(); // Try to read the integer
				if (standard >= 1 && standard <= 12) {
					validStandard1 = true; // Input is valid
				} else {
					System.out.println(StringConstants.INVALID_INPUT_ENTER_A_NUMBER_BETWEEN_1_AND_12);
				}
			} catch (InputMismatchException e) {
				System.out.println(StringConstants.INVALID_INPUT_PLEASE_ENTER_A_VALID_INTEGER_FOR_STANDARD);
				scanner.next(); // Clear the invalid input
			}
		}

		List<User> users = userController.getClassDetails(standard);
		if (users.isEmpty()) {
			System.out.println(StringConstants.USERS_NOT_FOUND4);
			return;
		}
		UserPrinter.printUsers(users);
	}

	public void deleteUser() {
		List<User> users = userController.getAllUser();
		if (users.isEmpty()) {
			System.out.println(StringConstants.NO_USERS_FOUND);
			return;
		}
		System.out.println(StringConstants.LIST_OF_ALL_USERS);
		int index = 0;
		UserPrinter.printUsers(users);
		System.out.println(StringConstants.ENTER_THE_USER_INDEX);
		boolean validateIndex = false;
		int limit = users.size();
		while (!validateIndex) {
			try {
				index = scanner.nextInt();
				if (Validator.isValidIndex(index, limit)) {
					validateIndex = true;
				} else {
					System.out.println(StringConstants.ENTER_VALID_USER_INDEX);
					validateIndex = false;
				}
			} catch (InputMismatchException e) {
				System.out.println(StringConstants.ENTER_VALID_INPUT);
				scanner.next();
			}
		}
		boolean flag = userController.deleteUser(users.get(index - 1).getUserId());
		if (flag == true) {
			System.out.println(StringConstants.USER_DELETED_SUCCESSFULLY);
		} else {
			System.out.println(StringConstants.USER_DELETION_UNSUCCESSFUL);
		}
	}

	public void getAllUser() {
		System.out.println(StringConstants.LIST_OF_ALL_USERS);
		List<User> list = userController.getAllUser();
		if (list.isEmpty()) {
			System.out.println(StringConstants.NO_USERS_FOUND);
			return;
		}
		UserPrinter.printUsers(list);
	}

	public void updateUser() {
		System.out.println(StringConstants.LIST_OF_ALL_USERS);
		List<User> list = userController.getAllUser();
		int index = 0;
		if (list.isEmpty()) {
			System.out.println(StringConstants.NO_USERS_FOUND);
			return;
		}
		// List<User> checkedList = new ArrayList<User>();
		// for(User user: list) {
//		List<User> checkedList = list.stream().filter(user -> user.getRole().equals("Admin"))
//				.collect(Collectors.toList());
		// }
//		List<Course> filteredCourses = list.stream().filter(course -> course.getStandard() == standard)
//				.collect(Collectors.toList());

		UserPrinter.printUsers(list);
		boolean validateIndex = false;
		int limit = list.size();
		System.out.println(StringConstants.ENTER_THE_USER_INDEX);
		while (!validateIndex) {
			try {
				index = scanner.nextInt();
				if (Validator.isValidIndex(index, limit)) {
					validateIndex = true;
				} else {
					System.out.println(StringConstants.ENTER_VALID_USER_INDEX);
					validateIndex = false;
				}
			} catch (InputMismatchException e) {
				System.out.println(StringConstants.ENTER_VALID_INPUT);
				scanner.next();
			}
		}
		userController.updateUser(list.get(index - 1).getUserId());
	}

	public void addFees() {
		List<User> users = userController.getAllUser();
		if (users.isEmpty()) {
			System.out.println(StringConstants.NO_USERS_FOUND);
			return;
		}
		int index = 0;
		UserPrinter.printUsers(users);
		System.out.println(StringConstants.ENTER_THE_USER_INDEX);
		boolean validateIndex = false;
		int limit = users.size();
		while (!validateIndex) {
			try {
				index = scanner.nextInt();
				if (Validator.isValidIndex(index, limit)) {
					validateIndex = true;
				} else {
					System.out.println(StringConstants.ENTER_VALID_USER_INDEX);
					validateIndex = false;
				}
			} catch (InputMismatchException e) {
				System.out.println(StringConstants.ENTER_VALID_INPUT);
				scanner.next();
			}
		}
		String userId = users.get(index - 1).getUserId();
		User user = userController.getUserById(userId);
		if (!user.getRole().toString().equals(StringConstants.STUDENT)) {
			System.out.println("Enter valid Student UserId");
			return;
		}
		System.out.println(StringConstants.ENTER_THE_FEE_AMOUNT);
		double feeAmount = 0;
		while (feeAmount <= 0) {
			try {
				feeAmount = scanner.nextDouble();
				if (feeAmount <= 0) {
					System.out.println(StringConstants.ENTER_VALID_FEE_AMOUNT);
				}
			} catch (InputMismatchException e) {
				System.out.println(StringConstants.INVALID_INPUT_PLEASE_ENTER_A_NUMERIC_VALUE);
				scanner.next();
			} catch (DateTimeParseException | InvalidFeeAmountException e) {
				e.printStackTrace();
				throw new InvalidFeeAmountException(StringConstants.FEE_AMOUNT_MUST_BE_GREATER_THAN_ZERO);
			}
		}
		LocalDate date = null;
		LocalDate date1 = LocalDate.of(2050, 12, 31);
		while (date == null) {
			System.out.println(StringConstants.ENTER_THE_DEADLINE_YYYY_MM_DD);
			try {
				boolean validDate = false;
				while (!validDate) {
					String input2 = scanner.next();
					date = LocalDate.parse(input2);
					if (date.isAfter(LocalDate.now()) && date.isBefore(date1)) {
						validDate = true;
					} else {
						validDate = false;
						System.out.println(StringConstants.VALID_DEAD_LINE_DATE);
					}
				}
			} catch (DateTimeParseException e) {
				System.out.println(StringConstants.INVALID_DATE_FORMAT);
			}
		}
		double fineAmount = 0;
		feeController.addFees(userId, feeAmount, date, fineAmount);
	}

	public void calculateFine() {
		List<User> users = userController.getAllUser();
		if (users.isEmpty()) {
			System.out.println(StringConstants.NO_USERS_FOUND5);
			return;
		}
		int index = 0;
		UserPrinter.printUsers(users);
		System.out.println(StringConstants.ENTER_THE_USER_INDEX);
		boolean validateIndex = false;
		int limit = users.size();
		while (!validateIndex) {
			try {
				index = scanner.nextInt();
				if (Validator.isValidIndex(index, limit)) {
					validateIndex = true;
				} else {
					System.out.println(StringConstants.ENTER_VALID_USER_INDEX);
					validateIndex = false;
				}
			} catch (InputMismatchException e) {
				System.out.println(StringConstants.ENTER_VALID_INPUT);
				scanner.next();
			}
		}
		String userId = users.get(index - 1).getUserId();
		String role = users.get(index - 1).getRole().toString();
		if (role.equals(StringConstants.ADMIN) || role.equals(StringConstants.FACULTY)) {
			System.out.println(StringConstants.ENTER_VALID_ID);
			return;
		}
		feeController.calculateFine(userId);
	}

	public void logout() {
		App.main(null);
	}
}