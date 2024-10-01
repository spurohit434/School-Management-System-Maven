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
import com.wg.helper.Validator;
import com.wg.model.Course;
import com.wg.model.CourseMarks;
import com.wg.model.Issue;
import com.wg.model.IssuesStatus;
import com.wg.model.Leaves;
import com.wg.model.LeavesStatus;
import com.wg.model.Notification;
import com.wg.model.User;
import com.wg.printer.CoursePrinter;
import com.wg.printer.IssuePrinter;
import com.wg.printer.LeavesPrinter;
import com.wg.printer.MarksheetPrinter;
import com.wg.printer.NotificationPrinter;
import com.wg.printer.UserPrinter;

public class FacultyUI extends BaseUI {

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

	public void manageLeaves(User user, String role) {
		while (true) {
			System.out.println(StringConstants.MANAGE_LEAVES_MENU_FACULTY);
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
				applyLeave(user);
				return;
			case 5:
				checkLeaveStatus(user);
				return;
			default:
				System.out.println(StringConstants.ENTER_VALID_CHOICE);
			}
		}
	}

	public void applyLeave(User user) {
		String userId = user.getUserId();
		System.out.println(StringConstants.ENTER_LEAVE_CONTENT);
		String content = null;
		while (true) {
			try {
				content = scanner.nextLine();
				if (content.matches(".*[a-zA-Z].*")) {
					break;
				} else {
					System.out.println(StringConstants.INVALID_INPUT);
				}
			} catch (InputMismatchException e) {
				System.out.println(StringConstants.INVALID_INPUT + "Exception");
				scanner.next();
			}
		}

		String input = StringConstants.PENDING;
		LeavesStatus status = LeavesStatus.valueOf(input);
		String randomString = UUID.randomUUID().toString();
		int desiredLength = 7;
		if (desiredLength > randomString.length()) {
			desiredLength = randomString.length();
		}
		String leaveId = randomString.substring(0, desiredLength);
		leaveId = 'L' + leaveId;

		LocalDate startDate = null;
		System.out.println(StringConstants.ENTER_THE_LEAVE_START_DATE_YYYY_MM_DD);
		while (startDate == null) {
			try {
				boolean validDate = false;
				while (!validDate) {
					String input2 = scanner.next();
					startDate = LocalDate.parse(input2);
					if (startDate.isAfter(LocalDate.now())) {
						validDate = true;
					} else {
						validDate = false;
						System.out.println(StringConstants.PLEASE_ENTER_VALID_DATE);
					}
				}
			} catch (DateTimeParseException e) {
				System.out.println(StringConstants.INVALID_DATE_FORMAT);
			}
		}

		LocalDate endDate = Validator.ValidateDate(startDate);
		Leaves leave = new Leaves(leaveId, userId, content, startDate, endDate, status);
		leavesController.applyLeave(leave);
	}

	public void checkLeaveStatus(User user) {
		String userId = user.getUserId();
		List<Leaves> leaves = leavesController.checkLeaveStatus(userId);
		if (leaves.isEmpty()) {
			System.out.println(StringConstants.FIRST_APPLY_FOR_LEAVE);
			return;
		}
		// LeavesPrinter.printLeaves(leaves);
		int index = 1;
		LeavesPrinter.extractedHeader();
		for (Leaves leave : leaves) {
			List<Leaves> leaveList = new ArrayList<Leaves>();
			String userId1 = leave.getUserId();
			User user1 = userController.getUserById(userId1);
			String name = user1.getName();
			String username = user1.getUsername();
			leaveList.add(leave);
			LeavesPrinter.printLeaves1(leaveList, name, username, index);
			index++;
		}
		LeavesPrinter.extractedFooter();
	}

	public void raiseIssue(User user) {
		String userId = user.getUserId();
		System.out.println(StringConstants.ENTER_ISSUE_CONTENT);
		String message = null;
		while (true) {
			try {
				message = scanner.nextLine();
				if (message.matches(".*[a-zA-Z].*")) {
					break;
				} else {
					System.out.println(StringConstants.INVALID_INPUT);
				}
			} catch (InputMismatchException e) {
				System.out.println(StringConstants.INVALID_INPUT);
				scanner.next();
			}
		}

		String input = StringConstants.PENDING2;
		IssuesStatus status = IssuesStatus.valueOf(input);
		String randomString = UUID.randomUUID().toString();
		int desiredLength = 7;
		if (desiredLength > randomString.length()) {
			desiredLength = randomString.length();
		}
		String issueID = randomString.substring(0, desiredLength);
		issueID = 'I' + issueID;
		Issue issue = new Issue(issueID, message, userId, status);
		issueController.raiseIssue(issue);
	}

	public void checkIssueStatus(User user) {
		String userId = user.getUserId();
		List<Issue> issue = issueController.checkIssueStatus(userId);
		if (issue.isEmpty()) {
			System.out.println(StringConstants.NO_ISSUES_FOUND);
			return;
		}
		IssuePrinter.printIssues(issue);
	}

	public void addMarks() {
		boolean validateUser = false;
		String userId = "";
		String courseId = "";
		ArrayList<User> storeUser = new ArrayList<User>();
		User user1 = null;
		while (!validateUser) {
			List<User> users = userController.getAllUser();
			System.out.println(StringConstants.LIST_OF_ALL_USERS);
			int index = 0;
			UserPrinter.printUsers(users);
			System.out.println(StringConstants.ENTER_THE_USER_INDEX);
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
			user1 = users.get(index - 1);
			userId = user1.getUserId();

			if (user1.getRole().toString().equals(StringConstants.ADMIN)
					|| user1.getRole().toString().equals(StringConstants.FACULTY)) {
				System.out.println("Marks can only be added to Student, Enter Student User Index");
				validateUser = false;
			} else {
				storeUser.add(user1);
				validateUser = true;
			}
		}
		validateUser = false;
		while (!validateUser) {
			List<Course> list = courseController.getAllCourses();
			User user2 = storeUser.get(0);
			int standard = user2.getStandard();
			List<Course> filteredCourses = list.stream().filter(course -> course.getStandard() == standard)
					.collect(Collectors.toList());
			if (filteredCourses.isEmpty()) {
				System.out.println(StringConstants.NO_COURSE);
				return;
			}
			int index = 0;
			if (storeUser.isEmpty()) {
				System.out.println(StringConstants.NO_COURSE);
				return;
			} else {
				CoursePrinter.printCourseDetails(filteredCourses);
			}
			boolean validateIndex = false;
			int limit = filteredCourses.size();
			System.out.println(StringConstants.ENTER_THE_COURSE_INDEX);
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
					System.out.println(StringConstants.ENTER_VALID_COURSE_INDEX);
					scanner.next();
				}
			}
			courseId = filteredCourses.get(index - 1).getCourseId();
			Course course = courseController.getCourse(courseId);
			if (course == null) {
				System.out.println(StringConstants.ENTER_VALID_COURSE_ID);
				validateUser = false;
			} else {
				validateUser = true;
			}
		}
		System.out.println(StringConstants.ENTER_MARKS_0_100);
		boolean validMarks = false;
		double marks = 0;
		while (!validMarks) {
			try {
				marks = scanner.nextDouble();
				if (marks >= 0 && marks <= 100) {
					validMarks = true;
				} else {
					System.out.println(StringConstants.ENTER_VALID_MARKS_0_100);
					validMarks = false;
				}
			} catch (InputMismatchException e) {
				System.out.println(StringConstants.INVALID_INPUT_FOR_MARKS_TYPE);
				scanner.next();
			}
		}
		int standard = user1.getStandard();
		courseMarksController.addMarks(userId, courseId, marks, standard);
	}

