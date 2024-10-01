package com.wg.model;

public class CourseMarks {

	private String userId;
	private String courseId;
	private double marks;
	private int standard;

	public int getStandard() {
		return standard;
	}

	public void setStandard(int standard) {
		this.standard = standard;
	}

	public CourseMarks(String userId, String courseId, double marks, int standard) {
		this.userId = userId;
		this.courseId = courseId;
		this.marks = marks;
		this.standard = standard;
	}

	public CourseMarks() {

	}

	// Getters and Setters
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public double getMarks() {
		return marks;
	}

	public void setMarks(double marks) {
		this.marks = marks;
	}

	@Override
	public String toString() {
		return "CourseMarks{" + "userId='" + userId + '\'' + ", courseId='" + courseId + '\'' + ", marks=" + marks
				+ '}';
	}
}