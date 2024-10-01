package com.wg.controller;

import java.util.List;
import java.util.Scanner;

import com.wg.model.CourseMarks;
import com.wg.services.CourseMarksService;

public class CourseMarksController {
	private final CourseMarksService courseMarksService = new CourseMarksService();
	Scanner scanner = new Scanner(System.in);

	public CourseMarksController() {

	}

	public void addMarks(String userId, String courseId, double marks, int standard) {
		if (marks > 100 || marks < 0) {
			System.out.println("Enter valid marks");
			return;
		}
		CourseMarks courseMarks = new CourseMarks(userId, courseId, marks, standard);
		courseMarksService.addMarks(courseMarks);
	}

	public List<CourseMarks> checkMarks(String userId) {
		return courseMarksService.checkMarks(userId);
	}
}