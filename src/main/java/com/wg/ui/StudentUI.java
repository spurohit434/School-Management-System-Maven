package com.wg.ui;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import com.wg.app.App;
import com.wg.constants.StringConstants;
import com.wg.controller.AttendanceController;
import com.wg.controller.CourseController;
import com.wg.controller.CourseMarksController;
import com.wg.controller.FeeController;
import com.wg.controller.IssueController;
import com.wg.controller.LeavesController;
import com.wg.controller.NotificationController;
import com.wg.controller.UserController;
import com.wg.helper.Validator;
import com.wg.model.Attendance;
import com.wg.model.Course;
import com.wg.model.CourseMarks;
import com.wg.model.Issue;
import com.wg.model.IssuesStatus;
import com.wg.model.Leaves;
import com.wg.model.LeavesStatus;
import com.wg.model.Notification;
import com.wg.model.User;
import com.wg.printer.AttendancePrinter;
import com.wg.printer.CourseMarksPrinter;
import com.wg.printer.IssuePrinter;
import com.wg.printer.LeavesPrinter;
import com.wg.printer.MarksheetPrinter;
import com.wg.printer.NotificationPrinter;

public class StudentUI {
	UserController userController = new UserController();
	CourseController courseController = new CourseController();
	AttendanceController attendanceController = new AttendanceController();
	NotificationController notificationController = new NotificationController();
	LeavesController leavesController = new LeavesController();
	IssueController issueController = new IssueController();
	CourseMarksController courseMarksController = new CourseMarksController();
	FeeController feeController = new FeeController();
	Scanner scanner = new Scanner(System.in);

	public void manageLeavesStudent(User user) {
		while (true) {
			System.out.println(" ");
			System.out.println(StringConstants.MANAGE_LEAVES_MENU_STUDENT);
			System.out.println(" ");
			System.out.println(StringConstants.ENTER_YOUR_CHOICE);
			int choice = Validator.getUserChoice();
			switch (choice) {
			case 1:
				applyLeave(user);
				break;
			case 2:
				checkLeaveStatus(user);
				break;
			case 3:
				return;
			default:
				System.out.println(StringConstants.ENTER_VALID_CHOICE);
			}
		}
	}

	public void manageIssueStudent(User user) {
		while (true) {
			System.out.println(" ");
			System.out.println(StringConstants.MANAGE_ISSUES_MENU_STUDENT);
			System.out.println(" ");
			System.out.println(StringConstants.ENTER_YOUR_CHOICE);
			int choice = Validator.getUserChoice();
			switch (choice) {
			case 1:
				raiseIssue(user);
				break;
			case 2:
				checkIssueStatus(user);
				break;
			case 3:
				return;
			default:
				System.out.println(StringConstants.ENTER_VALID_CHOICE);
			}
		}
	}

	public void manageFeesStudent(User user) {
		while (true) {
			System.out.println(" ");
			System.out.println(StringConstants.MANAGE_FEES_STUDENT);
			System.out.println(" ");
			System.out.println(StringConstants.ENTER_YOUR_CHOICE);
			int choice = Validator.getUserChoice();
			switch (choice) {
			case 1:
				checkFees(user);
				break;
			case 2:
				checkFine(user);
				break;
			case 3:
				payFees(user);
				break;
			case 4:
				return;
			default:
				System.out.println(StringConstants.ENTER_VALID_CHOICE);
			}
		}
	}

	public void checkFine(User user) {
		String userId = user.getUserId();
		feeController.calculateFine(userId);
	}

	public void payFees(User user) {
		String userId = user.getUserId();
		feeController.payFees(userId);
	}

	public void checkFees(User user) {
		String userId = user.getUserId();
		feeController.checkFees(userId);
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

	public void checkMarks(User user) {
		String userId = user.getUserId();
		List<CourseMarks> coursesMarks = courseMarksController.checkMarks(userId);
		if (coursesMarks.isEmpty()) {
			System.out.println(StringConstants.NO_MARKS_ADDED);
			return;
		}
		int index = 1;
		CourseMarksPrinter.extractedHeader();
		for (CourseMarks course : coursesMarks) {
			List<CourseMarks> userList = new ArrayList<CourseMarks>();
			Course course1 = courseController.getCourse(course.getCourseId());
			userList.add(course);
			String courseName = course1.getCourseName();
			CourseMarksPrinter.printCourseMarksDetails(userList, courseName, index);
			index++;
		}
		CourseMarksPrinter.extractedFooter();
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

	public void viewAttendance(User user) {
		String userId = user.getUserId();
		List<Attendance> attendance = attendanceController.viewAttendanceById(userId);
		if (attendance.isEmpty()) {
			System.out.println(StringConstants.NO_ATTENDANCE_RECORDS_AVAILABLE);
			return;
		}
		AttendancePrinter.printAttendanceDetails(attendance);
	}

	public void viewMarksheet(User user) {
		String userId = user.getUserId();
		List<CourseMarks> list = courseMarksController.checkMarks(userId);
		if (list.isEmpty()) {
			System.out.println(StringConstants.NO_MARKS_ADDED);
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

	public void logout() {
		App.main(null);
	}
}