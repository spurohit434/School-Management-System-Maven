package com.wg.ui;

import java.util.Scanner;

import com.wg.constants.StringConstants;
import com.wg.helper.Validator;
import com.wg.model.User;

public class StartMenu {
	private final Scanner scanner = new Scanner(System.in);
	BaseUI baseUI = new BaseUI();
	AdminUI adminUI = new AdminUI();
	StudentUI studentUI = new StudentUI();
	FacultyUI facultyUI = new FacultyUI();

	public void login() {
		while (true) {
			System.out.println(StringConstants.Welcome);
			System.out.println("Enter username: ");
			String username = scanner.next();
			System.out.println("Enter password: ");
			String password = scanner.next();

			User user = baseUI.authenticateUser(username, password);
			if (user == null) {
				System.out.println("Invalid credentials");
				System.out.println(" ");
			} else {
				displayMenu(user);
			}
		}
	}

	public void displayMenu(User user) {
		String role = user.getRole().toString();
		if (role.equals("Admin")) {
			while (true) {
				System.out.println(StringConstants.ADMIN_MENU);
				System.out.println("Enter your choice: ");
				int choice = Validator.getUserChoice();
				switch (choice) {
				case 1:
					adminUI.manageUser();
					break;
				case 2:
					adminUI.getClassDetails();
					break;
				case 3:
					adminUI.sendNotification();
					break;
				case 4:
					adminUI.manageFees();
					break;
				case 5:
					adminUI.manageLeaves(role);
					break;
				case 6:
					adminUI.manageIssues();
					break;
				case 7:
					adminUI.manageCourse();
					break;
				case 8:
					adminUI.manageAttendance();
					break;
				case 9:
					adminUI.logout();
					break;
				case 10:
					System.out.println("Exiting...");
					System.exit(0);
					return;
				default:
					System.out.println("Invalid choice. Please try again.");
				}
			}
		} else if (role.equals("Student")) {
			while (true) {
				System.out.println(StringConstants.STUDENT_MENU);
				System.out.println("Enter your choice: ");
				int choice = Validator.getUserChoice();
				switch (choice) {
				case 1:
					studentUI.manageLeavesStudent(user);
					break;
				case 2:
					studentUI.manageIssueStudent(user);
					break;
				case 3:
					studentUI.manageFeesStudent(user);
					break;
				case 4:
					studentUI.checkMarks(user);
					break;
				case 5:
					studentUI.readNotifications(user);
					break;
				case 6:
					studentUI.viewAttendance(user);
					break;
				case 7:
					studentUI.viewMarksheet(user);
					break;
				case 8:
					studentUI.logout();
					break;
				case 9:
					System.out.println("Exiting...");
					System.exit(0);
					return;
				default:
					System.out.println("Invalid choice. Please try again.");
				}
			}
		} else if (role.equals("Faculty")) {
			while (true) {
				System.out.println(StringConstants.FACULTY_MENU);
				System.out.println("Enter your choice: ");
				int choice = Validator.getUserChoice();
				// scanner.nextLine();
				switch (choice) {
				case 1:
					facultyUI.manageAttendance();
					break;
				case 2:
					facultyUI.manageLeaves(user, role);
					break;
				case 3:
					facultyUI.raiseIssue(user);
					break;
				case 4:
					facultyUI.checkIssueStatus(user);
					break;
				case 5:
					facultyUI.addMarks();
					break;
				case 6:
					facultyUI.generateMarksheet();
					break;
				case 7:
					facultyUI.readNotifications(user);
					break;
				case 8:
					facultyUI.logout();
					break;
				case 9:
					System.out.println("Exiting...");
					System.exit(0);
					return;
				default:
					System.out.println("Invalid choice. Please try again.");
				}
			}
		} else {
			System.out.println("Enter valid role!");
		}
	}
}