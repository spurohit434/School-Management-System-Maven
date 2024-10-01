package com.wg.controller;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import com.wg.constants.StringConstants;
import com.wg.helper.Validator;
import com.wg.model.Course;
import com.wg.services.CourseService;

public class CourseController {

	public static final String STANDARD = "Standard";
	public static final String COURSE_NAME = "CourseName";
	private final CourseService courseService = new CourseService();
	Scanner scanner = new Scanner(System.in);

//	public CourseController(CourseService courseService) {
//		this.courseService = courseService;
//	}

	public CourseController() {
	}

	public List<Course> getAllCourses() {
		List<Course> course = null;
		course = courseService.getAllCourses();
		return course;
	}

	public void updateCourse(String courseId) {
		try {
			Course course = courseService.getCourse(courseId);
			if (course != null) {
				boolean continueUpdating = true;

				while (continueUpdating) {
					displayUpdateMenu();

					System.out.print("Select an option(1-3): ");
					int choice = Validator.getUserChoice();

					String columnToUpdate = "";

					switch (choice) {
					case 1:
						columnToUpdate = COURSE_NAME;
						System.out.print("Enter new Course name: ");
						String courseName = "";
						while (true) {
							try {
								courseName = scanner.nextLine().trim();
								if (courseName.matches("^[a-zA-Z\\s]+$") && !courseName.isEmpty()) {
									break;
								} else {
									System.out.println(StringConstants.INVALID_INPUT);
								}
							} catch (InputMismatchException e) {
								System.out.println(StringConstants.INVALID_INPUT);
								scanner.next();
							}
						}
						course.setCourseName(courseName);
						break;
					case 2:
						columnToUpdate = STANDARD;
						System.out.print("Enter new standard: ");
						int standard = scanner.nextInt();
						boolean validStandard = false;
						while (!validStandard) {
							try {
								if (standard >= 1 && standard <= 12) {
									validStandard = true;
								} else {
									System.out.println(StringConstants.ENTER_VALID_STANDARD_1_12);
									standard = scanner.nextInt();
									validStandard = false;
								}
							} catch (InputMismatchException e) {
								System.out.println(
										StringConstants.INVALID_INPUT_PLEASE_ENTER_A_VALID_INTEGER_FOR_STANDARD2);
								scanner.next();
							}
						}
						course.setStandard(standard);
						break;
					case 3:
						continueUpdating = false;
						break;
					default:
						System.out.println("Invalid option. Please try again.");
					}
					if (!continueUpdating) {
						break;
					}
					boolean flag = courseService.updateCourse(course, columnToUpdate);
					if (flag == true) {
						System.out.println("Course updated successfully.");
					} else {
						System.out.println("Course can not be updated");
					}
				}
			} else {
				System.out.println("Course Not found");
			}
		} catch (Exception e) {
			System.out.println("Error updating Course: " + e.getMessage());
		}
	}

	private void displayUpdateMenu() {
		System.out.println("\nWhich field would you like to update?");
		System.out.println("1. Course Name");
		System.out.println("2. Standard");
		System.out.println("3. Done updating");
	}

	public boolean deleteCourse(String courseId) {
		boolean flag = false;
		flag = courseService.deleteCourse(courseId);
		return flag;
	}

	public boolean addCourse(String courseName, int standard) {
		String randomString = UUID.randomUUID().toString();
		int desiredLength = 8;
		if (desiredLength > randomString.length()) {
			desiredLength = randomString.length();
		}
		String courseId = randomString.substring(0, desiredLength);
		if (courseId == null || courseName == null || standard > 12) {
			System.out.println("Enter valid credentials");
			return false;
		}

		Course course = new Course(courseId, courseName, standard);
		courseService.addCourse(course);
		return true;
	}

	public Course getCourse(String courseId) {
		Course course = null;
		course = courseService.getCourse(courseId);
		if (course == null) {
			System.out.println("Course Not Found");
		}
		return course;
	}
}