//	public void generateMarksheet() {
//		boolean validateUser1 = false;
//		String userId1 = "";
//		ArrayList<User> storeUser1 = new ArrayList<User>();
//		User user11 = null;
//		while (!validateUser1) {
//			List<User> users = userController.getAllUser();
//			System.out.println(StringConstants.LIST_OF_ALL_USERS);
//			int index = 0;
//			UserPrinter.printUsers(users);
//			boolean validateIndex = false;
//			int limit = users.size();
//			System.out.println(StringConstants.ENTER_THE_USER_INDEX);
//			while (!validateIndex) {
//				try {
//					index = scanner.nextInt();
//					if (Validator.isValidIndex(index, limit)) {
//						validateIndex = true;
//					} else {
//						System.out.println(StringConstants.ENTER_VALID_USER_INDEX);
//						validateIndex = false;
//					}
//				} catch (InputMismatchException e) {
//					System.out.println(StringConstants.ENTER_VALID_USER_INDEX);
//					scanner.next();
//				}
//			}
//			user11 = users.get(index - 1);
//			userId1 = user11.getUserId();
//			if (user11.getRole().toString().equals(StringConstants.ADMIN)
//					|| user11.getRole().toString().equals(StringConstants.FACULTY)) {
//				System.out.println("Marksheets can only be added to Student, Enter Student UserId");
//				validateUser1 = false;
//			} else {
//				storeUser1.add(user11);
//				validateUser1 = true;
//			}
//		}
//
//		List<CourseMarks> list = courseMarksController.checkMarks(userId1);
//		if (list.isEmpty()) {
//			System.out.println(StringConstants.ADD_MARKS_FIRST_TO_GENERATE_MARKSHEET);
//			return;
//		}
//		double Totalmarks = 0;
//		for (CourseMarks course : list) {
//			Totalmarks += course.getMarks();
//		}
//		int noOfCourses = list.size();
//		double percentage = Totalmarks / noOfCourses;
//		System.out.println(StringConstants.TOTAL_MARKS + Totalmarks);
//		System.out.println(StringConstants.PERCENTAGE2 + percentage);
//		if (percentage > 45) {
//			System.out.println(StringConstants.RESULT_PASS);
//		} else {
//			System.out.println(StringConstants.RESULT_FAIL);
//		}
//	}

	public void generateMarksheet() {
		boolean validateUser1 = false;
		String userId1 = "";
		ArrayList<User> storeUser1 = new ArrayList<User>();
		User user11 = null;
		while (!validateUser1) {
			List<User> users = userController.getAllUser();
			System.out.println(StringConstants.LIST_OF_ALL_USERS);
			int index = 0;
			UserPrinter.printUsers(users);
			boolean validateIndex = false;
			int limit = users.size();
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
					System.out.println(StringConstants.ENTER_VALID_USER_INDEX);
					scanner.next();
				}
			}
			user11 = users.get(index - 1);
			userId1 = user11.getUserId();
			if (user11.getRole().toString().equals(StringConstants.ADMIN)
					|| user11.getRole().toString().equals(StringConstants.FACULTY)) {
				System.out.println("Marksheets can only be added to Student, Enter Student UserId");
				validateUser1 = false;
			} else {
				storeUser1.add(user11);
				validateUser1 = true;
			}
		}
		int standard = user11.getStandard();
		int courseCount = StandardWiseCourse.getCourseCount(standard);
		List<CourseMarks> list = courseMarksController.checkMarks(userId1);
		if (list.isEmpty()) {
			System.out.println(StringConstants.ADD_MARKS_FIRST_TO_GENERATE_MARKSHEET);
			return;
		}
		if (list.size() < courseCount) {
			System.out.println("Add marks for all the subjects to generate marksheet");
			return;
		}

		int index = 1;
		final String ROW_FORMAT = "%5d | %-15s | %5s";
		MarksheetPrinter.extractedHeader();
		for (CourseMarks c : list) {
			Course course = courseController.getCourse(c.getCourseId());
			System.out.printf(ROW_FORMAT, index, course.getCourseName(), c.getMarks());
			System.out.println();
			index++;
		}
		MarksheetPrinter.extractedFooter();

		double Totalmarks = 0;
		for (CourseMarks course : list) {
			Totalmarks += course.getMarks();
		}
		int noOfCourses = list.size();
		double percentage = Totalmarks / noOfCourses;
		System.out.println(StringConstants.TOTAL_MARKS + Totalmarks);
		System.out.println(StringConstants.PERCENTAGE2 + percentage);
		if (percentage > 45) {
			System.out.println(StringConstants.RESULT_PASS);
		} else {
			System.out.println(StringConstants.RESULT_FAIL);
		}
	}

	public void readNotifications(User user) {
		String userId = user.getUserId();
		List<Notification> notifications = notificationController.readNotifications(userId);
		if (notifications.isEmpty()) {
			System.out.println(StringConstants.NO_NOTIFICATIONS_FOUND);
			return;
		}
		NotificationPrinter.printNotifications(notifications);
	}

	public void logout() {
		App.main(null);
	}
}
