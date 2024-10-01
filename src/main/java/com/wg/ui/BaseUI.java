package com.wg.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

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
import com.wg.model.Leaves;
import com.wg.model.Status;
import com.wg.model.User;
import com.wg.printer.AttendancePrinter;
import com.wg.printer.LeavesPrinter;
import com.wg.printer.UserPrinter;

public class BaseUI {
	UserController userController = new UserController();
	CourseController courseController = new CourseController();
	AttendanceController attendanceController = new AttendanceController();
	NotificationController notificationController = new NotificationController();
	LeavesController leavesController = new LeavesController();
	IssueController issueController = new IssueController();
	CourseMarksController courseMarksController = new CourseMarksController();
	FeeController feeController = new FeeController();
	Scanner scanner = new Scanner(System.in);

	public User authenticateUser(String username, String password) {
		return userController.authenticateUser(username, password);
	}

	public void addAttendance() {
		List<User> list = userController.getAllUser();
		if (list.isEmpty()) {
			System.out.println(StringConstants.NO_USERS_FOUND2);
			return;
		}
		int index = 0;
		UserPrinter.printUsers(list);
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
		String studentId = list.get(index - 1).getUserId();
		String role = list.get(index - 1).getRole().toString();
		if (role.equals(StringConstants.ADMIN) || role.equals(StringConstants.FACULTY)) {
			System.out.println(StringConstants.ENTER_VALID_ID);
			return;
		}
		int standard = list.get(index - 1).getStandard();

		LocalDate date = null;
		boolean validateDate = false;
		while (!validateDate) {
			System.out.println(StringConstants.ENTER_THE_DATE_YYYY_MM_DD);
			try {
				String dateString = scanner.next();
				date = LocalDate.parse(dateString);
				if (date.isBefore(LocalDate.now())) {
					validateDate = true;
				} else {
					System.out.println("Enter valid date");
					validateDate = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(StringConstants.ENTER_STATUS_P_FOR_PRESENT_A_FOR_ABSENT);
		boolean validateStatus = false;
		String statusInput = null;
		Status status = null;
		while (!validateStatus) {
			statusInput = scanner.next();
			if (statusInput.equals("P") || statusInput.equals("A") || statusInput.equals("p")
					|| statusInput.equals("a")) {
				status = Status.valueOf(statusInput);
				validateStatus = true;
			} else {
				System.out.println("Enter valid Status");
				validateStatus = false;
			}
		}
		boolean flag = attendanceController.addAttendance(studentId, standard, date, status);
		if (flag) {
			System.out.println(StringConstants.ATTENDANCE_ADDED_SUCCESSFULLY);
		} else {
			System.out.println(StringConstants.ATTENDANCE_NOT_ADDED);
		}
	}

	public void viewAttendanceByStandard() {
		boolean validStandard = false;
		int standard = 0;
		while (!validStandard) {
			System.out.println(StringConstants.ENTER_THE_STANDARD);
			try {
				standard = scanner.nextInt(); // Try to read the integer
				if (standard >= 1 && standard <= 12) {
					validStandard = true; // Input is valid
				} else {
					System.out.println(StringConstants.INVALID_INPUT_ENTER_A_NUMBER_BETWEEN_1_AND_12);
				}
			} catch (InputMismatchException e) {
				System.out.println(StringConstants.INVALID_INPUT_PLEASE_ENTER_A_VALID_INTEGER_FOR_STANDARD);
				scanner.next(); // Clear the invalid input
			}
		}
		List<Attendance> list = attendanceController.viewAttendanceByStandard(standard);
		if (list.isEmpty()) {
			System.out.println(StringConstants.NO_ATTENDANCE_RECORD_FOUND);
			return;
		}
		int index = 1;
		AttendancePrinter.extractedHeader();
		for (Attendance user : list) {
			List<Attendance> userList = new ArrayList<Attendance>();
			String studentId = user.getStudentId();
			User userr = userController.getUserById(studentId);
			userList.add(user);
			String name = userr.getName();
			AttendancePrinter.printAttendanceDetails1(userList, name, index);
			index++;
		}
		AttendancePrinter.extractedFooter();
	}

	public void viewAttendanceById() {
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
		String userId = users.get(index - 1).getUserId();
		String role = users.get(index - 1).getRole().toString();
		if (role.equals(StringConstants.ADMIN) || role.equals(StringConstants.FACULTY)) {
			System.out.println(StringConstants.ENTER_VALID_ID);
			return;
		}
		List<Attendance> list = attendanceController.viewAttendanceById(userId);
		if (list.isEmpty()) {
			System.out.println(StringConstants.NO_ATTENDANCE_RECORDS_AVAILABLE);
		}
		AttendancePrinter.printAttendanceDetails(list);
	}

	public void approveLeave(String role) {
		List<Leaves> leaves = leavesController.viewAllLeave();
		if (leaves.isEmpty()) {
			System.out.println(StringConstants.NO_LEAVES_FOUND);
			return;
		}
		int index = 1;

		// LeavesPrinter.printLeaves(leaves);
		LeavesPrinter.extractedHeader();
		for (Leaves leave : leaves) {
			List<Leaves> leaveList = new ArrayList<Leaves>();
			String userId = leave.getUserId();
			User user = userController.getUserById(userId);
			String name = user.getName();
			String username = user.getUsername();
			leaveList.add(leave);
			LeavesPrinter.printLeaves1(leaveList, name, username, index);
			index++;
		}
		LeavesPrinter.extractedFooter();

		System.out.println(StringConstants.ENTER_THE_LEAVE_INDEX);
		boolean validateIndex = false;
		int limit = leaves.size();
		while (!validateIndex) {
			try {
				index = scanner.nextInt();
				if (Validator.isValidIndex(index, limit)) {
					validateIndex = true;
				} else {
					System.out.println(StringConstants.ENTER_VALID_LEAVE_INDEX);
					validateIndex = false;
				}
			} catch (InputMismatchException e) {
				System.out.println(StringConstants.ENTER_VALID_INPUT);
				scanner.next();
			}
		}
		String userId = leaves.get(index - 1).getUserId();
		String Status = leaves.get(index - 1).getStatus().toString();
		if (Status.equals(StringConstants.APPROVED)) {
			System.out.println(StringConstants.LEAVE_ALREADY_APPROVED);
			return;
		}
		if (Status.equals(StringConstants.REJECTED)) {
			System.out.println(StringConstants.LEAVE_ALREADY_REJECTED + " and can not be approved");
			return;
		}
		leavesController.approveLeave(userId);
	}

	public void rejectLeave(String role) {
		List<Leaves> leaves = leavesController.viewAllLeave();
		if (leaves.isEmpty()) {
			System.out.println(StringConstants.NO_LEAVES_FOUND);
			return;
		}
		int index = 1;
//		LeavesPrinter.printLeaves(leaves);
		LeavesPrinter.extractedHeader();
		for (Leaves leave : leaves) {
			List<Leaves> leaveList = new ArrayList<Leaves>();
			String userId = leave.getUserId();
			User user = userController.getUserById(userId);
			String name = user.getName();
			String username = user.getUsername();
			leaveList.add(leave);
			LeavesPrinter.printLeaves1(leaveList, name, username, index);
			index++;
		}
		LeavesPrinter.extractedFooter();
		System.out.println(StringConstants.ENTER_THE_LEAVE_INDEX);
		boolean validateIndex = false;
		int limit = leaves.size();
		while (!validateIndex) {
			index = scanner.nextInt();
			if (Validator.isValidIndex(index, limit)) {
				validateIndex = true;
			} else {
				System.out.println(StringConstants.ENTER_VALID_LEAVE_INDEX);
				validateIndex = false;
			}
		}
		String userId = leaves.get(index - 1).getUserId();
		String Status = leaves.get(index - 1).getStatus().toString();
		if (Status.equals(StringConstants.REJECTED)) {
			System.out.println(StringConstants.LEAVE_ALREADY_REJECTED);
			return;
		}
		if (Status.equals(StringConstants.APPROVED)) {
			System.out.println(StringConstants.LEAVE_ALREADY_APPROVED + " and can not be rejected");
			return;
		}
		leavesController.rejectLeave(userId);
	}

	public void viewAllLeave() {
		List<Leaves> leaves = leavesController.viewAllLeave();
		if (leaves.isEmpty()) {
			System.out.println(StringConstants.NO_LEAVES_FOUND);
			return;
		}
		// LeavesPrinter.printLeaves(leaves);
		int index = 1;
		LeavesPrinter.extractedHeader();
		for (Leaves leave : leaves) {
			List<Leaves> leaveList = new ArrayList<Leaves>();
			String userId = leave.getUserId();
			User user = userController.getUserById(userId);
			String name = user.getName();
			String username = user.getUsername();
			leaveList.add(leave);
			LeavesPrinter.printLeaves1(leaveList, name, username, index);
			index++;
		}
		LeavesPrinter.extractedFooter();
	}

}